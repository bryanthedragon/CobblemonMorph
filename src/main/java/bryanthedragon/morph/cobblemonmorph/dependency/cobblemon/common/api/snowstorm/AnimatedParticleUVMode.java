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

public class AnimatedParticleUVMode(startU: Expression = (new NumberExpression(0.0)) as Expression,
      startV: Expression = (new NumberExpression(0.0)) as Expression,
      textureSizeX: Int = 8,
      textureSizeY: Int = 8,
      uSize: Expression = (new NumberExpression(8.0)) as Expression,
      vSize: Expression = (new NumberExpression(8.0)) as Expression,
      stepU: Expression = (new NumberExpression(8.0)) as Expression,
      stepV: Expression = (new NumberExpression(0.0)) as Expression,
      maxFrame: Expression = (new NumberExpression(0.0)) as Expression,
      fps: Expression = (new NumberExpression(1.0)) as Expression,
      stretchToLifetime: Boolean = false,
      loop: Boolean = false
   )
   : ParticleUVMode {
   public final var fps: Expression
   public final var loop: Boolean
   public final var maxFrame: Expression
   public open var startU: Expression
   public open var startV: Expression
   public final var stepU: Expression
   public final var stepV: Expression
   public final var stretchToLifetime: Boolean
   public open var textureSizeX: Int
   public open var textureSizeY: Int
   public open val type: ParticleUVModeType
   public open var uSize: Expression
   public open var vSize: Expression

   init {
      this.startU = startU;
      this.startV = startV;
      this.textureSizeX = textureSizeX;
      this.textureSizeY = textureSizeY;
      this.uSize = uSize;
      this.vSize = vSize;
      this.stepU = stepU;
      this.stepV = stepV;
      this.maxFrame = maxFrame;
      this.fps = fps;
      this.stretchToLifetime = stretchToLifetime;
      this.loop = loop;
      this.type = ParticleUVModeType.ANIMATED;
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      val var10000: DataResult = CODEC.encodeStart(ops, this);
      return var10000;
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      var var10001: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.setStartU(var10001);
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.setStartV(var10001);
      this.setTextureSizeX(buffer.readInt());
      this.setTextureSizeY(buffer.readInt());
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.setUSize(var10001);
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.setVSize(var10001);
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.stepU = var10001;
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.stepV = var10001;
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.maxFrame = var10001;
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.fps = var10001;
      this.stretchToLifetime = buffer.readBoolean();
      this.loop = buffer.readBoolean();
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.getStartU()));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.getStartV()));
      buffer.writeInt(this.getTextureSizeX());
      buffer.writeInt(this.getTextureSizeY());
      buffer.m_130070_(MoLangExtensionsKt.getString(this.getUSize()));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.getVSize()));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.stepU));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.stepV));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.maxFrame));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.fps));
      buffer.writeBoolean(this.stretchToLifetime);
      buffer.writeBoolean(this.loop);
   }

   public override fun get(runtime: MoLangRuntime, age: Double, maxAge: Double, uvDetails: UVDetails): UVDetails {
      val maxFrame: Int = MoLangExtensionsKt.resolveInt(runtime, this.maxFrame) - 1;
      val stepU: Double = MoLangExtensionsKt.resolveDouble(runtime, this.stepU);
      val stepV: Double = MoLangExtensionsKt.resolveDouble(runtime, this.stepV);
      val uSize: Double = MoLangExtensionsKt.resolveDouble(runtime, this.getUSize());
      val vSize: Double = MoLangExtensionsKt.resolveDouble(runtime, this.getVSize());
      if (this.stretchToLifetime) {
         val startU: Double = MoLangExtensionsKt.resolveDouble(runtime, this.getStartU()) + (int)(age / maxAge * maxFrame) * stepU;
         val var25: Double = MoLangExtensionsKt.resolveDouble(runtime, this.getStartV()) + (int)(age / maxAge * maxFrame) * stepV;
         return uvDetails.set(
            startU / (double)this.getTextureSizeX(),
            var25 / (double)this.getTextureSizeY(),
            (startU + uSize) / (double)this.getTextureSizeX(),
            (var25 + vSize) / (double)this.getTextureSizeY()
         );
      } else {
         val fps: Double = MoLangExtensionsKt.resolveDouble(runtime, this.fps);
         val frame: Int = if (!this.loop && age * fps >= maxFrame) maxFrame else (int)(age * fps % maxFrame);
         val startU: Double = MoLangExtensionsKt.resolveDouble(runtime, this.getStartU()) + frame * stepU;
         val startV: Double = MoLangExtensionsKt.resolveDouble(runtime, this.getStartV()) + frame * stepV;
         return uvDetails.set(
            startU / (double)this.getTextureSizeX(),
            startV / (double)this.getTextureSizeY(),
            (startU + uSize) / (double)this.getTextureSizeX(),
            (startV + vSize) / (double)this.getTextureSizeY()
         );
      }
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$0`(it: AnimatedParticleUVMode): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$1`(it: AnimatedParticleUVMode): Expression {
      return it.getStartU();
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$2`(it: AnimatedParticleUVMode): Expression {
      return it.getStartV();
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$3`(it: AnimatedParticleUVMode): Int {
      return it.getTextureSizeX();
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$4`(it: AnimatedParticleUVMode): Int {
      return it.getTextureSizeY();
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$5`(it: AnimatedParticleUVMode): Expression {
      return it.getUSize();
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$6`(it: AnimatedParticleUVMode): Expression {
      return it.getVSize();
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$7`(it: AnimatedParticleUVMode): Expression {
      return it.stepU;
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$8`(it: AnimatedParticleUVMode): Expression {
      return it.stepV;
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$9`(it: AnimatedParticleUVMode): Expression {
      return it.maxFrame;
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$10`(it: AnimatedParticleUVMode): Expression {
      return it.fps;
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$11`(it: AnimatedParticleUVMode): java.lang.Boolean {
      return it.stretchToLifetime;
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$12`(it: AnimatedParticleUVMode): java.lang.Boolean {
      return it.loop;
   }

   @JvmStatic
   fun `CODEC$lambda$14$lambda$13`(
      var0: java.lang.String,
      startU: Expression,
      startV: Expression,
      textureSizeX: Int,
      textureSizeY: Int,
      uSize: Expression,
      vSize: Expression,
      stepU: Expression,
      stepV: Expression,
      maxFrame: Expression,
      fps: Expression,
      stretchToLifetime: java.lang.Boolean,
      loop: java.lang.Boolean
   ): AnimatedParticleUVMode {
      val var10004: Int = textureSizeX;
      val var10005: Int = textureSizeY;
      val var10012: Boolean = stretchToLifetime;
      return new AnimatedParticleUVMode(startU, startV, var10004, var10005, uSize, vSize, stepU, stepV, maxFrame, fps, var10012, loop);
   }

   @JvmStatic
   fun `CODEC$lambda$14`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("startU").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("startV").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$2) as App,
            PrimitiveCodec.INT.fieldOf("textureSizeX").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$3) as App,
            PrimitiveCodec.INT.fieldOf("textureSizeY").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$4) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("uSize").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$5) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("vSize").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$6) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("stepU").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$7) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("stepV").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$8) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("maxFrame").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$9) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("fps").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$10) as App,
            PrimitiveCodec.BOOL.fieldOf("stretchToLifetime").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$11) as App,
            PrimitiveCodec.BOOL.fieldOf("loop").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$12) as App
         )
         .apply(instance as Applicative, AnimatedParticleUVMode::CODEC$lambda$14$lambda$13);
   }

   fun AnimatedParticleUVMode() {
      this(null, null, 0, 0, null, null, null, null, null, null, false, false, 4095, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(AnimatedParticleUVMode::CODEC$lambda$14);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<AnimatedParticleUVMode>
   }
}
