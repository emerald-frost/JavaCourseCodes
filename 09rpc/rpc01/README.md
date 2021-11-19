# 第九周 作业3

- 将服务端写死查找接口实现类变成泛型和反射，在
`io/kimmking/rpcfx/server/RpcfxProviderAutoConfiguration.java`中通过扫描自动加载，
并且注册到zookeeper中

- 网页里写的将客户端动态代理改成AOP，代码注释里写的改成字节码生成，最后使用了cglib增强

- 使用 Netty+HTTP 作为 client 端传输方式

- 客户端使用zookeeper查找provider（实现较为粗略）