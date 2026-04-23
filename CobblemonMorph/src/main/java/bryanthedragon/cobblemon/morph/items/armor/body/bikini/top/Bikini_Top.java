package bryanthedragon.morph.cobblemonmorph.items.armor.body.bikini.top;

import bryanthedragon.morph.cobblemonmorph.items.armor.body.bikini.BikiniArmorItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.RegistryObject;

public class Bikini_Top extends BikiniArmorItem
{
	public Bikini_Top(String name, int durability, int defensePoints, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance) {
		super(name, durability, defensePoints, enchantability, equipSound, toughness, knockbackResistance);
	}

	public static final RegistryObject<Item> BIKINI_TOP  = ITEMS.register("bikini_top", () -> new Item(new Item.Properties()));
}
