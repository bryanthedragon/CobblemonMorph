package bryanthedragon.cobblemon.morph.items.armor.chestplate.shirt.shortshirt;

import bryanthedragon.cobblemon.morph.items.armor.chestplate.shirt.Shirts;

import net.minecraft.world.item.Item;

import net.minecraftforge.registries.RegistryObject;

public class Shirt_Short extends Shirts
{
    public static final RegistryObject<Item> SHIRT_SHORT  = ITEMS.register("shirt_short", () -> new Item(new Item.Properties()));
}
