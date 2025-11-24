/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.CommandBuildContext
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.commands.arguments.item.ItemArgument
 *  net.minecraft.commands.arguments.item.ItemInput
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PartySlotArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u001d\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J#\u0010\r\u001a\u00020\f2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\r\u0010\u000eR\u0014\u0010\u0010\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0011R\u0014\u0010\u0013\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0011R\u0014\u0010\u0014\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0011\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/command/HeldItemCommand;", "", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "ctx", "", "execute", "(Lcom/mojang/brigadier/context/CommandContext;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "Lnet/minecraft/commands/CommandBuildContext;", "commandRegistryAccess", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;Lnet/minecraft/commands/CommandBuildContext;)V", "", "ITEM", "Ljava/lang/String;", "NAME", "SLOT", "TARGET", "<init>", "()V", "common"})
public final class HeldItemCommand {
    @NotNull
    public static final HeldItemCommand INSTANCE = new HeldItemCommand();
    @NotNull
    private static final String NAME = "held_item";
    @NotNull
    private static final String TARGET = "target";
    @NotNull
    private static final String SLOT = "slot";
    @NotNull
    private static final String ITEM = "item";

    private HeldItemCommand() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher, @NotNull CommandBuildContext commandRegistryAccess) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        Intrinsics.checkNotNullParameter((Object)commandRegistryAccess, (String)"commandRegistryAccess");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)NAME);
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(NAME)");
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getHELD_ITEM(), false, 2, null)).then(Commands.m_82129_((String)TARGET, (ArgumentType)((ArgumentType)EntityArgument.m_91466_())).then(Commands.m_82129_((String)SLOT, (ArgumentType)PartySlotArgumentType.Companion.partySlot()).then(Commands.m_82129_((String)ITEM, (ArgumentType)((ArgumentType)ItemArgument.m_235279_((CommandBuildContext)commandRegistryAccess))).executes(this::execute)))));
    }

    private final int execute(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = EntityArgument.m_91474_(ctx, (String)TARGET);
        Intrinsics.checkNotNullExpressionValue((Object)player, (String)"player");
        Pokemon pokemon = PartySlotArgumentType.Companion.getPokemonOf(ctx, SLOT, player);
        ItemInput stackArgument = ItemArgument.m_120963_(ctx, (String)ITEM);
        ItemStack stack = stackArgument.m_120980_(1, false);
        Intrinsics.checkNotNullExpressionValue((Object)stack, (String)"stack");
        Pokemon.swapHeldItem$default(pokemon, stack, false, 2, null);
        ((CommandSourceStack)ctx.getSource()).m_288197_(() -> HeldItemCommand.execute$lambda$0(player, pokemon, stack), true);
        return 1;
    }

    private static final Component execute$lambda$0(ServerPlayer $player, Pokemon $pokemon, ItemStack $stack) {
        Intrinsics.checkNotNullParameter((Object)$pokemon, (String)"$pokemon");
        Object[] objectArray = new Object[3];
        Intrinsics.checkNotNullExpressionValue((Object)$player.m_7755_(), (String)"player.name");
        objectArray[1] = $pokemon.getSpecies().getTranslatedName();
        Intrinsics.checkNotNullExpressionValue((Object)$stack.m_41786_(), (String)"stack.name");
        return (Component)LocalizationUtilsKt.commandLang(NAME, objectArray);
    }
}

