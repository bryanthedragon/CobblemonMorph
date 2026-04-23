package bryanthedragon.morph.cobblemonmorph.icons.tabs.misc;

import bryanthedragon.morph.cobblemonmorph.icons.tabs.CobblemonMorphTabsIcons;

import net.minecraft.world.item.Item;

import net.minecraftforge.registries.RegistryObject;

public class CobblemonMorphMiscTabIcon extends CobblemonMorphTabsIcons
{
    // Singleton instance
    public static final CobblemonMorphMiscTabIcon INSTANCE = new CobblemonMorphMiscTabIcon();

    public CobblemonMorphMiscTabIcon() {

    }
    public static final RegistryObject<Item> icon = ICON.register("cobblemonmorph_misc_tab_icon", () -> new Item(new Item.Properties()));
}
