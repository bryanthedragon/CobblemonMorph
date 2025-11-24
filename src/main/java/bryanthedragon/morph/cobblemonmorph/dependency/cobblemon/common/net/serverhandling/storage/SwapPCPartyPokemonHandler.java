/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.SwapPCPartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/storage/SwapPCPartyPokemonHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/storage/SwapPCPartyPokemonPacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/storage/SwapPCPartyPokemonPacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSwapPCPartyPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SwapPCPartyPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/SwapPCPartyPokemonHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,32:1\n1#2:33\n*E\n"})
public final class SwapPCPartyPokemonHandler
implements ServerNetworkPacketHandler<SwapPCPartyPokemonPacket> {
    @NotNull
    public static final SwapPCPartyPokemonHandler INSTANCE = new SwapPCPartyPokemonHandler();

    private SwapPCPartyPokemonHandler() {
    }

    @Override
    public void handle(@NotNull SwapPCPartyPokemonPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
        PCStore pCStore = PCLinkManager.INSTANCE.getPC(player);
        if (pCStore == null) {
            SwapPCPartyPokemonHandler $this$handle_u24lambda_u240 = this;
            boolean bl = false;
            new ClosePCPacket(null).sendToPlayer(player);
            return;
        }
        PCStore pc = pCStore;
        Pokemon pokemon = party.get(packet.getPartyPosition());
        if (pokemon == null) {
            return;
        }
        Pokemon partyPokemon = pokemon;
        Pokemon pokemon2 = pc.get(packet.getPcPosition());
        if (pokemon2 == null) {
            return;
        }
        Pokemon pcPokemon = pokemon2;
        if (!Intrinsics.areEqual((Object)partyPokemon.getUuid(), (Object)packet.getPartyPokemonID()) || !Intrinsics.areEqual((Object)pcPokemon.getUuid(), (Object)packet.getPcPokemonID())) {
            return;
        }
        party.set(packet.getPartyPosition(), pcPokemon);
        pc.set(packet.getPcPosition(), partyPokemon);
    }

    @Override
    public void handleOnNettyThread(@NotNull SwapPCPartyPokemonPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

