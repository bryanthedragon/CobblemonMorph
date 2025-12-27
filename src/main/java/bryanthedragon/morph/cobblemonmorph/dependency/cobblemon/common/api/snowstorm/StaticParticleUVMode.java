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

public class StaticParticleUVMode(startU: Expression = (new NumberExpression(0.0)) as Expression,
      startV: Expression = (new NumberExpression(0.0)) as Expression,
      textureSizeX: Int = 8,
      textureSizeY: Int = 8,
      uSize: Expression = (new NumberExpression(8.0)) as Expression,
      vSize: Expression = (new NumberExpression(8.0)) as Expression
   )
   : ParticleUVMode {
   public open var startU: Expression
   public open var startV: Expression
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
      this.type = ParticleUVModeType.STATIC;
   }

   public override fun get(moLangRuntime: MoLangRuntime, age: Double, maxAge: Double, uvDetails: UVDetails): UVDetails {
      return uvDetails.set(
         MoLangExtensionsKt.resolveDouble(moLangRuntime, this.getStartU()) / (double)this.getTextureSizeX(),
         MoLangExtensionsKt.resolveDouble(moLangRuntime, this.getStartV()) / (double)this.getTextureSizeY(),
         (MoLangExtensionsKt.resolveDouble(moLangRuntime, this.getStartU()) + MoLangExtensionsKt.resolveDouble(moLangRuntime, this.getUSize()))
            / (double)this.getTextureSizeX(),
         (MoLangExtensionsKt.resolveDouble(moLangRuntime, this.getStartV()) + MoLangExtensionsKt.resolveDouble(moLangRuntime, this.getVSize()))
            / (double)this.getTextureSizeY()
      );
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
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.getStartU()));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.getStartV()));
      buffer.writeInt(this.getTextureSizeX());
      buffer.writeInt(this.getTextureSizeY());
      buffer.m_130070_(MoLangExtensionsKt.getString(this.getUSize()));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.getVSize()));
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$0`(it: StaticParticleUVMode): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$1`(it: StaticParticleUVMode): Expression {
      return it.getStartU();
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$2`(it: StaticParticleUVMode): Expression {
      return it.getStartV();
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$3`(it: StaticParticleUVMode): Int {
      return it.getTextureSizeX();
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$4`(it: StaticParticleUVMode): Int {
      return it.getTextureSizeY();
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$5`(it: StaticParticleUVMode): Expression {
      return it.getUSize();
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$6`(it: StaticParticleUVMode): Expression {
      return it.getVSize();
   }

   @JvmStatic
   fun `CODEC$lambda$8$lambda$7`(
      var0: java.lang.String, startU: Expression, startV: Expression, textureSizeX: Int, textureSizeY: Int, uSize: Expression, vSize: Expression
   ): StaticParticleUVMode {
      val var10004: Int = textureSizeX;
      val var10005: Int = textureSizeY;
      return new StaticParticleUVMode(startU, startV, var10004, var10005, uSize, vSize);
   }

   @JvmStatic
   fun `CODEC$lambda$8`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(StaticParticleUVMode::CODEC$lambda$8$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("startU").forGetter(StaticParticleUVMode::CODEC$lambda$8$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("startV").forGetter(StaticParticleUVMode::CODEC$lambda$8$lambda$2) as App,
            PrimitiveCodec.INT.fieldOf("textureSizeX").forGetter(StaticParticleUVMode::CODEC$lambda$8$lambda$3) as App,
            PrimitiveCodec.INT.fieldOf("textureSizeY").forGetter(StaticParticleUVMode::CODEC$lambda$8$lambda$4) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("uSize").forGetter(StaticParticleUVMode::CODEC$lambda$8$lambda$5) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("vSize").forGetter(StaticParticleUVMode::CODEC$lambda$8$lambda$6) as App
         )
         .apply(instance as Applicative, StaticParticleUVMode::CODEC$lambda$8$lambda$7);
   }

   fun StaticParticleUVMode() {
      this(null, null, 0, 0, null, null, 63, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(StaticParticleUVMode::CODEC$lambda$8);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<StaticParticleUVMode>
   }
}
