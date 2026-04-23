package bryanthedragon.morph.cobblemonmorph.helper.registries.tabs.items.armor;

import bryanthedragon.morph.cobblemonmorph.icons.tabs.items.armor.CobblemonMorphArmorTabIcon;
import bryanthedragon.morph.cobblemonmorph.helper.registries.tabs.items.CobblemonMorphItemsTabRegistry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("null")
public class CobblemonMorphArmorTabRegistry extends CobblemonMorphItemsTabRegistry
{
    // Register Creative Mode Tabs
    public static final RegistryObject<CreativeModeTab> COBBLEMONMORPH_ARMOR_TAB = COBBLEMONMORPH_TABS.register("cobblemonmorph_armor_tab", () -> CreativeModeTab.builder().icon(() ->  new ItemStack(CobblemonMorphArmorTabIcon.icon.get())).title(Component.translatable("creativetab.cobblemonmorph_armor")).displayItems((pParams, pOutput) -> {}).build());
}
