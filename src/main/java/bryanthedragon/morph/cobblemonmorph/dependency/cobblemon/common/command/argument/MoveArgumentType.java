/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0017B\u0007\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J5\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\"\b\b\u0000\u0010\b*\u00020\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\t2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/command/argument/MoveArgumentType;", "Lcom/mojang/brigadier/arguments/ArgumentType;", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "", "", "getExamples", "()Ljava/util/List;", "", "S", "Lcom/mojang/brigadier/context/CommandContext;", "context", "Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;", "builder", "Ljava/util/concurrent/CompletableFuture;", "Lcom/mojang/brigadier/suggestion/Suggestions;", "listSuggestions", "(Lcom/mojang/brigadier/context/CommandContext;Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;)Ljava/util/concurrent/CompletableFuture;", "Lcom/mojang/brigadier/StringReader;", "reader", "parse", "(Lcom/mojang/brigadier/StringReader;)Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "<init>", "()V", "Companion", "common"})
public final class MoveArgumentType
implements ArgumentType<MoveTemplate> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final List<String> EXAMPLES = CollectionsKt.listOf((Object)"tackle");
    @NotNull
    private static final MutableComponent INVALID_MOVE;

    @NotNull
    public MoveTemplate parse(@NotNull StringReader reader) {
        Intrinsics.checkNotNullParameter((Object)reader, (String)"reader");
        String string = reader.readString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"reader.readString()");
        MoveTemplate moveTemplate = Moves.INSTANCE.getByName(string);
        if (moveTemplate == null) {
            CommandSyntaxException commandSyntaxException = new SimpleCommandExceptionType((Message)INVALID_MOVE).createWithContext((ImmutableStringReader)reader);
            Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"SimpleCommandExceptionTy\u2026createWithContext(reader)");
            throw (Throwable)commandSyntaxException;
        }
        return moveTemplate;
    }

    @NotNull
    public <S> CompletableFuture<Suggestions> listSuggestions(@NotNull CommandContext<S> context, @NotNull SuggestionsBuilder builder) {
        Intrinsics.checkNotNullParameter(context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
        CompletableFuture completableFuture = SharedSuggestionProvider.m_82970_((Iterable)Moves.INSTANCE.names(), (SuggestionsBuilder)builder);
        Intrinsics.checkNotNullExpressionValue((Object)completableFuture, (String)"suggestMatching(Moves.names(), builder)");
        return completableFuture;
    }

    @NotNull
    public List<String> getExamples() {
        return EXAMPLES;
    }

    static {
        MutableComponent mutableComponent = Component.m_237115_((String)"cobblemon.command.pokespawn.invalid-move");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"translatable(\"cobblemon.\u2026.pokespawn.invalid-move\")");
        INVALID_MOVE = mutableComponent;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J)\u0010\b\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tJ\r\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u000b\u0010\fR\u001d\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0013\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/command/argument/MoveArgumentType$Companion;", "", "S", "Lcom/mojang/brigadier/context/CommandContext;", "context", "", "name", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getMove", "(Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;)Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "Lcom/cobblemon/mod/common/command/argument/MoveArgumentType;", "move", "()Lcom/cobblemon/mod/common/command/argument/MoveArgumentType;", "", "EXAMPLES", "Ljava/util/List;", "getEXAMPLES", "()Ljava/util/List;", "Lnet/minecraft/network/chat/MutableComponent;", "INVALID_MOVE", "Lnet/minecraft/network/chat/MutableComponent;", "getINVALID_MOVE", "()Lnet/minecraft/network/chat/MutableComponent;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final List<String> getEXAMPLES() {
            return EXAMPLES;
        }

        @NotNull
        public final MutableComponent getINVALID_MOVE() {
            return INVALID_MOVE;
        }

        @NotNull
        public final MoveArgumentType move() {
            return new MoveArgumentType();
        }

        @NotNull
        public final <S> MoveTemplate getMove(@NotNull CommandContext<S> context, @NotNull String name) {
            Intrinsics.checkNotNullParameter(context, (String)"context");
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Object object = context.getArgument(name, MoveTemplate.class);
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.getArgument(name\u2026MoveTemplate::class.java)");
            return (MoveTemplate)object;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

