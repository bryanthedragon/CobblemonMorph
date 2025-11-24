/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.tree.LiteralCommandNode
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001b\u0010\f\u001a\u00020\u000b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\t\u00a2\u0006\u0004\b\f\u0010\rR\u0014\u0010\u000f\u001a\u00020\u000e8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/command/HealPokemonCommand;", "", "Lnet/minecraft/commands/CommandSourceStack;", "source", "Lnet/minecraft/server/level/ServerPlayer;", "target", "", "execute", "(Lnet/minecraft/commands/CommandSourceStack;Lnet/minecraft/server/level/ServerPlayer;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "Lcom/mojang/brigadier/exceptions/SimpleCommandExceptionType;", "IN_BATTLE_EXCEPTION", "Lcom/mojang/brigadier/exceptions/SimpleCommandExceptionType;", "<init>", "()V", "common"})
public final class HealPokemonCommand {
    @NotNull
    public static final HealPokemonCommand INSTANCE = new HealPokemonCommand();
    @NotNull
    private static final SimpleCommandExceptionType IN_BATTLE_EXCEPTION;

    private HealPokemonCommand() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)"healpokemon");
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(\"healpokemon\")");
        LiteralArgumentBuilder literalArgumentBuilder2 = (LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getHEAL_POKEMON_SELF(), false, 2, null)).executes(HealPokemonCommand::register$lambda$0);
        RequiredArgumentBuilder requiredArgumentBuilder = Commands.m_82129_((String)"player", (ArgumentType)((ArgumentType)EntityArgument.m_91466_()));
        Intrinsics.checkNotNullExpressionValue((Object)requiredArgumentBuilder, (String)"argument(\"player\", EntityArgumentType.player())");
        LiteralCommandNode command = dispatcher.register((LiteralArgumentBuilder)literalArgumentBuilder2.then(((RequiredArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)requiredArgumentBuilder, CobblemonPermissions.INSTANCE.getHEAL_POKEMON_OTHER(), false, 2, null)).executes(HealPokemonCommand::register$lambda$1)));
        Intrinsics.checkNotNullExpressionValue((Object)command, (String)"command");
        dispatcher.register(CommandUtilsKt.alias(command, "pokeheal"));
    }

    private final int execute(CommandSourceStack source, ServerPlayer target) {
        if (PlayerExtensionsKt.isInBattle(target)) {
            CommandSyntaxException commandSyntaxException = IN_BATTLE_EXCEPTION.create();
            Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"IN_BATTLE_EXCEPTION.create()");
            throw (Throwable)commandSyntaxException;
        }
        if (!target.m_9236_().f_46443_) {
            PlayerPartyStore party = PlayerExtensionsKt.party(target);
            party.heal();
            source.m_288197_(() -> HealPokemonCommand.execute$lambda$2(target), true);
        }
        return 1;
    }

    private static final int register$lambda$0(CommandContext it) {
        Object object = it.getSource();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"it.source");
        CommandSourceStack commandSourceStack = (CommandSourceStack)object;
        ServerPlayer serverPlayer = ((CommandSourceStack)it.getSource()).m_81375_();
        Intrinsics.checkNotNullExpressionValue((Object)serverPlayer, (String)"it.source.playerOrThrow");
        return INSTANCE.execute(commandSourceStack, serverPlayer);
    }

    private static final int register$lambda$1(CommandContext it) {
        Object object = it.getSource();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"it.source");
        CommandSourceStack commandSourceStack = (CommandSourceStack)object;
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        ServerPlayer serverPlayer = CommandContextExtensionsKt.player((CommandContext<CommandSourceStack>)it, "player");
        Intrinsics.checkNotNullExpressionValue((Object)serverPlayer, (String)"it.player(\"player\")");
        return INSTANCE.execute(commandSourceStack, serverPlayer);
    }

    private static final Component execute$lambda$2(ServerPlayer $target) {
        Intrinsics.checkNotNullParameter((Object)$target, (String)"$target");
        Object[] objectArray = new Object[1];
        Intrinsics.checkNotNullExpressionValue((Object)$target.m_7755_(), (String)"target.name");
        return (Component)LocalizationUtilsKt.commandLang("healpokemon.heal", objectArray);
    }

    static {
        MutableComponent mutableComponent = LocalizationUtilsKt.commandLang("pokeheal.in_battle", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"commandLang(\"pokeheal.in_battle\")");
        IN_BATTLE_EXCEPTION = new SimpleCommandExceptionType((Message)TextKt.red(mutableComponent));
    }
}

