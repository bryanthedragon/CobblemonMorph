/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.narration.NarratableEntry$NarrationPriority
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.Mth
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownPokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.SwitchActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.SingleActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleOverlay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleActionSelection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleBackButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 -2\u00020\u0001:\u0002-.B\u0017\u0012\u0006\u0010(\u001a\u00020'\u0012\u0006\u0010*\u001a\u00020)\u00a2\u0006\u0004\b+\u0010,J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ'\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J/\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u0018H\u0014\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001d\u001a\u00020\u001c8\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u001d\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0!8\u0006\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\u00a8\u0006/"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleSwitchPokemonSelection;", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleActionSelection;", "Lnet/minecraft/client/gui/narration/NarrationElementOutput;", "builder", "", "appendDefaultNarrations", "(Lnet/minecraft/client/gui/narration/NarrationElementOutput;)V", "Lnet/minecraft/client/gui/Selectable$SelectionType;", "getType", "()Lnet/minecraft/client/gui/narration/NarratableEntry$NarrationPriority;", "", "mouseX", "mouseY", "", "button", "", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleBackButton;", "backButton", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleBackButton;", "getBackButton", "()Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleBackButton;", "", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleSwitchPokemonSelection$SwitchTile;", "tiles", "Ljava/util/List;", "getTiles", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;", "battleGUI", "Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;", "request", "<init>", "(Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;)V", "Companion", "SwitchTile", "common"})
@SourceDebugExtension(value={"SMAP\nBattleSwitchPokemonSelection.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleSwitchPokemonSelection.kt\ncom/cobblemon/mod/common/client/gui/battle/subscreen/BattleSwitchPokemonSelection\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,151:1\n1603#2,9:152\n1855#2:161\n1856#2:163\n1612#2:164\n800#2,11:165\n1549#2:176\n1620#2,3:177\n1603#2,9:180\n1855#2:189\n1856#2:192\n1612#2:193\n766#2:194\n857#2,2:195\n766#2:197\n857#2:198\n1549#2:199\n1620#2,3:200\n858#2:203\n766#2:204\n857#2,2:205\n1864#2,3:207\n1855#2,2:210\n1#3:162\n1#3:190\n1#3:191\n1#3:212\n*S KotlinDebug\n*F\n+ 1 BattleSwitchPokemonSelection.kt\ncom/cobblemon/mod/common/client/gui/battle/subscreen/BattleSwitchPokemonSelection\n*L\n93#1:152,9\n93#1:161\n93#1:163\n93#1:164\n93#1:165,11\n93#1:176\n93#1:177,3\n95#1:180,9\n95#1:189\n95#1:192\n95#1:193\n100#1:194\n100#1:195,2\n107#1:197\n107#1:198\n107#1:199\n107#1:200,3\n107#1:203\n108#1:204\n108#1:205,2\n110#1:207,3\n125#1:210,2\n93#1:162\n95#1:191\n*E\n"})
public final class BattleSwitchPokemonSelection
extends BattleActionSelection {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final List<SwitchTile> tiles;
    @NotNull
    private final BattleBackButton backButton;
    public static final int SWITCH_TILE_WIDTH = 131;
    public static final int SWITCH_TILE_HEIGHT = 40;
    public static final float SWITCH_TILE_HORIZONTAL_SPACING = 10.0f;
    public static final float SWITCH_TILE_VERTICAL_SPACING = 5.0f;

    /*
     * WARNING - void declaration
     */
    public BattleSwitchPokemonSelection(@NotNull BattleGUI battleGUI, @NotNull SingleActionRequest request) {
        Pair it;
        Iterable $this$filterTo$iv$iv;
        Iterable $this$filter$iv;
        Object it2;
        void $this$mapNotNullTo$iv$iv;
        Iterable $this$mapNotNull$iv;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        void $this$filterIsInstanceTo$iv$iv;
        Iterable $this$filterIsInstance$iv;
        void $this$mapNotNullTo$iv$iv2;
        Intrinsics.checkNotNullParameter((Object)((Object)battleGUI), (String)"battleGUI");
        Intrinsics.checkNotNullParameter((Object)request, (String)"request");
        int n = Mth.m_14167_((float)((float)(Minecraft.m_91087_().m_91268_().m_85446_() / 2) - 65.0f));
        MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("switch_pokemon", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"switch_pokemon\")");
        super(battleGUI, request, 12, n, 250, 100, mutableComponent);
        this.tiles = new ArrayList();
        this.backButton = new BattleBackButton((float)this.m_252754_() - 3.0f, (float)Minecraft.m_91087_().m_91268_().m_85446_() - 22.0f);
        ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
        Intrinsics.checkNotNull((Object)clientBattle);
        List<SingleActionRequest> pendingActionRequests = clientBattle.getPendingActionRequests();
        Iterable $this$mapNotNull$iv2 = pendingActionRequests;
        boolean $i$f$mapNotNull = false;
        Iterable iterable = $this$mapNotNull$iv2;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        Iterator $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv2;
        boolean $i$f$forEach = false;
        Object object = $this$forEach$iv$iv$iv.iterator();
        while (object.hasNext()) {
            ShowdownActionResponse it$iv$iv;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = object.next();
            boolean bl = false;
            SingleActionRequest it3 = (SingleActionRequest)element$iv$iv;
            boolean bl2 = false;
            if (it3.getResponse() == null) continue;
            boolean bl3 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        $this$mapNotNull$iv2 = (List)destination$iv$iv;
        boolean $i$f$filterIsInstance = false;
        $this$mapNotNullTo$iv$iv2 = $this$filterIsInstance$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof SwitchActionResponse)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filterIsInstance$iv = (List)destination$iv$iv;
        boolean $i$f$map22 = false;
        $this$filterIsInstanceTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it4;
            object = (SwitchActionResponse)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it4.getNewPokemonId());
        }
        List switchingInPokemon = (List)destination$iv$iv;
        ShowdownSide showdownSide = request.getSide();
        Intrinsics.checkNotNull((Object)showdownSide);
        Iterable $i$f$map22 = showdownSide.getPokemon();
        boolean $i$f$mapNotNull2 = false;
        destination$iv$iv = $this$mapNotNull$iv;
        Collection destination$iv$iv2 = new ArrayList();
        boolean $i$f$mapNotNullTo2 = false;
        void $this$forEach$iv$iv$iv2 = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach2 = false;
        Iterator bl = $this$forEach$iv$iv$iv2.iterator();
        while (bl.hasNext()) {
            Pair pair;
            Object v5;
            ShowdownPokemon showdownPokemon;
            block13: {
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = bl.next();
                boolean bl4 = false;
                showdownPokemon = (ShowdownPokemon)element$iv$iv;
                boolean bl5 = false;
                ClientBattleActor clientBattleActor = battleGUI.getActor();
                Intrinsics.checkNotNull((Object)clientBattleActor);
                Iterable bl3 = clientBattleActor.getPokemon();
                for (Object t : bl3) {
                    it2 = (Pokemon)t;
                    boolean bl6 = false;
                    if (!Intrinsics.areEqual((Object)((Pokemon)it2).getUuid(), (Object)showdownPokemon.getUuid())) continue;
                    v5 = t;
                    break block13;
                }
                v5 = null;
            }
            Pokemon pokemon = v5;
            if (pokemon != null) {
                Pokemon it5 = pokemon;
                boolean bl7 = false;
                pair = TuplesKt.to((Object)showdownPokemon, (Object)it5);
            } else {
                pair = null;
            }
            if (pair == null) continue;
            Pair it$iv$iv = pair;
            boolean bl8 = false;
            destination$iv$iv2.add(it$iv$iv);
        }
        $this$mapNotNull$iv = (List)destination$iv$iv2;
        boolean $i$f$filter = false;
        $this$mapNotNullTo$iv$iv = $this$filter$iv;
        destination$iv$iv2 = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            it = (Pair)element$iv$iv;
            boolean bl9 = false;
            boolean bl10 = request.getSide().getPokemon().get(0).getReviving() ? StringsKt.contains$default((CharSequence)((ShowdownPokemon)it.getFirst()).getCondition(), (CharSequence)"fnt", (boolean)false, (int)2, null) : !StringsKt.contains$default((CharSequence)((ShowdownPokemon)it.getFirst()).getCondition(), (CharSequence)"fnt", (boolean)false, (int)2, null);
            if (!bl10) continue;
            destination$iv$iv2.add(element$iv$iv);
        }
        $this$filter$iv = (List)destination$iv$iv2;
        $i$f$filter = false;
        $this$filterTo$iv$iv = $this$filter$iv;
        destination$iv$iv2 = new ArrayList();
        $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            void $this$mapTo$iv$iv2;
            it = (Pair)element$iv$iv;
            boolean bl11 = false;
            ClientBattleActor clientBattleActor = battleGUI.getActor();
            Intrinsics.checkNotNull((Object)clientBattleActor);
            Iterable $this$map$iv2 = clientBattleActor.getActivePokemon();
            boolean $i$f$map = false;
            Iterable showdownPokemon = $this$map$iv2;
            Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv2, (int)10));
            boolean $i$f$mapTo2 = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv2) {
                it2 = (ActiveClientBattlePokemon)item$iv$iv;
                Collection collection = destination$iv$iv3;
                boolean bl12 = false;
                ClientBattlePokemon clientBattlePokemon = ((ActiveClientBattlePokemon)it2).getBattlePokemon();
                collection.add(clientBattlePokemon != null ? clientBattlePokemon.getUuid() : null);
            }
            if (!(!((List)destination$iv$iv3).contains(((Pokemon)it.getSecond()).getUuid()))) continue;
            destination$iv$iv2.add(element$iv$iv);
        }
        $this$filter$iv = (List)destination$iv$iv2;
        $i$f$filter = false;
        $this$filterTo$iv$iv = $this$filter$iv;
        destination$iv$iv2 = new ArrayList();
        $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            it = (Pair)element$iv$iv;
            boolean bl13 = false;
            if (!(!switchingInPokemon.contains(((Pokemon)it.getSecond()).getUuid()))) continue;
            destination$iv$iv2.add(element$iv$iv);
        }
        List showdownPokemonToPokemon = (List)destination$iv$iv2;
        Iterable $this$forEachIndexed$iv = showdownPokemonToPokemon;
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            int n2;
            if ((n2 = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Pair pair = (Pair)item$iv;
            int index = n2;
            boolean bl14 = false;
            ShowdownPokemon showdownPokemon = (ShowdownPokemon)pair.component1();
            Pokemon pokemon = (Pokemon)pair.component2();
            int row = index / 2;
            int column = index % 2;
            float x = (float)this.m_252754_() + (float)column * 141.0f;
            float y = (float)this.m_252907_() + (float)row * 45.0f;
            this.tiles.add(new SwitchTile(this, x, y, pokemon, showdownPokemon));
        }
    }

    @NotNull
    public final List<SwitchTile> getTiles() {
        return this.tiles;
    }

    @NotNull
    public final BattleBackButton getBackButton() {
        return this.backButton;
    }

    protected void m_87963_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (this.getOpacity() <= 0.05f) {
            return;
        }
        Iterable $this$forEach$iv = this.tiles;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SwitchTile it = (SwitchTile)element$iv;
            boolean bl = false;
            it.render(context, mouseX, mouseY, delta);
        }
        PoseStack poseStack = context.m_280168_();
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"context.matrices");
        this.backButton.render(poseStack, mouseX, mouseY, delta);
    }

    @Override
    public boolean m_6375_(double mouseX, double mouseY, int button) {
        Object v1;
        block3: {
            if (this.backButton.isHovered(mouseX, mouseY)) {
                this.getBattleGUI().changeActionSelection(null);
                SoundManager soundManager = Minecraft.m_91087_().m_91106_();
                Intrinsics.checkNotNullExpressionValue((Object)soundManager, (String)"getInstance().soundManager");
                this.m_7435_(soundManager);
                return true;
            }
            Iterable iterable = this.tiles;
            for (Object t : iterable) {
                SwitchTile it = (SwitchTile)t;
                boolean bl = false;
                if (!it.isHovered(mouseX, mouseY)) continue;
                v1 = t;
                break block3;
            }
            v1 = null;
        }
        SwitchTile switchTile = v1;
        if (switchTile == null) {
            return false;
        }
        SwitchTile clicked = switchTile;
        Pokemon pokemon = clicked.getPokemon();
        SoundManager soundManager = Minecraft.m_91087_().m_91106_();
        Intrinsics.checkNotNullExpressionValue((Object)soundManager, (String)"getInstance().soundManager");
        this.m_7435_(soundManager);
        BattleGUI battleGUI = this.getBattleGUI();
        SingleActionRequest singleActionRequest = this.getRequest();
        UUID uUID = pokemon.getUuid();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon.uuid");
        battleGUI.selectAction(singleActionRequest, new SwitchActionResponse(uUID));
        return true;
    }

    @Override
    protected void m_168802_(@NotNull NarrationElementOutput builder) {
        Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
    }

    public void m_7435_(@NotNull SoundManager soundManager) {
        Intrinsics.checkNotNullParameter((Object)soundManager, (String)"soundManager");
        soundManager.m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)CobblemonSounds.GUI_CLICK, (float)1.0f));
    }

    @NotNull
    public NarratableEntry.NarrationPriority m_142684_() {
        return NarratableEntry.NarrationPriority.HOVERED;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00058\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0007R\u0014\u0010\t\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleSwitchPokemonSelection$Companion;", "", "", "SWITCH_TILE_HEIGHT", "I", "", "SWITCH_TILE_HORIZONTAL_SPACING", "F", "SWITCH_TILE_VERTICAL_SPACING", "SWITCH_TILE_WIDTH", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u0012\u0006\u0010\u001e\u001a\u00020\n\u0012\u0006\u0010\"\u001a\u00020\n\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u00a2\u0006\u0004\b$\u0010%J\u001d\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J-\u0010\r\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\r\u0010\u000eR\u0017\u0010\u0010\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0015\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u001a\u001a\u00020\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u0017\u0010\u001e\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!R\u0017\u0010\"\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010\u001f\u001a\u0004\b#\u0010!\u00a8\u0006&"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleSwitchPokemonSelection$SwitchTile;", "", "", "mouseX", "mouseY", "", "isHovered", "(DD)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "deltaTicks", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;DDF)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleSwitchPokemonSelection;", "selection", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleSwitchPokemonSelection;", "getSelection", "()Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleSwitchPokemonSelection;", "Lcom/cobblemon/mod/common/battles/ShowdownPokemon;", "showdownPokemon", "Lcom/cobblemon/mod/common/battles/ShowdownPokemon;", "getShowdownPokemon", "()Lcom/cobblemon/mod/common/battles/ShowdownPokemon;", "x", "F", "getX", "()F", "y", "getY", "<init>", "(Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleSwitchPokemonSelection;FFLcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/battles/ShowdownPokemon;)V", "common"})
    public static final class SwitchTile {
        @NotNull
        private final BattleSwitchPokemonSelection selection;
        private final float x;
        private final float y;
        @NotNull
        private final Pokemon pokemon;
        @NotNull
        private final ShowdownPokemon showdownPokemon;

        public SwitchTile(@NotNull BattleSwitchPokemonSelection selection, float x, float y, @NotNull Pokemon pokemon, @NotNull ShowdownPokemon showdownPokemon) {
            Intrinsics.checkNotNullParameter((Object)((Object)selection), (String)"selection");
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)showdownPokemon, (String)"showdownPokemon");
            this.selection = selection;
            this.x = x;
            this.y = y;
            this.pokemon = pokemon;
            this.showdownPokemon = showdownPokemon;
        }

        @NotNull
        public final BattleSwitchPokemonSelection getSelection() {
            return this.selection;
        }

        public final float getX() {
            return this.x;
        }

        public final float getY() {
            return this.y;
        }

        @NotNull
        public final Pokemon getPokemon() {
            return this.pokemon;
        }

        @NotNull
        public final ShowdownPokemon getShowdownPokemon() {
            return this.showdownPokemon;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public final boolean isHovered(double mouseX, double mouseY) {
            float f = this.x;
            if (!(mouseX <= (double)(this.x + (float)131))) return false;
            if (!((double)f <= mouseX)) return false;
            boolean bl = true;
            if (!bl) return false;
            f = this.y;
            if (!(mouseY <= (double)(this.y + (float)40))) return false;
            if (!((double)f <= mouseY)) return false;
            return true;
        }

        public final void render(@NotNull GuiGraphics context, double mouseX, double mouseY, float deltaTicks) {
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            String[] stringArray = new String[]{" "};
            CharSequence charSequence = (CharSequence)StringsKt.split$default((CharSequence)this.showdownPokemon.getCondition(), (String[])stringArray, (boolean)false, (int)0, (int)6, null).get(0);
            stringArray = new String[]{"/"};
            List healthRatioSplits = StringsKt.split$default((CharSequence)charSequence, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
            stringArray = healthRatioSplits.size() == 1 ? TuplesKt.to((Object)0, (Object)0) : TuplesKt.to((Object)Integer.parseInt((String)healthRatioSplits.get(0)), (Object)this.pokemon.getHp());
            int hp = ((Number)stringArray.component1()).intValue();
            int maxHp = ((Number)stringArray.component2()).intValue();
            BattleOverlay battleOverlay2 = CobblemonClient.INSTANCE.getBattleOverlay();
            float f = this.x;
            float f2 = this.y;
            Species species = this.pokemon.getSpecies();
            int n = this.pokemon.getLevel();
            Set<String> set2 = this.pokemon.getAspects();
            MutableComponent mutableComponent = this.pokemon.getDisplayName();
            Gender gender = this.pokemon.getGender();
            PersistentStatusContainer persistentStatusContainer = this.pokemon.getStatus();
            PersistentStatus persistentStatus = persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null;
            float f3 = hp;
            float f4 = this.selection.getOpacity();
            BattleOverlay.drawBattleTile$default(battleOverlay2, context, f, f2, deltaTicks, false, species, n, set2, mutableComponent, gender, persistentStatus, null, null, f4, null, maxHp, f3, true, 16384, null);
        }
    }
}

