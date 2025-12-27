package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nCobbledAdapterParent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobbledAdapterParent.kt\ncom/cobblemon/mod/common/api/storage/adapter/CobblemonAdapterParent\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,36:1\n1#2:37\n*E\n"])
public abstract class CobblemonAdapterParent<S> : CobblemonAdapter<S> {
   public final val children: MutableList<CobblemonAdapter<*>> = (new ArrayList()) as java.util.List

   public fun with(vararg children: CobblemonAdapter<*>): CobblemonAdapter<Any> {
      CollectionsKt.addAll(this.children, children);
      return this;
   }

   public override fun <E : StorePosition, T : PokemonStore<Any>> load(storeClass: Class<Any>, uuid: UUID): Any? {
      var var10000: PokemonStore = this.provide(storeClass, uuid);
      if (var10000 == null) {
         val var3: java.util.Iterator = this.children.iterator();

         while (true) {
            if (!var3.hasNext()) {
               var10000 = null;
               break;
            }

            val var6: PokemonStore = (var3.next() as CobblemonAdapter).load(storeClass, uuid);
            if (var6 != null) {
               var10000 = var6;
               break;
            }
         }
      }

      return (T)var10000;
   }

   public abstract fun <E : StorePosition, T : PokemonStore<Any>> provide(storeClass: Class<Any>, uuid: UUID): Any? {
   }
}
