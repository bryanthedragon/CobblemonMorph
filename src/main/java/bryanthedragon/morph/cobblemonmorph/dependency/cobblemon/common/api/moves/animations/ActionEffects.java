/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.IntRange
 *  net.minecraft.advancements.critereon.MinMaxBounds$Doubles
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.AddHoldsActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.AnimationActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.CanInterruptActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.CannotInterruptActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.EntityMoLangActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.EntityParticlesActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.EntitySoundActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ForkActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.MoLangActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.MoveToTargetActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ParallelActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.PauseActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.RemoveHoldsActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ReturnToPositionActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.RunActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.SavePositionActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.SequenceActionEffectKeyframe;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.adapters.CobblemonStatTypeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ActionEffectKeyframeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BoxAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BoxCollectionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ExpressionLikeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.FloatNumberRangeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.LiteralHexColorAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SingleToPluralAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.VerboseIntRangeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.VerboseVec3dAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.awt.Color;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b0\u00101J#\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u000b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR#\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\"\u0010\u0014\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u00048\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR \u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00000\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u001a\u0010\"\u001a\u00020!8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u001a\u0010'\u001a\u00020&8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*R \u0010,\u001a\b\u0012\u0004\u0012\u00020\u00020+8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/\u00a8\u00062"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/ActionEffects;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "", "Lnet/minecraft/resources/ResourceLocation;", "data", "", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "", "actionEffects", "Ljava/util/Map;", "getActionEffects", "()Ljava/util/Map;", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nActionEffects.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActionEffects.kt\ncom/cobblemon/mod/common/api/moves/animations/ActionEffects\n+ 2 ActionEffectKeyframe.kt\ncom/cobblemon/mod/common/api/moves/animations/keyframes/ActionEffectKeyframe$Companion\n*L\n1#1,95:1\n27#2,2:96\n27#2,2:98\n27#2,2:100\n27#2,2:102\n27#2,2:104\n27#2,2:106\n27#2,2:108\n27#2,2:110\n27#2,2:112\n27#2,2:114\n27#2,2:116\n27#2,2:118\n27#2,2:120\n27#2,2:122\n27#2,2:124\n27#2,2:126\n27#2,2:128\n*S KotlinDebug\n*F\n+ 1 ActionEffects.kt\ncom/cobblemon/mod/common/api/moves/animations/ActionEffects\n*L\n42#1:96,2\n43#1:98,2\n44#1:100,2\n45#1:102,2\n46#1:104,2\n47#1:106,2\n48#1:108,2\n49#1:110,2\n50#1:112,2\n51#1:114,2\n52#1:116,2\n53#1:118,2\n54#1:120,2\n55#1:122,2\n56#1:124,2\n57#1:126,2\n58#1:128,2\n*E\n"})
public final class ActionEffects
implements JsonDataRegistry<ActionEffectTimeline> {
    @NotNull
    public static final ActionEffects INSTANCE;
    @NotNull
    private static final ResourceLocation id;
    @NotNull
    private static final PackType type;
    @NotNull
    private static final SimpleObservable<ActionEffects> observable;
    private static final Gson gson;
    @NotNull
    private static final TypeToken<ActionEffectTimeline> typeToken;
    @NotNull
    private static final String resourcePath;
    @NotNull
    private static final Map<ResourceLocation, ActionEffectTimeline> actionEffects;

    private ActionEffects() {
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @NotNull
    public PackType getType() {
        return type;
    }

    @NotNull
    public SimpleObservable<ActionEffects> getObservable() {
        return observable;
    }

    @Override
    public Gson getGson() {
        return gson;
    }

    @Override
    @NotNull
    public TypeToken<ActionEffectTimeline> getTypeToken() {
        return typeToken;
    }

    @Override
    @NotNull
    public String getResourcePath() {
        return resourcePath;
    }

    @NotNull
    public final Map<ResourceLocation, ActionEffectTimeline> getActionEffects() {
        return actionEffects;
    }

    @Override
    public void reload(@NotNull Map<ResourceLocation, ActionEffectTimeline> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        actionEffects.clear();
        actionEffects.putAll(data);
        ActionEffects[] actionEffectsArray = new ActionEffects[]{this};
        this.getObservable().emit((ActionEffects[])actionEffectsArray);
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        JsonDataRegistry.DefaultImpls.reload(this, manager);
    }

    static {
        ActionEffectKeyframe.Companion this_$iv;
        INSTANCE = new ActionEffects();
        id = MiscUtils.cobblemonResource("action_effects");
        type = PackType.SERVER_DATA;
        observable = new SimpleObservable();
        Type[] typeArray = ActionEffectKeyframe.Companion;
        String type$iv = "animation";
        boolean $i$f$register = false;
        this_$iv.getTypes().put(type$iv, AnimationActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "entity_molang";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, EntityMoLangActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "molang";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, MoLangActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "parallel";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, ParallelActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "can_interrupt";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, CanInterruptActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "cannot_interrupt";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, CannotInterruptActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "remove_holds";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, RemoveHoldsActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "add_holds";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, AddHoldsActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "move_to_target";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, MoveToTargetActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "return_to_position";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, ReturnToPositionActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "pause";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, PauseActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "save_position";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, SavePositionActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "fork";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, ForkActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "sequence";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, SequenceActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "run_action_effect";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, RunActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "entity_particles";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, EntityParticlesActionEffectKeyframe.class);
        this_$iv = ActionEffectKeyframe.Companion;
        type$iv = "entity_sound";
        $i$f$register = false;
        this_$iv.getTypes().put(type$iv, EntitySoundActionEffectKeyframe.class);
        typeArray = new Type[]{AABB.class};
        GsonBuilder gsonBuilder = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().registerTypeAdapter((Type)((Object)ActionEffectKeyframe.class), (Object)ActionEffectKeyframeAdapter.INSTANCE).registerTypeAdapter((Type)((Object)MinMaxBounds.Doubles.class), (Object)FloatNumberRangeAdapter.INSTANCE).registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)Collection.class)), (Type[])typeArray).getType(), (Object)BoxCollectionAdapter.INSTANCE).registerTypeAdapter((Type)((Object)AABB.class), (Object)BoxAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Vec3.class), (Object)VerboseVec3dAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ResourceLocation.class), (Object)IdentifierAdapter.INSTANCE).registerTypeAdapter((Type)((Object)IntRange.class), (Object)VerboseIntRangeAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Color.class), (Object)LiteralHexColorAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Stat.class), (Object)CobblemonStatTypeAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Expression.class), (Object)ExpressionAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ExpressionLike.class), (Object)ExpressionLikeAdapter.INSTANCE);
        typeArray = new Type[]{TypeToken.get(ActionEffectKeyframe.class).getType()};
        gson = gsonBuilder.registerTypeAdapter(TypeToken.getParameterized((Type)TypeToken.get(List.class).getType(), (Type[])typeArray).getType(), new SingleToPluralAdapter(ActionEffectKeyframe.class, gson.1.INSTANCE)).create();
        TypeToken typeToken = TypeToken.get(ActionEffectTimeline.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken, (String)"get(ActionEffectTimeline::class.java)");
        ActionEffects.typeToken = typeToken;
        resourcePath = "action_effects";
        actionEffects = new LinkedHashMap();
    }
}

