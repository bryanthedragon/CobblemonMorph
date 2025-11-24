/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.evolution;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.evolution.AcceptEvolutionPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/evolution/AcceptEvolutionHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/pokemon/update/evolution/AcceptEvolutionPacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/pokemon/update/evolution/AcceptEvolutionPacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nAcceptEvolutionHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AcceptEvolutionHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/evolution/AcceptEvolutionHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,23:1\n288#2,2:24\n*S KotlinDebug\n*F\n+ 1 AcceptEvolutionHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/evolution/AcceptEvolutionHandler\n*L\n20#1:24,2\n*E\n"})
public final class AcceptEvolutionHandler
implements ServerNetworkPacketHandler<AcceptEvolutionPacket> {
    @NotNull
    public static final AcceptEvolutionHandler INSTANCE = new AcceptEvolutionHandler();

    private AcceptEvolutionHandler() {
    }

    @Override
    public void handle(@NotNull AcceptEvolutionPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        Object v1;
        Pokemon pokemon;
        block3: {
            Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Pokemon pokemon2 = PlayerExtensionsKt.party(player).get(packet.getPokemonUUID());
            if (pokemon2 == null) {
                return;
            }
            pokemon = pokemon2;
            Iterable $this$firstOrNull$iv = pokemon.getEvolutionProxy().server();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                Evolution evolution = (Evolution)element$iv;
                boolean bl = false;
                if (!StringsKt.equals((String)evolution.getId(), (String)packet.getEvolutionId(), (boolean)true)) continue;
                v1 = element$iv;
                break block3;
            }
            v1 = null;
        }
        Evolution evolution = v1;
        if (evolution == null) {
            return;
        }
        Evolution evolution2 = evolution;
        pokemon.getEvolutionProxy().server().start((Evolution)((EvolutionLike)evolution2));
    }

    @Override
    public void handleOnNettyThread(@NotNull AcceptEvolutionPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

