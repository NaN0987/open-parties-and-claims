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

package xaero.pac.common.packet.claims;

import net.minecraft.network.FriendlyByteBuf;
import xaero.pac.OpenPartiesAndClaims;
import xaero.pac.common.server.lazypacket.LazyPacket;

import java.util.function.Function;

public class ClientboundClaimsClaimUpdateNextZPosPacket extends LazyPacket<ClientboundClaimsClaimUpdateNextZPosPacket> {

	public static final Encoder<ClientboundClaimsClaimUpdateNextZPosPacket> ENCODER = new Encoder<>();
	public static final Decoder DECODER = new Decoder();

	public ClientboundClaimsClaimUpdateNextZPosPacket() {
		super();
	}

	@Override
	protected Function<FriendlyByteBuf, ClientboundClaimsClaimUpdateNextZPosPacket> getDecoder() {
		return DECODER;
	}

	@Override
	protected void writeOnPrepare(FriendlyByteBuf u) {
	}
	
	public static class Decoder implements Function<FriendlyByteBuf, ClientboundClaimsClaimUpdateNextZPosPacket> {

		@Override
		public ClientboundClaimsClaimUpdateNextZPosPacket apply(FriendlyByteBuf input) {
			try {
				return new ClientboundClaimsClaimUpdateNextZPosPacket();
			} catch(Throwable t) {
				OpenPartiesAndClaims.LOGGER.error("invalid packet", t);
				return null;
			}
		}
		
	}
	
	public static class ClientHandler extends Handler<ClientboundClaimsClaimUpdateNextZPosPacket> {
		
		@Override
		public void handle(ClientboundClaimsClaimUpdateNextZPosPacket t) {
			OpenPartiesAndClaims.INSTANCE.getClientDataInternal().getClientClaimsSyncHandler().onClaimUpdateNextZPos();
		}
		
	}

}
