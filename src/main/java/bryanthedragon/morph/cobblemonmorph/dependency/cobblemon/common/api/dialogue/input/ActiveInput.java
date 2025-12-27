package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input

import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt
import java.util.UUID
import net.minecraft.server.MinecraftServer

public class ActiveInput(activeDialogue: ActiveDialogue, dialogueInput: DialogueInput) {
   public final val activeDialogue: ActiveDialogue
   public final val dialogueInput: DialogueInput
   public final val inputId: UUID
   public final val startTime: Long
   public final val struct: MoValue

   init {
      this.activeDialogue = activeDialogue;
      this.dialogueInput = dialogueInput;
      this.inputId = UUID.randomUUID();
      val var10001: MinecraftServer = DistributionUtilsKt.server();
      this.startTime = var10001.m_129783_().m_46467_();
      this.struct = this.toMoLangStruct();
   }

   public fun handle(input: String) {
      val var10000: MinecraftServer = DistributionUtilsKt.server();
      val secondsToChoose: Float = (float)(var10000.m_129783_().m_46467_() - this.startTime) / 20.0F;
      this.activeDialogue.getRuntime().getEnvironment().setSimpleVariable("seconds_taken_to_input", new DoubleValue(secondsToChoose));
      this.dialogueInput.handle(this, input);
   }

   public fun toMoLangStruct(): MoValue {
      return this.dialogueInput.toMoLangStruct(this);
   }
}
