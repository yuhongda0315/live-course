const express = require('express');
let router = express.Router();

let Config = require('../config');

let RongSDK = require('rongcloud-sdk')({
	appkey: Config.appkey,
	secret: Config.secret
});

let User = RongSDK.User;

let utils = require('../utils');

let DB = require('../db');
let DBUser = DB.User;
/*
	user = {
		phone: '13269772799',
		password: 'rongcloud123.',
		nickname: 'Martin',
		portraitUri: 'http://www.rongcloud.cn/portart.jpg'
	};
*/
router.post('/register', (req, res) => {
	let user = req.body;
	let phone = user.phone;

	DBUser.find(user).then(_user => {
		if (_user) {
			//可按业务逻辑处理，决定直接返回或者提示用户已存在
			return res.json(_user);
		}

		DBUser.create(user).then(user => {
			let formatUser = utils.rename(user, {nickname: 'name', portraitUri: 'portrait'});
			//向 RongCloud Server 注册用户
			User.register(formatUser).then(_user => {
				let token = _user.token;
				utils.extend(user, {token: token});
				// update
				DBUser.Cache.set(phone, user);
				res.json({
					code: 200
				});
			}, error => { 
				res.json({
					code: 1001,
					msg: error
				})
			});
		});
	});

});

router.post('/login', (req, res) => {
	let user = req.body;
	DBUser.find(user).then(_user => {
		if (!_user) {
			return res.json({
				code: 1002,
				msg: 'unkown user'
			})
		}

		let isEqual = utils.isPropEqual(user, _user, 'password');
		if (!isEqual) {
			return res.json({
				code: 1003,
				msg: 'username and password not match'
			});
		}
		// 没有数据存储，没有查询
		delete _user.password;
		return res.json(_user);
	});
});

module.exports = router;