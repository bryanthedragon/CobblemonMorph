/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.exception.IllegalActionChoiceException;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleQueueRequestPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.BattleSelectActionsPacket;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/battle/BattleSelectActionsHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/battle/BattleSelectActionsPacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/battle/BattleSelectActionsPacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleSelectActionsHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleSelectActionsHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/battle/BattleSelectActionsHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,36:1\n1#2:37\n*E\n"})
public final class BattleSelectActionsHandler
implements ServerNetworkPacketHandler<BattleSelectActionsPacket> {
    @NotNull
    public static final BattleSelectActionsHandler INSTANCE = new BattleSelectActionsHandler();

    private BattleSelectActionsHandler() {
    }

    @Override
    public void handle(@NotNull BattleSelectActionsPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        BattleActor battleActor;
        block6: {
            Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            PokemonBattle pokemonBattle = BattleRegistry.INSTANCE.getBattle(packet.getBattleId());
            if (pokemonBattle == null) {
                return;
            }
            PokemonBattle battle2 = pokemonBattle;
            Iterable<BattleActor> iterable = battle2.getActors();
            Iterator<BattleActor> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                BattleActor battleActor2;
                BattleActor it = battleActor2 = iterator.next();
                boolean bl = false;
                if (!CollectionsKt.contains(it.getPlayerUUIDs(), (Object)player.m_20148_())) continue;
                battleActor = battleActor2;
                break block6;
            }
            battleActor = null;
        }
        BattleActor battleActor3 = battleActor;
        if (battleActor3 == null) {
            return;
        }
        BattleActor actor = battleActor3;
        if (!actor.getMustChoose()) {
            return;
        }
        try {
            actor.setActionResponses(packet.getShowdownActionResponses());
        }
        catch (IllegalActionChoiceException e) {
            String string = e.getMessage();
            Intrinsics.checkNotNull((Object)string);
            player.m_213846_((Component)TextKt.red(string));
            ShowdownActionRequest showdownActionRequest = actor.getRequest();
            Intrinsics.checkNotNull((Object)showdownActionRequest);
            actor.sendUpdate(new BattleQueueRequestPacket(showdownActionRequest));
            actor.sendUpdate(new BattleMakeChoicePacket());
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull BattleSelectActionsPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

