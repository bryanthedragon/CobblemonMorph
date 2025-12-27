package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.UUID
import java.util.Map.Entry
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nBattleInitializePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleInitializePacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,245:1\n11335#2:246\n11670#2,2:247\n11335#2:249\n11670#2,2:250\n11672#2:256\n11672#2:257\n1549#3:252\n1620#3,3:253\n*S KotlinDebug\n*F\n+ 1 BattleInitializePacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket\n*L\n59#1:246\n59#1:247,2\n61#1:249\n61#1:250,2\n61#1:256\n59#1:257\n66#1:252\n66#1:253,3\n*E\n"])
public class BattleInitializePacket : NetworkPacket<BattleInitializePacket> {
   public final lateinit var battleFormat: BattleFormat
   public final lateinit var battleId: UUID
   public open val id: ResourceLocation
   public final lateinit var side1: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.BattleSideDTO
   public final lateinit var side2: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.BattleSideDTO

   init {
      this.id = ID;
   }

   public constructor(battle: PokemonBattle, allySide: BattleSide?) : this() {
      val var10001: UUID = battle.getBattleId();
      this.setBattleId(var10001);
      this.setBattleFormat(battle.getFormat());
      val `$this$map$iv`: Array<BattleSide> = new BattleSide[]{battle.getSide1(), battle.getSide2()};
      val `destination$iv$iv`: java.util.Collection = new ArrayList(`$this$map$iv`.length);

      for (Object item$iv$iv : $this$map$iv) {
         val side: Any = `item$iv$iv`;
         val `$this$map$ivx`: Array<Any> = ((BattleSide)`item$iv$iv`).getActors();
         val `destination$iv$ivx`: java.util.Collection = new ArrayList(`$this$map$ivx`.length);

         for (Object item$iv$ivx : $this$map$ivx) {
            val var25: UUID = ((BattleActor)`item$iv$ivx`).getUuid();
            val var26: java.lang.String = ((BattleActor)`item$iv$ivx`).getShowdownId();
            val var27: MutableComponent = ((BattleActor)`item$iv$ivx`).getName();
            val `$this$map$ivxx`: java.lang.Iterable = ((BattleActor)`item$iv$ivx`).getActivePokemon();
            val `destination$iv$ivxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$ivxx`, 10));

            for (Object item$iv$ivxx : $this$map$ivxx) {
               val var10000: BattlePokemon = (`item$iv$ivxx` as ActiveBattlePokemon).getBattlePokemon();
               `destination$iv$ivxx`.add(
                  if (var10000 != null)
                     BattleInitializePacket.ActiveBattlePokemonDTO.Companion
                        .fromPokemon(var10000, allySide == side, (`item$iv$ivxx` as ActiveBattlePokemon).getIllusion())
                     else
                     null
               );
            }

            `destination$iv$ivx`.add(
               new BattleInitializePacket.BattleActorDTO(
                  var25,
                  var27,
                  var26,
                  `destination$iv$ivxx` as MutableList<BattleInitializePacket.ActiveBattlePokemonDTO>,
                  ((BattleActor)`item$iv$ivx`).getType()
               )
            );
         }

         `destination$iv$iv`.add(new BattleInitializePacket.BattleSideDTO(`destination$iv$ivx` as MutableList<BattleInitializePacket.BattleActorDTO>));
      }

      val sides: java.util.List = `destination$iv$iv` as java.util.List;
      this.setSide1((`destination$iv$iv` as java.util.List).get(0) as BattleInitializePacket.BattleSideDTO);
      this.setSide2(sides.get(1) as BattleInitializePacket.BattleSideDTO);
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.getBattleId());
      this.getBattleFormat().saveToBuffer(buffer);

      val var3: Array<BattleInitializePacket.BattleSideDTO>;
      for (BattleInitializePacket.BattleSideDTO side : var3) {
         NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, side.getActors().size());

         for (BattleInitializePacket.BattleActorDTO actor : side.getActors()) {
            buffer.m_130077_(actor.getUuid());
            buffer.m_130083_(actor.getDisplayName() as Component);
            buffer.m_130070_(actor.getShowdownId());
            NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, actor.getActivePokemon().size());

            for (BattleInitializePacket.ActiveBattlePokemonDTO activePokemon : actor.getActivePokemon()) {
               buffer.writeBoolean(activePokemon != null);
               if (activePokemon != null) {
                  activePokemon.saveToBuffer(buffer);
               }
            }

            NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, actor.getType().ordinal());
         }
      }
   }

   private fun decode(buffer: FriendlyByteBuf) {
      val var10001: UUID = buffer.m_130259_();
      this.setBattleId(var10001);
      this.setBattleFormat(BattleFormat.Companion.loadFromBuffer(buffer));
      val sides: java.util.List = new ArrayList();
      val var3: Byte = 2;

      for (int var4 = 0; var4 < var3; var4++) {
         val actors: java.util.List = new ArrayList();
         val var8: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);

         for (int var9 = 0; var9 < var8; var9++) {
            val uuid: UUID = buffer.m_130259_();
            val displayName: MutableComponent = buffer.m_130238_().m_6881_();
            val showdownId: java.lang.String = buffer.m_130277_();
            val activePokemon: java.util.List = new ArrayList();
            val type: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);

            for (int var17 = 0; var17 < type; var17++) {
               if (buffer.readBoolean()) {
                  activePokemon.add(BattleInitializePacket.ActiveBattlePokemonDTO.Companion.loadFromBuffer(buffer));
               } else {
                  activePokemon.add(null);
               }
            }

            val var20: ActorType = ActorType.values()[NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE)];
            actors.add(new BattleInitializePacket.BattleActorDTO(uuid, displayName, showdownId, activePokemon, var20));
         }

         sides.add(new BattleInitializePacket.BattleSideDTO(actors));
      }

      this.setSide1(sides.get(0) as BattleInitializePacket.BattleSideDTO);
      this.setSide2(sides.get(1) as BattleInitializePacket.BattleSideDTO);
   }

   override fun sendToPlayer(player: ServerPlayer) {
      NetworkPacket.DefaultImpls.sendToPlayer(this, player);
   }

   override fun sendToPlayers(players: MutableIterable<ServerPlayer>) {
      NetworkPacket.DefaultImpls.sendToPlayers(this, players);
   }

   override fun sendToAllPlayers() {
      NetworkPacket.DefaultImpls.sendToAllPlayers(this);
   }

   override fun sendToServer() {
      NetworkPacket.DefaultImpls.sendToServer(this);
   }

   override fun sendToPlayersAround(
      x: Double, y: Double, z: Double, distance: Double, worldKey: ResourceKey<Level>, exclusionCondition: (ServerPlayer?) -> java.lang.Boolean
   ) {
      NetworkPacket.DefaultImpls.sendToPlayersAround(this, x, y, z, distance, worldKey, exclusionCondition);
   }

   override fun toBuffer(): FriendlyByteBuf {
      return NetworkPacket.DefaultImpls.toBuffer(this);
   }

   @SourceDebugExtension(["SMAP\nBattleInitializePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleInitializePacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,245:1\n1#2:246\n*E\n"])
   public data ActiveBattlePokemonDTO(uuid: UUID,
      displayName: MutableComponent,
      properties: PokemonProperties,
      aspects: Set<String>,
      status: PersistentStatus?,
      hpValue: Float,
      maxHp: Float,
      isFlatHp: Boolean,
      statChanges: MutableMap<Stat, Int>
   ) {
      public final val aspects: Set<String>
      public final val displayName: MutableComponent
      public final val hpValue: Float
      public final val isFlatHp: Boolean
      public final val maxHp: Float
      public final val properties: PokemonProperties
      public final val statChanges: MutableMap<Stat, Int>
      public final val status: PersistentStatus?
      public final val uuid: UUID

      init {
         this.uuid = uuid;
         this.displayName = displayName;
         this.properties = properties;
         this.aspects = aspects;
         this.status = status;
         this.hpValue = hpValue;
         this.maxHp = maxHp;
         this.isFlatHp = isFlatHp;
         this.statChanges = statChanges;
      }

      public fun saveToBuffer(buffer: FriendlyByteBuf): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.ActiveBattlePokemonDTO {
         buffer.m_130077_(this.uuid);
         buffer.m_130083_(this.displayName as Component);
         buffer.m_130070_(PokemonProperties.asString$default(this.properties, null, 1, null));
         buffer.m_236828_(this.aspects, BattleInitializePacket.ActiveBattlePokemonDTO::saveToBuffer$lambda$0);
         buffer.writeBoolean(this.status != null);
         if (this.status != null) {
            buffer.m_130070_(this.status.getName().toString());
         }

         buffer.writeFloat(this.hpValue);
         buffer.writeFloat(this.maxHp);
         buffer.writeBoolean(this.isFlatHp);
         NetExtensionsKt.writeMapK(
            buffer as ByteBuf, IntSize.U_BYTE, this.statChanges, (new Function1<Entry<? extends Stat, ? extends Integer>, Unit>(buffer) {
               {
                  super(1);
                  this.$buffer = `$buffer`;
               }

               public final void invoke(@NotNull Entry<? extends Stat, Integer> var1) {
                  val stat: Stat = var1.getKey() as Stat;
                  val stages: Int = (var1.getValue() as java.lang.Number).intValue();
                  Cobblemon.INSTANCE.getStatProvider().encode(this.$buffer, stat);
                  NetExtensionsKt.writeSizedInt(this.$buffer as ByteBuf, IntSize.BYTE, stages);
               }
            }) as (MutableMap.MutableEntry<Stat, Int>?) -> Unit
         );
         return this;
      }

      public operator fun component1(): UUID {
         return this.uuid;
      }

      public operator fun component2(): MutableComponent {
         return this.displayName;
      }

      public operator fun component3(): PokemonProperties {
         return this.properties;
      }

      public operator fun component4(): Set<String> {
         return this.aspects;
      }

      public operator fun component5(): PersistentStatus? {
         return this.status;
      }

      public operator fun component6(): Float {
         return this.hpValue;
      }

      public operator fun component7(): Float {
         return this.maxHp;
      }

      public operator fun component8(): Boolean {
         return this.isFlatHp;
      }

      public operator fun component9(): MutableMap<Stat, Int> {
         return this.statChanges;
      }

      public fun copy(
         uuid: UUID = this.uuid,
         displayName: MutableComponent = this.displayName,
         properties: PokemonProperties = this.properties,
         aspects: Set<String> = this.aspects,
         status: PersistentStatus? = this.status,
         hpValue: Float = this.hpValue,
         maxHp: Float = this.maxHp,
         isFlatHp: Boolean = this.isFlatHp,
         statChanges: MutableMap<Stat, Int> = this.statChanges
      ): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.ActiveBattlePokemonDTO {
         return new BattleInitializePacket.ActiveBattlePokemonDTO(uuid, displayName, properties, aspects, status, hpValue, maxHp, isFlatHp, statChanges);
      }

      public override fun toString(): String {
         return "ActiveBattlePokemonDTO(uuid=${this.uuid}, displayName=${this.displayName}, properties=${this.properties}, aspects=${this.aspects}, status=${this.status}, hpValue=${this.hpValue}, maxHp=${this.maxHp}, isFlatHp=${this.isFlatHp}, statChanges=${this.statChanges})";
      }

      public override fun hashCode(): Int {
         val var10000: Int = (
               (
                        (
                                 (((this.uuid.hashCode() * 31 + this.displayName.hashCode()) * 31 + this.properties.hashCode()) * 31 + this.aspects.hashCode())
                                       * 31
                                    + (if (this.status == null) 0 else this.status.hashCode())
                              )
                              * 31
                           + java.lang.Float.hashCode(this.hpValue)
                     )
                     * 31
                  + java.lang.Float.hashCode(this.maxHp)
            )
            * 31;
         var var10001: Byte = this.isFlatHp;
         if (this.isFlatHp) {
            var10001 = 1;
         }

         return (var10000 + var10001) * 31 + this.statChanges.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is BattleInitializePacket.ActiveBattlePokemonDTO) {
            return false;
         } else {
            val var2: BattleInitializePacket.ActiveBattlePokemonDTO = other as BattleInitializePacket.ActiveBattlePokemonDTO;
            if (!(this.uuid == (other as BattleInitializePacket.ActiveBattlePokemonDTO).uuid)) {
               return false;
            } else if (!(this.displayName == var2.displayName)) {
               return false;
            } else if (!(this.properties == var2.properties)) {
               return false;
            } else if (!(this.aspects == var2.aspects)) {
               return false;
            } else if (!(this.status == var2.status)) {
               return false;
            } else if (java.lang.Float.compare(this.hpValue, var2.hpValue) != 0) {
               return false;
            } else if (java.lang.Float.compare(this.maxHp, var2.maxHp) != 0) {
               return false;
            } else if (this.isFlatHp != var2.isFlatHp) {
               return false;
            } else {
               return this.statChanges == var2.statChanges;
            }
         }
      }

      @JvmStatic
      fun `saveToBuffer$lambda$0`(buf: FriendlyByteBuf, it: java.lang.String) {
         buf.m_130070_(it);
      }

      @SourceDebugExtension(["SMAP\nBattleInitializePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleInitializePacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,245:1\n1#2:246\n*E\n"])
      public companion object {
         public fun fromPokemon(battlePokemon: BattlePokemon, isAlly: Boolean, illusion: BattlePokemon? = null): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.ActiveBattlePokemonDTO {
            val pokemon: Pokemon = battlePokemon.getEffectedPokemon();
            var var10000: Pokemon;
            if (isAlly) {
               var10000 = pokemon;
            } else {
               label23: {
                  if (illusion != null) {
                     var10000 = illusion.getEffectedPokemon();
                     if (var10000 != null) {
                        break label23;
                     }
                  }

                  var10000 = pokemon;
               }
            }

            val hpValue: Float = if (isAlly) pokemon.getCurrentHealth() else (float)pokemon.getCurrentHealth() / pokemon.getHp();
            val var23: UUID = var10000.getUuid();
            val var10001: MutableComponent = var10000.getDisplayName();
            val var22: PokemonProperties = var10000.createPokemonProperties(PokemonPropertyExtractor.SPECIES, PokemonPropertyExtractor.GENDER);
            var22.setLevel(pokemon.getLevel());
            val var10003: java.util.Set = var10000.getAspects();
            val var10004: PersistentStatusContainer = pokemon.getStatus();
            return new BattleInitializePacket.ActiveBattlePokemonDTO(
               var23,
               var10001,
               var22,
               var10003,
               if (var10004 != null) var10004.getStatus() else null,
               hpValue,
               pokemon.getHp(),
               isAlly,
               battlePokemon.getStatChanges()
            );
         }

         public fun fromMock(battlePokemon: BattlePokemon, isAlly: Boolean, mock: PokemonProperties): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.ActiveBattlePokemonDTO {
            val pokemon: Pokemon = battlePokemon.getEffectedPokemon();
            val hpValue: Float = if (isAlly) pokemon.getCurrentHealth() else (float)pokemon.getCurrentHealth() / pokemon.getHp();
            val var10000: UUID = battlePokemon.getUuid();
            val var10: MutableComponent = pokemon.getDisplayName();
            mock.setLevel(pokemon.getLevel());
            val var10003: java.util.Set = mock.getAspects();
            val var10004: PersistentStatusContainer = pokemon.getStatus();
            return new BattleInitializePacket.ActiveBattlePokemonDTO(
               var10000,
               var10,
               mock,
               var10003,
               if (var10004 != null) var10004.getStatus() else null,
               hpValue,
               pokemon.getHp(),
               isAlly,
               battlePokemon.getStatChanges()
            );
         }

         public fun loadFromBuffer(buffer: FriendlyByteBuf): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.ActiveBattlePokemonDTO {
            val uuid: UUID = buffer.m_130259_();
            val pokemonDisplayName: MutableComponent = buffer.m_130238_().m_6881_();
            val var10000: PokemonProperties.Companion = PokemonProperties.Companion;
            val var10001: java.lang.String = buffer.m_130277_();
            val properties: PokemonProperties = PokemonProperties.Companion.parse$default(var10000, var10001, " ", null, 4, null);
            val var12: java.util.List = buffer.m_236845_(BattleInitializePacket.ActiveBattlePokemonDTO.Companion::loadFromBuffer$lambda$2);
            val aspects: java.util.Set = CollectionsKt.toSet(var12);
            val var14: PersistentStatus;
            if (buffer.readBoolean()) {
               val var13: Statuses = Statuses.INSTANCE;
               val var15: ResourceLocation = buffer.m_130281_();
               val hpRatio: Status = var13.getStatus(var15);
               var14 = hpRatio as? PersistentStatus;
            } else {
               var14 = null;
            }

            val var11: Float = buffer.readFloat();
            val maxHp: Float = buffer.readFloat();
            val isFlatHp: Boolean = buffer.readBoolean();
            val statChanges: java.util.Map = new LinkedHashMap();
            NetExtensionsKt.readMapK(
               buffer as ByteBuf,
               IntSize.U_BYTE,
               statChanges,
               (
                  new Function0<Pair<? extends Stat, ? extends Integer>>(buffer) {
                     {
                        super(0);
                        this.$buffer = `$buffer`;
                     }

                     @NotNull
                     public final Pair<Stat, Integer> invoke() {
                        return TuplesKt.to(
                           Cobblemon.INSTANCE.getStatProvider().decode(this.$buffer), NetExtensionsKt.readSizedInt(this.$buffer as ByteBuf, IntSize.BYTE)
                        );
                     }
                  }
               ) as Function0
            );
            return new BattleInitializePacket.ActiveBattlePokemonDTO(uuid, pokemonDisplayName, properties, aspects, var14, var11, maxHp, isFlatHp, statChanges);
         }

         @JvmStatic
         fun `loadFromBuffer$lambda$2`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
            return `$buffer`.m_130277_();
         }
      }
   }

   public data BattleActorDTO(uuid: UUID,
      displayName: MutableComponent,
      showdownId: String,
      activePokemon: List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.ActiveBattlePokemonDTO?>,
      type: ActorType
   ) {
      public final val activePokemon: List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.ActiveBattlePokemonDTO?>
      public final val displayName: MutableComponent
      public final val showdownId: String
      public final val type: ActorType
      public final val uuid: UUID

      init {
         this.uuid = uuid;
         this.displayName = displayName;
         this.showdownId = showdownId;
         this.activePokemon = activePokemon;
         this.type = type;
      }

      public operator fun component1(): UUID {
         return this.uuid;
      }

      public operator fun component2(): MutableComponent {
         return this.displayName;
      }

      public operator fun component3(): String {
         return this.showdownId;
      }

      public operator fun component4(): List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.ActiveBattlePokemonDTO?> {
         return this.activePokemon;
      }

      public operator fun component5(): ActorType {
         return this.type;
      }

      public fun copy(
         uuid: UUID = this.uuid,
         displayName: MutableComponent = this.displayName,
         showdownId: String = this.showdownId,
         activePokemon: List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.ActiveBattlePokemonDTO?> = this.activePokemon,
         type: ActorType = this.type
      ): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.BattleActorDTO {
         return new BattleInitializePacket.BattleActorDTO(uuid, displayName, showdownId, activePokemon, type);
      }

      public override fun toString(): String {
         return "BattleActorDTO(uuid=${this.uuid}, displayName=${this.displayName}, showdownId=${this.showdownId}, activePokemon=${this.activePokemon}, type=${this.type})";
      }

      public override fun hashCode(): Int {
         return (((this.uuid.hashCode() * 31 + this.displayName.hashCode()) * 31 + this.showdownId.hashCode()) * 31 + this.activePokemon.hashCode()) * 31
            + this.type.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is BattleInitializePacket.BattleActorDTO) {
            return false;
         } else {
            val var2: BattleInitializePacket.BattleActorDTO = other as BattleInitializePacket.BattleActorDTO;
            if (!(this.uuid == (other as BattleInitializePacket.BattleActorDTO).uuid)) {
               return false;
            } else if (!(this.displayName == var2.displayName)) {
               return false;
            } else if (!(this.showdownId == var2.showdownId)) {
               return false;
            } else if (!(this.activePokemon == var2.activePokemon)) {
               return false;
            } else {
               return this.type === var2.type;
            }
         }
      }
   }

   public data BattleSideDTO(actors: List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.BattleActorDTO>) {
      public final val actors: List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.BattleActorDTO>

      init {
         this.actors = actors;
      }

      public operator fun component1(): List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.BattleActorDTO> {
         return this.actors;
      }

      public fun copy(actors: List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.BattleActorDTO> = this.actors): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket.BattleSideDTO {
         return new BattleInitializePacket.BattleSideDTO(actors);
      }

      public override fun toString(): String {
         return "BattleSideDTO(actors=${this.actors})";
      }

      public override fun hashCode(): Int {
         return this.actors.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is BattleInitializePacket.BattleSideDTO) {
            return false;
         } else {
            return this.actors == (other as BattleInitializePacket.BattleSideDTO).actors;
         }
      }
   }

   @SourceDebugExtension(["SMAP\nBattleInitializePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleInitializePacket.kt\ncom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,245:1\n1#2:246\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): BattleInitializePacket {
         val var2: BattleInitializePacket = new BattleInitializePacket();
         BattleInitializePacket.access$decode(var2, buffer);
         return var2;
      }
   }
}
