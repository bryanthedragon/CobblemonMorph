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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.SingleActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleQueueRequestPacket;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/battle/BattleQueueRequestHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleQueueRequestPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/battle/BattleQueueRequestPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleQueueRequestHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleQueueRequestHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleQueueRequestHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,23:1\n1#2:24\n*E\n"})
public final class BattleQueueRequestHandler
implements ClientNetworkPacketHandler<BattleQueueRequestPacket> {
    @NotNull
    public static final BattleQueueRequestHandler INSTANCE = new BattleQueueRequestHandler();

    private BattleQueueRequestHandler() {
    }

    @Override
    public void handle(@NotNull BattleQueueRequestPacket packet, @NotNull Minecraft client) {
        Object v2;
        block4: {
            Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
            Intrinsics.checkNotNullParameter((Object)client, (String)"client");
            ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
            if (clientBattle == null) {
                return;
            }
            ClientBattle battle2 = clientBattle;
            Iterable iterable = battle2.getSide1().getActors();
            for (Object t : iterable) {
                ClientBattleActor it = (ClientBattleActor)t;
                boolean bl = false;
                LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
                if (!Intrinsics.areEqual((Object)it.getUuid(), (Object)(localPlayer != null ? localPlayer.m_20148_() : null))) continue;
                v2 = t;
                break block4;
            }
            v2 = null;
        }
        ClientBattleActor clientBattleActor = v2;
        if (clientBattleActor == null) {
            return;
        }
        ClientBattleActor actor = clientBattleActor;
        ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
        if (clientBattle != null) {
            clientBattle.setPendingActionRequests(SingleActionRequest.Companion.composeFrom(actor, packet.getRequest()));
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull BattleQueueRequestPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

