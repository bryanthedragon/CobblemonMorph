/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.Block
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.events;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.BattleCountableContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CountableCriterionKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CountablePokemonTypeContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.EvolvePokemonContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.EvolvePokemonCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.LevelUpContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PlantTumblestoneContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.TradePokemonContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleVictoryEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.LevelUpEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonCapturedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.TradeCompletedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionCompleteEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerAdvancementData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.TumblestoneBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.TumblestoneItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0007\u00a2\u0006\u0004\b\b\u0010\tJ\u0015\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\n\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\r\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0015\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0015\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/events/AdvancementHandler;", "", "Lcom/cobblemon/mod/common/api/events/pokemon/PokemonCapturedEvent;", "event", "", "onCapture", "(Lcom/cobblemon/mod/common/api/events/pokemon/PokemonCapturedEvent;)V", "Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionCompleteEvent;", "onEvolve", "(Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionCompleteEvent;)V", "Lcom/cobblemon/mod/common/api/events/pokemon/LevelUpEvent;", "onLevelUp", "(Lcom/cobblemon/mod/common/api/events/pokemon/LevelUpEvent;)V", "Lcom/cobblemon/mod/common/api/events/pokemon/TradeCompletedEvent;", "onTradeCompleted", "(Lcom/cobblemon/mod/common/api/events/pokemon/TradeCompletedEvent;)V", "Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$RightClickBlock;", "onTumbleStonePlaced", "(Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$RightClickBlock;)V", "Lcom/cobblemon/mod/common/api/events/battles/BattleVictoryEvent;", "onWinBattle", "(Lcom/cobblemon/mod/common/api/events/battles/BattleVictoryEvent;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nAdvancementHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AdvancementHandler.kt\ncom/cobblemon/mod/common/events/AdvancementHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,159:1\n1855#2,2:160\n1360#2:162\n1446#2,2:163\n1603#2,9:165\n1855#2:174\n1856#2:176\n1612#2:177\n1448#2,3:178\n1855#2:181\n1855#2:182\n1855#2,2:183\n1856#2:185\n1856#2:186\n1360#2:187\n1446#2,2:188\n1603#2,9:190\n1855#2:199\n1856#2:201\n1612#2:202\n1448#2,3:203\n1855#2,2:206\n1#3:175\n1#3:200\n1#3:208\n*S KotlinDebug\n*F\n+ 1 AdvancementHandler.kt\ncom/cobblemon/mod/common/events/AdvancementHandler\n*L\n35#1:160,2\n83#1:162\n83#1:163,2\n83#1:165,9\n83#1:174\n83#1:176\n83#1:177\n83#1:178,3\n84#1:181\n87#1:182\n89#1:183,2\n87#1:185\n84#1:186\n100#1:187\n100#1:188,2\n100#1:190,9\n100#1:199\n100#1:201\n100#1:202\n100#1:203,3\n101#1:206,2\n83#1:175\n100#1:200\n*E\n"})
public final class AdvancementHandler {
    @NotNull
    public static final AdvancementHandler INSTANCE = new AdvancementHandler();

    private AdvancementHandler() {
    }

    public final void onCapture(@NotNull PokemonCapturedEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        PlayerData playerData = Cobblemon.INSTANCE.getPlayerData().get((Player)event.getPlayer());
        PlayerAdvancementData advancementData = playerData.getAdvancementData();
        advancementData.updateTotalCaptureCount();
        advancementData.updateAspectsCollected(event.getPlayer(), event.getPokemon());
        CobblemonCriteria.INSTANCE.getCATCH_POKEMON().trigger(event.getPlayer(), new CountablePokemonTypeContext(advancementData.getTotalCaptureCount(), "any"));
        Iterable<ElementalType> $this$forEach$iv = event.getPokemon().getTypes();
        boolean $i$f$forEach = false;
        Iterator<ElementalType> iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            ElementalType element$iv;
            ElementalType it = element$iv = iterator.next();
            boolean bl = false;
            advancementData.updateTotalTypeCaptureCount(it);
            CobblemonCriteria.INSTANCE.getCATCH_POKEMON().trigger(event.getPlayer(), new CountablePokemonTypeContext(advancementData.getTotalTypeCaptureCount(it), it.getName()));
        }
        if (event.getPokemon().getShiny()) {
            advancementData.updateTotalShinyCaptureCount();
            CountableCriterionKt.trigger(CobblemonCriteria.INSTANCE.getCATCH_SHINY_POKEMON(), event.getPlayer(), advancementData.getTotalShinyCaptureCount());
        }
        CobblemonCriteria.INSTANCE.getCOLLECT_ASPECT().trigger(event.getPlayer(), advancementData.getAspectsCollected());
        Cobblemon.INSTANCE.getPlayerData().saveSingle(playerData);
    }

    public final void onEvolve(@NotNull EvolutionCompleteEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        ServerPlayer player = event.getPokemon().getOwnerPlayer();
        if (player != null) {
            if (event.getPokemon().getPreEvolution() != null) {
                PlayerData playerData = Cobblemon.INSTANCE.getPlayerData().get((Player)player);
                PlayerAdvancementData advancementData = playerData.getAdvancementData();
                advancementData.updateTotalEvolvedCount();
                advancementData.updateAspectsCollected(player, event.getPokemon());
                Cobblemon.INSTANCE.getPlayerData().saveSingle(playerData);
                SimpleCriterionTrigger<EvolvePokemonContext, EvolvePokemonCriterionCondition> simpleCriterionTrigger = CobblemonCriteria.INSTANCE.getEVOLVE_POKEMON();
                PreEvolution preEvolution = event.getPokemon().getPreEvolution();
                Intrinsics.checkNotNull((Object)preEvolution);
                simpleCriterionTrigger.trigger(player, new EvolvePokemonContext(preEvolution.getSpecies().getResourceIdentifier(), event.getPokemon().getSpecies().getResourceIdentifier(), advancementData.getTotalEvolvedCount()));
                CobblemonCriteria.INSTANCE.getCOLLECT_ASPECT().trigger(player, advancementData.getAspectsCollected());
            } else {
                Cobblemon.INSTANCE.getLOGGER().warn("Evolution triggered by " + player.m_5446_() + " has missing evolution data for " + event.getPokemon().getSpecies().getResourceIdentifier() + ". Incomplete evolution data: " + event.getEvolution().getId() + ", please report to the datapack creator!");
            }
        }
    }

    public final void onWinBattle(@NotNull BattleVictoryEvent event) {
        PlayerAdvancementData advancementData;
        PlayerData playerData;
        boolean $i$f$forEach;
        Iterable $this$forEach$iv;
        Iterable list$iv$iv;
        ServerPlayer it$iv$iv;
        UUID p0;
        UUID element$iv$iv;
        UUID element$iv$iv$iv;
        Iterator<UUID> iterator;
        Iterable<UUID> $this$forEach$iv$iv$iv;
        boolean $i$f$mapNotNullTo;
        Collection destination$iv$iv;
        BattleActor it;
        Iterable $this$flatMapTo$iv$iv;
        boolean $i$f$flatMapTo;
        boolean $i$f$flatMap;
        Iterable $this$flatMap$iv;
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        if (!event.getWasWildCapture() && event.getBattle().isPvW()) {
            boolean $i$f$forEach2;
            $this$flatMap$iv = event.getWinners();
            $i$f$flatMap = false;
            Iterable iterable = $this$flatMap$iv;
            Collection destination$iv$iv2 = new ArrayList();
            $i$f$flatMapTo = false;
            Iterator iterator2 = $this$flatMapTo$iv$iv.iterator();
            while (iterator2.hasNext()) {
                Object element$iv$iv2 = iterator2.next();
                it = (BattleActor)element$iv$iv2;
                boolean bl3 = false;
                Iterable<UUID> $this$mapNotNull$iv = it.getPlayerUUIDs();
                boolean $i$f$mapNotNull = false;
                Iterable<UUID> iterable2 = $this$mapNotNull$iv;
                destination$iv$iv = new ArrayList();
                $i$f$mapNotNullTo = false;
                $this$forEach$iv$iv$iv = iterable2;
                $i$f$forEach2 = false;
                iterator = $this$forEach$iv$iv$iv.iterator();
                while (iterator.hasNext()) {
                    element$iv$iv = element$iv$iv$iv = iterator.next();
                    boolean bl = false;
                    p0 = element$iv$iv;
                    boolean bl4 = false;
                    if (PlayerExtensionsKt.getPlayer(p0) == null) continue;
                    boolean bl2 = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                list$iv$iv = (List)destination$iv$iv;
                CollectionsKt.addAll((Collection)destination$iv$iv2, (Iterable)list$iv$iv);
            }
            $this$forEach$iv = (List)destination$iv$iv2;
            $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                ServerPlayer player = (ServerPlayer)element$iv;
                boolean bl5 = false;
                playerData = Cobblemon.INSTANCE.getPlayerData().get((Player)player);
                advancementData = playerData.getAdvancementData();
                Iterable<BattleActor> $this$forEach$iv2 = event.getBattle().getActors();
                boolean $i$f$forEach3 = false;
                Iterator<BattleActor> $i$f$mapNotNull = $this$forEach$iv2.iterator();
                while ($i$f$mapNotNull.hasNext()) {
                    BattleActor battleActor;
                    BattleActor battleActor2 = battleActor = $i$f$mapNotNull.next();
                    boolean bl6 = false;
                    if (event.getWinners().contains(battleActor2) || battleActor2.getType() != ActorType.WILD) continue;
                    Iterable $this$forEach$iv3 = battleActor2.getPokemonList();
                    $i$f$forEach2 = false;
                    for (UUID element$iv3 : $this$forEach$iv3) {
                        BattlePokemon battlePokemon = (BattlePokemon)((Object)element$iv3);
                        boolean bl7 = false;
                        advancementData.updateTotalDefeatedCount(battlePokemon.getOriginalPokemon());
                    }
                }
                Cobblemon.INSTANCE.getPlayerData().saveSingle(playerData);
                CountableCriterionKt.trigger(CobblemonCriteria.INSTANCE.getDEFEAT_POKEMON(), player, advancementData.getTotalBattleVictoryCount());
            }
        }
        $this$flatMap$iv = event.getWinners();
        $i$f$flatMap = false;
        $this$flatMapTo$iv$iv = $this$flatMap$iv;
        Collection destination$iv$iv2 = new ArrayList();
        $i$f$flatMapTo = false;
        Iterator bl5 = $this$flatMapTo$iv$iv.iterator();
        while (bl5.hasNext()) {
            Object element$iv$iv2 = bl5.next();
            it = (BattleActor)element$iv$iv2;
            boolean bl8 = false;
            Iterable<UUID> $this$mapNotNull$iv = it.getPlayerUUIDs();
            boolean $i$f$mapNotNull = false;
            Iterable<UUID> iterable = $this$mapNotNull$iv;
            destination$iv$iv = new ArrayList();
            $i$f$mapNotNullTo = false;
            $this$forEach$iv$iv$iv = iterable;
            boolean $i$f$forEach2 = false;
            iterator = $this$forEach$iv$iv$iv.iterator();
            while (iterator.hasNext()) {
                element$iv$iv = element$iv$iv$iv = iterator.next();
                boolean bl2 = false;
                p0 = element$iv$iv;
                boolean bl9 = false;
                if (PlayerExtensionsKt.getPlayer(p0) == null) continue;
                boolean bl = false;
                destination$iv$iv.add(it$iv$iv);
            }
            list$iv$iv = (List)destination$iv$iv;
            CollectionsKt.addAll((Collection)destination$iv$iv2, (Iterable)list$iv$iv);
        }
        $this$forEach$iv = (List)destination$iv$iv2;
        $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ServerPlayer player = (ServerPlayer)element$iv;
            boolean bl10 = false;
            playerData = Cobblemon.INSTANCE.getPlayerData().get((Player)player);
            advancementData = playerData.getAdvancementData();
            advancementData.updateTotalBattleVictoryCount();
            if (event.getBattle().isPvW()) {
                advancementData.updateTotalPvWBattleVictoryCount();
            }
            if (event.getBattle().isPvP()) {
                advancementData.updateTotalPvPBattleVictoryCount();
            }
            if (event.getBattle().isPvN()) {
                advancementData.updateTotalPvNBattleVictoryCount();
            }
            Cobblemon.INSTANCE.getPlayerData().saveSingle(playerData);
            CobblemonCriteria.INSTANCE.getWIN_BATTLE().trigger(player, new BattleCountableContext(advancementData.getTotalBattleVictoryCount(), event.getBattle()));
        }
    }

    public final void onLevelUp(@NotNull LevelUpEvent event) {
        block0: {
            Intrinsics.checkNotNullParameter((Object)event, (String)"event");
            ServerPlayer serverPlayer = event.getPokemon().getOwnerPlayer();
            if (serverPlayer == null) break block0;
            ServerPlayer it = serverPlayer;
            boolean bl = false;
            CobblemonCriteria.INSTANCE.getLEVEL_UP().trigger(it, new LevelUpContext(event.getNewLevel(), event.getPokemon()));
        }
    }

    public final void onTradeCompleted(@NotNull TradeCompletedEvent event) {
        PlayerAdvancementData advancementData;
        PlayerData playerData;
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        ServerPlayer player1 = event.getTradeParticipant1Pokemon().getOwnerPlayer();
        ServerPlayer player2 = event.getTradeParticipant2Pokemon().getOwnerPlayer();
        if (player1 != null) {
            CobblemonCriteria.INSTANCE.getTRADE_POKEMON().trigger(player1, new TradePokemonContext(event.getTradeParticipant1Pokemon(), event.getTradeParticipant2Pokemon()));
            playerData = Cobblemon.INSTANCE.getPlayerData().get((Player)player1);
            advancementData = playerData.getAdvancementData();
            advancementData.updateTotalTradedCount();
            advancementData.updateAspectsCollected(player1, event.getTradeParticipant2Pokemon());
            CobblemonCriteria.INSTANCE.getCOLLECT_ASPECT().trigger(player1, advancementData.getAspectsCollected());
            Cobblemon.INSTANCE.getPlayerData().saveSingle(playerData);
        }
        if (player2 != null) {
            CobblemonCriteria.INSTANCE.getTRADE_POKEMON().trigger(player2, new TradePokemonContext(event.getTradeParticipant2Pokemon(), event.getTradeParticipant1Pokemon()));
            playerData = Cobblemon.INSTANCE.getPlayerData().get((Player)player2);
            advancementData = playerData.getAdvancementData();
            advancementData.updateTotalTradedCount();
            advancementData.updateAspectsCollected(player2, event.getTradeParticipant1Pokemon());
            CobblemonCriteria.INSTANCE.getCOLLECT_ASPECT().trigger(player2, advancementData.getAspectsCollected());
            Cobblemon.INSTANCE.getPlayerData().saveSingle(playerData);
        }
    }

    public final void onTumbleStonePlaced(@NotNull ServerPlayerEvent.RightClickBlock event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        if (Intrinsics.areEqual((Object)event.getPlayer().m_21120_(event.getHand()).m_41720_(), (Object)CobblemonItems.TUMBLESTONE.m_5456_())) {
            Item item = event.getPlayer().m_21120_(event.getHand()).m_41720_();
            Intrinsics.checkNotNull((Object)item, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.TumblestoneItem");
            Block block = ((TumblestoneItem)item).getBlock();
            Intrinsics.checkNotNull((Object)block, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.TumblestoneBlock");
            TumblestoneBlock block2 = (TumblestoneBlock)block;
            CobblemonCriteria.INSTANCE.getPLANT_TUMBLESTONE().trigger(event.getPlayer(), new PlantTumblestoneContext(event.getPos(), block2));
        }
    }
}

