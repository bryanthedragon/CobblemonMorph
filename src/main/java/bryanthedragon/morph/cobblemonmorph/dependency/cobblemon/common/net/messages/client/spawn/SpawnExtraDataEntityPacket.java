/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.PacketListener
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.PacketUtils
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.thread.BlockableEventLoop
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.ClientPlayNetworkHandlerInvoker;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u0000 \u001a*\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u00028\u00000\u0001*\b\b\u0001\u0010\u0004*\u00020\u00032\b\u0012\u0004\u0012\u00028\u00000\u0001:\u0001\u001aB\u000f\u0012\u0006\u0010\u0016\u001a\u00020\u0015\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00028\u0001H&\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\n\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0003H&\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\u000e\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0010\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\fH&\u00a2\u0006\u0004\b\u0010\u0010\u000fJ\u0015\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0016\u001a\u00020\u00158\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnExtraDataEntityPacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "T", "Lnet/minecraft/world/entity/Entity;", "E", "entity", "", "applyData", "(Lnet/minecraft/world/entity/Entity;)V", "", "checkType", "(Lnet/minecraft/world/entity/Entity;)Z", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encodeEntityData", "Lnet/minecraft/client/Minecraft;", "client", "spawnAndApply", "(Lnet/minecraft/client/Minecraft;)V", "Lnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;", "vanillaSpawnPacket", "Lnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;", "<init>", "(Lnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;)V", "Companion", "common"})
public abstract class SpawnExtraDataEntityPacket<T extends NetworkPacket<T>, E extends Entity>
implements NetworkPacket<T> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ClientboundAddEntityPacket vanillaSpawnPacket;

    public SpawnExtraDataEntityPacket(@NotNull ClientboundAddEntityPacket vanillaSpawnPacket) {
        Intrinsics.checkNotNullParameter((Object)vanillaSpawnPacket, (String)"vanillaSpawnPacket");
        this.vanillaSpawnPacket = vanillaSpawnPacket;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.encodeEntityData(buffer);
        this.vanillaSpawnPacket.m_5779_(buffer);
    }

    public abstract void encodeEntityData(@NotNull FriendlyByteBuf var1);

    public abstract void applyData(@NotNull E var1);

    public abstract boolean checkType(@NotNull Entity var1);

    public final void spawnAndApply(@NotNull Minecraft client) {
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        client.execute(() -> SpawnExtraDataEntityPacket.spawnAndApply$lambda$0(client, this));
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

    private static final void spawnAndApply$lambda$0(Minecraft $client, SpawnExtraDataEntityPacket this$0) {
        Intrinsics.checkNotNullParameter((Object)$client, (String)"$client");
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        LocalPlayer localPlayer = $client.f_91074_;
        if (localPlayer == null) {
            return;
        }
        LocalPlayer player = localPlayer;
        Level level = player.m_9236_();
        ClientLevel clientLevel = level instanceof ClientLevel ? (ClientLevel)level : null;
        if (clientLevel == null) {
            return;
        }
        ClientLevel world = clientLevel;
        PacketUtils.m_131363_((Packet)((Packet)this$0.vanillaSpawnPacket), (PacketListener)((PacketListener)player.f_108617_), (BlockableEventLoop)((BlockableEventLoop)$client));
        EntityType entityType = this$0.vanillaSpawnPacket.m_131508_();
        Entity entity2 = entityType.m_20615_((Level)world);
        if (entity2 == null) {
            return;
        }
        Entity entity3 = entity2;
        entity3.m_141965_(this$0.vanillaSpawnPacket);
        entity3.m_20256_(new Vec3(this$0.vanillaSpawnPacket.m_131503_(), this$0.vanillaSpawnPacket.m_131504_(), this$0.vanillaSpawnPacket.m_131505_()));
        if (this$0.checkType(entity3)) {
            this$0.applyData(entity3);
        }
        world.m_104627_(this$0.vanillaSpawnPacket.m_131496_(), entity3);
        ClientPacketListener clientPacketListener = player.f_108617_;
        Intrinsics.checkNotNull((Object)clientPacketListener, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.ClientPlayNetworkHandlerInvoker");
        ((ClientPlayNetworkHandlerInvoker)clientPacketListener).callPlaySpawnSound(entity3);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnExtraDataEntityPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;", "decodeVanillaPacket", "(Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ClientboundAddEntityPacket decodeVanillaPacket(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            return new ClientboundAddEntityPacket(buffer);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

