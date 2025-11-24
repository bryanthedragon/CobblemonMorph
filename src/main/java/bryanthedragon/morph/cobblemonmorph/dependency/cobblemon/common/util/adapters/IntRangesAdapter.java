/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.IntRange
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.IntRanges;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IntRangesAdapter;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u00032\b\u0012\u0004\u0012\u00028\u00000\u0004B5\u0012\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00028\u00000\u001a\u0012\u0018\u0010\u0016\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u0014\u0012\u0004\u0012\u00028\u00000\u0013\u00a2\u0006\u0004\b \u0010!J'\u0010\u000b\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ+\u0010\u0011\u001a\u00020\u00052\u0006\u0010\r\u001a\u00028\u00002\b\u0010\u000e\u001a\u0004\u0018\u00010\u00072\b\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012R)\u0010\u0016\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u0014\u0012\u0004\u0012\u00028\u00000\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R#\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00028\u00000\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/util/adapters/IntRangesAdapter;", "Lcom/cobblemon/mod/common/api/spawning/IntRanges;", "T", "Lcom/google/gson/JsonDeserializer;", "Lcom/google/gson/JsonSerializer;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "t", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/spawning/IntRanges;", "src", "typeOfSrc", "Lcom/google/gson/JsonSerializationContext;", "context", "serialize", "(Lcom/cobblemon/mod/common/api/spawning/IntRanges;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "Lkotlin/Function1;", "", "Lkotlin/ranges/IntRange;", "initializer", "Lkotlin/jvm/functions/Function1;", "getInitializer", "()Lkotlin/jvm/functions/Function1;", "", "", "ranges", "Ljava/util/Map;", "getRanges", "()Ljava/util/Map;", "<init>", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)V", "common"})
@SourceDebugExtension(value={"SMAP\nTimeRangeAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TimeRangeAdapter.kt\ncom/cobblemon/mod/common/util/adapters/IntRangesAdapter\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,59:1\n26#2:60\n1855#3,2:61\n37#4,2:63\n*S KotlinDebug\n*F\n+ 1 TimeRangeAdapter.kt\ncom/cobblemon/mod/common/util/adapters/IntRangesAdapter\n*L\n40#1:60\n44#1:61,2\n57#1:63,2\n*E\n"})
public final class IntRangesAdapter<T extends IntRanges>
implements JsonDeserializer<T>,
JsonSerializer<T> {
    @NotNull
    private final Map<String, T> ranges;
    @NotNull
    private final Function1<IntRange[], T> initializer;

    public IntRangesAdapter(@NotNull Map<String, ? extends T> ranges, @NotNull Function1<? super IntRange[], ? extends T> initializer) {
        Intrinsics.checkNotNullParameter(ranges, (String)"ranges");
        Intrinsics.checkNotNullParameter(initializer, (String)"initializer");
        this.ranges = ranges;
        this.initializer = initializer;
    }

    @NotNull
    public final Map<String, T> getRanges() {
        return this.ranges;
    }

    @NotNull
    public final Function1<IntRange[], T> getInitializer() {
        return this.initializer;
    }

    @NotNull
    public JsonElement serialize(@NotNull T src, @Nullable Type typeOfSrc, @Nullable JsonSerializationContext context) {
        Intrinsics.checkNotNullParameter(src, (String)"src");
        return (JsonElement)new JsonPrimitive(CollectionsKt.joinToString$default((Iterable)((IntRanges)src).getRanges(), null, null, null, (int)0, null, (Function1)serialize.1.INSTANCE, (int)31, null));
    }

    @NotNull
    public T deserialize(@NotNull JsonElement json, @NotNull Type t, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)t, (String)"t");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        String str = json.getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)str, (String)"str");
        String[] stringArray = new String[]{","};
        List splits = StringsKt.split$default((CharSequence)str, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
        if (splits.isEmpty()) {
            boolean $i$f$emptyArray = false;
            return (T)((IntRanges)this.initializer.invoke((Object)new IntRange[0]));
        }
        List ranges = new ArrayList();
        Iterable $this$forEach$iv = splits;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String it = (String)element$iv;
            boolean bl = false;
            String[] stringArray2 = new String[]{"-"};
            List range = StringsKt.split$default((CharSequence)it, (String[])stringArray2, (boolean)false, (int)0, (int)6, null);
            if (range.size() == 2 && MiscUtilsKt.isInt((String)range.get(0)) && MiscUtilsKt.isInt((String)range.get(1))) {
                ranges.add(new IntRange(Integer.parseInt((String)range.get(0)), Integer.parseInt((String)range.get(1))));
                continue;
            }
            if (range.size() != 1) continue;
            String string = ((String)range.get(0)).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
            IntRanges matchingRange = (IntRanges)this.ranges.get(string);
            if (matchingRange != null) {
                ranges.addAll((Collection)matchingRange.getRanges());
                continue;
            }
            if (!MiscUtilsKt.isInt((String)range.get(0))) continue;
            ranges.add(new IntRange(Integer.parseInt((String)range.get(0)), Integer.parseInt((String)range.get(0))));
        }
        Collection $this$toTypedArray$iv = ranges;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        return (T)((IntRanges)this.initializer.invoke((Object)thisCollection$iv.toArray(new IntRange[0])));
    }
}

