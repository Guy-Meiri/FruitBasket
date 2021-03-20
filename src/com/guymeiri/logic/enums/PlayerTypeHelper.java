package com.guymeiri.logic.enums;

import java.util.HashMap;
import java.util.Map;

public class PlayerTypeHelper {

    private static final Map<Integer, PlayerType> intToTypeMap = new HashMap<Integer, PlayerType>();
    static {
        for (PlayerType type : PlayerType.values()) {
            intToTypeMap.put(type.ordinal(), type);
        }
    }

    public static PlayerType fromInt(int i) {
        PlayerType type = intToTypeMap.get(Integer.valueOf(i));
        if (type == null)
            return null;
        return type;
    }
}
