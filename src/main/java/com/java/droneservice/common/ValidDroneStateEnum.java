package com.java.droneservice.common;

import lombok.Getter;

@Getter
public enum ValidDroneStateEnum {
    IDLE("IDLE"),

    LOADING("LOADING"),

    LOADED("LOADED");

    private final String value;

    ValidDroneStateEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public static ValidDroneStateEnum fromValue(String value) {
        for (ValidDroneStateEnum b : ValidDroneStateEnum.values()) {
            if (b.getValue().equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
