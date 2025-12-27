package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai

public class FormPokemonBehaviour {
   private final val _idle: IdleBehaviour?
   private final val _moving: MoveBehaviour?
   private final val _resting: RestBehaviour?

   public final val idle: IdleBehaviour
      public final get() {
         var var10000: IdleBehaviour = this._idle;
         if (this._idle == null) {
            var10000 = this.getParent().getIdle();
         }

         return var10000;
      }


   public final val moving: MoveBehaviour
      public final get() {
         var var10000: MoveBehaviour = this._moving;
         if (this._moving == null) {
            var10000 = this.getParent().getMoving();
         }

         return var10000;
      }


   public final lateinit var parent: PokemonBehaviour

   public final val resting: RestBehaviour
      public final get() {
         var var10000: RestBehaviour = this._resting;
         if (this._resting == null) {
            var10000 = this.getParent().getResting();
         }

         return var10000;
      }

}
