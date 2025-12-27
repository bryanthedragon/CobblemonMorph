package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input

import com.bedrockk.molang.runtime.struct.MoStruct

public sealed interface DialogueInput {
   public var timeout: DialogueTimeout?

   public abstract fun toMoLangStruct(activeInput: ActiveInput): MoStruct {
   }

   public abstract fun handle(activeInput: ActiveInput, value: String) {
   }
}
