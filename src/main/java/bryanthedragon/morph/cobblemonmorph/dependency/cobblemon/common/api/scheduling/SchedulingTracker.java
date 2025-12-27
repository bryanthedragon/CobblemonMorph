package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling

import java.util.ArrayList;

public open class SchedulingTracker {
   private final val tasks: MutableList<ScheduledTask> = (new ArrayList()) as java.util.List

   public fun clear() {
      this.tasks.clear();
   }

   public fun update(deltaSeconds: Float) {
      for (ScheduledTask task : CollectionsKt.toList(this.tasks)) {
         task.update(deltaSeconds);
         if (task.getExpired()) {
            this.tasks.remove(task);
         }
      }
   }

   public fun addTask(task: ScheduledTask): ScheduledTask {
      this.tasks.add(task);
      return task;
   }
}
