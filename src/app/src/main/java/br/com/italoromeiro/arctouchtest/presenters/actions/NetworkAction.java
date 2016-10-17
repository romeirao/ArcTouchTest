package br.com.italoromeiro.arctouchtest.presenters.actions;

/**
 * Created by italo on 08/08/16.
 */
public abstract class NetworkAction {
    public abstract void doAction() throws Throwable;

    public void doBeforeAction() {
    }

    public void doAfterAction() {
    }

    public void doError(Throwable throwable) {
        throwable.printStackTrace();
    }
}
