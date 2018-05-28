### POST /group/add

添加群成员

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| groupId    | String   | 是   | 群组 Id
| memberIds  | array    | 是   | 被邀请进去的成员列表

```json
{
  "groupId": "KC6kot3ID",
  "memberIds": ["52dzNbLBZ"]
}
```

**请求成功**

```json
{
  "code": 200
}
```