package bryanthedragon.morph.cobblemonmorph.items.materials.chestplate.scarf;

import bryanthedragon.morph.cobblemonmorph.items.materials.chestplate.ModChestplateArmorMaterial;

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