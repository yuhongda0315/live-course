var appkey = '8luwapkvucoil';

/*工具类*/
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

/* 获取用户信息 ======================================*/
var getUsers = function(params, callback) {
	callback = callback || tools.noop;
	var ids = params.ids;
	var url = 'http://127.0.0.1:8585/user/batch';
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

var scroptTop = function() {
	var messageBox = tools.getDom('rong-messages');
	messageBox.scrollTop = messageBox.scrollHeight;
};
/*
消息发送==============================================
*/
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
			getUsers({
				ids: ids
			}, function(users) {
				message.sender = users[senderId];
				renderMessage([message], function() {
					scroptTop();
				});
			});
			refreshConversations();
		},
		onError: function(errorCode, message) {
			console.log('Sent failed:' + errorCode);
		}
	});
};
/*清除上一个会话消息*/
var clearMessages = function() {
	var el = tools.getDom('rong-messages');
	el.innerHTML = '';
};
/*
渲染消息列表
*/
var renderMessage = function(messageList, callback) {
	callback = callback || tools.noop;
	var el = tools.getDom('rong-messages');
	var tpl = [
		'<div class="rong-message">',
		'<div class="rong-avatar rong-message-avatar" style="background-image: url({{this.sender.portrait}})"></div>',
		'<div class="rong-message-content"> {{this.content.content}} </div>',
		'</div>'
	].join('');

	var notifyTpl = '<span class="rong-message-notify">{{this.content.operatorUserId}} 创建了群组</span>';
	var html = '';

	var messageType = RongIMClient.MessageType;
	for (var i = 0; i < messageList.length; i++) {
		var message = messageList[i];
		var t = tpl;
		var isNotify = (message.messageType == messageType.GroupNotificationMessage);
		if (isNotify) {
			t = notifyTpl;
		}
		html += tools.render(message, t)
	}
	el.innerHTML += html;
	callback();
};

/*
获取会话中的历史消息======================================
*/
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
			}, function(users) {
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

/*
切换会话
*/
var switchConversation = function(conversation) {
	activeConversation = conversation;
	clearMessages();
	getHistoryMessages(conversation, renderMessage);
};

/*
1、获取会话列表
2、从 App Server 获取用信息，绑定至会话列表
*/
var getTime = function(time) {
	var date = new Date(time);
	var hours = date.getHours();
	var minutes = date.getMinutes();
	return hours + ':' + minutes;
};
/*
	获取会话列表======================================
*/
var getConversationList = function(callback) {
	var instance = RongIMClient.getInstance();
	instance.getConversationList({
		onSuccess: function(conversationList) {
			var ids = [], groupIds = [];
			conversationList.forEach(function(conversation) {
				var isPrivate = (conversation.conversationType == 1);
				if (isPrivate) {
					ids.push(conversation.targetId);
				}else{
					groupIds.push(conversation.targetId);
				}
				ids.push(conversation.latestMessage.senderUserId);
			});

			getUsers({
				ids: ids
			}, function(users) {
				conversationList.forEach(function(conversation) {
					var targetId = conversation.targetId;
					var latestMessage = conversation.latestMessage;
					var senderId = latestMessage.senderUserId;
					var sentTime = latestMessage.sentTime;

					conversation.target = users[targetId];
					conversation.sender = users[senderId];
					conversation.time = getTime(sentTime)
				});

				Group.batch({ids: groupIds}, function(groups){
					conversationList.forEach(function(conversation) {
						var isGroup = (conversation.conversationType == 3);
						var targetId = conversation.targetId;
						if (isGroup) {
							conversation.target = groups[targetId];
						}
					});

					callback(conversationList);
				});
			});

		},
		onError: function(error) {
			console.error(error);
		}
	}, null);
};
var refreshConversations = function() {
	getConversationList(function(list) {
		var header = '<div class="rong-header rong-conversations-header"></div>';
		var tpl = [
			'<div class="rong-conversation" target={{this.targetId}} type={{this.conversationType}} name={{this.target.name}}>',
			'<div class="rong-avatar rong-conversation-avatar" style="background-image: url({{this.target.portrait}})"></div>',
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
			var conversation = list[i];
			var message = conversation.latestMessage;
			var isNotify = (message.messageType == RongIMClient.MessageType.GroupNotificationMessage)
			if (isNotify) {
				message.content.content = '[群通知]';
			}
			html += tools.render(conversation, tpl)
		}
		var el = tools.getDom('rong-conversations');
		header += html;
		el.innerHTML = header;

		var addEvent = function() {
			var els = document.getElementsByClassName('rong-conversation');
			for (var i = 0; i < els.length; i++) {
				var el = els[i];
				el.onclick = function(e) {
					var type = parseInt(this.getAttribute('type'));
					var target = this.getAttribute('target')
					var name = this.getAttribute('name')
					switchConversation({
						type: type,
						target: target
					});
					var header = tools.getDom('rong-header-content');
					header.setAttribute('type', type);
					header.setAttribute('target', target);
					header.innerHTML = name;

					// 切换会话隐藏群成员
					var memberEl = tools.getDom('rong-group-members');
					memberEl.style.display = 'none';

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

/*连接融云服务器======================================*/
var connect = function(config, callback) {
	// 初始化
	RongIMClient.init(appkey);

	// 设置消息监听
	var onReceived = function(message) {
		var type = message.conversationType;
		var targetId = message.targetId;
		var isActive = (type == activeConversation.type && targetId == activeConversation.target);
		refreshConversations();
		var senderId = message.senderUserId;
		var ids = [senderId];
		if (isActive) {
			getUsers({
				ids: ids
			}, function(users) {
				message.sender = users[senderId];
				renderMessage([message], function() {
					scroptTop();
				});
			});
		}
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

var addEvent = function(){
	var editorEl = document.getElementsByClassName('rong-editor-input')[0];
	editorEl.addEventListener('keyup', function(e) {
		e.preventDefault();
		var isEnter = (e.keyCode == 13);
		if (isEnter) {
			var content = editorEl.value;
			content = content.replace(/\n/, '');
			var isEmpty = (content == '')
			if (isEmpty) {
				content = '';
				return;
			}
			sendMessage(content);
			setTimeout(function() {
				editorEl.value = '';
			});
		}
		return this.value;
	}, false);
};

var renderUser = function(user){
	user = user || {};
	var tpl = '<div class="rong-avatar rong-userinfo" style="background-image: url({{this.portrait}});"></div>'
	var html = tools.render(user, tpl);
	var header = tools.getDom('rong-nav-user');
	header.innerHTML = html;
};

/**********************Group start******************************/
var Group = {
	create: function(){
		var search = location.search;
		var creator = search.replace('?', '');

		var url = 'http://127.0.0.1:8585/group/create';
		var members = [
						'User1','User2','User3','User4','User5','User6','User7',
						'User8','User9','User10','User11','User12'
						];
		var name = '乌托邦';
		tools.request({
			url: url,
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: {
				creator: creator,
				name: name,
				members: members
			},
			success: function(result) {
				console.log(result);
			}
		});
	},
	batch: function(params, callback){
		callback = callback || tools.noop;
		var ids = params.ids;
		var url = 'http://127.0.0.1:8585/group/batch';
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
	},
	get: function(params, callback){
		callback = callback || tools.noop;
		var id = params.id;
		var url = 'http://127.0.0.1:8585/group/' + id;
		tools.request({
			url: url,
			headers: {
				'Content-Type': 'application/json'
			},
			success: callback
		});
	}
};

var getGroup = function(params){
	var memberEl = tools.getDom('rong-group-members');
	var id = params.id;
	Group.get({id: id}, function(group){
		var tpl = [
			'<div class="rong-group-info">',
				'<div class="rong-avatar rong-group-avatar"></div>',
				'<div class="rong-group-name">{{this.name}}</div>',
				'<div class="rong-group-count">群成员 {{this.members.length}} 人</div>',
			'</div>',
			'<ul class="rong-members">',
				'{{ for( var index in this.members ){ }} ',
				'<li class="rong-member">',
					'<div class="rong-avatar rong-member-avatar" style="background-image: url({{this.members[index].portrait}});"></div>',
					'<div class="rong-member-name">{{this.members[index].id}}</div>',
				'</li>',
				'{{ } }}',
			'</ul>'
		].join('');
		var html = tools.render(group, tpl);
		memberEl.innerHTML = html;
		memberEl.style.display = 'block';
	});
};
setTimeout(function(){
	var element = tools.getDom('rong-nav-create');
	element.onclick = function(){
		Group.create();
	};

	var chatHeader = tools.getDom('rong-header-content');
	chatHeader.onclick = function(){
		var target = this.getAttribute('target');
		var type = this.getAttribute('type');
		var isGroup = (type == RongIMLib.ConversationType.GROUP);
		if (isGroup) {
			getGroup({
				id: target
			});
		}
	};
});

/**********************Group End******************************/

/*
用户登录==============================================
1、身份验证
2、获取 Token
3、连接
*/
var login = function(user, callback) {
	callback = callback || tools.callback;

	var search = location.search;
	var userId = search.replace('?', '');
	// 获取 Token
	var url = 'http://127.0.0.1:8585/login';
	tools.request({
		url: url,
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: {
			id: userId
		},
		success: function(result) {
			connect(result, connectSuccess);
			callback(result);
		}
	});
};

/*自动触发登录*/
var user = null;
login(user, function(user) {
	addEvent();
	renderUser(user);
});