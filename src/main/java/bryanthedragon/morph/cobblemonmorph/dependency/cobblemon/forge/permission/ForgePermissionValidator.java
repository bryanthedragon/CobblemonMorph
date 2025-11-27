/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.server.permission.PermissionAPI
 *  net.minecraftforge.server.permission.events.PermissionGatherEvent$Nodes
 *  net.minecraftforge.server.permission.nodes.PermissionDynamicContext
 *  net.minecraftforge.server.permission.nodes.PermissionDynamicContextKey
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 *  net.minecraftforge.server.permission.nodes.PermissionTypes
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.permission;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.Permission;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.PermissionValidator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;
import net.minecraftforge.server.permission.nodes.PermissionDynamicContext;
import net.minecraftforge.server.permission.nodes.PermissionDynamicContextKey;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;

import org.jetbrains.annotations.NotNull;

public final class ForgePermissionValidator implements PermissionValidator {
    @NotNull
    public static final ForgePermissionValidator INSTANCE = new ForgePermissionValidator();
    @NotNull
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final HashMap<ResourceLocation, PermissionNode<Boolean>> nodes = new HashMap();

    private ForgePermissionValidator() {
    }

    @Override
    public void initialize() {
        Cobblemon.INSTANCE.getLOGGER().info("Booting ForgePermissionApiPermissionValidator, player permissions will be checked using MinecraftForge' PermissionAPI, non player command sources will use Minecraft' permission level system, see https://docs.minecraftforge.net/en/latest/ and https://minecraft.fandom.com/wiki/Permission_level");
    }

    @Override
    public boolean hasPermission(@NotNull ServerPlayer player, @NotNull Permission permission2) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)permission2, (String)"permission");
        PermissionNode<Boolean> permissionNode = this.findNode(permission2);
        if (permissionNode == null) {
            return player.m_20310_(permission2.getLevel().getNumericalValue());
        }
        PermissionNode<Boolean> node = permissionNode;
        Object object = PermissionAPI.getPermission((ServerPlayer)player, node, (PermissionDynamicContext[])new PermissionDynamicContext[0]);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"getPermission(player, node)");
        return (Boolean)object;
    }

    @Override
    public boolean hasPermission(@NotNull SharedSuggestionProvider source, @NotNull Permission permission2) {
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        Intrinsics.checkNotNullParameter((Object)permission2, (String)"permission");
        ServerPlayer serverPlayer = this.extractPlayerFromSource(source);
        if (serverPlayer == null) {
            return source.m_6761_(permission2.getLevel().getNumericalValue());
        }
        ServerPlayer player = serverPlayer;
        PermissionNode<Boolean> permissionNode = this.findNode(permission2);
        if (permissionNode == null) {
            return source.m_6761_(permission2.getLevel().getNumericalValue());
        }
        PermissionNode<Boolean> node = permissionNode;
        Object object = PermissionAPI.getPermission((ServerPlayer)player, node, (PermissionDynamicContext[])new PermissionDynamicContext[0]);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"getPermission(player, node)");
        return (Boolean)object;
    }

    /*
     * WARNING - void declaration
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private final List<PermissionNode<Boolean>> createNodes() {
        void $this$mapTo$iv$iv;
        Iterable<Permission> $this$map$iv = CobblemonPermissions.INSTANCE.all();
        boolean $i$f$map = false;
        Iterable<Permission> iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void permission2;
            Permission permission3 = (Permission)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            PermissionNode node = new PermissionNode(permission2.getIdentifier(), PermissionTypes.BOOLEAN, (arg_0, arg_1, arg_2) -> ForgePermissionValidator.createNodes$lambda$2$lambda$1((Permission)permission2, arg_0, arg_1, arg_2), new PermissionDynamicContextKey[0]);
            ((Map)nodes).put(permission2.getIdentifier(), node);
            Cobblemon.INSTANCE.getLOGGER().debug("Registered Forge permission node " + node.getNodeName());
            collection.add(node);
        }
        return (List)destination$iv$iv;
    }

    private final PermissionNode<Boolean> findNode(Permission permission2) {
        return nodes.get(permission2.getIdentifier());
    }

    private final ServerPlayer extractPlayerFromSource(SharedSuggestionProvider source) {
        return source instanceof CommandSourceStack ? ((CommandSourceStack)source).m_230896_() : null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final void _init_$lambda$0(PermissionGatherEvent.Nodes event) {
        Cobblemon.INSTANCE.getLOGGER().info("Starting Forge permission node registry");
        event.addNodes((Iterable)INSTANCE.createNodes());
        Cobblemon.INSTANCE.getLOGGER().debug("Finished Forge permission node registry");
    }

    @SuppressWarnings("rawtypes")
    private static final Boolean createNodes$lambda$2$lambda$1(Permission $permission, ServerPlayer player, UUID uUID, PermissionDynamicContext[] permissionDynamicContextArray) {
        Intrinsics.checkNotNullParameter((Object)$permission, (String)"$permission");
        ServerPlayer serverPlayer = player;
        return serverPlayer != null ? serverPlayer.m_20310_($permission.getLevel().getNumericalValue()) : false;
    }

    static {
        MinecraftForge.EVENT_BUS.addListener(ForgePermissionValidator::_init_$lambda$0);
    }
}

