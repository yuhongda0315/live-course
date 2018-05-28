### POST /user/batch

批量获取用户信息

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| userIds    |  array   | 是   | 用户 Id 列表

```json
{
  "userIds": ["userId01", "userId02"]
}
```

**请求成功**

```js
{
  "code": 200,
  "result": {
    "users": [{
      "id": "userId01",
      "nickname": "Martin",
      "portraitUri": "http://image.rongcloud.cn/martin.jpg"
    },{
      "id": "userId02",
      "nickname": "Thomas",
      "portraitUri": "http://image.rongcloud.cn/thomas.jpg"
    }]
  }
}
```