package bryanthedragon.cobblemon.morph.utils.mon;

import bryanthedragon.cobblemon.morph.utils.Utils;

public class MonUtils extends Utils
{
    private final UtilType type = UtilType.POKEMON;

    public UtilType getUtilType() {
        return type;
    }

    protected void printType() {
        System.err.println(this.type);
    }
}
