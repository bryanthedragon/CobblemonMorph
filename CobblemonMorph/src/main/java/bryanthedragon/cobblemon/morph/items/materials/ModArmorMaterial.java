package bryanthedragon.cobblemon.morph.items.materials;

import net.minecraft.resources.ResourceLocation;

public class ModArmorMaterial
{
    private ResourceLocation texture; 

    public ModArmorMaterial(ResourceLocation texture) 
    {
        this.texture = texture;
    }

    public ResourceLocation getTexture() 
    {
        return texture;
    }
}
