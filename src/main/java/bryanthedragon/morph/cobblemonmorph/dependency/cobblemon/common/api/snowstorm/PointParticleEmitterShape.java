package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.ast.NumberExpression
import com.bedrockk.molang.runtime.MoLangRuntime
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
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3

public class PointParticleEmitterShape(offset: Triple<Expression, Expression, Expression> = new Triple(
            new NumberExpression(0.0), new NumberExpression(0.0), new NumberExpression(0.0)
         )
   ) :
   ParticleEmitterShape {
   public final var offset: Triple<Expression, Expression, Expression>
   public open val type: ParticleEmitterShapeType

   init {
      this.offset = offset;
      this.type = ParticleEmitterShapeType.POINT;
   }

   public override fun getNewParticlePosition(runtime: MoLangRuntime, entity: Entity?): Vec3 {
      return MoLangExtensionsKt.resolveVec3d(runtime, this.offset);
   }

   public override fun getCenter(runtime: MoLangRuntime, entity: Entity?): Vec3 {
      val var10000: Vec3 = Vec3.f_82478_;
      return var10000;
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
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.offset.getFirst() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.offset.getSecond() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.offset.getThird() as Expression));
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$0`(it: PointParticleEmitterShape): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$1`(it: PointParticleEmitterShape): Expression {
      return it.offset.getFirst() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$2`(it: PointParticleEmitterShape): Expression {
      return it.offset.getSecond() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$3`(it: PointParticleEmitterShape): Expression {
      return it.offset.getThird() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$4`(var0: java.lang.String, offsetX: Expression, offsetY: Expression, offsetZ: Expression): PointParticleEmitterShape {
      return new PointParticleEmitterShape(new Triple(offsetX, offsetY, offsetZ));
   }

   @JvmStatic
   fun `CODEC$lambda$5`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(PointParticleEmitterShape::CODEC$lambda$5$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetX").forGetter(PointParticleEmitterShape::CODEC$lambda$5$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetY").forGetter(PointParticleEmitterShape::CODEC$lambda$5$lambda$2) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetZ").forGetter(PointParticleEmitterShape::CODEC$lambda$5$lambda$3) as App
         )
         .apply(instance as Applicative, PointParticleEmitterShape::CODEC$lambda$5$lambda$4);
   }

   fun PointParticleEmitterShape() {
      this(null, 1, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(PointParticleEmitterShape::CODEC$lambda$5);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<PointParticleEmitterShape>
   }
}
