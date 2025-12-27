package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform

public class StopAfterTransform<I>(predicate: (Any) -> Boolean) : Transform<I, I> {
   public final var finished: Boolean
   public final val predicate: (Any) -> Boolean

   init {
      this.predicate = predicate;
   }

   public override operator fun invoke(input: Any): Any {
      if (this.finished) {
         this.noTransform(true);
         throw new KotlinNothingValueException();
      } else {
         if (this.predicate.invoke(input) as java.lang.Boolean) {
            this.finished = true;
         }

         return (I)input;
      }
   }

   override fun noTransform(terminate: Boolean): Void {
      return Transform.DefaultImpls.noTransform(this, terminate);
   }
}
