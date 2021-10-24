package com.emeraldfrost.p10jdbcdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Jdbc02 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://192.168.204.132:3306/java_course?user=root&password=root";
        try (final Connection connection = DriverManager.getConnection(url)) {
            doBatchInsert(connection);
        }
    }

    private static void doBatchInsert(Connection connection) throws SQLException {
        String sql = "insert into t_demo (name) values (?)";

        //关闭自动提交
        connection.setAutoCommit(false);
        // 通过同一个pstmt进行批量插入
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < 10; i++) {
                pstmt.setString(1, "student" + i);
                pstmt.addBatch();
            }
            final int[] rs = pstmt.executeBatch();
            //提交事务
            connection.commit();

            for (int r : rs) {
                // batch中每条sql插入的数量
                System.out.println(r + " inserted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                //回滚事务
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
