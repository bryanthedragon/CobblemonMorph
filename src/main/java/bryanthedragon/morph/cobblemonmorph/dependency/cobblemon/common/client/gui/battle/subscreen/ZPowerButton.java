package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleMove
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleMoveSelection.MoveTile
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nBattleGimmickButton.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleGimmickButton.kt\ncom/cobblemon/mod/common/client/gui/battle/subscreen/ZPowerButton\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,214:1\n1549#2:215\n1620#2,3:216\n*S KotlinDebug\n*F\n+ 1 BattleGimmickButton.kt\ncom/cobblemon/mod/common/client/gui/battle/subscreen/ZPowerButton\n*L\n158#1:215\n158#1:216,3\n*E\n"])
public class ZPowerButton(moveSelection: BattleMoveSelection, x: Float, y: Float) : BattleGimmickButton(ShowdownMoveset.Gimmick.Z_POWER, x, y) {
   public open var tiles: List<MoveTile>

   init {
      val `$this$map$iv`: java.lang.Iterable = moveSelection.getBaseTiles();
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add(
            new ZPowerButton.ZPowerTile(
               moveSelection,
               (`item$iv$iv` as BattleMoveSelection.MoveTile).getMove(),
               (`item$iv$iv` as BattleMoveSelection.MoveTile).getX(),
               (`item$iv$iv` as BattleMoveSelection.MoveTile).getY()
            )
         );
      }

      this.tiles = `destination$iv$iv` as MutableList<BattleMoveSelection.MoveTile>;
   }

   public class ZPowerTile(moveSelection: BattleMoveSelection, move: InBattleMove, x: Float, y: Float) : BattleGimmickButton.GimmickTile(
         ShowdownMoveset.Gimmick.Z_POWER, moveSelection, move, x, y
      ) {
      public open val selectable: Boolean
         public open get() {
            return this.getGimmickMove() != null && !this.getGimmickMove().getDisabled();
         }

   }
}
