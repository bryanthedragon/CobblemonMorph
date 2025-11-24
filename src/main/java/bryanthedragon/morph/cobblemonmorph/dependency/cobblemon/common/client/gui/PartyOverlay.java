/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.toasts.Toast
 *  net.minecraft.client.gui.components.toasts.Toast$Visibility
 *  net.minecraft.client.gui.screens.ChatScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.toast.CobblemonToast;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CurrentKeyAccessorKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.HidePartyBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.SummaryBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientParty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0007\u00a2\u0006\u0004\b\u0018\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\r\u0010\t\u001a\u00020\u0006\u00a2\u0006\u0004\b\t\u0010\nR\u0016\u0010\f\u001a\u00020\u000b8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR\"\u0010\u0011\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00100\u000f0\u000e8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/client/gui/PartyOverlay;", "Lnet/minecraft/client/gui/Gui;", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "partialDeltaTicks", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;F)V", "resetAttachedToast", "()V", "", "attachedToast", "Z", "", "Ljava/lang/Class;", "Lnet/minecraft/client/gui/screens/Screen;", "screenExemptions", "Ljava/util/List;", "Lcom/cobblemon/mod/common/client/gui/toast/CobblemonToast;", "starterToast", "Lcom/cobblemon/mod/common/client/gui/toast/CobblemonToast;", "getStarterToast", "()Lcom/cobblemon/mod/common/client/gui/toast/CobblemonToast;", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPartyOverlay.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartyOverlay.kt\ncom/cobblemon/mod/common/client/gui/PartyOverlay\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,314:1\n2624#2,3:315\n1864#2,3:318\n*S KotlinDebug\n*F\n+ 1 PartyOverlay.kt\ncom/cobblemon/mod/common/client/gui/PartyOverlay\n*L\n100#1:315,3\n122#1:318,3\n*E\n"})
public final class PartyOverlay
extends Gui {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final List<Class<? extends Screen>> screenExemptions;
    @NotNull
    private final CobblemonToast starterToast;
    private boolean attachedToast;
    private static final int SLOT_HEIGHT = 30;
    private static final int SLOT_WIDTH = 62;
    private static final int SLOT_SPACING = 4;
    private static final int PORTRAIT_DIAMETER = 21;
    private static final float SCALE = 0.5f;
    @NotNull
    private static final ResourceLocation partySlot = MiscUtilsKt.cobblemonResource("textures/gui/party/party_slot.png");
    @NotNull
    private static final ResourceLocation partySlotActive = MiscUtilsKt.cobblemonResource("textures/gui/party/party_slot_active.png");
    @NotNull
    private static final ResourceLocation partySlotFainted = MiscUtilsKt.cobblemonResource("textures/gui/party/party_slot_fainted.png");
    @NotNull
    private static final ResourceLocation partySlotFaintedActive = MiscUtilsKt.cobblemonResource("textures/gui/party/party_slot_fainted_active.png");
    @NotNull
    private static final ResourceLocation partySlotCollapsed = MiscUtilsKt.cobblemonResource("textures/gui/party/party_slot_collapsed.png");
    @NotNull
    private static final ResourceLocation genderIconMale = MiscUtilsKt.cobblemonResource("textures/gui/party/party_gender_male.png");
    @NotNull
    private static final ResourceLocation genderIconFemale = MiscUtilsKt.cobblemonResource("textures/gui/party/party_gender_female.png");
    @NotNull
    private static final ResourceLocation portraitBackground = MiscUtilsKt.cobblemonResource("textures/gui/party/party_slot_portrait_background.png");

    public PartyOverlay() {
        super(Minecraft.m_91087_(), Minecraft.m_91087_().m_91291_());
        Object[] objectArray = new Class[]{ChatScreen.class, BattleGUI.class};
        this.screenExemptions = CollectionsKt.listOf((Object[])objectArray);
        UUID uUID = Mth.m_14002_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"randomUuid()");
        ItemStack itemStack = CobblemonItems.POKE_BALL.m_7968_();
        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"POKE_BALL.defaultStack");
        Object[] objectArray2 = new Object[1];
        Intrinsics.checkNotNullExpressionValue((Object)CurrentKeyAccessorKt.boundKey(SummaryBinding.INSTANCE).m_84875_(), (String)"SummaryBinding.boundKey().localizedText");
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.starter.choose_starter_title", objectArray2);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.starter.choose_\u2026boundKey().localizedText)");
        Component component = (Component)TextKt.red(mutableComponent);
        objectArray2 = new Object[1];
        Intrinsics.checkNotNullExpressionValue((Object)CurrentKeyAccessorKt.boundKey(SummaryBinding.INSTANCE).m_84875_(), (String)"SummaryBinding.boundKey().localizedText");
        MutableComponent mutableComponent2 = LocalizationUtilsKt.lang("ui.starter.choose_starter_description", objectArray2);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"lang(\"ui.starter.choose_\u2026boundKey().localizedText)");
        Component component2 = (Component)TextKt.darkGray(mutableComponent2);
        ResourceLocation resourceLocation = Toast.f_94893_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"TEXTURE");
        this.starterToast = new CobblemonToast(uUID, itemStack, component, component2, resourceLocation, -1.0f, 0);
    }

    @NotNull
    public final CobblemonToast getStarterToast() {
        return this.starterToast;
    }

    public final void resetAttachedToast() {
        Minecraft minecraft = Minecraft.m_91087_();
        minecraft.m_91300_().m_94919_();
        this.starterToast.setNextVisibility$common(Toast.Visibility.SHOW);
        this.attachedToast = false;
    }

    /*
     * WARNING - void declaration
     */
    public void m_280421_(@NotNull GuiGraphics context, float partialDeltaTicks) {
        boolean bl;
        PoseStack matrices;
        ClientParty party;
        int panelX;
        Minecraft minecraft;
        block15: {
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            minecraft = Minecraft.m_91087_();
            if (minecraft.f_91080_ != null) {
                Screen screen = minecraft.f_91080_;
                Class<Object> clazz = screen != null ? screen.getClass() : null;
                Intrinsics.checkNotNull(clazz, (String)"null cannot be cast to non-null type java.lang.Class<out net.minecraft.client.gui.screen.Screen>");
                if (!this.screenExemptions.contains(clazz)) {
                    return;
                }
            }
            if (minecraft.f_91066_.f_92063_) {
                return;
            }
            if (HidePartyBinding.INSTANCE.getShouldHide()) {
                return;
            }
            panelX = 0;
            party = CobblemonClient.INSTANCE.getStorage().getMyParty();
            matrices = context.m_280168_();
            Iterable $this$none$iv = party.getSlots();
            boolean $i$f$none = false;
            if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv : $this$none$iv) {
                    Pokemon it = (Pokemon)element$iv;
                    boolean bl2 = false;
                    if (!(it != null)) continue;
                    bl = false;
                    break block15;
                }
                bl = true;
            }
        }
        if (bl) {
            if (!(!CobblemonClient.INSTANCE.getClientPlayerData().getPromptStarter() || CobblemonClient.INSTANCE.getClientPlayerData().getStarterLocked() || CobblemonClient.INSTANCE.getClientPlayerData().getStarterSelected() || CobblemonClient.INSTANCE.getCheckedStarterScreen() || this.attachedToast)) {
                minecraft.m_91300_().m_94922_((Toast)this.starterToast);
                this.attachedToast = true;
            }
            return;
        }
        int totalHeight = party.getSlots().size() * 30;
        int midY = minecraft.m_91268_().m_85446_() / 2;
        int startY = midY - totalHeight / 2 - 10;
        int portraitFrameOffsetX = 22;
        int portraitFrameOffsetY = 2;
        int selectedSlot = CobblemonClient.INSTANCE.getStorage().getSelectedSlot();
        Iterable $this$forEachIndexed$iv = party;
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            ItemStack heldItem2;
            PersistentStatus status;
            int indexOffsetY;
            int selectedOffsetX;
            void pokemon;
            int n;
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Pokemon pokemon2 = (Pokemon)item$iv;
            int index = n;
            boolean bl3 = false;
            if (pokemon != null) {
                selectedOffsetX = selectedSlot == index ? 6 : 0;
                indexOffsetY = 34 * index;
                int y = startY + indexOffsetY + portraitFrameOffsetY;
                Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
                GuiUtilsKt.blitk$default(matrices, portraitBackground, panelX + portraitFrameOffsetX + selectedOffsetX, y, 21, 21, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
                context.m_280588_(panelX + portraitFrameOffsetX + selectedOffsetX, y, panelX + portraitFrameOffsetX + selectedOffsetX + 21, y + 21);
                matrices.m_85836_();
                matrices.m_85837_((double)(panelX + portraitFrameOffsetX + selectedOffsetX) + 10.5 - 1.0, (double)y - (double)12, 0.0);
                GuiUtilsKt.drawPortraitPokemon$default(pokemon.getSpecies(), pokemon.getAspects(), matrices, 0.0f, false, null, partialDeltaTicks, 56, null);
                matrices.m_85849_();
                context.m_280618_();
            }
            selectedOffsetX = selectedSlot == index ? 6 : 0;
            indexOffsetY = 34 * index;
            int indexY = startY + indexOffsetY;
            ResourceLocation slotTexture = pokemon != null ? (pokemon.isFainted() ? (selectedSlot == index ? partySlotFaintedActive : partySlotFainted) : (selectedSlot == index ? partySlotActive : partySlot)) : partySlotCollapsed;
            Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
            GuiUtilsKt.blitk$default(matrices, slotTexture, panelX, indexY, 30, 62, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
            if (pokemon == null) continue;
            ResourceLocation stateIcon = pokemon.getState().getIcon((Pokemon)pokemon);
            if (stateIcon != null) {
                GuiUtilsKt.blitk$default(matrices, stateIcon, Float.valueOf((float)(panelX + selectedOffsetX + 8) / 0.5f), Float.valueOf((float)(indexY + portraitFrameOffsetY + 1) / 0.5f), 17, 24, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
            }
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.lv", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.lv\")");
            RenderHelperKt.drawScaledText$default(context, null, mutableComponent, Float.valueOf((float)(panelX + selectedOffsetX) + 6.5f), (double)indexY + 13.5, 0.5f, null, 0, 0, true, true, null, null, 6594, null);
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(pokemon.getLevel())), Float.valueOf((float)(panelX + selectedOffsetX) + 6.5f), indexY + 18, 0.5f, null, 0, 0, true, true, null, null, 6594, null);
            RenderHelperKt.drawScaledText$default(context, null, pokemon.getDisplayName(), Float.valueOf((float)(panelX + selectedOffsetX) + 2.5f), indexY + 25, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
            if (pokemon.getGender() != Gender.GENDERLESS) {
                GuiUtilsKt.blitk$default(matrices, pokemon.getGender() == Gender.MALE ? genderIconMale : genderIconFemale, Float.valueOf((float)(panelX + selectedOffsetX + 40) / 0.5f), Float.valueOf((float)(indexY + 25) / 0.5f), 7, 5, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
            }
            float hpRatio = (float)pokemon.getCurrentHealth() / (float)pokemon.getHp();
            int barHeightMax = 18;
            int hpBarWidth = 2;
            float hpBarHeight = hpRatio * (float)barHeightMax;
            Pair pair = RenderHelperKt.getDepletableRedGreen$default(hpRatio, 0.0f, 0.0f, 6, null);
            float red = ((Number)pair.component1()).floatValue();
            float green = ((Number)pair.component2()).floatValue();
            ResourceLocation resourceLocation = CobblemonResources.INSTANCE.getWHITE();
            int n2 = panelX + selectedOffsetX + 46;
            float f = (float)indexY + ((float)barHeightMax - hpBarHeight) + (float)5;
            float f2 = hpBarHeight / hpRatio;
            float f3 = (float)barHeightMax - hpBarHeight;
            float f4 = red * 0.8f;
            float f5 = green * 0.8f;
            GuiUtilsKt.blitk$default(matrices, resourceLocation, n2, Float.valueOf(f), Float.valueOf(hpBarHeight), hpBarWidth, null, Float.valueOf(f3), null, Float.valueOf(f2), null, Float.valueOf(f4), Float.valueOf(f5), Float.valueOf(0.27f), null, false, 0.0f, 116032, null);
            int expForThisLevel = pokemon.getExperience() - (pokemon.getLevel() == 1 ? 0 : pokemon.getExperienceGroup().getExperience(pokemon.getLevel()));
            int expToNextLevel = pokemon.getExperienceGroup().getExperience(pokemon.getLevel() + 1) - pokemon.getExperienceGroup().getExperience(pokemon.getLevel());
            float expRatio = (float)expForThisLevel / (float)expToNextLevel;
            int expBarWidth = 1;
            float expBarHeight = expRatio * (float)barHeightMax;
            ResourceLocation resourceLocation2 = CobblemonResources.INSTANCE.getWHITE();
            int n3 = panelX + selectedOffsetX + 49;
            float f6 = (float)indexY + ((float)barHeightMax - expBarHeight) + (float)5;
            float f7 = expBarHeight / expRatio;
            float f8 = (float)barHeightMax - expBarHeight;
            GuiUtilsKt.blitk$default(matrices, resourceLocation2, n3, Float.valueOf(f6), Float.valueOf(expBarHeight), expBarWidth, null, Float.valueOf(f8), null, Float.valueOf(f7), null, 0.2, 0.65, 0.84, null, false, 0.0f, 116032, null);
            ResourceLocation ballIcon = MiscUtilsKt.cobblemonResource("textures/gui/ball/" + pokemon.getCaughtBall().getName().m_135815_() + ".png");
            int ballHeight = 22;
            GuiUtilsKt.blitk$default(matrices, ballIcon, ((double)(panelX + selectedOffsetX) + 43.5) / (double)0.5f, Float.valueOf((float)(indexY + 22) / 0.5f), ballHeight, 18, null, stateIcon != null ? ballHeight : 0, null, ballHeight * 2, null, null, null, null, null, false, 0.5f, 64832, null);
            PersistentStatusContainer persistentStatusContainer = pokemon.getStatus();
            PersistentStatus persistentStatus = status = persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null;
            if (!pokemon.isFainted() && status != null) {
                String statusName = status.getShowdownName();
                GuiUtilsKt.blitk$default(matrices, MiscUtilsKt.cobblemonResource("textures/gui/party/status_" + statusName + ".png"), panelX + selectedOffsetX + 51, indexY + 8, 14, 4, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
            }
            if ((heldItem2 = pokemon.heldItemNoCopy$common()).m_41619_()) continue;
            double d = (double)(panelX + selectedOffsetX) + 12.0;
            double d2 = (double)indexY + 14.0;
            RenderHelperKt.renderScaledGuiItemIcon(heldItem2, d, d2, 0.5, 0.0f, matrices);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004R\u0014\u0010\n\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0004R\u0014\u0010\f\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\rR\u0014\u0010\u000f\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\rR\u0014\u0010\u0010\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\rR\u0014\u0010\u0011\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\rR\u0014\u0010\u0012\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\rR\u0014\u0010\u0013\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\rR\u0014\u0010\u0014\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\r\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/client/gui/PartyOverlay$Companion;", "", "", "PORTRAIT_DIAMETER", "I", "", "SCALE", "F", "SLOT_HEIGHT", "SLOT_SPACING", "SLOT_WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "genderIconFemale", "Lnet/minecraft/resources/ResourceLocation;", "genderIconMale", "partySlot", "partySlotActive", "partySlotCollapsed", "partySlotFainted", "partySlotFaintedActive", "portraitBackground", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

