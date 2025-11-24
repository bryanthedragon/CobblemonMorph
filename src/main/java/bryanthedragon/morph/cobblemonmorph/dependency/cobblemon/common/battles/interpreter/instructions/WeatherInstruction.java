/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/WeatherInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
@SourceDebugExtension(value={"SMAP\nWeatherInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WeatherInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/WeatherInstruction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,52:1\n1#2:53\n*E\n"})
public final class WeatherInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public WeatherInstruction(@NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.message = message;
    }

    @NotNull
    public final BattleMessage getMessage() {
        return this.message;
    }

    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        BattlePokemon source;
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Object object = this.message.effectAt(0);
        if (object == null || (object = object.getId()) == null) {
            return;
        }
        Object weather = object;
        BattlePokemon battlePokemon = source = BattleMessage.battlePokemonFromOptional$default(this.message, battle2, null, 2, null);
        if (battlePokemon != null) {
            BattlePokemon it = battlePokemon;
            boolean bl = false;
            ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle2, BattleMessage.effect$default(this.message, null, 1, null), source);
        }
        battle2.dispatchWaiting(1.5f, (Function0<Unit>)((Function0)new Function0<Unit>(this, (String)weather, battle2){
            final /* synthetic */ WeatherInstruction this$0;
            final /* synthetic */ String $weather;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.this$0 = $receiver;
                this.$weather = $weather;
                this.$battle = $battle;
                super(0);
            }

            public final void invoke() {
                MutableComponent mutableComponent;
                if (this.this$0.getMessage().hasOptionalArgument("upkeep")) {
                    mutableComponent = LocalizationUtilsKt.battleLang("weather." + this.$weather + ".upkeep", new Object[0]);
                } else if (!Intrinsics.areEqual((Object)this.$weather, (Object)"none")) {
                    BattleContext[] battleContextArray = new BattleContext[]{ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), BattleContext.Type.WEATHER, this.$battle)};
                    this.$battle.getContextManager().add(battleContextArray);
                    mutableComponent = LocalizationUtilsKt.battleLang("weather." + this.$weather + ".start", new Object[0]);
                } else {
                    Collection<BattleContext> collection = this.$battle.getContextManager().get(BattleContext.Type.WEATHER);
                    if (collection == null || (collection = collection.iterator()) == null || (collection = (BattleContext)collection.next()) == null || (collection = collection.getId()) == null) {
                        return;
                    }
                    Collection<BattleContext> oldWeather = collection;
                    BattleContext.Type[] typeArray = new BattleContext.Type[]{BattleContext.Type.WEATHER};
                    this.$battle.getContextManager().clear(typeArray);
                    mutableComponent = LocalizationUtilsKt.battleLang("weather." + (String)((Object)oldWeather) + ".end", new Object[0]);
                }
                MutableComponent lang = mutableComponent;
                Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                this.$battle.broadcastChatMessage((Component)lang);
            }
        }));
    }
}

