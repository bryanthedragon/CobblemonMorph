package bryanthedragon.morph.cobblemonmorph.icons.tabs.main;

import bryanthedragon.morph.cobblemonmorph.icons.tabs.CobblemonMorphTabsIcons;

import net.minecraft.world.item.Item;

import net.minecraftforge.registries.RegistryObject;

public class CobblemonMorphMainTabIcon extends CobblemonMorphTabsIcons
{
    // Singleton instance
    public static final CobblemonMorphMainTabIcon INSTANCE = new CobblemonMorphMainTabIcon();

    public CobblemonMorphMainTabIcon() {

    }
    
    public static final RegistryObject<Item> icon = ICON.register("cobblemonmorph_main_tab_icon", () -> new Item(new Item.Properties()));
}
