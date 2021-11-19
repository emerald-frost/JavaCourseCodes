package io.kimmking.rpcfx.server;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.annotation.RpcfxService;
import io.kimmking.rpcfx.api.ServiceProviderDesc;
import io.kimmking.rpcfx.zk.ZkClientHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(RpcfxProviderProperties.class)
@ConditionalOnProperty(prefix = "rpcfx.provider", value = "enabled", havingValue = "true")
public class RpcfxProviderAutoConfiguration {

	private final RpcfxProviderProperties properties;

	@PostConstruct
	public void init() throws Exception {
		final String scanPackage = properties.getServiceScanPackage();
		//扫描service
		final List<String> serviceClassList = findServiceClass(scanPackage);
		//注册到zk
		for (String className : serviceClassList) {
			registerService(ZkClientHolder.getClient(), className);
		}
	}

	private List<String> findServiceClass(String packageName) {
		final List<String> result = new LinkedList<>();
		final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(RpcfxService.class));
		for (BeanDefinition beanDefinition : provider.findCandidateComponents(packageName)) {
			result.add(beanDefinition.getBeanClassName());
		}
		return result;
	}

	private static void registerService(CuratorFramework client, String service) throws Exception {
		ServiceProviderDesc userServiceSesc = ServiceProviderDesc.builder()
				.host(InetAddress.getLocalHost().getHostAddress())
				.port(8080)
				.serviceClass(service)
				.build();
		String userServiceSescJson = JSON.toJSONString(userServiceSesc);

		//查找接口，通过接口注册
		//不确定有多个接口时如何判断，根据包名找最近的？此处暂时就取唯一的一个
		final Class<?> iClz = Class.forName(service).getInterfaces()[0];

		try {
			if ( null == client.checkExists().forPath("/" + iClz.getName())) {
				client.create().withMode(CreateMode.PERSISTENT).forPath("/" + iClz.getName(), "service".getBytes());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		client.create().withMode(CreateMode.EPHEMERAL).
				forPath( "/" + iClz.getName() + "/" + userServiceSesc.getHost() + "_" + userServiceSesc.getPort(), "provider".getBytes());
	}
}
