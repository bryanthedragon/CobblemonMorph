/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.commands.SharedSuggestionProvider
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.PropertiesCompletionProvider;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.commands.SharedSuggestionProvider;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u001a2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001aB\u0007\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u001d\u0010\u0006\u001a\u0010\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00040\u00040\u0003H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0016\u00a2\u0006\u0004\b\b\u0010\u0007J1\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\"\u0004\b\u0000\u0010\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\n2\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J;\u0010\u0016\u001a&\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u000f0\u000f \u0005*\u0012\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u000f0\u000f\u0018\u00010\u000e0\u000e2\u0006\u0010\r\u001a\u00020\fH\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/command/argument/PokemonPropertiesArgumentType;", "Lcom/mojang/brigadier/arguments/ArgumentType;", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "", "", "kotlin.jvm.PlatformType", "collectSpeciesIdentifiers", "()Ljava/util/List;", "getExamples", "S", "Lcom/mojang/brigadier/context/CommandContext;", "context", "Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;", "builder", "Ljava/util/concurrent/CompletableFuture;", "Lcom/mojang/brigadier/suggestion/Suggestions;", "listSuggestions", "(Lcom/mojang/brigadier/context/CommandContext;Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;)Ljava/util/concurrent/CompletableFuture;", "Lcom/mojang/brigadier/StringReader;", "reader", "parse", "(Lcom/mojang/brigadier/StringReader;)Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "suggestSpeciesAndPropertyKeys", "(Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;)Ljava/util/concurrent/CompletableFuture;", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonPropertiesArgumentType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonPropertiesArgumentType.kt\ncom/cobblemon/mod/common/command/argument/PokemonPropertiesArgumentType\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,72:1\n766#2:73\n857#2,2:74\n1549#2:76\n1620#2,3:77\n1549#2:80\n1620#2,3:81\n*S KotlinDebug\n*F\n+ 1 PokemonPropertiesArgumentType.kt\ncom/cobblemon/mod/common/command/argument/PokemonPropertiesArgumentType\n*L\n61#1:73\n61#1:74,2\n61#1:76\n61#1:77,3\n69#1:80\n69#1:81,3\n*E\n"})
public final class PokemonPropertiesArgumentType
implements ArgumentType<PokemonProperties> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final List<String> EXAMPLES = CollectionsKt.listOf((Object)"eevee");
    @NotNull
    private static final String ASSIGNER = "=";

    @NotNull
    public PokemonProperties parse(@NotNull StringReader reader) {
        Intrinsics.checkNotNullParameter((Object)reader, (String)"reader");
        String properties2 = reader.getRemaining();
        reader.setCursor(reader.getTotalLength());
        Intrinsics.checkNotNullExpressionValue((Object)properties2, (String)"properties");
        return PokemonProperties.Companion.parse$default(PokemonProperties.Companion, properties2, null, null, 6, null);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public <S> CompletableFuture<Suggestions> listSuggestions(@NotNull CommandContext<S> context, @NotNull SuggestionsBuilder builder) {
        Intrinsics.checkNotNullParameter(context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
        String string = builder.getRemainingLowerCase();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"builder.remainingLowerCase");
        String[] stringArray = new String[]{" "};
        List sections = StringsKt.split$default((CharSequence)string, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
        if (sections.isEmpty()) {
            CompletableFuture<Suggestions> completableFuture = this.suggestSpeciesAndPropertyKeys(builder);
            Intrinsics.checkNotNullExpressionValue(completableFuture, (String)"this.suggestSpeciesAndPropertyKeys(builder)");
            return completableFuture;
        }
        String currentSection = (String)CollectionsKt.last((List)sections);
        if (StringsKt.contains$default((CharSequence)currentSection, (CharSequence)ASSIGNER, (boolean)false, (int)2, null)) {
            String propertyKey = StringsKt.substringBefore$default((String)currentSection, (String)ASSIGNER, null, (int)2, null);
            String currentValue = StringsKt.substringAfter$default((String)currentSection, (String)ASSIGNER, null, (int)2, null);
            return PropertiesCompletionProvider.INSTANCE.suggestValues(propertyKey, currentValue, builder);
        }
        if (sections.size() >= 2) {
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            String it;
            void $this$filterTo$iv$iv;
            Iterable $this$filter$iv = sections;
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                it = (String)element$iv$iv;
                boolean bl = false;
                if (!StringsKt.contains$default((CharSequence)it, (CharSequence)"=", (boolean)false, (int)2, null)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filter$iv = (List)destination$iv$iv;
            boolean $i$f$map = false;
            $this$filterTo$iv$iv = $this$map$iv;
            destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                it = (String)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(StringsKt.substringBefore$default((String)it, (String)"=", null, (int)2, null));
            }
            Set usedKeys = CollectionsKt.toSet((Iterable)((List)destination$iv$iv));
            return PropertiesCompletionProvider.INSTANCE.suggestKeys(currentSection, usedKeys, builder);
        }
        CompletableFuture<Suggestions> completableFuture = this.suggestSpeciesAndPropertyKeys(builder);
        Intrinsics.checkNotNullExpressionValue(completableFuture, (String)"this.suggestSpeciesAndPropertyKeys(builder)");
        return completableFuture;
    }

    private final CompletableFuture<Suggestions> suggestSpeciesAndPropertyKeys(SuggestionsBuilder builder) {
        return SharedSuggestionProvider.m_82970_((Iterable)CollectionsKt.plus((Collection)this.collectSpeciesIdentifiers(), (Iterable)PropertiesCompletionProvider.INSTANCE.keys()), (SuggestionsBuilder)builder);
    }

    /*
     * WARNING - void declaration
     */
    private final List<String> collectSpeciesIdentifiers() {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = PokemonSpecies.INSTANCE.getSpecies();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            Species species = (Species)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(Intrinsics.areEqual((Object)it.getResourceIdentifier().m_135827_(), (Object)"cobblemon") ? it.getResourceIdentifier().m_135815_() : it.getResourceIdentifier().toString());
        }
        return (List)destination$iv$iv;
    }

    @NotNull
    public List<String> getExamples() {
        return EXAMPLES;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J)\u0010\b\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tJ\r\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u00058\u0002X\u0082D\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000eR\u001d\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/command/argument/PokemonPropertiesArgumentType$Companion;", "", "S", "Lcom/mojang/brigadier/context/CommandContext;", "context", "", "name", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getPokemonProperties", "(Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "Lcom/cobblemon/mod/common/command/argument/PokemonPropertiesArgumentType;", "properties", "()Lcom/cobblemon/mod/common/command/argument/PokemonPropertiesArgumentType;", "ASSIGNER", "Ljava/lang/String;", "", "EXAMPLES", "Ljava/util/List;", "getEXAMPLES", "()Ljava/util/List;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final List<String> getEXAMPLES() {
            return EXAMPLES;
        }

        @NotNull
        public final PokemonPropertiesArgumentType properties() {
            return new PokemonPropertiesArgumentType();
        }

        @NotNull
        public final <S> PokemonProperties getPokemonProperties(@NotNull CommandContext<S> context, @NotNull String name) {
            Intrinsics.checkNotNullParameter(context, (String)"context");
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Object object = context.getArgument(name, PokemonProperties.class);
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.getArgument(name\u2026onProperties::class.java)");
            return (PokemonProperties)object;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

