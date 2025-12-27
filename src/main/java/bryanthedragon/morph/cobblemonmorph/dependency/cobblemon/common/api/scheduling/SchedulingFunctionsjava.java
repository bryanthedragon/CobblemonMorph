package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask.Builder
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1

@Deprecated(message = "Use afterOnServer or afterOnClient; ambiguous side is not good for your health")
@JvmOverloads
public fun after(ticks: Int = 0, seconds: Float = 0.0F, serverThread: Boolean = false, action: () -> Unit) {
   ((if (serverThread) ServerTaskTracker.INSTANCE else ClientTaskTracker.INSTANCE) as Schedulable).after(seconds + (float)ticks / 20.0F, action);
}

/** @deprecated */
@JvmSynthetic
fun `after$default`(var0: Int, var1: Float, var2: Boolean, var3: Function0, var4: Int, var5: Any) {
   if ((var4 and 1) != 0) {
      var0 = 0;
   }

   if ((var4 and 2) != 0) {
      var1 = 0.0F;
   }

   if ((var4 and 4) != 0) {
      var2 = false;
   }

   after(var0, var1, var2, var3);
}

public fun delayedFuture(ticks: Int = 0, seconds: Float = 0.0F, serverThread: Boolean = false): CompletableFuture<Unit> {
   val future: CompletableFuture = new CompletableFuture();
   if (ticks == 0 && seconds == 0.0F) {
      future.complete(Unit.INSTANCE);
   } else {
      after(ticks, seconds, serverThread, (new Function0<Unit>(future) {
         {
            super(0);
            this.$future = `$future`;
         }

         public final void invoke() {
            this.$future.complete(Unit.INSTANCE);
         }
      }) as () -> Unit);
   }

   return future;
}

@JvmSynthetic
fun `delayedFuture$default`(var0: Int, var1: Float, var2: Boolean, var3: Int, var4: Any): CompletableFuture {
   if ((var3 and 1) != 0) {
      var0 = 0;
   }

   if ((var3 and 2) != 0) {
      var1 = 0.0F;
   }

   if ((var3 and 4) != 0) {
      var2 = false;
   }

   return delayedFuture(var0, var1, var2);
}

@JvmOverloads
public fun afterOnServer(ticks: Int = 0, seconds: Float = 0.0F, action: () -> Unit): ScheduledTask {
   return ServerTaskTracker.INSTANCE.after(seconds + (float)ticks / 20.0F, action);
}

@JvmSynthetic
fun `afterOnServer$default`(var0: Int, var1: Float, var2: Function0, var3: Int, var4: Any): ScheduledTask {
   if ((var3 and 1) != 0) {
      var0 = 0;
   }

   if ((var3 and 2) != 0) {
      var1 = 0.0F;
   }

   return afterOnServer(var0, var1, var2);
}

@JvmOverloads
public fun afterOnClient(ticks: Int = 0, seconds: Float, action: () -> Unit): ScheduledTask {
   return ClientTaskTracker.INSTANCE.after(seconds + (float)ticks / 20.0F, action);
}

@JvmSynthetic
fun `afterOnClient$default`(var0: Int, var1: Float, var2: Function0, var3: Int, var4: Any): ScheduledTask {
   if ((var3 and 1) != 0) {
      var0 = 0;
   }

   return afterOnClient(var0, var1, var2);
}

@Deprecated(message = "Use lerpOnServer or lerpOnClient, side-ambiguity causes problems now")
public fun lerp(seconds: Float = 0.0F, serverThread: Boolean = false, action: (Float) -> Unit): ScheduledTask {
   return ((if (serverThread) ServerTaskTracker.INSTANCE else ClientTaskTracker.INSTANCE) as Schedulable).lerp(seconds, action);
}

/** @deprecated */
@JvmSynthetic
fun `lerp$default`(var0: Float, var1: Boolean, var2: Function1, var3: Int, var4: Any): ScheduledTask {
   if ((var3 and 1) != 0) {
      var0 = 0.0F;
   }

   if ((var3 and 2) != 0) {
      var1 = false;
   }

   return lerp(var0, var1, var2);
}

@JvmOverloads
public fun lerpOnServer(seconds: Float = 0.0F, action: (Float) -> Unit): ScheduledTask {
   return ServerTaskTracker.INSTANCE.lerp(seconds, action);
}

@JvmSynthetic
fun `lerpOnServer$default`(var0: Float, var1: Function1, var2: Int, var3: Any): ScheduledTask {
   if ((var2 and 1) != 0) {
      var0 = 0.0F;
   }

   return lerpOnServer(var0, var1);
}

@JvmOverloads
public fun lerpOnClient(seconds: Float = 0.0F, action: (Float) -> Unit): ScheduledTask {
   return ClientTaskTracker.INSTANCE.lerp(seconds, action);
}

@JvmSynthetic
fun `lerpOnClient$default`(var0: Float, var1: Function1, var2: Int, var3: Any): ScheduledTask {
   if ((var2 and 1) != 0) {
      var0 = 0.0F;
   }

   return lerpOnClient(var0, var1);
}

public fun taskBuilder(): Builder {
   return new ScheduledTask.Builder();
}

/** @deprecated */
@Deprecated(message = "Use afterOnServer or afterOnClient; ambiguous side is not good for your health")
@JvmOverloads
fun after(ticks: Int, seconds: Float, action: () -> Unit) {
   after$default(ticks, seconds, false, action, 4, null);
}

/** @deprecated */
@Deprecated(message = "Use afterOnServer or afterOnClient; ambiguous side is not good for your health")
@JvmOverloads
fun after(ticks: Int, action: () -> Unit) {
   after$default(ticks, 0.0F, false, action, 6, null);
}

/** @deprecated */
@Deprecated(message = "Use afterOnServer or afterOnClient; ambiguous side is not good for your health")
@JvmOverloads
fun after(action: () -> Unit) {
   after$default(0, 0.0F, false, action, 7, null);
}

@JvmOverloads
fun afterOnServer(ticks: Int, action: () -> Unit): ScheduledTask {
   return afterOnServer$default(ticks, 0.0F, action, 2, null);
}

@JvmOverloads
fun afterOnServer(action: () -> Unit): ScheduledTask {
   return afterOnServer$default(0, 0.0F, action, 3, null);
}

@JvmOverloads
fun afterOnClient(seconds: Float, action: () -> Unit): ScheduledTask {
   return afterOnClient$default(0, seconds, action, 1, null);
}

@JvmOverloads
fun lerpOnServer(action: (java.lang.Float?) -> Unit): ScheduledTask {
   return lerpOnServer$default(0.0F, action, 1, null);
}

@JvmOverloads
fun lerpOnClient(action: (java.lang.Float?) -> Unit): ScheduledTask {
   return lerpOnClient$default(0.0F, action, 1, null);
}
