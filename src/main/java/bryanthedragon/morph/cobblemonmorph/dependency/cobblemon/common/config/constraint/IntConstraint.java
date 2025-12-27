package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.constraint

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
annotation class IntConstraint(
   val min: Int,
   val max: Int
)
