package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    private static Connection connection;

    public DB() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:chat.db");
        Statement statement = connection.createStatement();
        statement.executeUpdate("IF (NOT EXIST (SELECT * FROM sqlite_schema WHERE name = 'Session')) " +
                "BEGIN create table session (id string, pseudo string)");

        /*statement.executeUpdate("create table session (id string, pseudo string)");
        statement.executeUpdate("create table message (id_session string, data string, date Date)");*/
    }


}
