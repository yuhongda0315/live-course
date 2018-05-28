### POST /user/login{#login}

用户登录, 登录成功后，会设置 Cookie，后续接口调用需要登录的权限都依赖于 Cookie

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| region     |  string  | 是   | 国际电话区号
| phone      |  string   | 是   | 手机号
| password   |  string  | 是   | 密码，6 到 20 个字节，不能包含空格

```json
{
  "region": 86,
  "phone": 13912345678,
  "password": "P@ssw0rd"
}
```

**请求成功**

```js
{
  "code": 200,
  "result": {
    // 登录用户 Id
    "id": "5Vg2Xh9f",
    // 融云 Token
    "token": "IWwcquFNlNhLydOQwZlSzscUQQfhEU6nFWJ+yPKQhMU6qP5XXBgOWA1AhckFbQ/t+nm4"
  }
}
```