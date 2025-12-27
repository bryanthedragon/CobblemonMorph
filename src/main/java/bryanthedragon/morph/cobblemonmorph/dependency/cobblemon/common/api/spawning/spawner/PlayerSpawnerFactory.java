package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.CobblemonSpawnRules
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.SpawnRule
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MutableLazyKt
import java.util.ArrayList;
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nPlayerSpawnerFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PlayerSpawnerFactory.kt\ncom/cobblemon/mod/common/api/spawning/spawner/PlayerSpawnerFactory\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,59:1\n1603#2,9:60\n1855#2:69\n1856#2:71\n1612#2:72\n766#2:73\n857#2,2:74\n1360#2:76\n1446#2,5:77\n1#3:70\n*S KotlinDebug\n*F\n+ 1 PlayerSpawnerFactory.kt\ncom/cobblemon/mod/common/api/spawning/spawner/PlayerSpawnerFactory\n*L\n50#1:60,9\n50#1:69\n50#1:71\n50#1:72\n54#1:73\n54#1:74,2\n54#1:76\n54#1:77,5\n50#1:70\n*E\n"])
public object PlayerSpawnerFactory {
   public final var influenceBuilders: MutableList<(ServerPlayer) -> SpawningInfluence?> =
      CollectionsKt.mutableListOf(new Function1[]{<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE})

   public final var spawns: SpawnPool by MutableLazyKt.mutableLazy(<unrepresentable>.INSTANCE)
      public final get() {
         return spawns$delegate.getValue(this, $$delegatedProperties[0]) as SpawnPool;
      }

      public final set(<set-?>) {
         spawns$delegate.setValue(this, $$delegatedProperties[0], `<set-?>`);
      }


   public fun create(spawnerManager: SpawnerManager, player: ServerPlayer): PlayerSpawner {
      val `$this$mapNotNull$iv`: java.lang.Iterable = influenceBuilders;
      val `$this$flatMap$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
         val var10000: SpawningInfluence = (`$i$f$flatMapTo` as Function1).invoke(player) as SpawningInfluence;
         if (var10000 != null) {
            `$this$flatMap$iv`.add(var10000);
         }
      }

      val influences: java.util.List = `$this$flatMap$iv` as java.util.List;
      val var19: PlayerSpawner = new PlayerSpawner(player, this.getSpawns(), spawnerManager);
      var19.getInfluences().addAll(influences);
      val var36: java.util.List = var19.getInfluences();
      val var20: java.lang.Iterable = CobblemonSpawnRules.INSTANCE.getRules().values();
      var `destination$iv$ivx`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : var20) {
         if ((var29 as SpawnRule).getEnabled()) {
            `destination$iv$ivx`.add(var29);
         }
      }

      val var21: java.lang.Iterable = `destination$iv$ivx` as java.util.List;
      `destination$iv$ivx` = new ArrayList();

      for (Object element$iv$ivx : var21) {
         CollectionsKt.addAll(`destination$iv$ivx`, (`element$iv$ivx` as SpawnRule).getComponents());
      }

      var36.addAll(`destination$iv$ivx` as java.util.List);
      return var19;
   }
}
