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
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PossibleHeldItem;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/PossibleHeldItemAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/spawning/detail/PossibleHeldItem;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "tp", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/spawning/detail/PossibleHeldItem;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nPossibleHeldItemAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PossibleHeldItemAdapter.kt\ncom/cobblemon/mod/common/util/adapters/PossibleHeldItemAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,46:1\n1#2:47\n*E\n"})
public final class PossibleHeldItemAdapter
implements JsonDeserializer<PossibleHeldItem> {
    @NotNull
    public static final PossibleHeldItemAdapter INSTANCE = new PossibleHeldItemAdapter();

    private PossibleHeldItemAdapter() {
    }

    @NotNull
    public PossibleHeldItem deserialize(@NotNull JsonElement json, @NotNull Type tp, @NotNull JsonDeserializationContext ctx) {
        CompoundTag compoundTag;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)tp, (String)"tp");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        if (json.isJsonPrimitive()) {
            String string = json.getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"asString");
            return new PossibleHeldItem(string, null, 100.0);
        }
        JsonObject cfr_ignored_0 = (JsonObject)json;
        Object object = ((JsonObject)json).get("nbt");
        if (object != null && (object = object.getAsString()) != null) {
            Object it = object;
            boolean bl = false;
            compoundTag = NbtUtils.m_178024_((String)it);
        } else {
            compoundTag = null;
        }
        CompoundTag nbt = compoundTag;
        String item = ((JsonObject)json).get("item").getAsString();
        JsonElement jsonElement = ((JsonObject)json).get("percentage");
        double percentage = jsonElement != null ? jsonElement.getAsDouble() : 100.0;
        Intrinsics.checkNotNullExpressionValue((Object)item, (String)"item");
        return new PossibleHeldItem(item, nbt, percentage);
    }
}

