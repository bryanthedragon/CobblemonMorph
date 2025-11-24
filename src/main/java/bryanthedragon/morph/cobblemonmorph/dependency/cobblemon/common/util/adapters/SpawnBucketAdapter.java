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
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J'\u0010\n\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/util/adapters/SpawnBucketAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "Lcom/google/gson/JsonSerializer;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "bucket", "Lcom/google/gson/JsonSerializationContext;", "Lcom/google/gson/JsonPrimitive;", "serialize", "(Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonPrimitive;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSpawnBucketAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnBucketAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SpawnBucketAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,34:1\n1#2:35\n*E\n"})
public final class SpawnBucketAdapter
implements JsonDeserializer<SpawnBucket>,
JsonSerializer<SpawnBucket> {
    @NotNull
    public static final SpawnBucketAdapter INSTANCE = new SpawnBucketAdapter();

    private SpawnBucketAdapter() {
    }

    @NotNull
    public JsonPrimitive serialize(@NotNull SpawnBucket bucket, @NotNull Type type, @NotNull JsonSerializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)bucket, (String)"bucket");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        return new JsonPrimitive(bucket.getName());
    }

    @NotNull
    public SpawnBucket deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        Object v0;
        block2: {
            Intrinsics.checkNotNullParameter((Object)json, (String)"json");
            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
            Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
            Iterable iterable = Cobblemon.INSTANCE.getBestSpawner().getConfig().getBuckets();
            for (Object t : iterable) {
                SpawnBucket it = (SpawnBucket)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getName(), (Object)json.getAsString())) continue;
                v0 = t;
                break block2;
            }
            v0 = null;
        }
        SpawnBucket spawnBucket = v0;
        if (spawnBucket == null) {
            throw new IllegalStateException("Spawn referred to invalid spawn bucket: " + json.getAsString() + ". Is it missing from the config?");
        }
        return spawnBucket;
    }
}

