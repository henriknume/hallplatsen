/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ex.nme.hallplatsen.tasks.domain.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.UUID;

/**
 * Immutable model class for a Task.
 */
public final class Task {

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
    public Task(@Nullable String stationNameFrom, @Nullable String stationIdFrom,
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

    public Task(@Nullable String stationNameFrom, @Nullable String stationIdFrom,
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
        Task task = (Task) o;
        return Objects.equal(mId, task.getId()) &&
               Objects.equal(mStationIdFrom, task.getStationIdFrom()) &&
               Objects.equal(mStationIdTo, task.getStationIdTo());
    }

    @Override
    public String toString() {
        return "Task {id: "+ mId +", fn:"+ mStationNameFrom +", tn:"+ mStationNameTo +"}";
    }
}
