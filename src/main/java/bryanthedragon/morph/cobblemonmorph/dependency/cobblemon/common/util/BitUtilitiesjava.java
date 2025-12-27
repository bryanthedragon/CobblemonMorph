package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt

public fun setBitForByte(byte: Byte, bit: Int, on: Boolean): Byte {
   val bitAsByte: Int = SimpleMathExtensionsKt.pow(2, bit - 1);
   return if (on) (byte)(byte or (byte)bitAsByte) else (byte)(byte and (byte)(-bitAsByte - 1));
}

public fun getBitForByte(byte: Byte, bit: Int): Boolean {
   return (byte)(byte and (byte)SimpleMathExtensionsKt.pow(2, bit - 1)) != 0;
}
