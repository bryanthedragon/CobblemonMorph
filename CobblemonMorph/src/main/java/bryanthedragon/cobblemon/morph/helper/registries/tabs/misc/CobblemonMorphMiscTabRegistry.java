package bryanthedragon.cobblemon.morph.helper.registries.tabs.misc;

import bryanthedragon.cobblemon.morph.icons.tabs.misc.CobblemonMorphMiscTabIcon;
import bryanthedragon.cobblemon.morph.helper.registries.tabs.CobblemonMorphTabRegistry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.registries.RegistryObject;

public class CobblemonMorphMiscTabRegistry extends CobblemonMorphTabRegistry
{
    // Register Creative Mode Tabs
    @SuppressWarnings("null")
    public static final RegistryObject<CreativeModeTab> COBBLEMONMORPH_MISC_TAB = COBBLEMONMORPH_TABS.register("cobblemonmorph_misc_tab", () -> CreativeModeTab.builder().icon(() ->  new ItemStack(CobblemonMorphMiscTabIcon.icon.get())).title(Component.translatable("creativetab.cobblemonmorph_misc")).displayItems((pParams, pOutput) -> {}).build());
}
