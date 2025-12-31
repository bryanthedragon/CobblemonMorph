/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scripting

import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.ScriptRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.contextOrEmpty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.endsWith
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import java.io.File
import java.util.concurrent.ExecutionException
public final class CobblemonScripts : DataRegistry {
    const val MOLANG_EXTENSION = ".molang"
    override val id = cobblemonResource("molang")
    override val type = PackType.SERVER_DATA
    override val observable = SimpleObservable<CobblemonScripts>()

    val clientScripts = mutableMapOf<ResourceLocation, ExpressionLike>()
    val scripts = mutableMapOf<ResourceLocation, ExpressionLike>()

    override fun reload(ResourceManager manager) {
        manager.listResources("molang") { path -> path.endsWith(MOLANG_EXTENSION) }.forEach { (identifier, resource) ->
            resource.open().use { stream ->
                stream.bufferedReader().use { reader ->
                    val resolvedIdentifier = ResourceLocation.fromNamespaceAndPath(identifier.namespace, File(identifier.path).nameWithoutExtension)
                    try {
                        val expression = reader.readText().asExpressionLike()
                        if (identifier.path.startsWith("molang/client/")) {
                            clientScripts[resolvedIdentifier] = expression
                        } else {
                            scripts[resolvedIdentifier] = expression
                        }
                    } catch (Exception exception) {
                        throw ExecutionException("Error loading MoLang script: $identifier", exception)
                    }
                }
            }
        }

        Cobblemon.LOGGER.info("Loaded ${scripts.size} server scripts and ${clientScripts.size} client scripts")
        observable.emit(this)
    }


    override fun sync(ServerPlayer player) {
        player.sendPacket(ScriptRegistrySyncPacket(clientScripts.entries))
    }

    @JvmStatic
    fun run(ResourceLocation identifier, MoLangRuntime runtime): MoValue? {
        return scripts[identifier]?.resolve(runtime, runtime.contextOrEmpty)
    }
}