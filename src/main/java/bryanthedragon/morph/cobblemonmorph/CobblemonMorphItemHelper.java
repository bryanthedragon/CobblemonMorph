package bryanthedragon.morph.cobblemonmorph;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class CobblemonMorphItemHelper 
{
    @SuppressWarnings("removal")
    public static Item getItemByName(String itemName)
    {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
    }

    @SuppressWarnings("removal")
    public static boolean isItemRegistered(String itemName)
    {
        return ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }
}