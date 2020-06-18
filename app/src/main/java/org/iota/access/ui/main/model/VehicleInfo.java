/*
 *  This file is part of the IOTA Access distribution
 *  (https://github.com/iotaledger/access)
 *
 *  Copyright (c) 2020 IOTA Stiftung.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.iota.access.ui.main.model;

import java.io.Serializable;

import io.reactivex.annotations.Nullable;

public class VehicleInfo implements Serializable {
    private boolean mSelected;
    private final String mName;
    private final String mId;

    public VehicleInfo(String id, String name) {
        this(id, name, false);
    }

    public VehicleInfo(String id, String name, boolean selected) {
        mId = id;
        mName = name;
        mSelected = selected;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof VehicleInfo) {
            return mId.equals(((VehicleInfo) obj).mId);
        } else {
            return false;
        }
    }

    @Override
    public VehicleInfo clone() {
        VehicleInfo vehicleInfo;
        try {
            vehicleInfo = (VehicleInfo) super.clone();
        } catch (Exception ignored) {
            vehicleInfo = new VehicleInfo(mId, mName, mSelected);
        }
        return vehicleInfo;
    }
}