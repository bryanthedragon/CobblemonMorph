package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec

import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.datafixers.util.Pair
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import kotlin.jvm.functions.Function1

public class MappedCodec<A extends CodecMapped, K>(codecRetriever: (Any) -> Codec<out Any>, keyName: String = "type", keyFromString: (String) -> Any) : Codec<A> {
   public final val codecRetriever: (Any) -> Codec<out Any>
   public final val keyFromString: (String) -> Any
   public final val keyName: String

   init {
      this.codecRetriever = codecRetriever;
      this.keyName = keyName;
      this.keyFromString = keyFromString;
   }

   public open fun <T> encode(input: Any, ops: DynamicOps<Any>, prefix: Any): DataResult<Any> {
      return input.encode(ops);
   }

   public open fun <T> decode(ops: DynamicOps<Any>, input: Any): DataResult<Pair<Any, Any>> {
      val var10000: Codec = RecordCodecBuilder.create(MappedCodec::decode$lambda$1);
      val var7: DataResult = (this.codecRetriever
            .invoke(this.keyFromString.invoke((var10000.decode(ops, input).map(MappedCodec::decode$lambda$2).get().left().get() as ThingWithType).getString())) as Codec)
         .decode(ops, input)
         .map(MappedCodec::decode$lambda$3);
      return var7;
   }

   @JvmStatic
   fun `decode$lambda$1$lambda$0`(it: ThingWithType): java.lang.String {
      return it.getString();
   }

   @JvmStatic
   fun `decode$lambda$1`(`this$0`: MappedCodec, instance: Instance): App {
      return instance.group(PrimitiveCodec.STRING.fieldOf(`this$0`.keyName).forGetter(MappedCodec::decode$lambda$1$lambda$0) as App)
         .apply(instance as Applicative, ThingWithType::new);
   }

   @JvmStatic
   fun `decode$lambda$2`(`$tmp0`: Function1, p0: Any): ThingWithType {
      return `$tmp0`.invoke(p0) as ThingWithType;
   }

   @JvmStatic
   fun `decode$lambda$3`(`$tmp0`: Function1, p0: Any): Pair {
      return `$tmp0`.invoke(p0) as Pair;
   }
}
