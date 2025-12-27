package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input

import com.bedrockk.molang.runtime.struct.QueryStruct
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue
import java.util.ArrayList;
import java.util.HashMap
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nDialogueOptionSetInput.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueOptionSetInput.kt\ncom/cobblemon/mod/common/api/dialogue/input/DialogueOptionSetInput\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,49:1\n766#2:50\n857#2,2:51\n288#2,2:53\n*S KotlinDebug\n*F\n+ 1 DialogueOptionSetInput.kt\ncom/cobblemon/mod/common/api/dialogue/input/DialogueOptionSetInput\n*L\n31#1:50\n31#1:51,2\n34#1:53,2\n*E\n"])
public class DialogueOptionSetInput(options: MutableList<DialogueOption>, timeout: DialogueTimeout? = null, vertical: Boolean = false) : DialogueInput {
   public final var options: MutableList<DialogueOption>
   public open var timeout: DialogueTimeout?
   public final var vertical: Boolean

   init {
      this.options = options;
      this.timeout = timeout;
      this.vertical = vertical;
   }

   public constructor() : this(new ArrayList<>(), null, false)
   public fun getVisibleOptions(activeDialogue: ActiveDialogue): List<DialogueOption> {
      val `$this$filter$iv`: java.lang.Iterable = this.options;
      val `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         if ((`element$iv$iv` as DialogueOption).isVisible().invoke(activeDialogue)) {
            `destination$iv$iv`.add(`element$iv$iv`);
         }
      }

      return `destination$iv$iv` as MutableList<DialogueOption>;
   }

   public open fun toMoLangStruct(activeInput: ActiveInput): QueryStruct {
      return new QueryStruct(new HashMap<>());
   }

   public override fun handle(activeInput: ActiveInput, value: String) {
      val var6: java.util.Iterator = this.options.iterator();

      var var10000: Any;
      while (true) {
         if (var6.hasNext()) {
            val `element$iv`: Any = var6.next();
            if (!((`element$iv` as DialogueOption).getValue() == value)) {
               continue;
            }

            var10000 = `element$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      val option: DialogueOption = var10000 as DialogueOption;
      if (var10000 as DialogueOption != null) {
         if (!option.isSelectable().invoke(activeInput.getActiveDialogue())) {
            Cobblemon.INSTANCE
               .getLOGGER()
               .warn(
                  "Dialogue option $value is not selectable but ${activeInput.getActiveDialogue().getPlayerEntity().m_36316_().getName()} selected it anyway"
               );
            activeInput.getActiveDialogue().close();
         } else if (!option.isVisible().invoke(activeInput.getActiveDialogue())) {
            Cobblemon.INSTANCE
               .getLOGGER()
               .warn("Dialogue option $value is not visible but ${activeInput.getActiveDialogue().getPlayerEntity().m_36316_().getName()} selected it anyway");
            activeInput.getActiveDialogue().close();
         }

         option.getAction().invoke(activeInput.getActiveDialogue(), value);
      } else {
         Cobblemon.INSTANCE.getLOGGER().warn("No option with value $value found in dialogue option set");
      }
   }
}
