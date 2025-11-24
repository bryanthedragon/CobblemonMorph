/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.IntRange
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.SpawnAllPokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J%\u0010\b\u001a\u00020\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u001b\u0010\r\u001a\u00020\f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u00a2\u0006\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/command/SpawnAllPokemon;", "", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "Lkotlin/ranges/IntRange;", "range", "", "execute", "(Lcom/mojang/brigadier/context/CommandContext;Lkotlin/ranges/IntRange;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "<init>", "()V", "common"})
public final class SpawnAllPokemon {
    @NotNull
    public static final SpawnAllPokemon INSTANCE = new SpawnAllPokemon();

    private SpawnAllPokemon() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)"spawnallpokemon");
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(\"spawnallpokemon\")");
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.requiresWithPermission((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getSPAWN_ALL_POKEMON(), (Function1<? super CommandSourceStack, Boolean>)((Function1)register.1.INSTANCE))).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"min", (ArgumentType)((ArgumentType)IntegerArgumentType.integer((int)1))).then(Commands.m_82129_((String)"max", (ArgumentType)((ArgumentType)IntegerArgumentType.integer((int)1))).executes(SpawnAllPokemon::register$lambda$0))).executes(SpawnAllPokemon::register$lambda$1))).executes(SpawnAllPokemon::register$lambda$2));
    }

    private final int execute(CommandContext<CommandSourceStack> context, IntRange range) {
        ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
        for (Species species : PokemonSpecies.INSTANCE.getImplemented()) {
            int n = range.getFirst();
            int n2 = range.getLast();
            int n3 = species.getNationalPokedexNumber();
            boolean bl = n <= n3 ? n3 <= n2 : false;
            if (!bl) continue;
            Cobblemon.INSTANCE.getLOGGER().debug(species.getName());
            Pokemon pokemon = Species.create$default(species, 0, 1, null);
            Level level = player.m_9236_();
            Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
            ServerLevel serverLevel = (ServerLevel)level;
            Vec3 vec3 = player.m_20182_();
            Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"player.pos");
            Pokemon.sendOut$default(pokemon, serverLevel, vec3, null, null, 8, null);
        }
        return 1;
    }

    private static final int register$lambda$0(CommandContext it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)it, new IntRange(IntegerArgumentType.getInteger((CommandContext)it, (String)"min"), IntegerArgumentType.getInteger((CommandContext)it, (String)"max")));
    }

    private static final int register$lambda$1(CommandContext it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)it, new IntRange(IntegerArgumentType.getInteger((CommandContext)it, (String)"min"), Integer.MAX_VALUE));
    }

    private static final int register$lambda$2(CommandContext it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)it, new IntRange(1, Integer.MAX_VALUE));
    }
}

