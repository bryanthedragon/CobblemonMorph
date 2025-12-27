package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching

public data CaptureContext(numberOfShakes: Int, isSuccessfulCapture: Boolean, isCriticalCapture: Boolean) {
   public final val isCriticalCapture: Boolean
   public final val isSuccessfulCapture: Boolean
   public final val numberOfShakes: Int

   init {
      this.numberOfShakes = numberOfShakes;
      this.isSuccessfulCapture = isSuccessfulCapture;
      this.isCriticalCapture = isCriticalCapture;
   }

   public operator fun component1(): Int {
      return this.numberOfShakes;
   }

   public operator fun component2(): Boolean {
      return this.isSuccessfulCapture;
   }

   public operator fun component3(): Boolean {
      return this.isCriticalCapture;
   }

   public fun copy(
      numberOfShakes: Int = this.numberOfShakes,
      isSuccessfulCapture: Boolean = this.isSuccessfulCapture,
      isCriticalCapture: Boolean = this.isCriticalCapture
   ): CaptureContext {
      return new CaptureContext(numberOfShakes, isSuccessfulCapture, isCriticalCapture);
   }

   public override fun toString(): String {
      return "CaptureContext(numberOfShakes=${this.numberOfShakes}, isSuccessfulCapture=${this.isSuccessfulCapture}, isCriticalCapture=${this.isCriticalCapture})";
   }

   public override fun hashCode(): Int {
      var var10000: Int = Integer.hashCode(this.numberOfShakes) * 31;
      var var10001: Byte = this.isSuccessfulCapture;
      if (this.isSuccessfulCapture) {
         var10001 = 1;
      }

      var10000 = (var10000 + var10001) * 31;
      var10001 = this.isCriticalCapture;
      if (this.isCriticalCapture) {
         var10001 = 1;
      }

      return var10000 + var10001;
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is CaptureContext) {
         return false;
      } else {
         val var2: CaptureContext = other as CaptureContext;
         if (this.numberOfShakes != (other as CaptureContext).numberOfShakes) {
            return false;
         } else if (this.isSuccessfulCapture != var2.isSuccessfulCapture) {
            return false;
         } else {
            return this.isCriticalCapture == var2.isCriticalCapture;
         }
      }
   }

   public companion object {
      public fun successful(critical: Boolean = false): CaptureContext {
         return if (critical) new CaptureContext(1, true, true) else new CaptureContext(4, true, false);
      }
   }
}
