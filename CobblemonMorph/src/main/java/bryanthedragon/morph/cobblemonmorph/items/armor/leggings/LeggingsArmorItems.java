package bryanthedragon.morph.cobblemonmorph.items.armor.leggings;

import bryanthedragon.morph.cobblemonmorph.items.armor.ModArmorItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class LeggingsArmorItems extends ModArmorItems
{
    public static final RegistryObject<Item> PANTS  = ITEMS.register("pants", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SHORTS  = ITEMS.register("shorts", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SKIRT  = ITEMS.register("skirt", () -> new Item(new Item.Properties()));
}
