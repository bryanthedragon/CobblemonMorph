package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.struct.MoStruct
import com.bedrockk.molang.runtime.struct.QueryStruct
import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.MoValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.ActiveInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTimeout
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ServerRealTimeTaskTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.DialogueOpenedPacket
import java.util.UUID
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nActiveDialogue.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActiveDialogue.kt\ncom/cobblemon/mod/common/api/dialogue/ActiveDialogue\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,125:1\n1#2:126\n*E\n"])
public class ActiveDialogue(playerEntity: ServerPlayer, dialogueReference: Dialogue) {
   public final var activeInput: ActiveInput
   public final var currentPage: DialoguePage

   public final val currentPageIndex: Int
      public final get() {
         return this.dialogueReference.getPages().indexOf(this.currentPage);
      }


   public final val dialogueId: UUID
   public final var dialogueReference: Dialogue
   public final var playerEntity: ServerPlayer
   public final val playerStruct: ObjectValue<ServerPlayer>
   public final val runtime: MoLangRuntime

   init {
      this.playerEntity = playerEntity;
      this.dialogueReference = dialogueReference;
      this.dialogueId = UUID.randomUUID();
      this.runtime = MoLangFunctions.INSTANCE.setup(new MoLangRuntime());
      this.currentPage = this.dialogueReference.getPages().get(0);
      this.playerStruct = MoLangFunctions.INSTANCE.asMoLangValue(this.playerEntity);
      this.activeInput = new ActiveInput(this, this.currentPage.getInput());
      val var10000: MoLangFunctions = MoLangFunctions.INSTANCE;
      val var10001: MoLangFunctions = MoLangFunctions.INSTANCE;
      val var10002: MoLangEnvironment = this.runtime.getEnvironment();
      var10000.addFunctions(
         MoLangFunctions.getQueryStruct$default(var10001, var10002, null, 1, null),
         MapsKt.mapOf(new Pair[]{TuplesKt.to("dialogue", ActiveDialogue::_init_$lambda$0), TuplesKt.to("player", ActiveDialogue::_init_$lambda$1)})
      );
   }

   public fun setPage(value: MoValue) {
      var var9: DialoguePage;
      if (value is StringValue) {
         val var5: java.util.Iterator = this.dialogueReference.getPages().iterator();

         while (true) {
            if (!var5.hasNext()) {
               var9 = null;
               break;
            }

            val var6: Any = var5.next();
            if ((var6 as DialoguePage).getId() == (value as StringValue).value) {
               var9 = (DialoguePage)var6;
               break;
            }
         }

         var9 = var9;
         if (var9 == null) {
            Cobblemon.INSTANCE.getLOGGER().error("Dialogue requested page ${(value as StringValue).value} but it doesn't exist");
            return;
         }
      } else {
         val pageNum: Int = (int)value.asDouble();
         if (pageNum < 0 || pageNum >= this.dialogueReference.getPages().size()) {
            Cobblemon.INSTANCE.getLOGGER().error("Dialogue requested page $pageNum but it doesn't exist");
            return;
         }

         var9 = this.dialogueReference.getPages().get(pageNum);
      }

      this.setPage(var9);
   }

   public fun isActive(): Boolean {
      return DialogueManager.INSTANCE.getActiveDialogues().get(this.playerEntity.m_20148_()) == this;
   }

   public fun incrementPage() {
      this.setPage(this.getCurrentPageIndex() + 1);
   }

   public fun setPage(page: DialoguePage) {
      this.currentPage = page;
      this.activeInput = new ActiveInput(this, this.currentPage.getInput());
      val var10000: DialogueTimeout = this.currentPage.getInput().getTimeout();
      val deadline: java.lang.Float = if (var10000 != null) var10000.getDuration() else null;
      val inputId: UUID = this.activeInput.getInputId();
      if (deadline != null && deadline > 0.0F) {
         ServerRealTimeTaskTracker.INSTANCE.after(deadline, (new Function0<Unit>(inputId, this) {
            {
               super(0);
               this.$inputId = `$inputId`;
               this.this$0 = `$receiver`;
            }

            public final void invoke() {
               if (this.$inputId == this.this$0.getActiveInput().getInputId() && this.this$0.isActive()) {
                  val var10000: DialogueTimeout = this.this$0.getActiveInput().getDialogueInput().getTimeout();
                  if (var10000 != null) {
                     val var1: DialogueAction = var10000.getAction();
                     if (var1 != null) {
                        var1.invoke(this.this$0, null);
                     }
                  }
               }
            }
         }) as () -> Unit);
      }

      CobblemonNetwork.INSTANCE.sendPacket(this.playerEntity, new DialogueOpenedPacket(this, false));
   }

   public fun setPage(index: Int) {
      if (index == this.dialogueReference.getPages().size()) {
         this.close();
      } else if (index >= 0 && index <= this.dialogueReference.getPages().size()) {
         this.setPage(this.dialogueReference.getPages().get(index));
      } else {
         Cobblemon.INSTANCE.getLOGGER().error("Dialogue requested page $index but it doesn't exist");
      }
   }

   public fun toMoLangStruct(): MoStruct {
      return MoLangFunctions.INSTANCE
         .addStandardFunctions(
            new QueryStruct(
               MapsKt.hashMapOf(
                  new Pair[]{
                     TuplesKt.to("current_page", ActiveDialogue::toMoLangStruct$lambda$3),
                     TuplesKt.to("current_page_number", ActiveDialogue::toMoLangStruct$lambda$4),
                     TuplesKt.to("next_page", ActiveDialogue::toMoLangStruct$lambda$5),
                     TuplesKt.to("set_page", ActiveDialogue::toMoLangStruct$lambda$6),
                     TuplesKt.to("close", ActiveDialogue::toMoLangStruct$lambda$7),
                     TuplesKt.to("input", ActiveDialogue::toMoLangStruct$lambda$8)
                  }
               )
            )
         );
   }

   public fun close() {
      DialogueManager.INSTANCE.stopDialogue(this.playerEntity);
   }

   public fun escape() {
      var var10000: DialogueAction = this.currentPage.getEscapeAction();
      if (var10000 == null) {
         var10000 = this.dialogueReference.getEscapeAction();
      }

      var10000.invoke(this, null);
   }

   @JvmStatic
   fun `_init_$lambda$0`(`this$0`: ActiveDialogue, var1: MoParams): Any {
      return `this$0`.toMoLangStruct();
   }

   @JvmStatic
   fun `_init_$lambda$1`(`this$0`: ActiveDialogue, var1: MoParams): Any {
      return `this$0`.playerStruct;
   }

   @JvmStatic
   fun `toMoLangStruct$lambda$3`(`this$0`: ActiveDialogue, var1: MoParams): Any {
      return `this$0`.currentPage.toMoLangStruct(`this$0`);
   }

   @JvmStatic
   fun `toMoLangStruct$lambda$4`(`this$0`: ActiveDialogue, var1: MoParams): Any {
      return new DoubleValue(`this$0`.getCurrentPageIndex());
   }

   @JvmStatic
   fun `toMoLangStruct$lambda$5`(`this$0`: ActiveDialogue, var1: MoParams): Unit {
      `this$0`.incrementPage();
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `toMoLangStruct$lambda$6`(`this$0`: ActiveDialogue, args: MoParams): Unit {
      val var10001: MoValue = args.get(0);
      `this$0`.setPage(var10001);
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `toMoLangStruct$lambda$7`(`this$0`: ActiveDialogue, var1: MoParams): Unit {
      `this$0`.close();
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `toMoLangStruct$lambda$8`(`this$0`: ActiveDialogue, params: MoParams): Unit {
      val var10000: ActiveInput = `this$0`.activeInput;
      val var10001: java.lang.String = if (params.getParams().size() > 0) params.<MoValue>get(0).asString() else "";
      var10000.handle(var10001);
      return Unit.INSTANCE;
   }
}
