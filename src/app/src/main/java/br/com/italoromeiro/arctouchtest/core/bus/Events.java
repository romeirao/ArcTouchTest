package br.com.italoromeiro.arctouchtest.core.bus;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by italo on 11/08/16.
 */
public class Events {
    public static class StopsViewReadyEvent {
    }

    public static class DeparturesViewReadyEvent {
    }

    public static final class GoogleAPIClientAction {
        public enum ActionType {
            START, STOP
        }

        private ActionType mActionType;

        public GoogleAPIClientAction(ActionType actionType) {
            mActionType = actionType;
        }

        public ActionType getActionType() {
            return mActionType;
        }
    }

    public static final class MapAction {
        public enum ActionType {
            /**
             * That state represents a simgle click on map
             */
            CLICK,

            /**
             * That state represents a long click on map
             */
            LONG_CLICK,

            /**
             * That state represents a click on my location button provided by google maps
             */
            MY_LOCATION_CLICK,

            /**
             * THat state is called every time the map moves
             */
            CAMERA_MOVES
        }

        /**
         * That attribute saves the location that represents the point on map clicked by the user
         */
        private LatLng mLatLng;

        private ActionType mActionType;

        public MapAction(ActionType actionType) {
            mActionType = actionType;
            mLatLng = null;
        }

        public MapAction(ActionType actionType, LatLng latLng) {
            mActionType = actionType;
            mLatLng = latLng;
        }

        public ActionType getActionType() {
            return mActionType;
        }

        public LatLng getLatLng() {
            return mLatLng;
        }
    }

    public static final class LocationAction {
        private Location mLocation;

        public LocationAction(Location location) {
            mLocation = location;
        }

        public Location getLocation() {
            return mLocation;
        }
    }
}
