var RongIM = (function() {
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
		}
	};
	/*
		config.appkey:
		config.serverUrl
	*/
	var connect = function(config, events) {
		events = events || {};
		var onConnected = events.onConnected || tools.noop;
		var onReceived = events.onReceived || tools.noop;

		// 初始化
		var appkey = config.appkey;
		RongIMClient.init(appkey);

		// 设置消息监听
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
					onConnected(instance);
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
	}

	var getToken = function(config, callback){
		callback = callback || tools.callback;
		// 获取 Token
		var url = config.serverUrl;
		tools.request({
			url: url,
			success: callback
		});
	};

	return {
		connect: connect,
		getToken: getToken
	}
})();