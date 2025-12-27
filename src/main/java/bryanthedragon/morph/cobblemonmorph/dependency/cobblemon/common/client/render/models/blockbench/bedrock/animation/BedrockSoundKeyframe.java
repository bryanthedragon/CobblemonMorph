package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.Entity

public class BedrockSoundKeyframe(seconds: Float, sound: ResourceLocation) : BedrockEffectKeyframe(seconds) {
   public final val sound: ResourceLocation

   init {
      this.sound = sound;
   }

   public override fun <T : Entity> run(entity: Any, state: PoseableEntityState<Any>) {
      val soundEvent: SoundEvent = SoundEvent.m_262824_(this.sound);
      if (soundEvent != null) {
         Minecraft.m_91087_()
            .m_91106_()
            .m_120367_(
               (
                  new SimpleSoundInstance(
                     soundEvent, SoundSource.NEUTRAL, 1.0F, 1.0F, entity.m_9236_().f_46441_, entity.m_20185_(), entity.m_20186_(), entity.m_20189_()
                  )
               ) as SoundInstance
            );
      }
   }
}
