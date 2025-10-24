package bryanthedragon.morph.cobblemonmorph.helper;

import bryanthedragon.morph.cobblemonmorph.CobblemonMorph;

import org.slf4j.Logger;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CobblemonMorphModHelper extends CobblemonMorph
{
    public CobblemonMorphModHelper(FMLJavaModLoadingContext context) 
    {
        super(context);
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
