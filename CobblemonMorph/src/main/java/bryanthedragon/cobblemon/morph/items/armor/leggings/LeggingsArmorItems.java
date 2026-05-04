package bryanthedragon.cobblemon.morph.items.armor.leggings;

import bryanthedragon.cobblemon.morph.items.armor.ModArmorItems;

public class LeggingsArmorItems extends ModArmorItems
{

    static ArmorType morphArmorType = ArmorType.CHESTPLATE;

    public LeggingsArmorItems() {
        super(morphArmorType);
    }

    enum LeggingsArmorType
    {
        PANTS,
        SHORTS,
        SKIRT,
    }
}
