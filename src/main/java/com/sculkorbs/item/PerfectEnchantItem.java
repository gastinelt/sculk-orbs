package com.sculkorbs.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class PerfectEnchantItem extends Item {

    private final int level;

    public PerfectEnchantItem(int level, Properties properties) {
        super(properties);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        String roman = switch (level) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            default -> String.valueOf(level);
        };

        tooltipComponents.add(Component.translatable("tooltip.sculkorbs.perfect_enchant_tier", roman).withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("tooltip.sculkorbs.perfect_enchant_desc").withStyle(ChatFormatting.AQUA));
        tooltipComponents.add(Component.translatable("tooltip.sculkorbs.perfect_enchant_max", level).withStyle(ChatFormatting.YELLOW));
        tooltipComponents.add(Component.empty());

        if (level == 1) {
            tooltipComponents.add(Component.translatable("tooltip.sculkorbs.craft_hint_t1").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        } else {
            String prevRoman = switch (level - 1) {
                case 1 -> "I";
                case 2 -> "II";
                case 3 -> "III";
                case 4 -> "IV";
                default -> String.valueOf(level - 1);
            };
            tooltipComponents.add(Component.translatable("tooltip.sculkorbs.craft_hint_tn", prevRoman).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        }
    }
}