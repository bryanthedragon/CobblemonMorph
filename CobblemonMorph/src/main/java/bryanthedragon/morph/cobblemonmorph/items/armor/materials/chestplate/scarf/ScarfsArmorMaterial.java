package bryanthedragon.morph.cobblemonmorph.items.armor.materials.chestplate.scarf;

import java.util.function.Supplier;

import bryanthedragon.morph.cobblemonmorph.items.armor.materials.chestplate.ModChestplateArmorMaterial;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;

public class ScarfsArmorMaterial extends ModChestplateArmorMaterial
{
    protected ScarfsArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) 
    {
        super(name, durabilityMultiplier, protectionAmounts, enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient);
    }
}