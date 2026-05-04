package bryanthedragon.cobblemon.morph.helper.mod;

import bryanthedragon.cobblemon.morph.CobblemonMorph;

import org.slf4j.Logger;

public class CobblemonMorphModHelper extends CobblemonMorph
{
    public CobblemonMorphModHelper()    
    {
        
    }
    public static String getModID()
    {
        return CobblemonMorph.MODID;
    }
    public static Logger getLogger()
    {
        return CobblemonMorph.LOGGER;
    }
}
