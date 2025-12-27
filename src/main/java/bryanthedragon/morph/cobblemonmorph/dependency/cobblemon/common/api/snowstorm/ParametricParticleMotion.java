package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.ast.NumberExpression
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt
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

public class ParametricParticleMotion(offset: Triple<Expression, Expression, Expression> = new Triple(
            new NumberExpression(0.0), new NumberExpression(0.0), new NumberExpression(0.0)
         ),
      direction: Triple<Expression, Expression, Expression> = new Triple(new NumberExpression(0.0), new NumberExpression(0.0), new NumberExpression(0.0))
   ) :
   ParticleMotion {
   public final var direction: Triple<Expression, Expression, Expression>
   public final var offset: Triple<Expression, Expression, Expression>
   public open val type: ParticleMotionType

   init {
      this.offset = offset;
      this.direction = direction;
      this.type = ParticleMotionType.PARAMETRIC;
   }

   public override fun getInitialVelocity(runtime: MoLangRuntime, storm: ParticleStorm, particlePos: Vec3, emitterPos: Vec3): Vec3 {
      return Vec3.f_82478_;
   }

   public override fun getVelocity(runtime: MoLangRuntime, particle: SnowstormParticle, velocity: Vec3): Vec3 {
      val var10000: Vec3 = new Vec3(particle.getStorm().getX(), particle.getStorm().getY(), particle.getStorm().getZ())
         .m_82549_(MoLangExtensionsKt.resolveVec3d(runtime, this.offset))
         .m_82546_(new Vec3(particle.getX(), particle.getY(), particle.getZ()));
      return var10000;
   }

   public override fun getParticleDirection(runtime: MoLangRuntime, storm: ParticleStorm, velocity: Vec3, minSpeed: Float): Vec3 {
      return MoLangExtensionsKt.resolveVec3d(runtime, this.direction).m_82541_();
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      this.offset = new Triple(
         MoLang.createParser(buffer.m_130277_()).parseExpression(),
         MoLang.createParser(buffer.m_130277_()).parseExpression(),
         MoLang.createParser(buffer.m_130277_()).parseExpression()
      );
      this.direction = new Triple(
         MoLang.createParser(buffer.m_130277_()).parseExpression(),
         MoLang.createParser(buffer.m_130277_()).parseExpression(),
         MoLang.createParser(buffer.m_130277_()).parseExpression()
      );
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.offset.getFirst() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.offset.getSecond() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.offset.getThird() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.direction.getFirst() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.direction.getSecond() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.direction.getThird() as Expression));
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$0`(it: ParametricParticleMotion): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$1`(it: ParametricParticleMotion): java.util.List {
      return CollectionsKt.listOf(new Expression[]{(Expression)it.offset.getFirst(), (Expression)it.offset.getSecond(), (Expression)it.offset.getThird()});
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$2`(it: ParametricParticleMotion): java.util.List {
      return CollectionsKt.listOf(
         new Expression[]{(Expression)it.direction.getFirst(), (Expression)it.direction.getSecond(), (Expression)it.direction.getThird()}
      );
   }

   @JvmStatic
   fun `CODEC$lambda$4$lambda$3`(var0: java.lang.String, offset: java.util.List, direction: java.util.List): ParametricParticleMotion {
      return new ParametricParticleMotion(
         new Triple(offset.get(0), offset.get(1), offset.get(2)), new Triple(direction.get(0), direction.get(1), direction.get(2))
      );
   }

   @JvmStatic
   fun `CODEC$lambda$4`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(ParametricParticleMotion::CODEC$lambda$4$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().listOf().fieldOf("offset").forGetter(ParametricParticleMotion::CODEC$lambda$4$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().listOf().fieldOf("direction").forGetter(ParametricParticleMotion::CODEC$lambda$4$lambda$2) as App
         )
         .apply(instance as Applicative, ParametricParticleMotion::CODEC$lambda$4$lambda$3);
   }

   fun ParametricParticleMotion() {
      this(null, null, 3, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(ParametricParticleMotion::CODEC$lambda$4);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<ParametricParticleMotion>
   }
}
