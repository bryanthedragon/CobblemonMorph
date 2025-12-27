package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

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

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.NotNull;

public object CobblemonNetwork : NetworkManager {
   public fun ServerPlayer.sendPacket;(packet;: NetworkPacket;<*>) {
      this.sendPacket;ToPlayer(`$this$sendPacket;`, packet;);
   }

   public fun sendToServer(packet;: NetworkPacket;<*>) {
      this.sendPacket;ToServer(packet;);
   }

   public fun sendToAllPlayers(packet;: NetworkPacket;<*>) {
      val var10001: MinecraftServer = DistributionUtilsKt.server();
      val var2: java.util.List = var10001.m_6846_().m_11314_();
      this.sendPacket;ToPlayers(var2, packet;);
   }

   public fun sendPacket;ToPlayers(players: Iterable<ServerPlayer>, packet;: NetworkPacket;<*>) {
      for (Object element$iv : players) {
         INSTANCE.sendPacket;ToPlayer(`element$iv` as ServerPlayer, packet;);
      }
   }

   public override fun registerClientBound() {
      Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(FriendshipUpdatePacket;.Companion.getID(), FriendshipUpdatePacket;::class,
      CobblemonNetwork$registerClientBound$$inlined$createClientBound$1.INSTANCE,
      (
         new Function1<FriendlyByteBuf, FriendshipUpdatePacket;>(FriendshipUpdatePacket;.Companion) {
            {
               super(1, receiver, FriendshipUpdatePacket;.Companion::class.java, "decode", "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/FriendshipUpdatePacket;;", 0);
            }
            @NotNull
            public final FriendshipUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as FriendshipUpdatePacket;.Companion).decode(p0);
            }
         }
      ) as Function1, new PokemonUpdatePacketHandler;());
      Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(MoveSetUpdatePacket;.Companion.getID(), MoveSetUpdatePacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$2.INSTANCE,
      (
         new Function1<FriendlyByteBuf, MoveSetUpdatePacket;>(MoveSetUpdatePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  MoveSetUpdatePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/MoveSetUpdatePacket;;",
                  0
               );
            }

            @NotNull
            public final MoveSetUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as MoveSetUpdatePacket;.Companion).decode(p0);
            }
         }
      ) as Function1, new PokemonUpdatePacketHandler;());

   Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(NatureUpdatePacket;.Companion.getID(), NatureUpdatePacket;::class,
   CobblemonNetwork$registerClientBound$$inlined$createClientBound$3.INSTANCE,
   (
      new Function1<FriendlyByteBuf, NatureUpdatePacket;>(NatureUpdatePacket;.Companion) {
         {
            super(1, receiver, NatureUpdatePacket;.Companion::class.java, "decode", "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/NatureUpdatePacket;;", 0);
         }
         @NotNull
         public final NatureUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
            return (this.receiver as NatureUpdatePacket;.Companion).decode(p0);
         }
      }
   ) as Function1, new PokemonUpdatePacketHandler;());
   Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(ShinyUpdatePacket;.Companion.getID(), ShinyUpdatePacket;::class,
         CobblemonNetwork$registerClientBound$$inlined$createClientBound$4.INSTANCE,
         (
            new Function1<FriendlyByteBuf, ShinyUpdatePacket;>(ShinyUpdatePacket;.Companion) {
               {
                  super(1, receiver, ShinyUpdatePacket;.Companion::class.java, "decode", "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/ShinyUpdatePacket;;", 0);
               }

               @NotNull
               public final ShinyUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                  return (this.receiver as ShinyUpdatePacket;.Companion).decode(p0);
               }
            }
         ) as Function1, new PokemonUpdatePacketHandler;()
      );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            SpeciesUpdatePacket;.Companion.getID(),
            SpeciesUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$5.INSTANCE,
            (
               new Function1<FriendlyByteBuf, SpeciesUpdatePacket;>(SpeciesUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        SpeciesUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/SpeciesUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final SpeciesUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as SpeciesUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            NicknameUpdatePacket;.Companion.getID(),
            NicknameUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$6.INSTANCE,
            (
               new Function1<FriendlyByteBuf, NicknameUpdatePacket;>(NicknameUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        NicknameUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/NicknameUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final NicknameUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as NicknameUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            HealthUpdatePacket;.Companion.getID(),
            HealthUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$7.INSTANCE,
            (
               new Function1<FriendlyByteBuf, HealthUpdatePacket;>(HealthUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        HealthUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/HealthUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final HealthUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as HealthUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            ExperienceUpdatePacket;.Companion.getID(),
            ExperienceUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$8.INSTANCE,
            (
               new Function1<FriendlyByteBuf, ExperienceUpdatePacket;>(ExperienceUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        ExperienceUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/ExperienceUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final ExperienceUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as ExperienceUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            StatusUpdatePacket;.Companion.getID(),
            StatusUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$9.INSTANCE,
            (
               new Function1<FriendlyByteBuf, StatusUpdatePacket;>(StatusUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        StatusUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/StatusUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final StatusUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as StatusUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            CaughtBallUpdatePacket;.Companion.getID(),
            CaughtBallUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$10.INSTANCE,
            (
               new Function1<FriendlyByteBuf, CaughtBallUpdatePacket;>(CaughtBallUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        CaughtBallUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/CaughtBallUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final CaughtBallUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as CaughtBallUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            BenchedMovesUpdatePacket;.Companion.getID(),
            BenchedMovesUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$11.INSTANCE,
            (
               new Function1<FriendlyByteBuf, BenchedMovesUpdatePacket;>(BenchedMovesUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        BenchedMovesUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/BenchedMovesUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final BenchedMovesUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as BenchedMovesUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            GenderUpdatePacket;.Companion.getID(),
            GenderUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$12.INSTANCE,
            (
               new Function1<FriendlyByteBuf, GenderUpdatePacket;>(GenderUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        GenderUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/GenderUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final GenderUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as GenderUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            AspectsUpdatePacket;.Companion.getID(),
            AspectsUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$13.INSTANCE,
            (
               new Function1<FriendlyByteBuf, AspectsUpdatePacket;>(AspectsUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        AspectsUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/AspectsUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final AspectsUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as AspectsUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            AbilityUpdatePacket;.Companion.getID(),
            AbilityUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$14.INSTANCE,
            (
               new Function1<FriendlyByteBuf, AbilityUpdatePacket;>(AbilityUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        AbilityUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/AbilityUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final AbilityUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as AbilityUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            EVsUpdatePacket;.Companion.getID(),
            EVsUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$15.INSTANCE,
            (
               new Function1<FriendlyByteBuf, EVsUpdatePacket;>(EVsUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        EVsUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/EVsUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final EVsUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as EVsUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            IVsUpdatePacket;.Companion.getID(),
            IVsUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$16.INSTANCE,
            (
               new Function1<FriendlyByteBuf, IVsUpdatePacket;>(IVsUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        IVsUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/IVsUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final IVsUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as IVsUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            HeldItemUpdatePacket;.Companion.getID(),
            HeldItemUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$17.INSTANCE,
            (
               new Function1<FriendlyByteBuf, HeldItemUpdatePacket;>(HeldItemUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        HeldItemUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/HeldItemUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final HeldItemUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as HeldItemUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            PokemonStateUpdatePacket;.Companion.getID(),
            PokemonStateUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$18.INSTANCE,
            (
               new Function1<FriendlyByteBuf, PokemonStateUpdatePacket;>(PokemonStateUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        PokemonStateUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/PokemonStateUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final PokemonStateUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as PokemonStateUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            TetheringUpdatePacket;.Companion.getID(),
            TetheringUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$19.INSTANCE,
            (
               new Function1<FriendlyByteBuf, TetheringUpdatePacket;>(TetheringUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        TetheringUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/TetheringUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final TetheringUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as TetheringUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            TradeableUpdatePacket;.Companion.getID(),
            TradeableUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$20.INSTANCE,
            (
               new Function1<FriendlyByteBuf, TradeableUpdatePacket;>(TradeableUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        TradeableUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/TradeableUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final TradeableUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as TradeableUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            SpeciesFeatureUpdatePacket;.Companion.getID(),
            SpeciesFeatureUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$21.INSTANCE,
            (
               new Function1<FriendlyByteBuf, SpeciesFeatureUpdatePacket;>(SpeciesFeatureUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        SpeciesFeatureUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/SpeciesFeatureUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final SpeciesFeatureUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as SpeciesFeatureUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            OriginalTrainerUpdatePacket;.Companion.getID(),
            OriginalTrainerUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$22.INSTANCE,
            (
               new Function1<FriendlyByteBuf, OriginalTrainerUpdatePacket;>(OriginalTrainerUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        OriginalTrainerUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/OriginalTrainerUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final OriginalTrainerUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as OriginalTrainerUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            FormUpdatePacket;.Companion.getID(),
            FormUpdatePacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$23.INSTANCE,
            (
               new Function1<FriendlyByteBuf, FormUpdatePacket;>(FormUpdatePacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        FormUpdatePacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/FormUpdatePacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final FormUpdatePacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as FormUpdatePacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            AddEvolutionPacket;.Companion.getID(),
            AddEvolutionPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$24.INSTANCE,
            (
               new Function1<FriendlyByteBuf, AddEvolutionPacket;>(AddEvolutionPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        AddEvolutionPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/evolution/AddEvolutionPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final AddEvolutionPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as AddEvolutionPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            ClearEvolutionsPacket;.Companion.getID(),
            ClearEvolutionsPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$25.INSTANCE,
            (
               new Function1<FriendlyByteBuf, ClearEvolutionsPacket;>(ClearEvolutionsPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        ClearEvolutionsPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/evolution/ClearEvolutionsPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final ClearEvolutionsPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as ClearEvolutionsPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            RemoveEvolutionPacket;.Companion.getID(),
            RemoveEvolutionPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$26.INSTANCE,
            (
               new Function1<FriendlyByteBuf, RemoveEvolutionPacket;>(RemoveEvolutionPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        RemoveEvolutionPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/evolution/RemoveEvolutionPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final RemoveEvolutionPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as RemoveEvolutionPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new PokemonUpdatePacketHandler;()
         );
      var var31: ResourceLocation = InitializePartyPacket;.Companion.getID();
      var var131: Function1 = (
         new Function1<FriendlyByteBuf, InitializePartyPacket;>(InitializePartyPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  InitializePartyPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/storage/party/InitializePartyPacket;;",
                  0
               );
            }

            @NotNull
            public final InitializePartyPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as InitializePartyPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var var231: ClientNetworkPacketHandler; = InitializePartyHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, InitializePartyPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$27.INSTANCE, var131, var231);
      var31 = SetPartyPokemonPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, SetPartyPokemonPacket;>(SetPartyPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SetPartyPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/storage/party/SetPartyPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final SetPartyPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SetPartyPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = SetPartyPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, SetPartyPokemonPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$28.INSTANCE, var131, var231);
      var31 = MoveClientPartyPokemonPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, MoveClientPartyPokemonPacket;>(MoveClientPartyPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  MoveClientPartyPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/storage/party/MoveClientPartyPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final MoveClientPartyPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as MoveClientPartyPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = MoveClientPartyPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, MoveClientPartyPokemonPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$29.INSTANCE, var131, var231
         );
      var31 = SetPartyReferencePacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, SetPartyReferencePacket;>(SetPartyReferencePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SetPartyReferencePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/storage/party/SetPartyReferencePacket;;",
                  0
               );
            }

            @NotNull
            public final SetPartyReferencePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SetPartyReferencePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = SetPartyReferenceHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, SetPartyReferencePacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$30.INSTANCE, var131, var231);
      var31 = InitializePCPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, InitializePCPacket;>(InitializePCPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  InitializePCPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/storage/pc/InitializePCPacket;;",
                  0
               );
            }

            @NotNull
            public final InitializePCPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as InitializePCPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = InitializePCHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, InitializePCPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$31.INSTANCE, var131, var231);
      var31 = MoveClientPCPokemonPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, MoveClientPCPokemonPacket;>(MoveClientPCPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  MoveClientPCPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/storage/pc/MoveClientPCPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final MoveClientPCPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as MoveClientPCPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = MoveClientPCPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, MoveClientPCPokemonPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$32.INSTANCE, var131, var231
         );
      var31 = SetPCBoxPokemonPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, SetPCBoxPokemonPacket;>(SetPCBoxPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SetPCBoxPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/storage/pc/SetPCBoxPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final SetPCBoxPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SetPCBoxPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = SetPCBoxPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, SetPCBoxPokemonPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$33.INSTANCE, var131, var231);
      var31 = SetPCPokemonPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, SetPCPokemonPacket;>(SetPCPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SetPCPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/storage/pc/SetPCPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final SetPCPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SetPCPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = SetPCPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, SetPCPokemonPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$34.INSTANCE, var131, var231);
      var31 = OpenPCPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, OpenPCPacket;>(OpenPCPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  OpenPCPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/storage/pc/OpenPCPacket;;",
                  0
               );
            }

            @NotNull
            public final OpenPCPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as OpenPCPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = OpenPCHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, OpenPCPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$35.INSTANCE, var131, var231);
      var31 = ClosePCPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, ClosePCPacket;>(ClosePCPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  ClosePCPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/storage/pc/ClosePCPacket;;",
                  0
               );
            }

            @NotNull
            public final ClosePCPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as ClosePCPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = ClosePCHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, ClosePCPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$36.INSTANCE, var131, var231);
      var31 = SwapClientPokemonPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, SwapClientPokemonPacket;>(SwapClientPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SwapClientPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/storage/SwapClientPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final SwapClientPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SwapClientPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = SwapClientPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, SwapClientPokemonPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$37.INSTANCE, var131, var231);
      var31 = RemoveClientPokemonPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, RemoveClientPokemonPacket;>(RemoveClientPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  RemoveClientPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/storage/RemoveClientPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final RemoveClientPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as RemoveClientPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = RemoveClientPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, RemoveClientPokemonPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$38.INSTANCE, var131, var231
         );
      var31 = SummaryUIPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, SummaryUIPacket;>(SummaryUIPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SummaryUIPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/ui/SummaryUIPacket;;",
                  0
               );
            }

            @NotNull
            public final SummaryUIPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SummaryUIPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = SummaryUIPacketHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, SummaryUIPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$39.INSTANCE, var131, var231);
      var31 = InteractPokemonUIPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, InteractPokemonUIPacket;>(InteractPokemonUIPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  InteractPokemonUIPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/ui/InteractPokemonUIPacket;;",
                  0
               );
            }

            @NotNull
            public final InteractPokemonUIPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as InteractPokemonUIPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = InteractPokemonUIPacketHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, InteractPokemonUIPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$40.INSTANCE, var131, var231);
      var31 = PlayerInteractOptionsPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, PlayerInteractOptionsPacket;>(PlayerInteractOptionsPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  PlayerInteractOptionsPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/PlayerInteractOptionsPacket;;",
                  0
               );
            }

            @NotNull
            public final PlayerInteractOptionsPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as PlayerInteractOptionsPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = PlayerInteractOptionsHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, PlayerInteractOptionsPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$41.INSTANCE, var131, var231
         );
      var31 = OpenStarterUIPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, OpenStarterUIPacket;>(OpenStarterUIPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  OpenStarterUIPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/starter/OpenStarterUIPacket;;",
                  0
               );
            }

            @NotNull
            public final OpenStarterUIPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as OpenStarterUIPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = StarterUIPacketHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, OpenStarterUIPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$42.INSTANCE, var131, var231);
      var31 = SetClientPlayerDataPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, SetClientPlayerDataPacket;>(SetClientPlayerDataPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SetClientPlayerDataPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/starter/SetClientPlayerDataPacket;;",
                  0
               );
            }

            @NotNull
            public final SetClientPlayerDataPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SetClientPlayerDataPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = SetClientPlayerDataHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, SetClientPlayerDataPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$43.INSTANCE, var131, var231
         );
      var31 = BattleEndPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleEndPacket;>(BattleEndPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleEndPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleEndPacket;;",
                  0
               );
            }

            @NotNull
            public final BattleEndPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleEndPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleEndHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, BattleEndPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$44.INSTANCE, var131, var231);
      var31 = BattleInitializePacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleInitializePacket;>(BattleInitializePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleInitializePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket;;",
                  0
               );
            }

            @NotNull
            public final BattleInitializePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleInitializePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleInitializeHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, BattleInitializePacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$45.INSTANCE, var131, var231);
      var31 = BattleQueueRequestPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleQueueRequestPacket;>(BattleQueueRequestPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleQueueRequestPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleQueueRequestPacket;;",
                  0
               );
            }

            @NotNull
            public final BattleQueueRequestPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleQueueRequestPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleQueueRequestHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, BattleQueueRequestPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$46.INSTANCE, var131, var231);
      var31 = BattleFaintPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleFaintPacket;>(BattleFaintPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleFaintPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleFaintPacket;;",
                  0
               );
            }

            @NotNull
            public final BattleFaintPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleFaintPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleFaintHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, BattleFaintPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$47.INSTANCE, var131, var231);
      var31 = BattleMakeChoicePacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleMakeChoicePacket;>(BattleMakeChoicePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleMakeChoicePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleMakeChoicePacket;;",
                  0
               );
            }

            @NotNull
            public final BattleMakeChoicePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleMakeChoicePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleMakeChoiceHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, BattleMakeChoicePacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$48.INSTANCE, var131, var231);
      var31 = BattleHealthChangePacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleHealthChangePacket;>(BattleHealthChangePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleHealthChangePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleHealthChangePacket;;",
                  0
               );
            }

            @NotNull
            public final BattleHealthChangePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleHealthChangePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleHealthChangeHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, BattleHealthChangePacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$49.INSTANCE, var131, var231);
      var31 = BattleSetTeamPokemonPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleSetTeamPokemonPacket;>(BattleSetTeamPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleSetTeamPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleSetTeamPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final BattleSetTeamPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleSetTeamPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleSetTeamPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, BattleSetTeamPokemonPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$50.INSTANCE, var131, var231
         );
      var31 = BattleSwitchPokemonPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleSwitchPokemonPacket;>(BattleSwitchPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleSwitchPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleSwitchPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final BattleSwitchPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleSwitchPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleSwitchPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, BattleSwitchPokemonPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$51.INSTANCE, var131, var231
         );
      var31 = BattleMessagePacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleMessagePacket;>(BattleMessagePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleMessagePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleMessagePacket;;",
                  0
               );
            }

            @NotNull
            public final BattleMessagePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleMessagePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleMessageHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, BattleMessagePacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$52.INSTANCE, var131, var231);
      var31 = BattleCaptureStartPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleCaptureStartPacket;>(BattleCaptureStartPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleCaptureStartPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleCaptureStartPacket;;",
                  0
               );
            }

            @NotNull
            public final BattleCaptureStartPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleCaptureStartPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleCaptureStartHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, BattleCaptureStartPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$53.INSTANCE, var131, var231);
      var31 = BattleCaptureEndPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleCaptureEndPacket;>(BattleCaptureEndPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleCaptureEndPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleCaptureEndPacket;;",
                  0
               );
            }

            @NotNull
            public final BattleCaptureEndPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleCaptureEndPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleCaptureEndHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, BattleCaptureEndPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$54.INSTANCE, var131, var231);
      var31 = BattleCaptureShakePacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleCaptureShakePacket;>(BattleCaptureShakePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleCaptureShakePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleCaptureShakePacket;;",
                  0
               );
            }

            @NotNull
            public final BattleCaptureShakePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleCaptureShakePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleCaptureShakeHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, BattleCaptureShakePacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$55.INSTANCE, var131, var231);
      var31 = BattleApplyPassResponsePacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleApplyPassResponsePacket;>(BattleApplyPassResponsePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleApplyPassResponsePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleApplyPassResponsePacket;;",
                  0
               );
            }

            @NotNull
            public final BattleApplyPassResponsePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleApplyPassResponsePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleApplyPassResponseHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, BattleApplyPassResponsePacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$56.INSTANCE, var131, var231
         );
      var31 = BattleChallengeNotificationPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleChallengeNotificationPacket;>(BattleChallengeNotificationPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleChallengeNotificationPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleChallengeNotificationPacket;;",
                  0
               );
            }

            @NotNull
            public final BattleChallengeNotificationPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleChallengeNotificationPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleChallengeNotificationHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, BattleChallengeNotificationPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$57.INSTANCE, var131, var231
         );
      var31 = BattleUpdateTeamPokemonPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleUpdateTeamPokemonPacket;>(BattleUpdateTeamPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleUpdateTeamPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleUpdateTeamPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final BattleUpdateTeamPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleUpdateTeamPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleUpdateTeamPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, BattleUpdateTeamPokemonPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$58.INSTANCE, var131, var231
         );
      var31 = BattlePersistentStatusPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattlePersistentStatusPacket;>(BattlePersistentStatusPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattlePersistentStatusPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattlePersistentStatusPacket;;",
                  0
               );
            }

            @NotNull
            public final BattlePersistentStatusPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattlePersistentStatusPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattlePersistentStatusHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, BattlePersistentStatusPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$59.INSTANCE, var131, var231
         );
      var31 = BattleMadeInvalidChoicePacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleMadeInvalidChoicePacket;>(BattleMadeInvalidChoicePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleMadeInvalidChoicePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleMadeInvalidChoicePacket;;",
                  0
               );
            }

            @NotNull
            public final BattleMadeInvalidChoicePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleMadeInvalidChoicePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleMadeInvalidChoiceHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, BattleMadeInvalidChoicePacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$60.INSTANCE, var131, var231
         );
      var31 = BattleMusicPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleMusicPacket;>(BattleMusicPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleMusicPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleMusicPacket;;",
                  0
               );
            }

            @NotNull
            public final BattleMusicPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleMusicPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleMusicHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, BattleMusicPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$61.INSTANCE, var131, var231);
      var31 = BattleChallengeExpiredPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleChallengeExpiredPacket;>(BattleChallengeExpiredPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleChallengeExpiredPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleChallengeExpiredPacket;;",
                  0
               );
            }

            @NotNull
            public final BattleChallengeExpiredPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleChallengeExpiredPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleChallengeExpiredHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, BattleChallengeExpiredPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$62.INSTANCE, var131, var231
         );
      var31 = BattleReplacePokemonPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleReplacePokemonPacket;>(BattleReplacePokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleReplacePokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleReplacePokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final BattleReplacePokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleReplacePokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleReplacePokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, BattleReplacePokemonPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$63.INSTANCE, var131, var231
         );
      var31 = BattleTransformPokemonPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, BattleTransformPokemonPacket;>(BattleTransformPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleTransformPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleTransformPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final BattleTransformPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleTransformPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = BattleTransformPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, BattleTransformPokemonPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$64.INSTANCE, var131, var231
         );
      var31 = ServerSettingsPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, ServerSettingsPacket;>(ServerSettingsPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  ServerSettingsPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/settings/ServerSettingsPacket;;",
                  0
               );
            }

            @NotNull
            public final ServerSettingsPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as ServerSettingsPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = ServerSettingsPacketHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, ServerSettingsPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$65.INSTANCE, var131, var231);
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            AbilityRegistrySyncPacket;.Companion.getID(),
            AbilityRegistrySyncPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$66.INSTANCE,
            (
               new Function1<FriendlyByteBuf, AbilityRegistrySyncPacket;>(AbilityRegistrySyncPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        AbilityRegistrySyncPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/data/AbilityRegistrySyncPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final AbilityRegistrySyncPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as AbilityRegistrySyncPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new DataRegistrySyncPacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            MovesRegistrySyncPacket;.Companion.getID(),
            MovesRegistrySyncPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$67.INSTANCE,
            (
               new Function1<FriendlyByteBuf, MovesRegistrySyncPacket;>(MovesRegistrySyncPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        MovesRegistrySyncPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/data/MovesRegistrySyncPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final MovesRegistrySyncPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as MovesRegistrySyncPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new DataRegistrySyncPacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            SpeciesRegistrySyncPacket;.Companion.getID(),
            SpeciesRegistrySyncPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$68.INSTANCE,
            (
               new Function1<FriendlyByteBuf, SpeciesRegistrySyncPacket;>(SpeciesRegistrySyncPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        SpeciesRegistrySyncPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/data/SpeciesRegistrySyncPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final SpeciesRegistrySyncPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as SpeciesRegistrySyncPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new DataRegistrySyncPacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            PropertiesCompletionRegistrySyncPacket;.Companion.getID(),
            PropertiesCompletionRegistrySyncPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$69.INSTANCE,
            (
               new Function1<FriendlyByteBuf, PropertiesCompletionRegistrySyncPacket;>(PropertiesCompletionRegistrySyncPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        PropertiesCompletionRegistrySyncPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/data/PropertiesCompletionRegistrySyncPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final PropertiesCompletionRegistrySyncPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as PropertiesCompletionRegistrySyncPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new DataRegistrySyncPacketHandler;()
         );
      var31 = UnlockReloadPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, UnlockReloadPacket;>(UnlockReloadPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  UnlockReloadPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/data/UnlockReloadPacket;;",
                  0
               );
            }

            @NotNull
            public final UnlockReloadPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as UnlockReloadPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = UnlockReloadPacketHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, UnlockReloadPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$70.INSTANCE, var131, var231);
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            BerryRegistrySyncPacket;.Companion.getID(),
            BerryRegistrySyncPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$71.INSTANCE,
            (
               new Function1<FriendlyByteBuf, BerryRegistrySyncPacket;>(BerryRegistrySyncPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        BerryRegistrySyncPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/data/BerryRegistrySyncPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final BerryRegistrySyncPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as BerryRegistrySyncPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new DataRegistrySyncPacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            StandardSpeciesFeatureSyncPacket;.Companion.getID(),
            StandardSpeciesFeatureSyncPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$72.INSTANCE,
            (
               new Function1<FriendlyByteBuf, StandardSpeciesFeatureSyncPacket;>(StandardSpeciesFeatureSyncPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        StandardSpeciesFeatureSyncPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/data/StandardSpeciesFeatureSyncPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final StandardSpeciesFeatureSyncPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as StandardSpeciesFeatureSyncPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new DataRegistrySyncPacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            GlobalSpeciesFeatureSyncPacket;.Companion.getID(),
            GlobalSpeciesFeatureSyncPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$73.INSTANCE,
            (
               new Function1<FriendlyByteBuf, GlobalSpeciesFeatureSyncPacket;>(GlobalSpeciesFeatureSyncPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        GlobalSpeciesFeatureSyncPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/data/GlobalSpeciesFeatureSyncPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final GlobalSpeciesFeatureSyncPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as GlobalSpeciesFeatureSyncPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new DataRegistrySyncPacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            SpeciesFeatureAssignmentSyncPacket;.Companion.getID(),
            SpeciesFeatureAssignmentSyncPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$74.INSTANCE,
            (
               new Function1<FriendlyByteBuf, SpeciesFeatureAssignmentSyncPacket;>(SpeciesFeatureAssignmentSyncPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        SpeciesFeatureAssignmentSyncPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureAssignmentSyncPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final SpeciesFeatureAssignmentSyncPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as SpeciesFeatureAssignmentSyncPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new DataRegistrySyncPacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            NaturalMaterialRegistrySyncPacket;.Companion.getID(),
            NaturalMaterialRegistrySyncPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$75.INSTANCE,
            (
               new Function1<FriendlyByteBuf, NaturalMaterialRegistrySyncPacket;>(NaturalMaterialRegistrySyncPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        NaturalMaterialRegistrySyncPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/fossil/NaturalMaterialRegistrySyncPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final NaturalMaterialRegistrySyncPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as NaturalMaterialRegistrySyncPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new DataRegistrySyncPacketHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            FossilRegistrySyncPacket;.Companion.getID(),
            FossilRegistrySyncPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$76.INSTANCE,
            (
               new Function1<FriendlyByteBuf, FossilRegistrySyncPacket;>(FossilRegistrySyncPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        FossilRegistrySyncPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/fossil/FossilRegistrySyncPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final FossilRegistrySyncPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as FossilRegistrySyncPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new DataRegistrySyncPacketHandler;()
         );
      var31 = SpawnSnowstormParticlePacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, SpawnSnowstormParticlePacket;>(SpawnSnowstormParticlePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SpawnSnowstormParticlePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/effect/SpawnSnowstormParticlePacket;;",
                  0
               );
            }

            @NotNull
            public final SpawnSnowstormParticlePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SpawnSnowstormParticlePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = SpawnSnowstormParticleHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, SpawnSnowstormParticlePacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$77.INSTANCE, var131, var231
         );
      var31 = SpawnSnowstormEntityParticlePacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, SpawnSnowstormEntityParticlePacket;>(SpawnSnowstormEntityParticlePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SpawnSnowstormEntityParticlePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/effect/SpawnSnowstormEntityParticlePacket;;",
                  0
               );
            }

            @NotNull
            public final SpawnSnowstormEntityParticlePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SpawnSnowstormEntityParticlePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = SpawnSnowstormEntityParticleHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, SpawnSnowstormEntityParticlePacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$78.INSTANCE, var131, var231
         );
      var31 = RunPosableMoLangPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, RunPosableMoLangPacket;>(RunPosableMoLangPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  RunPosableMoLangPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/effect/RunPosableMoLangPacket;;",
                  0
               );
            }

            @NotNull
            public final RunPosableMoLangPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as RunPosableMoLangPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = RunPosableMoLangHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, RunPosableMoLangPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$79.INSTANCE, var131, var231);
      var31 = UnvalidatedPlaySoundS2CPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, UnvalidatedPlaySoundS2CPacket;>(UnvalidatedPlaySoundS2CPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  UnvalidatedPlaySoundS2CPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/sound/UnvalidatedPlaySoundS2CPacket;;",
                  0
               );
            }

            @NotNull
            public final UnvalidatedPlaySoundS2CPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as UnvalidatedPlaySoundS2CPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = UnvalidatedPlaySoundS2CPacketHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, UnvalidatedPlaySoundS2CPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$80.INSTANCE, var131, var231
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            SpawnPokemonPacket;.Companion.getID(),
            SpawnPokemonPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$81.INSTANCE,
            (
               new Function1<FriendlyByteBuf, SpawnPokemonPacket;>(SpawnPokemonPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        SpawnPokemonPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokemonPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final SpawnPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as SpawnPokemonPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new SpawnExtraDataEntityHandler;()
         );
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            SpawnPokeballPacket;.Companion.getID(),
            SpawnPokeballPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$82.INSTANCE,
            (
               new Function1<FriendlyByteBuf, SpawnPokeballPacket;>(SpawnPokeballPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        SpawnPokeballPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokeballPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final SpawnPokeballPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as SpawnPokeballPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new SpawnExtraDataEntityHandler;()
         );
      var31 = ToastPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, ToastPacket;>(ToastPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  ToastPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket;;",
                  0
               );
            }

            @NotNull
            public final ToastPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as ToastPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = ToastPacketHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, ToastPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$83.INSTANCE, var131, var231);
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            SpawnGenericBedrockPacket;.Companion.getID(),
            SpawnGenericBedrockPacket;::class,
            CobblemonNetwork$registerClientBound$$inlined$createClientBound$84.INSTANCE,
            (
               new Function1<FriendlyByteBuf, SpawnGenericBedrockPacket;>(SpawnGenericBedrockPacket;.Companion) {
                  {
                     super(
                        1,
                        receiver,
                        SpawnGenericBedrockPacket;.Companion::class.java,
                        "decode",
                        "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnGenericBedrockPacket;;",
                        0
                     );
                  }

                  @NotNull
                  public final SpawnGenericBedrockPacket; invoke(@NotNull FriendlyByteBuf p0) {
                     return (this.receiver as SpawnGenericBedrockPacket;.Companion).decode(p0);
                  }
               }
            ) as Function1,
            new SpawnExtraDataEntityHandler;()
         );
      var31 = TradeAcceptanceChangedPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, TradeAcceptanceChangedPacket;>(TradeAcceptanceChangedPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  TradeAcceptanceChangedPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/trade/TradeAcceptanceChangedPacket;;",
                  0
               );
            }

            @NotNull
            public final TradeAcceptanceChangedPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as TradeAcceptanceChangedPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = TradeAcceptanceChangedHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, TradeAcceptanceChangedPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$85.INSTANCE, var131, var231
         );
      var31 = TradeCancelledPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, TradeCancelledPacket;>(TradeCancelledPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  TradeCancelledPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/trade/TradeCancelledPacket;;",
                  0
               );
            }

            @NotNull
            public final TradeCancelledPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as TradeCancelledPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = TradeCancelledHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, TradeCancelledPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$86.INSTANCE, var131, var231);
      var31 = TradeCompletedPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, TradeCompletedPacket;>(TradeCompletedPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  TradeCompletedPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/trade/TradeCompletedPacket;;",
                  0
               );
            }

            @NotNull
            public final TradeCompletedPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as TradeCompletedPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = TradeCompletedHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, TradeCompletedPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$87.INSTANCE, var131, var231);
      var31 = TradeUpdatedPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, TradeUpdatedPacket;>(TradeUpdatedPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  TradeUpdatedPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/trade/TradeUpdatedPacket;;",
                  0
               );
            }

            @NotNull
            public final TradeUpdatedPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as TradeUpdatedPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = TradeUpdatedHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, TradeUpdatedPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$88.INSTANCE, var131, var231);
      var31 = TradeOfferNotificationPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, TradeOfferNotificationPacket;>(TradeOfferNotificationPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  TradeOfferNotificationPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/trade/TradeOfferNotificationPacket;;",
                  0
               );
            }

            @NotNull
            public final TradeOfferNotificationPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as TradeOfferNotificationPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = TradeOfferNotificationHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, TradeOfferNotificationPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$89.INSTANCE, var131, var231
         );
      var31 = TradeOfferExpiredPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, TradeOfferExpiredPacket;>(TradeOfferExpiredPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  TradeOfferExpiredPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/trade/TradeOfferExpiredPacket;;",
                  0
               );
            }

            @NotNull
            public final TradeOfferExpiredPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as TradeOfferExpiredPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = TradeOfferExpiredHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, TradeOfferExpiredPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$90.INSTANCE, var131, var231);
      var31 = TradeStartedPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, TradeStartedPacket;>(TradeStartedPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  TradeStartedPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket;;",
                  0
               );
            }

            @NotNull
            public final TradeStartedPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as TradeStartedPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = TradeStartedHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, TradeStartedPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$91.INSTANCE, var131, var231);
      var31 = OpenPasturePacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, OpenPasturePacket;>(OpenPasturePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  OpenPasturePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket;;",
                  0
               );
            }

            @NotNull
            public final OpenPasturePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as OpenPasturePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = OpenPastureHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, OpenPasturePacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$92.INSTANCE, var131, var231);
      var31 = ClosePasturePacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, ClosePasturePacket;>(ClosePasturePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  ClosePasturePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pasture/ClosePasturePacket;;",
                  0
               );
            }

            @NotNull
            public final ClosePasturePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as ClosePasturePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = ClosePastureHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, ClosePasturePacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$93.INSTANCE, var131, var231);
      var31 = PokemonPasturedPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, PokemonPasturedPacket;>(PokemonPasturedPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  PokemonPasturedPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pasture/PokemonPasturedPacket;;",
                  0
               );
            }

            @NotNull
            public final PokemonPasturedPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as PokemonPasturedPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = PokemonPasturedHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, PokemonPasturedPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$94.INSTANCE, var131, var231);
      var31 = PokemonUnpasturedPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, PokemonUnpasturedPacket;>(PokemonUnpasturedPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  PokemonUnpasturedPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pasture/PokemonUnpasturedPacket;;",
                  0
               );
            }

            @NotNull
            public final PokemonUnpasturedPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as PokemonUnpasturedPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = PokemonUnpasturedHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, PokemonUnpasturedPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$95.INSTANCE, var131, var231);
      var31 = PlayPoseableAnimationPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, PlayPoseableAnimationPacket;>(PlayPoseableAnimationPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  PlayPoseableAnimationPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/animation/PlayPoseableAnimationPacket;;",
                  0
               );
            }

            @NotNull
            public final PlayPoseableAnimationPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as PlayPoseableAnimationPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = PlayPoseableAnimationHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, PlayPoseableAnimationPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$96.INSTANCE, var131, var231
         );
      var31 = OpenMoveCallbackPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, OpenMoveCallbackPacket;>(OpenMoveCallbackPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  OpenMoveCallbackPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/callback/OpenMoveCallbackPacket;;",
                  0
               );
            }

            @NotNull
            public final OpenMoveCallbackPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as OpenMoveCallbackPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = OpenMoveCallbackHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, OpenMoveCallbackPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$97.INSTANCE, var131, var231);
      var31 = OpenPartyCallbackPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, OpenPartyCallbackPacket;>(OpenPartyCallbackPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  OpenPartyCallbackPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/callback/OpenPartyCallbackPacket;;",
                  0
               );
            }

            @NotNull
            public final OpenPartyCallbackPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as OpenPartyCallbackPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = OpenPartyCallbackHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, OpenPartyCallbackPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$98.INSTANCE, var131, var231);
      var31 = OpenPartyMoveCallbackPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, OpenPartyMoveCallbackPacket;>(OpenPartyMoveCallbackPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  OpenPartyMoveCallbackPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/callback/OpenPartyMoveCallbackPacket;;",
                  0
               );
            }

            @NotNull
            public final OpenPartyMoveCallbackPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as OpenPartyMoveCallbackPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = OpenPartyMoveCallbackHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(
            var31, OpenPartyMoveCallbackPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$99.INSTANCE, var131, var231
         );
      var31 = DialogueClosedPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, DialogueClosedPacket;>(DialogueClosedPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  DialogueClosedPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/dialogue/DialogueClosedPacket;;",
                  0
               );
            }

            @NotNull
            public final DialogueClosedPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as DialogueClosedPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = DialogueClosedHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, DialogueClosedPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$100.INSTANCE, var131, var231);
      var31 = DialogueOpenedPacket;.Companion.getID();
      var131 = (
         new Function1<FriendlyByteBuf, DialogueOpenedPacket;>(DialogueOpenedPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  DialogueOpenedPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/dialogue/DialogueOpenedPacket;;",
                  0
               );
            }

            @NotNull
            public final DialogueOpenedPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as DialogueOpenedPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var231 = DialogueOpenedHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createClientBound(var31, DialogueOpenedPacket;::class, CobblemonNetwork$registerClientBound$$inlined$createClientBound$101.INSTANCE, var131, var231);
   }

   public override fun registerServerBound() {
      var `identifier$iv`: ResourceLocation = SetNicknamePacket;.Companion.getID();
      var `decoder$iv`: Function1 = (
         new Function1<FriendlyByteBuf, SetNicknamePacket;>(SetNicknamePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SetNicknamePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/pokemon/update/SetNicknamePacket;;",
                  0
               );
            }

            @NotNull
            public final SetNicknamePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SetNicknamePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      var `handler;$iv`: ServerNetworkPacketHandler; = SetNicknameHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`, SetNicknamePacket;::class, CobblemonNetwork$registerServerBound$$inlined$createServerBound$1.INSTANCE, `decoder$iv`, `handler;$iv`
         );
      `identifier$iv` = AcceptEvolutionPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, AcceptEvolutionPacket;>(AcceptEvolutionPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  AcceptEvolutionPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/pokemon/update/evolution/AcceptEvolutionPacket;;",
                  0
               );
            }

            @NotNull
            public final AcceptEvolutionPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as AcceptEvolutionPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = AcceptEvolutionHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            AcceptEvolutionPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$2.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = InteractPokemonPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, InteractPokemonPacket;>(InteractPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  InteractPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/pokemon/interact/InteractPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final InteractPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as InteractPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = InteractPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            InteractPokemonPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$3.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = RequestPlayerInteractionsPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, RequestPlayerInteractionsPacket;>(RequestPlayerInteractionsPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  RequestPlayerInteractionsPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/RequestPlayerInteractionsPacket;;",
                  0
               );
            }

            @NotNull
            public final RequestPlayerInteractionsPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as RequestPlayerInteractionsPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = RequestInteractionsHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            RequestPlayerInteractionsPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$4.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = SendOutPokemonPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, SendOutPokemonPacket;>(SendOutPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SendOutPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/SendOutPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final SendOutPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SendOutPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = SendOutPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            SendOutPokemonPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$5.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = RequestMoveSwapPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, RequestMoveSwapPacket;>(RequestMoveSwapPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  RequestMoveSwapPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/RequestMoveSwapPacket;;",
                  0
               );
            }

            @NotNull
            public final RequestMoveSwapPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as RequestMoveSwapPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = RequestMoveSwapHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            RequestMoveSwapPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$6.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = BenchMovePacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, BenchMovePacket;>(BenchMovePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BenchMovePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/BenchMovePacket;;",
                  0
               );
            }

            @NotNull
            public final BenchMovePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BenchMovePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = BenchMoveHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`, BenchMovePacket;::class, CobblemonNetwork$registerServerBound$$inlined$createServerBound$7.INSTANCE, `decoder$iv`, `handler;$iv`
         );
      `identifier$iv` = BattleChallengePacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, BattleChallengePacket;>(BattleChallengePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleChallengePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/BattleChallengePacket;;",
                  0
               );
            }

            @NotNull
            public final BattleChallengePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleChallengePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = ChallengeHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            BattleChallengePacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$8.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = MovePCPokemonToPartyPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, MovePCPokemonToPartyPacket;>(MovePCPokemonToPartyPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  MovePCPokemonToPartyPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/storage/pc/MovePCPokemonToPartyPacket;;",
                  0
               );
            }

            @NotNull
            public final MovePCPokemonToPartyPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as MovePCPokemonToPartyPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = MovePCPokemonToPartyHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            MovePCPokemonToPartyPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$9.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = MovePartyPokemonToPCPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, MovePartyPokemonToPCPacket;>(MovePartyPokemonToPCPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  MovePartyPokemonToPCPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/storage/pc/MovePartyPokemonToPCPacket;;",
                  0
               );
            }

            @NotNull
            public final MovePartyPokemonToPCPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as MovePartyPokemonToPCPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = MovePartyPokemonToPCHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            MovePartyPokemonToPCPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$10.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = ReleasePartyPokemonPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, ReleasePartyPokemonPacket;>(ReleasePartyPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  ReleasePartyPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/storage/party/ReleasePartyPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final ReleasePartyPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as ReleasePartyPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = ReleasePartyPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            ReleasePartyPokemonPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$11.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = ReleasePCPokemonPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, ReleasePCPokemonPacket;>(ReleasePCPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  ReleasePCPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/storage/pc/ReleasePCPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final ReleasePCPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as ReleasePCPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = ReleasePCPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            ReleasePCPokemonPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$12.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = UnlinkPlayerFromPCPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, UnlinkPlayerFromPCPacket;>(UnlinkPlayerFromPCPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  UnlinkPlayerFromPCPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/storage/pc/UnlinkPlayerFromPCPacket;;",
                  0
               );
            }

            @NotNull
            public final UnlinkPlayerFromPCPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as UnlinkPlayerFromPCPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = UnlinkPlayerFromPCHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            UnlinkPlayerFromPCPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$13.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = SelectStarterPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, SelectStarterPacket;>(SelectStarterPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SelectStarterPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/SelectStarterPacket;;",
                  0
               );
            }

            @NotNull
            public final SelectStarterPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SelectStarterPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = SelectStarterPacketHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            SelectStarterPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$14.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = RequestStarterScreenPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, RequestStarterScreenPacket;>(RequestStarterScreenPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  RequestStarterScreenPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/starter/RequestStarterScreenPacket;;",
                  0
               );
            }

            @NotNull
            public final RequestStarterScreenPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as RequestStarterScreenPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = RequestStarterScreenHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            RequestStarterScreenPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$15.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = SwapPCPokemonPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, SwapPCPokemonPacket;>(SwapPCPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SwapPCPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/storage/pc/SwapPCPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final SwapPCPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SwapPCPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = SwapPCPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            SwapPCPokemonPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$16.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = SwapPartyPokemonPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, SwapPartyPokemonPacket;>(SwapPartyPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SwapPartyPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/storage/party/SwapPartyPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final SwapPartyPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SwapPartyPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = SwapPartyPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            SwapPartyPokemonPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$17.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = MovePCPokemonPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, MovePCPokemonPacket;>(MovePCPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  MovePCPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/storage/pc/MovePCPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final MovePCPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as MovePCPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = MovePCPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            MovePCPokemonPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$18.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = MovePartyPokemonPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, MovePartyPokemonPacket;>(MovePartyPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  MovePartyPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/storage/party/MovePartyPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final MovePartyPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as MovePartyPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = MovePartyPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            MovePartyPokemonPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$19.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = SwapPCPartyPokemonPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, SwapPCPartyPokemonPacket;>(SwapPCPartyPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SwapPCPartyPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/storage/SwapPCPartyPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final SwapPCPartyPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SwapPCPartyPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = SwapPCPartyPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            SwapPCPartyPokemonPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$20.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = BattleSelectActionsPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, BattleSelectActionsPacket;>(BattleSelectActionsPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  BattleSelectActionsPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/battle/BattleSelectActionsPacket;;",
                  0
               );
            }

            @NotNull
            public final BattleSelectActionsPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as BattleSelectActionsPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = BattleSelectActionsHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            BattleSelectActionsPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$21.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = SpectateBattlePacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, SpectateBattlePacket;>(SpectateBattlePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  SpectateBattlePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/battle/SpectateBattlePacket;;",
                  0
               );
            }

            @NotNull
            public final SpectateBattlePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as SpectateBattlePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = SpectateBattleHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            SpectateBattlePacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$22.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = RemoveSpectatorPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, RemoveSpectatorPacket;>(RemoveSpectatorPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  RemoveSpectatorPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/battle/RemoveSpectatorPacket;;",
                  0
               );
            }

            @NotNull
            public final RemoveSpectatorPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as RemoveSpectatorPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = RemoveSpectatorHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            RemoveSpectatorPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$23.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = AcceptTradeRequestPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, AcceptTradeRequestPacket;>(AcceptTradeRequestPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  AcceptTradeRequestPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/trade/AcceptTradeRequestPacket;;",
                  0
               );
            }

            @NotNull
            public final AcceptTradeRequestPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as AcceptTradeRequestPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = AcceptTradeRequestHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            AcceptTradeRequestPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$24.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = CancelTradePacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, CancelTradePacket;>(CancelTradePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  CancelTradePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/trade/CancelTradePacket;;",
                  0
               );
            }

            @NotNull
            public final CancelTradePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as CancelTradePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = CancelTradeHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`, CancelTradePacket;::class, CobblemonNetwork$registerServerBound$$inlined$createServerBound$25.INSTANCE, `decoder$iv`, `handler;$iv`
         );
      `identifier$iv` = ChangeTradeAcceptancePacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, ChangeTradeAcceptancePacket;>(ChangeTradeAcceptancePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  ChangeTradeAcceptancePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/trade/ChangeTradeAcceptancePacket;;",
                  0
               );
            }

            @NotNull
            public final ChangeTradeAcceptancePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as ChangeTradeAcceptancePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = ChangeTradeAcceptanceHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            ChangeTradeAcceptancePacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$26.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = OfferTradePacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, OfferTradePacket;>(OfferTradePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  OfferTradePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/trade/OfferTradePacket;;",
                  0
               );
            }

            @NotNull
            public final OfferTradePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as OfferTradePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = OfferTradeHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`, OfferTradePacket;::class, CobblemonNetwork$registerServerBound$$inlined$createServerBound$27.INSTANCE, `decoder$iv`, `handler;$iv`
         );
      `identifier$iv` = UpdateTradeOfferPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, UpdateTradeOfferPacket;>(UpdateTradeOfferPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  UpdateTradeOfferPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/trade/UpdateTradeOfferPacket;;",
                  0
               );
            }

            @NotNull
            public final UpdateTradeOfferPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as UpdateTradeOfferPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = UpdateTradeOfferHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            UpdateTradeOfferPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$28.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = PasturePokemonPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, PasturePokemonPacket;>(PasturePokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  PasturePokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/pasture/PasturePokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final PasturePokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as PasturePokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = PasturePokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            PasturePokemonPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$29.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = UnpasturePokemonPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, UnpasturePokemonPacket;>(UnpasturePokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  UnpasturePokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/pasture/UnpasturePokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final UnpasturePokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as UnpasturePokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = UnpasturePokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            UnpasturePokemonPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$30.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = UnpastureAllPokemonPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, UnpastureAllPokemonPacket;>(UnpastureAllPokemonPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  UnpastureAllPokemonPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/pasture/UnpastureAllPokemonPacket;;",
                  0
               );
            }

            @NotNull
            public final UnpastureAllPokemonPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as UnpastureAllPokemonPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = UnpastureAllPokemonHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            UnpastureAllPokemonPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$31.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = MoveSelectedPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, MoveSelectedPacket;>(MoveSelectedPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  MoveSelectedPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/callback/move/MoveSelectedPacket;;",
                  0
               );
            }

            @NotNull
            public final MoveSelectedPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as MoveSelectedPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = MoveSelectedHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`, MoveSelectedPacket;::class, CobblemonNetwork$registerServerBound$$inlined$createServerBound$32.INSTANCE, `decoder$iv`, `handler;$iv`
         );
      `identifier$iv` = MoveSelectCancelledPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, MoveSelectCancelledPacket;>(MoveSelectCancelledPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  MoveSelectCancelledPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/callback/move/MoveSelectCancelledPacket;;",
                  0
               );
            }

            @NotNull
            public final MoveSelectCancelledPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as MoveSelectCancelledPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = MoveSelectCancelledHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            MoveSelectCancelledPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$33.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = PartyPokemonSelectedPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, PartyPokemonSelectedPacket;>(PartyPokemonSelectedPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  PartyPokemonSelectedPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/callback/party/PartyPokemonSelectedPacket;;",
                  0
               );
            }

            @NotNull
            public final PartyPokemonSelectedPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as PartyPokemonSelectedPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = PartyPokemonSelectedHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            PartyPokemonSelectedPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$34.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = PartySelectCancelledPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, PartySelectCancelledPacket;>(PartySelectCancelledPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  PartySelectCancelledPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/callback/party/PartySelectCancelledPacket;;",
                  0
               );
            }

            @NotNull
            public final PartySelectCancelledPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as PartySelectCancelledPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = PartySelectCancelledHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            PartySelectCancelledPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$35.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = PartyPokemonMoveSelectedPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, PartyPokemonMoveSelectedPacket;>(PartyPokemonMoveSelectedPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  PartyPokemonMoveSelectedPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/callback/partymove/PartyPokemonMoveSelectedPacket;;",
                  0
               );
            }

            @NotNull
            public final PartyPokemonMoveSelectedPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as PartyPokemonMoveSelectedPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = PartyPokemonMoveSelectedHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            PartyPokemonMoveSelectedPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$36.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = PartyMoveSelectCancelledPacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, PartyMoveSelectCancelledPacket;>(PartyMoveSelectCancelledPacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  PartyMoveSelectCancelledPacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/callback/partymove/PartyMoveSelectCancelledPacket;;",
                  0
               );
            }

            @NotNull
            public final PartyMoveSelectCancelledPacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as PartyMoveSelectCancelledPacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = PartyMoveSelectCancelledHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            PartyMoveSelectCancelledPacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$37.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = EscapeDialoguePacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, EscapeDialoguePacket;>(EscapeDialoguePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  EscapeDialoguePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/dialogue/EscapeDialoguePacket;;",
                  0
               );
            }

            @NotNull
            public final EscapeDialoguePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as EscapeDialoguePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = EscapeDialogueHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            EscapeDialoguePacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$38.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
      `identifier$iv` = InputToDialoguePacket;.Companion.getID();
      `decoder$iv` = (
         new Function1<FriendlyByteBuf, InputToDialoguePacket;>(InputToDialoguePacket;.Companion) {
            {
               super(
                  1,
                  receiver,
                  InputToDialoguePacket;.Companion::class.java,
                  "decode",
                  "decode(Lnet/minecraft/network/Packet;ByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/dialogue/InputToDialoguePacket;;",
                  0
               );
            }

            @NotNull
            public final InputToDialoguePacket; invoke(@NotNull FriendlyByteBuf p0) {
               return (this.receiver as InputToDialoguePacket;.Companion).decode(p0);
            }
         }
      ) as Function1;
      `handler;$iv` = InputToDialogueHandler;.INSTANCE;
      Cobblemon.INSTANCE
         .getImplementation()
         .getNetworkManager()
         .createServerBound(
            `identifier$iv`,
            InputToDialoguePacket;::class,
            CobblemonNetwork$registerServerBound$$inlined$createServerBound$39.INSTANCE,
            `decoder$iv`,
            `handler;$iv`
         );
   }

   public override fun <T : NetworkPacket;<Any>> createClientBound(
      identifier: ResourceLocation,
      kClass: KClass<Any>,
      encoder: (Any, FriendlyByteBuf) -> Unit,
      decoder: (FriendlyByteBuf) -> Any,
      handler;: ClientNetworkPacketHandler;<Any>
   ) {
      Cobblemon.INSTANCE.getImplementation().getNetworkManager().createClientBound(identifier, kClass, encoder, decoder, handler;);
   }

   public override fun <T : NetworkPacket;<Any>> createServerBound(
      identifier: ResourceLocation,
      kClass: KClass<Any>,
      encoder: (Any, FriendlyByteBuf) -> Unit,
      decoder: (FriendlyByteBuf) -> Any,
      handler;: ServerNetworkPacketHandler;<Any>
   ) {
      Cobblemon.INSTANCE.getImplementation().getNetworkManager().createServerBound(identifier, kClass, encoder, decoder, handler;);
   }

   public override fun sendPacket;ToPlayer(player: ServerPlayer, packet;: NetworkPacket;<*>) {
      Cobblemon.INSTANCE.getImplementation().getNetworkManager().sendPacket;ToPlayer(player, packet;);
   }

   public override fun sendPacket;ToServer(packet;: NetworkPacket;<*>) {
      Cobblemon.INSTANCE.getImplementation().getNetworkManager().sendPacket;ToServer(packet;);
   }

   public override fun <T : NetworkPacket;<*>> asVanillaClientBound(packet;: Any): Packet;<ClientGamePacket;Listener> {
      return Cobblemon.INSTANCE.getImplementation().getNetworkManager().asVanillaClientBound(packet;);
   }
}
