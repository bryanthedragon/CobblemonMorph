package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nTradeParticipant.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TradeParticipant.kt\ncom/cobblemon/mod/common/trade/DummyTradeParticipant\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,113:1\n1#2:114\n1855#3,2:115\n*S KotlinDebug\n*F\n+ 1 TradeParticipant.kt\ncom/cobblemon/mod/common/trade/DummyTradeParticipant\n*L\n96#1:115,2\n*E\n"])
public class DummyTradeParticipant(pokemonList: MutableList<Pokemon>) : TradeParticipant {
   public open val name: MutableComponent
   public open val party: PartyStore
   public final val pokemonList: MutableList<Pokemon>
   public open val uuid: UUID

   init {
      this.pokemonList = pokemonList;
      val var10001: UUID = UUID.randomUUID();
      this.uuid = var10001;
      this.name = TextKt.text("Debug Username");
      val var2: PartyStore = new PartyStore(this.getUuid());
      val it: PartyStore = var2;

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         it.add(`element$iv` as Pokemon);
      }

      this.party = var2;
   }

   public override fun cancelTrade(trade: ActiveTrade) {
   }

   public override fun completeTrade(trade: ActiveTrade, pokemonId1: UUID, pokemonId2: UUID) {
   }

   public override fun changeTradeAcceptance(trade: ActiveTrade, pokemonId: UUID, acceptance: Boolean) {
   }

   public override fun updateOffer(trade: ActiveTrade, tradeParticipant: TradeParticipant, pokemon: Pokemon?) {
   }
}
