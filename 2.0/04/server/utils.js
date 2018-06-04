'use strict';
let toJSON = (content) => {
  return JSON.stringify(content);
};

let noop = () => {
};

let isArray = (obj) => {
  return Object.prototype.toString.call(obj) == "[object Array]";
};

let isObject = (obj) => {
  return Object.prototype.toString.call(obj) == '[object Object]';
};

let forEach = (obj, callback) => {
  callback = callback || noop;
  var loopObj = function() {
    for (var key in obj) {
      callback(obj[key], key, obj);
    }
  };
  var loopArr = function() {
    for (var i = 0, len = obj.length; i < len; i++) {
      callback(obj[i], i);
    }
  };
  if (isObject(obj)) {
    loopObj();
  }
  if (isArray(obj)) {
    loopArr();
  }
};

let rename = (origin, newNames) => {
  var isObj = isObject(origin);
  if (isObj) {
    origin = [origin];
  }
  origin = JSON.parse(JSON.stringify(origin));
  var updateProperty = function(val, key, obj) {
    delete obj[key];
    key = newNames[key];
    obj[key] = val;
  };
  forEach(origin, function(item) {
    forEach(item, function(val, key, obj) {
      var isRename = (key in newNames);
      (isRename ? updateProperty : noop)(val, key, obj);
    });
  });
  return isObj ? origin[0] : origin;
};

function Cache() {
  let cache = {};

  this.set = (key, value) => {
    cache[key] = value;
  };
  this.get = (key) => {
    return cache[key];
  };
  this.del = (key) => {
    delete cache[key];
  }
}

let extend = (target, source) => {
  forEach(source, (value, key) => {
    target[key] = value;
  });
};

let isPropEqual = (target, source, prop) => {
  return (target[prop] == source[prop]);
};

module.exports = {
  isPropEqual,
  extend,
  Cache,
  toJSON,
  rename
};