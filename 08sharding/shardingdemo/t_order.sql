CREATE TABLE `t_order`  (
  `order_id` bigint(20) UNSIGNED NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT '0001-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '订单号',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `product_id` int(11) NOT NULL COMMENT '产品id',
  `product_count` int(11) NOT NULL COMMENT '产品数量',
  `total_price` int(11) NOT NULL COMMENT '总价格，单位为分',
  `status` int(11) NOT NULL COMMENT '0:未付款，1:已付款，2:已发货，3:已签收，-1:已取消，-2:已删除',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin;