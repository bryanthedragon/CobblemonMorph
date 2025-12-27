package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.collections

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import java.util.ArrayList;
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.CollectionToArray
import kotlin.jvm.internal.markers.KMutableSet
import kotlin.reflect.KClass
import org.jetbrains.annotations.NotNull

public class LazySet<T>(type: KClass<Any>, values: JsonArray) : java.util.Set<T>, KMutableSet {
   private final val elements: MutableSet<Any>
      private final get() {
         return this.elements$delegate.getValue() as MutableSet<T>;
      }


   private final val json: Set<JsonElement>

   public open val size: Int
      public open get() {
         return this.getElements().size();
      }


   private final val type: KClass<Any>

   init {
      this.type = type;
      this.json = CollectionsKt.toSet(values as java.lang.Iterable);
      this.elements$delegate = LazyKt.lazy(
         (
            new Function0<java.util.Set<T>>(this) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
               }

               @NotNull
               public final java.util.Set<T> invoke() {
                  val `$this$map$iv`: java.lang.Iterable = LazySet.access$getJson$p(this.this$0);
                  val var2: LazySet = this.this$0;
                  val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

                  for (Object item$iv$iv : $this$map$iv) {
                     `destination$iv$iv`.add(
                        PokemonSpecies.INSTANCE.getGson().fromJson(`item$iv$iv` as JsonElement, JvmClassMappingKt.getJavaClass(LazySet.access$getType$p(var2)))
                     );
                  }

                  return CollectionsKt.toMutableSet(`destination$iv$iv` as java.util.List);
               }
            }
         ) as Function0
      );
   }

   public override fun add(element: Any): Boolean {
      return this.getElements().add((T)element);
   }

   public override fun addAll(elements: Collection<Any>): Boolean {
      return this.getElements().addAll(elements);
   }

   public override fun clear() {
      this.getElements().clear();
   }

   public override operator fun iterator(): MutableIterator<Any> {
      return this.getElements().iterator();
   }

   public override fun remove(element: Any): Boolean {
      return element != null && this.getElements().remove(element);
   }

   public override fun removeAll(elements: Collection<Any>): Boolean {
      return this.getElements().removeAll(CollectionsKt.toSet(elements));
   }

   public override fun retainAll(elements: Collection<Any>): Boolean {
      return this.getElements().retainAll(CollectionsKt.toSet(elements));
   }

   public override operator fun contains(element: Any): Boolean {
      return element != null && this.getElements().contains(element);
   }

   public override fun containsAll(elements: Collection<Any>): Boolean {
      return this.getElements().containsAll(elements);
   }

   public override fun isEmpty(): Boolean {
      return this.getElements().isEmpty();
   }

   override fun <T> toArray(array: Array<T>): Array<T> {
      return (T[])CollectionToArray.toArray(this, array);
   }

   override fun toArray(): Array<Any> {
      return CollectionToArray.toArray(this);
   }
}
