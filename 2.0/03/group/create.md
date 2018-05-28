### POST /group/create

创建群组

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| name        | String   | 是   | 群组名称
| memberIds  | array    | 是   | 群成员列表

```json
{
  "name": "RongCloud",
  "memberIds": ["AUj8X32w1", "ODbpJIgrL"]
}
```

**请求成功**

```json
{
  "code":200,
  "result": {
    "id": "RfqHbcjes"
  }
}
```