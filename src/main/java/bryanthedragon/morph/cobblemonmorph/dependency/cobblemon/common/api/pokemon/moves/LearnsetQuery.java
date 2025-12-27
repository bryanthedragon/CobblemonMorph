package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import kotlin.jvm.internal.SourceDebugExtension

public fun interface LearnsetQuery {
   public abstract fun canLearn(move: MoveTemplate, learnset: Learnset): Boolean {
   }

   @SourceDebugExtension(["SMAP\nLearnsetQuery.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LearnsetQuery.kt\ncom/cobblemon/mod/common/api/pokemon/moves/LearnsetQuery$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,51:1\n1747#2,3:52\n1747#2,3:55\n*S KotlinDebug\n*F\n+ 1 LearnsetQuery.kt\ncom/cobblemon/mod/common/api/pokemon/moves/LearnsetQuery$Companion\n*L\n27#1:52,3\n37#1:55,3\n*E\n"])
   public companion object {
      public final val ANY: LearnsetQuery = LearnsetQuery.Companion::ANY$lambda$1
      public final val ANY_LEVEL: LearnsetQuery = LearnsetQuery.Companion::ANY_LEVEL$lambda$4
      public final val EGG_MOVE: LearnsetQuery = LearnsetQuery.Companion::EGG_MOVE$lambda$5
      public final val EVOLUTION: LearnsetQuery = LearnsetQuery.Companion::EVOLUTION$lambda$9
      public final val FORM_CHANGE: LearnsetQuery = LearnsetQuery.Companion::FORM_CHANGE$lambda$8
      public final val TM_MOVE: LearnsetQuery = LearnsetQuery.Companion::TM_MOVE$lambda$7
      public final val TUTOR_MOVES: LearnsetQuery = LearnsetQuery.Companion::TUTOR_MOVES$lambda$6

      public fun level(level: Int): LearnsetQuery {
         return LearnsetQuery.Companion::level$lambda$2;
      }

      @JvmStatic
      fun `ANY$lambda$1`(move: MoveTemplate, learnset: Learnset): Boolean {
         val `$this$any$iv`: java.lang.Iterable = learnset.getLevelUpMoves().values();
         var var10000: Boolean;
         if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
            var10000 = false;
         } else {
            val var4: java.util.Iterator = `$this$any$iv`.iterator();

            while (true) {
               if (!var4.hasNext()) {
                  var10000 = false;
                  break;
               }

               if ((var4.next() as java.util.List).contains(move)) {
                  var10000 = true;
                  break;
               }
            }
         }

         return var10000
            || learnset.getEggMoves().contains(move)
            || learnset.getTutorMoves().contains(move)
            || learnset.getTmMoves().contains(move)
            || learnset.getFormChangeMoves().contains(move)
            || learnset.getEvolutionMoves().contains(move);
      }

      @JvmStatic
      fun `level$lambda$2`(`$level`: Int, move: MoveTemplate, learnset: Learnset): Boolean {
         return learnset.getLevelUpMovesUpTo(`$level`).contains(move);
      }

      @JvmStatic
      fun `ANY_LEVEL$lambda$4`(move: MoveTemplate, learnset: Learnset): Boolean {
         val `$this$any$iv`: java.lang.Iterable = learnset.getLevelUpMoves().values();
         var var10000: Boolean;
         if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
            var10000 = false;
         } else {
            val var4: java.util.Iterator = `$this$any$iv`.iterator();

            while (true) {
               if (!var4.hasNext()) {
                  var10000 = false;
                  break;
               }

               if ((var4.next() as java.util.List).contains(move)) {
                  var10000 = true;
                  break;
               }
            }
         }

         return var10000;
      }

      @JvmStatic
      fun `EGG_MOVE$lambda$5`(move: MoveTemplate, learnset: Learnset): Boolean {
         return learnset.getEggMoves().contains(move);
      }

      @JvmStatic
      fun `TUTOR_MOVES$lambda$6`(move: MoveTemplate, learnset: Learnset): Boolean {
         return learnset.getTutorMoves().contains(move);
      }

      @JvmStatic
      fun `TM_MOVE$lambda$7`(move: MoveTemplate, learnset: Learnset): Boolean {
         return learnset.getTmMoves().contains(move);
      }

      @JvmStatic
      fun `FORM_CHANGE$lambda$8`(move: MoveTemplate, learnset: Learnset): Boolean {
         return learnset.getFormChangeMoves().contains(move);
      }

      @JvmStatic
      fun `EVOLUTION$lambda$9`(move: MoveTemplate, learnset: Learnset): Boolean {
         return learnset.getEvolutionMoves().contains(move);
      }
   }
}
