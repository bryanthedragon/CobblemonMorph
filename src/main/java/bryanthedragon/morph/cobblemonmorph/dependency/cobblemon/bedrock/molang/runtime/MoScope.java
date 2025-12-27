package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime;

import com.bedrockk.molang.runtime.value.MoValue;

public class MoScope {
   private boolean isBreak = false;
   private boolean isContinue = false;
   private MoValue returnValue;

   public boolean isBreak() {
      return this.isBreak;
   }

   public boolean isContinue() {
      return this.isContinue;
   }

   public MoValue getReturnValue() {
      return this.returnValue;
   }

   public void setBreak(boolean isBreak) {
      this.isBreak = isBreak;
   }

   public void setContinue(boolean isContinue) {
      this.isContinue = isContinue;
   }

   public void setReturnValue(MoValue returnValue) {
      this.returnValue = returnValue;
   }
}
