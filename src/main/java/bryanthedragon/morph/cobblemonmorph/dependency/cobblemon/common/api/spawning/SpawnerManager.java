package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.TickingSpawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.gamerules.CobblemonGameRules
import java.util.ArrayList;
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.GameRules

@SourceDebugExtension(["SMAP\nSpawnerManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnerManager.kt\ncom/cobblemon/mod/common/api/spawning/SpawnerManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,60:1\n31#1:73\n800#2,11:61\n800#2,11:74\n1855#2,2:85\n1#3:72\n*S KotlinDebug\n*F\n+ 1 SpawnerManager.kt\ncom/cobblemon/mod/common/api/spawning/SpawnerManager\n*L\n58#1:73\n31#1:61,11\n58#1:74,11\n58#1:85,2\n*E\n"])
public open class SpawnerManager {
   public final val influences: MutableList<SpawningInfluence> = (new ArrayList()) as java.util.List
   public final val spawners: MutableList<Spawner> = (new ArrayList()) as java.util.List

   public open fun getSpawnerByName(name: String): Spawner? {
      val var3: java.util.Iterator = this.spawners.iterator();

      var var10000: Any;
      while (true) {
         if (var3.hasNext()) {
            val var4: Any = var3.next();
            if (!((var4 as Spawner).getName() == name)) {
               continue;
            }

            var10000 = var4;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as Spawner;
   }

   public open fun registerSpawner(spawner: Spawner) {
      this.spawners.add(spawner);
      if (spawner !is TickingSpawner) {
         spawner.getInfluences().addAll(this.influences);
      }
   }

   public open fun unregisterSpawner(spawner: Spawner) {
      this.spawners.remove(spawner);
      if (spawner !is TickingSpawner) {
         spawner.getInfluences().removeAll(this.influences);
      }
   }

   public open fun onServerStarted() {
      this.spawners.clear();
   }

   public open fun onServerTick() {
      if (Cobblemon.INSTANCE.getConfig().getEnableSpawning()) {
         var var15: Boolean;
         label39: {
            val var10000: MinecraftServer = DistributionUtilsKt.server();
            if (var10000 != null) {
               val var14: GameRules = var10000.m_129900_();
               if (var14 != null) {
                  var15 = !var14.m_46207_(CobblemonGameRules.DO_POKEMON_SPAWNING);
                  break label39;
               }
            }

            var15 = false;
         }

         if (!var15) {
            this.influences.removeIf(SpawnerManager::onServerTick$lambda$1);
            val `$this$filterIsInstance$iv$iv`: java.lang.Iterable = this.getSpawners();
            val var6: java.util.Collection = new ArrayList();

            for (Object element$iv$iv$iv : $this$filterIsInstance$iv$iv) {
               if (`element$iv$iv$iv` is TickingSpawner) {
                  var6.add(`element$iv$iv$iv`);
               }
            }

            val `$this$forEach$iv`: java.lang.Iterable;
            for (Object element$iv : $this$forEach$iv) {
               (var12 as TickingSpawner).tick();
            }

            return;
         }
      }
   }

   @JvmStatic
   fun `onServerTick$lambda$1`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }
}
