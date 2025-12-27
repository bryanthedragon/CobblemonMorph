package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleQueueRequestPacket
import kotlin.jvm.functions.Function0

public class RequestInstruction(battleActor: BattleActor, message: BattleMessage) : InterpreterInstruction {
   public final val battleActor: BattleActor
   public final val message: BattleMessage

   init {
      this.battleActor = battleActor;
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.log("Request Instruction");
      if (!StringsKt.contains$default(this.message.getRawMessage(), "teamPreview", false, 2, null)) {
         val request: ShowdownActionRequest = BattleRegistry.INSTANCE
            .getGson()
            .fromJson(
               StringsKt.split$default(this.message.getRawMessage(), new java.lang.String[]{"|request|"}, false, 0, 6, null).get(1) as java.lang.String,
               ShowdownActionRequest.class
            ) as ShowdownActionRequest;
         request.sanitize(battle, this.battleActor);
         if (battle.getStarted()) {
            battle.dispatchGo((new Function0<Unit>(this, request, battle) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
                  this.$request = `$request`;
                  this.$battle = `$battle`;
               }

               public final void invoke() {
                  val var10000: BattleActor = this.this$0.getBattleActor();
                  val var10003: ShowdownActionRequest = this.$request;
                  var10000.sendUpdate(new BattleQueueRequestPacket(var10003));
                  this.this$0.getBattleActor().setRequest(this.$request);
                  this.this$0.getBattleActor().getResponses().clear();
                  if (this.$request.getForceSwitch().contains(true)) {
                     this.$battle.doWhenClear((new Function0<Unit>(this.this$0) {
                        {
                           super(0);
                           this.this$0 = `$receiver`;
                        }

                        public final void invoke() {
                           this.this$0.getBattleActor().setMustChoose(true);
                           this.this$0.getBattleActor().sendUpdate(new BattleMakeChoicePacket());
                        }
                     }) as () -> Unit);
                  }
               }
            }) as () -> Unit);
         } else {
            this.battleActor.setRequest(request);
            this.battleActor.getResponses().clear();
         }
      }
   }
}
