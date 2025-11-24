/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleReplacePokemonPacket;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/net/battle/BattleReplacePokemonHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleReplacePokemonPacket;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/battle/BattleReplacePokemonPacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
public final class BattleReplacePokemonHandler
implements ClientNetworkPacketHandler<BattleReplacePokemonPacket> {
    @NotNull
    public static final BattleReplacePokemonHandler INSTANCE = new BattleReplacePokemonHandler();

    private BattleReplacePokemonHandler() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(@NotNull BattleReplacePokemonPacket packet, @NotNull Minecraft client) {
        void it;
        ClientBattlePokemon clientBattlePokemon;
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
        if (clientBattle == null) {
            return;
        }
        ClientBattle battle2 = clientBattle;
        Pair<ClientBattleActor, ActiveClientBattlePokemon> pair = battle2.getPokemonFromPNX(packet.getPnx());
        ClientBattleActor actor = (ClientBattleActor)pair.component1();
        ActiveClientBattlePokemon activeBattlePokemon = (ActiveClientBattlePokemon)pair.component2();
        BattleInitializePacket.ActiveBattlePokemonDTO $this$handle_u24lambda_u241 = packet.getRealPokemon();
        boolean bl = false;
        ClientBattlePokemon clientBattlePokemon2 = clientBattlePokemon = new ClientBattlePokemon($this$handle_u24lambda_u241.getUuid(), $this$handle_u24lambda_u241.getDisplayName(), $this$handle_u24lambda_u241.getProperties(), $this$handle_u24lambda_u241.getAspects(), $this$handle_u24lambda_u241.getHpValue(), $this$handle_u24lambda_u241.getMaxHp(), packet.isAlly(), $this$handle_u24lambda_u241.getStatus(), $this$handle_u24lambda_u241.getStatChanges());
        ActiveClientBattlePokemon activeClientBattlePokemon = activeBattlePokemon;
        boolean bl2 = false;
        it.setActor(actor);
        activeClientBattlePokemon.setBattlePokemon(clientBattlePokemon);
    }

    @Override
    public void handleOnNettyThread(@NotNull BattleReplacePokemonPacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

