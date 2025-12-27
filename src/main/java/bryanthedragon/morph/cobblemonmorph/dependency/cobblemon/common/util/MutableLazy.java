package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

public class MutableLazy<T>(initializer: (() -> Any)?) : ReadWriteProperty<Object, T> {
   private final var initializer: (() -> Any)?
   private final var value: Any?

   init {
      this.initializer = initializer;
   }

   public open operator fun getValue(thisRef: Any?, property: KProperty<*>): Any {
      if (this.value == null) {
         this.value = (T)(if (this.initializer != null) this.initializer.invoke() else null);
         this.initializer = null;
      }

      val var10000: Any = this.value;
      return (T)var10000;
   }

   public open operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Any) {
      this.value = (T)value;
   }
}
