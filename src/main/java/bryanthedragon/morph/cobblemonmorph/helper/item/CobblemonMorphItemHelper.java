package bryanthedragon.morph.cobblemonmorph.helper.item;

import bryanthedragon.morph.cobblemonmorph.CobblemonMorph;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

public class CobblemonMorphItemHelper extends CobblemonMorph
{
    public CobblemonMorphItemHelper(FMLJavaModLoadingContext context) 
    {
        super(context);
    }

    @SuppressWarnings({ "removal", "null" })
    public static Item getItemByName(String itemName)
    {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
    }

    @SuppressWarnings({ "removal", "null" })
    public static boolean isItemRegistered(String itemName)
    {
        return ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }
}