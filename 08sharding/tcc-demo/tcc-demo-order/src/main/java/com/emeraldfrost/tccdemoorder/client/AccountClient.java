package com.emeraldfrost.tccdemoorder.client;

import com.emeraldfrost.tccdemocommon.model.AccountDTO;
import org.dromara.hmily.annotation.Hmily;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "tcc-demo-account", path = "/tcc-demo-account/account")
public interface AccountClient {

	@Hmily
	@PostMapping("/pay")
	Boolean pay(@RequestBody AccountDTO dto);
}
