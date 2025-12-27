package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.ast.NumberExpression
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.Matrix4fExtensionsKt
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

public class CustomMotionDirection(direction: Triple<Expression, Expression, Expression> = new Triple(
            new NumberExpression(0.0), new NumberExpression(0.0), new NumberExpression(0.0)
         )
   ) :
   ParticleMotionDirection {
   public final var direction: Triple<Expression, Expression, Expression>
   public open val type: ParticleMotionDirectionType

   init {
      this.direction = direction;
      this.type = ParticleMotionDirectionType.CUSTOM;
   }

   public override fun getDirectionVector(runtime: MoLangRuntime, storm: ParticleStorm, emitterPos: Vec3, particlePos: Vec3): Vec3 {
      return Matrix4fExtensionsKt.transformDirection(
         storm.getMatrixWrapper().getMatrix(),
         new Vec3(
            MoLangExtensionsKt.resolveDouble(runtime, this.direction.getFirst() as Expression),
            MoLangExtensionsKt.resolveDouble(runtime, this.direction.getSecond() as Expression),
            MoLangExtensionsKt.resolveDouble(runtime, this.direction.getThird() as Expression)
         )
      );
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      this.direction = new Triple(
         MoLang.createParser(buffer.m_130277_()).parseExpression(),
         MoLang.createParser(buffer.m_130277_()).parseExpression(),
         MoLang.createParser(buffer.m_130277_()).parseExpression()
      );
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.direction.getFirst() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.direction.getSecond() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.direction.getThird() as Expression));
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$0`(it: CustomMotionDirection): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$1`(it: CustomMotionDirection): Expression {
      return it.direction.getFirst() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$2`(it: CustomMotionDirection): Expression {
      return it.direction.getSecond() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$3`(it: CustomMotionDirection): Expression {
      return it.direction.getThird() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$4`(var0: java.lang.String, dirX: Expression, dirY: Expression, dirZ: Expression): CustomMotionDirection {
      return new CustomMotionDirection(new Triple(dirX, dirY, dirZ));
   }

   @JvmStatic
   fun `CODEC$lambda$5`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(CustomMotionDirection::CODEC$lambda$5$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("directionX").forGetter(CustomMotionDirection::CODEC$lambda$5$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("directionY").forGetter(CustomMotionDirection::CODEC$lambda$5$lambda$2) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("directionZ").forGetter(CustomMotionDirection::CODEC$lambda$5$lambda$3) as App
         )
         .apply(instance as Applicative, CustomMotionDirection::CODEC$lambda$5$lambda$4);
   }

   fun CustomMotionDirection() {
      this(null, 1, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(CustomMotionDirection::CODEC$lambda$5);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<CustomMotionDirection>
   }
}
