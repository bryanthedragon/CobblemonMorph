/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.reflect.KClass
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.reflect.KClass;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J+\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\"\f\b\u0000\u0010\u0003*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0004\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\u0007\u0010\bJq\u0010\u0015\u001a\u00020\u000f\"\u000e\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\n\u001a\u00020\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b2\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00028\u00000\u00112\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013H&\u00a2\u0006\u0004\b\u0015\u0010\u0016Jq\u0010\u0018\u001a\u00020\u000f\"\u000e\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\n\u001a\u00020\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b2\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00028\u00000\u00112\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0017H&\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u000f\u0010\u001a\u001a\u00020\u000fH&\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u000f\u0010\u001c\u001a\u00020\u000fH&\u00a2\u0006\u0004\b\u001c\u0010\u001bJ#\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001d2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002H&\u00a2\u0006\u0004\b\u001f\u0010 J\u001b\u0010!\u001a\u00020\u000f2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002H&\u00a2\u0006\u0004\b!\u0010\"\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/NetworkManager;", "", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "T", "packet", "Lnet/minecraft/network/protocol/Packet;", "Lnet/minecraft/network/protocol/game/ClientGamePacketListener;", "asVanillaClientBound", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)Lnet/minecraft/network/protocol/Packet;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lkotlin/reflect/KClass;", "kClass", "Lkotlin/Function2;", "Lnet/minecraft/network/FriendlyByteBuf;", "", "encoder", "Lkotlin/Function1;", "decoder", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "handler", "createClientBound", "(Lnet/minecraft/resources/ResourceLocation;Lkotlin/reflect/KClass;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;)V", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "createServerBound", "(Lnet/minecraft/resources/ResourceLocation;Lkotlin/reflect/KClass;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;)V", "registerClientBound", "()V", "registerServerBound", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sendPacketToPlayer", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "sendPacketToServer", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "common"})
public interface NetworkManager {
    public void registerClientBound();

    public void registerServerBound();

    public <T extends NetworkPacket<T>> void createClientBound(@NotNull ResourceLocation var1, @NotNull KClass<T> var2, @NotNull Function2<? super T, ? super FriendlyByteBuf, Unit> var3, @NotNull Function1<? super FriendlyByteBuf, ? extends T> var4, @NotNull ClientNetworkPacketHandler<T> var5);

    public <T extends NetworkPacket<T>> void createServerBound(@NotNull ResourceLocation var1, @NotNull KClass<T> var2, @NotNull Function2<? super T, ? super FriendlyByteBuf, Unit> var3, @NotNull Function1<? super FriendlyByteBuf, ? extends T> var4, @NotNull ServerNetworkPacketHandler<T> var5);

    public void sendPacketToPlayer(@NotNull ServerPlayer var1, @NotNull NetworkPacket<?> var2);

    public void sendPacketToServer(@NotNull NetworkPacket<?> var1);

    @NotNull
    public <T extends NetworkPacket<?>> Packet<ClientGamePacketListener> asVanillaClientBound(@NotNull T var1);
}

