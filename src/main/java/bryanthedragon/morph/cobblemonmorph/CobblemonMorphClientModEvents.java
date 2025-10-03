package bryanthedragon.morph.cobblemonmorph;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CobblemonMorph.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CobblemonMorphClientModEvents 
{
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    /**
     * Some client setup code
     */
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        // Some client setup code
        CobblemonMorph.LOGGER.info("HELLO FROM CLIENT SETUP");
        CobblemonMorph.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
