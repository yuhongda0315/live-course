### GET /group/:id

获取群信息

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| groupId    | String   | 是   | 群组 Id

```json
{
  "groupId": "AUj8X3sa"
}
```

**请求成功**

```js
{
  "code": 200,
  "result": {
    "id": "KC6kot3ID",
    "name": "RongCloud",
    "portraitUri": "",
    // 现有群成员人数
    "memberCount": 13,
    // 群组人数上限
    "maxMemberCount": 500,
    // 创建者
    "creatorId": "I8cpNlo7t",
    "type": 1,
    // 群公告
    "bulletin": null,
    "deletedAt": null
  }
}
```