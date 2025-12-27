package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei.CobblemonJeiProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.BerryItem
import java.util.ArrayList;
import java.util.LinkedHashSet
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import mezz.jei.api.recipe.category.IRecipeCategory
import mezz.jei.api.registration.IRecipeCategoryRegistration
import mezz.jei.api.registration.IRecipeRegistration

@SourceDebugExtension(["SMAP\nBerryMutationProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BerryMutationProvider.kt\ncom/cobblemon/mod/common/integration/jei/berry/BerryMutationProvider\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,43:1\n125#2:44\n152#2,3:45\n1855#3,2:48\n1549#3:50\n1620#3,3:51\n*S KotlinDebug\n*F\n+ 1 BerryMutationProvider.kt\ncom/cobblemon/mod/common/integration/jei/berry/BerryMutationProvider\n*L\n24#1:44\n24#1:45,3\n26#1:48,2\n37#1:50\n37#1:51,3\n*E\n"])
public class BerryMutationProvider : CobblemonJeiProvider {
   public override fun registerCategory(registration: IRecipeCategoryRegistration) {
      registration.addRecipeCategories(new IRecipeCategory[]{new BerryRecipeCategory(registration)});
   }

   public override fun registerRecipes(registration: IRecipeRegistration) {
      val berryMutations: java.util.Map = CobblemonItems.INSTANCE.berries();
      val `$i$f$map`: java.util.Collection = new ArrayList(berryMutations.size());

      for (Entry item$iv$iv : $this$map$iv.entrySet()) {
         `$i$f$map`.add(`$i$f$mapTo`.getValue() as BerryItem);
      }

      val berryList: java.util.List = `$i$f$map` as java.util.List;
      val var17: java.util.Set = new LinkedHashSet();

      val var18: java.lang.Iterable;
      for (Object element$iv : var18) {
         var var24: BerryItem;
         var var34: java.util.Set;
         label42: {
            var24 = var23 as BerryItem;
            val var10000: Berry = (var23 as BerryItem).berry();
            if (var10000 != null) {
               val var33: java.util.Map = var10000.getMutations();
               if (var33 != null) {
                  var34 = var33.entrySet();
                  if (var34 != null) {
                     break label42;
                  }
               }
            }

            var34 = SetsKt.emptySet();
         }

         for (Entry mut : var34) {
            val var35: BerryItem = CobblemonItems.INSTANCE.berries().get(var29.getKey());
            if (var35 != null) {
               val var36: BerryItem = CobblemonItems.INSTANCE.berries().get(var29.getValue());
               if (var36 != null) {
                  val mutation: Triple = new Triple(var24, var35, var36);
                  if (!var17.contains(mutation) && !var17.contains(new Triple(var35, var24, var36))) {
                     var17.add(mutation);
                  }
               }
            }
         }
      }

      val `$this$map$ivx`: java.lang.Iterable = var17;
      val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var17, 10));

      for (Object item$iv$iv : $this$map$ivx) {
         `destination$iv$ivx`.add(
            new BerryMutationRecipe(
               (var30 as Triple).getFirst() as BerryItem, (var30 as Triple).getSecond() as BerryItem, (var30 as Triple).getThird() as BerryItem
            )
         );
      }

      registration.addRecipes(BerryRecipeCategory.Companion.getRECIPE_TYPE(), `destination$iv$ivx` as java.util.List);
   }
}
