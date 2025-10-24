package bryanthedragon.morph.cobblemonmorph.items.armor.chestplate;

import bryanthedragon.morph.cobblemonmorph.items.armor.ModArmorItems;

import net.minecraft.world.item.Item;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class ChestplateArmorItems extends ModArmorItems
{
    public static final RegistryObject<Item> SHIRT  = ITEMS.register("shirt", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> JACKET  = ITEMS.register("jacket", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COAT  = ITEMS.register("coat", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARMOR_VEST  = ITEMS.register("armor_vest", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ROBE  = ITEMS.register("robe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SCARF  = ITEMS.register("scarf", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VEST  = ITEMS.register("vest", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TUNIC  = ITEMS.register("tunic", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLAZER  = ITEMS.register("blazer", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CARDIGAN  = ITEMS.register("cardigan", () -> new Item(new Item.Properties()));

    public static void registerItems(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
