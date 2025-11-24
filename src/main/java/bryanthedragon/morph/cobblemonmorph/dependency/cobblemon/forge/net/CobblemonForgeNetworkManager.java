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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.forge.net;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.NetworkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b-\u0010\u001bJ+\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\"\f\b\u0000\u0010\u0003*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0004\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJq\u0010\u0015\u001a\u00020\u000f\"\u000e\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\n\u001a\u00020\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b2\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00028\u00000\u00112\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016Jq\u0010\u0018\u001a\u00020\u000f\"\u000e\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\n\u001a\u00020\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b2\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00028\u00000\u00112\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0017H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u000f\u0010\u001a\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u000f\u0010\u001c\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001bJ#\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001d2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0016\u00a2\u0006\u0004\b\u001f\u0010 J\u001b\u0010!\u001a\u00020\u000f2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0016\u00a2\u0006\u0004\b!\u0010\"R\u0014\u0010$\u001a\u00020#8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b$\u0010%R\u001c\u0010(\u001a\n '*\u0004\u0018\u00010&0&8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b(\u0010)R\u0016\u0010+\u001a\u00020*8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b+\u0010,\u00a8\u0006."}, d2={"Lcom/cobblemon/mod/forge/net/CobblemonForgeNetworkManager;", "Lcom/cobblemon/mod/common/NetworkManager;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "T", "packet", "Lnet/minecraft/network/protocol/Packet;", "Lnet/minecraft/network/protocol/game/ClientGamePacketListener;", "asVanillaClientBound", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)Lnet/minecraft/network/protocol/Packet;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lkotlin/reflect/KClass;", "kClass", "Lkotlin/Function2;", "Lnet/minecraft/network/FriendlyByteBuf;", "", "encoder", "Lkotlin/Function1;", "decoder", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "handler", "createClientBound", "(Lnet/minecraft/resources/ResourceLocation;Lkotlin/reflect/KClass;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;)V", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "createServerBound", "(Lnet/minecraft/resources/ResourceLocation;Lkotlin/reflect/KClass;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;)V", "registerClientBound", "()V", "registerServerBound", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sendPacketToPlayer", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "sendPacketToServer", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "", "PROTOCOL_VERSION", "Ljava/lang/String;", "Lnet/minecraftforge/network/simple/SimpleChannel;", "kotlin.jvm.PlatformType", "channel", "Lnet/minecraftforge/network/simple/SimpleChannel;", "", "id", "I", "<init>", "forge"})
public final class CobblemonForgeNetworkManager
implements NetworkManager {
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
    public <T extends NetworkPacket<?>> Packet<ClientGamePacketListener> asVanillaClientBound(@NotNull T packet) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        Packet packet2 = channel.toVanillaPacket(packet, NetworkDirection.PLAY_TO_CLIENT);
        Intrinsics.checkNotNull((Object)packet2, (String)"null cannot be cast to non-null type net.minecraft.network.packet.Packet<net.minecraft.network.listener.ClientPlayPacketListener>");
        return packet2;
    }

    private static final String channel$lambda$0() {
        return PROTOCOL_VERSION;
    }

    private static final void createClientBound$lambda$1(ClientNetworkPacketHandler $handler, NetworkPacket msg, Supplier ctx) {
        Intrinsics.checkNotNullParameter((Object)$handler, (String)"$handler");
        NetworkEvent.Context context = (NetworkEvent.Context)ctx.get();
        Intrinsics.checkNotNullExpressionValue((Object)msg, (String)"msg");
        $handler.handleOnNettyThread(msg);
        context.setPacketHandled(true);
    }

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
        channel = NetworkRegistry.newSimpleChannel((ResourceLocation)MiscUtilsKt.cobblemonResource("main"), CobblemonForgeNetworkManager::channel$lambda$0, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    }
}

