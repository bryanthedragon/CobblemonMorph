/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.QueryStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.Schedulable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.ClientMoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.PrimaryAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockParticleKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.QuirkData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import java.lang.invoke.LambdaMetafactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00b8\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003B\b\u00a2\u0006\u0005\b\u008a\u0001\u00101J\u001b\u0010\b\u001a\u00020\u00072\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\u0004\b\b\u0010\tJ\u001b\u0010\f\u001a\u00020\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\n\u00a2\u0006\u0004\b\f\u0010\rJJ\u0010\u0014\u001a\u00020\u00072\u0010\u0010\u0006\u001a\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u000e2)\b\u0002\u0010\u0013\u001a#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0000\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u00070\u000f\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\u0018\u001a\u00020\u00072\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0011\u0010\u001a\u001a\u0004\u0018\u00018\u0000H&\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u001f\u0010\u001f\u001a\u00020\u001e2\u0010\u0010\u001d\u001a\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u001c\u00a2\u0006\u0004\b\u001f\u0010 J\r\u0010!\u001a\u00020\u001e\u00a2\u0006\u0004\b!\u0010\"J\u000f\u0010#\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0004\b#\u0010$J\u0017\u0010&\u001a\u00020\u00072\u0006\u0010%\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b&\u0010'J=\u0010-\u001a\u00020,2.\u0010+\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0000\u0012\u00020*0)0(\"\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0000\u0012\u00020*0)\u00a2\u0006\u0004\b-\u0010.J=\u0010/\u001a\u00020,2.\u0010+\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0000\u0012\u00020*0)0(\"\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0000\u0012\u00020*0)\u00a2\u0006\u0004\b/\u0010.J\r\u00100\u001a\u00020\u0007\u00a2\u0006\u0004\b0\u00101J\u000f\u00102\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b2\u00101J%\u00106\u001a\u00020\u00072\u0006\u0010%\u001a\u00028\u00002\u0006\u00104\u001a\u0002032\u0006\u00105\u001a\u000203\u00a2\u0006\u0004\b6\u00107J\u0015\u00109\u001a\u00020\u00072\u0006\u00108\u001a\u00020\u0005\u00a2\u0006\u0004\b9\u0010:J=\u0010<\u001a\u00020\u00072.\u0010;\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0001\u0012\u00020*0\u000e0(\"\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0001\u0012\u00020*0\u000e\u00a2\u0006\u0004\b<\u0010=J'\u0010?\u001a\u00020,2\u0010\u0010\u001d\u001a\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u001c2\u0006\u0010>\u001a\u00020\u001e\u00a2\u0006\u0004\b?\u0010@J\u0017\u0010B\u001a\u00020\u00072\u0006\u0010A\u001a\u000203H\u0016\u00a2\u0006\u0004\bB\u0010CJ\u0015\u0010F\u001a\u00020\u00072\u0006\u0010E\u001a\u00020D\u00a2\u0006\u0004\bF\u0010GJ\u0017\u0010I\u001a\u00020\u00072\u0006\u0010H\u001a\u00020\u001eH&\u00a2\u0006\u0004\bI\u0010JR\"\u0010A\u001a\u0002038\u0004@\u0004X\u0084\u000e\u00a2\u0006\u0012\n\u0004\bA\u0010K\u001a\u0004\bL\u0010M\"\u0004\bN\u0010CR!\u0010R\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u000e0O8F\u00a2\u0006\u0006\u001a\u0004\bP\u0010QR\u0011\u0010T\u001a\u00020\u001e8F\u00a2\u0006\u0006\u001a\u0004\bS\u0010\"R:\u0010W\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010U2\u000e\u0010V\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010U8\u0006@FX\u0086\u000e\u00a2\u0006\u0012\n\u0004\bW\u0010X\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\R\"\u0010]\u001a\u00020\u001e8\u0004@\u0004X\u0084\u000e\u00a2\u0006\u0012\n\u0004\b]\u0010^\u001a\u0004\b_\u0010\"\"\u0004\b`\u0010JR$\u0010a\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\ba\u0010b\u001a\u0004\bc\u0010$\"\u0004\bd\u0010:R#\u0010g\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020f0e8\u0006\u00a2\u0006\f\n\u0004\bg\u0010h\u001a\u0004\bi\u0010jR\u001d\u0010m\u001a\b\u0012\u0004\u0012\u00020l0k8\u0006\u00a2\u0006\f\n\u0004\bm\u0010n\u001a\u0004\bo\u0010QR*\u0010\u000b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000b\u0010p\u001a\u0004\bq\u0010r\"\u0004\bs\u0010\rR\"\u0010t\u001a\u00020\u001e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bt\u0010^\u001a\u0004\bu\u0010\"\"\u0004\bv\u0010JR3\u0010y\u001a\u001e\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030w\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000x0e8\u0006\u00a2\u0006\f\n\u0004\by\u0010h\u001a\u0004\bz\u0010jR#\u0010|\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00160{8\u0006\u00a2\u0006\f\n\u0004\b|\u0010}\u001a\u0004\b~\u0010\u007fR\u001d\u0010\u0081\u0001\u001a\u00030\u0080\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u0081\u0001\u0010\u0082\u0001\u001a\u0006\b\u0083\u0001\u0010\u0084\u0001R*\u0010\u0085\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u000e0k8\u0006\u00a2\u0006\u000e\n\u0005\b\u0085\u0001\u0010n\u001a\u0005\b\u0086\u0001\u0010QR&\u0010\u0087\u0001\u001a\u00020\u001e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u0087\u0001\u0010^\u001a\u0005\b\u0088\u0001\u0010\"\"\u0005\b\u0089\u0001\u0010J\u00a8\u0006\u008b\u0001"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/api/scheduling/Schedulable;", "", "", "animation", "", "addFirstAnimation", "(Ljava/util/Set;)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/PrimaryAnimation;", "primaryAnimation", "addPrimaryAnimation", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/PrimaryAnimation;)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "state", "whenComplete", "addStatefulAnimation", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;Lkotlin/jvm/functions/Function1;)V", "Lkotlin/Function0;", "action", "doLater", "(Lkotlin/jvm/functions/Function0;)V", "getEntity", "()Lnet/minecraft/world/entity/Entity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "idleAnimation", "", "getIdleIntensity", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;)F", "getPartialTicks", "()F", "getPose", "()Ljava/lang/String;", "entity", "incrementAge", "(Lnet/minecraft/world/entity/Entity;)V", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "poses", "", "isNotPosedIn", "([Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)Z", "isPosedIn", "preRender", "()V", "reset", "", "previousAge", "newAge", "runEffects", "(Lnet/minecraft/world/entity/Entity;II)V", "pose", "setPose", "(Ljava/lang/String;)V", "animations", "setStatefulAnimations", "([Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;)V", "requiredIntensity", "shouldIdleRun", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;F)Z", "age", "updateAge", "(I)V", "Lnet/minecraft/world/phys/Vec3;", "position", "updateLocatorPosition", "(Lnet/minecraft/world/phys/Vec3;)V", "partialTicks", "updatePartialTicks", "(F)V", "I", "getAge", "()I", "setAge", "", "getAllStatefulAnimations", "()Ljava/util/List;", "allStatefulAnimations", "getAnimationSeconds", "animationSeconds", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "value", "currentModel", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "getCurrentModel", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "setCurrentModel", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;)V", "currentPartialTicks", "F", "getCurrentPartialTicks", "setCurrentPartialTicks", "currentPose", "Ljava/lang/String;", "getCurrentPose", "setCurrentPose", "", "Lcom/cobblemon/mod/common/client/render/MatrixWrapper;", "locatorStates", "Ljava/util/Map;", "getLocatorStates", "()Ljava/util/Map;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockParticleKeyframe;", "poseParticles", "Ljava/util/List;", "getPoseParticles", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/PrimaryAnimation;", "getPrimaryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/PrimaryAnimation;", "setPrimaryAnimation", "primaryOverridePortion", "getPrimaryOverridePortion", "setPrimaryOverridePortion", "Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/QuirkData;", "quirks", "getQuirks", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "renderQueue", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "getRenderQueue", "()Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "getRuntime", "()Lcom/bedrockk/molang/runtime/MoLangRuntime;", "statefulAnimations", "getStatefulAnimations", "timeEnteredPose", "getTimeEnteredPose", "setTimeEnteredPose", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nPoseableEntityState.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PoseableEntityState.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,300:1\n76#2:301\n96#2,5:302\n1#3:307\n12744#4,2:308\n18987#4,2:310\n4098#4,11:312\n3792#4:341\n4307#4,2:342\n1360#5:323\n1446#5,5:324\n766#5:329\n857#5:330\n2624#5,3:331\n858#5:334\n1855#5,2:335\n1855#5,2:337\n1855#5,2:339\n1855#5,2:344\n1549#5:346\n1620#5,3:347\n1549#5:350\n1620#5,3:351\n*S KotlinDebug\n*F\n+ 1 PoseableEntityState.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState\n*L\n153#1:301\n153#1:302,5\n207#1:308,2\n208#1:310,2\n236#1:312,11\n279#1:341\n279#1:342,2\n237#1:323\n237#1:324,5\n238#1:329\n238#1:330\n238#1:331,3\n238#1:334\n239#1:335,2\n250#1:337,2\n277#1:339,2\n279#1:344,2\n117#1:346\n117#1:347,3\n121#1:350\n121#1:351,3\n*E\n"})
public abstract class PoseableEntityState<T extends Entity>
implements Schedulable {
    @Nullable
    private PoseableEntityModel<T> currentModel;
    @Nullable
    private String currentPose;
    @Nullable
    private PrimaryAnimation<T> primaryAnimation;
    @NotNull
    private final List<StatefulAnimation<T, ?>> statefulAnimations = new ArrayList();
    @NotNull
    private final Map<ModelQuirk<T, ?>, QuirkData<T>> quirks = new LinkedHashMap();
    @NotNull
    private final List<BedrockParticleKeyframe> poseParticles = new ArrayList();
    @NotNull
    private final MoLangRuntime runtime;
    private int age;
    private float currentPartialTicks;
    private float primaryOverridePortion;
    private float timeEnteredPose;
    @NotNull
    private final Map<String, MatrixWrapper> locatorStates;
    @NotNull
    private final ConcurrentLinkedQueue<Function0<Unit>> renderQueue;

    /*
     * WARNING - void declaration
     */
    public PoseableEntityState() {
        void runtime2;
        MoLangRuntime moLangRuntime;
        MoLangRuntime moLangRuntime2 = moLangRuntime = ClientMoLangFunctions.INSTANCE.setupClient(MoLangFunctions.INSTANCE.setup(new MoLangRuntime()));
        PoseableEntityState poseableEntityState = this;
        boolean bl = false;
        DoubleValue reusableAnimTime = new DoubleValue(0.0);
        MoLangEnvironment moLangEnvironment = runtime2.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"runtime.environment");
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)"anim_time", arg_0 -> PoseableEntityState.runtime$lambda$12$lambda$1(reusableAnimTime, this, arg_0)), TuplesKt.to((Object)"pose_type", arg_0 -> PoseableEntityState.runtime$lambda$12$lambda$2(this, arg_0)), TuplesKt.to((Object)"pose", arg_0 -> PoseableEntityState.runtime$lambda$12$lambda$3(this, arg_0)), TuplesKt.to((Object)"has_entity", arg_0 -> PoseableEntityState.runtime$lambda$12$lambda$4(this, arg_0)), TuplesKt.to((Object)"sound", arg_0 -> PoseableEntityState.runtime$lambda$12$lambda$5(this, arg_0)), TuplesKt.to((Object)"play_animation", arg_0 -> PoseableEntityState.runtime$lambda$12$lambda$6(this, (MoLangRuntime)runtime2, arg_0)), TuplesKt.to((Object)"particle", arg_0 -> PoseableEntityState.runtime$lambda$12$lambda$11(this, (MoLangRuntime)runtime2, arg_0))};
        MoLangFunctions.INSTANCE.addFunctions(MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null), MapsKt.mapOf((Pair[])pairArray));
        poseableEntityState.runtime = moLangRuntime;
        this.primaryOverridePortion = 1.0f;
        this.locatorStates = new LinkedHashMap();
        this.renderQueue = new ConcurrentLinkedQueue();
    }

    @Nullable
    public final PoseableEntityModel<T> getCurrentModel() {
        return this.currentModel;
    }

    public final void setCurrentModel(@Nullable PoseableEntityModel<T> value2) {
        this.currentModel = value2;
        MoLangEnvironment moLangEnvironment = this.runtime.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"runtime.environment");
        PoseableEntityModel<T> poseableEntityModel = value2;
        HashMap<String, Function<MoParams, Object>> hashMap = poseableEntityModel != null && (poseableEntityModel = poseableEntityModel.getFunctions()) != null ? ((QueryStruct)((Object)poseableEntityModel)).functions : null;
        MoLangFunctions.INSTANCE.addFunctions(MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null), hashMap == null ? (Map)new HashMap() : (Map)hashMap);
    }

    @Nullable
    public final String getCurrentPose() {
        return this.currentPose;
    }

    public final void setCurrentPose(@Nullable String string) {
        this.currentPose = string;
    }

    @Nullable
    public final PrimaryAnimation<T> getPrimaryAnimation() {
        return this.primaryAnimation;
    }

    public final void setPrimaryAnimation(@Nullable PrimaryAnimation<T> primaryAnimation2) {
        this.primaryAnimation = primaryAnimation2;
    }

    @NotNull
    public final List<StatefulAnimation<T, ?>> getStatefulAnimations() {
        return this.statefulAnimations;
    }

    @NotNull
    public final Map<ModelQuirk<T, ?>, QuirkData<T>> getQuirks() {
        return this.quirks;
    }

    @NotNull
    public final List<BedrockParticleKeyframe> getPoseParticles() {
        return this.poseParticles;
    }

    @NotNull
    public final MoLangRuntime getRuntime() {
        return this.runtime;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<StatefulAnimation<T, ?>> getAllStatefulAnimations() {
        void $this$flatMapTo$iv$iv;
        void $this$flatMap$iv;
        Map<ModelQuirk<T, ?>, QuirkData<T>> map = this.quirks;
        Collection collection = this.statefulAnimations;
        boolean $i$f$flatMap = false;
        void var3_4 = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        Iterator iterator = $this$flatMapTo$iv$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry element$iv$iv;
            Map.Entry it = element$iv$iv = iterator.next();
            boolean bl = false;
            Iterable list$iv$iv = ((QuirkData)it.getValue()).getAnimations();
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        return CollectionsKt.plus((Collection)collection, (Iterable)((List)destination$iv$iv));
    }

    protected final int getAge() {
        return this.age;
    }

    protected final void setAge(int n) {
        this.age = n;
    }

    protected final float getCurrentPartialTicks() {
        return this.currentPartialTicks;
    }

    protected final void setCurrentPartialTicks(float f) {
        this.currentPartialTicks = f;
    }

    public final float getPrimaryOverridePortion() {
        return this.primaryOverridePortion;
    }

    public final void setPrimaryOverridePortion(float f) {
        this.primaryOverridePortion = f;
    }

    @Nullable
    public abstract T getEntity();

    public final float getPartialTicks() {
        return this.currentPartialTicks;
    }

    public void updateAge(int age) {
        this.age = age;
    }

    public void incrementAge(@NotNull T entity2) {
        Intrinsics.checkNotNullParameter(entity2, (String)"entity");
        int previousAge = this.age;
        this.updateAge(this.age + 1);
        this.runEffects(entity2, previousAge, this.age);
        PrimaryAnimation<T> primaryAnimation2 = this.primaryAnimation;
        if (primaryAnimation2 == null) {
            return;
        }
        PrimaryAnimation<T> primaryAnimation3 = primaryAnimation2;
        if (primaryAnimation3.getStarted() + primaryAnimation3.getDuration() <= this.getAnimationSeconds()) {
            this.primaryAnimation = null;
            primaryAnimation3.getAfterAction().accept(Unit.INSTANCE);
        }
    }

    public abstract void updatePartialTicks(float var1);

    public void reset() {
        this.updateAge(0);
    }

    public final float getAnimationSeconds() {
        return ((float)this.age + this.getPartialTicks()) / 20.0f;
    }

    public final float getTimeEnteredPose() {
        return this.timeEnteredPose;
    }

    public final void setTimeEnteredPose(float f) {
        this.timeEnteredPose = f;
    }

    @NotNull
    public final Map<String, MatrixWrapper> getLocatorStates() {
        return this.locatorStates;
    }

    @NotNull
    public final ConcurrentLinkedQueue<Function0<Unit>> getRenderQueue() {
        return this.renderQueue;
    }

    public final void addFirstAnimation(@NotNull Set<String> animation) {
        StatefulAnimation<T, ?> statefulAnimation;
        block5: {
            Intrinsics.checkNotNullParameter(animation, (String)"animation");
            PoseableEntityModel<T> poseableEntityModel = this.currentModel;
            if (poseableEntityModel == null) {
                return;
            }
            PoseableEntityModel<T> model = poseableEntityModel;
            for (String it : (Iterable)animation) {
                boolean bl = false;
                StatefulAnimation<T, ?> statefulAnimation2 = model.getAnimation(this, it, this.runtime);
                if (statefulAnimation2 == null) continue;
                statefulAnimation = statefulAnimation2;
                break block5;
            }
            statefulAnimation = null;
        }
        if (statefulAnimation == null) {
            return;
        }
        StatefulAnimation<T, ?> animation2 = statefulAnimation;
        if (animation2 instanceof PrimaryAnimation) {
            this.addPrimaryAnimation((PrimaryAnimation)animation2);
        } else {
            PoseableEntityState.addStatefulAnimation$default(this, animation2, null, 2, null);
        }
    }

    public final boolean isPosedIn(Pose<T, ? super ModelFrame> ... poses) {
        boolean bl;
        block1: {
            Intrinsics.checkNotNullParameter(poses, (String)"poses");
            Pose<T, ? super ModelFrame>[] $this$any$iv = poses;
            boolean $i$f$any = false;
            int n = $this$any$iv.length;
            for (int i = 0; i < n; ++i) {
                Pose<T, ModelFrame> element$iv;
                Pose<T, ModelFrame> it = element$iv = $this$any$iv[i];
                boolean bl2 = false;
                if (!Intrinsics.areEqual((Object)it.getPoseName(), (Object)this.currentPose)) continue;
                bl = true;
                break block1;
            }
            bl = false;
        }
        return bl;
    }

    public final boolean isNotPosedIn(Pose<T, ? super ModelFrame> ... poses) {
        boolean bl;
        block1: {
            Intrinsics.checkNotNullParameter(poses, (String)"poses");
            Pose<T, ? super ModelFrame>[] $this$none$iv = poses;
            boolean $i$f$none = false;
            int n = $this$none$iv.length;
            for (int i = 0; i < n; ++i) {
                Pose<T, ModelFrame> element$iv;
                Pose<T, ModelFrame> it = element$iv = $this$none$iv[i];
                boolean bl2 = false;
                if (!Intrinsics.areEqual((Object)it.getPoseName(), (Object)this.currentPose)) continue;
                bl = false;
                break block1;
            }
            bl = true;
        }
        return bl;
    }

    public final void preRender() {
        while (this.renderQueue.peek() != null) {
            Function0<Unit> action2 = this.renderQueue.poll();
            action2.invoke();
        }
    }

    public final void doLater(@NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        this.renderQueue.offer(action2);
    }

    @Nullable
    public final String getPose() {
        return this.currentPose;
    }

    /*
     * Unable to fully structure code
     */
    public final void setPose(@NotNull String pose) {
        block11: {
            Intrinsics.checkNotNullParameter((Object)pose, (String)"pose");
            this.currentPose = pose;
            this.primaryOverridePortion = 1.0f;
            model = this.currentModel;
            if (model == null) break block11;
            v0 = model.getPose(pose);
            if (v0 == null) {
                return;
            }
            poseImpl = v0;
            this.poseParticles.removeIf((Predicate<Object>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, setPose$lambda$17(kotlin.jvm.functions.Function1 java.lang.Object ), (Ljava/lang/Object;)Z)((Function1)((Function1)new Function1<BedrockParticleKeyframe, Boolean>(poseImpl){
                final /* synthetic */ Pose<T, ? extends ModelFrame> $poseImpl;
                {
                    this.$poseImpl = $poseImpl;
                    super(1);
                }

                /*
                 * WARNING - void declaration
                 */
                @NotNull
                public final Boolean invoke(@NotNull BedrockParticleKeyframe particle) {
                    boolean bl;
                    block5: {
                        void $this$none$iv;
                        void $this$flatMapTo$iv$iv;
                        Iterable $this$filterIsInstanceTo$iv$iv;
                        Intrinsics.checkNotNullParameter((Object)particle, (String)"particle");
                        StatelessAnimation<T, ? extends ModelFrame>[] $this$filterIsInstance$iv = this.$poseImpl.getIdleAnimations();
                        boolean $i$f$filterIsInstance = false;
                        StatelessAnimation<T, ? extends ModelFrame>[] statelessAnimationArray = $this$filterIsInstance$iv;
                        Collection destination$iv$iv = new ArrayList<E>();
                        boolean $i$f$filterIsInstanceTo = false;
                        for (void element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                            if (!(element$iv$iv instanceof BedrockStatelessAnimation)) continue;
                            destination$iv$iv.add(element$iv$iv);
                        }
                        Iterable $this$flatMap$iv = (List)destination$iv$iv;
                        boolean $i$f$flatMap = false;
                        $this$filterIsInstanceTo$iv$iv = $this$flatMap$iv;
                        destination$iv$iv = new ArrayList<E>();
                        boolean $i$f$flatMapTo = false;
                        for (T element$iv$iv : $this$flatMapTo$iv$iv) {
                            BedrockStatelessAnimation it = (BedrockStatelessAnimation)element$iv$iv;
                            boolean bl2 = false;
                            Iterable list$iv$iv = it.getParticleKeyFrames();
                            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
                        }
                        $this$flatMap$iv = (List)destination$iv$iv;
                        BedrockParticleKeyframe bedrockParticleKeyframe = particle;
                        boolean $i$f$none = false;
                        if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                            bl = true;
                        } else {
                            for (T element$iv : $this$none$iv) {
                                BedrockParticleKeyframe p0 = (BedrockParticleKeyframe)element$iv;
                                boolean bl3 = false;
                                if (!bedrockParticleKeyframe.isSameAs(p0)) continue;
                                bl = false;
                                break block5;
                            }
                            bl = true;
                        }
                    }
                    return bl;
                }
            })));
            poseImpl.getOnTransitionedInto().invoke((Object)this);
            entity = this.getEntity();
            if (entity == null) break block11;
            var5_5 = poseImpl.getIdleAnimations();
            $i$f$filterIsInstance = false;
            var7_7 = $this$filterIsInstance$iv;
            destination$iv$iv = new ArrayList<E>();
            $i$f$filterIsInstanceTo = false;
            for (void element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof BedrockStatelessAnimation)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filterIsInstance$iv = (List)destination$iv$iv;
            $i$f$flatMap = false;
            $this$filterIsInstanceTo$iv$iv = $this$flatMap$iv;
            destination$iv$iv = new ArrayList<E>();
            $i$f$flatMapTo = false;
            for (T element$iv$iv : $this$flatMapTo$iv$iv) {
                it = (BedrockStatelessAnimation)element$iv$iv;
                $i$a$-flatMap-PoseableEntityState$setPose$2 = false;
                list$iv$iv = it.getParticleKeyFrames();
                CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
            }
            $this$flatMap$iv = (List)destination$iv$iv;
            $i$f$filter = false;
            $this$flatMapTo$iv$iv = $this$filter$iv;
            destination$iv$iv = new ArrayList<E>();
            $i$f$filterTo = false;
            for (T element$iv$iv : $this$filterTo$iv$iv) {
                block10: {
                    particle = (BedrockParticleKeyframe)element$iv$iv;
                    $i$a$-filter-PoseableEntityState$setPose$3 = false;
                    if (!(particle.getSeconds() == 0.0f)) ** GOTO lbl-1000
                    $this$none$iv = this.poseParticles;
                    $i$f$none = false;
                    if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                        v1 = true;
                    } else {
                        for (T element$iv : $this$none$iv) {
                            p0 = (BedrockParticleKeyframe)element$iv;
                            $i$a$-none-PoseableEntityState$setPose$3$1 = false;
                            if (!particle.isSameAs(p0)) continue;
                            v1 = false;
                            break block10;
                        }
                        v1 = true;
                    }
                }
                if (v1) {
                    v2 = true;
                } else lbl-1000:
                // 2 sources

                {
                    v2 = false;
                }
                if (!v2) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filter$iv = (List)destination$iv$iv;
            $i$f$forEach = false;
            for (T element$iv : $this$forEach$iv) {
                it = (BedrockParticleKeyframe)element$iv;
                $i$a$-forEach-PoseableEntityState$setPose$4 = false;
                it.run(entity, this);
            }
        }
    }

    public final void setStatefulAnimations(StatefulAnimation<T, ? extends ModelFrame> ... animations2) {
        Intrinsics.checkNotNullParameter(animations2, (String)"animations");
        this.statefulAnimations.clear();
        CollectionsKt.addAll((Collection)this.statefulAnimations, (Object[])animations2);
    }

    public final void updateLocatorPosition(@NotNull Vec3 position) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Iterable $this$forEach$iv = CollectionsKt.toList((Iterable)this.locatorStates.values());
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            MatrixWrapper it = (MatrixWrapper)element$iv;
            boolean bl = false;
            it.updatePosition(position);
        }
    }

    public final void addStatefulAnimation(@NotNull StatefulAnimation<T, ?> animation, @NotNull Function1<? super PoseableEntityState<T>, Unit> whenComplete) {
        Intrinsics.checkNotNullParameter(animation, (String)"animation");
        Intrinsics.checkNotNullParameter(whenComplete, (String)"whenComplete");
        this.statefulAnimations.add(animation);
        float duration = animation.getDuration();
        if (duration > 0.0f) {
            this.after((float)((int)(duration * 20.0f)) / 20.0f, (Function0<Unit>)((Function0)new Function0<Unit>(whenComplete, this){
                final /* synthetic */ Function1<PoseableEntityState<T>, Unit> $whenComplete;
                final /* synthetic */ PoseableEntityState<T> this$0;
                {
                    this.$whenComplete = $whenComplete;
                    this.this$0 = $receiver;
                    super(0);
                }

                public final void invoke() {
                    this.$whenComplete.invoke(this.this$0);
                }
            }));
        }
    }

    public static /* synthetic */ void addStatefulAnimation$default(PoseableEntityState poseableEntityState, StatefulAnimation statefulAnimation, Function1 function1, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addStatefulAnimation");
        }
        if ((n & 2) != 0) {
            function1 = addStatefulAnimation.1.INSTANCE;
        }
        poseableEntityState.addStatefulAnimation(statefulAnimation, function1);
    }

    public final void addPrimaryAnimation(@NotNull PrimaryAnimation<T> primaryAnimation2) {
        Intrinsics.checkNotNullParameter(primaryAnimation2, (String)"primaryAnimation");
        this.primaryAnimation = primaryAnimation2;
        this.statefulAnimations.clear();
        this.quirks.clear();
        this.primaryOverridePortion = 1.0f;
        primaryAnimation2.setStarted(this.getAnimationSeconds());
    }

    /*
     * WARNING - void declaration
     */
    public final void runEffects(@NotNull T entity2, int previousAge, int newAge) {
        block7: {
            StatelessAnimation<T, ModelFrame>[] it;
            StatelessAnimation<T, ModelFrame>[] statelessAnimationArray;
            Intrinsics.checkNotNullParameter(entity2, (String)"entity");
            float previousSeconds = (float)previousAge / 20.0f;
            float currentSeconds = (float)newAge / 20.0f;
            PoseableEntityModel<T> poseableEntityModel = this.currentModel;
            if (poseableEntityModel == null) break block7;
            PoseableEntityModel<T> model = poseableEntityModel;
            boolean bl = false;
            String string = this.currentPose;
            if (string != null) {
                String p0 = string;
                boolean bl2 = false;
                statelessAnimationArray = model.getPose(p0);
            } else {
                statelessAnimationArray = null;
            }
            StatelessAnimation<T, ModelFrame>[] pose = statelessAnimationArray;
            Iterable $this$forEach$iv = this.getAllStatefulAnimations();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                it = (StatelessAnimation<T, ModelFrame>[])element$iv;
                boolean bl3 = false;
                it.applyEffects(entity2, this, previousSeconds, currentSeconds);
            }
            StatefulAnimation<T, ModelFrame> statefulAnimation = this.primaryAnimation;
            if (statefulAnimation != null && (statefulAnimation = ((PrimaryAnimation)statefulAnimation).getAnimation()) != null) {
                statefulAnimation.applyEffects(entity2, this, previousSeconds, currentSeconds);
            }
            StatelessAnimation<T, ModelFrame>[] statelessAnimationArray2 = pose;
            if (pose != null && (statelessAnimationArray2 = statelessAnimationArray2.getIdleAnimations()) != null) {
                void $this$filterTo$iv$iv;
                StatelessAnimation<T, ModelFrame>[] $this$filter$iv = statelessAnimationArray2;
                boolean $i$f$filter = false;
                it = $this$filter$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterTo = false;
                int n = ((void)$this$filterTo$iv$iv).length;
                for (int i = 0; i < n; ++i) {
                    void element$iv$iv;
                    void it2 = element$iv$iv = $this$filterTo$iv$iv[i];
                    boolean bl4 = false;
                    if (!this.shouldIdleRun((StatelessAnimation<T, ?>)it2, 0.5f)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                Iterable $this$forEach$iv2 = (List)destination$iv$iv;
                boolean $i$f$forEach2 = false;
                for (Object element$iv : $this$forEach$iv2) {
                    StatelessAnimation it3 = (StatelessAnimation)element$iv;
                    boolean bl5 = false;
                    it3.applyEffects(entity2, this, previousSeconds, currentSeconds);
                }
            }
        }
    }

    public final boolean shouldIdleRun(@NotNull StatelessAnimation<T, ?> idleAnimation, float requiredIntensity) {
        Intrinsics.checkNotNullParameter(idleAnimation, (String)"idleAnimation");
        PrimaryAnimation<T> primaryAnimation2 = this.primaryAnimation;
        return primaryAnimation2 != null ? !primaryAnimation2.prevents(idleAnimation) || this.primaryOverridePortion > requiredIntensity : true;
    }

    public final float getIdleIntensity(@NotNull StatelessAnimation<T, ?> idleAnimation) {
        Intrinsics.checkNotNullParameter(idleAnimation, (String)"idleAnimation");
        PrimaryAnimation<T> primaryAnimation2 = this.primaryAnimation;
        return primaryAnimation2 != null && primaryAnimation2.prevents(idleAnimation) ? this.primaryOverridePortion : 1.0f;
    }

    @Override
    @NotNull
    public ScheduledTask momentarily(@NotNull Function0<Unit> action2) {
        return Schedulable.DefaultImpls.momentarily(this, action2);
    }

    @Override
    @NotNull
    public ScheduledTask after(float seconds, @NotNull Function0<Unit> action2) {
        return Schedulable.DefaultImpls.after(this, seconds, action2);
    }

    @Override
    @NotNull
    public ScheduledTask lerp(float seconds, @NotNull Function1<? super Float, Unit> action2) {
        return Schedulable.DefaultImpls.lerp(this, seconds, action2);
    }

    @Override
    @NotNull
    public ScheduledTask.Builder taskBuilder() {
        return Schedulable.DefaultImpls.taskBuilder(this);
    }

    private static final Object runtime$lambda$12$lambda$1(DoubleValue $reusableAnimTime, PoseableEntityState this$0, MoParams it) {
        DoubleValue doubleValue;
        Intrinsics.checkNotNullParameter((Object)$reusableAnimTime, (String)"$reusableAnimTime");
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        DoubleValue it2 = doubleValue = $reusableAnimTime;
        boolean bl = false;
        it2.value = this$0.getAnimationSeconds();
        return doubleValue;
    }

    private static final Object runtime$lambda$12$lambda$2(PoseableEntityState this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        Object t = this$0.getEntity();
        Intrinsics.checkNotNull(t, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable");
        return new StringValue(((Poseable)t).getCurrentPoseType().name());
    }

    private static final Object runtime$lambda$12$lambda$3(PoseableEntityState this$0, MoParams moParams) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)moParams, (String)"<anonymous parameter 0>");
        String string = this$0.currentPose;
        if (string == null) {
            string = "";
        }
        return new StringValue(string);
    }

    private static final Object runtime$lambda$12$lambda$4(PoseableEntityState this$0, MoParams moParams) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)moParams, (String)"<anonymous parameter 0>");
        return new DoubleValue(this$0.getEntity() != null);
    }

    private static final Unit runtime$lambda$12$lambda$5(PoseableEntityState this$0, MoParams params) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)params, (String)"params");
        Object t = this$0.getEntity();
        if (t == null) {
            return Unit.INSTANCE;
        }
        Object entity2 = t;
        if (!(params.get(0) instanceof StringValue)) {
            return Unit.INSTANCE;
        }
        String string = params.getString(0);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"params.getString(0)");
        SoundEvent soundEvent = SoundEvent.m_262824_((ResourceLocation)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(string, null, 1, null));
        if (soundEvent != null) {
            float volume = params.contains(1) ? (float)params.getDouble(1) : 1.0f;
            float pitch = params.contains(2) ? (float)params.getDouble(2) : 1.0f;
            Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)new SimpleSoundInstance(soundEvent, SoundSource.NEUTRAL, volume, pitch, entity2.m_9236_().f_46441_, entity2.m_20185_(), entity2.m_20186_(), entity2.m_20189_()));
        }
        return Unit.INSTANCE;
    }

    private static final Unit runtime$lambda$12$lambda$6(PoseableEntityState this$0, MoLangRuntime $runtime, MoParams params) {
        StatefulAnimation animation;
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)$runtime, (String)"$runtime");
        Intrinsics.checkNotNullParameter((Object)params, (String)"params");
        Object animationParameter = params.get(0);
        if (animationParameter instanceof ObjectValue) {
            Object t = ((ObjectValue)animationParameter).getObj();
            Intrinsics.checkNotNull(t, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation<T of bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState.runtime$lambda$12$lambda$6>");
            v1 = (BedrockStatefulAnimation)t;
        } else {
            PoseableEntityModel poseableEntityModel = this$0.currentModel;
            if (poseableEntityModel != null) {
                String string = animationParameter.asString();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"animationParameter.asString()");
                v1 = poseableEntityModel.getAnimation(this$0, string, $runtime);
            } else {
                v1 = animation = null;
            }
        }
        if (animation != null) {
            if (animation instanceof PrimaryAnimation) {
                this$0.addPrimaryAnimation((PrimaryAnimation)animation);
            } else {
                PoseableEntityState.addStatefulAnimation$default(this$0, animation, null, 2, null);
            }
        }
        return Unit.INSTANCE;
    }

    private static final Object runtime$lambda$12$lambda$11$lambda$10(MoLangRuntime $runtime, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)$runtime, (String)"$runtime");
        MoLangEnvironment moLangEnvironment = $runtime.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"runtime.environment");
        return MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null);
    }

    private static final Unit runtime$lambda$12$lambda$11(PoseableEntityState this$0, MoLangRuntime $runtime, MoParams params) {
        String it;
        Iterable $this$mapTo$iv$iv;
        boolean $i$f$mapTo;
        Collection destination$iv$iv;
        Iterable $this$map$iv;
        boolean $i$f$map;
        Collection<ResourceLocation> collection;
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)$runtime, (String)"$runtime");
        Intrinsics.checkNotNullParameter((Object)params, (String)"params");
        Object particlesParam = params.get(0);
        List particles = new ArrayList();
        Object t = particlesParam;
        if (t instanceof StringValue) {
            String string = ((StringValue)particlesParam).value;
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"particlesParam.value");
            particles.add(string);
        } else if (t instanceof VariableStruct) {
            Iterable iterable = ((VariableStruct)particlesParam).getMap().values();
            collection = particles;
            $i$f$map = false;
            Iterable iterable2 = $this$map$iv;
            destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                MoValue moValue = (MoValue)item$iv$iv;
                Collection collection2 = destination$iv$iv;
                boolean bl = false;
                collection2.add(it.asString());
            }
            collection.addAll((List)destination$iv$iv);
        } else {
            return Unit.INSTANCE;
        }
        $this$map$iv = particles;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            it = (String)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(it, null, 1, null));
        }
        List effectIds = (List)destination$iv$iv;
        for (ResourceLocation effectId : effectIds) {
            BedrockParticleEffect effect;
            Object entity2;
            String locator;
            String string = locator = params.getParams().size() > 1 ? params.getString(1) : "root";
            if (BedrockParticleEffectRepository.INSTANCE.getEffect(effectId) == null) {
                PoseableEntityState $this$runtime_u24lambda_u2412_u24lambda_u2411_u24lambda_u249 = this$0;
                boolean bl = false;
                Cobblemon.INSTANCE.getLOGGER().error("Unable to find a particle effect with id " + effectId);
                return Unit.INSTANCE;
            }
            if (this$0.getEntity() == null) {
                return Unit.INSTANCE;
            }
            Level level = entity2.m_9236_();
            Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.client.world.ClientWorld");
            ClientLevel world = (ClientLevel)level;
            MatrixWrapper matrixWrapper = this$0.locatorStates.get(locator);
            if (matrixWrapper == null) {
                MatrixWrapper matrixWrapper2 = this$0.locatorStates.get("root");
                Intrinsics.checkNotNull((Object)matrixWrapper2);
                matrixWrapper = matrixWrapper2;
            }
            MatrixWrapper matrixWrapper3 = matrixWrapper;
            MoLangRuntime particleRuntime = ClientMoLangFunctions.INSTANCE.setupClient(MoLangFunctions.INSTANCE.setup(new MoLangRuntime()));
            MoLangEnvironment moLangEnvironment = particleRuntime.getEnvironment();
            Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"particleRuntime.environment");
            MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null).addFunction("entity", arg_0 -> PoseableEntityState.runtime$lambda$12$lambda$11$lambda$10($runtime, arg_0));
            ParticleStorm storm2 = new ParticleStorm(effect, matrixWrapper3, world, (Function0)new Function0<Vec3>(entity2){
                final /* synthetic */ T $entity;
                {
                    this.$entity = $entity;
                    super(0);
                }

                @NotNull
                public final Vec3 invoke() {
                    Vec3 vec3 = this.$entity.m_20184_();
                    Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"entity.velocity");
                    return vec3;
                }
            }, (Function0)new Function0<Boolean>(entity2){
                final /* synthetic */ T $entity;
                {
                    this.$entity = $entity;
                    super(0);
                }

                @NotNull
                public final Boolean invoke() {
                    return !this.$entity.m_213877_();
                }
            }, (Function0)new Function0<Boolean>(entity2){
                final /* synthetic */ T $entity;
                {
                    this.$entity = $entity;
                    super(0);
                }

                @NotNull
                public final Boolean invoke() {
                    return !this.$entity.m_20145_();
                }
            }, null, particleRuntime, (Entity)entity2, 64, null);
            storm2.spawn();
        }
        return Unit.INSTANCE;
    }

    private static final boolean setPose$lambda$17(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

