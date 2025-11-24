/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem.CobblemonEmptyHeldItemManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\bf\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\bJ'\u0010\r\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000bH&\u00a2\u0006\u0004\b\r\u0010\u000eJ'\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000bH&\u00a2\u0006\u0004\b\u000f\u0010\u000eJ\u0017\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0011\u0010\u0012J'\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0019\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0016J\u001f\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0017\u0010\b\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/helditem/HeldItemManager;", "", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "pokemon", "", "showdownId", "", "give", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Ljava/lang/String;)V", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "battleMessage", "handleEndInstruction", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "handleStartInstruction", "Lnet/minecraft/network/chat/Component;", "nameOf", "(Ljava/lang/String;)Lnet/minecraft/network/chat/Component;", "", "shouldConsumeItem", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Ljava/lang/String;)Z", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Ljava/lang/String;", "take", "Companion", "common"})
public interface HeldItemManager {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem.HeldItemManager$Companion.$$INSTANCE;

    @Nullable
    public String showdownId(@NotNull BattlePokemon var1);

    @NotNull
    public Component nameOf(@NotNull String var1);

    public void handleStartInstruction(@NotNull BattlePokemon var1, @NotNull PokemonBattle var2, @NotNull BattleMessage var3);

    public void handleEndInstruction(@NotNull BattlePokemon var1, @NotNull PokemonBattle var2, @NotNull BattleMessage var3);

    public void give(@NotNull BattlePokemon var1, @NotNull String var2);

    public void take(@NotNull BattlePokemon var1, @NotNull String var2);

    public boolean shouldConsumeItem(@NotNull BattlePokemon var1, @NotNull PokemonBattle var2, @NotNull String var3);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/helditem/HeldItemManager$Companion;", "", "Lcom/cobblemon/mod/common/api/pokemon/helditem/HeldItemManager;", "EMPTY", "Lcom/cobblemon/mod/common/api/pokemon/helditem/HeldItemManager;", "getEMPTY", "()Lcom/cobblemon/mod/common/api/pokemon/helditem/HeldItemManager;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final HeldItemManager EMPTY;

        private Companion() {
        }

        @NotNull
        public final HeldItemManager getEMPTY() {
            return EMPTY;
        }

        static {
            $$INSTANCE = new Companion();
            EMPTY = CobblemonEmptyHeldItemManager.INSTANCE;
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static boolean shouldConsumeItem(@NotNull HeldItemManager $this, @NotNull BattlePokemon pokemon, @NotNull PokemonBattle battle2, @NotNull String showdownId) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            Intrinsics.checkNotNullParameter((Object)showdownId, (String)"showdownId");
            return false;
        }
    }
}

