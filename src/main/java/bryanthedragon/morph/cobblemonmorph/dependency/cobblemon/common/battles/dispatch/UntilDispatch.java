package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch

public class UntilDispatch(condition: () -> Boolean) : DispatchResult {
   public final val condition: () -> Boolean

   init {
      this.condition = condition;
   }

   public override fun canProceed(): Boolean {
      return this.condition.invoke() as java.lang.Boolean;
   }
}
