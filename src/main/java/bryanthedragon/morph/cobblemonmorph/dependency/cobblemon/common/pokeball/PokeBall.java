/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Deprecated
 *  kotlin.Metadata
 *  kotlin.ReplaceWith
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\t\b\u0016\u0018\u00002\u00020\u0001BQ\u0012\u0006\u0010*\u001a\u00020#\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0014\u0012\u000e\b\u0002\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019\u0012\u0006\u00101\u001a\u00020,\u0012\u0006\u0010$\u001a\u00020#\u0012\u0006\u0010(\u001a\u00020#\u0012\u0006\u0010-\u001a\u00020,\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\u0004\b3\u00104J\u0017\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0001\u00a2\u0006\u0004\b\u0005\u0010\u0006J\r\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\u0004\b\r\u0010\u000eR\u0017\u0010\u0010\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0015\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u001d\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\"\u0010\t\u001a\u00020\b8\u0000@\u0000X\u0080.\u00a2\u0006\u0012\n\u0004\b\t\u0010\u001f\u001a\u0004\b \u0010\n\"\u0004\b!\u0010\"R\u0017\u0010$\u001a\u00020#8\u0006\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'R\u0017\u0010(\u001a\u00020#8\u0006\u00a2\u0006\f\n\u0004\b(\u0010%\u001a\u0004\b)\u0010'R\u0017\u0010*\u001a\u00020#8\u0006\u00a2\u0006\f\n\u0004\b*\u0010%\u001a\u0004\b+\u0010'R\u0017\u0010-\u001a\u00020,8\u0006\u00a2\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100R\u0017\u00101\u001a\u00020,8\u0006\u00a2\u0006\f\n\u0004\b1\u0010.\u001a\u0004\b2\u00100\u00a8\u00065"}, d2={"Lcom/cobblemon/mod/common/pokeball/PokeBall;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "target", "", "hpForCalculation$common", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)I", "hpForCalculation", "Lcom/cobblemon/mod/common/item/PokeBallItem;", "item", "()Lcom/cobblemon/mod/common/item/PokeBallItem;", "count", "Lnet/minecraft/world/item/ItemStack;", "stack", "(I)Lnet/minecraft/world/item/ItemStack;", "", "ancient", "Z", "getAncient", "()Z", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "catchRateModifier", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "getCatchRateModifier", "()Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "", "Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureEffect;", "effects", "Ljava/util/List;", "getEffects", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/item/PokeBallItem;", "getItem$common", "setItem$common", "(Lcom/cobblemon/mod/common/item/PokeBallItem;)V", "Lnet/minecraft/resources/ResourceLocation;", "model2d", "Lnet/minecraft/resources/ResourceLocation;", "getModel2d", "()Lnet/minecraft/resources/ResourceLocation;", "model3d", "getModel3d", "name", "getName", "", "throwPower", "F", "getThrowPower", "()F", "waterDragValue", "getWaterDragValue", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;Ljava/util/List;FLnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;FZ)V", "common"})
public class PokeBall {
    @NotNull
    private final ResourceLocation name;
    @NotNull
    private final CatchRateModifier catchRateModifier;
    @NotNull
    private final List<CaptureEffect> effects;
    private final float waterDragValue;
    @NotNull
    private final ResourceLocation model2d;
    @NotNull
    private final ResourceLocation model3d;
    private final float throwPower;
    private final boolean ancient;
    public PokeBallItem item;

    public PokeBall(@NotNull ResourceLocation name, @NotNull CatchRateModifier catchRateModifier, @NotNull List<? extends CaptureEffect> effects, float waterDragValue, @NotNull ResourceLocation model2d, @NotNull ResourceLocation model3d, float throwPower, boolean ancient) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)catchRateModifier, (String)"catchRateModifier");
        Intrinsics.checkNotNullParameter(effects, (String)"effects");
        Intrinsics.checkNotNullParameter((Object)model2d, (String)"model2d");
        Intrinsics.checkNotNullParameter((Object)model3d, (String)"model3d");
        this.name = name;
        this.catchRateModifier = catchRateModifier;
        this.effects = effects;
        this.waterDragValue = waterDragValue;
        this.model2d = model2d;
        this.model3d = model3d;
        this.throwPower = throwPower;
        this.ancient = ancient;
    }

    public /* synthetic */ PokeBall(ResourceLocation resourceLocation, CatchRateModifier catchRateModifier, List list, float f, ResourceLocation resourceLocation2, ResourceLocation resourceLocation3, float f2, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            catchRateModifier = CatchRateModifier.Companion.getDUMMY$common();
        }
        if ((n & 4) != 0) {
            list = CollectionsKt.emptyList();
        }
        this(resourceLocation, catchRateModifier, list, f, resourceLocation2, resourceLocation3, f2, bl);
    }

    @NotNull
    public final ResourceLocation getName() {
        return this.name;
    }

    @NotNull
    public final CatchRateModifier getCatchRateModifier() {
        return this.catchRateModifier;
    }

    @NotNull
    public final List<CaptureEffect> getEffects() {
        return this.effects;
    }

    public final float getWaterDragValue() {
        return this.waterDragValue;
    }

    @NotNull
    public final ResourceLocation getModel2d() {
        return this.model2d;
    }

    @NotNull
    public final ResourceLocation getModel3d() {
        return this.model3d;
    }

    public final float getThrowPower() {
        return this.throwPower;
    }

    public final boolean getAncient() {
        return this.ancient;
    }

    @NotNull
    public final PokeBallItem getItem$common() {
        PokeBallItem pokeBallItem = this.item;
        if (pokeBallItem != null) {
            return pokeBallItem;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"item");
        return null;
    }

    public final void setItem$common(@NotNull PokeBallItem pokeBallItem) {
        Intrinsics.checkNotNullParameter((Object)((Object)pokeBallItem), (String)"<set-?>");
        this.item = pokeBallItem;
    }

    @NotNull
    public final PokeBallItem item() {
        return this.getItem$common();
    }

    @NotNull
    public final ItemStack stack(int count) {
        return new ItemStack((ItemLike)this.item(), count);
    }

    public static /* synthetic */ ItemStack stack$default(PokeBall pokeBall, int n, int n2, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: stack");
        }
        if ((n2 & 1) != 0) {
            n = 1;
        }
        return pokeBall.stack(n);
    }

    @Deprecated(message="This is a temporary solution for the safari ball dilemma", replaceWith=@ReplaceWith(expression="target.currentHealth", imports={}))
    public final int hpForCalculation$common(@NotNull Pokemon target) {
        Intrinsics.checkNotNullParameter((Object)target, (String)"target");
        return Intrinsics.areEqual((Object)this.name, (Object)PokeBalls.INSTANCE.getSAFARI_BALL().name) ? target.getHp() : target.getCurrentHealth();
    }
}

