package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nShowdownActionRequest.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownActionRequest\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,452:1\n1#2:453\n1855#3,2:454\n1855#3,2:456\n1855#3:458\n1855#3,2:459\n1856#3:461\n*S KotlinDebug\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownActionRequest\n*L\n49#1:454,2\n51#1:456,2\n77#1:458\n78#1:459,2\n77#1:461\n*E\n"])
public class ShowdownActionRequest(wait: Boolean = false,
   active: MutableList<ShowdownMoveset>? = null,
   forceSwitch: List<Boolean> = CollectionsKt.emptyList(),
   noCancel: Boolean = false,
   side: ShowdownSide? = null
) {
   public final var active: MutableList<ShowdownMoveset>?
   public final var forceSwitch: List<Boolean>
   public final var noCancel: Boolean
   public final var side: ShowdownSide?
   public final var wait: Boolean

   init {
      this.wait = wait;
      this.active = active;
      this.forceSwitch = forceSwitch;
      this.noCancel = noCancel;
      this.side = side;
   }

   public fun <T, E : Targetable> iterate(activePokemon: List<Any>, iterator: (Any, ShowdownMoveset?, Boolean) -> Any): List<Any> {
      val size: Int = Integer.max(if (this.active != null) this.active.size() else 0, this.forceSwitch.size());
      val responses: java.util.List = new ArrayList();

      for (int var5 = 0; var5 < size; var5++) {
         if (activePokemon.size() <= var5) {
            throw new IllegalStateException("No active Pokémon for slot $var5 but needed to choose action for it?");
         }

         val activeBattlePokemon: Targetable = activePokemon.get(var5) as Targetable;
         val var10000: ShowdownMoveset;
         if (this.active != null) {
            val it: java.util.List = this.active;
            var10000 = if (this.active.size() > var5) it.get(var5) as ShowdownMoveset else null;
         } else {
            var10000 = null;
         }

         val var14: java.util.List = this.forceSwitch;
         responses.add(iterator.invoke(activeBattlePokemon, var10000, this.forceSwitch.size() > var5 && var14.get(var5) as java.lang.Boolean));
      }

      return responses;
   }

   public fun saveToBuffer(buffer: FriendlyByteBuf) {
      buffer.writeBoolean(this.wait);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, if (this.active != null) this.active.size() else 0);
      if (this.active != null) {
         val `$i$f$forEach`: java.lang.Iterable;
         for (Object element$iv : $i$f$forEach) {
            (p0 as ShowdownMoveset).saveToBuffer(buffer);
         }
      }

      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.forceSwitch.size());

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         buffer.writeBoolean(var11 as java.lang.Boolean);
      }

      buffer.writeBoolean(this.noCancel);
      buffer.writeBoolean(this.side != null);
      if (this.side != null) {
         this.side.saveToBuffer(buffer);
      }
   }

   public fun loadFromBuffer(buffer: FriendlyByteBuf): ShowdownActionRequest {
      this.wait = buffer.readBoolean();
      val activeSize: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);
      if (activeSize > 0) {
         val forceSwitch: java.util.List = new ArrayList();

         for (int var4 = 0; var4 < activeSize; var4++) {
            forceSwitch.add(new ShowdownMoveset().loadFromBuffer(buffer));
         }

         this.active = forceSwitch;
      }

      val var8: java.util.List = new ArrayList();
      val var9: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);

      for (int it = 0; it < var9; it++) {
         var8.add(buffer.readBoolean());
      }

      this.forceSwitch = var8;
      this.noCancel = buffer.readBoolean();
      if (buffer.readBoolean()) {
         this.side = new ShowdownSide().loadFromBuffer(buffer);
      }

      return this;
   }

   public fun sanitize(battle: PokemonBattle, battleActor: BattleActor) {
      val var6: java.util.Iterator = battle.getPlayers().iterator();

      var var10000: Any;
      while (true) {
         if (var6.hasNext()) {
            val `element$iv`: Any = var6.next();
            if (!((`element$iv` as ServerPlayer).m_20148_() == battleActor.getUuid())) {
               continue;
            }

            var10000 = `element$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      var10000 = var10000 as ServerPlayer;
      if (var10000 as ServerPlayer != null) {
         val player: ServerPlayer = (ServerPlayer)var10000;
         if (this.active != null) {
            val `$this$forEach$iv`: java.lang.Iterable;
            for (Object element$iv : $this$forEach$iv) {
               val var20: ShowdownMoveset = var19 as ShowdownMoveset;

               val `$this$forEach$ivx`: java.lang.Iterable;
               for (Object element$ivx : $this$forEach$ivx) {
                  val gimmick: ShowdownMoveset.Gimmick = `element$ivx` as ShowdownMoveset.Gimmick;
                  switch (ShowdownActionRequest.WhenMappings.$EnumSwitchMapping$0[((ShowdownMoveset.Gimmick)element$ivx).ordinal()]) {
                     case 1:
                        var10000 = MiscUtilsKt.cobblemonResource("key_stone");
                        break;
                     case 2:
                        var10000 = MiscUtilsKt.cobblemonResource("dynamax_band");
                        break;
                     case 3:
                        var10000 = MiscUtilsKt.cobblemonResource("tera_orb");
                        break;
                     default:
                        var10000 = MiscUtilsKt.cobblemonResource("z_ring");
                  }

                  if (!PlayerExtensionsKt.hasKeyItem(player, (ResourceLocation)var10000)) {
                     var20.blockGimmick(gimmick);
                  }
               }
            }
         }
      }
   }

   fun ShowdownActionRequest() {
      this(false, null, null, false, null, 31, null);
   }
}
