package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenPartyCallbackPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.UUID
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nPartySelectCallback.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartySelectCallback.kt\ncom/cobblemon/mod/common/api/callback/PartySelectCallbacks\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,163:1\n1549#2:164\n1620#2,2:165\n1622#2:168\n1549#2:169\n1620#2,3:170\n1#3:167\n*S KotlinDebug\n*F\n+ 1 PartySelectCallback.kt\ncom/cobblemon/mod/common/api/callback/PartySelectCallbacks\n*L\n73#1:164\n73#1:165,2\n73#1:168\n88#1:169\n88#1:170,3\n*E\n"])
public object PartySelectCallbacks {
   public final val callbacks: MutableMap<UUID, PartySelectCallback> = (new LinkedHashMap()) as java.util.Map

   @JvmOverloads
   public fun create(
      player: ServerPlayer,
      title: Component = LocalizationUtilsKt.lang("ui.party"),
      pokemon: List<PartySelectPokemonDTO>,
      cancel: (ServerPlayer) -> Unit = <unrepresentable>.INSTANCE as Function1,
      handler: (ServerPlayer, Int) -> Unit
   ) {
      val callback: PartySelectCallback = new PartySelectCallback(null, pokemon, cancel, handler, 1, null);
      val var7: java.util.Map = callbacks;
      val var10000: UUID = player.m_20148_();
      var7.put(var10000, callback);
      val var8: CobblemonNetwork = CobblemonNetwork.INSTANCE;
      val var10004: UUID = callback.getUuid();
      val var10005: MutableComponent = title.m_6881_();
      var8.sendPacket(player, new OpenPartyCallbackPacket(var10004, var10005, pokemon));
   }

   @JvmOverloads
   public fun createBattleSelect(
      player: ServerPlayer,
      title: Component = LocalizationUtilsKt.lang("ui.party"),
      pokemon: List<BattlePokemon>,
      canSelect: (BattlePokemon) -> Boolean,
      cancel: (ServerPlayer) -> Unit = <unrepresentable>.INSTANCE as Function1,
      handler: (BattlePokemon) -> Unit
   ) {
      val `$this$map$iv`: java.lang.Iterable = pokemon;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(pokemon, 10));

      for (Object item$iv$iv : $this$map$iv) {
         val var17: PartySelectPokemonDTO = new PartySelectPokemonDTO((`item$iv$iv` as BattlePokemon).getEffectedPokemon(), false, 2, null);
         var17.setEnabled(canSelect.invoke(`item$iv$iv` as BattlePokemon) as java.lang.Boolean);
         `destination$iv$iv`.add(var17);
      }

      this.create(
         player, title, `destination$iv$iv` as MutableList<PartySelectPokemonDTO>, cancel, (new Function2<ServerPlayer, Integer, Unit>(handler, pokemon) {
            {
               super(2);
               this.$handler = `$handler`;
               this.$pokemon = `$pokemon`;
            }

            public final void invoke(@NotNull ServerPlayer var1, int index) {
               this.$handler.invoke(this.$pokemon.get(index));
            }
         }) as (ServerPlayer?, Int?) -> Unit
      );
   }

   @JvmOverloads
   public fun createFromPokemon(
      player: ServerPlayer,
      title: Component = LocalizationUtilsKt.lang("ui.party"),
      pokemon: List<Pokemon>,
      canSelect: (Pokemon) -> Boolean,
      cancel: (ServerPlayer) -> Unit = <unrepresentable>.INSTANCE as Function1,
      handler: (Pokemon) -> Unit
   ) {
      val `$this$map$iv`: java.lang.Iterable = pokemon;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(pokemon, 10));

      for (Object item$iv$iv : $this$map$iv) {
         val var17: PartySelectPokemonDTO = new PartySelectPokemonDTO(`item$iv$iv` as Pokemon, false, 2, null);
         var17.setEnabled(canSelect.invoke(`item$iv$iv` as Pokemon) as java.lang.Boolean);
         `destination$iv$iv`.add(var17);
      }

      this.create(
         player, title, `destination$iv$iv` as MutableList<PartySelectPokemonDTO>, cancel, (new Function2<ServerPlayer, Integer, Unit>(handler, pokemon) {
            {
               super(2);
               this.$handler = `$handler`;
               this.$pokemon = `$pokemon`;
            }

            public final void invoke(@NotNull ServerPlayer var1, int index) {
               this.$handler.invoke(this.$pokemon.get(index));
            }
         }) as (ServerPlayer?, Int?) -> Unit
      );
   }

   public fun handleCancelled(player: ServerPlayer, uuid: UUID) {
      val var10000: PartySelectCallback = callbacks.get(player.m_20148_());
      if (var10000 != null) {
         if (var10000.getUuid() == uuid) {
            callbacks.remove(player.m_20148_());
            var10000.getCancel().invoke(player);
         }
      }
   }

   public fun handleCallback(player: ServerPlayer, uuid: UUID, index: Int) {
      val var10000: PartySelectCallback = callbacks.get(player.m_20148_());
      if (var10000 != null) {
         callbacks.remove(player.m_20148_());
         if (!(var10000.getUuid() == uuid)) {
            Cobblemon.INSTANCE
               .getLOGGER()
               .warn("A party select callback ran but with a mismatching UUID from ${player.m_36316_().getName()}. Hacking attempts?");
         } else if (index >= var10000.getShownPokemon().size()) {
            Cobblemon.INSTANCE
               .getLOGGER()
               .warn(
                  "${player.m_36316_().getName()} used party select callback with an out of bounds index. Hacking attempts? Tried $index, Pokémon list size was ${var10000.getShownPokemon()
                     .size()}"
               );
         } else if (!var10000.getShownPokemon().get(index).getEnabled()) {
            Cobblemon.INSTANCE
               .getLOGGER()
               .warn("${player.m_36316_().getName()} used party select callback with a Pokémon that is not enabled. Hacking attempts?");
         } else {
            var10000.getHandler().invoke(player, index);
         }
      }
   }

   @JvmOverloads
   fun create(player: ServerPlayer, title: Component, pokemon: MutableList<PartySelectPokemonDTO>, handler: (ServerPlayer?, Int?) -> Unit) {
      create$default(this, player, title, pokemon, null, handler, 8, null);
   }

   @JvmOverloads
   fun create(player: ServerPlayer, pokemon: MutableList<PartySelectPokemonDTO>, handler: (ServerPlayer?, Int?) -> Unit) {
      create$default(this, player, null, pokemon, null, handler, 10, null);
   }

   @JvmOverloads
   fun createBattleSelect(
      player: ServerPlayer,
      title: Component,
      pokemon: MutableList<BattlePokemon>,
      canSelect: (BattlePokemon?) -> java.lang.Boolean,
      handler: (BattlePokemon?) -> Unit
   ) {
      createBattleSelect$default(this, player, title, pokemon, canSelect, null, handler, 16, null);
   }

   @JvmOverloads
   fun createBattleSelect(
      player: ServerPlayer, pokemon: MutableList<BattlePokemon>, canSelect: (BattlePokemon?) -> java.lang.Boolean, handler: (BattlePokemon?) -> Unit
   ) {
      createBattleSelect$default(this, player, null, pokemon, canSelect, null, handler, 18, null);
   }

   @JvmOverloads
   fun createFromPokemon(
      player: ServerPlayer, title: Component, pokemon: MutableList<Pokemon>, canSelect: (Pokemon?) -> java.lang.Boolean, handler: (Pokemon?) -> Unit
   ) {
      createFromPokemon$default(this, player, title, pokemon, canSelect, null, handler, 16, null);
   }

   @JvmOverloads
   fun createFromPokemon(player: ServerPlayer, pokemon: MutableList<Pokemon>, canSelect: (Pokemon?) -> java.lang.Boolean, handler: (Pokemon?) -> Unit) {
      createFromPokemon$default(this, player, null, pokemon, canSelect, null, handler, 18, null);
   }
}
