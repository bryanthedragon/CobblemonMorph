package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import net.minecraft.resources.ResourceLocation

public class EventParticleEffect(effect: ResourceLocation,
   type: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EventParticleEffect.EventParticleType,
   expression: ExpressionLike? = null
) {
   public final val effect: ResourceLocation
   public final val expression: ExpressionLike?
   public final val type: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EventParticleEffect.EventParticleType

   init {
      this.effect = effect;
      this.type = type;
      this.expression = expression;
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$0`(it: EventParticleEffect): ResourceLocation {
      return it.effect;
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$1`(it: EventParticleEffect): java.lang.String {
      return it.type.name();
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$2`(it: EventParticleEffect): java.lang.String {
      return if (it.expression != null) it.expression.toString() else null;
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$3`(effect: ResourceLocation, type: java.lang.String, expression: java.lang.String): EventParticleEffect {
      return new EventParticleEffect(
         effect, EventParticleEffect.EventParticleType.valueOf(type), if (expression != null) MoLangExtensionsKt.asExpressionLike(expression) else null
      );
   }

   @JvmStatic
   fun `CODEC$lambda$4`(instance: Instance): App {
      return instance.group(
            ResourceLocation.f_135803_.fieldOf("effect").forGetter(EventParticleEffect::CODEC$lambda$4$lambda$0) as App,
            PrimitiveCodec.STRING.fieldOf("type").forGetter(EventParticleEffect::CODEC$lambda$4$lambda$1) as App,
            PrimitiveCodec.STRING.optionalFieldOf("expression", null).forGetter(EventParticleEffect::CODEC$lambda$4$lambda$2) as App
         )
         .apply(instance as Applicative, EventParticleEffect::CODEC$lambda$4$lambda$3);
   }

   public companion object {
      public final val CODEC: Codec<EventParticleEffect>
   }

   public enum EventParticleType {
      EMITTER,
      EMITTER_BOUND,
      PARTICLE,
      PARTICLE_WITH_VELOCITY   }
}
