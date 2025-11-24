/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.ActiveTrade;
import java.util.UUID;
import kotlin.Metadata;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J'\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH&\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\u000f\u0010\u0010J)\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00002\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012H&\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0019\u001a\u00020\u00168&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u001d\u001a\u00020\u001a8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010 \u001a\u00020\u00078&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/trade/TradeParticipant;", "", "Lcom/cobblemon/mod/common/trade/ActiveTrade;", "trade", "", "cancelTrade", "(Lcom/cobblemon/mod/common/trade/ActiveTrade;)V", "Ljava/util/UUID;", "pokemonId", "", "acceptance", "changeTradeAcceptance", "(Lcom/cobblemon/mod/common/trade/ActiveTrade;Ljava/util/UUID;Z)V", "pokemonId1", "pokemonId2", "completeTrade", "(Lcom/cobblemon/mod/common/trade/ActiveTrade;Ljava/util/UUID;Ljava/util/UUID;)V", "tradeParticipant", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "updateOffer", "(Lcom/cobblemon/mod/common/trade/ActiveTrade;Lcom/cobblemon/mod/common/trade/TradeParticipant;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "Lnet/minecraft/network/chat/Component;", "getName", "()Lnet/minecraft/network/chat/Component;", "name", "Lcom/cobblemon/mod/common/api/storage/party/PartyStore;", "getParty", "()Lcom/cobblemon/mod/common/api/storage/party/PartyStore;", "party", "getUuid", "()Ljava/util/UUID;", "uuid", "common"})
public interface TradeParticipant {
    @NotNull
    public UUID getUuid();

    @NotNull
    public Component getName();

    @NotNull
    public PartyStore getParty();

    public void updateOffer(@NotNull ActiveTrade var1, @NotNull TradeParticipant var2, @Nullable Pokemon var3);

    public void changeTradeAcceptance(@NotNull ActiveTrade var1, @NotNull UUID var2, boolean var3);

    public void cancelTrade(@NotNull ActiveTrade var1);

    public void completeTrade(@NotNull ActiveTrade var1, @NotNull UUID var2, @NotNull UUID var3);
}

