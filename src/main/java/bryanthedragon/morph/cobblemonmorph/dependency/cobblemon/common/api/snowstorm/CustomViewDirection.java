package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
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
import net.minecraft.world.phys.Vec3

public class CustomViewDirection(direction: Triple<Expression, Expression, Expression>) : ParticleViewDirection {
   public final var direction: Triple<Expression, Expression, Expression>
   public open val type: ParticleViewDirectionType

   init {
      this.direction = direction;
      this.type = ParticleViewDirectionType.CUSTOM;
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.direction.getFirst() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.direction.getSecond() as Expression));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.direction.getThird() as Expression));
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      val var10003: java.lang.String = buffer.m_130277_();
      val var2: Expression = MoLangExtensionsKt.asExpression(var10003);
      val var10004: java.lang.String = buffer.m_130277_();
      val var3: Expression = MoLangExtensionsKt.asExpression(var10004);
      val var10005: java.lang.String = buffer.m_130277_();
      this.direction = new Triple(var2, var3, MoLangExtensionsKt.asExpression(var10005));
   }

   public override fun getDirection(runtime: MoLangRuntime, lastDirection: Vec3, currentVelocity: Vec3): Vec3 {
      return MoLangExtensionsKt.resolveVec3d(runtime, this.direction);
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$0`(it: CustomViewDirection): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$1`(it: CustomViewDirection): Expression {
      return it.direction.getFirst() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$2`(it: CustomViewDirection): Expression {
      return it.direction.getSecond() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$3`(it: CustomViewDirection): Expression {
      return it.direction.getThird() as Expression;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$4`(var0: java.lang.String, directionX: Expression, directionY: Expression, directionZ: Expression): CustomViewDirection {
      return new CustomViewDirection(new Triple(directionX, directionY, directionZ));
   }

   @JvmStatic
   fun `CODEC$lambda$5`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(CustomViewDirection::CODEC$lambda$5$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("directionX").forGetter(CustomViewDirection::CODEC$lambda$5$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("directionY").forGetter(CustomViewDirection::CODEC$lambda$5$lambda$2) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("directionZ").forGetter(CustomViewDirection::CODEC$lambda$5$lambda$3) as App
         )
         .apply(instance as Applicative, CustomViewDirection::CODEC$lambda$5$lambda$4);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(CustomViewDirection::CODEC$lambda$5);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<CustomViewDirection>
   }
}
