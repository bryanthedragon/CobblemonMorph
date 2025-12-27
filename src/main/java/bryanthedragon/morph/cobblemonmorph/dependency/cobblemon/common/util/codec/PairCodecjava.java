package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec

import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance

public fun <A, B> pairCodec(codecA: Codec<Any>, codecB: Codec<Any>): Codec<Pair<Any, Any>> {
   val var10000: Codec = RecordCodecBuilder.create(PairCodecKt::pairCodec$lambda$3);
   return var10000;
}

fun `pairCodec$lambda$3$lambda$0`(it: Pair): Any {
   return it.getFirst();
}

fun `pairCodec$lambda$3$lambda$1`(it: Pair): Any {
   return it.getSecond();
}

fun `pairCodec$lambda$3$lambda$2`(a: Any, b: Any): Pair {
   return TuplesKt.to(a, b);
}

fun `pairCodec$lambda$3`(`$codecA`: Codec, `$codecB`: Codec, instance: Instance): App {
   return instance.group(
         `$codecA`.fieldOf("first").forGetter(PairCodecKt::pairCodec$lambda$3$lambda$0) as App,
         `$codecB`.fieldOf("second").forGetter(PairCodecKt::pairCodec$lambda$3$lambda$1) as App
      )
      .apply(instance as Applicative, PairCodecKt::pairCodec$lambda$3$lambda$2);
}
