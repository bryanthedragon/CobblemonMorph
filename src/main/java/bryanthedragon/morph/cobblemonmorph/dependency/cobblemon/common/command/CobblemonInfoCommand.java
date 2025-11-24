/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.ChatFormatting
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.network.chat.TextColor
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBuildDetails;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0014\u0010\t\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR\u0014\u0010\f\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\rR\u0014\u0010\u000f\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\nR\u0014\u0010\u0010\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\rR\u0014\u0010\u0011\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\n\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/command/CobblemonInfoCommand;", "", "Lcom/mojang/brigadier/CommandDispatcher;", "Lnet/minecraft/commands/CommandSourceStack;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "Lnet/minecraft/network/chat/TextColor;", "GREEN", "Lnet/minecraft/network/chat/TextColor;", "Lnet/minecraft/network/chat/Component;", "INDENT", "Lnet/minecraft/network/chat/Component;", "NEW_LINE", "RED", "SPACE", "YELLOW", "<init>", "()V", "common"})
public final class CobblemonInfoCommand {
    @NotNull
    public static final CobblemonInfoCommand INSTANCE = new CobblemonInfoCommand();
    @NotNull
    private static final TextColor RED;
    @NotNull
    private static final TextColor YELLOW;
    @NotNull
    private static final TextColor GREEN;
    @NotNull
    private static final Component INDENT;
    @NotNull
    private static final Component NEW_LINE;
    @NotNull
    private static final Component SPACE;

    private CobblemonInfoCommand() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_((String)"cobblemon").then(LiteralArgumentBuilder.literal((String)"info").executes(CobblemonInfoCommand::register$lambda$7)));
    }

    private static final Style register$lambda$7$lambda$0(Style it) {
        return it.m_131148_(YELLOW);
    }

    private static final Style register$lambda$7$lambda$1(Style it) {
        return it.m_131140_(ChatFormatting.GRAY);
    }

    private static final Style register$lambda$7$lambda$2(Style it) {
        return it.m_131140_(ChatFormatting.GRAY);
    }

    private static final Style register$lambda$7$lambda$3(Style it) {
        return it.m_131148_(RED);
    }

    private static final Style register$lambda$7$lambda$4(Style it) {
        return it.m_131140_(ChatFormatting.GRAY);
    }

    private static final Style register$lambda$7$lambda$5(Style it) {
        String link = "https://gitlab.com/cable-mc/cobblemon/-/commit/df8f078d13702ab9a000438910b822ceffbb2248";
        return it.m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, (Object)Component.m_237113_((String)link))).m_131142_(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
    }

    private static final Style register$lambda$7$lambda$6(Style it) {
        return it.m_131140_(ChatFormatting.GRAY);
    }

    private static final int register$lambda$7(CommandContext ctx) {
        MutableComponent message = Component.m_237119_().m_7220_((Component)Component.m_237113_((String)"Cobblemon Build Details").m_130938_(CobblemonInfoCommand::register$lambda$7$lambda$0));
        message.m_7220_(NEW_LINE).m_7220_(INDENT).m_7220_((Component)Component.m_237113_((String)"Version:").m_130938_(CobblemonInfoCommand::register$lambda$7$lambda$1)).m_7220_(SPACE).m_130946_("1.5.2");
        message.m_7220_(NEW_LINE).m_7220_(INDENT).m_7220_((Component)Component.m_237113_((String)"Is Snapshot:").m_130938_(CobblemonInfoCommand::register$lambda$7$lambda$2)).m_7220_(SPACE).m_7220_((Component)Component.m_237113_((String)"No").m_130938_(CobblemonInfoCommand::register$lambda$7$lambda$3));
        message.m_7220_(NEW_LINE).m_7220_(INDENT).m_7220_((Component)Component.m_237113_((String)"Git Commit:").m_130938_(CobblemonInfoCommand::register$lambda$7$lambda$4)).m_7220_(SPACE).m_7220_((Component)Component.m_237113_((String)CobblemonBuildDetails.INSTANCE.smallCommitHash()).m_130938_(CobblemonInfoCommand::register$lambda$7$lambda$5));
        message.m_7220_(NEW_LINE).m_7220_(INDENT).m_7220_((Component)Component.m_237113_((String)"Branch:").m_130938_(CobblemonInfoCommand::register$lambda$7$lambda$6)).m_7220_(SPACE).m_130946_("HEAD");
        ((CommandSourceStack)ctx.getSource()).m_243053_((Component)message);
        return 0;
    }

    static {
        TextColor textColor = TextColor.m_131266_((int)13058626);
        Intrinsics.checkNotNullExpressionValue((Object)textColor, (String)"fromRgb(0xC74242)");
        RED = textColor;
        TextColor textColor2 = TextColor.m_131266_((int)0xDEDE00);
        Intrinsics.checkNotNullExpressionValue((Object)textColor2, (String)"fromRgb(0xDEDE00)");
        YELLOW = textColor2;
        TextColor textColor3 = TextColor.m_131266_((int)4376386);
        Intrinsics.checkNotNullExpressionValue((Object)textColor3, (String)"fromRgb(0x42C742)");
        GREEN = textColor3;
        MutableComponent mutableComponent = Component.m_237113_((String)"  ");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"  \")");
        INDENT = (Component)mutableComponent;
        MutableComponent mutableComponent2 = Component.m_237113_((String)"\n");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"literal(\"\\n\")");
        NEW_LINE = (Component)mutableComponent2;
        MutableComponent mutableComponent3 = Component.m_237113_((String)" ");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"literal(\" \")");
        SPACE = (Component)mutableComponent3;
    }
}

