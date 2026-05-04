package bryanthedragon.cobblemon.morph.items.armor.helmet;

import bryanthedragon.cobblemon.morph.items.armor.ModArmorItems;

public class HelmetArmorItems extends ModArmorItems
{
    static ArmorType morphArmorType = ArmorType.HELMET;

    public HelmetArmorItems() {
        super(morphArmorType);
    }

    enum HelmetArmorType
    {
        HAT
    }
}
