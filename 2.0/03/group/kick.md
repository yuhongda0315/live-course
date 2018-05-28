### POST /group/kick

群主或群管理将群成员移出群组

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| groupId    | String   | 是   | 群组 Id
| memberIds  | array    | 是   | 被移除的成员列表

```json
{
  "groupId": "KC6kot3ID",
  "memberIds": ["52dzNbLBZ"]
}
```

**请求成功**

```js
{
  "code": 200
}
```