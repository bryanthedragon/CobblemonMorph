package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import java.util.ArrayList;
import java.util.HashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nLevelUpMoves.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LevelUpMoves.kt\ncom/cobblemon/mod/common/pokemon/LevelUpMoves\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,14:1\n766#2:15\n857#2,2:16\n1045#2:18\n1360#2:19\n1446#2,5:20\n*S KotlinDebug\n*F\n+ 1 LevelUpMoves.kt\ncom/cobblemon/mod/common/pokemon/LevelUpMoves\n*L\n13#1:15\n13#1:16,2\n13#1:18\n13#1:19\n13#1:20,5\n*E\n"])
public class LevelUpMoves : HashMap<Integer, java.util.List<MoveTemplate>> {
   public fun getLevelUpMovesUpTo(level: Int): Set<MoveTemplate> {
      var var10000: java.util.Set = this.entrySet();
      var `$this$flatMap$iv`: java.lang.Iterable = var10000;
      var `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         var10000 = (java.util.Set)(`element$iv$iv` as Entry).getKey();
         if ((var10000 as java.lang.Number).intValue() <= level) {
            `destination$iv$iv`.add(`element$iv$iv`);
         }
      }

      `$this$flatMap$iv` = CollectionsKt.sortedWith(`destination$iv$iv` as java.util.List, new LevelUpMoves$getLevelUpMovesUpTo$$inlined$sortedBy$1());
      `destination$iv$iv` = new ArrayList();

      for (Object element$iv$ivx : $this$filter$iv) {
         var10000 = (java.util.Set)(`element$iv$ivx` as Entry).getValue();
         CollectionsKt.addAll(`destination$iv$iv`, var10000 as java.util.List);
      }

      return CollectionsKt.toSet(`destination$iv$iv` as java.util.List);
   }
}
