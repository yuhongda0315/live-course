var RongCloudSDK = require('rongcloud-sdk');

//创建应用、获取 Appkey、Secret

var RongSDK = RongCloudSDK({
    appkey: '8brlm7ufrg9e3',
    secret: '7oT89OelVovM'
});
var User = RongSDK.User;
// API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/user/user.html#register
var user = {
	id: 'user_a',
	name: 'Maritn',
	portrait: 'http://7xogjk.com1.z0.glb.clouddn.com/IuDkFprSQ1493563384017406982'
};
User.register(user).then(result => {
	console.log(result);
}, error => { 
	console.log(error);
});
