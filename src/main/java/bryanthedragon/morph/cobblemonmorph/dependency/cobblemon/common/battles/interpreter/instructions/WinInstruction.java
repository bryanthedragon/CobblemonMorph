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
 *  kotlin.text.StringsKt
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleVictoryEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.WaitDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/WinInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
@SourceDebugExtension(value={"SMAP\nWinInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WinInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/WinInstruction\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,74:1\n1549#2:75\n1620#2,3:76\n1549#2:79\n1620#2,3:80\n766#2:83\n857#2,2:84\n1549#2:86\n1620#2,3:87\n2661#2,7:90\n1549#2:97\n1620#2,3:98\n2661#2,7:101\n1747#2,3:108\n*S KotlinDebug\n*F\n+ 1 WinInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/WinInstruction\n*L\n39#1:75\n39#1:76,3\n40#1:79\n40#1:80,3\n41#1:83\n41#1:84,2\n42#1:86\n42#1:87,3\n42#1:90,7\n43#1:97\n43#1:98,3\n43#1:101,7\n44#1:108,3\n*E\n"})
public final class WinInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public WinInstruction(@NotNull BattleMessage message) {
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
        boolean bl;
        MutableComponent losersText;
        MutableComponent winnersText;
        List losers;
        List winners;
        block13: {
            void $this$reduce$iv;
            void $this$mapTo$iv$iv;
            void $this$reduce$iv2;
            void $this$mapTo$iv$iv2;
            void $this$filterTo$iv$iv;
            void $this$mapTo$iv$iv3;
            Collection collection;
            void $this$mapTo$iv$iv4;
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            String string = this.message.argumentAt(0);
            if (string == null) {
                return;
            }
            String user = string;
            String[] stringArray = new String[]{"&"};
            Iterable $this$map$iv = StringsKt.split$default((CharSequence)user, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Iterable destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv4) {
                void it;
                String string2 = (String)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl2 = false;
                collection.add(((Object)StringsKt.trim((CharSequence)((CharSequence)it))).toString());
            }
            List ids = (List)destination$iv$iv;
            Iterable $this$map$iv2 = ids;
            boolean $i$f$map2 = false;
            destination$iv$iv = $this$map$iv2;
            Iterable<Object> destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv2, (int)10));
            boolean $i$f$mapTo2 = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv3) {
                void it;
                String bl2 = (String)item$iv$iv;
                collection = destination$iv$iv2;
                boolean bl3 = false;
                UUID uUID = UUID.fromString((String)it);
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"fromString(it)");
                BattleActor battleActor = battle2.getActor(uUID);
                Intrinsics.checkNotNull((Object)battleActor);
                collection.add(battleActor);
            }
            winners = (List)destination$iv$iv2;
            Iterable<BattleActor> $this$filter$iv = battle2.getActors();
            boolean $i$f$filter = false;
            destination$iv$iv2 = $this$filter$iv;
            Iterable destination$iv$iv3 = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                BattleActor it = (BattleActor)element$iv$iv;
                boolean bl4 = false;
                if (!(!winners.contains(it))) continue;
                destination$iv$iv3.add(element$iv$iv);
            }
            losers = (List)destination$iv$iv3;
            Iterable $this$map$iv3 = winners;
            boolean $i$f$map3 = false;
            destination$iv$iv3 = $this$map$iv3;
            Collection destination$iv$iv4 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv3, (int)10));
            boolean $i$f$mapTo32 = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv2) {
                void it;
                BattleActor bl4 = (BattleActor)item$iv$iv;
                collection = destination$iv$iv4;
                boolean bl5 = false;
                collection.add(it.getName());
            }
            $this$map$iv3 = (List)destination$iv$iv4;
            boolean $i$f$reduce = false;
            Iterator iterator$iv = $this$reduce$iv2.iterator();
            if (!iterator$iv.hasNext()) {
                throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }
            Object accumulator$iv = iterator$iv.next();
            while (iterator$iv.hasNext()) {
                void next;
                MutableComponent $i$f$mapTo32 = (MutableComponent)iterator$iv.next();
                MutableComponent acc = (MutableComponent)accumulator$iv;
                boolean bl6 = false;
                accumulator$iv = TextKt.plus(TextKt.plus(acc, " & "), (Component)next);
            }
            winnersText = (MutableComponent)accumulator$iv;
            Iterable $this$map$iv4 = losers;
            boolean $i$f$map4 = false;
            accumulator$iv = $this$map$iv4;
            Collection destination$iv$iv5 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv4, (int)10));
            boolean $i$f$mapTo42 = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                BattleActor bl5 = (BattleActor)item$iv$iv;
                collection = destination$iv$iv5;
                boolean bl7 = false;
                collection.add(it.getName());
            }
            $this$map$iv4 = (List)destination$iv$iv5;
            boolean $i$f$reduce2 = false;
            Iterator iterator$iv2 = $this$reduce$iv.iterator();
            if (!iterator$iv2.hasNext()) {
                throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }
            Object accumulator$iv2 = iterator$iv2.next();
            while (iterator$iv2.hasNext()) {
                void next;
                MutableComponent $i$f$mapTo42 = (MutableComponent)iterator$iv2.next();
                MutableComponent acc = (MutableComponent)accumulator$iv2;
                boolean bl8 = false;
                accumulator$iv2 = TextKt.plus(TextKt.plus(acc, " & "), (Component)next);
            }
            losersText = (MutableComponent)accumulator$iv2;
            Iterable $this$any$iv = battle2.getShowdownMessages();
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    String it = (String)element$iv;
                    boolean bl9 = false;
                    if (!StringsKt.contains$default((CharSequence)it, (CharSequence)"capture", (boolean)false, (int)2, null)) continue;
                    bl = true;
                    break block13;
                }
                bl = false;
            }
        }
        boolean wasCaught = bl;
        battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(battle2, wasCaught, (List<? extends BattleActor>)losers, losersText, winnersText){
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ boolean $wasCaught;
            final /* synthetic */ List<BattleActor> $losers;
            final /* synthetic */ MutableComponent $losersText;
            final /* synthetic */ MutableComponent $winnersText;
            {
                this.$battle = $battle;
                this.$wasCaught = $wasCaught;
                this.$losers = $losers;
                this.$losersText = $losersText;
                this.$winnersText = $winnersText;
                super(0);
            }

            /*
             * Could not resolve type clashes
             * Unable to fully structure code
             */
            @NotNull
            public final DispatchResult invoke() {
                block20: {
                    block19: {
                        if (this.$battle.isPvW()) {
                            block16: {
                                $this$first$iv = this.$battle.getActors();
                                $i$f$first = false;
                                for (Object element$iv : $this$first$iv) {
                                    it = element$iv;
                                    $i$a$-first-WinInstruction$invoke$1$nonPlayerActor$1 = false;
                                    if (!(it.getType() == ActorType.WILD)) continue;
                                    break block16;
                                }
                                throw new NoSuchElementException("Collection contains no element matching the predicate.");
                            }
                            nonPlayerActor = element$iv;
                            wildPokemon = (BattlePokemon)CollectionsKt.first(nonPlayerActor.getPokemonList());
                            if (!this.$wasCaught) {
                                block17: {
                                    $this$any$iv = this.$losers;
                                    $i$f$any = false;
                                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                                        v0 = false;
                                    } else {
                                        for (E element$iv : $this$any$iv) {
                                            it = (BattleActor)element$iv /* !! */ ;
                                            $i$a$-any-WinInstruction$invoke$1$1 = false;
                                            if (!Intrinsics.areEqual((Object)it.getUuid(), (Object)wildPokemon.getUuid())) continue;
                                            v0 = true;
                                            break block17;
                                        }
                                        v0 = false;
                                    }
                                }
                                if (v0) {
                                    v1 = wildPokemon.getEffectedPokemon().getEntity();
                                    if (v1 != null) {
                                        block18: {
                                            element$iv /* !! */  = this.$battle.getActors();
                                            var12_14 = v1;
                                            $i$f$firstOrNull = false;
                                            for (T element$iv : $this$firstOrNull$iv) {
                                                it = (BattleActor)element$iv;
                                                $i$a$-firstOrNull-WinInstruction$invoke$1$2 = false;
                                                if (!(it.getType() == ActorType.PLAYER)) continue;
                                                v2 = element$iv;
                                                break block18;
                                            }
                                            v2 = null;
                                        }
                                        element$iv = v2;
                                        v3 = element$iv instanceof PlayerBattleActor != false ? (PlayerBattleActor)element$iv : null;
                                        var12_14.setKiller((ServerPlayer)(v3 != null ? v3.getEntity() : null));
                                    }
                                }
                            }
                        }
                        if (this.$wasCaught) break block20;
                        if (!this.$battle.isPvW()) ** GOTO lbl-1000
                        $this$any$iv = this.$losers;
                        $i$f$any = false;
                        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                            v4 = false;
                        } else {
                            for (Object element$iv : $this$any$iv) {
                                it = element$iv;
                                $i$a$-any-WinInstruction$invoke$1$blackedOut$1 = false;
                                if (!(it instanceof PlayerBattleActor)) continue;
                                v4 = true;
                                break block19;
                            }
                            v4 = false;
                        }
                    }
                    if (v4) {
                        v5 = true;
                    } else lbl-1000:
                    // 2 sources

                    {
                        v5 = blackedOut = false;
                    }
                    if (blackedOut) {
                        var4_4 = new Object[]{this.$losersText};
                        v6 = LocalizationUtilsKt.battleLang("lose", (Object[])var4_4);
                        Intrinsics.checkNotNullExpressionValue((Object)v6, (String)"battleLang(\"lose\", losersText)");
                        v7 = TextKt.red(v6);
                    } else {
                        var4_4 = new Object[]{this.$winnersText};
                        v8 = LocalizationUtilsKt.battleLang("win", (Object[])var4_4);
                        Intrinsics.checkNotNullExpressionValue((Object)v8, (String)"battleLang(\"win\", winnersText)");
                        v7 = TextKt.gold(v8);
                    }
                    lang = v7;
                    this.$battle.broadcastChatMessage((Component)lang);
                    return new WaitDispatch(2.0f);
                }
                return DispatchResultKt.getGO();
            }
        }));
        battle2.dispatchGo((Function0<Unit>)((Function0)new Function0<Unit>(battle2, (List<? extends BattleActor>)winners, (List<? extends BattleActor>)losers, wasCaught){
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ List<BattleActor> $winners;
            final /* synthetic */ List<BattleActor> $losers;
            final /* synthetic */ boolean $wasCaught;
            {
                this.$battle = $battle;
                this.$winners = $winners;
                this.$losers = $losers;
                this.$wasCaught = $wasCaught;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            public final void invoke() {
                void events$iv;
                void $this$iv;
                this.$battle.end();
                EventObservable<BattleVictoryEvent> eventObservable = CobblemonEvents.BATTLE_VICTORY;
                BattleVictoryEvent[] battleVictoryEventArray = new BattleVictoryEvent[]{new BattleVictoryEvent(this.$battle, this.$winners, this.$losers, this.$wasCaught)};
                boolean $i$f$post = false;
                $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
                void $this$forEach$iv$iv = events$iv;
                boolean $i$f$forEach = false;
                int n = ((void)$this$forEach$iv$iv).length;
                for (int i = 0; i < n; ++i) {
                    void element$iv$iv;
                    void var9_9 = element$iv$iv = $this$forEach$iv$iv[i];
                    boolean bl = false;
                    void it = var9_9;
                }
                ShowdownInterpreter.INSTANCE.getLastCauser().remove(this.$battle.getBattleId());
            }
        }));
    }
}

