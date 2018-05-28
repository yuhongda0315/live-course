### POST /group/set_bulletin

发布群公告

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| groupId    | String   | 是   | 群组 Id
| bulletin       | String   | 是   | 公告内容

```json
{
  "groupId": "KC6kot3ID",
  "bulletin": "@All 明天 4 点下班"
}
```

**请求成功**

```js
{
  "code": 200
}
```