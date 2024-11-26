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

package xaero.pac.common.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.NaturalSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.pac.OpenPartiesAndClaims;

@Mixin(NaturalSpawner.class)
public class MixinNaturalSpawner {

	@Inject(method = "isValidPositionForMob", at = @At("RETURN"), cancellable = true)
	private static void onIsValidPositionForMob(ServerLevel serverLevel, Mob mob, double d, CallbackInfoReturnable<Boolean> cir){
		if(cir.getReturnValue())
			cir.setReturnValue(!OpenPartiesAndClaims.INSTANCE.getCommonEvents().onMobSpawn(mob, mob.getX(), mob.getY(), mob.getZ(), EntitySpawnReason.NATURAL));
	}

}
