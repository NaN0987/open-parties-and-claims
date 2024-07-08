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

package xaero.pac.common.server.claims;

import xaero.pac.common.claims.IDimensionClaimsManager;
import xaero.pac.common.server.claims.api.IServerDimensionClaimsManagerAPI;
import xaero.pac.common.server.claims.api.IServerRegionClaimsAPI;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public interface IServerDimensionClaimsManager
<
	WRC extends IServerRegionClaims
> extends IDimensionClaimsManager<WRC>, IServerDimensionClaimsManagerAPI {

	//internal api

	@Nonnull
	@Override
	@SuppressWarnings("unchecked")
	default Stream<IServerRegionClaimsAPI> getRegionStream() {
		return (Stream<IServerRegionClaimsAPI>)(Object)getTypedRegionStream();
	}
	
}
