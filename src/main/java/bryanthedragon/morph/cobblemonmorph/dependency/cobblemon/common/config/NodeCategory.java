package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config

import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy

@Target(allowedTargets = [AnnotationTarget.FIELD])
@Retention(AnnotationRetention.RUNTIME)
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target([ElementType.FIELD])
annotation class NodeCategory(
   val category: Category
)
