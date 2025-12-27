package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei.berry.BerryMutationProvider
import kotlin.jvm.internal.SourceDebugExtension
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.registration.IRecipeCategoryRegistration
import mezz.jei.api.registration.IRecipeRegistration
import net.minecraft.resources.ResourceLocation

@JeiPlugin
@SourceDebugExtension(["SMAP\nCobblemonJeiPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonJeiPlugin.kt\ncom/cobblemon/mod/common/integration/jei/CobblemonJeiPlugin\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,49:1\n1855#2,2:50\n1855#2,2:52\n*S KotlinDebug\n*F\n+ 1 CobblemonJeiPlugin.kt\ncom/cobblemon/mod/common/integration/jei/CobblemonJeiPlugin\n*L\n34#1:50,2\n40#1:52,2\n*E\n"])
public class CobblemonJeiPlugin : IModPlugin {
   private final val jeiProviders: Set<CobblemonJeiProvider> = SetsKt.setOf(new BerryMutationProvider())

   public open fun getPluginUid(): ResourceLocation {
      return ID;
   }

   public open fun registerCategories(registration: IRecipeCategoryRegistration) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as CobblemonJeiProvider).registerCategory(registration);
      }
   }

   public open fun registerRecipes(registration: IRecipeRegistration) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as CobblemonJeiProvider).registerRecipes(registration);
      }
   }

   public companion object {
      public final val ID: ResourceLocation
   }
}
