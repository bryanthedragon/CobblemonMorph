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
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001d\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001b\u0010\u000b\u001a\u00020\n2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\b\u00a2\u0006\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/command/TakePokemon;", "", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "", "execute", "(Lcom/mojang/brigadier/context/CommandContext;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "<init>", "()V", "common"})
public final class TakePokemon {
    @NotNull
    public static final TakePokemon INSTANCE = new TakePokemon();

    private TakePokemon() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)"takepokemon");
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(\"takepokemon\")");
        LiteralArgumentBuilder command = (LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getTAKE_POKEMON(), false, 2, null)).then(Commands.m_82129_((String)"player", (ArgumentType)((ArgumentType)EntityArgument.m_91466_())).then(Commands.m_82129_((String)"slot", (ArgumentType)((ArgumentType)IntegerArgumentType.integer((int)1, (int)99))).executes(this::execute)));
        dispatcher.register(command);
    }

    private final int execute(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer target = EntityArgument.m_91474_(context, (String)"player");
            int slot = IntegerArgumentType.getInteger(context, (String)"slot");
            Intrinsics.checkNotNullExpressionValue((Object)target, (String)"target");
            PlayerPartyStore party = PlayerExtensionsKt.party(target);
            if (slot > party.size()) {
                ((CommandSourceStack)context.getSource()).m_81352_((Component)TextKt.text("Your party only has " + party.size() + " slots."));
                return 0;
            }
            Pokemon pokemon = party.get(slot - 1);
            if (pokemon == null) {
                ((CommandSourceStack)context.getSource()).m_81352_((Component)TextKt.text("There is no Pok\u00e9mon in slot " + slot));
                return 0;
            }
            party.remove(pokemon);
            if (!Intrinsics.areEqual((Object)((CommandSourceStack)context.getSource()).m_81373_(), (Object)target) && ((CommandSourceStack)context.getSource()).m_81373_() instanceof ServerPlayer) {
                ServerPlayer serverPlayer = ((CommandSourceStack)context.getSource()).m_230896_();
                if (serverPlayer == null) {
                    return 1;
                }
                ServerPlayer player = serverPlayer;
                PlayerPartyStore toParty = PlayerExtensionsKt.party(player);
                toParty.add(pokemon);
                ((CommandSourceStack)context.getSource()).m_288197_(() -> TakePokemon.execute$lambda$0(pokemon), true);
                return 1;
            }
            ((CommandSourceStack)context.getSource()).m_288197_(() -> TakePokemon.execute$lambda$1(pokemon), true);
            return 1;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    private static final Component execute$lambda$0(Pokemon $pokemon) {
        return (Component)TextKt.text("You took " + $pokemon.getSpecies().getName());
    }

    private static final Component execute$lambda$1(Pokemon $pokemon) {
        return (Component)TextKt.text($pokemon.getSpecies().getName() + " was removed.");
    }
}

