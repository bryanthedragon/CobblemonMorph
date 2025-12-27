package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class DialogueOptionDTO(text: MutableComponent = TextKt.text(""), value: String = "", selectable: Boolean = true) : Encodable, Decodable {
   public final var selectable: Boolean
   public final var text: MutableComponent
   public final var value: String

   init {
      this.text = text;
      this.value = value;
      this.selectable = selectable;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130083_(this.text as Component);
      buffer.m_130070_(this.value);
      buffer.writeBoolean(this.selectable);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      val var10001: MutableComponent = buffer.m_130238_().m_6881_();
      this.text = var10001;
      val var2: java.lang.String = buffer.m_130277_();
      this.value = var2;
      this.selectable = buffer.readBoolean();
   }

   fun DialogueOptionDTO() {
      this(null, null, false, 7, null);
   }
}
