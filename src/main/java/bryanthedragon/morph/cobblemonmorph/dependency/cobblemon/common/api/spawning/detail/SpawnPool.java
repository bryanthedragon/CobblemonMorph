package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnLoader
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.PrecalculationResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.RootPrecalculation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningPrecalculation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.markers.KMappedMarker
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager

@SourceDebugExtension(["SMAP\nSpawnPool.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnPool.kt\ncom/cobblemon/mod/common/api/spawning/detail/SpawnPool\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,110:1\n766#2:111\n857#2,2:112\n*S KotlinDebug\n*F\n+ 1 SpawnPool.kt\ncom/cobblemon/mod/common/api/spawning/detail/SpawnPool\n*L\n49#1:111\n49#1:112,2\n*E\n"])
public class SpawnPool(name: String) : JsonDataRegistry<SpawnSet>, java.lang.Iterable<SpawnDetail>, KMappedMarker {
   public final val details: MutableList<SpawnDetail>
   public open val gson: Gson
   public open val id: ResourceLocation
   public final val name: String
   public open val observable: SimpleObservable<SpawnPool>
   public final var precalculation: PrecalculationResult<*>
   public final val precalculators: MutableList<SpawningPrecalculation<*>>
   public open val resourcePath: String
   public open val type: PackType
   public open val typeToken: TypeToken<SpawnSet>

   init {
      this.name = name;
      this.id = MiscUtilsKt.cobblemonResource("spawn_pool_${this.name}");
      this.type = PackType.SERVER_DATA;
      this.observable = new SimpleObservable<>();
      val var10001: Gson = SpawnLoader.INSTANCE.getGson();
      this.gson = var10001;
      this.typeToken = TypeToken.get(SpawnSet.class);
      this.resourcePath = this.getId().m_135815_();
      this.details = new ArrayList<>();
      this.precalculation = RootPrecalculation.INSTANCE.generate(this.details, CollectionsKt.emptyList());
      this.precalculators = new ArrayList<>();
   }

   public override fun sync(player: ServerPlayer) {
   }

   public override fun reload(data: Map<ResourceLocation, SpawnSet>) {
      this.details.clear();

      for (SpawnSet set : data.values()) {
         val `$this$filter$iv`: java.lang.Iterable = set;
         val var13: java.util.List = this.details;
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filter$iv) {
            if ((`element$iv$iv` as SpawnDetail).isValid()) {
               `destination$iv$iv`.add(`element$iv$iv`);
            }
         }

         var13.addAll(`destination$iv$iv` as java.util.List);
      }

      this.precalculate();
   }

   public override operator fun iterator(): MutableIterator<SpawnDetail> {
      return this.details.iterator();
   }

   public fun addPrecalculators(vararg precalculators: SpawningPrecalculation<*>): SpawnPool {
      CollectionsKt.addAll(this.precalculators, precalculators);
      this.precalculate();
      return this;
   }

   public fun precalculate() {
      if (this.precalculators.isEmpty()) {
         this.precalculation = RootPrecalculation.INSTANCE.generate(this.details, CollectionsKt.emptyList());
      } else {
         this.precalculation = (CollectionsKt.first(this.precalculators) as SpawningPrecalculation)
            .generate(this.details, this.precalculators.subList(1, this.precalculators.size()));
      }
   }

   public fun retrieve(ctx: SpawningContext): List<SpawnDetail> {
      return this.precalculation.retrieve(ctx);
   }

   public fun copy(newName: String): SpawnPool {
      val copy: SpawnPool = new SpawnPool(newName);
      copy.details.addAll(this.details);
      copy.precalculators.addAll(this.precalculators);
      copy.precalculation = this.precalculation;
      return copy;
   }

   override fun reload(manager: ResourceManager) {
      JsonDataRegistry.DefaultImpls.reload(this, manager);
   }
}
