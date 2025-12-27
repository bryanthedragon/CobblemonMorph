package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform

public class TakeFirstTransform<I>(amount: Int = 1) : Transform<I, I> {
   private final var amount: Int

   init {
      this.amount = amount;
   }

   public override operator fun invoke(input: Any): Any {
      if (this.amount > 0) {
         this.amount += -1;
         return (I)input;
      } else {
         this.noTransform(true);
         throw new KotlinNothingValueException();
      }
   }

   override fun noTransform(terminate: Boolean): Void {
      return Transform.DefaultImpls.noTransform(this, terminate);
   }

   fun TakeFirstTransform() {
      this(0, 1, null);
   }
}
