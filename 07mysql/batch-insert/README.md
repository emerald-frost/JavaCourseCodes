# 测试插入 100 万订单模拟数据

## 单线程插入

结果 millis: 7375

## 多线程插入

结果 millis: 2315

## 优化

jdbc连接参数

```
rewriteBatchedStatements=true
```

提升极大，多线程插入执行时间从11秒降为2秒
