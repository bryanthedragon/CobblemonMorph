/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001d\u0010\b\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tJ\u001f\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/client/net/battle/BattleInitializeHandler;", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket;", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$BattleActorDTO;", "actorDTO", "", "isAlly", "Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "actorFromDTO", "(Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$BattleActorDTO;Z)Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "packet", "Lnet/minecraft/client/Minecraft;", "client", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket;Lnet/minecraft/client/Minecraft;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleInitializeHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleInitializeHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleInitializeHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,78:1\n2624#2,3:79\n1747#2,3:82\n1549#2:85\n1620#2,3:86\n1549#2:89\n1620#2,3:90\n1549#2:93\n1620#2,3:94\n*S KotlinDebug\n*F\n+ 1 BattleInitializeHandler.kt\ncom/cobblemon/mod/common/client/net/battle/BattleInitializeHandler\n*L\n28#1:79,3\n36#1:82,3\n37#1:85\n37#1:86,3\n38#1:89\n38#1:90,3\n61#1:93\n61#1:94,3\n*E\n"})
public final class BattleInitializeHandler
implements ClientNetworkPacketHandler<BattleInitializePacket> {
    @NotNull
    public static final BattleInitializeHandler INSTANCE = new BattleInitializeHandler();

    private BattleInitializeHandler() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(@NotNull BattleInitializePacket packet, @NotNull Minecraft client) {
        Iterable $this$mapTo$iv$iv;
        Iterable $this$map$iv;
        boolean bl;
        Iterable $this$any$iv;
        List<ClientBattleActor> list;
        void $this$handle_u24lambda_u245;
        BattleInitializePacket.BattleSideDTO otherSide;
        BattleInitializePacket.BattleSideDTO mySide;
        Object it;
        CobblemonClient cobblemonClient;
        ClientBattle clientBattle;
        block16: {
            boolean bl2;
            block14: {
                Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
                Intrinsics.checkNotNullParameter((Object)client, (String)"client");
                LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
                UUID playerUUID = localPlayer != null ? localPlayer.m_20148_() : null;
                ClientBattle clientBattle2 = clientBattle = new ClientBattle(packet.getBattleId(), packet.getBattleFormat());
                cobblemonClient = CobblemonClient.INSTANCE;
                boolean bl3 = false;
                Iterable $this$none$iv = packet.getSide2().getActors();
                boolean $i$f$none = false;
                if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                    bl2 = true;
                } else {
                    for (Object element$iv : $this$none$iv) {
                        it = (BattleInitializePacket.BattleActorDTO)element$iv;
                        boolean bl4 = false;
                        if (!Intrinsics.areEqual((Object)((BattleInitializePacket.BattleActorDTO)it).getUuid(), (Object)playerUUID)) continue;
                        bl2 = false;
                        break block14;
                    }
                    bl2 = true;
                }
            }
            mySide = bl2 ? packet.getSide1() : packet.getSide2();
            otherSide = Intrinsics.areEqual((Object)mySide, (Object)packet.getSide1()) ? packet.getSide2() : packet.getSide1();
            Object object = new BattleInitializePacket.BattleSideDTO[]{packet.getSide1(), packet.getSide2()};
            List sides = CollectionsKt.listOf((Object[])object);
            object = sides;
            list = $this$handle_u24lambda_u245;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    boolean bl5;
                    block15: {
                        BattleInitializePacket.BattleSideDTO it2 = (BattleInitializePacket.BattleSideDTO)element$iv;
                        boolean bl6 = false;
                        Iterable $this$any$iv2 = it2.getActors();
                        boolean $i$f$any2 = false;
                        if ($this$any$iv2 instanceof Collection && ((Collection)$this$any$iv2).isEmpty()) {
                            bl5 = false;
                        } else {
                            for (Object element$iv2 : $this$any$iv2) {
                                BattleInitializePacket.BattleActorDTO it3 = (BattleInitializePacket.BattleActorDTO)element$iv2;
                                boolean bl7 = false;
                                LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
                                if (!Intrinsics.areEqual((Object)it3.getUuid(), (Object)(localPlayer != null ? localPlayer.m_20148_() : null))) continue;
                                bl5 = true;
                                break block15;
                            }
                            bl5 = false;
                        }
                    }
                    if (!bl5) continue;
                    bl = true;
                    break block16;
                }
                bl = false;
            }
        }
        boolean bl8 = bl;
        ((ClientBattle)((Object)list)).setSpectating(!bl8);
        $this$any$iv = mySide.getActors();
        list = $this$handle_u24lambda_u245.getSide1().getActors();
        boolean $i$f$map = false;
        it = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it4;
            BattleInitializePacket.BattleActorDTO $i$f$any2 = (BattleInitializePacket.BattleActorDTO)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl9 = false;
            collection.add(INSTANCE.actorFromDTO((BattleInitializePacket.BattleActorDTO)it4, !$this$handle_u24lambda_u245.getSpectating()));
        }
        list.addAll((List)destination$iv$iv);
        $this$map$iv = otherSide.getActors();
        list = $this$handle_u24lambda_u245.getSide2().getActors();
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            BattleInitializePacket.BattleActorDTO it4 = (BattleInitializePacket.BattleActorDTO)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl10 = false;
            collection.add(INSTANCE.actorFromDTO(it4, false));
        }
        list.addAll((List)destination$iv$iv);
        Object[] $i$f$map2 = new ClientBattleSide[]{$this$handle_u24lambda_u245.getSide1(), $this$handle_u24lambda_u245.getSide2()};
        for (ClientBattleSide side : CollectionsKt.listOf((Object[])$i$f$map2)) {
            side.setBattle((ClientBattle)$this$handle_u24lambda_u245);
            for (ClientBattleActor actor : side.getActors()) {
                actor.setSide(side);
                for (ActiveClientBattlePokemon pokemon : actor.getActivePokemon()) {
                    ClientBattlePokemon clientBattlePokemon = pokemon.getBattlePokemon();
                    if (clientBattlePokemon == null) continue;
                    clientBattlePokemon.setActor(actor);
                }
            }
        }
        $this$handle_u24lambda_u245.setMinimised(false);
        cobblemonClient.setBattle(clientBattle);
        Minecraft.m_91087_().m_91152_((Screen)new BattleGUI());
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final ClientBattleActor actorFromDTO(@NotNull BattleInitializePacket.BattleActorDTO actorDTO, boolean isAlly) {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        ClientBattleActor clientBattleActor;
        Intrinsics.checkNotNullParameter((Object)actorDTO, (String)"actorDTO");
        ClientBattleActor $this$actorFromDTO_u24lambda_u248 = clientBattleActor = new ClientBattleActor(actorDTO.getShowdownId(), actorDTO.getDisplayName(), actorDTO.getUuid(), actorDTO.getType());
        boolean bl = false;
        Iterable iterable = actorDTO.getActivePokemon();
        List<ActiveClientBattlePokemon> list = $this$actorFromDTO_u24lambda_u248.getActivePokemon();
        boolean $i$f$map = false;
        void var9_9 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            ClientBattlePokemon clientBattlePokemon;
            void it;
            BattleInitializePacket.ActiveBattlePokemonDTO activeBattlePokemonDTO = (BattleInitializePacket.ActiveBattlePokemonDTO)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl2 = false;
            ClientBattleActor clientBattleActor2 = $this$actorFromDTO_u24lambda_u248;
            if (it != null) {
                void it2;
                ClientBattleActor clientBattleActor3 = clientBattleActor2;
                boolean bl3 = false;
                UUID uUID = it2.getUuid();
                PokemonProperties pokemonProperties = it2.getProperties();
                Set<String> set2 = it2.getAspects();
                MutableComponent mutableComponent = it2.getDisplayName();
                float f = it2.getHpValue();
                float f2 = it2.getMaxHp();
                PersistentStatus persistentStatus = it2.getStatus();
                Map<Stat, Integer> map = it2.getStatChanges();
                clientBattlePokemon = new ClientBattlePokemon(uUID, mutableComponent, pokemonProperties, set2, f, f2, isAlly, persistentStatus, map);
                clientBattleActor2 = clientBattleActor3;
            } else {
                clientBattlePokemon = null;
            }
            ClientBattlePokemon clientBattlePokemon2 = clientBattlePokemon;
            ClientBattleActor clientBattleActor4 = clientBattleActor2;
            collection.add(new ActiveClientBattlePokemon(clientBattleActor4, clientBattlePokemon2));
        }
        list.addAll((List)destination$iv$iv);
        return clientBattleActor;
    }

    @Override
    public void handleOnNettyThread(@NotNull BattleInitializePacket packet) {
        ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet);
    }
}

