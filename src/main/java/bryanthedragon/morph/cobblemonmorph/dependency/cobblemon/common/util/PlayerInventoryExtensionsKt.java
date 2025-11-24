/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$IntRef
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u001a'\u0010\u0007\u001a\u00020\u0006*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lnet/minecraft/world/entity/player/Inventory;", "", "amount", "Ljava/util/function/Predicate;", "Lnet/minecraft/world/item/ItemStack;", "rule", "", "removeAmountIf", "(Lnet/minecraft/world/entity/player/Inventory;ILjava/util/function/Predicate;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPlayerInventoryExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PlayerInventoryExtensions.kt\ncom/cobblemon/mod/common/util/PlayerInventoryExtensionsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,32:1\n1855#2,2:33\n*S KotlinDebug\n*F\n+ 1 PlayerInventoryExtensions.kt\ncom/cobblemon/mod/common/util/PlayerInventoryExtensionsKt\n*L\n17#1:33,2\n*E\n"})
public final class PlayerInventoryExtensionsKt {
    public static final void removeAmountIf(@NotNull Inventory $this$removeAmountIf, int amount, @NotNull Predicate<ItemStack> rule) {
        Intrinsics.checkNotNullParameter((Object)$this$removeAmountIf, (String)"<this>");
        Intrinsics.checkNotNullParameter(rule, (String)"rule");
        List list = $this$removeAmountIf.f_35979_;
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"this.combinedInventory");
        Iterable $this$forEach$iv = list;
        boolean $i$f$forEach = false;
        block0: for (Object element$iv : $this$forEach$iv) {
            ItemStack result;
            NonNullList it = (NonNullList)element$iv;
            boolean bl = false;
            Ref.IntRef index = new Ref.IntRef();
            List matches2 = it.stream().map(arg_0 -> PlayerInventoryExtensionsKt.removeAmountIf$lambda$2$lambda$0((Function1)new Function1<ItemStack, Pair<? extends Integer, ? extends ItemStack>>(index){
                final /* synthetic */ Ref.IntRef $index;
                {
                    this.$index = $index;
                    super(1);
                }

                public final Pair<Integer, ItemStack> invoke(ItemStack a) {
                    int n = this.$index.element;
                    this.$index.element = n + 1;
                    return new Pair((Object)n, (Object)a);
                }
            }, arg_0)).filter(arg_0 -> PlayerInventoryExtensionsKt.removeAmountIf$lambda$2$lambda$1((Function1)new Function1<Pair<? extends Integer, ? extends ItemStack>, Boolean>(rule){
                final /* synthetic */ Predicate<ItemStack> $rule;
                {
                    this.$rule = $rule;
                    super(1);
                }

                @NotNull
                public final Boolean invoke(Pair<Integer, ItemStack> it) {
                    return this.$rule.test((ItemStack)it.getSecond());
                }
            }, arg_0)).collect(Collectors.toList());
            for (int remaining = amount; remaining > 0; remaining -= result.m_41613_()) {
                Pair element;
                Intrinsics.checkNotNullExpressionValue(matches2, (String)"matches");
                if ((Pair)CollectionsKt.removeFirstOrNull(matches2) == null) continue block0;
                result = $this$removeAmountIf.m_7407_(((Number)element.getFirst()).intValue(), amount);
            }
        }
    }

    private static final Pair removeAmountIf$lambda$2$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Pair)$tmp0.invoke(p0);
    }

    private static final boolean removeAmountIf$lambda$2$lambda$1(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

