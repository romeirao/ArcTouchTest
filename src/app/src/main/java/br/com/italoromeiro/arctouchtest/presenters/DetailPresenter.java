package br.com.italoromeiro.arctouchtest.presenters;

import org.androidannotations.annotations.EBean;

import br.com.italoromeiro.arctouchtest.presenters.actions.NetworkAction;

/**
 * Created by italo on 10/08/16.
 */
@EBean
public class DetailPresenter extends BasePresenter{
    public void findStopsByRouteId(final Integer routeId) {
        executeAction(new NetworkAction() {
            @Override
            public void doAction() throws Throwable {
                mApi.findStopsByRouteId(routeId);
            }
        });
    }

    public void findDeparturesByRouteId(final Integer routeId) {
        executeAction(new NetworkAction() {
            @Override
            public void doAction() throws Throwable {
                mApi.findDeparturesByRouteId(routeId);
            }
        });
    }
}
