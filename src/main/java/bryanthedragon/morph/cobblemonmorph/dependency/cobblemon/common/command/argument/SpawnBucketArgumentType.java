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
 *  kotlin.text.StringsKt
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
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
import kotlin.text.StringsKt;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0017B\u0007\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J5\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\"\b\b\u0000\u0010\b*\u00020\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\t2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/command/argument/SpawnBucketArgumentType;", "Lcom/mojang/brigadier/arguments/ArgumentType;", "Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "", "", "getExamples", "()Ljava/util/List;", "", "S", "Lcom/mojang/brigadier/context/CommandContext;", "context", "Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;", "builder", "Ljava/util/concurrent/CompletableFuture;", "Lcom/mojang/brigadier/suggestion/Suggestions;", "listSuggestions", "(Lcom/mojang/brigadier/context/CommandContext;Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;)Ljava/util/concurrent/CompletableFuture;", "Lcom/mojang/brigadier/StringReader;", "reader", "parse", "(Lcom/mojang/brigadier/StringReader;)Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nSpawnBucketArgumentType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnBucketArgumentType.kt\ncom/cobblemon/mod/common/command/argument/SpawnBucketArgumentType\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,49:1\n1#2:50\n1549#3:51\n1620#3,3:52\n*S KotlinDebug\n*F\n+ 1 SpawnBucketArgumentType.kt\ncom/cobblemon/mod/common/command/argument/SpawnBucketArgumentType\n*L\n45#1:51\n45#1:52,3\n*E\n"})
public final class SpawnBucketArgumentType
implements ArgumentType<SpawnBucket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final List<String> EXAMPLES = CollectionsKt.listOf((Object)((SpawnBucket)CollectionsKt.first(Cobblemon.INSTANCE.getBestSpawner().getConfig().getBuckets())).getName());
    private static final MutableComponent INVALID_BUCKET = LocalizationUtilsKt.lang("command.checkspawns.invalid-bucket", new Object[0]);

    @NotNull
    public SpawnBucket parse(@NotNull StringReader reader) {
        Object v0;
        block2: {
            Intrinsics.checkNotNullParameter((Object)reader, (String)"reader");
            String name = reader.readString();
            Iterable iterable = Cobblemon.INSTANCE.getBestSpawner().getConfig().getBuckets();
            for (Object t : iterable) {
                SpawnBucket it = (SpawnBucket)t;
                boolean bl = false;
                if (!StringsKt.equals((String)it.getName(), (String)name, (boolean)true)) continue;
                v0 = t;
                break block2;
            }
            v0 = null;
        }
        SpawnBucket spawnBucket = v0;
        if (spawnBucket == null) {
            CommandSyntaxException commandSyntaxException = new SimpleCommandExceptionType((Message)INVALID_BUCKET).createWithContext((ImmutableStringReader)reader);
            Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"SimpleCommandExceptionTy\u2026createWithContext(reader)");
            throw (Throwable)commandSyntaxException;
        }
        return spawnBucket;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public <S> CompletableFuture<Suggestions> listSuggestions(@NotNull CommandContext<S> context, @NotNull SuggestionsBuilder builder) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
        Iterable $this$map$iv = Cobblemon.INSTANCE.getBestSpawner().getConfig().getBuckets();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            SpawnBucket spawnBucket = (SpawnBucket)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.getName());
        }
        CompletableFuture completableFuture = SharedSuggestionProvider.m_82970_((Iterable)((List)destination$iv$iv), (SuggestionsBuilder)builder);
        Intrinsics.checkNotNullExpressionValue((Object)completableFuture, (String)"suggestMatching(Cobblemo\u2026map { it.name }, builder)");
        return completableFuture;
    }

    @NotNull
    public List<String> getExamples() {
        return EXAMPLES;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0019J)\u0010\b\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tJ\r\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u000b\u0010\fR\u001d\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001f\u0010\u0014\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/command/argument/SpawnBucketArgumentType$Companion;", "", "S", "Lcom/mojang/brigadier/context/CommandContext;", "context", "", "name", "Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "getSpawnBucket", "(Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;)Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "Lcom/cobblemon/mod/common/command/argument/SpawnBucketArgumentType;", "spawnBucket", "()Lcom/cobblemon/mod/common/command/argument/SpawnBucketArgumentType;", "", "EXAMPLES", "Ljava/util/List;", "getEXAMPLES", "()Ljava/util/List;", "Lnet/minecraft/network/chat/MutableComponent;", "kotlin.jvm.PlatformType", "INVALID_BUCKET", "Lnet/minecraft/network/chat/MutableComponent;", "getINVALID_BUCKET", "()Lnet/minecraft/network/chat/MutableComponent;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final List<String> getEXAMPLES() {
            return EXAMPLES;
        }

        public final MutableComponent getINVALID_BUCKET() {
            return INVALID_BUCKET;
        }

        @NotNull
        public final SpawnBucketArgumentType spawnBucket() {
            return new SpawnBucketArgumentType();
        }

        @NotNull
        public final <S> SpawnBucket getSpawnBucket(@NotNull CommandContext<S> context, @NotNull String name) {
            Intrinsics.checkNotNullParameter(context, (String)"context");
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Object object = context.getArgument(name, SpawnBucket.class);
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.getArgument(name, SpawnBucket::class.java)");
            return (SpawnBucket)object;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

