package fxQuick.iViews;

import fxQuick.FXPromise;
import fxQuick.ServiceManager;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;

/**
 * @author Dominik Reset
 * <p>
 * Injects all FXServices on runtime when object is Initialized
 */
public class FXBase {

    public FXBase() {
        loadInjections();
    }

    private void loadInjections() {
        if (ServiceManager.DEMANDED_FIELDS.containsKey(this.getClass())) {
            for (Field f : ServiceManager.DEMANDED_FIELDS.get(this.getClass())) {
                try {
                    f.setAccessible(true);
                    f.set(this, ServiceManager.CLASSES.get(f.getType()));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Runs a callable function asycronously.
     * Cancels if timeout time is reached
     *
     * @param <T>     :object type
     * @param timeout : timeout in milliseconds
     * @param fun     :the callable function
     * @return returns a FXAsync object. call await() to work with called object when ready
     */
    public <T> FXPromise<T> async(long timeout, Callable<T> fun) {
        return new FXPromise<T>().async(timeout, fun);
    }

    /**
     * Runs a callable function asycronously.
     *
     * @param <T> :object type
     * @param fun :the callable function
     * @return returns a FXAsync object. call await() to work with called object when ready
     */
    public <T> FXPromise<T> async(Callable<T> fun) {
        return new FXPromise<T>().async(fun);
    }
}
