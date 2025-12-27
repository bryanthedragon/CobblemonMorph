package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import kotlin.jvm.functions.Function1
import org.jetbrains.annotations.NotNull

public final val EXPRESSION_CODEC: PrimitiveCodec<Expression> = (new PrimitiveCodec<Expression>() {
   @NotNull
   public <T> DataResult<Expression> read(@NotNull DynamicOps<T> ops, T input) {
      val var10000: DataResult = ops.getStringValue(input).map(<unrepresentable>::read$lambda$0);
      return var10000;
   }

   public <T> T write(@NotNull DynamicOps<T> ops, @NotNull Expression value) {
      return (T)ops.createString(MoLangExtensionsKt.getString(value));
   }

   private static final Expression read$lambda$0(Function1 $tmp0, Object p0) {
      return `$tmp0`.invoke(p0) as Expression;
   }
}) as PrimitiveCodec
