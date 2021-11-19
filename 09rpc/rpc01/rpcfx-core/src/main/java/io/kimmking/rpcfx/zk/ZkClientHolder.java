package io.kimmking.rpcfx.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 这里简单试验一下zk的注册和读取，配置写死不自动加载
 */
public class ZkClientHolder {

	private static final CuratorFramework CLIENT;

	static {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CLIENT = CuratorFrameworkFactory.builder().connectString("localhost:2181").namespace("rpcfx").retryPolicy(retryPolicy).build();
		CLIENT.start();
	}

	public static CuratorFramework getClient() {
		return CLIENT;
	}
}
