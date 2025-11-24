/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NotImplementedError
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokeball.PokeBallModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokeBallModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.VaryingModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0011\b\u00c6\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b'\u0010\fJ#\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00030\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0018\u001a\u00020\u00178\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0018\u0010\u001aR \u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001b\u0010\u000f\u001a\u0004\b\u001c\u0010\u0011R \u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u000f\u001a\u0004\b\u001e\u0010\u0011R\u001a\u0010\u001f\u001a\u00020\u00048\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020\u00048\u0016X\u0096D\u00a2\u0006\f\n\u0004\b#\u0010 \u001a\u0004\b$\u0010\"R \u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b%\u0010\u000f\u001a\u0004\b&\u0010\u0011\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/PokeBallModelRepository;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository;", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokeball/PokeBallModel;", "", "json", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "loadJsonPoser", "(Ljava/lang/String;)Lkotlin/jvm/functions/Function1;", "", "registerInBuiltPosers", "()V", "", "animationDirectories", "Ljava/util/List;", "getAnimationDirectories", "()Ljava/util/List;", "Lnet/minecraft/resources/ResourceLocation;", "fallback", "Lnet/minecraft/resources/ResourceLocation;", "getFallback", "()Lnet/minecraft/resources/ResourceLocation;", "", "isForLivingEntityRenderer", "Z", "()Z", "modelDirectories", "getModelDirectories", "poserDirectories", "getPoserDirectories", "title", "Ljava/lang/String;", "getTitle", "()Ljava/lang/String;", "type", "getType", "variationDirectories", "getVariationDirectories", "<init>", "common"})
public final class PokeBallModelRepository
extends VaryingModelRepository<EmptyPokeBallEntity, PokeBallModel> {
    @NotNull
    public static final PokeBallModelRepository INSTANCE = new PokeBallModelRepository();
    @NotNull
    private static final String title = "Pok\u00e9 Ball";
    @NotNull
    private static final String type = "poke_balls";
    @NotNull
    private static final List<String> variationDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/variations"));
    @NotNull
    private static final List<String> poserDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/posers"));
    @NotNull
    private static final List<String> modelDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/models"));
    @NotNull
    private static final List<String> animationDirectories = CollectionsKt.listOf((Object)("bedrock/" + INSTANCE.getType() + "/animations"));
    private static final boolean isForLivingEntityRenderer;
    @NotNull
    private static final ResourceLocation fallback;

    private PokeBallModelRepository() {
    }

    @Override
    @NotNull
    public String getTitle() {
        return title;
    }

    @Override
    @NotNull
    public String getType() {
        return type;
    }

    @Override
    @NotNull
    public List<String> getVariationDirectories() {
        return variationDirectories;
    }

    @Override
    @NotNull
    public List<String> getPoserDirectories() {
        return poserDirectories;
    }

    @Override
    @NotNull
    public List<String> getModelDirectories() {
        return modelDirectories;
    }

    @Override
    @NotNull
    public List<String> getAnimationDirectories() {
        return animationDirectories;
    }

    @Override
    public boolean isForLivingEntityRenderer() {
        return isForLivingEntityRenderer;
    }

    @Override
    @NotNull
    public ResourceLocation getFallback() {
        return fallback;
    }

    @Override
    @NotNull
    public Function1<Bone, PokeBallModel> loadJsonPoser(@NotNull String json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        String string = "Not yet implemented";
        throw new NotImplementedError("An operation is not implemented: " + string);
    }

    @Override
    public void registerInBuiltPosers() {
        this.inbuilt("azure_ball", registerInBuiltPosers.1.INSTANCE);
        this.inbuilt("beast_ball", registerInBuiltPosers.2.INSTANCE);
        this.inbuilt("cherish_ball", registerInBuiltPosers.3.INSTANCE);
        this.inbuilt("citrine_ball", registerInBuiltPosers.4.INSTANCE);
        this.inbuilt("dive_ball", registerInBuiltPosers.5.INSTANCE);
        this.inbuilt("dream_ball", registerInBuiltPosers.6.INSTANCE);
        this.inbuilt("dusk_ball", registerInBuiltPosers.7.INSTANCE);
        this.inbuilt("fast_ball", registerInBuiltPosers.8.INSTANCE);
        this.inbuilt("friend_ball", registerInBuiltPosers.9.INSTANCE);
        this.inbuilt("great_ball", registerInBuiltPosers.10.INSTANCE);
        this.inbuilt("heal_ball", registerInBuiltPosers.11.INSTANCE);
        this.inbuilt("heavy_ball", registerInBuiltPosers.12.INSTANCE);
        this.inbuilt("level_ball", registerInBuiltPosers.13.INSTANCE);
        this.inbuilt("love_ball", registerInBuiltPosers.14.INSTANCE);
        this.inbuilt("lure_ball", registerInBuiltPosers.15.INSTANCE);
        this.inbuilt("luxury_ball", registerInBuiltPosers.16.INSTANCE);
        this.inbuilt("master_ball", registerInBuiltPosers.17.INSTANCE);
        this.inbuilt("moon_ball", registerInBuiltPosers.18.INSTANCE);
        this.inbuilt("nest_ball", registerInBuiltPosers.19.INSTANCE);
        this.inbuilt("net_ball", registerInBuiltPosers.20.INSTANCE);
        this.inbuilt("park_ball", registerInBuiltPosers.21.INSTANCE);
        this.inbuilt("poke_ball", registerInBuiltPosers.22.INSTANCE);
        this.inbuilt("premier_ball", registerInBuiltPosers.23.INSTANCE);
        this.inbuilt("quick_ball", registerInBuiltPosers.24.INSTANCE);
        this.inbuilt("repeat_ball", registerInBuiltPosers.25.INSTANCE);
        this.inbuilt("roseate_ball", registerInBuiltPosers.26.INSTANCE);
        this.inbuilt("safari_ball", registerInBuiltPosers.27.INSTANCE);
        this.inbuilt("slate_ball", registerInBuiltPosers.28.INSTANCE);
        this.inbuilt("sport_ball", registerInBuiltPosers.29.INSTANCE);
        this.inbuilt("strange_ball", registerInBuiltPosers.30.INSTANCE);
        this.inbuilt("timer_ball", registerInBuiltPosers.31.INSTANCE);
        this.inbuilt("ultra_ball", registerInBuiltPosers.32.INSTANCE);
        this.inbuilt("verdant_ball", registerInBuiltPosers.33.INSTANCE);
        this.inbuilt("ancient_poke_ball", registerInBuiltPosers.34.INSTANCE);
        this.inbuilt("ancient_citrine_ball", registerInBuiltPosers.35.INSTANCE);
        this.inbuilt("ancient_verdant_ball", registerInBuiltPosers.36.INSTANCE);
        this.inbuilt("ancient_azure_ball", registerInBuiltPosers.37.INSTANCE);
        this.inbuilt("ancient_roseate_ball", registerInBuiltPosers.38.INSTANCE);
        this.inbuilt("ancient_slate_ball", registerInBuiltPosers.39.INSTANCE);
        this.inbuilt("ancient_ivory_ball", registerInBuiltPosers.40.INSTANCE);
        this.inbuilt("ancient_great_ball", registerInBuiltPosers.41.INSTANCE);
        this.inbuilt("ancient_ultra_ball", registerInBuiltPosers.42.INSTANCE);
        this.inbuilt("ancient_feather_ball", registerInBuiltPosers.43.INSTANCE);
        this.inbuilt("ancient_wing_ball", registerInBuiltPosers.44.INSTANCE);
        this.inbuilt("ancient_jet_ball", registerInBuiltPosers.45.INSTANCE);
        this.inbuilt("ancient_heavy_ball", registerInBuiltPosers.46.INSTANCE);
        this.inbuilt("ancient_leaden_ball", registerInBuiltPosers.47.INSTANCE);
        this.inbuilt("ancient_gigaton_ball", registerInBuiltPosers.48.INSTANCE);
    }

    static {
        fallback = PokeBalls.INSTANCE.getPOKE_BALL().getName();
    }
}

