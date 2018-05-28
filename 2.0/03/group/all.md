### POST /user/groups

获取当前用户所属群组列表

**请求成功**

```json
{
  "code": 200,
  "result": [
    {
      "id": "sdf9sd0df98",
      "name": "Team 1",
      "portraitUri": "http://test.com/group/abc123.jpg",
      "creatorId": "fgh89wefd9",
      "memberCount": 5
    },
    {
      "id": "fgh809fg098",
      "name": "Team 2",
      "portraitUri": "http://test.com/group/abc234.jpg",
      "creatorId": "kl234klj234",
      "memberCount": 8
    }
  ]
}
```