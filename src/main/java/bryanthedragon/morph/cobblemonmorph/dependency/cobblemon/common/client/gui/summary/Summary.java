/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.components.Renderable
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.narration.NarratableEntry
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.BenchedMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.ObservableSubscription;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.Schedulable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.ExitButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.TypeIcon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.SummaryButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.EvolutionSelectScreen;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.ModelWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.NicknameEntryWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.PartyWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.SummaryTab;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.info.InfoWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.moves.MoveSwapScreen;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.moves.MovesWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.stats.StatWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.party.MovePartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.party.SwapPartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00a2\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0006\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0004\u0018\u0000 h2\u00020\u00012\u00020\u0002:\u0001hB)\b\u0002\u0012\u000e\u0010H\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010F0e\u0012\u0006\u00104\u001a\u00020\u000f\u0012\u0006\u0010U\u001a\u00020\u0006\u00a2\u0006\u0004\bf\u0010gJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ!\u0010\f\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000e\u001a\u00020\u0003H\u0014\u00a2\u0006\u0004\b\u000e\u0010\u0005J\u000f\u0010\u0010\u001a\u00020\u000fH\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011J'\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0017\u001a\u00020\u0003H\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0005J7\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ'\u0010!\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010 \u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b!\u0010\"J\u0015\u0010%\u001a\u00020\u00032\u0006\u0010$\u001a\u00020#\u00a2\u0006\u0004\b%\u0010&J/\u0010+\u001a\u00020\u00032\u0006\u0010(\u001a\u00020'2\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010*\u001a\u00020)H\u0016\u00a2\u0006\u0004\b+\u0010,J\u000f\u0010-\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b-\u0010\u0011J\u001d\u00100\u001a\u00020\u00032\u0006\u0010.\u001a\u00020\u00062\u0006\u0010/\u001a\u00020\u0006\u00a2\u0006\u0004\b0\u00101J\u0015\u00103\u001a\u00020\u00032\u0006\u00102\u001a\u00020\u0006\u00a2\u0006\u0004\b3\u0010\tR\u0014\u00104\u001a\u00020\u000f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b4\u00105R\u0016\u00107\u001a\u0002068\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b7\u00108R\u0016\u00109\u001a\u00020\u00068\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b9\u0010:R\u0016\u0010<\u001a\u00020;8\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b<\u0010=R\u001e\u0010@\u001a\n\u0012\u0004\u0012\u00020?\u0018\u00010>8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b@\u0010AR\u0016\u0010C\u001a\u00020B8\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\bC\u0010DR(\u0010H\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010F0Ej\n\u0012\u0006\u0012\u0004\u0018\u00010F`G8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bH\u0010IR\u001a\u0010K\u001a\u00020J8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bK\u0010L\u001a\u0004\bM\u0010NR\"\u0010O\u001a\u00020F8\u0000@\u0000X\u0080.\u00a2\u0006\u0012\n\u0004\bO\u0010P\u001a\u0004\bQ\u0010R\"\u0004\bS\u0010TR\u0014\u0010U\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bU\u0010:R\"\u0010W\u001a\u00020V8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bW\u0010X\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\R\"\u0010]\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b]\u0010:\u001a\u0004\b^\u0010_\"\u0004\b`\u0010\tR\u001a\u0010c\u001a\b\u0012\u0004\u0012\u00020b0a8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bc\u0010d\u00a8\u0006i"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/Summary;", "Lnet/minecraft/client/gui/screens/Screen;", "Lcom/cobblemon/mod/common/api/scheduling/Schedulable;", "", "close", "()V", "", "screen", "displayMainScreen", "(I)V", "Lcom/cobblemon/mod/common/api/moves/Move;", "move", "displaySideScreen", "(ILcom/cobblemon/mod/common/api/moves/Move;)V", "init", "", "isOpen", "()Z", "keyCode", "scanCode", "modifiers", "keyPressed", "(III)Z", "listenToMoveSet", "", "mouseX", "mouseY", "button", "deltaX", "deltaY", "mouseDragged", "(DDIDD)Z", "amount", "mouseScrolled", "(DDD)Z", "Lnet/minecraft/sounds/SoundEvent;", "soundEvent", "playSound", "(Lnet/minecraft/sounds/SoundEvent;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "shouldPause", "sourceIndex", "targetIndex", "swapPartySlot", "(II)V", "newSelection", "switchSelection", "editable", "Z", "Lnet/minecraft/client/gui/components/AbstractWidget;", "mainScreen", "Lnet/minecraft/client/gui/components/AbstractWidget;", "mainScreenIndex", "I", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/ModelWidget;", "modelWidget", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/ModelWidget;", "Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "Lcom/cobblemon/mod/common/api/moves/MoveSet;", "moveSetSubscription", "Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/NicknameEntryWidget;", "nicknameEntryWidget", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/NicknameEntryWidget;", "Ljava/util/ArrayList;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lkotlin/collections/ArrayList;", "party", "Ljava/util/ArrayList;", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "schedulingTracker", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "getSchedulingTracker", "()Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "selectedPokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getSelectedPokemon$common", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "setSelectedPokemon$common", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "selection", "Lnet/minecraft/client/gui/components/events/GuiEventListener;", "sideScreen", "Lnet/minecraft/client/gui/components/events/GuiEventListener;", "getSideScreen", "()Lnet/minecraft/client/gui/components/events/GuiEventListener;", "setSideScreen", "(Lnet/minecraft/client/gui/components/events/GuiEventListener;)V", "sideScreenIndex", "getSideScreenIndex", "()I", "setSideScreenIndex", "", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/SummaryTab;", "summaryTabs", "Ljava/util/List;", "", "<init>", "(Ljava/util/Collection;ZI)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nSummary.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Summary.kt\ncom/cobblemon/mod/common/client/gui/summary/Summary\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,657:1\n223#2,2:658\n1855#2,2:660\n1864#2,3:663\n766#2:666\n857#2:667\n2624#2,3:668\n858#2:671\n1549#2:672\n1620#2,3:673\n1855#2,2:676\n1747#2,3:678\n1#3:662\n*S KotlinDebug\n*F\n+ 1 Summary.kt\ncom/cobblemon/mod/common/client/gui/summary/Summary\n*L\n135#1:658,2\n208#1:660,2\n321#1:663,3\n389#1:666\n389#1:667\n389#1:668,3\n389#1:671\n390#1:672\n390#1:673,3\n395#1:676,2\n597#1:678,3\n*E\n"})
public final class Summary
extends Screen
implements Schedulable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final boolean editable;
    private final int selection;
    @NotNull
    private final SchedulingTracker schedulingTracker;
    public Pokemon selectedPokemon;
    private AbstractWidget mainScreen;
    public GuiEventListener sideScreen;
    private ModelWidget modelWidget;
    private NicknameEntryWidget nicknameEntryWidget;
    @NotNull
    private final List<SummaryTab> summaryTabs;
    private int mainScreenIndex;
    private int sideScreenIndex;
    @NotNull
    private final ArrayList<Pokemon> party;
    @Nullable
    private ObservableSubscription<MoveSet> moveSetSubscription;
    public static final int BASE_WIDTH = 331;
    public static final int BASE_HEIGHT = 161;
    private static final int PORTRAIT_SIZE = 66;
    private static final float SCALE = 0.5f;
    private static final int INFO = 0;
    private static final int MOVES = 1;
    private static final int STATS = 2;
    public static final int PARTY = 0;
    public static final int MOVE_SWAP = 1;
    public static final int EVOLVE = 2;
    @NotNull
    private static final ResourceLocation baseResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_base.png");
    @NotNull
    private static final ResourceLocation portraitBackgroundResource = MiscUtils.cobblemonResource("textures/gui/summary/portrait_background.png");
    @NotNull
    private static final ResourceLocation typeSpacerResource = MiscUtils.cobblemonResource("textures/gui/summary/type_spacer.png");
    @NotNull
    private static final ResourceLocation typeSpacerDoubleResource = MiscUtils.cobblemonResource("textures/gui/summary/type_spacer_double.png");
    @NotNull
    private static final ResourceLocation sideSpacerResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_side_spacer.png");
    @NotNull
    private static final ResourceLocation evolveButtonResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_evolve_button.png");
    @NotNull
    private static final ResourceLocation iconShinyResource = MiscUtils.cobblemonResource("textures/gui/summary/icon_shiny.png");

    private Summary(Collection<? extends Pokemon> party, boolean editable, int selection) {
        super((Component)Component.m_237115_((String)"cobblemon.ui.summary.title"));
        this.editable = editable;
        this.selection = selection;
        this.schedulingTracker = new SchedulingTracker();
        this.summaryTabs = new ArrayList();
        this.party = new ArrayList<Pokemon>(party);
    }

    @Override
    @NotNull
    public SchedulingTracker getSchedulingTracker() {
        return this.schedulingTracker;
    }

    @NotNull
    public final Pokemon getSelectedPokemon$common() {
        Pokemon pokemon = this.selectedPokemon;
        if (pokemon != null) {
            return pokemon;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"selectedPokemon");
        return null;
    }

    public final void setSelectedPokemon$common(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"<set-?>");
        this.selectedPokemon = pokemon;
    }

    @NotNull
    public final GuiEventListener getSideScreen() {
        GuiEventListener guiEventListener = this.sideScreen;
        if (guiEventListener != null) {
            return guiEventListener;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"sideScreen");
        return null;
    }

    public final void setSideScreen(@NotNull GuiEventListener guiEventListener) {
        Intrinsics.checkNotNullParameter((Object)guiEventListener, (String)"<set-?>");
        this.sideScreen = guiEventListener;
    }

    public final int getSideScreenIndex() {
        return this.sideScreenIndex;
    }

    public final void setSideScreenIndex(int n) {
        this.sideScreenIndex = n;
    }

    /*
     * WARNING - void declaration
     */
    protected void m_7856_() {
        super.m_7856_();
        if (this.party.isEmpty()) {
            throw new IllegalArgumentException("Summary UI cannot display zero Pokemon");
        }
        if (this.party.size() > 6) {
            throw new IllegalArgumentException("Summary UI cannot display more than six Pokemon");
        }
        Pokemon idealSelected = this.party.get(this.selection);
        if (idealSelected == null) {
            Object element$iv2;
            Summary summary;
            block10: {
                void $this$first$iv;
                Iterable iterable = this.party;
                summary = this;
                boolean $i$f$first = false;
                for (Object element$iv2 : $this$first$iv) {
                    Pokemon it = (Pokemon)element$iv2;
                    boolean bl = false;
                    if (!(it != null)) continue;
                    break block10;
                }
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
            Object t = element$iv2;
            Intrinsics.checkNotNull(t);
            summary.setSelectedPokemon$common((Pokemon)t);
        } else {
            this.setSelectedPokemon$common(idealSelected);
        }
        this.listenToMoveSet();
        int x = (this.f_96543_ - 331) / 2;
        int y = (this.f_96544_ - 161) / 2;
        this.displayMainScreen(this.mainScreenIndex);
        Summary.displaySideScreen$default(this, 0, null, 2, null);
        float f = (float)x + 12.0f;
        float f2 = (float)y + 145.0f;
        Number number = 54;
        Number number2 = 15;
        Button.OnPress onPress = arg_0 -> Summary.init$lambda$1(this, arg_0);
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.evolve", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.evolve\")");
        this.m_142416_((GuiEventListener)new SummaryButton(f, f2, number, number2, onPress, mutableComponent, evolveButtonResource, null, (Function1)new Function1<SummaryButton, Boolean>(this){
            final /* synthetic */ Summary this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull SummaryButton it) {
                Intrinsics.checkNotNullParameter((Object)((Object)it), (String)"it");
                return !((Collection)this.this$0.getSelectedPokemon$common().getEvolutionProxy().client()).isEmpty();
            }
        }, (Function1)new Function1<SummaryButton, Boolean>(this){
            final /* synthetic */ Summary this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull SummaryButton it) {
                Intrinsics.checkNotNullParameter((Object)((Object)it), (String)"it");
                return !((Collection)this.this$0.getSelectedPokemon$common().getEvolutionProxy().client()).isEmpty();
            }
        }, false, false, false, false, 0.0f, 31872, null));
        this.summaryTabs.clear();
        MutableComponent mutableComponent2 = LocalizationUtilsKt.lang("ui.info", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"lang(\"ui.info\")");
        this.summaryTabs.add(new SummaryTab(x + 78, y - 1, mutableComponent2, arg_0 -> Summary.init$lambda$2(this, arg_0)));
        MutableComponent mutableComponent3 = LocalizationUtilsKt.lang("ui.moves", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"lang(\"ui.moves\")");
        this.summaryTabs.add(new SummaryTab(x + 119, y - 1, mutableComponent3, arg_0 -> Summary.init$lambda$3(this, arg_0)));
        MutableComponent mutableComponent4 = LocalizationUtilsKt.lang("ui.stats", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent4, (String)"lang(\"ui.stats\")");
        this.summaryTabs.add(new SummaryTab(x + 160, y - 1, mutableComponent4, arg_0 -> Summary.init$lambda$4(this, arg_0)));
        SummaryTab.toggleTab$default(this.summaryTabs.get(this.mainScreenIndex), false, 1, null);
        Iterable $this$forEach$iv = this.summaryTabs;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SummaryTab it = (SummaryTab)((Object)element$iv);
            boolean bl = false;
            this.m_142416_((GuiEventListener)it);
        }
        this.m_142416_((GuiEventListener)new ExitButton(x + 302, y + 145, arg_0 -> Summary.init$lambda$6(this, arg_0)));
        Pokemon pokemon = this.getSelectedPokemon$common();
        int n = (int)((double)y + 14.5);
        MutableComponent mutableComponent5 = LocalizationUtilsKt.lang("ui.nickname", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent5, (String)"lang(\"ui.nickname\")");
        this.nicknameEntryWidget = new NicknameEntryWidget(pokemon, x + 12, n, 50, 10, true, (Component)mutableComponent5);
        NicknameEntryWidget nicknameEntryWidget = this.nicknameEntryWidget;
        if (nicknameEntryWidget == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"nicknameEntryWidget");
            nicknameEntryWidget = null;
        }
        this.m_7522_((GuiEventListener)nicknameEntryWidget);
        NicknameEntryWidget nicknameEntryWidget2 = this.nicknameEntryWidget;
        if (nicknameEntryWidget2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"nicknameEntryWidget");
            nicknameEntryWidget2 = null;
        }
        nicknameEntryWidget2.m_93692_(false);
        NicknameEntryWidget nicknameEntryWidget3 = this.nicknameEntryWidget;
        if (nicknameEntryWidget3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"nicknameEntryWidget");
            nicknameEntryWidget3 = null;
        }
        this.m_142416_((GuiEventListener)nicknameEntryWidget3);
        this.modelWidget = new ModelWidget(x + 6, y + 32, 66, 66, this.getSelectedPokemon$common().asRenderablePokemon(), 2.0f, 325.0f, -10.0);
        ModelWidget modelWidget = this.modelWidget;
        if (modelWidget == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"modelWidget");
            modelWidget = null;
        }
        this.m_169394_(modelWidget);
    }

    public final void swapPartySlot(int sourceIndex, int targetIndex) {
        if (sourceIndex >= this.party.size() || targetIndex >= this.party.size()) {
            return;
        }
        Pokemon sourcePokemon = (Pokemon)CollectionsKt.getOrNull((List)this.party, (int)sourceIndex);
        if (sourcePokemon != null) {
            NetworkPacket networkPacket;
            Pokemon targetPokemon = (Pokemon)CollectionsKt.getOrNull((List)this.party, (int)targetIndex);
            PartyPosition sourcePosition = new PartyPosition(sourceIndex);
            PartyPosition targetPosition = new PartyPosition(targetIndex);
            Pokemon pokemon = targetPokemon;
            if (pokemon != null) {
                Pokemon it = pokemon;
                boolean bl = false;
                UUID uUID = it.getUuid();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"it.uuid");
                UUID uUID2 = sourcePokemon.getUuid();
                Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"sourcePokemon.uuid");
                networkPacket = new SwapPartyPokemonPacket(uUID, targetPosition, uUID2, sourcePosition);
            } else {
                UUID uUID = sourcePokemon.getUuid();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"sourcePokemon.uuid");
                networkPacket = new MovePartyPokemonPacket(uUID, sourcePosition, targetPosition);
            }
            NetworkPacket packet = networkPacket;
            packet.sendToServer();
            this.party.set(targetIndex, sourcePokemon);
            this.party.set(sourceIndex, targetPokemon);
            Summary.displaySideScreen$default(this, 0, null, 2, null);
            GuiEventListener guiEventListener = this.getSideScreen();
            Intrinsics.checkNotNull((Object)guiEventListener, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.PartyWidget");
            PartyWidget.enableSwap$default((PartyWidget)guiEventListener, false, 1, null);
        }
    }

    public final void switchSelection(int newSelection) {
        Object v3;
        block8: {
            Object it;
            Pokemon pokemon = (Pokemon)CollectionsKt.getOrNull((List)this.party, (int)newSelection);
            if (pokemon != null) {
                it = pokemon;
                boolean bl = false;
                this.setSelectedPokemon$common((Pokemon)it);
            }
            ObservableSubscription<MoveSet> observableSubscription = this.moveSetSubscription;
            if (observableSubscription != null) {
                observableSubscription.unsubscribe();
            }
            this.listenToMoveSet();
            this.displayMainScreen(this.mainScreenIndex);
            List list = this.m_6702_();
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"children()");
            it = list;
            Iterator bl = it.iterator();
            while (bl.hasNext()) {
                Object t = bl.next();
                GuiEventListener it2 = (GuiEventListener)t;
                boolean bl2 = false;
                if (!(it2 instanceof EvolutionSelectScreen)) continue;
                v3 = t;
                break block8;
            }
            v3 = null;
        }
        GuiEventListener guiEventListener = v3;
        if (guiEventListener != null) {
            GuiEventListener p0 = guiEventListener;
            boolean bl = false;
            this.m_169411_(p0);
        }
        if (this.modelWidget != null) {
            ModelWidget modelWidget = this.modelWidget;
            if (modelWidget == null) {
                Intrinsics.throwUninitializedPropertyAccessException((String)"modelWidget");
                modelWidget = null;
            }
            modelWidget.setPokemon(this.getSelectedPokemon$common().asRenderablePokemon());
        }
        if (this.nicknameEntryWidget != null) {
            NicknameEntryWidget nicknameEntryWidget = this.nicknameEntryWidget;
            if (nicknameEntryWidget == null) {
                Intrinsics.throwUninitializedPropertyAccessException((String)"nicknameEntryWidget");
                nicknameEntryWidget = null;
            }
            nicknameEntryWidget.setSelectedPokemon(this.getSelectedPokemon$common());
        }
    }

    private final void listenToMoveSet() {
        this.moveSetSubscription = Observable.DefaultImpls.subscribe$default(this.getSelectedPokemon$common().getMoveSet().getObservable().pipe(Observable.Companion.emitWhile((Function1)new Function1<MoveSet, Boolean>(this){
            final /* synthetic */ Summary this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull MoveSet it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return Summary.access$isOpen(this.this$0);
            }
        })), null, (Function1)new Function1<MoveSet, Unit>(this){
            final /* synthetic */ Summary this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(@NotNull MoveSet it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                AbstractWidget abstractWidget = Summary.access$getMainScreen$p(this.this$0);
                if (abstractWidget == null) {
                    Intrinsics.throwUninitializedPropertyAccessException((String)"mainScreen");
                    abstractWidget = null;
                }
                if (abstractWidget instanceof MovesWidget) {
                    Summary.access$displayMainScreen(this.this$0, 1);
                }
            }
        }, 1, null);
    }

    private final boolean isOpen() {
        return Intrinsics.areEqual((Object)Minecraft.m_91087_().f_91080_, (Object)this);
    }

    /*
     * Unable to fully structure code
     */
    private final void displayMainScreen(int screen) {
        if (this.mainScreenIndex != 2) ** GOTO lbl-1000
        v0 = this.mainScreen;
        if (v0 == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"mainScreen");
            v0 = null;
        }
        if (v0 instanceof StatWidget) {
            v1 = this.mainScreen;
            if (v1 == null) {
                Intrinsics.throwUninitializedPropertyAccessException((String)"mainScreen");
                v1 = null;
            }
            v2 = ((StatWidget)v1).getStatTabIndex();
        } else lbl-1000:
        // 2 sources

        {
            v2 = 0;
        }
        subIndex = v2;
        this.mainScreenIndex = screen;
        if (this.mainScreen != null) {
            v3 = this.mainScreen;
            if (v3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException((String)"mainScreen");
                v3 = null;
            }
            this.m_169411_((GuiEventListener)v3);
        }
        if (this.sideScreenIndex == 1) {
            Summary.displaySideScreen$default(this, 0, null, 2, null);
        }
        $this$forEachIndexed$iv = this.summaryTabs;
        $i$f$forEachIndexed = false;
        index$iv = 0;
        for (T item$iv : $this$forEachIndexed$iv) {
            if ((var8_9 = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            var9_10 = (SummaryTab)item$iv;
            index = var8_9;
            $i$a$-forEachIndexed-Summary$displayMainScreen$2 = false;
            if (index == screen) {
                SummaryTab.toggleTab$default((SummaryTab)item, false, 1, null);
                continue;
            }
            item.toggleTab(false);
        }
        x = (this.f_96543_ - 331) / 2;
        y = (this.f_96544_ - 161) / 2;
        switch (screen) {
            case 0: {
                this.mainScreen = new InfoWidget(x + 77, y + 12, this.getSelectedPokemon$common());
                break;
            }
            case 1: {
                this.mainScreen = new MovesWidget(x + 77, y + 12, this);
                break;
            }
            case 2: {
                this.mainScreen = new StatWidget(x + 77, y + 12, this.getSelectedPokemon$common(), subIndex);
            }
        }
        if ((v4 = this.mainScreen) == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"mainScreen");
            v4 = null;
        }
        this.m_142416_((GuiEventListener)v4);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final void displaySideScreen(int screen, @Nullable Move move) {
        this.sideScreenIndex = screen;
        if (this.sideScreen != null) {
            this.m_169411_(this.getSideScreen());
        }
        x = (this.f_96543_ - 331) / 2;
        y = (this.f_96544_ - 161) / 2;
        switch (screen) {
            case 0: {
                this.setSideScreen((GuiEventListener)new PartyWidget(x + 216, y + 24, CollectionsKt.contains((Iterable)CobblemonClient.INSTANCE.getStorage().getMyParty(), (Object)this.getSelectedPokemon$common()), this, (List<? extends Pokemon>)this.party));
                break;
            }
            case 1: {
                v0 = this.mainScreen;
                if (v0 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException((String)"mainScreen");
                    v0 = null;
                }
                if (!((movesWidget = v0) instanceof MovesWidget) || move == null) break;
                var8_7 = var7_6 = new MoveSwapScreen(x + 216, y + 22, (MovesWidget)movesWidget, move);
                var27_8 = this;
                $i$a$-also-Summary$displaySideScreen$2 = false;
                pokemon = this.getSelectedPokemon$common();
                var11_11 = pokemon.getAllAccessibleMoves();
                $i$f$filter = false;
                var13_13 = $this$filter$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$filterTo = false;
                for (T element$iv$iv : $this$filterTo$iv$iv) {
                    template = (MoveTemplate)element$iv$iv;
                    $i$a$-filter-Summary$displaySideScreen$2$1 = false;
                    $this$none$iv = pokemon.getMoveSet();
                    $i$f$none = false;
                    if (!($this$none$iv instanceof Collection) || !((Collection)$this$none$iv).isEmpty()) ** GOTO lbl34
                    v1 = true;
                    ** GOTO lbl42
lbl34:
                    // 2 sources

                    for (T element$iv : $this$none$iv) {
                        it = (Move)element$iv;
                        $i$a$-none-Summary$displaySideScreen$2$1$1 = false;
                        if (!Intrinsics.areEqual((Object)it.getTemplate(), (Object)template)) continue;
                        v1 = false;
                        ** GOTO lbl42
                    }
                    v1 = true;
lbl42:
                    // 3 sources

                    if (!v1) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filter$iv = (List)destination$iv$iv;
                $i$f$map = false;
                $this$filterTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    template = (MoveTemplate)item$iv$iv;
                    var26_31 = destination$iv$iv;
                    $i$a$-map-Summary$displaySideScreen$2$2 = false;
                    var20_22 = pokemon.getBenchedMoves();
                    for (Iterator<T> var22_25 : var20_22) {
                        it = (BenchedMove)var22_25 /* !! */ ;
                        $i$a$-find-Summary$displaySideScreen$2$2$benched$1 = false;
                        if (!Intrinsics.areEqual((Object)it.getMoveTemplate(), (Object)template)) continue;
                        v2 /* !! */  = var22_25 /* !! */ ;
                        ** GOTO lbl64
                    }
                    v2 /* !! */  = null;
lbl64:
                    // 2 sources

                    v3 = benched = (BenchedMove)v2 /* !! */ ;
                    var26_31.add(new MoveSwapScreen.MoveSlot((MoveSwapScreen)switchPane, template, v3 != null ? v3.getPpRaisedStages() : 0));
                }
                $this$map$iv = (List)destination$iv$iv;
                $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    it = (MoveSwapScreen.MoveSlot)element$iv;
                    $i$a$-forEach-Summary$displaySideScreen$2$3 = false;
                    switchPane.addEntry(it);
                }
                var27_8.setSideScreen((GuiEventListener)var7_6);
                break;
            }
            case 2: {
                this.setSideScreen((GuiEventListener)new EvolutionSelectScreen(x + 216, y + 22, this.getSelectedPokemon$common()));
            }
        }
        element = this.getSideScreen();
        if (element instanceof Renderable && element instanceof NarratableEntry) {
            this.m_142416_(element);
        }
    }

    public static /* synthetic */ void displaySideScreen$default(Summary summary, int n, Move move, int n2, Object object) {
        if ((n2 & 2) != 0) {
            move = null;
        }
        summary.displaySideScreen(n, move);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        PersistentStatus status;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        this.getSchedulingTracker().update(delta / 20.0f);
        int x = (this.f_96543_ - 331) / 2;
        int y = (this.f_96544_ - 161) / 2;
        PoseStack matrices = context.m_280168_();
        ResourceLocation resourceLocation = portraitBackgroundResource;
        int n = x + 6;
        int n2 = y + 32;
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, resourceLocation, n, n2, 66, 66, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        resourceLocation = baseResource;
        GuiUtilsKt.blitk$default(matrices, resourceLocation, x, y, 161, 331, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        PersistentStatusContainer persistentStatusContainer = this.getSelectedPokemon$common().getStatus();
        PersistentStatus persistentStatus = status = persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null;
        if (this.getSelectedPokemon$common().isFainted() || status != null) {
            String string;
            if (this.getSelectedPokemon$common().isFainted()) {
                string = "fnt";
            } else {
                PersistentStatus persistentStatus2 = status;
                string = persistentStatus2 != null ? persistentStatus2.getShowdownName() : null;
            }
            String statusName = string;
            GuiUtilsKt.blitk$default(matrices, MiscUtils.cobblemonResource("textures/gui/battle/battle_status_" + statusName + ".png"), x + 34, y + 4, 7, 39, 35, null, 74, null, null, null, null, null, null, false, 0.0f, 130688, null);
            GuiUtilsKt.blitk$default(matrices, MiscUtils.cobblemonResource("textures/gui/summary/status_trim.png"), x + 34, y + 5, 6, 3, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
            ResourceLocation resourceLocation2 = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.status." + statusName, new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.status.$statusName\")");
            RenderHelperKt.drawScaledText$default(context, resourceLocation2, TextKt.bold(mutableComponent), x + 39, y + 3, 0.0f, null, 0, 0, false, false, null, null, 8160, null);
        }
        ResourceLocation ballResource = MiscUtils.cobblemonResource("textures/item/poke_balls/" + this.getSelectedPokemon$common().getCaughtBall().getName().m_135815_() + ".png");
        double d = ((double)x + 3.5) / (double)0.5f;
        float f = (float)(y + 15) / 0.5f;
        GuiUtilsKt.blitk$default(matrices, ballResource, d, Float.valueOf(f), 16, 16, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        if (this.getSelectedPokemon$common().getGender() != Gender.GENDERLESS) {
            boolean isMale = this.getSelectedPokemon$common().getGender() == Gender.MALE;
            MutableComponent textSymbol = isMale ? TextKt.bold(TextKt.text("\u2642")) : TextKt.bold(TextKt.text("\u2640"));
            RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), textSymbol, x + 69, (double)y + 14.5, 0.0f, null, 0, isMale ? 3329023 : 16536660, false, true, null, null, 6880, null);
        }
        ResourceLocation resourceLocation3 = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.lv", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.lv\")");
        RenderHelperKt.drawScaledText$default(context, resourceLocation3, TextKt.bold(mutableComponent), x + 6, (double)y + 4.5, 0.0f, null, 0, 0, false, true, null, null, 7136, null);
        RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(TextKt.text(String.valueOf(this.getSelectedPokemon$common().getLevel()))), x + 19, (double)y + 4.5, 0.0f, null, 0, 0, false, true, null, null, 7136, null);
        if (this.getSelectedPokemon$common().getShiny()) {
            ResourceLocation isMale = iconShinyResource;
            double textSymbol = ((double)x + 62.5) / (double)0.5f;
            double d2 = ((double)y + 33.5) / (double)0.5f;
            GuiUtilsKt.blitk$default(matrices, isMale, textSymbol, d2, 16, 16, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        }
        ResourceLocation isMale = this.getSelectedPokemon$common().getSecondaryType() != null ? typeSpacerDoubleResource : typeSpacerResource;
        double textSymbol = ((double)x + 5.5) / (double)0.5f;
        float f2 = (float)(y + 126) / 0.5f;
        GuiUtilsKt.blitk$default(matrices, isMale, textSymbol, Float.valueOf(f2), 24, 134, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        ItemStack heldItem2 = this.getSelectedPokemon$common().heldItemNoCopy$common();
        int itemX = x + 3;
        int itemY = y + 104;
        if (!heldItem2.m_41619_()) {
            context.m_280480_(heldItem2, itemX, itemY);
            context.m_280370_(Minecraft.m_91087_().f_91062_, heldItem2, itemX, itemY);
        }
        MutableComponent mutableComponent2 = LocalizationUtilsKt.lang("held_item", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"lang(\"held_item\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent2, x + 27, (double)y + 114.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
        new TypeIcon(x + 39, y + 123, this.getSelectedPokemon$common().getPrimaryType(), this.getSelectedPokemon$common().getSecondaryType(), true, false, 0.0f, 0.0f, 0.0f, 480, null).render(context);
        ResourceLocation resourceLocation4 = sideSpacerResource;
        float f3 = (float)(x + 217) / 0.5f;
        float f4 = (float)(y + 141) / 0.5f;
        GuiUtilsKt.blitk$default(matrices, resourceLocation4, Float.valueOf(f3), Float.valueOf(f4), 14, 144, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        super.m_88315_(context, mouseX, mouseY, delta);
        if (heldItem2.m_41619_()) return;
        f3 = itemX;
        f4 = (float)itemX + (float)16;
        float f5 = mouseX;
        if (!(f3 <= f5)) return;
        if (!(f5 <= f4)) return;
        boolean bl = true;
        if (!bl) return;
        f3 = itemY;
        f4 = (float)itemY + (float)16;
        f5 = mouseY;
        if (!(f3 <= f5)) return;
        if (!(f5 <= f4)) return;
        boolean bl2 = true;
        if (!bl2) return;
        boolean bl3 = true;
        boolean itemHovered = bl3;
        if (!itemHovered) return;
        context.m_280153_(Minecraft.m_91087_().f_91062_, heldItem2, mouseX, mouseY);
    }

    public boolean m_7043_() {
        return false;
    }

    public boolean m_6050_(double mouseX, double mouseY, double amount) {
        boolean bl;
        block3: {
            List list = this.m_6702_();
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"children()");
            Iterable $this$any$iv = list;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    GuiEventListener it = (GuiEventListener)element$iv;
                    boolean bl2 = false;
                    if (!it.m_6050_(mouseX, mouseY, amount)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    public boolean m_7979_(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.sideScreenIndex == 1 || this.sideScreenIndex == 2) {
            this.getSideScreen().m_7979_(mouseX, mouseY, button, deltaX, deltaY);
        }
        return super.m_7979_(mouseX, mouseY, button, deltaX, deltaY);
    }

    public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
        if ((keyCode == 257 || keyCode == 335) && this.nicknameEntryWidget != null) {
            NicknameEntryWidget nicknameEntryWidget = this.nicknameEntryWidget;
            if (nicknameEntryWidget == null) {
                Intrinsics.throwUninitializedPropertyAccessException((String)"nicknameEntryWidget");
                nicknameEntryWidget = null;
            }
            if (nicknameEntryWidget.m_93696_()) {
                this.m_7522_(null);
            }
        }
        if (Cobblemon.INSTANCE.getConfig().getEnableDebugKeys()) {
            PokemonPoseableModel model = (PokemonPoseableModel)PokemonModelRepository.INSTANCE.getPoser(this.getSelectedPokemon$common().getSpecies().getResourceIdentifier(), this.getSelectedPokemon$common().getAspects());
            if (keyCode == 265) {
                Vec3 vec3 = model.getProfileTranslation().m_82520_(0.0, -0.01, 0.0);
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"model.profileTranslation.add(0.0, -0.01, 0.0)");
                model.setProfileTranslation(vec3);
            }
            if (keyCode == 264) {
                Vec3 vec3 = model.getProfileTranslation().m_82520_(0.0, 0.01, 0.0);
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"model.profileTranslation.add(0.0, 0.01, 0.0)");
                model.setProfileTranslation(vec3);
            }
            if (keyCode == 263) {
                Vec3 vec3 = model.getProfileTranslation().m_82520_(-0.01, 0.0, 0.0);
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"model.profileTranslation.add(-0.01, 0.0, 0.0)");
                model.setProfileTranslation(vec3);
            }
            if (keyCode == 262) {
                Vec3 vec3 = model.getProfileTranslation().m_82520_(0.01, 0.0, 0.0);
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"model.profileTranslation.add(0.01, 0.0, 0.0)");
                model.setProfileTranslation(vec3);
            }
            if (keyCode == 61) {
                model.setProfileScale(model.getProfileScale() + 0.01f);
            }
            if (keyCode == 45) {
                model.setProfileScale(model.getProfileScale() - 0.01f);
            }
        }
        return super.m_7933_(keyCode, scanCode, modifiers);
    }

    public final void playSound(@NotNull SoundEvent soundEvent) {
        Intrinsics.checkNotNullParameter((Object)soundEvent, (String)"soundEvent");
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)soundEvent, (float)1.0f));
    }

    public void m_7379_() {
        if (Cobblemon.INSTANCE.getConfig().getEnableDebugKeys()) {
            PokemonPoseableModel model = (PokemonPoseableModel)PokemonModelRepository.INSTANCE.getPoser(this.getSelectedPokemon$common().getSpecies().getResourceIdentifier(), this.getSelectedPokemon$common().getAspects());
            LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
            if (localPlayer != null) {
                localPlayer.m_213846_(Component.m_130674_((String)("Profile Translation: " + model.getProfileTranslation())));
            }
            LocalPlayer localPlayer2 = Minecraft.m_91087_().f_91074_;
            if (localPlayer2 != null) {
                localPlayer2.m_213846_(Component.m_130674_((String)("Profile Scale: " + model.getProfileScale())));
            }
            Cobblemon.INSTANCE.getLOGGER().info("override var profileTranslation = Vec3d(" + model.getProfileTranslation().f_82479_ + ", " + model.getProfileTranslation().f_82480_ + ", " + model.getProfileTranslation().f_82481_ + ")");
            Cobblemon.INSTANCE.getLOGGER().info("override var profileScale = " + model.getProfileScale() + "F");
        }
        super.m_7379_();
    }

    @Override
    @NotNull
    public ScheduledTask momentarily(@NotNull Function0<Unit> action2) {
        return Schedulable.DefaultImpls.momentarily(this, action2);
    }

    @Override
    @NotNull
    public ScheduledTask after(float seconds, @NotNull Function0<Unit> action2) {
        return Schedulable.DefaultImpls.after(this, seconds, action2);
    }

    @Override
    @NotNull
    public ScheduledTask lerp(float seconds, @NotNull Function1<? super Float, Unit> action2) {
        return Schedulable.DefaultImpls.lerp(this, seconds, action2);
    }

    @Override
    @NotNull
    public ScheduledTask.Builder taskBuilder() {
        return Schedulable.DefaultImpls.taskBuilder(this);
    }

    private static final void init$lambda$1(Summary this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        this$0.momentarily((Function0<Unit>)((Function0)new Function0<Unit>(this$0){
            final /* synthetic */ Summary this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            public final void invoke() {
                Summary.displaySideScreen$default(this.this$0, this.this$0.getSideScreenIndex() == 2 ? 0 : 2, null, 2, null);
            }
        }));
    }

    private static final void init$lambda$2(Summary this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        if (this$0.mainScreenIndex != 0) {
            this$0.displayMainScreen(0);
            this$0.playSound(CobblemonSounds.GUI_CLICK);
        }
    }

    private static final void init$lambda$3(Summary this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        if (this$0.mainScreenIndex != 1) {
            this$0.displayMainScreen(1);
            this$0.playSound(CobblemonSounds.GUI_CLICK);
        }
    }

    private static final void init$lambda$4(Summary this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        if (this$0.mainScreenIndex != 2) {
            this$0.displayMainScreen(2);
            this$0.playSound(CobblemonSounds.GUI_CLICK);
        }
    }

    private static final void init$lambda$6(Summary this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        this$0.playSound(CobblemonSounds.GUI_CLICK);
        Minecraft.m_91087_().m_91152_(null);
    }

    public /* synthetic */ Summary(Collection party, boolean editable, int selection, DefaultConstructorMarker $constructor_marker) {
        this(party, editable, selection);
    }

    public static final /* synthetic */ boolean access$isOpen(Summary $this) {
        return $this.isOpen();
    }

    public static final /* synthetic */ AbstractWidget access$getMainScreen$p(Summary $this) {
        return $this.mainScreen;
    }

    public static final /* synthetic */ void access$displayMainScreen(Summary $this, int screen) {
        $this.displayMainScreen(screen);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b$\u0010%J1\u0010\n\u001a\u00020\t2\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00022\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\rR\u0014\u0010\u000f\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\rR\u0014\u0010\u0010\u001a\u00020\u00078\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\rR\u0014\u0010\u0011\u001a\u00020\u00078\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\rR\u0014\u0010\u0012\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\rR\u0014\u0010\u0013\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\rR\u0014\u0010\u0014\u001a\u00020\u00078\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\rR\u0014\u0010\u0016\u001a\u00020\u00158\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00078\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\rR\u0014\u0010\u001a\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u001bR\u0017\u0010\u001d\u001a\u00020\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001b\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b \u0010\u001bR\u0014\u0010!\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\u001bR\u0014\u0010\"\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\"\u0010\u001bR\u0014\u0010#\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b#\u0010\u001b\u00a8\u0006&"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/Summary$Companion;", "", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "party", "", "editable", "", "selection", "", "open", "(Ljava/util/Collection;ZI)V", "BASE_HEIGHT", "I", "BASE_WIDTH", "EVOLVE", "INFO", "MOVES", "MOVE_SWAP", "PARTY", "PORTRAIT_SIZE", "", "SCALE", "F", "STATS", "Lnet/minecraft/resources/ResourceLocation;", "baseResource", "Lnet/minecraft/resources/ResourceLocation;", "evolveButtonResource", "iconShinyResource", "getIconShinyResource", "()Lnet/minecraft/resources/ResourceLocation;", "portraitBackgroundResource", "sideSpacerResource", "typeSpacerDoubleResource", "typeSpacerResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getIconShinyResource() {
            return iconShinyResource;
        }

        public final void open(@NotNull Collection<? extends Pokemon> party, boolean editable, int selection) {
            Intrinsics.checkNotNullParameter(party, (String)"party");
            Minecraft mc = Minecraft.m_91087_();
            Summary screen = new Summary(party, editable, selection, null);
            mc.m_91152_((Screen)screen);
        }

        public static /* synthetic */ void open$default(Companion companion, Collection collection, boolean bl, int n, int n2, Object object) {
            if ((n2 & 2) != 0) {
                bl = true;
            }
            if ((n2 & 4) != 0) {
                n = 0;
            }
            companion.open(collection, bl, n);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

