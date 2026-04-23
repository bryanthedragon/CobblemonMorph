package bryanthedragon.morph.cobblemonmorph.items.armor.body;

import bryanthedragon.morph.cobblemonmorph.items.armor.ModArmorItems;
import net.minecraft.sounds.SoundEvent;

public class BodyArmorItems extends ModArmorItems
{

    static ArmorType morphArmorType = ArmorType.BODY;
    
    public BodyArmorItems(String name, int durability, int defensePoints, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance)
    {
        super(morphArmorType);
    }
}
