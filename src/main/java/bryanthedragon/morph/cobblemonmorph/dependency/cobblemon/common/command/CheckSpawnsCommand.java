package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.CobblemonWorldSpawnerManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.prospecting.SpawningProspector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.PlayerSpawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.SpawningArea
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.SpawnBucketArgumentType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import java.text.DecimalFormat
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

@SourceDebugExtension(["SMAP\nCheckSpawnsCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CheckSpawnsCommand.kt\ncom/cobblemon/mod/common/command/CheckSpawnsCommand\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,128:1\n1855#2,2:129\n1054#2:131\n1855#2,2:132\n*S KotlinDebug\n*F\n+ 1 CheckSpawnsCommand.kt\ncom/cobblemon/mod/common/command/CheckSpawnsCommand\n*L\n84#1:129,2\n95#1:131\n97#1:132,2\n*E\n"])
public object CheckSpawnsCommand {
   public const val PURPLE_THRESHOLD: Float = 0.01F
   public const val RED_THRESHOLD: Float = 0.1F
   public const val YELLOW_THRESHOLD: Float = 5.0F
   public final val df: DecimalFormat = new DecimalFormat("#.##")

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      val var10001: LiteralArgumentBuilder = Commands.m_82127_("checkspawn");
      dispatcher.register(
         (PermissionUtilsKt.permission$default(var10001 as ArgumentBuilder, CobblemonPermissions.INSTANCE.getCHECKSPAWNS(), false, 2, null) as LiteralArgumentBuilder)
            .then(
               (Commands.m_82129_("bucket", SpawnBucketArgumentType.Companion.spawnBucket()).requires(CheckSpawnsCommand::register$lambda$0) as RequiredArgumentBuilder)
                  .executes(CheckSpawnsCommand::register$lambda$1)
            ) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>, player: ServerPlayer): Int {
      if (!Cobblemon.INSTANCE.getConfig().getEnableSpawning()) {
         return 0;
      } else {
         val var10000: PlayerSpawner = CobblemonWorldSpawnerManager.INSTANCE.getSpawnersForPlayers().get(player.m_20148_());
         if (var10000 == null) {
            return 1;
         } else {
            val cause: SpawnCause = new SpawnCause(var10000, SpawnBucketArgumentType.Companion.getSpawnBucket(context, "bucket"), player as Entity);
            val var37: SpawningProspector = var10000.getProspector();
            val var10001: Spawner = var10000;
            val var10005: Level = player.m_9236_();
            val spawnProbabilities: java.util.Map = var10000.getSpawningSelector()
               .getProbabilities(
                  var10000,
                  var10000.getResolver()
                     .resolve(
                        var10000,
                        var10000.getContextCalculators(),
                        var37.prospect(
                           var10001,
                           new SpawningArea(
                              cause,
                              var10005 as ServerLevel,
                              Mth.m_14165_(player.m_20185_() - (double)((float)Cobblemon.INSTANCE.getConfig().getWorldSliceDiameter() / 2.0F)),
                              Mth.m_14165_(player.m_20186_() - (double)((float)Cobblemon.INSTANCE.getConfig().getWorldSliceHeight() / 2.0F)),
                              Mth.m_14165_(player.m_20189_() - (double)((float)Cobblemon.INSTANCE.getConfig().getWorldSliceDiameter() / 2.0F)),
                              Cobblemon.INSTANCE.getConfig().getWorldSliceDiameter(),
                              Cobblemon.INSTANCE.getConfig().getWorldSliceHeight(),
                              Cobblemon.INSTANCE.getConfig().getWorldSliceDiameter()
                           )
                        )
                     )
               );
            val spawnNames: java.util.Map = new LinkedHashMap();
            val namedProbabilities: java.util.Map = new LinkedHashMap();

            val sortedEntries: java.lang.Iterable;
            for (Object element$iv : sortedEntries) {
               val nextMessage: Entry = `$i$f$forEach` as Entry;
               val var18: java.lang.String = ((`$i$f$forEach` as Entry).getKey() as SpawnDetail).getName().getString();
               if (!spawnNames.containsKey(var18)) {
                  spawnNames.put(var18, (nextMessage.getKey() as SpawnDetail).getName());
               }

               val var38: Any = spawnNames.get(var18);
               val var39: java.lang.Float = namedProbabilities.get(var38 as MutableComponent) as java.lang.Float;
               namedProbabilities.put(var38 as MutableComponent, (var39 ?: 0.0F) + (nextMessage.getValue() as java.lang.Number).floatValue());
            }

            val var22: java.util.List = CollectionsKt.sortedWith(namedProbabilities.entrySet(), new CheckSpawnsCommand$execute$$inlined$sortedByDescending$1());
            val var24: java.util.List = new ArrayList();

            val var26: java.lang.Iterable;
            for (Object element$iv : var26) {
               val var35: MutableComponent = (var32 as Entry).getKey() as MutableComponent;
               val percentage: Float = ((var32 as Entry).getValue() as java.lang.Number).floatValue();
               var24.add(TextKt.plus(TextKt.plus(var35, ": "), INSTANCE.applyColour(TextKt.text("${df.format(percentage)}%"), percentage) as Component));
            }

            if (var24.isEmpty()) {
               val var40: MutableComponent = LocalizationUtilsKt.lang("command.checkspawns.nothing");
               player.m_213846_(TextKt.red(var40) as Component);
            } else {
               val var41: MutableComponent = LocalizationUtilsKt.lang("command.checkspawns.spawns");
               player.m_213846_(TextKt.underline(var41) as Component);
               val var27: MutableComponent = var24.get(0) as MutableComponent;

               for (MutableComponent nextMessage : messages.subList(1, messages.size())) {
                  TextKt.add(var27, TextKt.plus(TextKt.text(", "), var31 as Component) as Component);
               }

               player.m_213846_(var27 as Component);
            }

            return 1;
         }
      }
   }

   public fun applyColour(name: MutableComponent, percentage: Float): MutableComponent {
      return if (percentage < 0.01F)
         TextKt.lightPurple(name)
         else
         (if (percentage < 0.1F) TextKt.red(name) else (if (percentage < 5.0F) TextKt.yellow(name) else TextKt.green(name)));
   }

   @JvmStatic
   fun `register$lambda$0`(it: CommandSourceStack): Boolean {
      return it.m_230896_() != null;
   }

   @JvmStatic
   fun `register$lambda$1`(it: CommandContext): Int {
      val var10000: CheckSpawnsCommand = INSTANCE;
      val var10002: ServerPlayer = (it.getSource() as CommandSourceStack).m_81375_();
      return var10000.execute(it, var10002);
   }
}
