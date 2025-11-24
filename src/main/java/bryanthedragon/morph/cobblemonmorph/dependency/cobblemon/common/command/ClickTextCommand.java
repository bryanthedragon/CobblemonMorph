/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u001b\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/command/ClickTextCommand;", "", "Lcom/mojang/brigadier/CommandDispatcher;", "Lnet/minecraft/commands/CommandSourceStack;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "<init>", "()V", "common"})
public final class ClickTextCommand {
    @NotNull
    public static final ClickTextCommand INSTANCE = new ClickTextCommand();

    private ClickTextCommand() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"cobblemonclicktext").requires(ClickTextCommand::register$lambda$0)).then(RequiredArgumentBuilder.argument((String)"callback", (ArgumentType)((ArgumentType)StringArgumentType.greedyString())).executes(ClickTextCommand::register$lambda$1)));
    }

    private static final boolean register$lambda$0(CommandSourceStack src) {
        return src.m_81373_() instanceof ServerPlayer;
    }

    private static final int register$lambda$1(CommandContext ctx) {
        block0: {
            Entity entity2 = ((CommandSourceStack)ctx.getSource()).m_81373_();
            Intrinsics.checkNotNull((Object)entity2, (String)"null cannot be cast to non-null type net.minecraft.server.network.ServerPlayerEntity");
            ServerPlayer player = (ServerPlayer)entity2;
            Function1<ServerPlayer, Unit> function1 = TextKt.getTextClickHandlers().get(UUID.fromString((String)ctx.getArgument("callback", String.class)));
            if (function1 == null) break block0;
            function1.invoke((Object)player);
        }
        return 1;
    }
}

