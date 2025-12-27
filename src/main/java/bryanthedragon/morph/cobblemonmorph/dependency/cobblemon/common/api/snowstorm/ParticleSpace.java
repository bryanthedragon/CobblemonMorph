package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import net.minecraft.network.FriendlyByteBuf

public class ParticleSpace(localPosition: Boolean = false, localRotation: Boolean = false, localVelocity: Boolean = false) {
   public final val isLocalSpace: Boolean
      public final get() {
         return this.localPosition || this.localRotation;
      }


   public final var localPosition: Boolean
   public final var localRotation: Boolean
   public final var localVelocity: Boolean

   init {
      this.localPosition = localPosition;
      this.localRotation = localRotation;
      this.localVelocity = localVelocity;
   }

   public fun readFromBuffer(buffer: FriendlyByteBuf) {
      this.localPosition = buffer.readBoolean();
      this.localRotation = buffer.readBoolean();
      this.localVelocity = buffer.readBoolean();
   }

   public fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.writeBoolean(this.localPosition);
      buffer.writeBoolean(this.localRotation);
      buffer.writeBoolean(this.localVelocity);
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$0`(it: ParticleSpace): java.lang.Boolean {
      return it.localPosition;
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$1`(it: ParticleSpace): java.lang.Boolean {
      return it.localRotation;
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$2`(it: ParticleSpace): java.lang.Boolean {
      return it.localVelocity;
   }

   @JvmStatic
   fun `CODEC$lambda$3`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.BOOL.fieldOf("localPosition").forGetter(ParticleSpace::CODEC$lambda$3$lambda$0) as App,
            PrimitiveCodec.BOOL.fieldOf("localRotation").forGetter(ParticleSpace::CODEC$lambda$3$lambda$1) as App,
            PrimitiveCodec.BOOL.fieldOf("localVelocity").forGetter(ParticleSpace::CODEC$lambda$3$lambda$2) as App
         )
         .apply(instance as Applicative, ParticleSpace::new);
   }

   fun ParticleSpace() {
      this(false, false, false, 7, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(ParticleSpace::CODEC$lambda$3);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<ParticleSpace>
   }
}
