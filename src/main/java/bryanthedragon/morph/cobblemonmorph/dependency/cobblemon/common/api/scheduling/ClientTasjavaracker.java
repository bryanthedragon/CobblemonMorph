package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling

public object ClientTaskTracker : SchedulingTracker, Schedulable {
   public open val schedulingTracker: ClientTaskTracker = INSTANCE

   override fun momentarily(action: () -> Unit): ScheduledTask {
      return Schedulable.DefaultImpls.momentarily(this, action);
   }

   override fun after(seconds: Float, action: () -> Unit): ScheduledTask {
      return Schedulable.DefaultImpls.after(this, seconds, action);
   }

   override fun lerp(seconds: Float, action: (java.lang.Float?) -> Unit): ScheduledTask {
      return Schedulable.DefaultImpls.lerp(this, seconds, action);
   }

   override fun taskBuilder(): ScheduledTask.Builder {
      return Schedulable.DefaultImpls.taskBuilder(this);
   }
}
