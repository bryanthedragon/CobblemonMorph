/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u0001*\u000e\b\u0001\u0010\u0004*\b\u0012\u0004\u0012\u00028\u00010\u00032\b\u0012\u0004\u0012\u00028\u00010\u0003B\u001f\u0012\u0006\u0010\u0016\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u0010\r\u001a\u00028\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u001f\u0010\u000b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\n\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\u00028\u00008\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0013\u001a\u0004\b\u0017\u0010\u0015\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/storage/MoveClientPokemonPacket;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "T", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "N", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "position", "encodePosition", "(Lnet/minecraft/network/FriendlyByteBuf;Lcom/cobblemon/mod/common/api/storage/StorePosition;)V", "newPosition", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "getNewPosition", "()Lcom/cobblemon/mod/common/api/storage/StorePosition;", "Ljava/util/UUID;", "pokemonID", "Ljava/util/UUID;", "getPokemonID", "()Ljava/util/UUID;", "storeID", "getStoreID", "<init>", "(Ljava/util/UUID;Ljava/util/UUID;Lcom/cobblemon/mod/common/api/storage/StorePosition;)V", "common"})
public abstract class MoveClientPokemonPacket<T extends StorePosition, N extends NetworkPacket<N>>
implements NetworkPacket<N> {
    @NotNull
    private final UUID storeID;
    @NotNull
    private final UUID pokemonID;
    @NotNull
    private final T newPosition;

    public MoveClientPokemonPacket(@NotNull UUID storeID, @NotNull UUID pokemonID, @NotNull T newPosition) {
        Intrinsics.checkNotNullParameter((Object)storeID, (String)"storeID");
        Intrinsics.checkNotNullParameter((Object)pokemonID, (String)"pokemonID");
        Intrinsics.checkNotNullParameter(newPosition, (String)"newPosition");
        this.storeID = storeID;
        this.pokemonID = pokemonID;
        this.newPosition = newPosition;
    }

    @NotNull
    public final UUID getStoreID() {
        return this.storeID;
    }

    @NotNull
    public final UUID getPokemonID() {
        return this.pokemonID;
    }

    @NotNull
    public final T getNewPosition() {
        return this.newPosition;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130077_(this.storeID);
        buffer.m_130077_(this.pokemonID);
        this.encodePosition(buffer, this.newPosition);
    }

    public abstract void encodePosition(@NotNull FriendlyByteBuf var1, @NotNull T var2);

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
}

