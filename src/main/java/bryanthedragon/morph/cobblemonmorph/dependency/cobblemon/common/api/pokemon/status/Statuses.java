package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.VolatileStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.nonpersistent.AttractStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.nonpersistent.ConfuseStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.BurnStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.FrozenStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.ParalysisStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.PoisonBadlyStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.PoisonStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.SleepStatus
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nStatuses.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Statuses.kt\ncom/cobblemon/mod/common/api/pokemon/status/Statuses\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,62:1\n1#2:63\n*E\n"])
public object Statuses {
   public final val ATTRACT: AttractStatus = INSTANCE.registerStatus(new AttractStatus()) as AttractStatus
   public final val BURN: BurnStatus = INSTANCE.registerStatus(new BurnStatus()) as BurnStatus
   public final val CONFUSE: ConfuseStatus = INSTANCE.registerStatus(new ConfuseStatus()) as ConfuseStatus
   public final val FROZEN: FrozenStatus = INSTANCE.registerStatus(new FrozenStatus()) as FrozenStatus
   public final val PARALYSIS: ParalysisStatus = INSTANCE.registerStatus(new ParalysisStatus()) as ParalysisStatus
   public final val POISON: PoisonStatus = INSTANCE.registerStatus(new PoisonStatus()) as PoisonStatus
   public final val POISON_BADLY: PoisonBadlyStatus = INSTANCE.registerStatus(new PoisonBadlyStatus()) as PoisonBadlyStatus
   public final val SLEEP: SleepStatus = INSTANCE.registerStatus(new SleepStatus()) as SleepStatus
   private final val allStatuses: MutableList<Status> = (new ArrayList()) as java.util.List
   private final val persistentStatuses: MutableList<Status> = (new ArrayList()) as java.util.List
   private final val volatileStatuses: MutableList<Status> = (new ArrayList()) as java.util.List

   public fun <T : Status> registerStatus(status: Any): Any {
      if (status is PersistentStatus) {
         persistentStatuses.add(status);
      } else if (status is VolatileStatus) {
         volatileStatuses.add(status);
      }

      allStatuses.add(status);
      return (T)status;
   }

   public fun getStatus(name: ResourceLocation): Status? {
      val var3: java.util.Iterator = allStatuses.iterator();

      var var10000: Any;
      while (true) {
         if (var3.hasNext()) {
            val var4: Any = var3.next();
            if (!((var4 as Status).getName() == name)) {
               continue;
            }

            var10000 = var4;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as Status;
   }

   public fun getStatus(showdownName: String): Status? {
      val var3: java.util.Iterator = allStatuses.iterator();

      var var10000: Any;
      while (true) {
         if (var3.hasNext()) {
            val var4: Any = var3.next();
            if (!((var4 as Status).getShowdownName() == showdownName)) {
               continue;
            }

            var10000 = var4;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as Status;
   }

   public fun getPersistentStatuses(): MutableList<Status> {
      return persistentStatuses;
   }
}
