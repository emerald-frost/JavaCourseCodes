package com.emeraldfrost.shardingdemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShardingdemoApplicationTests {

	@Autowired DataSource dataSource;

	@Test
	void insert() throws SQLException {
		final String sql = "insert into t_order " +
				"(order_id, order_no, user_id, product_id, product_count, total_price, status)" +
				"values(?,?,?,?,?,?,?)";
		try (final Connection connection = dataSource.getConnection();
			 final PreparedStatement pstmt = connection.prepareStatement(sql)) {
			//雪花算法生成的id，mod的计算结果很诡异的只有0和1，这里手动模拟一下订单id
			final AtomicLong orderIdSimulator = new AtomicLong();
			for (int userId = 1; userId <= 10; userId++) {
				for (int orders = 1; orders <= 32; orders++) {
					int col = 1;
					pstmt.setLong(col++, orderIdSimulator.incrementAndGet());
					pstmt.setString(col++, String.valueOf(System.currentTimeMillis()) + orders);
					pstmt.setInt(col++, userId);
					pstmt.setInt(col++, 1);
					pstmt.setInt(col++, 1);
					pstmt.setInt(col++, 1);
					pstmt.setInt(col++, 0);
					pstmt.addBatch();
				}
			}
			final int[] rs = pstmt.executeBatch();
			System.out.println("inserted: " + rs.length);
		}
	}

	@Test
	void update() throws SQLException {
		try (final Connection connection = dataSource.getConnection();
			 //会修改demo_ds_1中的t_order_1表的数据
			 final PreparedStatement pstmt = connection.prepareStatement("update t_order set status=1 where user_id=1 and order_id%16=1");
		) {
			final int rs = pstmt.executeUpdate();
			System.out.println("updated: " + rs);
		}
	}

	@Test
	void select() throws SQLException {
		try (final Connection connection = dataSource.getConnection()) {
			final String sql = "select count(*) from t_order where status=?";
			try (final PreparedStatement pstmt = connection.prepareStatement(sql)) {
				pstmt.setInt(1, 0);
				final ResultSet rs0 = pstmt.executeQuery();
				rs0.next();
				Assertions.assertEquals(320 - 2, rs0.getInt(1));
			}

			try (final PreparedStatement pstmt = connection.prepareStatement("select count(*) from t_order where user_id=1 and order_id%16=1 and status=?")) {
				pstmt.setInt(1, 1);
				final ResultSet rs0 = pstmt.executeQuery();
				rs0.next();
				Assertions.assertEquals(2, rs0.getInt(1));
			}
		}
	}

	@Test
	void delete() throws SQLException {
		final String sql = "delete from t_order";
		try (final Connection connection = dataSource.getConnection();
			 final Statement stmt = connection.createStatement()
		) {
			final int rs = stmt.executeUpdate(sql);
			System.out.println(rs);
		}
	}
}
