/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.MultiLineLabelK;
import java.util.List;
import java.util.stream.Collectors;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00182\u00020\u0001:\u0002\u0018\u0019B!\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\u0004\b\u0016\u0010\u0017J?\u0010\r\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\r\u0010\u000eR\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/api/gui/MultiLineLabelK;", "", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "x", "y", "ySpacing", "", "colour", "", "shadow", "", "renderLeftAligned", "(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;IZ)V", "", "Lcom/cobblemon/mod/common/api/gui/MultiLineLabelK$TextWithWidth;", "comps", "Ljava/util/List;", "Lnet/minecraft/resources/ResourceLocation;", "font", "Lnet/minecraft/resources/ResourceLocation;", "<init>", "(Ljava/util/List;Lnet/minecraft/resources/ResourceLocation;)V", "Companion", "TextWithWidth", "common"})
@SourceDebugExtension(value={"SMAP\nMultiLineLabelK.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MultiLineLabelK.kt\ncom/cobblemon/mod/common/api/gui/MultiLineLabelK\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,61:1\n1864#2,3:62\n*S KotlinDebug\n*F\n+ 1 MultiLineLabelK.kt\ncom/cobblemon/mod/common/api/gui/MultiLineLabelK\n*L\n48#1:62,3\n*E\n"})
public final class MultiLineLabelK {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final List<TextWithWidth> comps;
    @Nullable
    private final ResourceLocation font;
    private static final Font mcFont = Minecraft.m_91087_().f_91062_;

    public MultiLineLabelK(@NotNull List<TextWithWidth> comps, @Nullable ResourceLocation font) {
        Intrinsics.checkNotNullParameter(comps, (String)"comps");
        this.comps = comps;
        this.font = font;
    }

    public /* synthetic */ MultiLineLabelK(List list, ResourceLocation resourceLocation, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            resourceLocation = null;
        }
        this(list, resourceLocation);
    }

    /*
     * WARNING - void declaration
     */
    public final void renderLeftAligned(@NotNull GuiGraphics context, @NotNull Number x, @NotNull Number y, @NotNull Number ySpacing, int colour, boolean shadow) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)x, (String)"x");
        Intrinsics.checkNotNullParameter((Object)y, (String)"y");
        Intrinsics.checkNotNullParameter((Object)ySpacing, (String)"ySpacing");
        Iterable $this$forEachIndexed$iv = this.comps;
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void textWithWidth;
            int n;
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TextWithWidth textWithWidth2 = (TextWithWidth)item$iv;
            int index = n;
            boolean bl = false;
            float f = y.floatValue() + ySpacing.floatValue() * (float)index;
            String string = textWithWidth.getText().getString();
            ResourceLocation resourceLocation = this.font;
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"string");
            GuiUtilsKt.drawString(context, string, x, Float.valueOf(f), colour, shadow, resourceLocation);
        }
    }

    public static /* synthetic */ void renderLeftAligned$default(MultiLineLabelK multiLineLabelK, GuiGraphics guiGraphics, Number number, Number number2, Number number3, int n, boolean bl, int n2, Object object) {
        if ((n2 & 0x20) != 0) {
            bl = true;
        }
        multiLineLabelK.renderLeftAligned(guiGraphics, number, number2, number3, n, bl);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J%\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004\u00a2\u0006\u0004\b\b\u0010\tJ/\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\b\u0010\u000b\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0004\b\b\u0010\fR\u001c\u0010\u000f\u001a\n \u000e*\u0004\u0018\u00010\r0\r8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/gui/MultiLineLabelK$Companion;", "", "Lnet/minecraft/network/chat/Component;", "component", "", "width", "maxLines", "Lcom/cobblemon/mod/common/api/gui/MultiLineLabelK;", "create", "(Lnet/minecraft/network/chat/Component;Ljava/lang/Number;Ljava/lang/Number;)Lcom/cobblemon/mod/common/api/gui/MultiLineLabelK;", "Lnet/minecraft/resources/ResourceLocation;", "font", "(Lnet/minecraft/network/chat/Component;Ljava/lang/Number;Ljava/lang/Number;Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/api/gui/MultiLineLabelK;", "Lnet/minecraft/client/gui/Font;", "kotlin.jvm.PlatformType", "mcFont", "Lnet/minecraft/client/gui/Font;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final MultiLineLabelK create(@NotNull Component component, @NotNull Number width, @NotNull Number maxLines) {
            Intrinsics.checkNotNullParameter((Object)component, (String)"component");
            Intrinsics.checkNotNullParameter((Object)width, (String)"width");
            Intrinsics.checkNotNullParameter((Object)maxLines, (String)"maxLines");
            return this.create(component, width, maxLines, null);
        }

        @NotNull
        public final MultiLineLabelK create(@NotNull Component component, @NotNull Number width, @NotNull Number maxLines, @Nullable ResourceLocation font) {
            Intrinsics.checkNotNullParameter((Object)component, (String)"component");
            Intrinsics.checkNotNullParameter((Object)width, (String)"width");
            Intrinsics.checkNotNullParameter((Object)maxLines, (String)"maxLines");
            List<TextWithWidth> list = mcFont.m_92865_().m_92414_((FormattedText)component, width.intValue(), Style.f_131099_).stream().limit(maxLines.longValue()).map(arg_0 -> Companion.create$lambda$0(create.1.INSTANCE, arg_0)).collect(Collectors.toList());
            Intrinsics.checkNotNullExpressionValue(list, (String)"mcFont.textHandler.wrapL\u2026lect(Collectors.toList())");
            return new MultiLineLabelK(list, font);
        }

        private static final TextWithWidth create$lambda$0(Function1 $tmp0, Object p0) {
            Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
            return (TextWithWidth)$tmp0.invoke(p0);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/gui/MultiLineLabelK$TextWithWidth;", "", "Lnet/minecraft/network/chat/FormattedText;", "text", "Lnet/minecraft/network/chat/FormattedText;", "getText", "()Lnet/minecraft/network/chat/FormattedText;", "", "width", "I", "getWidth", "()I", "<init>", "(Lnet/minecraft/network/chat/FormattedText;I)V", "common"})
    public static final class TextWithWidth {
        @NotNull
        private final FormattedText text;
        private final int width;

        public TextWithWidth(@NotNull FormattedText text, int width) {
            Intrinsics.checkNotNullParameter((Object)text, (String)"text");
            this.text = text;
            this.width = width;
        }

        @NotNull
        public final FormattedText getText() {
            return this.text;
        }

        public final int getWidth() {
            return this.width;
        }
    }
}

