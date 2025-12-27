package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleMove
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenMoveCallbackPacket
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.UUID
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nMoveSelectCallback.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveSelectCallback.kt\ncom/cobblemon/mod/common/api/callback/MoveSelectCallbacks\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,133:1\n1549#2:134\n1620#2,2:135\n1622#2:138\n1549#2:139\n1620#2,3:140\n1#3:137\n*S KotlinDebug\n*F\n+ 1 MoveSelectCallback.kt\ncom/cobblemon/mod/common/api/callback/MoveSelectCallbacks\n*L\n65#1:134\n65#1:135,2\n65#1:138\n79#1:139\n79#1:140,3\n*E\n"])
public object MoveSelectCallbacks {
   public final val callbacks: MutableMap<UUID, MoveSelectCallback> = (new LinkedHashMap()) as java.util.Map

   @JvmOverloads
   public fun create(
      player: ServerPlayer,
      title: Component = TextKt.text("") as Component,
      possibleMoves: List<MoveSelectDTO>,
      cancel: (ServerPlayer) -> Unit = <unrepresentable>.INSTANCE as Function1,
      handler: (ServerPlayer, Int, MoveSelectDTO) -> Unit
   ) {
      val callback: MoveSelectCallback = new MoveSelectCallback(null, possibleMoves, cancel, handler, 1, null);
      val var7: java.util.Map = callbacks;
      val var10000: UUID = player.m_20148_();
      var7.put(var10000, callback);
      val var8: CobblemonNetwork = CobblemonNetwork.INSTANCE;
      val var10004: UUID = callback.getUuid();
      val var10005: MutableComponent = title.m_6881_();
      var8.sendPacket(player, new OpenMoveCallbackPacket(var10004, var10005, possibleMoves));
   }

   @JvmOverloads
   public fun create(
      player: ServerPlayer,
      moves: List<Move>,
      canSelect: (Move) -> Boolean = <unrepresentable>.INSTANCE as Function1,
      cancel: (ServerPlayer) -> Unit = <unrepresentable>.INSTANCE as Function1,
      handler: (Move) -> Unit
   ) {
      val `$this$map$iv`: java.lang.Iterable = moves;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(moves, 10));

      for (Object item$iv$iv : $this$map$iv) {
         val var15: MoveSelectDTO = new MoveSelectDTO(`item$iv$iv` as Move, false, 2, null);
         var15.setEnabled(canSelect.invoke(`item$iv$iv` as Move) as java.lang.Boolean);
         `destination$iv$iv`.add(var15);
      }

      create$default(
         this, player, null, `destination$iv$iv` as java.util.List, cancel, (new Function3<ServerPlayer, Integer, MoveSelectDTO, Unit>(handler, moves) {
            {
               super(3);
               this.$handler = `$handler`;
               this.$moves = `$moves`;
            }

            public final void invoke(@NotNull ServerPlayer var1, int index, @NotNull MoveSelectDTO var3) {
               this.$handler.invoke(this.$moves.get(index));
            }
         }) as Function3, 2, null
      );
   }

   @JvmOverloads
   public fun createBattleSelect(
      player: ServerPlayer,
      moves: List<InBattleMove>,
      canSelect: (InBattleMove) -> Boolean = <unrepresentable>.INSTANCE as Function1,
      cancel: (ServerPlayer) -> Unit = <unrepresentable>.INSTANCE as Function1,
      handler: (InBattleMove) -> Unit
   ) {
      val `$this$map$iv`: java.lang.Iterable = moves;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(moves, 10));

      for (Object item$iv$iv : $this$map$iv) {
         val var15: MoveSelectDTO = new MoveSelectDTO(`item$iv$iv` as InBattleMove, false, 2, null);
         var15.setEnabled(canSelect.invoke(`item$iv$iv` as InBattleMove) as java.lang.Boolean);
         `destination$iv$iv`.add(var15);
      }

      create$default(
         this, player, null, `destination$iv$iv` as java.util.List, cancel, (new Function3<ServerPlayer, Integer, MoveSelectDTO, Unit>(handler, moves) {
            {
               super(3);
               this.$handler = `$handler`;
               this.$moves = `$moves`;
            }

            public final void invoke(@NotNull ServerPlayer var1, int index, @NotNull MoveSelectDTO var3) {
               this.$handler.invoke(this.$moves.get(index));
            }
         }) as Function3, 2, null
      );
   }

   public fun handleCancelled(player: ServerPlayer, uuid: UUID) {
      val var10000: MoveSelectCallback = callbacks.get(player.m_20148_());
      if (var10000 != null) {
         if (var10000.getUuid() == uuid) {
            callbacks.remove(player.m_20148_());
            var10000.getCancel().invoke(player);
         }
      }
   }

   public fun handleCallback(player: ServerPlayer, uuid: UUID, index: Int) {
      val var10000: MoveSelectCallback = callbacks.get(player.m_20148_());
      if (var10000 != null) {
         callbacks.remove(player.m_20148_());
         if (!(var10000.getUuid() == uuid)) {
            Cobblemon.INSTANCE
               .getLOGGER()
               .warn("A move select callback ran but with a mismatching UUID from ${player.m_36316_().getName()}. Hacking attempts?");
         } else if (index >= var10000.getShownMoves().size()) {
            Cobblemon.INSTANCE
               .getLOGGER()
               .warn(
                  "${player.m_36316_().getName()} used move select callback with an index that was out of bounds. Hacking attempts? Tried $index, possible size was ${var10000.getShownMoves()
                     .size()}"
               );
         } else if (!var10000.getShownMoves().get(index).getEnabled()) {
            Cobblemon.INSTANCE.getLOGGER().warn("${player.m_36316_().getName()} used move select callback with a move that is not enabled. Hacking attempts?");
         } else {
            var10000.getHandler().invoke(player, index, var10000.getShownMoves().get(index));
         }
      }
   }

   @JvmOverloads
   fun create(player: ServerPlayer, title: Component, possibleMoves: MutableList<MoveSelectDTO>, handler: (ServerPlayer?, Int?, MoveSelectDTO?) -> Unit) {
      create$default(this, player, title, possibleMoves, null, handler, 8, null);
   }

   @JvmOverloads
   fun create(player: ServerPlayer, possibleMoves: MutableList<MoveSelectDTO>, handler: (ServerPlayer?, Int?, MoveSelectDTO?) -> Unit) {
      create$default(this, player, null, possibleMoves, null, handler, 10, null);
   }

   @JvmOverloads
   fun create(player: ServerPlayer, moves: MutableList<Move>, canSelect: (Move?) -> java.lang.Boolean, handler: (Move?) -> Unit) {
      create$default(this, player, moves, canSelect, null, handler, 8, null);
   }

   @JvmOverloads
   fun create(player: ServerPlayer, moves: MutableList<Move>, handler: (Move?) -> Unit) {
      create$default(this, player, moves, null, null, handler, 12, null);
   }

   @JvmOverloads
   fun createBattleSelect(
      player: ServerPlayer, moves: MutableList<InBattleMove>, canSelect: (InBattleMove?) -> java.lang.Boolean, handler: (InBattleMove?) -> Unit
   ) {
      createBattleSelect$default(this, player, moves, canSelect, null, handler, 8, null);
   }

   @JvmOverloads
   fun createBattleSelect(player: ServerPlayer, moves: MutableList<InBattleMove>, handler: (InBattleMove?) -> Unit) {
      createBattleSelect$default(this, player, moves, null, null, handler, 12, null);
   }
}
