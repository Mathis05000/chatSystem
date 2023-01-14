package db;


import java.sql.*;

public class DB {

    private static Connection connection;

    public DB() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:chat.db");
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM sqlite_schema WHERE name = 'Session';");
        if (!rs.next()) {
            statement.executeUpdate("create table session (id string, pseudo string)");
        }

        rs = statement.executeQuery("SELECT * FROM sqlite_schema WHERE name = 'Message';");
        if (!rs.next()) {
            statement.executeUpdate("create table message (id_session string, data string, date Date)");
        }
    }


}
