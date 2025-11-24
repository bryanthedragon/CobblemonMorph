/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractSelectionList$Entry
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.ObjectSelectionList
 *  net.minecraft.client.gui.components.ObjectSelectionList$Entry
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.PokemonGuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PasturePCGUIConfiguration;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PastureSlotIconButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PastureWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.PartySlotWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.UnpasturePokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.QuaternionUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u0000 22\b\u0012\u0004\u0012\u00020\u00020\u0001:\u000223B\u001f\u0012\u0006\u0010+\u001a\u00020\u0004\u0012\u0006\u0010.\u001a\u00020\u0004\u0012\u0006\u0010%\u001a\u00020$\u00a2\u0006\u0004\b0\u00101J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\n\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\f\u001a\u00020\u0004H\u0014\u00a2\u0006\u0004\b\f\u0010\u000bJ\u001d\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r\u00a2\u0006\u0004\b\u0011\u0010\u0012J'\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J7\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ/\u0010 \u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u001eH\u0016\u00a2\u0006\u0004\b \u0010!J\u001f\u0010\"\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rH\u0002\u00a2\u0006\u0004\b\"\u0010#R\u0017\u0010%\u001a\u00020$8\u0006\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(R\u0016\u0010)\u001a\u00020\u00108\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b)\u0010*R\u0017\u0010+\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b+\u0010,\u001a\u0004\b-\u0010\u000bR\u0017\u0010.\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b.\u0010,\u001a\u0004\b/\u0010\u000b\u00a8\u00064"}, d2={"Lcom/cobblemon/mod/common/client/gui/pasture/PasturePokemonScrollList;", "Lnet/minecraft/client/gui/components/ObjectSelectionList;", "Lcom/cobblemon/mod/common/client/gui/pasture/PasturePokemonScrollList$PastureSlot;", "entry", "", "addEntry", "(Lcom/cobblemon/mod/common/client/gui/pasture/PasturePokemonScrollList$PastureSlot;)I", "", "correctSize", "()V", "getRowWidth", "()I", "getScrollbarPositionX", "", "mouseX", "mouseY", "", "isHovered", "(DD)Z", "button", "mouseClicked", "(DDI)Z", "deltaX", "deltaY", "mouseDragged", "(DDIDD)Z", "removeEntry", "(Lcom/cobblemon/mod/common/client/gui/pasture/PasturePokemonScrollList$PastureSlot;)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "partialTicks", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "updateScrollingState", "(DD)V", "Lcom/cobblemon/mod/common/client/gui/pasture/PastureWidget;", "parent", "Lcom/cobblemon/mod/common/client/gui/pasture/PastureWidget;", "getParent", "()Lcom/cobblemon/mod/common/client/gui/pasture/PastureWidget;", "scrolling", "Z", "x", "I", "getX", "y", "getY", "<init>", "(IILcom/cobblemon/mod/common/client/gui/pasture/PastureWidget;)V", "Companion", "PastureSlot", "common"})
@SourceDebugExtension(value={"SMAP\nPasturePokemonScrollList.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PasturePokemonScrollList.kt\ncom/cobblemon/mod/common/client/gui/pasture/PasturePokemonScrollList\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,268:1\n1774#2,4:269\n1#3:273\n*S KotlinDebug\n*F\n+ 1 PasturePokemonScrollList.kt\ncom/cobblemon/mod/common/client/gui/pasture/PasturePokemonScrollList\n*L\n109#1:269,4\n*E\n"})
public final class PasturePokemonScrollList
extends ObjectSelectionList<PastureSlot> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int x;
    private final int y;
    @NotNull
    private final PastureWidget parent;
    private boolean scrolling;
    public static final int WIDTH = 70;
    public static final int HEIGHT = 120;
    public static final int SLOT_WIDTH = 62;
    public static final int SLOT_HEIGHT = 25;
    public static final int SLOT_SPACING = 3;
    public static final float SCALE = 0.5f;
    @NotNull
    private static final ResourceLocation scrollOverlayResource = MiscUtilsKt.cobblemonResource("textures/gui/pasture/pasture_scroll_overlay.png");
    @NotNull
    private static final ResourceLocation slotResource = MiscUtilsKt.cobblemonResource("textures/gui/pasture/pasture_slot.png");

    public PasturePokemonScrollList(int x, int y, @NotNull PastureWidget parent) {
        Intrinsics.checkNotNullParameter((Object)((Object)parent), (String)"parent");
        super(Minecraft.m_91087_(), 70, 120, 0, 120, 28);
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.correctSize();
        this.m_93496_(false);
        this.m_93488_(false);
        this.m_93471_(false);
        SettableObservable.subscribeIncludingCurrent$default(this.parent.getPasturePCGUIConfiguration().getPasturedPokemon(), null, (Function1)new Function1<List<? extends OpenPasturePacket.PasturePokemonDataDTO>, Unit>(){

            /*
             * WARNING - void declaration
             */
            public final void invoke(@NotNull List<OpenPasturePacket.PasturePokemonDataDTO> it) {
                Iterable $this$forEach$iv;
                void $this$filterTo$iv$iv;
                void $this$filterTo$iv$iv2;
                Intrinsics.checkNotNullParameter(it, (String)"it");
                List children = this.m_6702_();
                Iterable $this$filter$iv = it;
                boolean $i$f$filter = false;
                Iterable iterable = $this$filter$iv;
                Iterable<Object> destination$iv$iv = new ArrayList();
                boolean $i$f$filterTo = false;
                for (Object element$iv$iv : $this$filterTo$iv$iv2) {
                    boolean bl;
                    block10: {
                        OpenPasturePacket.PasturePokemonDataDTO pk = (OpenPasturePacket.PasturePokemonDataDTO)element$iv$iv;
                        boolean bl2 = false;
                        Intrinsics.checkNotNullExpressionValue((Object)children, (String)"children");
                        Iterable $this$none$iv = children;
                        boolean $i$f$none = false;
                        if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                            bl = true;
                        } else {
                            for (Object element$iv : $this$none$iv) {
                                PastureSlot it2 = (PastureSlot)((Object)element$iv);
                                boolean bl3 = false;
                                if (!Intrinsics.areEqual((Object)it2.getPokemon().getPokemonId(), (Object)pk.getPokemonId())) continue;
                                bl = false;
                                break block10;
                            }
                            bl = true;
                        }
                    }
                    if (!bl) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                List newEntries = (List)destination$iv$iv;
                List list = this.m_6702_();
                Intrinsics.checkNotNullExpressionValue((Object)list, (String)"children()");
                Iterable $this$filter$iv2 = list;
                boolean $i$f$filter2 = false;
                destination$iv$iv = $this$filter$iv2;
                Collection destination$iv$iv2 = new ArrayList();
                boolean $i$f$filterTo2 = false;
                for (Object element$iv$iv : $this$filterTo$iv$iv) {
                    boolean bl;
                    block11: {
                        PastureSlot pk = (PastureSlot)((Object)element$iv$iv);
                        boolean bl4 = false;
                        Iterable $this$none$iv = it;
                        boolean $i$f$none = false;
                        if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                            bl = true;
                        } else {
                            for (Object element$iv : $this$none$iv) {
                                OpenPasturePacket.PasturePokemonDataDTO it3 = (OpenPasturePacket.PasturePokemonDataDTO)element$iv;
                                boolean bl5 = false;
                                if (!Intrinsics.areEqual((Object)it3.getPokemonId(), (Object)pk.getPokemon().getPokemonId())) continue;
                                bl = false;
                                break block11;
                            }
                            bl = true;
                        }
                    }
                    if (!bl) continue;
                    destination$iv$iv2.add(element$iv$iv);
                }
                List removedEntries = (List)destination$iv$iv2;
                $this$filter$iv2 = removedEntries;
                PasturePokemonScrollList pasturePokemonScrollList = this;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    PastureSlot p0 = (PastureSlot)((Object)element$iv);
                    boolean bl = false;
                    pasturePokemonScrollList.removeEntry(p0);
                }
                $this$forEach$iv = newEntries;
                pasturePokemonScrollList = this;
                $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    OpenPasturePacket.PasturePokemonDataDTO it4 = (OpenPasturePacket.PasturePokemonDataDTO)element$iv;
                    boolean bl = false;
                    pasturePokemonScrollList.addEntry(new PastureSlot(it4, pasturePokemonScrollList.getParent()));
                }
            }
        }, 1, null);
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    @NotNull
    public final PastureWidget getParent() {
        return this.parent;
    }

    public int m_5759_() {
        return 62;
    }

    protected int m_5756_() {
        return this.f_93393_ + this.f_93388_ - 3;
    }

    public int addEntry(@NotNull PastureSlot entry) {
        Intrinsics.checkNotNullParameter((Object)((Object)entry), (String)"entry");
        return super.m_7085_((AbstractSelectionList.Entry)entry);
    }

    public boolean removeEntry(@NotNull PastureSlot entry) {
        Intrinsics.checkNotNullParameter((Object)((Object)entry), (String)"entry");
        return super.m_93502_((AbstractSelectionList.Entry)entry);
    }

    /*
     * WARNING - void declaration
     */
    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float partialTicks) {
        void it;
        int count$iv;
        int n;
        void $this$count$iv;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        this.correctSize();
        context.m_280588_(this.f_93393_, this.f_93390_ + 1, this.f_93393_ + this.f_93388_, this.f_93390_ + 1 + this.f_93389_);
        super.m_88315_(context, mouseX, mouseY, partialTicks);
        context.m_280618_();
        PoseStack poseStack = context.m_280168_();
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"context.matrices");
        GuiUtilsKt.blitk$default(poseStack, scrollOverlayResource, this.f_93393_, this.f_93390_ - 12, 131, 70, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        PasturePCGUIConfiguration config = this.parent.getPasturePCGUIConfiguration();
        ResourceLocation resourceLocation = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
        List list = this.m_6702_();
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"children()");
        Iterable iterable = list;
        ResourceLocation resourceLocation2 = resourceLocation;
        GuiGraphics guiGraphics = context;
        boolean $i$f$count = false;
        if ($this$count$iv instanceof Collection && ((Collection)$this$count$iv).isEmpty()) {
            n = 0;
        } else {
            count$iv = 0;
            for (Object element$iv : $this$count$iv) {
                PastureSlot it2 = (PastureSlot)((Object)element$iv);
                boolean bl = false;
                if (!it2.isOwned() || ++count$iv >= 0) continue;
                CollectionsKt.throwCountOverflow();
            }
            n = count$iv;
        }
        int n2 = n;
        Integer n3 = config.getPermissions().getMaxPokemon();
        count$iv = ((Number)n3).intValue();
        boolean bl = false;
        boolean bl2 = it >= 0;
        Integer n4 = bl2 ? n3 : null;
        RenderHelperKt.drawScaledText$default(guiGraphics, resourceLocation2, TextKt.bold(TextKt.text(n2 + "/" + (n4 != null ? n4.intValue() : config.getLimit()))), this.x + 35, this.y - 9, 0.0f, null, 0, 0, true, false, null, null, 7648, null);
    }

    public boolean m_6375_(double mouseX, double mouseY, int button) {
        this.updateScrollingState(mouseX, mouseY);
        if (this.scrolling) {
            this.m_7522_((GuiEventListener)this.m_93412_(mouseX, mouseY));
            this.m_7897_(true);
        }
        return super.m_6375_(mouseX, mouseY, button);
    }

    public boolean m_7979_(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.scrolling) {
            if (mouseY < (double)this.f_93390_) {
                this.m_93410_(0.0);
            } else if (mouseY > (double)this.f_93391_) {
                this.m_93410_(this.m_93518_());
            } else {
                this.m_93410_(this.m_93517_() + deltaY);
            }
        }
        return super.m_7979_(mouseX, mouseY, button, deltaX, deltaY);
    }

    private final void updateScrollingState(double mouseX, double mouseY) {
        this.scrolling = mouseX >= (double)this.m_5756_() && mouseX < (double)(this.m_5756_() + 3) && mouseY >= (double)this.f_93390_ && mouseY < (double)this.f_93391_;
    }

    private final void correctSize() {
        this.m_93437_(70, 120, this.y + 1, this.y + 1 + 118);
        this.m_93507_(this.x);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isHovered(double mouseX, double mouseY) {
        float f = this.x;
        float f2 = (float)this.x + (float)70;
        float f3 = (float)mouseX;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        boolean bl = true;
        if (!bl) return false;
        f = this.y;
        f2 = (float)this.y + (float)120;
        f3 = (float)mouseY;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        return true;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004R\u0014\u0010\n\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0004R\u0014\u0010\u000b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\u0004R\u0014\u0010\r\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u000e\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/client/gui/pasture/PasturePokemonScrollList$Companion;", "", "", "HEIGHT", "I", "", "SCALE", "F", "SLOT_HEIGHT", "SLOT_SPACING", "SLOT_WIDTH", "WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "scrollOverlayResource", "Lnet/minecraft/resources/ResourceLocation;", "slotResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\u0012\u0006\u0010)\u001a\u00020(\u0012\u0006\u0010&\u001a\u00020%\u00a2\u0006\u0004\b-\u0010.J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\b\u001a\u00020\u0002\u00a2\u0006\u0004\b\b\u0010\u0004J'\u0010\u000e\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ_\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cR\u0017\u0010\u001e\u001a\u00020\u001d8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!R\u0014\u0010#\u001a\u00020\"8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b#\u0010$R\u0014\u0010&\u001a\u00020%8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b&\u0010'R\u0017\u0010)\u001a\u00020(8\u0006\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,\u00a8\u0006/"}, d2={"Lcom/cobblemon/mod/common/client/gui/pasture/PasturePokemonScrollList$PastureSlot;", "Lnet/minecraft/client/gui/widget/AlwaysSelectedEntryListWidget$Entry;", "", "canUnpasture", "()Z", "Lnet/minecraft/network/chat/Component;", "getNarration", "()Lnet/minecraft/network/chat/Component;", "isOwned", "", "mouseX", "mouseY", "", "delta", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "index", "rowTop", "rowLeft", "rowWidth", "rowHeight", "isHovered", "", "partialTicks", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIIIIIIZF)V", "Lnet/minecraft/client/Minecraft;", "client", "Lnet/minecraft/client/Minecraft;", "getClient", "()Lnet/minecraft/client/Minecraft;", "Lcom/cobblemon/mod/common/client/gui/pasture/PastureSlotIconButton;", "moveButton", "Lcom/cobblemon/mod/common/client/gui/pasture/PastureSlotIconButton;", "Lcom/cobblemon/mod/common/client/gui/pasture/PastureWidget;", "parent", "Lcom/cobblemon/mod/common/client/gui/pasture/PastureWidget;", "Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket$PasturePokemonDataDTO;", "pokemon", "Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket$PasturePokemonDataDTO;", "getPokemon", "()Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket$PasturePokemonDataDTO;", "<init>", "(Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket$PasturePokemonDataDTO;Lcom/cobblemon/mod/common/client/gui/pasture/PastureWidget;)V", "common"})
    public static final class PastureSlot
    extends ObjectSelectionList.Entry<PastureSlot> {
        @NotNull
        private final OpenPasturePacket.PasturePokemonDataDTO pokemon;
        @NotNull
        private final PastureWidget parent;
        @NotNull
        private final Minecraft client;
        @NotNull
        private final PastureSlotIconButton moveButton;

        public PastureSlot(@NotNull OpenPasturePacket.PasturePokemonDataDTO pokemon, @NotNull PastureWidget parent) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)((Object)parent), (String)"parent");
            this.pokemon = pokemon;
            this.parent = parent;
            Minecraft minecraft = Minecraft.m_91087_();
            Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance()");
            this.client = minecraft;
            this.moveButton = new PastureSlotIconButton(0, 0, arg_0 -> PastureSlot.moveButton$lambda$0(this, arg_0));
        }

        @NotNull
        public final OpenPasturePacket.PasturePokemonDataDTO getPokemon() {
            return this.pokemon;
        }

        @NotNull
        public final Minecraft getClient() {
            return this.client;
        }

        public final boolean isOwned() {
            LocalPlayer localPlayer = this.client.f_91074_;
            return Intrinsics.areEqual((Object)(localPlayer != null ? localPlayer.m_20148_() : null), (Object)this.pokemon.getPlayerId());
        }

        public final boolean canUnpasture() {
            return this.isOwned() || this.parent.getPasturePCGUIConfiguration().getPermissions().getCanUnpastureOthers();
        }

        @NotNull
        public Component m_142172_() {
            return this.pokemon.getDisplayName();
        }

        public void m_6311_(@NotNull GuiGraphics context, int index, int rowTop, int rowLeft, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean isHovered, float partialTicks) {
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            int x = rowLeft - 4;
            int y = rowTop;
            PoseStack matrixStack = context.m_280168_();
            Intrinsics.checkNotNullExpressionValue((Object)matrixStack, (String)"matrixStack");
            GuiUtilsKt.blitk$default(matrixStack, slotResource, x, y, 25, rowWidth, null, isHovered ? 25 : 0, null, 50, null, null, null, null, null, false, 0.0f, 130368, null);
            matrixStack.m_85836_();
            matrixStack.m_85837_((double)(x + 11) + 12.5, (double)y - 5.0, 0.0);
            matrixStack.m_85841_(2.5f, 2.5f, 1.0f);
            PokemonGuiUtilsKt.drawProfilePokemon(this.pokemon.getSpecies(), this.pokemon.getAspects(), matrixStack, QuaternionUtilsKt.fromEulerXYZDegrees(new Quaternionf(), new Vector3f(13.0f, 35.0f, 0.0f)), null, partialTicks, 4.5f);
            matrixStack.m_85849_();
            ItemStack heldItem2 = this.pokemon.getHeldItem();
            if (!heldItem2.m_41619_()) {
                RenderHelperKt.renderScaledGuiItemIcon$default(heldItem2, (double)x + 23.5, (double)y + 9.0, 0.5, 0.0f, matrixStack, 16, null);
            }
            Object[] objectArray = new Object[]{this.pokemon.getLevel()};
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.lv.number", objectArray);
            int n = x + 46;
            int n2 = y + 13;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.lv.number\", pokemon.level)");
            RenderHelperKt.drawScaledText$default(context, null, mutableComponent, n, n2, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
            mutableComponent = this.pokemon.getDisplayName().m_6881_();
            n = x + 11;
            n2 = y + 20;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"copy()");
            RenderHelperKt.drawScaledText$default(context, null, mutableComponent, n, n2, 0.5f, null, 90, 0, false, false, null, null, 8002, null);
            if (this.pokemon.getAspects().contains("male") || this.pokemon.getAspects().contains("female")) {
                GuiUtilsKt.blitk$default(matrixStack, this.pokemon.getAspects().contains("male") ? PartySlotWidget.Companion.getGenderIconMale() : PartySlotWidget.Companion.getGenderIconFemale(), ((double)x + 56.5) / (double)0.5f, Float.valueOf((float)(y + 20) / 0.5f), 7, 5, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
            }
            if (this.canUnpasture()) {
                this.moveButton.setPos(x + 2, y + 9);
                this.moveButton.m_88315_(context, mouseX, mouseY, partialTicks);
            }
        }

        public boolean m_6375_(double mouseX, double mouseY, int delta) {
            if (this.moveButton.isHovered(mouseX, mouseY) && this.canUnpasture()) {
                this.moveButton.m_5691_();
                return true;
            }
            return false;
        }

        private static final void moveButton$lambda$0(PastureSlot this$0, Button it) {
            Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
            this$0.parent.getStorageWidget().getPcGui().playSound(CobblemonSounds.PC_CLICK);
            new UnpasturePokemonPacket(this$0.parent.getPasturePCGUIConfiguration().getPastureId(), this$0.pokemon.getPokemonId()).sendToServer();
        }
    }
}

