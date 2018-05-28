### GET /user/:id

获取单个用户信息

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| id         |  String  | 是   | 用户 Id

```json
{
  "id": "userId01"
}
```

**请求成功**

```json
{
  "code": 200,
  "result":{
    "id": "sdf9sd0df98",
    "nickname": "Tom",
    "portraitUri": "http://test.com/user/abc123.jpg"
  }
}
```
