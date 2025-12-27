package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.FloatArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.world.entity.EntityDimensions

@SourceDebugExtension(["SMAP\nChangeScaleAndSize.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChangeScaleAndSize.kt\ncom/cobblemon/mod/common/command/ChangeScaleAndSize\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,52:1\n1#2:53\n*E\n"])
public object ChangeScaleAndSize {
   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10000: LiteralArgumentBuilder = Commands.m_82127_("changescaleandsize");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10000 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getCHANGE_SCALE_AND_SIZE(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               (Commands.m_82129_("pokemon", PokemonArgumentType.Companion.pokemon())
                     .then(
                        Commands.m_82129_("scale", FloatArgumentType.floatArg() as ArgumentType)
                           .then(
                              Commands.m_82129_("width", FloatArgumentType.floatArg() as ArgumentType)
                                 .then(Commands.m_82129_("height", FloatArgumentType.floatArg() as ArgumentType).executes(this::execute))
                           )
                     ) as RequiredArgumentBuilder)
                  .executes(this::execute)
            ) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>): Int {
      val pkm: Species = PokemonArgumentType.Companion.getPokemon(context, "pokemon");
      val scale: Float = FloatArgumentType.getFloat(context, "scale");
      val width: Float = FloatArgumentType.getFloat(context, "width");
      val height: Float = FloatArgumentType.getFloat(context, "height");
      pkm.setBaseScale(scale);
      pkm.setHitbox(new EntityDimensions(width, height, false));
      pkm.getForms().clear();
      val var10000: java.util.List = pkm.getForms();
      val var6: FormData = new FormData(
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         -1,
         3,
         null
      );
      var6.initialize(pkm);
      var10000.add(var6);
      return 1;
   }
}
