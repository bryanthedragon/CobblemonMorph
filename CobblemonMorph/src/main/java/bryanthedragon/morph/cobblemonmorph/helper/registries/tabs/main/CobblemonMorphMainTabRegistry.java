package bryanthedragon.morph.cobblemonmorph.helper.registries.tabs.main;

import bryanthedragon.morph.cobblemonmorph.icons.tabs.main.CobblemonMorphMainTabIcon;
import bryanthedragon.morph.cobblemonmorph.helper.registries.tabs.CobblemonMorphTabRegistry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.registries.RegistryObject;

public class CobblemonMorphMainTabRegistry extends CobblemonMorphTabRegistry
{
    // Register Creative Mode Tabs
    @SuppressWarnings("null")
    public static final RegistryObject<CreativeModeTab> COBBLEMONMORPH_MAIN_TAB = COBBLEMONMORPH_TABS.register("cobblemonmorph_tab", () -> CreativeModeTab.builder().icon(() ->  new ItemStack(CobblemonMorphMainTabIcon.icon.get())).title(Component.translatable("creativetab.cobblemonmorph")).displayItems((pParams, pOutput) -> {}).build());
}
