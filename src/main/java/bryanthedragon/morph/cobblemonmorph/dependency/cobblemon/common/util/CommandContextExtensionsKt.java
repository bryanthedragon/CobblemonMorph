/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.context.CommandContext
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import com.mojang.brigadier.context.CommandContext;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a)\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004*\b\u0012\u0004\u0012\u00020\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2={"Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "", "argumentName", "Lnet/minecraft/server/level/ServerPlayer;", "kotlin.jvm.PlatformType", "player", "(Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;)Lnet/minecraft/server/level/ServerPlayer;", "common"})
public final class CommandContextExtensionsKt {
    public static final ServerPlayer player(@NotNull CommandContext<CommandSourceStack> $this$player, @NotNull String argumentName) {
        Intrinsics.checkNotNullParameter($this$player, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)argumentName, (String)"argumentName");
        return EntityArgument.m_91474_($this$player, (String)argumentName);
    }

    public static /* synthetic */ ServerPlayer player$default(CommandContext commandContext, String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = "player";
        }
        return CommandContextExtensionsKt.player((CommandContext<CommandSourceStack>)commandContext, string);
    }
}

