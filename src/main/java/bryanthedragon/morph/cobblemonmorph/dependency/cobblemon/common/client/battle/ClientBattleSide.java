package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle

import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nClientBattleSide.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientBattleSide.kt\ncom/cobblemon/mod/common/client/battle/ClientBattleSide\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,15:1\n1360#2:16\n1446#2,5:17\n*S KotlinDebug\n*F\n+ 1 ClientBattleSide.kt\ncom/cobblemon/mod/common/client/battle/ClientBattleSide\n*L\n14#1:16\n14#1:17,5\n*E\n"])
public class ClientBattleSide {
   public final val activeClientBattlePokemon: Iterable<ActiveClientBattlePokemon>
      public final get() {
         val `$this$flatMap$iv`: java.lang.Iterable = this.actors;
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$flatMap$iv) {
            CollectionsKt.addAll(`destination$iv$iv`, (`element$iv$iv` as ClientBattleActor).getActivePokemon());
         }

         return `destination$iv$iv`;
      }


   public final val actors: MutableList<ClientBattleActor> = (new ArrayList()) as java.util.List
   public final lateinit var battle: ClientBattle
}
