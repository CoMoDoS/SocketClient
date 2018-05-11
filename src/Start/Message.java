package Start;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = -5399605122490343339L;

    private String A;
    private String B;


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


    @Override
    public String toString() {
        return "Message{" +
                "A=" + A +
                ", B='" + B + '\'' +
                '}';
    }
}