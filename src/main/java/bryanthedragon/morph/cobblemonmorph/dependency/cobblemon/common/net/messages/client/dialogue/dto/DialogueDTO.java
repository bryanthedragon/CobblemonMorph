package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto

import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ArtificialDialogueFaceProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueFaceProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueSpeaker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ExpressionLikeDialogueFaceProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.PlayerDialogueFaceProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ReferenceDialogueFaceProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueAutoContinueInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueOptionSetInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTextInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.UUID
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nDialogueDTO.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueDTO\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,117:1\n135#2,9:118\n215#2:127\n216#2:129\n144#2:130\n215#2,2:131\n1#3:128\n1179#4,2:133\n1253#4,4:135\n*S KotlinDebug\n*F\n+ 1 DialogueDTO.kt\ncom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueDTO\n*L\n37#1:118,9\n37#1:127\n37#1:129\n37#1:130\n65#1:131,2\n37#1:128\n99#1:133,2\n99#1:135,4\n*E\n"])
public class DialogueDTO : Encodable, Decodable {
   public final lateinit var currentPageDTO: DialoguePageDTO
   public final lateinit var dialogueId: UUID
   public final lateinit var dialogueInput: DialogueInputDTO
   public final var speakers: Map<String, DialogueSpeakerDTO>?


   public constructor(activeDialogue: ActiveDialogue, includeFaces: Boolean)  {
      val var10001: UUID = activeDialogue.getDialogueId();
      this.setDialogueId(var10001);
      var var10000: DialogueDTO = this;
      val var25: java.util.Map;
      if (includeFaces) {
         val input: java.util.Map = activeDialogue.getDialogueReference().getSpeakers();
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Entry element$iv$iv$iv : input.entrySet()) {
            val key: java.lang.String = `element$iv$iv$iv`.getKey() as java.lang.String;
            val value: DialogueSpeaker = `element$iv$iv$iv`.getValue() as DialogueSpeaker;
            val var23: Pair;
            if (value.getFace() is ExpressionLikeDialogueFaceProvider) {
               val resolved: MoValue = MoLangExtensionsKt.resolve(
                  activeDialogue.getRuntime(), (value.getFace() as ExpressionLikeDialogueFaceProvider).getProviderExpression()
               );
               if (resolved is ObjectValue && (resolved as ObjectValue).getObj() is DialogueFaceProvider) {
                  val var10003: DialogueText = value.getName();
                  val var26: MutableComponent = if (var10003 != null) var10003.invoke(activeDialogue) else null;
                  val var10004: Any = (resolved as ObjectValue).getObj();
                  var23 = TuplesKt.to(key, new DialogueSpeakerDTO(var26, var10004 as DialogueFaceProvider));
               } else {
                  var23 = null;
               }
            } else {
               val var27: DialogueText = value.getName();
               var23 = TuplesKt.to(key, new DialogueSpeakerDTO(if (var27 != null) var27.invoke(activeDialogue) else null, value.getFace()));
            }

            if (var23 != null) {
               `destination$iv$iv`.add(var23);
            }
         }

         val var24: java.util.List = `destination$iv$iv` as java.util.List;
         var10000 = this;
         var25 = MapsKt.toMap(var24);
      } else {
         var25 = null;
      }

      var10000.speakers = var25;
      this.setCurrentPageDTO(new DialoguePageDTO(activeDialogue.getCurrentPage(), activeDialogue));
      val var22: DialogueInput = activeDialogue.getActiveInput().getDialogueInput();
      this.setDialogueInput(
         if (var22 is DialogueOptionSetInput)
            new DialogueInputDTO(var22 as DialogueOptionSetInput, activeDialogue)
            else
            (
               if (var22 is DialogueAutoContinueInput)
                  new DialogueInputDTO(var22 as DialogueAutoContinueInput, activeDialogue)
                  else
                  (if (var22 is DialogueTextInput) new DialogueInputDTO(var22 as DialogueTextInput, activeDialogue) else new DialogueInputDTO(activeDialogue))
            )
      );
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.getDialogueId());
      this.getCurrentPageDTO().encode(buffer);
      this.getDialogueInput().encode(buffer);
      buffer.m_236821_(this.speakers, DialogueDTO::encode$lambda$5);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      val var10001: UUID = buffer.m_130259_();
      this.setDialogueId(var10001);
      this.setCurrentPageDTO(new DialoguePageDTO());
      this.getCurrentPageDTO().decode(buffer);
      this.setDialogueInput(new DialogueInputDTO());
      this.getDialogueInput().decode(buffer);
      this.speakers = buffer.m_236868_(DialogueDTO::decode$lambda$10) as MutableMap<java.lang.String, DialogueSpeakerDTO>;
   }

   @JvmStatic
   fun `encode$lambda$5$lambda$4$lambda$1`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: MutableComponent) {
      `$buffer`.m_130083_(v as Component);
   }

   @JvmStatic
   fun `encode$lambda$5$lambda$4$lambda$2`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: DialogueFaceProvider) {
      `$buffer`.m_130070_(if (v is ReferenceDialogueFaceProvider) "reference" else (if (v is ArtificialDialogueFaceProvider) "artificial" else "player"));
   }

   @JvmStatic
   fun `encode$lambda$5$lambda$4$lambda$3`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, aspect: java.lang.String) {
      `$buffer`.m_130070_(aspect);
   }

   @JvmStatic
   fun `encode$lambda$5`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, speakers: java.util.Map) {
      `$buffer`.writeInt(speakers.size());

      for (Entry element$iv : speakers.entrySet()) {
         val key: java.lang.String = `element$iv`.getKey() as java.lang.String;
         val value: DialogueSpeakerDTO = `element$iv`.getValue() as DialogueSpeakerDTO;
         `$buffer`.m_130070_(key);
         `$buffer`.m_236821_(value.getName(), DialogueDTO::encode$lambda$5$lambda$4$lambda$1);
         `$buffer`.m_236821_(value.getFace(), DialogueDTO::encode$lambda$5$lambda$4$lambda$2);
         if (value.getFace() is ArtificialDialogueFaceProvider) {
            `$buffer`.m_130070_((value.getFace() as ArtificialDialogueFaceProvider).getModelType());
            `$buffer`.m_130085_((value.getFace() as ArtificialDialogueFaceProvider).getIdentifier());
            `$buffer`.m_236828_((value.getFace() as ArtificialDialogueFaceProvider).getAspects(), DialogueDTO::encode$lambda$5$lambda$4$lambda$3);
         } else if (value.getFace() is ReferenceDialogueFaceProvider) {
            `$buffer`.writeInt((value.getFace() as ReferenceDialogueFaceProvider).getEntityId());
         } else if (value.getFace() is PlayerDialogueFaceProvider) {
            `$buffer`.m_130077_((value.getFace() as PlayerDialogueFaceProvider).getPlayerId());
         }
      }
   }

   @JvmStatic
   fun `decode$lambda$10$lambda$9$lambda$6`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): MutableComponent {
      return `$buffer`.m_130238_().m_6881_();
   }

   @JvmStatic
   fun `decode$lambda$10$lambda$9$lambda$7`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }

   @JvmStatic
   fun `decode$lambda$10$lambda$9$lambda$8`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }

   @JvmStatic
   fun `decode$lambda$10`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf): java.util.Map {
      val `$this$associate$iv`: java.lang.Iterable = RangesKt.until(0, `$buffer`.readInt()) as java.lang.Iterable;
      val `destination$iv$iv`: java.util.Map = new LinkedHashMap(
         RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(`$this$associate$iv`, 10)), 16)
      );
      val var9: java.util.Iterator = `$this$associate$iv`.iterator();

      while (var9.hasNext()) {
         var var21: Pair;
         label29: {
            val `element$iv$iv`: Int = (var9 as IntIterator).nextInt();
            val key: java.lang.String = `$buffer`.m_130277_();
            val name: MutableComponent = `$buffer`.m_236868_(DialogueDTO::decode$lambda$10$lambda$9$lambda$6) as MutableComponent;
            val faceType: java.lang.String = `$buffer`.m_236868_(DialogueDTO::decode$lambda$10$lambda$9$lambda$7) as java.lang.String;
            if (faceType != null) {
               switch (faceType.hashCode()) {
                  case -985752863:
                     if (faceType.equals("player")) {
                        val var10006: UUID = `$buffer`.m_130259_();
                        var21 = TuplesKt.to(key, new DialogueSpeakerDTO(name, new PlayerDialogueFaceProvider(var10006)));
                        break label29;
                     }
                     break;
                  case -925155509:
                     if (faceType.equals("reference")) {
                        var21 = TuplesKt.to(key, new DialogueSpeakerDTO(name, new ReferenceDialogueFaceProvider(`$buffer`.readInt())));
                        break label29;
                     }
                     break;
                  case 248019002:
                     if (faceType.equals("artificial")) {
                        val modelType: java.lang.String = `$buffer`.m_130277_();
                        val identifier: ResourceLocation = `$buffer`.m_130281_();
                        val var10000: java.util.List = `$buffer`.m_236845_(DialogueDTO::decode$lambda$10$lambda$9$lambda$8);
                        val aspects: java.util.Set = CollectionsKt.toSet(var10000);
                        var21 = TuplesKt.to(key, new DialogueSpeakerDTO(name, new ArtificialDialogueFaceProvider(modelType, identifier, aspects)));
                        break label29;
                     }
                  default:
               }
            }

            var21 = TuplesKt.to(key, new DialogueSpeakerDTO(name, null));
         }

         `destination$iv$iv`.put(var21.getFirst(), var21.getSecond());
      }

      return `destination$iv$iv`;
   }
}
