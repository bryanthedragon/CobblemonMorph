package bryanthedragon.morph.cobblemonmorph.items.armor.leggings;

import bryanthedragon.morph.cobblemonmorph.items.armor.ModArmorItems;

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
