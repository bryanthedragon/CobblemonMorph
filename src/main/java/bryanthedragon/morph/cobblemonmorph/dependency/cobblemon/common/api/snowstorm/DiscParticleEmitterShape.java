package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.ast.NumberExpression
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.joml.Matrix3f

@SourceDebugExtension(["SMAP\nParticleEmitterShape.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParticleEmitterShape.kt\ncom/cobblemon/mod/common/api/snowstorm/DiscParticleEmitterShape\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,384:1\n1#2:385\n*E\n"])
public class DiscParticleEmitterShape(offset: Triple<Expression, Expression, Expression> = new Triple(
            new NumberExpression(0.0), new NumberExpression(0.0), new NumberExpression(0.0)
         ),
      radius: Expression = MoLangExtensionsKt.asExpression(0.0) as Expression,
      normal: Triple<Expression, Expression, Expression> = new Triple(new NumberExpression(0.0), new NumberExpression(1.0), new NumberExpression(0.0)),
      surfaceOnly: Boolean = false
   ) :
   ParticleEmitterShape {
   public final var normal: Triple<Expression, Expression, Expression>
   public final var offset: Triple<Expression, Expression, Expression>
   public final var radius: Expression
   public final var surfaceOnly: Boolean
   public open val type: ParticleEmitterShapeType

   init {
      this.offset = offset;
      this.radius = radius;
      this.normal = normal;
      this.surfaceOnly = surfaceOnly;
      this.type = ParticleEmitterShapeType.DISC;
   }

   public override fun getNewParticlePosition(runtime: MoLangRuntime, entity: Entity?): Vec3 {
      val center: Vec3 = this.getCenter(runtime, entity);
      val radius: Vec3 = MoLangExtensionsKt.resolveVec3d(runtime, this.normal);
      val normal: Vec3 = (if (radius == Vec3.f_82478_) new Vec3(0.0, 1.0, 0.0) else radius).m_82541_();
      val baseLine: Vec3 = new Vec3(0.0, 1.0, 0.0);
      val var18: Double = MoLangExtensionsKt.resolveDouble(runtime, this.radius);
      val rotation: Matrix3f = SimpleMathExtensionsKt.getRotationMatrix(baseLine, normal);
      val distance: Double = if (this.surfaceOnly) var18 else Random.Default.nextDouble(var18);
      val theta: Double = Random.Default.nextDouble() * 2 * Math.PI;
      val var10000: Vec3 = center.m_82549_(SimpleMathExtensionsKt.times(rotation, new Vec3(distance * Math.cos(theta), 0.0, distance * Math.sin(theta))));
      return var10000;
   }

   public override fun getCenter(runtime: MoLangRuntime, entity: Entity?): Vec3 {
      return MoLangExtensionsKt.resolveVec3d(runtime, this.offset);
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
      val var10001: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.radius = var10001;
      this.normal = new Triple(
         MoLang.createParser(buffer.m_130277_()).parseExpression(),
         MoLang.createParser(buffer.m_130277_()).parseExpression(),
         MoLang.createParser(buffer.m_130277_()).parseExpression()
      );
      this.surfaceOnly = buffer.readBoolean();
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.offset.getFirst() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.offset.getSecond() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.offset.getThird() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.radius));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.normal.getFirst() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.normal.getSecond() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.normal.getThird() as Expression));
      buffer.writeBoolean(this.surfaceOnly);
   }

   @JvmStatic
   fun `CODEC$lambda$11$lambda$1`(it: DiscParticleEmitterShape): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$11$lambda$2`(it: DiscParticleEmitterShape): Expression {
      return it.offset.getFirst() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$11$lambda$3`(it: DiscParticleEmitterShape): Expression {
      return it.offset.getSecond() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$11$lambda$4`(it: DiscParticleEmitterShape): Expression {
      return it.offset.getThird() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$11$lambda$5`(it: DiscParticleEmitterShape): Expression {
      return it.radius;
   }

   @JvmStatic
   fun `CODEC$lambda$11$lambda$6`(it: DiscParticleEmitterShape): Expression {
      return it.normal.getFirst() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$11$lambda$7`(it: DiscParticleEmitterShape): Expression {
      return it.normal.getSecond() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$11$lambda$8`(it: DiscParticleEmitterShape): Expression {
      return it.normal.getThird() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$11$lambda$9`(it: DiscParticleEmitterShape): java.lang.Boolean {
      return it.surfaceOnly;
   }

   @JvmStatic
   fun `CODEC$lambda$11$lambda$10`(
      var0: java.lang.String,
      offsetX: Expression,
      offsetY: Expression,
      offsetZ: Expression,
      radius: Expression,
      normalX: Expression,
      normalY: Expression,
      normalZ: Expression,
      surfaceOnly: java.lang.Boolean
   ): DiscParticleEmitterShape {
      val var10002: Triple = new Triple(offsetX, offsetY, offsetZ);
      val var10004: Triple = new Triple(normalX, normalY, normalZ);
      return new DiscParticleEmitterShape(var10002, radius, var10004, surfaceOnly);
   }

   @JvmStatic
   fun `CODEC$lambda$11`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetX").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$2) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetY").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$3) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetZ").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$4) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("radius").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$5) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("normalX").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$6) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("normalY").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$7) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("normalZ").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$8) as App,
            PrimitiveCodec.BOOL.fieldOf("surfaceOnly").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$9) as App
         )
         .apply(instance as Applicative, DiscParticleEmitterShape::CODEC$lambda$11$lambda$10);
   }

   fun DiscParticleEmitterShape() {
      this(null, null, null, false, 15, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(DiscParticleEmitterShape::CODEC$lambda$11);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<DiscParticleEmitterShape>
   }
}
