package bryanthedragon.morph.cobblemonmorph.items.armor.materials.chestplate.scarf.silk;

import java.util.function.Supplier;

import bryanthedragon.morph.cobblemonmorph.items.armor.materials.chestplate.scarf.ScarfsArmorMaterial;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;

public class Scarf_SilkArmorMaterial extends ScarfsArmorMaterial
{
    protected Scarf_SilkArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) 
    {
        super(name, durabilityMultiplier, protectionAmounts, enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient);
    }
}
