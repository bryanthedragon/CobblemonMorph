/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.SummaryButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.PartySlotWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u0000 H2\u00020\u0001:\u0001HB7\u0012\u0006\u0010D\u001a\u00020\n\u0012\u0006\u0010E\u001a\u00020\n\u0012\u0006\u0010&\u001a\u00020\u0002\u0012\u0006\u00104\u001a\u000203\u0012\u000e\u0010+\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010*0)\u00a2\u0006\u0004\bF\u0010GJ\u0017\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001f\u0010\u000b\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001d\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007\u00a2\u0006\u0004\b\r\u0010\u000eJ'\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J'\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0013J\u0015\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0015\u00a2\u0006\u0004\b\u0017\u0010\u0018J/\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001bH\u0014\u00a2\u0006\u0004\b\u001d\u0010\u001eR$\u0010 \u001a\u0004\u0018\u00010\u001f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u0017\u0010&\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b&\u0010(R\u001c\u0010+\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010*0)8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b+\u0010,R\u0014\u0010-\u001a\u00020\n8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b-\u0010.R$\u00101\u001a\u0012\u0012\u0004\u0012\u00020\u001f0/j\b\u0012\u0004\u0012\u00020\u001f`08\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b1\u00102R\u0017\u00104\u001a\u0002038\u0006\u00a2\u0006\f\n\u0004\b4\u00105\u001a\u0004\b6\u00107R\u0014\u00109\u001a\u0002088\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b9\u0010:R\"\u0010;\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b;\u0010'\u001a\u0004\b<\u0010(\"\u0004\b=\u0010\u0006R$\u0010>\u001a\u0004\u0018\u00010\n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b>\u0010?\u001a\u0004\b@\u0010A\"\u0004\bB\u0010C\u00a8\u0006I"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/PartyWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "", "boolean", "", "enableSwap", "(Z)V", "", "mouseX", "mouseY", "", "getIndexFromPos", "(DD)I", "isWithinScreen", "(DD)Z", "pMouseX", "pMouseY", "pButton", "mouseClicked", "(DDI)Z", "mouseReleased", "Lnet/minecraft/sounds/SoundEvent;", "soundEvent", "playSound", "(Lnet/minecraft/sounds/SoundEvent;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "pPartialTicks", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/PartySlotWidget;", "draggedSlot", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/PartySlotWidget;", "getDraggedSlot", "()Lcom/cobblemon/mod/common/client/gui/summary/widgets/PartySlotWidget;", "setDraggedSlot", "(Lcom/cobblemon/mod/common/client/gui/summary/widgets/PartySlotWidget;)V", "isParty", "Z", "()Z", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "partyList", "Ljava/util/List;", "partySize", "I", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "partySlots", "Ljava/util/ArrayList;", "Lcom/cobblemon/mod/common/client/gui/summary/Summary;", "summary", "Lcom/cobblemon/mod/common/client/gui/summary/Summary;", "getSummary", "()Lcom/cobblemon/mod/common/client/gui/summary/Summary;", "Lcom/cobblemon/mod/common/client/gui/summary/SummaryButton;", "swapButton", "Lcom/cobblemon/mod/common/client/gui/summary/SummaryButton;", "swapEnabled", "getSwapEnabled", "setSwapEnabled", "swapSource", "Ljava/lang/Integer;", "getSwapSource", "()Ljava/lang/Integer;", "setSwapSource", "(Ljava/lang/Integer;)V", "pX", "pY", "<init>", "(IIZLcom/cobblemon/mod/common/client/gui/summary/Summary;Ljava/util/List;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPartyWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartyWidget.kt\ncom/cobblemon/mod/common/client/gui/summary/widgets/PartyWidget\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,222:1\n1864#2,3:223\n1855#2,2:226\n*S KotlinDebug\n*F\n+ 1 PartyWidget.kt\ncom/cobblemon/mod/common/client/gui/summary/widgets/PartyWidget\n*L\n74#1:223,3\n137#1:226,2\n*E\n"})
public final class PartyWidget
extends SoundlessWidget {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final boolean isParty;
    @NotNull
    private final Summary summary;
    @NotNull
    private final List<Pokemon> partyList;
    private boolean swapEnabled;
    @Nullable
    private Integer swapSource;
    @Nullable
    private PartySlotWidget draggedSlot;
    private final int partySize;
    @NotNull
    private final ArrayList<PartySlotWidget> partySlots;
    @NotNull
    private final SummaryButton swapButton;
    public static final int WIDTH = 114;
    public static final int HEIGHT = 113;
    private static final float SCALE = 0.5f;
    @NotNull
    private static final ResourceLocation backgroundResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_party_background.png");
    @NotNull
    private static final ResourceLocation swapButtonResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_party_swap.png");
    @NotNull
    private static final ResourceLocation swapButtonActiveResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_party_swap_active.png");
    @NotNull
    private static final ResourceLocation swapButtonIconResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_party_swap_icon.png");

    /*
     * WARNING - void declaration
     */
    public PartyWidget(int pX, int pY, boolean isParty, @NotNull Summary summary, @NotNull List<? extends Pokemon> partyList) {
        Intrinsics.checkNotNullParameter((Object)summary, (String)"summary");
        Intrinsics.checkNotNullParameter(partyList, (String)"partyList");
        MutableComponent mutableComponent = Component.m_237113_((String)"PartyOverlay");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"PartyOverlay\")");
        super(pX, pY, 114, 113, (Component)mutableComponent);
        this.isParty = isParty;
        this.summary = summary;
        this.partyList = partyList;
        this.partySize = this.partyList.size();
        this.partySlots = new ArrayList();
        float f = (float)this.m_252754_() + 80.0f;
        float f2 = (float)this.m_252907_() - 9.0f;
        ResourceLocation resourceLocation = swapButtonResource;
        ResourceLocation resourceLocation2 = swapButtonActiveResource;
        Button.OnPress onPress = arg_0 -> PartyWidget.swapButton$lambda$0(this, arg_0);
        this.swapButton = new SummaryButton(f, f2, 26, 14, onPress, null, resourceLocation, resourceLocation2, null, null, false, false, false, false, 0.0f, 32544, null);
        if (this.partySize > 6 || this.partySize < 1) {
            throw new InvalidParameterException("Invalid party size");
        }
        Iterable $this$forEachIndexed$iv = this.partyList;
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void pokemon;
            PartySlotWidget partySlotWidget;
            int n;
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Pokemon pokemon2 = (Pokemon)item$iv;
            int index = n;
            boolean bl = false;
            int x = this.m_252754_() + 6;
            int y = this.m_252907_() + 7;
            if (index > 0) {
                boolean isEven = index % 2 == 0;
                int offsetIndex = (index - (isEven ? 0 : 1)) / 2;
                int offsetX = isEven ? 0 : 51;
                int offsetY = isEven ? 0 : 8;
                x += offsetX;
                y += 32 * offsetIndex + offsetY;
            }
            PartySlotWidget widget = partySlotWidget = new PartySlotWidget(x, y, this, this.summary, (Pokemon)pokemon, index, this.isParty);
            boolean bl2 = false;
            this.addWidget((GuiEventListener)widget);
            this.partySlots.add(widget);
        }
    }

    public final boolean isParty() {
        return this.isParty;
    }

    @NotNull
    public final Summary getSummary() {
        return this.summary;
    }

    public final boolean getSwapEnabled() {
        return this.swapEnabled;
    }

    public final void setSwapEnabled(boolean bl) {
        this.swapEnabled = bl;
    }

    @Nullable
    public final Integer getSwapSource() {
        return this.swapSource;
    }

    public final void setSwapSource(@Nullable Integer n) {
        this.swapSource = n;
    }

    @Nullable
    public final PartySlotWidget getDraggedSlot() {
        return this.draggedSlot;
    }

    public final void setDraggedSlot(@Nullable PartySlotWidget partySlotWidget) {
        this.draggedSlot = partySlotWidget;
    }

    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack matrices = context.m_280168_();
        ResourceLocation resourceLocation = backgroundResource;
        int n = this.m_252754_();
        int n2 = this.m_252907_();
        int n3 = this.f_93618_;
        int n4 = this.f_93619_;
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, resourceLocation, n, n2, n4, n3, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        ResourceLocation resourceLocation2 = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.party", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.party\")");
        RenderHelperKt.drawScaledText$default(context, resourceLocation2, TextKt.bold(mutableComponent), (double)this.m_252754_() + 32.5, (double)this.m_252907_() - 14.5, 0.0f, null, 0, 0, true, true, null, null, 6624, null);
        this.swapButton.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
        resourceLocation = swapButtonIconResource;
        float f = (float)(this.m_252754_() + 90) / 0.5f;
        float f2 = (float)(this.m_252907_() - 6) / 0.5f;
        GuiUtilsKt.blitk$default(matrices, resourceLocation, Float.valueOf(f), Float.valueOf(f2), 17, 12, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        Iterable $this$forEach$iv = this.partySlots;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PartySlotWidget it = (PartySlotWidget)((Object)element$iv);
            boolean bl = false;
            it.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
        }
        if (this.draggedSlot != null) {
            matrices.m_85836_();
            matrices.m_85837_(0.0, 0.0, 500.0);
            PartySlotWidget partySlotWidget = this.draggedSlot;
            Intrinsics.checkNotNull((Object)((Object)partySlotWidget));
            partySlotWidget.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
            matrices.m_85849_();
        }
    }

    @Override
    public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
        Pokemon sourcePokemon;
        int index;
        if (this.swapButton.m_274382_()) {
            this.swapButton.m_5691_();
            this.swapButton.setActive(this.swapEnabled);
        }
        if (this.swapEnabled && (index = this.getIndexFromPos(pMouseX, pMouseY)) > -1 && (sourcePokemon = this.partyList.get(index)) != null) {
            this.swapSource = index;
            this.draggedSlot = new PartySlotWidget(pMouseX - (double)23, pMouseY - (double)13, this, this.summary, sourcePokemon, -1, this.isParty);
            this.playSound(CobblemonSounds.PC_GRAB);
        }
        return super.m_6375_(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean m_6348_(double pMouseX, double pMouseY, int pButton) {
        if (this.swapEnabled && this.swapSource != null) {
            int index = this.getIndexFromPos(pMouseX, pMouseY);
            if (index > -1) {
                Integer n = this.swapSource;
                if (n == null || index != n) {
                    Integer n2 = this.swapSource;
                    Intrinsics.checkNotNull((Object)n2);
                    this.summary.swapPartySlot(n2, index);
                }
            }
            this.swapSource = null;
            this.draggedSlot = null;
            this.playSound(CobblemonSounds.PC_DROP);
        }
        return super.m_6348_(pMouseX, pMouseY, pButton);
    }

    public final void enableSwap(boolean bl) {
        this.swapEnabled = bl;
        this.swapButton.setActive(bl);
    }

    public static /* synthetic */ void enableSwap$default(PartyWidget partyWidget, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = true;
        }
        partyWidget.enableSwap(bl);
    }

    private final int getIndexFromPos(double mouseX, double mouseY) {
        for (int index = 0; index < 6; ++index) {
            int posX = this.m_252754_() + 6;
            int posY = this.m_252907_() + 7;
            if (index > 0) {
                boolean isEven = index % 2 == 0;
                int offsetIndex = (index - (isEven ? 0 : 1)) / 2;
                int offsetX = isEven ? 0 : 51;
                int offsetY = isEven ? 0 : 8;
                posX += offsetX;
                posY += 32 * offsetIndex + offsetY;
            }
            int n = posX + 46;
            int n2 = (int)mouseX;
            boolean bl = posX <= n2 ? n2 <= n : false;
            if (!bl) continue;
            n = posY + 27;
            n2 = (int)mouseY;
            boolean bl2 = posY <= n2 ? n2 <= n : false;
            if (!bl2) continue;
            return index;
        }
        return -1;
    }

    public final void playSound(@NotNull SoundEvent soundEvent) {
        Intrinsics.checkNotNullParameter((Object)soundEvent, (String)"soundEvent");
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)soundEvent, (float)1.0f));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isWithinScreen(double mouseX, double mouseY) {
        int n = this.m_252754_();
        int n2 = this.m_252754_() + 114;
        int n3 = (int)mouseX;
        if (n > n3) return false;
        if (n3 > n2) return false;
        boolean bl = true;
        if (!bl) return false;
        n = this.m_252907_();
        n2 = this.m_252907_() + 113;
        n3 = (int)mouseY;
        if (n > n3) return false;
        if (n3 > n2) return false;
        return true;
    }

    private static final void swapButton$lambda$0(PartyWidget this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        boolean bl = this$0.swapEnabled = !this$0.swapEnabled;
        if (!this$0.swapEnabled) {
            this$0.swapSource = null;
            this$0.draggedSlot = null;
        }
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)CobblemonSounds.GUI_CLICK, (float)1.0f));
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\n\u001a\u00020\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\u000bR\u0014\u0010\r\u001a\u00020\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000bR\u0014\u0010\u000e\u001a\u00020\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u000b\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/PartyWidget$Companion;", "", "", "HEIGHT", "I", "", "SCALE", "F", "WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "backgroundResource", "Lnet/minecraft/resources/ResourceLocation;", "swapButtonActiveResource", "swapButtonIconResource", "swapButtonResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

