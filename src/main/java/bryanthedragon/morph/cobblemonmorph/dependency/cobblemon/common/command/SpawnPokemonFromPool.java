/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.tree.LiteralCommandNode
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.CobblemonWorldSpawnerManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.EntitySpawnResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.PlayerSpawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.SpawningArea;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J%\u0010\u0007\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001b\u0010\f\u001a\u00020\u000b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\t\u00a2\u0006\u0004\b\f\u0010\rR\u0014\u0010\u000f\u001a\u00020\u000e8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u000e8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0010R\u001c\u0010\u0014\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/command/SpawnPokemonFromPool;", "", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "", "amount", "execute", "(Lcom/mojang/brigadier/context/CommandContext;I)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "", "ALIAS", "Ljava/lang/String;", "NAME", "Lnet/minecraft/network/chat/MutableComponent;", "kotlin.jvm.PlatformType", "UNABLE_TO_SPAWN", "Lnet/minecraft/network/chat/MutableComponent;", "<init>", "()V", "common"})
public final class SpawnPokemonFromPool {
    @NotNull
    public static final SpawnPokemonFromPool INSTANCE = new SpawnPokemonFromPool();
    @NotNull
    public static final String NAME = "spawnpokemonfrompool";
    @NotNull
    public static final String ALIAS = "forcespawn";
    private static final MutableComponent UNABLE_TO_SPAWN = LocalizationUtilsKt.commandLang("spawnpokemonfrompool.unable_to_spawn", new Object[0]);

    private SpawnPokemonFromPool() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)NAME);
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(NAME)");
        LiteralCommandNode spawnPokemonFromPoolCommand = dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getSPAWN_POKEMON(), false, 2, null)).then(Commands.m_82129_((String)"amount", (ArgumentType)((ArgumentType)IntegerArgumentType.integer((int)1))).executes(SpawnPokemonFromPool::register$lambda$0))).executes(SpawnPokemonFromPool::register$lambda$1));
        Intrinsics.checkNotNullExpressionValue((Object)spawnPokemonFromPoolCommand, (String)"spawnPokemonFromPoolCommand");
        dispatcher.register(CommandUtilsKt.alias(spawnPokemonFromPoolCommand, ALIAS));
    }

    private final int execute(CommandContext<CommandSourceStack> context, int amount) {
        ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
        Map<UUID, PlayerSpawner> map = CobblemonWorldSpawnerManager.INSTANCE.getSpawnersForPlayers();
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        PlayerSpawner spawner = (PlayerSpawner)MapsKt.getValue(map, (Object)uUID);
        int spawnsTriggered = 0;
        int i = 1;
        if (i <= amount) {
            while (true) {
                SpawnCause spawnCause;
                if (spawner.getArea(spawnCause = new SpawnCause(spawner, spawner.chooseBucket(), (Entity)spawner.getCauseEntity())) != null) {
                    SpawningArea area;
                    WorldSlice slice = spawner.getProspector().prospect(spawner, area);
                    List<AreaSpawningContext> contexts = spawner.getResolver().resolve(spawner, spawner.getContextCalculators(), slice);
                    if (contexts.isEmpty()) {
                        MutableComponent mutableComponent = UNABLE_TO_SPAWN;
                        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"UNABLE_TO_SPAWN");
                        player.m_213846_((Component)TextKt.red(mutableComponent));
                    } else {
                        Pair<SpawningContext, SpawnDetail> result = spawner.getSpawningSelector().select(spawner, contexts);
                        if (result == null) {
                            MutableComponent mutableComponent = UNABLE_TO_SPAWN;
                            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"UNABLE_TO_SPAWN");
                            player.m_213846_((Component)TextKt.red(mutableComponent));
                        } else {
                            SpawnAction<?> spawnAction = ((SpawnDetail)result.getSecond()).doSpawn((SpawningContext)result.getFirst());
                            spawnAction.getFuture().thenApply(arg_0 -> SpawnPokemonFromPool.execute$lambda$2(player, arg_0));
                            ++spawnsTriggered;
                        }
                    }
                }
                if (i == amount) break;
                ++i;
            }
        }
        return spawnsTriggered;
    }

    private static final int register$lambda$0(CommandContext context) {
        Intrinsics.checkNotNullExpressionValue((Object)context, (String)"context");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)context, IntegerArgumentType.getInteger((CommandContext)context, (String)"amount"));
    }

    private static final int register$lambda$1(CommandContext context) {
        Intrinsics.checkNotNullExpressionValue((Object)context, (String)"context");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)context, 1);
    }

    private static final Unit execute$lambda$2(ServerPlayer $player, Object it) {
        if (it instanceof EntitySpawnResult) {
            for (Entity entity2 : ((EntitySpawnResult)it).getEntities()) {
                Object[] objectArray = new Object[1];
                Intrinsics.checkNotNullExpressionValue((Object)entity2.m_5446_(), (String)"entity.displayName");
                MutableComponent mutableComponent = LocalizationUtilsKt.commandLang("spawnpokemonfrompool.success", objectArray);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"commandLang(\"spawnpokemo\u2026ess\", entity.displayName)");
                $player.m_213846_((Component)TextKt.green(mutableComponent));
            }
        }
        return Unit.INSTANCE;
    }
}

