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

package xaero.pac.client.claims.player;

import net.minecraft.resources.ResourceLocation;
import xaero.pac.client.claims.player.api.IClientPlayerClaimInfoAPI;
import xaero.pac.common.claims.player.IPlayerClaimInfo;
import xaero.pac.common.claims.player.IPlayerDimensionClaims;
import xaero.pac.common.claims.player.api.IPlayerDimensionClaimsAPI;

import javax.annotation.Nonnull;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Stream;

public interface IClientPlayerClaimInfo<DC extends IPlayerDimensionClaims<?>> extends IClientPlayerClaimInfoAPI, IPlayerClaimInfo<DC>	 {
	
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

}
