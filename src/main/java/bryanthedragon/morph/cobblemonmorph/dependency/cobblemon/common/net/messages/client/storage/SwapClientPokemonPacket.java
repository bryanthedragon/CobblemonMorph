/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u001f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001fB%\b\u0016\u0012\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u001a\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u0011\u001a\u00020\f\u00a2\u0006\u0004\b\u001c\u0010\u001dB)\b\u0000\u0012\u0006\u0010\u0016\u001a\u00020\u0015\u0012\u0006\u0010\u0013\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u0011\u001a\u00020\f\u00a2\u0006\u0004\b\u001c\u0010\u001eJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u000e\u001a\u0004\b\u0012\u0010\u0010R\u0017\u0010\u0013\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0014\u0010\u0010R\u0017\u0010\u0016\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/net/messages/client/storage/SwapClientPokemonPacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Ljava/util/UUID;", "pokemonID1", "Ljava/util/UUID;", "getPokemonID1", "()Ljava/util/UUID;", "pokemonID2", "getPokemonID2", "storeID", "getStoreID", "", "storeIsParty", "Z", "getStoreIsParty", "()Z", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "store", "<init>", "(Lcom/cobblemon/mod/common/api/storage/PokemonStore;Ljava/util/UUID;Ljava/util/UUID;)V", "(ZLjava/util/UUID;Ljava/util/UUID;Ljava/util/UUID;)V", "Companion", "common"})
public final class SwapClientPokemonPacket
implements NetworkPacket<SwapClientPokemonPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final boolean storeIsParty;
    @NotNull
    private final UUID storeID;
    @NotNull
    private final UUID pokemonID1;
    @NotNull
    private final UUID pokemonID2;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtils.cobblemonResource("swap_client_pokemon");

    public SwapClientPokemonPacket(boolean storeIsParty, @NotNull UUID storeID, @NotNull UUID pokemonID1, @NotNull UUID pokemonID2) {
        Intrinsics.checkNotNullParameter((Object)storeID, (String)"storeID");
        Intrinsics.checkNotNullParameter((Object)pokemonID1, (String)"pokemonID1");
        Intrinsics.checkNotNullParameter((Object)pokemonID2, (String)"pokemonID2");
        this.storeIsParty = storeIsParty;
        this.storeID = storeID;
        this.pokemonID1 = pokemonID1;
        this.pokemonID2 = pokemonID2;
        this.id = ID;
    }

    public final boolean getStoreIsParty() {
        return this.storeIsParty;
    }

    @NotNull
    public final UUID getStoreID() {
        return this.storeID;
    }

    @NotNull
    public final UUID getPokemonID1() {
        return this.pokemonID1;
    }

    @NotNull
    public final UUID getPokemonID2() {
        return this.pokemonID2;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    public SwapClientPokemonPacket(@NotNull PokemonStore<?> store, @NotNull UUID pokemonID1, @NotNull UUID pokemonID2) {
        Intrinsics.checkNotNullParameter(store, (String)"store");
        Intrinsics.checkNotNullParameter((Object)pokemonID1, (String)"pokemonID1");
        Intrinsics.checkNotNullParameter((Object)pokemonID2, (String)"pokemonID2");
        this(store instanceof PartyStore, store.getUuid(), pokemonID1, pokemonID2);
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.writeBoolean(this.storeIsParty);
        buffer.m_130077_(this.storeID);
        buffer.m_130077_(this.pokemonID1);
        buffer.m_130077_(this.pokemonID2);
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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/storage/SwapClientPokemonPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/storage/SwapClientPokemonPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/storage/SwapClientPokemonPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final SwapClientPokemonPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            boolean bl = buffer.readBoolean();
            UUID uUID = buffer.m_130259_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"buffer.readUuid()");
            UUID uUID2 = buffer.m_130259_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"buffer.readUuid()");
            UUID uUID3 = buffer.m_130259_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID3, (String)"buffer.readUuid()");
            return new SwapClientPokemonPacket(bl, uUID, uUID2, uUID3);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

