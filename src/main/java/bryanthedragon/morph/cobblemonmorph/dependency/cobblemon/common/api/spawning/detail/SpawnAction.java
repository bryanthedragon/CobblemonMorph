package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nSpawnAction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnAction.kt\ncom/cobblemon/mod/common/api/spawning/detail/SpawnAction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,53:1\n1#2:54\n1855#3,2:55\n*S KotlinDebug\n*F\n+ 1 SpawnAction.kt\ncom/cobblemon/mod/common/api/spawning/detail/SpawnAction\n*L\n42#1:55,2\n*E\n"])
public abstract class SpawnAction<R> {
   public final val ctx: SpawningContext
   public open val detail: SpawnDetail
   public final val future: CompletableFuture<Any>

   open fun SpawnAction(ctx: SpawningContext, detail: SpawnDetail) {
      this.ctx = ctx;
      this.detail = detail;
      val var3: CompletableFuture = new CompletableFuture();
      var3.thenApply(SpawnAction::future$lambda$1$lambda$0);
      this.future = var3;
   }

   protected abstract fun run(): Any? {
   }

   public open fun complete(): Any? {
      if (this.future.isDone()) {
         return null;
      } else {
         val result: Any;
         for (Object element$iv : result) {
            (`element$iv` as SpawningInfluence).affectAction(this);
         }

         result = this.run();
         if (result != null) {
            this.future.complete((R)result);
         } else {
            this.future.completeExceptionally(new Exception("Nothing was spawned."));
         }

         return (R)result;
      }
   }

   @JvmStatic
   fun `future$lambda$1$lambda$0`(`$tmp0`: Function1, p0: Any): Unit {
      return `$tmp0`.invoke(p0) as Unit;
   }
}
