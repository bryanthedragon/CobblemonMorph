package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import io.netty.buffer.ByteBuf
import kotlin.collections.Map.Entry
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3

public fun ByteBuf.writeConditional(condition: () -> Boolean, writer: () -> Unit) {
   writeConditional(`$this$writeConditional`, condition.invoke() as java.lang.Boolean, writer);
}

public fun ByteBuf.writeConditional(shouldWrite: Boolean, writer: () -> Unit) {
   `$this$writeConditional`.writeBoolean(shouldWrite);
   if (shouldWrite) {
      writer.invoke();
   }
}

public fun ByteBuf.writeSizedInt(size: IntSize, value: Int) {
   switch (NetExtensionsKt.WhenMappings.$EnumSwitchMapping$0[size.ordinal()]) {
      case 1:
         `$this$writeSizedInt`.writeInt(value);
         break;
      case 2:
      case 3:
         `$this$writeSizedInt`.writeShort(value);
         break;
      case 4:
      case 5:
         `$this$writeSizedInt`.writeByte(value);
      default:
   }
}

public fun ByteBuf.readConditional(reader: () -> Unit) {
   if (`$this$readConditional`.readBoolean()) {
      reader.invoke();
   }
}

public fun ByteBuf.readSizedInt(size: IntSize): Int {
   var var10000: Int;
   switch (NetExtensionsKt.WhenMappings.$EnumSwitchMapping$0[size.ordinal()]) {
      case 1:
         var10000 = `$this$readSizedInt`.readInt();
         break;
      case 2:
         var10000 = `$this$readSizedInt`.readShort();
         break;
      case 3:
         var10000 = `$this$readSizedInt`.readUnsignedShort();
         break;
      case 4:
         var10000 = `$this$readSizedInt`.readByte();
         break;
      case 5:
         var10000 = `$this$readSizedInt`.readUnsignedByte();
         break;
      default:
         throw new NoWhenBranchMatchedException();
   }

   return var10000;
}

public fun ByteBuf.readTimes(size: IntSize = IntSize.U_BYTE, readEntry: () -> Unit) {
   val times: Int = readSizedInt(`$this$readTimes`, size);

   for (int var4 = 0; var4 < times; var4++) {
      readEntry.invoke();
   }
}

@JvmSynthetic
fun `readTimes$default`(var0: ByteBuf, var1: IntSize, var2: Function0, var3: Int, var4: Any) {
   if ((var3 and 1) != 0) {
      var1 = IntSize.U_BYTE;
   }

   readTimes(var0, var1, var2);
}

public fun ByteBuf.writeBox(box: AABB) {
   `$this$writeBox`.writeDouble(box.f_82288_);
   `$this$writeBox`.writeDouble(box.f_82289_);
   `$this$writeBox`.writeDouble(box.f_82290_);
   `$this$writeBox`.writeDouble(box.f_82291_);
   `$this$writeBox`.writeDouble(box.f_82292_);
   `$this$writeBox`.writeDouble(box.f_82293_);
}

public fun ByteBuf.readBox(): AABB {
   return new AABB(
      `$this$readBox`.readDouble(),
      `$this$readBox`.readDouble(),
      `$this$readBox`.readDouble(),
      `$this$readBox`.readDouble(),
      `$this$readBox`.readDouble(),
      `$this$readBox`.readDouble()
   );
}

public fun <K, V> ByteBuf.writeMapK(size: IntSize = IntSize.U_BYTE, map: Map<Any, Any>, entryWriter: (Entry<Any, Any>) -> Unit) {
   writeSizedInt(`$this$writeMapK`, size, map.size());

   val `$this$forEach$iv`: java.lang.Iterable;
   for (Object element$iv : $this$forEach$iv) {
      entryWriter.invoke(`element$iv`);
   }
}

@JvmSynthetic
fun `writeMapK$default`(var0: ByteBuf, var1: IntSize, var2: java.util.Map, var3: Function1, var4: Int, var5: Any) {
   if ((var4 and 1) != 0) {
      var1 = IntSize.U_BYTE;
   }

   writeMapK(var0, var1, var2, var3);
}

public fun <K, V> ByteBuf.readMapK(size: IntSize = IntSize.U_BYTE, map: MutableMap<Any, Any>, entryReader: () -> Pair<Any, Any>) {
   val times: Int = readSizedInt(`$this$readMapK`, size);

   for (int var5 = 0; var5 < times; var5++) {
      val var8: Pair = entryReader.invoke() as Pair;
      map.put(var8.component1(), var8.component2());
   }
}

@JvmSynthetic
fun `readMapK$default`(var0: ByteBuf, var1: IntSize, var2: java.util.Map, var3: Function0, var4: Int, var5: Any) {
   if ((var4 and 1) != 0) {
      var1 = IntSize.U_BYTE;
   }

   readMapK(var0, var1, var2, var3);
}

public fun ByteBuf.writeVec3d(vec3d: Vec3) {
   `$this$writeVec3d`.writeDouble(vec3d.f_82479_);
   `$this$writeVec3d`.writeDouble(vec3d.f_82480_);
   `$this$writeVec3d`.writeDouble(vec3d.f_82481_);
}

public fun ByteBuf.readVec3d(): Vec3 {
   return new Vec3(`$this$readVec3d`.readDouble(), `$this$readVec3d`.readDouble(), `$this$readVec3d`.readDouble());
}
// $VF: Class flags could not be determined
@JvmSynthetic
internal class WhenMappings {
   @JvmStatic
   fun {
      val var0: IntArray = new int[IntSize.values().length];

      try {
         var0[IntSize.INT.ordinal()] = 1;
      } catch (var6: NoSuchFieldError) {
      }

      try {
         var0[IntSize.SHORT.ordinal()] = 2;
      } catch (var5: NoSuchFieldError) {
      }

      try {
         var0[IntSize.U_SHORT.ordinal()] = 3;
      } catch (var4: NoSuchFieldError) {
      }

      try {
         var0[IntSize.BYTE.ordinal()] = 4;
      } catch (var3: NoSuchFieldError) {
      }

      try {
         var0[IntSize.U_BYTE.ordinal()] = 5;
      } catch (var2: NoSuchFieldError) {
      }

      $EnumSwitchMapping$0 = var0;
   }
}
