let User = {};

let utils = require('./utils');
let Cache = utils.Cache;

let UserCache = new Cache();

User.find = (user) => {
	let phone = user.phone;
	user = UserCache.get(phone);
	return Promise.resolve(user);
};

let genUId = () => {
	return (+new Date).toString(16);
};

User.create = (user) => {
	user.id = genUId();
	return Promise.resolve(user);
};

User.Cache = UserCache;

module.exports = {
	User
};