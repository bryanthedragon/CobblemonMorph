package bryanthedragon.morph.cobblemonmorph.items.armor.materials.chestplate.coat;

import java.util.function.Supplier;

import bryanthedragon.morph.cobblemonmorph.items.armor.materials.chestplate.ModChestplateArmorMaterial;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;

public class CoatsArmorMaterial extends ModChestplateArmorMaterial
{
    protected CoatsArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) 
    {
        super(name, durabilityMultiplier, protectionAmounts, enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient);
    }
}
