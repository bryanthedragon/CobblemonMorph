package bryanthedragon.morph.cobblemonmorph.items.armor.materials.chestplate.scarf.wool;

import java.util.function.Supplier;

import bryanthedragon.morph.cobblemonmorph.items.armor.materials.chestplate.scarf.ScarfsArmorMaterial;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;

public class Scarf_WoolArmorMaterial extends ScarfsArmorMaterial
{
        protected Scarf_WoolArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) 
        {
                super(name, durabilityMultiplier, protectionAmounts, enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient);
        }
}
