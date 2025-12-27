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
import kotlin.random.Random
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3

public class BoxParticleEmitterShape(offset: Triple<Expression, Expression, Expression> = new Triple(
            new NumberExpression(0.0), new NumberExpression(0.0), new NumberExpression(0.0)
         ),
      boxSize: Triple<Expression, Expression, Expression> = new Triple(new NumberExpression(1.0), new NumberExpression(1.0), new NumberExpression(1.0)),
      surfaceOnly: Boolean = false
   ) :
   ParticleEmitterShape {
   public final var boxSize: Triple<Expression, Expression, Expression>
   public final var offset: Triple<Expression, Expression, Expression>
   public final var surfaceOnly: Boolean
   public open val type: ParticleEmitterShapeType

   init {
      this.offset = offset;
      this.boxSize = boxSize;
      this.surfaceOnly = surfaceOnly;
      this.type = ParticleEmitterShapeType.BOX;
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
      this.boxSize = new Triple(
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
      buffer.m_130070_(MoLangExtensionsKt.getString(this.boxSize.getFirst() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.boxSize.getSecond() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.boxSize.getThird() as Expression));
      buffer.writeBoolean(this.surfaceOnly);
   }

   public override fun getCenter(runtime: MoLangRuntime, entity: Entity?): Vec3 {
      return MoLangExtensionsKt.resolveVec3d(runtime, this.offset);
   }

   public override fun getNewParticlePosition(runtime: MoLangRuntime, entity: Entity?): Vec3 {
      val center: Vec3 = this.getCenter(runtime, entity);
      val sizes: Vec3 = MoLangExtensionsKt.resolveVec3d(runtime, this.boxSize).m_82490_(2.0).m_82520_(1.0E-4, 1.0E-4, 1.0E-4);
      var var10000: Vec3;
      if (this.surfaceOnly) {
         switch (Random.Default.nextInt(6)) {
            case 0:
               var10000 = new Vec3(
                  -0.5 * sizes.f_82479_,
                  Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0,
                  Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0
               );
               break;
            case 1:
               var10000 = new Vec3(
                  0.5 * sizes.f_82479_,
                  Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0,
                  Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0
               );
               break;
            case 2:
               var10000 = new Vec3(
                  Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0,
                  -0.5 * sizes.f_82480_,
                  Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0
               );
               break;
            case 3:
               var10000 = new Vec3(
                  Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0,
                  0.5 * sizes.f_82480_,
                  Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0
               );
               break;
            case 4:
               var10000 = new Vec3(
                  Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0,
                  Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0,
                  -0.5 * sizes.f_82481_
               );
               break;
            default:
               var10000 = new Vec3(
                  Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0,
                  Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0,
                  0.5 * sizes.f_82481_
               );
         }
      } else {
         var10000 = new Vec3(
            Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2,
            Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2,
            Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2
         );
      }

      var10000 = center.m_82549_(var10000);
      return var10000;
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$0`(it: BoxParticleEmitterShape): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$1`(it: BoxParticleEmitterShape): Expression {
      return it.offset.getFirst() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$2`(it: BoxParticleEmitterShape): Expression {
      return it.offset.getSecond() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$3`(it: BoxParticleEmitterShape): Expression {
      return it.offset.getThird() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$4`(it: BoxParticleEmitterShape): Expression {
      return it.boxSize.getFirst() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$5`(it: BoxParticleEmitterShape): Expression {
      return it.boxSize.getSecond() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$6`(it: BoxParticleEmitterShape): Expression {
      return it.boxSize.getThird() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$7`(it: BoxParticleEmitterShape): java.lang.Boolean {
      return it.surfaceOnly;
   }

   @JvmStatic
   fun `CODEC$lambda$9$lambda$8`(
      var0: java.lang.String,
      offsetX: Expression,
      offsetY: Expression,
      offsetZ: Expression,
      boxX: Expression,
      boxY: Expression,
      boxZ: Expression,
      surfaceOnly: java.lang.Boolean
   ): BoxParticleEmitterShape {
      val var10002: Triple = new Triple(offsetX, offsetY, offsetZ);
      val var10003: Triple = new Triple(boxX, boxY, boxZ);
      return new BoxParticleEmitterShape(var10002, var10003, surfaceOnly);
   }

   @JvmStatic
   fun `CODEC$lambda$9`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetX").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetY").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$2) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetZ").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$3) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("sizeX").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$4) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("sizeY").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$5) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("sizeZ").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$6) as App,
            PrimitiveCodec.BOOL.fieldOf("surfaceOnly").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$7) as App
         )
         .apply(instance as Applicative, BoxParticleEmitterShape::CODEC$lambda$9$lambda$8);
   }

   fun BoxParticleEmitterShape() {
      this(null, null, false, 7, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(BoxParticleEmitterShape::CODEC$lambda$9);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<BoxParticleEmitterShape>
   }
}
