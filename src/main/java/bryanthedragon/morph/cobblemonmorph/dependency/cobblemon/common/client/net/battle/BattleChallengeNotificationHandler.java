/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleChallenge;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CurrentKeyAccessorKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.PartySendBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleChallengeNotificationPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/battle/BattleChallengeNotificationHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleChallengeNotificationPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/battle/BattleChallengeNotificationPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class BattleChallengeNotificationHandler
implements ClientNetworkPacketHandler<BattleChallengeNotificationPacket> {
    @NotNull
    public static final BattleChallengeNotificationHandler INSTANCE = new BattleChallengeNotificationHandler();

    private BattleChallengeNotificationHandler() {
    }

    @Override
    public void handle(@NotNull BattleChallengeNotificationPacket packet, @NotNull Minecraft client) {
        block0: {
            Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
            Intrinsics.checkNotNullParameter((Object)client, (String)"client");
            CobblemonClient.INSTANCE.getRequests().getBattleChallenges().add(new ClientBattleChallenge(packet.getBattleChallengeId(), packet.getChallengerId()));
            LocalPlayer localPlayer = client.f_91074_;
            if (localPlayer == null) break block0;
            Object[] objectArray = new Object[2];
            objectArray[0] = packet.getChallengerName();
            Intrinsics.checkNotNullExpressionValue((Object)CurrentKeyAccessorKt.boundKey(PartySendBinding.INSTANCE).m_84875_(), (String)"PartySendBinding.boundKey().localizedText");
            localPlayer.m_5661_((Component)LocalizationUtilsKt.lang("challenge.receiver", objectArray), true);
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull BattleChallengeNotificationPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

