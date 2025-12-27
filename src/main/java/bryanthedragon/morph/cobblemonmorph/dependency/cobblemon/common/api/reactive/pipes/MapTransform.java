package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.pipes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform

public class MapTransform<I, O>(mapping: (Any) -> Any) : Transform<I, O> {
   private final val mapping: (Any) -> Any

   init {
      this.mapping = mapping;
   }

   public override operator fun invoke(input: Any): Any {
      return (O)this.mapping.invoke(input);
   }

   override fun noTransform(terminate: Boolean): Void {
      return Transform.DefaultImpls.noTransform(this, terminate);
   }
}
