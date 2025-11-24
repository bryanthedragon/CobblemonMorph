/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.ArrayStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ModDependant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.CompositeSpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.RegisteredSpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.multiplier.WeightMultiplier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018\u0000 f2\u00020\u0001:\u0001fB\u0007\u00a2\u0006\u0004\be\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001b\u0010\b\u001a\u0006\u0012\u0002\b\u00030\u00072\u0006\u0010\u0006\u001a\u00020\u0005H&\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0010\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011R,\u0010\u0014\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00130\u00128\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\"\u0010\u001b\u001a\u00020\u001a8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R$\u0010\"\u001a\u0004\u0018\u00010!8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R,\u0010(\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00130\u00128\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010\u0015\u001a\u0004\b)\u0010\u0017\"\u0004\b*\u0010\u0019R&\u0010,\u001a\u0006\u0012\u0002\b\u00030+8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R$\u00103\u001a\u0004\u0018\u0001028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b3\u00104\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\"\u0010:\u001a\u0002098\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b:\u0010;\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\"\u0010@\u001a\u0002028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b@\u00104\u001a\u0004\bA\u00106\"\u0004\bB\u00108R(\u0010C\u001a\b\u0012\u0004\u0012\u0002020\u00128\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bC\u0010\u0015\u001a\u0004\bD\u0010\u0017\"\u0004\bE\u0010\u0019R(\u0010G\u001a\b\u0012\u0004\u0012\u0002020F8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\bG\u0010\u0015\u001a\u0004\bH\u0010\u0017\"\u0004\bI\u0010\u0019R(\u0010J\u001a\b\u0012\u0004\u0012\u0002020F8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\bJ\u0010\u0015\u001a\u0004\bK\u0010\u0017\"\u0004\bL\u0010\u0019R\"\u0010N\u001a\u00020M8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bN\u0010O\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR\u0017\u0010U\u001a\u00020T8\u0006\u00a2\u0006\f\n\u0004\bU\u0010V\u001a\u0004\bW\u0010XR\u0014\u0010Z\u001a\u0002028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bY\u00106R\"\u0010[\u001a\u00020M8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b[\u0010O\u001a\u0004\b\\\u0010Q\"\u0004\b]\u0010SR(\u0010_\u001a\b\u0012\u0004\u0012\u00020^0\u00128\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b_\u0010\u0015\u001a\u0004\b`\u0010\u0017\"\u0004\ba\u0010\u0019R\"\u0010b\u001a\u0002098\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bb\u0010;\u001a\u0004\bc\u0010=\"\u0004\bd\u0010?\u00a8\u0006g"}, d2={"Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "Lcom/cobblemon/mod/common/api/ModDependant;", "", "autoLabel", "()V", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnAction;", "doSpawn", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Lcom/cobblemon/mod/common/api/spawning/detail/SpawnAction;", "Lnet/minecraft/network/chat/MutableComponent;", "getName", "()Lnet/minecraft/network/chat/MutableComponent;", "", "isSatisfiedBy", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Z", "isValid", "()Z", "", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "anticonditions", "Ljava/util/List;", "getAnticonditions", "()Ljava/util/List;", "setAnticonditions", "(Ljava/util/List;)V", "Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "bucket", "Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "getBucket", "()Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "setBucket", "(Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;)V", "Lcom/cobblemon/mod/common/api/spawning/condition/CompositeSpawningCondition;", "compositeCondition", "Lcom/cobblemon/mod/common/api/spawning/condition/CompositeSpawningCondition;", "getCompositeCondition", "()Lcom/cobblemon/mod/common/api/spawning/condition/CompositeSpawningCondition;", "setCompositeCondition", "(Lcom/cobblemon/mod/common/api/spawning/condition/CompositeSpawningCondition;)V", "conditions", "getConditions", "setConditions", "Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;", "context", "Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;", "getContext", "()Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;", "setContext", "(Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;)V", "", "displayName", "Ljava/lang/String;", "getDisplayName", "()Ljava/lang/String;", "setDisplayName", "(Ljava/lang/String;)V", "", "height", "I", "getHeight", "()I", "setHeight", "(I)V", "id", "getId", "setId", "labels", "getLabels", "setLabels", "", "neededInstalledMods", "getNeededInstalledMods", "setNeededInstalledMods", "neededUninstalledMods", "getNeededUninstalledMods", "setNeededUninstalledMods", "", "percentage", "F", "getPercentage", "()F", "setPercentage", "(F)V", "Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "struct", "Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "getStruct", "()Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "getType", "type", "weight", "getWeight", "setWeight", "Lcom/cobblemon/mod/common/api/spawning/multiplier/WeightMultiplier;", "weightMultipliers", "getWeightMultipliers", "setWeightMultipliers", "width", "getWidth", "setWidth", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nSpawnDetail.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnDetail.kt\ncom/cobblemon/mod/common/api/spawning/detail/SpawnDetail\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,94:1\n1559#2:95\n1590#2,4:96\n2624#2,3:100\n1747#2,3:103\n*S KotlinDebug\n*F\n+ 1 SpawnDetail.kt\ncom/cobblemon/mod/common/api/spawning/detail/SpawnDetail\n*L\n70#1:95\n70#1:96,4\n78#1:100,3\n80#1:103,3\n*E\n"})
public abstract class SpawnDetail
implements ModDependant {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String id = "";
    @Nullable
    private String displayName;
    public RegisteredSpawningContext<?> context;
    @NotNull
    private SpawnBucket bucket = new SpawnBucket("", 0.0f);
    @NotNull
    private List<SpawningCondition<?>> conditions = new ArrayList();
    @NotNull
    private List<SpawningCondition<?>> anticonditions = new ArrayList();
    @Nullable
    private CompositeSpawningCondition compositeCondition;
    @NotNull
    private List<WeightMultiplier> weightMultipliers = new ArrayList();
    private int width = -1;
    private int height = -1;
    private float weight = -1.0f;
    private float percentage = -1.0f;
    @NotNull
    private List<String> labels = new ArrayList();
    @NotNull
    private final VariableStruct struct = new VariableStruct();
    @NotNull
    private List<String> neededInstalledMods = CollectionsKt.emptyList();
    @NotNull
    private List<String> neededUninstalledMods = CollectionsKt.emptyList();
    @NotNull
    private static final Map<String, RegisteredSpawnDetail<?>> spawnDetailTypes = new LinkedHashMap();

    @NotNull
    public abstract String getType();

    @NotNull
    public final String getId() {
        return this.id;
    }

    public final void setId(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.id = string;
    }

    @Nullable
    public final String getDisplayName() {
        return this.displayName;
    }

    public final void setDisplayName(@Nullable String string) {
        this.displayName = string;
    }

    @NotNull
    public final RegisteredSpawningContext<?> getContext() {
        RegisteredSpawningContext<?> registeredSpawningContext = this.context;
        if (registeredSpawningContext != null) {
            return registeredSpawningContext;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"context");
        return null;
    }

    public final void setContext(@NotNull RegisteredSpawningContext<?> registeredSpawningContext) {
        Intrinsics.checkNotNullParameter(registeredSpawningContext, (String)"<set-?>");
        this.context = registeredSpawningContext;
    }

    @NotNull
    public final SpawnBucket getBucket() {
        return this.bucket;
    }

    public final void setBucket(@NotNull SpawnBucket spawnBucket) {
        Intrinsics.checkNotNullParameter((Object)spawnBucket, (String)"<set-?>");
        this.bucket = spawnBucket;
    }

    @NotNull
    public final List<SpawningCondition<?>> getConditions() {
        return this.conditions;
    }

    public final void setConditions(@NotNull List<SpawningCondition<?>> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.conditions = list;
    }

    @NotNull
    public final List<SpawningCondition<?>> getAnticonditions() {
        return this.anticonditions;
    }

    public final void setAnticonditions(@NotNull List<SpawningCondition<?>> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.anticonditions = list;
    }

    @Nullable
    public final CompositeSpawningCondition getCompositeCondition() {
        return this.compositeCondition;
    }

    public final void setCompositeCondition(@Nullable CompositeSpawningCondition compositeSpawningCondition) {
        this.compositeCondition = compositeSpawningCondition;
    }

    @NotNull
    public final List<WeightMultiplier> getWeightMultipliers() {
        return this.weightMultipliers;
    }

    public final void setWeightMultipliers(@NotNull List<WeightMultiplier> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.weightMultipliers = list;
    }

    public final int getWidth() {
        return this.width;
    }

    public final void setWidth(int n) {
        this.width = n;
    }

    public final int getHeight() {
        return this.height;
    }

    public final void setHeight(int n) {
        this.height = n;
    }

    public final float getWeight() {
        return this.weight;
    }

    public final void setWeight(float f) {
        this.weight = f;
    }

    public final float getPercentage() {
        return this.percentage;
    }

    public final void setPercentage(float f) {
        this.percentage = f;
    }

    @NotNull
    public final List<String> getLabels() {
        return this.labels;
    }

    public final void setLabels(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.labels = list;
    }

    @NotNull
    public final VariableStruct getStruct() {
        return this.struct;
    }

    @Override
    @NotNull
    public List<String> getNeededInstalledMods() {
        return this.neededInstalledMods;
    }

    @Override
    public void setNeededInstalledMods(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.neededInstalledMods = list;
    }

    @Override
    @NotNull
    public List<String> getNeededUninstalledMods() {
        return this.neededUninstalledMods;
    }

    @Override
    public void setNeededUninstalledMods(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.neededUninstalledMods = list;
    }

    /*
     * WARNING - void declaration
     */
    public void autoLabel() {
        Collection<Pair> collection;
        void $this$mapIndexedTo$iv$iv;
        void $this$mapIndexed$iv;
        this.struct.setDirectly("weight", new DoubleValue(this.weight));
        this.struct.setDirectly("percentage", new DoubleValue(this.percentage));
        this.struct.setDirectly("id", new StringValue(this.id));
        this.struct.setDirectly("bucket", new StringValue(this.bucket.getName()));
        this.struct.setDirectly("width", new DoubleValue(this.width));
        this.struct.setDirectly("height", new DoubleValue(this.height));
        this.struct.setDirectly("context", new StringValue(this.getContext().getName()));
        Iterable iterable = this.labels;
        String string = "labels";
        VariableStruct variableStruct = this.struct;
        boolean $i$f$mapIndexed = false;
        void var3_5 = $this$mapIndexed$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$mapIndexed$iv, (int)10));
        boolean $i$f$mapIndexedTo = false;
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexedTo$iv$iv) {
            void s;
            void index;
            int n;
            if ((n = index$iv$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String string2 = (String)item$iv$iv;
            int n2 = n;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(TuplesKt.to((Object)String.valueOf((int)index), (Object)new StringValue((String)s)));
        }
        collection = (List)destination$iv$iv;
        Map map = MapsKt.toMap((Iterable)collection);
        variableStruct.setDirectly(string, new ArrayStruct(map));
    }

    @NotNull
    public MutableComponent getName() {
        String string = this.displayName;
        Object object = string != null ? MiscUtilsKt.asTranslated(string) : null;
        if (object == null) {
            object = TextKt.text(this.id);
        }
        return object;
    }

    public boolean isSatisfiedBy(@NotNull SpawningContext ctx) {
        SpawningCondition it;
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        if (!ctx.preFilter(this)) {
            return false;
        }
        if (!((Collection)this.conditions).isEmpty()) {
            boolean bl;
            block12: {
                Iterable $this$none$iv = this.conditions;
                boolean $i$f$none = false;
                if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                    bl = true;
                } else {
                    for (Object element$iv : $this$none$iv) {
                        it = (SpawningCondition)element$iv;
                        boolean bl2 = false;
                        if (!it.isSatisfiedBy(ctx)) continue;
                        bl = false;
                        break block12;
                    }
                    bl = true;
                }
            }
            if (bl) {
                return false;
            }
        }
        if (!((Collection)this.anticonditions).isEmpty()) {
            boolean bl;
            block13: {
                Iterable $this$any$iv = this.anticonditions;
                boolean $i$f$any = false;
                if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                    bl = false;
                } else {
                    for (Object element$iv : $this$any$iv) {
                        it = (SpawningCondition)element$iv;
                        boolean bl3 = false;
                        if (!it.isSatisfiedBy(ctx)) continue;
                        bl = true;
                        break block13;
                    }
                    bl = false;
                }
            }
            if (bl) {
                return false;
            }
        }
        CompositeSpawningCondition compositeSpawningCondition = this.compositeCondition;
        boolean bl = compositeSpawningCondition != null ? !compositeSpawningCondition.satisfiedBy(ctx) : false;
        if (bl) {
            return false;
        }
        return ctx.postFilter(this);
    }

    public boolean isValid() {
        return this.isModDependencySatisfied();
    }

    @NotNull
    public abstract SpawnAction<?> doSpawn(@NotNull SpawningContext var1);

    @Override
    public boolean isModDependencySatisfied() {
        return ModDependant.DefaultImpls.isModDependencySatisfied(this);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J-\u0010\t\u001a\u00020\b\"\b\b\u0000\u0010\u0003*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u00a2\u0006\u0004\b\t\u0010\nR'\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f0\u000b8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail$Companion;", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "T", "", "name", "Ljava/lang/Class;", "detailClass", "", "registerSpawnType", "(Ljava/lang/String;Ljava/lang/Class;)V", "", "Lcom/cobblemon/mod/common/api/spawning/detail/RegisteredSpawnDetail;", "spawnDetailTypes", "Ljava/util/Map;", "getSpawnDetailTypes", "()Ljava/util/Map;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Map<String, RegisteredSpawnDetail<?>> getSpawnDetailTypes() {
            return spawnDetailTypes;
        }

        public final <T extends SpawnDetail> void registerSpawnType(@NotNull String name, @NotNull Class<T> detailClass) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Intrinsics.checkNotNullParameter(detailClass, (String)"detailClass");
            this.getSpawnDetailTypes().put(name, new RegisteredSpawnDetail<T>(detailClass));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

