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
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u001e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u000e\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u00028\u00010\u00022\b\u0012\u0004\u0012\u00028\u00010\u0002B\u0015\u0012\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010\u00a2\u0006\u0004\b\u001f\u0010\u0013J\u0017\u0010\t\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0000\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\n\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\f\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\f\u0010\bJ\u001f\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\r\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001d\u0010\u0012\u001a\u00020\u00062\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010H&\u00a2\u0006\u0004\b\u0012\u0010\u0013R$\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0005\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\bR*\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00028\u00000\u0018j\b\u0012\u0004\u0012\u00028\u0000`\u00198\u0000X\u0080\u0004\u00a2\u0006\f\n\u0004\b\u0011\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00000\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\u001e\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/net/messages/client/data/DataRegistrySyncPacket;", "T", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "N", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decodeBuffer$common", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "decodeBuffer", "decodeEntry", "(Lnet/minecraft/network/FriendlyByteBuf;)Ljava/lang/Object;", "encode", "entry", "encodeEntry", "(Lnet/minecraft/network/FriendlyByteBuf;Ljava/lang/Object;)V", "", "entries", "synchronizeDecoded", "(Ljava/util/Collection;)V", "Lnet/minecraft/network/FriendlyByteBuf;", "getBuffer", "()Lnet/minecraft/network/FriendlyByteBuf;", "setBuffer", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "Ljava/util/ArrayList;", "getEntries$common", "()Ljava/util/ArrayList;", "registryEntries", "Ljava/util/Collection;", "<init>", "common"})
public abstract class DataRegistrySyncPacket<T, N extends NetworkPacket<N>>
implements NetworkPacket<N> {
    @NotNull
    private final Collection<T> registryEntries;
    @Nullable
    private FriendlyByteBuf buffer;
    @NotNull
    private final ArrayList<T> entries;

    public DataRegistrySyncPacket(@NotNull Collection<? extends T> registryEntries) {
        Intrinsics.checkNotNullParameter(registryEntries, (String)"registryEntries");
        this.registryEntries = registryEntries;
        this.entries = new ArrayList();
    }

    @Nullable
    public final FriendlyByteBuf getBuffer() {
        return this.buffer;
    }

    public final void setBuffer(@Nullable FriendlyByteBuf friendlyByteBuf) {
        this.buffer = friendlyByteBuf;
    }

    @NotNull
    public final ArrayList<T> getEntries$common() {
        return this.entries;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_236828_(this.registryEntries, this::encodeEntry);
    }

    public final void decodeBuffer$common(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.buffer = buffer;
        buffer.retain();
    }

    public abstract void encodeEntry(@NotNull FriendlyByteBuf var1, T var2);

    @Nullable
    public abstract T decodeEntry(@NotNull FriendlyByteBuf var1);

    public abstract void synchronizeDecoded(@NotNull Collection<? extends T> var1);

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

