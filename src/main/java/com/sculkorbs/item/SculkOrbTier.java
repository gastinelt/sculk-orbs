package com.sculkorbs.item;

import net.minecraft.ChatFormatting;

public enum SculkOrbTier {
    MASSIVE("massive", "Orbe Massif", "Massive Orb", 25.0, 75, ChatFormatting.GREEN, false),
    GIANT("giant", "Orbe Géant", "Giant Orb", 25.0, 350, ChatFormatting.AQUA, false),
    COLOSSAL("colossal", "Orbe Colossal", "Colossal Orb", 25.0, 1500, ChatFormatting.LIGHT_PURPLE, true),
    MYTHIC("mythic", "Orbe Mythique", "Mythic Orb", 20.0, 6000, ChatFormatting.GOLD, true),
    MAXIMAL("maximal", "Orbe Maximal", "Maximal Orb", 5.0, 30000, ChatFormatting.DARK_RED, true);

    private final String id;
    private final String frenchName;
    private final String englishName;
    private final double dropRatePercent;
    private final int xpAmount;
    private final ChatFormatting color;
    private final boolean foil;

    SculkOrbTier(String id, String frenchName, String englishName, double dropRatePercent, int xpAmount, ChatFormatting color, boolean foil) {
        this.id = id;
        this.frenchName = frenchName;
        this.englishName = englishName;
        this.dropRatePercent = dropRatePercent;
        this.xpAmount = xpAmount;
        this.color = color;
        this.foil = foil;
    }

    public String getId() {
        return id;
    }

    public String getFrenchName() {
        return frenchName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public double getDropRatePercent() {
        return dropRatePercent;
    }

    public int getXpAmount() {
        return xpAmount;
    }

    public ChatFormatting getColor() {
        return color;
    }

    public boolean isFoil() {
        return foil;
    }
}
