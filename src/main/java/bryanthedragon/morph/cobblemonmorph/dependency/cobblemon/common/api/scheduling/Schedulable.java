package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask.Builder
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Ref.FloatRef
import org.jetbrains.annotations.NotNull

public interface Schedulable {
   public val schedulingTracker: SchedulingTracker

   public open fun momentarily(action: () -> Unit): ScheduledTask {
   }

   public open fun after(seconds: Float = ..., action: () -> Unit): ScheduledTask {
   }

   public open fun lerp(seconds: Float = ..., action: (Float) -> Unit): ScheduledTask {
   }

   public open fun taskBuilder(): Builder {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun momentarily(`$this`: Schedulable, action: () -> Unit): ScheduledTask {
         return after$default(`$this`, 0.0F, action, 1, null);
      }

      @JvmStatic
      fun after(`$this`: Schedulable, seconds: Float, action: () -> Unit): ScheduledTask {
         return `$this`.getSchedulingTracker().addTask(new ScheduledTask((new Function1<ScheduledTask, Unit>(action) {
            {
               super(1);
               this.$action = `$action`;
            }

            public final void invoke(@NotNull ScheduledTask it) {
               this.$action.invoke();
            }
         }) as Function1, null, seconds, 0.0F, 0, 26, null));
      }

      @JvmStatic
      fun lerp(`$this`: Schedulable, seconds: Float, action: (java.lang.Float?) -> Unit): ScheduledTask {
         val passed: FloatRef = new FloatRef();
         if (seconds == 0.0F) {
            action.invoke(1.0F);
            return ScheduledTask.Companion.getBLANK();
         } else {
            action.invoke(passed.element / seconds);
            return if (passed.element / seconds != 1.0F)
               `$this`.taskBuilder()
                  .tracker(`$this`.getSchedulingTracker())
                  .interval(0.0F)
                  .iterations(-1)
                  .execute((new Function1<ScheduledTask, Unit>(passed, seconds, action) {
                     {
                        super(1);
                        this.$passed = `$passed`;
                        this.$seconds = `$seconds`;
                        this.$action = `$action`;
                     }

                     public final void invoke(@NotNull ScheduledTask task) {
                        this.$passed.element = task.getSecondsPassed();
                        if (this.$passed.element > this.$seconds) {
                           this.$passed.element = this.$seconds;
                        }

                        this.$action.invoke(this.$passed.element / this.$seconds);
                        if (this.$passed.element >= this.$seconds) {
                           task.expire();
                        }
                     }
                  }) as (ScheduledTask?) -> Unit)
                  .build()
               else
               ScheduledTask.Companion.getBLANK();
         }
      }

      @JvmStatic
      fun taskBuilder(`$this`: Schedulable): ScheduledTask.Builder {
         return new ScheduledTask.Builder().tracker(`$this`.getSchedulingTracker());
      }
   }
}
