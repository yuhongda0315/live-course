### POST /friendship/invite

添加好友

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| friendId    | String   | 是   | 好友 Id
| message    | String   | 是   | 添加好友的备注信息

```json
{
  "friendId": "RfqHbcjes",
  "message": "你好，我是 Martin"
}
```

**请求成功**

```json
{
  "code": 200
}
```