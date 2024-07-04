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

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.pac.common.entity.EntityData;
import xaero.pac.common.entity.IEntityFabric;
import xaero.pac.common.server.core.ServerCore;

@Mixin(value = Entity.class, priority = 1000001)
public class MixinEntity implements IEntityFabric {

	private EntityData xaero_OPAC_data;
	private CompoundTag xaero_OPAC_persistentData;

	@Override
	public CompoundTag getXaero_OPAC_PersistentData() {
		if(xaero_OPAC_persistentData == null)
			xaero_OPAC_persistentData = new CompoundTag();
		return xaero_OPAC_persistentData;
	}

	@Inject(method = "saveWithoutId", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
	public void onAddAdditionalSaveData(CompoundTag tag, CallbackInfoReturnable<CompoundTag> info) {
		if(xaero_OPAC_persistentData != null && !xaero_OPAC_persistentData.isEmpty())
			tag.put("xaero_OPAC_PersistentData", xaero_OPAC_persistentData.copy());
	}

	@Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
	public void onReadAdditionalSaveData(CompoundTag tag, CallbackInfo info) {
		xaero_OPAC_persistentData = tag.getCompound("xaero_OPAC_PersistentData");
	}

	@Inject(at = @At("RETURN"), method = "isInvulnerableTo", cancellable = true)
	public void onIsInvulnerableTo(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(ServerCore.replaceEntityIsInvulnerable(cir.getReturnValue(), damageSource, (Entity)(Object)this));
	}

	@Inject(method = "handleNetherPortal", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;changeDimension(Lnet/minecraft/server/level/ServerLevel;)Lnet/minecraft/world/entity/Entity;"), cancellable = true)
	public void onHandleNetherPortal(CallbackInfo ci){
		if(ServerCore.onHandleNetherPortal((Entity)(Object)this))
			ci.cancel();
	}

	@Override
	public EntityData getXaero_OPAC_data() {
		return xaero_OPAC_data;
	}

	@Override
	public void setXaero_OPAC_data(EntityData entityData) {
		this.xaero_OPAC_data = entityData;
	}

}
