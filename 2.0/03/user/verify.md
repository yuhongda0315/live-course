### POST /user/verify_code

验证验证码

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| region     |  String  | 是   | 国际电话区号
| phone      |  String  | 是   | 手机号
| code       |  String  | 是   | 验证码，调用 [/user/send_code](./send.md) 将验证码发送至手机

```json
{
  "region": 86,
  "phone": 13912345678,
  "code": "1234"
}
```

**请求成功**

```js
{
  "code": 200
}
```
