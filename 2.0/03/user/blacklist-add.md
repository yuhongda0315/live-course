### POST /user/add_to_blacklist

将好友加入黑名单

参数**属性说明**：

| 参数        |  类型    | 必填  | 说明              
| :----------|:-------- |:-----|:----------------
| friendId   |  String  | 是   | 好友 Id

```json
{
  "friendId": "d9ewr89j238"
}
```

**请求成功**

```json
{
  "code": 200
}
```