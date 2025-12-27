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

public class DynamicParticleMotion(direction: ParticleMotionDirection = (new InwardsMotionDirection()) as ParticleMotionDirection,
      speed: Expression = (new NumberExpression(0.0)) as Expression,
      acceleration: Triple<Expression, Expression, Expression> = new Triple(new NumberExpression(0.0), new NumberExpression(0.0), new NumberExpression(0.0)),
      drag: Expression = (new NumberExpression(0.0)) as Expression
   ) :
   ParticleMotion {
   public final var acceleration: Triple<Expression, Expression, Expression>
   public final var direction: ParticleMotionDirection
   public final var drag: Expression
   public final var speed: Expression
   public open val type: ParticleMotionType

   init {
      this.direction = direction;
      this.speed = speed;
      this.acceleration = acceleration;
      this.drag = drag;
      this.type = ParticleMotionType.DYNAMIC;
   }

   public override fun getInitialVelocity(runtime: MoLangRuntime, storm: ParticleStorm, particlePos: Vec3, emitterPos: Vec3): Vec3 {
      val var10000: Vec3 = this.direction
         .getDirectionVector(runtime, storm, emitterPos, particlePos)
         .m_82541_()
         .m_82490_(MoLangExtensionsKt.resolveDouble(runtime, this.speed));
      return var10000;
   }

   public override fun getVelocity(runtime: MoLangRuntime, particle: SnowstormParticle, velocity: Vec3): Vec3 {
      val acceleration: Vec3 = new Vec3(
            MoLangExtensionsKt.resolveDouble(runtime, this.acceleration.getFirst() as Expression),
            MoLangExtensionsKt.resolveDouble(runtime, this.acceleration.getSecond() as Expression),
            MoLangExtensionsKt.resolveDouble(runtime, this.acceleration.getThird() as Expression)
         )
         .m_82546_(velocity.m_82490_((double)20 * MoLangExtensionsKt.resolveDouble(runtime, this.drag)))
         .m_82490_(0.05)
         .m_82490_(0.05);
      return new Vec3(velocity.f_82479_ + acceleration.f_82479_, velocity.f_82480_ + acceleration.f_82480_, velocity.f_82481_ + acceleration.f_82481_);
   }

   public override fun getParticleDirection(runtime: MoLangRuntime, storm: ParticleStorm, velocity: Vec3, minSpeed: Float): Vec3 {
      return velocity.m_82541_();
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      this.direction = ParticleMotionDirection.Companion.readFromBuffer(buffer);
      var var10001: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.speed = var10001;
      this.acceleration = new Triple(
         MoLang.createParser(buffer.m_130277_()).parseExpression(),
         MoLang.createParser(buffer.m_130277_()).parseExpression(),
         MoLang.createParser(buffer.m_130277_()).parseExpression()
      );
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.drag = var10001;
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      ParticleMotionDirection.Companion.writeToBuffer(buffer, this.direction);
      buffer.m_130070_(MoLangExtensionsKt.getString(this.speed));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.acceleration.getFirst() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.acceleration.getSecond() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.acceleration.getThird() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.drag));
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$0`(it: DynamicParticleMotion): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$1`(it: DynamicParticleMotion): ParticleMotionDirection {
      return it.direction;
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$2`(it: DynamicParticleMotion): Expression {
      return it.speed;
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$3`(it: DynamicParticleMotion): Expression {
      return it.acceleration.getFirst() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$4`(it: DynamicParticleMotion): Expression {
      return it.acceleration.getSecond() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$5`(it: DynamicParticleMotion): Expression {
      return it.acceleration.getThird() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$6`(it: DynamicParticleMotion): Expression {
      return it.drag;
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$7`(
      var0: java.lang.String,
      direction: ParticleMotionDirection,
      speed: Expression,
      accelX: Expression,
      accelY: Expression,
      accelZ: Expression,
      drag: Expression
   ): DynamicParticleMotion {
      val var10004: Triple = new Triple(accelX, accelY, accelZ);
      return new DynamicParticleMotion(direction, speed, var10004, drag);
   }

   @JvmStatic
   fun `CODEC$lambda$8`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(DynamicParticleMotion::CODEC$lambda$8$lambda$0) as App,
            ParticleMotionDirection.Companion.getCodec().fieldOf("direction").forGetter(DynamicParticleMotion::CODEC$lambda$8$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("speed").forGetter(DynamicParticleMotion::CODEC$lambda$8$lambda$2) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("accelX").forGetter(DynamicParticleMotion::CODEC$lambda$8$lambda$3) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("accelY").forGetter(DynamicParticleMotion::CODEC$lambda$8$lambda$4) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("accelZ").forGetter(DynamicParticleMotion::CODEC$lambda$8$lambda$5) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("drag").forGetter(DynamicParticleMotion::CODEC$lambda$8$lambda$6) as App
         )
         .apply(instance as Applicative, DynamicParticleMotion::CODEC$lambda$8$lambda$7);
   }

   fun DynamicParticleMotion() {
      this(null, null, null, null, 15, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(DynamicParticleMotion::CODEC$lambda$8);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<DynamicParticleMotion>
   }
}
