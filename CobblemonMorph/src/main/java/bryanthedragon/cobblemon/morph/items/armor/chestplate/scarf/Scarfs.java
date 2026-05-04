package bryanthedragon.cobblemon.morph.items.armor.chestplate.scarf;

import bryanthedragon.cobblemon.morph.items.armor.chestplate.ChestplateArmorItems;

import net.minecraftforge.eventbus.api.IEventBus;

public class Scarfs extends ChestplateArmorItems
{
    public static void registerItems(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}