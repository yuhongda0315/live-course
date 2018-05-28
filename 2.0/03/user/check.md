### POST /user/check_phone_available

检查手机号是否可以注册

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
  "result": true
}
```