package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.BattleSelectActionsPacket
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nClientBattle.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientBattle.kt\ncom/cobblemon/mod/common/client/battle/ClientBattle\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,58:1\n288#2,2:59\n1549#2:61\n1620#2,3:62\n10242#3:65\n10664#3,5:66\n10242#3:72\n10664#3,5:73\n1#4:71\n*S KotlinDebug\n*F\n+ 1 ClientBattle.kt\ncom/cobblemon/mod/common/client/battle/ClientBattle\n*L\n33#1:59,2\n39#1:61\n39#1:62,3\n47#1:65\n47#1:66,5\n56#1:72\n56#1:73,5\n*E\n"])
public class ClientBattle(battleId: UUID, battleFormat: BattleFormat) {
   public final val battleFormat: BattleFormat
   public final val battleId: UUID
   public final val messages: ClientBattleMessageQueue
   public final var minimised: Boolean
   public final var mustChoose: Boolean
   public final var pendingActionRequests: MutableList<SingleActionRequest>
   public final val side1: ClientBattleSide
   public final val side2: ClientBattleSide

   public final val sides: Array<ClientBattleSide>
      public final get() {
         return new ClientBattleSide[]{this.side1, this.side2};
      }


   public final var spectating: Boolean

   init {
      this.battleId = battleId;
      this.battleFormat = battleFormat;
      this.minimised = true;
      this.side1 = new ClientBattleSide();
      this.side2 = new ClientBattleSide();
      this.pendingActionRequests = new ArrayList<>();
      this.messages = new ClientBattleMessageQueue();
   }

   public fun getFirstUnansweredRequest(): SingleActionRequest? {
      val var3: java.util.Iterator = this.pendingActionRequests.iterator();

      var var10000: Any;
      while (true) {
         if (var3.hasNext()) {
            val `element$iv`: Any = var3.next();
            if ((`element$iv` as SingleActionRequest).getResponse() != null) {
               continue;
            }

            var10000 = `element$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as SingleActionRequest;
   }

   public fun checkForFinishedChoosing() {
      if (this.getFirstUnansweredRequest() == null) {
         val `$this$map$iv`: java.lang.Iterable = this.pendingActionRequests;
         val var11: UUID = this.battleId;
         val var10: CobblemonNetwork = CobblemonNetwork.INSTANCE;
         val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

         for (Object item$iv$iv : $this$map$iv) {
            val var10000: ShowdownActionResponse = (`item$iv$iv` as SingleActionRequest).getResponse();
            `destination$iv$iv`.add(var10000);
         }

         var10.sendPacketToServer(new BattleSelectActionsPacket(var11, `destination$iv$iv` as MutableList<ShowdownActionResponse>));
         this.mustChoose = false;
      }
   }

   public fun getPokemonFromPNX(pnx: String): Pair<ClientBattleActor, ActiveClientBattlePokemon> {
      val pokemon: Array<Any> = this.getSides();
      var `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$flatMap$iv) {
         CollectionsKt.addAll(`destination$iv$iv`, ((ClientBattleSide)var11).getActors());
      }

      val var16: java.util.Iterator = (`destination$iv$iv` as java.util.List).iterator();

      var var10000: Any;
      while (true) {
         if (var16.hasNext()) {
            `destination$iv$iv` = (java.util.Collection)var16.next();
            var10000 = (`destination$iv$iv` as ClientBattleActor).getShowdownId();
            val var10001: java.lang.String = pnx.substring(0, 2);
            if (!(var10000 == var10001)) {
               continue;
            }

            var10000 = `destination$iv$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      var10000 = var10000 as ClientBattleActor;
      if (var10000 as ClientBattleActor == null) {
         throw new IllegalStateException("Invalid pnx: $pnx - unknown actor");
      } else {
         val letter: Char = pnx.charAt(2);
         val var20: java.util.Iterator = ((ClientBattleActor)var10000).getSide().getActiveClientBattlePokemon().iterator();

         while (true) {
            if (var20.hasNext()) {
               val var22: Any = var20.next();
               if ((var22 as ActiveClientBattlePokemon).getLetter() != letter) {
                  continue;
               }

               var10000 = var22;
               break;
            }

            var10000 = null;
            break;
         }

         var10000 = var10000 as ActiveClientBattlePokemon;
         if (var10000 as ActiveClientBattlePokemon == null) {
            throw new IllegalStateException("Invalid pnx: $pnx - unknown pokemon");
         } else {
            return TuplesKt.to(var10000, var10000);
         }
      }
   }

   public fun getParticipatingActor(uuid: UUID): ClientBattleActor? {
      val `$this$flatMap$iv`: Array<Any> = this.getSides();
      var `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$flatMap$iv) {
         CollectionsKt.addAll(`destination$iv$iv`, ((ClientBattleSide)`element$iv$iv`).getActors());
      }

      val var13: java.util.Iterator = (`destination$iv$iv` as java.util.List).iterator();

      var var10000: Any;
      while (true) {
         if (var13.hasNext()) {
            `destination$iv$iv` = (java.util.Collection)var13.next();
            if (!((`destination$iv$iv` as ClientBattleActor).getUuid() == uuid)) {
               continue;
            }

            var10000 = `destination$iv$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as ClientBattleActor;
   }
}
