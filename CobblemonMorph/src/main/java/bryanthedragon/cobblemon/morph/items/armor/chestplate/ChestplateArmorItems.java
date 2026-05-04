package bryanthedragon.cobblemon.morph.items.armor.chestplate;

import bryanthedragon.cobblemon.morph.items.armor.ModArmorItems;

public class ChestplateArmorItems extends ModArmorItems
{

    static ArmorType morphArmorType = ArmorType.CHESTPLATE;

    public ChestplateArmorItems() {
        super(morphArmorType);
    }

    enum ChestplateArmorType
    {
        SHIRT,
        JACKET,
        COAT,
        ARMOR_VEST,
        ROBE,
        SCARF,
        VEST,
        TUNIC,
        BLAZER,
        CARDIGAN,
    }
}
