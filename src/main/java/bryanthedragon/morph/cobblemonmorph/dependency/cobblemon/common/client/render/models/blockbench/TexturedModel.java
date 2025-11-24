/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.annotations.SerializedName
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeDeformation
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.LayerDefinition
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.Cube;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.LocatorBone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelBone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelGeometry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.util.adapters.LocatorBoneAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u0007\u00a2\u0006\u0004\b \u0010!J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J9\u0010\f\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\b\u0010\n\u001a\u0004\u0018\u00010\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0004\b\f\u0010\rJ/\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\u00132\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e2\u0006\u0010\u0012\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u000f8\u0006X\u0087D\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\"\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001a8\u0006X\u0087\u0004\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/TexturedModel;", "", "", "isForLivingEntityRenderer", "Lnet/minecraft/client/model/geom/builders/LayerDefinition;", "create", "(Z)Lnet/minecraft/client/model/geom/builders/LayerDefinition;", "", "u", "v", "textureWidth", "textureHeight", "createWithUvOverride", "(ZIILjava/lang/Integer;Ljava/lang/Integer;)Lnet/minecraft/client/model/geom/builders/LayerDefinition;", "", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/ModelBone;", "boneMap", "bone", "", "resolveParentsFromRoot", "(Ljava/util/Map;Lcom/cobblemon/mod/common/client/render/models/blockbench/ModelBone;)Ljava/util/Set;", "formatVersion", "Ljava/lang/String;", "getFormatVersion", "()Ljava/lang/String;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/ModelGeometry;", "geometry", "Ljava/util/List;", "getGeometry", "()Ljava/util/List;", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nTexturedModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TexturedModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/TexturedModel\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,345:1\n1603#2,9:346\n1855#2:355\n1856#2:361\n1612#2:362\n1864#2,3:363\n125#3:356\n152#3,3:357\n1#4:360\n*S KotlinDebug\n*F\n+ 1 TexturedModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/TexturedModel\n*L\n163#1:346,9\n163#1:355\n163#1:361\n163#1:362\n266#1:363,3\n165#1:356\n165#1:357,3\n163#1:360\n*E\n"})
public final class TexturedModel {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @SerializedName(value="format_version")
    @NotNull
    private final String formatVersion;
    @SerializedName(value="minecraft:geometry")
    @Nullable
    private final List<ModelGeometry> geometry;
    private static final Gson GSON = new GsonBuilder().setLenient().registerTypeAdapter((Type)((Object)LocatorBone.class), (Object)LocatorBoneAdapter.INSTANCE).create();

    public TexturedModel() {
        this.formatVersion = "0";
    }

    @NotNull
    public final String getFormatVersion() {
        return this.formatVersion;
    }

    @Nullable
    public final List<ModelGeometry> getGeometry() {
        return this.geometry;
    }

    @NotNull
    public final LayerDefinition create(boolean isForLivingEntityRenderer) {
        return this.createWithUvOverride(isForLivingEntityRenderer, 0, 0, null, null);
    }

    @NotNull
    public final Set<ModelBone> resolveParentsFromRoot(@NotNull Map<String, ModelBone> boneMap, @NotNull ModelBone bone) {
        Set set2;
        Intrinsics.checkNotNullParameter(boneMap, (String)"boneMap");
        Intrinsics.checkNotNullParameter((Object)bone, (String)"bone");
        if (bone.getParent() == null) {
            set2 = SetsKt.emptySet();
        } else {
            ModelBone modelBone = boneMap.get(bone.getParent());
            if (modelBone == null) {
                return SetsKt.emptySet();
            }
            ModelBone parent = modelBone;
            set2 = SetsKt.plus(this.resolveParentsFromRoot(boneMap, parent), (Object)bone);
        }
        return set2;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final LayerDefinition createWithUvOverride(boolean isForLivingEntityRenderer, int u, int v, @Nullable Integer textureWidth, @Nullable Integer textureHeight) {
        MeshDefinition modelData = new MeshDefinition();
        HashMap parts = new HashMap();
        HashMap bones = new HashMap();
        try {
            void $this$createWithUvOverride_u24lambda_u242;
            CubeListBuilder $this$map$iv;
            void $this$mapNotNullTo$iv$iv;
            List<ModelGeometry> list = this.geometry;
            Intrinsics.checkNotNull(list);
            ModelGeometry geometry = list.get(0);
            List<ModelBone> list2 = geometry.getBones();
            Intrinsics.checkNotNull(list2);
            List geometryBones = CollectionsKt.toMutableList((Collection)list2);
            PartDefinition parentPart = null;
            Collection collection = geometryBones;
            Object $this$mapNotNull$iv = geometryBones;
            boolean $i$f$mapNotNull22 = false;
            Iterable iterable = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv$iv$iv.iterator();
            while (iterator.hasNext()) {
                List list3;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = iterator.next();
                boolean bl = false;
                ModelBone bone = (ModelBone)element$iv$iv;
                boolean bl2 = false;
                if (bone.getLocators() == null) {
                    list3 = null;
                } else {
                    void $this$mapTo$iv$iv;
                    CubeListBuilder locators;
                    $this$map$iv = locators;
                    boolean $i$f$map = false;
                    CubeListBuilder cubeListBuilder = $this$map$iv;
                    Collection destination$iv$iv2 = new ArrayList($this$map$iv.size());
                    boolean $i$f$mapTo = false;
                    Iterator iterator2 = $this$mapTo$iv$iv.entrySet().iterator();
                    while (iterator2.hasNext()) {
                        Map.Entry item$iv$iv;
                        Map.Entry entry = item$iv$iv = iterator2.next();
                        Collection collection2 = destination$iv$iv2;
                        boolean bl3 = false;
                        String name = (String)entry.getKey();
                        LocatorBone locator = (LocatorBone)entry.getValue();
                        ModelBone locatorBone = new ModelBone();
                        locatorBone.setName("locator_" + name);
                        locatorBone.setParent(bone.getName());
                        locatorBone.setPivot(locator.getOffset());
                        locatorBone.setRotation(locator.getRotation());
                        collection2.add(locatorBone);
                    }
                    list3 = (List)destination$iv$iv2;
                }
                if (list3 == null) continue;
                List it$iv$iv = list3;
                boolean bl4 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            Object $i$f$mapNotNull22 = $this$mapNotNull$iv = new ModelBone();
            Collection collection3 = CollectionsKt.flatten((Iterable)((List)destination$iv$iv));
            boolean bl = false;
            $this$createWithUvOverride_u24lambda_u242.setName("locator_root");
            CollectionsKt.addAll((Collection)collection, (Iterable)CollectionsKt.plus((Collection)collection3, (Object)$this$mapNotNull$iv));
            for (ModelBone bone : geometryBones) {
                PartDefinition element$iv$iv;
                String subPart;
                Object pivot;
                PartDefinition partDefinition;
                ((Map)bones).put(bone.getName(), bone);
                if (bone.getParent() != null) {
                    Object v2 = ((Map)parts).get(bone.getParent());
                    Intrinsics.checkNotNull(v2);
                    partDefinition = (PartDefinition)v2;
                } else {
                    PartDefinition partDefinition2 = modelData.m_171576_();
                    partDefinition = partDefinition2;
                    Intrinsics.checkNotNullExpressionValue((Object)partDefinition2, (String)"modelData.root");
                }
                parentPart = partDefinition;
                List<Float> boneRotation = bone.getRotation();
                PartPose modelTransform = null;
                if (bone.getParent() == null) {
                    PartPose partPose = PartPose.m_171419_((float)0.0f, (float)(isForLivingEntityRenderer ? 24.0f : 0.0f), (float)0.0f);
                    Intrinsics.checkNotNullExpressionValue((Object)partPose, (String)"pivot(0F, if (isForLivin\u2026enderer) 24F else 0F, 0F)");
                    modelTransform = partPose;
                } else if (boneRotation != null) {
                    Object v3 = ((Map)bones).get(bone.getParent());
                    Intrinsics.checkNotNull(v3);
                    float f = -(((Number)((ModelBone)v3).getPivot().get(0)).floatValue() - ((Number)bone.getPivot().get(0)).floatValue());
                    Object v4 = ((Map)bones).get(bone.getParent());
                    Intrinsics.checkNotNull(v4);
                    float f2 = ((Number)((ModelBone)v4).getPivot().get(1)).floatValue() - ((Number)bone.getPivot().get(1)).floatValue();
                    Object v5 = ((Map)bones).get(bone.getParent());
                    Intrinsics.checkNotNull(v5);
                    PartPose partPose = PartPose.m_171423_((float)f, (float)f2, (float)(-(((Number)((ModelBone)v5).getPivot().get(2)).floatValue() - ((Number)bone.getPivot().get(2)).floatValue())), (float)((float)Math.toRadians(((Number)boneRotation.get(0)).floatValue())), (float)((float)Math.toRadians(((Number)boneRotation.get(1)).floatValue())), (float)((float)Math.toRadians(((Number)boneRotation.get(2)).floatValue())));
                    Intrinsics.checkNotNullExpressionValue((Object)partPose, (String)"of(\n                    \u2026                        )");
                    modelTransform = partPose;
                } else {
                    Object v6 = ((Map)bones).get(bone.getParent());
                    Intrinsics.checkNotNull(v6);
                    float f = -(((Number)((ModelBone)v6).getPivot().get(0)).floatValue() - ((Number)bone.getPivot().get(0)).floatValue());
                    Object v7 = ((Map)bones).get(bone.getParent());
                    Intrinsics.checkNotNull(v7);
                    float f3 = ((Number)((ModelBone)v7).getPivot().get(1)).floatValue() - ((Number)bone.getPivot().get(1)).floatValue();
                    Object v8 = ((Map)bones).get(bone.getParent());
                    Intrinsics.checkNotNull(v8);
                    PartPose partPose = PartPose.m_171419_((float)f, (float)f3, (float)(-(((Number)((ModelBone)v8).getPivot().get(2)).floatValue() - ((Number)bone.getPivot().get(2)).floatValue())));
                    Intrinsics.checkNotNullExpressionValue((Object)partPose, (String)"pivot(\n                 \u2026                        )");
                    modelTransform = partPose;
                }
                CubeListBuilder modelPart = CubeListBuilder.m_171558_();
                List subParts = new ArrayList();
                List modelTransforms = new ArrayList();
                List<Cube> boneCubes = bone.getCubes();
                if (boneCubes != null) {
                    pivot = null;
                    subPart = null;
                    for (Cube cube : boneCubes) {
                        Object object;
                        if (cube.getRotation() != null) {
                            CubeListBuilder cubeListBuilder = CubeListBuilder.m_171558_();
                            v20 = cubeListBuilder;
                            Intrinsics.checkNotNullExpressionValue((Object)cubeListBuilder, (String)"create()");
                        } else {
                            Intrinsics.checkNotNullExpressionValue((Object)modelPart, (String)"modelPart");
                            v20 = subPart = modelPart;
                        }
                        if ((object = cube.getPivot()) == null) {
                            object = pivot = bone.getPivot();
                        }
                        if (cube.getUv() != null) {
                            subPart.m_171514_(((Number)cube.getUv().get(0)).intValue() + u, ((Number)cube.getUv().get(1)).intValue() + v);
                        }
                        cube.getMirror();
                        if (cube.getMirror()) {
                            subPart.m_171480_();
                        }
                        if (cube.getSize() != null && cube.getOrigin() != null) {
                            Float f = cube.getInflate();
                            subPart.m_171488_(((Number)cube.getOrigin().get(0)).floatValue() - ((Number)pivot.get(0)).floatValue(), -(((Number)cube.getOrigin().get(1)).floatValue() - ((Number)pivot.get(1)).floatValue() + ((Number)cube.getSize().get(1)).floatValue()), ((Number)cube.getOrigin().get(2)).floatValue() - ((Number)pivot.get(2)).floatValue(), ((Number)cube.getSize().get(0)).floatValue(), ((Number)cube.getSize().get(1)).floatValue(), ((Number)cube.getSize().get(2)).floatValue(), new CubeDeformation(f != null ? f.floatValue() : 0.0f));
                        }
                        cube.getMirror();
                        if (cube.getMirror()) {
                            subPart.m_171555_(false);
                        }
                        if (Intrinsics.areEqual((Object)subPart, (Object)modelPart)) continue;
                        float f = ((Number)bone.getPivot().get(0)).floatValue();
                        List<Float> list4 = cube.getPivot();
                        Intrinsics.checkNotNull(list4);
                        float f4 = -(f - ((Number)list4.get(0)).floatValue());
                        float f5 = ((Number)bone.getPivot().get(1)).floatValue() - ((Number)cube.getPivot().get(1)).floatValue();
                        float f6 = -(((Number)bone.getPivot().get(2)).floatValue() - ((Number)cube.getPivot().get(2)).floatValue());
                        List<Float> list5 = cube.getRotation();
                        Intrinsics.checkNotNull(list5);
                        PartPose partPose = PartPose.m_171423_((float)f4, (float)f5, (float)f6, (float)((float)Math.toRadians(((Number)list5.get(0)).floatValue())), (float)((float)Math.toRadians(((Number)cube.getRotation().get(1)).floatValue())), (float)((float)Math.toRadians(((Number)cube.getRotation().get(2)).floatValue())));
                        Intrinsics.checkNotNullExpressionValue((Object)partPose, (String)"of(\n                    \u2026                        )");
                        modelTransforms.add(partPose);
                        subParts.add(subPart);
                    }
                }
                pivot = parts;
                subPart = bone.getName();
                Intrinsics.checkNotNullExpressionValue((Object)parentPart.m_171599_(bone.getName(), modelPart, modelTransform), (String)"parentPart.addChild(\n   \u2026ansform\n                )");
                pivot.put(subPart, element$iv$iv);
                int counter = 0;
                Iterable $this$forEachIndexed$iv = subParts;
                boolean $i$f$forEachIndexed = false;
                int index$iv = 0;
                for (Object item$iv : $this$forEachIndexed$iv) {
                    void part;
                    int n;
                    if ((n = index$iv++) < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    $this$map$iv = (CubeListBuilder)item$iv;
                    int index = n;
                    boolean bl5 = false;
                    Object v9 = parts.get(bone.getName());
                    Intrinsics.checkNotNull(v9);
                    int n2 = counter;
                    counter = n2 + 1;
                    ((PartDefinition)v9).m_171599_(bone.getName() + n2, (CubeListBuilder)part, (PartPose)modelTransforms.get(index));
                }
            }
            Integer n = textureWidth;
            Integer n3 = textureHeight;
            LayerDefinition layerDefinition = LayerDefinition.m_171565_((MeshDefinition)modelData, (int)(n != null ? n.intValue() : geometry.getDescription().getTextureWidth()), (int)(n3 != null ? n3.intValue() : geometry.getDescription().getTextureHeight()));
            Intrinsics.checkNotNullExpressionValue((Object)layerDefinition, (String)"of(\n                mode\u2026xtureHeight\n            )");
            return layerDefinition;
        }
        catch (Exception e) {
            if (this.geometry != null) {
                throw new IllegalArgumentException("Error creating TexturedModelData with identifier " + this.geometry.get(0).getDescription().getIdentifier(), e);
            }
            throw new IllegalArgumentException("Error creating TexturedModelData", e);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001f\u0010\t\u001a\n \b*\u0004\u0018\u00010\u00070\u00078\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/TexturedModel$Companion;", "", "", "json", "Lcom/cobblemon/mod/common/client/render/models/blockbench/TexturedModel;", "from", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/client/render/models/blockbench/TexturedModel;", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "GSON", "Lcom/google/gson/Gson;", "getGSON", "()Lcom/google/gson/Gson;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final Gson getGSON() {
            return GSON;
        }

        @NotNull
        public final TexturedModel from(@NotNull String json) {
            Intrinsics.checkNotNullParameter((Object)json, (String)"json");
            try {
                Object object = this.getGSON().fromJson(json, TexturedModel.class);
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"GSON.fromJson(json, TexturedModel::class.java)");
                return (TexturedModel)object;
            }
            catch (Exception exception) {
                throw new IllegalStateException("Issue loading pokemon geo: " + json, exception);
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

