package io.kimmking.rpcfx.server;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rpcfx.provider")
@Getter
@Setter
public class RpcfxProviderProperties {

	private String serviceScanPackage;
}
