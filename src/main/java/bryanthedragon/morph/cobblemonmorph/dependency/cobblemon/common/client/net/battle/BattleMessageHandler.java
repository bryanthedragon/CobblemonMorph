/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.locale.Language
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleMessageQueue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMessagePacket;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/battle/BattleMessageHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleMessagePacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/battle/BattleMessagePacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class BattleMessageHandler
implements ClientNetworkPacketHandler<BattleMessagePacket> {
    @NotNull
    public static final BattleMessageHandler INSTANCE = new BattleMessageHandler();

    private BattleMessageHandler() {
    }

    @Override
    public void handle(@NotNull BattleMessagePacket packet, @NotNull Minecraft client) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
        if (clientBattle == null) {
            return;
        }
        ClientBattle battle2 = clientBattle;
        Font textRenderer = Minecraft.m_91087_().f_91062_;
        for (Component message : packet.getMessages()) {
            MutableComponent mutableComponent = message.m_6881_();
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"message.copy()");
            MutableComponent line = TextKt.font(TextKt.bold(mutableComponent), CobblemonResources.INSTANCE.getDEFAULT_LARGE());
            List lines = Language.m_128107_().m_128112_(textRenderer.m_92865_().m_92414_((FormattedText)line, 142, line.m_7383_()));
            ClientBattleMessageQueue clientBattleMessageQueue = battle2.getMessages();
            Intrinsics.checkNotNullExpressionValue((Object)lines, (String)"lines");
            clientBattleMessageQueue.add(lines);
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull BattleMessagePacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

