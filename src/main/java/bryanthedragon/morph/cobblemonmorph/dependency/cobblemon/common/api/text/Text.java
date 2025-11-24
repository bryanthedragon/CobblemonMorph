/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.Regex
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.Text;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u000b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\t\b\u0000\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\n \b*\u0004\u0018\u00010\u00070\u0007H\u0002\u00a2\u0006\u0004\b\t\u0010\nJ!\u0010\r\u001a\u00020\u00022\u0012\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u000b\"\u00020\u0001\u00a2\u0006\u0004\b\r\u0010\u000eR\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00028\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0011\u001a\n \b*\u0004\u0018\u00010\u00070\u00078\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/text/Text;", "", "Lnet/minecraft/network/chat/MutableComponent;", "component", "", "addComponent", "(Lnet/minecraft/network/chat/MutableComponent;)V", "Lnet/minecraft/network/chat/Style;", "kotlin.jvm.PlatformType", "getBlankStyle", "()Lnet/minecraft/network/chat/Style;", "", "components", "parse", "([Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;", "head", "Lnet/minecraft/network/chat/MutableComponent;", "style", "Lnet/minecraft/network/chat/Style;", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nText.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Text.kt\ncom/cobblemon/mod/common/api/text/Text\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,183:1\n13579#2:184\n13580#2:186\n1#3:185\n*S KotlinDebug\n*F\n+ 1 Text.kt\ncom/cobblemon/mod/common/api/text/Text\n*L\n41#1:184\n41#1:186\n*E\n"})
public final class Text {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private Style style = Style.f_131099_;
    @Nullable
    private MutableComponent head;

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final MutableComponent parse(Object ... components) {
        Intrinsics.checkNotNullParameter((Object)components, (String)"components");
        Object[] $this$forEach$iv = components;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            void it;
            MutableComponent mutableComponent;
            Object element$iv;
            Object it2 = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            Object object = it2;
            if (object instanceof MutableComponent) {
                this.addComponent((MutableComponent)it2);
                this.style = this.getBlankStyle();
                continue;
            }
            if (object instanceof ClickEvent) {
                this.style = this.style.m_131142_((ClickEvent)it2);
                continue;
            }
            if (object instanceof HoverEvent) {
                this.style = this.style.m_131144_((HoverEvent)it2);
                continue;
            }
            if (object instanceof ChatFormatting) {
                if (((ChatFormatting)it2).m_126664_()) {
                    this.style = this.style.m_131140_((ChatFormatting)it2);
                    continue;
                }
                if (it2 == ChatFormatting.UNDERLINE || Intrinsics.areEqual((Object)it2, (Object)TextKt.getUNDERLINED())) {
                    this.style = this.style.m_131162_(Boolean.valueOf(true));
                    continue;
                }
                if (it2 == ChatFormatting.BOLD || Intrinsics.areEqual((Object)it2, (Object)TextKt.getBOLD())) {
                    this.style = this.style.m_131136_(Boolean.valueOf(true));
                    continue;
                }
                if (it2 == ChatFormatting.ITALIC || Intrinsics.areEqual((Object)it2, (Object)TextKt.getITALIC())) {
                    this.style = this.style.m_131155_(Boolean.valueOf(true));
                    continue;
                }
                if (it2 == ChatFormatting.OBFUSCATED || Intrinsics.areEqual((Object)it2, (Object)TextKt.getOBFUSCATED())) {
                    this.style = this.style.m_178524_(Boolean.valueOf(true));
                    continue;
                }
                if (it2 != ChatFormatting.RESET && !Intrinsics.areEqual((Object)it2, (Object)TextKt.getRESET())) continue;
                this.style = Style.f_131099_;
                continue;
            }
            MutableComponent mutableComponent2 = mutableComponent = Companion.resolveComponent$common(it2);
            Text text = this;
            boolean bl2 = false;
            it.m_6270_(this.style.m_131146_(it.m_7383_()));
            text.addComponent(mutableComponent);
        }
        MutableComponent mutableComponent = this.head;
        if (mutableComponent == null) {
            MutableComponent mutableComponent3 = Component.m_237113_((String)"Empty!");
            mutableComponent = mutableComponent3;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"literal(\"Empty!\")");
        }
        return mutableComponent;
    }

    /*
     * WARNING - void declaration
     */
    private final void addComponent(MutableComponent component) {
        block1: {
            void it;
            MutableComponent mutableComponent;
            block0: {
                if (this.head != null) break block0;
                this.head = component;
                component.m_6270_(this.style.m_131146_(component.m_7383_()));
                this.style = this.getBlankStyle();
                break block1;
            }
            MutableComponent mutableComponent2 = this.head;
            if (mutableComponent2 == null) break block1;
            MutableComponent mutableComponent3 = mutableComponent = component;
            MutableComponent mutableComponent4 = mutableComponent2;
            boolean bl = false;
            it.m_6270_(this.style.m_131146_(it.m_7383_()));
            TextKt.add(mutableComponent4, (Component)mutableComponent);
        }
    }

    private final Style getBlankStyle() {
        return Style.f_131099_.m_131136_(Boolean.valueOf(false)).m_131155_(Boolean.valueOf(false)).m_131162_(Boolean.valueOf(false)).m_178524_(Boolean.valueOf(false)).m_131140_(ChatFormatting.WHITE).m_131142_(null).m_131144_(null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0001H\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/text/Text$Companion;", "", "text", "Lnet/minecraft/network/chat/MutableComponent;", "resolveComponent$common", "(Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;", "resolveComponent", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final MutableComponent resolveComponent$common(@NotNull Object text) {
            Intrinsics.checkNotNullParameter((Object)text, (String)"text");
            CharSequence charSequence = text.toString();
            Regex regex = new Regex("&[A-Fa-f\\dk-oK-oRr]");
            Function1 function1 = resolveComponent.1.INSTANCE;
            MutableComponent mutableComponent = Component.m_237115_((String)regex.replace(charSequence, function1));
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"translatable(text.toStri\u2026t.value.substring(1)}\" })");
            return mutableComponent;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

