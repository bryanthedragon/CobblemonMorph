package bryanthedragon.cobblemon.morph.utils.mon;

import java.util.ArrayList;
import java.util.List;

import bryanthedragon.cobblemon.morph.utils.mon.gen.eight.Gen8MonUtils;
import bryanthedragon.cobblemon.morph.utils.mon.gen.five.Gen5MonUtils;
import bryanthedragon.cobblemon.morph.utils.mon.gen.four.Gen4MonUtils;
import bryanthedragon.cobblemon.morph.utils.mon.gen.nine.Gen9MonUtils;
import bryanthedragon.cobblemon.morph.utils.mon.gen.one.Gen1MonUtils;
import bryanthedragon.cobblemon.morph.utils.mon.gen.seven.Gen7MonUtils;
import bryanthedragon.cobblemon.morph.utils.mon.gen.six.Gen6MonUtils;
import bryanthedragon.cobblemon.morph.utils.mon.gen.three.Gen3MonUtils;
import bryanthedragon.cobblemon.morph.utils.mon.gen.two.Gen2MonUtils;

public class MonUtils 
{
    public static List<String> validMonNames = new ArrayList<>();
    public MonUtils() 
    {
        Gen1MonUtils.getGen1MonUtils();
        Gen2MonUtils.getGen2MonUtils();
        Gen3MonUtils.getGen3MonUtils();
        Gen4MonUtils.getGen4MonUtils();
        Gen5MonUtils.getGen5MonUtils();
        Gen6MonUtils.getGen6MonUtils();
        Gen7MonUtils.getGen7MonUtils();
        Gen8MonUtils.getGen8MonUtils();
        Gen9MonUtils.getGen9MonUtils();
    }

    /**
     * Checks if the given name is valid for a mon.
     * @param name The name to check.
     * @return true if the name is valid, false otherwise.
     */
    public static boolean isValidMonName(String name) 
    {
        if (name == null) 
        {
            return false;
        }
        else
        {
            return validMonNames.contains(name);
        }
    }
    /**
     * * Returns a list of valid names of the pokemon of Gen 8.
     */
    public static List<String> getValidMonNames() 
    { 
        return validMonNames; 
    }
}
