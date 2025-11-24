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
import kotlin.Metadata;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u0000 \b2\u00020\u0001:\u0001\bJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0007\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/ai/SleepDepth;", "", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "", "canSleep", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Z", "shouldWake", "Companion", "common"})
public interface SleepDepth {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.SleepDepth$Companion.$$INSTANCE;

    public boolean canSleep(@NotNull PokemonEntity var1);

    public boolean shouldWake(@NotNull PokemonEntity var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR#\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00030\f8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\t\u001a\u0004\b\u0013\u0010\u000b\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/ai/SleepDepth$Companion;", "", "Lcom/cobblemon/mod/common/api/serialization/StringIdentifiedObjectAdapter;", "Lcom/cobblemon/mod/common/api/ai/SleepDepth;", "adapter", "Lcom/cobblemon/mod/common/api/serialization/StringIdentifiedObjectAdapter;", "getAdapter", "()Lcom/cobblemon/mod/common/api/serialization/StringIdentifiedObjectAdapter;", "comatose", "Lcom/cobblemon/mod/common/api/ai/SleepDepth;", "getComatose", "()Lcom/cobblemon/mod/common/api/ai/SleepDepth;", "", "", "depths", "Ljava/util/Map;", "getDepths", "()Ljava/util/Map;", "normal", "getNormal", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
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
            $$INSTANCE = new Companion();
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

                public boolean canSleep(@NotNull PokemonEntity pokemonEntity) {
                    Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
                    return pokemonEntity.m_9236_().m_45955_(TargetingConditions.m_148353_(), (LivingEntity)pokemonEntity, AABB.m_165882_((Vec3)pokemonEntity.m_20182_(), (double)16.0, (double)16.0, (double)16.0)).isEmpty();
                }

                public boolean shouldWake(@NotNull PokemonEntity pokemonEntity) {
                    boolean bl;
                    block3: {
                        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
                        List nearbyPlayers2 = pokemonEntity.m_9236_().m_45955_(TargetingConditions.m_148353_(), (LivingEntity)pokemonEntity, AABB.m_165882_((Vec3)pokemonEntity.m_20182_(), (double)16.0, (double)16.0, (double)16.0));
                        Intrinsics.checkNotNullExpressionValue((Object)nearbyPlayers2, (String)"nearbyPlayers");
                        Iterable $this$any$iv = nearbyPlayers2;
                        boolean $i$f$any = false;
                        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                            bl = false;
                        } else {
                            for (T element$iv : $this$any$iv) {
                                Player it = (Player)element$iv;
                                boolean bl2 = false;
                                if (!(!it.m_6144_())) continue;
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
            adapter = new StringIdentifiedObjectAdapter(adapter.1.INSTANCE);
        }
    }
}

