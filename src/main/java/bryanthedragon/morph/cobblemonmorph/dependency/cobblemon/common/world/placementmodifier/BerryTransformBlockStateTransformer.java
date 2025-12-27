package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
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
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.Property

public class BerryTransformBlockStateTransformer(minAge: Int, maxAge: Int, wild: Boolean) : BlockStateTransformer {
   public final val maxAge: Int
   public final val minAge: Int
   public open val type: BlockStateTransformerType
   public final val wild: Boolean

   init {
      this.minAge = minAge;
      this.maxAge = maxAge;
      this.wild = wild;
      this.type = BlockStateTransformerType.NONE;
   }

   public override fun transform(blockState: BlockState): BlockState {
      return (blockState.m_61124_(BerryBlock.Companion.getAGE() as Property, Random.Default.nextInt(this.minAge, this.maxAge + 1)) as BlockState)
         .m_61124_(BerryBlock.Companion.getWAS_GENERATED() as Property, this.wild) as BlockState;
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
   fun `CODEC$lambda$5$lambda$0`(it: BerryTransformBlockStateTransformer): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$1`(it: BerryTransformBlockStateTransformer): Int {
      return it.minAge;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$2`(it: BerryTransformBlockStateTransformer): Int {
      return it.maxAge;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$3`(it: BerryTransformBlockStateTransformer): java.lang.Boolean {
      return it.wild;
   }

   @JvmStatic
   fun `CODEC$lambda$5$lambda$4`(var0: java.lang.String, minAge: Int, maxAge: Int, isWild: java.lang.Boolean): BerryTransformBlockStateTransformer {
      val var10002: Int = minAge;
      val var10003: Int = maxAge;
      return new BerryTransformBlockStateTransformer(var10002, var10003, isWild);
   }

   @JvmStatic
   fun `CODEC$lambda$5`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(BerryTransformBlockStateTransformer::CODEC$lambda$5$lambda$0) as App,
            PrimitiveCodec.INT.fieldOf("minAge").forGetter(BerryTransformBlockStateTransformer::CODEC$lambda$5$lambda$1) as App,
            PrimitiveCodec.INT.fieldOf("maxAge").forGetter(BerryTransformBlockStateTransformer::CODEC$lambda$5$lambda$2) as App,
            PrimitiveCodec.BOOL.fieldOf("isWild").forGetter(BerryTransformBlockStateTransformer::CODEC$lambda$5$lambda$3) as App
         )
         .apply(instance as Applicative, BerryTransformBlockStateTransformer::CODEC$lambda$5$lambda$4);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(BerryTransformBlockStateTransformer::CODEC$lambda$5);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<BerryTransformBlockStateTransformer>
   }
}
