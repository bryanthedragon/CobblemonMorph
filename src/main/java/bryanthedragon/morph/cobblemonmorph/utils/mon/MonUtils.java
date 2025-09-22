package bryanthedragon.morph.cobblemonmorph.utils.mon;

import java.util.ArrayList;
import java.util.List;

import bryanthedragon.morph.cobblemonmorph.utils.mon.gen.eight.Gen8MonUtils;
import bryanthedragon.morph.cobblemonmorph.utils.mon.gen.five.Gen5MonUtils;
import bryanthedragon.morph.cobblemonmorph.utils.mon.gen.four.Gen4MonUtils;
import bryanthedragon.morph.cobblemonmorph.utils.mon.gen.nine.Gen9MonUtils;
import bryanthedragon.morph.cobblemonmorph.utils.mon.gen.one.Gen1MonUtils;
import bryanthedragon.morph.cobblemonmorph.utils.mon.gen.seven.Gen7MonUtils;
import bryanthedragon.morph.cobblemonmorph.utils.mon.gen.six.Gen6MonUtils;
import bryanthedragon.morph.cobblemonmorph.utils.mon.gen.three.Gen3MonUtils;
import bryanthedragon.morph.cobblemonmorph.utils.mon.gen.two.Gen2MonUtils;

public class MonUtils 
{
    private static List<String> validMonNames = new ArrayList<>();
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

    public static boolean isValidMonName(String name) 
    {
        return name != null && !name.trim().isEmpty() && validMonNames.contains(name);
    }
}
