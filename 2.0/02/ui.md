## UI 制作

1、根据功能选择 SDK 里对应的 API，例如: `sendMessage` 、`getConversationList` 等

2、应用服务器提供其他 API 之外所有的数据和功能支持，例如: 用户信息、好友列表、群组信息 

3、合并 `App Server` 和 `RongCloud Server` 两部分数据，通过模板渲染呈现（为了实时响应数据变化，建议使用双向绑定),例如:
  
[Vue](https://cn.vuejs.org/index.html)、[React](https://doc.react-china.org/tutorial/tutorial.html)

4、注册对应的事件，包括 API 的方法（比如发送消息等）或者自行实现的业务方法（注册登录等）