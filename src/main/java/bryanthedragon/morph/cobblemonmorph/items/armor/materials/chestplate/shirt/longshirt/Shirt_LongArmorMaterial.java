package bryanthedragon.morph.cobblemonmorph.items.armor.materials.chestplate.shirt.longshirt;

import java.util.function.Supplier;

import bryanthedragon.morph.cobblemonmorph.items.armor.materials.chestplate.shirt.ShirtsArmorMaterial;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;

public class Shirt_LongArmorMaterial extends ShirtsArmorMaterial
{
    protected Shirt_LongArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) 
    {
        super(name, durabilityMultiplier, protectionAmounts, enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient);
    }
}
