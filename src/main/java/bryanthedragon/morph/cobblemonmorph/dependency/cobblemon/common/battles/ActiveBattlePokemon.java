/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.Targetable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u001c\u001a\u00020\u001b\u0012\n\b\u0002\u0010&\u001a\u0004\u0018\u00010%\u00a2\u0006\u0004\b8\u00109J\u0015\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00000\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00000\bH\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\r\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0015\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00000\u0011H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0004J\u000f\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\r\u0010\u0016\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0016\u0010\u0015J\u0017\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0001H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\r\u0010\u001a\u001a\u00020\u0013\u00a2\u0006\u0004\b\u001a\u0010\u0015R\u0017\u0010\u001c\u001a\u00020\u001b8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010!\u001a\u00020 8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R$\u0010&\u001a\u0004\u0018\u00010%8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R$\u0010,\u001a\u0004\u0018\u00010%8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b,\u0010'\u001a\u0004\b-\u0010)\"\u0004\b.\u0010+R0\u00102\u001a\u0010\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u000201\u0018\u00010/8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b2\u00103\u001a\u0004\b4\u00105\"\u0004\b6\u00107\u00a8\u0006:"}, d2={"Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "Lcom/cobblemon/mod/common/battles/Targetable;", "", "getActorPokemon", "()Ljava/util/List;", "", "getActorShowdownId", "()Ljava/lang/String;", "", "getAllActivePokemon", "()Ljava/lang/Iterable;", "Lcom/cobblemon/mod/common/battles/BattleFormat;", "getFormat", "()Lcom/cobblemon/mod/common/battles/BattleFormat;", "Lcom/cobblemon/mod/common/battles/BattleSide;", "getSide", "()Lcom/cobblemon/mod/common/battles/BattleSide;", "", "getSidePokemon", "", "hasPokemon", "()Z", "isAlive", "other", "isAllied", "(Lcom/cobblemon/mod/common/battles/Targetable;)Z", "isGone", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "getActor", "()Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "getBattle", "()Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "getBattlePokemon", "()Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "setBattlePokemon", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)V", "illusion", "getIllusion", "setIllusion", "Lkotlin/Pair;", "Lnet/minecraft/server/level/ServerLevel;", "Lnet/minecraft/world/phys/Vec3;", "position", "Lkotlin/Pair;", "getPosition", "()Lkotlin/Pair;", "setPosition", "(Lkotlin/Pair;)V", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)V", "common"})
public final class ActiveBattlePokemon
implements Targetable {
    @NotNull
    private final BattleActor actor;
    @Nullable
    private BattlePokemon battlePokemon;
    @NotNull
    private final PokemonBattle battle;
    @Nullable
    private Pair<? extends ServerLevel, ? extends Vec3> position;
    @Nullable
    private BattlePokemon illusion;

    public ActiveBattlePokemon(@NotNull BattleActor actor, @Nullable BattlePokemon battlePokemon) {
        Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
        this.actor = actor;
        this.battlePokemon = battlePokemon;
        this.battle = this.actor.getBattle();
    }

    public /* synthetic */ ActiveBattlePokemon(BattleActor battleActor, BattlePokemon battlePokemon, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            battlePokemon = null;
        }
        this(battleActor, battlePokemon);
    }

    @NotNull
    public final BattleActor getActor() {
        return this.actor;
    }

    @Nullable
    public final BattlePokemon getBattlePokemon() {
        return this.battlePokemon;
    }

    public final void setBattlePokemon(@Nullable BattlePokemon battlePokemon) {
        this.battlePokemon = battlePokemon;
    }

    @NotNull
    public final PokemonBattle getBattle() {
        return this.battle;
    }

    @NotNull
    public final BattleSide getSide() {
        return this.actor.getSide();
    }

    @Nullable
    public final Pair<ServerLevel, Vec3> getPosition() {
        return this.position;
    }

    public final void setPosition(@Nullable Pair<? extends ServerLevel, ? extends Vec3> pair) {
        this.position = pair;
    }

    @Nullable
    public final BattlePokemon getIllusion() {
        return this.illusion;
    }

    public final void setIllusion(@Nullable BattlePokemon battlePokemon) {
        this.illusion = battlePokemon;
    }

    @NotNull
    public Iterable<ActiveBattlePokemon> getAllActivePokemon() {
        return this.battle.getActivePokemon();
    }

    @Override
    @NotNull
    public String getActorShowdownId() {
        return this.actor.getShowdownId();
    }

    @NotNull
    public List<ActiveBattlePokemon> getActorPokemon() {
        return this.actor.getActivePokemon();
    }

    @NotNull
    public List<ActiveBattlePokemon> getSidePokemon() {
        return this.getSide().getActivePokemon();
    }

    @Override
    @NotNull
    public BattleFormat getFormat() {
        return this.battle.getFormat();
    }

    @Override
    public boolean isAllied(@NotNull Targetable other) {
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        return Intrinsics.areEqual((Object)this.getSide(), (Object)((ActiveBattlePokemon)other).getSide());
    }

    @Override
    public boolean hasPokemon() {
        return this.battlePokemon != null;
    }

    public final boolean isGone() {
        BattlePokemon battlePokemon = this.battlePokemon;
        return battlePokemon != null ? battlePokemon.getGone() : true;
    }

    public final boolean isAlive() {
        BattlePokemon battlePokemon = this.battlePokemon;
        return (battlePokemon != null ? battlePokemon.getHealth() : 0) > 0;
    }

    @Override
    @NotNull
    public String getPNX() {
        return Targetable.DefaultImpls.getPNX(this);
    }

    @Override
    @NotNull
    public List<Targetable> getAdjacent() {
        return Targetable.DefaultImpls.getAdjacent(this);
    }

    @Override
    @NotNull
    public List<Targetable> getAdjacentAllies() {
        return Targetable.DefaultImpls.getAdjacentAllies(this);
    }

    @Override
    @NotNull
    public List<Targetable> getAdjacentOpponents() {
        return Targetable.DefaultImpls.getAdjacentOpponents(this);
    }

    @Override
    @NotNull
    public String getSignedDigitRelativeTo(@NotNull Targetable other) {
        return Targetable.DefaultImpls.getSignedDigitRelativeTo(this, other);
    }

    @Override
    public int getDigitRelativeTo(@NotNull Targetable other) {
        return Targetable.DefaultImpls.getDigitRelativeTo(this, other);
    }

    @Override
    public int getDigit(boolean asAlly) {
        return Targetable.DefaultImpls.getDigit(this, asAlly);
    }

    @Override
    public char getLetter() {
        return Targetable.DefaultImpls.getLetter(this);
    }
}

