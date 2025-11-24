/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.friendship;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\b\u00e6\u0080\u0001\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/friendship/FriendshipMutationCalculator;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "calculate", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)I", "Companion", "common"})
public interface FriendshipMutationCalculator {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.friendship.FriendshipMutationCalculator$Companion.$$INSTANCE;

    public int calculate(@NotNull Pokemon var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/friendship/FriendshipMutationCalculator$Companion;", "", "Lcom/cobblemon/mod/common/api/pokemon/friendship/FriendshipMutationCalculator;", "SWORD_AND_SHIELD_LEVEL_UP", "Lcom/cobblemon/mod/common/api/pokemon/friendship/FriendshipMutationCalculator;", "getSWORD_AND_SHIELD_LEVEL_UP", "()Lcom/cobblemon/mod/common/api/pokemon/friendship/FriendshipMutationCalculator;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final FriendshipMutationCalculator SWORD_AND_SHIELD_LEVEL_UP;

        private Companion() {
        }

        @NotNull
        public final FriendshipMutationCalculator getSWORD_AND_SHIELD_LEVEL_UP() {
            return SWORD_AND_SHIELD_LEVEL_UP;
        }

        private static final int SWORD_AND_SHIELD_LEVEL_UP$lambda$0(Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            return pokemon.getFriendship() <= 99 ? 3 : (pokemon.getFriendship() <= 199 ? 2 : 0);
        }

        static {
            $$INSTANCE = new Companion();
            SWORD_AND_SHIELD_LEVEL_UP = Companion::SWORD_AND_SHIELD_LEVEL_UP$lambda$0;
        }
    }
}

