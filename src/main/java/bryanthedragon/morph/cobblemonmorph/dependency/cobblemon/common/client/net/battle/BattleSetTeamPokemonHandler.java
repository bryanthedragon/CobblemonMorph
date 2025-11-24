/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.PokemonDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleSetTeamPokemonPacket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/battle/BattleSetTeamPokemonHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleSetTeamPokemonPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/battle/BattleSetTeamPokemonPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleSetTeamPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleSetTeamPokemonHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleSetTeamPokemonHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,22:1\n1#2:23\n1549#3:24\n1620#3,3:25\n*S KotlinDebug\n*F\n+ 1 BattleSetTeamPokemonHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleSetTeamPokemonHandler\n*L\n20#1:24\n20#1:25,3\n*E\n"})
public final class BattleSetTeamPokemonHandler
implements ClientNetworkPacketHandler<BattleSetTeamPokemonPacket> {
    @NotNull
    public static final BattleSetTeamPokemonHandler INSTANCE = new BattleSetTeamPokemonHandler();

    private BattleSetTeamPokemonHandler() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(@NotNull BattleSetTeamPokemonPacket packet, @NotNull Minecraft client) {
        Object v2;
        block3: {
            Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
            Intrinsics.checkNotNullParameter((Object)client, (String)"client");
            ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
            Intrinsics.checkNotNull((Object)clientBattle);
            Iterable iterable = clientBattle.getSide1().getActors();
            for (Object t : iterable) {
                ClientBattleActor it = (ClientBattleActor)t;
                boolean bl = false;
                LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
                if (!Intrinsics.areEqual((Object)it.getUuid(), (Object)(localPlayer != null ? localPlayer.m_20148_() : null))) continue;
                v2 = t;
                break block3;
            }
            v2 = null;
        }
        ClientBattleActor clientBattleActor = v2;
        if (clientBattleActor != null) {
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            Iterable iterable = packet.getTeam();
            ClientBattleActor clientBattleActor2 = clientBattleActor;
            boolean $i$f$map = false;
            Iterator iterator = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                PokemonDTO pokemonDTO = (PokemonDTO)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(it.create());
            }
            clientBattleActor2.setPokemon(CollectionsKt.toMutableList((Collection)((List)destination$iv$iv)));
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull BattleSetTeamPokemonPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

