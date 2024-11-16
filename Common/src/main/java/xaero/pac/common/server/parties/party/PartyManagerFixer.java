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

package xaero.pac.common.server.parties.party;

import xaero.pac.OpenPartiesAndClaims;
import xaero.pac.common.parties.party.member.PartyMember;

import java.util.*;

public final class PartyManagerFixer {
	
	public void fix(PartyManager partyManager) {
		OpenPartiesAndClaims.LOGGER.info("Fixing party inconsistencies...");
		List<ServerParty> partiesToRemove = new ArrayList<>();
		partyManager.getTypedAllStream().forEach(party -> {
			List<UUID> membersToRemove = new ArrayList<>();
			party.getTypedMemberInfoStream().forEach(p -> fixPlayer(party, partyManager, p, partiesToRemove, membersToRemove));
			membersToRemove.forEach(party::removeMember);//doesn't mess up client sync because nobody is online yet
		});
		partiesToRemove.forEach(partyManager::removeTypedParty);
	}
	
	private void fixPlayer(ServerParty fixingParty, PartyManager partyManager, PartyMember player, List<ServerParty> partiesToRemove, List<UUID> membersToRemove) {
		UUID playerId = player.getUUID();
		ServerParty correctParty = partyManager.getPartyByMember(playerId);
		if(correctParty == fixingParty)
			return;
		if(fixingParty.getOwner().getUUID().equals(playerId)) {
			partiesToRemove.add(fixingParty);
			return;
		}
		membersToRemove.add(player.getUUID());
	}

}
