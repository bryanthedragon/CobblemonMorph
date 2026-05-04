package bryanthedragon.cobblemon.morph.items;

import bryanthedragon.cobblemon.morph.CobblemonMorph;

import net.minecraft.world.item.Item;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems 
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CobblemonMorph.MODID);

    // public static final RegistryObject<Item> EXAMPLE  = ITEMS.register("example", () -> new Item(null));

    // Armor Items
    // public static final RegistryObject<Item> ITEM = ITEMS.register("item", () -> new Item(ITEM_PROPERTIES));
    // public static final RegistryObject<Item> CHESTPLATE  = ITEMS.register("chestplate", () -> new Item(ITEM_PROPERTIES));
    // public static final RegistryObject<Item> LEGGINGS  = ITEMS.register("leggings", () -> new Item(ITEM_PROPERTIES));
    // public static final RegistryObject<Item> BOOTS  = ITEMS.register("boots", () -> new Item(ITEM_PROPERTIES));
    // public static final RegistryObject<Item> HELMET  = ITEMS.register("helmet", () -> new Item(ITEM_PROPERTIES));

    public static void registerItems(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
