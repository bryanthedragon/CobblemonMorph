/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.PokemonDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00182\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0018B\u0019\u0012\u0006\u0010\r\u001a\u00020\f\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/trade/TradeUpdatedPacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Ljava/util/UUID;", "playerId", "Ljava/util/UUID;", "getPlayerId", "()Ljava/util/UUID;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "<init>", "(Ljava/util/UUID;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "Companion", "common"})
public final class TradeUpdatedPacket
implements NetworkPacket<TradeUpdatedPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final UUID playerId;
    @Nullable
    private final Pokemon pokemon;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("trade_updated");

    public TradeUpdatedPacket(@NotNull UUID playerId, @Nullable Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)playerId, (String)"playerId");
        this.playerId = playerId;
        this.pokemon = pokemon;
        this.id = ID;
    }

    @NotNull
    public final UUID getPlayerId() {
        return this.playerId;
    }

    @Nullable
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130077_(this.playerId);
        buffer.m_236821_((Object)this.pokemon, (arg_0, arg_1) -> TradeUpdatedPacket.encode$lambda$0(buffer, arg_0, arg_1));
    }

    @Override
    public void sendToPlayer(@NotNull ServerPlayer player) {
        NetworkPacket.DefaultImpls.sendToPlayer(this, player);
    }

    @Override
    public void sendToPlayers(@NotNull Iterable<? extends ServerPlayer> players2) {
        NetworkPacket.DefaultImpls.sendToPlayers(this, players2);
    }

    @Override
    public void sendToAllPlayers() {
        NetworkPacket.DefaultImpls.sendToAllPlayers(this);
    }

    @Override
    public void sendToServer() {
        NetworkPacket.DefaultImpls.sendToServer(this);
    }

    @Override
    public void sendToPlayersAround(double x, double y, double z, double distance, @NotNull ResourceKey<Level> worldKey, @NotNull Function1<? super ServerPlayer, Boolean> exclusionCondition) {
        NetworkPacket.DefaultImpls.sendToPlayersAround(this, x, y, z, distance, worldKey, exclusionCondition);
    }

    @Override
    @NotNull
    public FriendlyByteBuf toBuffer() {
        return NetworkPacket.DefaultImpls.toBuffer(this);
    }

    private static final void encode$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        Intrinsics.checkNotNullExpressionValue((Object)pokemon, (String)"pokemon");
        new PokemonDTO(pokemon, true).encode($buffer);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/trade/TradeUpdatedPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/trade/TradeUpdatedPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/trade/TradeUpdatedPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nTradeUpdatedPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TradeUpdatedPacket.kt\ncom/cobblemon/mod/common/net/messages/client/trade/TradeUpdatedPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,37:1\n1#2:38\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final TradeUpdatedPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            UUID uUID = buffer.m_130259_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"buffer.readUuid()");
            return new TradeUpdatedPacket(uUID, (Pokemon)buffer.m_236868_(arg_0 -> Companion.decode$lambda$1(buffer, arg_0)));
        }

        private static final Pokemon decode$lambda$1(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
            PokemonDTO pokemonDTO;
            Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
            PokemonDTO it2 = pokemonDTO = new PokemonDTO();
            boolean bl = false;
            it2.decode($buffer);
            return pokemonDTO.create();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

