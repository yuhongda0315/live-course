### 发送消息携带用户信息

1、请求 `App Server`

场景: IM 即时通讯引用，涉及修改用户信息的应用

示例:

```
// 向应用服务请求用户信息

var getUser = function(user, callback){
	var url = 'getUser/userId';
	$.get(url, callback);
};

RongIMClient.setOnReceiveMessageListener({
    onReceived: function (message) {
    	var user = {
    		id: message.senderUserId
    	}
		getUser(user, function(user){
			message.user = user;
			//TODO 后续业务
		});        
    }
});

```

2、消息中携带用户信息

场景: 直播聊天室

```
var user = {
	id: 'userA',
	name: '小红',
	portrait: 'http://rongcloud.cn/portait.jpg'
};
var message = new RongILib.TextMessage({
	content: 'hellowrold',
	user: user
});
```

### 文件、图片消息

[上传](https://github.com/rongcloud/rongcloud-web-im-upload)
	
[文件](http://support.rongcloud.cn/kb/Njgx)




