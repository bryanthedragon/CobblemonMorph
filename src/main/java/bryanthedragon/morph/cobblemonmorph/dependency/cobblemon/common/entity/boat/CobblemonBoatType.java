/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.properties.WoodType
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0001\u0018\u0000 \u001b2\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002:\u0001\u001bB\u0011\b\u0002\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u0011\u0010\t\u001a\u00020\u00068F\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\r\u001a\u00020\n8F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u000f\u001a\u00020\n8F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u0017\u0010\u0011\u001a\u00020\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0018\u001a\u00020\u00158F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017j\u0002\b\u001c\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;", "", "Lnet/minecraft/util/StringRepresentable;", "", "asString", "()Ljava/lang/String;", "Lnet/minecraft/world/level/block/Block;", "getBaseBlock", "()Lnet/minecraft/world/level/block/Block;", "baseBlock", "Lnet/minecraft/world/item/Item;", "getBoatItem", "()Lnet/minecraft/world/item/Item;", "boatItem", "getChestBoatItem", "chestBoatItem", "", "mountedOffset", "D", "getMountedOffset", "()D", "Lnet/minecraft/world/level/block/state/properties/WoodType;", "getWoodType", "()Lnet/minecraft/world/level/block/state/properties/WoodType;", "woodType", "<init>", "(Ljava/lang/String;ID)V", "Companion", "APRICORN", "common"})
public final class CobblemonBoatType
extends Enum<CobblemonBoatType>
implements StringRepresentable {
    @NotNull
    public static final Companion Companion;
    private final double mountedOffset;
    public static final /* enum */ CobblemonBoatType APRICORN;
    private static final /* synthetic */ CobblemonBoatType[] $VALUES;

    private CobblemonBoatType(double mountedOffset) {
        this.mountedOffset = mountedOffset;
    }

    public final double getMountedOffset() {
        return this.mountedOffset;
    }

    @NotNull
    public final Item getBoatItem() {
        if (WhenMappings.$EnumSwitchMapping$0[this.ordinal()] != 1) {
            throw new NoWhenBranchMatchedException();
        }
        return CobblemonItems.APRICORN_BOAT;
    }

    @NotNull
    public final Item getChestBoatItem() {
        if (WhenMappings.$EnumSwitchMapping$0[this.ordinal()] != 1) {
            throw new NoWhenBranchMatchedException();
        }
        return CobblemonItems.APRICORN_CHEST_BOAT;
    }

    @NotNull
    public final Block getBaseBlock() {
        if (WhenMappings.$EnumSwitchMapping$0[this.ordinal()] != 1) {
            throw new NoWhenBranchMatchedException();
        }
        return CobblemonBlocks.APRICORN_PLANKS;
    }

    @NotNull
    public final WoodType getWoodType() {
        if (WhenMappings.$EnumSwitchMapping$0[this.ordinal()] != 1) {
            throw new NoWhenBranchMatchedException();
        }
        WoodType woodType = CobblemonBlocks.INSTANCE.getAPRICORN_WOOD_TYPE();
        Intrinsics.checkNotNullExpressionValue((Object)woodType, (String)"CobblemonBlocks.APRICORN_WOOD_TYPE");
        return woodType;
    }

    @NotNull
    public String m_7912_() {
        String string = this.name().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        return string;
    }

    public static CobblemonBoatType[] values() {
        return (CobblemonBoatType[])$VALUES.clone();
    }

    public static CobblemonBoatType valueOf(String value2) {
        return Enum.valueOf(CobblemonBoatType.class, value2);
    }

    static {
        APRICORN = new CobblemonBoatType(-0.1);
        $VALUES = cobblemonBoatTypeArray = new CobblemonBoatType[]{CobblemonBoatType.APRICORN};
        Companion = new Companion(null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0000\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType$Companion;", "", "", "ordinal", "Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;", "ofOrdinal$common", "(I)Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;", "ofOrdinal", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final CobblemonBoatType ofOrdinal$common(int ordinal) {
            return CobblemonBoatType.values()[ordinal];
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[CobblemonBoatType.values().length];
            try {
                nArray[CobblemonBoatType.APRICORN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

