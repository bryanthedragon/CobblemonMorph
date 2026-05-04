package bryanthedragon.cobblemon.morph.helper.registries.tabs;

import bryanthedragon.cobblemon.morph.CobblemonMorph;
import bryanthedragon.cobblemon.morph.helper.registries.CobblemonMorphRegistry;
import bryanthedragon.cobblemon.morph.helper.registries.tabs.items.CobblemonMorphItemsTabRegistry;
import bryanthedragon.cobblemon.morph.helper.registries.tabs.main.CobblemonMorphMainTabRegistry;
import bryanthedragon.cobblemon.morph.helper.registries.tabs.misc.CobblemonMorphMiscTabRegistry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CobblemonMorphTabRegistry extends CobblemonMorphRegistry
{
    // Create Deferred Register
    public static final DeferredRegister<CreativeModeTab> COBBLEMONMORPH_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CobblemonMorph.MODID);

    // Register Creative Mode Tabs
    public static final RegistryObject<CreativeModeTab> COBBLEMONMORPH_MAIN_TAB = CobblemonMorphMainTabRegistry.COBBLEMONMORPH_MAIN_TAB;
    public static final RegistryObject<CreativeModeTab> COBBLEMONMORPH_ITEMS_TAB = CobblemonMorphItemsTabRegistry.COBBLEMONMORPH_ITEMS_TAB;
    public static final RegistryObject<CreativeModeTab> COBBLEMONMORPH_MISC_TAB = CobblemonMorphMiscTabRegistry.COBBLEMONMORPH_MISC_TAB;
}
