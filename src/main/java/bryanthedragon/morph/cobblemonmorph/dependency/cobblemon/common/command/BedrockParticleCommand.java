package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.SpawnSnowstormParticlePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3ExtensionsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.DimensionArgument
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.commands.arguments.ResourceLocationArgument
import net.minecraft.commands.arguments.coordinates.Vec3Argument
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nBedrockParticleCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockParticleCommand.kt\ncom/cobblemon/mod/common/command/BedrockParticleCommand\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,88:1\n1855#2,2:89\n1855#2,2:91\n1#3:93\n*S KotlinDebug\n*F\n+ 1 BedrockParticleCommand.kt\ncom/cobblemon/mod/common/command/BedrockParticleCommand\n*L\n77#1:89,2\n84#1:91,2\n*E\n"])
public object BedrockParticleCommand {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("bedrockparticle");
      val command: LiteralCommandNode = dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getBEDROCK_PARTICLE(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               (Commands.m_82129_("effect", ResourceLocationArgument.m_106984_() as ArgumentType)
                     .then(
                        (Commands.m_82129_("target", EntityArgument.m_91460_() as ArgumentType).executes(BedrockParticleCommand::register$lambda$1) as RequiredArgumentBuilder)
                           .then(Commands.m_82129_("locator", StringArgumentType.word() as ArgumentType).executes(BedrockParticleCommand::register$lambda$3))
                     ) as RequiredArgumentBuilder)
                  .then(
                     Commands.m_82129_("world", DimensionArgument.m_88805_() as ArgumentType)
                        .then(Commands.m_82129_("pos", Vec3Argument.m_120841_() as ArgumentType).executes(BedrockParticleCommand::register$lambda$4))
                  )
            ) as LiteralArgumentBuilder
      );
      dispatcher.register(CommandUtilsKt.alias(command, "bedrockparticle"));
   }

   private fun execute(source: CommandSourceStack, effectId: ResourceLocation, world: ServerLevel, target: Vec3): Int {
      val pos: BlockPos = Vec3ExtensionsKt.toBlockPos(target);

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val player: ServerPlayer = `element$iv` as ServerPlayer;
         val var10000: CobblemonNetwork = CobblemonNetwork.INSTANCE;
         var10000.sendPacket(player, new SpawnSnowstormParticlePacket(effectId, target));
      }

      return 1;
   }

   private fun execute(source: CommandSourceStack, effectId: ResourceLocation, world: ServerLevel, target: Entity, locator: String): Int {
      val pos: BlockPos = target.m_20183_();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val player: ServerPlayer = `element$iv` as ServerPlayer;
         val var10000: CobblemonNetwork = CobblemonNetwork.INSTANCE;
         var10000.sendPacket(player, new SpawnSnowstormEntityParticlePacket(effectId, target.m_19879_(), locator));
      }

      return 1;
   }

   @JvmStatic
   fun `register$lambda$1`(it: CommandContext): Int {
      val effectId: ResourceLocation = ResourceLocationArgument.m_107011_(it, "effect");
      val entities: java.util.Collection = EntityArgument.m_91461_(it, "target");
      val var3: java.lang.Iterable = entities;
      var var4: Int = 0;

      for (Object var6 : var3) {
         val entity: Entity = var6 as Entity;
         val var10000: BedrockParticleCommand = INSTANCE;
         var var10001: Any = it.getSource();
         var10001 = var10001 as CommandSourceStack;
         val var10003: Level = entity.m_9236_();
         val var12: ServerLevel = var10003 as ServerLevel;
         val var10004: Vec3 = entity.m_20182_();
         var4 += var10000.execute((CommandSourceStack)var10001, effectId, var12, var10004);
      }

      return var4;
   }

   @JvmStatic
   fun `register$lambda$3`(it: CommandContext): Int {
      val effectId: ResourceLocation = ResourceLocationArgument.m_107011_(it, "effect");
      val entities: java.util.Collection = EntityArgument.m_91461_(it, "target");
      val locator: java.lang.String = StringArgumentType.getString(it, "locator");
      val var4: java.lang.Iterable = entities;
      var var5: Int = 0;

      for (Object var7 : var4) {
         val entity: Entity = var7 as Entity;
         val var10000: BedrockParticleCommand = INSTANCE;
         var var10001: Any = it.getSource();
         var10001 = var10001 as CommandSourceStack;
         val var10003: Level = entity.m_9236_();
         val var13: ServerLevel = var10003 as ServerLevel;
         var5 += var10000.execute((CommandSourceStack)var10001, effectId, var13, entity, locator);
      }

      return var5;
   }

   @JvmStatic
   fun `register$lambda$4`(it: CommandContext): Int {
      val effectId: ResourceLocation = ResourceLocationArgument.m_107011_(it, "effect");
      val world: ServerLevel = DimensionArgument.m_88808_(it, "world");
      val pos: Vec3 = Vec3Argument.m_120844_(it, "pos");
      val var10000: BedrockParticleCommand = INSTANCE;
      var var10001: Any = it.getSource();
      var10001 = var10001 as CommandSourceStack;
      return var10000.execute((CommandSourceStack)var10001, effectId, world, pos);
   }

   @JvmStatic
   fun `execute$lambda$5`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }

   @JvmStatic
   fun `execute$lambda$7`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }
}
