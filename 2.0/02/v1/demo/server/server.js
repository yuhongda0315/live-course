var express = require('express')
var bodyParser = require('body-parser');

// 引入 ServerSDK
var RongCloudSDK = require('rongcloud-sdk');

/*
	配置 appkey、secret
*/
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

var Cache = {};

var getUser = () => {
	var nameList = "梦琪忆柳之桃慕青问兰尔岚元香初夏沛菡傲珊曼文乐菱痴珊恨玉惜文香寒新柔语蓉海安夜蓉涵柏水桃醉蓝春儿语琴从彤傲晴语兰又菱碧彤元霜怜梦紫寒妙彤曼易南莲紫翠雨寒易烟如萱若南寻真晓亦向珊慕灵以蕊寻雁映易雪柳孤岚笑霜海云";
	var nameSize = nameList.length;
	var portraits = [
		'https://rongcloud-image.cn.ronghub.com/fa33294a358e7f2abf.gif?e=2147483647&token=CddrKW5AbOMQaDRwc3ReDNvo3-sL_SO1fSUBKV3H:z2QkbpEqUEMrOPrJtV3tBP4gQYo=',
		'http://7xogjk.com1.z0.glb.clouddn.com/01fac54313ad977d6e.gif',
		'https://rongcloud-image.cn.ronghub.com/2fcdba4205860a63fb.gif?e=2147483647&token=livk5rb3__JZjCtEiMxXpQ8QscLxbNLehwhHySnX:m7S0ADf1E-d2bIG3E0vuiZJSH_w=',
		'http://oqekw07cj.bkt.clouddn.com/9da99c4255a24baba1.gif',
		'http://2f.zol-img.com.cn/product/172_100x75/267/cepP02EKJTV6.gif',
		'https://fsprodrcx.cn.ronghub.com/lVMs15VSLeR47CzXlVMs15VbxLGVULo2/timg.gif',
		'https://fsprodrcx.cn.ronghub.com/FmUv4RZmLtL72i_hFmUv4RYqrWMWbCI7/timg+%284%29.gif',
		'https://fsprodrcx.cn.ronghub.com/vJiff7ybnkxRJ59_vJiff7zADyO8gW0a/timg+%285%29.gif',
		'https://fsprodrcx.cn.ronghub.com/5FJuo-RTb5AJ7W6j5FJuo-Rf_-_kU162/timg+%283%29.gif',
		'https://fsprodrcx.cn.ronghub.com/Jx-MkScejaLKoIyRJx-MkScT89YnHp6U/timg+%282%29.gif',
		'https://fsprodrcx.cn.ronghub.com/pQjyn6UJ86xIt_KfpQjyn6UGM_6lDaO-/timg+%281%29.gif',
		'https://fsprodrcx.cn.ronghub.com/1T1xVdU_cGY4gnFV1T1xVdUFyRPVM_4N/test.gif',
		'https://fsprodrcx.cn.ronghub.com/yn2CV8p8g2QnwoJXyn2CV8ppkNXKdrNS/1512691986120.gif',
		'https://fsprodrcx.cn.ronghub.com/B0qmIAdLpxPq9aYgB0qmIAdV5acHSrhp/timg.jpeg'
	];

	var portraitSize = portraits.length;

	var getIndex = (max) => {
		return Math.floor(Math.random() * max);
	};

	var getName = (len) => {
		var names = [];
		for (var i = 0; i < len; i++) {
			var index = getIndex(nameSize);
			names.push(nameList[index]);
		}
		return names.join('');
	};

	var getPortrait = (index) => {
		return portraits[index];
	};

	var name = getName(3);

	var portraitIndex = getIndex(portraitSize);
	var portrait = getPortrait(portraitIndex);

	return {
		name: name,
		portrait: portrait
	};
};

var fastUniq = function(ary) {
	var obj = {},
		i, len = ary.length,
		r = [];
	for (i = 0; i < len; i++){
		 obj[ary[i]] = ary[i]
	};
	for (i in obj) {
		r.push(obj[i])
	};
	return r;
};

/*
	登录
*/
app.post('/login', (req, res) => {
	var params = req.body || {};
	var id = params.id || 'User_A';
	var user = Cache[id] ;

	if (!user) {
		user = getUser();

	}
	user.name = id;
	user.id = id;

	var User = RongSDK.User;
	User.register(user).then(result => {
		result.portrait = user.portrait;
		var result = JSON.stringify(result);
		res.end(result);
	}, error => {
		console.log(error);
	});
});

/*
	批量获取用户信息
*/
app.post('/batch', (req, res) => {
	var body = req.body || {};
	
	var ids = fastUniq(body.ids);

	var result = {};
	ids.forEach((id) => {
		var user = Cache[id];
		if (!user) {
			user = getUser();
			user.name = id;
			Cache[id] = user;
		}
		result[id] = user;
	});
	result = JSON.stringify(result);
	res.end(result);
});

var port = 8585;
app.listen(port);
console.log('listener port : %d', port);