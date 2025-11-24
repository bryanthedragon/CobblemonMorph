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
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.CommandExperienceSource;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J%\u0010\b\u001a\u00020\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u001b\u0010\r\u001a\u00020\f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u00a2\u0006\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/command/LevelUp;", "", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "execute", "(Lcom/mojang/brigadier/context/CommandContext;Lnet/minecraft/server/level/ServerPlayer;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "<init>", "()V", "common"})
public final class LevelUp {
    @NotNull
    public static final LevelUp INSTANCE = new LevelUp();

    private LevelUp() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)"levelup");
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(\"levelup\")");
        LiteralArgumentBuilder literalArgumentBuilder2 = (LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getLEVEL_UP_SELF(), false, 2, null);
        RequiredArgumentBuilder requiredArgumentBuilder = Commands.m_82129_((String)"player", (ArgumentType)((ArgumentType)EntityArgument.m_91466_()));
        Intrinsics.checkNotNullExpressionValue((Object)requiredArgumentBuilder, (String)"argument(\"player\", EntityArgumentType.player())");
        LiteralArgumentBuilder command = (LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder2.then(((RequiredArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)requiredArgumentBuilder, CobblemonPermissions.INSTANCE.getLEVEL_UP_OTHER(), false, 2, null)).then(Commands.m_82129_((String)"slot", (ArgumentType)((ArgumentType)IntegerArgumentType.integer((int)1, (int)99))).executes(LevelUp::register$lambda$0)))).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"slot", (ArgumentType)((ArgumentType)IntegerArgumentType.integer((int)1, (int)99))).requires(LevelUp::register$lambda$1)).executes(LevelUp::register$lambda$2));
        dispatcher.register(command);
    }

    private final int execute(CommandContext<CommandSourceStack> context, ServerPlayer player) {
        PlayerPartyStore party;
        int slot = IntegerArgumentType.getInteger(context, (String)"slot");
        if (slot > (party = PlayerExtensionsKt.party(player)).size()) {
            ((CommandSourceStack)context.getSource()).m_81352_((Component)TextKt.text("Your party only has " + party.size() + " slots."));
            return 0;
        }
        Pokemon pokemon = party.get(slot - 1);
        if (pokemon == null) {
            ((CommandSourceStack)context.getSource()).m_81352_((Component)TextKt.text("There is no Pok\u00e9mon in slot " + slot));
            return 0;
        }
        Object object = context.getSource();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.source");
        CommandExperienceSource source = new CommandExperienceSource((SharedSuggestionProvider)object);
        pokemon.addExperienceWithPlayer(player, source, pokemon.getExperienceToNextLevel());
        return 1;
    }

    private static final int register$lambda$0(CommandContext it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        ServerPlayer serverPlayer = CommandContextExtensionsKt.player$default(it, null, 1, null);
        Intrinsics.checkNotNullExpressionValue((Object)serverPlayer, (String)"it.player()");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)it, serverPlayer);
    }

    private static final boolean register$lambda$1(CommandSourceStack it) {
        return it.m_81373_() instanceof ServerPlayer && it.m_230896_() != null;
    }

    private static final int register$lambda$2(CommandContext it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        ServerPlayer serverPlayer = ((CommandSourceStack)it.getSource()).m_81375_();
        Intrinsics.checkNotNullExpressionValue((Object)serverPlayer, (String)"it.source.playerOrThrow");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)it, serverPlayer);
    }
}

