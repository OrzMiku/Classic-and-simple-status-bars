package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import cn.mcxkly.classicandsimplestatusbars.Config;
import cn.mcxkly.classicandsimplestatusbars.other.helper;
import io.github.apace100.apoli.util.HudRender;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Comparator;
import java.util.List;

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
        float MaxHealth = player.getMaxHealth(); // 最大血量
        float Health = Math.min(player.getHealth(), MaxHealth); // 当前血量
        float Absorption = player.getAbsorptionAmount(); // 吸收量
        int xx = x - 2;
        if ( Absorption > 0 ) {
            String text = helper.KeepOneDecimal(Health);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health, false);

            xx = xx + font.width(text); // '+'
            text =  Config.Interval_TTT;
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Interval_TTT, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(Absorption);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health_Absorb, false);

            xx = xx + font.width(text); // '/'
            text =  Config.Interval_lll;
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Interval_lll, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(MaxHealth);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health_Tail, false);
        } else {
            String text = helper.KeepOneDecimal(Health);
            guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Health, false);

            xx = xx + font.width(text); // '/'
            text =  Config.Interval_lll;
            guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Interval_lll, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(MaxHealth);
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

    private void renderHealthValue(Font font, GuiGraphics guiGraphics, int x, int Y, Player player) {
        int y = Y + 1;
        int finalY = Y - 8;
        int finalX = x + 72;
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

            xx = xx + font.width(text); // '+'
            text =  Config.Interval_TTT;
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Interval_TTT, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(Absorption);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health_Absorb, false);

            xx = xx + font.width(text); // '/'
            text =  Config.Interval_lll;
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Interval_lll, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(MaxHealth);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health_Tail, false);
        } else {
            text = helper.KeepOneDecimal(Health);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health, false);

            xx = xx + font.width(text); // '/'
            text =  Config.Interval_lll;
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Interval_lll, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(MaxHealth);
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Health_Tail, false);
        }
        if ( ARMOR > 0 && Config.Armour_On) {
            guiGraphics.blit(guiIconsLocation,
                    x, y - 19,
                    43, 9,
                    9, 9,
                    256, 256); // 护甲图标
            guiGraphics.drawString(font, helper.KeepOneDecimal(ARMOR), x + 10, y - 19, 0xEDEDED, false);
        }
        if ( Config.Origins_On ) {
            IPowerContainer.get(player).ifPresent((component) -> {
                int iconSize = 8;
                List<? extends ConfiguredPower<?, ?>> configuredPowers = component.getPowers().stream().map(Holder :: value).filter((power) -> power.asHudRendered().isPresent()).sorted(Comparator.comparing((power) -> power.getRenderSettings(player).orElse(HudRender.DONT_RENDER).spriteLocation())).toList();

                for (ConfiguredPower<?, ?> hudPower : configuredPowers) {
                    HudRender render = hudPower.getRenderSettings(player).orElse(HudRender.DONT_RENDER);
                    if ( render.shouldRender(player) && hudPower.shouldRender(player).orElse(false) ) {
                        ResourceLocation currentLocation = render.spriteLocation();
                        int v = 8 + render.barIndex() * 10;
                        float fill = hudPower.getFill(player).orElse(0.0F);
                        if ( render.isInverted() ) {
                            fill = 1.0F - fill;
                        }
                        String tex = helper.KeepOneDecimal((int)(fill * (float)100));
                        int finalY2 = finalY - 10;
                        /*
                        int finalY2 = finalY;
                        if ( Absorption > 0 ) {
                            // 避免血量文本太长. 在拥有吸收值时，提高高度.
                            finalY2 -= 10;
                        } */
                        guiGraphics.drawString(font, "%", finalX - font.width("%"), finalY2, Config.Color_Origins, false);
                        guiGraphics.drawString(font, tex, finalX - font.width(tex) - font.width("%"), finalY2,Config.Color_Origins , false);
                        // 渲染图标
                        guiGraphics.blit(currentLocation,
                                finalX, finalY2,
                                73, v,
                                iconSize, iconSize);
                    }
                }
            });
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
