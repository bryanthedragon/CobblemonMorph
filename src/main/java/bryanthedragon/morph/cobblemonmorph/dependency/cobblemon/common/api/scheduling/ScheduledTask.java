package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling

import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nScheduledTask.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ScheduledTask.kt\ncom/cobblemon/mod/common/api/scheduling/ScheduledTask\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,185:1\n1#2:186\n*E\n"])
public class ScheduledTask(action: (ScheduledTask) -> Unit,
   identifier: String? = null,
   delaySeconds: Float,
   intervalSeconds: Float = -1.0F,
   iterations: Int = 1
) {
   public final val action: (ScheduledTask) -> Unit

   public final var currentIteration: Int
      private set

   public final var expired: Boolean
      private set

   public final val future: CompletableFuture<Unit>
   public final val identifier: String?
   public final val intervalSeconds: Float
   public final val iterations: Int

   public final var paused: Boolean
      public final set(value) {
         this.paused = value;
      }


   public final var secondsPassed: Float

   public final var secondsRemaining: Float
      private set

   init {
      this.action = action;
      this.identifier = identifier;
      this.intervalSeconds = intervalSeconds;
      this.iterations = iterations;
      this.future = new CompletableFuture<>();
      if (delaySeconds > 0.0F) {
         this.secondsRemaining = delaySeconds;
      }
   }

   public override fun toString(): String {
      if (this.identifier != null) {
         val var10000: java.lang.String = "Task-${this.identifier}";
         if (var10000 != null) {
            return var10000;
         }
      }

      return super.toString();
   }

   public fun update(deltaSeconds: Float) {
      if (!this.expired && !this.paused) {
         this.secondsPassed += deltaSeconds;
         this.secondsRemaining = Math.max(0.0F, this.secondsRemaining - deltaSeconds);
         if (this.secondsRemaining == 0.0F) {
            this.action.invoke(this);
            val var2: Int = this.currentIteration++;
            if (this.intervalSeconds == -1.0F || this.currentIteration >= this.iterations && this.iterations != -1) {
               this.expired = true;
            } else {
               this.secondsRemaining = this.intervalSeconds;
            }
         }
      }
   }

   public fun expire() {
      this.expired = true;
      this.future.complete(Unit.INSTANCE);
   }

   @SourceDebugExtension(["SMAP\nScheduledTask.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ScheduledTask.kt\ncom/cobblemon/mod/common/api/scheduling/ScheduledTask$Builder\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,185:1\n1#2:186\n*E\n"])
   public class Builder {
      private final var action: ((ScheduledTask) -> Unit)?
      private final var delaySeconds: Float
      private final var identifier: String?
      private final var interval: Float = -1.0F
      private final var iterations: Int = 1
      private final var tracker: SchedulingTracker?

      public fun identifier(identifier: String): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask.Builder {
         this.identifier = identifier;
         return this;
      }

      public fun execute(action: (ScheduledTask) -> Unit): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask.Builder {
         this.action = action;
         return this;
      }

      public fun delay(delaySeconds: Float): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask.Builder {
         if (!(delaySeconds >= 0.0F)) {
            throw new IllegalArgumentException("Delay must not be below 0".toString());
         } else {
            this.delaySeconds = delaySeconds;
            return this;
         }
      }

      public fun interval(interval: Float): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask.Builder {
         if (!(interval >= 0.0F)) {
            throw new IllegalArgumentException("Interval must not be below 0".toString());
         } else {
            this.interval = interval;
            return this;
         }
      }

      public fun iterations(iterations: Int): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask.Builder {
         if (iterations < -1) {
            throw new IllegalArgumentException("Iterations must not be below -1".toString());
         } else {
            this.iterations = iterations;
            return this;
         }
      }

      public fun infiniteIterations(): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask.Builder {
         return this.iterations(-1);
      }

      public fun tracker(schedulingTracker: SchedulingTracker): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask.Builder {
         this.tracker = schedulingTracker;
         return this;
      }

      public fun build(): ScheduledTask {
         if (this.action == null) {
            throw new IllegalStateException("action must be set".toString());
         } else {
            val var10002: Function1 = this.action;
            val task: ScheduledTask = new ScheduledTask(var10002, this.identifier, this.delaySeconds, this.interval, this.iterations);
            var var10000: SchedulingTracker = this.tracker;
            if (this.tracker == null) {
               var10000 = ServerTaskTracker.INSTANCE;
            }

            var10000.addTask(task);
            return task;
         }
      }
   }

   public companion object {
      public final val BLANK: ScheduledTask
   }
}
