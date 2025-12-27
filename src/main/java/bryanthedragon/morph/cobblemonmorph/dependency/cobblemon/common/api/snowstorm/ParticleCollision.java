package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.ast.NumberExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import net.minecraft.network.FriendlyByteBuf

public class ParticleCollision(enabled: Expression = (new NumberExpression(0.0)) as Expression,
   radius: Expression = (new NumberExpression(0.1)) as Expression,
   friction: Expression = (new NumberExpression(10.0)) as Expression,
   bounciness: Expression = (new NumberExpression(0.0)) as Expression,
   expiresOnContact: Boolean = false
) {
   public final var bounciness: Expression
   public final var enabled: Expression
   public final var expiresOnContact: Boolean
   public final var friction: Expression
   public final var radius: Expression

   init {
      this.enabled = enabled;
      this.radius = radius;
      this.friction = friction;
      this.bounciness = bounciness;
      this.expiresOnContact = expiresOnContact;
   }

   public fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(MoLangExtensionsKt.getString(this.enabled));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.radius));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.friction));
      buffer.m_130070_(MoLangExtensionsKt.getString(this.bounciness));
      buffer.writeBoolean(this.expiresOnContact);
   }

   public fun readFromBuffer(buffer: FriendlyByteBuf) {
      var var10001: Expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.enabled = var10001;
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.radius = var10001;
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.friction = var10001;
      var10001 = MoLang.createParser(buffer.m_130277_()).parseExpression();
      this.bounciness = var10001;
      this.expiresOnContact = buffer.readBoolean();
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$0`(it: ParticleCollision): Expression {
      return it.enabled;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$1`(it: ParticleCollision): Expression {
      return it.radius;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$2`(it: ParticleCollision): Expression {
      return it.friction;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$3`(it: ParticleCollision): Expression {
      return it.bounciness;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$4`(it: ParticleCollision): java.lang.Boolean {
      return it.expiresOnContact;
   }

   @JvmStatic
   fun `CODEC$lambda$5`(instance: Instance): App {
      return instance.group(
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("enabled").forGetter(ParticleCollision::CODEC$lambda$5$lambda$0) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("radius").forGetter(ParticleCollision::CODEC$lambda$5$lambda$1) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("friction").forGetter(ParticleCollision::CODEC$lambda$5$lambda$2) as App,
            ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("bounciness").forGetter(ParticleCollision::CODEC$lambda$5$lambda$3) as App,
            PrimitiveCodec.BOOL.fieldOf("expiresOnContact").forGetter(ParticleCollision::CODEC$lambda$5$lambda$4) as App
         )
         .apply(instance as Applicative, ParticleCollision::new);
   }

   fun ParticleCollision() {
      this(null, null, null, null, false, 31, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(ParticleCollision::CODEC$lambda$5);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<ParticleCollision>
   }
}
