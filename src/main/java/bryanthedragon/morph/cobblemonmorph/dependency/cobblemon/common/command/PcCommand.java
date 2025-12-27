package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PermissiblePcLink
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.OpenPCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.Message
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

public object PcCommand {
   private final val IN_BATTLE_EXCEPTION: SimpleCommandExceptionType
   private const val NAME: String = "pc"

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("pc");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getPC(), false, 2, null) as LiteralArgumentBuilder)
            .executes(this::execute) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>): Int {
      val player: ServerPlayer = (context.getSource() as CommandSourceStack).m_81375_();
      val pc: PCStore = PlayerExtensionsKt.pc(player);
      if (PlayerExtensionsKt.isInBattle(player)) {
         val var5: CommandSyntaxException = IN_BATTLE_EXCEPTION.create();
         throw var5 as java.lang.Throwable;
      } else {
         PCLinkManager.INSTANCE.addLink(new PermissiblePcLink(pc, player, CobblemonPermissions.INSTANCE.getPC()));
         new OpenPCPacket(pc.getUuid()).sendToPlayer(player);
         val var10000: ServerLevel = (context.getSource() as CommandSourceStack).m_81372_();
         val var4: Level = var10000 as Level;
         val var10001: ServerPlayer = (context.getSource() as CommandSourceStack).m_230896_();
         val var6: Vec3 = var10001.m_20182_();
         WorldExtensionsKt.playSoundServer$default(var4, var6, CobblemonSounds.PC_ON, null, 0.5F, 1.0F, 4, null);
         return 1;
      }
   }

   @JvmStatic
   fun {
      val var10002: MutableComponent = LocalizationUtilsKt.lang("pc.inbattle");
      IN_BATTLE_EXCEPTION = new SimpleCommandExceptionType(TextKt.red(var10002) as Message);
   }
}
