package kale.db;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Kale
 * @date 2016/1/8
 */
public abstract class ValueObserver<T> implements Observer {

    public abstract void onValueUpdate(ObservableValue<T> observable, T value);
    
    @Override
    public void update(Observable observable, Object data) {
        onValueUpdate((ObservableValue<T>) observable, (T) data);
    }
}
