/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.material.Fluid
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0010\u0007\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b!\u0010\"J\u001b\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001b\u0010\b\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\b\u0010\u0007J\u001b\u0010\t\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\t\u0010\u0007R\u001a\u0010\n\u001a\u00020\u00058\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u00058\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000b\u001a\u0004\b\u000f\u0010\rR\u001a\u0010\u0010\u001a\u00020\u00058\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\rR\u001a\u0010\u0012\u001a\u00020\u00058\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0012\u0010\u000b\u001a\u0004\b\u0013\u0010\rR\u001a\u0010\u0014\u001a\u00020\u00058\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0014\u0010\u000b\u001a\u0004\b\u0015\u0010\rR\u001a\u0010\u0016\u001a\u00020\u00058\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0016\u0010\u000b\u001a\u0004\b\u0017\u0010\rR\u001a\u0010\u0018\u001a\u00020\u00058\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0018\u0010\u000b\u001a\u0004\b\u0019\u0010\rR\u001a\u0010\u001a\u001a\u00020\u00058\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u001a\u0010\u000b\u001a\u0004\b\u001b\u0010\rR\u001a\u0010\u001d\u001a\u00020\u001c8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 \u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/pokemon/ai/SwimBehaviour;", "", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/level/material/Fluid;", "tag", "", "canBreatheUnderFluid", "(Lnet/minecraft/tags/TagKey;)Z", "canSwimInFluid", "canWalkOnFluid", "avoidsWater", "Z", "getAvoidsWater", "()Z", "canBreatheUnderlava", "getCanBreatheUnderlava", "canBreatheUnderwater", "getCanBreatheUnderwater", "canSwimInLava", "getCanSwimInLava", "canSwimInWater", "getCanSwimInWater", "canWalkOnLava", "getCanWalkOnLava", "canWalkOnWater", "getCanWalkOnWater", "hurtByLava", "getHurtByLava", "", "swimSpeed", "F", "getSwimSpeed", "()F", "<init>", "()V", "common"})
public final class SwimBehaviour {
    private final boolean avoidsWater;
    private final boolean hurtByLava;
    private final boolean canSwimInWater;
    private final boolean canSwimInLava;
    private final float swimSpeed;
    private final boolean canBreatheUnderwater;
    private final boolean canBreatheUnderlava;
    private final boolean canWalkOnWater;
    private final boolean canWalkOnLava;

    public SwimBehaviour() {
        this.hurtByLava = true;
        this.canSwimInWater = true;
        this.canSwimInLava = true;
        this.swimSpeed = 0.3f;
    }

    public final boolean getAvoidsWater() {
        return this.avoidsWater;
    }

    public final boolean getHurtByLava() {
        return this.hurtByLava;
    }

    public final boolean getCanSwimInWater() {
        return this.canSwimInWater;
    }

    public final boolean getCanSwimInLava() {
        return this.canSwimInLava;
    }

    public final float getSwimSpeed() {
        return this.swimSpeed;
    }

    public final boolean getCanBreatheUnderwater() {
        return this.canBreatheUnderwater;
    }

    public final boolean getCanBreatheUnderlava() {
        return this.canBreatheUnderlava;
    }

    public final boolean getCanWalkOnWater() {
        return this.canWalkOnWater;
    }

    public final boolean getCanWalkOnLava() {
        return this.canWalkOnLava;
    }

    public final boolean canWalkOnFluid(@NotNull TagKey<Fluid> tag) {
        Intrinsics.checkNotNullParameter(tag, (String)"tag");
        return Intrinsics.areEqual(tag, (Object)FluidTags.f_13131_) ? this.canWalkOnWater : (Intrinsics.areEqual(tag, (Object)FluidTags.f_13132_) ? this.canWalkOnLava : false);
    }

    public final boolean canBreatheUnderFluid(@NotNull TagKey<Fluid> tag) {
        Intrinsics.checkNotNullParameter(tag, (String)"tag");
        return Intrinsics.areEqual(tag, (Object)FluidTags.f_13131_) ? this.canBreatheUnderwater : (Intrinsics.areEqual(tag, (Object)FluidTags.f_13132_) ? this.canBreatheUnderlava : false);
    }

    public final boolean canSwimInFluid(@NotNull TagKey<Fluid> tag) {
        Intrinsics.checkNotNullParameter(tag, (String)"tag");
        return Intrinsics.areEqual(tag, (Object)FluidTags.f_13131_) ? this.canSwimInWater : (Intrinsics.areEqual(tag, (Object)FluidTags.f_13132_) ? this.canSwimInLava : false);
    }
}

