package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nDialogueOptionSetDTO.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueOptionSetDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueOptionSetDTO\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,32:1\n1855#2,2:33\n1549#2:35\n1620#2,2:36\n1622#2:39\n1#3:38\n*S KotlinDebug\n*F\n+ 1 DialogueOptionSetDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueOptionSetDTO\n*L\n22#1:33,2\n30#1:35\n30#1:36,2\n30#1:39\n*E\n"])
public class DialogueOptionSetDTO(deadline: Float = 0.0F, options: List<DialogueOptionDTO> = CollectionsKt.emptyList()) : Encodable, Decodable {
   public final var deadline: Float
   public final var options: List<DialogueOptionDTO>

   init {
      this.deadline = deadline;
      this.options = options;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.writeFloat(this.deadline);
      buffer.writeInt(this.options.size());

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as DialogueOptionDTO).encode(buffer);
      }
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      this.deadline = buffer.readFloat();
      val `$this$map$iv`: java.lang.Iterable = RangesKt.until(0, buffer.readInt()) as java.lang.Iterable;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));
      val var8: java.util.Iterator = `$this$map$iv`.iterator();

      while (var8.hasNext()) {
         val `item$iv$iv`: Int = (var8 as IntIterator).nextInt();
         val var12: DialogueOptionDTO = new DialogueOptionDTO(null, null, false, 7, null);
         var12.decode(buffer);
         `destination$iv$iv`.add(var12);
      }

      this.options = `destination$iv$iv` as MutableList<DialogueOptionDTO>;
   }

   fun DialogueOptionSetDTO() {
      this(0.0F, null, 3, null);
   }
}
