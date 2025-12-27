package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.phys.Vec3

public class CommandDropEntry : DropEntry {
   public final val command: String = ""
   public open val maxSelectableTimes: Int = 1
   public open val percentage: Float = 100.0F
   public open val quantity: Int = 1
   public final val requiresPlayer: Boolean = true

   public override fun drop(entity: LivingEntity?, world: ServerLevel, pos: Vec3, player: ServerPlayer?) {
      if (!this.requiresPlayer || player != null) {
         var var5: java.lang.String;
         var var10000: Commands;
         var var10001: CommandSourceStack;
         label21: {
            var10000 = world.m_7654_().m_129892_();
            var10001 = world.m_7654_().m_129893_();
            if (player != null) {
               val var10004: Component = player.m_7755_();
               if (var10004 != null) {
                  var5 = var10004.getString();
                  break label21;
               }
            }

            var5 = null;
         }

         if (var5 == null) {
            var5 = "";
         }

         var10000.m_230957_(
            var10001,
            MiscUtilsKt.substitute(
               MiscUtilsKt.substitute(
                  MiscUtilsKt.substitute(
                     MiscUtilsKt.substitute(MiscUtilsKt.substitute(this.command, "player", var5), "world", world.m_46472_().m_135782_()), "x", pos.f_82479_
                  ),
                  "y",
                  pos.f_82480_
               ),
               "z",
               pos.f_82481_
            )
         );
      }
   }
}
