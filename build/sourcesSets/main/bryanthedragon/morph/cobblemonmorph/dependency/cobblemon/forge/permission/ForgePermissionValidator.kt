package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.permission

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.Permission
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.PermissionValidator
import java.util.ArrayList;
import java.util.HashMap
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.server.permission.PermissionAPI
import net.minecraftforge.server.permission.events.PermissionGatherEvent.Nodes
import net.minecraftforge.server.permission.nodes.PermissionDynamicContext
import net.minecraftforge.server.permission.nodes.PermissionDynamicContextKey
import net.minecraftforge.server.permission.nodes.PermissionNode
import net.minecraftforge.server.permission.nodes.PermissionTypes

@SourceDebugExtension(["SMAP\nForgePermissionValidator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ForgePermissionValidator.kt\ncom/cobblemon/mod/forge/permission/ForgePermissionValidator\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,64:1\n1549#2:65\n1620#2,3:66\n*S KotlinDebug\n*F\n+ 1 ForgePermissionValidator.kt\ncom/cobblemon/mod/forge/permission/ForgePermissionValidator\n*L\n52#1:65\n52#1:66,3\n*E\n"])
public object ForgePermissionValidator : PermissionValidator {
   private final val nodes: HashMap<ResourceLocation, PermissionNode<Boolean>> = new HashMap()

   public override fun initialize() {
      Cobblemon.INSTANCE
         .getLOGGER()
         .info(
            "Booting ForgePermissionApiPermissionValidator, player permissions will be checked using MinecraftForge' PermissionAPI, non player command sources will use Minecraft' permission level system, see https://docs.minecraftforge.net/en/latest/ and https://minecraft.fandom.com/wiki/Permission_level"
         );
   }

   public override fun hasPermission(player: ServerPlayer, permission: Permission): Boolean {
      var var10000: PermissionNode = this.findNode(permission);
      if (var10000 == null) {
         return player.m_20310_(permission.getLevel().getNumericalValue());
      } else {
         var10000 = (PermissionNode)PermissionAPI.getPermission(player, var10000, new PermissionDynamicContext[0]);
         return var10000 as java.lang.Boolean;
      }
   }

   public override fun hasPermission(source: SharedSuggestionProvider, permission: Permission): Boolean {
      var var10000: ServerPlayer = this.extractPlayerFromSource(source);
      if (var10000 == null) {
         return source.m_6761_(permission.getLevel().getNumericalValue());
      } else {
         val var5: PermissionNode = this.findNode(permission);
         if (var5 == null) {
            return source.m_6761_(permission.getLevel().getNumericalValue());
         } else {
            var10000 = (ServerPlayer)PermissionAPI.getPermission(var10000, var5, new PermissionDynamicContext[0]);
            return var10000 as java.lang.Boolean;
         }
      }
   }

   private fun createNodes(): List<PermissionNode<Boolean>> {
      val `$this$map$iv`: java.lang.Iterable = CobblemonPermissions.INSTANCE.all();
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         val node: PermissionNode = new PermissionNode(
            (`item$iv$iv` as Permission).getIdentifier(),
            PermissionTypes.BOOLEAN,
            ForgePermissionValidator::createNodes$lambda$2$lambda$1,
            new PermissionDynamicContextKey[0]
         );
         nodes.put((`item$iv$iv` as Permission).getIdentifier(), node);
         Cobblemon.INSTANCE.getLOGGER().debug("Registered Forge permission node ${node.getNodeName()}");
         `destination$iv$iv`.add(node);
      }

      return `destination$iv$iv` as MutableList<PermissionNode<java.lang.Boolean>>;
   }

   private fun findNode(permission: Permission): PermissionNode<Boolean>? {
      return nodes.get(permission.getIdentifier());
   }

   private fun extractPlayerFromSource(source: SharedSuggestionProvider): ServerPlayer? {
      return if (source is CommandSourceStack) (source as CommandSourceStack).m_230896_() else null;
   }

   @JvmStatic
   fun `_init_$lambda$0`(event: Nodes) {
      Cobblemon.INSTANCE.getLOGGER().info("Starting Forge permission node registry");
      event.addNodes(INSTANCE.createNodes());
      Cobblemon.INSTANCE.getLOGGER().debug("Finished Forge permission node registry");
   }

   @JvmStatic
   fun `createNodes$lambda$2$lambda$1`(`$permission`: Permission, player: ServerPlayer, var2: UUID, var3: Array<PermissionDynamicContext>): java.lang.Boolean {
      return player != null && player.m_20310_(`$permission`.getLevel().getNumericalValue());
   }

   @JvmStatic
   fun {
      MinecraftForge.EVENT_BUS.addListener(ForgePermissionValidator::_init_$lambda$0);
   }
}
