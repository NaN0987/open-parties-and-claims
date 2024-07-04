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

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.pac.OpenPartiesAndClaims;
import xaero.pac.OpenPartiesAndClaimsFabric;
import xaero.pac.common.server.core.ServerCore;
import xaero.pac.common.server.core.ServerCoreFabric;

@Mixin(value = LivingEntity.class, priority = 1000001)
public class MixinLivingEntity {

	@Inject(at = @At("HEAD"), method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", cancellable = true)
	public void onAddEffect(MobEffectInstance mobEffectInstance, Entity entity, CallbackInfoReturnable<Boolean> info){
		if(!ServerCore.canAddLivingEntityEffect((LivingEntity)(Object)this, mobEffectInstance, entity))
			info.setReturnValue(false);
	}

	@Inject(method = "createWitherRose", at = @At("HEAD"))
	public void onCreateWitherRose(CallbackInfo callbackInfo){
		ServerCoreFabric.tryToSetMobGriefingEntity((Entity)(Object)this);
	}

	@Inject(at = @At("HEAD"), method = "hurt", cancellable = true)
	public void onHurt(DamageSource source, float f, CallbackInfoReturnable<Boolean> info) {
		if(((OpenPartiesAndClaimsFabric) OpenPartiesAndClaims.INSTANCE).getCommonEvents().onLivingHurt(source, (Entity)(Object)this))
			info.setReturnValue(false);
	}

	@Inject(at = @At("HEAD"), method = "die")
	public void onDiePre(DamageSource source, CallbackInfo info) {
		ServerCore.onLivingEntityDiePre((LivingEntity) (Object)this, source);
	}

	@Inject(at = @At("RETURN"), method = "die")
	public void onDiePost(DamageSource source, CallbackInfo info) {
		ServerCore.onLivingEntityDiePost((LivingEntity) (Object)this);
	}

	@Inject(at = @At("HEAD"), method = "dropAllDeathLoot")
	public void onDropAllDeathLoot(DamageSource source, CallbackInfo info) {
		ServerCore.onLivingEntityDropDeathLootPre((LivingEntity) (Object)this, source);
	}

	@Inject(at = @At("RETURN"), method = "dropAllDeathLoot")
	public void onDie(DamageSource source, CallbackInfo info) {
		ServerCore.onLivingEntityDropDeathLootPost((LivingEntity) (Object)this);
	}

	@Inject(at = @At("HEAD"), method = "releaseUsingItem")
	public void onReleaseUsingItem(CallbackInfo info) {
		ServerCoreFabric.onReleaseUsingItem((LivingEntity) (Object)this);
	}

	@ModifyArg(method = "updatingUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;updateUsingItem(Lnet/minecraft/world/item/ItemStack;)V"))
	public ItemStack onUpdatingUsingItem(ItemStack arg) {
		ServerCoreFabric.onUpdatingUsingItem((LivingEntity) (Object)this);
		return ((LivingEntity) (Object)this).getUseItem();
	}

}
