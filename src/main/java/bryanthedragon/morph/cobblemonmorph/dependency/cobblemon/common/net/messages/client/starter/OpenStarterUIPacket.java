package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.RenderableStarterCategory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.StarterCategory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

@SourceDebugExtension(["SMAP\nOpenStarterUIPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 OpenStarterUIPacket.kt\ncom/cobblemon/mod/common/net/messages/client/starter/OpenStarterUIPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,58:1\n1549#2:59\n1620#2,3:60\n1855#2:63\n1855#2,2:64\n1856#2:66\n*S KotlinDebug\n*F\n+ 1 OpenStarterUIPacket.kt\ncom/cobblemon/mod/common/net/messages/client/starter/OpenStarterUIPacket\n*L\n22#1:59\n22#1:60,3\n26#1:63\n30#1:64,2\n26#1:66\n*E\n"])
public class OpenStarterUIPacket internal constructor(categories: List<RenderableStarterCategory>) : NetworkPacket<OpenStarterUIPacket> {
   public final val categories: List<RenderableStarterCategory>
   public open val id: ResourceLocation

   init {
      this.categories = categories;
      this.id = ID;
   }

   public constructor(categories: Collection<StarterCategory>)  {
      val `$this$map$iv`: java.lang.Iterable = categories;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(categories, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add((`item$iv$iv` as StarterCategory).asRenderableStarterCategory());
      }

      this(`destination$iv$iv` as MutableList<RenderableStarterCategory>);
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.writeInt(this.categories.size());

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val it: RenderableStarterCategory = `element$iv` as RenderableStarterCategory;
         buffer.m_130070_((`element$iv` as RenderableStarterCategory).getName());
         buffer.m_130070_(it.getDisplayName());
         buffer.writeInt(it.getPokemon().size());

         val `$this$forEach$ivx`: java.lang.Iterable;
         for (Object element$ivx : $this$forEach$ivx) {
            (`element$ivx` as RenderablePokemon).saveToBuffer(buffer);
         }
      }
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

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): OpenStarterUIPacket {
         val numCategories: Int = buffer.readInt();
         val categories: ArrayList = new ArrayList();

         for (int i = 0; i < numCategories; i++) {
            val name: java.lang.String = buffer.m_130277_();
            val displayName: java.lang.String = buffer.m_130277_();
            val numProperties: Int = buffer.readInt();
            val renderablePokemon: java.util.List = new ArrayList();

            for (int var9 = 0; var9 < numProperties; var9++) {
               renderablePokemon.add(RenderablePokemon.Companion.loadFromBuffer(buffer));
            }

            categories.add(new RenderableStarterCategory(name, displayName, renderablePokemon));
         }

         return new OpenStarterUIPacket(categories);
      }
   }
}
