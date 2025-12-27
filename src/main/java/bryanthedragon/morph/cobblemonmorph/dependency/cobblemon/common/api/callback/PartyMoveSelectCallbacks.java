package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenPartyMoveCallbackPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.UUID
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function5
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nPartyMoveSelectCallback.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartyMoveSelectCallback.kt\ncom/cobblemon/mod/common/api/callback/PartyMoveSelectCallbacks\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,119:1\n1549#2:120\n1620#2,3:121\n*S KotlinDebug\n*F\n+ 1 PartyMoveSelectCallback.kt\ncom/cobblemon/mod/common/api/callback/PartyMoveSelectCallbacks\n*L\n63#1:120\n63#1:121,3\n*E\n"])
public object PartyMoveSelectCallbacks {
   public final val callbacks: MutableMap<UUID, PartyMoveSelectCallback> = (new LinkedHashMap()) as java.util.Map

   @JvmOverloads
   public fun create(
      player: ServerPlayer,
      partyTitle: MutableComponent = LocalizationUtilsKt.lang("ui.party"),
      pokemon: List<Pair<PartySelectPokemonDTO, List<MoveSelectDTO>>>,
      cancel: (ServerPlayer) -> Unit = <unrepresentable>.INSTANCE as Function1,
      handler: (ServerPlayer, Int, PartySelectPokemonDTO, Int, MoveSelectDTO) -> Unit
   ) {
      val callback: PartyMoveSelectCallback = new PartyMoveSelectCallback(null, pokemon, cancel, handler, 1, null);
      val var7: java.util.Map = callbacks;
      val var10000: UUID = player.m_20148_();
      var7.put(var10000, callback);
      CobblemonNetwork.INSTANCE.sendPacket(player, new OpenPartyMoveCallbackPacket(callback.getUuid(), partyTitle, callback.getPokemon()));
   }

   @JvmOverloads
   public fun createFromPokemon(
      player: ServerPlayer,
      partyTitle: MutableComponent = LocalizationUtilsKt.lang("ui.party"),
      pokemon: List<Pokemon>,
      moves: (Pokemon) -> List<Move> = <unrepresentable>.INSTANCE as Function1,
      canSelectPokemon: (Pokemon) -> Boolean = <unrepresentable>.INSTANCE as Function1,
      canSelectMove: (Pokemon, Move) -> Boolean = <unrepresentable>.INSTANCE as Function2,
      cancel: (ServerPlayer) -> Unit = <unrepresentable>.INSTANCE as Function1,
      handler: (Pokemon, Move) -> Unit
   ) {
      val pokemonList: java.util.List = new ArrayList();

      for (Pokemon pk : pokemon) {
         val enabled: Boolean = canSelectPokemon.invoke(pk) as java.lang.Boolean;
         val `$this$map$iv`: java.lang.Iterable = moves.invoke(pk) as java.lang.Iterable;
         val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

         for (Object item$iv$iv : $this$map$iv) {
            `destination$iv$iv`.add(new MoveSelectDTO(`item$iv$iv` as Move, canSelectMove.invoke(pk, `item$iv$iv` as Move) as java.lang.Boolean));
         }

         pokemonList.add(TuplesKt.to(new PartySelectPokemonDTO(pk, enabled), `destination$iv$iv` as java.util.List));
      }

      this.create(
         player,
         partyTitle,
         pokemonList,
         cancel,
         (new Function5<ServerPlayer, Integer, PartySelectPokemonDTO, Integer, MoveSelectDTO, Unit>(handler, pokemon) {
            {
               super(5);
               this.$handler = `$handler`;
               this.$pokemon = `$pokemon`;
            }

            public final void invoke(@NotNull ServerPlayer var1, int pkIndex, @NotNull PartySelectPokemonDTO var3, int moveIndex, @NotNull MoveSelectDTO var5) {
               val var10000: Function2 = this.$handler;
               val var10001: Any = this.$pokemon.get(pkIndex);
               val var10002: Move = this.$pokemon.get(pkIndex).getMoveSet().get(moveIndex);
               if (var10002 != null) {
                  var10000.invoke(var10001, var10002);
               }
            }
         }) as (ServerPlayer?, Int?, PartySelectPokemonDTO?, Int?, MoveSelectDTO?) -> Unit
      );
   }

   public fun handleCancelled(player: ServerPlayer, uuid: UUID) {
      val var10000: PartyMoveSelectCallback = callbacks.get(player.m_20148_());
      if (var10000 != null) {
         if (var10000.getUuid() == uuid) {
            callbacks.remove(player.m_20148_());
            var10000.getCancel().invoke(player);
         }
      }
   }

   public fun handleCallback(player: ServerPlayer, uuid: UUID, pokemonIndex: Int, moveIndex: Int) {
      val var10000: PartyMoveSelectCallback = callbacks.get(player.m_20148_());
      if (var10000 != null) {
         callbacks.remove(player.m_20148_());
         if (!(var10000.getUuid() == uuid)) {
            Cobblemon.INSTANCE
               .getLOGGER()
               .warn("A party move select callback ran but with a mismatching UUID from ${player.m_36316_().getName()}. Hacking attempts?");
         } else if (pokemonIndex >= var10000.getPokemon().size()) {
            Cobblemon.INSTANCE
               .getLOGGER()
               .warn(
                  "${player.m_36316_().getName()} used party move select callback with a Pokémon index that was out of bounds. Hacking attempts? Tried $pokemonIndex, possible size was ${var10000.getPokemon()
                     .size()}"
               );
         } else {
            val var6: Pair = var10000.getPokemon().get(pokemonIndex);
            val pokemon: PartySelectPokemonDTO = var6.component1() as PartySelectPokemonDTO;
            val moves: java.util.List = var6.component2() as java.util.List;
            if (!pokemon.getEnabled()) {
               Cobblemon.INSTANCE
                  .getLOGGER()
                  .warn("${player.m_36316_().getName()} used party move select callback with a Pokémon that is not enabled. Hacking attempts?");
            } else if (moveIndex >= moves.size()) {
               Cobblemon.INSTANCE
                  .getLOGGER()
                  .warn(
                     "${player.m_36316_().getName()} used party move select callback with a move index that was out of bounds. Hacking attempts? Tried $pokemonIndex-$moveIndex, possible size was ${moves.size()}"
                  );
            } else {
               val move: MoveSelectDTO = moves.get(moveIndex) as MoveSelectDTO;
               if (!move.getEnabled()) {
                  Cobblemon.INSTANCE
                     .getLOGGER()
                     .warn("${player.m_36316_().getName()} used party move select callback with a move that is not enabled. Hacking attempts?");
               } else {
                  var10000.getHandler().invoke(player, pokemonIndex, pokemon, moveIndex, move);
               }
            }
         }
      }
   }

   @JvmOverloads
   fun create(
      player: ServerPlayer,
      partyTitle: MutableComponent,
      pokemon: MutableList<Pair<? extends PartySelectPokemonDTO, ? extends java.utilList<MoveSelectDTO>>>,
      handler: (ServerPlayer?, Int?, PartySelectPokemonDTO?, Int?, MoveSelectDTO?) -> Unit
   ) {
      create$default(this, player, partyTitle, pokemon, null, handler, 8, null);
   }

   @JvmOverloads
   fun create(
      player: ServerPlayer,
      pokemon: MutableList<Pair<? extends PartySelectPokemonDTO, ? extends java.utilList<MoveSelectDTO>>>,
      handler: (ServerPlayer?, Int?, PartySelectPokemonDTO?, Int?, MoveSelectDTO?) -> Unit
   ) {
      create$default(this, player, null, pokemon, null, handler, 10, null);
   }

   @JvmOverloads
   fun createFromPokemon(
      player: ServerPlayer,
      partyTitle: MutableComponent,
      pokemon: MutableList<Pokemon>,
      moves: (Pokemon?) -> MutableList<Move>,
      canSelectPokemon: (Pokemon?) -> java.lang.Boolean,
      canSelectMove: (Pokemon?, Move?) -> java.lang.Boolean,
      handler: (Pokemon?, Move?) -> Unit
   ) {
      createFromPokemon$default(this, player, partyTitle, pokemon, moves, canSelectPokemon, canSelectMove, null, handler, 64, null);
   }

   @JvmOverloads
   fun createFromPokemon(
      player: ServerPlayer,
      partyTitle: MutableComponent,
      pokemon: MutableList<Pokemon>,
      moves: (Pokemon?) -> MutableList<Move>,
      canSelectPokemon: (Pokemon?) -> java.lang.Boolean,
      handler: (Pokemon?, Move?) -> Unit
   ) {
      createFromPokemon$default(this, player, partyTitle, pokemon, moves, canSelectPokemon, null, null, handler, 96, null);
   }

   @JvmOverloads
   fun createFromPokemon(
      player: ServerPlayer,
      partyTitle: MutableComponent,
      pokemon: MutableList<Pokemon>,
      moves: (Pokemon?) -> MutableList<Move>,
      handler: (Pokemon?, Move?) -> Unit
   ) {
      createFromPokemon$default(this, player, partyTitle, pokemon, moves, null, null, null, handler, 112, null);
   }

   @JvmOverloads
   fun createFromPokemon(player: ServerPlayer, partyTitle: MutableComponent, pokemon: MutableList<Pokemon>, handler: (Pokemon?, Move?) -> Unit) {
      createFromPokemon$default(this, player, partyTitle, pokemon, null, null, null, null, handler, 120, null);
   }

   @JvmOverloads
   fun createFromPokemon(player: ServerPlayer, pokemon: MutableList<Pokemon>, handler: (Pokemon?, Move?) -> Unit) {
      createFromPokemon$default(this, player, null, pokemon, null, null, null, null, handler, 122, null);
   }
}
