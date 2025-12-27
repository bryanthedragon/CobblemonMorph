package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import java.util.Arrays

public class TimeRange : IntRanges {

   public constructor(vararg ranges: IntRange) : super(Arrays.copyOf(ranges, ranges.length))
   public companion object {
      public final val timeRanges: MutableMap<String, TimeRange>
   }
}
