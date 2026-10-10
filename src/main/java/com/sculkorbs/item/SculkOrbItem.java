package com.sculkorbs.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SculkOrbItem extends Item {

    private final SculkOrbTier tier;

    public SculkOrbItem(SculkOrbTier tier, Properties properties) {
        super(properties);
        this.tier = tier;
    }

    public SculkOrbTier getTier() {
        return tier;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return tier.isFoil() || super.isFoil(stack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            // Donner l'expérience
            player.giveExperiencePoints(tier.getXpAmount());

            // Effets, particules et sons selon le palier de l'orbe
            double px = player.getX();
            double py = player.getY();
            double pz = player.getZ();

            switch (tier) {
                case MASSIVE -> {
                    serverLevel.sendParticles(ParticleTypes.SCULK_CHARGE_POP, px, py + 1.0, pz, 20, 0.4, 0.5, 0.4, 0.05);
                    level.playSound(null, px, py, pz, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0f, 1.2f);
                }
                case GIANT -> {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 0)); // 15s Vitesse I
                    serverLevel.sendParticles(ParticleTypes.SCULK_SOUL, px, py + 1.0, pz, 30, 0.5, 0.6, 0.5, 0.08);
                    level.playSound(null, px, py, pz, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.PLAYERS, 1.0f, 1.3f);
                    level.playSound(null, px, py, pz, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0f, 1.0f);
                }
                case COLOSSAL -> {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1)); // 10s Regen II
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0)); // 30s Vision nocturne
                    serverLevel.sendParticles(ParticleTypes.SCULK_SOUL, px, py + 1.0, pz, 45, 0.6, 0.8, 0.6, 0.1);
                    serverLevel.sendParticles(ParticleTypes.END_ROD, px, py + 1.0, pz, 15, 0.4, 0.5, 0.4, 0.05);
                    level.playSound(null, px, py, pz, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 1.5f, 0.8f);
                    level.playSound(null, px, py, pz, SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.8f, 1.2f);
                }
                case MYTHIC -> {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 1)); // 30s Résistance II
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 2)); // 15s Regen III
                    player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 3)); // 60s Absorption IV
                    serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, px, py + 1.0, pz, 50, 0.5, 0.8, 0.5, 0.3);
                    serverLevel.sendParticles(ParticleTypes.SCULK_SOUL, px, py + 1.0, pz, 50, 0.6, 0.8, 0.6, 0.1);
                    level.playSound(null, px, py, pz, SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.8f, 1.4f);
                    level.playSound(null, px, py, pz, SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.PLAYERS, 0.5f, 1.3f);
                }
                case MAXIMAL -> {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 900, 2)); // 45s Force III
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 900, 2)); // 45s Résistance III
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 3)); // 30s Regen IV
                    player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 4)); // 2m Absorption V
                    player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 1)); // 1m Célérité II
                    serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, px, py + 1.0, pz, 1, 0, 0, 0, 0);
                    serverLevel.sendParticles(ParticleTypes.FLASH, px, py + 1.0, pz, 3, 0.2, 0.5, 0.2, 0);
                    serverLevel.sendParticles(ParticleTypes.SCULK_SOUL, px, py + 1.0, pz, 80, 0.8, 1.2, 0.8, 0.2);
                    serverLevel.sendParticles(ParticleTypes.ENCHANTED_HIT, px, py + 1.0, pz, 60, 0.8, 1.0, 0.8, 0.5);
                    level.playSound(null, px, py, pz, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 1.2f, 1.0f);
                    level.playSound(null, px, py, pz, SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.0f, 1.0f);
                }
            }

            // Consommation de l'item en mode survie
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }

            player.awardStat(Stats.ITEM_USED.get(this));
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(Component.translatable("tooltip.sculkorbs.drop_rate", tier.getDropRatePercent() + "%").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("tooltip.sculkorbs.xp_gain", String.format("%,d", tier.getXpAmount())).withStyle(ChatFormatting.GREEN));
        tooltipComponents.add(Component.empty());
        tooltipComponents.add(Component.translatable("tooltip.sculkorbs.use_hint").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
    }
}
