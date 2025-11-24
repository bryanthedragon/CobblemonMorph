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
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogues;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0017B\u0007\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J5\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\"\b\b\u0000\u0010\b*\u00020\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\t2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/command/argument/DialogueArgumentType;", "Lcom/mojang/brigadier/arguments/ArgumentType;", "Lnet/minecraft/resources/ResourceLocation;", "", "", "getExamples", "()Ljava/util/List;", "", "S", "Lcom/mojang/brigadier/context/CommandContext;", "context", "Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;", "builder", "Ljava/util/concurrent/CompletableFuture;", "Lcom/mojang/brigadier/suggestion/Suggestions;", "listSuggestions", "(Lcom/mojang/brigadier/context/CommandContext;Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;)Ljava/util/concurrent/CompletableFuture;", "Lcom/mojang/brigadier/StringReader;", "reader", "parse", "(Lcom/mojang/brigadier/StringReader;)Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nDialogueArgumentType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueArgumentType.kt\ncom/cobblemon/mod/common/command/argument/DialogueArgumentType\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,54:1\n1549#2:55\n1620#2,3:56\n*S KotlinDebug\n*F\n+ 1 DialogueArgumentType.kt\ncom/cobblemon/mod/common/command/argument/DialogueArgumentType\n*L\n50#1:55\n50#1:56,3\n*E\n"})
public final class DialogueArgumentType
implements ArgumentType<ResourceLocation> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final List<String> EXAMPLES = CollectionsKt.listOf((Object)"cobblemon:example");
    private static final MutableComponent INVALID_DIALOGUE = MiscUtilsKt.asTranslated("cobblemon.command.dialogue.invalid-dialogue");

    @NotNull
    public ResourceLocation parse(@NotNull StringReader reader) {
        Intrinsics.checkNotNullParameter((Object)reader, (String)"reader");
        try {
            return ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(reader, null, 1, null);
        }
        catch (Exception e) {
            CommandSyntaxException commandSyntaxException = new SimpleCommandExceptionType((Message)INVALID_DIALOGUE).createWithContext((ImmutableStringReader)reader);
            Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"SimpleCommandExceptionTy\u2026createWithContext(reader)");
            throw (Throwable)commandSyntaxException;
        }
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public <S> CompletableFuture<Suggestions> listSuggestions(@NotNull CommandContext<S> context, @NotNull SuggestionsBuilder builder) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
        Iterable $this$map$iv = Dialogues.INSTANCE.getDialogues().keySet();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ResourceLocation resourceLocation = (ResourceLocation)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(Intrinsics.areEqual((Object)it.m_135827_(), (Object)"cobblemon") ? it.m_135815_() : it.toString());
        }
        CompletableFuture completableFuture = SharedSuggestionProvider.m_82970_((Iterable)((List)destination$iv$iv), (SuggestionsBuilder)builder);
        Intrinsics.checkNotNullExpressionValue((Object)completableFuture, (String)"suggestMatching(Dialogue\u2026it.toString() }, builder)");
        return completableFuture;
    }

    @NotNull
    public List<String> getExamples() {
        return EXAMPLES;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0019J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J)\u0010\u000b\u001a\u00020\n\"\u0004\b\u0000\u0010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u00062\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\u000b\u0010\fR\u001d\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001f\u0010\u0014\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/command/argument/DialogueArgumentType$Companion;", "", "Lcom/cobblemon/mod/common/command/argument/DialogueArgumentType;", "dialogue", "()Lcom/cobblemon/mod/common/command/argument/DialogueArgumentType;", "S", "Lcom/mojang/brigadier/context/CommandContext;", "context", "", "name", "Lnet/minecraft/resources/ResourceLocation;", "getDialogue", "(Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;", "", "EXAMPLES", "Ljava/util/List;", "getEXAMPLES", "()Ljava/util/List;", "Lnet/minecraft/network/chat/MutableComponent;", "kotlin.jvm.PlatformType", "INVALID_DIALOGUE", "Lnet/minecraft/network/chat/MutableComponent;", "getINVALID_DIALOGUE", "()Lnet/minecraft/network/chat/MutableComponent;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final List<String> getEXAMPLES() {
            return EXAMPLES;
        }

        public final MutableComponent getINVALID_DIALOGUE() {
            return INVALID_DIALOGUE;
        }

        @NotNull
        public final DialogueArgumentType dialogue() {
            return new DialogueArgumentType();
        }

        @NotNull
        public final <S> ResourceLocation getDialogue(@NotNull CommandContext<S> context, @NotNull String name) {
            Intrinsics.checkNotNullParameter(context, (String)"context");
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Object object = context.getArgument(name, ResourceLocation.class);
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.getArgument(name, Identifier::class.java)");
            return (ResourceLocation)object;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

