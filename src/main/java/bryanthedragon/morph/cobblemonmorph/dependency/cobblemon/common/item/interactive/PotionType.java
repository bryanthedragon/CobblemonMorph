/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u001f\b\u0002\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/item/interactive/PotionType;", "", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "Lkotlin/Function0;", "Lcom/bedrockk/molang/Expression;", "amountToHeal", "Lkotlin/jvm/functions/Function0;", "getAmountToHeal", "()Lkotlin/jvm/functions/Function0;", "", "curesStatus", "Z", "getCuresStatus", "()Z", "<init>", "(Ljava/lang/String;ILkotlin/jvm/functions/Function0;Z)V", "POTION", "SUPER_POTION", "HYPER_POTION", "MAX_POTION", "FULL_RESTORE", "common"})
public abstract class PotionType
extends Enum<PotionType>
implements BagItem {
    @NotNull
    private final Function0<Expression> amountToHeal;
    private final boolean curesStatus;
    public static final /* enum */ PotionType POTION = new POTION("POTION", 0);
    public static final /* enum */ PotionType SUPER_POTION = new SUPER_POTION("SUPER_POTION", 1);
    public static final /* enum */ PotionType HYPER_POTION = new HYPER_POTION("HYPER_POTION", 2);
    public static final /* enum */ PotionType MAX_POTION = new MAX_POTION("MAX_POTION", 3);
    public static final /* enum */ PotionType FULL_RESTORE = new FULL_RESTORE("FULL_RESTORE", 4);
    private static final /* synthetic */ PotionType[] $VALUES;

    private PotionType(Function0<? extends Expression> amountToHeal, boolean curesStatus) {
        this.amountToHeal = amountToHeal;
        this.curesStatus = curesStatus;
    }

    @NotNull
    public final Function0<Expression> getAmountToHeal() {
        return this.amountToHeal;
    }

    public final boolean getCuresStatus() {
        return this.curesStatus;
    }

    @Override
    public boolean canStillUse(@NotNull ServerPlayer player, @NotNull PokemonBattle battle2, @NotNull BattleActor actor, @NotNull BattlePokemon target, @NotNull ItemStack stack) {
        return BagItem.DefaultImpls.canStillUse(this, player, battle2, actor, target, stack);
    }

    public static PotionType[] values() {
        return (PotionType[])$VALUES.clone();
    }

    public static PotionType valueOf(String value2) {
        return Enum.valueOf(PotionType.class, value2);
    }

    public /* synthetic */ PotionType(String $enum$name, int $enum$ordinal, Function0 amountToHeal, boolean curesStatus, DefaultConstructorMarker $constructor_marker) {
        this((Function0<? extends Expression>)amountToHeal, curesStatus);
    }

    static {
        $VALUES = potionTypeArray = new PotionType[]{PotionType.POTION, PotionType.SUPER_POTION, PotionType.HYPER_POTION, PotionType.MAX_POTION, PotionType.FULL_RESTORE};
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ)\u0010\u000e\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u00042\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\f8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/item/interactive/PotionType$FULL_RESTORE;", "Lcom/cobblemon/mod/common/item/interactive/PotionType;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "target", "", "canUse", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Z", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "battlePokemon", "", "data", "getShowdownInput", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Ljava/lang/String;)Ljava/lang/String;", "itemName", "Ljava/lang/String;", "getItemName", "()Ljava/lang/String;", "common"})
    static final class FULL_RESTORE
    extends PotionType {
        @NotNull
        private final String itemName;

        /*
         * WARNING - void declaration
         */
        FULL_RESTORE() {
            void var1_1;
            this.itemName = "item.cobblemon.full_restore";
        }

        @Override
        @NotNull
        public String getItemName() {
            return this.itemName;
        }

        @Override
        @NotNull
        public String getShowdownInput(@NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @Nullable String data) {
            Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
            Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
            return "full_restore";
        }

        @Override
        public boolean canUse(@NotNull PokemonBattle battle2, @NotNull BattlePokemon target) {
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            Intrinsics.checkNotNullParameter((Object)target, (String)"target");
            return target.getHealth() < target.getMaxHealth() && target.getHealth() > 0;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ)\u0010\u000e\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u00042\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\f8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/item/interactive/PotionType$HYPER_POTION;", "Lcom/cobblemon/mod/common/item/interactive/PotionType;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "target", "", "canUse", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Z", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "battlePokemon", "", "data", "getShowdownInput", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Ljava/lang/String;)Ljava/lang/String;", "itemName", "Ljava/lang/String;", "getItemName", "()Ljava/lang/String;", "common"})
    static final class HYPER_POTION
    extends PotionType {
        @NotNull
        private final String itemName;

        /*
         * WARNING - void declaration
         */
        HYPER_POTION() {
            void var1_1;
            this.itemName = "item.cobblemon.hyper_potion";
        }

        @Override
        @NotNull
        public String getItemName() {
            return this.itemName;
        }

        @Override
        @NotNull
        public String getShowdownInput(@NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @Nullable String data) {
            Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
            Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
            return "potion " + MoLangExtensionsKt.resolveInt(MoLangExtensionsKt.getGenericRuntime(), (Expression)this.getAmountToHeal().invoke(), battlePokemon);
        }

        @Override
        public boolean canUse(@NotNull PokemonBattle battle2, @NotNull BattlePokemon target) {
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            Intrinsics.checkNotNullParameter((Object)target, (String)"target");
            return target.getHealth() < target.getMaxHealth() && target.getHealth() > 0;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ)\u0010\u000e\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u00042\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\f8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/item/interactive/PotionType$MAX_POTION;", "Lcom/cobblemon/mod/common/item/interactive/PotionType;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "target", "", "canUse", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Z", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "battlePokemon", "", "data", "getShowdownInput", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Ljava/lang/String;)Ljava/lang/String;", "itemName", "Ljava/lang/String;", "getItemName", "()Ljava/lang/String;", "common"})
    static final class MAX_POTION
    extends PotionType {
        @NotNull
        private final String itemName;

        /*
         * WARNING - void declaration
         */
        MAX_POTION() {
            void var1_1;
            this.itemName = "item.cobblemon.max_potion";
        }

        @Override
        @NotNull
        public String getItemName() {
            return this.itemName;
        }

        @Override
        @NotNull
        public String getShowdownInput(@NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @Nullable String data) {
            Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
            Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
            return "potion " + (battlePokemon.getMaxHealth() - battlePokemon.getHealth());
        }

        @Override
        public boolean canUse(@NotNull PokemonBattle battle2, @NotNull BattlePokemon target) {
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            Intrinsics.checkNotNullParameter((Object)target, (String)"target");
            return target.getHealth() < target.getMaxHealth() && target.getHealth() > 0;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ)\u0010\u000e\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u00042\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\f8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/item/interactive/PotionType$POTION;", "Lcom/cobblemon/mod/common/item/interactive/PotionType;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "target", "", "canUse", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Z", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "battlePokemon", "", "data", "getShowdownInput", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Ljava/lang/String;)Ljava/lang/String;", "itemName", "Ljava/lang/String;", "getItemName", "()Ljava/lang/String;", "common"})
    static final class POTION
    extends PotionType {
        @NotNull
        private final String itemName;

        /*
         * WARNING - void declaration
         */
        POTION() {
            void var1_1;
            this.itemName = "item.cobblemon.potion";
        }

        @Override
        @NotNull
        public String getItemName() {
            return this.itemName;
        }

        @Override
        @NotNull
        public String getShowdownInput(@NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @Nullable String data) {
            Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
            Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
            return "potion " + MoLangExtensionsKt.resolveInt(MoLangExtensionsKt.getGenericRuntime(), (Expression)this.getAmountToHeal().invoke(), battlePokemon);
        }

        @Override
        public boolean canUse(@NotNull PokemonBattle battle2, @NotNull BattlePokemon target) {
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            Intrinsics.checkNotNullParameter((Object)target, (String)"target");
            return target.getHealth() < target.getMaxHealth() && target.getHealth() > 0;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u00c6\u0001\u0018\u00002\u00020\u0001J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ)\u0010\u000e\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u00042\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\f8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/item/interactive/PotionType$SUPER_POTION;", "Lcom/cobblemon/mod/common/item/interactive/PotionType;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "target", "", "canUse", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Z", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "battlePokemon", "", "data", "getShowdownInput", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Ljava/lang/String;)Ljava/lang/String;", "itemName", "Ljava/lang/String;", "getItemName", "()Ljava/lang/String;", "common"})
    static final class SUPER_POTION
    extends PotionType {
        @NotNull
        private final String itemName;

        /*
         * WARNING - void declaration
         */
        SUPER_POTION() {
            void var1_1;
            this.itemName = "item.cobblemon.super_potion";
        }

        @Override
        @NotNull
        public String getItemName() {
            return this.itemName;
        }

        @Override
        @NotNull
        public String getShowdownInput(@NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @Nullable String data) {
            Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
            Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
            return "potion " + MoLangExtensionsKt.resolveInt(MoLangExtensionsKt.getGenericRuntime(), (Expression)this.getAmountToHeal().invoke(), battlePokemon);
        }

        @Override
        public boolean canUse(@NotNull PokemonBattle battle2, @NotNull BattlePokemon target) {
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            Intrinsics.checkNotNullParameter((Object)target, (String)"target");
            return target.getHealth() < target.getMaxHealth() && target.getHealth() > 0;
        }
    }
}

