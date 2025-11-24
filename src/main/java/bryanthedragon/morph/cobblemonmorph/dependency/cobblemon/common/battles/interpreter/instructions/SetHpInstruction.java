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
 *  kotlin.math.MathKt
 *  kotlin.text.StringsKt
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleHealthChangePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u0011\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u000e\u001a\u0004\b\u0012\u0010\u0010\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/SetHpInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "getActor", "()Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "privateMessage", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getPrivateMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "publicMessage", "getPublicMessage", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class SetHpInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleActor actor;
    @NotNull
    private final BattleMessage publicMessage;
    @NotNull
    private final BattleMessage privateMessage;

    public SetHpInstruction(@NotNull BattleActor actor, @NotNull BattleMessage publicMessage, @NotNull BattleMessage privateMessage) {
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

    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        PokemonBattle.dispatchWaiting$default(battle2, 0.0f, (Function0)new Function0<Unit>(this, battle2){
            final /* synthetic */ SetHpInstruction this$0;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.this$0 = $receiver;
                this.$battle = $battle;
                super(0);
            }

            public final void invoke() {
                String[] stringArray;
                String[] stringArray2;
                Pair<String, String> pair = this.this$0.getPrivateMessage().pnxAndUuid(0);
                if (pair == null) {
                    return;
                }
                String pnx = (String)pair.component1();
                Object object = this.this$0.getPrivateMessage().argumentAt(1);
                if (object == null || (object = StringsKt.split$default((CharSequence)((CharSequence)object), (String[])(stringArray2 = new String[]{"/"}), (boolean)false, (int)0, (int)6, null)) == null || (object = (String)CollectionsKt.getOrNull((List)object, (int)0)) == null || (object = StringsKt.toFloatOrNull((String)object)) == null) {
                    return;
                }
                float flatHp = ((Float)object).floatValue();
                Object object2 = this.this$0.getPublicMessage().argumentAt(1);
                if (object2 == null || (object2 = StringsKt.split$default((CharSequence)((CharSequence)object2), (String[])(stringArray = new String[]{"/"}), (boolean)false, (int)0, (int)6, null)) == null || (object2 = (String)CollectionsKt.getOrNull((List)object2, (int)0)) == null || (object2 = StringsKt.toFloatOrNull((String)object2)) == null) {
                    return;
                }
                float ratioHp = ((Float)object2).floatValue() * 0.01f;
                BattlePokemon battlePokemon = this.this$0.getPrivateMessage().battlePokemon(0, this.$battle);
                if (battlePokemon == null) {
                    return;
                }
                BattlePokemon battlePokemon2 = battlePokemon;
                battlePokemon2.getEffectedPokemon().setCurrentHealth(MathKt.roundToInt((float)flatHp));
                PokemonBattle.sendSidedUpdate$default(this.$battle, this.this$0.getActor(), new BattleHealthChangePacket(pnx, flatHp, null, 4, null), new BattleHealthChangePacket(pnx, ratioHp, null, 4, null), false, 8, null);
                if (!this.this$0.getPublicMessage().hasOptionalArgument("silent")) {
                    Object object3 = BattleMessage.effect$default(this.this$0.getPublicMessage(), null, 1, null);
                    if (object3 == null || (object3 = object3.getId()) == null) {
                        return;
                    }
                    Object effectID = object3;
                    MutableComponent lang = LocalizationUtilsKt.battleLang("sethp." + (String)effectID, new Object[0]);
                    Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                    this.$battle.broadcastChatMessage((Component)lang);
                }
                ((Map)this.$battle.getMinorBattleActions()).put(battlePokemon2.getUuid(), this.this$0.getPublicMessage());
            }
        }, 1, null);
    }
}

