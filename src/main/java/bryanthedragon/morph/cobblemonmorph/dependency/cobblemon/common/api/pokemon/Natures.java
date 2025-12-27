package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Flavor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nNatures.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Natures.kt\ncom/cobblemon/mod/common/api/pokemon/Natures\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,189:1\n1#2:190\n*E\n"])
public object Natures {
   public final val ADAMANT: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("adamant"), "cobblemon.nature.adamant", Stats.ATTACK, Stats.SPECIAL_ATTACK, Flavor.SPICY, Flavor.DRY)
      )
      public final val BASHFUL: Nature =
      INSTANCE.registerNature(new Nature(MiscUtilsKt.cobblemonResource("bashful"), "cobblemon.nature.bashful", null, null, null, null))
      public final val BOLD: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("bold"), "cobblemon.nature.bold", Stats.DEFENCE, Stats.ATTACK, Flavor.SOUR, Flavor.SPICY)
      )
      public final val BRAVE: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("brave"), "cobblemon.nature.brave", Stats.ATTACK, Stats.SPEED, Flavor.SPICY, Flavor.SWEET)
      )
      public final val CALM: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("calm"), "cobblemon.nature.calm", Stats.SPECIAL_DEFENCE, Stats.ATTACK, Flavor.BITTER, Flavor.SPICY)
      )
      public final val CAREFUL: Nature =
      INSTANCE.registerNature(
         new Nature(
            MiscUtilsKt.cobblemonResource("careful"), "cobblemon.nature.careful", Stats.SPECIAL_DEFENCE, Stats.SPECIAL_ATTACK, Flavor.BITTER, Flavor.DRY
         )
      )
      public final val DOCILE: Nature =
      INSTANCE.registerNature(new Nature(MiscUtilsKt.cobblemonResource("docile"), "cobblemon.nature.docile", null, null, null, null))
      public final val GENTLE: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("gentle"), "cobblemon.nature.gentle", Stats.SPECIAL_DEFENCE, Stats.DEFENCE, Flavor.BITTER, Flavor.SOUR)
      )
      public final val HARDY: Nature =
      INSTANCE.registerNature(new Nature(MiscUtilsKt.cobblemonResource("hardy"), "cobblemon.nature.hardy", null, null, null, null))
      public final val HASTY: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("hasty"), "cobblemon.nature.hasty", Stats.SPEED, Stats.DEFENCE, Flavor.SWEET, Flavor.SOUR)
      )
      public final val IMPISH: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("impish"), "cobblemon.nature.impish", Stats.DEFENCE, Stats.SPECIAL_ATTACK, Flavor.SOUR, Flavor.DRY)
      )
      public final val JOLLY: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("jolly"), "cobblemon.nature.jolly", Stats.SPEED, Stats.SPECIAL_ATTACK, Flavor.SWEET, Flavor.DRY)
      )
      public final val LAX: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("lax"), "cobblemon.nature.lax", Stats.DEFENCE, Stats.SPECIAL_DEFENCE, Flavor.SOUR, Flavor.BITTER)
      )
      public final val LONELY: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("lonely"), "cobblemon.nature.lonely", Stats.ATTACK, Stats.DEFENCE, Flavor.SPICY, Flavor.SOUR)
      )
      public final val MILD: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("mild"), "cobblemon.nature.mild", Stats.SPECIAL_ATTACK, Stats.DEFENCE, Flavor.DRY, Flavor.SOUR)
      )
      public final val MODEST: Nature =
      INSTANCE.registerNature(new Nature(MiscUtilsKt.cobblemonResource("modest"), "cobblemon.nature.modest", Stats.SPECIAL_ATTACK, Stats.ATTACK, null, null))
      public final val NAIVE: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("naive"), "cobblemon.nature.naive", Stats.SPEED, Stats.SPECIAL_DEFENCE, Flavor.SWEET, Flavor.BITTER)
      )
      public final val NAUGHTY: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("naughty"), "cobblemon.nature.naughty", Stats.ATTACK, Stats.SPECIAL_DEFENCE, Flavor.SPICY, Flavor.BITTER)
      )
      public final val QUIET: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("quiet"), "cobblemon.nature.quiet", Stats.SPECIAL_ATTACK, Stats.SPEED, Flavor.DRY, Flavor.SWEET)
      )
      public final val QUIRKY: Nature =
      INSTANCE.registerNature(new Nature(MiscUtilsKt.cobblemonResource("quirky"), "cobblemon.nature.quirky", null, null, null, null))
      public final val RASH: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("rash"), "cobblemon.nature.rash", Stats.SPECIAL_ATTACK, Stats.SPECIAL_DEFENCE, Flavor.DRY, Flavor.BITTER)
      )
      public final val RELAXED: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("relaxed"), "cobblemon.nature.relaxed", Stats.DEFENCE, Stats.SPEED, Flavor.SOUR, Flavor.SWEET)
      )
      public final val SASSY: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("sassy"), "cobblemon.nature.sassy", Stats.SPECIAL_DEFENCE, Stats.SPEED, Flavor.BITTER, Flavor.SWEET)
      )
      public final val SERIOUS: Nature =
      INSTANCE.registerNature(new Nature(MiscUtilsKt.cobblemonResource("serious"), "cobblemon.nature.serious", null, null, null, null))
      public final val TIMID: Nature =
      INSTANCE.registerNature(
         new Nature(MiscUtilsKt.cobblemonResource("timid"), "cobblemon.nature.timid", Stats.SPEED, Stats.ATTACK, Flavor.SWEET, Flavor.SPICY)
      )
      private final val allNatures: MutableList<Nature> = (new ArrayList()) as java.util.List

   public fun registerNature(nature: Nature): Nature {
      allNatures.add(nature);
      return nature;
   }

   public fun getNature(name: ResourceLocation): Nature? {
      val var3: java.util.Iterator = allNatures.iterator();

      var var10000: Any;
      while (true) {
         if (var3.hasNext()) {
            val var4: Any = var3.next();
            if (!((var4 as Nature).getName() == name)) {
               continue;
            }

            var10000 = var4;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as Nature;
   }

   public fun getNature(identifier: String): Nature? {
      val nature: Nature = this.getNature(MiscUtilsKt.cobblemonResource(identifier));
      return nature ?: this.getNature(new ResourceLocation(identifier));
   }

   public fun getRandomNature(): Nature {
      return CollectionsKt.random(allNatures, Random.Default as Random) as Nature;
   }

   public fun all(): Collection<Nature> {
      return CollectionsKt.toList(allNatures);
   }
}
