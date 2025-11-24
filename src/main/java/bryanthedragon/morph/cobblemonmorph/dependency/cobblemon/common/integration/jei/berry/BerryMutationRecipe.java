/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.jei.berry;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.BerryItem;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0004J.\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0007\u001a\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0014\u001a\u00020\u0013H\u00d6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0016\u001a\u0004\b\u0017\u0010\u0004R\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0016\u001a\u0004\b\u0018\u0010\u0004R\u0017\u0010\b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0016\u001a\u0004\b\u0019\u0010\u0004\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/integration/jei/berry/BerryMutationRecipe;", "", "Lcom/cobblemon/mod/common/item/BerryItem;", "component1", "()Lcom/cobblemon/mod/common/item/BerryItem;", "component2", "component3", "berryOne", "berryTwo", "berryResult", "copy", "(Lcom/cobblemon/mod/common/item/BerryItem;Lcom/cobblemon/mod/common/item/BerryItem;Lcom/cobblemon/mod/common/item/BerryItem;)Lcom/cobblemon/mod/common/integration/jei/berry/BerryMutationRecipe;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/item/BerryItem;", "getBerryOne", "getBerryResult", "getBerryTwo", "<init>", "(Lcom/cobblemon/mod/common/item/BerryItem;Lcom/cobblemon/mod/common/item/BerryItem;Lcom/cobblemon/mod/common/item/BerryItem;)V", "common"})
public final class BerryMutationRecipe {
    @NotNull
    private final BerryItem berryOne;
    @NotNull
    private final BerryItem berryTwo;
    @NotNull
    private final BerryItem berryResult;

    public BerryMutationRecipe(@NotNull BerryItem berryOne, @NotNull BerryItem berryTwo, @NotNull BerryItem berryResult) {
        Intrinsics.checkNotNullParameter((Object)((Object)berryOne), (String)"berryOne");
        Intrinsics.checkNotNullParameter((Object)((Object)berryTwo), (String)"berryTwo");
        Intrinsics.checkNotNullParameter((Object)((Object)berryResult), (String)"berryResult");
        this.berryOne = berryOne;
        this.berryTwo = berryTwo;
        this.berryResult = berryResult;
    }

    @NotNull
    public final BerryItem getBerryOne() {
        return this.berryOne;
    }

    @NotNull
    public final BerryItem getBerryTwo() {
        return this.berryTwo;
    }

    @NotNull
    public final BerryItem getBerryResult() {
        return this.berryResult;
    }

    @NotNull
    public final BerryItem component1() {
        return this.berryOne;
    }

    @NotNull
    public final BerryItem component2() {
        return this.berryTwo;
    }

    @NotNull
    public final BerryItem component3() {
        return this.berryResult;
    }

    @NotNull
    public final BerryMutationRecipe copy(@NotNull BerryItem berryOne, @NotNull BerryItem berryTwo, @NotNull BerryItem berryResult) {
        Intrinsics.checkNotNullParameter((Object)((Object)berryOne), (String)"berryOne");
        Intrinsics.checkNotNullParameter((Object)((Object)berryTwo), (String)"berryTwo");
        Intrinsics.checkNotNullParameter((Object)((Object)berryResult), (String)"berryResult");
        return new BerryMutationRecipe(berryOne, berryTwo, berryResult);
    }

    public static /* synthetic */ BerryMutationRecipe copy$default(BerryMutationRecipe berryMutationRecipe, BerryItem berryItem, BerryItem berryItem2, BerryItem berryItem3, int n, Object object) {
        if ((n & 1) != 0) {
            berryItem = berryMutationRecipe.berryOne;
        }
        if ((n & 2) != 0) {
            berryItem2 = berryMutationRecipe.berryTwo;
        }
        if ((n & 4) != 0) {
            berryItem3 = berryMutationRecipe.berryResult;
        }
        return berryMutationRecipe.copy(berryItem, berryItem2, berryItem3);
    }

    @NotNull
    public String toString() {
        return "BerryMutationRecipe(berryOne=" + this.berryOne + ", berryTwo=" + this.berryTwo + ", berryResult=" + this.berryResult + ")";
    }

    public int hashCode() {
        int result = this.berryOne.hashCode();
        result = result * 31 + this.berryTwo.hashCode();
        result = result * 31 + this.berryResult.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BerryMutationRecipe)) {
            return false;
        }
        BerryMutationRecipe berryMutationRecipe = (BerryMutationRecipe)other;
        if (!Intrinsics.areEqual((Object)((Object)this.berryOne), (Object)((Object)berryMutationRecipe.berryOne))) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)((Object)this.berryTwo), (Object)((Object)berryMutationRecipe.berryTwo))) {
            return false;
        }
        return Intrinsics.areEqual((Object)((Object)this.berryResult), (Object)((Object)berryMutationRecipe.berryResult));
    }
}

