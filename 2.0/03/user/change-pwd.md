### POST /user/change_password

当前登录用户通过旧密码设置新密码

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| oldPassword|  String  | 是   | 原密码
| newPassword|  String  | 是   | 新密码

```json
{
  "oldPassword": "P@ssw0rdOld",
  "newPassword": "P@ssw0rdNew"
}
```

**请求成功**

```json
{
  "code": 200
}
```