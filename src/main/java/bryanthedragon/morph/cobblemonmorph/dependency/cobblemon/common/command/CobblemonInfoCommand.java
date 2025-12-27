package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBuildDetails
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.ChatFormatting
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextColor
import net.minecraft.network.chat.HoverEvent.Action

public object CobblemonInfoCommand {
   private final val GREEN: TextColor
   private final val INDENT: Component
   private final val NEW_LINE: Component
   private final val RED: TextColor
   private final val SPACE: Component
   private final val YELLOW: TextColor

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      dispatcher.register(
         Commands.m_82127_("cobblemon").then(LiteralArgumentBuilder.literal("info").executes(CobblemonInfoCommand::register$lambda$7)) as LiteralArgumentBuilder
      );
   }

   @JvmStatic
   fun `register$lambda$7$lambda$0`(it: Style): Style {
      return it.m_131148_(YELLOW);
   }

   @JvmStatic
   fun `register$lambda$7$lambda$1`(it: Style): Style {
      return it.m_131140_(ChatFormatting.GRAY);
   }

   @JvmStatic
   fun `register$lambda$7$lambda$2`(it: Style): Style {
      return it.m_131140_(ChatFormatting.GRAY);
   }

   @JvmStatic
   fun `register$lambda$7$lambda$3`(it: Style): Style {
      return it.m_131148_(RED);
   }

   @JvmStatic
   fun `register$lambda$7$lambda$4`(it: Style): Style {
      return it.m_131140_(ChatFormatting.GRAY);
   }

   @JvmStatic
   fun `register$lambda$7$lambda$5`(it: Style): Style {
      return it.m_131144_(
            new HoverEvent(Action.f_130831_, Component.m_237113_("https://gitlab.com/cable-mc/cobblemon/-/commit/df8f078d13702ab9a000438910b822ceffbb2248"))
         )
         .m_131142_(
            new ClickEvent(
               net.minecraft.network.chat.ClickEvent.Action.OPEN_URL, "https://gitlab.com/cable-mc/cobblemon/-/commit/df8f078d13702ab9a000438910b822ceffbb2248"
            )
         );
   }

   @JvmStatic
   fun `register$lambda$7$lambda$6`(it: Style): Style {
      return it.m_131140_(ChatFormatting.GRAY);
   }

   @JvmStatic
   fun `register$lambda$7`(ctx: CommandContext): Int {
      val message: MutableComponent = Component.m_237119_()
         .m_7220_(Component.m_237113_("Cobblemon Build Details").m_130938_(CobblemonInfoCommand::register$lambda$7$lambda$0) as Component);
      message.m_7220_(NEW_LINE)
         .m_7220_(INDENT)
         .m_7220_(Component.m_237113_("Version:").m_130938_(CobblemonInfoCommand::register$lambda$7$lambda$1) as Component)
         .m_7220_(SPACE)
         .m_130946_("1.5.2");
      message.m_7220_(NEW_LINE)
         .m_7220_(INDENT)
         .m_7220_(Component.m_237113_("Is Snapshot:").m_130938_(CobblemonInfoCommand::register$lambda$7$lambda$2) as Component)
         .m_7220_(SPACE)
         .m_7220_(Component.m_237113_("No").m_130938_(CobblemonInfoCommand::register$lambda$7$lambda$3) as Component);
      message.m_7220_(NEW_LINE)
         .m_7220_(INDENT)
         .m_7220_(Component.m_237113_("Git Commit:").m_130938_(CobblemonInfoCommand::register$lambda$7$lambda$4) as Component)
         .m_7220_(SPACE)
         .m_7220_(
            Component.m_237113_(CobblemonBuildDetails.INSTANCE.smallCommitHash()).m_130938_(CobblemonInfoCommand::register$lambda$7$lambda$5) as Component
         );
      message.m_7220_(NEW_LINE)
         .m_7220_(INDENT)
         .m_7220_(Component.m_237113_("Branch:").m_130938_(CobblemonInfoCommand::register$lambda$7$lambda$6) as Component)
         .m_7220_(SPACE)
         .m_130946_("HEAD");
      (ctx.getSource() as CommandSourceStack).m_243053_(message as Component);
      return 0;
   }

   @JvmStatic
   fun {
      var var10000: TextColor = TextColor.m_131266_(13058626);
      RED = var10000;
      var10000 = TextColor.m_131266_(14605824);
      YELLOW = var10000;
      var10000 = TextColor.m_131266_(4376386);
      GREEN = var10000;
      val var2: MutableComponent = Component.m_237113_("  ");
      INDENT = var2 as Component;
      val var3: MutableComponent = Component.m_237113_("\n");
      NEW_LINE = var3 as Component;
      val var4: MutableComponent = Component.m_237113_(" ");
      SPACE = var4 as Component;
   }
}
