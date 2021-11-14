package com.emeraldfrost.tccdemocommon.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDTO {

	private Long userId;

	private Integer amount;
}
