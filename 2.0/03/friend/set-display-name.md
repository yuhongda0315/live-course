### POST /friendship/set_display_name

添加好友

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| friendId    | String   | 是   | 好友 Id
| displayName  | String   | 是   | 备注名称

```json
{
  "friendId": "RfqHbcjes",
  "displayName": "备注"
}
```

**请求成功**

```json
{
  "code": 200
}
```