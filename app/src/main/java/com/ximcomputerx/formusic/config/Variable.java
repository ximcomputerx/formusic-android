package com.ximcomputerx.formusic.config;

import com.baidu.location.BDLocation;

/**
 * @CREATED HACKER
 */
public class Variable {

    public static BDLocation location = null;

    public static BDLocation getLocation() {
        return location;
    }

    public static void setLocation(BDLocation location) {
        Variable.location = location;
    }
}
