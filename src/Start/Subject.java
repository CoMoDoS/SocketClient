package Start;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observ> observers = new ArrayList<Observ>();

    public void attach(Observ observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (Observ observer : observers) {
            observer.update();
        }
    }

}
