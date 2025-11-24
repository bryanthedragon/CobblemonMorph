/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleGimmickButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleMoveSelection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001\u0011B\u001f\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000e\u001a\u00020\f\u00a2\u0006\u0004\b\u000f\u0010\u0010R(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/DynamaxButton;", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleGimmickButton;", "", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection$MoveTile;", "tiles", "Ljava/util/List;", "getTiles", "()Ljava/util/List;", "setTiles", "(Ljava/util/List;)V", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection;", "moveSelection", "", "x", "y", "<init>", "(Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection;FF)V", "DynamaxTile", "common"})
@SourceDebugExtension(value={"SMAP\nBattleGimmickButton.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleGimmickButton.kt\ncom/cobblemon/mod/common/client/gui/battle/subscreen/DynamaxButton\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,214:1\n1549#2:215\n1620#2,3:216\n*S KotlinDebug\n*F\n+ 1 BattleGimmickButton.kt\ncom/cobblemon/mod/common/client/gui/battle/subscreen/DynamaxButton\n*L\n192#1:215\n192#1:216,3\n*E\n"})
public final class DynamaxButton
extends BattleGimmickButton {
    @NotNull
    private List<? extends BattleMoveSelection.MoveTile> tiles;

    /*
     * WARNING - void declaration
     */
    public DynamaxButton(@NotNull BattleMoveSelection moveSelection, float x, float y) {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter((Object)((Object)moveSelection), (String)"moveSelection");
        super(ShowdownMoveset.Gimmick.DYNAMAX, x, y);
        Iterable iterable = moveSelection.getBaseTiles();
        DynamaxButton dynamaxButton = this;
        boolean $i$f$map = false;
        void var6_7 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void tile;
            BattleMoveSelection.MoveTile moveTile = (BattleMoveSelection.MoveTile)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(new DynamaxTile(moveSelection, tile.getMove(), tile.getX(), tile.getY()));
        }
        dynamaxButton.tiles = (List)destination$iv$iv;
    }

    @Override
    @NotNull
    public List<BattleMoveSelection.MoveTile> getTiles() {
        return this.tiles;
    }

    public void setTiles(@NotNull List<? extends BattleMoveSelection.MoveTile> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.tiles = list;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n\u00a2\u0006\u0004\b\r\u0010\u000eR\u0014\u0010\u0005\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/DynamaxButton$DynamaxTile;", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleGimmickButton$GimmickTile;", "", "getSelectable", "()Z", "selectable", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection;", "moveSelection", "Lcom/cobblemon/mod/common/battles/InBattleMove;", "move", "", "x", "y", "<init>", "(Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection;Lcom/cobblemon/mod/common/battles/InBattleMove;FF)V", "common"})
    public static final class DynamaxTile
    extends BattleGimmickButton.GimmickTile {
        public DynamaxTile(@NotNull BattleMoveSelection moveSelection, @NotNull InBattleMove move, float x, float y) {
            Intrinsics.checkNotNullParameter((Object)((Object)moveSelection), (String)"moveSelection");
            Intrinsics.checkNotNullParameter((Object)move, (String)"move");
            super(ShowdownMoveset.Gimmick.DYNAMAX, moveSelection, move, x, y);
        }

        @Override
        public boolean getSelectable() {
            return this.getGimmickMove() != null && !this.getGimmickMove().getDisabled();
        }
    }
}

