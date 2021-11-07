package com.emeraldfrost.batchinsert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class SingleThreadInsert {

	public static void main(String[] args) {
		final DataSource dataSource = getDataSource();
		final int total = 100_0000;
		final int batchSize = 1_0000;

		long begin = System.currentTimeMillis();
		try (Connection connection = dataSource.getConnection()) {
			//每次插入1万条
			for (int i = 0; i < total; i += batchSize) {
				//p+1作为id的起始区间,每个线程插入id包含[p+1,p+1+batchSize)，最后一个id应该是1000001
				final int p = i + 1;
				//关闭自动提交
				connection.setAutoCommit(false);
				doInsert(connection, p, batchSize);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();

		System.out.println("millis: " + (end - begin));
	}

	private static void doInsert(Connection connection, int partitionBeginIndex, int batchSize) {
		final String sql = "insert into `order` " +
				"(id,order_no,member_id,product_id,product_count,total_price,status) " +
				"values (?,?,?,?,?,?,?)";
		try (final PreparedStatement pstmt = connection.prepareStatement(sql)) {
			for (int j = 0; j < batchSize; j++) {
				int column = 1;
				final long id = partitionBeginIndex + j;
				pstmt.setLong(column++, id);
				pstmt.setString(column++, "order_no_" + id);
				pstmt.setInt(column++, j);
				pstmt.setInt(column++, 1);
				pstmt.setInt(column++, 1);
				pstmt.setInt(column++, 10000);
				pstmt.setInt(column, 0);
				pstmt.addBatch();
			}
			final int[] rs = pstmt.executeBatch();
			//提交
			connection.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static DataSource getDataSource() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/mall?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=PRC&rewriteBatchedStatements=true");
		config.setUsername("root");
		config.setPassword("");
		config.setMaximumPoolSize(Runtime.getRuntime().availableProcessors() * 2 + 1);
		config.setConnectionTimeout(2_000L);
		config.setConnectionTestQuery("SELECT 1");
		return new HikariDataSource(config);
	}
}
