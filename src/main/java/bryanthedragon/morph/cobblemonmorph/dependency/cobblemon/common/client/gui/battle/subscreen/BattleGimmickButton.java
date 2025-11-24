/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Regex
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ShowdownIdentifiable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleGimmickMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveTarget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.Targetable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleGimmickButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleMoveSelection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.DynamaxButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.ZPowerButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u0000 -2\u00020\u0001:\u0002-.B\u001f\u0012\u0006\u0010*\u001a\u00020)\u0012\u0006\u0010#\u001a\u00020\u000b\u0012\u0006\u0010'\u001a\u00020\u000b\u00a2\u0006\u0004\b+\u0010,J\u001d\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J-\u0010\u000e\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\r\u0010\u0010\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0014\u001a\n \u0013*\u0004\u0018\u00010\u00120\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00168\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0018R\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00198&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\"\u0010\u001e\u001a\u00020\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010\u0011\"\u0004\b!\u0010\"R\u0017\u0010#\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&R\u0017\u0010'\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b'\u0010$\u001a\u0004\b(\u0010&\u00a8\u0006/"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleGimmickButton;", "", "", "mouseX", "mouseY", "", "isHovered", "(DD)Z", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrices", "", "", "delta", "", "render", "(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V", "toggle", "()Z", "Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;", "kotlin.jvm.PlatformType", "sfx", "Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;", "", "texture", "Ljava/lang/String;", "", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection$MoveTile;", "getTiles", "()Ljava/util/List;", "tiles", "toggled", "Z", "getToggled", "setToggled", "(Z)V", "x", "F", "getX", "()F", "y", "getY", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset$Gimmick;", "gimmick", "<init>", "(Lcom/cobblemon/mod/common/battles/ShowdownMoveset$Gimmick;FF)V", "Companion", "GimmickTile", "common"})
public abstract class BattleGimmickButton {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final float x;
    private final float y;
    private boolean toggled;
    private final SimpleSoundInstance sfx;
    @NotNull
    private final String texture;
    public static final int WIDTH = 36;
    public static final int HEIGHT = 34;
    public static final float SCALE = 0.5f;
    public static final float XOFF = 18.0f;
    public static final float YOFF = 17.0f;
    public static final int SPACING = 26;

    public BattleGimmickButton(@NotNull ShowdownMoveset.Gimmick gimmick, float x, float y) {
        Intrinsics.checkNotNullParameter((Object)((Object)gimmick), (String)"gimmick");
        this.x = x;
        this.y = y;
        this.sfx = SimpleSoundInstance.m_119752_((SoundEvent)SoundEvents.f_11668_, (float)1.0f);
        this.texture = gimmick.getId();
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    @NotNull
    public abstract List<BattleMoveSelection.MoveTile> getTiles();

    public final boolean getToggled() {
        return this.toggled;
    }

    public final void setToggled(boolean bl) {
        this.toggled = bl;
    }

    public final void render(@NotNull PoseStack matrices, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, MiscUtilsKt.cobblemonResource("textures/gui/battle/battle_gimmick_" + this.texture + ".png"), Float.valueOf(this.x * (float)2), Float.valueOf(this.y * (float)2), 34, 36, null, this.toggled || this.isHovered(mouseX, mouseY) ? 34 : 0, null, 68, null, null, null, null, null, false, 0.5f, 64832, null);
    }

    public final boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= (double)this.x && mouseX <= (double)(this.x + 18.0f) && mouseY >= (double)this.y && mouseY <= (double)(this.y + 17.0f);
    }

    public final boolean toggle() {
        this.toggled = !this.toggled;
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)this.sfx);
        return this.toggled;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J-\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\r\u001a\u00020\f8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00068\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\f8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u000eR\u0014\u0010\u0012\u001a\u00020\f8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u000eR\u0014\u0010\u0013\u001a\u00020\u00068\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0010R\u0014\u0010\u0014\u001a\u00020\u00068\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0010\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleGimmickButton$Companion;", "", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset$Gimmick;", "gimmick", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection;", "moveSelection", "", "x", "y", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleGimmickButton;", "create", "(Lcom/cobblemon/mod/common/battles/ShowdownMoveset$Gimmick;Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection;FF)Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleGimmickButton;", "", "HEIGHT", "I", "SCALE", "F", "SPACING", "WIDTH", "XOFF", "YOFF", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final BattleGimmickButton create(@NotNull ShowdownMoveset.Gimmick gimmick, @NotNull BattleMoveSelection moveSelection, float x, float y) {
            Intrinsics.checkNotNullParameter((Object)((Object)gimmick), (String)"gimmick");
            Intrinsics.checkNotNullParameter((Object)((Object)moveSelection), (String)"moveSelection");
            return switch (WhenMappings.$EnumSwitchMapping$0[gimmick.ordinal()]) {
                case 1, 2 -> new ZPowerButton(moveSelection, x, y);
                case 3 -> new DynamaxButton(moveSelection, x, y);
                default -> new BattleGimmickButton(moveSelection, gimmick, x, y){
                    @NotNull
                    private List<? extends BattleMoveSelection.MoveTile> tiles;
                    {
                        void $this$mapTo$iv$iv;
                        void $this$map$iv;
                        Iterable iterable = $moveSelection.getBaseTiles();
                        create.1 var14_6 = this;
                        boolean $i$f$map = false;
                        void var7_8 = $this$map$iv;
                        Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                        boolean $i$f$mapTo = false;
                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                            void tile;
                            BattleMoveSelection.MoveTile moveTile = (BattleMoveSelection.MoveTile)item$iv$iv;
                            Collection collection = destination$iv$iv;
                            boolean bl = false;
                            collection.add(new GimmickTile($gimmick, $moveSelection, tile.getMove(), tile.getX(), tile.getY()));
                        }
                        var14_6.tiles = (List)destination$iv$iv;
                    }

                    @NotNull
                    public List<BattleMoveSelection.MoveTile> getTiles() {
                        return this.tiles;
                    }

                    public void setTiles(@NotNull List<? extends BattleMoveSelection.MoveTile> list) {
                        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
                        this.tiles = list;
                    }
                };
            };
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Metadata(mv={1, 8, 0}, k=3, xi=48)
        public final class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] nArray = new int[ShowdownMoveset.Gimmick.values().length];
                try {
                    nArray[ShowdownMoveset.Gimmick.Z_POWER.ordinal()] = 1;
                }
                catch (NoSuchFieldError noSuchFieldError) {
                    // empty catch block
                }
                try {
                    nArray[ShowdownMoveset.Gimmick.ULTRA_BURST.ordinal()] = 2;
                }
                catch (NoSuchFieldError noSuchFieldError) {
                    // empty catch block
                }
                try {
                    nArray[ShowdownMoveset.Gimmick.DYNAMAX.ordinal()] = 3;
                }
                catch (NoSuchFieldError noSuchFieldError) {
                    // empty catch block
                }
                $EnumSwitchMapping$0 = nArray;
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u001c\u001a\u00020\u001b\u0012\u0006\u0010\u001e\u001a\u00020\u001d\u0012\u0006\u0010 \u001a\u00020\u001f\u0012\u0006\u0010!\u001a\u00020\u001f\u00a2\u0006\u0004\b\"\u0010#R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR\u0016\u0010\r\u001a\u0004\u0018\u00010\n8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0011\u001a\u00020\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0015\u001a\u00020\u00128VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleGimmickButton$GimmickTile;", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection$MoveTile;", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset$Gimmick;", "gimmick", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset$Gimmick;", "Lcom/cobblemon/mod/common/battles/InBattleGimmickMove;", "gimmickMove", "Lcom/cobblemon/mod/common/battles/InBattleGimmickMove;", "getGimmickMove", "()Lcom/cobblemon/mod/common/battles/InBattleGimmickMove;", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getGimmickMoveTemplate", "()Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "gimmickMoveTemplate", "Lcom/cobblemon/mod/common/battles/MoveActionResponse;", "getResponse", "()Lcom/cobblemon/mod/common/battles/MoveActionResponse;", "response", "", "getSelectable", "()Z", "selectable", "", "Lcom/cobblemon/mod/common/battles/Targetable;", "getTargetList", "()Ljava/util/List;", "targetList", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection;", "moveSelection", "Lcom/cobblemon/mod/common/battles/InBattleMove;", "move", "", "x", "y", "<init>", "(Lcom/cobblemon/mod/common/battles/ShowdownMoveset$Gimmick;Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection;Lcom/cobblemon/mod/common/battles/InBattleMove;FF)V", "common"})
    public static class GimmickTile
    extends BattleMoveSelection.MoveTile {
        @NotNull
        private final ShowdownMoveset.Gimmick gimmick;
        @Nullable
        private final InBattleGimmickMove gimmickMove;

        public GimmickTile(@NotNull ShowdownMoveset.Gimmick gimmick, @NotNull BattleMoveSelection moveSelection, @NotNull InBattleMove move, float x, float y) {
            Intrinsics.checkNotNullParameter((Object)((Object)gimmick), (String)"gimmick");
            Intrinsics.checkNotNullParameter((Object)((Object)moveSelection), (String)"moveSelection");
            Intrinsics.checkNotNullParameter((Object)move, (String)"move");
            super(moveSelection, move, x, y);
            this.gimmick = gimmick;
            MoveTemplate moveTemplate = this.getGimmickMoveTemplate();
            if (moveTemplate != null) {
                MoveTemplate it = moveTemplate;
                boolean bl = false;
                this.setMoveTemplate(it);
                this.setRgb(SimpleMathExtensionsKt.toRGB(it.getElementalType().getHue()));
            }
            this.gimmickMove = move.getGimmickMove();
        }

        @Nullable
        protected final InBattleGimmickMove getGimmickMove() {
            return this.gimmickMove;
        }

        @Override
        @NotNull
        public MoveActionResponse getResponse() {
            return new MoveActionResponse(this.getMove().getId(), this.getTargetPnx(), this.gimmick.getId());
        }

        @Override
        @Nullable
        public List<Targetable> getTargetList() {
            return this.gimmickMove != null ? (List)this.gimmickMove.getTarget().getTargetList().invoke((Object)this.getMoveSelection().getRequest().getActivePokemon()) : super.getTargetList();
        }

        @Override
        public boolean getSelectable() {
            return this.gimmickMove != null ? !this.gimmickMove.getDisabled() : super.getSelectable();
        }

        private final MoveTemplate getGimmickMoveTemplate() {
            Object object;
            block7: {
                block6: {
                    String string;
                    object = this.getMove().getGimmickMove();
                    if (object == null || (object = ((InBattleGimmickMove)object).getMove()) == null) break block6;
                    String string2 = ((String)object).toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                    object = string2;
                    if (string2 == null) break block6;
                    CharSequence charSequence = (CharSequence)object;
                    Regex regex = ShowdownIdentifiable.Companion.getREGEX$common();
                    object = regex.replace(charSequence, string = "");
                    if (object != null) break block7;
                }
                return null;
            }
            Object gimmickMoveID = object;
            Double[] gimmickTemplate = Moves.INSTANCE.getByName((String)gimmickMoveID);
            int n = gimmickTemplate != null ? gimmickTemplate.getNum() : -1;
            Object object2 = gimmickTemplate;
            if (gimmickTemplate == null || (object2 = ((MoveTemplate)object2).getElementalType()) == null) {
                object2 = this.getMoveTemplate().getElementalType();
            }
            DamageCategory damageCategory = this.getMoveTemplate().getDamageCategory();
            double d = gimmickTemplate != null ? gimmickTemplate.getPower() : this.getMoveTemplate().getPower();
            Object object3 = gimmickTemplate;
            if (gimmickTemplate == null || (object3 = object3.getTarget()) == null) {
                object3 = this.getMoveTemplate().getTarget();
            }
            double d2 = gimmickTemplate != null ? gimmickTemplate.getAccuracy() : this.getMoveTemplate().getAccuracy();
            int n2 = gimmickTemplate != null ? gimmickTemplate.getPp() : this.getMoveTemplate().getPp();
            int n3 = gimmickTemplate != null ? gimmickTemplate.getPriority() : this.getMoveTemplate().getPriority();
            double d3 = gimmickTemplate != null ? gimmickTemplate.getCritRatio() : this.getMoveTemplate().getCritRatio();
            Double[] doubleArray = gimmickTemplate;
            if (gimmickTemplate == null || (doubleArray = doubleArray.getEffectChances()) == null) {
                doubleArray = this.getMoveTemplate().getEffectChances();
            }
            return new MoveTemplate((String)gimmickMoveID, n, (ElementalType)object2, damageCategory, d, (MoveTarget)((Object)object3), d2, n2, n3, d3, doubleArray, null);
        }
    }
}

