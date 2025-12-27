package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownSide
import java.util.ArrayList;
import kotlin.jvm.functions.Function3
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

public class SingleActionRequest(activePokemon: ActiveClientBattlePokemon,
   side: ShowdownSide?,
   moveSet: ShowdownMoveset?,
   forceSwitch: Boolean,
   canCancel: Boolean
) {
   public final val activePokemon: ActiveClientBattlePokemon
   public final val canCancel: Boolean
   public final val forceSwitch: Boolean
   public final val moveSet: ShowdownMoveset?
   public final var response: ShowdownActionResponse?
   public final val side: ShowdownSide?

   init {
      this.activePokemon = activePokemon;
      this.side = side;
      this.moveSet = moveSet;
      this.forceSwitch = forceSwitch;
      this.canCancel = canCancel;
   }

   public companion object {
      public fun composeFrom(actor: ClientBattleActor, request: ShowdownActionRequest): MutableList<SingleActionRequest> {
         val singleActionRequests: java.util.List = new ArrayList();
         singleActionRequests.addAll(
            request.iterate(
               actor.getActivePokemon(), (new Function3<ActiveClientBattlePokemon, ShowdownMoveset, java.lang.Boolean, SingleActionRequest>(request) {
                  {
                     super(3);
                     this.$request = `$request`;
                  }

                  @NotNull
                  public final SingleActionRequest invoke(@NotNull ActiveClientBattlePokemon targetable, @Nullable ShowdownMoveset moveSet, boolean forceSwitch) {
                     return new SingleActionRequest(targetable, this.$request.getSide(), moveSet, forceSwitch, !this.$request.getNoCancel());
                  }
               }) as Function3
            )
         );
         return singleActionRequests;
      }
   }
}
