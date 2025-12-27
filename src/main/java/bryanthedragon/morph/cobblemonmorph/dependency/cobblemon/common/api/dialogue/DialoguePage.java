package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.struct.MoStruct
import com.bedrockk.molang.runtime.struct.QueryStruct
import com.bedrockk.molang.runtime.value.MoValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueNoInput
import com.google.gson.JsonArray
import java.util.ArrayList;
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@SourceDebugExtension(["SMAP\nDialoguePage.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialoguePage.kt\ncom/cobblemon/mod/common/api/dialogue/DialoguePage\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,71:1\n1855#2,2:72\n*S KotlinDebug\n*F\n+ 1 DialoguePage.kt\ncom/cobblemon/mod/common/api/dialogue/DialoguePage\n*L\n65#1:72,2\n*E\n"])
public class DialoguePage(id: String = "",
   speaker: String? = null,
   lines: MutableList<DialogueText> = (new ArrayList()) as java.util.List,
   input: DialogueInput = (new DialogueNoInput(null, 1, null)) as DialogueInput,
   clientActions: MutableList<Expression> = (new ArrayList()) as java.util.List,
   escapeAction: DialogueAction? = null
) {
   public final var clientActions: MutableList<Expression>
   public final var escapeAction: DialogueAction?
   public final var id: String
   public final var input: DialogueInput
   public final var lines: MutableList<DialogueText>
   public final var speaker: String?

   init {
      this.id = id;
      this.speaker = speaker;
      this.lines = lines;
      this.input = input;
      this.clientActions = clientActions;
      this.escapeAction = escapeAction;
   }

   public fun toMoLangStruct(activeDialogue: ActiveDialogue): MoStruct {
      return new QueryStruct(
         MapsKt.hashMapOf(
            new Pair[]{
               TuplesKt.to("id", DialoguePage::toMoLangStruct$lambda$0),
               TuplesKt.to("input", DialoguePage::toMoLangStruct$lambda$1),
               TuplesKt.to("lines", DialoguePage::toMoLangStruct$lambda$3)
            }
         )
      );
   }

   @JvmStatic
   fun `toMoLangStruct$lambda$0`(`this$0`: DialoguePage, var1: MoParams): Any {
      return new StringValue(`this$0`.id);
   }

   @JvmStatic
   fun `toMoLangStruct$lambda$1`(`$activeDialogue`: ActiveDialogue, var1: MoParams): Any {
      return `$activeDialogue`.getActiveInput().getStruct();
   }

   @JvmStatic
   fun `toMoLangStruct$lambda$3`(`this$0`: DialoguePage, `$activeDialogue`: ActiveDialogue, var2: MoParams): Any {
      val array: JsonArray = new JsonArray();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         array.add((`element$iv` as DialogueText).invoke(`$activeDialogue`).getString());
      }

      return MoValue.of(array);
   }

   fun DialoguePage() {
      this(null, null, null, null, null, null, 63, null);
   }

   @SourceDebugExtension(["SMAP\nDialoguePage.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialoguePage.kt\ncom/cobblemon/mod/common/api/dialogue/DialoguePage$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,71:1\n1549#2:72\n1620#2,3:73\n1#3:76\n*S KotlinDebug\n*F\n+ 1 DialoguePage.kt\ncom/cobblemon/mod/common/api/dialogue/DialoguePage$Companion\n*L\n50#1:72\n50#1:73,3\n*E\n"])
   public companion object {
      @JvmOverloads
      public fun of(
         id: String = "",
         speaker: String? = null,
         lines: Iterable<MutableComponent>,
         input: DialogueInput = (new DialogueNoInput(null, 1, null)) as DialogueInput,
         clientActions: Iterable<Expression> = CollectionsKt.emptyList() as java.lang.Iterable,
         escapeAction: ((ActiveDialogue) -> Unit)? = null
      ): DialoguePage {
         val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(lines, 10));

         for (Object item$iv$iv : lines) {
            `destination$iv$iv`.add(new WrappedDialogueText(`item$iv$iv` as MutableComponent));
         }

         val var18: java.util.List = `destination$iv$iv` as java.util.List;
         var var10000: java.lang.String = id;
         var var10001: java.lang.String = speaker;
         val var10002: java.util.List = CollectionsKt.toMutableList(var18);
         var var10003: DialogueInput = input;
         val var10004: java.util.List = CollectionsKt.toMutableList(clientActions);
         val var10005: FunctionDialogueAction;
         if (escapeAction != null) {
            val var21: FunctionDialogueAction = new FunctionDialogueAction((new Function2<ActiveDialogue, java.lang.String, Unit>(escapeAction) {
               {
                  super(2);
                  this.$func = `$func`;
               }

               public final void invoke(@NotNull ActiveDialogue activeDialogue, @Nullable java.lang.String var2) {
                  this.$func.invoke(activeDialogue);
               }
            }) as (ActiveDialogue?, java.lang.String?) -> Unit);
            var10000 = id;
            var10001 = speaker;
            var10003 = input;
            var10005 = var21;
         } else {
            var10005 = null;
         }

         return new DialoguePage(var10000, var10001, var10002, var10003, var10004, var10005);
      }

      @JvmOverloads
      fun of(
         id: java.lang.String,
         speaker: java.lang.String?,
         lines: MutableIterable<MutableComponent>,
         input: DialogueInput,
         clientActions: MutableIterable<Expression>
      ): DialoguePage {
         return of$default(this, id, speaker, lines, input, clientActions, null, 32, null);
      }

      @JvmOverloads
      fun of(id: java.lang.String, speaker: java.lang.String?, lines: MutableIterable<MutableComponent>, input: DialogueInput): DialoguePage {
         return of$default(this, id, speaker, lines, input, null, null, 48, null);
      }

      @JvmOverloads
      fun of(id: java.lang.String, speaker: java.lang.String?, lines: MutableIterable<MutableComponent>): DialoguePage {
         return of$default(this, id, speaker, lines, null, null, null, 56, null);
      }

      @JvmOverloads
      fun of(id: java.lang.String, lines: MutableIterable<MutableComponent>): DialoguePage {
         return of$default(this, id, null, lines, null, null, null, 58, null);
      }

      @JvmOverloads
      fun of(lines: MutableIterable<MutableComponent>): DialoguePage {
         return of$default(this, null, null, lines, null, null, null, 59, null);
      }
   }
}
