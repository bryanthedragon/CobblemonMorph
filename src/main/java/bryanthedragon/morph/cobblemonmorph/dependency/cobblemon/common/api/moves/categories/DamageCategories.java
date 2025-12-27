package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.ArrayList;
import java.util.NoSuchElementException
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nDamageCategories.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DamageCategories.kt\ncom/cobblemon/mod/common/api/moves/categories/DamageCategories\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,64:1\n288#2,2:65\n223#2,2:67\n*S KotlinDebug\n*F\n+ 1 DamageCategories.kt\ncom/cobblemon/mod/common/api/moves/categories/DamageCategories\n*L\n56#1:65,2\n60#1:67,2\n*E\n"])
public object DamageCategories {
   public final val PHYSICAL: DamageCategory
   public final val SPECIAL: DamageCategory
   public final val STATUS: DamageCategory
   private final val allCategories: MutableList<DamageCategory> = (new ArrayList()) as java.util.List

   public fun register(
      name: String,
      displayName: Component,
      resourceLocation: ResourceLocation = MiscUtilsKt.cobblemonResource("textures/gui/categories.png"),
      textureXMultiplier: Int
   ): DamageCategory {
      val var5: DamageCategory = new DamageCategory(name, displayName, textureXMultiplier, resourceLocation);
      allCategories.add(var5);
      return var5;
   }

   public fun register(damageCategory: DamageCategory): DamageCategory {
      allCategories.add(damageCategory);
      return damageCategory;
   }

   public fun get(name: String): DamageCategory? {
      val var4: java.util.Iterator = allCategories.iterator();

      var var10000: Any;
      while (true) {
         if (var4.hasNext()) {
            val `element$iv`: Any = var4.next();
            if (!StringsKt.equals((`element$iv` as DamageCategory).getName(), name, true)) {
               continue;
            }

            var10000 = `element$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as DamageCategory;
   }

   public fun getOrException(name: String): DamageCategory {
      val `$this$first$iv`: java.lang.Iterable;
      for (Object element$iv : $this$first$iv) {
         if (StringsKt.equals((`element$iv` as DamageCategory).getName(), name, true)) {
            return `element$iv` as DamageCategory;
         }
      }

      throw new NoSuchElementException("Collection contains no element matching the predicate.");
   }

   public fun count(): Int {
      return allCategories.size();
   }

   @JvmStatic
   fun {
      var var10000: DamageCategories = INSTANCE;
      var var10002: MutableComponent = Component.m_237115_("cobblemon.move.category.physical");
      PHYSICAL = register$default(var10000, "physical", var10002 as Component, null, 0, 4, null);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.move.category.special");
      SPECIAL = register$default(var10000, "special", var10002 as Component, null, 1, 4, null);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.move.category.status");
      STATUS = register$default(var10000, "status", var10002 as Component, null, 2, 4, null);
   }
}
