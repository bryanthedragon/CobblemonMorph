/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.MoLang;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimationAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockBoneTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockBoneValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockInstructionKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockKeyFrameBoneValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockParticleKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockSoundKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.EmptyBoneValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.InterpolationType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.JumpBedrockAnimationKeyFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.MolangBoneValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.SimpleBedrockAnimationKeyFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.Transformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001f\u0010 J\u0015\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0006J'\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u000fH\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001f\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0015H\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u001d\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\u0015\u00a2\u0006\u0004\b\u001d\u0010\u001e\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;", "", "value", "cleanExpression", "(Ljava/lang/String;)Ljava/lang/String;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "typeOfT", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;", "Lcom/google/gson/JsonObject;", "bone", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneTimeline;", "deserializeBoneTimeline", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneTimeline;", "frames", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Transformation;", "transformation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockKeyFrameBoneValue;", "deserializeKeyframe", "(Lcom/google/gson/JsonObject;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Transformation;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockKeyFrameBoneValue;", "Lcom/google/gson/JsonArray;", "array", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/MolangBoneValue;", "deserializeMolangBoneValue", "(Lcom/google/gson/JsonArray;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Transformation;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/MolangBoneValue;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBedrockAnimationAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockAnimationAdapter.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,210:1\n1855#2,2:211\n1855#2,2:213\n1855#2,2:215\n1855#2:217\n1549#2:218\n1620#2,2:219\n1622#2:222\n1856#2:223\n1855#2,2:224\n1549#2:226\n1620#2,3:227\n1#3:221\n*S KotlinDebug\n*F\n+ 1 BedrockAnimationAdapter.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationAdapter\n*L\n37#1:211,2\n40#1:213,2\n64#1:215,2\n83#1:217\n88#1:218\n88#1:219,2\n88#1:222\n83#1:223\n160#1:224,2\n47#1:226\n47#1:227,3\n*E\n"})
public final class BedrockAnimationAdapter
implements JsonDeserializer<BedrockAnimation> {
    @NotNull
    public static final BedrockAnimationAdapter INSTANCE = new BedrockAnimationAdapter();

    private BedrockAnimationAdapter() {
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @NotNull
    public BedrockAnimation deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @NotNull JsonDeserializationContext context) {
        block19: {
            Intrinsics.checkNotNullParameter((Object)json, (String)"json");
            Intrinsics.checkNotNullParameter((Object)typeOfT, (String)"typeOfT");
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            if (!(json instanceof JsonObject)) break block19;
            v0 = ((JsonObject)json).get("animation_length");
            v1 = animationLength = v0 != null ? v0.getAsDouble() : -1.0;
            if (!(animationLength > 0.0)) ** GOTO lbl-1000
            v2 = ((JsonObject)json).get("loop");
            v3 = v2 != null ? v2.getAsBoolean() : false;
            if (v3) {
                v4 = true;
            } else lbl-1000:
            // 2 sources

            {
                v4 = false;
            }
            shouldLoop = v4;
            boneTimelines = new LinkedHashMap<K, V>();
            effects = new ArrayList<E>();
            v5 /* !! */  = ((JsonObject)json).get("bones");
            if (v5 /* !! */  != null && (v5 /* !! */  = v5 /* !! */ .getAsJsonObject()) != null && (v5 /* !! */  = v5 /* !! */ .entrySet()) != null) {
                $this$forEach$iv = (Iterable)v5 /* !! */ ;
                $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    var13_12 = (Map.Entry)element$iv;
                    $i$a$-forEach-BedrockAnimationAdapter$deserialize$1 = false;
                    Intrinsics.checkNotNullExpressionValue((Object)var13_12, (String)"(boneName, timeline)");
                    boneName = (String)var13_12.getKey();
                    timeline = (JsonElement)var13_12.getValue();
                    var17_16 = boneTimelines;
                    Intrinsics.checkNotNullExpressionValue((Object)boneName, (String)"boneName");
                    var18_17 = boneName;
                    v6 = timeline.getAsJsonObject();
                    Intrinsics.checkNotNullExpressionValue((Object)v6, (String)"timeline.asJsonObject");
                    var19_19 = BedrockAnimationAdapter.INSTANCE.deserializeBoneTimeline(v6);
                    var17_16.put(var18_17, var19_19);
                }
            }
            if ((v7 /* !! */  = ((JsonObject)json).get("particle_effects")) != null && (v7 /* !! */  = v7 /* !! */ .getAsJsonObject()) != null && (v7 /* !! */  = v7 /* !! */ .entrySet()) != null) {
                $this$forEach$iv = (Iterable)v7 /* !! */ ;
                $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    var13_12 = (Map.Entry)element$iv;
                    $i$a$-forEach-BedrockAnimationAdapter$deserialize$2 = false;
                    Intrinsics.checkNotNullExpressionValue((Object)var13_12, (String)"(frame, effectJson)");
                    frame = (String)var13_12.getKey();
                    effectJson = (JsonElement)var13_12.getValue();
                    if (effectJson instanceof JsonObject) {
                        effects.add(BedrockAnimationAdapter.deserialize$lambda$2$resolveEffect(frame, (JsonObject)effectJson));
                        continue;
                    }
                    if (!(effectJson instanceof JsonArray)) continue;
                    for (JsonElement effectJsonElement : (JsonArray)effectJson) {
                        Intrinsics.checkNotNull((Object)effectJsonElement, (String)"null cannot be cast to non-null type com.google.gson.JsonObject");
                        effects.add(BedrockAnimationAdapter.deserialize$lambda$2$resolveEffect(frame, (JsonObject)effectJsonElement));
                    }
                }
            }
            if ((v8 /* !! */  = ((JsonObject)json).get("sound_effects")) != null && (v8 /* !! */  = v8 /* !! */ .getAsJsonObject()) != null && (v8 /* !! */  = v8 /* !! */ .entrySet()) != null) {
                $this$forEach$iv = (Iterable)v8 /* !! */ ;
                $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    var13_12 = (Map.Entry)element$iv;
                    $i$a$-forEach-BedrockAnimationAdapter$deserialize$3 = false;
                    Intrinsics.checkNotNullExpressionValue((Object)var13_12, (String)"(frame, effectJson)");
                    frame = (String)var13_12.getKey();
                    effectJson = (JsonElement)var13_12.getValue();
                    if (effectJson instanceof JsonObject) {
                        effects.add(BedrockAnimationAdapter.deserialize$lambda$4$resolveEffect$3(frame, (JsonObject)effectJson));
                        continue;
                    }
                    if (!(effectJson instanceof JsonArray)) continue;
                    for (JsonElement effectJsonElement : (JsonArray)effectJson) {
                        Intrinsics.checkNotNull((Object)effectJsonElement, (String)"null cannot be cast to non-null type com.google.gson.JsonObject");
                        effects.add(BedrockAnimationAdapter.deserialize$lambda$4$resolveEffect$3(frame, (JsonObject)effectJsonElement));
                    }
                }
            }
            if ((v9 /* !! */  = ((JsonObject)json).get("timeline")) != null && (v9 /* !! */  = v9 /* !! */ .getAsJsonObject()) != null && (v9 /* !! */  = v9 /* !! */ .entrySet()) != null) {
                $this$forEach$iv = (Iterable)v9 /* !! */ ;
                $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    var13_12 = (Map.Entry)element$iv;
                    $i$a$-forEach-BedrockAnimationAdapter$deserialize$4 = false;
                    Intrinsics.checkNotNullExpressionValue((Object)var13_12, (String)"(frame, effectJson)");
                    frame = (String)var13_12.getKey();
                    effectJson = (JsonElement)var13_12.getValue();
                    v10 = effects;
                    Intrinsics.checkNotNullExpressionValue((Object)frame, (String)"frame");
                    v11 = Float.parseFloat(frame);
                    if (effectJson instanceof JsonArray) {
                        v12 = ((JsonArray)effectJson).getAsJsonArray();
                        Intrinsics.checkNotNullExpressionValue((Object)v12, (String)"effectJson.asJsonArray");
                        var17_16 = (Iterable)v12;
                        var18_18 = v11;
                        var20_20 = v10;
                        $i$f$map = false;
                        var22_22 = $this$map$iv;
                        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                        $i$f$mapTo = false;
                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                            var27_27 = (JsonElement)item$iv$iv;
                            var28_28 = destination$iv$iv;
                            $i$a$-map-BedrockAnimationAdapter$deserialize$4$1 = false;
                            it = it.getAsString();
                            $i$a$-let-BedrockAnimationAdapter$deserialize$4$1$1 = false;
                            Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                            if (StringsKt.endsWith$default((String)it, (String)";", (boolean)false, (int)2, null)) {
                                v13 = it.substring(0, it.length() - 1);
                                v14 = v13;
                                Intrinsics.checkNotNullExpressionValue((Object)v13, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
                            } else {
                                v14 = it;
                            }
                            Intrinsics.checkNotNullExpressionValue((Object)v14, (String)"it.asString.let { if (it\u2026 it.length - 1) else it }");
                            var28_28.add(MoLangExtensionsKt.asExpression(v14));
                        }
                        var28_28 = (List)destination$iv$iv;
                        v10 = var20_20;
                        v11 = var18_18;
                        v15 = var28_28;
                    } else {
                        v16 = effectJson.getAsString();
                        Intrinsics.checkNotNullExpressionValue((Object)v16, (String)"effectJson.asString");
                        v15 = CollectionsKt.listOf((Object)MoLangExtensionsKt.asExpression(v16));
                    }
                    var32_32 = v15;
                    var33_33 = v11;
                    v10.add(new BedrockInstructionKeyframe(var33_33, var32_32));
                }
            }
            return new BedrockAnimation(shouldLoop, animationLength, effects, boneTimelines);
        }
        throw new IllegalStateException("animation json could not be parsed");
    }

    /*
     * WARNING - void declaration
     */
    private final BedrockBoneTimeline deserializeBoneTimeline(JsonObject bone) {
        BedrockBoneValue bedrockBoneValue;
        BedrockBoneValue rotations;
        BedrockBoneValue positions;
        if (bone.has("position")) {
            if (bone.get("position").isJsonObject()) {
                JsonObject jsonObject = bone.get("position").getAsJsonObject();
                Intrinsics.checkNotNullExpressionValue((Object)jsonObject, (String)"bone[\"position\"].asJsonObject");
                v1 = this.deserializeKeyframe(jsonObject, Transformation.POSITION);
            } else {
                JsonArray jsonArray = bone.get("position").getAsJsonArray();
                Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"bone[\"position\"].asJsonArray");
                v1 = this.deserializeMolangBoneValue(jsonArray, Transformation.POSITION);
            }
        } else {
            v1 = positions = (BedrockBoneValue)EmptyBoneValue.INSTANCE;
        }
        if (bone.has("rotation")) {
            if (bone.get("rotation").isJsonObject()) {
                JsonObject jsonObject = bone.get("rotation").getAsJsonObject();
                Intrinsics.checkNotNullExpressionValue((Object)jsonObject, (String)"bone[\"rotation\"].asJsonObject");
                v4 = this.deserializeKeyframe(jsonObject, Transformation.ROTATION);
            } else {
                JsonArray jsonArray = bone.get("rotation").getAsJsonArray();
                Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"bone[\"rotation\"].asJsonArray");
                v4 = this.deserializeMolangBoneValue(jsonArray, Transformation.ROTATION);
            }
        } else {
            v4 = rotations = (BedrockBoneValue)EmptyBoneValue.INSTANCE;
        }
        if (bone.has("scale")) {
            JsonElement json = bone.get("scale");
            if (json.isJsonObject()) {
                JsonObject jsonObject = json.getAsJsonObject();
                Intrinsics.checkNotNullExpressionValue((Object)jsonObject, (String)"json.asJsonObject");
                bedrockBoneValue = this.deserializeKeyframe(jsonObject, Transformation.SCALE);
            } else if (json.isJsonArray()) {
                JsonArray jsonArray = json.getAsJsonArray();
                Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"json.asJsonArray");
                bedrockBoneValue = this.deserializeMolangBoneValue(jsonArray, Transformation.SCALE);
            } else {
                JsonArray jsonArray;
                String str = json.getAsString();
                JsonArray jsonArray2 = jsonArray = new JsonArray();
                BedrockAnimationAdapter bedrockAnimationAdapter = this;
                boolean bl = false;
                int n = 3;
                int n2 = 0;
                while (n2 < n) {
                    void arr;
                    int it = n2++;
                    boolean bl2 = false;
                    arr.add((JsonElement)new JsonPrimitive(str));
                }
                bedrockBoneValue = bedrockAnimationAdapter.deserializeMolangBoneValue(jsonArray, Transformation.SCALE);
            }
        } else {
            bedrockBoneValue = EmptyBoneValue.INSTANCE;
        }
        BedrockBoneValue scale = bedrockBoneValue;
        return new BedrockBoneTimeline(positions, rotations, scale);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final String cleanExpression(@NotNull String value2) {
        void var2_2;
        String string;
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        if (StringsKt.startsWith$default((String)value2, (String)"+", (boolean)false, (int)2, null)) {
            String string2 = value2.substring(1);
            string = string2;
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).substring(startIndex)");
        } else {
            string = value2;
        }
        String it = string;
        boolean bl = false;
        return StringsKt.replace$default((String)StringsKt.replace$default((String)StringsKt.replace$default((String)StringsKt.replace$default((String)(StringsKt.startsWith$default((String)it, (String)"-(", (boolean)false, (int)2, null) ? StringsKt.replaceFirst$default((String)it, (String)"-(", (String)"-1*(", (boolean)false, (int)4, null) : var2_2), (String)"*+", (String)"*", (boolean)false, (int)4, null), (String)"q.", (String)"query.", (boolean)false, (int)4, null), (String)"camera_rotation(0)", (String)"camera_rotation_x", (boolean)false, (int)4, null), (String)"camera_rotation(1)", (String)"camera_rotation_y", (boolean)false, (int)4, null);
    }

    @NotNull
    public final MolangBoneValue deserializeMolangBoneValue(@NotNull JsonArray array, @NotNull Transformation transformation) {
        Intrinsics.checkNotNullParameter((Object)array, (String)"array");
        Intrinsics.checkNotNullParameter((Object)((Object)transformation), (String)"transformation");
        try {
            String string = array.get(0).getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"array[0].asString");
            Expression expression = MoLang.createParser(this.cleanExpression(string)).parseExpression();
            Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"createParser(cleanExpres\u2026tring)).parseExpression()");
            String string2 = array.get(1).getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"array[1].asString");
            Expression expression2 = MoLang.createParser(this.cleanExpression(string2)).parseExpression();
            Intrinsics.checkNotNullExpressionValue((Object)expression2, (String)"createParser(cleanExpres\u2026tring)).parseExpression()");
            String string3 = array.get(2).getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"array[2].asString");
            Expression expression3 = MoLang.createParser(this.cleanExpression(string3)).parseExpression();
            Intrinsics.checkNotNullExpressionValue((Object)expression3, (String)"createParser(cleanExpres\u2026tring)).parseExpression()");
            return new MolangBoneValue(expression, expression2, expression3, transformation);
        }
        catch (Exception e) {
            Cobblemon.INSTANCE.getLOGGER().error(CollectionsKt.joinToString$default((Iterable)((Iterable)array), null, null, null, (int)0, null, (Function1)deserializeMolangBoneValue.1.INSTANCE, (int)31, null));
            throw e;
        }
    }

    private final BedrockKeyFrameBoneValue deserializeKeyframe(JsonObject frames, Transformation transformation) {
        BedrockKeyFrameBoneValue keyframes = new BedrockKeyFrameBoneValue();
        Set set2 = frames.entrySet();
        Intrinsics.checkNotNullExpressionValue((Object)set2, (String)"frames.entrySet()");
        Iterable $this$forEach$iv = set2;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Object object;
            Object object2;
            Map.Entry entry = (Map.Entry)element$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue((Object)entry, (String)"(time, keyframeJson)");
            String time = (String)entry.getKey();
            JsonElement keyframeJson = (JsonElement)entry.getValue();
            Intrinsics.checkNotNullExpressionValue((Object)time, (String)"time");
            double timeDbl = Double.parseDouble(time);
            if (keyframeJson instanceof JsonObject) {
                MolangBoneValue molangBoneValue;
                Object object3;
                InterpolationType interpolationType;
                String string;
                JsonElement jsonElement = ((JsonObject)keyframeJson).get("lerp_mode");
                object2 = jsonElement != null ? jsonElement.getAsString() : null;
                String string2 = object2;
                if (string2 == null) {
                    string = "linear";
                } else {
                    Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"keyframeJson.get(\"lerp_m\u2026e\")?.asString ?: \"linear\"");
                    string = object2;
                }
                InterpolationType interpolationType2 = interpolationType = Intrinsics.areEqual((Object)string, (Object)"catmullrom") ? InterpolationType.SMOOTH : InterpolationType.LINEAR;
                if (((JsonObject)keyframeJson).has("post")) {
                    JsonElement post2 = ((JsonObject)keyframeJson).get("post");
                    object2 = keyframes;
                    object = timeDbl;
                    JsonElement jsonElement2 = ((JsonObject)keyframeJson).get("pre");
                    if (jsonElement2 == null || (jsonElement2 = jsonElement2.getAsJsonArray()) == null) {
                        jsonElement2 = post2.getAsJsonArray();
                    }
                    Intrinsics.checkNotNullExpressionValue((Object)jsonElement2, (String)"keyframeJson[\"pre\"]?.asJ\u2026Array ?: post.asJsonArray");
                    object3 = INSTANCE.deserializeMolangBoneValue((JsonArray)jsonElement2, transformation);
                    JsonArray jsonArray = post2.getAsJsonArray();
                    Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"post.asJsonArray");
                    molangBoneValue = INSTANCE.deserializeMolangBoneValue(jsonArray, transformation);
                    object3 = new JumpBedrockAnimationKeyFrame(timeDbl, transformation, interpolationType, (MolangBoneValue)object3, molangBoneValue);
                    object2.put(object, object3);
                    continue;
                }
                if (((JsonObject)keyframeJson).has("pre")) {
                    JsonElement pre = ((JsonObject)keyframeJson).get("pre");
                    object2 = keyframes;
                    object = timeDbl;
                    JsonArray jsonArray = pre.getAsJsonArray();
                    Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"pre.asJsonArray");
                    object3 = INSTANCE.deserializeMolangBoneValue(jsonArray, transformation);
                    JsonElement jsonElement3 = ((JsonObject)keyframeJson).get("post");
                    if (jsonElement3 == null || (jsonElement3 = jsonElement3.getAsJsonArray()) == null) {
                        jsonElement3 = pre.getAsJsonArray();
                    }
                    Intrinsics.checkNotNullExpressionValue((Object)jsonElement3, (String)"keyframeJson[\"post\"]?.as\u2026nArray ?: pre.asJsonArray");
                    molangBoneValue = INSTANCE.deserializeMolangBoneValue((JsonArray)jsonElement3, transformation);
                    object3 = new JumpBedrockAnimationKeyFrame(timeDbl, transformation, interpolationType, (MolangBoneValue)object3, molangBoneValue);
                    object2.put(object, object3);
                    continue;
                }
                throw new IllegalStateException("transformation data ('post') could not be found");
            }
            if (keyframeJson instanceof JsonArray) {
                Map map = keyframes;
                Double d = timeDbl;
                object2 = INSTANCE.deserializeMolangBoneValue((JsonArray)keyframeJson, transformation);
                object = InterpolationType.LINEAR;
                object2 = new SimpleBedrockAnimationKeyFrame(timeDbl, transformation, (InterpolationType)((Object)object), (MolangBoneValue)object2);
                map.put(d, object2);
                continue;
            }
            throw new IllegalStateException("keyframe json could not be parsed");
        }
        return keyframes;
    }

    /*
     * WARNING - void declaration
     */
    private static final BedrockParticleKeyframe deserialize$lambda$2$resolveEffect(String frame, JsonObject jsonObject) {
        List list;
        String[] stringArray;
        String string = jsonObject.get("effect").getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"jsonObject.get(\"effect\").asString");
        ResourceLocation effectId = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(string, null, 1, null);
        BedrockParticleEffect bedrockParticleEffect = BedrockParticleEffectRepository.INSTANCE.getEffect(effectId);
        if (bedrockParticleEffect == null) {
            throw new IllegalArgumentException("Unrecognized particle effect " + effectId + " referenced in animation. Maybe your particle effect isn't named correctly inside the effect file?");
        }
        BedrockParticleEffect effect = bedrockParticleEffect;
        JsonElement jsonElement = jsonObject.get("locator");
        String string2 = jsonElement != null ? jsonElement.getAsString() : null;
        if (string2 == null) {
            string2 = "root";
        }
        String locator = string2;
        Intrinsics.checkNotNullExpressionValue((Object)frame, (String)"frame");
        float seconds = Float.parseFloat(frame);
        Object object = jsonObject.get("pre_effect_script");
        if (object != null && (object = object.getAsString()) != null && (object = StringsKt.split$default((CharSequence)((CharSequence)object), (String[])(stringArray = new String[]{"\n"}), (boolean)false, (int)0, (int)6, null)) != null) {
            void $this$mapTo$iv$iv;
            Iterable $this$map$iv = (Iterable)object;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                String string3 = (String)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(MoLang.createParser((String)it).parseExpression());
            }
            list = (List)destination$iv$iv;
        } else {
            list = CollectionsKt.emptyList();
        }
        List scripts = list;
        return new BedrockParticleKeyframe(seconds, effect, locator, scripts);
    }

    private static final BedrockSoundKeyframe deserialize$lambda$4$resolveEffect$3(String frame, JsonObject jsonObject) {
        String string = jsonObject.get("effect").getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"jsonObject.get(\"effect\").asString");
        ResourceLocation effectId = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(string, null, 1, null);
        Intrinsics.checkNotNullExpressionValue((Object)frame, (String)"frame");
        float seconds = Float.parseFloat(frame);
        return new BedrockSoundKeyframe(seconds, effectId);
    }
}

