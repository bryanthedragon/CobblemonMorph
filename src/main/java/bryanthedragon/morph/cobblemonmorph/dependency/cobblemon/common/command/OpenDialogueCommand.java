package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogues
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.DialogueArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

public object OpenDialogueCommand {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("opendialogue");
      val command: LiteralCommandNode = dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getOPEN_DIALOGUE(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               Commands.m_82129_("dialogue", DialogueArgumentType.Companion.dialogue())
                  .then(Commands.m_82129_("player", EntityArgument.m_91466_() as ArgumentType).executes(OpenDialogueCommand::register$lambda$0))
            ) as LiteralArgumentBuilder
      );
      dispatcher.register(CommandUtilsKt.alias(command, "opendialogue"));
   }

   private fun execute(source: CommandSourceStack, dialogueId: ResourceLocation, player: ServerPlayer): Int {
      val var10000: Dialogue = Dialogues.INSTANCE.getDialogues().get(dialogueId);
      if (var10000 == null) {
         val `$this$execute_u24lambda_u241`: OpenDialogueCommand = this;
         source.m_243053_(TextKt.text("Invalid dialogue ID: $dialogueId") as Component);
         return 1;
      } else {
         val dialogue: Dialogue = var10000;

         try {
            PlayerExtensionsKt.openDialogue(player, dialogue);
         } catch (var8: Exception) {
            var8.printStackTrace();
         }

         return 1;
      }
   }

   @JvmStatic
   fun `register$lambda$0`(it: CommandContext): Int {
      val var10000: DialogueArgumentType.Companion = DialogueArgumentType.Companion;
      val dialogueId: ResourceLocation = var10000.getDialogue(it, "dialogue");
      if (!Dialogues.INSTANCE.getDialogues().containsKey(dialogueId)) {
         (it.getSource() as CommandSourceStack).m_243053_(TextKt.text("Invalid dialogue: $dialogueId") as Component);
         return 1;
      } else {
         val player: ServerPlayer = EntityArgument.m_91474_(it, "player");
         val var3: OpenDialogueCommand = INSTANCE;
         var var10001: Any = it.getSource();
         var10001 = var10001 as CommandSourceStack;
         return var3.execute((CommandSourceStack)var10001, dialogueId, player);
      }
   }
}
