/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.Despawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.BestSpawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.CobblemonWorldSpawnerManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnDetailPresets;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.AreaSpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.BasicSpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.GroundedSpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SubmergedSpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SurfaceSpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.GroundedSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.LavafloorSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SeafloorSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SubmergedSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SurfaceSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.GroundedSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.LavafloorSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SeafloorSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SubmergedSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SurfaceSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PokemonSpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.BasicSpawnDetailPreset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.BestSpawnerConfig;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.PokemonSpawnDetailPreset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.CobblemonAgingDespawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001b\u0010\u0004J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0004R\"\u0010\u0007\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR(\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001d\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/spawning/BestSpawner;", "", "", "loadConfig", "()V", "onServerStarted", "Lcom/cobblemon/mod/common/api/spawning/preset/BestSpawnerConfig;", "config", "Lcom/cobblemon/mod/common/api/spawning/preset/BestSpawnerConfig;", "getConfig", "()Lcom/cobblemon/mod/common/api/spawning/preset/BestSpawnerConfig;", "setConfig", "(Lcom/cobblemon/mod/common/api/spawning/preset/BestSpawnerConfig;)V", "Lcom/cobblemon/mod/common/api/entity/Despawner;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "defaultPokemonDespawner", "Lcom/cobblemon/mod/common/api/entity/Despawner;", "getDefaultPokemonDespawner", "()Lcom/cobblemon/mod/common/api/entity/Despawner;", "setDefaultPokemonDespawner", "(Lcom/cobblemon/mod/common/api/entity/Despawner;)V", "", "Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;", "spawnerManagers", "Ljava/util/List;", "getSpawnerManagers", "()Ljava/util/List;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nBestSpawner.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BestSpawner.kt\ncom/cobblemon/mod/common/api/spawning/BestSpawner\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,122:1\n1855#2,2:123\n*S KotlinDebug\n*F\n+ 1 BestSpawner.kt\ncom/cobblemon/mod/common/api/spawning/BestSpawner\n*L\n120#1:123,2\n*E\n"})
public final class BestSpawner {
    @NotNull
    public static final BestSpawner INSTANCE = new BestSpawner();
    @NotNull
    private static BestSpawnerConfig config = new BestSpawnerConfig();
    @NotNull
    private static final List<SpawnerManager> spawnerManagers;
    @NotNull
    private static Despawner<PokemonEntity> defaultPokemonDespawner;

    private BestSpawner() {
    }

    @NotNull
    public final BestSpawnerConfig getConfig() {
        return config;
    }

    public final void setConfig(@NotNull BestSpawnerConfig bestSpawnerConfig) {
        Intrinsics.checkNotNullParameter((Object)bestSpawnerConfig, (String)"<set-?>");
        config = bestSpawnerConfig;
    }

    @NotNull
    public final List<SpawnerManager> getSpawnerManagers() {
        return spawnerManagers;
    }

    @NotNull
    public final Despawner<PokemonEntity> getDefaultPokemonDespawner() {
        return defaultPokemonDespawner;
    }

    public final void setDefaultPokemonDespawner(@NotNull Despawner<PokemonEntity> despawner) {
        Intrinsics.checkNotNullParameter(despawner, (String)"<set-?>");
        defaultPokemonDespawner = despawner;
    }

    public final void loadConfig() {
        Cobblemon.INSTANCE.getLOGGER().info("Starting the Best Spawner...");
        SpawningCondition.Companion.register("basic", BasicSpawningCondition.class);
        SpawningCondition.Companion.register("area", AreaSpawningCondition.class);
        SpawningCondition.Companion.register("submerged", SubmergedSpawningCondition.class);
        SpawningCondition.Companion.register("grounded", GroundedSpawningCondition.class);
        SpawningCondition.Companion.register("surface", SurfaceSpawningCondition.class);
        Cobblemon.INSTANCE.getLOGGER().info("Loaded " + SpawningCondition.Companion.getConditionTypes().size() + " spawning condition types.");
        SpawningContextCalculator.Companion.register$default(SpawningContextCalculator.Companion, GroundedSpawningContextCalculator.INSTANCE, null, 2, null);
        SpawningContextCalculator.Companion.register$default(SpawningContextCalculator.Companion, SeafloorSpawningContextCalculator.INSTANCE, null, 2, null);
        SpawningContextCalculator.Companion.register$default(SpawningContextCalculator.Companion, LavafloorSpawningContextCalculator.INSTANCE, null, 2, null);
        SpawningContextCalculator.Companion.register$default(SpawningContextCalculator.Companion, SubmergedSpawningContextCalculator.INSTANCE, null, 2, null);
        SpawningContextCalculator.Companion.register$default(SpawningContextCalculator.Companion, SurfaceSpawningContextCalculator.INSTANCE, null, 2, null);
        SpawningContext.Companion.register("grounded", GroundedSpawningContext.class, "grounded");
        SpawningContext.Companion.register("seafloor", SeafloorSpawningContext.class, "grounded");
        SpawningContext.Companion.register("lavafloor", LavafloorSpawningContext.class, "grounded");
        SpawningContext.Companion.register("submerged", SubmergedSpawningContext.class, "submerged");
        SpawningContext.Companion.register("surface", SurfaceSpawningContext.class, "surface");
        Cobblemon.INSTANCE.getLOGGER().info("Loaded " + SpawningContext.Companion.getContexts().size() + " spawning context types.");
        SpawnDetail.Companion.registerSpawnType(PokemonSpawnDetail.Companion.getTYPE(), PokemonSpawnDetail.class);
        Cobblemon.INSTANCE.getLOGGER().info("Loaded " + SpawnDetail.Companion.getSpawnDetailTypes().size() + " spawn detail types.");
        config = BestSpawnerConfig.Companion.load();
        SpawnDetailPresets.INSTANCE.registerPresetType("basic", BasicSpawnDetailPreset.class);
        SpawnDetailPresets.INSTANCE.registerPresetType("pokemon", PokemonSpawnDetailPreset.class);
    }

    public final void onServerStarted() {
        Iterable $this$forEach$iv = spawnerManagers;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SpawnerManager p0 = (SpawnerManager)element$iv;
            boolean bl = false;
            p0.onServerStarted();
        }
    }

    static {
        Object[] objectArray = new SpawnerManager[]{CobblemonWorldSpawnerManager.INSTANCE};
        spawnerManagers = CollectionsKt.mutableListOf((Object[])objectArray);
        defaultPokemonDespawner = new CobblemonAgingDespawner(0.0f, 0.0f, 0, 0, defaultPokemonDespawner.1.INSTANCE, 15, null);
    }
}

