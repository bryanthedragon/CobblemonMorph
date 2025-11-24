/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.IntRange
 *  kotlin.text.StringsKt
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.ClientMoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.GsonExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003:\u0001;B\u001d\u0012\f\u00106\u001a\b\u0012\u0004\u0012\u00028\u000005\u0012\u0006\u00108\u001a\u000207\u00a2\u0006\u0004\b9\u0010:R#\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR+\u0010\u0012\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0001\u0012\u00020\u00110\u00100\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR#\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000 0\u001a8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\u001d\u001a\u0004\b\"\u0010\u001fR\u0017\u0010$\u001a\u00020#8\u0006\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'R\u0017\u0010)\u001a\u00020(8\u0006\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R\u001d\u0010.\u001a\b\u0012\u0004\u0012\u00020-0\u000f8\u0006\u00a2\u0006\f\n\u0004\b.\u0010/\u001a\u0004\b0\u00101R\u001d\u00103\u001a\b\u0012\u0004\u0012\u0002020\u001a8\u0006\u00a2\u0006\f\n\u0004\b3\u0010\u001d\u001a\u0004\b4\u0010\u001f\u00a8\u0006<"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/JsonPose;", "Lnet/minecraft/world/entity/Entity;", "T", "", "", "", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "animations", "Ljava/util/Map;", "getAnimations", "()Ljava/util/Map;", "condition", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getCondition", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "idleAnimations", "[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "getIdleAnimations", "()[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "poseName", "Ljava/lang/String;", "getPoseName", "()Ljava/lang/String;", "", "Lcom/cobblemon/mod/common/entity/PoseType;", "poseTypes", "Ljava/util/List;", "getPoseTypes", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/SimpleQuirk;", "quirks", "getQuirks", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "getRuntime", "()Lcom/bedrockk/molang/runtime/MoLangRuntime;", "", "transformTicks", "I", "getTransformTicks", "()I", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "transformedParts", "[Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "getTransformedParts", "()[Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/JsonPose$JsonPoseTransition;", "transitions", "getTransitions", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "Lcom/google/gson/JsonObject;", "json", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/google/gson/JsonObject;)V", "JsonPoseTransition", "common"})
@SourceDebugExtension(value={"SMAP\nJsonPose.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonPose.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/JsonPose\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,131:1\n1549#2:132\n1620#2,3:133\n1549#2:136\n1620#2,2:137\n1622#2:140\n1549#2:141\n1620#2,3:142\n1603#2,9:147\n1855#2:156\n1549#2:157\n1620#2,3:158\n1856#2:162\n1612#2:163\n1549#2:166\n1620#2,3:167\n1549#2:170\n1620#2,3:171\n1#3:139\n1#3:161\n37#4,2:145\n37#4,2:164\n*S KotlinDebug\n*F\n+ 1 JsonPose.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/JsonPose\n*L\n40#1:132\n40#1:133,3\n43#1:136\n43#1:137,2\n43#1:140\n48#1:141\n48#1:142,3\n58#1:147,9\n58#1:156\n70#1:157\n70#1:158,3\n58#1:162\n58#1:163\n83#1:166\n83#1:167,3\n124#1:170\n124#1:171,3\n58#1:161\n56#1:145,2\n81#1:164,2\n*E\n"})
public final class JsonPose<T extends Entity> {
    @NotNull
    private final MoLangRuntime runtime;
    @NotNull
    private final ExpressionLike condition;
    @NotNull
    private final String poseName;
    @NotNull
    private final List<PoseType> poseTypes;
    private final int transformTicks;
    @NotNull
    private final ModelPartTransformation[] transformedParts;
    @NotNull
    private final StatelessAnimation<T, ? extends ModelFrame>[] idleAnimations;
    @NotNull
    private final List<SimpleQuirk<T>> quirks;
    @NotNull
    private final Map<String, ExpressionLike> animations;
    @NotNull
    private final List<JsonPoseTransition> transitions;

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public JsonPose(@NotNull PoseableEntityModel<T> model, @NotNull JsonObject json) {
        block45: {
            block44: {
                block43: {
                    block42: {
                        Intrinsics.checkNotNullParameter(model, (String)"model");
                        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
                        super();
                        var3_3 = ClientMoLangFunctions.INSTANCE.setupClient(MoLangFunctions.INSTANCE.setup(new MoLangRuntime()));
                        var4_4 = var3_3;
                        var30_6 = this;
                        $i$a$-also-JsonPose$runtime$1 = false;
                        v0 = it.getEnvironment();
                        Intrinsics.checkNotNullExpressionValue((Object)v0, (String)"it.environment");
                        v1 = MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, v0, null, 1, null);
                        v2 = model.getFunctions().functions;
                        Intrinsics.checkNotNullExpressionValue(v2, (String)"model.functions.functions");
                        MoLangFunctions.INSTANCE.addFunctions(v1, (Map<String, ? extends Function<MoParams, Object>>)v2);
                        var30_6.runtime = var3_3;
                        v3 = this;
                        v4 /* !! */  = GsonExtensionsKt.singularToPluralList$default(json, "condition", null, 2, null).get("conditions");
                        if (v4 /* !! */  == null || (v4 /* !! */  = GsonExtensionsKt.normalizeToArray(v4 /* !! */ )) == null) break block42;
                        var6_9 = (Iterable)v4 /* !! */ ;
                        var30_6 = v3;
                        $i$f$map = false;
                        var8_14 = $this$map$iv;
                        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                        $i$f$mapTo = false;
                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                            var13_29 = (JsonElement)item$iv$iv;
                            var31_39 = destination$iv$iv;
                            $i$a$-map-JsonPose$condition$1 = false;
                            var31_39.add(it.getAsString());
                        }
                        v3 = var30_6;
                        v4 /* !! */  = MoLangExtensionsKt.asExpressionLike((List)destination$iv$iv);
                        if (v4 /* !! */  != null) break block43;
                    }
                    v4 /* !! */  = MoLangExtensionsKt.asExpressionLike("true");
                }
                v3.condition = v4 /* !! */ ;
                v5 = json.get("poseName");
                v6 = v5 != null ? v5.getAsString() : null;
                if (v6 == null) {
                    v6 = "pose";
                }
                this.poseName = v6;
                v7 = this;
                v8 = json.get("poseTypes");
                if (v8 != null && (v8 = v8.getAsJsonArray()) != null) {
                    $this$map$iv = (Iterable)v8;
                    var30_6 = v7;
                    $i$f$map = false;
                    $this$mapTo$iv$iv = $this$map$iv;
                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                    $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        block41: {
                            it = (JsonElement)item$iv$iv;
                            var31_39 = destination$iv$iv;
                            $i$a$-map-JsonPose$poseTypes$1 = false;
                            var15_43 = PoseType.values();
                            var17_55 = var15_43.length;
                            for (var16_48 = 0; var16_48 < var17_55; ++var16_48) {
                                it = var18_61 = var15_43[var16_48];
                                $i$a$-find-JsonPose$poseTypes$1$1 = false;
                                v9 = it.name().toLowerCase(Locale.ROOT);
                                Intrinsics.checkNotNullExpressionValue((Object)v9, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                                v10 = name.getAsString();
                                Intrinsics.checkNotNullExpressionValue((Object)v10, (String)"name.asString");
                                v11 = v10.toLowerCase(Locale.ROOT);
                                Intrinsics.checkNotNullExpressionValue((Object)v11, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                                if (!Intrinsics.areEqual((Object)v9, (Object)v11)) continue;
                                v12 = var18_61;
                                break block41;
                            }
                            v12 = null;
                        }
                        if (v12 == null) {
                            throw new IllegalArgumentException("Unknown pose type " + name.getAsString());
                        }
                        var31_39.add(v12);
                    }
                    v13 = (List)destination$iv$iv;
                    v7 = var30_6;
                } else {
                    v13 = CollectionsKt.emptyList();
                }
                v14 = json.get("allPoseTypes");
                v7.poseTypes = CollectionsKt.plus((Collection)v13, (Iterable)((v14 != null ? v14.getAsBoolean() : false) != false ? ArraysKt.toList((Object[])PoseType.values()) : CollectionsKt.emptyList()));
                v15 = json.get("transformTicks");
                this.transformTicks = v15 != null ? v15.getAsInt() : 10;
                v16 = this;
                v17 = json.get("transformedParts");
                if (v17 == null || (v17 = v17.getAsJsonArray()) == null) break block44;
                $this$map$iv = (Iterable)v17;
                var30_6 = v16;
                $i$f$map = false;
                $this$mapTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    name = (JsonElement)item$iv$iv;
                    var31_39 = destination$iv$iv;
                    $i$a$-map-JsonPose$transformedParts$1 = false;
                    Intrinsics.checkNotNull((Object)it, (String)"null cannot be cast to non-null type com.google.gson.JsonObject");
                    (JsonObject)it;
                    partName = ((JsonObject)it).get("part").getAsString();
                    Intrinsics.checkNotNullExpressionValue((Object)partName, (String)"partName");
                    part = ModelPartExtensionsKt.createTransformation(model.getPart(partName));
                    v18 = ((JsonObject)it).get("rotation");
                    if (v18 != null && (v18 = (var17_56 = v18.getAsJsonArray())) != null) {
                        Intrinsics.checkNotNullExpressionValue((Object)v18, (String)"asJsonArray");
                        it = var17_56;
                        $i$a$-let-JsonPose$transformedParts$1$rotation$1 = false;
                        v19 = new Vec3(it.get(0).getAsDouble(), it.get(1).getAsDouble(), it.get(2).getAsDouble());
                    } else {
                        v19 = rotation = Vec3.f_82478_;
                    }
                    if ((v20 = ((JsonObject)it).get("position")) != null && (v20 = (var21_72 = v20.getAsJsonArray())) != null) {
                        Intrinsics.checkNotNullExpressionValue((Object)v20, (String)"asJsonArray");
                        it = var21_72;
                        $i$a$-let-JsonPose$transformedParts$1$position$1 = false;
                        v21 = new Vec3(it.get(0).getAsDouble(), it.get(1).getAsDouble(), it.get(2).getAsDouble());
                    } else {
                        v21 = Vec3.f_82478_;
                    }
                    position = v21;
                    v22 = ((JsonObject)it).get("isVisible");
                    isVisible = v22 != null ? v22.getAsBoolean() : true;
                    var31_39.add(part.withPosition(position.f_82479_, position.f_82480_, position.f_82481_).withRotationDegrees(rotation.f_82479_, rotation.f_82480_, rotation.f_82481_).withVisibility(isVisible));
                }
                v16 = var30_6;
                $i$f$map = (List)destination$iv$iv;
                $i$f$toTypedArray = false;
                thisCollection$iv = $this$toTypedArray$iv;
                v17 = thisCollection$iv.toArray(new ModelPartTransformation[0]);
                if (v17 != null) break block45;
            }
            v17 = v16.transformedParts = new ModelPartTransformation[]{};
        }
        if ((v23 = json.get("animations")) == null || (v23 = v23.getAsJsonArray()) == null) {
            v23 = new JsonArray();
        }
        v24 = v23.getAsJsonArray();
        Intrinsics.checkNotNullExpressionValue((Object)v24, (String)"json.get(\"animations\")?.\u2026 JsonArray()).asJsonArray");
        var3_3 = (Iterable)v24;
        var30_6 = this;
        $i$f$mapNotNull = false;
        $i$a$-also-JsonPose$runtime$1 = $this$mapNotNull$iv;
        destination$iv$iv = new ArrayList<E>();
        $i$f$mapNotNullTo = false;
        $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        $i$f$forEach = false;
        $i$f$mapTo = $this$forEach$iv$iv$iv.iterator();
        while ($i$f$mapTo.hasNext()) {
            element$iv$iv = element$iv$iv$iv = $i$f$mapTo.next();
            $i$a$-forEach-CollectionsKt___CollectionsKt$mapNotNullTo$1$iv$iv = false;
            it = (JsonElement)element$iv$iv;
            $i$a$-mapNotNull-JsonPose$idleAnimations$1 = false;
            animString = it.getAsString();
            if (Intrinsics.areEqual((Object)animString, (Object)"look")) {
                v25 = model instanceof HeadedFrame != false ? HeadedFrame.DefaultImpls.singleBoneLook$default((HeadedFrame)model, false, false, false, false, null, null, null, null, null, null, 1023, null) : HeadedFrame.DefaultImpls.singleBoneLook$default(new HeadedFrame(model){
                    @NotNull
                    private final Bone rootPart;
                    @NotNull
                    private final Bone head;
                    {
                        this.rootPart = $model.getRootPart();
                        String[] stringArray = new String[]{"head_ai", "head"};
                        this.head = $model.getPartFallback(stringArray);
                    }

                    @NotNull
                    public Bone getRootPart() {
                        return this.rootPart;
                    }

                    @NotNull
                    public Bone getHead() {
                        return this.head;
                    }

                    @NotNull
                    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
                        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
                    }
                }, false, false, false, false, null, null, null, null, null, null, 1023, null);
            } else {
                Intrinsics.checkNotNullExpressionValue((Object)animString, (String)"animString");
                if (StringsKt.startsWith$default((String)animString, (String)"bedrock", (boolean)false, (int)2, null)) {
                    isVisible = new String[]{","};
                    $this$map$iv = StringsKt.split$default((CharSequence)StringsKt.replace$default((String)StringsKt.replace$default((String)animString, (String)"bedrock(", (String)"", (boolean)false, (int)4, null), (String)")", (String)"", (boolean)false, (int)4, null), (String[])isVisible, (boolean)false, (int)0, (int)6, null);
                    $i$f$map = false;
                    it = $this$map$iv;
                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                    $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        var24_77 = (String)item$iv$iv;
                        var25_78 = destination$iv$iv;
                        $i$a$-map-JsonPose$idleAnimations$1$split$1 = false;
                        var25_78.add(StringsKt.trim((CharSequence)((CharSequence)p0)).toString());
                    }
                    split = (List)destination$iv$iv;
                    v25 = PoseableEntityModel.bedrock$default(model, (String)split.get(0), (String)split.get(1), null, 4, null);
                } else {
                    try {
                        expression = MoLangExtensionsKt.asExpressionLike(animString);
                        v26 = MoLangExtensionsKt.resolveObject(this.runtime, expression).getObj();
                        Intrinsics.checkNotNull(v26, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation<T of bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.JsonPose.idleAnimations$lambda$7, *>");
                        v25 = (StatelessAnimation)v26;
                    }
                    catch (Exception exception) {
                        v25 = null;
                    }
                }
            }
            if (v25 == null) continue;
            it$iv$iv = v25;
            $i$a$-let-CollectionsKt___CollectionsKt$mapNotNullTo$1$1$iv$iv = false;
            destination$iv$iv.add(it$iv$iv);
        }
        $this$toTypedArray$iv /* !! */  = (List)destination$iv$iv;
        $i$f$toTypedArray = false;
        thisCollection$iv = $this$toTypedArray$iv /* !! */ ;
        var30_6.idleAnimations = thisCollection$iv.toArray(new StatelessAnimation[0]);
        v27 = json.get("quirks");
        v28 /* !! */  = v27 != null ? v27.getAsJsonArray() : null;
        if (v28 /* !! */  == null) {
            v28 /* !! */  = new JsonArray();
        }
        $this$toTypedArray$iv /* !! */  = (Iterable)v28 /* !! */ ;
        var30_6 = this;
        $i$f$map = false;
        thisCollection$iv = $this$map$iv;
        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        $i$f$mapTo = false;
        for (T item$iv$iv : $this$mapTo$iv$iv) {
            block47: {
                block49: {
                    block48: {
                        block46: {
                            $i$f$mapTo = (JsonElement)item$iv$iv;
                            var31_39 = destination$iv$iv;
                            $i$a$-map-JsonPose$quirks$1 = false;
                            if (!(json instanceof JsonPrimitive)) break block46;
                            v29 = ((JsonPrimitive)json).getAsString();
                            Intrinsics.checkNotNullExpressionValue((Object)v29, (String)"json.asString");
                            v30 = MoLangExtensionsKt.asExpressionLike(v29).resolveObject(this.runtime).getObj();
                            Intrinsics.checkNotNull(v30, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk<T of bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.JsonPose.quirks$lambda$8>");
                            v31 = (SimpleQuirk<T>)v30;
                            break block47;
                        }
                        Intrinsics.checkNotNull((Object)json, (String)"null cannot be cast to non-null type com.google.gson.JsonObject");
                        (JsonObject)json;
                        GsonExtensionsKt.singularToPluralList$default((JsonObject)json, "animation", null, 2, null);
                        animations = (Function1)new Function1<PoseableEntityState<T>, List<? extends StatefulAnimation<T, ? extends ModelFrame>>>((JsonElement)json, this, model){
                            final /* synthetic */ JsonElement $json;
                            final /* synthetic */ JsonPose<T> this$0;
                            final /* synthetic */ PoseableEntityModel<T> $model;
                            {
                                this.$json = $json;
                                this.this$0 = $receiver;
                                this.$model = $model;
                                super(1);
                            }

                            /*
                             * WARNING - void declaration
                             */
                            @NotNull
                            public final List<StatefulAnimation<T, ? extends ModelFrame>> invoke(@NotNull PoseableEntityState<T> poseableEntityState) {
                                void $this$mapTo$iv$iv;
                                void $this$map$iv;
                                JsonArray jsonArray;
                                Intrinsics.checkNotNullParameter(poseableEntityState, (String)"<anonymous parameter 0>");
                                JsonElement jsonElement = ((JsonObject)this.$json).get("animations");
                                if ((jsonElement != null && (jsonElement = GsonExtensionsKt.normalizeToArray(jsonElement)) != null ? jsonElement.getAsJsonArray() : (jsonArray = null)) == null) {
                                    jsonArray = new JsonArray();
                                }
                                Iterable iterable = (Iterable)jsonArray;
                                JsonPose<T> jsonPose = this.this$0;
                                PoseableEntityModel<T> poseableEntityModel = this.$model;
                                boolean $i$f$map = false;
                                void var6_6 = $this$map$iv;
                                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                boolean $i$f$mapTo = false;
                                for (T item$iv$iv : $this$mapTo$iv$iv) {
                                    StatefulAnimation statefulAnimation;
                                    void animJson;
                                    JsonElement jsonElement2 = (JsonElement)item$iv$iv;
                                    Collection collection = destination$iv$iv;
                                    boolean bl = false;
                                    try {
                                        String string = animJson.getAsString();
                                        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"animJson.asString");
                                        ExpressionLike expr = MoLangExtensionsKt.asExpressionLike(string);
                                        ? obj = MoLangExtensionsKt.resolveObject(jsonPose.getRuntime(), expr).getObj();
                                        Intrinsics.checkNotNull(obj, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation<T of bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.JsonPose.<no name provided>.invoke$lambda$0, *>");
                                        statefulAnimation = (StatefulAnimation)obj;
                                    }
                                    catch (Exception e) {
                                        void $this$mapTo$iv$iv2;
                                        String string = animJson.getAsString();
                                        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"animJson.asString");
                                        String[] stringArray = new String[]{","};
                                        Iterable $this$map$iv2 = StringsKt.split$default((CharSequence)StringsKt.replace$default((String)StringsKt.replace$default((String)string, (String)"bedrock(", (String)"", (boolean)false, (int)4, null), (String)")", (String)"", (boolean)false, (int)4, null), (String[])stringArray, (boolean)false, (int)0, (int)6, null);
                                        boolean $i$f$map2 = false;
                                        Iterable iterable2 = $this$map$iv2;
                                        Collection destination$iv$iv2 = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv2, (int)10));
                                        boolean $i$f$mapTo2 = false;
                                        for (T item$iv$iv2 : $this$mapTo$iv$iv2) {
                                            void p0;
                                            String string2 = (String)item$iv$iv2;
                                            Collection collection2 = destination$iv$iv2;
                                            boolean bl2 = false;
                                            collection2.add(((Object)StringsKt.trim((CharSequence)((CharSequence)p0))).toString());
                                        }
                                        List split = (List)destination$iv$iv2;
                                        statefulAnimation = PoseableEntityModel.bedrockStateful$default(poseableEntityModel, (String)split.get(0), (String)split.get(1), null, 4, null);
                                    }
                                    collection.add(statefulAnimation);
                                }
                                return (List)destination$iv$iv;
                            }
                        };
                        v32 = ((JsonObject)json).get("loopTimes");
                        loopTimes = v32 != null ? v32.getAsInt() : 1;
                        v33 = ((JsonObject)json).get("minSecondsBetweenOccurrences");
                        minSeconds = v33 != null ? v33.getAsFloat() : 8.0f;
                        v34 = ((JsonObject)json).get("maxSecondsBetweenOccurrences");
                        maxSeconds = v34 != null ? v34.getAsFloat() : 30.0f;
                        v35 /* !! */  = ((JsonObject)json).get("condition");
                        if (v35 /* !! */  == null || (v35 /* !! */  = (animString = v35 /* !! */ .getAsString())) == null) break block48;
                        Intrinsics.checkNotNullExpressionValue((Object)v35 /* !! */ , (String)"asString");
                        v35 /* !! */  = MoLangExtensionsKt.asExpressionLike(animString);
                        if (v35 /* !! */  != null) break block49;
                    }
                    v35 /* !! */  = MoLangExtensionsKt.asExpressionLike("true");
                }
                condition /* !! */  = v35 /* !! */ ;
                $i$f$map = TuplesKt.to((Object)Float.valueOf(minSeconds), (Object)Float.valueOf(maxSeconds));
                animString = new IntRange(1, loopTimes);
                v31 = model.quirkMultiple((Pair<Float, Float>)$i$f$map, (IntRange)animString, (Function1)new Function1<PoseableEntityState<T>, Boolean>((ExpressionLike)condition /* !! */ ){
                    final /* synthetic */ ExpressionLike $condition;
                    {
                        this.$condition = $condition;
                        super(1);
                    }

                    @NotNull
                    public final Boolean invoke(@NotNull PoseableEntityState<T> it) {
                        Intrinsics.checkNotNullParameter(it, (String)"it");
                        return MoLangExtensionsKt.resolveBoolean(it.getRuntime(), this.$condition);
                    }
                }, animations);
            }
            var31_39.add(v31);
        }
        var30_6.quirks = (List)destination$iv$iv;
        v36 = this;
        v37 /* !! */  = json.get("namedAnimations");
        if (v37 /* !! */  == null) ** GOTO lbl-1000
        var5_8 = v37 /* !! */ ;
        destination$iv$iv = var5_8;
        var30_6 = v36;
        $i$a$-takeIf-JsonPose$animations$1 = false;
        var31_40 = it instanceof JsonObject;
        v36 = var30_6;
        v37 /* !! */  = var31_40 != false ? var5_8 : null;
        if (v37 /* !! */  != null && (v37 /* !! */  = v37 /* !! */ .getAsJsonObject()) != null) {
            $this$forEach$iv$iv$iv = v37 /* !! */ ;
            var30_6 = v36;
            $i$a$-let-JsonPose$animations$2 = false;
            map = new LinkedHashMap<K, V>();
            for (Object var12_28 : it.entrySet()) {
                Intrinsics.checkNotNullExpressionValue((Object)var12_28, (String)"it.entrySet()");
                key = (String)var12_28.getKey();
                value = (JsonElement)var12_28.getValue();
                maxSeconds = map;
                Intrinsics.checkNotNullExpressionValue((Object)key, (String)"key");
                animString = key;
                v38 = value.getAsString();
                Intrinsics.checkNotNullExpressionValue((Object)v38, (String)"value.asString");
                condition = MoLangExtensionsKt.asExpressionLike(v38);
                maxSeconds.put(animString, condition);
            }
            v39 = map;
            v36 = var30_6;
        } else lbl-1000:
        // 2 sources

        {
            v39 = new LinkedHashMap<K, V>();
        }
        v36.animations = v39;
        v40 = this;
        v41 /* !! */  = json.get("transitions");
        if (v41 /* !! */  == null) ** GOTO lbl-1000
        it = var5_8 = v41 /* !! */ ;
        var30_6 = v40;
        $i$a$-takeIf-JsonPose$transitions$1 = false;
        var31_41 = it instanceof JsonArray;
        v40 = var30_6;
        v41 /* !! */  = var31_41 != false ? var5_8 : null;
        if (v41 /* !! */  != null && (v41 /* !! */  = v41 /* !! */ .getAsJsonArray()) != null) {
            $i$a$-takeIf-JsonPose$transitions$1 = (Iterable)v41 /* !! */ ;
            var30_6 = v40;
            $i$f$map = false;
            $i$a$-let-JsonPose$animations$2 = $this$map$iv;
            destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            $i$f$mapTo = false;
            for (E item$iv$iv : $this$mapTo$iv$iv) {
                value = (JsonElement)item$iv$iv;
                var31_42 = destination$iv$iv;
                $i$a$-map-JsonPose$transitions$2 = false;
                Intrinsics.checkNotNull((Object)it, (String)"null cannot be cast to non-null type com.google.gson.JsonObject");
                (JsonObject)it;
                from = ((JsonObject)it).get("from").getAsString();
                to = ((JsonObject)it).get("to").getAsString();
                v42 = ((JsonObject)it).get("animation").getAsString();
                Intrinsics.checkNotNullExpressionValue((Object)v42, (String)"it.get(\"animation\").asString");
                animation = MoLangExtensionsKt.asExpressionLike(v42);
                Intrinsics.checkNotNullExpressionValue((Object)from, (String)"from");
                Intrinsics.checkNotNullExpressionValue((Object)to, (String)"to");
                var31_42.add(new JsonPoseTransition(from, to, animation));
            }
            v43 = (List)destination$iv$iv;
            v40 = var30_6;
        } else lbl-1000:
        // 2 sources

        {
            v43 = CollectionsKt.emptyList();
        }
        v40.transitions = v43;
    }

    @NotNull
    public final MoLangRuntime getRuntime() {
        return this.runtime;
    }

    @NotNull
    public final ExpressionLike getCondition() {
        return this.condition;
    }

    @NotNull
    public final String getPoseName() {
        return this.poseName;
    }

    @NotNull
    public final List<PoseType> getPoseTypes() {
        return this.poseTypes;
    }

    public final int getTransformTicks() {
        return this.transformTicks;
    }

    @NotNull
    public final ModelPartTransformation[] getTransformedParts() {
        return this.transformedParts;
    }

    @NotNull
    public final StatelessAnimation<T, ? extends ModelFrame>[] getIdleAnimations() {
        return this.idleAnimations;
    }

    @NotNull
    public final List<SimpleQuirk<T>> getQuirks() {
        return this.quirks;
    }

    @NotNull
    public final Map<String, ExpressionLike> getAnimations() {
        return this.animations;
    }

    @NotNull
    public final List<JsonPoseTransition> getTransitions() {
        return this.transitions;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\f\u001a\u00020\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\t\u001a\u0004\b\r\u0010\u000b\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/JsonPose$JsonPoseTransition;", "", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "animation", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getAnimation", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "", "from", "Ljava/lang/String;", "getFrom", "()Ljava/lang/String;", "to", "getTo", "<init>", "(Ljava/lang/String;Ljava/lang/String;Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)V", "common"})
    public static final class JsonPoseTransition {
        @NotNull
        private final String from;
        @NotNull
        private final String to;
        @NotNull
        private final ExpressionLike animation;

        public JsonPoseTransition(@NotNull String from, @NotNull String to, @NotNull ExpressionLike animation) {
            Intrinsics.checkNotNullParameter((Object)from, (String)"from");
            Intrinsics.checkNotNullParameter((Object)to, (String)"to");
            Intrinsics.checkNotNullParameter((Object)animation, (String)"animation");
            this.from = from;
            this.to = to;
            this.animation = animation;
        }

        @NotNull
        public final String getFrom() {
            return this.from;
        }

        @NotNull
        public final String getTo() {
            return this.to;
        }

        @NotNull
        public final ExpressionLike getAnimation() {
            return this.animation;
        }
    }
}

