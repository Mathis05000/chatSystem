package db;

import models.MessageChat;
import models.RemoteUser;
import session.ISession;
import session.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface IDao {
    void insertMessage(MessageChat message);
    void insertSession(ISession session);
    String getSession(RemoteUser user);
    List<MessageChat> getMessages(String idSession);
}
