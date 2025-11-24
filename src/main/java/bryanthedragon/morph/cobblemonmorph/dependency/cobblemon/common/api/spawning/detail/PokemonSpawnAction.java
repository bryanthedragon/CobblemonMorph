/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PokemonSpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PokemonSpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PossibleHeldItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SingleEntitySpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature.SeasonFeatureHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CollectionUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B!\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u001a\u0010\u0006\u001a\u00020\u00058\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR\"\u0010\u000b\u001a\u00020\n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/api/spawning/detail/PokemonSpawnAction;", "Lcom/cobblemon/mod/common/api/spawning/detail/SingleEntitySpawnAction;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "createEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/api/spawning/detail/PokemonSpawnDetail;", "detail", "Lcom/cobblemon/mod/common/api/spawning/detail/PokemonSpawnDetail;", "getDetail", "()Lcom/cobblemon/mod/common/api/spawning/detail/PokemonSpawnDetail;", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "props", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getProps", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "setProps", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;)V", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "<init>", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;Lcom/cobblemon/mod/common/api/spawning/detail/PokemonSpawnDetail;Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonSpawnAction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonSpawnAction.kt\ncom/cobblemon/mod/common/api/spawning/detail/PokemonSpawnAction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,58:1\n1#2:59\n*E\n"})
public final class PokemonSpawnAction
extends SingleEntitySpawnAction<PokemonEntity> {
    @NotNull
    private final PokemonSpawnDetail detail;
    @NotNull
    private PokemonProperties props;

    public PokemonSpawnAction(@NotNull SpawningContext ctx, @NotNull PokemonSpawnDetail detail, @NotNull PokemonProperties props) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
        Intrinsics.checkNotNullParameter((Object)props, (String)"props");
        super(ctx, detail);
        this.detail = detail;
        this.props = props;
    }

    public /* synthetic */ PokemonSpawnAction(SpawningContext spawningContext, PokemonSpawnDetail pokemonSpawnDetail, PokemonProperties pokemonProperties, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            pokemonProperties = pokemonSpawnDetail.getPokemon().copy();
        }
        this(spawningContext, pokemonSpawnDetail, pokemonProperties);
    }

    @Override
    @NotNull
    public PokemonSpawnDetail getDetail() {
        return this.detail;
    }

    @NotNull
    public final PokemonProperties getProps() {
        return this.props;
    }

    public final void setProps(@NotNull PokemonProperties pokemonProperties) {
        Intrinsics.checkNotNullParameter((Object)pokemonProperties, (String)"<set-?>");
        this.props = pokemonProperties;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public PokemonEntity createEntity() {
        PossibleHeldItem possibleHeldItem;
        List heldItems;
        List list;
        block10: {
            block9: {
                List list2;
                if (this.props.getSpecies() == null) {
                    Cobblemon.INSTANCE.getLOGGER().error("PokemonSpawnAction run with null species - Spawn detail: " + this.getDetail().getId());
                }
                if (this.props.getLevel() == null) {
                    this.props.setLevel(RangesKt.random((IntRange)this.getDetail().getDerivedLevelRange(), (Random)((Random)Random.Default)));
                }
                if ((list = this.getDetail().getHeldItems()) == null) break block9;
                List it = list2 = list;
                boolean bl = false;
                list = !((Collection)it).isEmpty() ? list2 : null;
                if (list != null && (list = CollectionsKt.toMutableList((Collection)list)) != null) break block10;
            }
            list = new ArrayList();
        }
        if (!((Collection)(heldItems = list)).isEmpty()) {
            double d;
            Iterable iterable = heldItems;
            double d2 = 1.0;
            double d3 = 0.0;
            for (Object t : iterable) {
                void it;
                PossibleHeldItem possibleHeldItem2 = (PossibleHeldItem)t;
                d = d3;
                boolean bl = false;
                double d4 = it.getPercentage() / (double)100;
                d3 = d + d4;
            }
            d = d3;
            double until100 = d2 - d;
            possibleHeldItem = until100 > 0.0 && this.getCtx().getWorld().f_46441_.m_188500_() < until100 ? null : (PossibleHeldItem)CollectionUtilsKt.weightedSelection(heldItems, createEntity.heldItem.1.INSTANCE);
        } else {
            possibleHeldItem = null;
        }
        PossibleHeldItem possibleHeldItem3 = possibleHeldItem;
        ItemStack heldItem2 = possibleHeldItem3 != null ? possibleHeldItem3.createStack(this.getCtx()) : null;
        PokemonEntity entity2 = this.props.createEntity((Level)this.getCtx().getWorld());
        SeasonFeatureHandler.INSTANCE.updateSeason(entity2.getPokemon(), Cobblemon.INSTANCE.getSeasonResolver().invoke((LevelAccessor)this.getCtx().getWorld(), this.getCtx().getPosition()));
        if (heldItem2 != null) {
            Pokemon.swapHeldItem$default(entity2.getPokemon(), heldItem2, false, 2, null);
        }
        entity2.setDrops(this.getDetail().getDrops());
        return entity2;
    }
}

