/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokemon.interact;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.interact.InteractPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/pokemon/interact/InteractPokemonHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/pokemon/interact/InteractPokemonPacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/pokemon/interact/InteractPokemonPacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nInteractPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InteractPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/pokemon/interact/InteractPokemonHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,32:1\n2624#2,3:33\n*S KotlinDebug\n*F\n+ 1 InteractPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/pokemon/interact/InteractPokemonHandler\n*L\n23#1:33,3\n*E\n"})
public final class InteractPokemonHandler
implements ServerNetworkPacketHandler<InteractPokemonPacket> {
    @NotNull
    public static final InteractPokemonHandler INSTANCE = new InteractPokemonHandler();

    private InteractPokemonHandler() {
    }

    @Override
    public void handle(@NotNull InteractPokemonPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        block8: {
            Entity pokemonEntity;
            block9: {
                block11: {
                    block10: {
                        boolean bl;
                        block7: {
                            Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
                            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
                            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
                            pokemonEntity = player.m_284548_().m_8791_(packet.getPokemonID());
                            if (!(pokemonEntity instanceof PokemonEntity)) break block8;
                            if (!packet.getMountShoulder()) break block9;
                            if (!((PokemonEntity)pokemonEntity).m_29897_()) break block10;
                            Iterable $this$none$iv = PlayerExtensionsKt.party(player);
                            boolean $i$f$none = false;
                            if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                                bl = true;
                            } else {
                                for (Object element$iv : $this$none$iv) {
                                    Pokemon it = (Pokemon)element$iv;
                                    boolean bl2 = false;
                                    if (!Intrinsics.areEqual((Object)it, (Object)((PokemonEntity)pokemonEntity).getPokemon())) continue;
                                    bl = false;
                                    break block7;
                                }
                                bl = true;
                            }
                        }
                        if (!bl) break block11;
                    }
                    return;
                }
                ((PokemonEntity)pokemonEntity).tryMountingShoulder(player);
                break block8;
            }
            PokemonEntity pokemonEntity2 = (PokemonEntity)pokemonEntity;
            Player player2 = (Player)player;
            ItemStack itemStack = player.m_21205_();
            Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"player.mainHandStack");
            pokemonEntity2.offerHeldItem(player2, itemStack);
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull InteractPokemonPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

