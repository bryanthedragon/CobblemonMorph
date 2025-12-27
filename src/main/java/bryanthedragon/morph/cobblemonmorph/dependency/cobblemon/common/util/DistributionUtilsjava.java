package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Environment
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.ObservableSubscription
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.Level

public fun ifClient(runnable: Runnable) {
   if (Cobblemon.INSTANCE.getImplementation().environment() === Environment.CLIENT) {
      runnable.run();
   }
}

public fun ifServer(runnable: Runnable) {
   if (Cobblemon.INSTANCE.getImplementation().environment() === Environment.SERVER) {
      runnable.run();
   }
}

public fun ifDedicatedServer(action: Runnable) {
   if (Cobblemon.INSTANCE.getImplementation().environment() === Environment.SERVER) {
      action.run();
   }
}

public fun <T> runOnServer(block: () -> Any): CompletableFuture<Any> {
   val future: CompletableFuture = new CompletableFuture();
   val server: MinecraftServer = server();
   if (server == null) {
      future.completeExceptionally(new IllegalStateException("There is no server to schedule it on."));
   } else {
      server.execute(DistributionUtilsKt::runOnServer$lambda$0);
   }

   return future;
}

public fun <T> Observable<Any>.subscribeOnServer(priority: Priority = Priority.NORMAL, block: () -> Unit): ObservableSubscription<Any> {
   return `$this$subscribeOnServer`.subscribe(priority, (new Function1<T, Unit>(block) {
      {
         super(1);
         this.$block = `$block`;
      }

      public final void invoke(T it) {
         DistributionUtilsKt.runOnServer(this.$block);
      }
   }) as Function1);
}

@JvmSynthetic
fun `subscribeOnServer$default`(var0: Observable, var1: Priority, var2: Function0, var3: Int, var4: Any): ObservableSubscription {
   if ((var3 and 1) != 0) {
      var1 = Priority.NORMAL;
   }

   return subscribeOnServer(var0, var1, var2);
}

public fun server(): MinecraftServer? {
   return Cobblemon.INSTANCE.getImplementation().server();
}

public fun Level.isServerSide(): Boolean {
   return !`$this$isServerSide`.f_46443_;
}

fun `runOnServer$lambda$0`(`$future`: CompletableFuture, `$block`: Function0) {
   `$future`.complete(`$block`.invoke());
}
