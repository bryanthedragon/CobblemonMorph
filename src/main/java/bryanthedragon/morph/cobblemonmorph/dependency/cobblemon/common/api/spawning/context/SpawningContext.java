package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context;

import com.bedrockk.molang.runtime.struct.VariableStruct;
import com.bedrockk.molang.runtime.value.DoubleValue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;

import java.util.LinkedHashSet;
import kotlin.jvm.functions.Function0;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;

import org.jetbrains.annotations.NotNull;

public abstract class SpawningContext {
   public final val biome: Biome by LazyKt.lazy((new Function0<Biome>(this) {
      {
         super(0);
         this.this$0 = `$receiver`;
      }

      public final Biome invoke() {
         return this.this$0.getWorld().m_204166_(this.this$0.getPosition()).m_203334_() as Biome;
      }
   }) as Function0)
      public final get() {
         val var10000: Any = this.biome$delegate.getValue();
         return var10000 as Biome;
      }


   public final val biomeName: ResourceLocation
      public final get() {
         val var10000: ResourceLocation = this.getBiomeRegistry().m_7981_(this.getBiome());
         return var10000;
      }


   public final val biomeRegistry: Registry<Biome> by LazyKt.lazy((new Function0<Registry<Biome>>(this) {
      {
         super(0);
         this.this$0 = `$receiver`;
      }

      public final Registry<Biome> invoke() {
         return this.this$0.getWorld().m_9598_().m_175515_(Registries.f_256952_);
      }
   }) as Function0)
      public final get() {
         val var10000: Any = this.biomeRegistry$delegate.getValue();
         return var10000 as Registry<Biome>;
      }


   public final val blockRegistry: Registry<Block> by LazyKt.lazy((new Function0<Registry<Block>>(this) {
      {
         super(0);
         this.this$0 = `$receiver`;
      }

      public final Registry<Block> invoke() {
         return this.this$0.getWorld().m_9598_().m_175515_(Registries.f_256747_);
      }
   }) as Function0)
      public final get() {
         val var10000: Any = this.blockRegistry$delegate.getValue();
         return var10000 as Registry<Block>;
      }


   public abstract val canSeeSky: Boolean
   public abstract val cause: SpawnCause

   public final val fluidRegistry: Registry<Fluid> by LazyKt.lazy((new Function0<Registry<Fluid>>(this) {
      {
         super(0);
         this.this$0 = `$receiver`;
      }

      public final Registry<Fluid> invoke() {
         return this.this$0.getWorld().m_9598_().m_175515_(Registries.f_256808_);
      }
   }) as Function0)
      public final get() {
         val var10000: Any = this.fluidRegistry$delegate.getValue();
         return var10000 as Registry<Fluid>;
      }


   public abstract val influences: MutableList<SpawningInfluence>
   public abstract val light: Int

   public final val moonPhase: Int by LazyKt.lazy((new Function0<Integer>(this) {
      {
         super(0);
         this.this$0 = `$receiver`;
      }

      @NotNull
      public final Integer invoke() {
         return this.this$0.getWorld().m_46941_();
      }
   }) as Function0)
      public final get() {
         return (this.moonPhase$delegate.getValue() as java.lang.Number).intValue();
      }


   public abstract val position: BlockPos
   public abstract val skyLight: Int

   public final val spawner: Spawner
      public final get() {
         return this.getCause().getSpawner();
      }


   private final val struct: VariableStruct = new VariableStruct()
   private final var structCompiled: Boolean
   public abstract val world: ServerLevel

   public abstract fun getStructureCache(pos: BlockPos): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext.StructureChunkCache {
   }

   public open fun preFilter(detail: SpawnDetail): Boolean {
      val `$this$none$iv`: java.lang.Iterable = this.getInfluences();
      val var10000: Boolean;
      if (`$this$none$iv` is java.util.Collection && (`$this$none$iv` as java.util.Collection).isEmpty()) {
         var10000 = true;
      } else {
         for (Object element$iv : $this$none$iv) {
            if (!(`element$iv` as SpawningInfluence).affectSpawnable(detail, this)) {
               return false;
            }
         }

         var10000 = true;
      }

      return var10000;
   }

   public open fun postFilter(detail: SpawnDetail): Boolean {
      return true;
   }

   public open fun afterSpawn(entity: Entity) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as SpawningInfluence).affectSpawn(entity);
      }
   }

   public open fun getWeight(detail: SpawnDetail): Float {
      var weight: Float = detail.getWeight();

      for (SpawningInfluence influence : CollectionsKt.plus(this.getInfluences(), detail.getWeightMultipliers())) {
         weight = influence.affectWeight(detail, this, weight);
      }

      return weight;
   }

   public fun getOrSetupStruct(): VariableStruct {
      if (this.structCompiled) {
         return this.struct;
      } else {
         this.struct.setDirectly("light", new DoubleValue((double)this.getLight()));
         this.struct.setDirectly("x", new DoubleValue((double)this.getPosition().m_123341_()));
         this.struct.setDirectly("y", new DoubleValue((double)this.getPosition().m_123342_()));
         this.struct.setDirectly("z", new DoubleValue((double)this.getPosition().m_123343_()));
         this.struct.setDirectly("moon_phase", new DoubleValue((double)this.getMoonPhase()));
         this.struct
            .setDirectly("world", new ObjectValue(this.getWorld().m_9598_().m_175515_(Registries.f_256858_).m_263177_(this.getWorld()), null, null, 6, null));
         this.struct.setDirectly("biome", new ObjectValue(this.getBiomeRegistry().m_263177_(this.getBiome()), null, null, 6, null));
         this.structCompiled = true;
         return this.struct;
      }
   }

   @SourceDebugExtension(["SMAP\nSpawningContext.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningContext.kt\ncom/cobblemon/mod/common/api/spawning/context/SpawningContext$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,187:1\n1#2:188\n*E\n"])
   public companion object {
      public final val contexts: MutableList<RegisteredSpawningContext<*>>

      public fun getByName(name: String): RegisteredSpawningContext<*>? {
         val var3: java.util.Iterator = this.getContexts().iterator();

         var var10000: Any;
         while (true) {
            if (var3.hasNext()) {
               val var4: Any = var3.next();
               if (!((var4 as RegisteredSpawningContext).getName() == name)) {
                  continue;
               }

               var10000 = var4;
               break;
            }

            var10000 = null;
            break;
         }

         return var10000 as RegisteredSpawningContext<?>;
      }

      public fun getByClass(ctx: SpawningContext): RegisteredSpawningContext<*>? {
         val var3: java.util.Iterator = this.getContexts().iterator();

         var var10000: Any;
         while (true) {
            if (var3.hasNext()) {
               val var4: Any = var3.next();
               if (!((var4 as RegisteredSpawningContext).getClazz() == ctx.getClass())) {
                  continue;
               }

               var10000 = var4;
               break;
            }

            var10000 = null;
            break;
         }

         return var10000 as RegisteredSpawningContext<?>;
      }

      public fun <T : SpawningContext> register(name: String, clazz: Class<Any>, defaultCondition: String = "basic") {
         this.getContexts().add(new RegisteredSpawningContext(name, clazz, defaultCondition));
      }
   }

   @SourceDebugExtension(["SMAP\nSpawningContext.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningContext.kt\ncom/cobblemon/mod/common/api/spawning/context/SpawningContext$StructureChunkCache\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,187:1\n1855#2,2:188\n*S KotlinDebug\n*F\n+ 1 SpawningContext.kt\ncom/cobblemon/mod/common/api/spawning/context/SpawningContext$StructureChunkCache\n*L\n123#1:188,2\n*E\n"])
   public class StructureChunkCache {
      public final val foundIdentifiers: MutableSet<ResourceLocation> = (new LinkedHashSet()) as java.util.Set
      public final val foundTags: MutableSet<TagKey<Structure>> = (new LinkedHashSet()) as java.util.Set
      public final var loadedStructures: Boolean
      public final val missingTags: MutableSet<TagKey<Structure>> = (new LinkedHashSet()) as java.util.Set
      public final val structures: MutableSet<Holder<Structure>> = (new LinkedHashSet()) as java.util.Set

      public fun loadStructures(structureAccess: StructureManager, pos: BlockPos) {
         structureAccess.m_220477_(new ChunkPos(pos), SpawningContext.StructureChunkCache::loadStructures$lambda$0);
         this.loadedStructures = true;
      }

      public fun check(structureAccess: StructureManager, pos: BlockPos, tagKey: TagKey<Structure>): Boolean {
         if (!this.loadedStructures) {
            this.loadStructures(structureAccess, pos);
         }

         if (this.missingTags.contains(tagKey)) {
            return false;
         } else if (this.foundTags.contains(tagKey)) {
            return true;
         } else {
            val `$this$forEach$iv`: java.lang.Iterable;
            for (Object element$iv : $this$forEach$iv) {
               if ((`element$iv` as Holder).m_203656_(tagKey)) {
                  this.foundTags.add(tagKey);
                  return true;
               }
            }

            this.missingTags.add(tagKey);
            return false;
         }
      }

      public fun check(structureAccess: StructureManager, pos: BlockPos, id: ResourceLocation): Boolean {
         if (!this.loadedStructures) {
            this.loadStructures(structureAccess, pos);
         }

         return this.foundIdentifiers.contains(id);
      }

      @JvmStatic
      fun `loadStructures$lambda$0`(`$registry`: Registry, `this$0`: SpawningContext.StructureChunkCache, structure: Structure): Boolean {
         val var10000: Holder = `$registry`.m_263177_(structure);
         if (var10000 == null) {
            return true;
         } else {
            `this$0`.structures.add(var10000);
            val var4: java.util.Set = `this$0`.foundIdentifiers;
            val var10001: ResourceLocation = (var10000.m_203543_().get() as ResourceKey).m_135782_();
            var4.add(var10001);
            return false;
         }
      }
   }
}
