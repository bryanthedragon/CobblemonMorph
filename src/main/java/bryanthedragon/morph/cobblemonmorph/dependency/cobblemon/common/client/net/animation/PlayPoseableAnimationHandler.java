package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.animation

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.animation.PlayPoseableAnimationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.Entity

public object PlayPoseableAnimationHandler : ClientNetworkPacketHandler<PlayPoseableAnimationPacket> {
   public open fun handle(packet: PlayPoseableAnimationPacket, client: Minecraft) {
      if (client.f_91073_ != null) {
         val var10000: Entity = client.f_91073_.m_6815_(packet.getEntityId());
         if (var10000 != null) {
            if (var10000 is Poseable) {
               val delegate: EntitySideDelegate = (var10000 as Poseable).getDelegate();
               if (delegate is PoseableEntityState) {
                  for (java.lang.String expr : packet.getExpressions()) {
                     val var8: MoLangRuntime = (delegate as PoseableEntityState).getRuntime();
                     val var10001: Expression = MoLangExtensionsKt.asExpression(expr);
                     MoLangExtensionsKt.resolve(var8, var10001);
                  }

                  (delegate as PoseableEntityState).addFirstAnimation(packet.getAnimation());
               }
            }
         }
      }
   }

   fun handleOnNettyThread(packet: PlayPoseableAnimationPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
