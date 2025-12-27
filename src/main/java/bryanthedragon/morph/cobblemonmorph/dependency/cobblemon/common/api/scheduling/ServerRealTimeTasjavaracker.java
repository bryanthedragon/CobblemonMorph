package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling

public object ServerRealTimeTaskTracker : SchedulingTracker, Schedulable {
   public final var lastTicked: Long = System.currentTimeMillis()
   public open val schedulingTracker: ServerRealTimeTaskTracker = INSTANCE

   public fun update() {
      val now: Long = System.currentTimeMillis();
      val delta: Long = now - lastTicked;
      lastTicked = now;
      this.update((float)delta / 1000.0F);
   }

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
