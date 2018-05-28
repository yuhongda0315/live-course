### POST /user/set_portrait_uri

当前用户设置自己的透析那个

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| portraitUri   |  String  | 是   | 用户头像，标准 URL 地址

```json
{
  "portraitUri": "http://test.com/user/abc123.jpg"
}
```

**请求成功**

```json
{
  "code": 200
}
```
