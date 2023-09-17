package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class FoodBar {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final Identifier fullHealthBarLocation = new Identifier(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/foodeeg.png");
    private static final Identifier emptyHealthBarLocation = new Identifier(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/empty.png");
    private static final Identifier saturationBarLocation = new Identifier(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/saturation.png");
    private Identifier currentBarLocation = fullHealthBarLocation;
    private static final Identifier emmmmnBarLocation = new Identifier(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/debuff-hunger.png");
    private static final Identifier intermediateHealthBarLocation = new Identifier(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/intermediate.png");
    private static final Identifier guiIconsLocation = new Identifier("minecraft", "textures/gui/icons.png");
    private float intermediateFood = 0;
//    public static boolean StopConflictRendering = true; // 支持口渴，目前没有fabric
//    public static void StopConflictRenderingIDEA(boolean is){StopConflictRendering = is;};

    public void render(DrawContext guiGraphics, float partialTick) {
        if (mc.cameraEntity instanceof PlayerEntity player
                && !mc.options.hudHidden
                && mc.interactionManager != null && mc.interactionManager.hasStatusBars()) {
            int width = mc.getWindow().getScaledWidth();
            int height = mc.getWindow().getScaledHeight();
            float x = (float) width / 2 + 11;
            float y = height - 39;
            y += 4;
            //y -= 70; // test

            HungerManager FoodData = player.getHungerManager();
            TextRenderer font = mc.textRenderer;
            updateBarTextures(player);
            renderFoodBar(guiGraphics, partialTick, (int) x, (int) y, player, FoodData);
            renderFoodValue(font, guiGraphics, (int) x, (int) y, player, FoodData);
        }
    }
    public void updateBarTextures(PlayerEntity player) {
        if (player.hasStatusEffect(StatusEffects.HUNGER)) {
            currentBarLocation = emmmmnBarLocation;
        } else {
            currentBarLocation = fullHealthBarLocation;
        }
    }

    private void renderFoodValue(TextRenderer font, DrawContext context, int x, int y, PlayerEntity player, HungerManager FoodData) {
        //double food = Math.ceil(FoodData.getSaturationLevel() * 10) / 10;
        // getSaturationLevel饱食条 | getFoodLevel饥饿度 |  getLastFoodLevel饥饿最大值 | FoodData.getExhaustionLevel(); 消耗度
        //String text = "getSaturationLevel: " + FoodData.getSaturationLevel() + " | " + "getFoodLevel: " + FoodData.getFoodLevel() + " | " + "getLastFoodLevel: " + FoodData.getLastFoodLevel();
        //text = text.replace(".0", ""); ARMOR_TOUGHNESS
        y += 1;
        context.drawTexture(guiIconsLocation,
                x, y - 10,
                16, 27,
                9, 9,
                256, 256); // 鸡腿图标-背景
        context.drawTexture(guiIconsLocation,
                x, y - 10,
                52, 27,
                9, 9,
                256, 256); // 鸡腿图标
        if (FoodData.getSaturationLevel() > 0) {
            //第一部分
            double food = Math.ceil(FoodData.getFoodLevel() * 10) / 10; // 饥饿度
            String text = String.valueOf(food);
            text = text.replace(".0", "");
            int xx = x + 10;
            context.drawText(font, text, xx, y - 9, 0xF4A460, false);
            //第二部分
            xx = xx + font.getWidth(text);
            food = Math.ceil(FoodData.getSaturationLevel() * 10) / 10; // 饱食度
            text = "+" + food;
            text = text.replace(".0", "");
            context.drawText(font, text, xx, y - 9, 0xEEEE00, false);


        } else {
            double food = Math.ceil(FoodData.getFoodLevel() * 10) / 10;
            String text = String.valueOf(food);
            text = text.replace(".0", "");
            context.drawText(font, text, x + 10, y - 9, 0xF4A460, false);
        }

        if (player.getAir() < 300) { // max=300
            int siz = player.getAir() / 3;
            String text;
            siz = Math.max(siz, 0); //防止负数
            text = String.valueOf(siz);
            int Y2 = y;
//            if (!StopConflictRendering) Y2 -= 10; // 如果口渴存在，在渲染时高度 + 10

            context.drawText(font, "%", x + 70 - font.getWidth("%"), Y2 - 9, 0x1E90FF, false);
            context.drawText(font, text, x + 70 - font.getWidth("99%"), Y2 - 9, 0x1E90FF, false);
            context.drawTexture(guiIconsLocation,
                    x + 70, Y2 - 10,
                    16, 18,
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
                context.drawTexture(guiIconsLocation,
                        x, y - 19,
                        88, 9,
                        9, 9,
                        256, 256); // 骑乘血量
                context.drawText(font, MountHealthsText + "/" + MountHealthsMaxText, x + 10, y - 19, 0xEE0000, false);
            }
        } else {
            double ARMORTOUGHNESS = Math.ceil((float)player.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS) * 10) / 10;
            //double ARMORTOUGHNESS = 1.7;
            String ARMORTOUGHNESSText = String.valueOf(ARMORTOUGHNESS);
            ARMORTOUGHNESSText = ARMORTOUGHNESSText.replace(".0", "");
            if (ARMORTOUGHNESS > 0) {
                context.drawTexture(guiIconsLocation,
                        x, y - 19,
                        43, 9,
                        9, 9,
                        256, 256); // 护甲图标
                context.drawTexture(guiIconsLocation,
                        x, y - 19,
                        43, 18,
                        9, 9,
                        256, 256); // 韧性图标
                context.drawText(font, ARMORTOUGHNESSText, x + 10, y - 19, 0x87CEEB, false);
            }
        }
    }

    private void renderFoodBar(DrawContext context, float partialTick, int x, int y, PlayerEntity player, HungerManager FoodData) {
        //float maxFood = 20; // 不，不能这样用。
        float maxFood = FoodData.getPrevFoodLevel();

        float Food = FoodData.getFoodLevel();
        float saturationProportion = FoodData.getSaturationLevel() / maxFood;

        // Calculate bar proportions
        float FoodProportion;
        if (Food > maxFood)maxFood = Food;
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
        context.drawTexture(emptyHealthBarLocation,
                x, y,
                0, 0,
                80-FoodWidth-intermediateWidth, 5,
                80, 5);

        // 饱食度
        context.drawTexture(currentBarLocation,
                x + 80 - FoodWidth, y,
                80 - FoodWidth, 0,
                FoodWidth, 5,
                80, 5);

        // 额外 饱和度
        context.drawTexture(saturationBarLocation,
                x + 80 - saturationWidth, y,
                80 - saturationWidth, 0,
                saturationWidth, 5,
                80, 5);

        // Display intermediate part
        context.drawTexture(intermediateHealthBarLocation,
                x + 80 - FoodWidth - intermediateWidth,y,
                80 - FoodWidth - intermediateWidth, 0,
                intermediateWidth, 5,
                80, 5);

//        // Display intermediate part
//        context.drawTexture(intermediateHealthBarLocation,
//                x + FoodWidth, y,
//                FoodWidth, 0,
//                intermediateWidth, 5,
//                80, 5);
//        // Display empty part
//        context.drawTexture(emptyHealthBarLocation,
//                x + FoodWidth + intermediateWidth, y,
//                FoodWidth + intermediateWidth, 0,
//                80 - FoodWidth - intermediateWidth, 5,
//                80, 5);
        int InsWidth = 0;
        float InsFood = 0;
        if (FoodData.getSaturationLevel() > 0){
            InsWidth = (int) saturationProportion;
            InsFood = FoodData.getSaturationLevel();
        } else {
            InsWidth = FoodWidth;
            InsFood = Food;
        }
        // Update intermediate health
        this.intermediateFood += (InsFood - intermediateFood) * partialTick * 0.08;
        //context.drawText(font, intermediateFood+" - " + intermediateWidth, x, y - 69, 0x1E90FF, false);
        if (Math.abs(InsFood - intermediateFood) <= 0.25) {
            this.intermediateFood = InsFood;
        }
    }
}