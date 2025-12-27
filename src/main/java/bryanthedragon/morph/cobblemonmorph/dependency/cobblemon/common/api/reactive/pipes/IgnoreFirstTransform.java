package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform

public class IgnoreFirstTransform<T>(amount: Int = 1) : Transform<T, T> {
   public final var amount: Int

   init {
      this.amount = amount;
   }

   public override operator fun invoke(input: Any): Any {
      if (this.amount > 0) {
         this.amount += -1;
         this.noTransform(false);
         throw new KotlinNothingValueException();
      } else {
         return (T)input;
      }
   }

   override fun noTransform(terminate: Boolean): Void {
      return Transform.DefaultImpls.noTransform(this, terminate);
   }

   fun IgnoreFirstTransform() {
      this(0, 1, null);
   }
}
