/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
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
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogues;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.DialogueArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011J'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001b\u0010\u000e\u001a\u00020\r2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00020\u000b\u00a2\u0006\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/command/OpenDialogueCommand;", "", "Lnet/minecraft/commands/CommandSourceStack;", "source", "Lnet/minecraft/resources/ResourceLocation;", "dialogueId", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "execute", "(Lnet/minecraft/commands/CommandSourceStack;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/server/level/ServerPlayer;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "<init>", "()V", "common"})
public final class OpenDialogueCommand {
    @NotNull
    public static final OpenDialogueCommand INSTANCE = new OpenDialogueCommand();

    private OpenDialogueCommand() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)"opendialogue");
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(\"opendialogue\")");
        LiteralCommandNode command = dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getOPEN_DIALOGUE(), false, 2, null)).then(Commands.m_82129_((String)"dialogue", (ArgumentType)DialogueArgumentType.Companion.dialogue()).then(Commands.m_82129_((String)"player", (ArgumentType)((ArgumentType)EntityArgument.m_91466_())).executes(OpenDialogueCommand::register$lambda$0))));
        Intrinsics.checkNotNullExpressionValue((Object)command, (String)"command");
        dispatcher.register(CommandUtilsKt.alias(command, "opendialogue"));
    }

    private final int execute(CommandSourceStack source, ResourceLocation dialogueId, ServerPlayer player) {
        Dialogue dialogue2 = Dialogues.INSTANCE.getDialogues().get(dialogueId);
        if (dialogue2 == null) {
            OpenDialogueCommand $this$execute_u24lambda_u241 = this;
            boolean bl = false;
            source.m_243053_((Component)TextKt.text("Invalid dialogue ID: " + dialogueId));
            return 1;
        }
        Dialogue dialogue3 = dialogue2;
        try {
            PlayerExtensionsKt.openDialogue(player, dialogue3);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return 1;
    }

    private static final int register$lambda$0(CommandContext it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        ResourceLocation dialogueId = DialogueArgumentType.Companion.getDialogue(it, "dialogue");
        if (!Dialogues.INSTANCE.getDialogues().containsKey(dialogueId)) {
            ((CommandSourceStack)it.getSource()).m_243053_((Component)TextKt.text("Invalid dialogue: " + dialogueId));
            return 1;
        }
        ServerPlayer player = EntityArgument.m_91474_((CommandContext)it, (String)"player");
        Object object = it.getSource();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"it.source");
        CommandSourceStack commandSourceStack = (CommandSourceStack)object;
        Intrinsics.checkNotNullExpressionValue((Object)player, (String)"player");
        return INSTANCE.execute(commandSourceStack, dialogueId, player);
    }
}

