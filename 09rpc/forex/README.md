# 第九周 作业7 TCC 外汇交易处理

结合 dubbo+hmily，实现一个 TCC 外汇交易处理，代码提交到 GitHub:

用户 A 的美元账户和人民币账户都在 A 库，使用 1 美元兑换 7 人民币 ;

用户 B 的美元账户和人民币账户都在 B 库，使用 7 人民币兑换 1 美元 ;

设计账户表，冻结资产表，实现上述两个本地事务的分布式事务。

## 模块

- forex-cny 负责人民币的转入转出
- forex-usd 负责美元的转入转出
- forex-ds 动态数据源，根据用户名判断应该去哪个库
- forex-common 提供汇率计算
- forex-user 通过用户名查询用户id

## 数据库

- forex_a
- forex_b
- forex_common 用于查询汇率
- forex_user 查询用户id

## 操作

### 用户A美元兑换人民币

发送到美元模块，模块根据用户判断所在库并切换数据源
```http request
username=A&amount=1&sourceCurrency=CNY&destCurrency=USD
```

### 用户B人民币兑换美元

发送到人民币模块，模块根据用户判断所在库并切换数据源
```http request
username=B&amount=7&sourceCurrency=USD&destCurrency=CNY
```