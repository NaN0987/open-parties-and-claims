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

package xaero.pac.common.server.claims.player;

import net.minecraft.resources.ResourceLocation;
import xaero.pac.common.claims.player.IPlayerChunkClaim;
import xaero.pac.common.claims.player.IPlayerClaimInfo;
import xaero.pac.common.claims.player.IPlayerClaimPosList;
import xaero.pac.common.claims.player.IPlayerDimensionClaims;
import xaero.pac.common.claims.player.api.IPlayerDimensionClaimsAPI;
import xaero.pac.common.parties.party.IPartyPlayerInfo;
import xaero.pac.common.parties.party.ally.IPartyAlly;
import xaero.pac.common.parties.party.member.IPartyMember;
import xaero.pac.common.server.IServerData;
import xaero.pac.common.server.claims.IServerClaimsManager;
import xaero.pac.common.server.claims.IServerDimensionClaimsManager;
import xaero.pac.common.server.claims.IServerRegionClaims;
import xaero.pac.common.server.claims.player.api.IServerPlayerClaimInfoAPI;
import xaero.pac.common.server.claims.player.task.PlayerClaimReplaceSpreadoutTask;
import xaero.pac.common.server.parties.party.IServerParty;
import xaero.pac.common.server.player.config.IPlayerConfig;

import javax.annotation.Nonnull;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Stream;

public interface IServerPlayerClaimInfo<DC extends IPlayerDimensionClaims<?>> extends IServerPlayerClaimInfoAPI, IPlayerClaimInfo<DC>	 {
	
	//internal api
	
	@Override
	public int getClaimCount();
	
	@Override
	public int getForceloadCount();
	
	@Nonnull
	@Override
	public UUID getPlayerId();
	
	@Nonnull
	@Override
	public String getPlayerUsername();
	
	@Nonnull
	@Override
	public Stream<Entry<ResourceLocation, DC>> getTypedStream();

	@Nonnull
	@Override
	@SuppressWarnings("unchecked")
	default Stream<Entry<ResourceLocation, IPlayerDimensionClaimsAPI>> getStream(){
		return (Stream<Entry<ResourceLocation, IPlayerDimensionClaimsAPI>>)(Object)getTypedStream();
	}

	public Stream<Entry<ResourceLocation, DC>> getFullStream();
	
	public void registerActivity();

	public long getConfirmedActivity();

	public boolean isReplacementInProgress();

	public void setReplacementInProgress(boolean replacementInProgress);

	public IPlayerConfig getConfig();

	public boolean hasReplacementTasks();

	public void addReplacementTask(PlayerClaimReplaceSpreadoutTask task, IServerData<IServerClaimsManager<IPlayerChunkClaim, IServerPlayerClaimInfo<IPlayerDimensionClaims<IPlayerClaimPosList>>, IServerDimensionClaimsManager<IServerRegionClaims>>, IServerParty<IPartyMember, IPartyPlayerInfo, IPartyAlly>> serverData);

	public PlayerClaimReplaceSpreadoutTask removeNextReplacementTask();

}
