/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00000\u00002\u00020\u0002J\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u001d\u0010\f\u001a\u00020\u00032\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0016\u00a2\u0006\u0004\b\f\u0010\rJS\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000e2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0014\b\u0002\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00170\u0016H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u000f\u0010\u001b\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u0005J\u000f\u0010\u001d\u001a\u00020\u001cH\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eR\u0014\u0010\"\u001a\u00020\u001f8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010!\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "T", "Lcom/cobblemon/mod/common/api/net/Encodable;", "", "sendToAllPlayers", "()V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sendToPlayer", "(Lnet/minecraft/server/level/ServerPlayer;)V", "", "players", "sendToPlayers", "(Ljava/lang/Iterable;)V", "", "x", "y", "z", "distance", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/level/Level;", "worldKey", "Lkotlin/Function1;", "", "exclusionCondition", "sendToPlayersAround", "(DDDDLnet/minecraft/resources/ResourceKey;Lkotlin/jvm/functions/Function1;)V", "sendToServer", "Lnet/minecraft/network/FriendlyByteBuf;", "toBuffer", "()Lnet/minecraft/network/FriendlyByteBuf;", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "id", "common"})
public interface NetworkPacket<T extends NetworkPacket<T>>
extends Encodable {
    @NotNull
    public ResourceLocation getId();

    public void sendToPlayer(@NotNull ServerPlayer var1);

    public void sendToPlayers(@NotNull Iterable<? extends ServerPlayer> var1);

    public void sendToAllPlayers();

    public void sendToServer();

    public void sendToPlayersAround(double var1, double var3, double var5, double var7, @NotNull ResourceKey<Level> var9, @NotNull Function1<? super ServerPlayer, Boolean> var10);

    @NotNull
    public FriendlyByteBuf toBuffer();

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nNetworkPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NetworkPacket.kt\ncom/cobblemon/mod/common/api/net/NetworkPacket$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,99:1\n766#2:100\n857#2,2:101\n1855#2,2:103\n*S KotlinDebug\n*F\n+ 1 NetworkPacket.kt\ncom/cobblemon/mod/common/api/net/NetworkPacket$DefaultImpls\n*L\n77#1:100\n77#1:101,2\n85#1:103,2\n*E\n"})
    public static final class DefaultImpls {
        public static <T extends NetworkPacket<T>> void sendToPlayer(@NotNull NetworkPacket<T> $this, @NotNull ServerPlayer player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            CobblemonNetwork.INSTANCE.sendPacketToPlayer(player, $this);
        }

        public static <T extends NetworkPacket<T>> void sendToPlayers(@NotNull NetworkPacket<T> $this, @NotNull Iterable<? extends ServerPlayer> players2) {
            Intrinsics.checkNotNullParameter(players2, (String)"players");
            if (CollectionsKt.any(players2)) {
                CobblemonNetwork.INSTANCE.sendPacketToPlayers(players2, $this);
            }
        }

        public static <T extends NetworkPacket<T>> void sendToAllPlayers(@NotNull NetworkPacket<T> $this) {
            CobblemonNetwork.INSTANCE.sendToAllPlayers($this);
        }

        public static <T extends NetworkPacket<T>> void sendToServer(@NotNull NetworkPacket<T> $this) {
            CobblemonNetwork.INSTANCE.sendPacketToServer($this);
        }

        /*
         * WARNING - void declaration
         */
        public static <T extends NetworkPacket<T>> void sendToPlayersAround(@NotNull NetworkPacket<T> $this, double x, double y, double z, double distance, @NotNull ResourceKey<Level> worldKey, @NotNull Function1<? super ServerPlayer, Boolean> exclusionCondition) {
            void $this$forEach$iv;
            void $this$filterTo$iv$iv;
            Intrinsics.checkNotNullParameter(worldKey, (String)"worldKey");
            Intrinsics.checkNotNullParameter(exclusionCondition, (String)"exclusionCondition");
            MinecraftServer minecraftServer = DistributionUtilsKt.server();
            if (minecraftServer == null) {
                return;
            }
            MinecraftServer server = minecraftServer;
            List list = server.m_6846_().m_11314_();
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"server.playerManager.playerList");
            Iterable $this$filter$iv = list;
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                double zDiff;
                double yDiff;
                double xDiff;
                ServerPlayer player = (ServerPlayer)element$iv$iv;
                boolean bl = false;
                Intrinsics.checkNotNullExpressionValue((Object)player, (String)"player");
                if (!((Boolean)exclusionCondition.invoke((Object)player) != false ? false : (xDiff = x - player.m_20185_()) * xDiff + (yDiff = y - player.m_20186_()) * yDiff + (zDiff = z - player.m_20189_()) < distance * distance)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filter$iv = (List)destination$iv$iv;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                ServerPlayer player = (ServerPlayer)element$iv;
                boolean bl = false;
                Intrinsics.checkNotNullExpressionValue((Object)player, (String)"player");
                CobblemonNetwork.INSTANCE.sendPacketToPlayer(player, $this);
            }
        }

        public static /* synthetic */ void sendToPlayersAround$default(NetworkPacket networkPacket, double d, double d2, double d3, double d4, ResourceKey resourceKey, Function1 function1, int n, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sendToPlayersAround");
            }
            if ((n & 0x20) != 0) {
                function1 = sendToPlayersAround.1.INSTANCE;
            }
            networkPacket.sendToPlayersAround(d, d2, d3, d4, (ResourceKey<Level>)resourceKey, (Function1<ServerPlayer, Boolean>)function1);
        }

        @NotNull
        public static <T extends NetworkPacket<T>> FriendlyByteBuf toBuffer(@NotNull NetworkPacket<T> $this) {
            FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
            $this.encode(buffer);
            return buffer;
        }
    }
}

