/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.tree.LiteralCommandNode
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.coordinates.Vec3Argument
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonPropertiesArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3ExtensionsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ%\u0010\b\u001a\u00020\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u001b\u0010\r\u001a\u00020\f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u00a2\u0006\u0004\b\r\u0010\u000eR\u0014\u0010\u0010\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0011R\u0014\u0010\u0013\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0011R\u0014\u0010\u0015\u001a\u00020\u00148\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00148\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0016R\u0014\u0010\u0018\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0011R\u0014\u0010\u0019\u001a\u00020\u00148\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u0016R\u0014\u0010\u001a\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u0011R\u0014\u0010\u001b\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\u0011\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/command/SpawnPokemon;", "", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "Lnet/minecraft/world/phys/Vec3;", "pos", "", "execute", "(Lcom/mojang/brigadier/context/CommandContext;Lnet/minecraft/world/phys/Vec3;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "", "ALIAS", "Ljava/lang/String;", "AT_ALIAS", "AT_NAME", "Lcom/mojang/brigadier/exceptions/SimpleCommandExceptionType;", "FAILED_SPAWN_EXCEPTION", "Lcom/mojang/brigadier/exceptions/SimpleCommandExceptionType;", "INVALID_POS_EXCEPTION", "NAME", "NO_SPECIES_EXCEPTION", "POSITION", "PROPERTIES", "<init>", "()V", "common"})
public final class SpawnPokemon {
    @NotNull
    public static final SpawnPokemon INSTANCE = new SpawnPokemon();
    @NotNull
    private static final String NAME = "spawnpokemon";
    @NotNull
    private static final String PROPERTIES = "properties";
    @NotNull
    private static final String POSITION = "pos";
    @NotNull
    private static final String ALIAS = "pokespawn";
    @NotNull
    private static final String AT_NAME = "spawnpokemonat";
    @NotNull
    private static final String AT_ALIAS = "pokespawnat";
    @NotNull
    private static final SimpleCommandExceptionType NO_SPECIES_EXCEPTION;
    @NotNull
    private static final SimpleCommandExceptionType INVALID_POS_EXCEPTION;
    @NotNull
    private static final SimpleCommandExceptionType FAILED_SPAWN_EXCEPTION;

    private SpawnPokemon() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)NAME);
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(NAME)");
        LiteralCommandNode contextPositionCommand = dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getSPAWN_POKEMON(), false, 2, null)).then(Commands.m_82129_((String)PROPERTIES, (ArgumentType)PokemonPropertiesArgumentType.Companion.properties()).executes(arg_0 -> SpawnPokemon.register$lambda$0(this, arg_0))));
        Intrinsics.checkNotNullExpressionValue((Object)contextPositionCommand, (String)"contextPositionCommand");
        dispatcher.register(CommandUtilsKt.alias(contextPositionCommand, ALIAS));
        LiteralArgumentBuilder literalArgumentBuilder2 = Commands.m_82127_((String)AT_NAME);
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder2, (String)"literal(AT_NAME)");
        LiteralCommandNode argumentPositionCommand = dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder2, CobblemonPermissions.INSTANCE.getSPAWN_POKEMON(), false, 2, null)).then(Commands.m_82129_((String)POSITION, (ArgumentType)((ArgumentType)Vec3Argument.m_120841_())).then(Commands.m_82129_((String)PROPERTIES, (ArgumentType)PokemonPropertiesArgumentType.Companion.properties()).executes(SpawnPokemon::register$lambda$1))));
        Intrinsics.checkNotNullExpressionValue((Object)argumentPositionCommand, (String)"argumentPositionCommand");
        dispatcher.register(CommandUtilsKt.alias(argumentPositionCommand, AT_ALIAS));
    }

    private final int execute(CommandContext<CommandSourceStack> context, Vec3 pos) {
        ServerLevel world = ((CommandSourceStack)context.getSource()).m_81372_();
        BlockPos blockPos2 = Vec3ExtensionsKt.toBlockPos(pos);
        if (!Level.m_46741_((BlockPos)blockPos2)) {
            CommandSyntaxException commandSyntaxException = INVALID_POS_EXCEPTION.create();
            Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"INVALID_POS_EXCEPTION.create()");
            throw (Throwable)commandSyntaxException;
        }
        PokemonProperties properties2 = PokemonPropertiesArgumentType.Companion.getPokemonProperties(context, PROPERTIES);
        if (properties2.getSpecies() == null) {
            CommandSyntaxException commandSyntaxException = NO_SPECIES_EXCEPTION.create();
            Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"NO_SPECIES_EXCEPTION.create()");
            throw (Throwable)commandSyntaxException;
        }
        Intrinsics.checkNotNullExpressionValue((Object)world, (String)"world");
        PokemonEntity pokemonEntity = properties2.createEntity((Level)world);
        pokemonEntity.m_7678_(pos.f_82479_, pos.f_82480_, pos.f_82481_, pokemonEntity.m_146908_(), pokemonEntity.m_146909_());
        pokemonEntity.m_20088_().m_135381_(PokemonEntity.Companion.getSPAWN_DIRECTION(), (Object)Float.valueOf(pokemonEntity.m_217043_().m_188501_() * 360.0f));
        if (world.m_7967_((Entity)pokemonEntity)) {
            return 1;
        }
        CommandSyntaxException commandSyntaxException = FAILED_SPAWN_EXCEPTION.create();
        Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"FAILED_SPAWN_EXCEPTION.create()");
        throw (Throwable)commandSyntaxException;
    }

    private static final int register$lambda$0(SpawnPokemon this$0, CommandContext context) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullExpressionValue((Object)context, (String)"context");
        Vec3 vec3 = ((CommandSourceStack)context.getSource()).m_81371_();
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"context.source.position");
        return this$0.execute((CommandContext<CommandSourceStack>)context, vec3);
    }

    private static final int register$lambda$1(CommandContext context) {
        Intrinsics.checkNotNullExpressionValue((Object)context, (String)"context");
        Vec3 vec3 = Vec3Argument.m_120844_((CommandContext)context, (String)POSITION);
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"getVec3(context, POSITION)");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)context, vec3);
    }

    static {
        MutableComponent mutableComponent = LocalizationUtilsKt.commandLang("spawnpokemon.nospecies", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"commandLang(\"${NAME}.nospecies\")");
        NO_SPECIES_EXCEPTION = new SimpleCommandExceptionType((Message)TextKt.red(mutableComponent));
        MutableComponent mutableComponent2 = Component.m_237113_((String)"Invalid position");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"literal(\"Invalid position\")");
        INVALID_POS_EXCEPTION = new SimpleCommandExceptionType((Message)TextKt.red(mutableComponent2));
        MutableComponent mutableComponent3 = Component.m_237113_((String)"Unable to spawn at the given position");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"literal(\"Unable to spawn at the given position\")");
        FAILED_SPAWN_EXCEPTION = new SimpleCommandExceptionType((Message)TextKt.red(mutableComponent3));
    }
}

