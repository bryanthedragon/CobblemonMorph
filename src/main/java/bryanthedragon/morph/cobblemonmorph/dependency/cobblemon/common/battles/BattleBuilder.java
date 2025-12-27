package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PokemonBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.UUID
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerPlayer
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nBattleBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleBuilder.kt\ncom/cobblemon/mod/common/battles/BattleBuilder\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,285:1\n1045#2:286\n*S KotlinDebug\n*F\n+ 1 BattleBuilder.kt\ncom/cobblemon/mod/common/battles/BattleBuilder\n*L\n102#1:286\n*E\n"])
public object BattleBuilder {
   @JvmOverloads
   public fun pvp1v1(
      player1: ServerPlayer,
      player2: ServerPlayer,
      leadingPokemonPlayer1: UUID? = null,
      leadingPokemonPlayer2: UUID? = null,
      battleFormat: BattleFormat = BattleFormat.Companion.getGEN_9_SINGLES(),
      cloneParties: Boolean = false,
      healFirst: Boolean = false,
      partyAccessor: (ServerPlayer) -> PartyStore = <unrepresentable>.INSTANCE as Function1
   ): BattleStartResult {
      val team1: java.util.List = (partyAccessor.invoke(player1) as PartyStore).toBattleTeam(cloneParties, !healFirst, leadingPokemonPlayer1);
      val team2: java.util.List = (partyAccessor.invoke(player2) as PartyStore).toBattleTeam(cloneParties, !healFirst, leadingPokemonPlayer2);
      var var10002: UUID = player1.m_20148_();
      val player1Actor: PlayerBattleActor = new PlayerBattleActor(var10002, team1);
      var10002 = player2.m_20148_();
      val player2Actor: PlayerBattleActor = new PlayerBattleActor(var10002, team2);
      val errors: ErroredBattleStart = new ErroredBattleStart(null, null, 3, null);

      val var15: Array<Pair>;
      for (Pair var17 : var15) {
         val player: ServerPlayer = var17.component1() as ServerPlayer;
         val actor: PlayerBattleActor = var17.component2() as PlayerBattleActor;
         if (actor.getPokemonList().size() < battleFormat.getBattleType().getSlotsPerActor()) {
            (errors.getParticipantErrors().get((Object)actor) as java.util.Collection)
               .add(BattleStartError.Companion.insufficientPokemon(player, battleFormat.getBattleType().getSlotsPerActor(), actor.getPokemonList().size()));
         }

         if (BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(player) != null) {
            (errors.getParticipantErrors().get((Object)actor) as java.util.Collection).add(BattleStartError.Companion.alreadyInBattle(player));
         }
      }

      return if (errors.isEmpty())
         BattleRegistry.startBattle$default(BattleRegistry.INSTANCE, battleFormat, new BattleSide(player1Actor), new BattleSide(player2Actor), false, 8, null)
            .ifSuccessful((new Function1<PokemonBattle, Unit>(player1Actor, player2, player2Actor, player1) {
               {
                  super(1);
                  this.$player1Actor = `$player1Actor`;
                  this.$player2 = `$player2`;
                  this.$player2Actor = `$player2Actor`;
                  this.$player1 = `$player1`;
               }

               public final void invoke(@NotNull PokemonBattle it) {
                  this.$player1Actor.setBattleTheme(PlayerExtensionsKt.getBattleTheme(this.$player2));
                  this.$player2Actor.setBattleTheme(PlayerExtensionsKt.getBattleTheme(this.$player1));
               }
            }) as (PokemonBattle?) -> Unit)
         else
         errors;
   }

   @JvmOverloads
   public fun pve(
      player: ServerPlayer,
      pokemonEntity: PokemonEntity,
      leadingPokemon: UUID? = null,
      battleFormat: BattleFormat = BattleFormat.Companion.getGEN_9_SINGLES(),
      cloneParties: Boolean = false,
      healFirst: Boolean = false,
      fleeDistance: Float = Cobblemon.INSTANCE.getConfig().getDefaultFleeDistance(),
      party: PartyStore = PlayerExtensionsKt.party(player) as PartyStore
   ): BattleStartResult {
      val playerTeam: java.util.List = CollectionsKt.sortedWith(
         party.toBattleTeam(cloneParties, !healFirst, leadingPokemon), new BattleBuilder$pve$$inlined$sortedBy$1()
      );
      var var10002: UUID = player.m_20148_();
      val var14: PlayerBattleActor = new PlayerBattleActor(var10002, playerTeam);
      var10002 = pokemonEntity.getPokemon().getUuid();
      val var15: PokemonBattleActor = new PokemonBattleActor(
         var10002, new BattlePokemon(pokemonEntity.getPokemon(), null, null, 6, null), fleeDistance, null, 8, null
      );
      val errors: ErroredBattleStart = new ErroredBattleStart(null, null, 3, null);
      if (!playerTeam.isEmpty() && (playerTeam.get(0) as BattlePokemon).getHealth() <= 0) {
         (errors.getParticipantErrors().get((Object)var14) as java.util.Collection)
            .add(BattleStartError.Companion.insufficientPokemon(player, battleFormat.getBattleType().getSlotsPerActor(), var14.getPokemonList().size()));
      }

      if (var14.getPokemonList().size() < battleFormat.getBattleType().getSlotsPerActor()) {
         (errors.getParticipantErrors().get((Object)var14) as java.util.Collection)
            .add(BattleStartError.Companion.insufficientPokemon(player, battleFormat.getBattleType().getSlotsPerActor(), var14.getPokemonList().size()));
      }

      if (BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(player) != null) {
         (errors.getParticipantErrors().get((Object)var14) as java.util.Collection).add(BattleStartError.Companion.alreadyInBattle(var14));
      }

      if (pokemonEntity.getBattleId() != null) {
         (errors.getParticipantErrors().get((Object)var15) as java.util.Collection).add(BattleStartError.Companion.alreadyInBattle(var15));
      }

      return if (errors.isEmpty())
         BattleRegistry.startBattle$default(BattleRegistry.INSTANCE, battleFormat, new BattleSide(var14), new BattleSide(var15), false, 8, null)
            .ifSuccessful((new Function1<PokemonBattle, Unit>(cloneParties, pokemonEntity, var14) {
               {
                  super(1);
                  this.$cloneParties = `$cloneParties`;
                  this.$pokemonEntity = `$pokemonEntity`;
                  this.$playerActor = `$playerActor`;
               }

               public final void invoke(@NotNull PokemonBattle it) {
                  if (!this.$cloneParties) {
                     this.$pokemonEntity.setBattleId(it.getBattleId());
                  }

                  this.$playerActor.setBattleTheme(this.$pokemonEntity.getBattleTheme());
               }
            }) as (PokemonBattle?) -> Unit)
         else
         errors;
   }

   @JvmOverloads
   fun pvp1v1(
      player1: ServerPlayer,
      player2: ServerPlayer,
      leadingPokemonPlayer1: UUID?,
      leadingPokemonPlayer2: UUID?,
      battleFormat: BattleFormat,
      cloneParties: Boolean,
      healFirst: Boolean
   ): BattleStartResult {
      return pvp1v1$default(this, player1, player2, leadingPokemonPlayer1, leadingPokemonPlayer2, battleFormat, cloneParties, healFirst, null, 128, null);
   }

   @JvmOverloads
   fun pvp1v1(
      player1: ServerPlayer,
      player2: ServerPlayer,
      leadingPokemonPlayer1: UUID?,
      leadingPokemonPlayer2: UUID?,
      battleFormat: BattleFormat,
      cloneParties: Boolean
   ): BattleStartResult {
      return pvp1v1$default(this, player1, player2, leadingPokemonPlayer1, leadingPokemonPlayer2, battleFormat, cloneParties, false, null, 192, null);
   }

   @JvmOverloads
   fun pvp1v1(player1: ServerPlayer, player2: ServerPlayer, leadingPokemonPlayer1: UUID?, leadingPokemonPlayer2: UUID?, battleFormat: BattleFormat): BattleStartResult {
      return pvp1v1$default(this, player1, player2, leadingPokemonPlayer1, leadingPokemonPlayer2, battleFormat, false, false, null, 224, null);
   }

   @JvmOverloads
   fun pvp1v1(player1: ServerPlayer, player2: ServerPlayer, leadingPokemonPlayer1: UUID?, leadingPokemonPlayer2: UUID?): BattleStartResult {
      return pvp1v1$default(this, player1, player2, leadingPokemonPlayer1, leadingPokemonPlayer2, null, false, false, null, 240, null);
   }

   @JvmOverloads
   fun pvp1v1(player1: ServerPlayer, player2: ServerPlayer, leadingPokemonPlayer1: UUID?): BattleStartResult {
      return pvp1v1$default(this, player1, player2, leadingPokemonPlayer1, null, null, false, false, null, 248, null);
   }

   @JvmOverloads
   fun pvp1v1(player1: ServerPlayer, player2: ServerPlayer): BattleStartResult {
      return pvp1v1$default(this, player1, player2, null, null, null, false, false, null, 252, null);
   }

   @JvmOverloads
   fun pve(
      player: ServerPlayer,
      pokemonEntity: PokemonEntity,
      leadingPokemon: UUID?,
      battleFormat: BattleFormat,
      cloneParties: Boolean,
      healFirst: Boolean,
      fleeDistance: Float
   ): BattleStartResult {
      return pve$default(this, player, pokemonEntity, leadingPokemon, battleFormat, cloneParties, healFirst, fleeDistance, null, 128, null);
   }

   @JvmOverloads
   fun pve(player: ServerPlayer, pokemonEntity: PokemonEntity, leadingPokemon: UUID?, battleFormat: BattleFormat, cloneParties: Boolean, healFirst: Boolean): BattleStartResult {
      return pve$default(this, player, pokemonEntity, leadingPokemon, battleFormat, cloneParties, healFirst, 0.0F, null, 192, null);
   }

   @JvmOverloads
   fun pve(player: ServerPlayer, pokemonEntity: PokemonEntity, leadingPokemon: UUID?, battleFormat: BattleFormat, cloneParties: Boolean): BattleStartResult {
      return pve$default(this, player, pokemonEntity, leadingPokemon, battleFormat, cloneParties, false, 0.0F, null, 224, null);
   }

   @JvmOverloads
   fun pve(player: ServerPlayer, pokemonEntity: PokemonEntity, leadingPokemon: UUID?, battleFormat: BattleFormat): BattleStartResult {
      return pve$default(this, player, pokemonEntity, leadingPokemon, battleFormat, false, false, 0.0F, null, 240, null);
   }

   @JvmOverloads
   fun pve(player: ServerPlayer, pokemonEntity: PokemonEntity, leadingPokemon: UUID?): BattleStartResult {
      return pve$default(this, player, pokemonEntity, leadingPokemon, null, false, false, 0.0F, null, 248, null);
   }

   @JvmOverloads
   fun pve(player: ServerPlayer, pokemonEntity: PokemonEntity): BattleStartResult {
      return pve$default(this, player, pokemonEntity, null, null, false, false, 0.0F, null, 252, null);
   }
}
