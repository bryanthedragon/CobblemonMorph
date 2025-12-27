package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.phys.Vec3

public class StaticParticleMotion : ParticleMotion {
   public open val type: ParticleMotionType = ParticleMotionType.STATIC

   public override fun getInitialVelocity(runtime: MoLangRuntime, storm: ParticleStorm, particlePos: Vec3, emitterPos: Vec3): Vec3 {
      return Vec3.f_82478_;
   }

   public override fun getVelocity(runtime: MoLangRuntime, particle: SnowstormParticle, velocity: Vec3): Vec3 {
      return velocity;
   }

   public override fun getParticleDirection(runtime: MoLangRuntime, storm: ParticleStorm, velocity: Vec3, minSpeed: Float): Vec3 {
      return velocity.m_82541_();
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$0`(it: StaticParticleMotion): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$1`(it: java.lang.String): StaticParticleMotion {
      return new StaticParticleMotion();
   }

   @JvmStatic
   fun `CODEC$lambda$2`(instance: Instance): App {
      return instance.group(PrimitiveCodec.STRING.fieldOf("type").forGetter(StaticParticleMotion::CODEC$lambda$2$lambda$0) as App)
         .apply(instance as Applicative, StaticParticleMotion::CODEC$lambda$2$lambda$1);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(StaticParticleMotion::CODEC$lambda$2);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<StaticParticleMotion>
   }
}
