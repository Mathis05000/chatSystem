package models;

public class MessagePseudo extends Message {

    private String newPseudo;

    public MessagePseudo(String oldPseudo, String newPseudo) {
        super(oldPseudo);
        this.newPseudo = newPseudo;
    }

    public String getNewPseudo() {
        return newPseudo;
    }
}
