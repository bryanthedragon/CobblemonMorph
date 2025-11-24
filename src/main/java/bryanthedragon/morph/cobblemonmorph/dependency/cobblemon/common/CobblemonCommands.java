/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.CommandBuildContext
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands$CommandSelection
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.BedrockParticleCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.ChangeScaleAndSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.ChangeWalkSpeed;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.CheckSpawnsCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.ClearPCCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.ClearPartyCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.ClickTextCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.CobblemonInfoCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.FriendshipCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.GetNBT;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.GiveAllPokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.GivePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.HealPokemonCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.HeldItemCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.LevelUp;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.OpenDialogueCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.OpenStarterScreenCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.PcCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.PokeboxCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.PokemonEditCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.PokemonRestartCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.QueryLearnsetCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.ReloadShowdownCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.SpawnAllPokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.SpawnPokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.SpawnPokemonFromPool;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.StopBattleCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.TakePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.TeachCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.TestCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.TestPartySlotCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.TestPcSlotCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.TestStoreCommand;
import com.mojang.brigadier.CommandDispatcher;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ+\u0010\n\u001a\u00020\t2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/CobblemonCommands;", "", "Lcom/mojang/brigadier/CommandDispatcher;", "Lnet/minecraft/commands/CommandSourceStack;", "dispatcher", "Lnet/minecraft/commands/CommandBuildContext;", "registry", "Lnet/minecraft/server/command/CommandManager$RegistrationEnvironment;", "selection", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;Lnet/minecraft/commands/CommandBuildContext;Lnet/minecraft/commands/Commands$CommandSelection;)V", "<init>", "()V", "common"})
public final class CobblemonCommands {
    @NotNull
    public static final CobblemonCommands INSTANCE = new CobblemonCommands();

    private CobblemonCommands() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher, @NotNull CommandBuildContext registry, @NotNull Commands.CommandSelection selection) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        Intrinsics.checkNotNullParameter((Object)registry, (String)"registry");
        Intrinsics.checkNotNullParameter((Object)selection, (String)"selection");
        SpawnPokemon.INSTANCE.register(dispatcher);
        GivePokemon.INSTANCE.register(dispatcher);
        TakePokemon.INSTANCE.register(dispatcher);
        ChangeScaleAndSize.INSTANCE.register(dispatcher);
        ChangeWalkSpeed.INSTANCE.register(dispatcher);
        TestCommand.INSTANCE.register(dispatcher);
        ReloadShowdownCommand.INSTANCE.register(dispatcher);
        ClickTextCommand.INSTANCE.register(dispatcher);
        PokemonEditCommand.INSTANCE.register(dispatcher);
        TeachCommand.INSTANCE.register(dispatcher);
        LevelUp.INSTANCE.register(dispatcher);
        HealPokemonCommand.INSTANCE.register(dispatcher);
        StopBattleCommand.INSTANCE.register(dispatcher);
        CheckSpawnsCommand.INSTANCE.register(dispatcher);
        GetNBT.INSTANCE.register(dispatcher);
        OpenStarterScreenCommand.INSTANCE.register(dispatcher);
        SpawnAllPokemon.INSTANCE.register(dispatcher);
        FriendshipCommand.INSTANCE.register(dispatcher);
        GiveAllPokemon.INSTANCE.register(dispatcher);
        HeldItemCommand.INSTANCE.register(dispatcher, registry);
        PcCommand.INSTANCE.register(dispatcher);
        SpawnPokemonFromPool.INSTANCE.register(dispatcher);
        PokeboxCommand.INSTANCE.register(dispatcher);
        TestStoreCommand.INSTANCE.register(dispatcher);
        QueryLearnsetCommand.INSTANCE.register(dispatcher);
        TestPcSlotCommand.INSTANCE.register(dispatcher);
        TestPartySlotCommand.INSTANCE.register(dispatcher);
        ClearPartyCommand.INSTANCE.register(dispatcher);
        ClearPCCommand.INSTANCE.register(dispatcher);
        PokemonRestartCommand.INSTANCE.register(dispatcher);
        BedrockParticleCommand.INSTANCE.register(dispatcher);
        OpenDialogueCommand.INSTANCE.register(dispatcher);
        CobblemonInfoCommand.INSTANCE.register(dispatcher);
    }
}

