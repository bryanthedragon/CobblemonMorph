/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.TurnInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMusicPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleQueueRequestPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleSetTeamPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/TurnInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
@SourceDebugExtension(value={"SMAP\nTurnInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TurnInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/TurnInstruction\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,68:1\n800#2,11:69\n1855#2,2:80\n1855#2:82\n1549#2:83\n1620#2,3:84\n1856#2:87\n*S KotlinDebug\n*F\n+ 1 TurnInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/TurnInstruction\n*L\n40#1:69,11\n40#1:80,2\n45#1:82\n46#1:83\n46#1:84,3\n45#1:87\n*E\n"})
public final class TurnInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public TurnInstruction(@NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.message = message;
    }

    @NotNull
    public final BattleMessage getMessage() {
        return this.message;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        String string = this.message.argumentAt(0);
        if (string == null) {
            return;
        }
        int turnNumber = Integer.parseInt(string);
        if (!battle2.getStarted()) {
            Object initializePacket;
            void $this$filterIsInstanceTo$iv$iv;
            battle2.setStarted(true);
            Iterable<BattleActor> $this$filterIsInstance$iv = battle2.getActors();
            boolean $i$f$filterIsInstance = false;
            Iterator<BattleActor> iterator = $this$filterIsInstance$iv;
            Collection collection = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof PlayerBattleActor)) continue;
                collection.add(element$iv$iv);
            }
            Iterable<BattleActor> $this$forEach$iv = (List)collection;
            boolean $i$f$forEach = false;
            for (Object e : $this$forEach$iv) {
                PlayerBattleActor actor = (PlayerBattleActor)e;
                boolean bl = false;
                initializePacket = new BattleInitializePacket(battle2, actor.getSide());
                actor.sendUpdate((NetworkPacket)initializePacket);
                actor.sendUpdate(new BattleMusicPacket(actor.getBattleTheme(), 0.0f, 0.0f, 6, null));
            }
            $this$forEach$iv = battle2.getActors();
            $i$f$forEach = false;
            iterator = $this$forEach$iv.iterator();
            while (iterator.hasNext()) {
                ShowdownActionRequest req;
                void $this$mapTo$iv$iv;
                void $this$map$iv;
                BattleActor battleActor;
                BattleActor actor = battleActor = iterator.next();
                boolean bl = false;
                initializePacket = actor.getPokemonList();
                BattleActor battleActor2 = actor;
                boolean $i$f$map = false;
                void var12_20 = $this$map$iv;
                Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    BattlePokemon battlePokemon = (BattlePokemon)item$iv$iv;
                    Collection collection2 = destination$iv$iv2;
                    boolean bl2 = false;
                    collection2.add(it.getEffectedPokemon());
                }
                Collection collection3 = (List)destination$iv$iv2;
                battleActor2.sendUpdate(new BattleSetTeamPokemonPacket(collection3));
                if (actor.getRequest() == null) continue;
                actor.sendUpdate(new BattleQueueRequestPacket(req));
            }
            battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(battle2){
                final /* synthetic */ PokemonBattle $battle;
                {
                    this.$battle = $battle;
                    super(0);
                }

                @NotNull
                public final DispatchResult invoke() {
                    return () -> invoke.3.invoke$lambda$0(this.$battle);
                }

                private static final boolean invoke$lambda$0(PokemonBattle $battle) {
                    Intrinsics.checkNotNullParameter((Object)$battle, (String)"$battle");
                    return !$battle.getSide1().stillSendingOut() && !$battle.getSide2().stillSendingOut();
                }
            }));
            battle2.dispatchGo((Function0<Unit>)((Function0)new Function0<Unit>(battle2){
                final /* synthetic */ PokemonBattle $battle;
                {
                    this.$battle = $battle;
                    super(0);
                }

                public final void invoke() {
                    this.$battle.getSide1().playCries();
                    SchedulingFunctionsKt.afterOnServer$default(0, 1.0f, (Function0)new Function0<Unit>(this.$battle){
                        final /* synthetic */ PokemonBattle $battle;
                        {
                            this.$battle = $battle;
                            super(0);
                        }

                        public final void invoke() {
                            this.$battle.getSide2().playCries();
                        }
                    }, 1, null);
                }
            }));
        }
        battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(battle2, turnNumber){
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ int $turnNumber;
            {
                this.$battle = $battle;
                this.$turnNumber = $turnNumber;
                super(0);
            }

            @NotNull
            public final DispatchResult invoke() {
                this.$battle.sendToActors(new BattleMakeChoicePacket());
                Object[] objectArray = new Object[]{this.$turnNumber};
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("turn", objectArray);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"turn\", turnNumber)");
                this.$battle.broadcastChatMessage((Component)TextKt.aqua(mutableComponent));
                this.$battle.turn(this.$turnNumber);
                return DispatchResultKt.getGO();
            }
        }));
    }
}

