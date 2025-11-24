/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.reflect.KClass
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork$registerClientBound$;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork$registerServerBound$;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.NetworkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.PlayerInteractOptionsHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.SetClientPlayerDataHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.animation.PlayPoseableAnimationHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleApplyPassResponseHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleCaptureEndHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleCaptureShakeHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleCaptureStartHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleChallengeExpiredHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleChallengeNotificationHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleEndHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleFaintHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleHealthChangeHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleInitializeHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleMadeInvalidChoiceHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleMakeChoiceHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleMessageHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleMusicHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattlePersistentStatusHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleQueueRequestHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleReplacePokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleSetTeamPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleSwitchPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleTransformPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle.BattleUpdateTeamPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.callback.move.OpenMoveCallbackHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.callback.party.OpenPartyCallbackHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.callback.partymove.OpenPartyMoveCallbackHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.data.DataRegistrySyncPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.data.UnlockReloadPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.dialogue.DialogueClosedHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.dialogue.DialogueOpenedHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.effect.RunPosableMoLangHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.effect.SpawnSnowstormEntityParticleHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.effect.SpawnSnowstormParticleHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.gui.InteractPokemonUIPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.gui.SummaryUIPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pasture.ClosePastureHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pasture.OpenPastureHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pasture.PokemonPasturedHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pasture.PokemonUnpasturedHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pokemon.update.PokemonUpdatePacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.settings.ServerSettingsPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.sound.UnvalidatedPlaySoundS2CPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.spawn.SpawnExtraDataEntityHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.starter.StarterUIPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.RemoveClientPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.SwapClientPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.party.InitializePartyHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.party.MoveClientPartyPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.party.SetPartyPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.party.SetPartyReferenceHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc.ClosePCHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc.InitializePCHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc.MoveClientPCPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc.OpenPCHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc.SetPCBoxPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc.SetPCPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.toast.ToastPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade.TradeAcceptanceChangedHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade.TradeCancelledHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade.TradeCompletedHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade.TradeOfferExpiredHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade.TradeOfferNotificationHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade.TradeStartedHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade.TradeUpdatedHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PlayerInteractOptionsPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.animation.PlayPoseableAnimationPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleApplyPassResponsePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureEndPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureShakePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureStartPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleChallengeExpiredPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleChallengeNotificationPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleEndPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleFaintPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleHealthChangePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMadeInvalidChoicePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMessagePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMusicPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattlePersistentStatusPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleQueueRequestPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleReplacePokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleSetTeamPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleSwitchPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleTransformPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenMoveCallbackPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenPartyCallbackPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenPartyMoveCallbackPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.AbilityRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.BerryRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.GlobalSpeciesFeatureSyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.MovesRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.PropertiesCompletionRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.SpeciesFeatureAssignmentSyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.SpeciesRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.StandardSpeciesFeatureSyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.UnlockReloadPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.DialogueClosedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.DialogueOpenedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.RunPosableMoLangPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.SpawnSnowstormParticlePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.fossil.FossilRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.fossil.NaturalMaterialRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.ClosePasturePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.PokemonPasturedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.PokemonUnpasturedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.AspectsUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.BenchedMovesUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.CaughtBallUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.EVsUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.ExperienceUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.FormUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.FriendshipUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.GenderUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.HealthUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.HeldItemUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.IVsUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.MoveSetUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.NatureUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.NicknameUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.OriginalTrainerUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.PokemonStateUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.ShinyUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.SpeciesFeatureUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.SpeciesUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.StatusUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.TetheringUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.TradeableUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution.AddEvolutionPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution.ClearEvolutionsPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution.RemoveEvolutionPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.settings.ServerSettingsPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.sound.UnvalidatedPlaySoundS2CPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn.SpawnGenericBedrockPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn.SpawnPokeballPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn.SpawnPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter.OpenStarterUIPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter.SetClientPlayerDataPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.RemoveClientPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.SwapClientPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.InitializePartyPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.MoveClientPartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.SetPartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.SetPartyReferencePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.InitializePCPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.MoveClientPCPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.OpenPCPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.SetPCBoxPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.SetPCPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.toast.ToastPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeAcceptanceChangedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeCancelledPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeCompletedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeOfferExpiredPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeOfferNotificationPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeUpdatedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui.InteractPokemonUIPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui.SummaryUIPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.BattleChallengePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.BenchMovePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.RequestMoveSwapPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.RequestPlayerInteractionsPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.SelectStarterPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.SendOutPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.BattleSelectActionsPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.RemoveSpectatorPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.SpectateBattlePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.move.MoveSelectCancelledPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.move.MoveSelectedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.party.PartyPokemonSelectedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.party.PartySelectCancelledPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.partymove.PartyMoveSelectCancelledPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.partymove.PartyPokemonMoveSelectedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.dialogue.EscapeDialoguePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.dialogue.InputToDialoguePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.PasturePokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.UnpastureAllPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.UnpasturePokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.interact.InteractPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.SetNicknamePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.evolution.AcceptEvolutionPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.starter.RequestStarterScreenPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.SwapPCPartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.party.MovePartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.party.ReleasePartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.party.SwapPartyPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.MovePCPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.MovePCPokemonToPartyPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.MovePartyPokemonToPCPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.ReleasePCPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.SwapPCPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.UnlinkPlayerFromPCPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.AcceptTradeRequestPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.CancelTradePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.ChangeTradeAcceptancePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.OfferTradePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.UpdateTradeOfferPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.ChallengeHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.RequestInteractionsHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle.BattleSelectActionsHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle.RemoveSpectatorHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.battle.SpectateBattleHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.callback.move.MoveSelectCancelledHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.callback.move.MoveSelectedHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.callback.party.PartyPokemonSelectedHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.callback.party.PartySelectCancelledHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.callback.partymove.PartyMoveSelectCancelledHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.callback.partymove.PartyPokemonMoveSelectedHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.dialogue.EscapeDialogueHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.dialogue.InputToDialogueHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.evolution.AcceptEvolutionHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pasture.PasturePokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pasture.UnpastureAllPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pasture.UnpasturePokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokemon.interact.InteractPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokemon.update.SetNicknameHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.starter.RequestStarterScreenHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.starter.SelectStarterPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.BenchMoveHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.RequestMoveSwapHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.SendOutPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.SwapPCPartyPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.party.MovePartyPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.party.ReleasePCPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.party.SwapPartyPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc.MovePCPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc.MovePCPokemonToPartyHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc.MovePartyPokemonToPCHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc.ReleasePartyPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc.SwapPCPokemonHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc.UnlinkPlayerFromPCHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.trade.AcceptTradeRequestHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.trade.CancelTradeHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.trade.ChangeTradeAcceptanceHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.trade.OfferTradeHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.trade.UpdateTradeOfferHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b,\u0010\u001dJ+\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\"\f\b\u0000\u0010\u0003*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0004\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJN\u0010\u0011\u001a\u00020\u0010\"\u0010\b\u0000\u0010\u0003\u0018\u0001*\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\n\u001a\u00020\t2\u0014\b\b\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00028\u00000\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eH\u0082\b\u00a2\u0006\u0004\b\u0011\u0010\u0012Jq\u0010\u0011\u001a\u00020\u0010\"\u000e\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\n\u001a\u00020\t2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00132\u0018\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00100\u00152\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00028\u00000\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0017JN\u0010\u0019\u001a\u00020\u0010\"\u0010\b\u0000\u0010\u0003\u0018\u0001*\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\n\u001a\u00020\t2\u0014\b\b\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00028\u00000\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0018H\u0082\b\u00a2\u0006\u0004\b\u0019\u0010\u001aJq\u0010\u0019\u001a\u00020\u0010\"\u000e\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\n\u001a\u00020\t2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00132\u0018\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00100\u00152\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00028\u00000\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0018H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001bJ\u000f\u0010\u001c\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001e\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001dJ#\u0010!\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\u001f2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0016\u00a2\u0006\u0004\b!\u0010\"J'\u0010%\u001a\u00020\u00102\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u001f0#2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002\u00a2\u0006\u0004\b%\u0010&J\u001b\u0010'\u001a\u00020\u00102\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0016\u00a2\u0006\u0004\b'\u0010(J\u0019\u0010)\u001a\u00020\u00102\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002\u00a2\u0006\u0004\b)\u0010(J\u0019\u0010*\u001a\u00020\u00102\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002\u00a2\u0006\u0004\b*\u0010(J\u001d\u0010+\u001a\u00020\u0010*\u00020\u001f2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002\u00a2\u0006\u0004\b+\u0010\"\u00a8\u0006-"}, d2={"Lcom/cobblemon/mod/common/CobblemonNetwork;", "Lcom/cobblemon/mod/common/NetworkManager;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "T", "packet", "Lnet/minecraft/network/protocol/Packet;", "Lnet/minecraft/network/protocol/game/ClientGamePacketListener;", "asVanillaClientBound", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)Lnet/minecraft/network/protocol/Packet;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lkotlin/Function1;", "Lnet/minecraft/network/FriendlyByteBuf;", "decoder", "Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;", "handler", "", "createClientBound", "(Lnet/minecraft/resources/ResourceLocation;Lkotlin/jvm/functions/Function1;Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;)V", "Lkotlin/reflect/KClass;", "kClass", "Lkotlin/Function2;", "encoder", "(Lnet/minecraft/resources/ResourceLocation;Lkotlin/reflect/KClass;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;Lcom/cobblemon/mod/common/api/net/ClientNetworkPacketHandler;)V", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "createServerBound", "(Lnet/minecraft/resources/ResourceLocation;Lkotlin/jvm/functions/Function1;Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;)V", "(Lnet/minecraft/resources/ResourceLocation;Lkotlin/reflect/KClass;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;)V", "registerClientBound", "()V", "registerServerBound", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sendPacketToPlayer", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "", "players", "sendPacketToPlayers", "(Ljava/lang/Iterable;Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "sendPacketToServer", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "sendToAllPlayers", "sendToServer", "sendPacket", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonNetwork.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonNetwork.kt\ncom/cobblemon/mod/common/CobblemonNetwork\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,458:1\n426#1,2:461\n426#1,2:463\n426#1,2:465\n426#1,2:467\n426#1,2:469\n426#1,2:471\n426#1,2:473\n426#1,2:475\n426#1,2:477\n426#1,2:479\n426#1,2:481\n426#1,2:483\n426#1,2:485\n426#1,2:487\n426#1,2:489\n426#1,2:491\n426#1,2:493\n426#1,2:495\n426#1,2:497\n426#1,2:499\n426#1,2:501\n426#1,2:503\n426#1,2:505\n426#1,2:507\n426#1,2:509\n426#1,2:511\n426#1,2:513\n426#1,2:515\n426#1,2:517\n426#1,2:519\n426#1,2:521\n426#1,2:523\n426#1,2:525\n426#1,2:527\n426#1,2:529\n426#1,2:531\n426#1,2:533\n426#1,2:535\n426#1,2:537\n426#1,2:539\n426#1,2:541\n426#1,2:543\n426#1,2:545\n426#1,2:547\n426#1,2:549\n426#1,2:551\n426#1,2:553\n426#1,2:555\n426#1,2:557\n426#1,2:559\n426#1,2:561\n426#1,2:563\n426#1,2:565\n426#1,2:567\n426#1,2:569\n426#1,2:571\n426#1,2:573\n426#1,2:575\n426#1,2:577\n426#1,2:579\n426#1,2:581\n426#1,2:583\n426#1,2:585\n426#1,2:587\n426#1,2:589\n426#1,2:591\n426#1,2:593\n426#1,2:595\n426#1,2:597\n426#1,2:599\n426#1,2:601\n426#1,2:603\n426#1,2:605\n426#1,2:607\n426#1,2:609\n426#1,2:611\n426#1,2:613\n426#1,2:615\n426#1,2:617\n426#1,2:619\n426#1,2:621\n426#1,2:623\n426#1,2:625\n426#1,2:627\n426#1,2:629\n426#1,2:631\n426#1,2:633\n426#1,2:635\n426#1,2:637\n426#1,2:639\n426#1,2:641\n426#1,2:643\n426#1,2:645\n426#1,2:647\n426#1,2:649\n426#1,2:651\n426#1,2:653\n426#1,2:655\n426#1,2:657\n426#1,2:659\n426#1,2:661\n430#1,2:663\n430#1,2:665\n430#1,2:667\n430#1,2:669\n430#1,2:671\n430#1,2:673\n430#1,2:675\n430#1,2:677\n430#1,2:679\n430#1,2:681\n430#1,2:683\n430#1,2:685\n430#1,2:687\n430#1,2:689\n430#1,2:691\n430#1,2:693\n430#1,2:695\n430#1,2:697\n430#1,2:699\n430#1,2:701\n430#1,2:703\n430#1,2:705\n430#1,2:707\n430#1,2:709\n430#1,2:711\n430#1,2:713\n430#1,2:715\n430#1,2:717\n430#1,2:719\n430#1,2:721\n430#1,2:723\n430#1,2:725\n430#1,2:727\n430#1,2:729\n430#1,2:731\n430#1,2:733\n430#1,2:735\n430#1,2:737\n430#1,2:739\n1855#2,2:459\n*S KotlinDebug\n*F\n+ 1 CobblemonNetwork.kt\ncom/cobblemon/mod/common/CobblemonNetwork\n*L\n216#1:461,2\n217#1:463,2\n218#1:465,2\n219#1:467,2\n220#1:469,2\n221#1:471,2\n222#1:473,2\n223#1:475,2\n224#1:477,2\n225#1:479,2\n226#1:481,2\n227#1:483,2\n228#1:485,2\n229#1:487,2\n230#1:489,2\n231#1:491,2\n232#1:493,2\n233#1:495,2\n234#1:497,2\n235#1:499,2\n236#1:501,2\n237#1:503,2\n238#1:505,2\n241#1:507,2\n242#1:509,2\n243#1:511,2\n247#1:513,2\n248#1:515,2\n249#1:517,2\n250#1:519,2\n252#1:521,2\n253#1:523,2\n254#1:525,2\n255#1:527,2\n256#1:529,2\n257#1:531,2\n259#1:533,2\n260#1:535,2\n263#1:537,2\n264#1:539,2\n265#1:541,2\n268#1:543,2\n269#1:545,2\n272#1:547,2\n273#1:549,2\n274#1:551,2\n275#1:553,2\n276#1:555,2\n277#1:557,2\n278#1:559,2\n279#1:561,2\n280#1:563,2\n281#1:565,2\n282#1:567,2\n283#1:569,2\n284#1:571,2\n285#1:573,2\n286#1:575,2\n287#1:577,2\n288#1:579,2\n289#1:581,2\n290#1:583,2\n291#1:585,2\n292#1:587,2\n297#1:589,2\n300#1:591,2\n301#1:593,2\n302#1:595,2\n303#1:597,2\n304#1:599,2\n305#1:601,2\n306#1:603,2\n307#1:605,2\n308#1:607,2\n309#1:609,2\n310#1:611,2\n313#1:613,2\n314#1:615,2\n315#1:617,2\n318#1:619,2\n319#1:621,2\n320#1:623,2\n321#1:625,2\n322#1:627,2\n325#1:629,2\n326#1:631,2\n327#1:633,2\n328#1:635,2\n329#1:637,2\n330#1:639,2\n331#1:641,2\n334#1:643,2\n335#1:645,2\n336#1:647,2\n337#1:649,2\n340#1:651,2\n343#1:653,2\n346#1:655,2\n349#1:657,2\n352#1:659,2\n353#1:661,2\n358#1:663,2\n361#1:665,2\n364#1:667,2\n365#1:669,2\n368#1:671,2\n369#1:673,2\n370#1:675,2\n371#1:677,2\n373#1:679,2\n374#1:681,2\n375#1:683,2\n376#1:685,2\n377#1:687,2\n380#1:689,2\n381#1:691,2\n383#1:693,2\n384#1:695,2\n386#1:697,2\n387#1:699,2\n389#1:701,2\n392#1:703,2\n393#1:705,2\n394#1:707,2\n397#1:709,2\n398#1:711,2\n399#1:713,2\n400#1:715,2\n401#1:717,2\n404#1:719,2\n405#1:721,2\n406#1:723,2\n409#1:725,2\n410#1:727,2\n413#1:729,2\n414#1:731,2\n417#1:733,2\n418#1:735,2\n421#1:737,2\n422#1:739,2\n212#1:459,2\n*E\n"})
public final class CobblemonNetwork
implements NetworkManager {
    @NotNull
    public static final CobblemonNetwork INSTANCE = new CobblemonNetwork();

    private CobblemonNetwork() {
    }

    public final void sendPacket(@NotNull ServerPlayer $this$sendPacket, @NotNull NetworkPacket<?> packet) {
        Intrinsics.checkNotNullParameter((Object)$this$sendPacket, (String)"<this>");
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        this.sendPacketToPlayer($this$sendPacket, packet);
    }

    public final void sendToServer(@NotNull NetworkPacket<?> packet) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        this.sendPacketToServer(packet);
    }

    public final void sendToAllPlayers(@NotNull NetworkPacket<?> packet) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        MinecraftServer minecraftServer = DistributionUtilsKt.server();
        Intrinsics.checkNotNull((Object)minecraftServer);
        List list = minecraftServer.m_6846_().m_11314_();
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"server()!!.playerManager.playerList");
        this.sendPacketToPlayers(list, packet);
    }

    public final void sendPacketToPlayers(@NotNull Iterable<? extends ServerPlayer> players2, @NotNull NetworkPacket<?> packet) {
        Intrinsics.checkNotNullParameter(players2, (String)"players");
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        Iterable<? extends ServerPlayer> $this$forEach$iv = players2;
        boolean $i$f$forEach = false;
        Iterator<? extends ServerPlayer> iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            ServerPlayer element$iv;
            ServerPlayer it = element$iv = iterator.next();
            boolean bl = false;
            INSTANCE.sendPacketToPlayer(it, packet);
        }
    }

    @Override
    public void registerClientBound() {
        Function1 decoder$iv;
        ResourceLocation identifier$iv;
        CobblemonNetwork cobblemonNetwork = this;
        ResourceLocation resourceLocation = FriendshipUpdatePacket.Companion.getID();
        Function1 function1 = (Function1)new Function1<FriendlyByteBuf, FriendshipUpdatePacket>((Object)FriendshipUpdatePacket.Companion){

            @NotNull
            public final FriendshipUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((FriendshipUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        ClientNetworkPacketHandler handler$iv = new PokemonUpdatePacketHandler();
        boolean $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(FriendshipUpdatePacket.class), registerClientBound$$inlined$createClientBound$1.INSTANCE, decoder$iv, handler$iv);
        CobblemonNetwork this_$iv = this;
        identifier$iv = MoveSetUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, MoveSetUpdatePacket>((Object)MoveSetUpdatePacket.Companion){

            @NotNull
            public final MoveSetUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((MoveSetUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(MoveSetUpdatePacket.class), registerClientBound$$inlined$createClientBound$2.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = NatureUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, NatureUpdatePacket>((Object)NatureUpdatePacket.Companion){

            @NotNull
            public final NatureUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((NatureUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(NatureUpdatePacket.class), registerClientBound$$inlined$createClientBound$3.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = ShinyUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, ShinyUpdatePacket>((Object)ShinyUpdatePacket.Companion){

            @NotNull
            public final ShinyUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((ShinyUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(ShinyUpdatePacket.class), registerClientBound$$inlined$createClientBound$4.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SpeciesUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SpeciesUpdatePacket>((Object)SpeciesUpdatePacket.Companion){

            @NotNull
            public final SpeciesUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SpeciesUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SpeciesUpdatePacket.class), registerClientBound$$inlined$createClientBound$5.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = NicknameUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, NicknameUpdatePacket>((Object)NicknameUpdatePacket.Companion){

            @NotNull
            public final NicknameUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((NicknameUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(NicknameUpdatePacket.class), registerClientBound$$inlined$createClientBound$6.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = HealthUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, HealthUpdatePacket>((Object)HealthUpdatePacket.Companion){

            @NotNull
            public final HealthUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((HealthUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(HealthUpdatePacket.class), registerClientBound$$inlined$createClientBound$7.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = ExperienceUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, ExperienceUpdatePacket>((Object)ExperienceUpdatePacket.Companion){

            @NotNull
            public final ExperienceUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((ExperienceUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(ExperienceUpdatePacket.class), registerClientBound$$inlined$createClientBound$8.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = StatusUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, StatusUpdatePacket>((Object)StatusUpdatePacket.Companion){

            @NotNull
            public final StatusUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((StatusUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(StatusUpdatePacket.class), registerClientBound$$inlined$createClientBound$9.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = CaughtBallUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, CaughtBallUpdatePacket>((Object)CaughtBallUpdatePacket.Companion){

            @NotNull
            public final CaughtBallUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((CaughtBallUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(CaughtBallUpdatePacket.class), registerClientBound$$inlined$createClientBound$10.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BenchedMovesUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BenchedMovesUpdatePacket>((Object)BenchedMovesUpdatePacket.Companion){

            @NotNull
            public final BenchedMovesUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BenchedMovesUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BenchedMovesUpdatePacket.class), registerClientBound$$inlined$createClientBound$11.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = GenderUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, GenderUpdatePacket>((Object)GenderUpdatePacket.Companion){

            @NotNull
            public final GenderUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((GenderUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(GenderUpdatePacket.class), registerClientBound$$inlined$createClientBound$12.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = AspectsUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, AspectsUpdatePacket>((Object)AspectsUpdatePacket.Companion){

            @NotNull
            public final AspectsUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((AspectsUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(AspectsUpdatePacket.class), registerClientBound$$inlined$createClientBound$13.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = AbilityUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, AbilityUpdatePacket>((Object)AbilityUpdatePacket.Companion){

            @NotNull
            public final AbilityUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((AbilityUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(AbilityUpdatePacket.class), registerClientBound$$inlined$createClientBound$14.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = EVsUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, EVsUpdatePacket>((Object)EVsUpdatePacket.Companion){

            @NotNull
            public final EVsUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((EVsUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(EVsUpdatePacket.class), registerClientBound$$inlined$createClientBound$15.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = IVsUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, IVsUpdatePacket>((Object)IVsUpdatePacket.Companion){

            @NotNull
            public final IVsUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((IVsUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(IVsUpdatePacket.class), registerClientBound$$inlined$createClientBound$16.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = HeldItemUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, HeldItemUpdatePacket>((Object)HeldItemUpdatePacket.Companion){

            @NotNull
            public final HeldItemUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((HeldItemUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(HeldItemUpdatePacket.class), registerClientBound$$inlined$createClientBound$17.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = PokemonStateUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, PokemonStateUpdatePacket>((Object)PokemonStateUpdatePacket.Companion){

            @NotNull
            public final PokemonStateUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((PokemonStateUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(PokemonStateUpdatePacket.class), registerClientBound$$inlined$createClientBound$18.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = TetheringUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, TetheringUpdatePacket>((Object)TetheringUpdatePacket.Companion){

            @NotNull
            public final TetheringUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((TetheringUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(TetheringUpdatePacket.class), registerClientBound$$inlined$createClientBound$19.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = TradeableUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, TradeableUpdatePacket>((Object)TradeableUpdatePacket.Companion){

            @NotNull
            public final TradeableUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((TradeableUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(TradeableUpdatePacket.class), registerClientBound$$inlined$createClientBound$20.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SpeciesFeatureUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SpeciesFeatureUpdatePacket>((Object)SpeciesFeatureUpdatePacket.Companion){

            @NotNull
            public final SpeciesFeatureUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SpeciesFeatureUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SpeciesFeatureUpdatePacket.class), registerClientBound$$inlined$createClientBound$21.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = OriginalTrainerUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, OriginalTrainerUpdatePacket>((Object)OriginalTrainerUpdatePacket.Companion){

            @NotNull
            public final OriginalTrainerUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((OriginalTrainerUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(OriginalTrainerUpdatePacket.class), registerClientBound$$inlined$createClientBound$22.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = FormUpdatePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, FormUpdatePacket>((Object)FormUpdatePacket.Companion){

            @NotNull
            public final FormUpdatePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((FormUpdatePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(FormUpdatePacket.class), registerClientBound$$inlined$createClientBound$23.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = AddEvolutionPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, AddEvolutionPacket>((Object)AddEvolutionPacket.Companion){

            @NotNull
            public final AddEvolutionPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((AddEvolutionPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(AddEvolutionPacket.class), registerClientBound$$inlined$createClientBound$24.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = ClearEvolutionsPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, ClearEvolutionsPacket>((Object)ClearEvolutionsPacket.Companion){

            @NotNull
            public final ClearEvolutionsPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((ClearEvolutionsPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(ClearEvolutionsPacket.class), registerClientBound$$inlined$createClientBound$25.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = RemoveEvolutionPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, RemoveEvolutionPacket>((Object)RemoveEvolutionPacket.Companion){

            @NotNull
            public final RemoveEvolutionPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((RemoveEvolutionPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new PokemonUpdatePacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(RemoveEvolutionPacket.class), registerClientBound$$inlined$createClientBound$26.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = InitializePartyPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, InitializePartyPacket>((Object)InitializePartyPacket.Companion){

            @NotNull
            public final InitializePartyPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((InitializePartyPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = InitializePartyHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(InitializePartyPacket.class), registerClientBound$$inlined$createClientBound$27.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SetPartyPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SetPartyPokemonPacket>((Object)SetPartyPokemonPacket.Companion){

            @NotNull
            public final SetPartyPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SetPartyPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SetPartyPokemonHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SetPartyPokemonPacket.class), registerClientBound$$inlined$createClientBound$28.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = MoveClientPartyPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, MoveClientPartyPokemonPacket>((Object)MoveClientPartyPokemonPacket.Companion){

            @NotNull
            public final MoveClientPartyPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((MoveClientPartyPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = MoveClientPartyPokemonHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(MoveClientPartyPokemonPacket.class), registerClientBound$$inlined$createClientBound$29.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SetPartyReferencePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SetPartyReferencePacket>((Object)SetPartyReferencePacket.Companion){

            @NotNull
            public final SetPartyReferencePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SetPartyReferencePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SetPartyReferenceHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SetPartyReferencePacket.class), registerClientBound$$inlined$createClientBound$30.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = InitializePCPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, InitializePCPacket>((Object)InitializePCPacket.Companion){

            @NotNull
            public final InitializePCPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((InitializePCPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = InitializePCHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(InitializePCPacket.class), registerClientBound$$inlined$createClientBound$31.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = MoveClientPCPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, MoveClientPCPokemonPacket>((Object)MoveClientPCPokemonPacket.Companion){

            @NotNull
            public final MoveClientPCPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((MoveClientPCPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = MoveClientPCPokemonHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(MoveClientPCPokemonPacket.class), registerClientBound$$inlined$createClientBound$32.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SetPCBoxPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SetPCBoxPokemonPacket>((Object)SetPCBoxPokemonPacket.Companion){

            @NotNull
            public final SetPCBoxPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SetPCBoxPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SetPCBoxPokemonHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SetPCBoxPokemonPacket.class), registerClientBound$$inlined$createClientBound$33.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SetPCPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SetPCPokemonPacket>((Object)SetPCPokemonPacket.Companion){

            @NotNull
            public final SetPCPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SetPCPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SetPCPokemonHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SetPCPokemonPacket.class), registerClientBound$$inlined$createClientBound$34.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = OpenPCPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, OpenPCPacket>((Object)OpenPCPacket.Companion){

            @NotNull
            public final OpenPCPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((OpenPCPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = OpenPCHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(OpenPCPacket.class), registerClientBound$$inlined$createClientBound$35.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = ClosePCPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, ClosePCPacket>((Object)ClosePCPacket.Companion){

            @NotNull
            public final ClosePCPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((ClosePCPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = ClosePCHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(ClosePCPacket.class), registerClientBound$$inlined$createClientBound$36.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SwapClientPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SwapClientPokemonPacket>((Object)SwapClientPokemonPacket.Companion){

            @NotNull
            public final SwapClientPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SwapClientPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SwapClientPokemonHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SwapClientPokemonPacket.class), registerClientBound$$inlined$createClientBound$37.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = RemoveClientPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, RemoveClientPokemonPacket>((Object)RemoveClientPokemonPacket.Companion){

            @NotNull
            public final RemoveClientPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((RemoveClientPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = RemoveClientPokemonHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(RemoveClientPokemonPacket.class), registerClientBound$$inlined$createClientBound$38.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SummaryUIPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SummaryUIPacket>((Object)SummaryUIPacket.Companion){

            @NotNull
            public final SummaryUIPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SummaryUIPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SummaryUIPacketHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SummaryUIPacket.class), registerClientBound$$inlined$createClientBound$39.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = InteractPokemonUIPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, InteractPokemonUIPacket>((Object)InteractPokemonUIPacket.Companion){

            @NotNull
            public final InteractPokemonUIPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((InteractPokemonUIPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = InteractPokemonUIPacketHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(InteractPokemonUIPacket.class), registerClientBound$$inlined$createClientBound$40.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = PlayerInteractOptionsPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, PlayerInteractOptionsPacket>((Object)PlayerInteractOptionsPacket.Companion){

            @NotNull
            public final PlayerInteractOptionsPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((PlayerInteractOptionsPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = PlayerInteractOptionsHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(PlayerInteractOptionsPacket.class), registerClientBound$$inlined$createClientBound$41.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = OpenStarterUIPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, OpenStarterUIPacket>((Object)OpenStarterUIPacket.Companion){

            @NotNull
            public final OpenStarterUIPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((OpenStarterUIPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = StarterUIPacketHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(OpenStarterUIPacket.class), registerClientBound$$inlined$createClientBound$42.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SetClientPlayerDataPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SetClientPlayerDataPacket>((Object)SetClientPlayerDataPacket.Companion){

            @NotNull
            public final SetClientPlayerDataPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SetClientPlayerDataPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SetClientPlayerDataHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SetClientPlayerDataPacket.class), registerClientBound$$inlined$createClientBound$43.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleEndPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleEndPacket>((Object)BattleEndPacket.Companion){

            @NotNull
            public final BattleEndPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleEndPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleEndHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleEndPacket.class), registerClientBound$$inlined$createClientBound$44.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleInitializePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleInitializePacket>((Object)BattleInitializePacket.Companion){

            @NotNull
            public final BattleInitializePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleInitializePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleInitializeHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleInitializePacket.class), registerClientBound$$inlined$createClientBound$45.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleQueueRequestPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleQueueRequestPacket>((Object)BattleQueueRequestPacket.Companion){

            @NotNull
            public final BattleQueueRequestPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleQueueRequestPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleQueueRequestHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleQueueRequestPacket.class), registerClientBound$$inlined$createClientBound$46.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleFaintPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleFaintPacket>((Object)BattleFaintPacket.Companion){

            @NotNull
            public final BattleFaintPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleFaintPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleFaintHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleFaintPacket.class), registerClientBound$$inlined$createClientBound$47.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleMakeChoicePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleMakeChoicePacket>((Object)BattleMakeChoicePacket.Companion){

            @NotNull
            public final BattleMakeChoicePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleMakeChoicePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleMakeChoiceHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleMakeChoicePacket.class), registerClientBound$$inlined$createClientBound$48.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleHealthChangePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleHealthChangePacket>((Object)BattleHealthChangePacket.Companion){

            @NotNull
            public final BattleHealthChangePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleHealthChangePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleHealthChangeHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleHealthChangePacket.class), registerClientBound$$inlined$createClientBound$49.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleSetTeamPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleSetTeamPokemonPacket>((Object)BattleSetTeamPokemonPacket.Companion){

            @NotNull
            public final BattleSetTeamPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleSetTeamPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleSetTeamPokemonHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleSetTeamPokemonPacket.class), registerClientBound$$inlined$createClientBound$50.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleSwitchPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleSwitchPokemonPacket>((Object)BattleSwitchPokemonPacket.Companion){

            @NotNull
            public final BattleSwitchPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleSwitchPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleSwitchPokemonHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleSwitchPokemonPacket.class), registerClientBound$$inlined$createClientBound$51.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleMessagePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleMessagePacket>((Object)BattleMessagePacket.Companion){

            @NotNull
            public final BattleMessagePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleMessagePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleMessageHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleMessagePacket.class), registerClientBound$$inlined$createClientBound$52.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleCaptureStartPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleCaptureStartPacket>((Object)BattleCaptureStartPacket.Companion){

            @NotNull
            public final BattleCaptureStartPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleCaptureStartPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleCaptureStartHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleCaptureStartPacket.class), registerClientBound$$inlined$createClientBound$53.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleCaptureEndPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleCaptureEndPacket>((Object)BattleCaptureEndPacket.Companion){

            @NotNull
            public final BattleCaptureEndPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleCaptureEndPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleCaptureEndHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleCaptureEndPacket.class), registerClientBound$$inlined$createClientBound$54.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleCaptureShakePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleCaptureShakePacket>((Object)BattleCaptureShakePacket.Companion){

            @NotNull
            public final BattleCaptureShakePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleCaptureShakePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleCaptureShakeHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleCaptureShakePacket.class), registerClientBound$$inlined$createClientBound$55.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleApplyPassResponsePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleApplyPassResponsePacket>((Object)BattleApplyPassResponsePacket.Companion){

            @NotNull
            public final BattleApplyPassResponsePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleApplyPassResponsePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleApplyPassResponseHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleApplyPassResponsePacket.class), registerClientBound$$inlined$createClientBound$56.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleChallengeNotificationPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleChallengeNotificationPacket>((Object)BattleChallengeNotificationPacket.Companion){

            @NotNull
            public final BattleChallengeNotificationPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleChallengeNotificationPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleChallengeNotificationHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleChallengeNotificationPacket.class), registerClientBound$$inlined$createClientBound$57.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleUpdateTeamPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleUpdateTeamPokemonPacket>((Object)BattleUpdateTeamPokemonPacket.Companion){

            @NotNull
            public final BattleUpdateTeamPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleUpdateTeamPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleUpdateTeamPokemonHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleUpdateTeamPokemonPacket.class), registerClientBound$$inlined$createClientBound$58.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattlePersistentStatusPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattlePersistentStatusPacket>((Object)BattlePersistentStatusPacket.Companion){

            @NotNull
            public final BattlePersistentStatusPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattlePersistentStatusPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattlePersistentStatusHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattlePersistentStatusPacket.class), registerClientBound$$inlined$createClientBound$59.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleMadeInvalidChoicePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleMadeInvalidChoicePacket>((Object)BattleMadeInvalidChoicePacket.Companion){

            @NotNull
            public final BattleMadeInvalidChoicePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleMadeInvalidChoicePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleMadeInvalidChoiceHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleMadeInvalidChoicePacket.class), registerClientBound$$inlined$createClientBound$60.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleMusicPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleMusicPacket>((Object)BattleMusicPacket.Companion){

            @NotNull
            public final BattleMusicPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleMusicPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleMusicHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleMusicPacket.class), registerClientBound$$inlined$createClientBound$61.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleChallengeExpiredPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleChallengeExpiredPacket>((Object)BattleChallengeExpiredPacket.Companion){

            @NotNull
            public final BattleChallengeExpiredPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleChallengeExpiredPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleChallengeExpiredHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleChallengeExpiredPacket.class), registerClientBound$$inlined$createClientBound$62.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleReplacePokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleReplacePokemonPacket>((Object)BattleReplacePokemonPacket.Companion){

            @NotNull
            public final BattleReplacePokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleReplacePokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleReplacePokemonHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleReplacePokemonPacket.class), registerClientBound$$inlined$createClientBound$63.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleTransformPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleTransformPokemonPacket>((Object)BattleTransformPokemonPacket.Companion){

            @NotNull
            public final BattleTransformPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleTransformPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleTransformPokemonHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleTransformPokemonPacket.class), registerClientBound$$inlined$createClientBound$64.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = ServerSettingsPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, ServerSettingsPacket>((Object)ServerSettingsPacket.Companion){

            @NotNull
            public final ServerSettingsPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((ServerSettingsPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = ServerSettingsPacketHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(ServerSettingsPacket.class), registerClientBound$$inlined$createClientBound$65.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = AbilityRegistrySyncPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, AbilityRegistrySyncPacket>((Object)AbilityRegistrySyncPacket.Companion){

            @NotNull
            public final AbilityRegistrySyncPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((AbilityRegistrySyncPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new DataRegistrySyncPacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(AbilityRegistrySyncPacket.class), registerClientBound$$inlined$createClientBound$66.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = MovesRegistrySyncPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, MovesRegistrySyncPacket>((Object)MovesRegistrySyncPacket.Companion){

            @NotNull
            public final MovesRegistrySyncPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((MovesRegistrySyncPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new DataRegistrySyncPacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(MovesRegistrySyncPacket.class), registerClientBound$$inlined$createClientBound$67.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SpeciesRegistrySyncPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SpeciesRegistrySyncPacket>((Object)SpeciesRegistrySyncPacket.Companion){

            @NotNull
            public final SpeciesRegistrySyncPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SpeciesRegistrySyncPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new DataRegistrySyncPacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SpeciesRegistrySyncPacket.class), registerClientBound$$inlined$createClientBound$68.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = PropertiesCompletionRegistrySyncPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, PropertiesCompletionRegistrySyncPacket>((Object)PropertiesCompletionRegistrySyncPacket.Companion){

            @NotNull
            public final PropertiesCompletionRegistrySyncPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((PropertiesCompletionRegistrySyncPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new DataRegistrySyncPacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(PropertiesCompletionRegistrySyncPacket.class), registerClientBound$$inlined$createClientBound$69.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = UnlockReloadPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, UnlockReloadPacket>((Object)UnlockReloadPacket.Companion){

            @NotNull
            public final UnlockReloadPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((UnlockReloadPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = UnlockReloadPacketHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(UnlockReloadPacket.class), registerClientBound$$inlined$createClientBound$70.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BerryRegistrySyncPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BerryRegistrySyncPacket>((Object)BerryRegistrySyncPacket.Companion){

            @NotNull
            public final BerryRegistrySyncPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BerryRegistrySyncPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new DataRegistrySyncPacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(BerryRegistrySyncPacket.class), registerClientBound$$inlined$createClientBound$71.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = StandardSpeciesFeatureSyncPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, StandardSpeciesFeatureSyncPacket>((Object)StandardSpeciesFeatureSyncPacket.Companion){

            @NotNull
            public final StandardSpeciesFeatureSyncPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((StandardSpeciesFeatureSyncPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new DataRegistrySyncPacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(StandardSpeciesFeatureSyncPacket.class), registerClientBound$$inlined$createClientBound$72.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = GlobalSpeciesFeatureSyncPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, GlobalSpeciesFeatureSyncPacket>((Object)GlobalSpeciesFeatureSyncPacket.Companion){

            @NotNull
            public final GlobalSpeciesFeatureSyncPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((GlobalSpeciesFeatureSyncPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new DataRegistrySyncPacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(GlobalSpeciesFeatureSyncPacket.class), registerClientBound$$inlined$createClientBound$73.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SpeciesFeatureAssignmentSyncPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SpeciesFeatureAssignmentSyncPacket>((Object)SpeciesFeatureAssignmentSyncPacket.Companion){

            @NotNull
            public final SpeciesFeatureAssignmentSyncPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SpeciesFeatureAssignmentSyncPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new DataRegistrySyncPacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SpeciesFeatureAssignmentSyncPacket.class), registerClientBound$$inlined$createClientBound$74.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = NaturalMaterialRegistrySyncPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, NaturalMaterialRegistrySyncPacket>((Object)NaturalMaterialRegistrySyncPacket.Companion){

            @NotNull
            public final NaturalMaterialRegistrySyncPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((NaturalMaterialRegistrySyncPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new DataRegistrySyncPacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(NaturalMaterialRegistrySyncPacket.class), registerClientBound$$inlined$createClientBound$75.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = FossilRegistrySyncPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, FossilRegistrySyncPacket>((Object)FossilRegistrySyncPacket.Companion){

            @NotNull
            public final FossilRegistrySyncPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((FossilRegistrySyncPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new DataRegistrySyncPacketHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(FossilRegistrySyncPacket.class), registerClientBound$$inlined$createClientBound$76.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SpawnSnowstormParticlePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SpawnSnowstormParticlePacket>((Object)SpawnSnowstormParticlePacket.Companion){

            @NotNull
            public final SpawnSnowstormParticlePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SpawnSnowstormParticlePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SpawnSnowstormParticleHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SpawnSnowstormParticlePacket.class), registerClientBound$$inlined$createClientBound$77.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SpawnSnowstormEntityParticlePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SpawnSnowstormEntityParticlePacket>((Object)SpawnSnowstormEntityParticlePacket.Companion){

            @NotNull
            public final SpawnSnowstormEntityParticlePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SpawnSnowstormEntityParticlePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SpawnSnowstormEntityParticleHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SpawnSnowstormEntityParticlePacket.class), registerClientBound$$inlined$createClientBound$78.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = RunPosableMoLangPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, RunPosableMoLangPacket>((Object)RunPosableMoLangPacket.Companion){

            @NotNull
            public final RunPosableMoLangPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((RunPosableMoLangPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = RunPosableMoLangHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(RunPosableMoLangPacket.class), registerClientBound$$inlined$createClientBound$79.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = UnvalidatedPlaySoundS2CPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, UnvalidatedPlaySoundS2CPacket>((Object)UnvalidatedPlaySoundS2CPacket.Companion){

            @NotNull
            public final UnvalidatedPlaySoundS2CPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((UnvalidatedPlaySoundS2CPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = UnvalidatedPlaySoundS2CPacketHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(UnvalidatedPlaySoundS2CPacket.class), registerClientBound$$inlined$createClientBound$80.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SpawnPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SpawnPokemonPacket>((Object)SpawnPokemonPacket.Companion){

            @NotNull
            public final SpawnPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SpawnPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new SpawnExtraDataEntityHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SpawnPokemonPacket.class), registerClientBound$$inlined$createClientBound$81.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SpawnPokeballPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SpawnPokeballPacket>((Object)SpawnPokeballPacket.Companion){

            @NotNull
            public final SpawnPokeballPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SpawnPokeballPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new SpawnExtraDataEntityHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SpawnPokeballPacket.class), registerClientBound$$inlined$createClientBound$82.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = ToastPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, ToastPacket>((Object)ToastPacket.Companion){

            @NotNull
            public final ToastPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((ToastPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = ToastPacketHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(ToastPacket.class), registerClientBound$$inlined$createClientBound$83.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SpawnGenericBedrockPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SpawnGenericBedrockPacket>((Object)SpawnGenericBedrockPacket.Companion){

            @NotNull
            public final SpawnGenericBedrockPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SpawnGenericBedrockPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = new SpawnExtraDataEntityHandler();
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(SpawnGenericBedrockPacket.class), registerClientBound$$inlined$createClientBound$84.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = TradeAcceptanceChangedPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, TradeAcceptanceChangedPacket>((Object)TradeAcceptanceChangedPacket.Companion){

            @NotNull
            public final TradeAcceptanceChangedPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((TradeAcceptanceChangedPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = TradeAcceptanceChangedHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(TradeAcceptanceChangedPacket.class), registerClientBound$$inlined$createClientBound$85.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = TradeCancelledPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, TradeCancelledPacket>((Object)TradeCancelledPacket.Companion){

            @NotNull
            public final TradeCancelledPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((TradeCancelledPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = TradeCancelledHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(TradeCancelledPacket.class), registerClientBound$$inlined$createClientBound$86.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = TradeCompletedPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, TradeCompletedPacket>((Object)TradeCompletedPacket.Companion){

            @NotNull
            public final TradeCompletedPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((TradeCompletedPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = TradeCompletedHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(TradeCompletedPacket.class), registerClientBound$$inlined$createClientBound$87.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = TradeUpdatedPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, TradeUpdatedPacket>((Object)TradeUpdatedPacket.Companion){

            @NotNull
            public final TradeUpdatedPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((TradeUpdatedPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = TradeUpdatedHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(TradeUpdatedPacket.class), registerClientBound$$inlined$createClientBound$88.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = TradeOfferNotificationPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, TradeOfferNotificationPacket>((Object)TradeOfferNotificationPacket.Companion){

            @NotNull
            public final TradeOfferNotificationPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((TradeOfferNotificationPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = TradeOfferNotificationHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(TradeOfferNotificationPacket.class), registerClientBound$$inlined$createClientBound$89.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = TradeOfferExpiredPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, TradeOfferExpiredPacket>((Object)TradeOfferExpiredPacket.Companion){

            @NotNull
            public final TradeOfferExpiredPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((TradeOfferExpiredPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = TradeOfferExpiredHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(TradeOfferExpiredPacket.class), registerClientBound$$inlined$createClientBound$90.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = TradeStartedPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, TradeStartedPacket>((Object)TradeStartedPacket.Companion){

            @NotNull
            public final TradeStartedPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((TradeStartedPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = TradeStartedHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(TradeStartedPacket.class), registerClientBound$$inlined$createClientBound$91.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = OpenPasturePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, OpenPasturePacket>((Object)OpenPasturePacket.Companion){

            @NotNull
            public final OpenPasturePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((OpenPasturePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = OpenPastureHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(OpenPasturePacket.class), registerClientBound$$inlined$createClientBound$92.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = ClosePasturePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, ClosePasturePacket>((Object)ClosePasturePacket.Companion){

            @NotNull
            public final ClosePasturePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((ClosePasturePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = ClosePastureHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(ClosePasturePacket.class), registerClientBound$$inlined$createClientBound$93.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = PokemonPasturedPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, PokemonPasturedPacket>((Object)PokemonPasturedPacket.Companion){

            @NotNull
            public final PokemonPasturedPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((PokemonPasturedPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = PokemonPasturedHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(PokemonPasturedPacket.class), registerClientBound$$inlined$createClientBound$94.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = PokemonUnpasturedPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, PokemonUnpasturedPacket>((Object)PokemonUnpasturedPacket.Companion){

            @NotNull
            public final PokemonUnpasturedPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((PokemonUnpasturedPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = PokemonUnpasturedHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(PokemonUnpasturedPacket.class), registerClientBound$$inlined$createClientBound$95.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = PlayPoseableAnimationPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, PlayPoseableAnimationPacket>((Object)PlayPoseableAnimationPacket.Companion){

            @NotNull
            public final PlayPoseableAnimationPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((PlayPoseableAnimationPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = PlayPoseableAnimationHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(PlayPoseableAnimationPacket.class), registerClientBound$$inlined$createClientBound$96.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = OpenMoveCallbackPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, OpenMoveCallbackPacket>((Object)OpenMoveCallbackPacket.Companion){

            @NotNull
            public final OpenMoveCallbackPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((OpenMoveCallbackPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = OpenMoveCallbackHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(OpenMoveCallbackPacket.class), registerClientBound$$inlined$createClientBound$97.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = OpenPartyCallbackPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, OpenPartyCallbackPacket>((Object)OpenPartyCallbackPacket.Companion){

            @NotNull
            public final OpenPartyCallbackPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((OpenPartyCallbackPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = OpenPartyCallbackHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(OpenPartyCallbackPacket.class), registerClientBound$$inlined$createClientBound$98.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = OpenPartyMoveCallbackPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, OpenPartyMoveCallbackPacket>((Object)OpenPartyMoveCallbackPacket.Companion){

            @NotNull
            public final OpenPartyMoveCallbackPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((OpenPartyMoveCallbackPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = OpenPartyMoveCallbackHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(OpenPartyMoveCallbackPacket.class), registerClientBound$$inlined$createClientBound$99.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = DialogueClosedPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, DialogueClosedPacket>((Object)DialogueClosedPacket.Companion){

            @NotNull
            public final DialogueClosedPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((DialogueClosedPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = DialogueClosedHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(DialogueClosedPacket.class), registerClientBound$$inlined$createClientBound$100.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = DialogueOpenedPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, DialogueOpenedPacket>((Object)DialogueOpenedPacket.Companion){

            @NotNull
            public final DialogueOpenedPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((DialogueOpenedPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = DialogueOpenedHandler.INSTANCE;
        $i$f$createClientBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier$iv, Reflection.getOrCreateKotlinClass(DialogueOpenedPacket.class), registerClientBound$$inlined$createClientBound$101.INSTANCE, decoder$iv, handler$iv);
    }

    @Override
    public void registerServerBound() {
        Function1 decoder$iv;
        ResourceLocation identifier$iv;
        CobblemonNetwork cobblemonNetwork = this;
        ResourceLocation resourceLocation = SetNicknamePacket.Companion.getID();
        Function1 function1 = (Function1)new Function1<FriendlyByteBuf, SetNicknamePacket>((Object)SetNicknamePacket.Companion){

            @NotNull
            public final SetNicknamePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SetNicknamePacket.Companion)this.receiver).decode(p0);
            }
        };
        ServerNetworkPacketHandler handler$iv = SetNicknameHandler.INSTANCE;
        boolean $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(SetNicknamePacket.class), registerServerBound$$inlined$createServerBound$1.INSTANCE, decoder$iv, handler$iv);
        CobblemonNetwork this_$iv = this;
        identifier$iv = AcceptEvolutionPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, AcceptEvolutionPacket>((Object)AcceptEvolutionPacket.Companion){

            @NotNull
            public final AcceptEvolutionPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((AcceptEvolutionPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = AcceptEvolutionHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(AcceptEvolutionPacket.class), registerServerBound$$inlined$createServerBound$2.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = InteractPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, InteractPokemonPacket>((Object)InteractPokemonPacket.Companion){

            @NotNull
            public final InteractPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((InteractPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = InteractPokemonHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(InteractPokemonPacket.class), registerServerBound$$inlined$createServerBound$3.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = RequestPlayerInteractionsPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, RequestPlayerInteractionsPacket>((Object)RequestPlayerInteractionsPacket.Companion){

            @NotNull
            public final RequestPlayerInteractionsPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((RequestPlayerInteractionsPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = RequestInteractionsHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(RequestPlayerInteractionsPacket.class), registerServerBound$$inlined$createServerBound$4.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SendOutPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SendOutPokemonPacket>((Object)SendOutPokemonPacket.Companion){

            @NotNull
            public final SendOutPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SendOutPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SendOutPokemonHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(SendOutPokemonPacket.class), registerServerBound$$inlined$createServerBound$5.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = RequestMoveSwapPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, RequestMoveSwapPacket>((Object)RequestMoveSwapPacket.Companion){

            @NotNull
            public final RequestMoveSwapPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((RequestMoveSwapPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = RequestMoveSwapHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(RequestMoveSwapPacket.class), registerServerBound$$inlined$createServerBound$6.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BenchMovePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BenchMovePacket>((Object)BenchMovePacket.Companion){

            @NotNull
            public final BenchMovePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BenchMovePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BenchMoveHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(BenchMovePacket.class), registerServerBound$$inlined$createServerBound$7.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleChallengePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleChallengePacket>((Object)BattleChallengePacket.Companion){

            @NotNull
            public final BattleChallengePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleChallengePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = ChallengeHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleChallengePacket.class), registerServerBound$$inlined$createServerBound$8.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = MovePCPokemonToPartyPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, MovePCPokemonToPartyPacket>((Object)MovePCPokemonToPartyPacket.Companion){

            @NotNull
            public final MovePCPokemonToPartyPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((MovePCPokemonToPartyPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = MovePCPokemonToPartyHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(MovePCPokemonToPartyPacket.class), registerServerBound$$inlined$createServerBound$9.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = MovePartyPokemonToPCPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, MovePartyPokemonToPCPacket>((Object)MovePartyPokemonToPCPacket.Companion){

            @NotNull
            public final MovePartyPokemonToPCPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((MovePartyPokemonToPCPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = MovePartyPokemonToPCHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(MovePartyPokemonToPCPacket.class), registerServerBound$$inlined$createServerBound$10.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = ReleasePartyPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, ReleasePartyPokemonPacket>((Object)ReleasePartyPokemonPacket.Companion){

            @NotNull
            public final ReleasePartyPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((ReleasePartyPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = ReleasePartyPokemonHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(ReleasePartyPokemonPacket.class), registerServerBound$$inlined$createServerBound$11.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = ReleasePCPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, ReleasePCPokemonPacket>((Object)ReleasePCPokemonPacket.Companion){

            @NotNull
            public final ReleasePCPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((ReleasePCPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = ReleasePCPokemonHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(ReleasePCPokemonPacket.class), registerServerBound$$inlined$createServerBound$12.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = UnlinkPlayerFromPCPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, UnlinkPlayerFromPCPacket>((Object)UnlinkPlayerFromPCPacket.Companion){

            @NotNull
            public final UnlinkPlayerFromPCPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((UnlinkPlayerFromPCPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = UnlinkPlayerFromPCHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(UnlinkPlayerFromPCPacket.class), registerServerBound$$inlined$createServerBound$13.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SelectStarterPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SelectStarterPacket>((Object)SelectStarterPacket.Companion){

            @NotNull
            public final SelectStarterPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SelectStarterPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SelectStarterPacketHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(SelectStarterPacket.class), registerServerBound$$inlined$createServerBound$14.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = RequestStarterScreenPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, RequestStarterScreenPacket>((Object)RequestStarterScreenPacket.Companion){

            @NotNull
            public final RequestStarterScreenPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((RequestStarterScreenPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = RequestStarterScreenHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(RequestStarterScreenPacket.class), registerServerBound$$inlined$createServerBound$15.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SwapPCPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SwapPCPokemonPacket>((Object)SwapPCPokemonPacket.Companion){

            @NotNull
            public final SwapPCPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SwapPCPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SwapPCPokemonHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(SwapPCPokemonPacket.class), registerServerBound$$inlined$createServerBound$16.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SwapPartyPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SwapPartyPokemonPacket>((Object)SwapPartyPokemonPacket.Companion){

            @NotNull
            public final SwapPartyPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SwapPartyPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SwapPartyPokemonHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(SwapPartyPokemonPacket.class), registerServerBound$$inlined$createServerBound$17.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = MovePCPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, MovePCPokemonPacket>((Object)MovePCPokemonPacket.Companion){

            @NotNull
            public final MovePCPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((MovePCPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = MovePCPokemonHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(MovePCPokemonPacket.class), registerServerBound$$inlined$createServerBound$18.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = MovePartyPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, MovePartyPokemonPacket>((Object)MovePartyPokemonPacket.Companion){

            @NotNull
            public final MovePartyPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((MovePartyPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = MovePartyPokemonHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(MovePartyPokemonPacket.class), registerServerBound$$inlined$createServerBound$19.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SwapPCPartyPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SwapPCPartyPokemonPacket>((Object)SwapPCPartyPokemonPacket.Companion){

            @NotNull
            public final SwapPCPartyPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SwapPCPartyPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SwapPCPartyPokemonHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(SwapPCPartyPokemonPacket.class), registerServerBound$$inlined$createServerBound$20.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = BattleSelectActionsPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, BattleSelectActionsPacket>((Object)BattleSelectActionsPacket.Companion){

            @NotNull
            public final BattleSelectActionsPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((BattleSelectActionsPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = BattleSelectActionsHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(BattleSelectActionsPacket.class), registerServerBound$$inlined$createServerBound$21.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = SpectateBattlePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, SpectateBattlePacket>((Object)SpectateBattlePacket.Companion){

            @NotNull
            public final SpectateBattlePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((SpectateBattlePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = SpectateBattleHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(SpectateBattlePacket.class), registerServerBound$$inlined$createServerBound$22.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = RemoveSpectatorPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, RemoveSpectatorPacket>((Object)RemoveSpectatorPacket.Companion){

            @NotNull
            public final RemoveSpectatorPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((RemoveSpectatorPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = RemoveSpectatorHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(RemoveSpectatorPacket.class), registerServerBound$$inlined$createServerBound$23.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = AcceptTradeRequestPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, AcceptTradeRequestPacket>((Object)AcceptTradeRequestPacket.Companion){

            @NotNull
            public final AcceptTradeRequestPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((AcceptTradeRequestPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = AcceptTradeRequestHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(AcceptTradeRequestPacket.class), registerServerBound$$inlined$createServerBound$24.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = CancelTradePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, CancelTradePacket>((Object)CancelTradePacket.Companion){

            @NotNull
            public final CancelTradePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((CancelTradePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = CancelTradeHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(CancelTradePacket.class), registerServerBound$$inlined$createServerBound$25.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = ChangeTradeAcceptancePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, ChangeTradeAcceptancePacket>((Object)ChangeTradeAcceptancePacket.Companion){

            @NotNull
            public final ChangeTradeAcceptancePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((ChangeTradeAcceptancePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = ChangeTradeAcceptanceHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(ChangeTradeAcceptancePacket.class), registerServerBound$$inlined$createServerBound$26.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = OfferTradePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, OfferTradePacket>((Object)OfferTradePacket.Companion){

            @NotNull
            public final OfferTradePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((OfferTradePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = OfferTradeHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(OfferTradePacket.class), registerServerBound$$inlined$createServerBound$27.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = UpdateTradeOfferPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, UpdateTradeOfferPacket>((Object)UpdateTradeOfferPacket.Companion){

            @NotNull
            public final UpdateTradeOfferPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((UpdateTradeOfferPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = UpdateTradeOfferHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(UpdateTradeOfferPacket.class), registerServerBound$$inlined$createServerBound$28.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = PasturePokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, PasturePokemonPacket>((Object)PasturePokemonPacket.Companion){

            @NotNull
            public final PasturePokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((PasturePokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = PasturePokemonHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(PasturePokemonPacket.class), registerServerBound$$inlined$createServerBound$29.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = UnpasturePokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, UnpasturePokemonPacket>((Object)UnpasturePokemonPacket.Companion){

            @NotNull
            public final UnpasturePokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((UnpasturePokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = UnpasturePokemonHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(UnpasturePokemonPacket.class), registerServerBound$$inlined$createServerBound$30.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = UnpastureAllPokemonPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, UnpastureAllPokemonPacket>((Object)UnpastureAllPokemonPacket.Companion){

            @NotNull
            public final UnpastureAllPokemonPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((UnpastureAllPokemonPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = UnpastureAllPokemonHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(UnpastureAllPokemonPacket.class), registerServerBound$$inlined$createServerBound$31.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = MoveSelectedPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, MoveSelectedPacket>((Object)MoveSelectedPacket.Companion){

            @NotNull
            public final MoveSelectedPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((MoveSelectedPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = MoveSelectedHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(MoveSelectedPacket.class), registerServerBound$$inlined$createServerBound$32.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = MoveSelectCancelledPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, MoveSelectCancelledPacket>((Object)MoveSelectCancelledPacket.Companion){

            @NotNull
            public final MoveSelectCancelledPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((MoveSelectCancelledPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = MoveSelectCancelledHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(MoveSelectCancelledPacket.class), registerServerBound$$inlined$createServerBound$33.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = PartyPokemonSelectedPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, PartyPokemonSelectedPacket>((Object)PartyPokemonSelectedPacket.Companion){

            @NotNull
            public final PartyPokemonSelectedPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((PartyPokemonSelectedPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = PartyPokemonSelectedHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(PartyPokemonSelectedPacket.class), registerServerBound$$inlined$createServerBound$34.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = PartySelectCancelledPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, PartySelectCancelledPacket>((Object)PartySelectCancelledPacket.Companion){

            @NotNull
            public final PartySelectCancelledPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((PartySelectCancelledPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = PartySelectCancelledHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(PartySelectCancelledPacket.class), registerServerBound$$inlined$createServerBound$35.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = PartyPokemonMoveSelectedPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, PartyPokemonMoveSelectedPacket>((Object)PartyPokemonMoveSelectedPacket.Companion){

            @NotNull
            public final PartyPokemonMoveSelectedPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((PartyPokemonMoveSelectedPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = PartyPokemonMoveSelectedHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(PartyPokemonMoveSelectedPacket.class), registerServerBound$$inlined$createServerBound$36.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = PartyMoveSelectCancelledPacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, PartyMoveSelectCancelledPacket>((Object)PartyMoveSelectCancelledPacket.Companion){

            @NotNull
            public final PartyMoveSelectCancelledPacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((PartyMoveSelectCancelledPacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = PartyMoveSelectCancelledHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(PartyMoveSelectCancelledPacket.class), registerServerBound$$inlined$createServerBound$37.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = EscapeDialoguePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, EscapeDialoguePacket>((Object)EscapeDialoguePacket.Companion){

            @NotNull
            public final EscapeDialoguePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((EscapeDialoguePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = EscapeDialogueHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(EscapeDialoguePacket.class), registerServerBound$$inlined$createServerBound$38.INSTANCE, decoder$iv, handler$iv);
        this_$iv = this;
        identifier$iv = InputToDialoguePacket.Companion.getID();
        decoder$iv = (Function1)new Function1<FriendlyByteBuf, InputToDialoguePacket>((Object)InputToDialoguePacket.Companion){

            @NotNull
            public final InputToDialoguePacket invoke(@NotNull FriendlyByteBuf p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return ((InputToDialoguePacket.Companion)this.receiver).decode(p0);
            }
        };
        handler$iv = InputToDialogueHandler.INSTANCE;
        $i$f$createServerBound = false;
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier$iv, Reflection.getOrCreateKotlinClass(InputToDialoguePacket.class), registerServerBound$$inlined$createServerBound$39.INSTANCE, decoder$iv, handler$iv);
    }

    private final /* synthetic */ <T extends NetworkPacket<T>> void createClientBound(ResourceLocation identifier, Function1<? super FriendlyByteBuf, ? extends T> decoder, ClientNetworkPacketHandler<T> handler) {
        boolean $i$f$createClientBound = false;
        NetworkManager networkManager = Cobblemon.INSTANCE.getImplementation().getNetworkManager();
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        KClass kClass = Reflection.getOrCreateKotlinClass(NetworkPacket.class);
        Intrinsics.needClassReification();
        networkManager.createClientBound(identifier, kClass, createClientBound.1.INSTANCE, decoder, handler);
    }

    private final /* synthetic */ <T extends NetworkPacket<T>> void createServerBound(ResourceLocation identifier, Function1<? super FriendlyByteBuf, ? extends T> decoder, ServerNetworkPacketHandler<T> handler) {
        boolean $i$f$createServerBound = false;
        NetworkManager networkManager = Cobblemon.INSTANCE.getImplementation().getNetworkManager();
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        KClass kClass = Reflection.getOrCreateKotlinClass(NetworkPacket.class);
        Intrinsics.needClassReification();
        networkManager.createServerBound(identifier, kClass, createServerBound.1.INSTANCE, decoder, handler);
    }

    @Override
    public <T extends NetworkPacket<T>> void createClientBound(@NotNull ResourceLocation identifier, @NotNull KClass<T> kClass, @NotNull Function2<? super T, ? super FriendlyByteBuf, Unit> encoder, @NotNull Function1<? super FriendlyByteBuf, ? extends T> decoder, @NotNull ClientNetworkPacketHandler<T> handler) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter(kClass, (String)"kClass");
        Intrinsics.checkNotNullParameter(encoder, (String)"encoder");
        Intrinsics.checkNotNullParameter(decoder, (String)"decoder");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier, kClass, encoder, decoder, handler);
    }

    @Override
    public <T extends NetworkPacket<T>> void createServerBound(@NotNull ResourceLocation identifier, @NotNull KClass<T> kClass, @NotNull Function2<? super T, ? super FriendlyByteBuf, Unit> encoder, @NotNull Function1<? super FriendlyByteBuf, ? extends T> decoder, @NotNull ServerNetworkPacketHandler<T> handler) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter(kClass, (String)"kClass");
        Intrinsics.checkNotNullParameter(encoder, (String)"encoder");
        Intrinsics.checkNotNullParameter(decoder, (String)"decoder");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier, kClass, encoder, decoder, handler);
    }

    @Override
    public void sendPacketToPlayer(@NotNull ServerPlayer player, @NotNull NetworkPacket<?> packet) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().sendPacketToPlayer(player, packet);
    }

    @Override
    public void sendPacketToServer(@NotNull NetworkPacket<?> packet) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().sendPacketToServer(packet);
    }

    @Override
    @NotNull
    public <T extends NetworkPacket<?>> Packet<ClientGamePacketListener> asVanillaClientBound(@NotNull T packet) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        return Cobblemon.INSTANCE.getImplementation().getNetworkManager().asVanillaClientBound(packet);
    }
}

