/*
 * Open Parties and Claims - adds chunk claims and player parties to Minecraft
 * Copyright (C) 2022-2023, Xaero <xaero1996@gmail.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of version 3 of the GNU Lesser General Public License
 * (LGPL-3.0-only) as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received copies of the GNU Lesser General Public License
 * and the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package xaero.pac.client.event;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xaero.pac.client.IClientData;
import xaero.pac.client.claims.IClientClaimsManager;
import xaero.pac.client.claims.IClientDimensionClaimsManager;
import xaero.pac.client.claims.IClientRegionClaims;
import xaero.pac.client.claims.player.IClientPlayerClaimInfo;
import xaero.pac.client.event.api.OPACClientAddonRegisterEvent;
import xaero.pac.client.parties.party.IClientParty;
import xaero.pac.client.parties.party.IClientPartyAllyInfo;
import xaero.pac.client.parties.party.IClientPartyMemberDynamicInfoSyncableStorage;
import xaero.pac.client.parties.party.IClientPartyStorage;
import xaero.pac.client.player.config.IPlayerConfigClientStorage;
import xaero.pac.client.player.config.IPlayerConfigClientStorageManager;
import xaero.pac.client.player.config.IPlayerConfigStringableOptionClientStorage;
import xaero.pac.common.claims.player.IPlayerChunkClaim;
import xaero.pac.common.claims.player.IPlayerClaimPosList;
import xaero.pac.common.claims.player.IPlayerDimensionClaims;
import xaero.pac.common.parties.party.IPartyMemberDynamicInfoSyncable;
import xaero.pac.common.parties.party.IPartyPlayerInfo;
import xaero.pac.common.parties.party.ally.IPartyAlly;
import xaero.pac.common.parties.party.member.IPartyMember;

public final class ClientEventsForge extends ClientEvents {

	protected ClientEventsForge(IClientData<IPlayerConfigClientStorageManager<IPlayerConfigClientStorage<IPlayerConfigStringableOptionClientStorage<?>>>, IClientPartyStorage<IClientPartyAllyInfo, IClientParty<IPartyMember, IPartyPlayerInfo, IPartyAlly>, IClientPartyMemberDynamicInfoSyncableStorage<IPartyMemberDynamicInfoSyncable>>, IClientClaimsManager<IPlayerChunkClaim, IClientPlayerClaimInfo<IPlayerDimensionClaims<IPlayerClaimPosList>>, IClientDimensionClaimsManager<IClientRegionClaims>>> clientData) {
		super(clientData);
	}

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		super.onClientTick(event.phase == Phase.START);
	}

	@SubscribeEvent
	public void onWorldLoaded(LevelEvent.Load event) {
		if(event.getLevel() instanceof ClientLevel)
			super.onClientWorldLoaded((ClientLevel) event.getLevel());
	}

	@SubscribeEvent
	public void onPlayerLogout(ClientPlayerNetworkEvent.LoggingOut event){
		super.onPlayerLogout(event.getPlayer());
	}
	
	@SubscribeEvent
	public void onPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
		super.onPlayerLogin(event.getPlayer());
	}

	@Override
	public void fireAddonRegisterEvent() {
		MinecraftForge.EVENT_BUS.post(new OPACClientAddonRegisterEvent(clientData.getClaimsManager().getTracker(), clientData.getClaimsManager().getClaimResultTracker()));
	}

	public static final class Builder extends ClientEvents.Builder<Builder> {

		@Override
		public Builder setDefault() {
			super.setDefault();
			return this;
		}

		@Override
		protected ClientEvents buildInternally() {
			return new ClientEventsForge(clientData);
		}

		@Override
		public ClientEventsForge build() {
			return (ClientEventsForge) super.build();
		}

		public static Builder begin() {
			return new Builder().setDefault();
		}

	}

}
