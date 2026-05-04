package bryanthedragon.cobblemon.morph.helper.item;

import bryanthedragon.cobblemon.morph.CobblemonMorph;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings({ "null" })
public class CobblemonMorphItemHelper extends CobblemonMorph
{
    public CobblemonMorphItemHelper()    
    {
        
    }

    public static Item getItemByName(String itemName)
    {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
    }

    public static boolean isItemRegistered(String itemName)
    {
        return ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }
}