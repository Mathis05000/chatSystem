package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DBMessage {

    private Connection connection;

    public DBMessage(Connection connection) {
        this.connection = connection;
    }

    public void insert(String id_session,String data, Date date) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.executeUpdate("insert into Message values('" + id_session + "', '" + date + "', '"+ data +"')");
        statement.close();
    }

    public void getMessages(String id_session) throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet result = statement.executeQuery("select * from Message where id_session = '" + id_session +"'");
        // TODO
        statement.close();
    }
}
