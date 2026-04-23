package bryanthedragon.morph.cobblemonmorph.items.armor.helmet;

import bryanthedragon.morph.cobblemonmorph.items.armor.ModArmorItems;

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
