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

package xaero.pac.common.server.info.io.serialization.nbt;

import net.minecraft.nbt.CompoundTag;
import xaero.pac.common.server.info.ServerInfo;
import xaero.pac.common.server.info.ServerInfoHolder;
import xaero.pac.common.server.io.serialization.SerializationHandler;

public class ServerInfoSerializationHandler extends SerializationHandler<CompoundTag, Object, ServerInfo, ServerInfoHolder> {

	@Override
	public CompoundTag serialize(ServerInfo object) {
		CompoundTag tag = new CompoundTag();
		tag.putLong("totalUseTime", object.getTotalUseTime());
		tag.putInt("version", ServerInfo.CURRENT_VERSION);
		return tag;
	}

	@Override
	public ServerInfo deserialize(Object id, ServerInfoHolder manager, CompoundTag serializedData) {
		long useTime = serializedData.getLong("totalUseTime");
		int loadedVersion = serializedData.getInt("version");
		return new ServerInfo(useTime, loadedVersion);
	}

}
