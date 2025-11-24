/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.ActiveTrade;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeParticipant;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0015\u0012\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00120 \u00a2\u0006\u0004\b)\u0010*J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J'\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J)\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0017\u001a\u00020\u00168\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001c\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u001d\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00120 8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/trade/DummyTradeParticipant;", "Lcom/cobblemon/mod/common/trade/TradeParticipant;", "Lcom/cobblemon/mod/common/trade/ActiveTrade;", "trade", "", "cancelTrade", "(Lcom/cobblemon/mod/common/trade/ActiveTrade;)V", "Ljava/util/UUID;", "pokemonId", "", "acceptance", "changeTradeAcceptance", "(Lcom/cobblemon/mod/common/trade/ActiveTrade;Ljava/util/UUID;Z)V", "pokemonId1", "pokemonId2", "completeTrade", "(Lcom/cobblemon/mod/common/trade/ActiveTrade;Ljava/util/UUID;Ljava/util/UUID;)V", "tradeParticipant", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "updateOffer", "(Lcom/cobblemon/mod/common/trade/ActiveTrade;Lcom/cobblemon/mod/common/trade/TradeParticipant;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "Lnet/minecraft/network/chat/MutableComponent;", "name", "Lnet/minecraft/network/chat/MutableComponent;", "getName", "()Lnet/minecraft/network/chat/MutableComponent;", "Lcom/cobblemon/mod/common/api/storage/party/PartyStore;", "party", "Lcom/cobblemon/mod/common/api/storage/party/PartyStore;", "getParty", "()Lcom/cobblemon/mod/common/api/storage/party/PartyStore;", "", "pokemonList", "Ljava/util/List;", "getPokemonList", "()Ljava/util/List;", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "<init>", "(Ljava/util/List;)V", "common"})
@SourceDebugExtension(value={"SMAP\nTradeParticipant.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TradeParticipant.kt\ncom/cobblemon/mod/common/trade/DummyTradeParticipant\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,113:1\n1#2:114\n1855#3,2:115\n*S KotlinDebug\n*F\n+ 1 TradeParticipant.kt\ncom/cobblemon/mod/common/trade/DummyTradeParticipant\n*L\n96#1:115,2\n*E\n"})
public final class DummyTradeParticipant
implements TradeParticipant {
    @NotNull
    private final List<Pokemon> pokemonList;
    @NotNull
    private final UUID uuid;
    @NotNull
    private final MutableComponent name;
    @NotNull
    private final PartyStore party;

    /*
     * WARNING - void declaration
     */
    public DummyTradeParticipant(@NotNull List<Pokemon> pokemonList) {
        PartyStore partyStore;
        Intrinsics.checkNotNullParameter(pokemonList, (String)"pokemonList");
        this.pokemonList = pokemonList;
        UUID uUID = UUID.randomUUID();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"randomUUID()");
        this.uuid = uUID;
        this.name = TextKt.text("Debug Username");
        PartyStore partyStore2 = partyStore = new PartyStore(this.getUuid());
        DummyTradeParticipant dummyTradeParticipant = this;
        boolean bl = false;
        Iterable $this$forEach$iv = this.pokemonList;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            void it;
            Pokemon p0 = (Pokemon)element$iv;
            boolean bl2 = false;
            it.add(p0);
        }
        dummyTradeParticipant.party = partyStore;
    }

    @NotNull
    public final List<Pokemon> getPokemonList() {
        return this.pokemonList;
    }

    @Override
    @NotNull
    public UUID getUuid() {
        return this.uuid;
    }

    @NotNull
    public MutableComponent getName() {
        return this.name;
    }

    @Override
    @NotNull
    public PartyStore getParty() {
        return this.party;
    }

    @Override
    public void cancelTrade(@NotNull ActiveTrade trade2) {
        Intrinsics.checkNotNullParameter((Object)trade2, (String)"trade");
    }

    @Override
    public void completeTrade(@NotNull ActiveTrade trade2, @NotNull UUID pokemonId1, @NotNull UUID pokemonId2) {
        Intrinsics.checkNotNullParameter((Object)trade2, (String)"trade");
        Intrinsics.checkNotNullParameter((Object)pokemonId1, (String)"pokemonId1");
        Intrinsics.checkNotNullParameter((Object)pokemonId2, (String)"pokemonId2");
    }

    @Override
    public void changeTradeAcceptance(@NotNull ActiveTrade trade2, @NotNull UUID pokemonId, boolean acceptance) {
        Intrinsics.checkNotNullParameter((Object)trade2, (String)"trade");
        Intrinsics.checkNotNullParameter((Object)pokemonId, (String)"pokemonId");
    }

    @Override
    public void updateOffer(@NotNull ActiveTrade trade2, @NotNull TradeParticipant tradeParticipant, @Nullable Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)trade2, (String)"trade");
        Intrinsics.checkNotNullParameter((Object)tradeParticipant, (String)"tradeParticipant");
    }
}

