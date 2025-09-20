package bryanthedragon.morph.cobblemonmorph.items.armor.materials;

import bryanthedragon.morph.cobblemonmorph.CobblemonMorph;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class ModArmorMaterial
{
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;
    private static final int[] BASE_DURABILITY = { 11, 16, 16, 13};

    protected ModArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) 
    {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    public int getDurabilityForType(Type pType) 
    {
        return BASE_DURABILITY[pType.getSlot().getIndex()] * this.durabilityMultiplier;
    }


    public int getDefenseForType(Type pType) 
    {
        return this.protectionAmounts[pType.getSlot().getIndex()];
    }

    public int getEnchantmentValue() 
    {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() 
    {
        return this.equipSound;
    }

    public Ingredient getRepairIngredient() 
    {
        return this.repairIngredient.get();
    }

    public String getName() 
    {
        return CobblemonMorph.MODID + ":" + this.name;
    }

    public float getToughness() 
    {   
        return this.toughness;
    }

    public float getKnockbackResistance() 
    {
        return this.knockbackResistance;
    }

}
