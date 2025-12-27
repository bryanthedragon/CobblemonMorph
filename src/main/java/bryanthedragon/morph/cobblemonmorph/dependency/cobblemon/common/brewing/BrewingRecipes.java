package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient.CobblemonIngredient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.brewing.ingredient.CobblemonItemIngredient
import net.minecraft.world.item.Item

public object BrewingRecipes {
   public final val recipes: List<Triple<CobblemonIngredient, CobblemonIngredient, Item>> by LazyKt.lazy(<unrepresentable>.INSTANCE)
      public final get() {
         return recipes$delegate.getValue() as MutableList<Triple<CobblemonIngredient, CobblemonIngredient, Item>>;
      }


   private fun convert(input: Item, ingredient: Item, output: Item): Triple<CobblemonIngredient, CobblemonIngredient, Item> {
      return new Triple(new CobblemonItemIngredient(input), new CobblemonItemIngredient(ingredient), output);
   }
}
