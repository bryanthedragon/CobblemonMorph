/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/DropEntryAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/drop/DropEntry;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/drop/DropEntry;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nDropEntryAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DropEntryAdapter.kt\ncom/cobblemon/mod/common/util/adapters/DropEntryAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,33:1\n1#2:34\n*E\n"})
public final class DropEntryAdapter
implements JsonDeserializer<DropEntry> {
    @NotNull
    public static final DropEntryAdapter INSTANCE = new DropEntryAdapter();

    private DropEntryAdapter() {
    }

    /*
     * Unable to fully structure code
     */
    @NotNull
    public DropEntry deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        (JsonObject)json;
        var5_4 = ((JsonObject)json).get("type");
        if (var5_4 == null || (var6_5 = var5_4.getAsString()) == null) ** GOTO lbl-1000
        it = var6_5;
        $i$a$-let-DropEntryAdapter$deserialize$entryClass$1 = false;
        v0 = DropEntry.Companion.getByName(it);
        if (v0 == null) {
            throw new IllegalArgumentException("Unrecognized drop entry type: " + it);
        }
        var7_8 = v0;
        if (var7_8 != null) {
            v1 = var7_8;
        } else lbl-1000:
        // 2 sources

        {
            v1 = DropEntry.Companion.getDefaultType();
        }
        entryClass = v1;
        v2 = ctx.deserialize(json, (Type)entryClass);
        Intrinsics.checkNotNullExpressionValue((Object)v2, (String)"ctx.deserialize(json, entryClass)");
        return (DropEntry)v2;
    }
}

