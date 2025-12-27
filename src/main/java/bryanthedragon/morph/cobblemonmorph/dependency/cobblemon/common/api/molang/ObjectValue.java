package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang

import com.bedrockk.molang.runtime.struct.QueryStruct
import java.util.HashMap
import kotlin.jvm.functions.Function1

public class ObjectValue<T>(obj: Any,
   stringify: (Any) -> String = <unrepresentable>.INSTANCE as Function1,
   doublify: (Any) -> Double = <unrepresentable>.INSTANCE as Function1
) : QueryStruct(new HashMap<>()) {
   public final val doublify: (Any) -> Double
   public final var obj: Any
   public final val stringify: (Any) -> String

   init {
      this.obj = (T)obj;
      this.stringify = stringify;
      this.doublify = doublify;
   }

   public open fun value(): ObjectValue<Any> {
      return this;
   }

   public override fun asDouble(): Double {
      return (this.doublify.invoke(this.obj) as java.lang.Number).doubleValue();
   }

   public override fun asString(): String {
      return this.stringify.invoke(this.obj) as java.lang.String;
   }
}
