/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Multimap
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Renderable
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.InteractWheelButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.InteractWheelGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.InteractWheelOption;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.Orientation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.startselection.widgets.preview.ArrowButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 ;2\u00020\u0001:\u0001;B#\u0012\u0012\u00105\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000404\u0012\u0006\u00108\u001a\u000207\u00a2\u0006\u0004\b9\u0010:J!\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ/\u0010\u000e\u001a\u00028\u0000\"\u0016\b\u0000\u0010\f*\u0004\u0018\u00010\t*\u0004\u0018\u00010\n*\u0004\u0018\u00010\u000b2\u0006\u0010\r\u001a\u00028\u0000H\u0014\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0010\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011J#\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00130\u0012H\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u000f\u0010\u0018\u001a\u00020\u0006H\u0014\u00a2\u0006\u0004\b\u0018\u0010\u0011J\u001f\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0019H\u0002\u00a2\u0006\u0004\b\u001d\u0010\u001eJ'\u0010 \u001a\u00020\u001c2\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b \u0010!J/\u0010&\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\"2\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u00132\u0006\u0010%\u001a\u00020$H\u0016\u00a2\u0006\u0004\b&\u0010'J\u0017\u0010)\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u0013H\u0002\u00a2\u0006\u0004\b)\u0010*J\u000f\u0010+\u001a\u00020\u001cH\u0016\u00a2\u0006\u0004\b+\u0010,R\u001a\u0010/\u001a\b\u0012\u0004\u0012\u00020.0-8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b/\u00100R\u0016\u00101\u001a\u00020\u00138\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b1\u00102R\u0016\u00103\u001a\u00020\u00138\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b3\u00102R \u00105\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0004048\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b5\u00106\u00a8\u0006<"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelGUI;", "Lnet/minecraft/client/gui/screens/Screen;", "Lcom/cobblemon/mod/common/client/gui/interact/wheel/Orientation;", "orientation", "Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelOption;", "option", "", "addButton", "(Lcom/cobblemon/mod/common/client/gui/interact/wheel/Orientation;Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelOption;)V", "Lnet/minecraft/client/gui/components/events/GuiEventListener;", "Lnet/minecraft/client/gui/components/Renderable;", "Lnet/minecraft/client/gui/narration/NarratableEntry;", "T", "drawableElement", "addDrawableChild", "(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", "calculateMaxPage", "()V", "Lkotlin/Pair;", "", "getButtonPosition", "(Lcom/cobblemon/mod/common/client/gui/interact/wheel/Orientation;)Lkotlin/Pair;", "getDimensions", "()Lkotlin/Pair;", "init", "", "mouseX", "mouseY", "", "isMouseInCenter", "(DD)Z", "button", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "page", "setPage", "(I)V", "shouldPause", "()Z", "", "Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelButton;", "buttons", "Ljava/util/List;", "currentPage", "I", "maxPage", "Lcom/google/common/collect/Multimap;", "options", "Lcom/google/common/collect/Multimap;", "Lnet/minecraft/network/chat/Component;", "title", "<init>", "(Lcom/google/common/collect/Multimap;Lnet/minecraft/network/chat/Component;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nInteractWheelGUI.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InteractWheelGUI.kt\ncom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelGUI\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,158:1\n1855#2,2:159\n13579#3,2:161\n*S KotlinDebug\n*F\n+ 1 InteractWheelGUI.kt\ncom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelGUI\n*L\n83#1:159,2\n86#1:161,2\n*E\n"})
public final class InteractWheelGUI
extends Screen {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Multimap<Orientation, InteractWheelOption> options;
    @NotNull
    private final List<InteractWheelButton> buttons;
    private int maxPage;
    private int currentPage;
    public static final int SIZE = 138;
    public static final int OPTION_SIZE = 69;
    @NotNull
    private static final ResourceLocation backgroundResource = MiscUtils.cobblemonResource("textures/gui/interact/interact_base.png");
    @NotNull
    private static final Map<Orientation, ResourceLocation> buttonResources;

    public InteractWheelGUI(@NotNull Multimap<Orientation, InteractWheelOption> options, @NotNull Component title) {
        Intrinsics.checkNotNullParameter(options, (String)"options");
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        super(title);
        this.options = options;
        this.buttons = new ArrayList();
        this.maxPage = 1;
    }

    protected void m_7856_() {
        this.calculateMaxPage();
        Collection collection = this.options.get((Object)Orientation.TOP_LEFT);
        Intrinsics.checkNotNullExpressionValue((Object)collection, (String)"options[Orientation.TOP_LEFT]");
        this.addButton(Orientation.TOP_LEFT, (InteractWheelOption)CollectionsKt.getOrNull((List)CollectionsKt.toList((Iterable)collection), (int)0));
        Collection collection2 = this.options.get((Object)Orientation.TOP_RIGHT);
        Intrinsics.checkNotNullExpressionValue((Object)collection2, (String)"options[Orientation.TOP_RIGHT]");
        this.addButton(Orientation.TOP_RIGHT, (InteractWheelOption)CollectionsKt.getOrNull((List)CollectionsKt.toList((Iterable)collection2), (int)0));
        Collection collection3 = this.options.get((Object)Orientation.BOTTOM_LEFT);
        Intrinsics.checkNotNullExpressionValue((Object)collection3, (String)"options[Orientation.BOTTOM_LEFT]");
        this.addButton(Orientation.BOTTOM_LEFT, (InteractWheelOption)CollectionsKt.getOrNull((List)CollectionsKt.toList((Iterable)collection3), (int)0));
        Collection collection4 = this.options.get((Object)Orientation.BOTTOM_RIGHT);
        Intrinsics.checkNotNullExpressionValue((Object)collection4, (String)"options[Orientation.BOTTOM_RIGHT]");
        this.addButton(Orientation.BOTTOM_RIGHT, (InteractWheelOption)CollectionsKt.getOrNull((List)CollectionsKt.toList((Iterable)collection4), (int)0));
        if (this.maxPage > 1) {
            this.m_142416_((GuiEventListener)new ArrowButton(this.f_96543_ / 3 - 12, this.f_96544_ / 2 - 7, 9, 14, 0, 0, 0, false, null, arg_0 -> InteractWheelGUI.init$lambda$0(this, arg_0), 368, null));
            this.m_142416_((GuiEventListener)new ArrowButton(this.f_96543_ / 3 * 2, this.f_96544_ / 2 - 7, 9, 14, 0, 0, 0, true, null, arg_0 -> InteractWheelGUI.init$lambda$1(this, arg_0), 368, null));
        }
    }

    private final void calculateMaxPage() {
        this.maxPage = Math.max(Math.max(this.options.get((Object)Orientation.TOP_LEFT).size(), this.options.get((Object)Orientation.TOP_RIGHT).size()), Math.max(this.options.get((Object)Orientation.BOTTOM_LEFT).size(), this.options.get((Object)Orientation.BOTTOM_RIGHT).size()));
    }

    private final void setPage(int page) {
        Orientation[] orientations;
        this.currentPage = page;
        Iterable $this$forEach$iv = this.buttons;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            InteractWheelButton it = (InteractWheelButton)((Object)element$iv);
            boolean bl = false;
            this.m_169411_((GuiEventListener)it);
        }
        this.buttons.clear();
        Orientation[] $this$forEach$iv2 = orientations = Orientation.values();
        boolean $i$f$forEach2 = false;
        int n = $this$forEach$iv2.length;
        for (int i = 0; i < n; ++i) {
            Orientation element$iv;
            Orientation orientation = element$iv = $this$forEach$iv2[i];
            boolean bl = false;
            Collection collection = this.options.get((Object)orientation);
            Intrinsics.checkNotNullExpressionValue((Object)collection, (String)"options[orientation]");
            InteractWheelOption option = (InteractWheelOption)CollectionsKt.getOrNull((List)CollectionsKt.toList((Iterable)collection), (int)page);
            this.addButton(orientation, option);
        }
    }

    private final void addButton(Orientation orientation, InteractWheelOption option) {
        Pair<Integer, Integer> pair = this.getButtonPosition(orientation);
        int x = ((Number)pair.component1()).intValue();
        int y = ((Number)pair.component2()).intValue();
        Function0 function0 = option;
        Object object = function0 != null ? function0.getIconResource() : null;
        ResourceLocation resourceLocation = buttonResources.get((Object)orientation);
        Intrinsics.checkNotNull((Object)resourceLocation);
        Function0 function02 = option;
        String string = function02 != null ? function02.getTooltipText() : null;
        boolean bl = option != null;
        Function0 function03 = option;
        if (function03 == null || (function03 = function03.getColour()) == null) {
            function03 = addButton.1.INSTANCE;
        }
        this.m_142416_((GuiEventListener)new InteractWheelButton((ResourceLocation)object, resourceLocation, string, x, y, bl, (Function0<? extends Vector3f>)function03, arg_0 -> InteractWheelGUI.addButton$lambda$4((InteractWheelOption)option, arg_0)));
    }

    protected <T extends GuiEventListener & Renderable> T m_142416_(T drawableElement) {
        if (drawableElement instanceof InteractWheelButton) {
            this.buttons.add((InteractWheelButton)drawableElement);
        }
        return (T)super.m_142416_(drawableElement);
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Pair<Integer, Integer> pair = this.getDimensions();
        int x = ((Number)pair.component1()).intValue();
        int y = ((Number)pair.component2()).intValue();
        PoseStack poseStack = context.m_280168_();
        ResourceLocation resourceLocation = backgroundResource;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, x, y, 138, 138, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        super.m_88315_(context, mouseX, mouseY, delta);
    }

    private final Pair<Integer, Integer> getDimensions() {
        return new Pair((Object)((this.f_96543_ - 138) / 2), (Object)((this.f_96544_ - 138) / 2));
    }

    private final Pair<Integer, Integer> getButtonPosition(Orientation orientation) {
        Pair<Integer, Integer> pair = this.getDimensions();
        int x = ((Number)pair.component1()).intValue();
        int y = ((Number)pair.component2()).intValue();
        return switch (WhenMappings.$EnumSwitchMapping$0[orientation.ordinal()]) {
            case 1 -> new Pair((Object)x, (Object)y);
            case 2 -> new Pair((Object)(x + 69), (Object)y);
            case 3 -> new Pair((Object)x, (Object)(y + 69));
            case 4 -> new Pair((Object)(x + 69), (Object)(y + 69));
            default -> throw new NoWhenBranchMatchedException();
        };
    }

    public boolean m_7043_() {
        return false;
    }

    public boolean m_6375_(double mouseX, double mouseY, int button) {
        if (this.isMouseInCenter(mouseX, mouseY)) {
            return false;
        }
        return super.m_6375_(mouseX, mouseY, button);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean isMouseInCenter(double mouseX, double mouseY) {
        float x = (this.f_96543_ - 138) / 2 + 44;
        float xMax = x + (float)50;
        float y = (this.f_96544_ - 138) / 2 + 44;
        float yMax = y + (float)50;
        float f = (float)mouseX;
        if (!(x <= f)) return false;
        if (!(f <= xMax)) return false;
        boolean bl = true;
        if (!bl) return false;
        f = (float)mouseY;
        if (!(y <= f)) return false;
        if (!(f <= yMax)) return false;
        return true;
    }

    private static final void init$lambda$0(InteractWheelGUI this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        this$0.setPage(this$0.currentPage == 0 ? this$0.maxPage - 1 : this$0.currentPage - 1);
    }

    private static final void init$lambda$1(InteractWheelGUI this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        this$0.setPage((this$0.currentPage + 1) % Math.max(1, this$0.maxPage));
    }

    private static final void addButton$lambda$4(InteractWheelOption $option, Button it) {
        block0: {
            Function0<Unit> function0 = $option;
            if (function0 == null || (function0 = function0.getOnPress()) == null) break block0;
            function0.invoke();
        }
    }

    static {
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)((Object)Orientation.TOP_LEFT), (Object)MiscUtils.cobblemonResource("textures/gui/interact/button_left_top.png")), TuplesKt.to((Object)((Object)Orientation.TOP_RIGHT), (Object)MiscUtils.cobblemonResource("textures/gui/interact/button_right_top.png")), TuplesKt.to((Object)((Object)Orientation.BOTTOM_LEFT), (Object)MiscUtils.cobblemonResource("textures/gui/interact/button_left_bottom.png")), TuplesKt.to((Object)((Object)Orientation.BOTTOM_RIGHT), (Object)MiscUtils.cobblemonResource("textures/gui/interact/button_right_bottom.png"))};
        buttonResources = MapsKt.mutableMapOf((Pair[])pairArray);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\bR \u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00060\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelGUI$Companion;", "", "", "OPTION_SIZE", "I", "SIZE", "Lnet/minecraft/resources/ResourceLocation;", "backgroundResource", "Lnet/minecraft/resources/ResourceLocation;", "", "Lcom/cobblemon/mod/common/client/gui/interact/wheel/Orientation;", "buttonResources", "Ljava/util/Map;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Orientation.values().length];
            try {
                nArray[Orientation.TOP_LEFT.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Orientation.TOP_RIGHT.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Orientation.BOTTOM_LEFT.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Orientation.BOTTOM_RIGHT.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

