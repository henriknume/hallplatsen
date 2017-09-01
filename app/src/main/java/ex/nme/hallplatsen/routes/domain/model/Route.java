package ex.nme.hallplatsen.routes.domain.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;

import java.util.UUID;

/**
 * Immutable model class for a Route.
 */
public final class Route {

    @NonNull
    private final String mId;
    @Nullable
    private final String mStationNameFrom;
    @Nullable
    private final String mStationIdFrom;
    @Nullable
    private final String mStationNameTo;
    @Nullable
    private final String mStationIdTo;

    /**
     * Use this constructor to create a new Journey.
     *
     * @param stationNameFrom   name of origin station
     * @param stationIdFrom     id of origin station
     * @param stationNameTo     name of destination station
     * @param stationIdTo       id of destination station
     */
    public Route(@Nullable String stationNameFrom, @Nullable String stationIdFrom,
                 @Nullable String stationNameTo, @Nullable String stationIdTo) {
        this(stationNameFrom, stationIdFrom,
                stationNameTo, stationIdTo, UUID.randomUUID().toString());
    }

    /**
     * Use this constructor to create an active Journey if the Journey already has an id (copy of another
     * Journey).
     *
     * @param stationNameFrom   name of origin station
     * @param stationIdFrom     id of origin station
     * @param stationNameTo     name of destination station
     * @param stationIdTo       id of destination station
     * @param id                id of the Journey
     */

    public Route(@Nullable String stationNameFrom, @Nullable String stationIdFrom,
                 @Nullable String stationNameTo, @Nullable String stationIdTo,
                 @NonNull String id) {
        mId = id;
        mStationNameFrom = stationNameFrom;
        mStationIdFrom = stationIdFrom;
        mStationNameTo = stationNameTo;
        mStationIdTo = stationIdTo;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getStationNameFrom() {
        return mStationNameFrom;
    }

    @Nullable
    public String getStationIdFrom() {
        return mStationIdFrom;
    }

    @Nullable
    public String getStationNameTo() {
        return mStationNameTo;
    }

    @Nullable
    public String getStationIdTo() {
        return mStationIdTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equal(mId, route.getId()) &&
               Objects.equal(mStationIdFrom, route.getStationIdFrom()) &&
               Objects.equal(mStationIdTo, route.getStationIdTo());
    }

    @Override
    public String toString() {
        return "Route {id: "+ mId +", fn:"+ mStationNameFrom +", tn:"+ mStationNameTo +"}";
    }
}
