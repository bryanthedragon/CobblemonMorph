/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  kotlin.Metadata
 *  kotlin.NotImplementedError
 *  kotlin.Pair
 *  kotlin.Triple
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$ObjectRef
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.CharsKt
 *  kotlin.text.StringsKt
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Vector4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.NumberExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.AnimatedParticleUVMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEmitter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BezierChainMoLangCurve;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BezierMoLangCurve;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BoxParticleEmitterShape;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.CatmullRomMoLangCurve;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.CustomMotionDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.CustomViewDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DirectionX;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DirectionY;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DirectionZ;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DiscParticleEmitterShape;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DynamicParticleMotion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DynamicParticleRotation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EmitterXYPlane;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EmitterXZPlane;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EmitterYZPlane;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EntityBoundingBoxParticleEmitterShape;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EventParticleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EventSoundEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EventTriggerTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ExpressionEmitterLifetime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ExpressionParticleTinting;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.FromMotionViewDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.GradientParticleTinting;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.InstantParticleEmitterRate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.InwardsMotionDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LinearMoLangCurve;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LookAtDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LookAtXYZ;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LookAtY;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LoopingEmitterLifetime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LoopingTravelDistanceEventTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.OnceEmitterLifetime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.OutwardsMotionDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParametricParticleMotion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParametricParticleRotation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCollision;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMaterial;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotionDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleRotation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleSpace;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleUVMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.PointParticleEmitterShape;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.RotateXYZCameraMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.RotateYCameraMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.SimpleEventTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.SphereParticleEmitterShape;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.StaticParticleMotion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.StaticParticleUVMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.SteadyParticleEmitterRate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.GsonExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
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
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/particle/SnowstormParticleReader;", "", "Lcom/google/gson/JsonObject;", "json", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "loadEffect", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "", "hex", "Lorg/joml/Vector4f;", "parseHex", "(Ljava/lang/String;)Lorg/joml/Vector4f;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSnowstormParticleReader.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SnowstormParticleReader.kt\ncom/cobblemon/mod/common/particle/SnowstormParticleReader\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,449:1\n1#2:450\n1#2:477\n1603#3,9:451\n1855#3:460\n1549#3:461\n1620#3,3:462\n1549#3:465\n1620#3,3:466\n1549#3:469\n1620#3,3:470\n1549#3:473\n1620#3,3:474\n1856#3:478\n1612#3:479\n1855#3,2:480\n766#3:482\n857#3,2:483\n1549#3:485\n1620#3,3:486\n766#3:489\n857#3,2:490\n1549#3:492\n1620#3,3:493\n766#3:496\n857#3,2:497\n1549#3:499\n1620#3,3:500\n766#3:503\n857#3,2:504\n1549#3:506\n1620#3,3:507\n1549#3:510\n1620#3,3:511\n1549#3:514\n1620#3,3:515\n1549#3:518\n1620#3,3:519\n1549#3:522\n1620#3,3:523\n1549#3:526\n1620#3,3:527\n1549#3:530\n1620#3,3:531\n1549#3:534\n1620#3,3:535\n1549#3:538\n1620#3,3:539\n1549#3:542\n1620#3,3:543\n1549#3:546\n1620#3,3:547\n1549#3:550\n1620#3,3:551\n1549#3:554\n1620#3,3:555\n1549#3:558\n1620#3,3:559\n1549#3:562\n1620#3,3:563\n1549#3:566\n1620#3,2:567\n1549#3:569\n1620#3,3:570\n1622#3:573\n1549#3:574\n1620#3,3:575\n1549#3:578\n1620#3,3:579\n1549#3:582\n1620#3,2:583\n1549#3:585\n1620#3,3:586\n1622#3:589\n1549#3:590\n1620#3,2:591\n1549#3:593\n1620#3,3:594\n1622#3:597\n1549#3:598\n1620#3,2:599\n1549#3:601\n1620#3,3:602\n1622#3:605\n1549#3:606\n1620#3,3:607\n*S KotlinDebug\n*F\n+ 1 SnowstormParticleReader.kt\ncom/cobblemon/mod/common/particle/SnowstormParticleReader\n*L\n71#1:477\n71#1:451,9\n71#1:460\n76#1:461\n76#1:462,3\n85#1:465\n85#1:466,3\n98#1:469\n98#1:470,3\n110#1:473\n110#1:474,3\n71#1:478\n71#1:479\n118#1:480,2\n134#1:482\n134#1:483,2\n134#1:485\n134#1:486,3\n135#1:489\n135#1:490,2\n135#1:492\n135#1:493,3\n136#1:496\n136#1:497,2\n136#1:499\n136#1:500,3\n137#1:503\n137#1:504,2\n137#1:506\n137#1:507,3\n185#1:510\n185#1:511,3\n189#1:514\n189#1:515,3\n198#1:518\n198#1:519,3\n201#1:522\n201#1:523,3\n220#1:526\n220#1:527,3\n222#1:530\n222#1:531,3\n236#1:534\n236#1:535,3\n246#1:538\n246#1:539,3\n248#1:542\n248#1:543,3\n260#1:546\n260#1:547,3\n328#1:550\n328#1:551,3\n333#1:554\n333#1:555,3\n368#1:558\n368#1:559,3\n369#1:562\n369#1:563,3\n370#1:566\n370#1:567,2\n371#1:569\n371#1:570,3\n370#1:573\n380#1:574\n380#1:575,3\n381#1:578\n381#1:579,3\n382#1:582\n382#1:583,2\n383#1:585\n383#1:586,3\n382#1:589\n385#1:590\n385#1:591,2\n388#1:593\n388#1:594,3\n385#1:597\n391#1:598\n391#1:599,2\n392#1:601\n392#1:602,3\n391#1:605\n172#1:606\n172#1:607,3\n*E\n"})
public final class SnowstormParticleReader {
    @NotNull
    public static final SnowstormParticleReader INSTANCE = new SnowstormParticleReader();

    private SnowstormParticleReader() {
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    @NotNull
    public final BedrockParticleEffect loadEffect(@NotNull JsonObject json) {
        block265: {
            block264: {
                block263: {
                    block262: {
                        block261: {
                            block260: {
                                block259: {
                                    block258: {
                                        block257: {
                                            block256: {
                                                block255: {
                                                    block248: {
                                                        block254: {
                                                            block253: {
                                                                block252: {
                                                                    block251: {
                                                                        block250: {
                                                                            block249: {
                                                                                block246: {
                                                                                    block245: {
                                                                                        block244: {
                                                                                            block241: {
                                                                                                Intrinsics.checkNotNullParameter((Object)json, (String)"json");
                                                                                                effectJson = json.get("particle_effect").getAsJsonObject();
                                                                                                descJson = effectJson.get("description").getAsJsonObject();
                                                                                                basicRenderParametersJson = descJson.get("basic_render_parameters").getAsJsonObject();
                                                                                                v0 = var7_5 /* !! */  = effectJson.get("curves");
                                                                                                var6_6 /* !! */  = v0 != null ? v0.getAsJsonObject() : null;
                                                                                                v1 = var6_6 /* !! */ ;
                                                                                                if (v1 == null) {
                                                                                                    v1 = new JsonObject();
                                                                                                }
                                                                                                curvesJson = v1;
                                                                                                v2 = var8_8 /* !! */  = effectJson.get("components");
                                                                                                var7_5 /* !! */  = v2 != null ? v2.getAsJsonObject() : null;
                                                                                                v3 = var7_5 /* !! */ ;
                                                                                                if (v3 == null) {
                                                                                                    v3 = new JsonObject();
                                                                                                }
                                                                                                componentsJson = v3;
                                                                                                v4 = var9_9 /* !! */  = componentsJson.get("minecraft:emitter_initialization");
                                                                                                var8_8 /* !! */  = v4 != null ? v4.getAsJsonObject() : null;
                                                                                                v5 = var8_8 /* !! */ ;
                                                                                                if (v5 == null) {
                                                                                                    v5 = new JsonObject();
                                                                                                }
                                                                                                emitterInitializationJson = v5;
                                                                                                v6 = var10_10 = componentsJson.get("minecraft:particle_initialization");
                                                                                                var9_9 /* !! */  = v6 != null ? v6.getAsJsonObject() : null;
                                                                                                v7 = var9_9 /* !! */ ;
                                                                                                if (v7 == null) {
                                                                                                    v7 = new JsonObject();
                                                                                                }
                                                                                                particleInitializationJson = v7;
                                                                                                v8 = var10_10 = componentsJson.get("minecraft:emitter_rate_steady");
                                                                                                steadyRateJson = v8 != null ? v8.getAsJsonObject() : null;
                                                                                                v9 = var11_11 = componentsJson.get("minecraft:emitter_rate_instant");
                                                                                                instantRateJson = v9 != null ? v9.getAsJsonObject() : null;
                                                                                                v10 = var12_12 = componentsJson.get("minecraft:emitter_lifetime_once");
                                                                                                emitterLifetimeOnceJson = v10 != null ? v10.getAsJsonObject() : null;
                                                                                                v11 = var13_13 = componentsJson.get("minecraft:emitter_lifetime_looping");
                                                                                                emitterLifetimeLoopingJson = v11 != null ? v11.getAsJsonObject() : null;
                                                                                                v12 = var14_14 = componentsJson.get("minecraft:emitter_lifetime_expression");
                                                                                                emitterLifetimeExpressionJson = v12 != null ? v12.getAsJsonObject() : null;
                                                                                                v13 = var15_15 = componentsJson.get("minecraft:emitter_shape_point");
                                                                                                emitterShapePointJson = v13 != null ? v13.getAsJsonObject() : null;
                                                                                                v14 = var16_16 = componentsJson.get("minecraft:emitter_shape_sphere");
                                                                                                emitterShapeSphereJson = v14 != null ? v14.getAsJsonObject() : null;
                                                                                                v15 = var17_17 = componentsJson.get("minecraft:emitter_shape_disc");
                                                                                                emitterShapeDiscJson = v15 != null ? v15.getAsJsonObject() : null;
                                                                                                v16 = var18_18 = componentsJson.get("minecraft:emitter_shape_box");
                                                                                                emitterShapeBoxJson = v16 != null ? v16.getAsJsonObject() : null;
                                                                                                v17 = var19_19 = componentsJson.get("minecraft:emitter_shape_entity_aabb");
                                                                                                emitterShapeEntityBoundingBoxJson = v17 != null ? v17.getAsJsonObject() : null;
                                                                                                v18 = var20_20 = componentsJson.get("minecraft:emitter_lifetime_events");
                                                                                                emitterLifetimeEventsJson = v18 != null ? v18.getAsJsonObject() : null;
                                                                                                v19 = var21_21 = componentsJson.get("minecraft:particle_motion_dynamic");
                                                                                                dynamicMotionJson = v19 != null ? v19.getAsJsonObject() : null;
                                                                                                v20 = var22_22 = componentsJson.get("minecraft:particle_motion_parametric");
                                                                                                parametricMotionJson = v20 != null ? v20.getAsJsonObject() : null;
                                                                                                particleAppearanceJson = componentsJson.get("minecraft:particle_appearance_billboard").getAsJsonObject();
                                                                                                v21 = var24_23 = particleAppearanceJson.get("size");
                                                                                                sizeJson = v21 != null ? v21.getAsJsonArray() : null;
                                                                                                v22 = var25_25 = componentsJson.get("minecraft:particle_lifetime_expression");
                                                                                                particleLifetimeJson = v22 != null ? v22.getAsJsonObject() : null;
                                                                                                var26_26 = particleAppearanceJson.get("facing_camera_mode");
                                                                                                v23 = var26_26;
                                                                                                if (v23 == null) {
                                                                                                    v23 = cameraModeJson = (JsonElement)new JsonPrimitive("rotate_xyz");
                                                                                                }
                                                                                                if ((v24 = (var27_27 = particleAppearanceJson.get("direction"))) == null) {
                                                                                                    v24 = null;
                                                                                                }
                                                                                                particleDirectionJson = v24;
                                                                                                uvModeJson = particleAppearanceJson.get("uv").getAsJsonObject();
                                                                                                v25 = var29_28 = componentsJson.get("minecraft:particle_initial_spin");
                                                                                                particleInitialSpinJson = v25 != null ? v25.getAsJsonObject() : null;
                                                                                                v26 = var30_30 = componentsJson.get("minecraft:particle_appearance_tinting");
                                                                                                v27 = tintingJson = v26 != null ? v26.getAsJsonObject() : null;
                                                                                                colourJson = v27 != null ? v27.get("color") : null;
                                                                                                v28 = var32_31 = componentsJson.get("minecraft:particle_motion_collision");
                                                                                                collisionJson = v28 != null ? v28.getAsJsonObject() : null;
                                                                                                v29 = var33_33 = componentsJson.get("minecraft:emitter_local_space");
                                                                                                spaceJson = v29 != null ? v29.getAsJsonObject() : null;
                                                                                                v30 = var34_34 = componentsJson.get("minecraft:particle_lifetime_events");
                                                                                                particleLifetimeEventsJson = v30 != null ? v30.getAsJsonObject() : null;
                                                                                                id = new ResourceLocation(descJson.get("identifier").getAsString());
                                                                                                v31 /* !! */  = particleLifetimeJson;
                                                                                                var36_37 = v31 /* !! */  != null && (v31 /* !! */  = (var37_35 = v31 /* !! */ .get("max_lifetime"))) != null && (v31 /* !! */  = (var38_36 = v31 /* !! */ .getAsString())) != null ? MoLangExtensionsKt.asExpression((String)v31 /* !! */ ) : null;
                                                                                                v32 = var36_37;
                                                                                                if (v32 == null) {
                                                                                                    v32 = maxAge = (Expression)MoLangExtensionsKt.asExpression(0.0);
                                                                                                }
                                                                                                if ((v33 /* !! */  = (var37_35 = (v34 /* !! */  = particleLifetimeJson) != null && (v34 /* !! */  = (var38_36 = v34 /* !! */ .get("expiration_expression"))) != null && (v34 /* !! */  = (var39_39 = v34 /* !! */ .getAsString())) != null ? MoLangExtensionsKt.asExpression((String)v34 /* !! */ ) : null)) == null) {
                                                                                                    v33 /* !! */  = MoLangExtensionsKt.asExpression(0.0);
                                                                                                }
                                                                                                killExpression = v33 /* !! */ ;
                                                                                                var38_36 = basicRenderParametersJson.get("material").getAsString();
                                                                                                Intrinsics.checkNotNullExpressionValue((Object)var38_36, (String)"basicRenderParametersJson.get(\"material\").asString");
                                                                                                var38_36 = StringsKt.substringAfter$default((String)var38_36, (String)"_", null, (int)2, null);
                                                                                                v35 = var38_36.toUpperCase(Locale.ROOT);
                                                                                                Intrinsics.checkNotNullExpressionValue((Object)v35, (String)"this as java.lang.String).toUpperCase(Locale.ROOT)");
                                                                                                material = ParticleMaterial.valueOf(v35);
                                                                                                it = var40_40 = basicRenderParametersJson.get("texture").getAsString();
                                                                                                $i$a$-let-SnowstormParticleReader$loadEffect$texture$1 = false;
                                                                                                Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                                                                                                var39_39 = StringsKt.endsWith$default((String)it, (String)".png", (boolean)false, (int)2, null) ? StringsKt.replace$default((String)it, (String)".png", (String)"", (boolean)false, (int)4, null) : it;
                                                                                                Intrinsics.checkNotNullExpressionValue((Object)var39_39, (String)"basicRenderParametersJso\u2026ace(\".png\", \"\") else it }");
                                                                                                texture = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(StringsKt.replace$default((String)StringsKt.replace$default((String)var39_39, (String)"particles/", (String)"", (boolean)false, (int)4, null), (String)"textures/", (String)"", (boolean)false, (int)4, null), null, 1, null);
                                                                                                v36 /* !! */  = sizeJson;
                                                                                                var40_40 = v36 /* !! */  != null && (v36 /* !! */  = (it = v36 /* !! */ .get(0))) != null && (v36 /* !! */  = ($i$a$-let-SnowstormParticleReader$loadEffect$texture$1 = v36 /* !! */ .getAsString())) != null ? MoLangExtensionsKt.asExpression((String)v36 /* !! */ ) : null;
                                                                                                v37 = var40_40;
                                                                                                if (v37 == null) {
                                                                                                    v37 = sizeX = (Expression)MoLangExtensionsKt.asExpression(1.0);
                                                                                                }
                                                                                                if ((v38 = (it = (v39 /* !! */  = sizeJson) != null && (v39 /* !! */  = ($i$a$-let-SnowstormParticleReader$loadEffect$texture$1 = v39 /* !! */ .get(1))) != null && (v39 /* !! */  = (var43_46 = v39 /* !! */ .getAsString())) != null ? MoLangExtensionsKt.asExpression((String)v39 /* !! */ ) : null)) == null) {
                                                                                                    v38 = sizeY = (Expression)MoLangExtensionsKt.asExpression(1.0);
                                                                                                }
                                                                                                if ((v40 = ($i$a$-let-SnowstormParticleReader$loadEffect$texture$1 = (v41 /* !! */  = particleInitialSpinJson) != null && (v41 /* !! */  = (var43_46 = v41 /* !! */ .get("rotation"))) != null && (v41 /* !! */  = (var44_47 = v41 /* !! */ .getAsString())) != null ? MoLangExtensionsKt.asExpression((String)v41 /* !! */ ) : null)) == null) {
                                                                                                    v40 = startRotation = (Expression)MoLangExtensionsKt.asExpression(0.0);
                                                                                                }
                                                                                                if ((v42 = (var43_46 = (v43 /* !! */  = particleInitialSpinJson) != null && (v43 /* !! */  = (var44_47 = v43 /* !! */ .get("rotation_rate"))) != null && (v43 /* !! */  = (var45_48 = v43 /* !! */ .getAsString())) != null ? MoLangExtensionsKt.asExpression((String)v43 /* !! */ ) : null)) == null) {
                                                                                                    v42 = MoLangExtensionsKt.asExpression(0.0);
                                                                                                }
                                                                                                rotationSpeed = v42;
                                                                                                var44_47 = curvesJson.entrySet();
                                                                                                Intrinsics.checkNotNullExpressionValue((Object)var44_47, (String)"curvesJson.entrySet()");
                                                                                                $this$mapNotNull$iv = (Iterable)var44_47;
                                                                                                $i$f$mapNotNull = false;
                                                                                                var46_51 = $this$mapNotNull$iv;
                                                                                                destination$iv$iv = new ArrayList<E>();
                                                                                                $i$f$mapNotNullTo = false;
                                                                                                $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv /* !! */ ;
                                                                                                $i$f$forEach = false;
                                                                                                var51_65 = $this$forEach$iv$iv$iv.iterator();
                                                                                                while (var51_65.hasNext()) {
                                                                                                    element$iv$iv = element$iv$iv$iv = var51_65.next();
                                                                                                    $i$a$-forEach-CollectionsKt___CollectionsKt$mapNotNullTo$1$iv$iv = false;
                                                                                                    var55_87 = (Map.Entry)element$iv$iv;
                                                                                                    $i$a$-mapNotNull-SnowstormParticleReader$loadEffect$curves$1 = false;
                                                                                                    Intrinsics.checkNotNullExpressionValue((Object)var55_87, (String)"(name, curveJson)");
                                                                                                    name = (String)var55_87.getKey();
                                                                                                    curveJson = (JsonElement)var55_87.getValue();
                                                                                                    Intrinsics.checkNotNull((Object)curveJson, (String)"null cannot be cast to non-null type com.google.gson.JsonObject");
                                                                                                    (JsonObject)curveJson;
                                                                                                    Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
                                                                                                    variableName = StringsKt.substringAfter$default((String)name, (String)".", null, (int)2, null);
                                                                                                    var60_157 = ((JsonObject)curveJson).get("type").getAsString();
                                                                                                    if (var60_157 == null) ** GOTO lbl-1000
                                                                                                    tmp = -1;
                                                                                                    switch (var60_157.hashCode()) {
                                                                                                        case -1102672091: {
                                                                                                            if (var60_157.equals("linear")) {
                                                                                                                tmp = 1;
                                                                                                            }
                                                                                                            break;
                                                                                                        }
                                                                                                        case 2043326479: {
                                                                                                            if (var60_157.equals("catmull_rom")) {
                                                                                                                tmp = 2;
                                                                                                            }
                                                                                                            break;
                                                                                                        }
                                                                                                        case 493524321: {
                                                                                                            if (var60_157.equals("bezier_chain")) {
                                                                                                                tmp = 3;
                                                                                                            }
                                                                                                            break;
                                                                                                        }
                                                                                                        case -1392296225: {
                                                                                                            if (var60_157.equals("bezier")) {
                                                                                                                tmp = 4;
                                                                                                            }
                                                                                                            break;
                                                                                                        }
                                                                                                    }
                                                                                                    switch (tmp) {
                                                                                                        case 2: {
                                                                                                            v44 = ((JsonObject)curveJson).getAsJsonArray("nodes");
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v44, (String)"curveJson.getAsJsonArray(\"nodes\")");
                                                                                                            $this$map$iv = (Iterable)v44;
                                                                                                            $i$f$map = false;
                                                                                                            var63_197 = $this$map$iv;
                                                                                                            destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                                            $i$f$mapTo = false;
                                                                                                            for (T item$iv$iv : $this$mapTo$iv$iv) {
                                                                                                                var68_296 = (JsonElement)item$iv$iv;
                                                                                                                var69_310 = destination$iv$iv;
                                                                                                                $i$a$-map-SnowstormParticleReader$loadEffect$curves$1$nodes$1 = false;
                                                                                                                var69_310.add(it.getAsDouble());
                                                                                                            }
                                                                                                            nodes = (List)destination$iv$iv;
                                                                                                            v45 = ((JsonObject)curveJson).get("input").getAsString();
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v45, (String)"curveJson.get(\"input\").asString");
                                                                                                            v46 = MoLangExtensionsKt.asExpression(v45);
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v46, (String)"curveJson.get(\"input\").asString.asExpression()");
                                                                                                            v47 = ((JsonObject)curveJson).get("horizontal_range").getAsString();
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v47, (String)"curveJson.get(\"horizontal_range\").asString");
                                                                                                            v48 = MoLangExtensionsKt.asExpression(v47);
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v48, (String)"curveJson.get(\"horizonta\u2026).asString.asExpression()");
                                                                                                            v49 = new CatmullRomMoLangCurve(variableName, v46, v48, nodes);
                                                                                                            break;
                                                                                                        }
                                                                                                        case 4: {
                                                                                                            v50 = ((JsonObject)curveJson).getAsJsonArray("nodes");
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v50, (String)"curveJson.getAsJsonArray(\"nodes\")");
                                                                                                            $this$map$iv = (Iterable)v50;
                                                                                                            $i$f$map = false;
                                                                                                            $this$mapTo$iv$iv = $this$map$iv;
                                                                                                            destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                                            $i$f$mapTo = false;
                                                                                                            for (T item$iv$iv : $this$mapTo$iv$iv) {
                                                                                                                it = (JsonElement)item$iv$iv;
                                                                                                                var69_310 = destination$iv$iv;
                                                                                                                $i$a$-map-SnowstormParticleReader$loadEffect$curves$1$nodes$2 = false;
                                                                                                                var69_310.add(it.getAsDouble());
                                                                                                            }
                                                                                                            nodes = (List)destination$iv$iv;
                                                                                                            v51 = ((JsonObject)curveJson).get("input").getAsString();
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v51, (String)"curveJson.get(\"input\").asString");
                                                                                                            v52 = MoLangExtensionsKt.asExpression(v51);
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v52, (String)"curveJson.get(\"input\").asString.asExpression()");
                                                                                                            v53 = ((JsonObject)curveJson).get("horizontal_range").getAsString();
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v53, (String)"curveJson.get(\"horizontal_range\").asString");
                                                                                                            v54 = MoLangExtensionsKt.asExpression(v53);
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v54, (String)"curveJson.get(\"horizonta\u2026).asString.asExpression()");
                                                                                                            v49 = new BezierMoLangCurve(variableName, v52, v54, ((Number)nodes.get(0)).doubleValue(), ((Number)nodes.get(1)).doubleValue(), ((Number)nodes.get(2)).doubleValue(), ((Number)nodes.get(3)).doubleValue());
                                                                                                            break;
                                                                                                        }
                                                                                                        case 3: {
                                                                                                            v55 = ((JsonObject)curveJson).get("input").getAsString();
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v55, (String)"curveJson.get(\"input\").asString");
                                                                                                            input = MoLangExtensionsKt.asExpression(v55);
                                                                                                            v56 = ((JsonObject)curveJson).get("nodes").getAsJsonObject().entrySet();
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v56, (String)"curveJson.get(\"nodes\").asJsonObject.entrySet()");
                                                                                                            $this$map$iv = v56;
                                                                                                            $i$f$map = false;
                                                                                                            destination$iv$iv = $this$map$iv;
                                                                                                            destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                                            $i$f$mapTo = false;
                                                                                                            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                                                                                                                $i$a$-map-SnowstormParticleReader$loadEffect$curves$1$nodes$2 = (Map.Entry)item$iv$iv;
                                                                                                                var69_310 = destination$iv$iv;
                                                                                                                $i$a$-map-SnowstormParticleReader$loadEffect$curves$1$nodes$3 = false;
                                                                                                                Intrinsics.checkNotNullExpressionValue((Object)$i$a$-map-SnowstormParticleReader$loadEffect$curves$1$nodes$2, (String)"(key, value)");
                                                                                                                key = (String)$i$a$-map-SnowstormParticleReader$loadEffect$curves$1$nodes$2.getKey();
                                                                                                                value /* !! */  = (JsonElement)$i$a$-map-SnowstormParticleReader$loadEffect$curves$1$nodes$2.getValue();
                                                                                                                Intrinsics.checkNotNullExpressionValue((Object)key, (String)"key");
                                                                                                                v57 = Double.parseDouble(key);
                                                                                                                Intrinsics.checkNotNull((Object)value /* !! */ , (String)"null cannot be cast to non-null type com.google.gson.JsonObject");
                                                                                                                var75_395 = (JsonObject)value /* !! */ ;
                                                                                                                var76_408 = v57;
                                                                                                                $i$a$-let-SnowstormParticleReader$loadEffect$curves$1$nodes$3$1 = false;
                                                                                                                var69_310.add(TuplesKt.to((Object)var76_408, (Object)new BezierChainMoLangCurve.BezierChainNode(it.get("value").getAsDouble(), it.get("slope").getAsDouble())));
                                                                                                            }
                                                                                                            nodes = MapsKt.toMap((Iterable)((List)destination$iv$iv));
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)input, (String)"input");
                                                                                                            v49 = new BezierChainMoLangCurve(variableName, (Expression)input, nodes);
                                                                                                            break;
                                                                                                        }
                                                                                                        case 1: {
                                                                                                            v58 = ((JsonObject)curveJson).get("input").getAsString();
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v58, (String)"curveJson.get(\"input\").asString");
                                                                                                            input = MoLangExtensionsKt.asExpression(v58);
                                                                                                            v59 = ((JsonObject)curveJson).get("horizontal_range").getAsString();
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v59, (String)"curveJson.get(\"horizontal_range\").asString");
                                                                                                            horizontalRange = MoLangExtensionsKt.asExpression(v59);
                                                                                                            v60 = ((JsonObject)curveJson).get("nodes").getAsJsonArray();
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v60, (String)"curveJson.get(\"nodes\").asJsonArray");
                                                                                                            $this$map$iv = (Iterable)v60;
                                                                                                            $i$f$map = false;
                                                                                                            destination$iv$iv = $this$map$iv;
                                                                                                            destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                                            $i$f$mapTo = false;
                                                                                                            item$iv$iv = $this$mapTo$iv$iv.iterator();
                                                                                                            while (item$iv$iv.hasNext()) {
                                                                                                                item$iv$iv = item$iv$iv.next();
                                                                                                                $i$a$-map-SnowstormParticleReader$loadEffect$curves$1$nodes$3 = (JsonElement)item$iv$iv;
                                                                                                                var69_310 = destination$iv$iv;
                                                                                                                $i$a$-map-SnowstormParticleReader$loadEffect$curves$1$nodes$4 = false;
                                                                                                                var69_310.add(it.getAsDouble());
                                                                                                            }
                                                                                                            nodes = (List)destination$iv$iv;
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)input, (String)"input");
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)horizontalRange, (String)"horizontalRange");
                                                                                                            v49 = new LinearMoLangCurve(variableName, (Expression)input, (Expression)horizontalRange, nodes);
                                                                                                            break;
                                                                                                        }
                                                                                                        default: lbl-1000:
                                                                                                        // 2 sources

                                                                                                        {
                                                                                                            input = "Unrecognized curve type was used";
                                                                                                            throw new NotImplementedError("An operation is not implemented: " + (String)input);
                                                                                                        }
                                                                                                    }
                                                                                                    it$iv$iv = v49;
                                                                                                    $i$a$-let-CollectionsKt___CollectionsKt$mapNotNullTo$1$1$iv$iv = false;
                                                                                                    destination$iv$iv.add(it$iv$iv);
                                                                                                }
                                                                                                curves = (List)destination$iv$iv;
                                                                                                events = new LinkedHashMap<K, V>();
                                                                                                v61 = $this$mapNotNullTo$iv$iv /* !! */  = effectJson.get("events");
                                                                                                eventJson /* !! */  = v61 != null ? v61.getAsJsonObject() : null;
                                                                                                v62 = eventJson /* !! */ ;
                                                                                                if (v62 == null || (v62 = ($this$mapNotNullTo$iv$iv /* !! */  = v62.entrySet())) == null) break block241;
                                                                                                $this$forEach$iv = (Iterable)v62;
                                                                                                $i$f$forEach = false;
                                                                                                for (T element$iv : $this$forEach$iv) {
                                                                                                    block243: {
                                                                                                        block242: {
                                                                                                            var51_65 = (Map.Entry)element$iv;
                                                                                                            $i$a$-forEach-SnowstormParticleReader$loadEffect$1 = false;
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)var51_65, (String)"(name, event)");
                                                                                                            name = (String)var51_65.getKey();
                                                                                                            event = (JsonElement)var51_65.getValue();
                                                                                                            eventObj = event.getAsJsonObject();
                                                                                                            v63 = eventObj.get("particle_effect");
                                                                                                            if (v63 == null || (v63 = ($i$a$-mapNotNull-SnowstormParticleReader$loadEffect$curves$1 = v63.getAsJsonObject())) == null) break block242;
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v63, (String)"asJsonObject");
                                                                                                            it /* !! */  = $i$a$-mapNotNull-SnowstormParticleReader$loadEffect$curves$1;
                                                                                                            $i$a$-let-SnowstormParticleReader$loadEffect$1$particleEffect$1 = false;
                                                                                                            v64 = it /* !! */ .get("effect").getAsString();
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v64, (String)"it.get(\"effect\").asString");
                                                                                                            effect = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(v64, null, 1, null);
                                                                                                            type = it /* !! */ .get("type").getAsString();
                                                                                                            v65 /* !! */  = it /* !! */ .get("pre_effect_expression");
                                                                                                            if (v65 /* !! */  == null) ** GOTO lbl-1000
                                                                                                            horizontalRange = v65 /* !! */ .getAsString();
                                                                                                            v65 /* !! */  = horizontalRange;
                                                                                                            if (horizontalRange != null) {
                                                                                                                Intrinsics.checkNotNullExpressionValue((Object)v65 /* !! */ , (String)"asString");
                                                                                                                v66 = MoLangExtensionsKt.asExpressionLike((String)horizontalRange);
                                                                                                            } else lbl-1000:
                                                                                                            // 2 sources

                                                                                                            {
                                                                                                                v66 = null;
                                                                                                            }
                                                                                                            preEffectExpression = v66;
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)type, (String)"type");
                                                                                                            v67 = type.toUpperCase(Locale.ROOT);
                                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v67, (String)"this as java.lang.String).toUpperCase(Locale.ROOT)");
                                                                                                            typeEnum = EventParticleEffect.EventParticleType.valueOf(v67);
                                                                                                            v68 = new EventParticleEffect(effect, typeEnum, preEffectExpression);
                                                                                                            break block243;
                                                                                                        }
                                                                                                        v68 = particleEffect = null;
                                                                                                    }
                                                                                                    if ((v69 = eventObj.get("sound_effect")) != null && (v69 = ($this$mapTo$iv$iv = v69.getAsJsonObject())) != null) {
                                                                                                        Intrinsics.checkNotNullExpressionValue((Object)v69, (String)"asJsonObject");
                                                                                                        it = $this$mapTo$iv$iv;
                                                                                                        $i$a$-let-SnowstormParticleReader$loadEffect$1$soundEffect$1 = false;
                                                                                                        v70 = it.get("event_name").getAsString();
                                                                                                        Intrinsics.checkNotNullExpressionValue((Object)v70, (String)"it.get(\"event_name\").asString");
                                                                                                        eventName = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(v70, null, 1, null);
                                                                                                        v71 = new EventSoundEffect(eventName);
                                                                                                    } else {
                                                                                                        v71 = soundEffect = null;
                                                                                                    }
                                                                                                    if ((v72 = eventObj.get("expression")) != null && (v72 = (it /* !! */  = v72.getAsString())) != null) {
                                                                                                        Intrinsics.checkNotNullExpressionValue((Object)v72, (String)"asString");
                                                                                                        v73 = MoLangExtensionsKt.asExpressionLike((String)it /* !! */ );
                                                                                                    } else {
                                                                                                        v73 = null;
                                                                                                    }
                                                                                                    expression = v73;
                                                                                                    Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
                                                                                                    events.put(name, new ParticleEvent(particleEffect, soundEffect, expression));
                                                                                                }
                                                                                                v74 = Unit.INSTANCE;
                                                                                                break block244;
                                                                                            }
                                                                                            v74 = null;
                                                                                        }
                                                                                        v75 = $i$f$forEach = emitterInitializationJson.get("creation_expression");
                                                                                        v76 = $this$forEach$iv = v75 != null ? v75.getAsString() : null;
                                                                                        if ($this$forEach$iv == null) {
                                                                                            v76 = "";
                                                                                        }
                                                                                        $this$forEach$iv = new String[]{";"};
                                                                                        $this$filter$iv = StringsKt.split$default((CharSequence)((CharSequence)v76), (String[])$this$forEach$iv, (boolean)false, (int)0, (int)6, null);
                                                                                        $i$f$filter = false;
                                                                                        $this$forEach$iv$iv$iv = $this$filter$iv;
                                                                                        destination$iv$iv /* !! */  = new ArrayList<E>();
                                                                                        $i$f$filterTo = false;
                                                                                        for (T element$iv$iv : $this$filterTo$iv$iv) {
                                                                                            it = (String)element$iv$iv;
                                                                                            $i$a$-filter-SnowstormParticleReader$loadEffect$emitterStartExpressions$1 = false;
                                                                                            v77 = ((CharSequence)it).length() > 0;
                                                                                            if (!v77) continue;
                                                                                            destination$iv$iv /* !! */ .add(element$iv$iv);
                                                                                        }
                                                                                        $this$filter$iv = (List)destination$iv$iv /* !! */ ;
                                                                                        $i$f$map = false;
                                                                                        $this$filterTo$iv$iv = $this$map$iv;
                                                                                        destination$iv$iv /* !! */  = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                        $i$f$mapTo = false;
                                                                                        for (Object item$iv$iv : $this$mapTo$iv$iv) {
                                                                                            it = (String)item$iv$iv;
                                                                                            var102_449 = destination$iv$iv /* !! */ ;
                                                                                            $i$a$-map-SnowstormParticleReader$loadEffect$emitterStartExpressions$2 = false;
                                                                                            var103_450 = MoLangExtensionsKt.asExpression(it);
                                                                                            var102_449.add(var103_450);
                                                                                        }
                                                                                        emitterStartExpressions = (List)destination$iv$iv /* !! */ ;
                                                                                        v78 = $this$mapTo$iv$iv = emitterInitializationJson.get("per_update_expression");
                                                                                        v79 = $i$f$map = v78 != null ? v78.getAsString() : null;
                                                                                        if ($i$f$map == null) {
                                                                                            v79 = "";
                                                                                        }
                                                                                        $i$f$map = new String[]{";"};
                                                                                        $this$filter$iv = StringsKt.split$default((CharSequence)((CharSequence)v79), (String[])$i$f$map, (boolean)false, (int)0, (int)6, null);
                                                                                        $i$f$filter = false;
                                                                                        destination$iv$iv /* !! */  = $this$filter$iv;
                                                                                        destination$iv$iv /* !! */  = new ArrayList<E>();
                                                                                        $i$f$filterTo = false;
                                                                                        for (E element$iv$iv : $this$filterTo$iv$iv) {
                                                                                            it = (String)element$iv$iv;
                                                                                            $i$a$-filter-SnowstormParticleReader$loadEffect$emitterUpdateExpressions$1 = false;
                                                                                            v80 = ((CharSequence)it).length() > 0;
                                                                                            if (!v80) continue;
                                                                                            destination$iv$iv /* !! */ .add(element$iv$iv);
                                                                                        }
                                                                                        $this$filter$iv = (List)destination$iv$iv /* !! */ ;
                                                                                        $i$f$map = false;
                                                                                        $this$filterTo$iv$iv = $this$map$iv;
                                                                                        destination$iv$iv /* !! */  = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                        $i$f$mapTo = false;
                                                                                        for (E item$iv$iv : $this$mapTo$iv$iv) {
                                                                                            it = (String)item$iv$iv;
                                                                                            var102_449 = destination$iv$iv /* !! */ ;
                                                                                            $i$a$-map-SnowstormParticleReader$loadEffect$emitterUpdateExpressions$2 = false;
                                                                                            var103_450 = MoLangExtensionsKt.asExpression(it);
                                                                                            var102_449.add(var103_450);
                                                                                        }
                                                                                        emitterUpdateExpressions = (List)destination$iv$iv /* !! */ ;
                                                                                        v81 = $this$mapTo$iv$iv = particleInitializationJson.get("per_update_expression");
                                                                                        v82 = $i$f$map = v81 != null ? v81.getAsString() : null;
                                                                                        if ($i$f$map == null) {
                                                                                            v82 = "";
                                                                                        }
                                                                                        $i$f$map = new String[]{";"};
                                                                                        $this$filter$iv = StringsKt.split$default((CharSequence)((CharSequence)v82), (String[])$i$f$map, (boolean)false, (int)0, (int)6, null);
                                                                                        $i$f$filter = false;
                                                                                        destination$iv$iv /* !! */  = $this$filter$iv;
                                                                                        destination$iv$iv /* !! */  = new ArrayList<E>();
                                                                                        $i$f$filterTo = false;
                                                                                        for (T element$iv$iv : $this$filterTo$iv$iv) {
                                                                                            it = (String)element$iv$iv;
                                                                                            $i$a$-filter-SnowstormParticleReader$loadEffect$particleUpdateExpressions$1 = false;
                                                                                            v83 = ((CharSequence)it).length() > 0;
                                                                                            if (!v83) continue;
                                                                                            destination$iv$iv /* !! */ .add(element$iv$iv);
                                                                                        }
                                                                                        $this$filter$iv = (List)destination$iv$iv /* !! */ ;
                                                                                        $i$f$map = false;
                                                                                        $this$filterTo$iv$iv = $this$map$iv;
                                                                                        destination$iv$iv /* !! */  = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                        $i$f$mapTo = false;
                                                                                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                                                                                            it = (String)item$iv$iv;
                                                                                            var102_449 = destination$iv$iv /* !! */ ;
                                                                                            $i$a$-map-SnowstormParticleReader$loadEffect$particleUpdateExpressions$2 = false;
                                                                                            var103_450 = MoLangExtensionsKt.asExpression(it);
                                                                                            var102_449.add(var103_450);
                                                                                        }
                                                                                        particleUpdateExpressions = (List)destination$iv$iv /* !! */ ;
                                                                                        v84 = $this$mapTo$iv$iv = particleInitializationJson.get("per_render_expressions");
                                                                                        v85 = $i$f$map = v84 != null ? v84.getAsString() : null;
                                                                                        if ($i$f$map == null) {
                                                                                            v85 = "";
                                                                                        }
                                                                                        $i$f$map = new String[]{";"};
                                                                                        $this$filter$iv = StringsKt.split$default((CharSequence)((CharSequence)v85), (String[])$i$f$map, (boolean)false, (int)0, (int)6, null);
                                                                                        $i$f$filter = false;
                                                                                        destination$iv$iv /* !! */  = $this$filter$iv;
                                                                                        destination$iv$iv = new ArrayList<E>();
                                                                                        $i$f$filterTo = false;
                                                                                        for (T element$iv$iv : $this$filterTo$iv$iv) {
                                                                                            it = (String)element$iv$iv;
                                                                                            $i$a$-filter-SnowstormParticleReader$loadEffect$particleRenderExpressions$1 = false;
                                                                                            v86 = ((CharSequence)it).length() > 0;
                                                                                            if (!v86) continue;
                                                                                            destination$iv$iv.add(element$iv$iv);
                                                                                        }
                                                                                        $this$filter$iv = (List)destination$iv$iv;
                                                                                        $i$f$map = false;
                                                                                        $this$filterTo$iv$iv = $this$map$iv;
                                                                                        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                        $i$f$mapTo = false;
                                                                                        item$iv$iv /* !! */  = $this$mapTo$iv$iv.iterator();
                                                                                        while (item$iv$iv /* !! */ .hasNext()) {
                                                                                            item$iv$iv = item$iv$iv /* !! */ .next();
                                                                                            it = (String)item$iv$iv;
                                                                                            var102_449 = destination$iv$iv;
                                                                                            $i$a$-map-SnowstormParticleReader$loadEffect$particleRenderExpressions$2 = false;
                                                                                            var103_450 = MoLangExtensionsKt.asExpression(it);
                                                                                            var102_449.add(var103_450);
                                                                                        }
                                                                                        particleRenderExpressions = (List)destination$iv$iv;
                                                                                        direction = new Ref.ObjectRef();
                                                                                        destination$iv$iv = componentsJson.get("minecraft:particle_initial_speed");
                                                                                        v87 = destination$iv$iv;
                                                                                        $this$mapTo$iv$iv = v87 != null ? v87.getAsString() : null;
                                                                                        v88 = $this$mapTo$iv$iv;
                                                                                        if (v88 == null) {
                                                                                            v88 = "0.0";
                                                                                        }
                                                                                        speed = MoLangExtensionsKt.asExpression(v88);
                                                                                        if (instantRateJson != null) {
                                                                                            v89 = item$iv$iv /* !! */  = instantRateJson.get("num_particles");
                                                                                            $i$f$mapTo = v89 != null ? v89.getAsString() : null;
                                                                                            v90 = $i$f$mapTo;
                                                                                            if (v90 == null) {
                                                                                                v90 = "1.0";
                                                                                            }
                                                                                            destination$iv$iv = MoLangExtensionsKt.asExpression((String)v90);
                                                                                            Intrinsics.checkNotNullExpressionValue((Object)destination$iv$iv, (String)"instantRateJson.get(\"num\u2026 ?: \"1.0\").asExpression()");
                                                                                            v91 = new InstantParticleEmitterRate((Expression)destination$iv$iv);
                                                                                        } else if (steadyRateJson != null) {
                                                                                            v92 = item$iv$iv /* !! */  = steadyRateJson.get("spawn_rate");
                                                                                            $i$f$mapTo = v92 != null ? v92.getAsString() : null;
                                                                                            v93 = $i$f$mapTo;
                                                                                            if (v93 == null) {
                                                                                                v93 = "1.0";
                                                                                            }
                                                                                            destination$iv$iv = MoLangExtensionsKt.asExpression((String)v93);
                                                                                            Intrinsics.checkNotNullExpressionValue((Object)destination$iv$iv, (String)"steadyRateJson.get(\"spaw\u2026 ?: \"1.0\").asExpression()");
                                                                                            v94 = destination$iv$iv;
                                                                                            $i$f$mapTo = steadyRateJson.get("max_particles").getAsString();
                                                                                            v95 = $i$f$mapTo;
                                                                                            if (v95 == null) {
                                                                                                v95 = "1.0";
                                                                                            }
                                                                                            destination$iv$iv = MoLangExtensionsKt.asExpression((String)v95);
                                                                                            Intrinsics.checkNotNullExpressionValue((Object)destination$iv$iv, (String)"steadyRateJson.get(\"max_\u2026 ?: \"1.0\").asExpression()");
                                                                                            v91 = new SteadyParticleEmitterRate((Expression)v94, (Expression)destination$iv$iv);
                                                                                        } else {
                                                                                            throw new IllegalStateException("Missing or unspecified emitter rate");
                                                                                        }
                                                                                        rate = v91;
                                                                                        if (emitterLifetimeOnceJson != null) {
                                                                                            v96 = item$iv$iv /* !! */  = emitterLifetimeOnceJson.get("active_time");
                                                                                            item$iv$iv /* !! */  = v96 != null ? v96.getAsString() : null;
                                                                                            v97 /* !! */  = item$iv$iv /* !! */ ;
                                                                                            if (v97 /* !! */  == null) {
                                                                                                v97 /* !! */  = "";
                                                                                            }
                                                                                            $i$f$mapTo = MoLangExtensionsKt.asExpression((String)v97 /* !! */ );
                                                                                            Intrinsics.checkNotNullExpressionValue((Object)$i$f$mapTo, (String)"emitterLifetimeOnceJson.\u2026ing ?: \"\").asExpression()");
                                                                                            v98 = new OnceEmitterLifetime((Expression)$i$f$mapTo);
                                                                                        } else if (emitterLifetimeLoopingJson != null) {
                                                                                            v99 = item$iv$iv /* !! */  = emitterLifetimeLoopingJson.get("active_time");
                                                                                            item$iv$iv /* !! */  = v99 != null ? v99.getAsString() : null;
                                                                                            v100 /* !! */  = item$iv$iv /* !! */ ;
                                                                                            if (v100 /* !! */  == null) {
                                                                                                v100 /* !! */  = "";
                                                                                            }
                                                                                            $i$f$mapTo = MoLangExtensionsKt.asExpression((String)v100 /* !! */ );
                                                                                            Intrinsics.checkNotNullExpressionValue((Object)$i$f$mapTo, (String)"emitterLifetimeLoopingJs\u2026ing ?: \"\").asExpression()");
                                                                                            v101 = $i$f$mapTo;
                                                                                            v102 = item$iv$iv /* !! */  = emitterLifetimeLoopingJson.get("sleep_time");
                                                                                            item$iv$iv /* !! */  = v102 != null ? v102.getAsString() : null;
                                                                                            v103 /* !! */  = item$iv$iv /* !! */ ;
                                                                                            if (v103 /* !! */  == null) {
                                                                                                v103 /* !! */  = "0.0";
                                                                                            }
                                                                                            $i$f$mapTo = MoLangExtensionsKt.asExpression((String)v103 /* !! */ );
                                                                                            Intrinsics.checkNotNullExpressionValue((Object)$i$f$mapTo, (String)"emitterLifetimeLoopingJs\u2026 ?: \"0.0\").asExpression()");
                                                                                            v98 = new LoopingEmitterLifetime((Expression)v101, (Expression)$i$f$mapTo);
                                                                                        } else if (emitterLifetimeExpressionJson != null) {
                                                                                            v104 = item$iv$iv /* !! */  = emitterLifetimeExpressionJson.get("activation_expression");
                                                                                            item$iv$iv /* !! */  = v104 != null ? v104.getAsString() : null;
                                                                                            v105 /* !! */  = item$iv$iv /* !! */ ;
                                                                                            if (v105 /* !! */  == null) {
                                                                                                v105 /* !! */  = "";
                                                                                            }
                                                                                            $i$f$mapTo = MoLangExtensionsKt.asExpression((String)v105 /* !! */ );
                                                                                            Intrinsics.checkNotNullExpressionValue((Object)$i$f$mapTo, (String)"emitterLifetimeExpressio\u2026ing ?: \"\").asExpression()");
                                                                                            v106 = $i$f$mapTo;
                                                                                            v107 = item$iv$iv /* !! */  = emitterLifetimeExpressionJson.get("expiration_expression");
                                                                                            item$iv$iv /* !! */  = v107 != null ? v107.getAsString() : null;
                                                                                            v108 /* !! */  = item$iv$iv /* !! */ ;
                                                                                            if (v108 /* !! */  == null) {
                                                                                                v108 /* !! */  = "";
                                                                                            }
                                                                                            $i$f$mapTo = MoLangExtensionsKt.asExpression((String)v108 /* !! */ );
                                                                                            Intrinsics.checkNotNullExpressionValue((Object)$i$f$mapTo, (String)"emitterLifetimeExpressio\u2026ing ?: \"\").asExpression()");
                                                                                            v98 = new ExpressionEmitterLifetime((Expression)v106, (Expression)$i$f$mapTo);
                                                                                        } else {
                                                                                            $i$f$mapTo = "Missing or unspecified emitter lifetime";
                                                                                            throw new NotImplementedError("An operation is not implemented: " + (String)$i$f$mapTo);
                                                                                        }
                                                                                        lifetime = v98;
                                                                                        if (emitterShapePointJson != null) {
                                                                                            item$iv$iv /* !! */  = emitterShapePointJson.get("offset");
                                                                                            v109 = item$iv$iv /* !! */ ;
                                                                                            if (v109 != null && (v109 = (it = v109.getAsJsonArray())) != null) {
                                                                                                $this$map$iv = (Iterable)v109;
                                                                                                $i$f$map = false;
                                                                                                horizontalRange = $this$map$iv;
                                                                                                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                                $i$f$mapTo = false;
                                                                                                particleEffect = $this$mapTo$iv$iv.iterator();
                                                                                                while (particleEffect.hasNext()) {
                                                                                                    item$iv$iv = particleEffect.next();
                                                                                                    soundEffect = (JsonElement)item$iv$iv;
                                                                                                    var102_449 = destination$iv$iv;
                                                                                                    $i$a$-map-SnowstormParticleReader$loadEffect$shape$arr$1 = false;
                                                                                                    v110 = it.getAsString();
                                                                                                    Intrinsics.checkNotNullExpressionValue((Object)v110, (String)"it.asString");
                                                                                                    var103_450 = MoLangExtensionsKt.asExpression(v110);
                                                                                                    var102_449.add(var103_450);
                                                                                                }
                                                                                                v111 = $i$a$-map-SnowstormParticleReader$loadEffect$particleRenderExpressions$2 = (List)destination$iv$iv;
                                                                                            } else {
                                                                                                $this$map$iv = new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)};
                                                                                                v111 = CollectionsKt.listOf((Object[])$this$map$iv);
                                                                                            }
                                                                                            arr = v111;
                                                                                            SnowstormParticleReader.loadEffect$resolveDirection((Ref.ObjectRef<ParticleMotionDirection>)direction, emitterShapePointJson);
                                                                                            v112 = new PointParticleEmitterShape((Triple<? extends Expression, ? extends Expression, ? extends Expression>)new Triple(arr.get(0), arr.get(1), arr.get(2)));
                                                                                        } else if (emitterShapeSphereJson != null) {
                                                                                            item$iv$iv /* !! */  = emitterShapeSphereJson.get("offset");
                                                                                            v113 = item$iv$iv /* !! */ ;
                                                                                            if (v113 != null && (v113 = (it = v113.getAsJsonArray())) != null) {
                                                                                                $this$map$iv = (Iterable)v113;
                                                                                                $i$f$map = false;
                                                                                                $this$mapTo$iv$iv = $this$map$iv;
                                                                                                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                                $i$f$mapTo = false;
                                                                                                particleEffect = $this$mapTo$iv$iv.iterator();
                                                                                                while (particleEffect.hasNext()) {
                                                                                                    item$iv$iv = particleEffect.next();
                                                                                                    it = (JsonElement)item$iv$iv;
                                                                                                    var102_449 = destination$iv$iv;
                                                                                                    $i$a$-map-SnowstormParticleReader$loadEffect$shape$arr$2 = false;
                                                                                                    v114 = it.getAsString();
                                                                                                    Intrinsics.checkNotNullExpressionValue((Object)v114, (String)"it.asString");
                                                                                                    var103_450 = MoLangExtensionsKt.asExpression(v114);
                                                                                                    var102_449.add(var103_450);
                                                                                                }
                                                                                                v115 = $i$a$-map-SnowstormParticleReader$loadEffect$particleRenderExpressions$2 = (List)destination$iv$iv;
                                                                                            } else {
                                                                                                $this$map$iv = new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)};
                                                                                                v115 = CollectionsKt.listOf((Object[])$this$map$iv);
                                                                                            }
                                                                                            arr = v115;
                                                                                            SnowstormParticleReader.loadEffect$resolveDirection((Ref.ObjectRef<ParticleMotionDirection>)direction, emitterShapeSphereJson);
                                                                                            v116 = new Triple(arr.get(0), arr.get(1), arr.get(2));
                                                                                            it = emitterShapeSphereJson.get("radius");
                                                                                            v117 /* !! */  = it;
                                                                                            item$iv$iv /* !! */  = v117 /* !! */  != null && (v117 /* !! */  = ($i$a$-map-SnowstormParticleReader$loadEffect$particleRenderExpressions$2 = v117 /* !! */ .getAsString())) != null ? MoLangExtensionsKt.asExpression((String)v117 /* !! */ ) : null;
                                                                                            v118 /* !! */  = item$iv$iv /* !! */ ;
                                                                                            if (v118 /* !! */  == null) {
                                                                                                v118 /* !! */  = MoLangExtensionsKt.asExpression(0.0);
                                                                                            }
                                                                                            v119 = item$iv$iv /* !! */  = emitterShapeSphereJson.get("surface_only");
                                                                                            v112 = new SphereParticleEmitterShape((Triple<? extends Expression, ? extends Expression, ? extends Expression>)v116, (Expression)v118 /* !! */ , v119 != null ? (it = v119.getAsBoolean()) : false);
                                                                                        } else if (emitterShapeDiscJson != null) {
                                                                                            SnowstormParticleReader.loadEffect$resolveDirection((Ref.ObjectRef<ParticleMotionDirection>)direction, emitterShapeDiscJson);
                                                                                            item$iv$iv /* !! */  = emitterShapeDiscJson.get("offset");
                                                                                            v120 = item$iv$iv /* !! */ ;
                                                                                            if (v120 != null && (v120 = (it = v120.getAsJsonArray())) != null) {
                                                                                                $this$map$iv /* !! */  = (Iterable)v120;
                                                                                                $i$f$map = false;
                                                                                                $this$mapTo$iv$iv = $this$map$iv /* !! */ ;
                                                                                                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv /* !! */ , (int)10));
                                                                                                $i$f$mapTo = false;
                                                                                                particleEffect = $this$mapTo$iv$iv.iterator();
                                                                                                while (particleEffect.hasNext()) {
                                                                                                    item$iv$iv = particleEffect.next();
                                                                                                    it = (JsonElement)item$iv$iv;
                                                                                                    var102_449 = destination$iv$iv;
                                                                                                    $i$a$-map-SnowstormParticleReader$loadEffect$shape$offsetExpressions$1 = false;
                                                                                                    v121 = it.getAsString();
                                                                                                    Intrinsics.checkNotNullExpressionValue((Object)v121, (String)"it.asString");
                                                                                                    var103_450 = MoLangExtensionsKt.asExpression(v121);
                                                                                                    var102_449.add(var103_450);
                                                                                                }
                                                                                                v122 = $i$a$-map-SnowstormParticleReader$loadEffect$particleRenderExpressions$2 = (List)destination$iv$iv;
                                                                                            } else {
                                                                                                $this$map$iv /* !! */  = new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)};
                                                                                                v122 = offsetExpressions = CollectionsKt.listOf((Object[])$this$map$iv /* !! */ );
                                                                                            }
                                                                                            if ((v123 = (it = emitterShapeDiscJson.get("plane_normal"))) == null) {
                                                                                                v123 = (JsonElement)new JsonPrimitive("y");
                                                                                            }
                                                                                            if ((normalJson = v123).isJsonArray()) {
                                                                                                $this$map$iv /* !! */  = normalJson.getAsJsonArray();
                                                                                                Intrinsics.checkNotNullExpressionValue((Object)$this$map$iv /* !! */ , (String)"normalJson.asJsonArray");
                                                                                                $this$map$iv /* !! */  = (Iterable)$this$map$iv /* !! */ ;
                                                                                                $i$f$map = false;
                                                                                                $this$mapTo$iv$iv = $this$map$iv /* !! */ ;
                                                                                                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv /* !! */ , (int)10));
                                                                                                $i$f$mapTo = false;
                                                                                                particleEffect = $this$mapTo$iv$iv.iterator();
                                                                                                while (particleEffect.hasNext()) {
                                                                                                    item$iv$iv = particleEffect.next();
                                                                                                    it = (JsonElement)item$iv$iv;
                                                                                                    var102_449 = destination$iv$iv;
                                                                                                    $i$a$-map-SnowstormParticleReader$loadEffect$shape$normal$normalArr$1 = false;
                                                                                                    v124 = it.getAsString();
                                                                                                    Intrinsics.checkNotNullExpressionValue((Object)v124, (String)"it.asString");
                                                                                                    var103_450 = MoLangExtensionsKt.asExpression(v124);
                                                                                                    var102_449.add(var103_450);
                                                                                                }
                                                                                                normalArr = (List)destination$iv$iv;
                                                                                                v125 = new Triple(normalArr.get(0), normalArr.get(1), normalArr.get(2));
                                                                                            } else {
                                                                                                normalArr = normalJson.getAsString();
                                                                                                v125 = Intrinsics.areEqual((Object)normalArr, (Object)"x") != false ? new Triple((Object)MoLangExtensionsKt.asExpression(1.0), (Object)MoLangExtensionsKt.asExpression(0.0), (Object)MoLangExtensionsKt.asExpression(0.0)) : (Intrinsics.areEqual((Object)normalArr, (Object)"y") != false ? new Triple((Object)MoLangExtensionsKt.asExpression(0.0), (Object)MoLangExtensionsKt.asExpression(1.0), (Object)MoLangExtensionsKt.asExpression(0.0)) : new Triple((Object)MoLangExtensionsKt.asExpression(0.0), (Object)MoLangExtensionsKt.asExpression(0.0), (Object)MoLangExtensionsKt.asExpression(1.0)));
                                                                                            }
                                                                                            normal = v125;
                                                                                            SnowstormParticleReader.loadEffect$resolveDirection((Ref.ObjectRef<ParticleMotionDirection>)direction, emitterShapeDiscJson);
                                                                                            normalArr = new Triple(offsetExpressions.get(0), offsetExpressions.get(1), offsetExpressions.get(2));
                                                                                            $this$mapTo$iv$iv = emitterShapeDiscJson.get("radius");
                                                                                            v126 = $this$mapTo$iv$iv;
                                                                                            $i$f$map = v126 != null && (v126 = (destination$iv$iv = v126.getAsString())) != null ? MoLangExtensionsKt.asExpression((String)v126) : null;
                                                                                            v127 = $i$f$map;
                                                                                            if (v127 == null) {
                                                                                                v127 = MoLangExtensionsKt.asExpression(0.0);
                                                                                            }
                                                                                            $this$map$iv /* !! */  = v127;
                                                                                            v128 = $this$mapTo$iv$iv = emitterShapeDiscJson.get("surface_only");
                                                                                            $i$f$map = v128 != null ? (destination$iv$iv = v128.getAsBoolean()) : false;
                                                                                            v112 = new DiscParticleEmitterShape((Triple<? extends Expression, ? extends Expression, ? extends Expression>)normalArr, (Expression)$this$map$iv /* !! */ , (Triple<? extends Expression, ? extends Expression, ? extends Expression>)normal, $i$f$map);
                                                                                        } else if (emitterShapeBoxJson != null) {
                                                                                            SnowstormParticleReader.loadEffect$resolveDirection((Ref.ObjectRef<ParticleMotionDirection>)direction, emitterShapeBoxJson);
                                                                                            normalJson = emitterShapeBoxJson.get("offset");
                                                                                            v129 = normalJson;
                                                                                            if (v129 != null && (v129 = (normal = v129.getAsJsonArray())) != null) {
                                                                                                $this$map$iv = (Iterable)v129;
                                                                                                $i$f$map = false;
                                                                                                $this$mapTo$iv$iv = $this$map$iv;
                                                                                                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                                $i$f$mapTo = false;
                                                                                                particleEffect = $this$mapTo$iv$iv.iterator();
                                                                                                while (particleEffect.hasNext()) {
                                                                                                    item$iv$iv = particleEffect.next();
                                                                                                    it = (JsonElement)item$iv$iv;
                                                                                                    var102_449 = destination$iv$iv;
                                                                                                    $i$a$-map-SnowstormParticleReader$loadEffect$shape$offsetExpressions$2 = false;
                                                                                                    v130 = it.getAsString();
                                                                                                    Intrinsics.checkNotNullExpressionValue((Object)v130, (String)"it.asString");
                                                                                                    var103_450 = MoLangExtensionsKt.asExpression(v130);
                                                                                                    var102_449.add(var103_450);
                                                                                                }
                                                                                                v131 = normalArr = (List)destination$iv$iv;
                                                                                            } else {
                                                                                                $this$map$iv = new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)};
                                                                                                v131 = offsetExpressions = CollectionsKt.listOf((Object[])$this$map$iv);
                                                                                            }
                                                                                            if ((v132 = (normal = emitterShapeBoxJson.get("half_dimensions"))) != null && (v132 = (normalArr = v132.getAsJsonArray())) != null) {
                                                                                                $this$map$iv = (Iterable)v132;
                                                                                                $i$f$map = false;
                                                                                                destination$iv$iv = $this$map$iv;
                                                                                                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                                $i$f$mapTo = false;
                                                                                                for (T item$iv$iv : $this$mapTo$iv$iv) {
                                                                                                    $i$a$-map-SnowstormParticleReader$loadEffect$shape$offsetExpressions$2 = (JsonElement)item$iv$iv;
                                                                                                    var102_449 = destination$iv$iv;
                                                                                                    $i$a$-map-SnowstormParticleReader$loadEffect$shape$boxSizeExpressions$1 = false;
                                                                                                    v133 = it.getAsString();
                                                                                                    Intrinsics.checkNotNullExpressionValue((Object)v133, (String)"it.asString");
                                                                                                    var103_450 = MoLangExtensionsKt.asExpression(v133);
                                                                                                    var102_449.add(var103_450);
                                                                                                }
                                                                                                v134 = $this$map$iv = (List)destination$iv$iv;
                                                                                            } else {
                                                                                                $this$map$iv = new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)};
                                                                                                v134 = CollectionsKt.listOf((Object[])$this$map$iv);
                                                                                            }
                                                                                            boxSizeExpressions = v134;
                                                                                            v135 = normal = emitterShapeBoxJson.get("surface_only");
                                                                                            v112 = new BoxParticleEmitterShape((Triple<? extends Expression, ? extends Expression, ? extends Expression>)new Triple(offsetExpressions.get(0), offsetExpressions.get(1), offsetExpressions.get(2)), (Triple<? extends Expression, ? extends Expression, ? extends Expression>)new Triple(boxSizeExpressions.get(0), boxSizeExpressions.get(1), boxSizeExpressions.get(2)), v135 != null ? (normalArr = v135.getAsBoolean()) : false);
                                                                                        } else if (emitterShapeEntityBoundingBoxJson != null) {
                                                                                            SnowstormParticleReader.loadEffect$resolveDirection((Ref.ObjectRef<ParticleMotionDirection>)direction, emitterShapeEntityBoundingBoxJson);
                                                                                            offsetExpressions = emitterShapeEntityBoundingBoxJson.get("surface_only");
                                                                                            v136 = offsetExpressions;
                                                                                            v112 = new EntityBoundingBoxParticleEmitterShape(v136 != null ? (boxSizeExpressions = v136.getAsBoolean()) : false);
                                                                                        } else {
                                                                                            offsetExpressions = "Missing or unimplemented emitter shape";
                                                                                            throw new NotImplementedError("An operation is not implemented: " + (String)offsetExpressions);
                                                                                        }
                                                                                        shape = v112;
                                                                                        if (dynamicMotionJson == null) break block245;
                                                                                        normal = dynamicMotionJson.get("linear_acceleration");
                                                                                        v137 = normal;
                                                                                        if (v137 != null && (v137 = (normalArr = v137.getAsJsonArray())) != null) {
                                                                                            $this$map$iv /* !! */  = (Iterable)v137;
                                                                                            $i$f$map = false;
                                                                                            $this$mapTo$iv$iv = $this$map$iv /* !! */ ;
                                                                                            destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv /* !! */ , (int)10));
                                                                                            $i$f$mapTo = false;
                                                                                            for (T item$iv$iv : $this$mapTo$iv$iv) {
                                                                                                it = (JsonElement)item$iv$iv;
                                                                                                var102_449 = destination$iv$iv;
                                                                                                $i$a$-map-SnowstormParticleReader$loadEffect$motion$accelerationExpressions$1 = false;
                                                                                                v138 = it.getAsString();
                                                                                                Intrinsics.checkNotNullExpressionValue((Object)v138, (String)"it.asString");
                                                                                                var103_450 = MoLangExtensionsKt.asExpression(v138);
                                                                                                var102_449.add(var103_450);
                                                                                            }
                                                                                            v139 = $this$map$iv = (List)destination$iv$iv;
                                                                                        } else {
                                                                                            $this$map$iv /* !! */  = new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)};
                                                                                            v139 = CollectionsKt.listOf((Object[])$this$map$iv /* !! */ );
                                                                                        }
                                                                                        accelerationExpressions = v139;
                                                                                        v140 /* !! */  = $this$map$iv = dynamicMotionJson.get("linear_drag_coefficient");
                                                                                        if ($this$map$iv == null) ** GOTO lbl-1000
                                                                                        v140 /* !! */  = $this$map$iv /* !! */  = v140 /* !! */ .getAsString();
                                                                                        if ($this$map$iv /* !! */  != null) {
                                                                                            v141 = MoLangExtensionsKt.asExpression((String)v140 /* !! */ );
                                                                                        } else lbl-1000:
                                                                                        // 2 sources

                                                                                        {
                                                                                            v141 = null;
                                                                                        }
                                                                                        if ((v142 = (normalArr = v141)) == null) {
                                                                                            v142 = MoLangExtensionsKt.asExpression(0.0);
                                                                                        }
                                                                                        drag = v142;
                                                                                        v143 = direction.element;
                                                                                        Intrinsics.checkNotNull((Object)v143);
                                                                                        v144 = (ParticleMotionDirection)v143;
                                                                                        Intrinsics.checkNotNullExpressionValue((Object)speed, (String)"speed");
                                                                                        v145 = new DynamicParticleMotion(v144, speed, (Triple<? extends Expression, ? extends Expression, ? extends Expression>)new Triple(accelerationExpressions.get(0), accelerationExpressions.get(1), accelerationExpressions.get(2)), drag);
                                                                                        break block246;
                                                                                    }
                                                                                    if (parametricMotionJson != null) {
                                                                                        drag = parametricMotionJson.get("relative_position");
                                                                                        v146 = drag;
                                                                                        if (v146 != null && (v146 = (normalArr = v146.getAsJsonArray())) != null) {
                                                                                            $this$map$iv = (Iterable)v146;
                                                                                            $i$f$map = false;
                                                                                            $this$mapTo$iv$iv = $this$map$iv;
                                                                                            destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                            $i$f$mapTo = false;
                                                                                            for (T item$iv$iv : $this$mapTo$iv$iv) {
                                                                                                it = (JsonElement)item$iv$iv;
                                                                                                var102_449 = destination$iv$iv;
                                                                                                $i$a$-map-SnowstormParticleReader$loadEffect$motion$offsetExpressions$1 = false;
                                                                                                v147 = it.getAsString();
                                                                                                Intrinsics.checkNotNullExpressionValue((Object)v147, (String)"it.asString");
                                                                                                var103_450 = MoLangExtensionsKt.asExpression(v147);
                                                                                                var102_449.add(var103_450);
                                                                                            }
                                                                                            v148 = $this$map$iv = (List)destination$iv$iv;
                                                                                        } else {
                                                                                            $this$map$iv = new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)};
                                                                                            v148 = offsetExpressions = CollectionsKt.listOf((Object[])$this$map$iv);
                                                                                        }
                                                                                        if ((v149 = (normalArr = parametricMotionJson.get("direction"))) != null && (v149 = ($this$map$iv = v149.getAsJsonArray())) != null) {
                                                                                            $this$map$iv = (Iterable)v149;
                                                                                            $i$f$map = false;
                                                                                            destination$iv$iv = $this$map$iv;
                                                                                            destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                            $i$f$mapTo = false;
                                                                                            for (T item$iv$iv : $this$mapTo$iv$iv) {
                                                                                                $i$a$-map-SnowstormParticleReader$loadEffect$motion$offsetExpressions$1 = (JsonElement)item$iv$iv;
                                                                                                var102_449 = destination$iv$iv;
                                                                                                $i$a$-map-SnowstormParticleReader$loadEffect$motion$directionExpressions$1 = false;
                                                                                                v150 = it.getAsString();
                                                                                                Intrinsics.checkNotNullExpressionValue((Object)v150, (String)"it.asString");
                                                                                                var103_450 = MoLangExtensionsKt.asExpression(v150);
                                                                                                var102_449.add(var103_450);
                                                                                            }
                                                                                            v151 = $this$map$iv = (List)destination$iv$iv;
                                                                                        } else {
                                                                                            $this$map$iv = new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)};
                                                                                            v151 = CollectionsKt.listOf((Object[])$this$map$iv);
                                                                                        }
                                                                                        directionExpressions = v151;
                                                                                        v145 = new ParametricParticleMotion((Triple<? extends Expression, ? extends Expression, ? extends Expression>)new Triple(offsetExpressions.get(0), offsetExpressions.get(1), offsetExpressions.get(2)), (Triple<? extends Expression, ? extends Expression, ? extends Expression>)new Triple(directionExpressions.get(0), directionExpressions.get(1), directionExpressions.get(2)));
                                                                                    } else {
                                                                                        v145 = motion = (ParticleMotion)new StaticParticleMotion();
                                                                                    }
                                                                                }
                                                                                if ((v152 = particleDirectionJson) != null && (v152 = (directionExpressions = v152.getAsJsonObject())) != null) {
                                                                                    it = $this$map$iv = v152;
                                                                                    $i$a$-let-SnowstormParticleReader$loadEffect$viewDirection$1 = false;
                                                                                    if (Intrinsics.areEqual((Object)it.get("mode").getAsString(), (Object)"custom")) {
                                                                                        v153 = it.get("custom_direction").getAsJsonArray();
                                                                                        Intrinsics.checkNotNullExpressionValue((Object)v153, (String)"it.get(\"custom_direction\").asJsonArray");
                                                                                        $this$map$iv = (Iterable)v153;
                                                                                        $i$f$map = false;
                                                                                        item$iv$iv = $this$map$iv;
                                                                                        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                        $i$f$mapTo = false;
                                                                                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                                                                                            input = (JsonElement)item$iv$iv;
                                                                                            it = destination$iv$iv;
                                                                                            $i$a$-map-SnowstormParticleReader$loadEffect$viewDirection$1$1 = false;
                                                                                            v154 = it.getAsString();
                                                                                            Intrinsics.checkNotNullExpressionValue((Object)v154, (String)"it.asString");
                                                                                            it.add(MoLangExtensionsKt.asExpression(v154));
                                                                                        }
                                                                                        it = it = (List)destination$iv$iv;
                                                                                        $i$a$-let-SnowstormParticleReader$loadEffect$viewDirection$1$2 = false;
                                                                                        it = new Triple(it.get(0), it.get(1), it.get(2));
                                                                                        var107_451 = it;
                                                                                        v155 = new CustomViewDirection((Triple<? extends Expression, ? extends Expression, ? extends Expression>)var107_451);
                                                                                    } else {
                                                                                        v156 = it.get("min_speed_threshold");
                                                                                        v155 = new FromMotionViewDirection(v156 != null ? v156.getAsDouble() : 0.01);
                                                                                    }
                                                                                    v157 = normalArr = v155;
                                                                                } else {
                                                                                    v157 = new FromMotionViewDirection(0.0, 1, null);
                                                                                }
                                                                                viewDirection = v157;
                                                                                $this$map$iv = cameraModeType = cameraModeJson.isJsonPrimitive() != false ? cameraModeJson.getAsString() : "rotate_xyz";
                                                                                if ($this$map$iv == null) ** GOTO lbl-1000
                                                                                tmp = -1;
                                                                                switch ($this$map$iv.hashCode()) {
                                                                                    case -2041694580: {
                                                                                        if ($this$map$iv.equals("lookat_y")) {
                                                                                            tmp = 1;
                                                                                        }
                                                                                        break;
                                                                                    }
                                                                                    case -1631834184: {
                                                                                        if ($this$map$iv.equals("direction_x")) {
                                                                                            tmp = 2;
                                                                                        }
                                                                                        break;
                                                                                    }
                                                                                    case -1631834183: {
                                                                                        if ($this$map$iv.equals("direction_y")) {
                                                                                            tmp = 3;
                                                                                        }
                                                                                        break;
                                                                                    }
                                                                                    case -78399307: {
                                                                                        if ($this$map$iv.equals("rotate_xyz")) {
                                                                                            tmp = 4;
                                                                                        }
                                                                                        break;
                                                                                    }
                                                                                    case 731565804: {
                                                                                        if ($this$map$iv.equals("lookat_xyz")) {
                                                                                            tmp = 5;
                                                                                        }
                                                                                        break;
                                                                                    }
                                                                                    case -1631834182: {
                                                                                        if ($this$map$iv.equals("direction_z")) {
                                                                                            tmp = 6;
                                                                                        }
                                                                                        break;
                                                                                    }
                                                                                    case -40305003: {
                                                                                        if ($this$map$iv.equals("rotate_y")) {
                                                                                            tmp = 7;
                                                                                        }
                                                                                        break;
                                                                                    }
                                                                                    case 1939370469: {
                                                                                        if ($this$map$iv.equals("emitter_transform_xy")) {
                                                                                            tmp = 8;
                                                                                        }
                                                                                        break;
                                                                                    }
                                                                                    case -1888716142: {
                                                                                        if ($this$map$iv.equals("lookat_direction")) {
                                                                                            tmp = 9;
                                                                                        }
                                                                                        break;
                                                                                    }
                                                                                    case 1939370470: {
                                                                                        if ($this$map$iv.equals("emitter_transform_xz")) {
                                                                                            tmp = 10;
                                                                                        }
                                                                                        break;
                                                                                    }
                                                                                }
                                                                                switch (tmp) {
                                                                                    case 4: {
                                                                                        v158 = new RotateXYZCameraMode();
                                                                                        break;
                                                                                    }
                                                                                    case 7: {
                                                                                        v158 = new RotateYCameraMode();
                                                                                        break;
                                                                                    }
                                                                                    case 5: {
                                                                                        v158 = new LookAtXYZ();
                                                                                        break;
                                                                                    }
                                                                                    case 1: {
                                                                                        v158 = new LookAtY();
                                                                                        break;
                                                                                    }
                                                                                    case 9: {
                                                                                        v158 = new LookAtDirection();
                                                                                        break;
                                                                                    }
                                                                                    case 2: {
                                                                                        v158 = new DirectionX();
                                                                                        break;
                                                                                    }
                                                                                    case 3: {
                                                                                        v158 = new DirectionY();
                                                                                        break;
                                                                                    }
                                                                                    case 6: {
                                                                                        v158 = new DirectionZ();
                                                                                        break;
                                                                                    }
                                                                                    case 8: {
                                                                                        v158 = new EmitterXYPlane();
                                                                                        break;
                                                                                    }
                                                                                    case 10: {
                                                                                        v158 = new EmitterXZPlane();
                                                                                        break;
                                                                                    }
                                                                                    default: lbl-1000:
                                                                                    // 2 sources

                                                                                    {
                                                                                        v158 = cameraMode = (ParticleCameraMode)new EmitterYZPlane();
                                                                                    }
                                                                                }
                                                                                if (uvModeJson.has("flipbook")) {
                                                                                    flipbook = uvModeJson.get("flipbook").getAsJsonObject();
                                                                                    baseUV = flipbook.get("base_UV").getAsJsonArray();
                                                                                    sizeUV /* !! */  = flipbook.get("size_UV").getAsJsonArray();
                                                                                    stepUV = flipbook.get("step_UV").getAsJsonArray();
                                                                                    it = baseUV.get(0).getAsString();
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)it, (String)"baseUV[0].asString");
                                                                                    destination$iv$iv = MoLangExtensionsKt.asExpression((String)it);
                                                                                    $i$a$-let-SnowstormParticleReader$loadEffect$viewDirection$1$2 = baseUV.get(1).getAsString();
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)$i$a$-let-SnowstormParticleReader$loadEffect$viewDirection$1$2, (String)"baseUV[1].asString");
                                                                                    it = MoLangExtensionsKt.asExpression((String)$i$a$-let-SnowstormParticleReader$loadEffect$viewDirection$1$2);
                                                                                    destination$iv$iv = sizeUV /* !! */ .get(0).getAsString();
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)destination$iv$iv, (String)"sizeUV[0].asString");
                                                                                    $i$a$-let-SnowstormParticleReader$loadEffect$viewDirection$1$2 = MoLangExtensionsKt.asExpression((String)destination$iv$iv);
                                                                                    $i$f$mapTo = sizeUV /* !! */ .get(1).getAsString();
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)$i$f$mapTo, (String)"sizeUV[1].asString");
                                                                                    destination$iv$iv = MoLangExtensionsKt.asExpression((String)$i$f$mapTo);
                                                                                    $i$a$-map-SnowstormParticleReader$loadEffect$motion$directionExpressions$1 = stepUV.get(0).getAsString();
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)$i$a$-map-SnowstormParticleReader$loadEffect$motion$directionExpressions$1, (String)"stepUV[0].asString");
                                                                                    $i$f$mapTo = MoLangExtensionsKt.asExpression((String)$i$a$-map-SnowstormParticleReader$loadEffect$motion$directionExpressions$1);
                                                                                    item$iv$iv = stepUV.get(1).getAsString();
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)item$iv$iv, (String)"stepUV[1].asString");
                                                                                    $i$a$-map-SnowstormParticleReader$loadEffect$motion$directionExpressions$1 = MoLangExtensionsKt.asExpression(item$iv$iv);
                                                                                    v159 = it = uvModeJson.get("texture_width");
                                                                                    item$iv$iv = v159 != null ? (it = v159.getAsInt()) : 128;
                                                                                    v160 = it /* !! */  = uvModeJson.get("texture_height");
                                                                                    it = v160 != null ? ($i$a$-map-SnowstormParticleReader$loadEffect$viewDirection$1$1 = v160.getAsInt()) : 128;
                                                                                    value /* !! */  = flipbook.get("max_frame");
                                                                                    v161 /* !! */  = value /* !! */ ;
                                                                                    $i$a$-map-SnowstormParticleReader$loadEffect$viewDirection$1$1 = v161 /* !! */  != null && (v161 /* !! */  = (it = v161 /* !! */ .getAsString())) != null ? MoLangExtensionsKt.asExpression((String)v161 /* !! */ ) : null;
                                                                                    v162 = $i$a$-map-SnowstormParticleReader$loadEffect$viewDirection$1$1;
                                                                                    if (v162 == null) {
                                                                                        v162 = new NumberExpression(0.0);
                                                                                    }
                                                                                    it /* !! */  = v162;
                                                                                    v163 = value /* !! */  = flipbook.get("loop");
                                                                                    $i$a$-map-SnowstormParticleReader$loadEffect$viewDirection$1$1 = v163 != null ? (it = v163.getAsBoolean()) : false;
                                                                                    var76_408 = flipbook.get("frames_per_second");
                                                                                    v164 = var76_408;
                                                                                    it = v164 != null && (v164 = ($i$a$-let-SnowstormParticleReader$loadEffect$curves$1$nodes$3$1 = v164.getAsString())) != null ? MoLangExtensionsKt.asExpression((String)v164) : null;
                                                                                    v165 = it;
                                                                                    if (v165 == null) {
                                                                                        v165 = new NumberExpression(0.0);
                                                                                    }
                                                                                    value /* !! */  = v165;
                                                                                    v166 = var76_408 = flipbook.get("stretch_to_lifetime");
                                                                                    it = v166 != null ? ($i$a$-let-SnowstormParticleReader$loadEffect$curves$1$nodes$3$1 = v166.getAsBoolean()) : false;
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)destination$iv$iv, (String)"asExpression()");
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)it, (String)"asExpression()");
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)$i$a$-let-SnowstormParticleReader$loadEffect$viewDirection$1$2, (String)"asExpression()");
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)destination$iv$iv, (String)"asExpression()");
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)$i$f$mapTo, (String)"asExpression()");
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)$i$a$-map-SnowstormParticleReader$loadEffect$motion$directionExpressions$1, (String)"asExpression()");
                                                                                    v167 = new AnimatedParticleUVMode(destination$iv$iv, (Expression)it, item$iv$iv, it, (Expression)$i$a$-let-SnowstormParticleReader$loadEffect$viewDirection$1$2, (Expression)destination$iv$iv, (Expression)$i$f$mapTo, (Expression)$i$a$-map-SnowstormParticleReader$loadEffect$motion$directionExpressions$1, (Expression)it /* !! */ , (Expression)value /* !! */ , it, $i$a$-map-SnowstormParticleReader$loadEffect$viewDirection$1$1);
                                                                                } else {
                                                                                    baseUV = uvModeJson.get("uv").getAsJsonArray();
                                                                                    if (baseUV == null) {
                                                                                        it = sizeUV = new JsonArray();
                                                                                        $i$a$-also-SnowstormParticleReader$loadEffect$uvMode$uvJson$1 = false;
                                                                                        it.add((JsonElement)new JsonPrimitive("0"));
                                                                                        it.add((JsonElement)new JsonPrimitive("0"));
                                                                                        v168 = sizeUV;
                                                                                    } else {
                                                                                        v168 = baseUV;
                                                                                    }
                                                                                    uvJson = v168;
                                                                                    v169 = it /* !! */  = uvModeJson.get("uv_size");
                                                                                    v170 /* !! */  = sizeUV /* !! */  = v169 != null ? v169.getAsJsonArray() : null;
                                                                                    if (sizeUV /* !! */  == null) {
                                                                                        it = it /* !! */  = new JsonArray();
                                                                                        $i$a$-also-SnowstormParticleReader$loadEffect$uvMode$uvSizeJson$1 = false;
                                                                                        it.add((JsonElement)new JsonPrimitive("128"));
                                                                                        it.add((JsonElement)new JsonPrimitive("128"));
                                                                                        v171 = it /* !! */ ;
                                                                                    } else {
                                                                                        v171 = sizeUV /* !! */ ;
                                                                                    }
                                                                                    uvSizeJson = v171;
                                                                                    it /* !! */  = uvJson.get(0).getAsString();
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)it /* !! */ , (String)"uvJson[0].asString");
                                                                                    sizeUV /* !! */  = MoLangExtensionsKt.asExpression((String)it /* !! */ );
                                                                                    it = uvJson.get(1).getAsString();
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)it, (String)"uvJson[1].asString");
                                                                                    it /* !! */  = MoLangExtensionsKt.asExpression((String)it);
                                                                                    $i$a$-also-SnowstormParticleReader$loadEffect$uvMode$uvSizeJson$1 = uvSizeJson.get(0).getAsString();
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)$i$a$-also-SnowstormParticleReader$loadEffect$uvMode$uvSizeJson$1, (String)"uvSizeJson[0].asString");
                                                                                    it = MoLangExtensionsKt.asExpression((String)$i$a$-also-SnowstormParticleReader$loadEffect$uvMode$uvSizeJson$1);
                                                                                    $i$a$-let-SnowstormParticleReader$loadEffect$viewDirection$1$2 = uvSizeJson.get(1).getAsString();
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)$i$a$-let-SnowstormParticleReader$loadEffect$viewDirection$1$2, (String)"uvSizeJson[1].asString");
                                                                                    $i$a$-also-SnowstormParticleReader$loadEffect$uvMode$uvSizeJson$1 = MoLangExtensionsKt.asExpression($i$a$-let-SnowstormParticleReader$loadEffect$viewDirection$1$2);
                                                                                    v172 = destination$iv$iv = uvModeJson.get("texture_width");
                                                                                    $i$a$-let-SnowstormParticleReader$loadEffect$viewDirection$1$2 = v172 != null ? ($i$f$mapTo = v172.getAsInt()) : 128;
                                                                                    v173 = $i$f$mapTo = uvModeJson.get("texture_height");
                                                                                    destination$iv$iv = v173 != null ? ($i$a$-map-SnowstormParticleReader$loadEffect$motion$directionExpressions$1 = v173.getAsInt()) : 128;
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)sizeUV /* !! */ , (String)"asExpression()");
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)it /* !! */ , (String)"asExpression()");
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)it, (String)"asExpression()");
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)$i$a$-also-SnowstormParticleReader$loadEffect$uvMode$uvSizeJson$1, (String)"asExpression()");
                                                                                    v167 = uvMode = (ParticleUVMode)new StaticParticleUVMode((Expression)sizeUV /* !! */ , (Expression)it /* !! */ , $i$a$-let-SnowstormParticleReader$loadEffect$viewDirection$1$2, destination$iv$iv, (Expression)it, (Expression)$i$a$-also-SnowstormParticleReader$loadEffect$uvMode$uvSizeJson$1);
                                                                                }
                                                                                if ((v174 = dynamicMotionJson) == null) {
                                                                                    v174 = parametricMotionJson;
                                                                                }
                                                                                v175 = (v176 = (motionJson = v174)) != null && (v176 = (sizeUV /* !! */  = v176.get("rotation"))) != null && (v176 = (it /* !! */  = v176.getAsString())) != null ? MoLangExtensionsKt.asExpression((String)v176) : (parametricParticleRotation = null);
                                                                                if (parametricParticleRotation != null) {
                                                                                    v177 = new ParametricParticleRotation(parametricParticleRotation);
                                                                                } else {
                                                                                    v178 /* !! */  = dynamicMotionJson;
                                                                                    it /* !! */  = v178 /* !! */  != null && (v178 /* !! */  = (it = v178 /* !! */ .get("rotation_acceleration"))) != null && (v178 /* !! */  = ($i$a$-also-SnowstormParticleReader$loadEffect$uvMode$uvSizeJson$1 = v178 /* !! */ .getAsString())) != null ? MoLangExtensionsKt.asExpression((String)v178 /* !! */ ) : null;
                                                                                    v179 /* !! */  = it /* !! */ ;
                                                                                    if (v179 /* !! */  == null) {
                                                                                        v179 /* !! */  = MoLangExtensionsKt.asExpression(0.0);
                                                                                    }
                                                                                    if ((v180 /* !! */  = (it /* !! */  = (v181 /* !! */  = dynamicMotionJson) != null && (v181 /* !! */  = (it = v181 /* !! */ .get("rotation_drag_coefficient"))) != null && (v181 /* !! */  = ($i$a$-also-SnowstormParticleReader$loadEffect$uvMode$uvSizeJson$1 = v181 /* !! */ .getAsString())) != null ? MoLangExtensionsKt.asExpression((String)v181 /* !! */ ) : null)) == null) {
                                                                                        v180 /* !! */  = MoLangExtensionsKt.asExpression(0.0);
                                                                                    }
                                                                                    v177 = rotation = (ParticleRotation)new DynamicParticleRotation(startRotation, (Expression)rotationSpeed, (Expression)v179 /* !! */ , (Expression)v180 /* !! */ );
                                                                                }
                                                                                if (colourJson instanceof JsonObject) {
                                                                                    $i$a$-also-SnowstormParticleReader$loadEffect$uvMode$uvSizeJson$1 = ((JsonObject)colourJson).get("interpolant").getAsString();
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)$i$a$-also-SnowstormParticleReader$loadEffect$uvMode$uvSizeJson$1, (String)"colourJson.get(\"interpolant\").asString");
                                                                                    it = MoLangExtensionsKt.asExpression((String)$i$a$-also-SnowstormParticleReader$loadEffect$uvMode$uvSizeJson$1);
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)it, (String)"colourJson.get(\"interpol\u2026).asString.asExpression()");
                                                                                    v182 = it;
                                                                                    it = ((JsonObject)colourJson).get("gradient").getAsJsonObject().entrySet();
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)it, (String)"colourJson.get(\"gradient\").asJsonObject.entrySet()");
                                                                                    it = (Iterable)it;
                                                                                    var104_452 = v182;
                                                                                    $i$f$map = false;
                                                                                    $i$a$-let-SnowstormParticleReader$loadEffect$viewDirection$1$2 = $this$map$iv;
                                                                                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                    $i$f$mapTo = false;
                                                                                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                                                                                        it = (Map.Entry)item$iv$iv;
                                                                                        var105_453 = destination$iv$iv;
                                                                                        $i$a$-map-SnowstormParticleReader$loadEffect$tinting$1 = false;
                                                                                        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"(key, hex)");
                                                                                        key = (String)it.getKey();
                                                                                        hex = (JsonElement)it.getValue();
                                                                                        Intrinsics.checkNotNullExpressionValue((Object)key, (String)"key");
                                                                                        v183 = Double.parseDouble(key);
                                                                                        v184 = hex.getAsString();
                                                                                        Intrinsics.checkNotNullExpressionValue((Object)v184, (String)"hex.asString");
                                                                                        var106_454 = TuplesKt.to((Object)v183, (Object)SnowstormParticleReader.INSTANCE.parseHex(v184));
                                                                                        var105_453.add(var106_454);
                                                                                    }
                                                                                    var105_453 = (List)destination$iv$iv;
                                                                                    var108_455 = MapsKt.toMap((Iterable)var105_453);
                                                                                    var109_456 = var104_452;
                                                                                    v185 = new GradientParticleTinting((Expression)var109_456, var108_455);
                                                                                } else if (colourJson instanceof JsonArray) {
                                                                                    $this$map$iv /* !! */  = (Iterable)colourJson;
                                                                                    $i$f$map = false;
                                                                                    destination$iv$iv = $this$map$iv /* !! */ ;
                                                                                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv /* !! */ , (int)10));
                                                                                    $i$f$mapTo = false;
                                                                                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                                                                                        $i$a$-map-SnowstormParticleReader$loadEffect$tinting$1 = (JsonElement)item$iv$iv;
                                                                                        var102_449 = destination$iv$iv;
                                                                                        $i$a$-map-SnowstormParticleReader$loadEffect$tinting$arr$1 = false;
                                                                                        v186 = it.getAsString();
                                                                                        Intrinsics.checkNotNullExpressionValue((Object)v186, (String)"it.asString");
                                                                                        var103_450 = MoLangExtensionsKt.asExpression(v186);
                                                                                        var102_449.add(var103_450);
                                                                                    }
                                                                                    arr = (List)destination$iv$iv;
                                                                                    $this$map$iv /* !! */  = arr.get(0);
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)$this$map$iv /* !! */ , (String)"arr[0]");
                                                                                    v187 = (Expression)$this$map$iv /* !! */ ;
                                                                                    $this$map$iv /* !! */  = arr.get(1);
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)$this$map$iv /* !! */ , (String)"arr[1]");
                                                                                    v188 = (Expression)$this$map$iv /* !! */ ;
                                                                                    $this$map$iv /* !! */  = arr.get(2);
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)$this$map$iv /* !! */ , (String)"arr[2]");
                                                                                    v189 = (Expression)$this$map$iv /* !! */ ;
                                                                                    $this$map$iv /* !! */  = arr.get(3);
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)$this$map$iv /* !! */ , (String)"arr[3]");
                                                                                    v185 = new ExpressionParticleTinting(v187, v188, v189, (Expression)$this$map$iv /* !! */ );
                                                                                } else {
                                                                                    v185 = new ExpressionParticleTinting(null, null, null, null, 15, null);
                                                                                }
                                                                                tinting = v185;
                                                                                environmentLighting = componentsJson.has("minecraft:particle_appearance_lighting");
                                                                                v190 = collisionJson;
                                                                                if (v190 != null) {
                                                                                    it = $this$mapTo$iv$iv = v190;
                                                                                    $i$a$-let-SnowstormParticleReader$loadEffect$collision$1 = false;
                                                                                    collides = false;
                                                                                    collides = true;
                                                                                    v191 /* !! */  = it.get("collision_radius");
                                                                                    if (v191 /* !! */  != null && (v191 /* !! */  = (item$iv$iv = v191 /* !! */ .getAsString())) != null) {
                                                                                        Intrinsics.checkNotNullExpressionValue((Object)v191 /* !! */ , (String)"asString");
                                                                                        v192 = MoLangExtensionsKt.asExpression(item$iv$iv);
                                                                                    } else {
                                                                                        v192 = it = null;
                                                                                    }
                                                                                    if (it == null) {
                                                                                        $this$loadEffect_u24lambda_u2437_u24lambda_u2436 = SnowstormParticleReader.INSTANCE;
                                                                                        $i$a$-run-SnowstormParticleReader$loadEffect$collision$1$radius$1 = false;
                                                                                        collides = false;
                                                                                        v193 = new NumberExpression(0.1);
                                                                                    } else {
                                                                                        v193 = radius = it;
                                                                                    }
                                                                                    if ((v194 /* !! */  = it.get("enabled")) != null && (v194 /* !! */  = ($this$loadEffect_u24lambda_u2437_u24lambda_u2436 = v194 /* !! */ .getAsString())) != null) {
                                                                                        Intrinsics.checkNotNullExpressionValue((Object)v194 /* !! */ , (String)"asString");
                                                                                        v195 = MoLangExtensionsKt.asExpression($this$loadEffect_u24lambda_u2437_u24lambda_u2436);
                                                                                    } else {
                                                                                        v195 = null;
                                                                                    }
                                                                                    v196 = it = v195;
                                                                                    if (v196 == null) {
                                                                                        v197 = new NumberExpression(collides != false ? 1.0 : 0.0);
                                                                                    } else {
                                                                                        Intrinsics.checkNotNullExpressionValue((Object)v196, (String)"it.get(\"enabled\")?.asStr\u2026 (collides) 1.0 else 0.0)");
                                                                                        v197 = it;
                                                                                    }
                                                                                    if ((v198 /* !! */  = it.get("collision_drag")) != null && (v198 /* !! */  = ($this$loadEffect_u24lambda_u2437_u24lambda_u2436 = v198 /* !! */ .getAsString())) != null) {
                                                                                        Intrinsics.checkNotNullExpressionValue((Object)v198 /* !! */ , (String)"asString");
                                                                                        v199 = MoLangExtensionsKt.asExpression($this$loadEffect_u24lambda_u2437_u24lambda_u2436);
                                                                                    } else {
                                                                                        v199 = null;
                                                                                    }
                                                                                    v200 = it = v199;
                                                                                    if (v200 == null) {
                                                                                        v201 = new NumberExpression(10.0);
                                                                                    } else {
                                                                                        Intrinsics.checkNotNullExpressionValue((Object)v200, (String)"it.get(\"collision_drag\")\u2026?: NumberExpression(10.0)");
                                                                                        v201 = it;
                                                                                    }
                                                                                    if ((v202 /* !! */  = it.get("coefficient_of_restitution")) != null && (v202 /* !! */  = ($this$loadEffect_u24lambda_u2437_u24lambda_u2436 = v202 /* !! */ .getAsString())) != null) {
                                                                                        Intrinsics.checkNotNullExpressionValue((Object)v202 /* !! */ , (String)"asString");
                                                                                        v203 = MoLangExtensionsKt.asExpression($this$loadEffect_u24lambda_u2437_u24lambda_u2436);
                                                                                    } else {
                                                                                        v203 = null;
                                                                                    }
                                                                                    v204 = it = v203;
                                                                                    if (v204 == null) {
                                                                                        v205 = new NumberExpression(0.0);
                                                                                    } else {
                                                                                        Intrinsics.checkNotNullExpressionValue((Object)v204, (String)"it.get(\"coefficient_of_r\u2026 ?: NumberExpression(0.0)");
                                                                                        v205 = it;
                                                                                    }
                                                                                    v206 = it.get("expire_on_contact");
                                                                                    v207 = $i$f$map = new ParticleCollision(v197, radius, v201, v205, v206 != null ? v206.getAsBoolean() : false);
                                                                                } else {
                                                                                    v207 = new ParticleCollision(null, null, null, null, false, 31, null);
                                                                                }
                                                                                collision = v207;
                                                                                v208 = spaceJson;
                                                                                if (v208 != null) {
                                                                                    it = it = v208;
                                                                                    $i$a$-let-SnowstormParticleReader$loadEffect$space$1 = false;
                                                                                    v209 = it.get("rotation");
                                                                                    v210 = spaceRotation = v209 != null ? v209.getAsBoolean() : false;
                                                                                    if (spaceRotation) {
                                                                                        v211 = true;
                                                                                    } else {
                                                                                        v212 = it.get("position");
                                                                                        v211 = v212 != null ? v212.getAsBoolean() : false;
                                                                                    }
                                                                                    v213 = it.get("velocity");
                                                                                    v214 = $this$mapTo$iv$iv = new ParticleSpace(v211, spaceRotation, v213 != null ? v213.getAsBoolean() : false);
                                                                                } else {
                                                                                    v214 = new ParticleSpace(false, false, false, 7, null);
                                                                                }
                                                                                space = v214;
                                                                                v215 = particleLifetimeEventsJson;
                                                                                if (v215 == null) break block248;
                                                                                it = it = v215;
                                                                                $i$a$-let-SnowstormParticleReader$loadEffect$particleEventSet$1 = false;
                                                                                it = it.get("creation_event");
                                                                                v216 /* !! */  = it;
                                                                                if (v216 /* !! */  == null) break block249;
                                                                                Intrinsics.checkNotNullExpressionValue((Object)v216 /* !! */ , (String)"get(\"creation_event\")");
                                                                                v216 /* !! */  = GsonExtensionsKt.normalizeToArray(it);
                                                                                if (v216 /* !! */  == null) break block249;
                                                                                $this$map$iv = (Iterable)v216 /* !! */ ;
                                                                                $i$f$map = false;
                                                                                it = $this$map$iv;
                                                                                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                                $i$f$mapTo = false;
                                                                                for (E item$iv$iv : $this$mapTo$iv$iv) {
                                                                                    var80_457 = (JsonElement)item$iv$iv;
                                                                                    var81_465 = destination$iv$iv;
                                                                                    $i$a$-map-SnowstormParticleReader$loadEffect$particleEventSet$1$creationEvents$1 = false;
                                                                                    v217 = it.getAsString();
                                                                                    Intrinsics.checkNotNullExpressionValue((Object)v217, (String)"it.asString");
                                                                                    var81_465.add(new SimpleEventTrigger(v217));
                                                                                }
                                                                                v216 /* !! */  = CollectionsKt.toMutableList((Collection)((List)destination$iv$iv));
                                                                                if (v216 /* !! */  != null) break block250;
                                                                            }
                                                                            v216 /* !! */  = creationEvents /* !! */  = (List)new ArrayList<E>();
                                                                        }
                                                                        if ((v218 /* !! */  = (var84_479 = it.get("expiration_event"))) == null) break block251;
                                                                        Intrinsics.checkNotNullExpressionValue((Object)v218 /* !! */ , (String)"get(\"expiration_event\")");
                                                                        v218 /* !! */  = GsonExtensionsKt.normalizeToArray((JsonElement)var84_479);
                                                                        if (v218 /* !! */  == null) break block251;
                                                                        $this$map$iv = (Iterable)v218 /* !! */ ;
                                                                        $i$f$map = false;
                                                                        destination$iv$iv = $this$map$iv;
                                                                        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                        $i$f$mapTo = false;
                                                                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                                                                            $i$a$-map-SnowstormParticleReader$loadEffect$particleEventSet$1$creationEvents$1 = (JsonElement)item$iv$iv;
                                                                            var81_465 = destination$iv$iv;
                                                                            $i$a$-map-SnowstormParticleReader$loadEffect$particleEventSet$1$expirationEvents$1 = false;
                                                                            v219 = it.getAsString();
                                                                            Intrinsics.checkNotNullExpressionValue((Object)v219, (String)"it.asString");
                                                                            var81_465.add((SimpleEventTrigger)new SimpleEventTrigger(v219));
                                                                        }
                                                                        v218 /* !! */  = CollectionsKt.toMutableList((Collection)((List)destination$iv$iv));
                                                                        if (v218 /* !! */  != null) break block252;
                                                                    }
                                                                    v218 /* !! */  = expirationEvents /* !! */  = (List)new ArrayList<E>();
                                                                }
                                                                if ((v220 /* !! */  = (var86_485 /* !! */  = it.get("timeline"))) == null || (v220 /* !! */  = ($this$map$iv = v220 /* !! */ .getAsJsonObject())) == null || (v220 /* !! */  = ($this$map$iv = v220 /* !! */ .entrySet())) == null) break block253;
                                                                $this$map$iv = (Iterable)v220 /* !! */ ;
                                                                $i$f$map = false;
                                                                $i$f$mapTo = $this$map$iv;
                                                                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                $i$f$mapTo = false;
                                                                for (T item$iv$iv : $this$mapTo$iv$iv) {
                                                                    var87_486 = (Map.Entry)item$iv$iv;
                                                                    var81_465 = destination$iv$iv;
                                                                    $i$a$-map-SnowstormParticleReader$loadEffect$particleEventSet$1$timeline$1 = false;
                                                                    Intrinsics.checkNotNullExpressionValue((Object)var87_486, (String)"(key, value)");
                                                                    key = (String)var87_486.getKey();
                                                                    value = (JsonElement)var87_486.getValue();
                                                                    Intrinsics.checkNotNullExpressionValue((Object)key, (String)"key");
                                                                    v221 = Double.parseDouble(key);
                                                                    Intrinsics.checkNotNullExpressionValue((Object)value, (String)"value");
                                                                    var91_492 = (Iterable)GsonExtensionsKt.normalizeToArray(value);
                                                                    var92_496 = v221;
                                                                    $i$f$map = false;
                                                                    var94_501 = $this$map$iv;
                                                                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                                    $i$f$mapTo = false;
                                                                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                                                                        var99_508 = (JsonElement)item$iv$iv;
                                                                        var100_509 = destination$iv$iv;
                                                                        $i$a$-map-SnowstormParticleReader$loadEffect$particleEventSet$1$timeline$1$1 = false;
                                                                        var100_509.add(it.getAsString());
                                                                    }
                                                                    var81_465.add((Pair)TuplesKt.to(var92_496, (Object)CollectionsKt.toMutableList((Collection)((List)destination$iv$iv))));
                                                                }
                                                                $i$f$map = (List)destination$iv$iv;
                                                                $this$map$iv = MapsKt.toMap((Iterable)$i$f$map);
                                                                v220 /* !! */  = $this$map$iv;
                                                                if (v220 /* !! */  != null && (v220 /* !! */  = ($i$f$map = MapsKt.toMutableMap((Map)v220 /* !! */ ))) != null) break block254;
                                                            }
                                                            v220 /* !! */  = new LinkedHashMap<K, V>();
                                                        }
                                                        timeline /* !! */  = v220 /* !! */ ;
                                                        v222 = it = new BedrockParticle.EventSet((List<SimpleEventTrigger>)creationEvents /* !! */ , (List<SimpleEventTrigger>)expirationEvents /* !! */ , new EventTriggerTimeline((Map<Double, List<String>>)timeline /* !! */ ));
                                                        break block255;
                                                    }
                                                    v222 = particleEventSet = new BedrockParticle.EventSet((List<SimpleEventTrigger>)new ArrayList<E>(), (List<SimpleEventTrigger>)new ArrayList<E>(), new EventTriggerTimeline((Map<Double, List<String>>)new LinkedHashMap<K, V>()));
                                                }
                                                if ((v223 /* !! */  = emitterLifetimeEventsJson) == null || (v223 /* !! */  = (it = v223 /* !! */ .get("creation_event"))) == null || (v223 /* !! */  = (it = GsonExtensionsKt.normalizeToArray((JsonElement)v223 /* !! */ ))) == null) break block256;
                                                $this$map$iv = (Iterable)v223 /* !! */ ;
                                                $i$f$map = false;
                                                $this$map$iv = $this$map$iv;
                                                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                                $i$f$mapTo = false;
                                                for (T item$iv$iv : $this$mapTo$iv$iv) {
                                                    destination$iv$iv = (JsonElement)item$iv$iv;
                                                    var102_449 = destination$iv$iv;
                                                    $i$a$-map-SnowstormParticleReader$loadEffect$emitterCreationEvents$1 = false;
                                                    v224 = it.getAsString();
                                                    Intrinsics.checkNotNullExpressionValue((Object)v224, (String)"it.asString");
                                                    var103_450 = new SimpleEventTrigger(v224);
                                                    var102_449.add(var103_450);
                                                }
                                                $i$a$-let-SnowstormParticleReader$loadEffect$particleEventSet$1 = (List)destination$iv$iv;
                                                $this$map$iv = CollectionsKt.toMutableList((Collection)$i$a$-let-SnowstormParticleReader$loadEffect$particleEventSet$1);
                                                v223 /* !! */  = $this$map$iv;
                                                if (v223 /* !! */  != null) break block257;
                                            }
                                            v223 /* !! */  = emitterCreationEvents /* !! */  = (List)new ArrayList<E>();
                                        }
                                        if ((v225 /* !! */  = emitterLifetimeEventsJson) == null || (v225 /* !! */  = (it = v225 /* !! */ .get("expiration_event"))) == null || (v225 /* !! */  = ($i$a$-let-SnowstormParticleReader$loadEffect$particleEventSet$1 = GsonExtensionsKt.normalizeToArray((JsonElement)v225 /* !! */ ))) == null) break block258;
                                        $this$map$iv = (Iterable)v225 /* !! */ ;
                                        $i$f$map = false;
                                        destination$iv$iv = $this$map$iv;
                                        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                        $i$f$mapTo = false;
                                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                                            $i$a$-map-SnowstormParticleReader$loadEffect$emitterCreationEvents$1 = (JsonElement)item$iv$iv;
                                            var102_449 = destination$iv$iv;
                                            $i$a$-map-SnowstormParticleReader$loadEffect$emitterExpirationEvents$1 = false;
                                            v226 = it.getAsString();
                                            Intrinsics.checkNotNullExpressionValue((Object)v226, (String)"it.asString");
                                            var103_450 = new SimpleEventTrigger(v226);
                                            var102_449.add(var103_450);
                                        }
                                        $this$map$iv = (List)destination$iv$iv;
                                        var73_380 = CollectionsKt.toMutableList((Collection)$this$map$iv);
                                        v225 /* !! */  = var73_380;
                                        if (v225 /* !! */  != null) break block259;
                                    }
                                    v225 /* !! */  = emitterExpirationEvents /* !! */  = (List)new ArrayList<E>();
                                }
                                if ((v227 /* !! */  = emitterLifetimeEventsJson) == null || (v227 /* !! */  = ($i$a$-let-SnowstormParticleReader$loadEffect$particleEventSet$1 = v227 /* !! */ .get("travel_distance_events"))) == null || (v227 /* !! */  = ($this$map$iv = v227 /* !! */ .getAsJsonObject())) == null || (v227 /* !! */  = (var73_381 = v227 /* !! */ .entrySet())) == null) break block260;
                                $this$map$iv = (Iterable)v227 /* !! */ ;
                                $i$f$map = false;
                                $i$f$mapTo = $this$map$iv;
                                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                $i$f$mapTo = false;
                                for (T item$iv$iv : $this$mapTo$iv$iv) {
                                    it = (Map.Entry)item$iv$iv;
                                    var102_449 = destination$iv$iv;
                                    $i$a$-map-SnowstormParticleReader$loadEffect$emitterTravelDistanceEvents$1 = false;
                                    Intrinsics.checkNotNullExpressionValue((Object)it, (String)"(key, value)");
                                    key = (String)it.getKey();
                                    value = (JsonElement)it.getValue();
                                    Intrinsics.checkNotNullExpressionValue((Object)key, (String)"key");
                                    v228 = Double.parseDouble(key);
                                    Intrinsics.checkNotNullExpressionValue((Object)value, (String)"value");
                                    var86_485 /* !! */  = (Iterable)GsonExtensionsKt.normalizeToArray(value);
                                    var87_486 = v228;
                                    $i$f$map = false;
                                    key = $this$map$iv;
                                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                                    $i$f$mapTo = false;
                                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                                        $this$mapTo$iv$iv = (JsonElement)item$iv$iv;
                                        destination$iv$iv = destination$iv$iv;
                                        $i$a$-map-SnowstormParticleReader$loadEffect$emitterTravelDistanceEvents$1$1 = false;
                                        destination$iv$iv.add(it.getAsString());
                                    }
                                    var103_450 = TuplesKt.to((Object)var87_486, (Object)CollectionsKt.toMutableList((Collection)((List)destination$iv$iv)));
                                    var102_449.add(var103_450);
                                }
                                var74_391 = (List)destination$iv$iv;
                                $this$map$iv = MapsKt.toMap((Iterable)var74_391);
                                v227 /* !! */  = $this$map$iv;
                                if (v227 /* !! */  != null && (v227 /* !! */  = ($i$f$map = MapsKt.toMutableMap((Map)v227 /* !! */ ))) != null) break block261;
                            }
                            v227 /* !! */  = emitterTravelDistanceEvents /* !! */  = (Map)new LinkedHashMap<K, V>();
                        }
                        if ((v229 /* !! */  = emitterLifetimeEventsJson) == null || (v229 /* !! */  = ($this$map$iv = v229 /* !! */ .get("looping_travel_distance_events"))) == null || (v229 /* !! */  = (var73_382 = v229 /* !! */ .getAsJsonArray())) == null) break block262;
                        $this$map$iv = (Iterable)v229 /* !! */ ;
                        $i$f$map = false;
                        $this$mapTo$iv$iv = $this$map$iv;
                        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                        $i$f$mapTo = false;
                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                            it = (JsonElement)item$iv$iv;
                            var102_449 = destination$iv$iv;
                            $i$a$-map-SnowstormParticleReader$loadEffect$emitterLoopingTravelDistanceEvents$1 = false;
                            obj = it.getAsJsonObject();
                            distance = obj.get("distance").getAsDouble();
                            v230 = obj.get("events");
                            Intrinsics.checkNotNullExpressionValue((Object)v230, (String)"obj.get(\"events\")");
                            $this$map$iv = (Iterable)GsonExtensionsKt.normalizeToArray(v230);
                            $i$f$map = false;
                            $this$mapTo$iv$iv = $this$map$iv;
                            destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                            $i$f$mapTo = false;
                            for (T item$iv$iv : $this$mapTo$iv$iv) {
                                it = (JsonElement)item$iv$iv;
                                destination$iv$iv = destination$iv$iv;
                                $i$a$-map-SnowstormParticleReader$loadEffect$emitterLoopingTravelDistanceEvents$1$events$1 = false;
                                destination$iv$iv.add(it.getAsString());
                            }
                            events = CollectionsKt.toMutableList((Collection)((List)destination$iv$iv));
                            var103_450 = new LoopingTravelDistanceEventTrigger(distance, events);
                            var102_449.add(var103_450);
                        }
                        var74_392 = (List)destination$iv$iv;
                        var75_405 = CollectionsKt.toMutableList((Collection)var74_392);
                        v229 /* !! */  = var75_405;
                        if (v229 /* !! */  != null) break block263;
                    }
                    v229 /* !! */  = emitterLoopingTravelDistanceEvents /* !! */  = (List)new ArrayList<E>();
                }
                if ((v231 /* !! */  = emitterLifetimeEventsJson) == null || (v231 /* !! */  = (var73_383 = v231 /* !! */ .get("timeline"))) == null || (v231 /* !! */  = (var74_393 = v231 /* !! */ .getAsJsonObject())) == null || (v231 /* !! */  = (var75_406 = v231 /* !! */ .entrySet())) == null) break block264;
                $this$map$iv = (Iterable)v231 /* !! */ ;
                $i$f$map = false;
                $i$f$mapTo = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    var84_479 = (Map.Entry)item$iv$iv;
                    var104_452 = destination$iv$iv;
                    $i$a$-map-SnowstormParticleReader$loadEffect$emitterEventTimeline$1 = false;
                    Intrinsics.checkNotNullExpressionValue((Object)var84_479, (String)"(key, value)");
                    key = (String)var84_479.getKey();
                    value = (JsonElement)var84_479.getValue();
                    Intrinsics.checkNotNullExpressionValue((Object)key, (String)"key");
                    v232 = Double.parseDouble(key);
                    Intrinsics.checkNotNullExpressionValue((Object)value, (String)"value");
                    $i$f$map = (Iterable)GsonExtensionsKt.normalizeToArray(value);
                    var89_489 = v232;
                    $i$f$map = false;
                    $i$f$mapTo = $this$map$iv;
                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                    $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        $i$a$-map-SnowstormParticleReader$loadEffect$emitterLoopingTravelDistanceEvents$1$events$1 = (JsonElement)item$iv$iv;
                        var97_505 = destination$iv$iv;
                        $i$a$-map-SnowstormParticleReader$loadEffect$emitterEventTimeline$1$1 = false;
                        var97_505.add(it.getAsString());
                    }
                    var105_453 = TuplesKt.to((Object)var89_489, (Object)CollectionsKt.toMutableList((Collection)((List)destination$iv$iv)));
                    var104_452.add(var105_453);
                }
                var104_452 = (List)destination$iv$iv;
                var76_414 = var104_452;
                var77_427 = MapsKt.toMap((Iterable)((Iterable)var76_414));
                v231 /* !! */  = var77_427;
                if (v231 /* !! */  != null && (v231 /* !! */  = (var78_437 = MapsKt.toMutableMap((Map)v231 /* !! */ ))) != null) break block265;
            }
            v231 /* !! */  = new LinkedHashMap<K, V>();
        }
        var110_511 /* !! */  = v231 /* !! */ ;
        emitterEventTimeline = new EventTriggerTimeline((Map<Double, List<String>>)var110_511 /* !! */ );
        var73_384 = new BedrockParticleEmitter(CollectionsKt.toMutableList((Collection)emitterStartExpressions), CollectionsKt.toMutableList((Collection)emitterUpdateExpressions), rate, shape, lifetime, emitterEventTimeline, (List<SimpleEventTrigger>)emitterCreationEvents /* !! */ , (List<SimpleEventTrigger>)emitterExpirationEvents /* !! */ , new EventTriggerTimeline((Map<Double, List<String>>)emitterTravelDistanceEvents /* !! */ ), (List<LoopingTravelDistanceEventTrigger>)emitterLoopingTravelDistanceEvents /* !! */ );
        var74_394 = CollectionsKt.toMutableList((Collection)curves);
        var76_415 = CollectionsKt.toMutableList((Collection)particleUpdateExpressions);
        var77_428 = CollectionsKt.toMutableList((Collection)particleRenderExpressions);
        var78_438 = particleEventSet.getCreationEvents();
        var79_448 = particleEventSet.getExpirationEvents();
        var80_464 = particleEventSet.getTimeline();
        var75_407 = new BedrockParticle(texture, material, uvMode, (Expression)sizeX, (Expression)sizeY, maxAge, (Expression)killExpression, var76_415, var77_428, motion, rotation, viewDirection, cameraMode, tinting, collision, environmentLighting, var78_438, var79_448, var80_464);
        return new BedrockParticleEffect(id, var73_384, var75_407, var74_394, space, events);
    }

    private final Vector4f parseHex(String hex) {
        String cleaned = StringsKt.replace$default((String)hex, (String)"#", (String)"", (boolean)false, (int)4, null);
        String string = cleaned.substring(0, 2);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
        String alphaHex = string;
        String string2 = cleaned.substring(2, 4);
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
        String redHex = string2;
        String string3 = cleaned.substring(4, 6);
        Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
        String greenHex = string3;
        String string4 = cleaned.substring(6, 8);
        Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
        String blueHex = string4;
        return new Vector4f((float)Integer.parseInt(redHex, CharsKt.checkRadix((int)16)) / 255.0f, (float)Integer.parseInt(greenHex, CharsKt.checkRadix((int)16)) / 255.0f, (float)Integer.parseInt(blueHex, CharsKt.checkRadix((int)16)) / 255.0f, (float)Integer.parseInt(alphaHex, CharsKt.checkRadix((int)16)) / 255.0f);
    }

    /*
     * WARNING - void declaration
     */
    private static final void loadEffect$resolveDirection(Ref.ObjectRef<ParticleMotionDirection> direction, JsonObject json) {
        ParticleMotionDirection particleMotionDirection;
        JsonElement jsonElement = json.get("direction");
        if (jsonElement == null) {
            SnowstormParticleReader it = INSTANCE;
            boolean bl = false;
            direction.element = new OutwardsMotionDirection();
            return;
        }
        JsonElement directionProperty = jsonElement;
        Ref.ObjectRef<ParticleMotionDirection> objectRef = direction;
        if (directionProperty.isJsonArray()) {
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            JsonArray jsonArray = directionProperty.getAsJsonArray();
            Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"directionProperty.asJsonArray");
            Iterable iterable = (Iterable)jsonArray;
            Ref.ObjectRef<ParticleMotionDirection> objectRef2 = objectRef;
            boolean $i$f$map = false;
            void bl = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                JsonElement jsonElement2 = (JsonElement)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl2 = false;
                String string = it.getAsString();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"it.asString");
                collection.add(MoLangExtensionsKt.asExpression(string));
            }
            objectRef = objectRef2;
            List arr = (List)destination$iv$iv;
            particleMotionDirection = new CustomMotionDirection((Triple<? extends Expression, ? extends Expression, ? extends Expression>)new Triple(arr.get(0), arr.get(1), arr.get(2)));
        } else {
            String name = directionProperty.getAsString();
            particleMotionDirection = Intrinsics.areEqual((Object)name, (Object)"outwards") ? (ParticleMotionDirection)new OutwardsMotionDirection() : (ParticleMotionDirection)new InwardsMotionDirection();
        }
        objectRef.element = particleMotionDirection;
    }
}

