构建小泡专用的openfire.

做两个docker 一个本地即可构建，一个可无环境构建。

clientControl 无配置
accountCenter 无配置 貌似也没什么用
restApi 估计改过不少…………
mucplugin 1
presence 无配置
websocket 无配置

# 测试点
```bash
curl -v -H "Authorization: N3rw8g4XiqijHwY2" http://localhost:9090/plugins/restapi/v1/users
```

