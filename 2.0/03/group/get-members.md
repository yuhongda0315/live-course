### GET /group/:id/members

获取群成员列表

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| groupId    | String   | 是   | 群组 Id

```json
{
  "groupId": "AUj8X3sa"
}
```

**请求成功**

```json
{
"code": 200,
"result": [
  {
    "displayName": "Martin",
    "role": 1,
    "createdAt": "2016-11-22T03:06:13.000Z",
    "updatedAt": "2016-11-22T03:06:13.000Z",
    "user": {
    "id": "xNlpDTUmw",
    "nickname": "zl01",
    "portraitUri": "http://a.ronglcoud.cn/zl.jpg"
    }
  },{
    "displayName": "Tom",
    "role": 1,
    "createdAt": "2016-11-22T03:14:09.000Z",
    "updatedAt": "2016-11-22T03:14:09.000Z",
    "user": {
      "id": "h6nEgcPC7",
      "nickname": "zl02",
      "portraitUri": "http://a.ronglcoud.cn/h4.jpg"
    }
  }]
}
```