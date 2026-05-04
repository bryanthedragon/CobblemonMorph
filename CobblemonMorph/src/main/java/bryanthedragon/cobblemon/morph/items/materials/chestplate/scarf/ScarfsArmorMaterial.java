package bryanthedragon.cobblemon.morph.items.materials.chestplate.scarf;

import bryanthedragon.cobblemon.morph.items.materials.chestplate.ModChestplateArmorMaterial;

import net.minecraft.resources.ResourceLocation;

public class ScarfsArmorMaterial extends ModChestplateArmorMaterial
{
    protected ScarfsArmorMaterial(ResourceLocation texture) 
    {
        super(texture);
    }

    enum ScarfsArmorMaterialType
    {
        WOOL,
        SILK
    }
}