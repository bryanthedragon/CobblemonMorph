/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.SleepDepth;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.StringIdentifiedObjectAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.NotNull;

public interface SleepDepth {
    @NotNull
    @SuppressWarnings("static-access")
    public static final SleepDepth.Companion Companion = SleepDepth.Companion.INSTANCE;

    public boolean canSleep(@NotNull PokemonEntity var1);

    public boolean shouldWake(@NotNull PokemonEntity var1);

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final class Companion {
        public static final Companion INSTANCE = new Companion();
        private static final SleepDepth comatose;
        @NotNull
        private static final SleepDepth normal;
        @NotNull
        private static final Map<String, SleepDepth> depths;
        @NotNull
        private static final StringIdentifiedObjectAdapter<SleepDepth> adapter;

        private Companion() {
        }

        @NotNull
        public final SleepDepth getComatose() {
            return comatose;
        }

        @NotNull
        public final SleepDepth getNormal() {
            return normal;
        }

        @NotNull
        public final Map<String, SleepDepth> getDepths() {
            return depths;
        }

        @NotNull
        public final StringIdentifiedObjectAdapter<SleepDepth> getAdapter() {
            return adapter;
        }

        static {
            comatose = new SleepDepth(){
                public boolean canSleep(@NotNull PokemonEntity pokemonEntity) {
                    Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
                    return true;
                }

                public boolean shouldWake(@NotNull PokemonEntity pokemonEntity) {
                    Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
                    return true;
                }
            };
            normal = new SleepDepth(){
                @SuppressWarnings("null")
                public boolean canSleep(@NotNull PokemonEntity pokemonEntity) {
                    Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
                    return pokemonEntity.m_9236_().getNearbyPlayers(TargetingConditions.forNonCombat(), (LivingEntity)pokemonEntity, AABB.ofSize((Vec3)pokemonEntity.position(), 16.0, 16.0, 16.0)).isEmpty();
                }

                @SuppressWarnings({ "unused", "null" })
                public boolean shouldWake(@NotNull PokemonEntity pokemonEntity) {
                    boolean bl;
                    block3: {
                        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
                        List nearbyPlayers2 = pokemonEntity.m_9236_().getNearbyPlayers(TargetingConditions.forNonCombat(), (LivingEntity)pokemonEntity, AABB.ofSize((Vec3)pokemonEntity.position(), 16.0, 16.0, 16.0));
                        Intrinsics.checkNotNullExpressionValue((Object)nearbyPlayers2, (String)"nearbyPlayers");
                        Iterable $this$any$iv = nearbyPlayers2;
                        boolean $i$f$any = false;
                        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                            bl = false;
                        } else {
                            for (Object element$iv : $this$any$iv) {
                                Player it = (Player)element$iv;
                                boolean bl2 = false;
                                if (!it.isSpectator()) continue;
                                bl = true;
                                break block3;
                            }
                            bl = false;
                        }
                    }
                    return bl;
                }
            };
            Pair[] pairArray = new Pair[]{TuplesKt.to((Object)"comatose", (Object)comatose), TuplesKt.to((Object)"normal", (Object)normal)};
            depths = MapsKt.mutableMapOf((Pair[])pairArray);
            adapter = new StringIdentifiedObjectAdapter<SleepDepth>(null);
        }
    }
}
