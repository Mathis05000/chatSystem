package db;


import models.MessageChat;
import models.RemoteUser;
import session.ISession;
import session.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dao implements IDao {

    private static final Dao INSTANCE = new Dao();
    private Connection connection;
    private Dao() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:chat.db");
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM sqlite_schema where name = 'session';");
            if (!rs.next()) {
                statement.executeUpdate("create table session (id string, pseudo string)");
            }

            rs = statement.executeQuery("SELECT * FROM sqlite_schema WHERE name = 'message';");
            if (!rs.next()) {
                statement.executeUpdate("create table message (id_session string, data string, date Date)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Point d'accès pour l'instance unique du singleton */
    public static Dao getInstance() {
        return INSTANCE;
    }

    public void insertMessage(MessageChat message){
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate("insert into Message values('" + message.getIdSession() + "', '" + message.getData() + "', '"+ new java.sql.Date(message.getDate().getTime()) +"')");
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertSession(ISession session) {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate("insert into Session values('" + session.getId() + "', '" + session.getUser().getPseudo() + "')");
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSession(RemoteUser user) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("select id from Session where pseudo = '" + user.getPseudo() + "'");
            statement.close();
            if (res.next()) {
                return res.getString("id");

            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<MessageChat> getMessages(String idSession) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("select * from Message where id_session = '" + idSession + "'");
            statement.close();

            List<MessageChat> list = new ArrayList<MessageChat>();
            while (res.next()) {
                list.add(new MessageChat(res.getString("data"), res.getString("id_session"), new Date(res.getDate("date").getTime())));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
