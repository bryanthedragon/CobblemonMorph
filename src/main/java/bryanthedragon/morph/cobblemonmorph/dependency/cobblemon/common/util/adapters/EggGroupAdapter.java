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
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.egg.EggGroup;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.EggGroupAdapter;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J'\u0010\n\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000f\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00020\u00118\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/util/adapters/EggGroupAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/pokemon/egg/EggGroup;", "Lcom/google/gson/JsonSerializer;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "typeOfT", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/pokemon/egg/EggGroup;", "src", "typeOfSrc", "Lcom/google/gson/JsonSerializationContext;", "serialize", "(Lcom/cobblemon/mod/common/api/pokemon/egg/EggGroup;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "", "eggGroups", "[Lcom/cobblemon/mod/common/api/pokemon/egg/EggGroup;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nEggGroupAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EggGroupAdapter.kt\ncom/cobblemon/mod/common/util/adapters/EggGroupAdapter\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,40:1\n1282#2,2:41\n*S KotlinDebug\n*F\n+ 1 EggGroupAdapter.kt\ncom/cobblemon/mod/common/util/adapters/EggGroupAdapter\n*L\n27#1:41,2\n*E\n"})
public final class EggGroupAdapter
implements JsonDeserializer<EggGroup>,
JsonSerializer<EggGroup> {
    @NotNull
    public static final EggGroupAdapter INSTANCE = new EggGroupAdapter();
    @NotNull
    private static final EggGroup[] eggGroups = EggGroup.values();

    private EggGroupAdapter() {
    }

    @NotNull
    public EggGroup deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @NotNull JsonDeserializationContext context) {
        EggGroup eggGroup;
        String rawID;
        block2: {
            Intrinsics.checkNotNullParameter((Object)json, (String)"json");
            Intrinsics.checkNotNullParameter((Object)typeOfT, (String)"typeOfT");
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            rawID = json.getAsString();
            EggGroup[] $this$firstOrNull$iv = eggGroups;
            boolean $i$f$firstOrNull = false;
            int n = $this$firstOrNull$iv.length;
            for (int i = 0; i < n; ++i) {
                EggGroup element$iv;
                EggGroup eggGroup2 = element$iv = $this$firstOrNull$iv[i];
                boolean bl = false;
                if (!StringsKt.equals((String)eggGroup2.name(), (String)rawID, (boolean)true)) continue;
                eggGroup = element$iv;
                break block2;
            }
            eggGroup = null;
        }
        if (eggGroup == null) {
            throw new IllegalStateException("Failed to resolve egg group from: " + rawID);
        }
        return eggGroup;
    }

    @NotNull
    public JsonElement serialize(@NotNull EggGroup src, @NotNull Type typeOfSrc, @NotNull JsonSerializationContext context) {
        Intrinsics.checkNotNullParameter((Object)((Object)src), (String)"src");
        Intrinsics.checkNotNullParameter((Object)typeOfSrc, (String)"typeOfSrc");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        String string = src.name().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        String[] stringArray = new String[]{"_"};
        return (JsonElement)new JsonPrimitive(CollectionsKt.joinToString$default((Iterable)StringsKt.split$default((CharSequence)string, (String[])stringArray, (boolean)false, (int)0, (int)6, null), (CharSequence)" ", null, null, (int)0, null, (Function1)serialize.1.INSTANCE, (int)30, null));
    }
}

