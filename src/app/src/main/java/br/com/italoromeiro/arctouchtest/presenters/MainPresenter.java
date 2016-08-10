package br.com.italoromeiro.arctouchtest.presenters;

import org.androidannotations.annotations.EBean;

import br.com.italoromeiro.arctouchtest.presenters.actions.NetworkAction;

/**
 * Created by italo on 08/08/16.
 */
@EBean
public class MainPresenter extends BasePresenter {
    public void findRoutesByStopName(final String stopName) {
        executeAction(new NetworkAction() {
            @Override
            public void doAction() throws Throwable {
                api.findRoutesByStopName(stopName);
            }
        });
    }
}
