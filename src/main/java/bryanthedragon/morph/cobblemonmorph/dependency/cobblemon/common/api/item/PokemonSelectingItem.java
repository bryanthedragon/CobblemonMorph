package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PokemonInteractContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectCallbacks
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
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.phys.AABB
import org.jetbrains.annotations.NotNull

public interface PokemonSelectingItem {
   public val bagItem: BagItem?

   public open fun use(player: ServerPlayer, stack: ItemStack): InteractionResultHolder<ItemStack> {
   }

   public abstract fun applyToPokemon(player: ServerPlayer, stack: ItemStack, pokemon: Pokemon): InteractionResultHolder<ItemStack>? {
   }

   public open fun applyToBattlePokemon(player: ServerPlayer, stack: ItemStack, battlePokemon: BattlePokemon) {
   }

   public abstract fun canUseOnPokemon(pokemon: Pokemon): Boolean {
   }

   public open fun canUseOnBattlePokemon(battlePokemon: BattlePokemon): Boolean {
   }

   public open fun interactWithSpecificBattle(player: ServerPlayer, stack: ItemStack, battlePokemon: BattlePokemon): InteractionResultHolder<ItemStack> {
   }

   public open fun interactGeneral(player: ServerPlayer, stack: ItemStack): InteractionResultHolder<ItemStack> {
   }

   public open fun interactGeneralBattle(player: ServerPlayer, stack: ItemStack, actor: BattleActor): InteractionResultHolder<ItemStack> {
   }

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nPokemonSelectingItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonSelectingItem.kt\ncom/cobblemon/mod/common/api/item/PokemonSelectingItem$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,153:1\n766#2:154\n857#2,2:155\n2333#2,14:157\n1#3:171\n*S KotlinDebug\n*F\n+ 1 PokemonSelectingItem.kt\ncom/cobblemon/mod/common/api/item/PokemonSelectingItem$DefaultImpls\n*L\n47#1:154\n47#1:155,2\n48#1:157,14\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun use(`$this`: PokemonSelectingItem, player: ServerPlayer, stack: ItemStack): InteractionResultHolder<ItemStack> {
         var var10000: java.util.List = player.m_9236_().m_45933_(player as Entity, AABB.m_165882_(player.m_20182_(), 16.0, 16.0, 16.0));
         val `$this$minByOrNull$iv`: java.lang.Iterable = var10000;
         val pokemon: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$minByOrNull$iv) {
            val var12: Entity = `v$iv` as Entity;
            val var36: Entity = player as Entity;
            if (PlayerExtensionsKt.isLookingAt$default(var36, var12, 0.0F, 0.1F, 2, null)) {
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
                  val var28: Any = var7.next();
                  val var31: Float = (var28 as Entity).m_20270_(player as Entity);
                  if (java.lang.Float.compare(var24, var31) > 0) {
                     var20 = var28;
                     var24 = var31;
                  }
               } while (iterator$iv.hasNext());

               var10000 = (java.util.List)var20;
            }
         }

         val entity: PokemonEntity = var10000 as? PokemonEntity;
         val var38: Pair = PlayerExtensionsKt.getBattleState(player);
         val var40: Unit;
         if (var38 != null) {
            val var25: BattleActor = var38.component2() as BattleActor;
            if (`$this`.getBagItem() == null) {
               val var42: InteractionResultHolder = InteractionResultHolder.m_19100_(stack);
               return var42;
            }

            label79: {
               for (Object var34 : actor.getPokemonList()) {
                  if ((var34 as BattlePokemon).getEffectedPokemon() == (if (entity != null) entity.getPokemon() else null)) {
                     var10000 = (java.util.List)var34;
                     break label79;
                  }
               }

               var10000 = null;
            }

            val battlePokemon: BattlePokemon = var10000 as BattlePokemon;
            if (!var25.canFitForcedAction()) {
               val var10001: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.cannot");
               player.m_213846_(TextKt.red(var10001) as Component);
               val var41: InteractionResultHolder = InteractionResultHolder.m_19100_(stack);
               return var41;
            }

            if (entity == null) {
               return `$this`.interactGeneralBattle(player, stack, var25);
            }

            if (battlePokemon != null) {
               return `$this`.interactWithSpecificBattle(player, stack, battlePokemon);
            }

            var40 = Unit.INSTANCE;
         } else {
            var40 = null;
         }

         if (var40 == null) {
            if (!player.m_6144_()) {
               val var44: InteractionResultHolder;
               if (entity != null) {
                  val var22: Pokemon = entity.getPokemon();
                  if (entity.m_21805_() == player.m_20148_()) {
                     val var26: InteractionResultHolder = `$this`.applyToPokemon(player, stack, var22);
                     if (var26 != null) {
                        val var43: SimpleCriterionTrigger = CobblemonCriteria.INSTANCE.getPOKEMON_INTERACT();
                        val var10004: ResourceLocation = var22.getSpecies().getResourceIdentifier();
                        val var10005: ResourceLocation = BuiltInRegistries.f_257033_.m_7981_(stack.m_41720_());
                        var43.trigger(player, new PokemonInteractContext(var10004, var10005));
                        var44 = var26;
                     } else {
                        var44 = InteractionResultHolder.m_19098_(stack);
                     }
                  } else {
                     var44 = InteractionResultHolder.m_19100_(stack);
                  }
               } else {
                  var44 = `$this`.interactGeneral(player, stack);
               }

               return var44;
            }
         }

         val var45: InteractionResultHolder = InteractionResultHolder.m_19098_(stack);
         return var45;
      }

      @JvmStatic
      fun applyToBattlePokemon(`$this`: PokemonSelectingItem, player: ServerPlayer, stack: ItemStack, battlePokemon: BattlePokemon) {
         val battle: PokemonBattle = battlePokemon.getActor().getBattle();
         val bagItem: BagItem = `$this`.getBagItem();
         if (!battlePokemon.getActor().canFitForcedAction()) {
            val var10001: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.cannot");
            player.m_213846_(TextKt.red(var10001) as Component);
         } else if (!bagItem.canUse(battle, battlePokemon)) {
            val var6: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.invalid");
            player.m_213846_(TextKt.red(var6) as Component);
         } else {
            battlePokemon.getActor().forceChoose(new BagItemActionResponse(bagItem, battlePokemon, null, 4, null));
            if (!player.m_7500_()) {
               stack.m_41774_(1);
            }

            val var10000: SimpleCriterionTrigger = CobblemonCriteria.INSTANCE.getPOKEMON_INTERACT();
            val var10004: PokemonEntity = battlePokemon.getEntity();
            val var7: ResourceLocation = var10004.getPokemon().getSpecies().getResourceIdentifier();
            val var10005: ResourceLocation = BuiltInRegistries.f_257033_.m_7981_(stack.m_41720_());
            var10000.trigger(player, new PokemonInteractContext(var7, var10005));
         }
      }

      @JvmStatic
      fun canUseOnBattlePokemon(`$this`: PokemonSelectingItem, battlePokemon: BattlePokemon): Boolean {
         val var10000: BagItem = `$this`.getBagItem();
         return var10000.canUse(battlePokemon.getActor().getBattle(), battlePokemon);
      }

      @JvmStatic
      fun interactWithSpecificBattle(`$this`: PokemonSelectingItem, player: ServerPlayer, stack: ItemStack, battlePokemon: BattlePokemon): InteractionResultHolder<ItemStack> {
         val var10000: InteractionResultHolder;
         if (`$this`.canUseOnBattlePokemon(battlePokemon)) {
            `$this`.applyToBattlePokemon(player, stack, battlePokemon);
            var10000 = InteractionResultHolder.m_19090_(stack);
         } else {
            val var10001: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.invalid");
            player.m_213846_(TextKt.red(var10001) as Component);
            val var4: InteractionResultHolder = InteractionResultHolder.m_19100_(stack);
            var10000 = var4;
         }

         return var10000;
      }

      @JvmStatic
      fun interactGeneral(`$this`: PokemonSelectingItem, player: ServerPlayer, stack: ItemStack): InteractionResultHolder<ItemStack> {
         val party: java.util.List = CollectionsKt.toList(PlayerExtensionsKt.party(player));
         if (party.isEmpty()) {
            val var4: InteractionResultHolder = InteractionResultHolder.m_19100_(stack);
            return var4;
         } else {
            PartySelectCallbacks.createFromPokemon$default(
               PartySelectCallbacks.INSTANCE, player, null, party, (new Function1<Pokemon, java.lang.Boolean>(`$this`) {
                  {
                     super(1, receiver, PokemonSelectingItem::class.java, "canUseOnPokemon", "canUseOnPokemon(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", 0);
                  }

                  @NotNull
                  public final java.lang.Boolean invoke(@NotNull Pokemon p0) {
                     return (this.receiver as PokemonSelectingItem).canUseOnPokemon(p0);
                  }
               }) as Function1, null, (new Function1<Pokemon, Unit>(stack, player, `$this`) {
                  {
                     super(1);
                     this.$stack = `$stack`;
                     this.$player = `$player`;
                     this.this$0 = `$receiver`;
                  }

                  public final void invoke(@NotNull Pokemon pk) {
                     if (ItemStackExtensionsKt.isHeld(this.$stack, this.$player)) {
                        this.this$0.applyToPokemon(this.$player, this.$stack, pk);
                        val var10000: SimpleCriterionTrigger = CobblemonCriteria.INSTANCE.getPOKEMON_INTERACT();
                        val var10001: ServerPlayer = this.$player;
                        val var10004: ResourceLocation = pk.getSpecies().getResourceIdentifier();
                        val var10005: ResourceLocation = BuiltInRegistries.f_257033_.m_7981_(this.$stack.m_41720_());
                        var10000.trigger(var10001, new PokemonInteractContext(var10004, var10005));
                     }
                  }
               }) as Function1, 18, null
            );
            val var10000: InteractionResultHolder = InteractionResultHolder.m_19090_(stack);
            return var10000;
         }
      }

      @JvmStatic
      fun interactGeneralBattle(`$this`: PokemonSelectingItem, player: ServerPlayer, stack: ItemStack, actor: BattleActor): InteractionResultHolder<ItemStack> {
         PartySelectCallbacks.createBattleSelect$default(
            PartySelectCallbacks.INSTANCE, player, null, actor.getPokemonList(), (new Function1<BattlePokemon, java.lang.Boolean>(`$this`, actor) {
               {
                  super(1);
                  this.this$0 = `$receiver`;
                  this.$actor = `$actor`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull BattlePokemon pk) {
                  var var10000: PokemonSelectingItem = this.this$0;
                  val var4: java.util.Iterator = this.$actor.getPokemonList().iterator();

                  while (true) {
                     if (var4.hasNext()) {
                        val var5: Any = var4.next();
                        if (!((var5 as BattlePokemon).getEffectedPokemon() == pk.getEffectedPokemon())) {
                           continue;
                        }

                        var10000 = (PokemonSelectingItem)var5;
                        break;
                     }

                     var10000 = null;
                     break;
                  }

                  return var10000.canUseOnBattlePokemon(var10000 as BattlePokemon);
               }
            }) as Function1, null, (new Function1<BattlePokemon, Unit>(`$this`, player, stack, actor) {
               {
                  super(1);
                  this.this$0 = `$receiver`;
                  this.$player = `$player`;
                  this.$stack = `$stack`;
                  this.$actor = `$actor`;
               }

               public final void invoke(@NotNull BattlePokemon pk) {
                  val var10000: PokemonSelectingItem = this.this$0;
                  val var10001: ServerPlayer = this.$player;
                  val var10002: ItemStack = this.$stack;
                  val var4: java.util.Iterator = this.$actor.getPokemonList().iterator();

                  while (true) {
                     if (var4.hasNext()) {
                        val var5: Any = var4.next();
                        if (!((var5 as BattlePokemon).getEffectedPokemon() == pk.getEffectedPokemon())) {
                           continue;
                        }

                        var12 = var5;
                        break;
                     }

                     var12 = null;
                     break;
                  }

                  var10000.applyToBattlePokemon(var10001, var10002, var12 as BattlePokemon);
               }
            }) as Function1, 18, null
         );
         val var10000: InteractionResultHolder = InteractionResultHolder.m_19090_(stack);
         return var10000;
      }
   }
}
