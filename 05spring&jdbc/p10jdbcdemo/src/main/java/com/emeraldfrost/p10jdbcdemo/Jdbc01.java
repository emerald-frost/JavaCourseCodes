package com.emeraldfrost.p10jdbcdemo;

import java.sql.*;

public class Jdbc01 {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://192.168.204.132:3306/java_course?user=root&password=root";
        try (final Connection connection = DriverManager.getConnection(url)) {
            createTable(connection);

            doInsert(connection);

            final int id = doSelect(connection);

            doUpdate(connection, id);

            doDelete(connection, id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            //create table
            stmt.execute("CREATE TABLE IF NOT EXISTS `t_demo` (\n" +
                    "  `id` int(10) NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(200) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;\n");
        }
    }

    private static void doInsert(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("insert into t_demo (name) values ('jack')");
        }
    }

    private static int doSelect(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            final ResultSet resultSet = stmt.executeQuery("select id from t_demo where name='jack'");
            resultSet.next();
            int id = resultSet.getInt(1);
            System.out.println("id: " + id);
            return id;
        }
    }

    private static void doUpdate(Connection connection, int id) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            final int updatedRows = stmt.executeUpdate("update t_demo set name='jackson' where id = " + id);
            System.out.println("updated rows: " + updatedRows);
        }
    }

    private static void doDelete(Connection connection, int id) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            final int deletedRows = stmt.executeUpdate("delete from t_demo where id = " + id);
            System.out.println("deleted rows: " + deletedRows);
        }
    }
}
