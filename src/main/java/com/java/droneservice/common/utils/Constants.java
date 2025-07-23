package com.java.droneservice.common.utils;

import com.java.droneservice.model.DroneModelEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Constants {

    public static Map<DroneModelEnum, Integer> modelMap;
    static {
        modelMap = new HashMap<>();
        modelMap.put(DroneModelEnum.LIGHT_WEIGHT, 50);
        modelMap.put(DroneModelEnum.MIDDLE_WEIGHT, 100);
        modelMap.put(DroneModelEnum.HEAVY_WEIGHT, 500);
        modelMap.put(DroneModelEnum.CRUISER_WEIGHT, 1000);
    }

}
