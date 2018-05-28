### GET /friendship/:friendId/profile

获取好友信息

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| friendId    | String   | 是   | 好友 Id
| message    | String   | 是   | 添加好友的备注信息

```json
{
  "friendId": "RfqHbcjes"
}
```

**请求成功**

```json
{ "code":200,
  "result": {
    "displayName": "Wee",
    "user": {
      "id":"g891BoDvN",
      "nickname":"Tina",
      "region":"86",
      "phone":"18221252163",
      "portraitUri":"http://7xogjk.com1.z0.glb.clouddn.com/FjsNMjYoVKfGmA86SNwnggfKgE6_"
    }
  }
}
```