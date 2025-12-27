package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.effect

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.SpawnSnowstormParticlePacket
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import org.joml.Matrix4f

public object SpawnSnowstormParticleHandler : ClientNetworkPacketHandler<SpawnSnowstormParticlePacket> {
   public open fun handle(packet: SpawnSnowstormParticlePacket, client: Minecraft) {
      val wrapper: MatrixWrapper = new MatrixWrapper();
      val matrix: PoseStack = new PoseStack();
      matrix.m_85837_(packet.getPosition().f_82479_, packet.getPosition().f_82480_, packet.getPosition().f_82481_);
      val var10001: Matrix4f = matrix.m_85850_().m_252922_();
      wrapper.updateMatrix(var10001);
      val var10000: ClientLevel = Minecraft.m_91087_().f_91073_;
      if (var10000 != null) {
         val var7: BedrockParticleEffect = BedrockParticleEffectRepository.INSTANCE.getEffect(packet.getEffectId());
         if (var7 != null) {
            new ParticleStorm(var7, wrapper, var10000, null, null, null, null, null, null, 504, null).spawn();
         }
      }
   }

   fun handleOnNettyThread(packet: SpawnSnowstormParticlePacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
