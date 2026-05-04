package bryanthedragon.cobblemon.morph.items.armor.chestplate.shirt;

import bryanthedragon.cobblemon.morph.items.armor.chestplate.ChestplateArmorItems;

import net.minecraftforge.eventbus.api.IEventBus;

public class Shirts extends ChestplateArmorItems
{
    public static void registerItems(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
