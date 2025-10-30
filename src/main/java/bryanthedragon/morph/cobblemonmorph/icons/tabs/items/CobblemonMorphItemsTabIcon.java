package bryanthedragon.morph.cobblemonmorph.icons.tabs.items;

import bryanthedragon.morph.cobblemonmorph.icons.CobblemonMorphIcons;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class CobblemonMorphItemsTabIcon extends CobblemonMorphIcons
{
    public static final RegistryObject<Item> icon = ICON.register("cobblemonmorph_items_tab_icon", () -> new Item(new Item.Properties()));
}
