/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ShowdownIdentifiable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.HealInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleHealthChangePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattlePersistentStatusPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u0011\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u000e\u001a\u0004\b\u0012\u0010\u0010\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/HealInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "getActor", "()Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "privateMessage", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getPrivateMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "publicMessage", "getPublicMessage", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
@SourceDebugExtension(value={"SMAP\nHealInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/HealInstruction\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,98:1\n1549#2:99\n1620#2,3:100\n1549#2:103\n1620#2,3:104\n*S KotlinDebug\n*F\n+ 1 HealInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/HealInstruction\n*L\n39#1:99\n39#1:100,3\n40#1:103\n40#1:104,3\n*E\n"})
public final class HealInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleActor actor;
    @NotNull
    private final BattleMessage publicMessage;
    @NotNull
    private final BattleMessage privateMessage;

    public HealInstruction(@NotNull BattleActor actor, @NotNull BattleMessage publicMessage, @NotNull BattleMessage privateMessage) {
        Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
        Intrinsics.checkNotNullParameter((Object)publicMessage, (String)"publicMessage");
        Intrinsics.checkNotNullParameter((Object)privateMessage, (String)"privateMessage");
        this.actor = actor;
        this.publicMessage = publicMessage;
        this.privateMessage = privateMessage;
    }

    @NotNull
    public final BattleActor getActor() {
        return this.actor;
    }

    @NotNull
    public final BattleMessage getPublicMessage() {
        return this.publicMessage;
    }

    @NotNull
    public final BattleMessage getPrivateMessage() {
        return this.privateMessage;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        void $this$mapTo$iv$iv;
        Collection collection;
        void $this$mapTo$iv$iv2;
        String[] stringArray;
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Pair<String, String> pair = this.privateMessage.pnxAndUuid(0);
        String pnx = pair != null ? (String)pair.getFirst() : null;
        BattlePokemon battlePokemon = this.privateMessage.battlePokemon(0, battle2);
        if (battlePokemon == null) {
            return;
        }
        BattlePokemon battlePokemon2 = battlePokemon;
        Object object = this.privateMessage.argumentAt(1);
        if (object == null || (object = StringsKt.split$default((CharSequence)((CharSequence)object), (String[])(stringArray = new String[]{" "}), (boolean)false, (int)0, (int)6, null)) == null) {
            return;
        }
        Object rawHpAndStatus = object;
        String string = (String)CollectionsKt.getOrNull((List)rawHpAndStatus, (int)0);
        if (string == null) {
            return;
        }
        String rawHpRatio = string;
        stringArray = new String[]{"/"};
        Iterable $this$map$iv = StringsKt.split$default((CharSequence)rawHpRatio, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
        boolean $i$f$map22 = false;
        Iterable iterable = $this$map$iv;
        Iterable destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            void it;
            String string2 = (String)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            Float f = StringsKt.toFloatOrNull((String)it);
            if (f == null) {
                return;
            }
            collection.add(Float.valueOf(f.floatValue()));
        }
        List newHealth = (List)destination$iv$iv;
        String[] $i$f$map22 = new String[]{"/"};
        Iterable $this$map$iv2 = StringsKt.split$default((CharSequence)rawHpRatio, (String[])$i$f$map22, (boolean)false, (int)0, (int)6, null);
        boolean $i$f$map = false;
        destination$iv$iv = $this$map$iv2;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv2, (int)10));
        boolean $i$f$mapTo2 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            String bl = (String)item$iv$iv;
            collection = destination$iv$iv2;
            boolean bl2 = false;
            Float f = StringsKt.toFloatOrNull((String)it);
            if (f == null) {
                return;
            }
            collection.add(Float.valueOf(f.floatValue() / ((Number)newHealth.get(1)).floatValue()));
        }
        List newHealthRatio = (List)destination$iv$iv2;
        Effect effect = BattleMessage.effect$default(this.privateMessage, null, 1, null);
        ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle2, effect, battlePokemon2);
        PokemonBattle.dispatchWaiting$default(battle2, 0.0f, (Function0)new Function0<Unit>(pnx, battle2, this, (List<Float>)newHealth, (List<Float>)newHealthRatio, battlePokemon2, effect, (List<String>)rawHpAndStatus){
            final /* synthetic */ String $pnx;
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ HealInstruction this$0;
            final /* synthetic */ List<Float> $newHealth;
            final /* synthetic */ List<Float> $newHealthRatio;
            final /* synthetic */ BattlePokemon $battlePokemon;
            final /* synthetic */ Effect $effect;
            final /* synthetic */ List<String> $rawHpAndStatus;
            {
                this.$pnx = $pnx;
                this.$battle = $battle;
                this.this$0 = $receiver;
                this.$newHealth = $newHealth;
                this.$newHealthRatio = $newHealthRatio;
                this.$battlePokemon = $battlePokemon;
                this.$effect = $effect;
                this.$rawHpAndStatus = $rawHpAndStatus;
                super(0);
            }

            /*
             * Unable to fully structure code
             */
            public final void invoke() {
                block20: {
                    block22: {
                        block24: {
                            block25: {
                                block23: {
                                    block19: {
                                        block21: {
                                            if (this.$pnx != null) {
                                                PokemonBattle.sendSidedUpdate$default(this.$battle, this.this$0.getActor(), new BattleHealthChangePacket(this.$pnx, ((Number)this.$newHealth.get(0)).floatValue(), this.$newHealth.get(1)), new BattleHealthChangePacket(this.$pnx, ((Number)this.$newHealthRatio.get(0)).floatValue(), null, 4, null), false, 8, null);
                                            }
                                            if (silent = this.this$0.getPrivateMessage().hasOptionalArgument("silent")) break block20;
                                            if (!this.this$0.getPrivateMessage().hasOptionalArgument("zeffect")) break block21;
                                            var3_2 = new Object[]{this.$battlePokemon.getName()};
                                            v0 = LocalizationUtilsKt.battleLang("heal.zeffect", var3_2);
                                            break block22;
                                        }
                                        if (!this.this$0.getPrivateMessage().hasOptionalArgument("wisher")) break block23;
                                        v1 = this.this$0.getPrivateMessage().optionalArgument("wisher");
                                        Intrinsics.checkNotNull((Object)v1);
                                        name = v1;
                                        v2 = name.toLowerCase(Locale.ROOT);
                                        Intrinsics.checkNotNullExpressionValue((Object)v2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                                        var5_3 = v2;
                                        var6_4 = ShowdownIdentifiable.Companion.getREGEX$common();
                                        var7_5 = "";
                                        showdownId = var6_4.replace((CharSequence)var5_3, var7_5);
                                        $this$firstOrNull$iv = this.this$0.getActor().getPokemonList();
                                        $i$f$firstOrNull = false;
                                        var8_10 = $this$firstOrNull$iv.iterator();
                                        while (var8_10.hasNext()) {
                                            element$iv = var8_10.next();
                                            it = (BattlePokemon)element$iv;
                                            $i$a$-firstOrNull-HealInstruction$invoke$1$lang$wisher$1 = false;
                                            if (!Intrinsics.areEqual((Object)it.getEffectedPokemon().showdownId(), (Object)showdownId)) continue;
                                            v3 = element$iv;
                                            break block19;
                                        }
                                        v3 = null;
                                    }
                                    wisher = v3;
                                    $this$firstOrNull$iv = new Object[1];
                                    v4 = wisher;
                                    if (v4 == null || (v4 = v4.getName()) == null) {
                                        v4 = this.this$0.getActor().nameOwned((String)name);
                                    }
                                    $this$firstOrNull$iv[0] = v4;
                                    v0 = LocalizationUtilsKt.battleLang("heal.wish", (Object[])$this$firstOrNull$iv);
                                    break block22;
                                }
                                if (!this.this$0.getPrivateMessage().hasOptionalArgument("from")) break block24;
                                v5 = this.$effect;
                                Intrinsics.checkNotNull((Object)v5);
                                if (invoke.WhenMappings.$EnumSwitchMapping$0[v5.getType().ordinal()] != 1) break block25;
                                var5_3 = this.$effect.getId();
                                switch (var5_3.hashCode()) {
                                    case 1803082547: {
                                        if (!var5_3.equals("shellbell")) {
                                            break;
                                        }
                                        ** GOTO lbl57
                                    }
                                    case 1756801656: {
                                        if (!var5_3.equals("leftovers")) {
                                            break;
                                        }
                                        ** GOTO lbl57
                                    }
                                    case 1328235077: {
                                        if (!var5_3.equals("blacksludge")) break;
lbl57:
                                        // 3 sources

                                        $this$firstOrNull$iv = new Object[]{this.$battlePokemon.getName(), this.$effect.getTypelessData()};
                                        v0 = LocalizationUtilsKt.battleLang("heal.leftovers", (Object[])$this$firstOrNull$iv);
                                        break block22;
                                    }
                                }
                                $this$firstOrNull$iv = new Object[]{this.$battlePokemon.getName(), this.$effect.getTypelessData()};
                                v0 = LocalizationUtilsKt.battleLang("heal.item", (Object[])$this$firstOrNull$iv);
                                break block22;
                            }
                            if (Intrinsics.areEqual((Object)this.$effect.getId(), (Object)"drain")) {
                                v6 = BattleMessage.battlePokemonFromOptional$default(this.this$0.getPrivateMessage(), this.$battle, null, 2, null);
                                if (v6 == null) {
                                    return;
                                }
                                drained = v6;
                                $i$f$firstOrNull = new Object[]{drained.getName()};
                                v0 = LocalizationUtilsKt.battleLang("heal.drain", $i$f$firstOrNull);
                            } else {
                                var6_4 = new Object[]{this.$battlePokemon.getName()};
                                v0 = LocalizationUtilsKt.battleLang("heal." + this.$effect.getId(), (Object[])var6_4);
                            }
                            break block22;
                        }
                        name = new Object[]{this.$battlePokemon.getName()};
                        v0 = LocalizationUtilsKt.battleLang("heal.generic", name);
                    }
                    lang = v0;
                    Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                    this.$battle.broadcastChatMessage((Component)lang);
                }
                ((Map)this.$battle.getMinorBattleActions()).put(this.$battlePokemon.getUuid(), this.this$0.getPrivateMessage());
                this.$battlePokemon.getEffectedPokemon().setCurrentHealth((int)((Number)this.$newHealth.get(0)).floatValue());
                v7 = (String)CollectionsKt.getOrNull(this.$rawHpAndStatus, (int)1);
                if (v7 == null) {
                    return;
                }
                rawStatus = v7;
                v8 = Statuses.INSTANCE.getStatus(rawStatus);
                if (v8 == null) {
                    return;
                }
                status = v8;
                if (status instanceof PersistentStatus) {
                    v9 = this.$battlePokemon.getEffectedPokemon().getStatus();
                    if (!Intrinsics.areEqual((Object)(v9 != null ? v9.getStatus() : null), (Object)status)) {
                        this.$battlePokemon.getEffectedPokemon().applyStatus((PersistentStatus)status);
                        if (this.$pnx != null) {
                            this.$battle.sendUpdate(new BattlePersistentStatusPacket(this.$pnx, (PersistentStatus)status));
                        }
                        if (!silent) {
                            var4_9 = status.getApplyMessage();
                            var5_3 = this.$battle;
                            var6_4 = this.$battlePokemon;
                            it = var4_9;
                            $i$a$-let-HealInstruction$invoke$1$1 = false;
                            var9_12 = new Object[]{var6_4.getName()};
                            v10 = MiscUtilsKt.asTranslated(it, var9_12);
                            Intrinsics.checkNotNullExpressionValue((Object)v10, (String)"it.asTranslated(battlePokemon.getName())");
                            var5_3.broadcastChatMessage((Component)v10);
                        }
                    }
                }
            }
        }, 1, null);
    }
}

