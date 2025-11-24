/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.core.Holder
 *  net.minecraft.core.HolderSet$Named
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import java.util.Optional;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0007\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\f\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0019\u0010\r\u001a\u0004\u0018\u00010\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/api/spawning/detail/PossibleHeldItem;", "", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "Lnet/minecraft/world/item/ItemStack;", "createStack", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Lnet/minecraft/world/item/ItemStack;", "", "item", "Ljava/lang/String;", "getItem", "()Ljava/lang/String;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "Lnet/minecraft/nbt/CompoundTag;", "getNbt", "()Lnet/minecraft/nbt/CompoundTag;", "", "percentage", "D", "getPercentage", "()D", "<init>", "(Ljava/lang/String;Lnet/minecraft/nbt/CompoundTag;D)V", "common"})
@SourceDebugExtension(value={"SMAP\nPossibleHeldItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PossibleHeldItem.kt\ncom/cobblemon/mod/common/api/spawning/detail/PossibleHeldItem\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,53:1\n1#2:54\n*E\n"})
public final class PossibleHeldItem {
    @NotNull
    private final String item;
    @Nullable
    private final CompoundTag nbt;
    private final double percentage;

    public PossibleHeldItem(@NotNull String item, @Nullable CompoundTag nbt, double percentage) {
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        this.item = item;
        this.nbt = nbt;
        this.percentage = percentage;
    }

    public /* synthetic */ PossibleHeldItem(String string, CompoundTag compoundTag, double d, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            compoundTag = null;
        }
        if ((n & 4) != 0) {
            d = 100.0;
        }
        this(string, compoundTag, d);
    }

    @NotNull
    public final String getItem() {
        return this.item;
    }

    @Nullable
    public final CompoundTag getNbt() {
        return this.nbt;
    }

    public final double getPercentage() {
        return this.percentage;
    }

    @Nullable
    public final ItemStack createStack(@NotNull SpawningContext ctx) {
        Object object;
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        Registry itemRegistry = ctx.getWorld().m_9598_().m_175515_(Registries.f_256913_);
        if (StringsKt.startsWith$default((String)this.item, (String)"#", (boolean)false, (int)2, null)) {
            String string = this.item.substring(1);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).substring(startIndex)");
            TagKey tag = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)new ResourceLocation(string));
            opt = itemRegistry.m_203431_(tag);
            if (((Optional)opt).isPresent() && ((HolderSet.Named)((Optional)opt).get()).m_203632_() > 0) {
                Object t = ((Optional)opt).get();
                Intrinsics.checkNotNullExpressionValue(t, (String)"opt.get()");
                HolderSet.Named entryList = (HolderSet.Named)t;
                object = (Item)((Holder)entryList.m_213653_(ctx.getWorld().f_46441_).get()).m_203334_();
            } else {
                Cobblemon.INSTANCE.getLOGGER().error("Unable to find matching spawn held items for tag: " + this.item);
                object = null;
            }
        } else {
            Item item = (Item)itemRegistry.m_7745_(new ResourceLocation(this.item));
            if (item != null) {
                opt = item;
                Item it = opt;
                boolean bl = false;
                object = !Intrinsics.areEqual((Object)it, (Object)Items.f_41852_) ? opt : null;
            } else {
                object = null;
            }
        }
        if (object == null) {
            PossibleHeldItem $this$createStack_u24lambda_u241 = this;
            boolean bl = false;
            Cobblemon.INSTANCE.getLOGGER().error("Unable to find matching spawn held item for ID: " + $this$createStack_u24lambda_u241.item);
            return null;
        }
        Object item = object;
        ItemStack stack = new ItemStack((ItemLike)item, 1);
        if (this.nbt != null) {
            stack.m_41751_(this.nbt);
        }
        return stack;
    }
}

