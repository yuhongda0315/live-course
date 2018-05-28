### POST /group/set_display_name

设置自己的群名片

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| groupId    | String   | 是   | 群组 Id
| portraitUri| String   | 是   | 群组头像

```json
{
  "groupId": "KC6kot3ID",
  "displayName": "Martin"
}
```

**请求成功**

```js
{
  "code": 200
}
```