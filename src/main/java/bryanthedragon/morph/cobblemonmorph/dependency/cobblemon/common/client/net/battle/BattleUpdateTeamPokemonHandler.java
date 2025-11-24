/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/battle/BattleUpdateTeamPokemonHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleUpdateTeamPokemonPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/battle/BattleUpdateTeamPokemonPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleUpdateTeamPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleUpdateTeamPokemonHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleUpdateTeamPokemonHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,28:1\n1#2:29\n*E\n"})
public final class BattleUpdateTeamPokemonHandler
implements ClientNetworkPacketHandler<BattleUpdateTeamPokemonPacket> {
    @NotNull
    public static final BattleUpdateTeamPokemonHandler INSTANCE = new BattleUpdateTeamPokemonHandler();

    private BattleUpdateTeamPokemonHandler() {
    }

    @Override
    public void handle(@NotNull BattleUpdateTeamPokemonPacket packet, @NotNull Minecraft client) {
        Object object;
        block5: {
            Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
            Intrinsics.checkNotNullParameter((Object)client, (String)"client");
            ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
            if (clientBattle == null) {
                return;
            }
            ClientBattle battle2 = clientBattle;
            Iterable iterable = battle2.getSide1().getActors();
            for (Object object2 : iterable) {
                ClientBattleActor it = (ClientBattleActor)object2;
                boolean bl = false;
                LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
                if (!Intrinsics.areEqual((Object)it.getUuid(), (Object)(localPlayer != null ? localPlayer.m_20148_() : null))) continue;
                object = object2;
                break block5;
            }
            object = null;
        }
        ClientBattleActor actor = (ClientBattleActor)object;
        if (actor != null) {
            Object v3;
            block6: {
                Object object2;
                Iterable iterable = actor.getPokemon();
                object2 = iterable.iterator();
                while (object2.hasNext()) {
                    Object e = object2.next();
                    Pokemon it = (Pokemon)e;
                    boolean bl = false;
                    if (!Intrinsics.areEqual((Object)it.getUuid(), (Object)packet.getPokemon().getUuid())) continue;
                    v3 = e;
                    break block6;
                }
                v3 = null;
            }
            Pokemon previous = v3;
            if (previous != null) {
                actor.getPokemon().add(actor.getPokemon().indexOf(previous), packet.getPokemon().create());
                actor.getPokemon().remove(previous);
            }
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull BattleUpdateTeamPokemonPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

