package db;

import models.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBSession {

    private Connection connection;

    public DBSession(Connection connection) {
        this.connection = connection;
    }

    public void insert(String id, String pseudo) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.executeUpdate("insert into Session values('" + id + "', '" + pseudo + "')");
        statement.close();
    }

    public void getSession(String pseudo) throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet result = statement.executeQuery("select * from Session, Message where pseudo = '" + pseudo +"'");


        statement.close();
    }
}
