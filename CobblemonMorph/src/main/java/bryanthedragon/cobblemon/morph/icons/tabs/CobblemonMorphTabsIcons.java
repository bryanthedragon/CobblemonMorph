package bryanthedragon.cobblemon.morph.icons.tabs;

import bryanthedragon.cobblemon.morph.CobblemonMorph;
import bryanthedragon.cobblemon.morph.icons.tabs.items.CobblemonMorphItemsTabIcon;
import bryanthedragon.cobblemon.morph.icons.tabs.main.CobblemonMorphMainTabIcon;
import bryanthedragon.cobblemon.morph.icons.tabs.misc.CobblemonMorphMiscTabIcon;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.DeferredRegister;

public class CobblemonMorphTabsIcons 
{
    public static final DeferredRegister<Item> ICON = DeferredRegister.create(Registries.ITEM, CobblemonMorph.MODID);

    public static final CobblemonMorphMainTabIcon MAIN_TAB_ICON = CobblemonMorphMainTabIcon.INSTANCE;
    public static final CobblemonMorphItemsTabIcon ITEMS_TAB_ICON = CobblemonMorphItemsTabIcon.INSTANCE;
    public static final CobblemonMorphMiscTabIcon MISC_TAB_ICON = CobblemonMorphMiscTabIcon.INSTANCE;
}
