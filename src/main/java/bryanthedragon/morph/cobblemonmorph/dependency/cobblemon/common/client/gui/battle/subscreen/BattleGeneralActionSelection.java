/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.narration.NarratableEntry$NarrationPriority
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.SingleActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleActionSelection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleMoveSelection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleSwitchPokemonSelection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.ForfeitConfirmationSelection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.widgets.BattleOptionTile;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010,\u001a\u00020+\u0012\u0006\u0010.\u001a\u00020-\u00a2\u0006\u0004\b/\u00100J5\u0010\u000b\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\rH\u0014\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J'\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eJ/\u0010#\u001a\u00020\t2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u00022\u0006\u0010\"\u001a\u00020!H\u0014\u00a2\u0006\u0004\b#\u0010$R\u001d\u0010'\u001a\b\u0012\u0004\u0012\u00020&0%8\u0006\u00a2\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*\u00a8\u00061"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleGeneralActionSelection;", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleActionSelection;", "", "rank", "Lnet/minecraft/network/chat/MutableComponent;", "text", "Lnet/minecraft/resources/ResourceLocation;", "texture", "Lkotlin/Function0;", "", "onClick", "addOption", "(ILnet/minecraft/network/chat/MutableComponent;Lnet/minecraft/resources/ResourceLocation;Lkotlin/jvm/functions/Function0;)V", "Lnet/minecraft/client/gui/narration/NarrationElementOutput;", "builder", "appendDefaultNarrations", "(Lnet/minecraft/client/gui/narration/NarrationElementOutput;)V", "Lnet/minecraft/client/gui/Selectable$SelectionType;", "getType", "()Lnet/minecraft/client/gui/narration/NarratableEntry$NarrationPriority;", "", "mouseX", "mouseY", "button", "", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "", "Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleOptionTile;", "tiles", "Ljava/util/List;", "getTiles", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;", "battleGUI", "Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;", "request", "<init>", "(Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;)V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleGeneralActionSelection.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleGeneralActionSelection.kt\ncom/cobblemon/mod/common/client/gui/battle/subscreen/BattleGeneralActionSelection\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,112:1\n1747#2,3:113\n*S KotlinDebug\n*F\n+ 1 BattleGeneralActionSelection.kt\ncom/cobblemon/mod/common/client/gui/battle/subscreen/BattleGeneralActionSelection\n*L\n101#1:113,3\n*E\n"})
public final class BattleGeneralActionSelection
extends BattleActionSelection {
    @NotNull
    private final List<BattleOptionTile> tiles;

    public BattleGeneralActionSelection(final @NotNull BattleGUI battleGUI, final @NotNull SingleActionRequest request) {
        block3: {
            int rank;
            Intrinsics.checkNotNullParameter((Object)((Object)battleGUI), (String)"battleGUI");
            Intrinsics.checkNotNullParameter((Object)request, (String)"request");
            int n = Minecraft.m_91087_().m_91268_().m_85446_() - 85;
            MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("choose_action", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"choose_action\")");
            super(battleGUI, request, 12, n, 99, 35, mutableComponent);
            this.tiles = new ArrayList();
            int n2 = rank = 0;
            rank = n2 + 1;
            MutableComponent mutableComponent2 = LocalizationUtilsKt.battleLang("ui.fight", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"battleLang(\"ui.fight\")");
            this.addOption(n2, mutableComponent2, BattleGUI.Companion.getFightResource(), (Function0<Unit>)((Function0)new Function0<Unit>(){

                public final void invoke() {
                    SoundManager soundManager = Minecraft.m_91087_().m_91106_();
                    Intrinsics.checkNotNullExpressionValue((Object)soundManager, (String)"getInstance().soundManager");
                    this.m_7435_(soundManager);
                    battleGUI.changeActionSelection(new BattleMoveSelection(battleGUI, request));
                }
            }));
            ShowdownMoveset showdownMoveset = request.getMoveSet();
            if (!(showdownMoveset != null ? showdownMoveset.getTrapped() : false)) {
                n2 = rank;
                rank = n2 + 1;
                MutableComponent mutableComponent3 = LocalizationUtilsKt.battleLang("ui.switch", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"battleLang(\"ui.switch\")");
                this.addOption(n2, mutableComponent3, BattleGUI.Companion.getSwitchResource(), (Function0<Unit>)((Function0)new Function0<Unit>(){

                    public final void invoke() {
                        battleGUI.changeActionSelection(new BattleSwitchPokemonSelection(battleGUI, request));
                        SoundManager soundManager = Minecraft.m_91087_().m_91106_();
                        Intrinsics.checkNotNullExpressionValue((Object)soundManager, (String)"getInstance().soundManager");
                        this.m_7435_(soundManager);
                    }
                }));
            }
            ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
            if (clientBattle == null) break block3;
            ClientBattle battle2 = clientBattle;
            boolean bl = false;
            if (battle2.getBattleFormat().getBattleType().getPokemonPerSide() == 1 && ((ClientBattleActor)CollectionsKt.first(battle2.getSide2().getActors())).getType() == ActorType.WILD) {
                int n3 = rank;
                rank = n3 + 1;
                MutableComponent mutableComponent4 = LocalizationUtilsKt.battleLang("ui.capture", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent4, (String)"battleLang(\"ui.capture\")");
                this.addOption(n3, mutableComponent4, BattleGUI.Companion.getBagResource(), (Function0<Unit>)((Function0)new Function0<Unit>(this){
                    final /* synthetic */ BattleGeneralActionSelection this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    public final void invoke() {
                        ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
                        if (clientBattle != null) {
                            clientBattle.setMinimised(true);
                        }
                        LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
                        if (localPlayer != null) {
                            localPlayer.m_5661_((Component)LocalizationUtilsKt.battleLang("throw_pokeball_prompt", new Object[0]), false);
                        }
                        SoundManager soundManager = Minecraft.m_91087_().m_91106_();
                        Intrinsics.checkNotNullExpressionValue((Object)soundManager, (String)"getInstance().soundManager");
                        this.this$0.m_7435_(soundManager);
                    }
                }));
                n3 = rank;
                rank = n3 + 1;
                MutableComponent mutableComponent5 = LocalizationUtilsKt.battleLang("ui.run", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent5, (String)"battleLang(\"ui.run\")");
                this.addOption(n3, mutableComponent5, BattleGUI.Companion.getRunResource(), (Function0<Unit>)((Function0)new Function0<Unit>(this){
                    final /* synthetic */ BattleGeneralActionSelection this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    public final void invoke() {
                        ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
                        if (clientBattle != null) {
                            clientBattle.setMinimised(true);
                        }
                        LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
                        if (localPlayer != null) {
                            localPlayer.m_5661_((Component)LocalizationUtilsKt.battleLang("run_prompt", new Object[0]), false);
                        }
                        SoundManager soundManager = Minecraft.m_91087_().m_91106_();
                        Intrinsics.checkNotNullExpressionValue((Object)soundManager, (String)"getInstance().soundManager");
                        this.this$0.m_7435_(soundManager);
                    }
                }));
            } else {
                int n4 = rank;
                rank = n4 + 1;
                MutableComponent mutableComponent6 = LocalizationUtilsKt.battleLang("ui.forfeit", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent6, (String)"battleLang(\"ui.forfeit\")");
                this.addOption(n4, mutableComponent6, BattleGUI.Companion.getRunResource(), (Function0<Unit>)((Function0)new Function0<Unit>(battleGUI, request, this){
                    final /* synthetic */ BattleGUI $battleGUI;
                    final /* synthetic */ SingleActionRequest $request;
                    final /* synthetic */ BattleGeneralActionSelection this$0;
                    {
                        this.$battleGUI = $battleGUI;
                        this.$request = $request;
                        this.this$0 = $receiver;
                        super(0);
                    }

                    public final void invoke() {
                        this.$battleGUI.changeActionSelection(new ForfeitConfirmationSelection(this.$battleGUI, this.$request));
                        SoundManager soundManager = Minecraft.m_91087_().m_91106_();
                        Intrinsics.checkNotNullExpressionValue((Object)soundManager, (String)"getInstance().soundManager");
                        this.this$0.m_7435_(soundManager);
                    }
                }));
            }
        }
    }

    @NotNull
    public final List<BattleOptionTile> getTiles() {
        return this.tiles;
    }

    private final void addOption(int rank, MutableComponent text, ResourceLocation texture, Function0<Unit> onClick) {
        int startY = Minecraft.m_91087_().m_91268_().m_85446_() - 85;
        int x = rank % 2 == 0 ? 12 : 105;
        int y = rank > 1 ? startY + 26 + 3 : startY;
        this.tiles.add(new BattleOptionTile(this.getBattleGUI(), x, y, texture, text, onClick));
    }

    protected void m_87963_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        for (BattleOptionTile tile : this.tiles) {
            tile.m_88315_(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public boolean m_6375_(double mouseX, double mouseY, int button) {
        boolean bl;
        block3: {
            Iterable $this$any$iv = this.tiles;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    BattleOptionTile it = (BattleOptionTile)element$iv;
                    boolean bl2 = false;
                    if (!it.m_6375_(mouseX, mouseY, button)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
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
        return NarratableEntry.NarrationPriority.NONE;
    }
}

