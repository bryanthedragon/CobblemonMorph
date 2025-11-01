package bryanthedragon.morph.cobblemonmorph.helper.registries.tabs;

import bryanthedragon.morph.cobblemonmorph.CobblemonMorph;
import bryanthedragon.morph.cobblemonmorph.icons.tabs.items.CobblemonMorphItemsTabIcon;
import bryanthedragon.morph.cobblemonmorph.icons.tabs.items.armor.CobblemonMorphArmorTabIcon;
import bryanthedragon.morph.cobblemonmorph.icons.tabs.misc.CobblemonMorphMiscTabIcon;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("null")
public class CobblemonMorphTabRegistry 
{
        public static final DeferredRegister<CreativeModeTab> COBBLEMONMORPH_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CobblemonMorph.MODID);

    // example
    // public static final RegistryObject<CreativeModeTab> COBBLEMONMORPH_TAB = COBBLEMONMORPH_TABS.register("cobblemonmorph_tab", () -> CreativeModeTab.builder().build());.

    public static final RegistryObject<CreativeModeTab> COBBLEMONMORPH_MISC_TAB = COBBLEMONMORPH_TABS.register("cobblemonmorph_misc_tab", () -> CreativeModeTab.builder()
        .icon(() ->  new ItemStack(CobblemonMorphMiscTabIcon.icon.get()))
        .title(Component.translatable("creativetab.cobblemonmorph_misc"))
        .displayItems((pParams, pOutput) ->
            {
                // Add items to the misc tab here
            }
        )
        .build()
    );
    public static final RegistryObject<CreativeModeTab> COBBLEMONMORPH_ARMOR_TAB = COBBLEMONMORPH_TABS.register("cobblemonmorph_armor_tab", () -> CreativeModeTab.builder()
        .icon(() ->  new ItemStack(CobblemonMorphArmorTabIcon.icon.get()))
        .title(Component.translatable("creativetab.cobblemonmorph_armor"))
        .displayItems((pParams, pOutput) ->
            {
                // Add items to the armor tab here
            }
        )
        .build()
    );
    public static final RegistryObject<CreativeModeTab> COBBLEMONMORPH_ITEMS_TAB = COBBLEMONMORPH_TABS.register("cobblemonmorph_items_tab", () -> CreativeModeTab.builder()
        .icon(() -> new ItemStack(CobblemonMorphItemsTabIcon.icon.get()))
        .title(Component.translatable("creativetab.cobblemonmorph_items"))
        .displayItems((pParams, pOutput) ->
            {
                // Add items to the items tab here
            }
        )
        .build()
    );
}
