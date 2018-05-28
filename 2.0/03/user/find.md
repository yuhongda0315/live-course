### POST /user/find/:region/:phone

根据手机号查找用户信息

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| region     |  String  | 是   | 国际电话区号
| phone      |  String  | 是   | 手机号

```json
{
  "region": 86,
  "phone": 13912345678
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