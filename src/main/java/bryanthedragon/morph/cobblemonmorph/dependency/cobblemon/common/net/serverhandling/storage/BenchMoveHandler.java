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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.BenchMovePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/storage/BenchMoveHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/BenchMovePacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/BenchMovePacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBenchMoveHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BenchMoveHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/BenchMoveHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,45:1\n1#2:46\n2624#3,3:47\n1747#3,3:50\n*S KotlinDebug\n*F\n+ 1 BenchMoveHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/BenchMoveHandler\n*L\n31#1:47,3\n31#1:50,3\n*E\n"})
public final class BenchMoveHandler
implements ServerNetworkPacketHandler<BenchMovePacket> {
    @NotNull
    public static final BenchMoveHandler INSTANCE = new BenchMoveHandler();

    private BenchMoveHandler() {
    }

    @Override
    public void handle(@NotNull BenchMovePacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        Pokemon pokemon;
        block18: {
            block17: {
                boolean bl;
                block16: {
                    Move it;
                    boolean bl2;
                    block15: {
                        PokemonStore pokemonStore;
                        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
                        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
                        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
                        if (packet.isParty()) {
                            pokemonStore = PlayerExtensionsKt.party(player);
                        } else {
                            PCStore pCStore = PCLinkManager.INSTANCE.getPC(player);
                            if (pCStore == null) {
                                BenchMoveHandler $this$handle_u24lambda_u240 = this;
                                boolean bl3 = false;
                                new ClosePCPacket(null).sendToPlayer(player);
                                return;
                            }
                            pokemonStore = pCStore;
                        }
                        PokemonStore pokemonStore2 = pokemonStore;
                        Pokemon pokemon2 = pokemonStore2.get(packet.getUuid());
                        if (pokemon2 == null) {
                            return;
                        }
                        pokemon = pokemon2;
                        Iterable $this$none$iv = pokemon.getMoveSet();
                        boolean $i$f$none = false;
                        if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                            bl2 = true;
                        } else {
                            for (Object element$iv : $this$none$iv) {
                                it = (Move)element$iv;
                                boolean bl4 = false;
                                if (!Intrinsics.areEqual((Object)it.getTemplate(), (Object)packet.getOldMove())) continue;
                                bl2 = false;
                                break block15;
                            }
                            bl2 = true;
                        }
                    }
                    if (bl2) break block17;
                    Iterable $this$any$iv = pokemon.getMoveSet();
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl = false;
                    } else {
                        for (Object element$iv : $this$any$iv) {
                            it = (Move)element$iv;
                            boolean bl5 = false;
                            if (!Intrinsics.areEqual((Object)it.getTemplate(), (Object)packet.getNewMove())) continue;
                            bl = true;
                            break block16;
                        }
                        bl = false;
                    }
                }
                if (!bl) break block18;
            }
            pokemon.getMoveSet().update();
            return;
        }
        if (!pokemon.getAllAccessibleMoves().contains(packet.getNewMove())) {
            Cobblemon.INSTANCE.getLOGGER().warn(player.m_7755_() + " tried to bench " + packet.getOldMove().getName() + " for " + packet.getNewMove().getName() + " but it doesn't have " + packet.getNewMove().getName() + " learned. Could be a hacker!");
            return;
        }
        pokemon.exchangeMove(packet.getOldMove(), packet.getNewMove());
    }

    @Override
    public void handleOnNettyThread(@NotNull BenchMovePacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

