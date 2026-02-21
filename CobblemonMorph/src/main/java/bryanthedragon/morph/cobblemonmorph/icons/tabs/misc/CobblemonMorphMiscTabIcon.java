package bryanthedragon.morph.cobblemonmorph.icons.tabs.misc;

import bryanthedragon.morph.cobblemonmorph.icons.CobblemonMorphIcons;

import net.minecraft.world.item.Item;

import net.minecraftforge.registries.RegistryObject;

public class CobblemonMorphMiscTabIcon extends CobblemonMorphIcons
{
    public static final RegistryObject<Item> icon = ICON.register("cobblemonmorph_misc_tab_icon", () -> new Item(new Item.Properties()));
}
