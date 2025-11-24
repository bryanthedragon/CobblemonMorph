/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.stats.Stats
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonChestBoatEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonBoatItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem;
import java.util.List;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u001f\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\u0006\u0010\u001c\u001a\u00020\u001b\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ-\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/item/CobblemonBoatItem;", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/phys/HitResult;", "hitResult", "Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatEntity;", "createBoat", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/HitResult;)Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatEntity;", "Lnet/minecraft/world/entity/player/Player;", "user", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/InteractionResultHolder;", "Lnet/minecraft/world/item/ItemStack;", "use", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", "Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;", "boatType", "Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;", "getBoatType", "()Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;", "", "hasChest", "Z", "getHasChest", "()Z", "Lnet/minecraft/item/Item$Settings;", "settings", "<init>", "(Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;ZLnet/minecraft/world/item/Item$Properties;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonBoatItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonBoatItem.kt\ncom/cobblemon/mod/common/item/CobblemonBoatItem\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,75:1\n1855#2,2:76\n*S KotlinDebug\n*F\n+ 1 CobblemonBoatItem.kt\ncom/cobblemon/mod/common/item/CobblemonBoatItem\n*L\n36#1:76,2\n*E\n"})
public final class CobblemonBoatItem
extends CobblemonItem {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final CobblemonBoatType boatType;
    private final boolean hasChest;
    private static final Predicate<Entity> RIDERS = EntitySelector.f_20408_.and(arg_0 -> CobblemonBoatItem.RIDERS$lambda$1(Companion.RIDERS.1.INSTANCE, arg_0));

    public CobblemonBoatItem(@NotNull CobblemonBoatType boatType, boolean hasChest, @NotNull Item.Properties settings) {
        Intrinsics.checkNotNullParameter((Object)((Object)boatType), (String)"boatType");
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        super(settings);
        this.boatType = boatType;
        this.hasChest = hasChest;
    }

    @NotNull
    public final CobblemonBoatType getBoatType() {
        return this.boatType;
    }

    public final boolean getHasChest() {
        return this.hasChest;
    }

    @NotNull
    public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)user, (String)"user");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        ItemStack stack = user.m_21120_(hand);
        BlockHitResult hitResult = Item.m_41435_((Level)world, (Player)user, (ClipContext.Fluid)ClipContext.Fluid.ANY);
        if (hitResult.m_6662_() == HitResult.Type.MISS) {
            InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19098_((Object)stack);
            Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"pass(stack)");
            return interactionResultHolder;
        }
        Vec3 vec3d = user.m_20252_(1.0f);
        Vec3 eyePos = user.m_146892_();
        List list = world.m_6249_((Entity)user, user.m_20191_().m_82369_(vec3d.m_82490_(5.0)).m_82400_(1.0), RIDERS);
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"world.getOtherEntities(u\u2026.0)).expand(1.0), RIDERS)");
        Iterable $this$forEach$iv = list;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Entity entity2 = (Entity)element$iv;
            boolean bl = false;
            AABB box = entity2.m_20191_().m_82400_((double)entity2.m_6143_());
            if (!box.m_82390_(eyePos)) continue;
            InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19098_((Object)stack);
            Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"pass(stack)");
            return interactionResultHolder;
        }
        if (hitResult.m_6662_() != HitResult.Type.BLOCK) {
            InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19098_((Object)stack);
            Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"pass(stack)");
            return interactionResultHolder;
        }
        Intrinsics.checkNotNullExpressionValue((Object)hitResult, (String)"hitResult");
        CobblemonBoatEntity boatEntity = this.createBoat(world, (HitResult)hitResult);
        boatEntity.setBoatType(this.boatType);
        boatEntity.m_146922_(user.m_146908_());
        if (!world.m_45756_((Entity)boatEntity, boatEntity.m_20191_())) {
            InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19100_((Object)stack);
            Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"fail(stack)");
            return interactionResultHolder;
        }
        if (!world.f_46443_) {
            world.m_7967_((Entity)boatEntity);
            world.m_220400_((Entity)user, GameEvent.f_157810_, hitResult.m_82450_());
            if (!user.m_150110_().f_35937_) {
                stack.m_41774_(1);
            }
        }
        user.m_36246_(Stats.f_12982_.m_12902_((Object)this));
        InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19092_((Object)stack, (boolean)world.f_46443_);
        Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"success(stack, world.isClient)");
        return interactionResultHolder;
    }

    private final CobblemonBoatEntity createBoat(Level world, HitResult hitResult) {
        if (this.hasChest) {
            return new CobblemonChestBoatEntity(world, hitResult.m_82450_().f_82479_, hitResult.m_82450_().f_82480_, hitResult.m_82450_().f_82481_);
        }
        return new CobblemonBoatEntity(world, hitResult.m_82450_().f_82479_, hitResult.m_82450_().f_82480_, hitResult.m_82450_().f_82481_);
    }

    private static final boolean RIDERS$lambda$1(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR8\u0010\u0005\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/item/CobblemonBoatItem$Companion;", "", "Ljava/util/function/Predicate;", "Lnet/minecraft/world/entity/Entity;", "kotlin.jvm.PlatformType", "RIDERS", "Ljava/util/function/Predicate;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

