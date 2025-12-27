package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.callback.partymove

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectPokemonDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect.MoveSelectConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect.MoveSelectGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect.PartySelectConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect.PartySelectGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenPartyMoveCallbackPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.partymove.PartyMoveSelectCancelledPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.partymove.PartyPokemonMoveSelectedPacket
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Intrinsics
import kotlin.jvm.internal.Ref.ObjectRef
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.NotNull

public object OpenPartyMoveCallbackHandler : ClientNetworkPacketHandler<OpenPartyMoveCallbackPacket> {
   public open fun handle(packet: OpenPartyMoveCallbackPacket, client: Minecraft) {
      val pokemonToMoves: java.util.Map = MapsKt.toMap(packet.getPokemonList());
      val cancel: Function1 = (new Function1<Object, Unit>(packet) {
         {
            super(1);
            this.$packet = `$packet`;
         }

         public final void invoke(@NotNull Object it) {
            CobblemonNetwork.INSTANCE.sendToServer(new PartyMoveSelectCancelledPacket(this.$packet.getUuid()));
            if (it is MoveSelectGUI) {
               (it as MoveSelectGUI).closeProperly();
            } else if (it is PartySelectGUI) {
               (it as PartySelectGUI).closeProperly();
            }
         }
      }) as Function1;
      val partySelectConfiguration: ObjectRef = new ObjectRef();
      partySelectConfiguration.element = new PartySelectConfiguration(
         packet.getPartyTitle(),
         CollectionsKt.toList(pokemonToMoves.keySet()),
         cancel,
         cancel,
         (
            new Function2<PartySelectGUI, PartySelectPokemonDTO, Unit>(pokemonToMoves, cancel, partySelectConfiguration, packet) {
               {
                  super(2);
                  this.$pokemonToMoves = `$pokemonToMoves`;
                  this.$cancel = `$cancel`;
                  this.$partySelectConfiguration = `$partySelectConfiguration`;
                  this.$packet = `$packet`;
               }

               public final void invoke(@NotNull PartySelectGUI var1, @NotNull PartySelectPokemonDTO it) {
                  Minecraft.m_91087_()
                     .m_91152_(
                        new MoveSelectGUI(
                           OpenPartyMoveCallbackHandler.access$handle$makeMoveSelectConfiguration(
                              this.$pokemonToMoves, this.$cancel, this.$partySelectConfiguration, this.$packet, it
                           )
                        )
                     );
               }
            }
         ) as (PartySelectGUI?, PartySelectPokemonDTO?) -> Unit
      );
      val var10000: Minecraft = Minecraft.m_91087_();
      val var10001: PartySelectGUI = new PartySelectGUI;
      val var10003: PartySelectConfiguration;
      if (partySelectConfiguration.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("partySelectConfiguration");
         var10003 = null;
      } else {
         var10003 = partySelectConfiguration.element as PartySelectConfiguration;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003);
      var10000.m_91152_(var10001);
   }

   fun handleOnNettyThread(packet: OpenPartyMoveCallbackPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }

   @JvmStatic
   fun `handle$makeMoveSelectConfiguration`(
      pokemonToMoves: MutableMap<PartySelectPokemonDTO, MutableList<MoveSelectDTO>>,
      cancel: (Any?) -> Unit,
      partySelectConfiguration: ObjectRef<PartySelectConfiguration>,
      `$packet`: OpenPartyMoveCallbackPacket,
      pokemonSelectDTO: PartySelectPokemonDTO
   ): MoveSelectConfiguration {
      val var10002: MutableComponent = TextKt.text("");
      val var10003: Any = pokemonToMoves.get(pokemonSelectDTO);
      return new MoveSelectConfiguration(
         var10002,
         var10003 as MutableList<MoveSelectDTO>,
         cancel,
         (new Function1<MoveSelectGUI, Unit>(partySelectConfiguration) {
            {
               super(1);
               this.$partySelectConfiguration = `$partySelectConfiguration`;
            }

            public final void invoke(@NotNull MoveSelectGUI it) {
               val var10000: Minecraft = Minecraft.m_91087_();
               val var10001: PartySelectGUI = new PartySelectGUI;
               val var10003: PartySelectConfiguration;
               if (this.$partySelectConfiguration.element == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("partySelectConfiguration");
                  var10003 = null;
               } else {
                  var10003 = this.$partySelectConfiguration.element as PartySelectConfiguration;
               }

               var10001./* $VF: Unable to resugar constructor */<init>(var10003);
               var10000.m_91152_(var10001);
            }
         }) as (MoveSelectGUI?) -> Unit,
         (
            new Function2<MoveSelectGUI, MoveSelectDTO, Unit>(`$packet`, pokemonToMoves, pokemonSelectDTO) {
               {
                  super(2);
                  this.$packet = `$packet`;
                  this.$pokemonToMoves = `$pokemonToMoves`;
                  this.$pokemonSelectDTO = `$pokemonSelectDTO`;
               }

               public final void invoke(@NotNull MoveSelectGUI gui, @NotNull MoveSelectDTO moveSelectDTO) {
                  val moveIndex: java.util.List = this.$packet.getPokemonList();
                  val var5: PartySelectPokemonDTO = this.$pokemonSelectDTO;
                  var `index$iv`: Int = 0;
                  val var8: java.util.Iterator = moveIndex.iterator();

                  var var10000: Int;
                  while (true) {
                     if (!var8.hasNext()) {
                        var10000 = -1;
                        break;
                     }

                     if ((var8.next() as Pair).getFirst() == var5) {
                        var10000 = `index$iv`;
                        break;
                     }

                     `index$iv`++;
                  }

                  val var13: Any = this.$pokemonToMoves.get(this.$pokemonSelectDTO);
                  CobblemonNetwork.INSTANCE
                     .sendToServer(new PartyPokemonMoveSelectedPacket(this.$packet.getUuid(), var10000, (var13 as java.util.List).indexOf(moveSelectDTO)));
                  gui.closeProperly();
               }
            }
         ) as (MoveSelectGUI?, MoveSelectDTO?) -> Unit
      );
   }
}
