package bryanthedragon.morph.cobblemonmorph.helper;

import bryanthedragon.morph.cobblemonmorph.CobblemonMorph;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = CobblemonMorph.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CobblemonMorphClientModEventsHelper extends CobblemonMorph
{
    public CobblemonMorphClientModEventsHelper(FMLJavaModLoadingContext context) 
    {
        super(context);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    
    /**
     * Called when the client is setting up, after the initial registration of all mods.
     * This is the point where you can safely do things that require the client to be setup.
     */
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        // Some client setup code
        CobblemonMorph.LOGGER.info("HELLO FROM CLIENT SETUP");
        CobblemonMorph.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
