package mocks;

import db.IDao;
import models.MessageChat;
import models.RemoteUser;
import session.ISession;

import java.util.List;

public class MockDao implements IDao {
    @Override
    public void insertMessage(MessageChat message) {

    }

    @Override
    public void insertSession(ISession session) {

    }

    @Override
    public String getSession(RemoteUser user) {
        return null;
    }

    @Override
    public List<MessageChat> getMessages(String idSession) {
        return null;
    }

    @Override
    public void changePseudo(String oldPseudo, String newPseudo) {

    }
}
