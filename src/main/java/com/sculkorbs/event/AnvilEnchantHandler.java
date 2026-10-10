package com.sculkorbs.event;

import com.sculkorbs.enchant.PerfectEnchantApplier;
import com.sculkorbs.item.PerfectEnchantItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class AnvilEnchantHandler {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.isEmpty() || right.isEmpty()) return;

        if (right.getItem() instanceof PerfectEnchantItem enchantItem) {
            HolderLookup.Provider registries;
            if (event.getPlayer() != null) {
                registries = event.getPlayer().level().registryAccess();
            } else {
                return;
            }

            ItemStack output = PerfectEnchantApplier.applyPerfectEnchantments(left, enchantItem.getLevel(), registries);

            if (!output.isEmpty()) {
                event.setOutput(output);
                event.setCost(1); // 1 niveau d'XP de coût à l'enclume
                event.setMaterialCost(1); // Consomme 1 Perfect Enchant
            }
        }
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack crafted = event.getCrafting();
        if (crafted.getItem() instanceof PerfectEnchantItem) {
            Container container = event.getInventory();

            // Calcul du nombre de slots contenant du sculk
            int sculkSlots = 0;
            for (int i = 0; i < container.getContainerSize(); i++) {
                ItemStack stack = container.getItem(i);
                if (stack.is(Blocks.SCULK.asItem())) {
                    sculkSlots++;
                }
            }

            // Minecraft a déjà décrémenté 1 item par slot non vide lors du clic de craft.
            // On retire le reste des 64 blocs de sculk nécessaires :
            int sculkToConsume = Math.max(0, 64 - sculkSlots);

            for (int i = 0; i < container.getContainerSize(); i++) {
                if (sculkToConsume <= 0) break;
                ItemStack stack = container.getItem(i);
                if (stack.is(Blocks.SCULK.asItem())) {
                    int toTake = Math.min(stack.getCount(), sculkToConsume);
                    stack.shrink(toTake);
                    sculkToConsume -= toTake;
                }
            }
        }
    }
}