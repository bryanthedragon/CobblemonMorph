/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.JvmClassMappingKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.reflect.KClass
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraftforge.network.NetworkDirection
 *  net.minecraftforge.network.NetworkEvent$Context
 *  net.minecraftforge.network.NetworkRegistry
 *  net.minecraftforge.network.PacketDistributor
 *  net.minecraftforge.network.simple.SimpleChannel
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.net;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.NetworkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;

import java.util.function.Supplier;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import org.jetbrains.annotations.NotNull;

public final class CobblemonForgeNetworkManager implements NetworkManager {
    @NotNull
    public static final CobblemonForgeNetworkManager INSTANCE = new CobblemonForgeNetworkManager();
    @NotNull
    private static final String PROTOCOL_VERSION = "1";
    private static int id;
    private static final SimpleChannel channel;

    private CobblemonForgeNetworkManager() {
    }

    @Override
    public void registerClientBound() {
        CobblemonNetwork.INSTANCE.registerClientBound();
    }

    @Override
    public void registerServerBound() {
        CobblemonNetwork.INSTANCE.registerServerBound();
    }

    @Override
    public <T extends NetworkPacket<T>> void createClientBound(@NotNull ResourceLocation identifier, @NotNull KClass<T> kClass, @NotNull Function2<? super T, ? super FriendlyByteBuf, Unit> encoder, @NotNull Function1<? super FriendlyByteBuf, ? extends T> decoder, @NotNull ClientNetworkPacketHandler<T> handler) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter(kClass, (String)"kClass");
        Intrinsics.checkNotNullParameter(encoder, (String)"encoder");
        Intrinsics.checkNotNullParameter(decoder, (String)"decoder");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        int n = id;
        id = n + 1;
        channel.registerMessage(n, JvmClassMappingKt.getJavaClass(kClass), (p0$p1, p1$p2) -> encoder.invoke(p0$p1, p1$p2), arg_0 -> decoder.invoke(arg_0), (arg_0, arg_1) -> CobblemonForgeNetworkManager.createClientBound$lambda$1(handler, arg_0, arg_1));
    }

    @Override
    public <T extends NetworkPacket<T>> void createServerBound(@NotNull ResourceLocation identifier, @NotNull KClass<T> kClass, @NotNull Function2<? super T, ? super FriendlyByteBuf, Unit> encoder, @NotNull Function1<? super FriendlyByteBuf, ? extends T> decoder, @NotNull ServerNetworkPacketHandler<T> handler) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter(kClass, (String)"kClass");
        Intrinsics.checkNotNullParameter(encoder, (String)"encoder");
        Intrinsics.checkNotNullParameter(decoder, (String)"decoder");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        int n = id;
        id = n + 1;
        channel.registerMessage(n, JvmClassMappingKt.getJavaClass(kClass), (p0$p1, p1$p2) -> encoder.invoke(p0$p1, p1$p2), arg_0 -> decoder.invoke(arg_0), (arg_0, arg_1) -> CobblemonForgeNetworkManager.createServerBound$lambda$3(handler, arg_0, arg_1));
    }

    @Override
    public void sendPacketToPlayer(@NotNull ServerPlayer player, @NotNull NetworkPacket<?> packet) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        channel.send(PacketDistributor.PLAYER.with(() -> CobblemonForgeNetworkManager.sendPacketToPlayer$lambda$4(player)), packet);
    }

    @Override
    public void sendPacketToServer(@NotNull NetworkPacket<?> packet) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        channel.sendToServer(packet);
    }

    @Override
    @NotNull
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T extends NetworkPacket<?>> Packet<ClientGamePacketListener> asVanillaClientBound(@NotNull T packet) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        Packet packet2 = channel.toVanillaPacket(packet, NetworkDirection.PLAY_TO_CLIENT);
        Intrinsics.checkNotNull((Object)packet2, (String)"null cannot be cast to non-null type net.minecraft.network.packet.Packet<net.minecraft.network.listener.ClientPlayPacketListener>");
        return packet2;
    }

    private static final String channel$lambda$0() {
        return PROTOCOL_VERSION;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static final void createClientBound$lambda$1(ClientNetworkPacketHandler $handler, NetworkPacket msg, Supplier ctx) {
        Intrinsics.checkNotNullParameter((Object)$handler, (String)"$handler");
        NetworkEvent.Context context = (NetworkEvent.Context)ctx.get();
        Intrinsics.checkNotNullExpressionValue((Object)msg, (String)"msg");
        $handler.handleOnNettyThread(msg);
        context.setPacketHandled(true);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static final void createServerBound$lambda$3(ServerNetworkPacketHandler $handler, NetworkPacket msg, Supplier ctx) {
        Intrinsics.checkNotNullParameter((Object)$handler, (String)"$handler");
        NetworkEvent.Context context = (NetworkEvent.Context)ctx.get();
        Intrinsics.checkNotNullExpressionValue((Object)msg, (String)"msg");
        ServerPlayer serverPlayer = context.getSender();
        Intrinsics.checkNotNull((Object)serverPlayer);
        MinecraftServer minecraftServer = serverPlayer.f_8924_;
        Intrinsics.checkNotNullExpressionValue((Object)minecraftServer, (String)"context.sender!!.server");
        ServerPlayer serverPlayer2 = context.getSender();
        Intrinsics.checkNotNull((Object)serverPlayer2);
        $handler.handleOnNettyThread(msg, minecraftServer, serverPlayer2);
        context.setPacketHandled(true);
    }

    private static final ServerPlayer sendPacketToPlayer$lambda$4(ServerPlayer $player) {
        Intrinsics.checkNotNullParameter((Object)$player, (String)"$player");
        return $player;
    }

    static {
        channel = NetworkRegistry.newSimpleChannel((ResourceLocation)MiscUtils.cobblemonResource("main"), CobblemonForgeNetworkManager::channel$lambda$0, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    }
}

