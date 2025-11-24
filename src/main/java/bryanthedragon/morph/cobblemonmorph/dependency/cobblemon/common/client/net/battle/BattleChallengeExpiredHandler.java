/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleChallenge;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleChallengeExpiredPacket;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/battle/BattleChallengeExpiredHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleChallengeExpiredPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/battle/BattleChallengeExpiredPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class BattleChallengeExpiredHandler
implements ClientNetworkPacketHandler<BattleChallengeExpiredPacket> {
    @NotNull
    public static final BattleChallengeExpiredHandler INSTANCE = new BattleChallengeExpiredHandler();

    private BattleChallengeExpiredHandler() {
    }

    @Override
    public void handle(@NotNull BattleChallengeExpiredPacket packet, @NotNull Minecraft client) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        CobblemonClient.INSTANCE.getRequests().getBattleChallenges().removeIf(arg_0 -> BattleChallengeExpiredHandler.handle$lambda$0((Function1)new Function1<ClientBattleChallenge, Boolean>(packet){
            final /* synthetic */ BattleChallengeExpiredPacket $packet;
            {
                this.$packet = $packet;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull ClientBattleChallenge it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return Intrinsics.areEqual((Object)it.getChallengeId(), (Object)this.$packet.getBattleChallengeId());
            }
        }, arg_0));
    }

    @Override
    public void handleOnNettyThread(@NotNull BattleChallengeExpiredPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }

    private static final boolean handle$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

