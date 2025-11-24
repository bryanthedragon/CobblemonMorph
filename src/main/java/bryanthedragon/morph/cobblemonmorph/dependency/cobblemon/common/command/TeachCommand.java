/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.BenchedMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.PermissionValidator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.LearnsetQuery;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.MoveArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PartySlotArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0019\u0010\u001aJ%\u0010\b\u001a\u00020\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u001b\u0010\r\u001a\u00020\f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u00a2\u0006\u0004\b\r\u0010\u000eR\u0014\u0010\u0010\u001a\u00020\u000f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u000f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0011R\u0014\u0010\u0014\u001a\u00020\u00138\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00138\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00138\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\u00138\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0015\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/command/TeachCommand;", "", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "execute", "(Lcom/mojang/brigadier/context/CommandContext;Lnet/minecraft/server/level/ServerPlayer;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "Lcom/mojang/brigadier/exceptions/Dynamic2CommandExceptionType;", "ALREADY_KNOWS_EXCEPTION", "Lcom/mojang/brigadier/exceptions/Dynamic2CommandExceptionType;", "CANT_LEARN_EXCEPTION", "", "MOVE", "Ljava/lang/String;", "NAME", "PLAYER", "SLOT", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nTeachCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TeachCommand.kt\ncom/cobblemon/mod/common/command/TeachCommand\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,80:1\n1747#2,3:81\n*S KotlinDebug\n*F\n+ 1 TeachCommand.kt\ncom/cobblemon/mod/common/command/TeachCommand\n*L\n59#1:81,3\n*E\n"})
public final class TeachCommand {
    @NotNull
    public static final TeachCommand INSTANCE = new TeachCommand();
    @NotNull
    private static final String NAME = "teach";
    @NotNull
    private static final String PLAYER = "player";
    @NotNull
    private static final String SLOT = "slot";
    @NotNull
    private static final String MOVE = "move";
    @NotNull
    private static final Dynamic2CommandExceptionType ALREADY_KNOWS_EXCEPTION = new Dynamic2CommandExceptionType(TeachCommand::ALREADY_KNOWS_EXCEPTION$lambda$0);
    @NotNull
    private static final Dynamic2CommandExceptionType CANT_LEARN_EXCEPTION = new Dynamic2CommandExceptionType(TeachCommand::CANT_LEARN_EXCEPTION$lambda$1);

    private TeachCommand() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)NAME);
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(NAME)");
        LiteralArgumentBuilder command = (LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getTEACH(), false, 2, null)).then(Commands.m_82129_((String)PLAYER, (ArgumentType)((ArgumentType)EntityArgument.m_91466_())).then(Commands.m_82129_((String)SLOT, (ArgumentType)PartySlotArgumentType.Companion.partySlot()).then(Commands.m_82129_((String)MOVE, (ArgumentType)MoveArgumentType.Companion.move()).executes(TeachCommand::register$lambda$2))));
        dispatcher.register(command);
    }

    private final int execute(CommandContext<CommandSourceStack> context, ServerPlayer player) {
        MoveTemplate move;
        Pokemon pokemon;
        block17: {
            block16: {
                boolean bl;
                block15: {
                    Object it;
                    boolean bl2;
                    boolean $i$f$any;
                    Iterable $this$any$iv;
                    block14: {
                        pokemon = PartySlotArgumentType.Companion.getPokemonOf(context, SLOT, player);
                        move = MoveArgumentType.Companion.getMove(context, MOVE);
                        PermissionValidator permissionValidator = Cobblemon.INSTANCE.getPermissionValidator();
                        Object object = context.getSource();
                        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.source");
                        if (!permissionValidator.hasPermission((SharedSuggestionProvider)object, CobblemonPermissions.INSTANCE.getTEACH_BYPASS_LEARNSET()) && !LearnsetQuery.Companion.getANY().canLearn(move, pokemon.getForm().getMoves())) {
                            CommandSyntaxException commandSyntaxException = CANT_LEARN_EXCEPTION.create((Object)pokemon.getDisplayName(), (Object)move.getDisplayName());
                            Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"CANT_LEARN_EXCEPTION.cre\u2026Name(), move.displayName)");
                            throw (Throwable)commandSyntaxException;
                        }
                        $this$any$iv = pokemon.getMoveSet().getMoves();
                        $i$f$any = false;
                        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                            bl2 = false;
                        } else {
                            for (Object element$iv : $this$any$iv) {
                                it = (Move)element$iv;
                                boolean bl3 = false;
                                if (!Intrinsics.areEqual((Object)((Move)it).getTemplate(), (Object)move)) continue;
                                bl2 = true;
                                break block14;
                            }
                            bl2 = false;
                        }
                    }
                    if (bl2) break block16;
                    $this$any$iv = pokemon.getBenchedMoves();
                    $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl = false;
                    } else {
                        for (Object element$iv : $this$any$iv) {
                            it = (BenchedMove)element$iv;
                            boolean bl4 = false;
                            if (!Intrinsics.areEqual((Object)((BenchedMove)it).getMoveTemplate(), (Object)move)) continue;
                            bl = true;
                            break block15;
                        }
                        bl = false;
                    }
                }
                if (!bl) break block17;
            }
            CommandSyntaxException commandSyntaxException = ALREADY_KNOWS_EXCEPTION.create((Object)pokemon.getDisplayName(), (Object)move.getDisplayName());
            Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"ALREADY_KNOWS_EXCEPTION.\u2026Name(), move.displayName)");
            throw (Throwable)commandSyntaxException;
        }
        if (pokemon.getMoveSet().hasSpace()) {
            pokemon.getMoveSet().add(move.create());
        } else {
            pokemon.getBenchedMoves().add(new BenchedMove(move, 0));
        }
        Object[] objectArray = new Object[3];
        objectArray[0] = pokemon.getSpecies().getTranslatedName();
        Intrinsics.checkNotNullExpressionValue((Object)player.m_7755_(), (String)"player.name");
        objectArray[2] = move.getDisplayName();
        MutableComponent pokemonLearntMessage = LocalizationUtilsKt.commandLang(NAME, objectArray);
        ((CommandSourceStack)context.getSource()).m_288197_(() -> TeachCommand.execute$lambda$5(pokemonLearntMessage), true);
        ServerPlayer serverPlayer = ((CommandSourceStack)context.getSource()).m_230896_();
        if (!(serverPlayer != null ? serverPlayer.equals((Object)player) : false)) {
            player.m_213846_((Component)pokemonLearntMessage);
        }
        return 1;
    }

    private static final Message ALREADY_KNOWS_EXCEPTION$lambda$0(Object a, Object b) {
        Object[] objectArray = new Object[2];
        Intrinsics.checkNotNullExpressionValue((Object)a, (String)"a");
        objectArray[0] = a;
        Intrinsics.checkNotNullExpressionValue((Object)b, (String)"b");
        objectArray[1] = b;
        MutableComponent mutableComponent = LocalizationUtilsKt.commandLang("teach.already_knows", objectArray);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"commandLang(\"$NAME.already_knows\", a, b)");
        return (Message)TextKt.red(mutableComponent);
    }

    private static final Message CANT_LEARN_EXCEPTION$lambda$1(Object a, Object b) {
        Object[] objectArray = new Object[2];
        Intrinsics.checkNotNullExpressionValue((Object)a, (String)"a");
        objectArray[0] = a;
        Intrinsics.checkNotNullExpressionValue((Object)b, (String)"b");
        objectArray[1] = b;
        MutableComponent mutableComponent = LocalizationUtilsKt.commandLang("teach.cant_learn", objectArray);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"commandLang(\"$NAME.cant_learn\", a, b)");
        return (Message)TextKt.red(mutableComponent);
    }

    private static final int register$lambda$2(CommandContext it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        ServerPlayer serverPlayer = CommandContextExtensionsKt.player$default(it, null, 1, null);
        Intrinsics.checkNotNullExpressionValue((Object)serverPlayer, (String)"it.player()");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)it, serverPlayer);
    }

    private static final Component execute$lambda$5(MutableComponent $pokemonLearntMessage) {
        return (Component)$pokemonLearntMessage;
    }
}

