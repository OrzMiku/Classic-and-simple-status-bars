package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



public class FoodLevel implements IGuiOverlay {
    private static final ResourceLocation fullHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/foodeeg.png");
    private static final ResourceLocation emptyHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/empty.png");
    private static final ResourceLocation saturationBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/saturation.png");
    private ResourceLocation currentBarLocation = fullHealthBarLocation;
    private static final ResourceLocation emmmmnBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/debuff-hunger.png");
    private static final ResourceLocation intermediateHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/intermediate.png");
    private static final ResourceLocation guiIconsLocation = new ResourceLocation("minecraft", "textures/gui/icons.png");
    private float intermediateFood = 0;
    private File file = new File(guiIconsLocation.getPath());
    //int[] FileHW = GetImageInfo(file);
    private float intermediateFoodSaturation = 0;

    public int[] GetImageInfo(File files) {
        try {
// 读取图片文件
            BufferedImage image = ImageIO.read(files);
// 获取图片的高度和宽度
            int height = image.getHeight();
            int width = image.getWidth();


//            System.out.println("图片高度：" + height + " 像素");
//            System.out.println("图片宽度：" + width + " 像素");
            return new int[]{height, width};
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {


        if (gui.shouldDrawSurvivalElements()) {
            Font font = gui.getFont();

            Player player = (Player) Minecraft.getInstance().cameraEntity;
            if (player == null) return;

            int x = width / 2 + 11;
            int y = height - 39;
            y += 4;

            updateBarTextures(player);
            renderFoodBar(font,guiGraphics, partialTick, x, y, player);
            //Sntext = renderMountValue(player);
            //Sntext = 123 + " | " + 123;


            //player = (Player) Minecraft.getInstance().cameraEntity;
            renderFoodValue(font, guiGraphics, x, y, player);
//            if (player.getAbsorptionAmount() > 0) {
//                renderAbsorptionBar(guiGraphics, x, y, player);
//                renderAbsorptionValue(font, guiGraphics, x, y, player);
//            }
        }
    }
    public void updateBarTextures(Player player) {
        if (player.hasEffect(MobEffects.HUNGER)) {
            currentBarLocation = emmmmnBarLocation;
        } else {
            currentBarLocation = fullHealthBarLocation;
        }
    }

    private void renderFoodValue(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        //double food = Math.ceil(player.getFoodData().getSaturationLevel() * 10) / 10;
        // getSaturationLevel饱食条 | getFoodLevel饥饿度 |  getLastFoodLevel饥饿最大值 | player.getFoodData().getExhaustionLevel(); 消耗度
        //String text = "getSaturationLevel: " + player.getFoodData().getSaturationLevel() + " | " + "getFoodLevel: " + player.getFoodData().getFoodLevel() + " | " + "getLastFoodLevel: " + player.getFoodData().getLastFoodLevel();
        //text = text.replace(".0", ""); ARMOR_TOUGHNESS
        y += 1;
        guiGraphics.blit(guiIconsLocation,
                x, y - 10,
                16, 27,
                9, 9,
                256, 256); // 鸡腿图标-背景
        guiGraphics.blit(guiIconsLocation,
                x, y - 10,
                52, 27,
                9, 9,
                256, 256); // 鸡腿图标
        if (player.getFoodData().getSaturationLevel() > 0) {
            //第一部分
            double food = Math.ceil(player.getFoodData().getFoodLevel() * 10) / 10; // 饥饿度
            String text = String.valueOf(food);
            text = text.replace(".0", "");
            int xx = x + 10;
            guiGraphics.drawString(font, text, xx, y - 9, 0xF4A460, false);
            //第二部分
            xx = xx + font.width(text);
            food = Math.ceil(player.getFoodData().getSaturationLevel() * 10) / 10; // 饱食度
            text = "+" + food;
            text = text.replace(".0", "");
            guiGraphics.drawString(font, text, xx, y - 9, 0xEEEE00, false);


        } else {
            double food = Math.ceil(player.getFoodData().getFoodLevel() * 10) / 10;
            String text = String.valueOf(food);
            text = text.replace(".0", "");
            guiGraphics.drawString(font, text, x + 10, y - 9, 0xF4A460, false);
        }

        if (player.getAirSupply() < 300) { // max=300
            int siz = player.getAirSupply() / 3;
            String text;
            siz = siz > 0 ? siz : 0;//防止负数
            text = String.valueOf(siz);

            guiGraphics.drawString(font, "%", x + 80 - font.width("%"), y - 9, 0x1E90FF, false);

            guiGraphics.drawString(font, text, x + 80 - font.width("99%"), y - 9, 0x1E90FF, false);
            guiGraphics.blit(guiIconsLocation,
                    x + 80 - font.width("99%") - 10, y - 10,
                    16, 19,
                    9, 9,
                    256, 256); // 气泡图标
        }
        Entity tsssmp = player.getVehicle();
        if (tsssmp != null) {
            LivingEntity FsMount = (LivingEntity) tsssmp;
            double MountHealths = Math.ceil(FsMount.getHealth() * 10) / 10;
            double MountHealthsMax = Math.ceil(FsMount.getMaxHealth() * 10) / 10;
            if (MountHealths > 0){
                if (MountHealths > MountHealthsMax)MountHealths=MountHealthsMax;
                String MountHealthsText = String.valueOf(MountHealths);
                MountHealthsText = MountHealthsText.replace(".0", "");
                String MountHealthsMaxText = String.valueOf(MountHealthsMax);
                MountHealthsMaxText = MountHealthsMaxText.replace(".0", "");
                guiGraphics.blit(guiIconsLocation,
                        x, y - 19,
                        88, 9,
                        9, 9,
                        256, 256); // 骑乘血量
                guiGraphics.drawString(font, MountHealthsText + "/" + MountHealthsMaxText, x + 10, y - 19, 0xEE0000, false);
            }
        } else {
            double ARMORTOUGHNESS = Math.ceil(player.getAttribute(Attributes.ARMOR_TOUGHNESS).getValue() * 10) / 10;
            String ARMORTOUGHNESSText = String.valueOf(ARMORTOUGHNESS);
            ARMORTOUGHNESSText = ARMORTOUGHNESSText.replace(".0", "");
            if (ARMORTOUGHNESS > 0) {
                guiGraphics.blit(guiIconsLocation,
                        x, y - 19,
                        43, 9,
                        9, 9,
                        256, 256); // 护甲图标
                guiGraphics.blit(guiIconsLocation,
                        x, y - 19,
                        43, 18,
                        9, 9,
                        256, 256); // 韧性图标
                guiGraphics.drawString(font, ARMORTOUGHNESSText, x + 10, y - 19, 0x87CEEB, false);
            }
        }
    }


//    private String renderMountValue(Player player) {
//
//        float MountHealths;
//        MountHealths = FMount.getHealth();
//        if (MountHealths > FMount.getMaxHealth())MountHealths=FMount.getMaxHealth();
//        if (FMount.getHealth() > 0) {
//            return MountHealths + "/" + FMount.getMaxHealth();
//        }
//        return null;
//    }


    private void renderFoodBar(Font font,GuiGraphics guiGraphics, float partialTick, int x, int y, Player player) {
        float maxFood = 20;

        float Food = player.getFoodData().getFoodLevel();
        float saturationProportion = player.getFoodData().getSaturationLevel() / maxFood;

        // Calculate bar proportions
        float FoodProportion;

        float intermediateProportion;
        if (Food < intermediateFood) {
            //FoodProportion = Food / maxFood;
            intermediateProportion = (intermediateFood - Food) / maxFood;
        } else {
            //FoodProportion = intermediateFood / maxFood;
            intermediateProportion = 0;
        }
        FoodProportion = Food / maxFood;
        //if (FoodProportion > 1) FoodProportion = 1F;
        //if (FoodProportion + intermediateProportion > 1) intermediateProportion = 1 - FoodProportion;
        int FoodWidth = (int) Math.ceil(80 * FoodProportion);
        int saturationWidth = (int) Math.ceil(80 * saturationProportion);
        int intermediateWidth = (int) Math.ceil(80 * intermediateProportion);

        // Display empty part
        guiGraphics.blit(emptyHealthBarLocation,
                x, y,
                0, 0,
                80-FoodWidth-intermediateWidth, 5,
                80, 5);

        // 饱食度
        guiGraphics.blit(currentBarLocation,
                x + 80 - FoodWidth, y,
                80 - FoodWidth, 0,
                FoodWidth, 5,
                80, 5);

        // 额外 饱和度
        guiGraphics.blit(saturationBarLocation,
                x + 80 - saturationWidth, y,
                80 - saturationWidth, 0,
                saturationWidth, 5,
                80, 5);

        // Display intermediate part
        guiGraphics.blit(intermediateHealthBarLocation,
                x + 80 - FoodWidth - intermediateWidth,y,
                80 - FoodWidth - intermediateWidth, 0,
                intermediateWidth, 5,
                80, 5);

//        // Display intermediate part
//        guiGraphics.blit(intermediateHealthBarLocation,
//                x + FoodWidth, y,
//                FoodWidth, 0,
//                intermediateWidth, 5,
//                80, 5);
//        // Display empty part
//        guiGraphics.blit(emptyHealthBarLocation,
//                x + FoodWidth + intermediateWidth, y,
//                FoodWidth + intermediateWidth, 0,
//                80 - FoodWidth - intermediateWidth, 5,
//                80, 5);
        int InsWidth = 0;
        float InsFood = 0;
        if (player.getFoodData().getSaturationLevel() > 0){
            InsWidth = (int) saturationProportion;
            InsFood = player.getFoodData().getSaturationLevel();
        } else {
            InsWidth = FoodWidth;
            InsFood = Food;
        }
        // Update intermediate health
        this.intermediateFood += (InsFood - intermediateFood) * partialTick * 0.08;
        //guiGraphics.drawString(font, intermediateFood+" - " + intermediateWidth, x, y - 69, 0x1E90FF, false);
        if (Math.abs(InsFood - intermediateFood) <= 0.25) {
            this.intermediateFood = InsFood;
        }
    }
}
