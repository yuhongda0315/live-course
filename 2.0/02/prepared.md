## 准备工作

创建 [融云开发者账号](https://developer.rongcloud.cn/signin)、创建应用获取 `AppKey`、`Secret`

引入各端 SDK ，参考文档: http://www.rongcloud.cn/docs/

搭建 `App Sever` 维护用户信息、群组信息、好友关系、并提供对应接口供集成使用

`App Server` 提供与 `RongCloud Server` 对接能力, `获取 Token` 、`发送系统通知` 等

>Q&A

Q1: 什么是 Appkey ？什么是 Secret?

A1: AppKey 是应用的唯一表示，Secret 是每个应用的密钥

Q2: 在开发调试阶段没有 `App Server` 该如何调试？

A2: 用户 `Token` 可以通过融云开发者后台的 API 调试做集成开发, 地址: https://developer.rongcloud.cn/signin

Q3: `App Server` 和 `RongCloud Server` 如何对接？

A3: 融云提供了 [Server SDK](http://rongcloud.github.io/server-sdk-nodejs/docs/v1/) 用来帮助  `App Server` 与 `RongCloud Server`  对接