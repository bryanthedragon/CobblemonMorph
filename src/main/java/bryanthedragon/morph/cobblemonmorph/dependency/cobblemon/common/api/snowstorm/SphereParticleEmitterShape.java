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
import kotlin.random.Random
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3

public class SphereParticleEmitterShape(offset: Triple<Expression, Expression, Expression> = new Triple(
            new NumberExpression(0.0), new NumberExpression(0.0), new NumberExpression(0.0)
         ),
      radius: Expression = (new NumberExpression(0.0)) as Expression,
      surfaceOnly: Boolean = false
   ) :
   ParticleEmitterShape {
   public final var offset: Triple<Expression, Expression, Expression>
   public final var radius: Expression
   public final var surfaceOnly: Boolean
   public open val type: ParticleEmitterShapeType

   init {
      this.offset = offset;
      this.radius = radius;
      this.surfaceOnly = surfaceOnly;
      this.type = ParticleEmitterShapeType.SPHERE;
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      val var10000: DataResult = CODEC.encodeStart(ops, this);
      return var10000;
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      this.offset = new Triple(
         MoLang.createParser(buffer.m_130277_()).parseExpression(),
         MoLang.createParser(buffer.m_130277_()).parseExpression(),
         MoLang.createParser(buffer.m_130277_()).parseExpression()
      );
      val var10001: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.radius = var10001;
      this.surfaceOnly = buffer.readBoolean();
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.offset.getFirst() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.offset.getSecond() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.offset.getThird() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.radius));
      buffer.writeBoolean(this.surfaceOnly);
   }

   public override fun getCenter(runtime: MoLangRuntime, entity: Entity?): Vec3 {
      return MoLangExtensionsKt.resolveVec3d(runtime, this.offset);
   }

   public override fun getNewParticlePosition(runtime: MoLangRuntime, entity: Entity?): Vec3 {
      val radius: Double = MoLangExtensionsKt.resolveDouble(runtime, this.radius) * (if (this.surfaceOnly) 1.0 else Random.Default.nextDouble());
      val var10000: Vec3 = this.getCenter(runtime, entity)
         .m_82549_(
            SimpleMathExtensionsKt.convertSphericalToCartesian(radius, (Math.PI * 2) * Random.Default.nextDouble(), (Math.PI * 2) * Random.Default.nextDouble())
         );
      return var10000;
   }

   @JvmStatic
   fun `CODEC$lambda$7$lambda$0`(it: SphereParticleEmitterShape): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$7$lambda$1`(it: SphereParticleEmitterShape): Expression {
      return it.offset.getFirst() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$7$lambda$2`(it: SphereParticleEmitterShape): Expression {
      return it.offset.getSecond() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$7$lambda$3`(it: SphereParticleEmitterShape): Expression {
      return it.offset.getThird() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$7$lambda$4`(it: SphereParticleEmitterShape): Expression {
      return it.radius;
   }

   @JvmStatic
   fun `CODEC$lambda$7$lambda$5`(it: SphereParticleEmitterShape): java.lang.Boolean {
      return it.surfaceOnly;
   }

   @JvmStatic
   fun `CODEC$lambda$7$lambda$6`(
      var0: java.lang.String, offsetX: Expression, offsetY: Expression, offsetZ: Expression, radius: Expression, surfaceOnly: java.lang.Boolean
   ): SphereParticleEmitterShape {
      val var10002: Triple = new Triple(offsetX, offsetY, offsetZ);
      return new SphereParticleEmitterShape(var10002, radius, surfaceOnly);
   }

   @JvmStatic
   fun `CODEC$lambda$7`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(SphereParticleEmitterShape::CODEC$lambda$7$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetX").forGetter(SphereParticleEmitterShape::CODEC$lambda$7$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetY").forGetter(SphereParticleEmitterShape::CODEC$lambda$7$lambda$2) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetZ").forGetter(SphereParticleEmitterShape::CODEC$lambda$7$lambda$3) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("radius").forGetter(SphereParticleEmitterShape::CODEC$lambda$7$lambda$4) as App,
            PrimitiveCodec.BOOL.fieldOf("surfaceOnly").forGetter(SphereParticleEmitterShape::CODEC$lambda$7$lambda$5) as App
         )
         .apply(instance as Applicative, SphereParticleEmitterShape::CODEC$lambda$7$lambda$6);
   }

   fun SphereParticleEmitterShape() {
      this(null, null, false, 7, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(SphereParticleEmitterShape::CODEC$lambda$7);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<SphereParticleEmitterShape>
   }
}
