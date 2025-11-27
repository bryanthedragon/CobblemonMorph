/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  kotlin.text.StringsKt
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.DoubleTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.dimension.DimensionType
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.ArrayStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.MoStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.QueryStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.text.StringsKt;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\bE\u0010FJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\u0004\b\b\u0010\tJ7\u0010\u0011\u001a\u00028\u0000\"\b\b\u0000\u0010\u000b*\u00020\n*\u00028\u00002\u0006\u0010\r\u001a\u00020\f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e\u00a2\u0006\u0004\b\u0011\u0010\u0012J;\u0010\u0015\u001a\u00028\u0000\"\b\b\u0000\u0010\u000b*\u00020\n*\u00028\u00002\u001e\u0010\u0014\u001a\u001a\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e0\u0013\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0011\u0010\u0017\u001a\u00020\n*\u00020\n\u00a2\u0006\u0004\b\u0017\u0010\u0018J#\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u001b*\b\u0012\u0004\u0012\u00020\u001a0\u0019\u00a2\u0006\u0004\b\u001c\u0010\u001dJ#\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u00190\u001b*\b\u0012\u0004\u0012\u00020\u001e0\u0019\u00a2\u0006\u0004\b\u001f\u0010\u001dJ#\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u00190\u001b*\b\u0012\u0004\u0012\u00020 0\u0019\u00a2\u0006\u0004\b!\u0010\u001dJ=\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00190\u001b\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u00028\u00000\u00192\u0012\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000#0\"\u00a2\u0006\u0004\b%\u0010&J\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020'0\u001b*\u00020'\u00a2\u0006\u0004\b%\u0010(J#\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020)0\u00190\u001b*\b\u0012\u0004\u0012\u00020)0\u0019\u00a2\u0006\u0004\b*\u0010\u001dJ\u001b\u0010,\u001a\u00020\n*\u00020+2\b\b\u0002\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b,\u0010-J\u0011\u0010/\u001a\u00020.*\u00020.\u00a2\u0006\u0004\b/\u00100RK\u00103\u001a6\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e01j\u001a\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e`28\u0006\u00a2\u0006\f\n\u0004\b3\u00104\u001a\u0004\b5\u00106RK\u00107\u001a6\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e01j\u001a\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e`28\u0006\u00a2\u0006\f\n\u0004\b7\u00104\u001a\u0004\b8\u00106RK\u00109\u001a6\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e01j\u001a\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e`28\u0006\u00a2\u0006\f\n\u0004\b9\u00104\u001a\u0004\b:\u00106RK\u0010;\u001a6\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e01j\u001a\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e`28\u0006\u00a2\u0006\f\n\u0004\b;\u00104\u001a\u0004\b<\u00106R]\u0010?\u001aH\u0012D\u0012B\u0012\u0004\u0012\u00020'\u00128\u00126\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e01j\u001a\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e`20>0=8\u0006\u00a2\u0006\f\n\u0004\b?\u0010@\u001a\u0004\bA\u0010BRK\u0010C\u001a6\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e01j\u001a\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e`28\u0006\u00a2\u0006\f\n\u0004\bC\u00104\u001a\u0004\bD\u00106\u00a8\u0006G"}, d2={"Lcom/cobblemon/mod/common/api/molang/MoLangFunctions;", "", "Lnet/minecraft/nbt/Tag;", "nbt", "Lcom/bedrockk/molang/runtime/value/MoValue;", "readMoValueFromNBT", "(Lnet/minecraft/nbt/Tag;)Lcom/bedrockk/molang/runtime/value/MoValue;", "value", "writeMoValueToNBT", "(Lcom/bedrockk/molang/runtime/value/MoValue;)Lnet/minecraft/nbt/Tag;", "Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "T", "", "name", "Ljava/util/function/Function;", "Lcom/bedrockk/molang/runtime/MoParams;", "function", "addFunction", "(Lcom/bedrockk/molang/runtime/struct/QueryStruct;Ljava/lang/String;Ljava/util/function/Function;)Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "", "functions", "addFunctions", "(Lcom/bedrockk/molang/runtime/struct/QueryStruct;Ljava/util/Map;)Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "addStandardFunctions", "(Lcom/bedrockk/molang/runtime/struct/QueryStruct;)Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "Lnet/minecraft/core/Holder;", "Lnet/minecraft/world/level/biome/Biome;", "Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "asBiomeMoLangValue", "(Lnet/minecraft/core/Holder;)Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "Lnet/minecraft/world/level/block/Block;", "asBlockMoLangValue", "Lnet/minecraft/world/level/dimension/DimensionType;", "asDimensionTypeMoLangValue", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/core/Registry;", "key", "asMoLangValue", "(Lnet/minecraft/core/Holder;Lnet/minecraft/resources/ResourceKey;)Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "Lnet/minecraft/server/level/ServerPlayer;", "(Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "Lnet/minecraft/world/level/Level;", "asWorldMoLangValue", "Lcom/bedrockk/molang/runtime/MoLangEnvironment;", "getQueryStruct", "(Lcom/bedrockk/molang/runtime/MoLangEnvironment;Ljava/lang/String;)Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "setup", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)Lcom/bedrockk/molang/runtime/MoLangRuntime;", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "biomeFunctions", "Ljava/util/HashMap;", "getBiomeFunctions", "()Ljava/util/HashMap;", "blockFunctions", "getBlockFunctions", "dimensionTypeFunctions", "getDimensionTypeFunctions", "generalFunctions", "getGeneralFunctions", "", "Lkotlin/Function1;", "playerFunctions", "Ljava/util/List;", "getPlayerFunctions", "()Ljava/util/List;", "worldFunctions", "getWorldFunctions", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nMoLangFunctions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoLangFunctions.kt\ncom/cobblemon/mod/common/api/molang/MoLangFunctions\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,196:1\n1360#2:197\n1446#2,2:198\n1549#2:200\n1620#2,3:201\n1448#2,3:204\n1603#2,9:214\n1855#2:223\n1856#2:225\n1612#2:226\n1855#2,2:227\n1855#2,2:231\n1855#2,2:233\n1864#2,3:235\n361#3,7:207\n1#4:224\n215#5,2:229\n*S KotlinDebug\n*F\n+ 1 MoLangFunctions.kt\ncom/cobblemon/mod/common/api/molang/MoLangFunctions\n*L\n107#1:197\n107#1:198,2\n107#1:200\n107#1:201,3\n107#1:204,3\n156#1:214,9\n156#1:223\n156#1:225\n156#1:226\n156#1:227,2\n178#1:231,2\n187#1:233,2\n76#1:235,3\n142#1:207,7\n156#1:224\n161#1:229,2\n*E\n"})
public final class MoLangFunctions {
    @NotNull
    public static final MoLangFunctions INSTANCE = new MoLangFunctions();
    @NotNull
    private static final HashMap<String, Function<MoParams, Object>> generalFunctions;
    @NotNull
    private static final HashMap<String, Function<MoParams, Object>> biomeFunctions;
    @NotNull
    private static final HashMap<String, Function<MoParams, Object>> worldFunctions;
    @NotNull
    private static final HashMap<String, Function<MoParams, Object>> dimensionTypeFunctions;
    @NotNull
    private static final HashMap<String, Function<MoParams, Object>> blockFunctions;
    @NotNull
    private static final List<Function1<ServerPlayer, HashMap<String, Function<MoParams, Object>>>> playerFunctions;

    private MoLangFunctions() {
    }

    @NotNull
    public final HashMap<String, Function<MoParams, Object>> getGeneralFunctions() {
        return generalFunctions;
    }

    @NotNull
    public final HashMap<String, Function<MoParams, Object>> getBiomeFunctions() {
        return biomeFunctions;
    }

    @NotNull
    public final HashMap<String, Function<MoParams, Object>> getWorldFunctions() {
        return worldFunctions;
    }

    @NotNull
    public final HashMap<String, Function<MoParams, Object>> getDimensionTypeFunctions() {
        return dimensionTypeFunctions;
    }

    @NotNull
    public final HashMap<String, Function<MoParams, Object>> getBlockFunctions() {
        return blockFunctions;
    }

    @NotNull
    public final List<Function1<ServerPlayer, HashMap<String, Function<MoParams, Object>>>> getPlayerFunctions() {
        return playerFunctions;
    }

    @NotNull
    public final ObjectValue<Holder<Biome>> asBiomeMoLangValue(@NotNull Holder<Biome> $this$asBiomeMoLangValue) {
        Intrinsics.checkNotNullParameter($this$asBiomeMoLangValue, (String)"<this>");
        ResourceKey resourceKey = Registries.f_256952_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"BIOME");
        return (ObjectValue)this.addFunctions((QueryStruct)this.asMoLangValue($this$asBiomeMoLangValue, resourceKey), (Map<String, ? extends Function<MoParams, Object>>)biomeFunctions);
    }

    @NotNull
    public final ObjectValue<Holder<Level>> asWorldMoLangValue(@NotNull Holder<Level> $this$asWorldMoLangValue) {
        Intrinsics.checkNotNullParameter($this$asWorldMoLangValue, (String)"<this>");
        ResourceKey resourceKey = Registries.f_256858_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"WORLD");
        return (ObjectValue)this.addFunctions((QueryStruct)this.asMoLangValue($this$asWorldMoLangValue, resourceKey), (Map<String, ? extends Function<MoParams, Object>>)worldFunctions);
    }

    @NotNull
    public final ObjectValue<Holder<Block>> asBlockMoLangValue(@NotNull Holder<Block> $this$asBlockMoLangValue) {
        Intrinsics.checkNotNullParameter($this$asBlockMoLangValue, (String)"<this>");
        ResourceKey resourceKey = Registries.f_256747_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"BLOCK");
        return (ObjectValue)this.addFunctions((QueryStruct)this.asMoLangValue($this$asBlockMoLangValue, resourceKey), (Map<String, ? extends Function<MoParams, Object>>)blockFunctions);
    }

    @NotNull
    public final ObjectValue<Holder<DimensionType>> asDimensionTypeMoLangValue(@NotNull Holder<DimensionType> $this$asDimensionTypeMoLangValue) {
        Intrinsics.checkNotNullParameter($this$asDimensionTypeMoLangValue, (String)"<this>");
        ResourceKey resourceKey = Registries.f_256787_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"DIMENSION_TYPE");
        return (ObjectValue)this.addFunctions((QueryStruct)this.asMoLangValue($this$asDimensionTypeMoLangValue, resourceKey), (Map<String, ? extends Function<MoParams, Object>>)dimensionTypeFunctions);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final ObjectValue<ServerPlayer> asMoLangValue(@NotNull ServerPlayer $this$asMoLangValue) {
        void $this$flatMapTo$iv$iv;
        void $this$flatMap$iv;
        Intrinsics.checkNotNullParameter((Object)$this$asMoLangValue, (String)"<this>");
        ObjectValue<ServerPlayer> value2 = new ObjectValue<ServerPlayer>($this$asMoLangValue, asMoLangValue.value.1.INSTANCE, null, 4, null);
        Iterable iterable = playerFunctions;
        QueryStruct queryStruct = value2;
        MoLangFunctions moLangFunctions = this;
        boolean $i$f$flatMap = false;
        void var5_7 = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            void $this$mapTo$iv$iv;
            Function1 it = (Function1)element$iv$iv;
            boolean bl = false;
            Set set2 = ((HashMap)it.invoke((Object)$this$asMoLangValue)).entrySet();
            Intrinsics.checkNotNullExpressionValue(set2, (String)"it(this).entries");
            Iterable $this$map$iv = set2;
            boolean $i$f$map = false;
            Iterable iterable2 = $this$map$iv;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it2;
                Map.Entry entry = (Map.Entry)item$iv$iv;
                Collection collection = destination$iv$iv2;
                boolean bl2 = false;
                collection.add(TuplesKt.to(it2.getKey(), it2.getValue()));
            }
            Iterable list$iv$iv = (List)destination$iv$iv2;
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        List list = (List)destination$iv$iv;
        moLangFunctions.addFunctions(queryStruct, MapsKt.toMap((Iterable)list));
        return value2;
    }

    @NotNull
    public final <T> ObjectValue<Holder<T>> asMoLangValue(@NotNull Holder<T> $this$asMoLangValue, @NotNull ResourceKey<Registry<T>> key) {
        Intrinsics.checkNotNullParameter($this$asMoLangValue, (String)"<this>");
        Intrinsics.checkNotNullParameter(key, (String)"key");
        ObjectValue<Holder<T>> value2 = new ObjectValue<Holder<T>>($this$asMoLangValue, asMoLangValue.value.2.INSTANCE, null, 4, null);
        value2.functions.put("is_in", arg_0 -> MoLangFunctions.asMoLangValue$lambda$12(key, value2, arg_0));
        value2.functions.put("is_of", arg_0 -> MoLangFunctions.asMoLangValue$lambda$13(value2, arg_0));
        return value2;
    }

    @NotNull
    public final QueryStruct addStandardFunctions(@NotNull QueryStruct $this$addStandardFunctions) {
        Intrinsics.checkNotNullParameter((Object)$this$addStandardFunctions, (String)"<this>");
        $this$addStandardFunctions.functions.putAll((Map<String, Function<MoParams, Object>>)generalFunctions);
        return $this$addStandardFunctions;
    }

    @NotNull
    public final <T extends QueryStruct> T addFunctions(@NotNull T $this$addFunctions, @NotNull Map<String, ? extends Function<MoParams, Object>> functions2) {
        Intrinsics.checkNotNullParameter($this$addFunctions, (String)"<this>");
        Intrinsics.checkNotNullParameter(functions2, (String)"functions");
        $this$addFunctions.functions.putAll(functions2);
        return $this$addFunctions;
    }

    @NotNull
    public final <T extends QueryStruct> T addFunction(@NotNull T $this$addFunction, @NotNull String name, @NotNull Function<MoParams, Object> function) {
        Intrinsics.checkNotNullParameter($this$addFunction, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(function, (String)"function");
        HashMap<String, Function<MoParams, Object>> hashMap = $this$addFunction.functions;
        Intrinsics.checkNotNullExpressionValue(hashMap, (String)"this.functions");
        ((Map)hashMap).put(name, function);
        return $this$addFunction;
    }

    @NotNull
    public final QueryStruct getQueryStruct(@NotNull MoLangEnvironment $this$getQueryStruct, @NotNull String name) {
        Object object;
        Intrinsics.checkNotNullParameter((Object)$this$getQueryStruct, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        HashMap<String, MoStruct> hashMap = $this$getQueryStruct.getStructs();
        Intrinsics.checkNotNullExpressionValue(hashMap, (String)"structs");
        Map $this$getOrPut$iv = hashMap;
        boolean $i$f$getOrPut = false;
        Object value$iv = $this$getOrPut$iv.get(name);
        if (value$iv == null) {
            boolean bl = false;
            MoStruct answer$iv = new QueryStruct(new HashMap<String, Function<MoParams, Object>>());
            $this$getOrPut$iv.put(name, answer$iv);
            object = answer$iv;
        } else {
            object = value$iv;
        }
        return (QueryStruct)object;
    }

    public static /* synthetic */ QueryStruct getQueryStruct$default(MoLangFunctions moLangFunctions, MoLangEnvironment moLangEnvironment, String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = "query";
        }
        return moLangFunctions.getQueryStruct(moLangEnvironment, string);
    }

    @NotNull
    public final MoLangRuntime setup(@NotNull MoLangRuntime $this$setup) {
        Intrinsics.checkNotNullParameter((Object)$this$setup, (String)"<this>");
        MoLangEnvironment moLangEnvironment = $this$setup.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"environment");
        this.addStandardFunctions(MoLangFunctions.getQueryStruct$default(this, moLangEnvironment, null, 1, null));
        return $this$setup;
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final Tag writeMoValueToNBT(@NotNull MoValue value2) {
        Tag tag;
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        MoValue moValue = value2;
        if (moValue instanceof DoubleValue) {
            tag = (Tag)DoubleTag.m_128500_((double)((DoubleValue)value2).value);
        } else if (moValue instanceof StringValue) {
            tag = (Tag)StringTag.m_129297_((String)((StringValue)value2).value);
        } else if (moValue instanceof ArrayStruct) {
            void $this$forEach$iv;
            Iterator $this$mapNotNullTo$iv$iv;
            Collection<MoValue> list = ((ArrayStruct)value2).getMap().values();
            ListTag nbtList = new ListTag();
            Iterable $this$mapNotNull$iv = list;
            boolean $i$f$mapNotNull = false;
            Iterable iterable = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv$iv$iv.iterator();
            while (iterator.hasNext()) {
                Tag it$iv$iv;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = iterator.next();
                boolean bl = false;
                MoValue p0 = (MoValue)element$iv$iv;
                boolean bl2 = false;
                if (this.writeMoValueToNBT(p0) == null) continue;
                boolean bl3 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            $this$mapNotNull$iv = (List)destination$iv$iv;
            boolean $i$f$forEach2 = false;
            for (Object element$iv : $this$forEach$iv) {
                Tag p0 = (Tag)element$iv;
                boolean bl = false;
                nbtList.add((Object)p0);
            }
            tag = (Tag)nbtList;
        } else if (moValue instanceof VariableStruct) {
            void var3_4;
            CompoundTag nbt = new CompoundTag();
            Map<String, MoValue> map = ((VariableStruct)value2).getMap();
            Intrinsics.checkNotNullExpressionValue(map, (String)"value.map");
            Map<String, MoValue> $this$forEach$iv = map;
            boolean $i$f$forEach = false;
            Iterator<Map.Entry<String, MoValue>> iterator = $this$forEach$iv.entrySet().iterator();
            while (iterator.hasNext()) {
                Tag element;
                Map.Entry<String, MoValue> element$iv;
                Map.Entry<String, MoValue> entry = element$iv = iterator.next();
                boolean bl = false;
                String key = entry.getKey();
                MoValue value3 = entry.getValue();
                Intrinsics.checkNotNullExpressionValue((Object)value3, (String)"value");
                if (INSTANCE.writeMoValueToNBT(value3) == null) continue;
                nbt.m_128365_(key, element);
            }
            tag = (Tag)var3_4;
        } else {
            tag = null;
        }
        return tag;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final MoValue readMoValueFromNBT(@NotNull Tag nbt) {
        MoValue moValue;
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        Tag tag = nbt;
        if (tag instanceof DoubleTag) {
            moValue = new DoubleValue(((DoubleTag)nbt).m_7061_());
        } else if (tag instanceof StringTag) {
            moValue = new StringValue(nbt.m_7916_());
        } else if (tag instanceof ListTag) {
            ArrayStruct array = new ArrayStruct(new HashMap());
            int index = 0;
            Iterable $this$forEach$iv = (Iterable)nbt;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Tag element = (Tag)element$iv;
                boolean bl = false;
                Intrinsics.checkNotNullExpressionValue((Object)element, (String)"element");
                MoValue value2 = INSTANCE.readMoValueFromNBT(element);
                array.setDirectly(String.valueOf(index), value2);
                ++index;
            }
            moValue = array;
        } else if (tag instanceof CompoundTag) {
            void var3_4;
            VariableStruct variable = new VariableStruct(new HashMap());
            Set set2 = ((CompoundTag)nbt).m_128431_();
            Intrinsics.checkNotNullExpressionValue((Object)set2, (String)"nbt.keys");
            Iterable $this$forEach$iv = CollectionsKt.toList((Iterable)set2);
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                String key = (String)element$iv;
                boolean bl = false;
                Tag tag2 = ((CompoundTag)nbt).m_128423_(key);
                Intrinsics.checkNotNull((Object)tag2);
                MoValue value3 = INSTANCE.readMoValueFromNBT(tag2);
                Map<String, MoValue> map = variable.getMap();
                Intrinsics.checkNotNullExpressionValue(map, (String)"variable.map");
                map.put(key, value3);
            }
            moValue = (MoValue)var3_4;
        } else {
            moValue = null;
        }
        if (moValue == null) {
            throw new IllegalArgumentException("Invalid NBT element type: " + nbt.m_7060_());
        }
        return moValue;
    }

    private static final Object generalFunctions$lambda$0(MoParams params) {
        Intrinsics.checkNotNullParameter((Object)params, (String)"params");
        String string = params.get(0).asString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"params.get<MoValue>(0).asString()");
        return new DoubleValue(MiscUtils.isInt(string));
    }

    private static final Object generalFunctions$lambda$1(MoParams params) {
        Intrinsics.checkNotNullParameter((Object)params, (String)"params");
        String string = params.get(0).asString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"params.get<MoValue>(0).asString()");
        return new DoubleValue(StringsKt.toDoubleOrNull((String)string) != null);
    }

    private static final Object generalFunctions$lambda$2(MoParams params) {
        Intrinsics.checkNotNullParameter((Object)params, (String)"params");
        String string = params.get(0).asString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"params.get<MoValue>(0).asString()");
        Double d = StringsKt.toDoubleOrNull((String)string);
        return new DoubleValue(d != null ? d : 0.0);
    }

    private static final Object generalFunctions$lambda$3(MoParams params) {
        Intrinsics.checkNotNullParameter((Object)params, (String)"params");
        String string = params.get(0).asString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"params.get<MoValue>(0).asString()");
        Integer n = StringsKt.toIntOrNull((String)string);
        return new DoubleValue(n != null ? n : 0);
    }

    private static final Object generalFunctions$lambda$4(MoParams params) {
        Intrinsics.checkNotNullParameter((Object)params, (String)"params");
        return new StringValue(params.get(0).asString());
    }

    private static final Object generalFunctions$lambda$5(MoParams moParams) {
        Intrinsics.checkNotNullParameter((Object)moParams, (String)"<anonymous parameter 0>");
        return new DoubleValue(Cobblemon.INSTANCE.getConfig().getWalkingInBattleAnimations());
    }

    private static final Object generalFunctions$lambda$6(MoParams params) {
        Intrinsics.checkNotNullParameter((Object)params, (String)"params");
        List options = new ArrayList();
        int index = 0;
        while (params.contains(index)) {
            Object t = params.get(index);
            Intrinsics.checkNotNullExpressionValue(t, (String)"params.get(index)");
            options.add(t);
            ++index;
        }
        return CollectionsKt.random((Collection)options, (Random)((Random)Random.Default));
    }

    private static final Object generalFunctions$lambda$7(MoParams params) {
        Intrinsics.checkNotNullParameter((Object)params, (String)"params");
        String curveName = params.getString(0);
        Function1<Float, Float> function1 = WaveFunctions.INSTANCE.getFunctions().get(curveName);
        if (function1 == null) {
            throw new IllegalArgumentException("Unknown curve: " + curveName);
        }
        Function1<Float, Float> curve2 = function1;
        return new ObjectValue(curve2, null, null, 6, null);
    }

    /*
     * WARNING - void declaration
     */
    private static final Object generalFunctions$lambda$9(MoParams params) {
        Intrinsics.checkNotNullParameter((Object)params, (String)"params");
        List<MoValue> values = params.getParams();
        ArrayStruct array = new ArrayStruct(new HashMap());
        Intrinsics.checkNotNullExpressionValue(values, (String)"values");
        Iterable $this$forEachIndexed$iv = values;
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void moValue;
            int n;
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            MoValue moValue2 = (MoValue)item$iv;
            int index = n;
            boolean bl = false;
            array.setDirectly(String.valueOf(index), (MoValue)moValue);
        }
        return array;
    }

    private static final Object asMoLangValue$lambda$12(ResourceKey $key, ObjectValue $value, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)$key, (String)"$key");
        Intrinsics.checkNotNullParameter((Object)$value, (String)"$value");
        String string = it.getString(0);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"it.getString(0)");
        TagKey tag = TagKey.m_203882_((ResourceKey)$key, (ResourceLocation)new ResourceLocation(StringsKt.replace$default((String)string, (String)"#", (String)"", (boolean)false, (int)4, null)));
        return new DoubleValue(((Holder)$value.getObj()).m_203656_(tag) ? 1.0 : 0.0);
    }

    private static final Object asMoLangValue$lambda$13(ObjectValue $value, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)$value, (String)"$value");
        ResourceLocation identifier = new ResourceLocation(it.getString(0));
        return new DoubleValue(((Holder)$value.getObj()).m_203373_(identifier) ? 1.0 : 0.0);
    }

    static {
        Object[] objectArray = new Pair[]{TuplesKt.to((Object)"is_int", MoLangFunctions::generalFunctions$lambda$0), TuplesKt.to((Object)"is_number", MoLangFunctions::generalFunctions$lambda$1), TuplesKt.to((Object)"to_number", MoLangFunctions::generalFunctions$lambda$2), TuplesKt.to((Object)"to_int", MoLangFunctions::generalFunctions$lambda$3), TuplesKt.to((Object)"to_string", MoLangFunctions::generalFunctions$lambda$4), TuplesKt.to((Object)"do_effect_walks", MoLangFunctions::generalFunctions$lambda$5), TuplesKt.to((Object)"random", MoLangFunctions::generalFunctions$lambda$6), TuplesKt.to((Object)"curve", MoLangFunctions::generalFunctions$lambda$7), TuplesKt.to((Object)"array", MoLangFunctions::generalFunctions$lambda$9)};
        generalFunctions = MapsKt.hashMapOf((Pair[])objectArray);
        biomeFunctions = new HashMap();
        worldFunctions = new HashMap();
        dimensionTypeFunctions = new HashMap();
        blockFunctions = new HashMap();
        objectArray = new Function1[]{playerFunctions.1.INSTANCE};
        playerFunctions = CollectionsKt.mutableListOf((Object[])objectArray);
    }
}

