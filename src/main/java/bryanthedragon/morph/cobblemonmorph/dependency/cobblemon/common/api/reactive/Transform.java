package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive

public interface Transform<I, O> {
   public abstract operator fun invoke(input: Any): Any {
   }

   public open fun noTransform(terminate: Boolean): Nothing {
   }

   public companion object {
      private final val noTransformNoTerminateThrowable: NoTransformThrowable = new NoTransformThrowable(false)
      private final val noTransformTerminateThrowable: NoTransformThrowable = new NoTransformThrowable(true)
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <I, O> noTransform(`$this`: Transform<I, O>, terminate: Boolean): Void {
         throw if (terminate)
            Transform.Companion.access$getNoTransformTerminateThrowable$p() as java.lang.Throwable
            else
            Transform.Companion.access$getNoTransformNoTerminateThrowable$p() as java.lang.Throwable;
      }
   }
}
