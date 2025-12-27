package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch

public class WaitDispatch(delaySeconds: Float) : DispatchResult {
   public final val readyTime: Long

   init {
      this.readyTime = System.currentTimeMillis() + (int)(delaySeconds * 1000);
   }

   public override fun canProceed(): Boolean {
      return System.currentTimeMillis() >= this.readyTime;
   }
}
