### POST /user/register

用户注册

参数**属性说明**：

| 参数                  |  类型    | 必填  | 说明              
| :-------------------- |:-------- |:-----|:----------------
| nickname              |  String  | 是   | 用户昵称
| password              |  String  | 是   | 登录密码
| verification_token    |  String  | 是   | 验证验证码 [/user/verify_code](./verify.md) 返回的 token

```json
{
  "nickname": "Tom",
  "password": "P@ssw0rd",
  "verification_token": "75dd0f90-9b0d-11e5-803f-59b82644bc50"
}
```

**请求成功**

```js
{
  "code": 200,
  "result": {
    // 注册的用户 Id
    "id": "userId"
  }
}
```
