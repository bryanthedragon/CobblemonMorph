package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

public fun <T> mutableLazy(initializer: () -> Any): MutableLazy<Any> {
   return new MutableLazy(initializer);
}
