/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.toasts.Toast
 *  net.minecraft.client.gui.components.toasts.Toast$Visibility
 *  net.minecraft.client.gui.components.toasts.ToastComponent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.TextColor
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.toast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.toast.ToastPacket;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\bH\u0010\u0012B?\u0012\u0006\u0010*\u001a\u00020)\u0012\u0006\u0010#\u001a\u00020\"\u0012\u0006\u0010E\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u0012\u0006\u0010\u001c\u001a\u00020\u001b\u0012\u0006\u00109\u001a\u00020.\u0012\u0006\u0010?\u001a\u00020>\u00a2\u0006\u0004\bH\u0010IJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u000eH\u0000\u00a2\u0006\u0004\b\u0011\u0010\u0012R\"\u0010\u0015\u001a\u00020\u00148\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\"\u0010\u001c\u001a\u00020\u001b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\"\u0010#\u001a\u00020\"8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u0017\u0010*\u001a\u00020)8\u0006\u00a2\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-R\u0016\u0010/\u001a\u00020.8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b/\u00100R\u0016\u00101\u001a\u00020\u00068\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b1\u00102R\"\u00103\u001a\u00020\b8\u0000@\u0000X\u0080\u000e\u00a2\u0006\u0012\n\u0004\b3\u00104\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\"\u00109\u001a\u00020.8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b9\u00100\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\"\u0010?\u001a\u00020>8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b?\u0010@\u001a\u0004\bA\u0010B\"\u0004\bC\u0010DR\"\u0010E\u001a\u00020\u00148\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bE\u0010\u0016\u001a\u0004\bF\u0010\u0018\"\u0004\bG\u0010\u001a\u00a8\u0006J"}, d2={"Lcom/cobblemon/mod/common/client/gui/toast/CobblemonToast;", "Lnet/minecraft/client/gui/components/toasts/Toast;", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "Lnet/minecraft/client/gui/components/toasts/ToastComponent;", "manager", "", "startTime", "Lnet/minecraft/client/toast/Toast$Visibility;", "draw", "(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/components/toasts/ToastComponent;J)Lnet/minecraft/client/gui/components/toasts/Toast$Visibility;", "", "hasProgressBar", "()Z", "Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket;", "packet", "", "updateFrom$common", "(Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket;)V", "updateFrom", "Lnet/minecraft/network/chat/Component;", "description", "Lnet/minecraft/network/chat/Component;", "getDescription", "()Lnet/minecraft/network/chat/Component;", "setDescription", "(Lnet/minecraft/network/chat/Component;)V", "Lnet/minecraft/resources/ResourceLocation;", "frameTexture", "Lnet/minecraft/resources/ResourceLocation;", "getFrameTexture", "()Lnet/minecraft/resources/ResourceLocation;", "setFrameTexture", "(Lnet/minecraft/resources/ResourceLocation;)V", "Lnet/minecraft/world/item/ItemStack;", "icon", "Lnet/minecraft/world/item/ItemStack;", "getIcon", "()Lnet/minecraft/world/item/ItemStack;", "setIcon", "(Lnet/minecraft/world/item/ItemStack;)V", "Ljava/util/UUID;", "id", "Ljava/util/UUID;", "getId", "()Ljava/util/UUID;", "", "lastProgress", "F", "lastTime", "J", "nextVisibility", "Lnet/minecraft/client/gui/components/toasts/Toast$Visibility;", "getNextVisibility$common", "()Lnet/minecraft/client/gui/components/toasts/Toast$Visibility;", "setNextVisibility$common", "(Lnet/minecraft/client/gui/components/toasts/Toast$Visibility;)V", "progress", "getProgress", "()F", "setProgress", "(F)V", "", "progressColor", "I", "getProgressColor", "()I", "setProgressColor", "(I)V", "title", "getTitle", "setTitle", "<init>", "(Ljava/util/UUID;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;Lnet/minecraft/resources/ResourceLocation;FI)V", "common"})
@OnlyIn(value=Dist.CLIENT)
public final class CobblemonToast
implements Toast {
    @NotNull
    private final UUID id;
    @NotNull
    private ItemStack icon;
    @NotNull
    private Component title;
    @NotNull
    private Component description;
    @NotNull
    private ResourceLocation frameTexture;
    private float progress;
    private int progressColor;
    private float lastProgress;
    private long lastTime;
    @NotNull
    private Toast.Visibility nextVisibility;

    public CobblemonToast(@NotNull UUID id, @NotNull ItemStack icon, @NotNull Component title, @NotNull Component description, @NotNull ResourceLocation frameTexture, float progress2, int progressColor) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)icon, (String)"icon");
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter((Object)description, (String)"description");
        Intrinsics.checkNotNullParameter((Object)frameTexture, (String)"frameTexture");
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.frameTexture = frameTexture;
        this.progress = progress2;
        this.progressColor = progressColor;
        this.nextVisibility = Toast.Visibility.SHOW;
    }

    @NotNull
    public final UUID getId() {
        return this.id;
    }

    @NotNull
    public final ItemStack getIcon() {
        return this.icon;
    }

    public final void setIcon(@NotNull ItemStack itemStack) {
        Intrinsics.checkNotNullParameter((Object)itemStack, (String)"<set-?>");
        this.icon = itemStack;
    }

    @NotNull
    public final Component getTitle() {
        return this.title;
    }

    public final void setTitle(@NotNull Component component) {
        Intrinsics.checkNotNullParameter((Object)component, (String)"<set-?>");
        this.title = component;
    }

    @NotNull
    public final Component getDescription() {
        return this.description;
    }

    public final void setDescription(@NotNull Component component) {
        Intrinsics.checkNotNullParameter((Object)component, (String)"<set-?>");
        this.description = component;
    }

    @NotNull
    public final ResourceLocation getFrameTexture() {
        return this.frameTexture;
    }

    public final void setFrameTexture(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.frameTexture = resourceLocation;
    }

    public final float getProgress() {
        return this.progress;
    }

    public final void setProgress(float f) {
        this.progress = f;
    }

    public final int getProgressColor() {
        return this.progressColor;
    }

    public final void setProgressColor(int n) {
        this.progressColor = n;
    }

    public CobblemonToast(@NotNull ToastPacket packet) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        this(packet.getUuid(), packet.getIcon(), packet.getTitle(), packet.getDescription(), packet.getFrameTexture(), packet.getProgress(), packet.getProgressColor());
    }

    @NotNull
    public final Toast.Visibility getNextVisibility$common() {
        return this.nextVisibility;
    }

    public final void setNextVisibility$common(@NotNull Toast.Visibility visibility) {
        Intrinsics.checkNotNullParameter((Object)visibility, (String)"<set-?>");
        this.nextVisibility = visibility;
    }

    @NotNull
    public Toast.Visibility m_7172_(@NotNull GuiGraphics context, @NotNull ToastComponent manager, long startTime) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
        context.m_280218_(this.frameTexture, 0, 0, 0, 32, this.m_7828_(), this.m_94899_());
        Font textRenderer = manager.m_94929_().f_91062_;
        TextColor textColor = this.title.m_7383_().m_131135_();
        context.m_280614_(textRenderer, this.title, 30, 7, textColor != null ? textColor.m_131265_() : -1, false);
        TextColor textColor2 = this.description.m_7383_().m_131135_();
        context.m_280614_(textRenderer, this.description, 30, 18, textColor2 != null ? textColor2.m_131265_() : -1, false);
        context.m_280203_(this.icon, 8, 8);
        if (this.hasProgressBar()) {
            context.m_280509_(3, 28, 157, 29, -1);
            float f = Mth.m_144920_((float)this.lastProgress, (float)this.progress, (float)((float)(startTime - this.lastTime) / 100.0f));
            context.m_280509_(3, 28, (int)(3.0f + 154.0f * f), 29, this.progressColor);
            this.lastProgress = f;
        }
        this.lastTime = startTime;
        return this.nextVisibility;
    }

    public final void updateFrom$common(@NotNull ToastPacket packet) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        this.icon = packet.getIcon();
        this.title = packet.getTitle();
        this.description = packet.getDescription();
        this.frameTexture = packet.getFrameTexture();
        this.progress = packet.getProgress();
        this.progressColor = packet.getProgressColor();
        this.lastProgress = Math.min(this.progress, this.lastProgress);
        this.nextVisibility = switch (WhenMappings.$EnumSwitchMapping$0[packet.getBehaviour().ordinal()]) {
            case 1 -> Toast.Visibility.SHOW;
            case 2 -> Toast.Visibility.HIDE;
            default -> throw new NoWhenBranchMatchedException();
        };
    }

    private final boolean hasProgressBar() {
        float f = this.progress;
        return 0.0f <= f ? f <= 1.0f : false;
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[ToastPacket.Behaviour.values().length];
            try {
                nArray[ToastPacket.Behaviour.SHOW_OR_UPDATE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[ToastPacket.Behaviour.HIDE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

