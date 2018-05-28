### POST /user/get_token

登录后，获取融云 Token，App Server 需获取当前用户的 UserId 调用融云 ServerSDK 获取用户 token 接口并返回 token 至前端 

**请求成功**

```js
{
  "code": 200,
  "result":{
    "token": "XTGzPIboQ7AjrdxiIV/GUG20V27X..."
  }
}
```
