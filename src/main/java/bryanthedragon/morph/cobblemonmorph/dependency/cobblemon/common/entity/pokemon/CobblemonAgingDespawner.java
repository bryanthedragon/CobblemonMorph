/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.Despawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0013\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003BC\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0011\u0012\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\u0004\b\"\u0010#J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\t\u0010\nR\u0017\u0010\f\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR#\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00110\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u0017\u001a\u0004\b\u001b\u0010\u0019R\u0017\u0010\u001c\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\r\u001a\u0004\b\u001d\u0010\u000fR\u0017\u0010\u001e\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\r\u001a\u0004\b\u001f\u0010\u000fR\u0017\u0010 \u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b \u0010\u0017\u001a\u0004\b!\u0010\u0019\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/CobblemonAgingDespawner;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/api/entity/Despawner;", "entity", "", "beginTracking", "(Lnet/minecraft/world/entity/Entity;)V", "", "shouldDespawn", "(Lnet/minecraft/world/entity/Entity;)Z", "", "farDistance", "F", "getFarDistance", "()F", "Lkotlin/Function1;", "", "getAgeTicks", "Lkotlin/jvm/functions/Function1;", "getGetAgeTicks", "()Lkotlin/jvm/functions/Function1;", "maxAgeTicks", "I", "getMaxAgeTicks", "()I", "minAgeTicks", "getMinAgeTicks", "nearDistance", "getNearDistance", "nearToFar", "getNearToFar", "youngToOld", "getYoungToOld", "<init>", "(FFIILkotlin/jvm/functions/Function1;)V", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonAgingDespawner.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonAgingDespawner.kt\ncom/cobblemon/mod/common/entity/pokemon/CobblemonAgingDespawner\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,61:1\n1#2:62\n*E\n"})
public final class CobblemonAgingDespawner<T extends Entity>
implements Despawner<T> {
    private final float nearDistance;
    private final float farDistance;
    private final int minAgeTicks;
    private final int maxAgeTicks;
    @NotNull
    private final Function1<T, Integer> getAgeTicks;
    private final float nearToFar;
    private final int youngToOld;

    public CobblemonAgingDespawner(float nearDistance, float farDistance, int minAgeTicks, int maxAgeTicks, @NotNull Function1<? super T, Integer> getAgeTicks) {
        Intrinsics.checkNotNullParameter(getAgeTicks, (String)"getAgeTicks");
        this.nearDistance = nearDistance;
        this.farDistance = farDistance;
        this.minAgeTicks = minAgeTicks;
        this.maxAgeTicks = maxAgeTicks;
        this.getAgeTicks = getAgeTicks;
        this.nearToFar = this.farDistance - this.nearDistance;
        this.youngToOld = this.maxAgeTicks - this.minAgeTicks;
    }

    public /* synthetic */ CobblemonAgingDespawner(float f, float f2, int n, int n2, Function1 function1, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 1) != 0) {
            f = 32.0f;
        }
        if ((n3 & 2) != 0) {
            f2 = 96.0f;
        }
        if ((n3 & 4) != 0) {
            n = 600;
        }
        if ((n3 & 8) != 0) {
            n2 = 3600;
        }
        this(f, f2, n, n2, function1);
    }

    public final float getNearDistance() {
        return this.nearDistance;
    }

    public final float getFarDistance() {
        return this.farDistance;
    }

    public final int getMinAgeTicks() {
        return this.minAgeTicks;
    }

    public final int getMaxAgeTicks() {
        return this.maxAgeTicks;
    }

    @NotNull
    public final Function1<T, Integer> getGetAgeTicks() {
        return this.getAgeTicks;
    }

    public final float getNearToFar() {
        return this.nearToFar;
    }

    public final int getYoungToOld() {
        return this.youngToOld;
    }

    @Override
    public void beginTracking(@NotNull T entity2) {
        Intrinsics.checkNotNullParameter(entity2, (String)"entity");
    }

    @Override
    public boolean shouldDespawn(@NotNull T entity2) {
        float distanceRatio;
        float maximumAge;
        Float f;
        Intrinsics.checkNotNullParameter(entity2, (String)"entity");
        int age = ((Number)this.getAgeTicks.invoke(entity2)).intValue();
        if (age < this.minAgeTicks || entity2 instanceof PokemonEntity && ((PokemonEntity)entity2).isBusy() || entity2.m_20159_()) {
            return false;
        }
        List list = entity2.m_9236_().m_6907_();
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"entity.world.players");
        Iterator iterator = ((Iterable)list).iterator();
        if (!iterator.hasNext()) {
            f = null;
        } else {
            Player it = (Player)iterator.next();
            boolean bl = false;
            float f2 = it.m_20270_(entity2);
            while (iterator.hasNext()) {
                Player it2 = (Player)iterator.next();
                $i$a$-minOfOrNull-CobblemonAgingDespawner$shouldDespawn$closestDistance$1 = false;
                float f3 = it2.m_20270_(entity2);
                f2 = Math.min(f2, f3);
            }
            f = Float.valueOf(f2);
        }
        float closestDistance = f != null ? f.floatValue() : Float.MAX_VALUE;
        return closestDistance < this.nearDistance ? false : (age > this.maxAgeTicks || closestDistance > this.farDistance ? true : (float)age > (maximumAge = (1.0f - (distanceRatio = (closestDistance - this.nearDistance) / this.nearToFar)) * (float)this.youngToOld));
    }
}

