/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\r\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u000e\u001a\u00020\u0002\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\b\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\b\u00a2\u0006\u0004\b#\u0010$J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0018\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bH\u00c6\u0003\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\bH\u00c6\u0003\u00a2\u0006\u0004\b\r\u0010\u000bJH\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u000e\u001a\u00020\u00022\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00052\u0010\b\u0002\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\b2\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\bH\u00c6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001a\u0010\u0016\u001a\u00020\u00152\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0019\u001a\u00020\u0018H\u00d6\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0010\u0010\u001b\u001a\u00020\u0005H\u00d6\u0001\u00a2\u0006\u0004\b\u001b\u0010\u0007R\u001f\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\b8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u001c\u001a\u0004\b\u001d\u0010\u000bR\u0017\u0010\u000e\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u001e\u001a\u0004\b\u001f\u0010\u0004R\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\b8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u001c\u001a\u0004\b \u0010\u000bR\u0019\u0010\u000f\u001a\u0004\u0018\u00010\u00058\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010!\u001a\u0004\b\"\u0010\u0007\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelOption;", "", "Lnet/minecraft/resources/ResourceLocation;", "component1", "()Lnet/minecraft/resources/ResourceLocation;", "", "component2", "()Ljava/lang/String;", "Lkotlin/Function0;", "Lorg/joml/Vector3f;", "component3", "()Lkotlin/jvm/functions/Function0;", "", "component4", "iconResource", "tooltipText", "colour", "onPress", "copy", "(Lnet/minecraft/resources/ResourceLocation;Ljava/lang/String;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelOption;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "Lkotlin/jvm/functions/Function0;", "getColour", "Lnet/minecraft/resources/ResourceLocation;", "getIconResource", "getOnPress", "Ljava/lang/String;", "getTooltipText", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Ljava/lang/String;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "common"})
public final class InteractWheelOption {
    @NotNull
    private final ResourceLocation iconResource;
    @Nullable
    private final String tooltipText;
    @NotNull
    private final Function0<Vector3f> colour;
    @NotNull
    private final Function0<Unit> onPress;

    public InteractWheelOption(@NotNull ResourceLocation iconResource, @Nullable String tooltipText, @NotNull Function0<? extends Vector3f> colour, @NotNull Function0<Unit> onPress) {
        Intrinsics.checkNotNullParameter((Object)iconResource, (String)"iconResource");
        Intrinsics.checkNotNullParameter(colour, (String)"colour");
        Intrinsics.checkNotNullParameter(onPress, (String)"onPress");
        this.iconResource = iconResource;
        this.tooltipText = tooltipText;
        this.colour = colour;
        this.onPress = onPress;
    }

    public /* synthetic */ InteractWheelOption(ResourceLocation resourceLocation, String string, Function0 function0, Function0 function02, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            function0 = 1.INSTANCE;
        }
        this(resourceLocation, string, (Function0<? extends Vector3f>)function0, (Function0<Unit>)function02);
    }

    @NotNull
    public final ResourceLocation getIconResource() {
        return this.iconResource;
    }

    @Nullable
    public final String getTooltipText() {
        return this.tooltipText;
    }

    @NotNull
    public final Function0<Vector3f> getColour() {
        return this.colour;
    }

    @NotNull
    public final Function0<Unit> getOnPress() {
        return this.onPress;
    }

    @NotNull
    public final ResourceLocation component1() {
        return this.iconResource;
    }

    @Nullable
    public final String component2() {
        return this.tooltipText;
    }

    @NotNull
    public final Function0<Vector3f> component3() {
        return this.colour;
    }

    @NotNull
    public final Function0<Unit> component4() {
        return this.onPress;
    }

    @NotNull
    public final InteractWheelOption copy(@NotNull ResourceLocation iconResource, @Nullable String tooltipText, @NotNull Function0<? extends Vector3f> colour, @NotNull Function0<Unit> onPress) {
        Intrinsics.checkNotNullParameter((Object)iconResource, (String)"iconResource");
        Intrinsics.checkNotNullParameter(colour, (String)"colour");
        Intrinsics.checkNotNullParameter(onPress, (String)"onPress");
        return new InteractWheelOption(iconResource, tooltipText, colour, onPress);
    }

    public static /* synthetic */ InteractWheelOption copy$default(InteractWheelOption interactWheelOption, ResourceLocation resourceLocation, String string, Function0 function0, Function0 function02, int n, Object object) {
        if ((n & 1) != 0) {
            resourceLocation = interactWheelOption.iconResource;
        }
        if ((n & 2) != 0) {
            string = interactWheelOption.tooltipText;
        }
        if ((n & 4) != 0) {
            function0 = interactWheelOption.colour;
        }
        if ((n & 8) != 0) {
            function02 = interactWheelOption.onPress;
        }
        return interactWheelOption.copy(resourceLocation, string, function0, function02);
    }

    @NotNull
    public String toString() {
        return "InteractWheelOption(iconResource=" + this.iconResource + ", tooltipText=" + this.tooltipText + ", colour=" + this.colour + ", onPress=" + this.onPress + ")";
    }

    public int hashCode() {
        int result = this.iconResource.hashCode();
        result = result * 31 + (this.tooltipText == null ? 0 : this.tooltipText.hashCode());
        result = result * 31 + this.colour.hashCode();
        result = result * 31 + this.onPress.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InteractWheelOption)) {
            return false;
        }
        InteractWheelOption interactWheelOption = (InteractWheelOption)other;
        if (!Intrinsics.areEqual((Object)this.iconResource, (Object)interactWheelOption.iconResource)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.tooltipText, (Object)interactWheelOption.tooltipText)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.colour, interactWheelOption.colour)) {
            return false;
        }
        return Intrinsics.areEqual(this.onPress, interactWheelOption.onPress);
    }
}

