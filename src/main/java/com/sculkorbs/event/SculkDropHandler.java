package com.sculkorbs.event;

import com.sculkorbs.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

import java.util.Random;

public class SculkDropHandler {

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        Level level = event.getLevel();
        if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) {
            return;
        }

        BlockState state = event.getState();
        if (!state.is(Blocks.SCULK)) {
            return;
        }

        Entity breaker = event.getBreaker();
        if (breaker instanceof Player player && player.isCreative()) {
            return;
        }

        ItemStack tool = event.getTool();
        
        // Vérification de Silk Touch (Toucher de soie)
        boolean hasSilkTouch = false;
        if (!tool.isEmpty()) {
            var silkTouchHolder = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.SILK_TOUCH);
            if (silkTouchHolder.isPresent()) {
                hasSilkTouch = EnchantmentHelper.getItemEnchantmentLevel(silkTouchHolder.get(), tool) > 0;
            }
        }

        if (hasSilkTouch) {
            return; // Silk Touch conserve le bloc de sculk vanilla
        }

        // Vider les drops par défaut
        event.getDrops().clear();

        // Tirage aléatoire :
        // 25% Orbe Massif   (0.00 à 0.25)
        // 25% Orbe Géant    (0.25 à 0.50)
        // 25% Orbe Colossal (0.50 à 0.75)
        // 20% Orbe Mythique (0.75 à 0.95)
        // 5%  Orbe Maximal  (0.95 à 1.00)
        double roll = RANDOM.nextDouble();
        ItemStack droppedOrb;

        if (roll < 0.25) {
            droppedOrb = new ItemStack(ModItems.MASSIVE_ORB.get());
        } else if (roll < 0.50) {
            droppedOrb = new ItemStack(ModItems.GIANT_ORB.get());
        } else if (roll < 0.75) {
            droppedOrb = new ItemStack(ModItems.COLOSSAL_ORB.get());
        } else if (roll < 0.95) {
            droppedOrb = new ItemStack(ModItems.MYTHIC_ORB.get());
        } else {
            droppedOrb = new ItemStack(ModItems.MAXIMAL_ORB.get());
        }

        BlockPos pos = event.getPos();
        ItemEntity orbEntity = new ItemEntity(
                serverLevel,
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                droppedOrb
        );
        orbEntity.setDefaultPickUpDelay();
        event.getDrops().add(orbEntity);
    }
}
