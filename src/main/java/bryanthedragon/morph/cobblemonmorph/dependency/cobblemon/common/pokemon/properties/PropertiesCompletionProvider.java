/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.PropertiesCompletionRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c0\u0002\u0018\u00002\u00020\u0001:\u00018B\t\b\u0002\u00a2\u0006\u0004\b7\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0004J)\u0010\u000b\u001a\u00020\u00022\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0013\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\r\u00a2\u0006\u0004\b\b\u0010\u000eJ\r\u0010\u000f\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0004J\u0017\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0012J1\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u0013\u001a\u00020\u00072\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u0006\u0010\u0016\u001a\u00020\u0015\u00a2\u0006\u0004\b\u0019\u0010\u001aJ+\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0015\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0017\u0010!\u001a\u00020\u00022\u0006\u0010 \u001a\u00020\u001fH\u0016\u00a2\u0006\u0004\b!\u0010\"R\u001a\u0010$\u001a\u00020#8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'R \u0010)\u001a\b\u0012\u0004\u0012\u00020\u00000(8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R$\u00100\u001a\u0012\u0012\u0004\u0012\u00020.0-j\b\u0012\u0004\u0012\u00020.`/8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b0\u00101R\u001a\u00103\u001a\u0002028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b3\u00104\u001a\u0004\b5\u00106\u00a8\u00069"}, d2={"Lcom/cobblemon/mod/common/pokemon/properties/PropertiesCompletionProvider;", "Lcom/cobblemon/mod/common/api/data/DataRegistry;", "", "addCustom", "()V", "addDefaults", "", "", "keys", "", "suggestions", "inject", "(Ljava/lang/Iterable;Ljava/util/Collection;)V", "", "()Ljava/util/List;", "reload", "Lnet/minecraft/server/packs/resources/ResourceManager;", "manager", "(Lnet/minecraft/server/packs/resources/ResourceManager;)V", "partialKey", "excludedKeys", "Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;", "builder", "Ljava/util/concurrent/CompletableFuture;", "Lcom/mojang/brigadier/suggestion/Suggestions;", "suggestKeys", "(Ljava/lang/String;Ljava/util/Collection;Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;)Ljava/util/concurrent/CompletableFuture;", "possibleKey", "currentValue", "suggestValues", "(Ljava/lang/String;Ljava/lang/String;Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;)Ljava/util/concurrent/CompletableFuture;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Ljava/util/HashSet;", "Lcom/cobblemon/mod/common/pokemon/properties/PropertiesCompletionProvider$SuggestionHolder;", "Lkotlin/collections/HashSet;", "providers", "Ljava/util/HashSet;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "<init>", "SuggestionHolder", "common"})
@SourceDebugExtension(value={"SMAP\nPropertiesCompletionProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PropertiesCompletionProvider.kt\ncom/cobblemon/mod/common/pokemon/properties/PropertiesCompletionProvider\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,171:1\n1360#2:172\n1446#2,5:173\n1855#2:178\n2624#2,3:179\n1855#2,2:182\n1856#2:184\n288#2,2:185\n1855#2,2:187\n1549#2:193\n1620#2,3:194\n1549#2:197\n1620#2,3:198\n1549#2:201\n1620#2,3:202\n1549#2:205\n1620#2,3:206\n1855#2,2:209\n1549#2:211\n1620#2,3:212\n1855#2,2:215\n11335#3:189\n11670#3,3:190\n*S KotlinDebug\n*F\n+ 1 PropertiesCompletionProvider.kt\ncom/cobblemon/mod/common/pokemon/properties/PropertiesCompletionProvider\n*L\n79#1:172\n79#1:173,5\n92#1:178\n93#1:179,3\n94#1:182,2\n92#1:184\n122#1:185,2\n123#1:187,2\n137#1:193\n137#1:194,3\n138#1:197\n138#1:198,3\n139#1:201\n139#1:202,3\n142#1:205\n142#1:206,3\n147#1:209,2\n153#1:211\n153#1:212,3\n157#1:215,2\n135#1:189\n135#1:190,3\n*E\n"})
public final class PropertiesCompletionProvider
implements DataRegistry {
    @NotNull
    public static final PropertiesCompletionProvider INSTANCE = new PropertiesCompletionProvider();
    @NotNull
    private static final ResourceLocation id = MiscUtils.cobblemonResource("properties_tab_completion");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;
    @NotNull
    private static final SimpleObservable<PropertiesCompletionProvider> observable = new SimpleObservable();
    @NotNull
    private static final HashSet<SuggestionHolder> providers = new HashSet();

    private PropertiesCompletionProvider() {
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @NotNull
    public PackType getType() {
        return type;
    }

    @NotNull
    public SimpleObservable<PropertiesCompletionProvider> getObservable() {
        return observable;
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
        this.reload();
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        new PropertiesCompletionRegistrySyncPacket((Collection<SuggestionHolder>)providers).sendToPlayer(player);
    }

    public final void reload() {
        providers.clear();
        this.addDefaults();
        this.addCustom();
    }

    public final void inject(@NotNull Iterable<String> keys, @NotNull Collection<String> suggestions) {
        Intrinsics.checkNotNullParameter(keys, (String)"keys");
        Intrinsics.checkNotNullParameter(suggestions, (String)"suggestions");
        ((Collection)providers).add(new SuggestionHolder(CollectionsKt.toList(keys), suggestions));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<String> keys() {
        void $this$flatMapTo$iv$iv;
        Iterable $this$flatMap$iv = providers;
        boolean $i$f$flatMap = false;
        Iterable iterable = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            SuggestionHolder it = (SuggestionHolder)element$iv$iv;
            boolean bl = false;
            Iterable list$iv$iv = it.getKeys();
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @NotNull
    public final CompletableFuture<Suggestions> suggestKeys(@NotNull String partialKey, @NotNull Collection<String> excludedKeys, @NotNull SuggestionsBuilder builder) {
        Intrinsics.checkNotNullParameter((Object)partialKey, (String)"partialKey");
        Intrinsics.checkNotNullParameter(excludedKeys, (String)"excludedKeys");
        Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
        int matches2 = 0;
        boolean exactMatch = false;
        Iterable $this$forEach$iv = providers;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String key;
            boolean bl;
            SuggestionHolder provider;
            block6: {
                provider = (SuggestionHolder)element$iv;
                boolean bl2 = false;
                Iterable $this$none$iv = provider.getKeys();
                boolean $i$f$none = false;
                if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                    bl = true;
                } else {
                    for (Object element$iv2 : $this$none$iv) {
                        key = (String)element$iv2;
                        boolean bl3 = false;
                        if (!excludedKeys.contains(key)) continue;
                        bl = false;
                        break block6;
                    }
                    bl = true;
                }
            }
            if (!bl) continue;
            Iterable $this$forEach$iv2 = provider.getKeys();
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                key = (String)element$iv2;
                boolean bl4 = false;
                if (!StringsKt.startsWith$default((String)key, (String)partialKey, (boolean)false, (int)2, null)) continue;
                String substring = StringsKt.substringAfter$default((String)key, (String)partialKey, null, (int)2, null);
                builder.suggest(builder.getRemaining() + substring);
                ++matches2;
                if (!(((CharSequence)substring).length() == 0)) continue;
                exactMatch = true;
            }
        }
        if (matches2 == 1 && exactMatch) {
            builder.suggest(builder.getRemaining() + "=");
        }
        CompletableFuture completableFuture = builder.buildFuture();
        Intrinsics.checkNotNullExpressionValue((Object)completableFuture, (String)"builder.buildFuture()");
        return completableFuture;
    }

    @NotNull
    public final CompletableFuture<Suggestions> suggestValues(@NotNull String possibleKey, @NotNull String currentValue, @NotNull SuggestionsBuilder builder) {
        Object v0;
        block3: {
            Intrinsics.checkNotNullParameter((Object)possibleKey, (String)"possibleKey");
            Intrinsics.checkNotNullParameter((Object)currentValue, (String)"currentValue");
            Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
            Iterable $this$firstOrNull$iv = providers;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                SuggestionHolder provider = (SuggestionHolder)element$iv;
                boolean bl = false;
                if (!provider.getKeys().contains(possibleKey)) continue;
                v0 = element$iv;
                break block3;
            }
            v0 = null;
        }
        SuggestionHolder suggestionHolder = v0;
        if (suggestionHolder == null) {
            CompletableFuture completableFuture = Suggestions.empty();
            Intrinsics.checkNotNullExpressionValue((Object)completableFuture, (String)"empty()");
            return completableFuture;
        }
        SuggestionHolder suggestionHolder2 = suggestionHolder;
        Iterable $this$forEach$iv = suggestionHolder2.getSuggestions();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String suggestion = (String)element$iv;
            boolean bl = false;
            if (!StringsKt.startsWith$default((String)suggestion, (String)currentValue, (boolean)false, (int)2, null)) continue;
            String substring = StringsKt.substringAfter$default((String)suggestion, (String)currentValue, null, (int)2, null);
            builder.suggest(builder.getRemaining() + substring);
        }
        CompletableFuture completableFuture = builder.buildFuture();
        Intrinsics.checkNotNullExpressionValue((Object)completableFuture, (String)"builder.buildFuture()");
        return completableFuture;
    }

    /*
     * WARNING - void declaration
     */
    private final void addDefaults() {
        Object it;
        Collection<String> collection;
        Object $this$mapTo$iv$iv;
        Object $this$map$iv;
        Object[] objectArray = new String[]{"level", "lvl", "l"};
        Iterable iterable = SetsKt.setOf((Object[])objectArray);
        objectArray = new String[]{"1", String.valueOf(Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel())};
        this.inject(iterable, SetsKt.setOf((Object[])objectArray));
        objectArray = new String[]{"shiny", "s"};
        Iterable iterable2 = SetsKt.setOf((Object[])objectArray);
        objectArray = new String[]{"yes", "no"};
        this.inject(iterable2, SetsKt.setOf((Object[])objectArray));
        objectArray = Gender.values();
        Iterable iterable3 = SetsKt.setOf((Object)"gender");
        PropertiesCompletionProvider propertiesCompletionProvider = this;
        boolean $i$f$map = false;
        void var3_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(((void)$this$map$iv).length);
        boolean $i$f$mapTo = false;
        for (PokeBall item$iv$iv : $this$mapTo$iv$iv) {
            void it2;
            void var9_21 = item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            String string = it2.name().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
            collection.add(string);
        }
        collection = (List)destination$iv$iv;
        propertiesCompletionProvider.inject(iterable3, collection);
        $this$map$iv = new String[]{"0", String.valueOf(Cobblemon.INSTANCE.getConfig().getMaxPokemonFriendship())};
        this.inject(SetsKt.setOf((Object)"friendship"), SetsKt.setOf((Object[])$this$map$iv));
        $this$map$iv = PokeBalls.INSTANCE.all();
        iterable3 = SetsKt.setOf((Object)"pokeball");
        propertiesCompletionProvider = this;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        $i$f$mapTo = false;
        Iterator iterator = $this$mapTo$iv$iv.iterator();
        while (iterator.hasNext()) {
            PokeBall item$iv$iv;
            Object item$iv$iv2 = iterator.next();
            item$iv$iv = (PokeBall)item$iv$iv2;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(Intrinsics.areEqual((Object)((PokeBall)it).getName().m_135827_(), (Object)"cobblemon") ? ((PokeBall)it).getName().m_135815_() : ((PokeBall)it).getName().toString());
        }
        collection = (List)destination$iv$iv;
        propertiesCompletionProvider.inject(iterable3, collection);
        $this$map$iv = Natures.INSTANCE.all();
        iterable3 = SetsKt.setOf((Object)"nature");
        propertiesCompletionProvider = this;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        $i$f$mapTo = false;
        iterator = $this$mapTo$iv$iv.iterator();
        while (iterator.hasNext()) {
            Object item$iv$iv = iterator.next();
            it = (Nature)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(Intrinsics.areEqual((Object)((Nature)it).getName().m_135827_(), (Object)"cobblemon") ? ((Nature)it).getName().m_135815_() : ((Nature)it).getName().toString());
        }
        collection = (List)destination$iv$iv;
        propertiesCompletionProvider.inject(iterable3, collection);
        $this$map$iv = Abilities.INSTANCE.all();
        iterable3 = SetsKt.setOf((Object)"ability");
        propertiesCompletionProvider = this;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        $i$f$mapTo = false;
        iterator = $this$mapTo$iv$iv.iterator();
        while (iterator.hasNext()) {
            Object item$iv$iv = iterator.next();
            it = (AbilityTemplate)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(Intrinsics.areEqual((Object)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(((AbilityTemplate)it).getName(), null, 1, null).m_135827_(), (Object)"cobblemon") ? ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(((AbilityTemplate)it).getName(), null, 1, null).m_135815_() : ((AbilityTemplate)it).getName());
        }
        collection = (List)destination$iv$iv;
        propertiesCompletionProvider.inject(iterable3, collection);
        $this$map$iv = new String[]{"0", String.valueOf(Cobblemon.INSTANCE.getConfig().getMaxDynamaxLevel())};
        this.inject(SetsKt.setOf((Object)"dmax"), SetsKt.setOf((Object[])$this$map$iv));
        $this$map$iv = new String[]{"yes", "no"};
        this.inject(SetsKt.setOf((Object)"gmax"), SetsKt.setOf((Object[])$this$map$iv));
        $this$map$iv = ElementalTypes.INSTANCE.all();
        iterable3 = SetsKt.setOf((Object)"tera");
        propertiesCompletionProvider = this;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        $i$f$mapTo = false;
        iterator = $this$mapTo$iv$iv.iterator();
        while (iterator.hasNext()) {
            Object item$iv$iv = iterator.next();
            it = (ElementalType)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(((ElementalType)it).getName());
        }
        collection = (List)destination$iv$iv;
        propertiesCompletionProvider.inject(iterable3, collection);
        $this$map$iv = new String[]{"yes", "no"};
        this.inject(SetsKt.setOf((Object)"tradeable"), SetsKt.setOf((Object[])$this$map$iv));
        $this$map$iv = new String[]{"originaltrainer", "ot"};
        this.inject(SetsKt.setOf((Object[])$this$map$iv), SetsKt.setOf((Object)""));
        $this$map$iv = new String[]{"originaltrainertype", "ottype"};
        Iterable iterable4 = SetsKt.setOf((Object[])$this$map$iv);
        $this$map$iv = new String[]{"None", "Player", "NPC"};
        this.inject(iterable4, SetsKt.setOf((Object[])$this$map$iv));
        Iterable $this$forEach$iv = Stats.Companion.getPERMANENT();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String statName;
            Stat stat = (Stat)element$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue((Object)stat.toString().toLowerCase(Locale.ROOT), (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
            it = new String[]{"0", "31"};
            this.inject(SetsKt.setOf((Object)(statName + "_iv")), SetsKt.setOf((Object[])it));
            it = new String[]{"0", "252"};
            this.inject(SetsKt.setOf((Object)(statName + "_ev")), SetsKt.setOf((Object[])it));
        }
        $this$forEach$iv = Statuses.INSTANCE.getPersistentStatuses();
        iterable3 = SetsKt.setOf((Object)"status");
        propertiesCompletionProvider = this;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        $i$f$mapTo = false;
        Iterator iterator2 = $this$mapTo$iv$iv.iterator();
        while (iterator2.hasNext()) {
            Object item$iv$iv = iterator2.next();
            it = (Status)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(Intrinsics.areEqual((Object)((Status)it).getName().m_135827_(), (Object)"cobblemon") ? ((Status)it).getName().m_135815_() : ((Status)it).getName().toString());
        }
        collection = (List)destination$iv$iv;
        propertiesCompletionProvider.inject(iterable3, collection);
    }

    private final void addCustom() {
        Iterable $this$forEach$iv = CustomPokemonProperty.Companion.getProperties();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            CustomPokemonPropertyType property = (CustomPokemonPropertyType)element$iv;
            boolean bl = false;
            if (!property.getNeedsKey()) continue;
            this.inject(property.getKeys(), property.examples());
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\b\u0080\b\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0005J0\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\t\u0010\nJ\u001a\u0010\r\u001a\u00020\f2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0010\u0010\u0010\u001a\u00020\u000fH\u00d6\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0010\u0010\u0012\u001a\u00020\u0003H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0014\u001a\u0004\b\u0015\u0010\u0005R\u001d\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0014\u001a\u0004\b\u0016\u0010\u0005\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/pokemon/properties/PropertiesCompletionProvider$SuggestionHolder;", "", "", "", "component1", "()Ljava/util/Collection;", "component2", "keys", "suggestions", "copy", "(Ljava/util/Collection;Ljava/util/Collection;)Lcom/cobblemon/mod/common/pokemon/properties/PropertiesCompletionProvider$SuggestionHolder;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "()Ljava/lang/String;", "Ljava/util/Collection;", "getKeys", "getSuggestions", "<init>", "(Ljava/util/Collection;Ljava/util/Collection;)V", "common"})
    public static final class SuggestionHolder {
        @NotNull
        private final Collection<String> keys;
        @NotNull
        private final Collection<String> suggestions;

        public SuggestionHolder(@NotNull Collection<String> keys, @NotNull Collection<String> suggestions) {
            Intrinsics.checkNotNullParameter(keys, (String)"keys");
            Intrinsics.checkNotNullParameter(suggestions, (String)"suggestions");
            this.keys = keys;
            this.suggestions = suggestions;
        }

        @NotNull
        public final Collection<String> getKeys() {
            return this.keys;
        }

        @NotNull
        public final Collection<String> getSuggestions() {
            return this.suggestions;
        }

        @NotNull
        public final Collection<String> component1() {
            return this.keys;
        }

        @NotNull
        public final Collection<String> component2() {
            return this.suggestions;
        }

        @NotNull
        public final SuggestionHolder copy(@NotNull Collection<String> keys, @NotNull Collection<String> suggestions) {
            Intrinsics.checkNotNullParameter(keys, (String)"keys");
            Intrinsics.checkNotNullParameter(suggestions, (String)"suggestions");
            return new SuggestionHolder(keys, suggestions);
        }

        public static /* synthetic */ SuggestionHolder copy$default(SuggestionHolder suggestionHolder, Collection collection, Collection collection2, int n, Object object) {
            if ((n & 1) != 0) {
                collection = suggestionHolder.keys;
            }
            if ((n & 2) != 0) {
                collection2 = suggestionHolder.suggestions;
            }
            return suggestionHolder.copy(collection, collection2);
        }

        @NotNull
        public String toString() {
            return "SuggestionHolder(keys=" + this.keys + ", suggestions=" + this.suggestions + ")";
        }

        public int hashCode() {
            int result = ((Object)this.keys).hashCode();
            result = result * 31 + ((Object)this.suggestions).hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SuggestionHolder)) {
                return false;
            }
            SuggestionHolder suggestionHolder = (SuggestionHolder)other;
            if (!Intrinsics.areEqual(this.keys, suggestionHolder.keys)) {
                return false;
            }
            return Intrinsics.areEqual(this.suggestions, suggestionHolder.suggestions);
        }
    }
}

