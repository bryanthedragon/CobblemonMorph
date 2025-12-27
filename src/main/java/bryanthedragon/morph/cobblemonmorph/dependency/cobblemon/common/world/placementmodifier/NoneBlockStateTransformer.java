package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier

import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.block.state.BlockState

public class NoneBlockStateTransformer : BlockStateTransformer {
   public open val type: BlockStateTransformerType = BlockStateTransformerType.NONE

   public override fun transform(blockState: BlockState): BlockState {
      return blockState;
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      val var10000: DataResult = CODEC.encodeStart(ops, this);
      return var10000;
   }

   public open fun readFromBuffer(buffer: FriendlyByteBuf): Nothing {
      throw new NotImplementedError("Not supposed to use this for block state transformers");
   }

   public open fun writeToBuffer(buffer: FriendlyByteBuf): Nothing {
      throw new NotImplementedError("Not supposed to use this for block state transformers");
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$0`(it: NoneBlockStateTransformer): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$2$lambda$1`(var0: java.lang.String): NoneBlockStateTransformer {
      return new NoneBlockStateTransformer();
   }

   @JvmStatic
   fun `CODEC$lambda$2`(instance: Instance): App {
      return instance.group(PrimitiveCodec.STRING.fieldOf("type").forGetter(NoneBlockStateTransformer::CODEC$lambda$2$lambda$0) as App)
         .apply(instance as Applicative, NoneBlockStateTransformer::CODEC$lambda$2$lambda$1);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(NoneBlockStateTransformer::CODEC$lambda$2);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<NoneBlockStateTransformer>
   }
}
