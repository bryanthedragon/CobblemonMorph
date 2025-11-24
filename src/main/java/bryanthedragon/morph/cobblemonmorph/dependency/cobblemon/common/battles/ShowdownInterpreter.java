/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function4
 *  kotlin.jvm.functions.Function6
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BasicContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.MissingContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.ContextManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.UnknownInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b+\u0010,J%\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\t\u0010\nJ'\u0010\u000b\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\u000b\u0010\nJ%\u0010\u0011\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001d\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u001d\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0015\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001d\u0010\u001b\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\r\u001a\u00020\u0015\u00a2\u0006\u0004\b\u001b\u0010\u001cR#\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\f0\u001d8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!R>\u0010&\u001a,\u0012\u0004\u0012\u00020\u0015\u0012\"\u0012 \u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020%0\"0\u001d8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b&\u0010\u001fRP\u0010)\u001a>\u0012\u0004\u0012\u00020\u0015\u00124\u00122\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0(\u0012\u0004\u0012\u00020%0'0\u001d8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b)\u0010\u001fRD\u0010*\u001a2\u0012\u0004\u0012\u00020\u0015\u0012(\u0012&\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0(\u0012\u0004\u0012\u00020%0\"0\u001d8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b*\u0010\u001f\u00a8\u0006-"}, d2={"Lcom/cobblemon/mod/common/battles/ShowdownInterpreter;", "", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/api/battles/interpreter/Effect;", "effect", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "pokemon", "", "broadcastAbility", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/api/battles/interpreter/Effect;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)V", "broadcastOptionalAbility", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;", "type", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;", "getContextFromAction", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;", "getContextFromFaint", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;", "", "rawMessage", "interpret", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Ljava/lang/String;)V", "Ljava/util/UUID;", "battleId", "interpretMessage", "(Ljava/util/UUID;Ljava/lang/String;)V", "", "lastCauser", "Ljava/util/Map;", "getLastCauser", "()Ljava/util/Map;", "Lkotlin/Function4;", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "sideInstructionParser", "Lkotlin/Function6;", "", "splitInstructionParser", "updateInstructionParser", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nShowdownInterpreter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShowdownInterpreter.kt\ncom/cobblemon/mod/common/battles/ShowdownInterpreter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,289:1\n1855#2,2:290\n1549#2:295\n1620#2,3:296\n1855#2,2:301\n3792#3:292\n4307#3,2:293\n37#4,2:299\n*S KotlinDebug\n*F\n+ 1 ShowdownInterpreter.kt\ncom/cobblemon/mod/common/battles/ShowdownInterpreter\n*L\n171#1:290,2\n232#1:295\n232#1:296,3\n60#1:301,2\n231#1:292\n231#1:293,2\n234#1:299,2\n*E\n"})
public final class ShowdownInterpreter {
    @NotNull
    public static final ShowdownInterpreter INSTANCE = new ShowdownInterpreter();
    @NotNull
    private static final Map<UUID, BattleMessage> lastCauser = new LinkedHashMap();
    @NotNull
    private static final Map<String, Function4<PokemonBattle, InstructionSet, BattleMessage, Iterator<BattleMessage>, InterpreterInstruction>> updateInstructionParser = new LinkedHashMap();
    @NotNull
    private static final Map<String, Function6<PokemonBattle, BattleActor, InstructionSet, BattleMessage, BattleMessage, Iterator<BattleMessage>, InterpreterInstruction>> splitInstructionParser = new LinkedHashMap();
    @NotNull
    private static final Map<String, Function4<PokemonBattle, BattleActor, InstructionSet, BattleMessage, InterpreterInstruction>> sideInstructionParser = new LinkedHashMap();

    private ShowdownInterpreter() {
    }

    @NotNull
    public final Map<UUID, BattleMessage> getLastCauser() {
        return lastCauser;
    }

    public final void interpretMessage(@NotNull UUID battleId, @NotNull String message) {
        Intrinsics.checkNotNullParameter((Object)battleId, (String)"battleId");
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        if (StringsKt.startsWith$default((String)message, (String)"{\"winner\":\"", (boolean)false, (int)2, null)) {
            return;
        }
        PokemonBattle battle2 = BattleRegistry.INSTANCE.getBattle(battleId);
        if (battle2 == null) {
            Cobblemon.INSTANCE.getLOGGER().info("No battle could be found with the id: " + battleId);
            return;
        }
        DistributionUtilsKt.runOnServer((Function0)new Function0<Unit>(battle2, message){
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ String $message;
            {
                this.$battle = $battle;
                this.$message = $message;
                super(0);
            }

            public final void invoke() {
                this.$battle.getShowdownMessages().add(this.$message);
                ShowdownInterpreter.INSTANCE.interpret(this.$battle, this.$message);
            }
        });
    }

    public final void interpret(@NotNull PokemonBattle battle2, @NotNull String rawMessage) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)rawMessage, (String)"rawMessage");
        PokemonBattle.log$default(battle2, null, 1, null);
        battle2.log(rawMessage);
        PokemonBattle.log$default(battle2, null, 1, null);
        InstructionSet instructionSet = new InstructionSet();
        List battleMessages = new ArrayList();
        try {
            String[] stringArray = new String[]{"\n"};
            List lines = CollectionsKt.toMutableList((Collection)StringsKt.split$default((CharSequence)rawMessage, (String[])stringArray, (boolean)false, (int)0, (int)6, null));
            if (Intrinsics.areEqual(lines.get(0), (Object)"update")) {
                lines.remove(0);
                Iterable $this$forEach$iv = lines;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    String it = (String)element$iv;
                    boolean bl = false;
                    battleMessages.add(new BattleMessage(it));
                }
                Iterator iterator = battleMessages.iterator();
                while (iterator.hasNext()) {
                    BattleMessage message = (BattleMessage)iterator.next();
                    String id = StringsKt.replace$default((String)message.getId(), (String)"|", (String)"", (boolean)false, (int)4, null);
                    Object object = updateInstructionParser.get(id);
                    if (object == null || (object = (InterpreterInstruction)object.invoke((Object)battle2, (Object)instructionSet, (Object)message, iterator)) == null) {
                        object = new UnknownInstruction(message);
                    }
                    Object instruction = object;
                    instructionSet.getInstructions().add((InterpreterInstruction)instruction);
                }
            } else if (Intrinsics.areEqual(lines.get(0), (Object)"sideupdate")) {
                String showdownId = (String)lines.get(1);
                BattleActor targetActor = battle2.getActor(showdownId);
                BattleMessage message = new BattleMessage((String)lines.get(2));
                String id = StringsKt.replace$default((String)message.getId(), (String)"|", (String)"", (boolean)false, (int)4, null);
                if (targetActor == null) {
                    battle2.log("No actor could be found with the showdown id: " + showdownId);
                    return;
                }
                Object object = sideInstructionParser.get(id);
                if (object == null || (object = (InterpreterInstruction)object.invoke((Object)battle2, (Object)targetActor, (Object)instructionSet, (Object)message)) == null) {
                    object = new UnknownInstruction(message);
                }
                Object instruction = object;
                instructionSet.getInstructions().add((InterpreterInstruction)instruction);
            }
            instructionSet.execute(battle2);
        }
        catch (Exception e) {
            Cobblemon.INSTANCE.getLOGGER().error("Caught exception interpreting {}", (Throwable)e);
        }
    }

    public final void broadcastOptionalAbility(@NotNull PokemonBattle battle2, @Nullable Effect effect, @NotNull BattlePokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        if (effect != null && effect.getType() == Effect.Type.ABILITY) {
            this.broadcastAbility(battle2, effect, pokemon);
        }
    }

    public final void broadcastAbility(@NotNull PokemonBattle battle2, @NotNull Effect effect, @NotNull BattlePokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)effect, (String)"effect");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        battle2.dispatchGo((Function0<Unit>)((Function0)new Function0<Unit>(pokemon, effect, battle2){
            final /* synthetic */ BattlePokemon $pokemon;
            final /* synthetic */ Effect $effect;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.$pokemon = $pokemon;
                this.$effect = $effect;
                this.$battle = $battle;
                super(0);
            }

            public final void invoke() {
                Object[] objectArray = new Object[]{this.$pokemon.getName(), this.$effect.getTypelessData()};
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("ability.generic", objectArray);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"ability.gene\u2026e(), effect.typelessData)");
                MutableComponent lang = TextKt.yellow(mutableComponent);
                this.$battle.broadcastChatMessage((Component)lang);
            }
        }));
    }

    /*
     * Unable to fully structure code
     */
    @NotNull
    public final BattleContext getContextFromFaint(@NotNull BattlePokemon pokemon, @NotNull PokemonBattle battle) {
        block25: {
            block24: {
                block26: {
                    Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
                    Intrinsics.checkNotNullParameter((Object)battle, (String)"battle");
                    v0 = battle.getMinorBattleActions().get(pokemon.getUuid());
                    if (v0 == null && (v0 = ShowdownInterpreter.lastCauser.get(battle.getBattleId())) == null) {
                        return new MissingContext(null, 0, null, null, 15, null);
                    }
                    cause = v0;
                    side = pokemon.getActor().getSide();
                    var5_5 = cause.getId();
                    switch (var5_5.hashCode()) {
                        case 1398069333: {
                            if (var5_5.equals("-start")) break;
                            ** break;
                        }
                        case 3357649: {
                            if (!var5_5.equals("move")) {
                                ** break;
                            }
                            ** GOTO lbl23
                        }
                        case -1387046880: {
                            if (!var5_5.equals("-activate")) {
                                ** break;
                            }
                            break block24;
                        }
                        case -56166948: {
                            if (!var5_5.equals("-damage")) ** break;
lbl23:
                            // 2 sources

                            v1 = cause.effect("of");
                            if (v1 != null) {
                                it = v1;
                                $i$a$-let-ShowdownInterpreter$getContextFromFaint$1 = false;
                                v2 = BattleMessage.effect$default(cause, null, 1, null);
                                if (v2 == null || (v2 = v2.getId()) == null) {
                                    v2 = it.getId();
                                }
                                effectID = v2;
                                v3 = cause.optionalArgument("of");
                                Intrinsics.checkNotNull((Object)v3);
                                originPnx = StringsKt.substringBefore$default((String)v3, (char)':', null, (int)2, null);
                                v4 = cause.optionalArgument("of");
                                Intrinsics.checkNotNull((Object)v4);
                                uuid = StringsKt.trim((CharSequence)StringsKt.substringAfter$default((String)v4, (char)':', null, (int)2, null)).toString();
                                origin = battle.getBattlePokemon(originPnx, uuid);
                                v5 = new BasicContext((String)effectID, battle.getTurn(), BattleContext.Type.FAINT, origin);
                            } else {
                                v6 = BattleMessage.effect$default(cause, null, 1, null);
                                if (v6 != null) {
                                    effect = v6;
                                    $i$a$-let-ShowdownInterpreter$getContextFromFaint$2 = false;
                                    $this$filter$iv = BattleContext.Type.values();
                                    $i$f$filter = false;
                                    var12_26 = $this$filter$iv;
                                    destination$iv$iv = new ArrayList<E>();
                                    $i$f$filterTo = false;
                                    for (void element$iv$iv : $this$filterTo$iv$iv) {
                                        it = element$iv$iv;
                                        $i$a$-filter-ShowdownInterpreter$getContextFromFaint$2$damagingContexts$1 = false;
                                        if (!it.getDamaging()) continue;
                                        destination$iv$iv.add(element$iv$iv);
                                    }
                                    damagingContexts = (List)destination$iv$iv;
                                    $this$map$iv = (Collection[])damagingContexts;
                                    $i$f$map = false;
                                    destination$iv$iv = $this$map$iv;
                                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                    $i$f$mapTo = false;
                                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                                        it = (BattleContext.Type)item$iv$iv;
                                        var21_40 = destination$iv$iv;
                                        $i$a$-map-ShowdownInterpreter$getContextFromFaint$2$contextBuckets$1 = false;
                                        v7 = pokemon.getContextManager().get(it);
                                        if (v7 == null && (v7 = side.getContextManager().get(it)) == null) {
                                            v7 = battle.getContextManager().get(it);
                                        }
                                        var21_40.add(v7);
                                    }
                                    contextBuckets = (List)destination$iv$iv;
                                    $this$toTypedArray$iv = contextBuckets;
                                    $i$f$toTypedArray = false;
                                    thisCollection$iv = $this$toTypedArray$iv;
                                    $this$map$iv = thisCollection$iv.toArray(new Collection[0]);
                                    v8 = ContextManager.Companion.scoop(effect.getId(), Arrays.copyOf($this$map$iv, $this$map$iv.length));
                                } else {
                                    v8 = v5 = null;
                                }
                                if (v8 == null) {
                                    v9 = ShowdownInterpreter.lastCauser.get(battle.getBattleId());
                                    if (v9 != null) {
                                        it = v9;
                                        $i$a$-let-ShowdownInterpreter$getContextFromFaint$3 = false;
                                        v10 = it.effectAt(1);
                                        Intrinsics.checkNotNull((Object)v10);
                                        move = v10.getId();
                                        origin = it.battlePokemon(0, battle);
                                        v11 = new BasicContext(move, battle.getTurn(), BattleContext.Type.FAINT, origin);
                                    } else {
                                        v11 = null;
                                    }
                                    v5 = v11 != null ? (BattleContext)v11 : (BattleContext)new MissingContext(null, 0, null, null, 15, null);
                                }
                            }
                            break block25;
                        }
                    }
                    v5 = cause.effectAt(1);
                    if (v5 == null) break block26;
                    it = v5;
                    $i$a$-let-ShowdownInterpreter$getContextFromFaint$4 = false;
                    effectID = StringsKt.contains$default((CharSequence)it.getId(), (CharSequence)"perish", (boolean)false, (int)2, null) != false ? "perishsong" : it.getId();
                    var9_19 = new Collection[]{pokemon.getContextManager().get(BattleContext.Type.VOLATILE)};
                    v12 = ContextManager.Companion.scoop(effectID, var9_19);
                    v5 = v12;
                    if (v12 != null) break block25;
                }
                v5 = new MissingContext(null, 0, null, null, 15, null);
                break block25;
            }
            v13 = cause.effectAt(1);
            if (v13 != null) {
                it = v13;
                $i$a$-let-ShowdownInterpreter$getContextFromFaint$5 = false;
                origin = cause.battlePokemon(0, battle);
                v5 = new BasicContext(it.getId(), battle.getTurn(), BattleContext.Type.FAINT, origin);
            } else {
                v5 = new MissingContext(null, 0, null, null, 15, null);
            }
            break block25;
lbl123:
            // 5 sources

            v5 = new MissingContext(null, 0, null, null, 15, null);
        }
        return v5;
    }

    /*
     * Unable to fully structure code
     */
    @NotNull
    public final BattleContext getContextFromAction(@NotNull BattleMessage message, @NotNull BattleContext.Type type, @NotNull PokemonBattle battle) {
        block10: {
            block13: {
                block12: {
                    block11: {
                        block9: {
                            Intrinsics.checkNotNullParameter((Object)message, (String)"message");
                            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
                            Intrinsics.checkNotNullParameter((Object)battle, (String)"battle");
                            v0 = BattleMessage.actorAndActivePokemonFromOptional$default(message, battle, null, 2, null);
                            if (v0 == null) break block9;
                            it = v0;
                            $i$a$-let-ShowdownInterpreter$getContextFromAction$1 = false;
                            v1 = message.effectAt(1);
                            if (v1 != null && (v1 = v1.getId()) != null) ** GOTO lbl15
                            v2 = message.effectAt(0);
                            if (v2 == null) {
                                v3 = new MissingContext(null, 0, null, null, 15, null);
                            } else {
                                v1 = v2.getId();
lbl15:
                                // 2 sources

                                effectID = v1;
                                v3 = new BasicContext((String)effectID, battle.getTurn(), type, ((ActiveBattlePokemon)it.getSecond()).getBattlePokemon());
                            }
                            break block10;
                        }
                        v4 = message.actorAndActivePokemon(0, battle);
                        if (v4 == null) break block11;
                        it = v4;
                        $i$a$-let-ShowdownInterpreter$getContextFromAction$2 = false;
                        v5 = message.effectAt(1);
                        if (v5 == null || (v5 = v5.getId()) == null) {
                            v3 = new MissingContext(null, 0, null, null, 15, null);
                        } else {
                            effectID = v5;
                            v6 = ShowdownInterpreter.lastCauser.get(battle.getBattleId());
                            if (v6 == null || (v6 = v6.battlePokemon(0, battle)) == null) {
                                v3 = new MissingContext(null, 0, null, null, 15, null);
                            } else {
                                origin = v6;
                                v3 = new BasicContext((String)effectID, battle.getTurn(), type, (BattlePokemon)origin);
                            }
                        }
                        break block10;
                    }
                    v7 = ShowdownInterpreter.lastCauser.get(battle.getBattleId());
                    if (v7 == null) break block12;
                    it = v7;
                    $i$a$-let-ShowdownInterpreter$getContextFromAction$3 = false;
                    v8 = message.effectAt(1);
                    if (v8 != null && (v8 = v8.getId()) != null) ** GOTO lbl49
                    v9 = message.effectAt(0);
                    if (v9 == null) {
                        v10 = new MissingContext(null, 0, null, null, 15, null);
                    } else {
                        v8 = v9.getId();
lbl49:
                        // 2 sources

                        effectID = v8;
                        v10 = new BasicContext((String)effectID, battle.getTurn(), type, it.battlePokemon(0, battle));
                    }
                    break block13;
                }
                v10 = v3 = null;
            }
            if (v10 == null) {
                v3 = new MissingContext(null, 0, null, null, 15, null);
            }
        }
        return v3;
    }

    static {
        updateInstructionParser.put("split", 1.INSTANCE);
        Object[] objectArray = new String[]{"player", "teamsize", "gametype", "gen", "tier", "rated", "clearpoke", "poke", "teampreview", "start", "rule", "t:", "", "capture"};
        Iterable $this$forEach$iv = CollectionsKt.listOf((Object[])objectArray);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String it = (String)element$iv;
            boolean bl = false;
            updateInstructionParser.put(it, 2.1.INSTANCE);
        }
        updateInstructionParser.put("-ability", 3.INSTANCE);
        updateInstructionParser.put("-activate", 4.INSTANCE);
        updateInstructionParser.put("bagitem", 5.INSTANCE);
        updateInstructionParser.put("-boost", 6.INSTANCE);
        updateInstructionParser.put("-block", 7.INSTANCE);
        updateInstructionParser.put("cant", 8.INSTANCE);
        updateInstructionParser.put("-clearallboost", 9.INSTANCE);
        updateInstructionParser.put("-clearnegativeboost", 10.INSTANCE);
        updateInstructionParser.put("-copyboost", 11.INSTANCE);
        updateInstructionParser.put("-crit", 12.INSTANCE);
        updateInstructionParser.put("-curestatus", 13.INSTANCE);
        updateInstructionParser.put("detailschange", 14.INSTANCE);
        updateInstructionParser.put("-endability", 15.INSTANCE);
        updateInstructionParser.put("-end", 16.INSTANCE);
        updateInstructionParser.put("-enditem", 17.INSTANCE);
        updateInstructionParser.put("-fail", 18.INSTANCE);
        updateInstructionParser.put("faint", 19.INSTANCE);
        updateInstructionParser.put("-fieldactivate", 20.INSTANCE);
        updateInstructionParser.put("-fieldend", 21.INSTANCE);
        updateInstructionParser.put("-fieldstart", 22.INSTANCE);
        updateInstructionParser.put("-hitcount", 23.INSTANCE);
        updateInstructionParser.put("-immune", 24.INSTANCE);
        updateInstructionParser.put("-invertboost", 25.INSTANCE);
        updateInstructionParser.put("-item", 26.INSTANCE);
        updateInstructionParser.put("-mega", 27.INSTANCE);
        updateInstructionParser.put("-miss", 28.INSTANCE);
        updateInstructionParser.put("move", 29.INSTANCE);
        updateInstructionParser.put("-nothing", 30.INSTANCE);
        updateInstructionParser.put("pp_update", 31.INSTANCE);
        updateInstructionParser.put("-prepare", 32.INSTANCE);
        updateInstructionParser.put("-mustrecharge", 33.INSTANCE);
        updateInstructionParser.put("replace", 34.INSTANCE);
        updateInstructionParser.put("-resisted", 35.INSTANCE);
        updateInstructionParser.put("-resisted", 36.INSTANCE);
        updateInstructionParser.put("-setboost", 37.INSTANCE);
        updateInstructionParser.put("-sideend", 38.INSTANCE);
        updateInstructionParser.put("-sidestart", 39.INSTANCE);
        updateInstructionParser.put("-singlemove", 40.INSTANCE);
        updateInstructionParser.put("-singleturn", 41.INSTANCE);
        updateInstructionParser.put("-start", 42.INSTANCE);
        updateInstructionParser.put("-status", 43.INSTANCE);
        updateInstructionParser.put("-supereffective", 44.INSTANCE);
        updateInstructionParser.put("-swapboost", 45.INSTANCE);
        updateInstructionParser.put("-swapsideconditions", 46.INSTANCE);
        updateInstructionParser.put("-terastallize", 47.INSTANCE);
        updateInstructionParser.put("-transform", 48.INSTANCE);
        updateInstructionParser.put("turn", 49.INSTANCE);
        updateInstructionParser.put("-unboost", 50.INSTANCE);
        updateInstructionParser.put("upkeep", 51.INSTANCE);
        updateInstructionParser.put("-weather", 52.INSTANCE);
        updateInstructionParser.put("win", 53.INSTANCE);
        updateInstructionParser.put("-zbroken", 54.INSTANCE);
        updateInstructionParser.put("-zpower", 55.INSTANCE);
        sideInstructionParser.put("error", 56.INSTANCE);
        sideInstructionParser.put("request", 57.INSTANCE);
        splitInstructionParser.put("-damage", 58.INSTANCE);
        splitInstructionParser.put("drag", 59.INSTANCE);
        splitInstructionParser.put("-heal", 60.INSTANCE);
        splitInstructionParser.put("-sethp", 61.INSTANCE);
        splitInstructionParser.put("switch", 62.INSTANCE);
    }
}

