package bryanthedragon.morph.cobblemonmorph;

public class CobblemonMorphLoggerHelper 
{
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
