package com.emeraldfrost.p10jdbcdemo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UseDataSource {

    private static DataSource dataSource = null;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://192.168.204.132:3306/java_course");
        config.setUsername("root");
        config.setPassword("root");
        dataSource = new HikariDataSource(config);
    }

    public static void main(String[] args) {
        try (
                final Connection connection = dataSource.getConnection();
                final Statement stmt = connection.createStatement()
        ) {
            final ResultSet rs = stmt.executeQuery("select count(*) from t_demo");
            rs.next();
            System.out.println("count: " + rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
