package bryanthedragon.cobblemon.morph.utils;

import java.util.Arrays;

import bryanthedragon.cobblemon.morph.handler.utils.UtilHandler;

public class Utils extends UtilHandler {
    private UtilType type;
    
    public String[] printUtils() {
        return Arrays.stream(UtilType.values()).map(Enum::name).toArray(String[]::new);
    }

    public UtilType getUtilType() {
        return type;
    }
}