package com.sculkorbs.enchant;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.ArrayList;
import java.util.List;

public class PerfectEnchantApplier {

    private record EnchantTarget(ResourceKey<Enchantment> key, int maxLevel) {}

    public static ItemStack applyPerfectEnchantments(ItemStack original, int tier, HolderLookup.Provider registries) {
        if (original.isEmpty()) return ItemStack.EMPTY;

        ItemStack result = original.copy();
        Item item = result.getItem();

        List<EnchantTarget> targets = getEnchantmentsForItem(item);
        if (targets.isEmpty()) {
            // Si c'est un item endommagé / enchantable générique (ex: moddé, bouclier, briquet, etc.)
            if (result.isDamageableItem() || item.isEnchantable(result)) {
                targets = List.of(
                        new EnchantTarget(Enchantments.UNBREAKING, 3),
                        new EnchantTarget(Enchantments.MENDING, 1)
                );
            } else {
                return ItemStack.EMPTY;
            }
        }

        var enchantmentLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
        ItemEnchantments existing = EnchantmentHelper.getEnchantmentsForCrafting(result);
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(existing);

        boolean modified = false;

        for (EnchantTarget target : targets) {
            var holderOpt = enchantmentLookup.get(target.key());
            if (holderOpt.isPresent()) {
                Holder<Enchantment> holder = holderOpt.get();
                int targetLevel = Math.min(tier, target.maxLevel());
                int currentLevel = mutable.getLevel(holder);

                if (targetLevel > currentLevel) {
                    mutable.set(holder, targetLevel);
                    modified = true;
                }
            }
        }

        if (!modified && !existing.isEmpty()) {
            // Même si tous les enchants étaient déjà au max, on peut valider l'opération
            modified = true;
        }

        if (modified) {
            EnchantmentHelper.setEnchantments(result, mutable.toImmutable());
            return result;
        }

        return ItemStack.EMPTY;
    }

    private static List<EnchantTarget> getEnchantmentsForItem(Item item) {
        List<EnchantTarget> list = new ArrayList<>();

        if (item instanceof PickaxeItem) {
            // Pioche : Fortune par défaut (le plus polyvalent comme demandé), Efficacité, Solidité, Raccommodage
            list.add(new EnchantTarget(Enchantments.EFFICIENCY, 5));
            list.add(new EnchantTarget(Enchantments.FORTUNE, 3));
            list.add(new EnchantTarget(Enchantments.UNBREAKING, 3));
            list.add(new EnchantTarget(Enchantments.MENDING, 1));
        } else if (item instanceof AxeItem) {
            // Hache : Efficacité, Tranchant, Fortune, Solidité, Raccommodage
            list.add(new EnchantTarget(Enchantments.EFFICIENCY, 5));
            list.add(new EnchantTarget(Enchantments.SHARPNESS, 5));
            list.add(new EnchantTarget(Enchantments.FORTUNE, 3));
            list.add(new EnchantTarget(Enchantments.UNBREAKING, 3));
            list.add(new EnchantTarget(Enchantments.MENDING, 1));
        } else if (item instanceof ShovelItem || item instanceof HoeItem) {
            // Pelle & Houe : Efficacité, Fortune, Solidité, Raccommodage
            list.add(new EnchantTarget(Enchantments.EFFICIENCY, 5));
            list.add(new EnchantTarget(Enchantments.FORTUNE, 3));
            list.add(new EnchantTarget(Enchantments.UNBREAKING, 3));
            list.add(new EnchantTarget(Enchantments.MENDING, 1));
        } else if (item instanceof SwordItem) {
            // Épée : Tranchant, Butin, Balayage, Aura de Feu, Recul, Solidité, Raccommodage
            list.add(new EnchantTarget(Enchantments.SHARPNESS, 5));
            list.add(new EnchantTarget(Enchantments.LOOTING, 3));
            list.add(new EnchantTarget(Enchantments.SWEEPING_EDGE, 3));
            list.add(new EnchantTarget(Enchantments.FIRE_ASPECT, 2));
            list.add(new EnchantTarget(Enchantments.KNOCKBACK, 2));
            list.add(new EnchantTarget(Enchantments.UNBREAKING, 3));
            list.add(new EnchantTarget(Enchantments.MENDING, 1));
        } else if (item instanceof MaceItem) {
            // Masse (1.21) : Densité, Brèche, Rafale, Solidité, Raccommodage
            list.add(new EnchantTarget(Enchantments.DENSITY, 5));
            list.add(new EnchantTarget(Enchantments.BREACH, 4));
            list.add(new EnchantTarget(Enchantments.WIND_BURST, 3));
            list.add(new EnchantTarget(Enchantments.UNBREAKING, 3));
            list.add(new EnchantTarget(Enchantments.MENDING, 1));
        } else if (item instanceof BowItem) {
            // Arc : Puissance, Flamme, Frappe, Infinité, Solidité
            list.add(new EnchantTarget(Enchantments.POWER, 5));
            list.add(new EnchantTarget(Enchantments.FLAME, 1));
            list.add(new EnchantTarget(Enchantments.PUNCH, 2));
            list.add(new EnchantTarget(Enchantments.INFINITY, 1));
            list.add(new EnchantTarget(Enchantments.UNBREAKING, 3));
        } else if (item instanceof CrossbowItem) {
            // Arbalète : Tir multiple, Charge rapide, Solidité, Raccommodage
            list.add(new EnchantTarget(Enchantments.MULTISHOT, 1));
            list.add(new EnchantTarget(Enchantments.QUICK_CHARGE, 3));
            list.add(new EnchantTarget(Enchantments.UNBREAKING, 3));
            list.add(new EnchantTarget(Enchantments.MENDING, 1));
        } else if (item instanceof TridentItem) {
            // Trident : Empalement, Loyauté, Canalisation, Solidité, Raccommodage
            list.add(new EnchantTarget(Enchantments.IMPALING, 5));
            list.add(new EnchantTarget(Enchantments.LOYALTY, 3));
            list.add(new EnchantTarget(Enchantments.CHANNELING, 1));
            list.add(new EnchantTarget(Enchantments.UNBREAKING, 3));
            list.add(new EnchantTarget(Enchantments.MENDING, 1));
        } else if (item instanceof ArmorItem armor) {
            // Armures
            list.add(new EnchantTarget(Enchantments.PROTECTION, 4));
            list.add(new EnchantTarget(Enchantments.UNBREAKING, 3));
            list.add(new EnchantTarget(Enchantments.MENDING, 1));
            list.add(new EnchantTarget(Enchantments.THORNS, 3));

            switch (armor.getType()) {
                case HELMET -> {
                    list.add(new EnchantTarget(Enchantments.RESPIRATION, 3));
                    list.add(new EnchantTarget(Enchantments.AQUA_AFFINITY, 1));
                }
                case LEGGINGS -> {
                    list.add(new EnchantTarget(Enchantments.SWIFT_SNEAK, 3));
                }
                case BOOTS -> {
                    list.add(new EnchantTarget(Enchantments.FEATHER_FALLING, 4));
                    list.add(new EnchantTarget(Enchantments.DEPTH_STRIDER, 3));
                    list.add(new EnchantTarget(Enchantments.SOUL_SPEED, 3));
                }
                default -> {}
            }
        } else if (item instanceof ElytraItem) {
            list.add(new EnchantTarget(Enchantments.UNBREAKING, 3));
            list.add(new EnchantTarget(Enchantments.MENDING, 1));
        } else if (item instanceof FishingRodItem) {
            list.add(new EnchantTarget(Enchantments.LUCK_OF_THE_SEA, 3));
            list.add(new EnchantTarget(Enchantments.LURE, 3));
            list.add(new EnchantTarget(Enchantments.UNBREAKING, 3));
            list.add(new EnchantTarget(Enchantments.MENDING, 1));
        } else if (item instanceof ShearsItem) {
            list.add(new EnchantTarget(Enchantments.EFFICIENCY, 5));
            list.add(new EnchantTarget(Enchantments.UNBREAKING, 3));
            list.add(new EnchantTarget(Enchantments.MENDING, 1));
        }

        return list;
    }
}