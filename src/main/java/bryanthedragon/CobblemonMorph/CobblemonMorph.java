package bryanthedragon.CobblemonMorph;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CobblemonMorph.MODID)
public class CobblemonMorph
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "CobblemonMorph";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "CobblemonMorph" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "CobblemonMorph" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "CobblemonMorph" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "CobblemonMorph:example_block", combining the namespace and path
    public static final RegistryObject<Block> COBBLEMONMORPH_BLOCK = BLOCKS.register("cobblemonmorph_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    // Creates a new BlockItem with the id "CobblemonMorph:cobblemonmorph_block", combining the namespace and path
    public static final RegistryObject<Item> COBBLEMONMORPH_BLOCK_ITEM = ITEMS.register("cobblemonmorph_block", () -> new BlockItem(COBBLEMONMORPH_BLOCK.get(), new Item.Properties()));

    // Creates a new food item with the id "CobblemonMorph:cobblemonmorph_item", nutrition 1 and saturation 2
    public static final RegistryObject<Item> COBBLEMONMORPH_ITEM = ITEMS.register("cobblemonmorph_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(2f).build())));

    // Creates a creative tab with the id "CobblemonMorph:cobblemonmorph_tab" for the cobblemonmorph_item, that is placed after the combat tab
    // Add the cobblemonmorph_item to the tab. For your own tabs, this method is preferred over the event
    public static final RegistryObject<CreativeModeTab> COBBLEMONMORPH_TAB = CREATIVE_MODE_TABS.register("cobblemonmorph_tab", () -> CreativeModeTab.builder().withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> COBBLEMONMORPH_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {output.accept(COBBLEMONMORPH_ITEM.get()); }).build()); 

    public CobblemonMorph(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    /**
     * This method is called once when the mod is about to be loaded and common setup code should be executed here.
     * This method is called on the logical client, which is the same side as the mod class.
     * This method is called after the mod registration, so all Deferred Registers have been populated and all RegistryObjects have been registered.
     * This method is called before any other mod event is called, so the common setup code should be executed here.
     * The common setup code is the code that is executed on both the logical client and server sides, so it should be placed here.
     * The common setup code is executed after the mod registration and before any other mod event.
     * @param event The FMLCommonSetupEvent which is passed to the method.
     */
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
        {
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
        }
        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    
    /**
     * Add the example block item to the building blocks tab.
     * This method is subscribed to the BuildCreativeModeTabContentsEvent and checks if the tab key is BUILDING_BLOCKS.
     * If so, it adds the EXAMPLE_BLOCK_ITEM to the tab.
     * @param event the BuildCreativeModeTabContentsEvent
     */
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
        {
            event.accept(COBBLEMONMORPH_BLOCK_ITEM);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call

    /**
     * Called when the server starts.
     * This is a good place to add code that should only run on the server.
     * @param event the ServerStartingEvent
     */
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
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

        // public static boolean isModVersionAtLeast(String modid, String version)
        // {
        //     return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getVersion().compareTo(VersionChecker.parseVersionReference(version)) >= 0).orElse(false);
        // }

        // public static boolean isModVersionAtMost(String modid, String version)
        // {
        //     return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getVersion().compareTo(VersionChecker.parseVersionReference(version)) <= 0).orElse(false);
        // }

        // public static boolean isModVersionExact(String modid, String version)
        // {
        //     return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getVersion().compareTo(VersionChecker.parseVersionReference(version)) == 0).orElse(false);
        // }

        // public static String getModAuthors(String modid)
        // {
        //     return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> String.join(", ", modContainer.getModInfo().getAuthors())).orElse("N/A");
        // }

        // public static Optional<URL> getModURL(String modid)
        // {
        //     return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getModURL()).orElseThrow();
        // }
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
