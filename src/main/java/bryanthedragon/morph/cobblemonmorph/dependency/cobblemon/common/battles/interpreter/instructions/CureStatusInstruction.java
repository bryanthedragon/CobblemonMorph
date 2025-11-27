/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.CureStatusInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattlePersistentStatusPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/CureStatusInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
@SourceDebugExtension(value={"SMAP\nCureStatusInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CureStatusInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/CureStatusInstruction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,58:1\n1#2:59\n*E\n"})
public final class CureStatusInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public CureStatusInstruction(@NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.message = message;
    }

    @NotNull
    public final BattleMessage getMessage() {
        return this.message;
    }

    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        Object object;
        BattlePokemon pokemon;
        BattlePokemon maybeActivePokemon;
        block5: {
            block4: {
                Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
                Object object2 = this.message.actorAndActivePokemon(0, battle2);
                maybeActivePokemon = object2 != null && (object2 = (ActiveBattlePokemon)object2.getSecond()) != null ? ((ActiveBattlePokemon)object2).getBattlePokemon() : null;
                BattlePokemon maybePartyPokemon = this.message.battlePokemon(0, battle2);
                BattlePokemon battlePokemon = maybeActivePokemon;
                if (battlePokemon == null && (battlePokemon = maybePartyPokemon) == null) {
                    return;
                }
                pokemon = battlePokemon;
                object = this.message.argumentAt(1);
                if (object == null) break block4;
                String string = object;
                Statuses statuses = Statuses.INSTANCE;
                String p0 = string;
                boolean bl = false;
                Status status = statuses.getStatus(p0);
                object = status;
                if (status != null) break block5;
            }
            return;
        }
        Object status = object;
        Effect effect = BattleMessage.effect$default(this.message, null, 1, null);
        ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle2, effect, pokemon);
        PokemonBattle.dispatchWaiting$default(battle2, 0.0f, (Function0)new Function0<Unit>(pokemon, maybeActivePokemon, this, effect, (Status)status, battle2){
            final /* synthetic */ BattlePokemon $pokemon;
            final /* synthetic */ BattlePokemon $maybeActivePokemon;
            final /* synthetic */ CureStatusInstruction this$0;
            final /* synthetic */ Effect $effect;
            final /* synthetic */ Status $status;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.$pokemon = $pokemon;
                this.$maybeActivePokemon = $maybeActivePokemon;
                this.this$0 = $receiver;
                this.$effect = $effect;
                this.$status = $status;
                this.$battle = $battle;
                super(0);
            }

            public final void invoke() {
                MutableComponent mutableComponent;
                MutableComponent pokemonName = this.$pokemon.getName();
                this.$pokemon.getEffectedPokemon().setStatus(null);
                this.$pokemon.sendUpdate();
                if (this.$maybeActivePokemon != null) {
                    Pair<String, String> pair = this.this$0.getMessage().pnxAndUuid(0);
                    if (pair != null) {
                        Pair<String, String> pair2 = pair;
                        PokemonBattle pokemonBattle = this.$battle;
                        Pair<String, String> it = pair2;
                        boolean bl = false;
                        pokemonBattle.sendUpdate(new BattlePersistentStatusPacket((String)it.getFirst(), null));
                    }
                }
                Effect effect = this.$effect;
                Effect.Type type = effect != null ? effect.getType() : null;
                if ((type == null ? -1 : invoke.WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) == 1) {
                    var5_4 = new Object[]{pokemonName};
                    mutableComponent = LocalizationUtilsKt.battleLang("curestatus." + this.$effect.getId(), var5_4);
                } else {
                    var5_4 = new Object[]{pokemonName};
                    mutableComponent = MiscUtils.asTranslated(this.$status.getRemoveMessage(), var5_4);
                }
                MutableComponent lang = mutableComponent;
                Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                this.$battle.broadcastChatMessage((Component)lang);
                this.$pokemon.getContextManager().remove(this.$status.getShowdownName(), BattleContext.Type.STATUS);
                ((Map)this.$battle.getMinorBattleActions()).put(this.$pokemon.getUuid(), this.this$0.getMessage());
            }
        }, 1, null);
    }
}

