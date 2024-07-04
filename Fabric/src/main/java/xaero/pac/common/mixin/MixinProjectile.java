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

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.pac.common.server.core.ServerCore;
import xaero.pac.common.server.core.ServerCoreFabric;

@Mixin(value = Projectile.class, priority = 1000001)
public class MixinProjectile {

	@Inject(method = "mayInteract", at = @At("HEAD"))
	public void onMobGriefGameRuleMethod(CallbackInfoReturnable<Boolean> callbackInfo){
		ServerCoreFabric.tryToSetMobGriefingEntity((Entity)(Object)this);
	}

	@Inject(method = "hitTargetOrDeflectSelf", at = @At("HEAD"), cancellable = true)
	public void hitHead(HitResult hitResult, CallbackInfoReturnable<ProjectileDeflection> cir){
		ProjectileDeflection checkResult = ServerCore.checkProjectileHit(hitResult, (Projectile)(Object)this);
		if(checkResult != null)
			cir.setReturnValue(checkResult);
	}

	@Inject(method = "hitTargetOrDeflectSelf", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/Projectile;onHit(Lnet/minecraft/world/phys/HitResult;)V"))
	public void preHit(CallbackInfoReturnable<ProjectileDeflection> cir){
		ServerCore.preProjectileHit((Projectile)(Object)this);
	}

	@Inject(method = "hitTargetOrDeflectSelf", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/world/entity/projectile/Projectile;onHit(Lnet/minecraft/world/phys/HitResult;)V"))
	public void postHit(CallbackInfoReturnable<ProjectileDeflection> cir){
		ServerCore.postProjectileHit((Projectile)(Object)this);
	}

}
