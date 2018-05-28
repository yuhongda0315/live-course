### POST /group/set_portrait_uri

设置群头像

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| groupId    | String   | 是   | 群组 Id
| portraitUri| String   | 是   | 群组头像

```json
{
  "groupId": "KC6kot3ID",
  "portraitUri": "http://7xogjk.com1.z0.glb.clouddn.com/u0LUuhzHm1466557920584458984"
}
```

**请求成功**

```js
{
  "code": 200
}
```