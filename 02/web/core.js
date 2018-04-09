var appkey = '8luwapkvucoil';

var tools = {
	noop: function() {},
	request: function(options) {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				var result = xhr.responseText || "{}";
				result = JSON.parse(result);
				options.success && options.success(result);
			}
		};

		var method = options.url;
		var url = options.url;
		var method = options.method || 'GET';
		xhr.open(method, url);
		var headers = options.headers;
		for (var key in headers) {
			var value = headers[key];
			xhr.setRequestHeader(key, value);
		}
		var body = JSON.stringify(options.body);
		xhr.send(body);
	},
	render: function(data, template) {
		template = template || "";
		data = data || [""];
		var re = /{{((?:(?!}}).)+)}}/g,
			reExp = /(^( )?(var|if|for|else|switch|case|break|{|}))(.*)?/g,
			code = 'var r=[];\n',
			cursor = 0;
		var add = function(line, js) {
			js ? (code += line.match(reExp) ? line + '\n' : 'r.push(' + line + ');\n') :
				(code += line != '' ? 'r.push("' + line.replace(/"/g, '\\"') + '");\n' : '');
			return add;
		}
		var match;
		while (match = re.exec(template)) {
			add(template.slice(cursor, match.index))(match[1], true);
			cursor = match.index + match[0].length;
		}
		add(template.substr(cursor, template.length - cursor));
		code += 'return r.join("");';

		data = isNaN(data.length) ? [data] : data;
		var html = "";
		for (var i = 0, length = data.length; i < length; i++) {
			html += new Function(code.replace(/[\r\t\n]/g, '')).apply(data[i]);
		}
		return html;
	},
	getDom: function(id) {
		return document.getElementById(id);
	}
};

var activeConversation = {};

var connect = function(config, callback) {
	// 初始化
	RongIMClient.init(appkey);

	// 设置消息监听
	var onReceived = function(message) {
		var type = message.conversationType;
		var targetId = message.targetId;
		var isActive = (type == activeConversation.type && targetId == activeConversation.target);
		refreshConversations();
	};
	RongIMClient.setOnReceiveMessageListener({
		onReceived: onReceived
	});

	// 设置状态监听
	var Status = RongIMLib.ConnectionStatus;
	RongIMClient.setConnectionStatusListener({
		onChanged: function(status) {
			var isConnected = (status == Status.CONNECTED);
			if (isConnected) {
				var instance = RongIMClient.getInstance();
				callback(instance);
			}
		}
	});

	// 连接融云服务器
	var token = config.token;
	RongIMClient.connect(token, {
		onSuccess: function(userId) {
			console.log('connect Successfully.');
		},
		onTokenIncorrect: function() {
			console.log('token 无效');
		},
		onError: function(errorCode) {
			console.log('connect failed: ', errorCode);
		}
	});
};

var getUsers = function(params, callback) {
	callback = callback || tools.noop;
	var ids = params.ids;
	var url = 'http://127.0.0.1:8585/batch';
	tools.request({
		url: url,
		method: 'POST',
		body: {
			ids: ids
		},
		headers: {
			'Content-Type': 'application/json'
		},
		success: callback
	});
};

var getTime = function(time) {
	var date = new Date(time);
	var hours = date.getHours();
	var minutes = date.getMinutes();
	return hours + ':' + minutes;
};

var getConversationList = function(callback) {
	var instance = RongIMClient.getInstance();
	instance.getConversationList({
		onSuccess: function(conversationList) {
			var ids = [];
			conversationList.forEach(function(conversation) {
				ids.push(conversation.targetId);
				ids.push(conversation.latestMessage.senderUserId);
			});

			getUsers({ids: ids}, function(users){
				conversationList.forEach(function(conversation) {
				var targetId = conversation.targetId;
				var latestMessage = conversation.latestMessage;
				var senderId = latestMessage.senderUserId;
				var sentTime = latestMessage.sentTime;
				conversation.target = users[targetId];
				conversation.sender = users[senderId];
				conversation.time = getTime(sentTime)
				});
				callback(conversationList);
			});

		},
		onError: function(error) {
			console.error(error);
		}
	}, null);
};

var refreshConversations = function() {
	getConversationList(function(list) {
		var tpl = [
			'<div class="rong-conversation" target={{this.targetId}} type={{this.conversationType}}>',
			'<div class="rong-avatar" style="background-image: url({{this.target.portrait}})"></div>',
			'<div class="rong-conversation-title">{{this.target.name}}</div>',
			'<div class="rong-conversation-message">',
			'<span class="rong-conversation-message-sender">{{this.sender.name}}:</span>',
			'<em class="rong-conversation-message-content">{{this.latestMessage.content.content}}</em>',
			'<span class="rong-conversation-message-senttime">{{this.time}}</span>',
			'</div>',
			'</div>'
		].join('');
		var html = '';
		for (var i = 0; i < list.length; i++) {
			html += tools.render(list[i], tpl)
		}
		var el = tools.getDom('rong-conversations');
		el.innerHTML = html;

		var addEvent = function() {
			var els = document.getElementsByClassName('rong-conversation');
			for (var i = 0; i < els.length; i++) {
				var el = els[i];
				el.onclick = function(e) {
					var type = parseInt(this.getAttribute('type'));
					var target = this.getAttribute('target')
					switchConversation({
						type: type,
						target: target
					});
				};
			}
		};
		setTimeout(function() {
			addEvent();
		});
	});
};

var connectSuccess = function() {
	refreshConversations();
};

var login = function(user, callback) {
	callback = callback || tools.callback;
	// 获取 Token
	var url = 'http://127.0.0.1:8585/get_token/userA';
	tools.request({
		url: url,
		success: function(result) {
			connect(result, connectSuccess);
			callback();
		}
	});
};

var sendMessage = function(content) {
	var type = activeConversation.type;
	var targetId = activeConversation.target;
	if (!type) {
		return;
	}
	var msg = new RongIMLib.TextMessage({
		content: content
	});
	RongIMClient.getInstance().sendMessage(type, targetId, msg, {
		onSuccess: function(message) {
			var senderId = message.senderUserId;
			var ids = [senderId];
			getUsers({ids: ids}, function(users){
				message.sender = users[senderId];
				renderMessage([message]);
			});
			refreshConversations();
		},
		onError: function(errorCode, message) {
			console.log('Sent failed:' + info);
		}
	});
};

var getHistoryMessages = function(params, callback) {
	var instance = RongIMClient.getInstance();
	var type = params.type;
	var targetId = params.target;
	var timestamp = 0;
	var count = 20;
	instance.getHistoryMessages(type, targetId, timestamp, count, {
		onSuccess: function(msgList, hasMsg) {
			var ids = [];
			msgList.forEach(function(msg) {
				ids.push(msg.senderUserId);
			});

			getUsers({
				ids: ids
			}, function(users){
				msgList.forEach(function(message) {
					message.sender = users[message.senderUserId];
				});
				callback(msgList);
			});
		},
		onError: function(error) {
			console.log("GetHistoryMessages,errorcode:" + error);
		}
	});
};

var clearMessages = function() {
	var el = tools.getDom('rong-messages');
	el.innerHTML = '';
};

var renderMessage = function(messageList) {
	var el = tools.getDom('rong-messages');
	var tpl = [
		'<div class="rong-message">',
		'<div class="rong-avatar rong-message-avatar" style="background-image: url({{this.sender.portrait}})"></div>',
		'<div class="rong-message-content"> {{this.content.content}} </div>',
		'</div>'
	].join('');
	var html = '';
	for (var i = 0; i < messageList.length; i++) {
		html += tools.render(messageList[i], tpl)
	}
	el.innerHTML += html;
};

var switchConversation = function(conversation) {
	activeConversation = conversation;
	clearMessages();
	getHistoryMessages(conversation, renderMessage);
};

var user = null;
login(user, function() {
	var editorEl = document.getElementsByClassName('rong-editor-input')[0];
	editorEl.addEventListener('keyup', function(e) {
		e.preventDefault();
		var isEnter = (e.keyCode == 13);
		if (isEnter) {
			var content = editorEl.value;
			sendMessage(content);
			setTimeout(function() {
				editorEl.value = '';
			});
		}
		return this.value;
	}, false);
});