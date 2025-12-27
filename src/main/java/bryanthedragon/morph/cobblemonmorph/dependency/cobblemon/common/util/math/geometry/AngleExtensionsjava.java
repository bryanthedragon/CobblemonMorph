package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry

private const val RADIAN_IN_DEGREES: Float = 57.2958F

public fun Number.toRadians(): Float {
   return `$this$toRadians`.floatValue() / 57.2958F;
}

public fun Number.toDegrees(): Float {
   return `$this$toDegrees`.floatValue() * 57.2958F;
}
