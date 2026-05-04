package bryanthedragon.cobblemon.morph.helper.logger;

import bryanthedragon.cobblemon.morph.CobblemonMorph;

public class CobblemonMorphLoggerHelper extends CobblemonMorph
{
    public CobblemonMorphLoggerHelper()    
    {
        
    }

    public static void logInfo(String message)
    {
        CobblemonMorph.LOGGER.info(message);
    }

    public static void logWarn(String message)
    {
        CobblemonMorph.LOGGER.warn(message);
    }

    public static void logError(String message)
    {
        CobblemonMorph.LOGGER.error(message);
    }
}
