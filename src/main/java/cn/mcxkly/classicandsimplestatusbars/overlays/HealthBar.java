package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import cn.mcxkly.classicandsimplestatusbars.other.helper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class HealthBar {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final Identifier fullHealthBarLocation = new Identifier(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/full.png");
    private static final Identifier witherBarLocation = new Identifier(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/wither.png");
    private static final Identifier poisonBarLocation = new Identifier(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/poison.png");
    private static final Identifier frozenBarLocation = new Identifier(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/frozen.png");
    private Identifier currentBarLocation = fullHealthBarLocation;
    private static final Identifier intermediateHealthBarLocation = new Identifier(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/intermediate.png");
    private static final Identifier emptyHealthBarLocation = new Identifier(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/empty.png");
    private static final Identifier absorptionBarLocation = new Identifier(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/absorption.png");
    public static boolean isUseSeparateIcons = false;
    private static final Identifier guiIconsLocation = new Identifier("minecraft", "textures/gui/icons.png");
    private static final Identifier armor_full = new Identifier("minecraft", "textures/gui/sprites/hud/armor_full.png");
    private static final Identifier heart_full = new Identifier("minecraft", "textures/gui/sprites/hud/heart/full.png");
    public static void isUseSeparateIconsIDEA(boolean is) {
        isUseSeparateIcons = is;
    }
    private float intermediateHealth = 0;

    public void render(DrawContext context, float tickDelta) {
        if (mc.cameraEntity instanceof PlayerEntity player
                && !mc.options.hudHidden
                && mc.interactionManager != null && mc.interactionManager.hasStatusBars()) {
            int width = mc.getWindow().getScaledWidth();
            int height = mc.getWindow().getScaledHeight();
            float x = (float) width / 2 - 91;
            float y = height - 39;
            y += 4;
            TextRenderer font = mc.textRenderer;
            updateBarTextures(player);
            renderHealthBar(context, tickDelta, (int) x, (int) y, player);
            renderHealthValue(font, context, (int) x, (int) y, player);
        }
    }

    public void updateBarTextures(PlayerEntity player) {
        if (player.hasStatusEffect(StatusEffects.WITHER)) {
            currentBarLocation = witherBarLocation;
        } else if (player.hasStatusEffect(StatusEffects.POISON)) {
            currentBarLocation = poisonBarLocation;
        } else if (player.isFrozen()) {
            currentBarLocation = frozenBarLocation;
        } else {
            currentBarLocation = fullHealthBarLocation;
        }
    }

    private void renderHealthValue(TextRenderer font, DrawContext context, int x, int y, PlayerEntity player) {
        y += 1;
        if (isUseSeparateIcons) {
            context.drawTexture(heart_full,
                    x, y - 10,
                    0, 0,
                    9, 9,
                    9, 9); // 红心图标
        } else {
            context.drawTexture(guiIconsLocation,
                    x, y - 10,
                    52, 0,
                    9, 9,
                    256, 256); // 红心图标
        }
        float MaxHealth = player.getMaxHealth(); // 最大血量
        float Health = Math.min(player.getHealth(), MaxHealth); // 当前血量
        float Absorption = player.getAbsorptionAmount(); // 吸收量
        float ARMOR = player.getArmor(); // 护甲值
        int xx = x + 10;
        String text;
        if (Absorption > 0) {
            text = helper.KeepOneDecimal(Health);
            context.drawText(font, text, xx, y - 9, 0xEE0000, false);
            xx = xx + font.getWidth(text);
            text = "+" + helper.KeepOneDecimal(Absorption);
            context.drawText(font, text, xx, y - 9, 0xEEEE00, false);
            xx = xx + font.getWidth(text);
            text = "/" + helper.KeepOneDecimal(MaxHealth);
            context.drawText(font, text, xx, y - 9, 0xEE0000, false);
        } else {
            text = helper.KeepOneDecimal(Health) + "/" + helper.KeepOneDecimal(MaxHealth);
            context.drawText(font, text, x + 10, y - 9, 0xEE0000, false);
        }
        if (ARMOR > 0) {
            if (isUseSeparateIcons) {
                context.drawTexture(armor_full,
                        x, y - 19,
                        0, 0,
                        9, 9,
                        9, 9); // 护甲图标
            } else {
                context.drawTexture(guiIconsLocation,
                        x, y - 19,
                        43, 9,
                        9, 9,
                        256, 256); // 护甲图标
            }
            context.drawText(font, helper.KeepOneDecimal(ARMOR), x + 10, y - 19, 0xEDEDED, false);
        }
    }

    private void renderHealthBar(DrawContext context, float tickDelta, int x, int y, PlayerEntity player) {
        float maxHealth = player.getMaxHealth();
        float health = Math.min(player.getHealth(), maxHealth);
        // Calculate bar proportions
        float healthProportion;
        float intermediateProportion;
        if (intermediateHealth > maxHealth) intermediateHealth = maxHealth;
        if (health < intermediateHealth) {
            //healthProportion = health / maxHealth;
            intermediateProportion = (intermediateHealth - health) / maxHealth;
        } else {
            //healthProportion = intermediateHealth / maxHealth;
            intermediateProportion = 0;
        }
        //if (healthProportion > 1) healthProportion = 1F;
        healthProportion = health / maxHealth;
        //if (healthProportion + intermediateProportion > 1) intermediateProportion = 1 - healthProportion;
        int healthWidth = (int) Math.ceil(80 * healthProportion);
        int intermediateWidth = (int) Math.ceil(80 * intermediateProportion);
        // Display empty part
        context.drawTexture(emptyHealthBarLocation,
                x + healthWidth + intermediateWidth, y,
                healthWidth + intermediateWidth, 0,
                80 - healthWidth - intermediateWidth, 5,
                80, 5);
        context.drawTexture(currentBarLocation,
                x, y,
                0, 0,
                healthWidth, 5,
                80, 5);
        float absorption = Math.min(player.getAbsorptionAmount(), maxHealth);
        float absorptionProportion = absorption / maxHealth;
        if (absorptionProportion > 1) absorptionProportion = 1F;
        int absorptionWidth = (int) Math.ceil(80 * absorptionProportion);
        if (absorption > 0) {
            context.drawTexture(absorptionBarLocation,
                    x, y,
                    0, 0,
                    absorptionWidth, 5,
                    80, 5);
        }
        int InsWidth = 0;
        float Inshealth = 0;
        if (absorption > 0) {
            InsWidth = absorptionWidth;
            Inshealth = absorption;
        } else {
            InsWidth = healthWidth;
            Inshealth = health;
        }
        // Display intermediate part
        context.drawTexture(intermediateHealthBarLocation,
                x + InsWidth, y,
                InsWidth, 0,
                intermediateWidth, 5,
                80, 5);
        // Update intermediate health
        this.intermediateHealth += (Inshealth - intermediateHealth) * tickDelta * 0.08;
        if (Math.abs(Inshealth - intermediateHealth) <= 0.25) {
            this.intermediateHealth = Inshealth;
        }
    }
}
