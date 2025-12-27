package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.markers.KMappedMarker

@SourceDebugExtension(["SMAP\nExperienceGroups.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExperienceGroups.kt\ncom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroups\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,138:1\n1#2:139\n*E\n"])
public object ExperienceGroups : java.lang.Iterable<ExperienceGroup>, KMappedMarker {
   private final val groups: MutableList<ExperienceGroup> = (new ArrayList()) as java.util.List

   public override operator fun iterator(): MutableIterator<ExperienceGroup> {
      return groups.iterator();
   }

   public fun findByName(name: String): ExperienceGroup? {
      val var3: java.util.Iterator = this.iterator();

      var var10000: Any;
      while (true) {
         if (var3.hasNext()) {
            val var4: Any = var3.next();
            if (!StringsKt.equals((var4 as ExperienceGroup).getName(), name, true)) {
               continue;
            }

            var10000 = var4;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as ExperienceGroup;
   }

   public fun register(experienceGroup: ExperienceGroup): ExperienceGroup {
      groups.add(experienceGroup);
      return experienceGroup;
   }

   public fun unregister(experienceGroup: ExperienceGroup): Boolean {
      return groups.remove(experienceGroup);
   }

   public fun registerDefaults() {
      this.register(Erratic.INSTANCE);
      this.register(Fast.INSTANCE);
      this.register(MediumFast.INSTANCE);
      this.register(MediumSlow.INSTANCE);
      this.register(Slow.INSTANCE);
      this.register(Fluctuating.INSTANCE);
   }
}
