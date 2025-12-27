package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

public interface Targetable {
   public abstract fun getAllActivePokemon(): Iterable<Targetable> {
   }

   public abstract fun getActorPokemon(): Iterable<Targetable> {
   }

   public abstract fun getSidePokemon(): Iterable<Targetable> {
   }

   public abstract fun getFormat(): BattleFormat {
   }

   public abstract fun isAllied(other: Targetable): Boolean {
   }

   public abstract fun hasPokemon(): Boolean {
   }

   public abstract fun getActorShowdownId(): String {
   }

   public open fun getPNX(): String {
   }

   public open fun getAdjacent(): List<Targetable> {
   }

   public open fun getAdjacentAllies(): List<Targetable> {
   }

   public open fun getAdjacentOpponents(): List<Targetable> {
   }

   public open fun getSignedDigitRelativeTo(other: Targetable): String {
   }

   public open fun getDigitRelativeTo(other: Targetable): Int {
   }

   public open fun getDigit(asAlly: Boolean = ...): Int {
   }

   public open fun getLetter(): Char {
   }

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nMoveTarget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveTarget.kt\ncom/cobblemon/mod/common/battles/Targetable$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,117:1\n766#2:118\n857#2,2:119\n766#2:121\n857#2,2:122\n819#2:124\n847#2,2:125\n*S KotlinDebug\n*F\n+ 1 MoveTarget.kt\ncom/cobblemon/mod/common/battles/Targetable$DefaultImpls\n*L\n27#1:118\n27#1:119,2\n38#1:121\n38#1:122,2\n39#1:124\n39#1:125,2\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun getPNX(`$this`: Targetable): java.lang.String {
         return "${`$this`.getActorShowdownId()}${`$this`.getLetter()}";
      }

      @JvmStatic
      fun getAdjacent(`$this`: Targetable): MutableList<Targetable> {
         val digit: Int = getDigit$default(`$this`, false, 1, null);
         val sideSize: Int = `$this`.getFormat().getBattleType().getPokemonPerSide();
         val `$this$filter$iv`: java.lang.Iterable = `$this`.getAllActivePokemon();
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filter$iv) {
            if (Math.abs(
                     (
                           if ((`element$iv$iv` as Targetable).isAllied(`$this`))
                              getDigit$default(`element$iv$iv` as Targetable, false, 1, null)
                              else
                              sideSize - getDigit$default(`element$iv$iv` as Targetable, false, 1, null) + 1
                        )
                        - digit
                  )
                  <= 1
               && !(`element$iv$iv` as Targetable == `$this`)) {
               `destination$iv$iv`.add(`element$iv$iv`);
            }
         }

         return `destination$iv$iv` as MutableList<Targetable>;
      }

      @JvmStatic
      fun getAdjacentAllies(`$this`: Targetable): MutableList<Targetable> {
         val `$this$filter$iv`: java.lang.Iterable = `$this`.getAdjacent();
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filter$iv) {
            if ((`element$iv$iv` as Targetable).isAllied(`$this`)) {
               `destination$iv$iv`.add(`element$iv$iv`);
            }
         }

         return `destination$iv$iv` as MutableList<Targetable>;
      }

      @JvmStatic
      fun getAdjacentOpponents(`$this`: Targetable): MutableList<Targetable> {
         val `$this$filterNot$iv`: java.lang.Iterable = `$this`.getAdjacent();
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filterNot$iv) {
            if (!(`element$iv$iv` as Targetable).isAllied(`$this`)) {
               `destination$iv$iv`.add(`element$iv$iv`);
            }
         }

         return `destination$iv$iv` as MutableList<Targetable>;
      }

      @JvmStatic
      fun getSignedDigitRelativeTo(`$this`: Targetable, other: Targetable): java.lang.String {
         val digit: Int = `$this`.getDigitRelativeTo(other);
         return if (`$this`.isAllied(other)) "-$digit" else "+$digit";
      }

      @JvmStatic
      fun getDigitRelativeTo(`$this`: Targetable, other: Targetable): Int {
         return `$this`.getDigit(`$this`.isAllied(other));
      }

      @JvmStatic
      fun getDigit(`$this`: Targetable, asAlly: Boolean): Int {
         var digit: Int = 1;

         for (Targetable activePokemon : $this.getSidePokemon()) {
            if (activePokemon == `$this`) {
               return digit;
            }

            digit++;
         }

         return digit * (if (asAlly) 1 else -1);
      }

      @JvmStatic
      fun getLetter(`$this`: Targetable): Char {
         var index: Int = 0;

         for (Targetable activePokemon : $this.getActorPokemon()) {
            if (activePokemon == `$this`) {
               break;
            }

            index++;
         }

         var var10000: Char;
         switch (index) {
            case 0:
               var10000 = 'a';
               break;
            case 1:
               var10000 = 'b';
               break;
            case 2:
               var10000 = 'c';
               break;
            case 3:
               var10000 = 'd';
               break;
            case 4:
               var10000 = 'e';
               break;
            case 5:
               var10000 = 'f';
               break;
            default:
               throw new IllegalStateException("Battle has more than 6 in the active slot, makes no sense.");
         }

         return var10000;
      }
   }
}
