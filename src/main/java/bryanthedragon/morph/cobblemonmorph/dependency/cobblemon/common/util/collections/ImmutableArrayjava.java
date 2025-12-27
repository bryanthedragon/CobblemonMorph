package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.collections

import java.util.Arrays

@JvmSynthetic
public inline fun <reified T> immutableArrayOf(vararg values: Any): ImmutableArray<Any> {
   return (ImmutableArray<T>)(new ImmutableArray<>(Arrays.copyOf(values, values.length)));
}
