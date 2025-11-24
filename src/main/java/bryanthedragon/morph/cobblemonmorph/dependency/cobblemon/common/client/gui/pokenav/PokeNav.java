/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashBasedTable
 *  com.google.common.collect.Table
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pokenav;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pokenav.PokeNav;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pokenav.PokeNavFillerButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pokenav.PokeNavImageButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CurrentKeyAccessorKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.PokeNavigatorBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 G2\u00020\u0001:\u0001GB\u0007\u00a2\u0006\u0004\bF\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001f\u0010\t\u001a\u00020\b2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\u000b\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\r\u0010\fJ\u001f\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001b\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0013H\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0017J\u000f\u0010\u0019\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u0019\u0010\u0004J7\u0010\"\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u001e2\u000e\b\u0002\u0010!\u001a\b\u0012\u0004\u0012\u00020\b0 H\u0002\u00a2\u0006\u0004\b\"\u0010#J'\u0010'\u001a\u00020\b2\u0006\u0010$\u001a\u00020\u00052\u0006\u0010%\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b'\u0010(J'\u0010)\u001a\u00020\b2\u0006\u0010$\u001a\u00020\u00052\u0006\u0010%\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b)\u0010(J\u0017\u0010*\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b*\u0010\u0017J\u0017\u0010+\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b+\u0010\u0017J\u001f\u0010,\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b,\u0010-J\u0017\u00100\u001a\u00020\u00022\u0006\u0010/\u001a\u00020.H\u0002\u00a2\u0006\u0004\b0\u00101J\u0017\u00102\u001a\u00020\u00022\u0006\u0010/\u001a\u00020.H\u0002\u00a2\u0006\u0004\b2\u00101J/\u00109\u001a\u00020\u00022\u0006\u00104\u001a\u0002032\u0006\u00105\u001a\u00020\u00052\u0006\u00106\u001a\u00020\u00052\u0006\u00108\u001a\u000207H\u0016\u00a2\u0006\u0004\b9\u0010:J\u000f\u0010;\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b;\u0010<R\u0016\u0010=\u001a\u00020\b8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b=\u0010>R&\u0010A\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020@0?8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bA\u0010BR\"\u0010C\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00138\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\bC\u0010DR\u0016\u0010E\u001a\u00020\b8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\bE\u0010>\u00a8\u0006H"}, d2={"Lcom/cobblemon/mod/common/client/gui/pokenav/PokeNav;", "Lnet/minecraft/client/gui/screens/Screen;", "", "applyMouseMoveNarratorDelay", "()V", "", "x", "y", "", "buttonExists", "(II)Z", "currentMaxColumn", "()I", "currentMaxRow", "posX", "posY", "Lcom/cobblemon/mod/common/client/gui/pokenav/PokeNavFillerButton;", "fillerButtonOf", "(II)Lcom/cobblemon/mod/common/client/gui/pokenav/PokeNavFillerButton;", "Lkotlin/Pair;", "findNextInsertion", "()Lkotlin/Pair;", "getHeightFor", "(I)I", "getWidthForPos", "init", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "onPress", "Lnet/minecraft/network/chat/MutableComponent;", "text", "Lkotlin/Function0;", "canClick", "insertButton", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/gui/components/Button$OnPress;Lnet/minecraft/network/chat/MutableComponent;Lkotlin/jvm/functions/Function0;)V", "pKeyCode", "pScanCode", "pModifiers", "keyPressed", "(III)Z", "keyReleased", "maxColumnAt", "maxRowAt", "moveSelected", "(II)V", "Lnet/minecraft/client/gui/components/Button;", "button", "onPressExit", "(Lnet/minecraft/client/gui/components/Button;)V", "onPressPokemon", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "pMouseX", "pMouseY", "", "pPartialTicks", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "shouldPause", "()Z", "aboutToClose", "Z", "Lcom/google/common/collect/Table;", "Lcom/cobblemon/mod/common/client/gui/pokenav/PokeNavImageButton;", "buttons", "Lcom/google/common/collect/Table;", "currentSelectionPos", "Lkotlin/Pair;", "focusWithKey", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPokeNav.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokeNav.kt\ncom/cobblemon/mod/common/client/gui/pokenav/PokeNav\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,344:1\n1855#2,2:345\n*S KotlinDebug\n*F\n+ 1 PokeNav.kt\ncom/cobblemon/mod/common/client/gui/pokenav/PokeNav\n*L\n72#1:345,2\n*E\n"})
public final class PokeNav
extends Screen {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Table<Integer, Integer, PokeNavImageButton> buttons;
    @NotNull
    private Pair<Integer, Integer> currentSelectionPos;
    private boolean aboutToClose;
    private boolean focusWithKey;
    private static final int MAX_BUTTONS_PER_ROW = 3;
    private static final int MAX_BUTTONS_PER_COLUMN = 2;
    private static final int HORIZONTAL_SPACING = 8;
    private static final int VERTICAL_SPACING = 26;
    private static final int backgroundHeight = 125;
    private static final int backgroundWidth = 218;
    private static final int buttonHeight = 39;
    private static final int buttonWidth = 64;
    @NotNull
    private static final ResourceLocation background = MiscUtilsKt.cobblemonResource("textures/gui/pokenav/pokenav_base.png");
    @NotNull
    private static final ResourceLocation exit = MiscUtilsKt.cobblemonResource("textures/gui/pokenav/pokenav_exit.png");
    @NotNull
    private static final ResourceLocation pokemon = MiscUtilsKt.cobblemonResource("textures/gui/pokenav/pokenav_pokemon.png");
    @NotNull
    private static final ResourceLocation select = MiscUtilsKt.cobblemonResource("textures/gui/pokenav/pokenav_select.png");

    public PokeNav() {
        super((Component)Component.m_237115_((String)"cobblemon.ui.pokenav.title"));
        HashBasedTable hashBasedTable = HashBasedTable.create();
        Intrinsics.checkNotNullExpressionValue((Object)hashBasedTable, (String)"create()");
        this.buttons = (Table)hashBasedTable;
        this.currentSelectionPos = TuplesKt.to((Object)0, (Object)0);
        this.focusWithKey = true;
    }

    protected void m_7856_() {
        this.buttons.clear();
        Button.OnPress onPress = this::onPressPokemon;
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.pokemon", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.pokemon\")");
        this.insertButton(pokemon, onPress, mutableComponent, (Function0<Boolean>)((Function0)init.2.INSTANCE));
        Button.OnPress onPress2 = this::onPressExit;
        MutableComponent mutableComponent2 = LocalizationUtilsKt.lang("ui.exit", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"lang(\"ui.exit\")");
        PokeNav.insertButton$default(this, exit, onPress2, mutableComponent2, null, 8, null);
        Collection collection = this.buttons.values();
        Intrinsics.checkNotNullExpressionValue((Object)collection, (String)"this.buttons.values()");
        Iterable $this$forEach$iv = collection;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PokeNavImageButton button = (PokeNavImageButton)((Object)element$iv);
            boolean bl = false;
            this.m_142416_((GuiEventListener)button);
            if (button.canClick()) continue;
            this.m_142416_((GuiEventListener)this.fillerButtonOf(button.getPosX(), button.getPosY()));
        }
        super.m_7856_();
    }

    public boolean m_7933_(int pKeyCode, int pScanCode, int pModifiers) {
        Pair pair;
        int n = pKeyCode;
        if (n == 262 ? true : n == 68) {
            pair = TuplesKt.to((Object)1, (Object)0);
        } else if (n == 263 ? true : n == 65) {
            pair = TuplesKt.to((Object)-1, (Object)0);
        } else if (n == 265 ? true : n == 87) {
            pair = TuplesKt.to((Object)0, (Object)-1);
        } else if (n == 264 ? true : n == 83) {
            pair = TuplesKt.to((Object)0, (Object)1);
        } else if (n == 32) {
            PokeNavImageButton button;
            PokeNavImageButton pokeNavImageButton = button = (PokeNavImageButton)((Object)this.buttons.get(this.currentSelectionPos.getFirst(), this.currentSelectionPos.getSecond()));
            if (pokeNavImageButton != null) {
                SoundManager soundManager = Minecraft.m_91087_().m_91106_();
                Intrinsics.checkNotNullExpressionValue((Object)soundManager, (String)"getInstance().soundManager");
                pokeNavImageButton.m_7435_(soundManager);
            }
            PokeNavImageButton pokeNavImageButton2 = button;
            if (pokeNavImageButton2 != null) {
                pokeNavImageButton2.m_5691_();
            }
            pair = TuplesKt.to((Object)0, (Object)0);
        } else if ((n == CurrentKeyAccessorKt.boundKey(PokeNavigatorBinding.INSTANCE).m_84873_() ? true : n == 340) ? true : n == 344) {
            this.aboutToClose = true;
            pair = TuplesKt.to((Object)0, (Object)0);
        } else {
            pair = TuplesKt.to((Object)0, (Object)0);
        }
        Pair movement = pair;
        this.moveSelected(((Number)movement.getFirst()).intValue(), ((Number)movement.getSecond()).intValue());
        return super.m_7933_(pKeyCode, pScanCode, pModifiers);
    }

    public boolean m_7920_(int pKeyCode, int pScanCode, int pModifiers) {
        if ((pKeyCode == CurrentKeyAccessorKt.boundKey(PokeNavigatorBinding.INSTANCE).m_84873_() || pKeyCode == 340 || pKeyCode == 344) && this.aboutToClose) {
            Minecraft.m_91087_().m_91152_(null);
        }
        return super.m_7920_(pKeyCode, pScanCode, pModifiers);
    }

    public void m_88315_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        this.m_280273_(context);
        PoseStack poseStack = context.m_280168_();
        Object object = background;
        int n = (this.f_96543_ - 218) / 2;
        int n2 = (this.f_96544_ - 125) / 2;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, (ResourceLocation)object, n, n2, 125, 218, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        super.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
        PokeNavImageButton pokeNavImageButton = (PokeNavImageButton)((Object)this.buttons.get(this.currentSelectionPos.getFirst(), this.currentSelectionPos.getSecond()));
        if (pokeNavImageButton == null) {
            return;
        }
        PokeNavImageButton selectedButton = pokeNavImageButton;
        if (!this.focusWithKey) {
            for (PokeNavImageButton button : this.buttons.values()) {
                if (!button.m_274382_()) continue;
                this.currentSelectionPos = TuplesKt.to((Object)button.getPosX(), (Object)button.getPosY());
                break;
            }
        }
        object = context.m_280168_();
        ResourceLocation resourceLocation = select;
        double d = (double)this.getWidthForPos(((Number)this.currentSelectionPos.getFirst()).intValue()) + 2.55;
        double d2 = (double)this.getHeightFor(((Number)this.currentSelectionPos.getSecond()).intValue()) + 2.45;
        Number number = selectedButton.canClick() ? (Number)1 : (Number)0.28235;
        Number number2 = selectedButton.canClick() ? (Number)1 : (Number)0.29412;
        Number number3 = selectedButton.canClick() ? (Number)1 : (Number)0.3098;
        Number number4 = selectedButton.canClick() ? (Number)1 : (Number)0.9;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"matrices");
        GuiUtilsKt.blitk$default((PoseStack)object, resourceLocation, d, d2, 34.5, 59, null, null, null, null, null, number, number2, number3, number4, false, 0.0f, 100288, null);
    }

    public void m_169414_() {
        this.focusWithKey = false;
        super.m_169414_();
    }

    public boolean m_7043_() {
        return true;
    }

    private final void moveSelected(int x, int y) {
        if (x == 0 && y == 0) {
            return;
        }
        this.focusWithKey = true;
        int currentX = ((Number)this.currentSelectionPos.getFirst()).intValue();
        int currentY = ((Number)this.currentSelectionPos.getSecond()).intValue();
        int maxColumn = this.currentMaxColumn();
        int maxRow = this.currentMaxRow();
        int newX = currentX + x;
        int newY = currentY + y;
        if (newY > maxRow) {
            newY = 0;
        } else if (newY < 0) {
            newY = maxRow;
        } else if (newX > maxColumn && newY < this.maxRowAt(0) && this.maxColumnAt(newY + 1) >= 0) {
            newX = 0;
            ++newY;
        } else if (newX > maxColumn) {
            newX = 0;
            newY = 0;
        } else if (newX < 0 && newY > 0) {
            newX = this.maxColumnAt(--newY);
        }
        if (this.buttonExists(newX, newY)) {
            this.currentSelectionPos = TuplesKt.to((Object)newX, (Object)newY);
        }
    }

    private final boolean buttonExists(int x, int y) {
        return this.buttons.get((Object)x, (Object)y) != null;
    }

    private final int currentMaxColumn() {
        int y = ((Number)this.currentSelectionPos.getSecond()).intValue();
        return this.maxColumnAt(y);
    }

    private final int maxColumnAt(int y) {
        for (int x = 3; -1 < x; --x) {
            if (!this.buttonExists(x, y)) continue;
            return x;
        }
        throw new IllegalStateException("No buttons exist");
    }

    private final int currentMaxRow() {
        int x = ((Number)this.currentSelectionPos.getFirst()).intValue();
        return this.maxRowAt(x);
    }

    private final int maxRowAt(int x) {
        for (int y = 2; -1 < y; --y) {
            if (!this.buttonExists(x, y)) continue;
            return y;
        }
        throw new IllegalStateException("No buttons exist");
    }

    private final int getWidthForPos(int posX) {
        int baseX = (this.f_96543_ - 218) / 2;
        return baseX + posX * 64 + (posX + 1) * 8 - posX * 3;
    }

    private final int getHeightFor(int posY) {
        return (this.f_96544_ - 125) / 2 + posY * 39 + posY * 26 + (posY == 0 ? 8 : 0);
    }

    private final void insertButton(ResourceLocation identifier, Button.OnPress onPress, MutableComponent text, Function0<Boolean> canClick) {
        Pair<Integer, Integer> insertion = this.findNextInsertion();
        int posX = ((Number)insertion.getFirst()).intValue();
        int posY = ((Number)insertion.getSecond()).intValue();
        this.buttons.put((Object)posX, (Object)posY, (Object)new PokeNavImageButton(posX, posY, this.getWidthForPos(posX), this.getHeightFor(posY), 64, 39, 0, 0, 0, identifier, 64, 39, onPress, text, canClick));
    }

    static /* synthetic */ void insertButton$default(PokeNav pokeNav, ResourceLocation resourceLocation, Button.OnPress onPress, MutableComponent mutableComponent, Function0 function0, int n, Object object) {
        if ((n & 8) != 0) {
            function0 = insertButton.1.INSTANCE;
        }
        pokeNav.insertButton(resourceLocation, onPress, mutableComponent, (Function0<Boolean>)function0);
    }

    private final PokeNavFillerButton fillerButtonOf(int posX, int posY) {
        return new PokeNavFillerButton(posX, posY, this.getWidthForPos(posX), this.getHeightFor(posY), 64, 39, 0, 0, 0, 64, 39);
    }

    private final Pair<Integer, Integer> findNextInsertion() {
        for (int y = 0; y < 2; ++y) {
            for (int x = 0; x < 3; ++x) {
                if (this.buttons.get((Object)x, (Object)y) != null) continue;
                return TuplesKt.to((Object)x, (Object)y);
            }
        }
        throw new IllegalStateException("Cannot fit more buttons");
    }

    private final void onPressPokemon(Button button) {
        try {
            Summary.Companion.open((Collection<? extends Pokemon>)CobblemonClient.INSTANCE.getStorage().getMyParty().getSlots(), true, CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
        }
        catch (Exception e) {
            Minecraft.m_91087_().m_91152_(null);
            Cobblemon.INSTANCE.getLOGGER().debug("Failed to open the summary from the PokeNav screen", (Throwable)e);
        }
    }

    private final void onPressExit(Button button) {
        Minecraft.m_91087_().m_91152_(null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0004R\u0014\u0010\t\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\u0004R\u0014\u0010\f\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\f\u0010\u0004R\u0014\u0010\r\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\r\u0010\u0004R\u0014\u0010\u000e\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u0004R\u0014\u0010\u000f\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\nR\u0014\u0010\u0010\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\nR\u0014\u0010\u0011\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\n\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/client/gui/pokenav/PokeNav$Companion;", "", "", "HORIZONTAL_SPACING", "I", "MAX_BUTTONS_PER_COLUMN", "MAX_BUTTONS_PER_ROW", "VERTICAL_SPACING", "Lnet/minecraft/resources/ResourceLocation;", "background", "Lnet/minecraft/resources/ResourceLocation;", "backgroundHeight", "backgroundWidth", "buttonHeight", "buttonWidth", "exit", "pokemon", "select", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

