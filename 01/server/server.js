var express      = require('express')
var bodyParser = require('body-parser');
var RongCloudSDK = require('rongcloud-sdk');

var RongSDK = RongCloudSDK({
    appkey: '8luwapkvucoil',
    secret: 'y0icysjl4h3LWz'
});

var app = express()
app.use(bodyParser.json());

app.all('*', (req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Headers', '*');
    res.header('Access-Control-Allow-Methods', '*');
    next();
});

app.get('/get_token', (req, res) => {
	var user = {
		id: 'ujadk90ha',
		name: 'Maritn',
		portrait: 'http://7xogjk.com1.z0.glb.clouddn.com/IuDkFprSQ1493563384017406982'
	};

	var User = RongSDK.User;
	User.register(user).then(result => {
		var result = JSON.stringify(result);
		res.end(result);
	}, error => { 
		console.log(error);
	});
});

app.listen(8585);
console.log('listener port : 8585');
