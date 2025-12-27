package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.MappedCodec
import com.mojang.serialization.Codec
import java.util.LinkedHashMap
import kotlin.jvm.functions.Function1
import net.minecraft.network.FriendlyByteBuf
import org.jetbrains.annotations.NotNull

public abstract class ArbitrarilyMappedSerializableCompanion<T extends CodecMapped, K> {
   public final val codec: MappedCodec<Any, Any>
   public final val keyFromString: (String) -> Any
   public final val keyFromValue: (Any) -> Any
   public final val stringFromKey: (Any) -> String
   private final val subtypes: MutableMap<Any, RegisteredSubtype<out Any>>

   open fun ArbitrarilyMappedSerializableCompanion(keyFromString: (java.lang.String?) -> K, stringFromKey: (K?) -> java.lang.String, keyFromValue: (T?) -> K) {
      this.keyFromString = keyFromString;
      this.stringFromKey = stringFromKey;
      this.keyFromValue = keyFromValue;
      this.codec = new MappedCodec<>((new Function1<K, Codec<? extends T>>(this) {
         {
            super(1);
            this.this$0 = `$receiver`;
         }

         @NotNull
         public final Codec<? extends T> invoke(K it) {
            val var10000: Any = ArbitrarilyMappedSerializableCompanion.access$getSubtypes$p(this.this$0).get(it);
            return (var10000 as RegisteredSubtype).getCodec();
         }
      }) as Function1, null, this.keyFromString, 2, null);
      this.subtypes = new LinkedHashMap<>();
   }

   public fun <E : Any> registerSubtype(key: Any, clazz: Class<Any>, codec: Codec<Any>) {
      this.subtypes.put((K)key, new RegisteredSubtype<>(clazz, codec));
   }

   public fun writeToBuffer(buffer: FriendlyByteBuf, value: Any) {
      buffer.m_130070_(this.stringFromKey.invoke(this.keyFromValue.invoke(value)) as java.lang.String);
      value.writeToBuffer(buffer);
   }

   public fun readFromBuffer(buffer: FriendlyByteBuf): Any {
      val typeString: java.lang.String = buffer.m_130277_();
      val var10000: java.util.Map = this.subtypes;
      val var10001: Function1 = this.keyFromString;
      val var5: RegisteredSubtype = var10000.get(var10001.invoke(typeString)) as RegisteredSubtype;
      if (var5 != null) {
         val var6: Class = var5.getClazz();
         if (var6 != null) {
            val value: CodecMapped = var6.getDeclaredConstructor().newInstance() as CodecMapped;
            value.readFromBuffer(buffer);
            return (T)value;
         }
      }

      throw new IllegalArgumentException("Unrecognized subtype: $typeString");
   }
}
