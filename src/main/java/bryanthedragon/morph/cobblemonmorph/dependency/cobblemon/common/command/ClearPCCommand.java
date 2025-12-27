package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nClearPCCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClearPCCommand.kt\ncom/cobblemon/mod/common/command/ClearPCCommand\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,41:1\n1855#2,2:42\n*S KotlinDebug\n*F\n+ 1 ClearPCCommand.kt\ncom/cobblemon/mod/common/command/ClearPCCommand\n*L\n37#1:42,2\n*E\n"])
public object ClearPCCommand {
   private const val NAME: String = "clearpc"
   private const val PLAYER: String = "player"

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10000: LiteralArgumentBuilder = Commands.m_82127_("clearpc");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10000 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getCLEAR_PC(), false, 2, null) as LiteralArgumentBuilder)
            .then(Commands.m_82129_("player", EntityArgument.m_91470_() as ArgumentType).executes(this::execute)) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>): Int {
      val target: ServerPlayer = EntityArgument.m_91474_(context, "player");
      val pc: PCStore = PlayerExtensionsKt.pc(target);

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         pc.remove(`element$iv` as Pokemon);
      }

      (context.getSource() as CommandSourceStack).m_288197_(ClearPCCommand::execute$lambda$0, true);
      return 1;
   }

   @JvmStatic
   fun `execute$lambda$0`(`$target`: ServerPlayer): Component {
      val var1: Array<Any> = new Object[1];
      val var10003: Component = `$target`.m_5446_();
      var1[0] = var10003;
      return LocalizationUtilsKt.commandLang("clearpc.cleared", var1) as Component;
   }
}
