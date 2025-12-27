package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService
import java.util.LinkedList
import java.util.Queue
import java.util.concurrent.CountDownLatch
import kotlin.jvm.functions.Function1

public class ShowdownThread : Thread("Cobblemon Showdown") {
   private final val latch: CountDownLatch = new CountDownLatch(1)
   private final val whenReady: Queue<(ShowdownService) -> Unit> = (new LinkedList()) as Queue

   public fun launch() {
      this.start();
      this.latch.await();

      for (Function1 action : this.whenReady) {
         action.invoke(ShowdownService.Companion.getService());
      }
   }

   public fun queue(action: (ShowdownService) -> Unit) {
      if (this.latch.getCount() == 0L) {
         action.invoke(ShowdownService.Companion.getService());
      } else {
         this.whenReady.add(action);
      }
   }

   public override fun run() {
      Cobblemon.INSTANCE.getLOGGER().info("Starting showdown service...");
      ShowdownService.Companion.getService().openConnection();
      Cobblemon.INSTANCE.getLOGGER().info("Showdown has been started!");
      this.latch.countDown();
   }
}
