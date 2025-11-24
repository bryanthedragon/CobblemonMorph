/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.blaze3d.vertex.VertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  com.mojang.math.Axis
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$ObjectRef
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.TypeIntrinsics
 *  kotlin.random.Random
 *  kotlin.ranges.IntRange
 *  kotlin.text.StringsKt
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderStateShard
 *  net.minecraft.client.renderer.RenderStateShard$EmptyTextureStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TextureStateShard
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.RenderType$CompositeRenderType
 *  net.minecraft.client.renderer.RenderType$CompositeState
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.ArrayStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.QueryStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.PokemonSideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.ClientMoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelLayer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelTextureSupplier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.CobblemonRenderLayers;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.LocatorAccess;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.BimanualSwingAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.BipedWalkAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.PoseTransitionAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.PrimaryAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.QuadrupedWalkAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.RotationFunctionStatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.TranslationFunctionStatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WingFlapIdleAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimationRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00cc\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u001c\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010$\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b!\n\u0002\u0010!\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0019\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u00032\u00020\u0004B \u0012\u0015\b\u0002\u0010\u00fb\u0001\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020%0N\u00a2\u0006\u0006\b\u00fc\u0001\u0010\u00fd\u0001J\u001f\u0010\n\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\n\u0010\u000bJ-\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0010\u0010\u0011J-\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00000\u00122\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0013\u0010\u0014J!\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u0003\u0018\u00010\u00162\u0006\u0010\u0015\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0017\u0010\u0018J5\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u0003\u0018\u00010\u00162\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u00192\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001c\u00a2\u0006\u0004\b\u001e\u0010\u001fJ%\u0010&\u001a\u00020%2\u0006\u0010!\u001a\u00020 2\u0006\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\"\u00a2\u0006\u0004\b&\u0010'J\u001b\u0010*\u001a\u0004\u0018\u00010)2\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u0016\u00a2\u0006\u0004\b*\u0010+J\u0015\u0010-\u001a\u00020,2\u0006\u0010\u001b\u001a\u00020\u0005\u00a2\u0006\u0004\b-\u0010.J!\u00102\u001a\u0002012\u0012\u00100\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050/\"\u00020\u0005\u00a2\u0006\u0004\b2\u00103J%\u00106\u001a\u0012\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0001\u0012\u00020\u0004\u0018\u0001052\u0006\u0010\u0006\u001a\u000204\u00a2\u0006\u0004\b6\u00107J%\u00106\u001a\u0012\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0001\u0012\u00020\u0004\u0018\u0001052\u0006\u0010\u001b\u001a\u00020\u0005\u00a2\u0006\u0004\b6\u00108J\u001d\u00109\u001a\b\u0012\u0004\u0012\u00028\u00000\u00192\u0006\u0010(\u001a\u00028\u0000H&\u00a2\u0006\u0004\b9\u0010:J\r\u0010;\u001a\u00020\t\u00a2\u0006\u0004\b;\u0010<J\u0015\u0010>\u001a\u00020\t2\u0006\u0010=\u001a\u000201\u00a2\u0006\u0004\b>\u0010?J\u0015\u0010>\u001a\u00020\t2\u0006\u0010@\u001a\u00020,\u00a2\u0006\u0004\b>\u0010AJ%\u0010D\u001a\u00020\t2\u0006\u0010@\u001a\u00020,2\f\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00050BH\u0002\u00a2\u0006\u0004\bD\u0010EJ%\u0010F\u001a\u00020%2\u0006\u0010!\u001a\u00020 2\u0006\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\"\u00a2\u0006\u0004\bF\u0010'J;\u0010H\u001a\u00020\t2\b\u0010(\u001a\u0004\u0018\u00018\u00002\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u00192\u0014\u0010G\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0001\u0012\u00020\u000405\u00a2\u0006\u0004\bH\u0010IJ\u0091\u0001\u0010R\u001a\b\u0012\u0004\u0012\u00028\u00000Q2\u0014\b\u0002\u0010K\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070J2\b\b\u0002\u0010M\u001a\u00020L2)\b\u0002\u0010P\u001a#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0019\u00a2\u0006\f\bO\u0012\b\b\u001b\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\"0N21\u0010\r\u001a-\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0019\u00a2\u0006\f\bO\u0012\b\b\u001b\u0012\u0004\b\b(\u001a\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u00160N\u00a2\u0006\u0004\bR\u0010SJp\u0010V\u001a\b\u0012\u0004\u0012\u00028\u00000Q2\u0014\b\u0002\u0010K\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070J2\b\b\u0002\u0010M\u001a\u00020L2\b\b\u0002\u0010U\u001a\u00020T21\u0010\r\u001a-\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0019\u00a2\u0006\f\bO\u0012\b\b\u001b\u0012\u0004\b\b(\u001a\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u00160N\u00a2\u0006\u0004\bV\u0010WJ\u0097\u0001\u0010Z\u001a\b\u0012\u0004\u0012\u00028\u00000Q2\u0014\b\u0002\u0010K\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070J2\b\b\u0002\u0010M\u001a\u00020L2)\b\u0002\u0010P\u001a#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0019\u00a2\u0006\f\bO\u0012\b\b\u001b\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\"0N27\u0010Y\u001a3\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0019\u00a2\u0006\f\bO\u0012\b\b\u001b\u0012\u0004\b\b(\u001a\u0012\u0014\u0012\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u00160X0N\u00a2\u0006\u0004\bZ\u0010SJ\u00c9\u0001\u0010f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u000105\"\b\b\u0001\u0010[*\u00020\u00042\u0006\u0010\\\u001a\u0002042\u0016\b\u0002\u0010P\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\"\u0018\u00010N2\b\b\u0002\u0010]\u001a\u00020)2\u0014\b\u0002\u0010Y\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020T0^2\u001c\b\u0002\u0010_\u001a\u0016\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0019\u0012\u0004\u0012\u00020\t0N2\u001c\b\u0002\u0010a\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0001\u0012\u00028\u00010`0/2\u000e\b\u0002\u0010c\u001a\b\u0012\u0004\u0012\u00020b0/2\u0018\b\u0002\u0010e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030d0/\u00a2\u0006\u0004\bf\u0010gJ\u00d1\u0001\u0010f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u000105\"\b\b\u0001\u0010[*\u00020\u00042\u0006\u0010h\u001a\u00020\u00052\u0006\u0010\\\u001a\u0002042\u0016\b\u0002\u0010P\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\"\u0018\u00010N2\b\b\u0002\u0010]\u001a\u00020)2\u0014\b\u0002\u0010Y\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020T0^2\u001c\b\u0002\u0010_\u001a\u0016\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0019\u0012\u0004\u0012\u00020\t0N2\u001c\b\u0002\u0010a\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0001\u0012\u00028\u00010`0/2\u000e\b\u0002\u0010c\u001a\b\u0012\u0004\u0012\u00020b0/2\u0018\b\u0002\u0010e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030d0/\u00a2\u0006\u0004\bf\u0010iJ\u00d7\u0001\u0010f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u000105\"\b\b\u0001\u0010[*\u00020\u00042\u0006\u0010h\u001a\u00020\u00052\f\u0010k\u001a\b\u0012\u0004\u0012\u0002040j2\u0016\b\u0002\u0010P\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\"\u0018\u00010N2\b\b\u0002\u0010]\u001a\u00020)2\u0014\b\u0002\u0010Y\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020T0^2\u001c\b\u0002\u0010_\u001a\u0016\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0019\u0012\u0004\u0012\u00020\t0N2\u001c\b\u0002\u0010a\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0001\u0012\u00028\u00010`0/2\u000e\b\u0002\u0010c\u001a\b\u0012\u0004\u0012\u00020b0/2\u0018\b\u0002\u0010e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030d0/\u00a2\u0006\u0004\bf\u0010lJ\u000f\u0010m\u001a\u00020\tH&\u00a2\u0006\u0004\bm\u0010<J!\u0010o\u001a\u00020,2\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020,0J\u00a2\u0006\u0004\bo\u0010pJ\u001d\u0010o\u001a\u00020,2\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010q\u001a\u00020,\u00a2\u0006\u0004\bo\u0010rJV\u0010\u007f\u001a\u00020\t2\u0006\u0010t\u001a\u00020s2\u0006\u0010v\u001a\u00020u2\u0006\u0010x\u001a\u00020w2\u0006\u0010y\u001a\u00020)2\u0006\u0010z\u001a\u00020)2\u0006\u0010{\u001a\u00020\u00072\u0006\u0010|\u001a\u00020\u00072\u0006\u0010}\u001a\u00020\u00072\u0006\u0010~\u001a\u00020\u0007\u00a2\u0006\u0005\b\u007f\u0010\u0080\u0001JP\u0010\u007f\u001a\u00020\t2\u0006\u0010v\u001a\u00020u2\u0006\u0010x\u001a\u00020w2\u0006\u0010y\u001a\u00020)2\u0006\u0010z\u001a\u00020)2\u0006\u0010{\u001a\u00020\u00072\u0006\u0010|\u001a\u00020\u00072\u0006\u0010}\u001a\u00020\u00072\u0006\u0010~\u001a\u00020\u0007H\u0016\u00a2\u0006\u0005\b\u007f\u0010\u0081\u0001J\u000f\u0010\u0082\u0001\u001a\u00020\t\u00a2\u0006\u0005\b\u0082\u0001\u0010<JD\u0010\u0085\u0001\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u0003\u0018\u00010\u00162\u0014\u0010\u0084\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020T0\u0083\u00012\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001cH\u0002\u00a2\u0006\u0006\b\u0085\u0001\u0010\u0086\u0001JG\u0010\u008c\u0001\u001a\u00020\t2\u0006\u0010(\u001a\u00028\u00002\u0007\u0010\u0087\u0001\u001a\u00020\u00072\u0007\u0010\u0088\u0001\u001a\u00020\u00072\u0007\u0010\u0089\u0001\u001a\u00020\u00072\u0007\u0010\u008a\u0001\u001a\u00020\u00072\u0007\u0010\u008b\u0001\u001a\u00020\u0007H\u0016\u00a2\u0006\u0006\b\u008c\u0001\u0010\u008d\u0001J\u000f\u0010\u008e\u0001\u001a\u00020\t\u00a2\u0006\u0005\b\u008e\u0001\u0010<J9\u0010\u0092\u0001\u001a\u00020\t2\u0007\u0010x\u001a\u00030\u008f\u00012\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00192\u000e\u0010\u0091\u0001\u001a\t\u0012\u0005\u0012\u00030\u0090\u00010B\u00a2\u0006\u0006\b\u0092\u0001\u0010\u0093\u0001J6\u0010\u0094\u0001\u001a\u00020\t\"\b\b\u0001\u0010[*\u00020\u00042\u0006\u0010h\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u000105\u00a2\u0006\u0006\b\u0094\u0001\u0010\u0095\u0001JU\u0010\u0096\u0001\u001a\u00020\t2\b\u0010(\u001a\u0004\u0018\u00018\u00002\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u00192\u0007\u0010\u0087\u0001\u001a\u00020\u00072\u0007\u0010\u0088\u0001\u001a\u00020\u00072\u0007\u0010\u0089\u0001\u001a\u00020\u00072\u0007\u0010\u008a\u0001\u001a\u00020\u00072\u0007\u0010\u008b\u0001\u001a\u00020\u0007\u00a2\u0006\u0006\b\u0096\u0001\u0010\u0097\u0001JO\u0010\u0098\u0001\u001a\u00020\t2\u0006\u0010\\\u001a\u0002042\t\b\u0002\u0010\u0087\u0001\u001a\u00020\u00072\t\b\u0002\u0010\u0088\u0001\u001a\u00020\u00072\t\b\u0002\u0010\u008a\u0001\u001a\u00020\u00072\t\b\u0002\u0010\u008b\u0001\u001a\u00020\u00072\t\b\u0002\u0010\u0089\u0001\u001a\u00020\u0007\u00a2\u0006\u0006\b\u0098\u0001\u0010\u0099\u0001JU\u0010\u0098\u0001\u001a\u00020\t2\f\u0010k\u001a\b\u0012\u0004\u0012\u0002040j2\t\b\u0002\u0010\u0087\u0001\u001a\u00020\u00072\t\b\u0002\u0010\u0088\u0001\u001a\u00020\u00072\t\b\u0002\u0010\u008a\u0001\u001a\u00020\u00072\t\b\u0002\u0010\u008b\u0001\u001a\u00020\u00072\t\b\u0002\u0010\u0089\u0001\u001a\u00020\u0007\u00a2\u0006\u0006\b\u0098\u0001\u0010\u009a\u0001J\u001c\u0010\u009b\u0001\u001a\u00020\t2\b\u0010(\u001a\u0004\u0018\u00018\u0000H\u0016\u00a2\u0006\u0006\b\u009b\u0001\u0010\u009c\u0001J\u001e\u0010\u009d\u0001\u001a\u00020\t2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u0019\u00a2\u0006\u0006\b\u009d\u0001\u0010\u009e\u0001JI\u0010\u00a1\u0001\u001a\u00020\t2\u0007\u0010x\u001a\u00030\u008f\u00012\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00192\u000e\u0010\u0091\u0001\u001a\t\u0012\u0005\u0012\u00030\u0090\u00010B2\u000e\u0010\u00a0\u0001\u001a\t\u0012\u0004\u0012\u00020\t0\u009f\u0001\u00a2\u0006\u0006\b\u00a1\u0001\u0010\u00a2\u0001J\u001c\u0010\u00a3\u0001\u001a\u00020,*\u00020,2\u0006\u0010\u001b\u001a\u00020\u0005\u00a2\u0006\u0006\b\u00a3\u0001\u0010\u00a4\u0001J*\u0010\u00a5\u0001\u001a\u00020,*\u00020,2\u0006\u0010\u001b\u001a\u00020\u00052\f\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00050B\u00a2\u0006\u0006\b\u00a5\u0001\u0010\u00a6\u0001J\u0099\u0001\u0010\u00ad\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000\u00ac\u0001*\u00020,2\u0018\u0010\u00a8\u0001\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070Nj\u0003`\u00a7\u00012\u0007\u0010\u00a9\u0001\u001a\u00020)2Y\u0010\u00ab\u0001\u001aT\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0019\u00a2\u0006\f\bO\u0012\b\b\u001b\u0012\u0004\b\b(\u001a\u0012\u0014\u0012\u00120\u0007\u00a2\u0006\r\bO\u0012\t\b\u001b\u0012\u0005\b\b(\u0087\u0001\u0012\u0014\u0012\u00120\u0007\u00a2\u0006\r\bO\u0012\t\b\u001b\u0012\u0005\b\b(\u0089\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00aa\u0001\u00a2\u0006\u0006\b\u00ad\u0001\u0010\u00ae\u0001J\u0099\u0001\u0010\u00b0\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000\u00af\u0001*\u00020,2\u0018\u0010\u00a8\u0001\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070Nj\u0003`\u00a7\u00012\u0007\u0010\u00a9\u0001\u001a\u00020)2Y\u0010\u00ab\u0001\u001aT\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0019\u00a2\u0006\f\bO\u0012\b\b\u001b\u0012\u0004\b\b(\u001a\u0012\u0014\u0012\u00120\u0007\u00a2\u0006\r\bO\u0012\t\b\u001b\u0012\u0005\b\b(\u0087\u0001\u0012\u0014\u0012\u00120\u0007\u00a2\u0006\r\bO\u0012\t\b\u001b\u0012\u0005\b\b(\u0089\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00aa\u0001\u00a2\u0006\u0006\b\u00b0\u0001\u0010\u00b1\u0001R(\u0010\u00b2\u0001\u001a\u00020\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0017\n\u0005\b\u00b2\u0001\u0010[\u001a\u0006\b\u00b3\u0001\u0010\u00b4\u0001\"\u0006\b\u00b5\u0001\u0010\u00b6\u0001R&\u0010Y\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020T0^8\u0006\u00a2\u0006\u000f\n\u0005\bY\u0010\u00b7\u0001\u001a\u0006\b\u00b8\u0001\u0010\u00b9\u0001R(\u0010\u00ba\u0001\u001a\u00020\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0017\n\u0005\b\u00ba\u0001\u0010[\u001a\u0006\b\u00bb\u0001\u0010\u00b4\u0001\"\u0006\b\u00bc\u0001\u0010\u00b6\u0001R,\u0010\u00bd\u0001\u001a\u0005\u0018\u00010\u008f\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00bd\u0001\u0010\u00be\u0001\u001a\u0006\b\u00bf\u0001\u0010\u00c0\u0001\"\u0006\b\u00c1\u0001\u0010\u00c2\u0001R\u001a\u0010t\u001a\u00020s8\u0006\u00a2\u0006\u000f\n\u0005\bt\u0010\u00c3\u0001\u001a\u0006\b\u00c4\u0001\u0010\u00c5\u0001R0\u0010\u00c6\u0001\u001a\t\u0012\u0005\u0012\u00030\u0090\u00010B8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00c6\u0001\u0010\u00c7\u0001\u001a\u0006\b\u00c8\u0001\u0010\u00c9\u0001\"\u0006\b\u00ca\u0001\u0010\u00cb\u0001R1\u0010\u00cc\u0001\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00198\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00cc\u0001\u0010\u00cd\u0001\u001a\u0006\b\u00ce\u0001\u0010\u00cf\u0001\"\u0006\b\u00d0\u0001\u0010\u009e\u0001R#\u0010\u00d2\u0001\u001a\t\u0012\u0004\u0012\u00020b0\u00d1\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u00d2\u0001\u0010\u00d3\u0001\u001a\u0006\b\u00d4\u0001\u0010\u00d5\u0001R(\u0010\u00d6\u0001\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u00168\u0006\u00a2\u0006\u0010\n\u0006\b\u00d6\u0001\u0010\u00d7\u0001\u001a\u0006\b\u00d8\u0001\u0010\u00d9\u0001R'\u0010\u00dc\u0001\u001a\r \u00db\u0001*\u0005\u0018\u00010\u00da\u00010\u00da\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u00dc\u0001\u0010\u00dd\u0001\u001a\u0006\b\u00de\u0001\u0010\u00df\u0001R(\u0010\u00e0\u0001\u001a\u00020\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0017\n\u0005\b\u00e0\u0001\u0010[\u001a\u0006\b\u00e1\u0001\u0010\u00b4\u0001\"\u0006\b\u00e2\u0001\u0010\u00b6\u0001R\u0017\u0010\u00e3\u0001\u001a\u00020\"8&X\u00a6\u0004\u00a2\u0006\b\u001a\u0006\b\u00e3\u0001\u0010\u00e4\u0001R*\u0010\u00e6\u0001\u001a\u00030\u00e5\u00018\u0006@\u0006X\u0086.\u00a2\u0006\u0018\n\u0006\b\u00e6\u0001\u0010\u00e7\u0001\u001a\u0006\b\u00e8\u0001\u0010\u00e9\u0001\"\u0006\b\u00ea\u0001\u0010\u00eb\u0001R6\u0010\u00ec\u0001\u001a\u001c\u0012\u0004\u0012\u00020\u0005\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\b\u0001\u0012\u00020\u0004050^8\u0006\u00a2\u0006\u0010\n\u0006\b\u00ec\u0001\u0010\u00b7\u0001\u001a\u0006\b\u00ed\u0001\u0010\u00b9\u0001R+\u0010e\u001a\u0013\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030d0\u00d1\u00018\u0006\u00a2\u0006\u000f\n\u0005\be\u0010\u00d3\u0001\u001a\u0006\b\u00ee\u0001\u0010\u00d5\u0001R(\u0010\u00ef\u0001\u001a\u00020\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0017\n\u0005\b\u00ef\u0001\u0010[\u001a\u0006\b\u00f0\u0001\u0010\u00b4\u0001\"\u0006\b\u00f1\u0001\u0010\u00b6\u0001R#\u0010\u00f2\u0001\u001a\t\u0012\u0004\u0012\u00020,0\u00d1\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u00f2\u0001\u0010\u00d3\u0001\u001a\u0006\b\u00f3\u0001\u0010\u00d5\u0001R(\u0010\u00f4\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020,0^8\u0006\u00a2\u0006\u0010\n\u0006\b\u00f4\u0001\u0010\u00b7\u0001\u001a\u0006\b\u00f5\u0001\u0010\u00b9\u0001R\u001a\u0010\u001d\u001a\u00020\u001c8\u0006\u00a2\u0006\u000f\n\u0005\b\u001d\u0010\u00f6\u0001\u001a\u0006\b\u00f7\u0001\u0010\u00f8\u0001R(\u0010\u00f9\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020T0^8\u0006\u00a2\u0006\u0010\n\u0006\b\u00f9\u0001\u0010\u00b7\u0001\u001a\u0006\b\u00fa\u0001\u0010\u00b9\u0001\u00a8\u0006\u00fe\u0001"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "Lnet/minecraft/world/entity/Entity;", "T", "Lnet/minecraft/client/model/EntityModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "", "pose", "", "intensity", "", "applyPose", "(Ljava/lang/String;F)Lkotlin/Unit;", "animationGroup", "animation", "animationPrefix", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatelessAnimation;", "bedrock", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatelessAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "bedrockStateful", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "string", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "extractAnimation", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "name", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "getAnimation", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;Ljava/lang/String;Lcom/bedrockk/molang/runtime/MoLangRuntime;)Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "Lnet/minecraft/resources/ResourceLocation;", "texture", "", "emissive", "translucent", "Lnet/minecraft/client/renderer/RenderType;", "getLayer", "(Lnet/minecraft/resources/ResourceLocation;ZZ)Lnet/minecraft/client/renderer/RenderType;", "entity", "", "getOverlayTexture", "(Lnet/minecraft/world/entity/Entity;)Ljava/lang/Integer;", "Lnet/minecraft/client/model/geom/ModelPart;", "getPart", "(Ljava/lang/String;)Lnet/minecraft/client/model/geom/ModelPart;", "", "names", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "getPartFallback", "([Ljava/lang/String;)Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "Lcom/cobblemon/mod/common/entity/PoseType;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getPose", "(Lcom/cobblemon/mod/common/entity/PoseType;)Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getState", "(Lnet/minecraft/world/entity/Entity;)Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "initializeLocatorAccess", "()V", "bone", "loadAllNamedChildren", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;)V", "modelPart", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "", "nameList", "loadSpecificNamedChildren", "(Lnet/minecraft/client/model/geom/ModelPart;Ljava/lang/Iterable;)V", "makeLayer", "desirablePose", "moveToPose", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "Lkotlin/Pair;", "secondsBetweenOccurrences", "Lkotlin/ranges/IntRange;", "loopTimes", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "condition", "Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/SimpleQuirk;", "quirk", "(Lkotlin/Pair;Lkotlin/ranges/IntRange;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/SimpleQuirk;", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "conditionExpression", "quirkMoLangCondition", "(Lkotlin/Pair;Lkotlin/ranges/IntRange;Lcom/cobblemon/mod/common/api/molang/ExpressionLike;Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/SimpleQuirk;", "", "animations", "quirkMultiple", "F", "poseType", "transformTicks", "", "onTransitionedInto", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "idleAnimations", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "transformedParts", "Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;", "quirks", "registerPose", "(Lcom/cobblemon/mod/common/entity/PoseType;Lkotlin/jvm/functions/Function1;ILjava/util/Map;Lkotlin/jvm/functions/Function1;[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;[Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;[Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;)Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "poseName", "(Ljava/lang/String;Lcom/cobblemon/mod/common/entity/PoseType;Lkotlin/jvm/functions/Function1;ILjava/util/Map;Lkotlin/jvm/functions/Function1;[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;[Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;[Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;)Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "", "poseTypes", "(Ljava/lang/String;Ljava/util/Set;Lkotlin/jvm/functions/Function1;ILjava/util/Map;Lkotlin/jvm/functions/Function1;[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;[Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;[Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;)Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "registerPoses", "pairing", "registerRelevantPart", "(Lkotlin/Pair;)Lnet/minecraft/client/model/geom/ModelPart;", "part", "(Ljava/lang/String;Lnet/minecraft/client/model/geom/ModelPart;)Lnet/minecraft/client/model/geom/ModelPart;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext;", "context", "Lcom/mojang/blaze3d/vertex/PoseStack;", "stack", "Lcom/mojang/blaze3d/vertex/VertexConsumer;", "buffer", "packedLight", "packedOverlay", "r", "g", "b", "a", "render", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V", "(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V", "resetLayerContext", "", "map", "resolveFromAnimationMap", "(Ljava/util/Map;Ljava/lang/String;Lcom/bedrockk/molang/runtime/MoLangRuntime;)Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "setAngles", "(Lnet/minecraft/world/entity/Entity;FFFFF)V", "setDefault", "Lnet/minecraft/client/renderer/MultiBufferSource;", "Lcom/cobblemon/mod/common/client/render/ModelLayer;", "layers", "setLayerContext", "(Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;Ljava/lang/Iterable;)V", "setPose", "(Ljava/lang/String;Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "setupAnimStateful", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFF)V", "setupAnimStateless", "(Lcom/cobblemon/mod/common/entity/PoseType;FFFFF)V", "(Ljava/util/Set;FFFFF)V", "setupEntityTypeContext", "(Lnet/minecraft/world/entity/Entity;)V", "updateLocators", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)V", "Lkotlin/Function0;", "action", "withLayerContext", "(Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;Ljava/lang/Iterable;Lkotlin/jvm/functions/Function0;)V", "registerChildWithAllChildren", "(Lnet/minecraft/client/model/geom/ModelPart;Ljava/lang/String;)Lnet/minecraft/client/model/geom/ModelPart;", "registerChildWithSpecificChildren", "(Lnet/minecraft/client/model/geom/ModelPart;Ljava/lang/String;Ljava/lang/Iterable;)Lnet/minecraft/client/model/geom/ModelPart;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/wavefunction/WaveFunction;", "function", "axis", "Lkotlin/Function3;", "timeVariable", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/RotationFunctionStatelessAnimation;", "rotation", "(Lnet/minecraft/client/model/geom/ModelPart;Lkotlin/jvm/functions/Function1;ILkotlin/jvm/functions/Function3;)Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/RotationFunctionStatelessAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/TranslationFunctionStatelessAnimation;", "translation", "(Lnet/minecraft/client/model/geom/ModelPart;Lkotlin/jvm/functions/Function1;ILkotlin/jvm/functions/Function3;)Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/TranslationFunctionStatelessAnimation;", "alpha", "getAlpha", "()F", "setAlpha", "(F)V", "Ljava/util/Map;", "getAnimations", "()Ljava/util/Map;", "blue", "getBlue", "setBlue", "bufferProvider", "Lnet/minecraft/client/renderer/MultiBufferSource;", "getBufferProvider", "()Lnet/minecraft/client/renderer/MultiBufferSource;", "setBufferProvider", "(Lnet/minecraft/client/renderer/MultiBufferSource;)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext;", "getContext", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext;", "currentLayers", "Ljava/lang/Iterable;", "getCurrentLayers", "()Ljava/lang/Iterable;", "setCurrentLayers", "(Ljava/lang/Iterable;)V", "currentState", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "getCurrentState", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "setCurrentState", "", "defaultPositions", "Ljava/util/List;", "getDefaultPositions", "()Ljava/util/List;", "dummyAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "getDummyAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "kotlin.jvm.PlatformType", "functions", "Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "getFunctions", "()Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "green", "getGreen", "setGreen", "isForLivingEntityRenderer", "()Z", "Lcom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess;", "locatorAccess", "Lcom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess;", "getLocatorAccess", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess;", "setLocatorAccess", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess;)V", "poses", "getPoses", "getQuirks", "red", "getRed", "setRed", "relevantParts", "getRelevantParts", "relevantPartsByName", "getRelevantPartsByName", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "getRuntime", "()Lcom/bedrockk/molang/runtime/MoLangRuntime;", "transitions", "getTransitions", "renderTypeFunc", "<init>", "(Lkotlin/jvm/functions/Function1;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPoseableEntityModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PoseableEntityModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,945:1\n1#2:946\n26#3:947\n26#3:948\n26#3:949\n26#3:950\n26#3:951\n26#3:952\n26#3:953\n26#3:954\n26#3:955\n26#3:966\n13579#4,2:956\n13579#4,2:962\n13579#4,2:972\n288#5,2:958\n1855#5,2:960\n288#5,2:964\n819#5:967\n847#5,2:968\n1855#5,2:970\n819#5:974\n847#5,2:975\n2624#5,3:977\n223#5,2:980\n1549#5:982\n1620#5,3:983\n1549#5:986\n1620#5,3:987\n*S KotlinDebug\n*F\n+ 1 PoseableEntityModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel\n*L\n409#1:947\n410#1:948\n411#1:949\n435#1:950\n436#1:951\n437#1:952\n461#1:953\n462#1:954\n463#1:955\n712#1:966\n644#1:956,2\n683#1:962,2\n737#1:972,2\n645#1:958,2\n649#1:960,2\n709#1:964,2\n735#1:967\n735#1:968,2\n735#1:970,2\n753#1:974\n753#1:975,2\n784#1:977,3\n807#1:980,2\n251#1:982\n251#1:983,3\n266#1:986\n266#1:987,3\n*E\n"})
public abstract class PoseableEntityModel<T extends Entity>
extends EntityModel<T>
implements ModelFrame {
    @NotNull
    private final RenderContext context;
    @NotNull
    private final Map<String, Pose<T, ? extends ModelFrame>> poses;
    public LocatorAccess locatorAccess;
    private float red;
    private float green;
    private float blue;
    private float alpha;
    @NotNull
    private transient Iterable<ModelLayer> currentLayers;
    @Nullable
    private transient MultiBufferSource bufferProvider;
    @Nullable
    private transient PoseableEntityState<T> currentState;
    @NotNull
    private final Map<String, ExpressionLike> animations;
    @NotNull
    private final Map<String, ExpressionLike> transitions;
    @NotNull
    private final List<ModelPartTransformation> defaultPositions;
    @NotNull
    private final List<ModelPart> relevantParts;
    @NotNull
    private final Map<String, ModelPart> relevantPartsByName;
    private final transient QueryStruct functions;
    @NotNull
    private final transient MoLangRuntime runtime;
    @NotNull
    private final List<ModelQuirk<T, ?>> quirks;
    @NotNull
    private final StatefulAnimation<T, ModelFrame> dummyAnimation;

    /*
     * WARNING - void declaration
     */
    public PoseableEntityModel(@NotNull Function1<? super ResourceLocation, ? extends RenderType> renderTypeFunc) {
        void it;
        MoLangRuntime moLangRuntime;
        Intrinsics.checkNotNullParameter(renderTypeFunc, (String)"renderTypeFunc");
        super(arg_0 -> PoseableEntityModel._init_$lambda$0(renderTypeFunc, arg_0));
        this.context = new RenderContext();
        this.poses = new LinkedHashMap();
        this.red = 1.0f;
        this.green = 1.0f;
        this.blue = 1.0f;
        this.alpha = 1.0f;
        this.currentLayers = CollectionsKt.emptyList();
        this.animations = new LinkedHashMap();
        this.transitions = new LinkedHashMap();
        this.defaultPositions = new ArrayList();
        this.relevantParts = new ArrayList();
        this.relevantPartsByName = new LinkedHashMap();
        this.functions = new QueryStruct(new HashMap<String, Function<MoParams, Object>>()).addFunction("bedrock_primary", arg_0 -> PoseableEntityModel.functions$lambda$1(this, arg_0)).addFunction("bedrock_stateful", arg_0 -> PoseableEntityModel.functions$lambda$2(this, arg_0)).addFunction("bedrock", arg_0 -> PoseableEntityModel.functions$lambda$3(this, arg_0)).addFunction("look", arg_0 -> PoseableEntityModel.functions$lambda$4(this, arg_0)).addFunction("quadruped_walk", arg_0 -> PoseableEntityModel.functions$lambda$5(this, arg_0)).addFunction("biped_walk", arg_0 -> PoseableEntityModel.functions$lambda$6(this, arg_0)).addFunction("bimanual_swing", arg_0 -> PoseableEntityModel.functions$lambda$7(this, arg_0)).addFunction("sine_wing_flap", arg_0 -> PoseableEntityModel.functions$lambda$8(this, arg_0)).addFunction("bedrock_quirk", arg_0 -> PoseableEntityModel.functions$lambda$11(this, arg_0)).addFunction("bedrock_primary_quirk", arg_0 -> PoseableEntityModel.functions$lambda$14(this, arg_0));
        MoLangRuntime moLangRuntime2 = moLangRuntime = ClientMoLangFunctions.INSTANCE.setupClient(MoLangFunctions.INSTANCE.setup(new MoLangRuntime()));
        PoseableEntityModel poseableEntityModel = this;
        boolean bl = false;
        MoLangEnvironment moLangEnvironment = it.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"it.environment");
        QueryStruct queryStruct = MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null);
        HashMap<String, Function<MoParams, Object>> hashMap = this.functions.functions;
        Intrinsics.checkNotNullExpressionValue(hashMap, (String)"functions.functions");
        MoLangFunctions.INSTANCE.addFunctions(queryStruct, (Map<String, ? extends Function<MoParams, Object>>)hashMap);
        poseableEntityModel.runtime = moLangRuntime;
        this.quirks = new ArrayList();
        this.dummyAnimation = new StatefulAnimation<T, ModelFrame>(){
            private final boolean isTransform;
            private final float duration;
            {
                this.duration = 1.0f;
            }

            public boolean isTransform() {
                return this.isTransform;
            }

            public float getDuration() {
                return this.duration;
            }

            public boolean run(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @NotNull PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
                Intrinsics.checkNotNullParameter(model, (String)"model");
                Intrinsics.checkNotNullParameter(state, (String)"state");
                return false;
            }

            public void applyEffects(@NotNull T entity2, @NotNull PoseableEntityState<T> state, float previousSeconds, float newSeconds) {
                StatefulAnimation.DefaultImpls.applyEffects(this, entity2, state, previousSeconds, newSeconds);
            }
        };
    }

    public /* synthetic */ PoseableEntityModel(Function1 function1, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            function1 = 1.INSTANCE;
        }
        this((Function1<? super ResourceLocation, ? extends RenderType>)function1);
    }

    @NotNull
    public final RenderContext getContext() {
        return this.context;
    }

    public abstract boolean isForLivingEntityRenderer();

    @NotNull
    public final Map<String, Pose<T, ? extends ModelFrame>> getPoses() {
        return this.poses;
    }

    @NotNull
    public final LocatorAccess getLocatorAccess() {
        LocatorAccess locatorAccess = this.locatorAccess;
        if (locatorAccess != null) {
            return locatorAccess;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"locatorAccess");
        return null;
    }

    public final void setLocatorAccess(@NotNull LocatorAccess locatorAccess) {
        Intrinsics.checkNotNullParameter((Object)locatorAccess, (String)"<set-?>");
        this.locatorAccess = locatorAccess;
    }

    public final float getRed() {
        return this.red;
    }

    public final void setRed(float f) {
        this.red = f;
    }

    public final float getGreen() {
        return this.green;
    }

    public final void setGreen(float f) {
        this.green = f;
    }

    public final float getBlue() {
        return this.blue;
    }

    public final void setBlue(float f) {
        this.blue = f;
    }

    public final float getAlpha() {
        return this.alpha;
    }

    public final void setAlpha(float f) {
        this.alpha = f;
    }

    @NotNull
    public final Iterable<ModelLayer> getCurrentLayers() {
        return this.currentLayers;
    }

    public final void setCurrentLayers(@NotNull Iterable<ModelLayer> iterable) {
        Intrinsics.checkNotNullParameter(iterable, (String)"<set-?>");
        this.currentLayers = iterable;
    }

    @Nullable
    public final MultiBufferSource getBufferProvider() {
        return this.bufferProvider;
    }

    public final void setBufferProvider(@Nullable MultiBufferSource multiBufferSource) {
        this.bufferProvider = multiBufferSource;
    }

    @Nullable
    public final PoseableEntityState<T> getCurrentState() {
        return this.currentState;
    }

    public final void setCurrentState(@Nullable PoseableEntityState<T> poseableEntityState) {
        this.currentState = poseableEntityState;
    }

    @NotNull
    public final Map<String, ExpressionLike> getAnimations() {
        return this.animations;
    }

    @NotNull
    public final Map<String, ExpressionLike> getTransitions() {
        return this.transitions;
    }

    @NotNull
    public final List<ModelPartTransformation> getDefaultPositions() {
        return this.defaultPositions;
    }

    @NotNull
    public final List<ModelPart> getRelevantParts() {
        return this.relevantParts;
    }

    @NotNull
    public final Map<String, ModelPart> getRelevantPartsByName() {
        return this.relevantPartsByName;
    }

    public final QueryStruct getFunctions() {
        return this.functions;
    }

    @NotNull
    public final MoLangRuntime getRuntime() {
        return this.runtime;
    }

    public abstract void registerPoses();

    @NotNull
    public abstract PoseableEntityState<T> getState(@NotNull T var1);

    @Nullable
    public final StatefulAnimation<T, ?> getAnimation(@NotNull PoseableEntityState<?> state, @NotNull String name, @NotNull MoLangRuntime runtime2) {
        Pose<T, ModelFrame> poseAnimations;
        StatefulAnimation<Object, Object> statefulAnimation;
        Object object;
        block12: {
            block11: {
                Intrinsics.checkNotNullParameter(state, (String)"state");
                Intrinsics.checkNotNullParameter((Object)name, (String)"name");
                Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
                object = state.getCurrentPose();
                if (object == null) break block11;
                String p0 = object;
                boolean bl = false;
                Pose<T, ModelFrame> pose = this.getPose(p0);
                object = pose;
                if (pose != null && (object = ((Pose)object).getAnimations()) != null) break block12;
            }
            object = MapsKt.emptyMap();
        }
        if ((statefulAnimation = this.resolveFromAnimationMap((Map<String, ? extends ExpressionLike>)((Object)(poseAnimations = object)), name, runtime2)) == null && (statefulAnimation = this.resolveFromAnimationMap(this.animations, name, runtime2)) == null) {
            if (Intrinsics.areEqual((Object)name, (Object)"cry") && this instanceof PokemonPoseableModel) {
                CryProvider cryProvider = ((PokemonPoseableModel)this).getCryAnimation();
                Object obj = state.getEntity();
                Intrinsics.checkNotNull(obj, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity");
                var6_4 = cryProvider.invoke((PokemonEntity)obj, state);
                statefulAnimation = var6_4 instanceof StatefulAnimation ? var6_4 : null;
            } else if (Intrinsics.areEqual((Object)name, (Object)"faint") && this instanceof PokemonPoseableModel) {
                PokemonPoseableModel pokemonPoseableModel = (PokemonPoseableModel)this;
                Object obj = state.getEntity();
                Intrinsics.checkNotNull(obj, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity");
                var6_4 = pokemonPoseableModel.getFaintAnimation((PokemonEntity)obj, state);
                statefulAnimation = var6_4 instanceof StatefulAnimation ? var6_4 : null;
            } else {
                try {
                    Object obj = MoLangExtensionsKt.asExpressionLike(name).resolveObject(runtime2).getObj();
                    Intrinsics.checkNotNull(obj, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation<T of bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel, *>");
                    var6_4 = (StatefulAnimation<PokemonEntity, ModelFrame>)obj;
                }
                catch (Exception exception) {
                    var6_4 = this.extractAnimation(name);
                }
                statefulAnimation = var6_4;
            }
        }
        StatefulAnimation<T, ?> animation = statefulAnimation;
        return animation;
    }

    @Nullable
    public final StatefulAnimation<T, ?> extractAnimation(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"string");
        String group = StringsKt.substringBefore$default((String)string, (String)":", null, (int)2, null);
        String animationName = StringsKt.substringBefore$default((String)StringsKt.substringAfter$default((String)string, (String)":", null, (int)2, null), (String)":", null, (int)2, null);
        boolean isPrimary = StringsKt.endsWith$default((String)string, (String)":primary", (boolean)false, (int)2, null);
        if (!StringsKt.isBlank((CharSequence)animationName) && !Intrinsics.areEqual((Object)animationName, (Object)string)) {
            BedrockAnimation bedrockAnimation = BedrockAnimationRepository.INSTANCE.tryGetAnimation(group, animationName);
            if (bedrockAnimation == null) {
                return null;
            }
            BedrockAnimation animation = bedrockAnimation;
            return isPrimary ? (StatefulAnimation)new PrimaryAnimation(new BedrockStatefulAnimation(animation), null, null, false, 14, null) : (StatefulAnimation)new BedrockStatefulAnimation(animation);
        }
        return null;
    }

    private final StatefulAnimation<T, ?> resolveFromAnimationMap(Map<String, ? extends ExpressionLike> map, String name, MoLangRuntime runtime2) {
        StatefulAnimation statefulAnimation;
        ExpressionLike expressionLike = map.get(name);
        if (expressionLike == null) {
            return null;
        }
        ExpressionLike animationExpression = expressionLike;
        try {
            Object obj = animationExpression.resolveObject(runtime2).getObj();
            Intrinsics.checkNotNull(obj, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation<T of bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel, *>");
            statefulAnimation = (StatefulAnimation)obj;
        }
        catch (Exception e) {
            Cobblemon.INSTANCE.getLOGGER().error("Failed to create animation by name " + name + ", most likely something wrong in the MoLang");
            e.printStackTrace();
            statefulAnimation = null;
        }
        return statefulAnimation;
    }

    public final void withLayerContext(@NotNull MultiBufferSource buffer, @Nullable PoseableEntityState<T> state, @NotNull Iterable<ModelLayer> layers, @NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter(layers, (String)"layers");
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        this.setLayerContext(buffer, state, layers);
        action2.invoke();
        this.resetLayerContext();
    }

    public final void setLayerContext(@NotNull MultiBufferSource buffer, @Nullable PoseableEntityState<T> state, @NotNull Iterable<ModelLayer> layers) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter(layers, (String)"layers");
        this.currentLayers = layers;
        this.bufferProvider = buffer;
        this.currentState = state;
    }

    public final void resetLayerContext() {
        this.currentLayers = CollectionsKt.emptyList();
        this.bufferProvider = null;
        this.currentState = null;
    }

    @Nullable
    public Integer getOverlayTexture(@Nullable Entity entity2) {
        return entity2 instanceof LivingEntity ? Integer.valueOf(OverlayTexture.m_118093_((int)OverlayTexture.m_118088_((float)0.0f), (int)OverlayTexture.m_118096_((((LivingEntity)entity2).f_20916_ > 0 || ((LivingEntity)entity2).f_20919_ > 0 ? 1 : 0) != 0))) : (entity2 != null ? Integer.valueOf(OverlayTexture.f_118083_) : null);
    }

    @NotNull
    public final <F extends ModelFrame> Pose<T, F> registerPose(@NotNull PoseType poseType, @Nullable Function1<? super T, Boolean> condition2, int transformTicks, @NotNull Map<String, ExpressionLike> animations2, @NotNull Function1<? super PoseableEntityState<T>, Unit> onTransitionedInto, @NotNull StatelessAnimation<T, ? extends F>[] idleAnimations2, @NotNull ModelPartTransformation[] transformedParts, @NotNull ModelQuirk<T, ?>[] quirks2) {
        Pose<? super T, ? extends F> pose;
        Intrinsics.checkNotNullParameter((Object)((Object)poseType), (String)"poseType");
        Intrinsics.checkNotNullParameter(animations2, (String)"animations");
        Intrinsics.checkNotNullParameter(onTransitionedInto, (String)"onTransitionedInto");
        Intrinsics.checkNotNullParameter(idleAnimations2, (String)"idleAnimations");
        Intrinsics.checkNotNullParameter((Object)transformedParts, (String)"transformedParts");
        Intrinsics.checkNotNullParameter(quirks2, (String)"quirks");
        Pose<? super T, ? extends F> it = pose = new Pose<T, F>(poseType.name(), SetsKt.setOf((Object)((Object)poseType)), condition2, onTransitionedInto, transformTicks, animations2, idleAnimations2, transformedParts, quirks2);
        boolean bl = false;
        this.setPose(poseType.name(), it);
        return pose;
    }

    public static /* synthetic */ Pose registerPose$default(PoseableEntityModel poseableEntityModel, PoseType poseType, Function1 function1, int n, Map map, Function1 function12, StatelessAnimation[] statelessAnimationArray, ModelPartTransformation[] modelPartTransformationArray, ModelQuirk[] modelQuirkArray, int n2, Object object) {
        boolean $i$f$emptyArray;
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: registerPose");
        }
        if ((n2 & 2) != 0) {
            function1 = null;
        }
        if ((n2 & 4) != 0) {
            n = 10;
        }
        if ((n2 & 8) != 0) {
            map = new LinkedHashMap();
        }
        if ((n2 & 0x10) != 0) {
            function12 = registerPose.1.INSTANCE;
        }
        if ((n2 & 0x20) != 0) {
            $i$f$emptyArray = false;
            statelessAnimationArray = new StatelessAnimation[]{};
        }
        if ((n2 & 0x40) != 0) {
            $i$f$emptyArray = false;
            modelPartTransformationArray = new ModelPartTransformation[]{};
        }
        if ((n2 & 0x80) != 0) {
            $i$f$emptyArray = false;
            modelQuirkArray = new ModelQuirk[]{};
        }
        return poseableEntityModel.registerPose(poseType, function1, n, map, function12, statelessAnimationArray, modelPartTransformationArray, modelQuirkArray);
    }

    @NotNull
    public final <F extends ModelFrame> Pose<T, F> registerPose(@NotNull String poseName, @NotNull Set<? extends PoseType> poseTypes, @Nullable Function1<? super T, Boolean> condition2, int transformTicks, @NotNull Map<String, ExpressionLike> animations2, @NotNull Function1<? super PoseableEntityState<T>, Unit> onTransitionedInto, @NotNull StatelessAnimation<T, ? extends F>[] idleAnimations2, @NotNull ModelPartTransformation[] transformedParts, @NotNull ModelQuirk<T, ?>[] quirks2) {
        Pose<? super T, ? extends F> pose;
        Intrinsics.checkNotNullParameter((Object)poseName, (String)"poseName");
        Intrinsics.checkNotNullParameter(poseTypes, (String)"poseTypes");
        Intrinsics.checkNotNullParameter(animations2, (String)"animations");
        Intrinsics.checkNotNullParameter(onTransitionedInto, (String)"onTransitionedInto");
        Intrinsics.checkNotNullParameter(idleAnimations2, (String)"idleAnimations");
        Intrinsics.checkNotNullParameter((Object)transformedParts, (String)"transformedParts");
        Intrinsics.checkNotNullParameter(quirks2, (String)"quirks");
        Pose<? super T, ? extends F> it = pose = new Pose<T, F>(poseName, poseTypes, condition2, onTransitionedInto, transformTicks, animations2, idleAnimations2, transformedParts, quirks2);
        boolean bl = false;
        this.setPose(poseName, it);
        return pose;
    }

    public static /* synthetic */ Pose registerPose$default(PoseableEntityModel poseableEntityModel, String string, Set set2, Function1 function1, int n, Map map, Function1 function12, StatelessAnimation[] statelessAnimationArray, ModelPartTransformation[] modelPartTransformationArray, ModelQuirk[] modelQuirkArray, int n2, Object object) {
        boolean $i$f$emptyArray;
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: registerPose");
        }
        if ((n2 & 4) != 0) {
            function1 = null;
        }
        if ((n2 & 8) != 0) {
            n = 10;
        }
        if ((n2 & 0x10) != 0) {
            map = new LinkedHashMap();
        }
        if ((n2 & 0x20) != 0) {
            function12 = registerPose.3.INSTANCE;
        }
        if ((n2 & 0x40) != 0) {
            $i$f$emptyArray = false;
            statelessAnimationArray = new StatelessAnimation[]{};
        }
        if ((n2 & 0x80) != 0) {
            $i$f$emptyArray = false;
            modelPartTransformationArray = new ModelPartTransformation[]{};
        }
        if ((n2 & 0x100) != 0) {
            $i$f$emptyArray = false;
            modelQuirkArray = new ModelQuirk[]{};
        }
        return poseableEntityModel.registerPose(string, set2, function1, n, (Map<String, ExpressionLike>)map, function12, statelessAnimationArray, modelPartTransformationArray, modelQuirkArray);
    }

    @NotNull
    public final <F extends ModelFrame> Pose<T, F> registerPose(@NotNull String poseName, @NotNull PoseType poseType, @Nullable Function1<? super T, Boolean> condition2, int transformTicks, @NotNull Map<String, ExpressionLike> animations2, @NotNull Function1<? super PoseableEntityState<T>, Unit> onTransitionedInto, @NotNull StatelessAnimation<T, ? extends F>[] idleAnimations2, @NotNull ModelPartTransformation[] transformedParts, @NotNull ModelQuirk<T, ?>[] quirks2) {
        Pose<? super T, ? extends F> pose;
        Intrinsics.checkNotNullParameter((Object)poseName, (String)"poseName");
        Intrinsics.checkNotNullParameter((Object)((Object)poseType), (String)"poseType");
        Intrinsics.checkNotNullParameter(animations2, (String)"animations");
        Intrinsics.checkNotNullParameter(onTransitionedInto, (String)"onTransitionedInto");
        Intrinsics.checkNotNullParameter(idleAnimations2, (String)"idleAnimations");
        Intrinsics.checkNotNullParameter((Object)transformedParts, (String)"transformedParts");
        Intrinsics.checkNotNullParameter(quirks2, (String)"quirks");
        Pose<? super T, ? extends F> it = pose = new Pose<T, F>(poseName, SetsKt.setOf((Object)((Object)poseType)), condition2, onTransitionedInto, transformTicks, animations2, idleAnimations2, transformedParts, quirks2);
        boolean bl = false;
        this.setPose(poseName, it);
        return pose;
    }

    public static /* synthetic */ Pose registerPose$default(PoseableEntityModel poseableEntityModel, String string, PoseType poseType, Function1 function1, int n, Map map, Function1 function12, StatelessAnimation[] statelessAnimationArray, ModelPartTransformation[] modelPartTransformationArray, ModelQuirk[] modelQuirkArray, int n2, Object object) {
        boolean $i$f$emptyArray;
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: registerPose");
        }
        if ((n2 & 4) != 0) {
            function1 = null;
        }
        if ((n2 & 8) != 0) {
            n = 10;
        }
        if ((n2 & 0x10) != 0) {
            map = new LinkedHashMap();
        }
        if ((n2 & 0x20) != 0) {
            function12 = registerPose.5.INSTANCE;
        }
        if ((n2 & 0x40) != 0) {
            $i$f$emptyArray = false;
            statelessAnimationArray = new StatelessAnimation[]{};
        }
        if ((n2 & 0x80) != 0) {
            $i$f$emptyArray = false;
            modelPartTransformationArray = new ModelPartTransformation[]{};
        }
        if ((n2 & 0x100) != 0) {
            $i$f$emptyArray = false;
            modelQuirkArray = new ModelQuirk[]{};
        }
        return poseableEntityModel.registerPose(string, poseType, function1, n, (Map<String, ExpressionLike>)map, function12, statelessAnimationArray, modelPartTransformationArray, modelQuirkArray);
    }

    public final <F extends ModelFrame> void setPose(@NotNull String poseName, @NotNull Pose<T, F> pose) {
        Intrinsics.checkNotNullParameter((Object)poseName, (String)"poseName");
        Intrinsics.checkNotNullParameter(pose, (String)"pose");
        if (this.poses.containsKey(poseName)) {
            Cobblemon.INSTANCE.getLOGGER().error("Pose with name " + poseName + " already exists for class " + Reflection.getOrCreateKotlinClass(this.getClass()).getSimpleName());
        }
        this.poses.put(poseName, pose);
    }

    @NotNull
    public final ModelPart registerChildWithAllChildren(@NotNull ModelPart $this$registerChildWithAllChildren, @NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)$this$registerChildWithAllChildren, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        ModelPart modelPart = $this$registerChildWithAllChildren.m_171324_(name);
        Intrinsics.checkNotNull((Object)modelPart);
        ModelPart child = modelPart;
        this.registerRelevantPart((Pair<String, ModelPart>)TuplesKt.to((Object)name, (Object)child));
        this.loadAllNamedChildren(child);
        return child;
    }

    @NotNull
    public final ModelPart registerChildWithSpecificChildren(@NotNull ModelPart $this$registerChildWithSpecificChildren, @NotNull String name, @NotNull Iterable<String> nameList) {
        Intrinsics.checkNotNullParameter((Object)$this$registerChildWithSpecificChildren, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(nameList, (String)"nameList");
        ModelPart child = $this$registerChildWithSpecificChildren.m_171324_(name);
        this.registerRelevantPart((Pair<String, ModelPart>)TuplesKt.to((Object)name, (Object)child));
        Intrinsics.checkNotNullExpressionValue((Object)child, (String)"child");
        this.loadSpecificNamedChildren(child, nameList);
        return child;
    }

    public final void initializeLocatorAccess() {
        LocatorAccess locatorAccess = LocatorAccess.Companion.resolve(this.getRootPart());
        if (locatorAccess == null) {
            locatorAccess = new LocatorAccess(this.getRootPart(), null, null, 6, null);
        }
        this.setLocatorAccess(locatorAccess);
    }

    @NotNull
    public final ModelPart getPart(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        ModelPart modelPart = this.relevantPartsByName.get(name);
        Intrinsics.checkNotNull((Object)modelPart);
        return modelPart;
    }

    @NotNull
    public final Bone getPartFallback(String ... names) {
        ModelPart modelPart;
        block2: {
            Intrinsics.checkNotNullParameter((Object)names, (String)"names");
            for (String it : names) {
                boolean bl = false;
                modelPart = this.relevantPartsByName.get(it);
                if (modelPart == null) {
                    continue;
                }
                break block2;
            }
            modelPart = null;
        }
        return modelPart != null ? (Bone)modelPart : this.getRootPart();
    }

    private final void loadSpecificNamedChildren(ModelPart modelPart, Iterable<String> nameList) {
        for (Map.Entry entry : modelPart.f_104213_.entrySet()) {
            String name = (String)entry.getKey();
            ModelPart child = (ModelPart)entry.getValue();
            if (!CollectionsKt.contains(nameList, (Object)name)) continue;
            Intrinsics.checkNotNullExpressionValue((Object)child, (String)"child");
            ModelPartTransformation modelPartTransformation = ModelPartTransformation.Companion.derive(child);
            this.relevantParts.add(child);
            Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
            this.relevantPartsByName.put(name, child);
            this.defaultPositions.add(modelPartTransformation);
            this.loadAllNamedChildren(child);
        }
    }

    public final void loadAllNamedChildren(@NotNull Bone bone) {
        Intrinsics.checkNotNullParameter((Object)bone, (String)"bone");
        if (bone instanceof ModelPart) {
            this.loadAllNamedChildren((ModelPart)bone);
        }
    }

    public final void loadAllNamedChildren(@NotNull ModelPart modelPart) {
        Intrinsics.checkNotNullParameter((Object)modelPart, (String)"modelPart");
        for (Map.Entry entry : modelPart.f_104213_.entrySet()) {
            String name = (String)entry.getKey();
            ModelPart child = (ModelPart)entry.getValue();
            Intrinsics.checkNotNullExpressionValue((Object)child, (String)"child");
            ModelPartTransformation modelPartTransformation = ModelPartTransformation.Companion.derive(child);
            this.relevantParts.add(child);
            Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
            this.relevantPartsByName.put(name, child);
            this.defaultPositions.add(modelPartTransformation);
            this.loadAllNamedChildren(child);
        }
    }

    @NotNull
    public final ModelPart registerRelevantPart(@NotNull String name, @NotNull ModelPart part) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)part, (String)"part");
        ModelPartTransformation modelPartTransformation = ModelPartTransformation.Companion.derive(part);
        this.relevantParts.add(part);
        this.relevantPartsByName.put(name, part);
        this.defaultPositions.add(modelPartTransformation);
        return part;
    }

    @NotNull
    public final ModelPart registerRelevantPart(@NotNull Pair<String, ModelPart> pairing) {
        Intrinsics.checkNotNullParameter(pairing, (String)"pairing");
        return this.registerRelevantPart((String)pairing.getFirst(), (ModelPart)pairing.getSecond());
    }

    public void m_7695_(@NotNull PoseStack stack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float r, float g, float b, float a) {
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.render(this.context, stack, buffer, packedLight, packedOverlay, r, g, b, a);
    }

    public final void render(@NotNull RenderContext context, @NotNull PoseStack stack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float r, float g, float b, float a) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Integer n = this.getOverlayTexture(context.request(RenderContext.Companion.getENTITY()));
        this.getRootPart().render(context, stack, buffer, packedLight, n != null ? n : packedOverlay, this.red * r, this.green * g, this.blue * b, this.alpha * a);
        MultiBufferSource provider = this.bufferProvider;
        if (provider != null) {
            for (ModelLayer layer : this.currentLayers) {
                ModelTextureSupplier modelTextureSupplier = layer.getTexture();
                if (modelTextureSupplier != null) {
                    PoseableEntityState<T> poseableEntityState = this.currentState;
                    if ((modelTextureSupplier = modelTextureSupplier.invoke(poseableEntityState != null ? poseableEntityState.getAnimationSeconds() : 0.0f)) == null) {
                        continue;
                    }
                    ModelTextureSupplier texture = modelTextureSupplier;
                    RenderType renderLayer = this.getLayer((ResourceLocation)texture, layer.getEmissive(), layer.getTranslucent());
                    VertexConsumer consumer = provider.m_6299_(renderLayer);
                    stack.m_85836_();
                    Integer n2 = this.getOverlayTexture(context.request(RenderContext.Companion.getENTITY()));
                    this.getRootPart().render(context, stack, consumer, packedLight, n2 != null ? n2 : packedOverlay, layer.getTint().x, layer.getTint().y, layer.getTint().z, layer.getTint().w);
                    stack.m_85849_();
                }
            }
        }
    }

    @NotNull
    public final RenderType makeLayer(@NotNull ResourceLocation texture, boolean emissive, boolean translucent) {
        Intrinsics.checkNotNullParameter((Object)texture, (String)"texture");
        RenderType.CompositeState compositeState = RenderType.CompositeState.m_110628_().m_173292_(emissive && translucent ? RenderStateShard.f_234323_ : (!emissive && translucent ? RenderStateShard.f_173066_ : (!emissive && !translucent ? RenderStateShard.f_173113_ : RenderStateShard.f_234323_))).m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(texture, false, false)).m_110685_(translucent ? RenderStateShard.f_110139_ : RenderStateShard.f_110134_).m_110661_(RenderStateShard.f_110158_).m_110687_(RenderStateShard.f_110114_).m_110677_(RenderStateShard.f_110154_).m_110691_(false);
        Intrinsics.checkNotNullExpressionValue((Object)compositeState, (String)"builder()\n            .p\u2026            .build(false)");
        RenderType.CompositeState multiPhaseParameters = compositeState;
        RenderType.CompositeRenderType compositeRenderType = RenderType.m_173215_((String)"cobblemon_entity_layer", (VertexFormat)DefaultVertexFormat.f_85812_, (VertexFormat.Mode)VertexFormat.Mode.QUADS, (int)256, (boolean)true, (boolean)translucent, (RenderType.CompositeState)multiPhaseParameters);
        Intrinsics.checkNotNullExpressionValue((Object)compositeRenderType, (String)"of(\n            \"cobblem\u2026PhaseParameters\n        )");
        return (RenderType)compositeRenderType;
    }

    @NotNull
    public final RenderType getLayer(@NotNull ResourceLocation texture, boolean emissive, boolean translucent) {
        RenderType renderType;
        Intrinsics.checkNotNullParameter((Object)texture, (String)"texture");
        if (!emissive && !translucent) {
            RenderType renderType2 = CobblemonRenderLayers.INSTANCE.getENTITY_CUTOUT().apply(texture);
            Intrinsics.checkNotNullExpressionValue((Object)renderType2, (String)"{\n            CobblemonR\u2026.apply(texture)\n        }");
            renderType = renderType2;
        } else if (!emissive) {
            RenderType renderType3 = CobblemonRenderLayers.INSTANCE.getENTITY_TRANSLUCENT().apply(texture, true);
            Intrinsics.checkNotNullExpressionValue((Object)renderType3, (String)"{\n            CobblemonR\u2026(texture, true)\n        }");
            renderType = renderType3;
        } else {
            renderType = this.makeLayer(texture, emissive, translucent);
        }
        return renderType;
    }

    @Nullable
    public final Unit applyPose(@NotNull String pose, float intensity) {
        Unit unit;
        Intrinsics.checkNotNullParameter((Object)pose, (String)"pose");
        ModelPartTransformation[] modelPartTransformationArray = this.getPose(pose);
        if (modelPartTransformationArray != null && (modelPartTransformationArray = modelPartTransformationArray.getTransformedParts()) != null) {
            ModelPartTransformation[] $this$forEach$iv = modelPartTransformationArray;
            boolean $i$f$forEach = false;
            int n = $this$forEach$iv.length;
            for (int i = 0; i < n; ++i) {
                ModelPartTransformation element$iv;
                ModelPartTransformation it = element$iv = $this$forEach$iv[i];
                boolean bl = false;
                it.apply(intensity);
            }
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        return unit;
    }

    @Nullable
    public final Pose<T, ? extends ModelFrame> getPose(@NotNull PoseType pose) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)((Object)pose), (String)"pose");
            Iterable $this$firstOrNull$iv = this.poses.values();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                Pose it = (Pose)element$iv;
                boolean bl = false;
                if (!it.getPoseTypes().contains((Object)pose)) continue;
                v0 = element$iv;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    @Nullable
    public final Pose<T, ? extends ModelFrame> getPose(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return this.poses.get(name);
    }

    public final void setDefault() {
        Iterable $this$forEach$iv = this.defaultPositions;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ModelPartTransformation it = (ModelPartTransformation)element$iv;
            boolean bl = false;
            it.set();
        }
    }

    @NotNull
    public final List<ModelQuirk<T, ?>> getQuirks() {
        return this.quirks;
    }

    public final void setupAnimStateless(@NotNull PoseType poseType, float limbSwing, float limbSwingAmount, float headYaw, float headPitch, float ageInTicks) {
        Intrinsics.checkNotNullParameter((Object)((Object)poseType), (String)"poseType");
        this.setupAnimStateless(SetsKt.setOf((Object)((Object)poseType)), limbSwing, limbSwingAmount, headYaw, headPitch, ageInTicks);
    }

    public static /* synthetic */ void setupAnimStateless$default(PoseableEntityModel poseableEntityModel, PoseType poseType, float f, float f2, float f3, float f4, float f5, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setupAnimStateless");
        }
        if ((n & 2) != 0) {
            f = 0.0f;
        }
        if ((n & 4) != 0) {
            f2 = 0.0f;
        }
        if ((n & 8) != 0) {
            f3 = 0.0f;
        }
        if ((n & 0x10) != 0) {
            f4 = 0.0f;
        }
        if ((n & 0x20) != 0) {
            f5 = 0.0f;
        }
        poseableEntityModel.setupAnimStateless(poseType, f, f2, f3, f4, f5);
    }

    public final void setupAnimStateless(@NotNull Set<? extends PoseType> poseTypes, float limbSwing, float limbSwingAmount, float headYaw, float headPitch, float ageInTicks) {
        Pose pose;
        Pose pose2;
        block3: {
            Intrinsics.checkNotNullParameter(poseTypes, (String)"poseTypes");
            this.context.pop(RenderContext.Companion.getENTITY());
            this.setDefault();
            for (PoseType it : (Iterable)poseTypes) {
                boolean bl = false;
                Pose pose3 = this.getPose(it);
                if (pose3 == null) continue;
                pose2 = pose3;
                break block3;
            }
            pose2 = pose = null;
        }
        if (pose2 == null) {
            pose = (Pose)CollectionsKt.first((Iterable)this.poses.values());
        }
        Pose pose4 = pose;
        ModelPartTransformation[] $this$forEach$iv = pose4.getTransformedParts();
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            ModelPartTransformation element$iv;
            ModelPartTransformation it = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            it.apply(1.0f);
        }
        pose4.idleStateless(this, null, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, 1.0f);
    }

    public static /* synthetic */ void setupAnimStateless$default(PoseableEntityModel poseableEntityModel, Set set2, float f, float f2, float f3, float f4, float f5, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setupAnimStateless");
        }
        if ((n & 2) != 0) {
            f = 0.0f;
        }
        if ((n & 4) != 0) {
            f2 = 0.0f;
        }
        if ((n & 8) != 0) {
            f3 = 0.0f;
        }
        if ((n & 0x10) != 0) {
            f4 = 0.0f;
        }
        if ((n & 0x20) != 0) {
            f5 = 0.0f;
        }
        poseableEntityModel.setupAnimStateless(set2, f, f2, f3, f4, f5);
    }

    /*
     * WARNING - void declaration
     */
    public final void setupAnimStateful(@Nullable T entity2, @NotNull PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        void $this$filterNotTo$iv$iv;
        void $this$filterNot$iv;
        boolean bl;
        Iterator $this$filterNotTo$iv$iv232;
        boolean $i$f$filterNotTo;
        boolean bl2;
        PoseType entityPoseType;
        Pose pose;
        String poseName;
        Intrinsics.checkNotNullParameter(state, (String)"state");
        this.context.put(RenderContext.Companion.getENTITY(), entity2);
        this.setupEntityTypeContext(entity2);
        state.setCurrentModel(this);
        this.setDefault();
        if (entity2 != null) {
            this.updateLocators(state);
        }
        state.preRender();
        String string = poseName = state.getPose();
        if (string != null) {
            String it2 = string;
            boolean bl3 = false;
            pose = this.getPose(it2);
        } else {
            pose = null;
        }
        Pose pose2 = pose;
        PoseType poseType = entityPoseType = entity2 instanceof Poseable ? ((Poseable)entity2).getCurrentPoseType() : null;
        if (!(entity2 == null || poseName != null && pose2 != null && pose2.isSuitable(entity2) && CollectionsKt.contains((Iterable)pose2.getPoseTypes(), (Object)((Object)entityPoseType)))) {
            Pose desirablePose2;
            Pose pose3;
            Object v3;
            block21: {
                Iterable $this$firstOrNull$iv = this.poses.values();
                boolean $i$f$firstOrNull = false;
                for (Object element$iv : $this$firstOrNull$iv) {
                    Pose pose4 = (Pose)element$iv;
                    bl2 = false;
                    if (!((entityPoseType == null || pose4.getPoseTypes().contains((Object)entityPoseType)) && pose4.isSuitable(entity2))) continue;
                    v3 = element$iv;
                    break block21;
                }
                v3 = null;
            }
            if ((pose3 = (Pose)v3) == null) {
                boolean $i$f$emptyArray = false;
                $i$f$emptyArray = false;
                $i$f$emptyArray = false;
                pose3 = desirablePose2 = new Pose("none", SetsKt.setOf((Object)((Object)PoseType.NONE)), null, setupAnimStateful.desirablePose.2.INSTANCE, 0, new LinkedHashMap(), new StatelessAnimation[0], new ModelPartTransformation[0], new ModelQuirk[0]);
            }
            if (pose2 != null && poseName != null) {
                if (state.getPrimaryAnimation() == null) {
                    this.moveToPose(entity2, state, desirablePose2);
                }
            } else {
                pose2 = desirablePose2;
                poseName = desirablePose2.getPoseName();
                this.getState(entity2).setPose(poseName);
            }
        } else {
            String string2 = poseName;
            if (string2 == null) {
                string2 = ((Pose)CollectionsKt.first((Iterable)this.poses.values())).getPoseName();
            }
            poseName = string2;
        }
        Pose<T, ModelFrame> currentPose = this.getPose(poseName);
        this.applyPose(poseName, 1.0f);
        PrimaryAnimation<T> primaryAnimation2 = state.getPrimaryAnimation();
        if (currentPose != null && primaryAnimation2 == null) {
            ModelQuirk<T, ?>[] $this$forEach$iv;
            Iterable $this$filterNot$iv2;
            Object element$iv;
            Iterable $i$f$emptyArray = state.getQuirks().keySet();
            Object[] $i$f$firstOrNull = currentPose.getQuirks();
            boolean $i$f$filterNot = false;
            element$iv = $this$filterNot$iv2;
            Collection collection = new ArrayList();
            $i$f$filterNotTo = false;
            Iterator iterator = $this$filterNotTo$iv$iv232.iterator();
            while (iterator.hasNext()) {
                Object element$iv$iv = iterator.next();
                ModelQuirk p0 = (ModelQuirk)element$iv$iv;
                bl = false;
                if (ArraysKt.contains((Object[])$i$f$firstOrNull, (Object)p0)) continue;
                collection.add(element$iv$iv);
            }
            $this$filterNot$iv2 = (List)collection;
            $i$f$firstOrNull = state.getQuirks();
            int $i$f$forEach = 0;
            for (Object t : $this$forEach$iv) {
                ModelQuirk p0 = (ModelQuirk)t;
                boolean bl4 = false;
                $i$f$firstOrNull.remove(p0);
            }
            $this$forEach$iv = currentPose.getQuirks();
            boolean $i$f$forEach2 = false;
            int $this$filterNotTo$iv$iv232 = $this$forEach$iv.length;
            for ($i$f$forEach = 0; $i$f$forEach < $this$filterNotTo$iv$iv232; ++$i$f$forEach) {
                ModelQuirk<T, ?> modelQuirk;
                ModelQuirk<T, ?> it3 = modelQuirk = $this$forEach$iv[$i$f$forEach];
                boolean bl5 = false;
                it3.tick(entity2, this, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, 1.0f);
            }
        }
        if (primaryAnimation2 != null) {
            float portion = (state.getAnimationSeconds() - primaryAnimation2.getStarted()) / primaryAnimation2.getDuration();
            state.setPrimaryOverridePortion(1.0f - ((Number)primaryAnimation2.getCurve().invoke((Object)Float.valueOf(portion))).floatValue());
            if (!primaryAnimation2.run(entity2, this, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, 1.0f - state.getPrimaryOverridePortion())) {
                primaryAnimation2.getAfterAction().accept(Unit.INSTANCE);
                state.setPrimaryAnimation(null);
                state.setPrimaryOverridePortion(1.0f);
            }
        }
        Iterable $i$f$forEach2 = CollectionsKt.toList((Iterable)state.getStatefulAnimations());
        boolean $i$f$filterNot = false;
        $this$filterNotTo$iv$iv232 = $this$filterNot$iv;
        Collection collection = new ArrayList();
        $i$f$filterNotTo = false;
        for (Object element$iv$iv : $this$filterNotTo$iv$iv) {
            StatefulAnimation it4 = (StatefulAnimation)element$iv$iv;
            bl = false;
            if (it4.run(entity2, this, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, 1.0f)) continue;
            collection.add(element$iv$iv);
        }
        List removedStatefuls = (List)collection;
        state.getStatefulAnimations().removeAll(removedStatefuls);
        String string3 = state.getCurrentPose();
        if (string3 != null) {
            String string4 = string3;
            bl2 = false;
            Pose<T, ModelFrame> pose4 = this.getPose(string4);
            if (pose4 != null) {
                pose4.idleStateful(entity2, this, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
            }
        }
        if (entity2 != null) {
            this.updateLocators(state);
        }
    }

    public void setupEntityTypeContext(@Nullable T entity2) {
    }

    public void m_6973_(@NotNull T entity2, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        Intrinsics.checkNotNullParameter(entity2, (String)"entity");
        this.setupAnimStateful(entity2, this.getState(entity2), limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
    }

    /*
     * WARNING - void declaration
     */
    public final void moveToPose(@Nullable T entity2, @NotNull PoseableEntityState<T> state, @NotNull Pose<T, ? extends ModelFrame> desirablePose2) {
        boolean bl;
        PoseType desirablePoseType;
        Object previousPose;
        block13: {
            Object object;
            block16: {
                block15: {
                    Intrinsics.checkNotNullParameter(state, (String)"state");
                    Intrinsics.checkNotNullParameter(desirablePose2, (String)"desirablePose");
                    object = state.getPose();
                    if (object == null) break block15;
                    String it = object;
                    boolean bl2 = false;
                    Pose<T, ModelFrame> pose = this.getPose(it);
                    object = pose;
                    if (pose != null) break block16;
                }
                PoseableEntityModel $this$moveToPose_u24lambda_u2432 = this;
                boolean bl3 = false;
                state.setPose(desirablePose2.getPoseName());
                return;
            }
            previousPose = object;
            desirablePoseType = (PoseType)((Object)CollectionsKt.first((Iterable)desirablePose2.getPoseTypes()));
            Iterable $this$none$iv = state.getStatefulAnimations();
            boolean $i$f$none = false;
            if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv : $this$none$iv) {
                    StatefulAnimation it = (StatefulAnimation)element$iv;
                    boolean bl4 = false;
                    if (!it.isTransform()) continue;
                    bl = false;
                    break block13;
                }
                bl = true;
            }
        }
        if (bl) {
            Function2 transition = ((Pose)previousPose).getTransitions().get(desirablePose2.getPoseName());
            if (transition == null && ((Pose)previousPose).getTransformTicks() > 0) {
                PrimaryAnimation primaryAnimation2 = new PrimaryAnimation(new PoseTransitionAnimation((Pose)previousPose, desirablePose2, ((Pose)previousPose).getTransformTicks(), null, 8, null), moveToPose.primaryAnimation.1.INSTANCE, null, false, 12, null);
                state.addPrimaryAnimation(primaryAnimation2);
                primaryAnimation2.setAfterAction(MiscUtilsKt.plus(primaryAnimation2.getAfterAction(), (Function1)new Function1<Unit, Unit>(state, desirablePose2){
                    final /* synthetic */ PoseableEntityState<T> $state;
                    final /* synthetic */ Pose<T, ? extends ModelFrame> $desirablePose;
                    {
                        this.$state = $state;
                        this.$desirablePose = $desirablePose;
                        super(1);
                    }

                    public final void invoke(@NotNull Unit it) {
                        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                        this.$state.setPose(this.$desirablePose.getPoseName());
                    }
                }));
            } else if (transition != null) {
                StatefulAnimation animation = (StatefulAnimation)transition.invoke(previousPose, desirablePose2);
                PrimaryAnimation primaryAnimation3 = animation instanceof PrimaryAnimation ? (PrimaryAnimation)animation : new PrimaryAnimation(animation, moveToPose.primaryAnimation.2.INSTANCE, null, false, 12, null);
                primaryAnimation3.setAfterAction(MiscUtilsKt.plus(primaryAnimation3.getAfterAction(), (Function1)new Function1<Unit, Unit>(state, desirablePose2){
                    final /* synthetic */ PoseableEntityState<T> $state;
                    final /* synthetic */ Pose<T, ? extends ModelFrame> $desirablePose;
                    {
                        this.$state = $state;
                        this.$desirablePose = $desirablePose;
                        super(1);
                    }

                    public final void invoke(@NotNull Unit it) {
                        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                        this.$state.setPose(this.$desirablePose.getPoseName());
                    }
                }));
                state.addPrimaryAnimation(primaryAnimation3);
            } else {
                Object element$iv2;
                PoseableEntityState poseableEntityState;
                block14: {
                    void $this$first$iv;
                    Iterable animation = this.poses.values();
                    poseableEntityState = state;
                    boolean $i$f$first = false;
                    for (Object element$iv2 : $this$first$iv) {
                        Pose it = (Pose)element$iv2;
                        boolean bl5 = false;
                        if (!(it.getPoseTypes().contains((Object)desirablePoseType) && (it.getCondition() == null || entity2 != null && (Boolean)it.getCondition().invoke(entity2) != false))) continue;
                        break block14;
                    }
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
                poseableEntityState.setPose(((Pose)element$iv2).getPoseName());
            }
        }
    }

    public final void updateLocators(@NotNull PoseableEntityState<T> state) {
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Entity entity2 = this.context.request(RenderContext.Companion.getENTITY());
        if (entity2 == null) {
            return;
        }
        Entity entity3 = entity2;
        PoseStack matrixStack = new PoseStack();
        float scale = 1.0f;
        if (entity3 instanceof PokemonEntity) {
            matrixStack.m_252781_(Axis.f_252436_.m_252977_((float)180 - ((PokemonEntity)entity3).f_20883_));
            matrixStack.m_85836_();
            matrixStack.m_85841_(-1.0f, -1.0f, 1.0f);
            float f = ((PokemonEntity)entity3).getPokemon().getForm().getBaseScale() * ((PokemonEntity)entity3).getPokemon().getScaleModifier();
            PokemonSideDelegate pokemonSideDelegate = ((PokemonEntity)entity3).getDelegate();
            Intrinsics.checkNotNull((Object)pokemonSideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate");
            scale = f * ((PokemonClientDelegate)pokemonSideDelegate).getEntityScaleModifier();
            matrixStack.m_85841_(scale, scale, scale);
        } else if (entity3 instanceof EmptyPokeBallEntity) {
            matrixStack.m_252781_(Axis.f_252436_.m_252977_(((EmptyPokeBallEntity)entity3).m_146908_()));
            matrixStack.m_85836_();
            matrixStack.m_85841_(1.0f, -1.0f, -1.0f);
            scale = 0.7f;
            matrixStack.m_85841_(scale, scale, scale);
        } else if (entity3 instanceof GenericBedrockEntity) {
            matrixStack.m_252781_(Axis.f_252436_.m_252977_(((GenericBedrockEntity)entity3).m_146908_()));
            matrixStack.m_85836_();
            matrixStack.m_85841_(1.0f, -1.0f, 1.0f);
        }
        Map<String, MatrixWrapper> states = state.getLocatorStates();
        if (this.isForLivingEntityRenderer()) {
            matrixStack.m_85837_(0.0, -1.5, 0.0);
        }
        this.getLocatorAccess().update(matrixStack, entity3, scale, states, true);
    }

    @NotNull
    public final TranslationFunctionStatelessAnimation<T> translation(@NotNull ModelPart $this$translation, @NotNull Function1<? super Float, Float> function, int axis, @NotNull Function3<? super PoseableEntityState<T>, ? super Float, ? super Float, Float> timeVariable) {
        Intrinsics.checkNotNullParameter((Object)$this$translation, (String)"<this>");
        Intrinsics.checkNotNullParameter(function, (String)"function");
        Intrinsics.checkNotNullParameter(timeVariable, (String)"timeVariable");
        return new TranslationFunctionStatelessAnimation($this$translation, function, axis, timeVariable, this);
    }

    @NotNull
    public final RotationFunctionStatelessAnimation<T> rotation(@NotNull ModelPart $this$rotation, @NotNull Function1<? super Float, Float> function, int axis, @NotNull Function3<? super PoseableEntityState<T>, ? super Float, ? super Float, Float> timeVariable) {
        Intrinsics.checkNotNullParameter((Object)$this$rotation, (String)"<this>");
        Intrinsics.checkNotNullParameter(function, (String)"function");
        Intrinsics.checkNotNullParameter(timeVariable, (String)"timeVariable");
        return new RotationFunctionStatelessAnimation($this$rotation, function, axis, timeVariable, this);
    }

    @NotNull
    public final BedrockStatelessAnimation<T> bedrock(@NotNull String animationGroup, @NotNull String animation, @NotNull String animationPrefix) {
        Intrinsics.checkNotNullParameter((Object)animationGroup, (String)"animationGroup");
        Intrinsics.checkNotNullParameter((Object)animation, (String)"animation");
        Intrinsics.checkNotNullParameter((Object)animationPrefix, (String)"animationPrefix");
        return new BedrockStatelessAnimation(this, BedrockAnimationRepository.INSTANCE.getAnimation(animationGroup, animationPrefix + "." + animation));
    }

    public static /* synthetic */ BedrockStatelessAnimation bedrock$default(PoseableEntityModel poseableEntityModel, String string, String string2, String object, int n, Object object2) {
        if (object2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: bedrock");
        }
        if ((n & 4) != 0) {
            object = "animation." + string;
        }
        return poseableEntityModel.bedrock(string, string2, (String)object);
    }

    @NotNull
    public final BedrockStatefulAnimation<T> bedrockStateful(@NotNull String animationGroup, @NotNull String animation, @NotNull String animationPrefix) {
        Intrinsics.checkNotNullParameter((Object)animationGroup, (String)"animationGroup");
        Intrinsics.checkNotNullParameter((Object)animation, (String)"animation");
        Intrinsics.checkNotNullParameter((Object)animationPrefix, (String)"animationPrefix");
        return new BedrockStatefulAnimation(BedrockAnimationRepository.INSTANCE.getAnimation(animationGroup, animationPrefix + "." + animation));
    }

    public static /* synthetic */ BedrockStatefulAnimation bedrockStateful$default(PoseableEntityModel poseableEntityModel, String string, String string2, String object, int n, Object object2) {
        if (object2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: bedrockStateful");
        }
        if ((n & 4) != 0) {
            object = "animation." + string;
        }
        return poseableEntityModel.bedrockStateful(string, string2, (String)object);
    }

    @NotNull
    public final SimpleQuirk<T> quirk(@NotNull Pair<Float, Float> secondsBetweenOccurrences, @NotNull IntRange loopTimes, @NotNull Function1<? super PoseableEntityState<T>, Boolean> condition2, @NotNull Function1<? super PoseableEntityState<T>, ? extends StatefulAnimation<T, ?>> animation) {
        Intrinsics.checkNotNullParameter(secondsBetweenOccurrences, (String)"secondsBetweenOccurrences");
        Intrinsics.checkNotNullParameter((Object)loopTimes, (String)"loopTimes");
        Intrinsics.checkNotNullParameter(condition2, (String)"condition");
        Intrinsics.checkNotNullParameter(animation, (String)"animation");
        return new SimpleQuirk(secondsBetweenOccurrences, condition2, loopTimes, (Function1)new Function1<PoseableEntityState<T>, Iterable<? extends StatefulAnimation<T, ?>>>(animation){
            final /* synthetic */ Function1<PoseableEntityState<T>, StatefulAnimation<T, ?>> $animation;
            {
                this.$animation = $animation;
                super(1);
            }

            @NotNull
            public final Iterable<StatefulAnimation<T, ?>> invoke(@NotNull PoseableEntityState<T> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return CollectionsKt.listOf((Object)this.$animation.invoke(it));
            }
        });
    }

    public static /* synthetic */ SimpleQuirk quirk$default(PoseableEntityModel poseableEntityModel, Pair pair, IntRange intRange, Function1 function1, Function1 function12, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: quirk");
        }
        if ((n & 1) != 0) {
            pair = TuplesKt.to((Object)Float.valueOf(8.0f), (Object)Float.valueOf(30.0f));
        }
        if ((n & 2) != 0) {
            intRange = new IntRange(1, 1);
        }
        if ((n & 4) != 0) {
            function1 = quirk.1.INSTANCE;
        }
        return poseableEntityModel.quirk((Pair<Float, Float>)pair, intRange, function1, function12);
    }

    @NotNull
    public final SimpleQuirk<T> quirkMoLangCondition(@NotNull Pair<Float, Float> secondsBetweenOccurrences, @NotNull IntRange loopTimes, @NotNull ExpressionLike conditionExpression, @NotNull Function1<? super PoseableEntityState<T>, ? extends StatefulAnimation<T, ?>> animation) {
        Intrinsics.checkNotNullParameter(secondsBetweenOccurrences, (String)"secondsBetweenOccurrences");
        Intrinsics.checkNotNullParameter((Object)loopTimes, (String)"loopTimes");
        Intrinsics.checkNotNullParameter((Object)conditionExpression, (String)"conditionExpression");
        Intrinsics.checkNotNullParameter(animation, (String)"animation");
        return new SimpleQuirk(secondsBetweenOccurrences, (Function1)new Function1<PoseableEntityState<T>, Boolean>(conditionExpression){
            final /* synthetic */ ExpressionLike $conditionExpression;
            {
                this.$conditionExpression = $conditionExpression;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull PoseableEntityState<T> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return MoLangExtensionsKt.resolveBoolean(it.getRuntime(), this.$conditionExpression);
            }
        }, loopTimes, (Function1)new Function1<PoseableEntityState<T>, Iterable<? extends StatefulAnimation<T, ?>>>(animation){
            final /* synthetic */ Function1<PoseableEntityState<T>, StatefulAnimation<T, ?>> $animation;
            {
                this.$animation = $animation;
                super(1);
            }

            @NotNull
            public final Iterable<StatefulAnimation<T, ?>> invoke(@NotNull PoseableEntityState<T> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return CollectionsKt.listOf((Object)this.$animation.invoke(it));
            }
        });
    }

    public static /* synthetic */ SimpleQuirk quirkMoLangCondition$default(PoseableEntityModel poseableEntityModel, Pair pair, IntRange intRange, ExpressionLike expressionLike, Function1 function1, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: quirkMoLangCondition");
        }
        if ((n & 1) != 0) {
            pair = TuplesKt.to((Object)Float.valueOf(8.0f), (Object)Float.valueOf(30.0f));
        }
        if ((n & 2) != 0) {
            intRange = new IntRange(1, 1);
        }
        if ((n & 4) != 0) {
            expressionLike = MoLangExtensionsKt.asExpressionLike("true");
        }
        return poseableEntityModel.quirkMoLangCondition((Pair<Float, Float>)pair, intRange, expressionLike, function1);
    }

    @NotNull
    public final SimpleQuirk<T> quirkMultiple(@NotNull Pair<Float, Float> secondsBetweenOccurrences, @NotNull IntRange loopTimes, @NotNull Function1<? super PoseableEntityState<T>, Boolean> condition2, @NotNull Function1<? super PoseableEntityState<T>, ? extends List<? extends StatefulAnimation<T, ?>>> animations2) {
        Intrinsics.checkNotNullParameter(secondsBetweenOccurrences, (String)"secondsBetweenOccurrences");
        Intrinsics.checkNotNullParameter((Object)loopTimes, (String)"loopTimes");
        Intrinsics.checkNotNullParameter(condition2, (String)"condition");
        Intrinsics.checkNotNullParameter(animations2, (String)"animations");
        return new SimpleQuirk(secondsBetweenOccurrences, condition2, loopTimes, (Function1)new Function1<PoseableEntityState<T>, Iterable<? extends StatefulAnimation<T, ?>>>(animations2){
            final /* synthetic */ Function1<PoseableEntityState<T>, List<StatefulAnimation<T, ?>>> $animations;
            {
                this.$animations = $animations;
                super(1);
            }

            @NotNull
            public final Iterable<StatefulAnimation<T, ?>> invoke(@NotNull PoseableEntityState<T> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return (Iterable)this.$animations.invoke(it);
            }
        });
    }

    public static /* synthetic */ SimpleQuirk quirkMultiple$default(PoseableEntityModel poseableEntityModel, Pair pair, IntRange intRange, Function1 function1, Function1 function12, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: quirkMultiple");
        }
        if ((n & 1) != 0) {
            pair = TuplesKt.to((Object)Float.valueOf(8.0f), (Object)Float.valueOf(30.0f));
        }
        if ((n & 2) != 0) {
            intRange = new IntRange(1, 1);
        }
        if ((n & 4) != 0) {
            function1 = quirkMultiple.1.INSTANCE;
        }
        return poseableEntityModel.quirkMultiple((Pair<Float, Float>)pair, intRange, function1, function12);
    }

    @NotNull
    public final StatefulAnimation<T, ModelFrame> getDummyAnimation() {
        return this.dummyAnimation;
    }

    private static final RenderType _init_$lambda$0(Function1 $tmp0, ResourceLocation p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (RenderType)$tmp0.invoke((Object)p0);
    }

    private static final Object functions$lambda$1(PoseableEntityModel this$0, MoParams params) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        String group = params.getString(0);
        String animation = params.getString(1);
        Intrinsics.checkNotNullExpressionValue((Object)group, (String)"group");
        Intrinsics.checkNotNullExpressionValue((Object)animation, (String)"animation");
        BedrockStatefulAnimation anim = PoseableEntityModel.bedrockStateful$default(this$0, group, animation, null, 4, null);
        Set excludedLabels = new LinkedHashSet();
        Function1 curve2 = functions.1.curve.1.INSTANCE;
        int n = params.getParams().size();
        for (int index = 2; index < n; ++index) {
            String label;
            Object param = params.get(index);
            if (param instanceof ObjectValue) {
                Object t = ((ObjectValue)param).getObj();
                Intrinsics.checkNotNull(t, (String)"null cannot be cast to non-null type kotlin.Function1<kotlin.Float, kotlin.Float>{ bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt.WaveFunction }");
                curve2 = (Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(t, (int)1);
                continue;
            }
            if (params.getString(index) == null) continue;
            excludedLabels.add(label);
        }
        return new ObjectValue(new PrimaryAnimation(anim, curve2, excludedLabels, false, 8, null), null, null, 6, null);
    }

    private static final Object functions$lambda$2(PoseableEntityModel this$0, MoParams params) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        String group = params.getString(0);
        String animation = params.getString(1);
        Intrinsics.checkNotNullExpressionValue((Object)group, (String)"group");
        Intrinsics.checkNotNullExpressionValue((Object)animation, (String)"animation");
        BedrockStatefulAnimation anim = PoseableEntityModel.bedrockStateful$default(this$0, group, animation, null, 4, null);
        return new ObjectValue(anim, null, null, 6, null);
    }

    private static final Object functions$lambda$3(PoseableEntityModel this$0, MoParams params) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        String group = params.getString(0);
        String animation = params.getString(1);
        Intrinsics.checkNotNullExpressionValue((Object)group, (String)"group");
        Intrinsics.checkNotNullExpressionValue((Object)animation, (String)"animation");
        BedrockStatelessAnimation anim = PoseableEntityModel.bedrock$default(this$0, group, animation, null, 4, null);
        return new ObjectValue(anim, null, null, 6, null);
    }

    private static final Object functions$lambda$4(PoseableEntityModel this$0, MoParams params) {
        Number number;
        Double maxYaw;
        Number number2;
        Double minPitch;
        Number number3;
        Double maxPitch;
        Number number4;
        Double yawMultiplier;
        Number number5;
        Double pitchMultiplier;
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        String boneName = params.getString(0);
        Intrinsics.checkNotNullExpressionValue((Object)params, (String)"params");
        Number number6 = MoLangExtensionsKt.getDoubleOrNull(params, 1);
        if (number6 == null) {
            number6 = pitchMultiplier = Float.valueOf(1.0f);
        }
        if ((number5 = MoLangExtensionsKt.getDoubleOrNull(params, 2)) == null) {
            number5 = yawMultiplier = Float.valueOf(1.0f);
        }
        if ((number4 = MoLangExtensionsKt.getDoubleOrNull(params, 3)) == null) {
            number4 = maxPitch = Float.valueOf(70.0f);
        }
        if ((number3 = MoLangExtensionsKt.getDoubleOrNull(params, 4)) == null) {
            number3 = minPitch = Float.valueOf(-45.0f);
        }
        if ((number2 = MoLangExtensionsKt.getDoubleOrNull(params, 5)) == null) {
            number2 = maxYaw = Float.valueOf(45.0f);
        }
        if ((number = MoLangExtensionsKt.getDoubleOrNull(params, 6)) == null) {
            number = Float.valueOf(-45.0f);
        }
        Double minYaw = number;
        ModelFrame modelFrame = this$0;
        Intrinsics.checkNotNullExpressionValue((Object)boneName, (String)"boneName");
        return new ObjectValue(new SingleBoneLookAnimation(modelFrame, (Bone)this$0.getPart(boneName), ((Number)pitchMultiplier).floatValue(), ((Number)yawMultiplier).floatValue(), ((Number)maxPitch).floatValue(), ((Number)minPitch).floatValue(), ((Number)maxYaw).floatValue(), ((Number)minYaw).floatValue()), null, null, 6, null);
    }

    private static final Object functions$lambda$5(PoseableEntityModel this$0, MoParams params) {
        String string;
        String leftBackLeftName;
        String string2;
        String leftFrontRightName;
        String string3;
        String leftFrontLeftName;
        String string4;
        Double amplitudeMultiplier;
        Number number;
        Double periodMultiplier;
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullExpressionValue((Object)params, (String)"params");
        Number number2 = MoLangExtensionsKt.getDoubleOrNull(params, 0);
        if (number2 == null) {
            number2 = periodMultiplier = Float.valueOf(0.6662f);
        }
        if ((number = MoLangExtensionsKt.getDoubleOrNull(params, 1)) == null) {
            number = amplitudeMultiplier = Float.valueOf(1.4f);
        }
        if ((string4 = MoLangExtensionsKt.getStringOrNull(params, 2)) == null) {
            string4 = leftFrontLeftName = "leg_front_left";
        }
        if ((string3 = MoLangExtensionsKt.getStringOrNull(params, 3)) == null) {
            string3 = leftFrontRightName = "leg_front_right";
        }
        if ((string2 = MoLangExtensionsKt.getStringOrNull(params, 4)) == null) {
            string2 = leftBackLeftName = "leg_back_left";
        }
        if ((string = MoLangExtensionsKt.getStringOrNull(params, 5)) == null) {
            string = "leg_back_right";
        }
        String leftBackRightName = string;
        float f = ((Number)periodMultiplier).floatValue();
        float f2 = ((Number)amplitudeMultiplier).floatValue();
        ModelPart modelPart = this$0.getPart(leftFrontLeftName);
        ModelPart modelPart2 = this$0.getPart(leftFrontRightName);
        ModelPart modelPart3 = this$0.getPart(leftBackLeftName);
        ModelPart modelPart4 = this$0.getPart(leftBackRightName);
        return new ObjectValue(new QuadrupedWalkAnimation(this$0, (Bone)modelPart, (Bone)modelPart2, (Bone)modelPart3, (Bone)modelPart4, f, f2), null, null, 6, null);
    }

    private static final Object functions$lambda$6(PoseableEntityModel this$0, MoParams params) {
        String string;
        String leftLegName;
        String string2;
        Double amplitudeMultiplier;
        Number number;
        Double periodMultiplier;
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullExpressionValue((Object)params, (String)"params");
        Number number2 = MoLangExtensionsKt.getDoubleOrNull(params, 0);
        if (number2 == null) {
            number2 = periodMultiplier = Float.valueOf(0.6662f);
        }
        if ((number = MoLangExtensionsKt.getDoubleOrNull(params, 1)) == null) {
            number = amplitudeMultiplier = Float.valueOf(1.4f);
        }
        if ((string2 = MoLangExtensionsKt.getStringOrNull(params, 2)) == null) {
            string2 = leftLegName = "leg_left";
        }
        if ((string = MoLangExtensionsKt.getStringOrNull(params, 3)) == null) {
            string = "leg_right";
        }
        String rightLegName = string;
        return new ObjectValue(new BipedWalkAnimation(this$0, ((Number)periodMultiplier).floatValue(), ((Number)amplitudeMultiplier).floatValue(), (Bone)this$0.getPart(leftLegName), (Bone)this$0.getPart(rightLegName)), null, null, 6, null);
    }

    private static final Object functions$lambda$7(PoseableEntityModel this$0, MoParams params) {
        String string;
        String leftArmName;
        String string2;
        Double amplitudeMultiplier;
        Number number;
        Double swingPeriodMultiplier;
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullExpressionValue((Object)params, (String)"params");
        Number number2 = MoLangExtensionsKt.getDoubleOrNull(params, 0);
        if (number2 == null) {
            number2 = swingPeriodMultiplier = Float.valueOf(0.6662f);
        }
        if ((number = MoLangExtensionsKt.getDoubleOrNull(params, 1)) == null) {
            number = amplitudeMultiplier = Float.valueOf(1.0f);
        }
        if ((string2 = MoLangExtensionsKt.getStringOrNull(params, 2)) == null) {
            string2 = leftArmName = "arm_left";
        }
        if ((string = MoLangExtensionsKt.getStringOrNull(params, 3)) == null) {
            string = "arm_right";
        }
        String rightArmName = string;
        return new ObjectValue(new BimanualSwingAnimation(this$0, ((Number)swingPeriodMultiplier).floatValue(), ((Number)amplitudeMultiplier).floatValue(), (Bone)this$0.getPart(leftArmName), (Bone)this$0.getPart(rightArmName)), null, null, 6, null);
    }

    private static final Object functions$lambda$8(PoseableEntityModel this$0, MoParams params) {
        String string;
        String wingLeft;
        String string2;
        int axisIndex;
        String axis;
        String string3;
        Double verticalShift;
        Number number;
        Double period;
        Number number2;
        Double amplitude;
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullExpressionValue((Object)params, (String)"params");
        Number number3 = MoLangExtensionsKt.getDoubleOrNull(params, 0);
        if (number3 == null) {
            number3 = amplitude = Float.valueOf(0.9f);
        }
        if ((number2 = MoLangExtensionsKt.getDoubleOrNull(params, 1)) == null) {
            number2 = period = Float.valueOf(0.9f);
        }
        if ((number = MoLangExtensionsKt.getDoubleOrNull(params, 2)) == null) {
            number = verticalShift = Float.valueOf(0.0f);
        }
        if ((string3 = MoLangExtensionsKt.getStringOrNull(params, 3)) == null) {
            string3 = "y";
        }
        switch (axis = string3) {
            case "x": {
                int n = 0;
                break;
            }
            case "y": {
                int n = 1;
                break;
            }
            case "z": {
                int n = 2;
                break;
            }
            default: {
                int n = axisIndex = 1;
            }
        }
        if ((string2 = MoLangExtensionsKt.getStringOrNull(params, 4)) == null) {
            string2 = wingLeft = "wing_left";
        }
        if ((string = MoLangExtensionsKt.getStringOrNull(params, 5)) == null) {
            string = "wing_right";
        }
        String wingRight = string;
        float f = ((Number)verticalShift).floatValue();
        float f2 = ((Number)period).floatValue();
        Function1 function1 = WaveFunctionKt.sineFunction$default(((Number)amplitude).floatValue(), f2, 0.0f, f, 4, null);
        ModelPart modelPart = this$0.getPart(wingLeft);
        ModelPart modelPart2 = this$0.getPart(wingRight);
        return new ObjectValue(new WingFlapIdleAnimation(this$0, (Bone)modelPart, (Bone)modelPart2, function1, null, axisIndex, 16, null), null, null, 6, null);
    }

    /*
     * WARNING - void declaration
     */
    private static final Object functions$lambda$11(PoseableEntityModel this$0, MoParams params) {
        Number number;
        Double minSeconds;
        Object object;
        String animationGroup;
        block9: {
            block8: {
                List list;
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                animationGroup = params.getString(0);
                object = params.get(1);
                if (object == null) break block8;
                Object it = object;
                boolean bl = false;
                if (it instanceof ArrayStruct) {
                    void $this$mapTo$iv$iv;
                    Iterable $this$map$iv = ((ArrayStruct)it).getMap().values();
                    boolean $i$f$map = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                    boolean $i$f$mapTo = false;
                    for (Object item$iv$iv : $this$mapTo$iv$iv) {
                        void it2;
                        MoValue moValue = (MoValue)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl2 = false;
                        collection.add(it2.asString());
                    }
                    list = (List)destination$iv$iv;
                } else {
                    list = CollectionsKt.listOf((Object)it.asString());
                }
                object = list;
                if (list != null) break block9;
            }
            object = CollectionsKt.emptyList();
        }
        Object animationNames = object;
        Intrinsics.checkNotNullExpressionValue((Object)params, (String)"params");
        Number number2 = MoLangExtensionsKt.getDoubleOrNull(params, 2);
        if (number2 == null) {
            number2 = minSeconds = Float.valueOf(8.0f);
        }
        if ((number = MoLangExtensionsKt.getDoubleOrNull(params, 3)) == null) {
            number = Float.valueOf(30.0f);
        }
        Double maxSeconds = number;
        Double d = MoLangExtensionsKt.getDoubleOrNull(params, 4);
        int loopTimes = d != null ? (int)d.doubleValue() : 1;
        Pair pair = TuplesKt.to((Object)Float.valueOf(((Number)minSeconds).floatValue()), (Object)Float.valueOf(((Number)maxSeconds).floatValue()));
        IntRange intRange = new IntRange(1, loopTimes);
        return new ObjectValue(this$0.quirk((Pair<Float, Float>)pair, intRange, functions.9.1.INSTANCE, (Function1)new Function1<PoseableEntityState<T>, StatefulAnimation<T, ?>>(this$0, animationGroup, (List<String>)animationNames){
            final /* synthetic */ PoseableEntityModel<T> this$0;
            final /* synthetic */ String $animationGroup;
            final /* synthetic */ List<String> $animationNames;
            {
                this.this$0 = $receiver;
                this.$animationGroup = $animationGroup;
                this.$animationNames = $animationNames;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<T, ?> invoke(@NotNull PoseableEntityState<T> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                String string = this.$animationGroup;
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"animationGroup");
                Object object = CollectionsKt.random((Collection)this.$animationNames, (Random)((Random)Random.Default));
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"animationNames.random()");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, string, (String)object, null, 4, null);
            }
        }), null, null, 6, null);
    }

    /*
     * WARNING - void declaration
     */
    private static final Object functions$lambda$14(PoseableEntityModel this$0, MoParams params) {
        Number number;
        Double minSeconds;
        Object object;
        String animationGroup;
        block11: {
            block10: {
                List list;
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                animationGroup = params.getString(0);
                object = params.get(1);
                if (object == null) break block10;
                Object it = object;
                boolean bl = false;
                if (it instanceof ArrayStruct) {
                    void $this$mapTo$iv$iv;
                    Iterable $this$map$iv = ((ArrayStruct)it).getMap().values();
                    boolean $i$f$map = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                    boolean $i$f$mapTo = false;
                    for (Object item$iv$iv : $this$mapTo$iv$iv) {
                        void it2;
                        MoValue moValue = (MoValue)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl2 = false;
                        collection.add(it2.asString());
                    }
                    list = (List)destination$iv$iv;
                } else {
                    list = CollectionsKt.listOf((Object)it.asString());
                }
                object = list;
                if (list != null) break block11;
            }
            object = CollectionsKt.emptyList();
        }
        Object animationNames = object;
        Intrinsics.checkNotNullExpressionValue((Object)params, (String)"params");
        Number number2 = MoLangExtensionsKt.getDoubleOrNull(params, 2);
        if (number2 == null) {
            number2 = minSeconds = Float.valueOf(8.0f);
        }
        if ((number = MoLangExtensionsKt.getDoubleOrNull(params, 3)) == null) {
            number = Float.valueOf(30.0f);
        }
        Double maxSeconds = number;
        Double d = MoLangExtensionsKt.getDoubleOrNull(params, 4);
        int loopTimes = d != null ? (int)d.doubleValue() : 1;
        Set excludedLabels = new LinkedHashSet();
        Ref.ObjectRef curve2 = new Ref.ObjectRef();
        curve2.element = functions.10.curve.1.INSTANCE;
        int n = params.getParams().size();
        for (int index = 5; index < n; ++index) {
            String label;
            Object param = params.get(index);
            if (param instanceof ObjectValue) {
                Object t = ((ObjectValue)param).getObj();
                Intrinsics.checkNotNull(t, (String)"null cannot be cast to non-null type kotlin.Function1<kotlin.Float, kotlin.Float>{ bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt.WaveFunction }");
                curve2.element = (Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(t, (int)1);
                continue;
            }
            if (params.getString(index) == null) continue;
            excludedLabels.add(label);
        }
        Pair pair = TuplesKt.to((Object)Float.valueOf(((Number)minSeconds).floatValue()), (Object)Float.valueOf(((Number)maxSeconds).floatValue()));
        IntRange intRange = new IntRange(1, loopTimes);
        return new ObjectValue(this$0.quirk((Pair<Float, Float>)pair, intRange, functions.10.1.INSTANCE, (Function1)new Function1<PoseableEntityState<T>, StatefulAnimation<T, ?>>(this$0, animationGroup, (List<String>)animationNames, (Ref.ObjectRef<Function1<Float, Float>>)curve2, excludedLabels){
            final /* synthetic */ PoseableEntityModel<T> this$0;
            final /* synthetic */ String $animationGroup;
            final /* synthetic */ List<String> $animationNames;
            final /* synthetic */ Ref.ObjectRef<Function1<Float, Float>> $curve;
            final /* synthetic */ Set<String> $excludedLabels;
            {
                this.this$0 = $receiver;
                this.$animationGroup = $animationGroup;
                this.$animationNames = $animationNames;
                this.$curve = $curve;
                this.$excludedLabels = $excludedLabels;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<T, ?> invoke(@NotNull PoseableEntityState<T> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                String string = this.$animationGroup;
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"animationGroup");
                Object object = CollectionsKt.random((Collection)this.$animationNames, (Random)((Random)Random.Default));
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"animationNames.random()");
                BedrockStatefulAnimation bedrockStatefulAnimation = PoseableEntityModel.bedrockStateful$default(this.this$0, string, (String)object, null, 4, null);
                Function1 function1 = (Function1)this.$curve.element;
                return new PrimaryAnimation<T>(bedrockStatefulAnimation, function1, this.$excludedLabels, false, 8, null);
            }
        }), null, null, 6, null);
    }

    public PoseableEntityModel() {
        this(null, 1, null);
    }
}

