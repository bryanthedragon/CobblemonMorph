/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ForcePassActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.SingleActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleApplyPassResponsePacket;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/battle/BattleApplyPassResponseHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleApplyPassResponsePacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/battle/BattleApplyPassResponsePacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleApplyPassResponseHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleApplyPassResponseHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleApplyPassResponseHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,31:1\n288#2,2:32\n*S KotlinDebug\n*F\n+ 1 BattleApplyPassResponseHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleApplyPassResponseHandler\n*L\n21#1:32,2\n*E\n"})
public final class BattleApplyPassResponseHandler
implements ClientNetworkPacketHandler<BattleApplyPassResponsePacket> {
    @NotNull
    public static final BattleApplyPassResponseHandler INSTANCE = new BattleApplyPassResponseHandler();

    private BattleApplyPassResponseHandler() {
    }

    @Override
    public void handle(@NotNull BattleApplyPassResponsePacket packet, @NotNull Minecraft client) {
        Object v1;
        ClientBattle battle2;
        block5: {
            Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
            Intrinsics.checkNotNullParameter((Object)client, (String)"client");
            ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
            if (clientBattle == null) {
                return;
            }
            battle2 = clientBattle;
            Iterable $this$firstOrNull$iv = battle2.getPendingActionRequests();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                SingleActionRequest it = (SingleActionRequest)element$iv;
                boolean bl = false;
                if (!(it.getResponse() == null)) continue;
                v1 = element$iv;
                break block5;
            }
            v1 = null;
        }
        SingleActionRequest singleActionRequest = v1;
        if (singleActionRequest == null) {
            return;
        }
        SingleActionRequest req = singleActionRequest;
        ForcePassActionResponse res = new ForcePassActionResponse();
        Screen gui = Minecraft.m_91087_().f_91080_;
        if (gui instanceof BattleGUI) {
            ((BattleGUI)gui).selectAction(req, res);
        } else {
            req.setResponse(res);
            battle2.checkForFinishedChoosing();
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull BattleApplyPassResponsePacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

