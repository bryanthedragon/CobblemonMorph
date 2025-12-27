package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei

import mezz.jei.api.registration.IRecipeCategoryRegistration
import mezz.jei.api.registration.IRecipeRegistration

public interface CobblemonJeiProvider {
   public abstract fun registerCategory(registration: IRecipeCategoryRegistration) {
   }

   public abstract fun registerRecipes(registration: IRecipeRegistration) {
   }
}
