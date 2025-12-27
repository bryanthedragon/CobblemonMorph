package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity.SpawnEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt
import java.util.Arrays
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.entity.Entity
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nSingleEntitySpawnAction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SingleEntitySpawnAction.kt\ncom/cobblemon/mod/common/api/spawning/detail/SingleEntitySpawnAction\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable$postThen$1\n*L\n1#1,56:1\n39#2,2:57\n41#2,2:62\n44#2,3:65\n47#2:70\n17#3,2:59\n19#3:69\n13579#4:61\n13580#4:68\n39#5:64\n*S KotlinDebug\n*F\n+ 1 SingleEntitySpawnAction.kt\ncom/cobblemon/mod/common/api/spawning/detail/SingleEntitySpawnAction\n*L\n34#1:57,2\n34#1:62,2\n34#1:65,3\n34#1:70\n34#1:59,2\n34#1:69\n34#1:61\n34#1:68\n34#1:64\n*E\n"])
public abstract class SingleEntitySpawnAction<T extends Entity> : SpawnAction<EntitySpawnResult> {
   public final val entity: SimpleObservable<Any>

   open fun SingleEntitySpawnAction(ctx: SpawningContext, detail: SpawnDetail) {
      super(ctx, detail);
      val var3: SimpleObservable = new SimpleObservable();
      Observable.DefaultImpls.subscribe$default(var3, null, (new Function1<T, Unit>(ctx) {
         {
            super(1);
            this.$ctx = `$ctx`;
         }

         public final void invoke(@NotNull T entity) {
            this.$ctx.afterSpawn(entity);
         }
      }) as Function1, 1, null);
      this.entity = var3;
   }

   public abstract fun createEntity(): Any? {
   }

   protected open fun run(): EntitySpawnResult? {
      val var10000: Entity = this.createEntity();
      if (var10000 == null) {
         return null;
      } else {
         val e: Entity = var10000;
         var10000.m_146884_(BlockPosExtensionsKt.toVec3d(this.getCtx().getPosition()).m_82520_(0.5, 1.0, 0.5));
         var shouldSpawn: Boolean = false;
         val `$this$iv`: CancelableObservable = CobblemonEvents.ENTITY_SPAWN;
         val `event$iv`: Cancelable = new SpawnEvent<>(var10000, this.getCtx());
         val `this_$iv$iv`: EventObservable = `$this$iv`;
         val `events$iv$iv`: Array<Cancelable> = new Cancelable[]{`event$iv`};
         `this_$iv$iv`.emit(Arrays.copyOf(`events$iv$iv`, `events$iv$iv`.length));

         for (Object element$iv$iv$iv : events$iv$iv) {
            if (!((Cancelable)`element$iv$iv$iv`).isCanceled()) {
               val it: SpawnEvent = `element$iv$iv$iv` as SpawnEvent;
               this.getCtx().getWorld().m_7967_(e);
               shouldSpawn = true;
            }
         }

         val var21: EntitySpawnResult;
         if (shouldSpawn) {
            this.entity.emit((T[])(new Entity[]{e}));
            var21 = new EntitySpawnResult(CollectionsKt.listOf(e));
         } else {
            var21 = null;
         }

         return var21;
      }
   }
}
