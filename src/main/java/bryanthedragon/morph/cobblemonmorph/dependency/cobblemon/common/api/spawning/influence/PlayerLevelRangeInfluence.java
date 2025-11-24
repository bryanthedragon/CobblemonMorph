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
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PokemonSpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStoreManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010&\u001a\u00020%\u0012\u0006\u0010!\u001a\u00020 \u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0018\u001a\u00020\n\u00a2\u0006\u0004\b'\u0010(J\u001b\u0010\u0005\u001a\u00020\u00042\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\r\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\b\u0010\tR\"\u0010\u000b\u001a\u00020\n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\tR\"\u0010\u0014\u001a\u00020\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0014\u0010\u0012\u001a\u0004\b\u0015\u0010\t\"\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\f\u001a\u0004\b\u0019\u0010\u000eR\u001f\u0010\u001c\u001a\n \u001b*\u0004\u0018\u00010\u001a0\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010!\u001a\u00020 8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\u00a8\u0006)"}, d2={"Lcom/cobblemon/mod/common/api/spawning/influence/PlayerLevelRangeInfluence;", "Lcom/cobblemon/mod/common/api/spawning/influence/SpawningInfluence;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnAction;", "action", "", "affectAction", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnAction;)V", "Lkotlin/ranges/IntRange;", "getPlayerLevelRange", "()Lkotlin/ranges/IntRange;", "", "lastCalculatedTime", "J", "getLastCalculatedTime", "()J", "setLastCalculatedTime", "(J)V", "noPokemonRange", "Lkotlin/ranges/IntRange;", "getNoPokemonRange", "previousRange", "getPreviousRange", "setPreviousRange", "(Lkotlin/ranges/IntRange;)V", "recalculationMillis", "getRecalculationMillis", "Ljava/util/UUID;", "kotlin.jvm.PlatformType", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "", "variation", "I", "getVariation", "()I", "Lnet/minecraft/server/level/ServerPlayer;", "player", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;ILkotlin/ranges/IntRange;J)V", "common"})
@SourceDebugExtension(value={"SMAP\nPlayerLevelRangeInfluence.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PlayerLevelRangeInfluence.kt\ncom/cobblemon/mod/common/api/spawning/influence/PlayerLevelRangeInfluence\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,74:1\n1#2:75\n*E\n"})
public class PlayerLevelRangeInfluence
implements SpawningInfluence {
    private final int variation;
    @NotNull
    private final IntRange noPokemonRange;
    private final long recalculationMillis;
    private final UUID uuid;
    private long lastCalculatedTime;
    @NotNull
    private IntRange previousRange;

    public PlayerLevelRangeInfluence(@NotNull ServerPlayer player, int variation, @NotNull IntRange noPokemonRange, long recalculationMillis) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)noPokemonRange, (String)"noPokemonRange");
        this.variation = variation;
        this.noPokemonRange = noPokemonRange;
        this.recalculationMillis = recalculationMillis;
        this.uuid = player.m_20148_();
        this.previousRange = this.noPokemonRange;
    }

    public /* synthetic */ PlayerLevelRangeInfluence(ServerPlayer serverPlayer, int n, IntRange intRange, long l, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 4) != 0) {
            intRange = new IntRange(1, Cobblemon.INSTANCE.getConfig().getMinimumLevelRangeMax());
        }
        if ((n2 & 8) != 0) {
            l = 5000L;
        }
        this(serverPlayer, n, intRange, l);
    }

    public final int getVariation() {
        return this.variation;
    }

    @NotNull
    public final IntRange getNoPokemonRange() {
        return this.noPokemonRange;
    }

    public final long getRecalculationMillis() {
        return this.recalculationMillis;
    }

    public final UUID getUuid() {
        return this.uuid;
    }

    public final long getLastCalculatedTime() {
        return this.lastCalculatedTime;
    }

    public final void setLastCalculatedTime(long l) {
        this.lastCalculatedTime = l;
    }

    @NotNull
    public final IntRange getPreviousRange() {
        return this.previousRange;
    }

    public final void setPreviousRange(@NotNull IntRange intRange) {
        Intrinsics.checkNotNullParameter((Object)intRange, (String)"<set-?>");
        this.previousRange = intRange;
    }

    @NotNull
    public final IntRange getPlayerLevelRange() {
        IntRange intRange;
        if (System.currentTimeMillis() - this.lastCalculatedTime > this.recalculationMillis) {
            IntRange intRange2;
            this.lastCalculatedTime = System.currentTimeMillis();
            PokemonStoreManager pokemonStoreManager = Cobblemon.INSTANCE.getStorage();
            UUID uUID = this.uuid;
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"uuid");
            PlayerPartyStore party = pokemonStoreManager.getParty(uUID);
            PlayerLevelRangeInfluence playerLevelRangeInfluence = this;
            if (CollectionsKt.any((Iterable)party)) {
                Iterable iterable = party;
                PlayerLevelRangeInfluence playerLevelRangeInfluence2 = playerLevelRangeInfluence;
                Iterator iterator = iterable.iterator();
                if (!iterator.hasNext()) {
                    throw new NoSuchElementException();
                }
                Pokemon it = (Pokemon)iterator.next();
                boolean bl = false;
                int n = it.getLevel();
                while (iterator.hasNext()) {
                    Pokemon it2 = (Pokemon)iterator.next();
                    $i$a$-maxOf-PlayerLevelRangeInfluence$getPlayerLevelRange$maximumLevel$1 = false;
                    int n2 = it2.getLevel();
                    if (n >= n2) continue;
                    n = n2;
                }
                int n3 = n;
                playerLevelRangeInfluence = playerLevelRangeInfluence2;
                int maximumLevel = n3;
                intRange2 = new IntRange(Math.max(maximumLevel - this.variation, 1), Math.min(Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel(), Math.max(maximumLevel + this.variation, Cobblemon.INSTANCE.getConfig().getMinimumLevelRangeMax())));
            } else {
                intRange2 = this.noPokemonRange;
            }
            playerLevelRangeInfluence.previousRange = intRange2;
            intRange = this.previousRange;
        } else {
            intRange = this.previousRange;
        }
        return intRange;
    }

    @Override
    public void affectAction(@NotNull SpawnAction<?> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        if (action2 instanceof PokemonSpawnAction && ((PokemonSpawnAction)action2).getProps().getLevel() == null) {
            IntRange playerLevelRange = this.getPlayerLevelRange();
            IntRange derivedLevelRange = ((PokemonSpawnAction)action2).getDetail().getDerivedLevelRange();
            IntRange spawnLevelRange = SimpleMathExtensionsKt.intersection(playerLevelRange, derivedLevelRange);
            int pokemonRangeWidth = derivedLevelRange.getLast() - derivedLevelRange.getFirst();
            if (spawnLevelRange.isEmpty()) {
                spawnLevelRange = derivedLevelRange.getFirst() > playerLevelRange.getLast() ? new IntRange(derivedLevelRange.getFirst(), (int)((float)derivedLevelRange.getFirst() + (float)pokemonRangeWidth / 4.0f)) : new IntRange((int)((float)derivedLevelRange.getFirst() + (float)(3 * pokemonRangeWidth) / 4.0f), derivedLevelRange.getLast());
            }
            ((PokemonSpawnAction)action2).getProps().setLevel(RangesKt.random((IntRange)spawnLevelRange, (Random)((Random)Random.Default)));
        }
    }

    @Override
    public boolean isExpired() {
        return SpawningInfluence.DefaultImpls.isExpired(this);
    }

    @Override
    public boolean affectSpawnable(@NotNull SpawnDetail detail, @NotNull SpawningContext ctx) {
        return SpawningInfluence.DefaultImpls.affectSpawnable(this, detail, ctx);
    }

    @Override
    public float affectWeight(@NotNull SpawnDetail detail, @NotNull SpawningContext ctx, float weight) {
        return SpawningInfluence.DefaultImpls.affectWeight(this, detail, ctx, weight);
    }

    @Override
    public void affectSpawn(@NotNull Entity entity2) {
        SpawningInfluence.DefaultImpls.affectSpawn(this, entity2);
    }

    @Override
    public float affectBucketWeight(@NotNull SpawnBucket bucket, float weight) {
        return SpawningInfluence.DefaultImpls.affectBucketWeight(this, bucket, weight);
    }

    @Override
    public boolean isAllowedPosition(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull SpawningContextCalculator<?, ?> contextCalculator) {
        return SpawningInfluence.DefaultImpls.isAllowedPosition(this, world, pos, contextCalculator);
    }
}

