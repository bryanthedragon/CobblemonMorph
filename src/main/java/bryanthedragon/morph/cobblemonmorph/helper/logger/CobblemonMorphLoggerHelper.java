package bryanthedragon.morph.cobblemonmorph.helper.logger;

import bryanthedragon.morph.cobblemonmorph.CobblemonMorph;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CobblemonMorphLoggerHelper extends CobblemonMorph
{
    public CobblemonMorphLoggerHelper(FMLJavaModLoadingContext context) 
    {
        super(context);
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
