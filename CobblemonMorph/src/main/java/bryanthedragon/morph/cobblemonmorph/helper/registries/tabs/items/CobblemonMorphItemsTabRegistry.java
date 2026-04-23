package bryanthedragon.morph.cobblemonmorph.helper.registries.tabs.items;

import bryanthedragon.morph.cobblemonmorph.helper.registries.tabs.CobblemonMorphTabRegistry;
import bryanthedragon.morph.cobblemonmorph.icons.tabs.items.CobblemonMorphItemsTabIcon;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("null")
public class CobblemonMorphItemsTabRegistry extends CobblemonMorphTabRegistry
{
    // Register Creative Mode Tabs
    public static final RegistryObject<CreativeModeTab> COBBLEMONMORPH_ITEMS_TAB = COBBLEMONMORPH_TABS.register("cobblemonmorph_items_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(CobblemonMorphItemsTabIcon.icon.get())).title(Component.translatable("creativetab.cobblemonmorph_items")).displayItems((pParams, pOutput) -> {}).build());
}
