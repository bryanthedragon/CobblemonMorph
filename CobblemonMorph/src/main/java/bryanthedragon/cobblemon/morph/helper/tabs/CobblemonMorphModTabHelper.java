package bryanthedragon.cobblemon.morph.helper.tabs;

import bryanthedragon.cobblemon.morph.CobblemonMorph;
import bryanthedragon.cobblemon.morph.helper.registries.tabs.CobblemonMorphTabRegistry;

import net.minecraftforge.eventbus.api.IEventBus;

public class CobblemonMorphModTabHelper extends CobblemonMorph
{
    public CobblemonMorphModTabHelper()    
    {
        
    }

    // Register Creative Mode Tabs

    public void register(IEventBus eventBus)
    {
        CobblemonMorphTabRegistry.COBBLEMONMORPH_TABS.register(eventBus);
    }
}