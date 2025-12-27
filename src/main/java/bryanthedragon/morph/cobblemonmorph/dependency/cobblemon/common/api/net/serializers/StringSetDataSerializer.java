package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.LinkedHashSet
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.syncher.EntityDataSerializer

@SourceDebugExtension(["SMAP\nStringSetDataSerializer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StringSetDataSerializer.kt\ncom/cobblemon/mod/common/api/net/serializers/StringSetDataSerializer\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,33:1\n1855#2,2:34\n*S KotlinDebug\n*F\n+ 1 StringSetDataSerializer.kt\ncom/cobblemon/mod/common/api/net/serializers/StringSetDataSerializer\n*L\n20#1:34,2\n*E\n"])
public object StringSetDataSerializer : EntityDataSerializer<java.util.Set<? extends java.lang.String>> {
   public open fun write(buffer: FriendlyByteBuf, set: Set<String>) {
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, set.size());

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         buffer.m_130070_(`element$iv` as java.lang.String);
      }
   }

   public open fun read(buffer: FriendlyByteBuf): Set<String> {
      val set: java.util.Set = new LinkedHashSet();
      val var3: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);

      for (int var4 = 0; var4 < var3; var4++) {
         val var10001: java.lang.String = buffer.m_130277_();
         set.add(var10001);
      }

      return set;
   }

   public open fun copy(set: Set<String>): Set<String> {
      return CollectionsKt.toSet(set);
   }
}
