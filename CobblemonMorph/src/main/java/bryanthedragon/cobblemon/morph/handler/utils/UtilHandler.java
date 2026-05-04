package bryanthedragon.cobblemon.morph.handler.utils;

import bryanthedragon.cobblemon.morph.handler.Handlers;

public class UtilHandler extends Handlers {
    private final HandlerType type = HandlerType.UTIL;

    protected enum UtilType {
        POKEMON
    }
    
    public HandlerType getType() {
        return type;
    }
    
    protected void printType() {
        System.err.println(this.type);
    }
}
