package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import cn.mcxkly.classicandsimplestatusbars.Config;
import cn.mcxkly.classicandsimplestatusbars.other.helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class HealthBar implements IGuiOverlay {

    private static final ResourceLocation fullHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/full.png");
    private static final ResourceLocation witherBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/wither.png");
    private static final ResourceLocation poisonBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/poison.png");
    private static final ResourceLocation frozenBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/frozen.png");
    private ResourceLocation currentBarLocation = fullHealthBarLocation;
    private static final ResourceLocation intermediateHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/intermediate.png");
    private static final ResourceLocation emptyHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/empty.png");

    private static final ResourceLocation absorptionBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/healthbars/absorption.png");
    private static final ResourceLocation guiIconsLocation = new ResourceLocation("minecraft", "textures/gui/icons.png");

    private float intermediateHealth = 0;

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        if ( gui.shouldDrawSurvivalElements() ) {
            Font font = gui.getFont();
            Player player = (Player) Minecraft.getInstance().cameraEntity;
            if ( player == null ) return;
            int x = width / 2 - 91;
            int y = height - 39;
            y += 4;
            if ( Config.All_On ) {
                if ( Config.Health_On ) {
                    updateBarTextures(player);
                    renderHealthBar(guiGraphics, partialTick, x, y, player);
                    renderHealthValue(font, guiGraphics, x, y, player);
                } else if ( Config.EasyMode_Text_On ) {
                    renderHealthValue_Easy(font, guiGraphics, x, y, player);
                }
            } else if ( Config.EasyMode_Text_On ) {
                renderHealthValue_Easy(font, guiGraphics, x, y, player);
            }
        }
    }

    private void renderHealthValue_Easy(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        y -= 2;
//        guiGraphics.blit(guiIconsLocation,
//                x, y - 10,
//                52, 0,
//                9, 9,
//                256, 256); // 红心图标
        float MaxHealth = player.getMaxHealth(); // 最大血量
        float Health = Math.min(player.getHealth(), MaxHealth); // 当前血量
        float Absorption = player.getAbsorptionAmount(); // 吸收量
        int xx = x - 2;
        if ( Absorption > 0 ) {

            String text1 = "/" + helper.KeepOneDecimal(MaxHealth);
            String text2 = "+" + helper.KeepOneDecimal(Absorption);
            String text3 = helper.KeepOneDecimal(Health);

            int x3 = font.width(text3);
            int x2 = font.width(text2);
            int x1 = font.width(text1);

            guiGraphics.drawString(font, text1, xx - x1, y - 1, Config.Color_Health_Tail, false);
            guiGraphics.drawString(font, text2, xx - x2 - x1, y - 1, Config.Color_Health_Absorb, false);
            guiGraphics.drawString(font, text3, xx - x3 - x2 - x1, y - 1, Config.Color_Health, false);
        } else {
            String text = helper.KeepOneDecimal(Health) + "/" + helper.KeepOneDecimal(MaxHealth);
            xx = xx - font.width(text);
            guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Health_Tail, false);
        }
    }

    public void updateBarTextures(Player player) {
        if ( player.hasEffect(MobEffects.WITHER) ) {
            currentBarLocation = witherBarLocation;
        } else if ( player.hasEffect(MobEffects.POISON) ) {
            currentBarLocation = poisonBarLocation;
        } else if ( player.isFullyFrozen() ) {
            currentBarLocation = frozenBarLocation;
        } else {
            currentBarLocation = fullHealthBarLocation;
        }
    }

    private void renderHealthValue(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        y += 1;
        guiGraphics.blit(guiIconsLocation,
                x, y - 10,
                52, 0,
                9, 9,
                256, 256); // 红心图标
        float MaxHealth = player.getMaxHealth(); // 最大血量
        float Health = Math.min(player.getHealth(), MaxHealth); // 当前血量
        float Absorption = player.getAbsorptionAmount(); // 吸收量
        float ARMOR = player.getArmorValue(); // 护甲值
        int xx = x + 10;
        String text;
        if ( Absorption > 0 ) {
            text = helper.KeepOneDecimal(Health);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health, false);
            xx = xx + font.width(text);
            text = "+" + helper.KeepOneDecimal(Absorption);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health_Absorb, false);
            xx = xx + font.width(text);
            text = "/" + helper.KeepOneDecimal(MaxHealth);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health_Tail, false);
        } else {
            text = helper.KeepOneDecimal(Health) + "/" + helper.KeepOneDecimal(MaxHealth);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health_Tail, false);
        }
        if ( ARMOR > 0 ) {
            guiGraphics.blit(guiIconsLocation,
                    x, y - 19,
                    43, 9,
                    9, 9,
                    256, 256); // 护甲图标
            guiGraphics.drawString(font, helper.KeepOneDecimal(ARMOR), x + 10, y - 19, 0xEDEDED, false);
        }
    }

    private void renderHealthBar(GuiGraphics guiGraphics, float partialTick, int x, int y, Player player) {
        float maxHealth = player.getMaxHealth();
        float health = Math.min(player.getHealth(), maxHealth);
        // Calculate bar proportions
        float healthProportion;
        float intermediateProportion;
        if ( intermediateHealth > maxHealth ) intermediateHealth = maxHealth;
        if ( health < intermediateHealth ) {
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
        guiGraphics.blit(emptyHealthBarLocation,
                x + healthWidth + intermediateWidth, y,
                healthWidth + intermediateWidth, 0,
                80 - healthWidth - intermediateWidth, 5,
                80, 5);

        // Display full part
        guiGraphics.blit(currentBarLocation,
                x, y,
                0, 0,
                healthWidth, 5,
                80, 5);


        float absorption = Math.min(player.getAbsorptionAmount(), maxHealth);
        float absorptionProportion = absorption / maxHealth;
        if ( absorptionProportion > 1 ) absorptionProportion = 1F;
        int absorptionWidth = (int) Math.ceil(80 * absorptionProportion);
        if ( absorption > 0 ) {
            guiGraphics.blit(absorptionBarLocation,
                    x, y,
                    0, 0,
                    absorptionWidth, 5,
                    80, 5);
        }
        int InsWidth = 0;
        float Inshealth = 0;
        if ( absorption > 0 ) {
            InsWidth = absorptionWidth;
            Inshealth = absorption;
        } else {
            InsWidth = healthWidth;
            Inshealth = health;
        }
        // Display intermediate part
        guiGraphics.blit(intermediateHealthBarLocation,
                x + InsWidth, y,
                InsWidth, 0,
                intermediateWidth, 5,
                80, 5);
        // Update intermediate health
        this.intermediateHealth += (Inshealth - intermediateHealth) * partialTick * 0.08;
        if ( Math.abs(Inshealth - intermediateHealth) <= 0.25 ) {
            this.intermediateHealth = Inshealth;
        }
    }


}
