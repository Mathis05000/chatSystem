package db;


import models.MessageChat;
import models.RemoteUser;
import session.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DB {

    private Connection connection;

    private DB() throws SQLException {
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

    private static DB INSTANCE;

    static {
        try {
            INSTANCE = new DB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Point d'accès pour l'instance unique du singleton */
    public static DB getInstance()
    {
        return INSTANCE;
    }

    public void insertMessage(MessageChat message) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.executeUpdate("insert into Message values('" + message.getIdSession() + "', '" + message.getData() + "', '"+ new java.sql.Date(message.getDate().getTime()) +"')");
        statement.close();
    }

    public void insertSession(Session session) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.executeUpdate("insert into Session values('" + session.getId() + "', '" + session.getUser().getPseudo() + "')");
        statement.close();
    }

    public String getSession(RemoteUser user) throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet res = statement.executeQuery("select id from Session where pseudo = '" + user.getPseudo() + "'");
        statement.close();
        if (res.next()) {
            return res.getString("id");

        }
        else {
            return null;
        }

    }

    public List<MessageChat> getMessages(String idSession) throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet res = statement.executeQuery("select * from Message where id_session = '" + idSession + "'");
        statement.close();

        List<MessageChat> list = new ArrayList<MessageChat>();
        while (res.next()) {
            list.add(new MessageChat(res.getString("data"), res.getString("id_session"), new Date(res.getDate("date").getTime())));
        }
        return list;
    }

}
