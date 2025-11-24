/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleQueueRequestPacket;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/RequestInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "battleActor", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "getBattleActor", "()Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class RequestInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleActor battleActor;
    @NotNull
    private final BattleMessage message;

    public RequestInstruction(@NotNull BattleActor battleActor, @NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)battleActor, (String)"battleActor");
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.battleActor = battleActor;
        this.message = message;
    }

    @NotNull
    public final BattleActor getBattleActor() {
        return this.battleActor;
    }

    @NotNull
    public final BattleMessage getMessage() {
        return this.message;
    }

    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        battle2.log("Request Instruction");
        if (StringsKt.contains$default((CharSequence)this.message.getRawMessage(), (CharSequence)"teamPreview", (boolean)false, (int)2, null)) {
            return;
        }
        String[] stringArray = new String[]{"|request|"};
        ShowdownActionRequest request = (ShowdownActionRequest)BattleRegistry.INSTANCE.getGson().fromJson((String)StringsKt.split$default((CharSequence)this.message.getRawMessage(), (String[])stringArray, (boolean)false, (int)0, (int)6, null).get(1), ShowdownActionRequest.class);
        request.sanitize(battle2, this.battleActor);
        if (battle2.getStarted()) {
            battle2.dispatchGo((Function0<Unit>)((Function0)new Function0<Unit>(this, request, battle2){
                final /* synthetic */ RequestInstruction this$0;
                final /* synthetic */ ShowdownActionRequest $request;
                final /* synthetic */ PokemonBattle $battle;
                {
                    this.this$0 = $receiver;
                    this.$request = $request;
                    this.$battle = $battle;
                    super(0);
                }

                public final void invoke() {
                    BattleActor battleActor = this.this$0.getBattleActor();
                    ShowdownActionRequest showdownActionRequest = this.$request;
                    Intrinsics.checkNotNullExpressionValue((Object)showdownActionRequest, (String)"request");
                    battleActor.sendUpdate(new BattleQueueRequestPacket(showdownActionRequest));
                    this.this$0.getBattleActor().setRequest(this.$request);
                    this.this$0.getBattleActor().getResponses().clear();
                    if (this.$request.getForceSwitch().contains(true)) {
                        this.$battle.doWhenClear((Function0<Unit>)((Function0)new Function0<Unit>(this.this$0){
                            final /* synthetic */ RequestInstruction this$0;
                            {
                                this.this$0 = $receiver;
                                super(0);
                            }

                            public final void invoke() {
                                this.this$0.getBattleActor().setMustChoose(true);
                                this.this$0.getBattleActor().sendUpdate(new BattleMakeChoicePacket());
                            }
                        }));
                    }
                }
            }));
        } else {
            this.battleActor.setRequest(request);
            this.battleActor.getResponses().clear();
        }
    }
}

