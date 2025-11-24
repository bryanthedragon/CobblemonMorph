/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/MulchVariantAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/mulch/MulchVariant;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/mulch/MulchVariant;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nMulchVariantAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MulchVariantAdapter.kt\ncom/cobblemon/mod/common/util/adapters/MulchVariantAdapter\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,25:1\n3792#2:26\n4307#2,2:27\n*S KotlinDebug\n*F\n+ 1 MulchVariantAdapter.kt\ncom/cobblemon/mod/common/util/adapters/MulchVariantAdapter\n*L\n22#1:26\n22#1:27,2\n*E\n"})
public final class MulchVariantAdapter
implements JsonDeserializer<MulchVariant> {
    @NotNull
    public static final MulchVariantAdapter INSTANCE = new MulchVariantAdapter();

    private MulchVariantAdapter() {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public MulchVariant deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        String string = json.getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.asString");
        String string2 = string.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        String mulchName = string2;
        MulchVariant[] $this$filter$iv = MulchVariant.values();
        boolean $i$f$filter = false;
        MulchVariant[] mulchVariantArray = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        int n = ((void)$this$filterTo$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void it = element$iv$iv = $this$filterTo$iv$iv[i];
            boolean bl = false;
            String string3 = it.name().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (!Intrinsics.areEqual((Object)string3, (Object)mulchName)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (MulchVariant)((Object)CollectionsKt.first((List)((List)destination$iv$iv)));
    }
}

