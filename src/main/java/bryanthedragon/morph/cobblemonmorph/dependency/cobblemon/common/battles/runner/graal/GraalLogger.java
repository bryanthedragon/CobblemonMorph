package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.graal

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogRecord

public object GraalLogger : Handler {
   public override fun publish(record: LogRecord?) {
      if (record != null) {
         val var2: Level = record.getLevel();
         if (var2 == Level.INFO) {
            Cobblemon.INSTANCE.getLOGGER().info(record.getMessage());
         } else if (var2 == Level.WARNING) {
            Cobblemon.INSTANCE.getLOGGER().warn(record.getMessage());
         } else if (var2 == Level.SEVERE) {
            Cobblemon.INSTANCE.getLOGGER().error(record.getMessage());
         } else {
            Cobblemon.INSTANCE.getLOGGER().debug(record.getMessage());
         }
      }
   }

   public override fun flush() {
   }

   public override fun close() {
   }
}
