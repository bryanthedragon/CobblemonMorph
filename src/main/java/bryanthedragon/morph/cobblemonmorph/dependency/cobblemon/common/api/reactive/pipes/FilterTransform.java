package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform

public class FilterTransform<I>(predicate: (Any) -> Boolean) : Transform<I, I> {
   private final val predicate: (Any) -> Boolean

   init {
      this.predicate = predicate;
   }

   public override operator fun invoke(input: Any): Any {
      if (this.predicate.invoke(input) as java.lang.Boolean) {
         return (I)input;
      } else {
         this.noTransform(false);
         throw new KotlinNothingValueException();
      }
   }

   override fun noTransform(terminate: Boolean): Void {
      return Transform.DefaultImpls.noTransform(this, terminate);
   }
}
