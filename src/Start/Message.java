package Start;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {
    private static final long serialVersionUID = -5399605122490343339L;

    private String A;
    private String B;

    private List<Message> messageList = new ArrayList<Message>();

    public Message(String firstNumber, String string ){
        this.A = firstNumber;
        this.B = string;
    }

    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }

    public void addList(Message m)
    {
        System.out.println(m.getA() + m.getB());
        this.messageList.add(m);
    }

    public List<Message> getMessageList()
    {
        return this.messageList;
    }

    @Override
    public String toString() {
        return "Message{" +
                "A=" + A +
                ", B='" + B + '\'' +
                '}';
    }
}