### POST /user/blacklist

获取当前用户黑名单列表

**请求成功**

```js
{
  "code": 200,
  "result": [
    {
      "id": "sdf9sd0df98",
      "nickname": "Tom",
      "portraitUri": "http://test.com/user/abc123.jpg"
    },
    {
      "id": "fgh809fg098",
      "nickname": "Jerry",
      "portraitUri": "http://test.com/user/abc234.jpg"
    }
  ]
}
```