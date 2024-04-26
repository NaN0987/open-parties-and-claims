/*
 * Open Parties and Claims - adds chunk claims and player parties to Minecraft
 * Copyright (C) 2022-2024, Xaero <xaero1996@gmail.com> and contributors
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

package xaero.pac.common.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import xaero.pac.common.packet.type.PacketType;

public abstract class PacketReceiver<C> {

	private final PacketHandlerFull packetHandlerFull;

	public PacketReceiver(PacketHandlerFull packetHandlerFull) {
		this.packetHandlerFull = packetHandlerFull;
	}

	private PacketType<?> getPacketType(FriendlyByteBuf buf) {
		if(buf.readableBytes() <= 0)
			return null;
		int index = buf.readByte();
		return packetHandlerFull.getPacketTypeByIndex(index);
	}

	protected void receive(ReentrantBlockableEventLoop<?> executor, FriendlyByteBuf buf, C context){
		receive(executor, getPacketType(buf), buf, context);
	}

	private <T> void receive(ReentrantBlockableEventLoop<?> executor, PacketType<T> packetType, FriendlyByteBuf buf, C context){
		if(packetType == null)
			return;
		if(!isCorrectSide(packetType))
			return;
		T packet = packetType.getDecoder().apply(buf);
		executor.execute(getTask(packetType, packet, context));
	}

	protected abstract <T> boolean isCorrectSide(PacketType<T> packetType);
	protected abstract <T> Runnable getTask(PacketType<T> packetType, T packet, C context);

}
