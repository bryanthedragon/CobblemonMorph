package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import com.mojang.datafixers.util.Pair
import java.util.ArrayList;
import java.util.Arrays
import java.util.function.Consumer
import kotlin.jvm.functions.Function1
import kotlin.random.Random
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.phys.shapes.VoxelShape

public final val either: Boolean
   public final get() {
      return `$this$either`.getFirst() as java.lang.Boolean || `$this$either`.getSecond() as java.lang.Boolean;
   }


public fun cobblemonResource(path: String): ResourceLocation {
   return new ResourceLocation("cobblemon", path);
}

public fun cobblemonModel(path: String, variant: String): ModelResourceLocation {
   return new ModelResourceLocation("cobblemon", path, variant);
}

public fun String.asTranslated(): MutableComponent {
   return Component.m_237115_(`$this$asTranslated`);
}

public fun String.asResource(): ResourceLocation {
   return new ResourceLocation(`$this$asResource`);
}

public fun String.asTranslated(vararg data: Any): MutableComponent {
   return Component.m_237110_(`$this$asTranslated`, Arrays.copyOf(data, data.length));
}

public fun String.isInt(): Boolean {
   return StringsKt.toIntOrNull(`$this$isInt`) != null;
}

public fun String.isHigherVersion(other: String): Boolean {
   val thisSplits: java.util.List = StringsKt.split$default(`$this$isHigherVersion`, new java.lang.String[]{"."}, false, 0, 6, null);
   val var10: java.util.List = StringsKt.split$default(other, new java.lang.String[]{"."}, false, 0, 6, null);
   val var11: Int = thisSplits.size();
   val thatCount: Int = var10.size();
   val min: Int = Math.min(var11, thatCount);

   for (int i = 0; i < min; i++) {
      val thisDigit: Int = StringsKt.toIntOrNull(thisSplits.get(i) as java.lang.String);
      val thatDigit: Int = StringsKt.toIntOrNull(var10.get(i) as java.lang.String);
      if (thisDigit == null || thatDigit == null) {
         return false;
      }

      if (thisDigit > thatDigit) {
         return true;
      }

      if (thisDigit < thatDigit) {
         return false;
      }
   }

   return var11 > thatCount;
}

public fun String.substitute(placeholder: String, value: Any?): String {
   val var10001: java.lang.String = "{{$placeholder}}";
   if (value != null) {
      val var10002: java.lang.String = value.toString();
      if (var10002 != null) {
         return StringsKt.replace$default(`$this$substitute`, var10001, var10002, false, 4, null);
      }
   }

   return StringsKt.replace$default(`$this$substitute`, var10001, "", false, 4, null);
}

public fun Random.nextBetween(min: Float, max: Float): Float {
   return `$this$nextBetween`.nextFloat() * (max - min) + min;
}

public fun Random.nextBetween(min: Double, max: Double): Double {
   return `$this$nextBetween`.nextDouble() * (max - min) + min;
}

public fun Random.nextBetween(min: Int, max: Int): Int {
   return `$this$nextBetween`.nextInt(max - min + 1) + min;
}

public infix fun <A, B> Any.toDF(b: Any): Pair<Any, Any> {
   return new Pair(`$this$toDF`, b);
}

public fun isUuid(string: String): Boolean {
   return new Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$").matches(string);
}

public fun VoxelShape.blockPositionsAsList(): List<BlockPos> {
   val result: java.util.List = new ArrayList();
   `$this$blockPositionsAsList`.m_83286_(MiscUtilsKt::blockPositionsAsList$lambda$0);
   return result;
}

public operator fun <T> Consumer<Any>.plus(action: (Any) -> Unit): Consumer<Any> {
   val var10000: Consumer = `$this$plus`.andThen(MiscUtilsKt::plus$lambda$1);
   return var10000;
}

fun `blockPositionsAsList$lambda$0`(`$result`: java.util.List, minX: Double, minY: Double, minZ: Double, maxX: Double, maxY: Double, maxZ: Double) {
   var x: Int = (int)minX;

   for (int var14 = (int)maxX; x < var14; x++) {
      var y: Int = (int)minY;

      for (int var16 = (int)maxY; y < var16; y++) {
         var z: Int = (int)minZ;

         for (int var18 = (int)maxZ; z < var18; z++) {
            `$result`.add(new BlockPos(x, y, z));
         }
      }
   }
}

fun `plus$lambda$1`(`$tmp0`: Function1, p0: Any) {
   `$tmp0`.invoke(p0);
}
