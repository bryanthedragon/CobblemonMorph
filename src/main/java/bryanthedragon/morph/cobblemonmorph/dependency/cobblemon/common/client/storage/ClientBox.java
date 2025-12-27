package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.markers.KMappedMarker

@SourceDebugExtension(["SMAP\nClientBox.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientBox.kt\ncom/cobblemon/mod/common/client/storage/ClientBox\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,16:1\n1#2:17\n*E\n"])
public class ClientBox : java.lang.Iterable<Pokemon>, KMappedMarker {
   public final val slots: MutableList<Pokemon?>

   public override operator fun iterator(): MutableIterator<Pokemon?> {
      return this.slots.iterator();
   }
}
