/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PasturePCGUIConfiguration;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PastureWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.BoxStorageSlot;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.GrabbedStorageSlot;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PartyStorageSlot;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.ReleaseButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.ReleaseConfirmButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.StorageSlot;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.settings.ServerSettings;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientPC;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientParty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.SwapPCPartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.party.MovePartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.party.ReleasePartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.party.SwapPartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.MovePCPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.MovePCPokemonToPartyPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.MovePartyPokemonToPCPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.ReleasePCPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.SwapPCPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00a4\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 e2\u00020\u0001:\u0001eB/\u0012\u0006\u0010a\u001a\u00020\u000b\u0012\u0006\u0010b\u001a\u00020\u000b\u0012\u0006\u0010L\u001a\u00020K\u0012\u0006\u0010I\u001a\u00020H\u0012\u0006\u0010=\u001a\u00020<\u00a2\u0006\u0004\bc\u0010dJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0011\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J'\u0010\r\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u000fH\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0014H\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J/\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u001cH\u0014\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010 \u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b \u0010!J\u000f\u0010\"\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b\"\u0010!J\u000f\u0010#\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b#\u0010!R*\u0010%\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\u000b8\u0006@FX\u0086\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R$\u0010.\u001a\u0012\u0012\u0004\u0012\u00020,0+j\b\u0012\u0004\u0012\u00020,`-8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b.\u0010/R\"\u00100\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b0\u00101\u001a\u0004\b2\u0010\u0004\"\u0004\b3\u00104R$\u00106\u001a\u0004\u0018\u0001058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b6\u00107\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R\u0014\u0010=\u001a\u00020<8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b=\u0010>R$\u0010@\u001a\u0012\u0012\u0004\u0012\u00020?0+j\b\u0012\u0004\u0012\u00020?`-8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b@\u0010/R$\u0010B\u001a\u0004\u0018\u00010A8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bB\u0010C\u001a\u0004\bD\u0010E\"\u0004\bF\u0010GR\u0014\u0010I\u001a\u00020H8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bI\u0010JR\u0017\u0010L\u001a\u00020K8\u0006\u00a2\u0006\f\n\u0004\bL\u0010M\u001a\u0004\bN\u0010OR\u0014\u0010Q\u001a\u00020P8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bQ\u0010RR\u0014\u0010T\u001a\u00020S8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bT\u0010UR\u0014\u0010V\u001a\u00020S8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bV\u0010UR\"\u0010W\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bW\u00101\u001a\u0004\bX\u0010\u0004\"\u0004\bY\u00104R$\u0010[\u001a\u0004\u0018\u00010Z8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b[\u0010\\\u001a\u0004\b]\u0010^\"\u0004\b_\u0010`\u00a8\u0006f"}, d2={"Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "", "canDeleteSelected", "()Z", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getSelectedPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "pMouseX", "pMouseY", "", "pButton", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/gui/components/Button;", "button", "", "onStorageSlotClicked", "(Lnet/minecraft/client/gui/components/Button;)V", "Lnet/minecraft/sounds/SoundEvent;", "soundEvent", "playSound", "(Lnet/minecraft/sounds/SoundEvent;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "mouseX", "mouseY", "", "delta", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "resetSelected", "()V", "resetStorageSlots", "setupStorageSlots", "value", "box", "I", "getBox", "()I", "setBox", "(I)V", "Ljava/util/ArrayList;", "Lcom/cobblemon/mod/common/client/gui/pc/BoxStorageSlot;", "Lkotlin/collections/ArrayList;", "boxSlots", "Ljava/util/ArrayList;", "displayConfirmRelease", "Z", "getDisplayConfirmRelease", "setDisplayConfirmRelease", "(Z)V", "Lcom/cobblemon/mod/common/client/gui/pc/GrabbedStorageSlot;", "grabbedSlot", "Lcom/cobblemon/mod/common/client/gui/pc/GrabbedStorageSlot;", "getGrabbedSlot", "()Lcom/cobblemon/mod/common/client/gui/pc/GrabbedStorageSlot;", "setGrabbedSlot", "(Lcom/cobblemon/mod/common/client/gui/pc/GrabbedStorageSlot;)V", "Lcom/cobblemon/mod/common/client/storage/ClientParty;", "party", "Lcom/cobblemon/mod/common/client/storage/ClientParty;", "Lcom/cobblemon/mod/common/client/gui/pc/PartyStorageSlot;", "partySlots", "Lcom/cobblemon/mod/common/client/gui/pasture/PastureWidget;", "pastureWidget", "Lcom/cobblemon/mod/common/client/gui/pasture/PastureWidget;", "getPastureWidget", "()Lcom/cobblemon/mod/common/client/gui/pasture/PastureWidget;", "setPastureWidget", "(Lcom/cobblemon/mod/common/client/gui/pasture/PastureWidget;)V", "Lcom/cobblemon/mod/common/client/storage/ClientPC;", "pc", "Lcom/cobblemon/mod/common/client/storage/ClientPC;", "Lcom/cobblemon/mod/common/client/gui/pc/PCGUI;", "pcGui", "Lcom/cobblemon/mod/common/client/gui/pc/PCGUI;", "getPcGui", "()Lcom/cobblemon/mod/common/client/gui/pc/PCGUI;", "Lcom/cobblemon/mod/common/client/gui/pc/ReleaseButton;", "releaseButton", "Lcom/cobblemon/mod/common/client/gui/pc/ReleaseButton;", "Lcom/cobblemon/mod/common/client/gui/pc/ReleaseConfirmButton;", "releaseNoButton", "Lcom/cobblemon/mod/common/client/gui/pc/ReleaseConfirmButton;", "releaseYesButton", "screenLoaded", "getScreenLoaded", "setScreenLoaded", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "selectedPosition", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "getSelectedPosition", "()Lcom/cobblemon/mod/common/api/storage/StorePosition;", "setSelectedPosition", "(Lcom/cobblemon/mod/common/api/storage/StorePosition;)V", "pX", "pY", "<init>", "(IILcom/cobblemon/mod/common/client/gui/pc/PCGUI;Lcom/cobblemon/mod/common/client/storage/ClientPC;Lcom/cobblemon/mod/common/client/storage/ClientParty;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nStorageWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StorageWidget.kt\ncom/cobblemon/mod/common/client/gui/pc/StorageWidget\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,441:1\n1855#2,2:442\n1855#2,2:444\n1855#2,2:446\n1855#2,2:448\n350#2,7:450\n1774#2,4:457\n336#2,8:461\n1#3:469\n*S KotlinDebug\n*F\n+ 1 StorageWidget.kt\ncom/cobblemon/mod/common/client/gui/pc/StorageWidget\n*L\n271#1:442,2\n283#1:444,2\n310#1:446,2\n313#1:448,2\n357#1:450,7\n365#1:457,4\n368#1:461,8\n*E\n"})
public final class StorageWidget
extends SoundlessWidget {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final PCGUI pcGui;
    @NotNull
    private final ClientPC pc;
    @NotNull
    private final ClientParty party;
    @NotNull
    private final ArrayList<PartyStorageSlot> partySlots;
    @NotNull
    private final ArrayList<BoxStorageSlot> boxSlots;
    @NotNull
    private final ReleaseButton releaseButton;
    @NotNull
    private final ReleaseConfirmButton releaseYesButton;
    @NotNull
    private final ReleaseConfirmButton releaseNoButton;
    @Nullable
    private PastureWidget pastureWidget;
    private boolean displayConfirmRelease;
    private boolean screenLoaded;
    @Nullable
    private StorePosition selectedPosition;
    @Nullable
    private GrabbedStorageSlot grabbedSlot;
    private int box;
    public static final int WIDTH = 263;
    public static final int HEIGHT = 155;
    public static final int BOX_SLOT_START_OFFSET_X = 7;
    public static final int BOX_SLOT_START_OFFSET_Y = 11;
    public static final int PARTY_SLOT_START_OFFSET_X = 193;
    public static final int PARTY_SLOT_START_OFFSET_Y = 8;
    public static final int BOX_SLOT_PADDING = 2;
    public static final int PARTY_SLOT_PADDING = 6;
    @NotNull
    private static final ResourceLocation partyPanelResource = MiscUtils.cobblemonResource("textures/gui/pc/party_panel.png");
    @NotNull
    private static final ResourceLocation screenOverlayResource = MiscUtils.cobblemonResource("textures/gui/pc/pc_screen_overlay.png");

    public StorageWidget(int pX, int pY, @NotNull PCGUI pcGui, @NotNull ClientPC pc, @NotNull ClientParty party) {
        Intrinsics.checkNotNullParameter((Object)((Object)pcGui), (String)"pcGui");
        Intrinsics.checkNotNullParameter((Object)pc, (String)"pc");
        Intrinsics.checkNotNullParameter((Object)party, (String)"party");
        MutableComponent mutableComponent = Component.m_237113_((String)"PCWidget");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"PCWidget\")");
        super(pX, pY, 263, 155, (Component)mutableComponent);
        this.pcGui = pcGui;
        this.pc = pc;
        this.party = party;
        this.partySlots = new ArrayList();
        this.boxSlots = new ArrayList();
        this.setupStorageSlots();
        this.releaseButton = new ReleaseButton(this.m_252754_() + 194, this.m_252907_() + 124, this, arg_0 -> StorageWidget._init_$lambda$0(this, arg_0));
        this.releaseYesButton = new ReleaseConfirmButton(this.m_252754_() + 190, this.m_252907_() + 131, this, "ui.generic.yes", arg_0 -> StorageWidget._init_$lambda$1(this, arg_0));
        this.releaseNoButton = new ReleaseConfirmButton(this.m_252754_() + 226, this.m_252907_() + 131, this, "ui.generic.no", arg_0 -> StorageWidget._init_$lambda$2(this, arg_0));
        if (this.pcGui.getConfiguration() instanceof PasturePCGUIConfiguration) {
            this.pastureWidget = new PastureWidget(this, (PasturePCGUIConfiguration)this.pcGui.getConfiguration(), this.m_252754_() + 182, this.m_252907_() - 19);
        }
    }

    @NotNull
    public final PCGUI getPcGui() {
        return this.pcGui;
    }

    @Nullable
    public final PastureWidget getPastureWidget() {
        return this.pastureWidget;
    }

    public final void setPastureWidget(@Nullable PastureWidget pastureWidget) {
        this.pastureWidget = pastureWidget;
    }

    public final boolean getDisplayConfirmRelease() {
        return this.displayConfirmRelease;
    }

    public final void setDisplayConfirmRelease(boolean bl) {
        this.displayConfirmRelease = bl;
    }

    public final boolean getScreenLoaded() {
        return this.screenLoaded;
    }

    public final void setScreenLoaded(boolean bl) {
        this.screenLoaded = bl;
    }

    @Nullable
    public final StorePosition getSelectedPosition() {
        return this.selectedPosition;
    }

    public final void setSelectedPosition(@Nullable StorePosition storePosition) {
        this.selectedPosition = storePosition;
    }

    @Nullable
    public final GrabbedStorageSlot getGrabbedSlot() {
        return this.grabbedSlot;
    }

    public final void setGrabbedSlot(@Nullable GrabbedStorageSlot grabbedStorageSlot) {
        this.grabbedSlot = grabbedStorageSlot;
    }

    public final int getBox() {
        return this.box;
    }

    public final void setBox(int value2) {
        this.box = value2 > 0 && value2 < this.pc.getBoxes().size() ? value2 : (value2 < 0 ? this.pc.getBoxes().size() - 1 : 0);
        this.setupStorageSlots();
    }

    public final boolean canDeleteSelected() {
        return (!(this.selectedPosition instanceof PartyPosition) || CollectionsKt.filterNotNull((Iterable)this.party).size() > 1) && this.selectedPosition != null && this.grabbedSlot != null;
    }

    private final void setupStorageSlots() {
        this.resetStorageSlots();
        int index = 0;
        int boxStartX = this.m_252754_() + 7;
        int boxStartY = this.m_252907_() + 11;
        for (int row = 1; row < 6; ++row) {
            for (int col = 1; col < 7; ++col) {
                BoxStorageSlot boxStorageSlot;
                BoxStorageSlot widget = boxStorageSlot = new BoxStorageSlot(boxStartX + (col - 1) * 27, boxStartY + (row - 1) * 27, this, this.pc, new PCPosition(this.box, index), arg_0 -> StorageWidget.setupStorageSlots$lambda$3(this, arg_0));
                boolean bl = false;
                this.addWidget((GuiEventListener)widget);
                this.boxSlots.add(widget);
                ++index;
            }
        }
        if (this.pcGui.getConfiguration().getShowParty()) {
            for (int partyIndex = 0; partyIndex < 6; ++partyIndex) {
                PartyStorageSlot partyStorageSlot;
                int partyX = this.m_252754_() + 193;
                int partyY = this.m_252907_() + 8;
                if (partyIndex > 0) {
                    boolean isEven = partyIndex % 2 == 0;
                    int offsetIndex = (partyIndex - (isEven ? 0 : 1)) / 2;
                    int offsetX = isEven ? 0 : 31;
                    int offsetY = isEven ? 0 : 8;
                    partyX += offsetX;
                    partyY += 31 * offsetIndex + offsetY;
                }
                PartyStorageSlot widget = partyStorageSlot = new PartyStorageSlot(partyX, partyY, this, this.party, new PartyPosition(partyIndex), arg_0 -> StorageWidget.setupStorageSlots$lambda$5(this, arg_0));
                boolean bl = false;
                this.addWidget((GuiEventListener)widget);
                this.partySlots.add(widget);
            }
        }
    }

    private final Pokemon getSelectedPokemon() {
        StorePosition storePosition = this.selectedPosition;
        if (storePosition == null) {
            return null;
        }
        StorePosition selectedPosition = storePosition;
        StorePosition storePosition2 = selectedPosition;
        return storePosition2 instanceof PCPosition ? this.pc.get((PCPosition)selectedPosition) : (storePosition2 instanceof PartyPosition ? this.party.get((PartyPosition)selectedPosition) : null);
    }

    protected void m_87963_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        block9: {
            Pokemon pokemon;
            StorageSlot slot;
            boolean $i$f$forEach;
            Iterable $this$forEach$iv;
            int n;
            ResourceLocation resourceLocation;
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            PoseStack matrices = context.m_280168_();
            if (this.pcGui.getConfiguration().getShowParty()) {
                resourceLocation = context.m_280168_();
                ResourceLocation resourceLocation2 = partyPanelResource;
                n = this.m_252754_() + 182;
                int n2 = this.m_252907_() - 19;
                Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"matrices");
                GuiUtilsKt.blitk$default((PoseStack)resourceLocation, resourceLocation2, n, n2, 169, 82, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
                ResourceLocation resourceLocation3 = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
                MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.party", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.party\")");
                RenderHelperKt.drawScaledText$default(context, resourceLocation3, TextKt.bold(mutableComponent), this.m_252754_() + 213, (double)this.m_252907_() - 15.5, 0.0f, null, 0, 0, true, true, null, null, 6624, null);
                if (this.canDeleteSelected() && this.displayConfirmRelease) {
                    ResourceLocation resourceLocation4 = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
                    MutableComponent mutableComponent2 = LocalizationUtilsKt.lang("ui.pc.release", new Object[0]);
                    Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"lang(\"ui.pc.release\")");
                    RenderHelperKt.drawScaledText$default(context, resourceLocation4, TextKt.bold(mutableComponent2), this.m_252754_() + 223, this.m_252907_() + 119, 0.0f, null, 0, 0, true, false, null, null, 7648, null);
                }
                this.releaseButton.m_88315_(context, mouseX, mouseY, delta);
                this.releaseYesButton.m_88315_(context, mouseX, mouseY, delta);
                this.releaseNoButton.m_88315_(context, mouseX, mouseY, delta);
            }
            resourceLocation = screenOverlayResource;
            int n3 = this.m_252754_() - 17;
            n = this.m_252907_() - 17;
            float f = this.screenLoaded ? 1.0f : RangesKt.coerceIn((float)((float)this.pcGui.getTicksElapsed() / 10.0f), (float)0.0f, (float)1.0f);
            Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
            GuiUtilsKt.blitk$default(matrices, resourceLocation, n3, n, 189, 208, null, null, null, null, null, null, null, null, Float.valueOf(f), false, 0.0f, 114624, null);
            if (this.screenLoaded) {
                $this$forEach$iv = this.boxSlots;
                $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    slot = (BoxStorageSlot)((Object)element$iv);
                    boolean bl = false;
                    slot.m_88315_(context, mouseX, mouseY, delta);
                    pokemon = ((BoxStorageSlot)slot).getPokemon();
                    if (this.grabbedSlot != null || !slot.isHovered(mouseX, mouseY) || pokemon == null || Intrinsics.areEqual((Object)pokemon, (Object)this.pcGui.getPreviewPokemon$common())) continue;
                    this.pcGui.setPreviewPokemon(pokemon);
                }
            } else if (this.pcGui.getTicksElapsed() >= 10) {
                this.screenLoaded = true;
            }
            if (this.pcGui.getConfiguration().getShowParty()) {
                $this$forEach$iv = this.partySlots;
                $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    slot = (PartyStorageSlot)((Object)element$iv);
                    boolean bl = false;
                    slot.m_88315_(context, mouseX, mouseY, delta);
                    pokemon = ((PartyStorageSlot)slot).getPokemon();
                    if (this.grabbedSlot != null || !slot.isHovered(mouseX, mouseY) || pokemon == null || Intrinsics.areEqual((Object)pokemon, (Object)this.pcGui.getPreviewPokemon$common())) continue;
                    this.pcGui.setPreviewPokemon(pokemon);
                }
            }
            PastureWidget pastureWidget = this.pastureWidget;
            if (pastureWidget != null) {
                pastureWidget.m_87963_(context, mouseX, mouseY, delta);
            }
            GrabbedStorageSlot grabbedStorageSlot = this.grabbedSlot;
            if (grabbedStorageSlot == null) break block9;
            grabbedStorageSlot.m_88315_(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
        block5: {
            if (this.displayConfirmRelease) {
                if (this.releaseYesButton.isHovered(pMouseX, pMouseY)) {
                    this.releaseYesButton.m_6375_(pMouseX, pMouseY, pButton);
                }
                if (this.releaseNoButton.isHovered(pMouseX, pMouseY)) {
                    this.releaseNoButton.m_6375_(pMouseX, pMouseY, pButton);
                }
            } else if (this.releaseButton.isHovered(pMouseX, pMouseY)) {
                this.releaseButton.m_6375_(pMouseX, pMouseY, pButton);
            }
            PastureWidget pastureWidget = this.pastureWidget;
            if (pastureWidget == null) break block5;
            pastureWidget.m_6375_(pMouseX, pMouseY, pButton);
        }
        return super.m_6375_(pMouseX, pMouseY, pButton);
    }

    private final void resetStorageSlots() {
        GuiEventListener p0;
        Iterable $this$forEach$iv = this.partySlots;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            p0 = (GuiEventListener)element$iv;
            boolean bl = false;
            this.removeWidget(p0);
        }
        this.partySlots.clear();
        $this$forEach$iv = this.boxSlots;
        $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            p0 = (GuiEventListener)element$iv;
            boolean bl = false;
            this.removeWidget(p0);
        }
        this.boxSlots.clear();
    }

    private final void playSound(SoundEvent soundEvent) {
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)soundEvent, (float)1.0f));
    }

    private final void onStorageSlotClicked(Button button) {
        Pokemon pokemon;
        StorePosition storePosition;
        Button button2 = button;
        if (button2 instanceof BoxStorageSlot) {
            storePosition = ((BoxStorageSlot)button).getPosition();
        } else if (button2 instanceof PartyStorageSlot) {
            storePosition = ((PartyStorageSlot)button).getPosition();
        } else {
            return;
        }
        StorePosition clickedPosition = storePosition;
        this.displayConfirmRelease = false;
        if (this.selectedPosition != null && Intrinsics.areEqual((Object)this.selectedPosition, (Object)clickedPosition)) {
            if (this.grabbedSlot != null) {
                this.playSound(CobblemonSounds.PC_DROP);
            }
            this.resetSelected();
            return;
        }
        Button button3 = button;
        if (button3 instanceof BoxStorageSlot) {
            Intrinsics.checkNotNull((Object)clickedPosition, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition");
            pokemon = this.pc.get((PCPosition)clickedPosition);
        } else if (button3 instanceof PartyStorageSlot) {
            Intrinsics.checkNotNull((Object)clickedPosition, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition");
            pokemon = this.party.get((PartyPosition)clickedPosition);
        } else {
            pokemon = null;
        }
        Pokemon clickedPokemon = pokemon;
        Function3<PCGUI, StorePosition, Pokemon, Unit> selectOverride = this.pcGui.getConfiguration().getSelectOverride();
        if (selectOverride != null) {
            selectOverride.invoke((Object)this.pcGui, (Object)clickedPosition, (Object)clickedPokemon);
            return;
        }
        if (this.grabbedSlot == null) {
            if (clickedPokemon != null) {
                boolean shiftClicked = Screen.m_96638_();
                if (shiftClicked) {
                    if (clickedPosition instanceof PCPosition) {
                        int firstEmptySpace;
                        block51: {
                            int n;
                            List<Pokemon> $this$indexOfFirst$iv = this.party.getSlots();
                            boolean $i$f$indexOfFirst = false;
                            int index$iv = 0;
                            Iterator<Pokemon> iterator = $this$indexOfFirst$iv.iterator();
                            while (iterator.hasNext()) {
                                Pokemon item$iv;
                                Pokemon it = item$iv = iterator.next();
                                boolean bl = false;
                                if (it == null) {
                                    n = index$iv;
                                    break block51;
                                }
                                ++index$iv;
                            }
                            n = firstEmptySpace = -1;
                        }
                        if (firstEmptySpace != -1) {
                            UUID uUID = clickedPokemon.getUuid();
                            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"clickedPokemon.uuid");
                            MovePCPokemonToPartyPacket packet = new MovePCPokemonToPartyPacket(uUID, (PCPosition)clickedPosition, new PartyPosition(firstEmptySpace));
                            packet.sendToServer();
                            this.playSound(CobblemonSounds.PC_DROP);
                            return;
                        }
                    } else if (clickedPosition instanceof PartyPosition) {
                        int firstEmptySpace;
                        block52: {
                            int n;
                            if (ServerSettings.INSTANCE.getPreventCompletePartyDeposit()) {
                                int n2;
                                Iterable $this$count$iv = this.party;
                                boolean $i$f$count = false;
                                if ($this$count$iv instanceof Collection && ((Collection)$this$count$iv).isEmpty()) {
                                    n2 = 0;
                                } else {
                                    int count$iv = 0;
                                    for (Iterator element$iv : $this$count$iv) {
                                        Pokemon it = (Pokemon)((Object)element$iv);
                                        boolean bl = false;
                                        if (!(it != null) || ++count$iv >= 0) continue;
                                        CollectionsKt.throwCountOverflow();
                                    }
                                    n2 = count$iv;
                                }
                                if (n2 == 1) {
                                    return;
                                }
                            }
                            Iterable $this$indexOfFirst$iv = this.pc.getBoxes().get(this.box);
                            boolean $i$f$indexOfFirst = false;
                            int index$iv = 0;
                            for (Object item$iv : $this$indexOfFirst$iv) {
                                if (index$iv < 0) {
                                    CollectionsKt.throwIndexOverflow();
                                }
                                Pokemon it = (Pokemon)item$iv;
                                boolean bl = false;
                                if (it == null) {
                                    n = index$iv;
                                    break block52;
                                }
                                ++index$iv;
                            }
                            n = firstEmptySpace = -1;
                        }
                        if (firstEmptySpace != -1) {
                            UUID uUID = clickedPokemon.getUuid();
                            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"clickedPokemon.uuid");
                            MovePartyPokemonToPCPacket packet = new MovePartyPokemonToPCPacket(uUID, (PartyPosition)clickedPosition, new PCPosition(this.box, firstEmptySpace));
                            packet.sendToServer();
                            this.playSound(CobblemonSounds.PC_DROP);
                            return;
                        }
                    }
                }
                this.selectedPosition = clickedPosition;
                this.pcGui.setPreviewPokemon(clickedPokemon);
                this.grabbedSlot = new GrabbedStorageSlot(button.m_252754_(), button.m_252907_(), this, clickedPokemon);
                this.playSound(CobblemonSounds.PC_GRAB);
            }
        } else {
            Pokemon pokemon2;
            StorePosition storePosition2 = this.selectedPosition;
            if (storePosition2 instanceof PCPosition) {
                StorePosition storePosition3 = this.selectedPosition;
                Intrinsics.checkNotNull((Object)storePosition3, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition");
                pokemon2 = this.pc.get((PCPosition)storePosition3);
            } else if (storePosition2 instanceof PartyPosition) {
                StorePosition storePosition4 = this.selectedPosition;
                Intrinsics.checkNotNull((Object)storePosition4, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition");
                pokemon2 = this.party.get((PartyPosition)storePosition4);
            } else {
                pokemon2 = null;
            }
            if (pokemon2 == null) {
                return;
            }
            Pokemon selectedPokemon = pokemon2;
            if (this.selectedPosition instanceof PCPosition && clickedPosition instanceof PCPosition) {
                NetworkPacket networkPacket;
                Pokemon pokemon3 = clickedPokemon;
                if (pokemon3 != null) {
                    Pokemon it = pokemon3;
                    boolean bl = false;
                    UUID uUID = it.getUuid();
                    Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"it.uuid");
                    PCPosition pCPosition = (PCPosition)clickedPosition;
                    UUID uUID2 = selectedPokemon.getUuid();
                    Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"selectedPokemon.uuid");
                    StorePosition storePosition5 = this.selectedPosition;
                    Intrinsics.checkNotNull((Object)storePosition5, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition");
                    networkPacket = new SwapPCPokemonPacket(uUID, pCPosition, uUID2, (PCPosition)storePosition5);
                } else {
                    UUID uUID = selectedPokemon.getUuid();
                    Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"selectedPokemon.uuid");
                    StorePosition storePosition6 = this.selectedPosition;
                    Intrinsics.checkNotNull((Object)storePosition6, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition");
                    networkPacket = new MovePCPokemonPacket(uUID, (PCPosition)storePosition6, (PCPosition)clickedPosition);
                }
                NetworkPacket packet = networkPacket;
                packet.sendToServer();
                this.playSound(CobblemonSounds.PC_DROP);
                this.resetSelected();
            } else if (this.selectedPosition instanceof PCPosition && clickedPosition instanceof PartyPosition) {
                NetworkPacket networkPacket;
                Pokemon pokemon4 = clickedPokemon;
                if (pokemon4 != null) {
                    Pokemon it = pokemon4;
                    boolean bl = false;
                    UUID uUID = clickedPokemon.getUuid();
                    Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"clickedPokemon.uuid");
                    PartyPosition partyPosition = (PartyPosition)clickedPosition;
                    UUID uUID3 = selectedPokemon.getUuid();
                    Intrinsics.checkNotNullExpressionValue((Object)uUID3, (String)"selectedPokemon.uuid");
                    StorePosition storePosition7 = this.selectedPosition;
                    Intrinsics.checkNotNull((Object)storePosition7, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition");
                    networkPacket = new SwapPCPartyPokemonPacket(uUID, partyPosition, uUID3, (PCPosition)storePosition7);
                } else {
                    UUID uUID = selectedPokemon.getUuid();
                    Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"selectedPokemon.uuid");
                    StorePosition storePosition8 = this.selectedPosition;
                    Intrinsics.checkNotNull((Object)storePosition8, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition");
                    networkPacket = new MovePCPokemonToPartyPacket(uUID, (PCPosition)storePosition8, (PartyPosition)clickedPosition);
                }
                NetworkPacket packet = networkPacket;
                packet.sendToServer();
                this.playSound(CobblemonSounds.PC_DROP);
                this.resetSelected();
            } else if (this.selectedPosition instanceof PartyPosition && clickedPosition instanceof PCPosition) {
                NetworkPacket networkPacket;
                if (ServerSettings.INSTANCE.getPreventCompletePartyDeposit() && CollectionsKt.filterNotNull((Iterable)this.party).size() == 1 && clickedPokemon == null) {
                    return;
                }
                Pokemon pokemon5 = clickedPokemon;
                if (pokemon5 != null) {
                    Pokemon it = pokemon5;
                    boolean bl = false;
                    UUID uUID = selectedPokemon.getUuid();
                    Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"selectedPokemon.uuid");
                    StorePosition storePosition9 = this.selectedPosition;
                    Intrinsics.checkNotNull((Object)storePosition9, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition");
                    PartyPosition partyPosition = (PartyPosition)storePosition9;
                    UUID uUID4 = clickedPokemon.getUuid();
                    Intrinsics.checkNotNullExpressionValue((Object)uUID4, (String)"clickedPokemon.uuid");
                    networkPacket = new SwapPCPartyPokemonPacket(uUID, partyPosition, uUID4, (PCPosition)clickedPosition);
                } else {
                    UUID uUID = selectedPokemon.getUuid();
                    Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"selectedPokemon.uuid");
                    StorePosition storePosition10 = this.selectedPosition;
                    Intrinsics.checkNotNull((Object)storePosition10, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition");
                    networkPacket = new MovePartyPokemonToPCPacket(uUID, (PartyPosition)storePosition10, (PCPosition)clickedPosition);
                }
                NetworkPacket packet = networkPacket;
                packet.sendToServer();
                this.playSound(CobblemonSounds.PC_DROP);
                this.resetSelected();
            } else if (this.selectedPosition instanceof PartyPosition && clickedPosition instanceof PartyPosition) {
                NetworkPacket networkPacket;
                Pokemon pokemon6 = clickedPokemon;
                if (pokemon6 != null) {
                    Pokemon it = pokemon6;
                    boolean bl = false;
                    UUID uUID = it.getUuid();
                    Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"it.uuid");
                    PartyPosition partyPosition = (PartyPosition)clickedPosition;
                    UUID uUID5 = selectedPokemon.getUuid();
                    Intrinsics.checkNotNullExpressionValue((Object)uUID5, (String)"selectedPokemon.uuid");
                    StorePosition storePosition11 = this.selectedPosition;
                    Intrinsics.checkNotNull((Object)storePosition11, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition");
                    networkPacket = new SwapPartyPokemonPacket(uUID, partyPosition, uUID5, (PartyPosition)storePosition11);
                } else {
                    UUID uUID = selectedPokemon.getUuid();
                    Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"selectedPokemon.uuid");
                    StorePosition storePosition12 = this.selectedPosition;
                    Intrinsics.checkNotNull((Object)storePosition12, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition");
                    networkPacket = new MovePartyPokemonPacket(uUID, (PartyPosition)storePosition12, (PartyPosition)clickedPosition);
                }
                NetworkPacket packet = networkPacket;
                packet.sendToServer();
                this.playSound(CobblemonSounds.PC_DROP);
                this.resetSelected();
            }
        }
    }

    private final void resetSelected() {
        this.selectedPosition = null;
        this.grabbedSlot = null;
        this.pcGui.setPreviewPokemon(null);
    }

    private static final void _init_$lambda$0(StorageWidget this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        if (!this$0.displayConfirmRelease && this$0.canDeleteSelected()) {
            this$0.displayConfirmRelease = true;
            this$0.playSound(CobblemonSounds.PC_CLICK);
        }
    }

    private static final void _init_$lambda$1(StorageWidget this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        if (this$0.canDeleteSelected() && this$0.displayConfirmRelease) {
            NetworkPacket networkPacket;
            StorePosition storePosition = this$0.selectedPosition;
            if (storePosition == null) {
                return;
            }
            StorePosition position = storePosition;
            Pokemon pokemon = this$0.getSelectedPokemon();
            if (pokemon == null) {
                return;
            }
            Pokemon pokemon2 = pokemon;
            StorePosition storePosition2 = position;
            if (storePosition2 instanceof PartyPosition) {
                UUID uUID = pokemon2.getUuid();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon.uuid");
                networkPacket = new ReleasePartyPokemonPacket(uUID, (PartyPosition)position);
            } else if (storePosition2 instanceof PCPosition) {
                UUID uUID = pokemon2.getUuid();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon.uuid");
                networkPacket = new ReleasePCPokemonPacket(uUID, (PCPosition)position);
            } else {
                return;
            }
            NetworkPacket packet = networkPacket;
            CobblemonNetwork.INSTANCE.sendPacketToServer(packet);
            this$0.playSound(CobblemonSounds.PC_RELEASE);
            this$0.resetSelected();
            this$0.displayConfirmRelease = false;
        }
    }

    private static final void _init_$lambda$2(StorageWidget this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        if (this$0.displayConfirmRelease) {
            this$0.displayConfirmRelease = false;
            this$0.playSound(CobblemonSounds.PC_CLICK);
        }
    }

    private static final void setupStorageSlots$lambda$3(StorageWidget this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        this$0.onStorageSlotClicked(it);
    }

    private static final void setupStorageSlots$lambda$5(StorageWidget this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        this$0.onStorageSlotClicked(it);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004R\u0014\u0010\n\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0004R\u0014\u0010\u000b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\u0004R\u0014\u0010\r\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u000e\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget$Companion;", "", "", "BOX_SLOT_PADDING", "I", "BOX_SLOT_START_OFFSET_X", "BOX_SLOT_START_OFFSET_Y", "HEIGHT", "PARTY_SLOT_PADDING", "PARTY_SLOT_START_OFFSET_X", "PARTY_SLOT_START_OFFSET_Y", "WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "partyPanelResource", "Lnet/minecraft/resources/ResourceLocation;", "screenOverlayResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

