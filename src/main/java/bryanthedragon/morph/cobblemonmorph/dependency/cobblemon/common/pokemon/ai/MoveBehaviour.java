/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.FlyBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.SwimBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.WalkBehaviour;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b'\u0010(R\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\u00028\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\f\u0010\u0004\u001a\u0004\b\r\u0010\u0006R\u001a\u0010\u000f\u001a\u00020\u000e8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0019\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001e\u001a\u00020\u001d8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!R\u001a\u0010#\u001a\u00020\"8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\u00a8\u0006)"}, d2={"Lcom/cobblemon/mod/common/pokemon/ai/MoveBehaviour;", "", "", "canLook", "Z", "getCanLook", "()Z", "Lcom/cobblemon/mod/common/pokemon/ai/FlyBehaviour;", "fly", "Lcom/cobblemon/mod/common/pokemon/ai/FlyBehaviour;", "getFly", "()Lcom/cobblemon/mod/common/pokemon/ai/FlyBehaviour;", "looksAtEntities", "getLooksAtEntities", "", "stepHeight", "F", "getStepHeight", "()F", "Lcom/cobblemon/mod/common/pokemon/ai/SwimBehaviour;", "swim", "Lcom/cobblemon/mod/common/pokemon/ai/SwimBehaviour;", "getSwim", "()Lcom/cobblemon/mod/common/pokemon/ai/SwimBehaviour;", "Lcom/cobblemon/mod/common/pokemon/ai/WalkBehaviour;", "walk", "Lcom/cobblemon/mod/common/pokemon/ai/WalkBehaviour;", "getWalk", "()Lcom/cobblemon/mod/common/pokemon/ai/WalkBehaviour;", "", "wanderChance", "I", "getWanderChance", "()I", "", "wanderSpeed", "D", "getWanderSpeed", "()D", "<init>", "()V", "common"})
public final class MoveBehaviour {
    @NotNull
    private final WalkBehaviour walk = new WalkBehaviour();
    @NotNull
    private final SwimBehaviour swim = new SwimBehaviour();
    @NotNull
    private final FlyBehaviour fly = new FlyBehaviour();
    private final float stepHeight;
    private final int wanderChance;
    private final double wanderSpeed;
    private final boolean canLook;
    private final boolean looksAtEntities;

    public MoveBehaviour() {
        this.stepHeight = 0.6f;
        this.wanderChance = 120;
        this.wanderSpeed = 1.0;
        this.canLook = true;
        this.looksAtEntities = true;
    }

    @NotNull
    public final WalkBehaviour getWalk() {
        return this.walk;
    }

    @NotNull
    public final SwimBehaviour getSwim() {
        return this.swim;
    }

    @NotNull
    public final FlyBehaviour getFly() {
        return this.fly;
    }

    public final float getStepHeight() {
        return this.stepHeight;
    }

    public final int getWanderChance() {
        return this.wanderChance;
    }

    public final double getWanderSpeed() {
        return this.wanderSpeed;
    }

    public final boolean getCanLook() {
        return this.canLook;
    }

    public final boolean getLooksAtEntities() {
        return this.looksAtEntities;
    }
}

