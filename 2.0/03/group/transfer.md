### POST /group/transfer

转让群主

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| groupId    | String   | 是   | 群组 Id
| userId| String   | 是   | 群内成员 Id

```json
{
  "groupId": "KC6kot3ID",
  "userId": "52dzNbLBZ"
}
```

**请求成功**

```js
{
  "code": 200
}
```