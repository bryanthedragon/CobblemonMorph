/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnLoader;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.multiplier.WeightMultiplier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MergeMode;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\bG\u0010HJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J-\u0010\u000b\u001a\u00020\u00042\u0010\u0010\t\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0\u00072\f\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b\u00a2\u0006\u0004\b\u000b\u0010\fJ!\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u000f\u0010\u0010R$\u0010\u0011\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R$\u0010\u0018\u001a\u0004\u0018\u00010\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR$\u0010\u001e\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u0012\u001a\u0004\b\u001f\u0010\u0014\"\u0004\b \u0010\u0016R(\u0010\"\u001a\b\u0012\u0002\b\u0003\u0018\u00010!8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\"\u0010)\u001a\u00020(8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R$\u00100\u001a\u0004\u0018\u00010/8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b0\u00101\u001a\u0004\b2\u00103\"\u0004\b4\u00105R$\u00107\u001a\u0004\u0018\u0001068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b7\u00108\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R$\u0010=\u001a\u0004\u0018\u00010/8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b=\u00101\u001a\u0004\b>\u00103\"\u0004\b?\u00105R*\u0010A\u001a\n\u0012\u0004\u0012\u00020@\u0018\u00010\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bA\u0010B\u001a\u0004\bC\u0010D\"\u0004\bE\u0010F\u00a8\u0006I"}, d2={"Lcom/cobblemon/mod/common/api/spawning/preset/SpawnDetailPreset;", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "spawnDetail", "", "apply", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;)V", "", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "conditions", "resolvedCondition", "applyToConditionList", "(Ljava/util/List;Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;)V", "Lcom/google/gson/JsonObject;", "conditionJson", "resolveCondition", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "anticondition", "Lcom/google/gson/JsonObject;", "getAnticondition", "()Lcom/google/gson/JsonObject;", "setAnticondition", "(Lcom/google/gson/JsonObject;)V", "Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "bucket", "Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "getBucket", "()Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "setBucket", "(Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;)V", "condition", "getCondition", "setCondition", "Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;", "context", "Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;", "getContext", "()Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;", "setContext", "(Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;)V", "Lcom/cobblemon/mod/common/util/MergeMode;", "mergeMode", "Lcom/cobblemon/mod/common/util/MergeMode;", "getMergeMode", "()Lcom/cobblemon/mod/common/util/MergeMode;", "setMergeMode", "(Lcom/cobblemon/mod/common/util/MergeMode;)V", "", "percentage", "Ljava/lang/Float;", "getPercentage", "()Ljava/lang/Float;", "setPercentage", "(Ljava/lang/Float;)V", "", "spawnDetailType", "Ljava/lang/String;", "getSpawnDetailType", "()Ljava/lang/String;", "setSpawnDetailType", "(Ljava/lang/String;)V", "weight", "getWeight", "setWeight", "Lcom/cobblemon/mod/common/api/spawning/multiplier/WeightMultiplier;", "weightMultipliers", "Ljava/util/List;", "getWeightMultipliers", "()Ljava/util/List;", "setWeightMultipliers", "(Ljava/util/List;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSpawnDetailPreset.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnDetailPreset.kt\ncom/cobblemon/mod/common/api/spawning/preset/SpawnDetailPreset\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,76:1\n1#2:77\n1855#3,2:78\n*S KotlinDebug\n*F\n+ 1 SpawnDetailPreset.kt\ncom/cobblemon/mod/common/api/spawning/preset/SpawnDetailPreset\n*L\n66#1:78,2\n*E\n"})
public abstract class SpawnDetailPreset {
    @Nullable
    private SpawnBucket bucket;
    @Nullable
    private String spawnDetailType;
    @Nullable
    private RegisteredSpawningContext<?> context;
    @Nullable
    private JsonObject condition;
    @Nullable
    private JsonObject anticondition;
    @Nullable
    private List<WeightMultiplier> weightMultipliers;
    @Nullable
    private Float weight;
    @Nullable
    private Float percentage;
    @NotNull
    private MergeMode mergeMode = MergeMode.INSERT;

    @Nullable
    public final SpawnBucket getBucket() {
        return this.bucket;
    }

    public final void setBucket(@Nullable SpawnBucket spawnBucket) {
        this.bucket = spawnBucket;
    }

    @Nullable
    public final String getSpawnDetailType() {
        return this.spawnDetailType;
    }

    public final void setSpawnDetailType(@Nullable String string) {
        this.spawnDetailType = string;
    }

    @Nullable
    public final RegisteredSpawningContext<?> getContext() {
        return this.context;
    }

    public final void setContext(@Nullable RegisteredSpawningContext<?> registeredSpawningContext) {
        this.context = registeredSpawningContext;
    }

    @Nullable
    public final JsonObject getCondition() {
        return this.condition;
    }

    public final void setCondition(@Nullable JsonObject jsonObject) {
        this.condition = jsonObject;
    }

    @Nullable
    public final JsonObject getAnticondition() {
        return this.anticondition;
    }

    public final void setAnticondition(@Nullable JsonObject jsonObject) {
        this.anticondition = jsonObject;
    }

    @Nullable
    public final List<WeightMultiplier> getWeightMultipliers() {
        return this.weightMultipliers;
    }

    public final void setWeightMultipliers(@Nullable List<WeightMultiplier> list) {
        this.weightMultipliers = list;
    }

    @Nullable
    public final Float getWeight() {
        return this.weight;
    }

    public final void setWeight(@Nullable Float f) {
        this.weight = f;
    }

    @Nullable
    public final Float getPercentage() {
        return this.percentage;
    }

    public final void setPercentage(@Nullable Float f) {
        this.percentage = f;
    }

    @NotNull
    public final MergeMode getMergeMode() {
        return this.mergeMode;
    }

    public final void setMergeMode(@NotNull MergeMode mergeMode) {
        Intrinsics.checkNotNullParameter((Object)mergeMode, (String)"<set-?>");
        this.mergeMode = mergeMode;
    }

    public void apply(@NotNull SpawnDetail spawnDetail) {
        block6: {
            SpawningCondition<?> spawningCondition;
            Object it;
            Intrinsics.checkNotNullParameter((Object)spawnDetail, (String)"spawnDetail");
            SpawnBucket spawnBucket = this.bucket;
            if (spawnBucket != null) {
                it = spawnBucket;
                boolean bl = false;
                spawnDetail.setBucket((SpawnBucket)it);
            }
            RegisteredSpawningContext<?> registeredSpawningContext = this.context;
            if (registeredSpawningContext != null) {
                it = registeredSpawningContext;
                boolean bl = false;
                spawnDetail.setContext((RegisteredSpawningContext<?>)it);
            }
            Float f = this.weight;
            if (f != null) {
                float it2 = ((Number)f).floatValue();
                boolean bl = false;
                spawnDetail.setWeight(it2);
            }
            Float f2 = this.percentage;
            if (f2 != null) {
                float it3 = ((Number)f2).floatValue();
                boolean bl = false;
                spawnDetail.setPercentage(it3);
            }
            this.mergeMode.merge((Collection)spawnDetail.getWeightMultipliers(), (Collection)this.weightMultipliers);
            SpawnDetailPreset spawnDetailPreset = this;
            List<SpawningCondition<?>> list = spawnDetail.getConditions();
            JsonObject jsonObject = this.condition;
            if (jsonObject != null) {
                JsonObject it3 = jsonObject;
                List<SpawningCondition<?>> list2 = list;
                SpawnDetailPreset spawnDetailPreset2 = spawnDetailPreset;
                boolean bl = false;
                SpawningCondition<?> spawningCondition2 = this.resolveCondition(spawnDetail, it3);
                spawnDetailPreset = spawnDetailPreset2;
                list = list2;
                spawningCondition = spawningCondition2;
            } else {
                spawningCondition = null;
            }
            spawnDetailPreset.applyToConditionList(list, spawningCondition);
            JsonObject jsonObject2 = this.anticondition;
            if (jsonObject2 == null) break block6;
            JsonObject it4 = jsonObject2;
            boolean bl = false;
            spawnDetail.getAnticonditions().add(this.resolveCondition(spawnDetail, it4));
        }
    }

    public final void applyToConditionList(@NotNull List<SpawningCondition<?>> conditions, @Nullable SpawningCondition<?> resolvedCondition) {
        Intrinsics.checkNotNullParameter(conditions, (String)"conditions");
        if (resolvedCondition == null) {
            return;
        }
        Iterable $this$forEach$iv = conditions;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SpawningCondition it = (SpawningCondition)element$iv;
            boolean bl = false;
            it.copyFrom(resolvedCondition, this.mergeMode);
        }
        if (conditions.isEmpty()) {
            conditions.add(resolvedCondition);
        }
    }

    @NotNull
    public final SpawningCondition<?> resolveCondition(@NotNull SpawnDetail spawnDetail, @NotNull JsonObject conditionJson) {
        Intrinsics.checkNotNullParameter((Object)spawnDetail, (String)"spawnDetail");
        Intrinsics.checkNotNullParameter((Object)conditionJson, (String)"conditionJson");
        SpawnLoader.INSTANCE.setDeserializingConditionClass(SpawningCondition.Companion.getByName(spawnDetail.getContext().getDefaultCondition()));
        Object object = SpawnLoader.INSTANCE.getGson().fromJson((JsonElement)conditionJson, SpawningCondition.class);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"SpawnLoader.gson.fromJso\u2026ingCondition::class.java)");
        return (SpawningCondition)object;
    }
}

