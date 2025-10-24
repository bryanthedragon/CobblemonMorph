package bryanthedragon.morph.cobblemonmorph.items.armor;

import bryanthedragon.morph.cobblemonmorph.items.ModItems;

import net.minecraft.world.item.Item;

import net.minecraftforge.registries.RegistryObject;

public class ModArmorItems extends ModItems
{
    // Armor Items
    // public static final RegistryObject<Item> ITEM = ITEMS.register("item", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHESTPLATE  = ITEMS.register("chestplate", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEGGINGS  = ITEMS.register("leggings", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BOOTS  = ITEMS.register("boots", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HELMET  = ITEMS.register("helmet", () -> new Item(new Item.Properties()));
}
