package bryanthedragon.morph.cobblemonmorph;

import bryanthedragon.morph.cobblemonmorph.helper.dependency.CobblemonMorphDependencyHelper;

import com.mojang.logging.LogUtils;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;

@Mod(CobblemonMorph.MODID)
public class CobblemonMorph
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "cobblemonmorph";

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")
    public CobblemonMorph(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        CobblemonMorphDependencyHelper.dependencyCheck();
    }

    /**
     * Called when the mod is being setup. This is a good time to initialize any config values that need to be accessed
     * during the course of the mod's operation.
     * @param event The event that triggered this method call.
     */
    private void commonSetup(final FMLCommonSetupEvent event) 
    {

    }

    /**
     * Called when the mod is being setup. This is a good time to initialize any config values that need to be accessed
     * during the course of the mod's operation.
     * @param event The event that triggered this method call.
     */
    private void addCreative(BuildCreativeModeTabContentsEvent event) 
    {

    }

    /**
     * Called when the server is starting.
     * This is a good time to initialize any config values that need to be accessed
     * during the course of the server's operation.
     * @param event The event that triggered this method call.
     */
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) 
    {
        
    }
}