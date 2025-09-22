package bryanthedragon.morph.cobblemonmorph;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import org.slf4j.Logger;

import java.net.URL;
import java.util.Optional;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CobblemonMorph.MODID)
public class CobblemonMorph
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "cobblemonmorph";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")
    public CobblemonMorph(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) 
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) 
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) 
    {
        
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
    public static class DependencyHelper
    {
        public static boolean isModLoaded(String modid)
        {
            return net.minecraftforge.fml.ModList.get().isLoaded(modid);
        }
        
        public static String getModVersion(String modid)
        {
            return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getVersion().toString()).orElse("N/A");
        }

        public static String getModDisplayName(String modid)
        {
            return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getDisplayName()).orElse("N/A");
        }
        public static String getModDescription(String modid)
        {
            return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getDescription()).orElse("N/A");
        }

//         public static boolean isModVersionAtLeast(String modid, String version)
//         {
//             return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getVersion().compareTo(VersionChecker.parseVersionReference(version)) >= 0).orElse(false);
//         }
//
//         public static boolean isModVersionAtMost(String modid, String version)
//         {
//             return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getVersion().compareTo(VersionChecker.parseVersionReference(version)) <= 0).orElse(false);
//         }

//         public static boolean isModVersionExact(String modid, String version)
//         {
//             return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getVersion().compareTo(VersionChecker.parseVersionReference(version)) == 0).orElse(false);
//         }
//
//         public static String getModAuthors(String modid)
//         {
//             return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> String.join(", ", modContainer.getModInfo().getAuthors())).orElse("N/A");
//         }

        public static Optional<URL> getModURL(String modid)
        {
            return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getModURL()).orElseThrow();
        }
    }
    public static class LoggerHelper
    {
        public static void logInfo(String message)
        {
            LOGGER.info(message);
        }

        public static void logWarn(String message)
        {
            LOGGER.warn(message);
        }

        public static void logError(String message)
        {
            LOGGER.error(message);
        }
    }
    public static class ItemHelper
    {
        @SuppressWarnings("removal")
        public static Item getItemByName(String itemName)
        {
            return ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
        }

        @SuppressWarnings("removal")
        public static boolean isItemRegistered(String itemName)
        {
            return ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
        }
    }
}
