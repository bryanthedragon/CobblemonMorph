/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.FriendshipUpdatedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.effects.CaptureEffects;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.effects.FriendshipEarningBoostEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.BaseStatModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.CatchRateModifiers;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.GuaranteedModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.LabelModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers.MultiplierModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\bd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000b\b\u0002\u00a2\u0006\u0006\b\u00a5\u0001\u0010\u00a6\u0001J\u0013\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\b\b\u0010\tJc\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\n2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u00102\b\b\u0002\u0010\u0013\u001a\u00020\u000e2\b\b\u0002\u0010\u0015\u001a\u00020\u0014H\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0018\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0007\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0018\u0010\u0019J#\u0010\u001d\u001a\u00020\u001c2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00020\u001aH\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0017\u0010!\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020\u001fH\u0016\u00a2\u0006\u0004\b!\u0010\"R\u0011\u0010%\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b#\u0010$R\u0011\u0010'\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b&\u0010$R\u0011\u0010)\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b(\u0010$R\u0011\u0010+\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b*\u0010$R\u0011\u0010-\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b,\u0010$R\u0011\u0010/\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b.\u0010$R\u0011\u00101\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b0\u0010$R\u0011\u00103\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b2\u0010$R\u0011\u00105\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b4\u0010$R\u0011\u00107\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b6\u0010$R\u0011\u00109\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b8\u0010$R\u0011\u0010;\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b:\u0010$R\u0011\u0010=\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b<\u0010$R\u0011\u0010?\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b>\u0010$R\u0011\u0010A\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b@\u0010$R\u0011\u0010C\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bB\u0010$R\u0011\u0010E\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bD\u0010$R\u0011\u0010G\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bF\u0010$R\u0011\u0010I\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bH\u0010$R\u0011\u0010K\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bJ\u0010$R\u0011\u0010M\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bL\u0010$R\u0011\u0010O\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bN\u0010$R\u0011\u0010Q\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bP\u0010$R\u0011\u0010S\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bR\u0010$R\u0011\u0010U\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bT\u0010$R\u0011\u0010W\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bV\u0010$R\u0011\u0010Y\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bX\u0010$R\u0011\u0010[\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bZ\u0010$R\u0011\u0010]\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\\\u0010$R\u0011\u0010_\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b^\u0010$R\u0011\u0010a\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b`\u0010$R\u0011\u0010c\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bb\u0010$R\u0011\u0010e\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bd\u0010$R\u0011\u0010g\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bf\u0010$R\u0011\u0010i\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bh\u0010$R\u0011\u0010k\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bj\u0010$R\u0011\u0010m\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bl\u0010$R\u0011\u0010o\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bn\u0010$R\u0011\u0010q\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bp\u0010$R\u0011\u0010s\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\br\u0010$R\u0011\u0010u\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bt\u0010$R\u0011\u0010w\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bv\u0010$R\u0011\u0010y\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bx\u0010$R\u0011\u0010{\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\bz\u0010$R\u0011\u0010}\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b|\u0010$R\u0011\u0010\u007f\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b~\u0010$R\u0013\u0010\u0081\u0001\u001a\u00020\u00028F\u00a2\u0006\u0007\u001a\u0005\b\u0080\u0001\u0010$R\u0013\u0010\u0083\u0001\u001a\u00020\u00028F\u00a2\u0006\u0007\u001a\u0005\b\u0082\u0001\u0010$R5\u0010\u0086\u0001\u001a \u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00020\u0084\u0001j\u000f\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0002`\u0085\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u0086\u0001\u0010\u0087\u0001R5\u0010\u0088\u0001\u001a \u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00020\u0084\u0001j\u000f\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0002`\u0085\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u0088\u0001\u0010\u0087\u0001R \u0010\u008a\u0001\u001a\u00030\u0089\u00018\u0016X\u0096\u0004\u00a2\u0006\u0010\n\u0006\b\u008a\u0001\u0010\u008b\u0001\u001a\u0006\b\u008c\u0001\u0010\u008d\u0001R\u001f\u0010\u008e\u0001\u001a\u00020\u00108\u0016X\u0096\u0004\u00a2\u0006\u0010\n\u0006\b\u008e\u0001\u0010\u008f\u0001\u001a\u0006\b\u0090\u0001\u0010\u0091\u0001R&\u0010\u0093\u0001\u001a\t\u0012\u0004\u0012\u00020\u00000\u0092\u00018\u0016X\u0096\u0004\u00a2\u0006\u0010\n\u0006\b\u0093\u0001\u0010\u0094\u0001\u001a\u0006\b\u0095\u0001\u0010\u0096\u0001R\u001f\u0010\u0097\u0001\u001a\u00020\u00068\u0016X\u0096D\u00a2\u0006\u0010\n\u0006\b\u0097\u0001\u0010\u0098\u0001\u001a\u0006\b\u0099\u0001\u0010\u009a\u0001R \u0010\u009c\u0001\u001a\u00030\u009b\u00018\u0016X\u0096\u0004\u00a2\u0006\u0010\n\u0006\b\u009c\u0001\u0010\u009d\u0001\u001a\u0006\b\u009e\u0001\u0010\u009f\u0001R&\u0010\u00a1\u0001\u001a\t\u0012\u0004\u0012\u00020\u00020\u00a0\u00018\u0016X\u0096\u0004\u00a2\u0006\u0010\n\u0006\b\u00a1\u0001\u0010\u00a2\u0001\u001a\u0006\b\u00a3\u0001\u0010\u00a4\u0001\u00a8\u0006\u00a7\u0001"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/PokeBalls;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "", "all", "()Ljava/util/List;", "", "name", "byName", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/pokeball/PokeBall;", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "modifier", "Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureEffect;", "effects", "", "waterDragValue", "Lnet/minecraft/resources/ResourceLocation;", "model2d", "model3d", "throwPower", "", "ancient", "createDefault", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;Ljava/util/List;FLnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;FZ)Lcom/cobblemon/mod/common/pokeball/PokeBall;", "getPokeBall", "(Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/pokeball/PokeBall;", "", "data", "", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "getANCIENT_AZURE_BALL", "()Lcom/cobblemon/mod/common/pokeball/PokeBall;", "ANCIENT_AZURE_BALL", "getANCIENT_CITRINE_BALL", "ANCIENT_CITRINE_BALL", "getANCIENT_FEATHER_BALL", "ANCIENT_FEATHER_BALL", "getANCIENT_GIGATON_BALL", "ANCIENT_GIGATON_BALL", "getANCIENT_GREAT_BALL", "ANCIENT_GREAT_BALL", "getANCIENT_HEAVY_BALL", "ANCIENT_HEAVY_BALL", "getANCIENT_IVORY_BALL", "ANCIENT_IVORY_BALL", "getANCIENT_JET_BALL", "ANCIENT_JET_BALL", "getANCIENT_LEADEN_BALL", "ANCIENT_LEADEN_BALL", "getANCIENT_ORIGIN_BALL", "ANCIENT_ORIGIN_BALL", "getANCIENT_POKE_BALL", "ANCIENT_POKE_BALL", "getANCIENT_ROSEATE_BALL", "ANCIENT_ROSEATE_BALL", "getANCIENT_SLATE_BALL", "ANCIENT_SLATE_BALL", "getANCIENT_ULTRA_BALL", "ANCIENT_ULTRA_BALL", "getANCIENT_VERDANT_BALL", "ANCIENT_VERDANT_BALL", "getANCIENT_WING_BALL", "ANCIENT_WING_BALL", "getAZURE_BALL", "AZURE_BALL", "getBEAST_BALL", "BEAST_BALL", "getCHERISH_BALL", "CHERISH_BALL", "getCITRINE_BALL", "CITRINE_BALL", "getDIVE_BALL", "DIVE_BALL", "getDREAM_BALL", "DREAM_BALL", "getDUSK_BALL", "DUSK_BALL", "getFAST_BALL", "FAST_BALL", "getFRIEND_BALL", "FRIEND_BALL", "getGREAT_BALL", "GREAT_BALL", "getHEAL_BALL", "HEAL_BALL", "getHEAVY_BALL", "HEAVY_BALL", "getLEVEL_BALL", "LEVEL_BALL", "getLOVE_BALL", "LOVE_BALL", "getLURE_BALL", "LURE_BALL", "getLUXURY_BALL", "LUXURY_BALL", "getMASTER_BALL", "MASTER_BALL", "getMOON_BALL", "MOON_BALL", "getNEST_BALL", "NEST_BALL", "getNET_BALL", "NET_BALL", "getPARK_BALL", "PARK_BALL", "getPOKE_BALL", "POKE_BALL", "getPREMIER_BALL", "PREMIER_BALL", "getQUICK_BALL", "QUICK_BALL", "getREPEAT_BALL", "REPEAT_BALL", "getROSEATE_BALL", "ROSEATE_BALL", "getSAFARI_BALL", "SAFARI_BALL", "getSLATE_BALL", "SLATE_BALL", "getSPORT_BALL", "SPORT_BALL", "getTIMER_BALL", "TIMER_BALL", "getULTRA_BALL", "ULTRA_BALL", "getVERDANT_BALL", "VERDANT_BALL", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "custom", "Ljava/util/HashMap;", "defaults", "Lcom/google/gson/Gson;", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nPokeBalls.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokeBalls.kt\ncom/cobblemon/mod/common/api/pokeball/PokeBalls\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,258:1\n467#2,7:259\n*S KotlinDebug\n*F\n+ 1 PokeBalls.kt\ncom/cobblemon/mod/common/api/pokeball/PokeBalls\n*L\n234#1:259,7\n*E\n"})
public final class PokeBalls
implements JsonDataRegistry<PokeBall> {
    @NotNull
    public static final PokeBalls INSTANCE = new PokeBalls();
    @NotNull
    private static final ResourceLocation id = MiscUtilsKt.cobblemonResource("pokeballs");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;
    @NotNull
    private static final SimpleObservable<PokeBalls> observable = new SimpleObservable();
    @NotNull
    private static final Gson gson;
    @NotNull
    private static final TypeToken<PokeBall> typeToken;
    @NotNull
    private static final String resourcePath;
    @NotNull
    private static final HashMap<ResourceLocation, PokeBall> defaults;
    @NotNull
    private static final HashMap<ResourceLocation, PokeBall> custom;

    private PokeBalls() {
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
    public SimpleObservable<PokeBalls> getObservable() {
        return observable;
    }

    @Override
    @NotNull
    public Gson getGson() {
        return gson;
    }

    @Override
    @NotNull
    public TypeToken<PokeBall> getTypeToken() {
        return typeToken;
    }

    @Override
    @NotNull
    public String getResourcePath() {
        return resourcePath;
    }

    @NotNull
    public final PokeBall getPOKE_BALL() {
        return this.byName("poke_ball");
    }

    @NotNull
    public final PokeBall getSLATE_BALL() {
        return this.byName("slate_ball");
    }

    @NotNull
    public final PokeBall getAZURE_BALL() {
        return this.byName("azure_ball");
    }

    @NotNull
    public final PokeBall getVERDANT_BALL() {
        return this.byName("verdant_ball");
    }

    @NotNull
    public final PokeBall getROSEATE_BALL() {
        return this.byName("roseate_ball");
    }

    @NotNull
    public final PokeBall getCITRINE_BALL() {
        return this.byName("citrine_ball");
    }

    @NotNull
    public final PokeBall getGREAT_BALL() {
        return this.byName("great_ball");
    }

    @NotNull
    public final PokeBall getULTRA_BALL() {
        return this.byName("ultra_ball");
    }

    @NotNull
    public final PokeBall getMASTER_BALL() {
        return this.byName("master_ball");
    }

    @NotNull
    public final PokeBall getSAFARI_BALL() {
        return this.byName("safari_ball");
    }

    @NotNull
    public final PokeBall getFAST_BALL() {
        return this.byName("fast_ball");
    }

    @NotNull
    public final PokeBall getLEVEL_BALL() {
        return this.byName("level_ball");
    }

    @NotNull
    public final PokeBall getLURE_BALL() {
        return this.byName("lure_ball");
    }

    @NotNull
    public final PokeBall getHEAVY_BALL() {
        return this.byName("heavy_ball");
    }

    @NotNull
    public final PokeBall getLOVE_BALL() {
        return this.byName("love_ball");
    }

    @NotNull
    public final PokeBall getFRIEND_BALL() {
        return this.byName("friend_ball");
    }

    @NotNull
    public final PokeBall getMOON_BALL() {
        return this.byName("moon_ball");
    }

    @NotNull
    public final PokeBall getSPORT_BALL() {
        return this.byName("sport_ball");
    }

    @NotNull
    public final PokeBall getNET_BALL() {
        return this.byName("net_ball");
    }

    @NotNull
    public final PokeBall getDIVE_BALL() {
        return this.byName("dive_ball");
    }

    @NotNull
    public final PokeBall getNEST_BALL() {
        return this.byName("nest_ball");
    }

    @NotNull
    public final PokeBall getREPEAT_BALL() {
        return this.byName("repeat_ball");
    }

    @NotNull
    public final PokeBall getTIMER_BALL() {
        return this.byName("timer_ball");
    }

    @NotNull
    public final PokeBall getLUXURY_BALL() {
        return this.byName("luxury_ball");
    }

    @NotNull
    public final PokeBall getPREMIER_BALL() {
        return this.byName("premier_ball");
    }

    @NotNull
    public final PokeBall getDUSK_BALL() {
        return this.byName("dusk_ball");
    }

    @NotNull
    public final PokeBall getHEAL_BALL() {
        return this.byName("heal_ball");
    }

    @NotNull
    public final PokeBall getQUICK_BALL() {
        return this.byName("quick_ball");
    }

    @NotNull
    public final PokeBall getCHERISH_BALL() {
        return this.byName("cherish_ball");
    }

    @NotNull
    public final PokeBall getPARK_BALL() {
        return this.byName("park_ball");
    }

    @NotNull
    public final PokeBall getDREAM_BALL() {
        return this.byName("dream_ball");
    }

    @NotNull
    public final PokeBall getBEAST_BALL() {
        return this.byName("beast_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_POKE_BALL() {
        return this.byName("ancient_poke_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_CITRINE_BALL() {
        return this.byName("ancient_citrine_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_VERDANT_BALL() {
        return this.byName("ancient_verdant_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_AZURE_BALL() {
        return this.byName("ancient_azure_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_ROSEATE_BALL() {
        return this.byName("ancient_roseate_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_SLATE_BALL() {
        return this.byName("ancient_slate_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_IVORY_BALL() {
        return this.byName("ancient_ivory_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_GREAT_BALL() {
        return this.byName("ancient_great_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_ULTRA_BALL() {
        return this.byName("ancient_ultra_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_HEAVY_BALL() {
        return this.byName("ancient_heavy_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_LEADEN_BALL() {
        return this.byName("ancient_leaden_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_GIGATON_BALL() {
        return this.byName("ancient_gigaton_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_FEATHER_BALL() {
        return this.byName("ancient_feather_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_WING_BALL() {
        return this.byName("ancient_wing_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_JET_BALL() {
        return this.byName("ancient_jet_ball");
    }

    @NotNull
    public final PokeBall getANCIENT_ORIGIN_BALL() {
        return this.byName("ancient_origin_ball");
    }

    @Override
    public void reload(@NotNull Map<ResourceLocation, ? extends PokeBall> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        custom.clear();
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
    }

    @Nullable
    public final PokeBall getPokeBall(@NotNull ResourceLocation name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        PokeBall pokeBall = custom.get(name);
        if (pokeBall == null) {
            pokeBall = defaults.get(name);
        }
        return pokeBall;
    }

    @NotNull
    public final List<PokeBall> all() {
        Map $this$filterKeys$iv = defaults;
        boolean $i$f$filterKeys = false;
        LinkedHashMap result$iv = new LinkedHashMap();
        for (Map.Entry entry$iv : $this$filterKeys$iv.entrySet()) {
            ResourceLocation it = (ResourceLocation)entry$iv.getKey();
            boolean bl = false;
            if (!(!custom.containsKey(it))) continue;
            result$iv.put(entry$iv.getKey(), entry$iv.getValue());
        }
        Collection collection = ((Map)result$iv).values();
        Collection<PokeBall> collection2 = custom.values();
        Intrinsics.checkNotNullExpressionValue(collection2, (String)"custom.values");
        return CollectionsKt.plus(collection, (Iterable)collection2);
    }

    private final PokeBall createDefault(String name, CatchRateModifier modifier, List<? extends CaptureEffect> effects, float waterDragValue, ResourceLocation model2d, ResourceLocation model3d, float throwPower, boolean ancient) {
        ResourceLocation identifier = MiscUtilsKt.cobblemonResource(name);
        PokeBall pokeball = new PokeBall(identifier, modifier, effects, waterDragValue, model2d, model3d, throwPower, ancient);
        ((Map)defaults).put(identifier, pokeball);
        return pokeball;
    }

    static /* synthetic */ PokeBall createDefault$default(PokeBalls pokeBalls, String string, CatchRateModifier catchRateModifier, List list, float f, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, float f2, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            catchRateModifier = new MultiplierModifier(1.0f, (Function2<? super LivingEntity, ? super Pokemon, Boolean>)((Function2)createDefault.1.INSTANCE));
        }
        if ((n & 4) != 0) {
            list = CollectionsKt.emptyList();
        }
        if ((n & 8) != 0) {
            f = 0.8f;
        }
        if ((n & 0x10) != 0) {
            resourceLocation = MiscUtilsKt.cobblemonResource(string);
        }
        if ((n & 0x20) != 0) {
            resourceLocation2 = MiscUtilsKt.cobblemonResource(string + "_model");
        }
        if ((n & 0x40) != 0) {
            f2 = 1.25f;
        }
        if ((n & 0x80) != 0) {
            bl = false;
        }
        return pokeBalls.createDefault(string, catchRateModifier, list, f, resourceLocation, resourceLocation2, f2, bl);
    }

    private final PokeBall byName(String name) {
        ResourceLocation identifier = MiscUtilsKt.cobblemonResource(name);
        PokeBall pokeBall = custom.get(identifier);
        if (pokeBall == null) {
            PokeBall pokeBall2 = defaults.get(identifier);
            Intrinsics.checkNotNull((Object)pokeBall2);
            pokeBall = pokeBall2;
        }
        return pokeBall;
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        JsonDataRegistry.DefaultImpls.reload(this, manager);
    }

    static {
        Gson gson2 = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"GsonBuilder()\n        .d\u2026nting()\n        .create()");
        gson = gson2;
        TypeToken typeToken = TypeToken.get(PokeBall.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken, (String)"get(PokeBall::class.java)");
        PokeBalls.typeToken = typeToken;
        resourcePath = "pokeballs";
        defaults = new HashMap();
        custom = new HashMap();
        PokeBalls.createDefault$default(INSTANCE, "poke_ball", null, null, 0.0f, null, null, 0.0f, false, 254, null);
        PokeBalls.createDefault$default(INSTANCE, "slate_ball", null, null, 0.0f, null, null, 0.0f, false, 254, null);
        PokeBalls.createDefault$default(INSTANCE, "azure_ball", null, null, 0.0f, null, null, 0.0f, false, 254, null);
        PokeBalls.createDefault$default(INSTANCE, "verdant_ball", null, null, 0.0f, null, null, 0.0f, false, 254, null);
        PokeBalls.createDefault$default(INSTANCE, "roseate_ball", null, null, 0.0f, null, null, 0.0f, false, 254, null);
        PokeBalls.createDefault$default(INSTANCE, "citrine_ball", null, null, 0.0f, null, null, 0.0f, false, 254, null);
        PokeBalls.createDefault$default(INSTANCE, "great_ball", new MultiplierModifier(1.5f, null, 2, null), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "ultra_ball", new MultiplierModifier(2.0f, null, 2, null), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "master_ball", new GuaranteedModifier(), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "safari_ball", CatchRateModifiers.INSTANCE.getSAFARI(), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "fast_ball", new BaseStatModifier(Stats.SPEED, (Function1<? super Integer, Boolean>)((Function1)1.INSTANCE), 4.0f), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "level_ball", CatchRateModifiers.INSTANCE.getLEVEL(), null, 0.0f, null, null, 0.0f, false, 252, null);
        Object[] objectArray = new ElementalType[]{ElementalTypes.INSTANCE.getWATER()};
        PokeBalls.createDefault$default(INSTANCE, "lure_ball", CatchRateModifiers.INSTANCE.typeBoosting(2.0f, (ElementalType[])objectArray), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "heavy_ball", CatchRateModifiers.INSTANCE.getWEIGHT_BASED(), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "love_ball", CatchRateModifiers.INSTANCE.getLOVE(), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "friend_ball", null, CollectionsKt.listOf((Object)CaptureEffects.INSTANCE.friendshipSetter(150)), 0.0f, null, null, 0.0f, false, 250, null);
        PokeBalls.createDefault$default(INSTANCE, "moon_ball", CatchRateModifiers.INSTANCE.getMOON_PHASES(), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "sport_ball", new MultiplierModifier(1.5f, null, 2, null), null, 0.0f, null, null, 0.0f, false, 252, null);
        objectArray = new ElementalType[]{ElementalTypes.INSTANCE.getBUG(), ElementalTypes.INSTANCE.getWATER()};
        PokeBalls.createDefault$default(INSTANCE, "net_ball", CatchRateModifiers.INSTANCE.typeBoosting(3.0f, (ElementalType[])objectArray), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "dive_ball", CatchRateModifiers.INSTANCE.getSUBMERGED_IN_WATER(), null, 0.99f, null, null, 0.0f, false, 244, null);
        PokeBalls.createDefault$default(INSTANCE, "nest_ball", CatchRateModifiers.INSTANCE.getNEST(), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "repeat_ball", null, null, 0.0f, null, null, 0.0f, false, 254, null);
        PokeBalls.createDefault$default(INSTANCE, "timer_ball", CatchRateModifiers.INSTANCE.turnBased((Function1<? super Integer, Float>)((Function1)2.INSTANCE)), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "luxury_ball", null, CollectionsKt.listOf((Object)new FriendshipEarningBoostEffect(2.0f)), 0.0f, null, null, 0.0f, false, 250, null);
        PokeBalls.createDefault$default(INSTANCE, "premier_ball", null, null, 0.0f, null, null, 0.0f, false, 254, null);
        PokeBalls.createDefault$default(INSTANCE, "dusk_ball", CatchRateModifiers.INSTANCE.getLIGHT_LEVEL(), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "heal_ball", null, CollectionsKt.listOf((Object)CaptureEffects.INSTANCE.getFULL_RESTORE()), 0.0f, null, null, 0.0f, false, 250, null);
        PokeBalls.createDefault$default(INSTANCE, "quick_ball", CatchRateModifiers.INSTANCE.turnBased((Function1<? super Integer, Float>)((Function1)3.INSTANCE)), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "cherish_ball", null, null, 0.0f, null, null, 0.0f, false, 254, null);
        PokeBalls.createDefault$default(INSTANCE, "park_ball", CatchRateModifiers.INSTANCE.getPARK(), null, 0.0f, null, null, 0.0f, false, 252, null);
        objectArray = new Status[]{Statuses.INSTANCE.getSLEEP()};
        PokeBalls.createDefault$default(INSTANCE, "dream_ball", CatchRateModifiers.INSTANCE.statusBoosting(4.0f, (Status[])objectArray), null, 0.0f, null, null, 0.0f, false, 252, null);
        objectArray = new String[]{"ultra_beast"};
        PokeBalls.createDefault$default(INSTANCE, "beast_ball", new LabelModifier(5.0f, true, (String[])objectArray), null, 0.0f, null, null, 0.0f, false, 252, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_poke_ball", null, null, 0.0f, null, null, 0.0f, true, 126, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_citrine_ball", null, null, 0.0f, null, null, 0.0f, true, 126, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_verdant_ball", null, null, 0.0f, null, null, 0.0f, true, 126, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_azure_ball", null, null, 0.0f, null, null, 0.0f, true, 126, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_roseate_ball", null, null, 0.0f, null, null, 0.0f, true, 126, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_slate_ball", null, null, 0.0f, null, null, 0.0f, true, 126, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_ivory_ball", null, null, 0.0f, null, null, 0.0f, true, 126, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_great_ball", new MultiplierModifier(1.5f, null, 2, null), null, 0.0f, null, null, 0.0f, true, 124, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_ultra_ball", new MultiplierModifier(2.0f, null, 2, null), null, 0.0f, null, null, 0.0f, true, 124, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_heavy_ball", null, null, 0.0f, null, null, 0.75f, true, 62, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_leaden_ball", null, null, 0.0f, null, null, 0.75f, true, 62, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_gigaton_ball", null, null, 0.0f, null, null, 0.75f, true, 62, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_feather_ball", null, null, 0.0f, null, null, 2.5f, true, 62, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_wing_ball", null, null, 0.0f, null, null, 2.5f, true, 62, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_jet_ball", null, null, 0.0f, null, null, 2.5f, true, 62, null);
        PokeBalls.createDefault$default(INSTANCE, "ancient_origin_ball", new GuaranteedModifier(), null, 0.0f, null, null, 0.0f, true, 124, null);
        CobblemonEvents.FRIENDSHIP_UPDATED.subscribe(Priority.LOW, (Function1<FriendshipUpdatedEvent, Unit>)((Function1)4.INSTANCE));
    }
}

