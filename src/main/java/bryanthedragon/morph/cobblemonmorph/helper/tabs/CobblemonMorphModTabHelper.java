package bryanthedragon.morph.cobblemonmorph.helper.tabs;

import bryanthedragon.morph.cobblemonmorph.CobblemonMorph;
import bryanthedragon.morph.cobblemonmorph.helper.registries.tabs.CobblemonMorphTabRegistry;

import net.minecraftforge.eventbus.api. IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CobblemonMorphModTabHelper extends CobblemonMorph
{
    public CobblemonMorphModTabHelper(FMLJavaModLoadingContext context) 
    {
        super(context);
    }

    // Register Creative Mode Tabs

    public void register(IEventBus eventBus)
    {
        CobblemonMorphTabRegistry.COBBLEMONMORPH_TABS.register(eventBus);
    }
}