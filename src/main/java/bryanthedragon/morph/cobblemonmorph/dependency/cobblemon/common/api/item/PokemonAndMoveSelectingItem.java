package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectCallbacks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartyMoveSelectCallbacks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItemActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ItemStackExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.ArrayList;
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.phys.AABB
import org.jetbrains.annotations.NotNull

public interface PokemonAndMoveSelectingItem {
   public val bagItem: BagItem?

   public open fun use(player: ServerPlayer, stack: ItemStack): InteractionResultHolder<ItemStack>? {
   }

   public abstract fun applyToPokemon(player: ServerPlayer, stack: ItemStack, pokemon: Pokemon, move: Move) {
   }

   public open fun applyToBattlePokemon(player: ServerPlayer, stack: ItemStack, battlePokemon: BattlePokemon, move: Move) {
   }

   public abstract fun canUseOnPokemon(pokemon: Pokemon): Boolean {
   }

   public open fun canUseOnBattlePokemon(battlePokemon: BattlePokemon): Boolean {
   }

   public open fun canUseOnMove(pokemon: Pokemon, move: Move): Boolean {
   }

   public abstract fun canUseOnMove(move: Move): Boolean {
   }

   public open fun interactWithSpecific(player: ServerPlayer, stack: ItemStack, pokemon: Pokemon): InteractionResultHolder<ItemStack>? {
   }

   public open fun interactWithSpecificBattle(player: ServerPlayer, stack: ItemStack, battlePokemon: BattlePokemon): InteractionResultHolder<ItemStack>? {
   }

   public open fun interactGeneral(player: ServerPlayer, stack: ItemStack): InteractionResultHolder<ItemStack>? {
   }

   public open fun interactGeneralBattle(player: ServerPlayer, stack: ItemStack, actor: BattleActor): InteractionResultHolder<ItemStack>? {
   }

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nPokemonAndMoveSelectingItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonAndMoveSelectingItem.kt\ncom/cobblemon/mod/common/api/item/PokemonAndMoveSelectingItem$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,155:1\n766#2:156\n857#2,2:157\n2333#2,14:159\n1549#2:174\n1620#2,3:175\n1#3:173\n*S KotlinDebug\n*F\n+ 1 PokemonAndMoveSelectingItem.kt\ncom/cobblemon/mod/common/api/item/PokemonAndMoveSelectingItem$DefaultImpls\n*L\n44#1:156\n44#1:157,2\n45#1:159,14\n145#1:174\n145#1:175,3\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun use(`$this`: PokemonAndMoveSelectingItem, player: ServerPlayer, stack: ItemStack): InteractionResultHolder<ItemStack>? {
         var var10000: java.util.List = player.m_9236_().m_45933_(player as Entity, AABB.m_165882_(player.m_20182_(), 16.0, 16.0, 16.0));
         val `$this$minByOrNull$iv`: java.lang.Iterable = var10000;
         val pokemon: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$minByOrNull$iv) {
            val var12: Entity = `v$iv` as Entity;
            val var35: Entity = player as Entity;
            if (PlayerExtensionsKt.isLookingAt$default(var35, var12, 0.0F, 0.1F, 2, null)) {
               pokemon.add(`v$iv`);
            }
         }

         val var7: java.util.Iterator = (pokemon as java.util.List).iterator();
         if (!var7.hasNext()) {
            var10000 = null;
         } else {
            var var20: Any = var7.next();
            if (!var7.hasNext()) {
               var10000 = (java.util.List)var20;
            } else {
               var var24: Float = (var20 as Entity).m_20270_(player as Entity);

               do {
                  val var27: Any = var7.next();
                  val var30: Float = (var27 as Entity).m_20270_(player as Entity);
                  if (java.lang.Float.compare(var24, var30) > 0) {
                     var20 = var27;
                     var24 = var30;
                  }
               } while (iterator$iv.hasNext());

               var10000 = (java.util.List)var20;
            }
         }

         val entity: PokemonEntity = var10000 as? PokemonEntity;
         val var37: Pair = PlayerExtensionsKt.getBattleState(player);
         val var39: Unit;
         if (var37 != null) {
            val var25: BattleActor = var37.component2() as BattleActor;
            if (`$this`.getBagItem() == null) {
               return InteractionResultHolder.m_19100_(stack);
            }

            if (!var25.canFitForcedAction()) {
               val var10001: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.cannot");
               player.m_213846_(TextKt.red(var10001) as Component);
               return InteractionResultHolder.m_19100_(stack);
            }

            label71: {
               for (Object var33 : actor.getPokemonList()) {
                  if ((var33 as BattlePokemon).getEffectedPokemon() == (if (entity != null) entity.getPokemon() else null)) {
                     var10000 = (java.util.List)var33;
                     break label71;
                  }
               }

               var10000 = null;
            }

            val battlePokemon: BattlePokemon = var10000 as BattlePokemon;
            if (entity == null) {
               return `$this`.interactGeneralBattle(player, stack, var25);
            }

            if (battlePokemon != null) {
               return `$this`.interactWithSpecificBattle(player, stack, battlePokemon);
            }

            var39 = Unit.INSTANCE;
         } else {
            var39 = null;
         }

         if (var39 == null) {
            if (entity != null) {
               return if (entity.m_21805_() == player.m_20148_())
                  `$this`.interactWithSpecific(player, stack, entity.getPokemon())
                  else
                  InteractionResultHolder.m_19100_(stack);
            } else {
               return `$this`.interactGeneral(player, stack);
            }
         } else {
            return null;
         }
      }

      @JvmStatic
      fun applyToBattlePokemon(`$this`: PokemonAndMoveSelectingItem, player: ServerPlayer, stack: ItemStack, battlePokemon: BattlePokemon, move: Move) {
         val battle: PokemonBattle = battlePokemon.getActor().getBattle();
         val bagItem: BagItem = `$this`.getBagItem();
         if (!battlePokemon.getActor().canFitForcedAction()) {
            val var10001: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.cannot");
            player.m_213846_(TextKt.red(var10001) as Component);
         } else if (!bagItem.canUse(battle, battlePokemon)) {
            val var7: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.invalid");
            player.m_213846_(TextKt.red(var7) as Component);
         } else {
            battlePokemon.getActor().forceChoose(new BagItemActionResponse(bagItem, battlePokemon, move.getTemplate().getName()));
            if (!player.m_7500_()) {
               stack.m_41774_(1);
            }
         }
      }

      @JvmStatic
      fun canUseOnBattlePokemon(`$this`: PokemonAndMoveSelectingItem, battlePokemon: BattlePokemon): Boolean {
         val var10000: BagItem = `$this`.getBagItem();
         return var10000.canUse(battlePokemon.getActor().getBattle(), battlePokemon);
      }

      @JvmStatic
      fun canUseOnMove(`$this`: PokemonAndMoveSelectingItem, pokemon: Pokemon, move: Move): Boolean {
         return `$this`.canUseOnMove(move);
      }

      @JvmStatic
      fun interactWithSpecific(`$this`: PokemonAndMoveSelectingItem, player: ServerPlayer, stack: ItemStack, pokemon: Pokemon): InteractionResultHolder<ItemStack>? {
         if (player.m_6144_()) {
            return InteractionResultHolder.m_19098_(stack);
         } else {
            MoveSelectCallbacks.create$default(
               MoveSelectCallbacks.INSTANCE, player, CollectionsKt.toList(pokemon.getMoveSet()), (new Function1<Move, java.lang.Boolean>(`$this`) {
                  {
                     super(1, receiver, PokemonAndMoveSelectingItem::class.java, "canUseOnMove", "canUseOnMove(Lcom/cobblemon/mod/common/api/moves/Move;)Z", 0);
                  }

                  @NotNull
                  public final java.lang.Boolean invoke(@NotNull Move p0) {
                     return (this.receiver as PokemonAndMoveSelectingItem).canUseOnMove(p0);
                  }
               }) as Function1, null, (new Function1<Move, Unit>(stack, player, `$this`, pokemon) {
                  {
                     super(1);
                     this.$stack = `$stack`;
                     this.$player = `$player`;
                     this.this$0 = `$receiver`;
                     this.$pokemon = `$pokemon`;
                  }

                  public final void invoke(@NotNull Move move) {
                     if (ItemStackExtensionsKt.isHeld(this.$stack, this.$player)) {
                        this.this$0.applyToPokemon(this.$player, this.$stack, this.$pokemon, move);
                     }
                  }
               }) as Function1, 8, null
            );
            return InteractionResultHolder.m_19090_(stack);
         }
      }

      @JvmStatic
      fun interactWithSpecificBattle(`$this`: PokemonAndMoveSelectingItem, player: ServerPlayer, stack: ItemStack, battlePokemon: BattlePokemon): InteractionResultHolder<ItemStack>? {
         val var10000: InteractionResultHolder;
         if (`$this`.canUseOnBattlePokemon(battlePokemon)) {
            MoveSelectCallbacks.create$default(
               MoveSelectCallbacks.INSTANCE, player, battlePokemon.getMoveSet().getMoves(), (new Function1<Move, java.lang.Boolean>(`$this`) {
                  {
                     super(1, receiver, PokemonAndMoveSelectingItem::class.java, "canUseOnMove", "canUseOnMove(Lcom/cobblemon/mod/common/api/moves/Move;)Z", 0);
                  }

                  @NotNull
                  public final java.lang.Boolean invoke(@NotNull Move p0) {
                     return (this.receiver as PokemonAndMoveSelectingItem).canUseOnMove(p0);
                  }
               }) as Function1, null, (new Function1<Move, Unit>(`$this`, player, stack, battlePokemon) {
                  {
                     super(1);
                     this.this$0 = `$receiver`;
                     this.$player = `$player`;
                     this.$stack = `$stack`;
                     this.$battlePokemon = `$battlePokemon`;
                  }

                  public final void invoke(@NotNull Move it) {
                     this.this$0.applyToBattlePokemon(this.$player, this.$stack, this.$battlePokemon, it);
                  }
               }) as Function1, 8, null
            );
            var10000 = InteractionResultHolder.m_19090_(stack);
         } else {
            val var10001: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.invalid");
            player.m_213846_(TextKt.red(var10001) as Component);
            var10000 = InteractionResultHolder.m_19100_(stack);
         }

         return var10000;
      }

      @JvmStatic
      fun interactGeneral(`$this`: PokemonAndMoveSelectingItem, player: ServerPlayer, stack: ItemStack): InteractionResultHolder<ItemStack>? {
         PartyMoveSelectCallbacks.createFromPokemon$default(
            PartyMoveSelectCallbacks.INSTANCE,
            player,
            null,
            CollectionsKt.toList(PlayerExtensionsKt.party(player)),
            null,
            (
               new Function1<Pokemon, java.lang.Boolean>(`$this`) {
                  {
                     super(
                        1,
                        receiver,
                        PokemonAndMoveSelectingItem::class.java,
                        "canUseOnPokemon",
                        "canUseOnPokemon(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z",
                        0
                     );
                  }

                  @NotNull
                  public final java.lang.Boolean invoke(@NotNull Pokemon p0) {
                     return (this.receiver as PokemonAndMoveSelectingItem).canUseOnPokemon(p0);
                  }
               }
            ) as Function1,
            (
               new Function2<Pokemon, Move, java.lang.Boolean>(`$this`) {
                  {
                     super(
                        2,
                        receiver,
                        PokemonAndMoveSelectingItem::class.java,
                        "canUseOnMove",
                        "canUseOnMove(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/moves/Move;)Z",
                        0
                     );
                  }

                  @NotNull
                  public final java.lang.Boolean invoke(@NotNull Pokemon p0, @NotNull Move p1) {
                     return (this.receiver as PokemonAndMoveSelectingItem).canUseOnMove(p0, p1);
                  }
               }
            ) as Function2,
            null,
            (new Function2<Pokemon, Move, Unit>(stack, player, `$this`) {
               {
                  super(2);
                  this.$stack = `$stack`;
                  this.$player = `$player`;
                  this.this$0 = `$receiver`;
               }

               public final void invoke(@NotNull Pokemon pk, @NotNull Move mv) {
                  if (ItemStackExtensionsKt.isHeld(this.$stack, this.$player)) {
                     this.this$0.applyToPokemon(this.$player, this.$stack, pk, mv);
                  }
               }
            }) as Function2,
            74,
            null
         );
         return InteractionResultHolder.m_19090_(stack);
      }

      @JvmStatic
      fun interactGeneralBattle(`$this`: PokemonAndMoveSelectingItem, player: ServerPlayer, stack: ItemStack, actor: BattleActor): InteractionResultHolder<ItemStack>? {
         val var10000: PartyMoveSelectCallbacks = PartyMoveSelectCallbacks.INSTANCE;
         val `$this$map$iv`: java.lang.Iterable = actor.getPokemonList();
         val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

         for (Object item$iv$iv : $this$map$iv) {
            `destination$iv$iv`.add((`item$iv$iv` as BattlePokemon).getEffectedPokemon());
         }

         PartyMoveSelectCallbacks.createFromPokemon$default(var10000, player, null, `destination$iv$iv` as java.util.List,
            (new Function1<Pokemon, java.util.List<? extends Move>>(actor) {
               {
                  super(1);
                  this.$actor = `$actor`;
               }

               @NotNull
               public final java.util.List<Move> invoke(@NotNull Pokemon pk) {
                  val var3: java.util.Iterator = this.$actor.getPokemonList().iterator();
                  var var10000: Any;
                  while (true) {
                     if (var3.hasNext()) {
                        val var4: Any = var3.next();
                        if (!((var4 as BattlePokemon).getEffectedPokemon() == pk)) {
                           continue;
                        }
                        var10000 = var4;
                        break;
                     }
                     var10000 = null;
                     break;
                  }
                  return (var10000 as BattlePokemon).getMoveSet().getMoves();
               }
            }) as Function1,
            (new Function1<Pokemon, java.lang.Boolean>(`$this`, actor) {
               {
                  super(1);
                  this.this$0 = `$receiver`;
                  this.$actor = `$actor`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull Pokemon pk) {
                  var var10000: PokemonAndMoveSelectingItem = this.this$0;
                  val var4: java.util.Iterator = this.$actor.getPokemonList().iterator();
                  while (true) {
                     if (var4.hasNext()) {
                        val var5: Any = var4.next();
                        if (!((var5 as BattlePokemon).getEffectedPokemon() == pk)) {
                           continue;
                        }
                        var10000 = (PokemonAndMoveSelectingItem)var5;
                        break;
                     }
                     var10000 = null;
                     break;
                  }

                  return var10000.canUseOnBattlePokemon(var10000 as BattlePokemon);
               }
            }) as Function1,
            (
               new Function2<Pokemon, Move, java.lang.Boolean>(`$this`) 
               {
                  super(2, receiver, PokemonAndMoveSelectingItem::class.java, "canUseOnMove", "canUseOnMove(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/moves/Move;)Z", 0);
                  @NotNull
                  public final java.lang.Boolean invoke(@NotNull Pokemon p0, @NotNull Move p1) {
                     return (this.receiver as PokemonAndMoveSelectingItem).canUseOnMove(p0, p1);
                  }
               }
            ) as Function2,
            null,
            (new Function2<Pokemon, Move, Unit>(`$this`, player, stack, actor) {
               {
                  super(2);
                  this.this$0 = `$receiver`;
                  this.$player = `$player`;
                  this.$stack = `$stack`;
                  this.$actor = `$actor`;
               }

               public final void invoke(@NotNull Pokemon pk, @NotNull Move mv) {
                  val var10000: PokemonAndMoveSelectingItem = this.this$0;
                  val var10001: ServerPlayer = this.$player;
                  val var10002: ItemStack = this.$stack;
                  val var5: java.util.Iterator = this.$actor.getPokemonList().iterator();
                  while (true) {
                     if (var5.hasNext()) {
                        val var6: Any = var5.next();
                        if (!((var6 as BattlePokemon).getEffectedPokemon() == pk)) {
                           continue;
                        }
                        var13 = var6;
                        break;
                     }
                     var13 = null;
                     break;
                  }
                  var10000.applyToBattlePokemon(var10001, var10002, var13 as BattlePokemon, mv);
               }
            }) as Function2,
            66,
            null
         );
         return InteractionResultHolder.m_19090_(stack);
      }
   }
}
