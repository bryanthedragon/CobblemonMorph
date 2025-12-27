package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.gui.drawable.IDrawableStatic
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeIngredientRole
import mezz.jei.api.recipe.RecipeType
import mezz.jei.api.recipe.category.IRecipeCategory
import mezz.jei.api.registration.IRecipeCategoryRegistration
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

public class BerryRecipeCategory(registration: IRecipeCategoryRegistration) : IRecipeCategory<BerryMutationRecipe> {
   private final var background: IDrawable
   private final var icon: IDrawable
   private final val registration: IRecipeCategoryRegistration

   init {
      this.registration = registration;
      val guiHelper: IGuiHelper = this.registration.getJeiHelpers().getGuiHelper();
      val var10001: IDrawableStatic = guiHelper.createDrawable(GUI_TEXTURE_ID, 0, 0, 124, 17);
      this.background = var10001 as IDrawable;
      val var3: IDrawable = guiHelper.createDrawableItemStack(CobblemonItems.SURPRISE_MULCH.m_7968_());
      this.icon = var3;
   }

   public open fun getRecipeType(): RecipeType<BerryMutationRecipe> {
      return RECIPE_TYPE;
   }

   public open fun getTitle(): Component {
      val var10000: Component = Component.m_130674_("Berry Mutation");
      return var10000;
   }

   public open fun getBackground(): IDrawable {
      return this.background;
   }

   public open fun getIcon(): IDrawable {
      return this.icon;
   }

   public open fun setRecipe(p0: IRecipeLayoutBuilder, p1: BerryMutationRecipe, p2: IFocusGroup) {
      p0.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(Ingredient.m_43927_(new ItemStack[]{p1.getBerryOne().m_7968_()}));
      p0.addSlot(RecipeIngredientRole.INPUT, 50, 1).addIngredients(Ingredient.m_43927_(new ItemStack[]{p1.getBerryTwo().m_7968_()}));
      p0.addSlot(RecipeIngredientRole.OUTPUT, 108, 1).addIngredients(Ingredient.m_43927_(new ItemStack[]{p1.getBerryResult().m_7968_()}));
   }

   @JvmStatic
   fun {
      val var10000: RecipeType = RecipeType.create("cobblemon", "berry_recipe", BerryMutationRecipe.class);
      RECIPE_TYPE = var10000;
   }

   public companion object {
      public final val GUI_TEXTURE_ID: ResourceLocation
      public const val HEIGHT: Int
      public final val RECIPE_TYPE: RecipeType<BerryMutationRecipe>
      public const val WIDTH: Int
   }
}
