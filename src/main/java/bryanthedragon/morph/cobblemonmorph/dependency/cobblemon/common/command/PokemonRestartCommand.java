/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.tree.LiteralCommandNode
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter.SetClientPlayerDataPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.InitializePartyPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.InitializePCPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001b\u0010\u001cJ-\u0010\n\u001a\u00020\t2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\u000f\u001a\u00020\u000e2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001f\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\u00138\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00138\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00138\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\u00138\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0015R\u0014\u0010\u0019\u001a\u00020\u00138\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u0015R\u0014\u0010\u001a\u001a\u00020\u00138\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u0015\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/command/PokemonRestartCommand;", "", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "resetStarters", "", "execute", "(Lcom/mojang/brigadier/context/CommandContext;Lnet/minecraft/server/level/ServerPlayer;Z)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "resetPlayerPokemonData", "(Lnet/minecraft/server/level/ServerPlayer;Z)V", "", "ALIAS", "Ljava/lang/String;", "ALIAS_OTHER", "NAME", "NAME_OTHER", "PLAYER", "STARTERS", "<init>", "()V", "common"})
public final class PokemonRestartCommand {
    @NotNull
    public static final PokemonRestartCommand INSTANCE = new PokemonRestartCommand();
    @NotNull
    private static final String NAME = "pokemonrestart";
    @NotNull
    private static final String NAME_OTHER = "pokemonrestartother";
    @NotNull
    private static final String PLAYER = "player";
    @NotNull
    private static final String STARTERS = "reset_starters";
    @NotNull
    private static final String ALIAS = "pokerestart";
    @NotNull
    private static final String ALIAS_OTHER = "pokerestartother";

    private PokemonRestartCommand() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)NAME);
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(NAME)");
        LiteralCommandNode selfCommand = dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getPOKEMON_EDIT_SELF(), false, 2, null)).then(Commands.m_82129_((String)STARTERS, (ArgumentType)((ArgumentType)BoolArgumentType.bool())).executes(PokemonRestartCommand::register$lambda$0)));
        Intrinsics.checkNotNullExpressionValue((Object)selfCommand, (String)"selfCommand");
        dispatcher.register(CommandUtilsKt.alias(selfCommand, ALIAS));
        LiteralArgumentBuilder literalArgumentBuilder2 = Commands.m_82127_((String)NAME_OTHER);
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder2, (String)"literal(NAME_OTHER)");
        LiteralCommandNode otherCommand = dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder2, CobblemonPermissions.INSTANCE.getPOKEMON_EDIT_OTHER(), false, 2, null)).then(Commands.m_82129_((String)PLAYER, (ArgumentType)((ArgumentType)EntityArgument.m_91466_())).then(Commands.m_82129_((String)STARTERS, (ArgumentType)((ArgumentType)BoolArgumentType.bool())).executes(PokemonRestartCommand::register$lambda$1))));
        Intrinsics.checkNotNullExpressionValue((Object)otherCommand, (String)"otherCommand");
        dispatcher.register(CommandUtilsKt.alias(otherCommand, ALIAS_OTHER));
        LiteralArgumentBuilder literalArgumentBuilder3 = Commands.m_82127_((String)NAME);
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder3, (String)"literal(NAME)");
        LiteralCommandNode selfCommandWithoutStarters = dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder3, CobblemonPermissions.INSTANCE.getPOKEMON_EDIT_SELF(), false, 2, null)).executes(PokemonRestartCommand::register$lambda$2));
        Intrinsics.checkNotNullExpressionValue((Object)selfCommandWithoutStarters, (String)"selfCommandWithoutStarters");
        dispatcher.register(CommandUtilsKt.alias(selfCommandWithoutStarters, ALIAS));
        LiteralArgumentBuilder literalArgumentBuilder4 = Commands.m_82127_((String)NAME_OTHER);
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder4, (String)"literal(NAME_OTHER)");
        LiteralCommandNode otherCommandWithoutStarters = dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder4, CobblemonPermissions.INSTANCE.getPOKEMON_EDIT_OTHER(), false, 2, null)).then(Commands.m_82129_((String)PLAYER, (ArgumentType)((ArgumentType)EntityArgument.m_91466_())).executes(PokemonRestartCommand::register$lambda$3)));
        Intrinsics.checkNotNullExpressionValue((Object)otherCommandWithoutStarters, (String)"otherCommandWithoutStarters");
        dispatcher.register(CommandUtilsKt.alias(otherCommandWithoutStarters, ALIAS_OTHER));
    }

    private final int execute(CommandContext<CommandSourceStack> context, ServerPlayer player, boolean resetStarters) {
        this.resetPlayerPokemonData(player, resetStarters);
        ((CommandSourceStack)context.getSource()).m_288197_(() -> PokemonRestartCommand.execute$lambda$4(player), true);
        return 1;
    }

    private final void resetPlayerPokemonData(ServerPlayer player, boolean resetStarters) {
        PlayerExtensionsKt.party(player).clearParty();
        PlayerExtensionsKt.pc(player).clearPC();
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        CobblemonNetwork.INSTANCE.sendPacket(player, new InitializePartyPacket(true, uUID, PlayerExtensionsKt.party(player).size()));
        UUID uUID2 = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"player.uuid");
        CobblemonNetwork.INSTANCE.sendPacket(player, new InitializePCPacket(uUID2, PlayerExtensionsKt.pc(player).getBoxes().size(), false));
        PlayerData playerData = Cobblemon.INSTANCE.getPlayerData().get((Player)player);
        playerData.setStarterPrompted(false);
        playerData.setStarterLocked(false);
        playerData.setStarterSelected(!resetStarters);
        CobblemonNetwork.INSTANCE.sendPacket(player, new SetClientPlayerDataPacket(playerData, resetStarters));
    }

    private static final int register$lambda$0(CommandContext it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        ServerPlayer serverPlayer = ((CommandSourceStack)it.getSource()).m_81375_();
        Intrinsics.checkNotNullExpressionValue((Object)serverPlayer, (String)"it.source.playerOrThrow");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)it, serverPlayer, BoolArgumentType.getBool((CommandContext)it, (String)STARTERS));
    }

    private static final int register$lambda$1(CommandContext it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        ServerPlayer serverPlayer = CommandContextExtensionsKt.player$default(it, null, 1, null);
        Intrinsics.checkNotNullExpressionValue((Object)serverPlayer, (String)"it.player()");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)it, serverPlayer, BoolArgumentType.getBool((CommandContext)it, (String)STARTERS));
    }

    private static final int register$lambda$2(CommandContext it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        ServerPlayer serverPlayer = ((CommandSourceStack)it.getSource()).m_81375_();
        Intrinsics.checkNotNullExpressionValue((Object)serverPlayer, (String)"it.source.playerOrThrow");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)it, serverPlayer, false);
    }

    private static final int register$lambda$3(CommandContext it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        ServerPlayer serverPlayer = CommandContextExtensionsKt.player$default(it, null, 1, null);
        Intrinsics.checkNotNullExpressionValue((Object)serverPlayer, (String)"it.player()");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)it, serverPlayer, false);
    }

    private static final Component execute$lambda$4(ServerPlayer $player) {
        Intrinsics.checkNotNullParameter((Object)$player, (String)"$player");
        Object[] objectArray = new Object[1];
        Intrinsics.checkNotNullExpressionValue((Object)$player.m_7755_(), (String)"player.name");
        return (Component)LocalizationUtilsKt.commandLang(NAME, objectArray);
    }
}

