package bryanthedragon.cobblemon.morph.icons.tabs.items.armor;

import bryanthedragon.cobblemon.morph.icons.tabs.items.CobblemonMorphItemsTabIcon;

import net.minecraft.world.item.Item;

import net.minecraftforge.registries.RegistryObject;

public class CobblemonMorphArmorTabIcon extends CobblemonMorphItemsTabIcon
{
    public static RegistryObject<Item> icon = ICON.register("cobblemonmorph_armor_tab_icon", () -> new Item(new Item.Properties()));
}
