package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import cn.mcxkly.classicandsimplestatusbars.other.helper;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
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
    public static boolean isUseSeparateIcons = false;
    private static final Identifier food_empty = new Identifier("minecraft", "textures/gui/sprites/hud/food_empty.png");
    private static final Identifier food_full = new Identifier("minecraft", "textures/gui/sprites/hud/food_full.png");
    private static final Identifier air = new Identifier("minecraft", "textures/gui/sprites/hud/air.png");
    private static final Identifier vehicle_full = new Identifier("minecraft", "textures/gui/sprites/hud/heart/vehicle_full.png");
    private static final Identifier armor_toughness = new Identifier(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/armor_toughness.png"); // The official deleted it, and I can only do this
    private static final Identifier armor_full = new Identifier("minecraft", "textures/gui/sprites/hud/armor_full.png");
    public static boolean StopConflictRendering = true; // 支持 脱水Mod
    public static void isUseSeparateIconsIDEA(boolean is) {
        isUseSeparateIcons = is;
    }
    public static void StopConflictRenderingIDEA(boolean is) {
        StopConflictRendering = is;
    }
    private float intermediateFood = 0;
    public void render(DrawContext guiGraphics, float partialTick) {
        if (mc.cameraEntity instanceof PlayerEntity player
                && !mc.options.hudHidden
                && mc.interactionManager != null && mc.interactionManager.hasStatusBars()) {
            int width = mc.getWindow().getScaledWidth();
            int height = mc.getWindow().getScaledHeight();
            float x = (float) width / 2 + 11;
            float y = height - 39;
            y += 4;
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
        y += 1;
        if (isUseSeparateIcons) {
            context.drawTexture(food_empty,
                    x, y - 10,
                    0, 0,
                    9, 9,
                    9, 9); // 鸡腿图标-背景
            context.drawTexture(food_full,
                    x, y - 10,
                    0, 0,
                    9, 9,
                    9, 9); // 鸡腿图标
        } else {
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
        }
        String text = helper.KeepOneDecimal(FoodData.getFoodLevel());
        int xx = x + 10;
        context.drawText(font, text, xx, y - 9, 0xF4A460, false);
        if (FoodData.getSaturationLevel() > 0) {
            //第二部分
            xx = xx + font.getWidth(text);
            text = "+" + helper.KeepOneDecimal(FoodData.getSaturationLevel());
            context.drawText(font, text, xx, y - 9, 0xEEEE00, false);
        }
        if (player.getAir() < 300) { // max=300
            int siz = player.getAir() / 3;
            siz = Math.max(siz, 0); //防止负数
            text = String.valueOf(siz);
            int Y2 = y;
            if (!StopConflictRendering) Y2 -= 10; // 如果口渴存在，在渲染时高度 + 10
            context.drawText(font, "%", x + 70 - font.getWidth("%"), Y2 - 9, 0x1E90FF, false);
            context.drawText(font, text, x + 70 - font.getWidth("99%"), Y2 - 9, 0x1E90FF, false);
            if (isUseSeparateIcons) {
                context.drawTexture(air,
                        x + 70, Y2 - 10,
                        0, 0,
                        9, 9,
                        9, 9); // 气泡图标
            } else {
                context.drawTexture(guiIconsLocation,
                        x + 70, Y2 - 10,
                        16, 18,
                        9, 9,
                        256, 256); // 气泡图标
            }
        }
        Entity tsssmp = player.getVehicle();
        if (tsssmp != null) {
            if (tsssmp.getType() == EntityType.SKELETON_HORSE ||
                    tsssmp.getType() == EntityType.PIG ||
                    tsssmp.getType() == EntityType.HORSE ||
                    tsssmp.getType() == EntityType.CAMEL ||
                    tsssmp.getType() == EntityType.MULE ||
                    tsssmp.getType() == EntityType.STRIDER ||
                    tsssmp.getType() == EntityType.TRADER_LLAMA
            ) {
                LivingEntity FsMount = (LivingEntity) tsssmp;
                float MountHealthsMax = FsMount.getMaxHealth();
                float MountHealths = Math.min(FsMount.getHealth(), MountHealthsMax);
                if (MountHealths > 0) {
                    if (isUseSeparateIcons) {
                        context.drawTexture(vehicle_full,
                                x, y - 19,
                                0, 0,
                                9, 9,
                                9, 9); // 骑乘血量
                    } else {
                        context.drawTexture(guiIconsLocation,
                                x, y - 19,
                                88, 9,
                                9, 9,
                                256, 256); // 骑乘血量
                    }
                        context.drawText(font, helper.KeepOneDecimal(MountHealths) + "/" + helper.KeepOneDecimal(MountHealthsMax), x + 10, y - 19, 0xEE0000, false);
                }
            }
        } else {
            float ARMORTOUGHNESS = (float) player.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
            if (ARMORTOUGHNESS > 0) {
                if (isUseSeparateIcons) {
                    context.drawTexture(armor_full,
                            x, y - 19,
                            0, 0,
                            9, 9,
                            9, 9); // 韧性图标
                    context.drawTexture(armor_toughness,
                            x, y - 19,
                            0, 0,
                            9, 9,
                            9, 9); // 背景图标 - 覆盖在上面的图标
                } else {
                    context.drawTexture(guiIconsLocation,
                            x, y - 19,
                            43, 9,
                            9, 9,
                            256, 256); // 背景图标
                    context.drawTexture(guiIconsLocation,
                            x, y - 19,
                            43, 18,
                            9, 9,
                            256, 256); // 韧性图标
                }
                context.drawText(font, helper.KeepOneDecimal(ARMORTOUGHNESS), x + 10, y - 19, 0x87CEEB, false);
            }
        }
    }

    private void renderFoodBar(DrawContext context, float partialTick, int x, int y, PlayerEntity player, HungerManager FoodData) {
        //float maxFood = 20; // 不，不能这样用。
        float maxFood = FoodData.getPrevFoodLevel();
        float Food = Math.min(FoodData.getFoodLevel(), maxFood);
        float saturationProportion = FoodData.getSaturationLevel() / maxFood;

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
        context.drawTexture(emptyHealthBarLocation,
                x, y,
                0, 0,
                80 - FoodWidth - intermediateWidth, 5,
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
                x + 80 - FoodWidth - intermediateWidth, y,
                80 - FoodWidth - intermediateWidth, 0,
                intermediateWidth, 5,
                80, 5);
        int InsWidth = 0;
        float InsFood = 0;
        if (FoodData.getSaturationLevel() > 0) {
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