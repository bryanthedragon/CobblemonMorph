package bryanthedragon.morph.cobblemonmorph.helper.registries;

import bryanthedragon.morph.cobblemonmorph.helper.CobblemonMorphHelper;
import bryanthedragon.morph.cobblemonmorph.helper.registries.tabs.CobblemonMorphTabRegistry;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CobblemonMorphRegistry extends CobblemonMorphHelper
{
    private static CobblemonMorphTabRegistry cobblemonmorphtabregistry;
    private static CobblemonMorphTabRegistry getCobblemonMorphTabRegistry()
    {
        return cobblemonmorphtabregistry;
    }
    public CobblemonMorphRegistry(FMLJavaModLoadingContext context) 
    {
        super(context);
        getCobblemonMorphTabRegistry();
    }
}
