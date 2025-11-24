/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.MoveTileOffscreenAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.TileAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleOverlay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureEndPacket;
import java.util.concurrent.ConcurrentLinkedQueue;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/battle/BattleCaptureEndHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleCaptureEndPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/battle/BattleCaptureEndPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleCaptureEndHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleCaptureEndHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleCaptureEndHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,27:1\n1#2:28\n*E\n"})
public final class BattleCaptureEndHandler
implements ClientNetworkPacketHandler<BattleCaptureEndPacket> {
    @NotNull
    public static final BattleCaptureEndHandler INSTANCE = new BattleCaptureEndHandler();

    private BattleCaptureEndHandler() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(@NotNull BattleCaptureEndPacket packet, @NotNull Minecraft client) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
        if (clientBattle == null) {
            return;
        }
        ClientBattle battle2 = clientBattle;
        BattleOverlay overlay2 = CobblemonClient.INSTANCE.getBattleOverlay();
        ActiveClientBattlePokemon activeBattlePokemon = (ActiveClientBattlePokemon)battle2.getPokemonFromPNX(packet.getTargetPNX()).component2();
        if (packet.getSucceeded()) {
            void it;
            MoveTileOffscreenAnimation moveTileOffscreenAnimation;
            MoveTileOffscreenAnimation moveTileOffscreenAnimation2 = moveTileOffscreenAnimation = new MoveTileOffscreenAnimation(0.0f, 1, null);
            ConcurrentLinkedQueue<TileAnimation> concurrentLinkedQueue = activeBattlePokemon.getAnimations();
            boolean bl = false;
            overlay2.after(it.getDuration(), (Function0<Unit>)((Function0)new Function0<Unit>(activeBattlePokemon){
                final /* synthetic */ ActiveClientBattlePokemon $activeBattlePokemon;
                {
                    this.$activeBattlePokemon = $activeBattlePokemon;
                    super(0);
                }

                public final void invoke() {
                    this.$activeBattlePokemon.setBallCapturing(null);
                }
            }));
            concurrentLinkedQueue.add(moveTileOffscreenAnimation);
        }
        activeBattlePokemon.setBallCapturing(null);
    }

    @Override
    public void handleOnNettyThread(@NotNull BattleCaptureEndPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

