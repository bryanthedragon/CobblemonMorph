/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.PokemonGuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.PartyWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.QuaternionUtilsKt;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0002\b\u0006\u0018\u0000 82\u00020\u0001:\u00018BA\u0012\u0006\u00104\u001a\u000203\u0012\u0006\u00105\u001a\u000203\u0012\u0006\u0010-\u001a\u00020,\u0012\u0006\u00101\u001a\u000200\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010(\u001a\u00020\n\u0012\u0006\u0010*\u001a\u00020\u0007\u00a2\u0006\u0004\b6\u00107J\u0019\u0010\u0005\u001a\u00020\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J)\u0010\u000b\u001a\u00020\n2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00022\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\nH\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J'\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0012J7\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J'\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u001b\u0010\u0012J/\u0010!\u001a\u00020 2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\u001eH\u0014\u00a2\u0006\u0004\b!\u0010\"J\u001f\u0010#\u001a\u00020 2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rH\u0002\u00a2\u0006\u0004\b#\u0010$J\u0017\u0010&\u001a\u00020 2\u0006\u0010%\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020\n8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b(\u0010)R\u0014\u0010*\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b*\u0010+R\u0014\u0010-\u001a\u00020,8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b-\u0010.R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0003\u0010/R\u0014\u00101\u001a\u0002008\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b1\u00102\u00a8\u00069"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/PartySlotWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/resources/ResourceLocation;", "getSlotTexture", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lnet/minecraft/resources/ResourceLocation;", "", "isHovered", "isSelected", "", "getSlotVOffset", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;ZZ)I", "", "mouseX", "mouseY", "button", "isValidClick", "(DDI)Z", "mouseClicked", "f", "g", "mouseDragged", "(DDIDD)Z", "pMouseX", "pMouseY", "pButton", "mouseReleased", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "repositionSlot", "(DD)V", "boolean", "toggleDrag", "(Z)V", "index", "I", "isClientPartyMember", "Z", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/PartyWidget;", "partyWidget", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/PartyWidget;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/client/gui/summary/Summary;", "summary", "Lcom/cobblemon/mod/common/client/gui/summary/Summary;", "", "pX", "pY", "<init>", "(Ljava/lang/Number;Ljava/lang/Number;Lcom/cobblemon/mod/common/client/gui/summary/widgets/PartyWidget;Lcom/cobblemon/mod/common/client/gui/summary/Summary;Lcom/cobblemon/mod/common/pokemon/Pokemon;IZ)V", "Companion", "common"})
public final class PartySlotWidget
extends SoundlessWidget {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final PartyWidget partyWidget;
    @NotNull
    private final Summary summary;
    @Nullable
    private final Pokemon pokemon;
    private final int index;
    private final boolean isClientPartyMember;
    public static final int WIDTH = 46;
    public static final int HEIGHT = 27;
    private static final int PORTRAIT_DIAMETER = 25;
    @NotNull
    private static final ResourceLocation slotResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_party_slot.png");
    @NotNull
    private static final ResourceLocation slotFaintedResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_party_slot_fainted.png");
    @NotNull
    private static final ResourceLocation slotEmptyResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_party_slot_empty.png");
    @NotNull
    private static final ResourceLocation genderIconMale = MiscUtils.cobblemonResource("textures/gui/party/party_gender_male.png");
    @NotNull
    private static final ResourceLocation genderIconFemale = MiscUtils.cobblemonResource("textures/gui/party/party_gender_female.png");

    public PartySlotWidget(@NotNull Number pX, @NotNull Number pY, @NotNull PartyWidget partyWidget, @NotNull Summary summary, @Nullable Pokemon pokemon, int index, boolean isClientPartyMember) {
        Intrinsics.checkNotNullParameter((Object)pX, (String)"pX");
        Intrinsics.checkNotNullParameter((Object)pY, (String)"pY");
        Intrinsics.checkNotNullParameter((Object)((Object)partyWidget), (String)"partyWidget");
        Intrinsics.checkNotNullParameter((Object)summary, (String)"summary");
        int n = pX.intValue();
        int n2 = pY.intValue();
        MutableComponent mutableComponent = Component.m_237113_((String)"PartyMember");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"PartyMember\")");
        super(n, n2, 46, 27, (Component)mutableComponent);
        this.partyWidget = partyWidget;
        this.summary = summary;
        this.pokemon = pokemon;
        this.index = index;
        this.isClientPartyMember = isClientPartyMember;
    }

    private final ResourceLocation getSlotTexture(Pokemon pokemon) {
        if (pokemon != null) {
            if (pokemon.isFainted()) {
                return slotFaintedResource;
            }
            return slotResource;
        }
        return slotEmptyResource;
    }

    private final int getSlotVOffset(Pokemon pokemon, boolean isHovered, boolean isSelected) {
        if (isHovered || isSelected) {
            if (pokemon == null) {
                if (this.partyWidget.getSwapEnabled()) {
                    return this.f_93619_;
                }
                return 0;
            }
            return this.f_93619_;
        }
        return 0;
    }

    /*
     * Unable to fully structure code
     */
    protected void m_87963_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        this.f_93622_ = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
        matrices = context.m_280168_();
        if (!this.partyWidget.getSwapEnabled()) ** GOTO lbl-1000
        v0 = this.partyWidget.getSwapSource();
        var7_6 = this.index;
        if (v0 != null && v0 == var7_6) {
            v1 = true;
        } else lbl-1000:
        // 2 sources

        {
            v1 = false;
        }
        isDraggedSlot = v1;
        v2 = slotPokemon = isDraggedSlot != false ? null : this.pokemon;
        if (!this.isClientPartyMember) ** GOTO lbl-1000
        v3 = slotPokemon;
        if (Intrinsics.areEqual((Object)this.summary.getSelectedPokemon$common().getUuid(), (Object)(v3 != null ? v3.getUuid() : null))) {
            v4 = true;
        } else lbl-1000:
        // 2 sources

        {
            v4 = false;
        }
        isSelected = v4;
        var9_10 = this.getSlotTexture(slotPokemon);
        var10_12 = this.m_252754_();
        var11_14 = this.m_252907_();
        var12_16 = this.f_93618_;
        var13_17 = this.f_93619_;
        var14_19 = this.getSlotVOffset(slotPokemon, this.m_274382_(), isSelected);
        var15_22 = this.f_93619_ * 2;
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, var9_10, var10_12, var11_14, var13_17, var12_16, null, var14_19, null, var15_22, null, null, null, null, null, false, 0.0f, 130368, null);
        if (slotPokemon != null) {
            halfScale = 0.5f;
            stateIcon = slotPokemon.getState().getIcon(slotPokemon);
            if (stateIcon != null) {
                GuiUtilsKt.blitk$default(matrices, stateIcon, ((double)this.m_252754_() + 24.5) / (double)halfScale, Float.valueOf((float)(this.m_252907_() + 3) / halfScale), 17, 24, null, null, null, null, null, null, null, null, null, false, halfScale, 65472, null);
            }
            ballIcon = MiscUtils.cobblemonResource("textures/gui/ball/" + slotPokemon.getCaughtBall().getName().m_135815_() + ".png");
            ballHeight = 22;
            GuiUtilsKt.blitk$default(matrices, ballIcon, Float.valueOf((float)(this.m_252754_() - 2) / halfScale), Float.valueOf((float)(this.m_252907_() - 3) / halfScale), ballHeight, 18, null, null, null, ballHeight * 2, null, null, null, null, null, false, halfScale, 64960, null);
            v5 = slotPokemon.getStatus();
            v6 = status = v5 != null ? v5.getStatus() : null;
            if (!slotPokemon.isFainted() && status != null) {
                statusName = status.getShowdownName();
                GuiUtilsKt.blitk$default(matrices, MiscUtils.cobblemonResource("textures/gui/party/status_" + statusName + ".png"), this.m_252754_() + 42, this.m_252907_() + 5, 14, 4, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
            }
            hpRatio = (float)slotPokemon.getCurrentHealth() / (float)slotPokemon.getHp();
            barWidthMax = 37;
            barWidth = hpRatio * (float)barWidthMax;
            var17_24 = RenderHelperKt.getDepletableRedGreen$default(hpRatio, 0.0f, 0.0f, 6, null);
            red = ((Number)var17_24.component1()).floatValue();
            green = ((Number)var17_24.component2()).floatValue();
            var20_27 = CobblemonResources.INSTANCE.getWHITE();
            var21_28 = this.m_252754_() + 4;
            var22_31 = this.m_252907_() + 25;
            var23_34 = barWidth / hpRatio;
            var24_35 = (float)barWidthMax - barWidth;
            var25_36 = red * 0.8f;
            var26_37 = green * 0.8f;
            GuiUtilsKt.blitk$default(matrices, var20_27, var21_28, var22_31, 1, Float.valueOf(barWidth), Float.valueOf(var24_35), null, Float.valueOf(var23_34), null, null, Float.valueOf(var25_36), Float.valueOf(var26_37), Float.valueOf(0.27f), null, false, 0.0f, 116352, null);
            matrices.m_85836_();
            matrices.m_85837_((double)this.m_252754_() + 12.5, (double)this.m_252907_() - 3.0, 0.0);
            matrices.m_85841_(2.5f, 2.5f, 1.0f);
            var20_27 = slotPokemon.getSpecies().getResourceIdentifier();
            var21_29 = CollectionsKt.toSet((Iterable)slotPokemon.getAspects());
            var22_32 = QuaternionUtilsKt.fromEulerXYZDegrees(new Quaternionf(), new Vector3f(13.0f, 35.0f, 0.0f));
            PokemonGuiUtilsKt.drawProfilePokemon(var20_27, (Set<String>)var21_29, matrices, var22_32, null, delta, 4.5f);
            matrices.m_85849_();
            RenderHelperKt.drawScaledText$default(context, null, slotPokemon.getDisplayName(), this.m_252754_() + 4, this.m_252907_() + 20, halfScale, null, 0, 0, false, false, null, null, 8130, null);
            if (slotPokemon.getGender() != Gender.GENDERLESS) {
                GuiUtilsKt.blitk$default(matrices, slotPokemon.getGender() == Gender.MALE ? PartySlotWidget.genderIconMale : PartySlotWidget.genderIconFemale, Float.valueOf((float)(this.m_252754_() + 40) / halfScale), Float.valueOf((float)(this.m_252907_() + 20) / halfScale), 7, 5, null, null, null, null, null, null, null, null, null, false, halfScale, 65472, null);
            }
            var21_29 = new Object[]{slotPokemon.getLevel()};
            var20_27 = LocalizationUtilsKt.lang("ui.lv.number", var21_29);
            var21_30 = this.m_252754_() + 31;
            var22_33 = this.m_252907_() + 13;
            Intrinsics.checkNotNullExpressionValue((Object)var20_27, (String)"lang(\"ui.lv.number\", slotPokemon.level)");
            RenderHelperKt.drawScaledText$default(context, null, (MutableComponent)var20_27, var21_30, var22_33, halfScale, null, 0, 0, true, true, null, null, 6594, null);
            heldItem = slotPokemon.heldItemNoCopy$common();
            if (!heldItem.m_41619_()) {
                RenderHelperKt.renderScaledGuiItemIcon$default(heldItem, (double)this.m_252754_() + 14.0, (double)this.m_252907_() + 9.5, 0.5, 0.0f, matrices, 16, null);
            }
        }
    }

    @Override
    public boolean m_6375_(double mouseX, double mouseY, int button) {
        if (this.isValidClick(mouseX, mouseY, button)) {
            if (this.partyWidget.getSwapEnabled()) {
                this.toggleDrag(true);
            } else if (this.index > -1) {
                Pokemon pokemon = this.pokemon;
                if (!Intrinsics.areEqual((Object)this.summary.getSelectedPokemon$common().getUuid(), (Object)(pokemon != null ? pokemon.getUuid() : null))) {
                    this.summary.switchSelection(this.index);
                    if (this.pokemon != null) {
                        this.partyWidget.playSound(CobblemonSounds.GUI_CLICK);
                    }
                    return true;
                }
            }
        }
        return super.m_6375_(mouseX, mouseY, button);
    }

    @Override
    public boolean m_6348_(double pMouseX, double pMouseY, int pButton) {
        if (this.partyWidget.getSwapEnabled()) {
            this.toggleDrag(false);
        }
        return super.m_6348_(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean m_7979_(double mouseX, double mouseY, int button, double f, double g) {
        if (this.partyWidget.getSwapEnabled() && this.partyWidget.isWithinScreen(mouseX, mouseY) && this.index < 0) {
            this.repositionSlot(mouseX, mouseY);
        } else {
            if (this.partyWidget.getSwapSource() != null) {
                this.partyWidget.playSound(CobblemonSounds.PC_DROP);
            }
            this.toggleDrag(false);
            this.partyWidget.setSwapSource(null);
            this.partyWidget.setDraggedSlot(null);
        }
        return super.m_7979_(mouseX, mouseY, button, f, g);
    }

    private final void toggleDrag(boolean bl) {
        PartySlotWidget focusedElement = bl ? this.partyWidget.getDraggedSlot() : null;
        this.summary.m_7522_((GuiEventListener)focusedElement);
        this.summary.m_7897_(bl);
    }

    private final void repositionSlot(double mouseX, double mouseY) {
        this.m_252865_((int)(mouseX - (double)23));
        this.m_253211_((int)(mouseY - (double)13));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean isValidClick(double mouseX, double mouseY, int button) {
        if (button != 0) return false;
        int n = this.m_252754_();
        int n2 = this.m_252754_() + this.f_93618_;
        int n3 = (int)mouseX;
        if (n > n3) return false;
        if (n3 > n2) return false;
        boolean bl = true;
        if (!bl) return false;
        n = this.m_252907_();
        n2 = this.m_252907_() + this.f_93619_;
        n3 = (int)mouseY;
        if (n > n3) return false;
        if (n3 > n2) return false;
        return true;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\t\u001a\u0004\b\r\u0010\u000bR\u0014\u0010\u000e\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\tR\u0014\u0010\u000f\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\tR\u0014\u0010\u0010\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\t\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/PartySlotWidget$Companion;", "", "", "HEIGHT", "I", "PORTRAIT_DIAMETER", "WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "genderIconFemale", "Lnet/minecraft/resources/ResourceLocation;", "getGenderIconFemale", "()Lnet/minecraft/resources/ResourceLocation;", "genderIconMale", "getGenderIconMale", "slotEmptyResource", "slotFaintedResource", "slotResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getGenderIconMale() {
            return genderIconMale;
        }

        @NotNull
        public final ResourceLocation getGenderIconFemale() {
            return genderIconFemale;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

