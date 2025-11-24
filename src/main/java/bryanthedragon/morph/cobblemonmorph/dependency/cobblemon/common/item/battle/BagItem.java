/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\b\bf\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019J7\u0010\r\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\bH&\u00a2\u0006\u0004\b\u000f\u0010\u0010J)\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012H&\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\u00128&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/item/battle/BagItem;", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "target", "Lnet/minecraft/world/item/ItemStack;", "stack", "", "canStillUse", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lnet/minecraft/world/item/ItemStack;)Z", "canUse", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Z", "battlePokemon", "", "data", "getShowdownInput", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Ljava/lang/String;)Ljava/lang/String;", "getItemName", "()Ljava/lang/String;", "itemName", "Companion", "common"})
public interface BagItem {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem$Companion.$$INSTANCE;

    @NotNull
    public String getItemName();

    public boolean canUse(@NotNull PokemonBattle var1, @NotNull BattlePokemon var2);

    @NotNull
    public String getShowdownInput(@NotNull BattleActor var1, @NotNull BattlePokemon var2, @Nullable String var3);

    public boolean canStillUse(@NotNull ServerPlayer var1, @NotNull PokemonBattle var2, @NotNull BattleActor var3, @NotNull BattlePokemon var4, @NotNull ItemStack var5);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/item/battle/BagItem$Companion;", "", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "EMPTY", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "getEMPTY", "()Lcom/cobblemon/mod/common/item/battle/BagItem;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final BagItem EMPTY;

        private Companion() {
        }

        @NotNull
        public final BagItem getEMPTY() {
            return EMPTY;
        }

        static {
            $$INSTANCE = new Companion();
            EMPTY = new BagItem(){
                @NotNull
                private final String itemName;
                {
                    this.itemName = "name";
                }

                @NotNull
                public String getItemName() {
                    return this.itemName;
                }

                public boolean canUse(@NotNull PokemonBattle battle2, @NotNull BattlePokemon target) {
                    Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
                    Intrinsics.checkNotNullParameter((Object)target, (String)"target");
                    return true;
                }

                @NotNull
                public String getShowdownInput(@NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @Nullable String data) {
                    Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
                    Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
                    return "none";
                }

                public boolean canStillUse(@NotNull ServerPlayer player, @NotNull PokemonBattle battle2, @NotNull BattleActor actor, @NotNull BattlePokemon target, @NotNull ItemStack stack) {
                    return DefaultImpls.canStillUse(this, player, battle2, actor, target, stack);
                }
            };
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static boolean canStillUse(@NotNull BagItem $this, @NotNull ServerPlayer player, @NotNull PokemonBattle battle2, @NotNull BattleActor actor, @NotNull BattlePokemon target, @NotNull ItemStack stack) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
            Intrinsics.checkNotNullParameter((Object)target, (String)"target");
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            Iterable iterable = player.m_6167_();
            Intrinsics.checkNotNullExpressionValue((Object)iterable, (String)"player.handItems");
            return CollectionsKt.contains((Iterable)iterable, (Object)stack) && stack.m_41613_() > 0 && $this.canUse(battle2, target) && actor.canFitForcedAction();
        }
    }
}

