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

package xaero.pac.client.player.config;

import xaero.pac.client.player.config.api.IPlayerConfigClientStorageManagerAPI;
import xaero.pac.common.misc.MapFactory;
import xaero.pac.common.player.config.dynamic.PlayerConfigDynamicOptions;
import xaero.pac.common.server.player.config.api.IPlayerConfigOptionSpecAPI;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface IPlayerConfigClientStorageManager<CS extends IPlayerConfigClientStorage<?>> extends IPlayerConfigClientStorageManagerAPI {

	//internal api

	@Override
	@Nonnull
	public CS getServerClaimsConfig();

	@Override
	@Nonnull
	public CS getExpiredClaimsConfig();

	@Override
	@Nonnull
	public CS getWildernessConfig();

	@Override
	@Nonnull
	public CS getDefaultPlayerConfig();
	@Override
	@Nonnull
	public CS getMyPlayerConfig();

	public void setOtherPlayerConfig(CS otherPlayerConfig);
	public CS getOtherPlayerConfig();
	public IPlayerConfigClientStorage.IBuilder<CS> beginConfigStorageBuild(MapFactory mapFactory);
	Collection<IPlayerConfigOptionSpecAPI<?>> getOverridableOptions();
	public void setDynamicOptions(PlayerConfigDynamicOptions dynamicOptions);
}
