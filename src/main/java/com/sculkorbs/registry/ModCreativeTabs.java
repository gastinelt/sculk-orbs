package com.sculkorbs.registry;

import com.sculkorbs.SculkOrbsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SculkOrbsMod.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SCULK_ORBS_TAB = CREATIVE_MODE_TABS.register("sculk_orbs_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.sculkorbs"))
                    .icon(() -> new ItemStack(ModItems.PERFECT_ENCHANT_5.get()))
                    .displayItems((parameters, output) -> {
                        // Orbes de Sculk
                        output.accept(ModItems.MASSIVE_ORB.get());
                        output.accept(ModItems.GIANT_ORB.get());
                        output.accept(ModItems.COLOSSAL_ORB.get());
                        output.accept(ModItems.MYTHIC_ORB.get());
                        output.accept(ModItems.MAXIMAL_ORB.get());

                        // Perfect Enchants
                        output.accept(ModItems.PERFECT_ENCHANT_1.get());
                        output.accept(ModItems.PERFECT_ENCHANT_2.get());
                        output.accept(ModItems.PERFECT_ENCHANT_3.get());
                        output.accept(ModItems.PERFECT_ENCHANT_4.get());
                        output.accept(ModItems.PERFECT_ENCHANT_5.get());
                    })
                    .build());
}