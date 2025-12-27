package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ModDependant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import java.nio.file.Path
import java.util.ArrayList;
import kotlin.jvm.internal.markers.KMappedMarker

public class SpawnSet : java.lang.Iterable<SpawnDetail>, ModDependant, KMappedMarker {
   public final var enabled: Boolean = true
   public open var neededInstalledMods: List<String> = CollectionsKt.emptyList()
   public open var neededUninstalledMods: List<String> = CollectionsKt.emptyList()
   public final lateinit var path: Path
   public final var spawns: MutableList<SpawnDetail> = (new ArrayList()) as java.util.List

   public fun isEnabled(): Boolean {
      return this.enabled && this.isModDependencySatisfied();
   }

   public override operator fun iterator(): MutableIterator<SpawnDetail> {
      return this.spawns.iterator();
   }

   override fun isModDependencySatisfied(): Boolean {
      return ModDependant.DefaultImpls.isModDependencySatisfied(this);
   }
}
