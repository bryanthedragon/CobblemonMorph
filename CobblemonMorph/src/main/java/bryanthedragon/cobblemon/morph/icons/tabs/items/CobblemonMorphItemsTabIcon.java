package bryanthedragon.morph.cobblemonmorph.icons.tabs.items;

import bryanthedragon.morph.cobblemonmorph.icons.tabs.CobblemonMorphTabsIcons;

import net.minecraft.world.item.Item;

import net.minecraftforge.registries.RegistryObject;

public class CobblemonMorphItemsTabIcon extends CobblemonMorphTabsIcons
{
    public static final CobblemonMorphItemsTabIcon INSTANCE = new CobblemonMorphItemsTabIcon();
    public static final RegistryObject<Item> icon = ICON.register("cobblemonmorph_items_tab_icon", () -> new Item(new Item.Properties()));
}
