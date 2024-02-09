package cn.mcxkly.classicandsimplestatusbars.overlays;

/*import arcaios26.supersaturation.data.CapabilitySuperSat;
import artifacts.component.SwimData;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModGameRules;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.util.Helper;*/
import cn.mcxkly.classicandsimplestatusbars.ClassicAndSimpleStatusBars;
import cn.mcxkly.classicandsimplestatusbars.Config;
import cn.mcxkly.classicandsimplestatusbars.other.helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class FoodLevel implements IGuiOverlay {
    private final ResourceLocation Vampires_Icons = new ResourceLocation("vampirism", "textures/gui/icons.png");
    private static final ResourceLocation vampiresBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/vampires.png");
    private static final ResourceLocation fullHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/foodeeg.png");
    private static final ResourceLocation emptyHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/empty.png");
    private static final ResourceLocation saturationBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/saturation.png");
    private ResourceLocation currentBarLocation = fullHealthBarLocation;
    private static final ResourceLocation emmmmnBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/debuff-hunger.png");
    private static final ResourceLocation intermediateHealthBarLocation = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/intermediate.png");

    private static final ResourceLocation food_empty = new ResourceLocation("minecraft", "textures/gui/sprites/hud/food_empty.png");
    private static final ResourceLocation food_full = new ResourceLocation("minecraft", "textures/gui/sprites/hud/food_full.png");
    private static final ResourceLocation air = new ResourceLocation("minecraft", "textures/gui/sprites/hud/air.png");
    private static final ResourceLocation vehicle_full = new ResourceLocation("minecraft", "textures/gui/sprites/hud/heart/vehicle_full.png");
    private static final ResourceLocation armor_toughness = new ResourceLocation(ClassicAndSimpleStatusBars.MOD_ID, "textures/gui/foodbars/armor_toughness.png"); // The official deleted it, and I can only do this
    private static final ResourceLocation armor_full = new ResourceLocation("minecraft", "textures/gui/sprites/hud/armor_full.png");
    private float intermediateFood = 0;
    public static boolean StopConflictRendering = true; // 如果 false ，抬高渲染，为需要水分的模组兼容.

    public static void StopConflictRenderingIDEA(boolean is) {
        StopConflictRendering = is;
    }

    public static boolean ArtifactsAir = false; // 奇异饰品-火烈鸟
    private static final ResourceLocation HELIUM_FLAMINGO_ICON = new ResourceLocation("artifacts", "textures/gui/icons.png");

    public static void ArtifactsIDEA(boolean b) {
        ArtifactsAir = b;
    }

    @Override
    public void render(ExtendedGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        if ( gui.shouldDrawSurvivalElements() ) {
            Font font = gui.getFont();

            Player player = (Player) Minecraft.getInstance().cameraEntity;
            if ( player == null ) return;
            int x = width / 2 + 11;
            int y = height - 39;
            y += 4;
            //y -= 70; // test
            if ( Config.Food_On && Config.All_On ) {
                updateBarTextures(player);
                // 其他元素
                renderFoodValue(font, guiGraphics, x, y, player);
/*                if ( ClassicAndSimpleStatusBars.vampirism && Helper.isVampire(player) ) {
                    // 如果当前是吸血鬼.
                    if ( Config.Bloodsucker_On ) {
                        // 如果开启功能，将渲染血条,否则仅把鸡腿饱食度文本替换成吸血鬼血液文本
                        renderVampiresBar(guiGraphics, partialTick, x, y, player);
                    }
                    // 吸血鬼血液文本
                    renderInfectedVampires(font, guiGraphics, x, y, player);
                } else {*/
                    // 文本
                    renderFood(font, guiGraphics, x, y, player);
                    // 状态栏图
                    renderFoodBar(guiGraphics, partialTick, x, y, player);
                }
/*            } else if ( Config.EasyMode_Text_On ) {
                renderFoodValue_Easy(font, guiGraphics, x, y, player);
            }*/
        }
    }

    /*private void renderFoodValue_Easy(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        AtomicReference<Integer> AddedHunger = new AtomicReference<>(0);

        AtomicReference<Float> AddedSat = new AtomicReference<>(0.0f);
        if ( Config.supersaturation_On ) {
            player.getCapability(CapabilitySuperSat.SUPER_SAT, (Direction) null).ifPresent((c) -> {
                AddedHunger.set(c.getHunger());
                AddedSat.set(c.getSat());
            });
        }

        y -= 2;
        String text;
        text = helper.KeepOneDecimal(player.getFoodData().getFoodLevel() + AddedHunger.get());
        int xx = x + 82; // 右侧
        guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Food, false);
        if ( player.getFoodData().getSaturationLevel() + AddedSat.get() > 0 ) {
            //第二部分
            xx = xx + font.width(text); // '+'
            text = Config.Interval_TTT;
            guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Interval_TTT, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(player.getFoodData().getSaturationLevel() + AddedSat.get());
            guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Food_Saturation, false);
        }
        boolean OnMaxFood = (Config.MaxFood_On == 1) || (Config.MaxFood_On == 0 && player.getFoodData().getLastFoodLevel() > 20);
        if ( OnMaxFood ) {
            //第三部分  Max
            xx = xx + font.width(text); // '/'
            text = Config.Interval_lll;
            guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Interval_lll, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(player.getFoodData().getLastFoodLevel());
            guiGraphics.drawString(font, text, xx, y - 1, Config.Color_Food_Tail, false);
        }
    }

    float intermediate = 0;

    private void renderVampiresBar(GuiGraphics guiGraphics, float partialTick, int x, int y, Player player) {
        VampirePlayer.getOpt(player).map(VampirePlayer :: getBloodStats).ifPresent((stats) -> {
            float BloodValue = stats.getBloodLevel();
            float maxBlood = stats.getMaxBlood();
            float BloodProportion = BloodValue / maxBlood;
            float Proportion;
            float intermediateProportion;

            if ( BloodValue < intermediate ) {
                intermediateProportion = (intermediate - BloodValue) / maxBlood;
            } else {
                intermediateProportion = 0;
            }
            Proportion = BloodValue / maxBlood;
            int Width = (int) Math.ceil(80 * Proportion);
            int saturationWidth = (int) Math.ceil(80 * BloodProportion);
            int intermediateWidth = (int) Math.ceil(80 * intermediateProportion);

            // Display empty part
            guiGraphics.blit(emptyHealthBarLocation,
                    x, y,
                    0, 0,
                    80 - Width - intermediateWidth, 5,
                    80, 5);

            // 血条
            guiGraphics.blit(vampiresBarLocation,
                    x + 80 - Width, y,
                    80 - Width, 0,
                    Width, 5,
                    80, 5);

            // Display intermediate part
            guiGraphics.blit(intermediateHealthBarLocation,
                    x + 80 - Width - intermediateWidth, y,
                    80 - Width - intermediateWidth, 0,
                    intermediateWidth, 5,
                    80, 5);

            // Update intermediate health
            intermediate += (BloodValue - intermediate) * partialTick * 0.08;
            if ( Math.abs(BloodValue - intermediate) <= 0.25 ) {
                intermediate = BloodValue;
            }
        });
    }

    private void renderInfectedVampires(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        y += 1;
        int finalY = y;
        VampirePlayer.getOpt(player).map(VampirePlayer :: getBloodStats).ifPresent((stats) -> {
            String text;
            int blood = stats.getBloodLevel();
            int maxBlood = stats.getMaxBlood();
            guiGraphics.blit(Vampires_Icons,
                    x, finalY - 10,
                    9, 9,
                    9, 9); // 血液
            text = helper.KeepOneDecimal(blood);
            int xx = x + 10;
            guiGraphics.drawString(font, text, xx, finalY - 9, Config.Color_Vampires_Blood, false);
            xx = xx + font.width(text);
            text = Config.Interval_lll;
            guiGraphics.drawString(font, text, xx, finalY - 9, Config.Color_Interval_lll, false);
            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(maxBlood);
            guiGraphics.drawString(font, text, xx, finalY - 9, Config.Color_Vampires_MaxBlood, false);
        });
    }*/

    public void updateBarTextures(Player player) {
        if ( player.hasEffect(MobEffects.HUNGER) ) {
            currentBarLocation = emmmmnBarLocation;
        } else {
            currentBarLocation = fullHealthBarLocation;
        }
    }

    private void renderFood(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        y += 1;
        String text;
        guiGraphics.blit(food_empty,
                x, y - 10,
                0, 0,
                9, 9,
                9, 9); // 鸡腿图标-背景
        guiGraphics.blit(food_full,
                x, y - 10,
                0, 0,
                9, 9,
                9, 9); // 鸡腿图标

        AtomicReference<Integer> AddedHunger = new AtomicReference<>(0);

        AtomicReference<Float> AddedSat = new AtomicReference<>(0.0f);
/*        if ( Config.supersaturation_On ) {
            player.getCapability(CapabilitySuperSat.SUPER_SAT, (Direction) null).ifPresent((c) -> {
                AddedHunger.set(c.getHunger());
                AddedSat.set(c.getSat());
            });
        }*/

        text = helper.KeepOneDecimal(player.getFoodData().getFoodLevel() + AddedHunger.get());
        int xx = x + 10;
        guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Food, false);
        if ( player.getFoodData().getSaturationLevel() + AddedSat.get() > 0 ) {
            //第二部分
            xx = xx + font.width(text); // '+'
            text = Config.Interval_TTT;
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Interval_TTT, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(player.getFoodData().getSaturationLevel() + AddedSat.get());
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Food_Saturation, false);
        }
        boolean OnMaxFood = (Config.MaxFood_On == 1) || (Config.MaxFood_On == 0 && player.getFoodData().getLastFoodLevel() > 20);
        if ( OnMaxFood ) {
            //第三部分  Max
            xx = xx + font.width(text); // '/'
            text = Config.Interval_lll;
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Interval_lll, false);

            xx = xx + font.width(text);
            text = helper.KeepOneDecimal(player.getFoodData().getLastFoodLevel());
            guiGraphics.drawString(font, text, xx, y - 9, Config.Color_Food_Tail, false);
        }
    }

    private void renderFoodValue(Font font, GuiGraphics guiGraphics, int x, int y, Player player) {
        // getSaturationLevel饱食条 | getFoodLevel饥饿度 |  getLastFoodLevel饥饿最大值 | player.getFoodData().getExhaustionLevel(); 消耗度
        y += 1;
        String text;
        if ( Config.Air_On && player.getAirSupply() < 300 ) { // max=300
            int siz = player.getAirSupply() / 3;
            siz = Math.max(siz, 0); //防止负数
            text = String.valueOf(siz);
            int y2 = y;
            if ( !StopConflictRendering ) y2 -= 10; // 如果口渴/意志坚定存在，在渲染时高度 + 10
            guiGraphics.drawString(font, "%", x + 70 - font.width("%"), y2 - 9, Config.Color_Air_Symbol, false);

            guiGraphics.drawString(font, text, x + 70 - font.width(text) - font.width("%"), y2 - 9, Config.Color_Air, false);
            guiGraphics.blit(air,
                    x + 70, y2 - 10,
                    0, 0,
                    9, 9,
                    9, 9); // 气泡图标
        }
/*        if ( ArtifactsAir && Config.Artifacts_On ) {
            SwimData swimData = PlatformServices.platformHelper.getSwimData(player);
            if ( swimData == null ) {
            } else {
                int swimTime = swimData.getSwimTime();
                int maxProgressTime;
                if ( swimTime != 0 ) {
                    int AirY = y;
                    if ( Config.Air_On && player.getAirSupply() < 300 ) AirY -= 10; // 如果渲染了氧气值，在渲染时高度 + 10
                    if ( !StopConflictRendering ) AirY -= 10; // 如果口渴/意志坚定存在，在渲染时高度 + 10
                    if ( swimTime > 0 ) {
                        maxProgressTime = Math.max(1, ModGameRules.HELIUM_FLAMINGO_FLIGHT_DURATION.get() * 20);
                    } else {
                        maxProgressTime = Math.max(1, ModGameRules.HELIUM_FLAMINGO_RECHARGE_DURATION.get() * 20);
                    }
                    int swimTimes = swimTime * 100 / maxProgressTime;
                    swimTimes = (swimTimes <= 0 ? -1 : 100 - swimTimes);
                    String texts = Math.max(swimTimes, 0) + ""; //防止负数
                    guiGraphics.drawString(font, "%", x + 70 - font.width("%"), AirY - 9, Config.Color_Artifacts_Symbol, false);
                    guiGraphics.drawString(font, texts, x + 70 - font.width(texts) - font.width("%"), AirY - 9, Config.Color_Artifacts, false);
                    guiGraphics.blit(HELIUM_FLAMINGO_ICON,
                            x + 70, AirY - 10,
                            (swimTimes < 0 ? 9 : 0), 0,
                            9, 9,
                            32, 16); // 烈火鸟 泳圈
                }
            }
        }*/
        Entity tsssmp = player.getVehicle();
        Boolean NotAValidMount = false; // 防止玩家正在乘坐，但并不是有效的坐骑（比如船）.
        if ( tsssmp != null ) {
            if ( tsssmp.getType() == EntityType.SKELETON_HORSE ||
                    tsssmp.getType() == EntityType.PIG ||
                    tsssmp.getType() == EntityType.HORSE ||
                    tsssmp.getType() == EntityType.CAMEL ||
                    tsssmp.getType() == EntityType.MULE ||
                    tsssmp.getType() == EntityType.STRIDER ||
                    tsssmp.getType() == EntityType.TRADER_LLAMA ) {
                LivingEntity FsMount = (LivingEntity) tsssmp;
                float MountHealthsMax = FsMount.getMaxHealth();
                float MountHealths = Math.min(FsMount.getHealth(), MountHealthsMax);
                if ( MountHealths > 0 ) {
                    guiGraphics.blit(vehicle_full,
                            x, y - 19,
                            0, 0,
                            9, 9,
                            9, 9); // 骑乘血量
                    // 骑乘血量
                    String text_Mount = helper.KeepOneDecimal(MountHealths);
                    int X_Mount = x + 10;
                    guiGraphics.drawString(font, text_Mount, X_Mount, y - 19, Config.Color_Health, false);
                    X_Mount += font.width(text_Mount);
                    text_Mount = Config.Interval_lll;
                    guiGraphics.drawString(font, text_Mount, X_Mount, y - 19, Config.Color_Interval_lll, false);
                    X_Mount += font.width(text_Mount);
                    text_Mount = helper.KeepOneDecimal(MountHealthsMax);
                    guiGraphics.drawString(font, text_Mount, X_Mount, y - 19, Config.Color_Health_Tail, false);
                }
                NotAValidMount = true;
            }
        }
        if ( !NotAValidMount ) {
            if ( Config.Armor_Toughness_On ) {
                float ARMORTOUGHNESS = (float) Objects.requireNonNull(player.getAttribute(Attributes.ARMOR_TOUGHNESS)).getValue();
                if ( ARMORTOUGHNESS > 0 ) {
                    guiGraphics.blit(armor_full,
                            x, y - 19,
                            0, 0,
                            9, 9,
                            9, 9); // 韧性图标
                    guiGraphics.blit(armor_toughness,
                            x, y - 19,
                            0, 0,
                            9, 9,
                            9, 9); // 背景图标 - 覆盖在上面的图标
                    guiGraphics.drawString(font, helper.KeepOneDecimal(ARMORTOUGHNESS), x + 10, y - 19, Config.Color_Armor_Toughness, false);
                }
            }
        }
    }

    private void renderFoodBar(GuiGraphics guiGraphics, float partialTick, int x, int y, Player player) {
        //float maxFood = 20; // 不，不能这样用。
        float maxFood = player.getFoodData().getLastFoodLevel();

        float Food = Math.min(player.getFoodData().getFoodLevel(), maxFood);
        float saturationProportion = player.getFoodData().getSaturationLevel() / maxFood;

        // Calculate bar proportions
        float FoodProportion;
        float intermediateProportion;
        if ( Food < intermediateFood ) {
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
                80 - FoodWidth - intermediateWidth, 5,
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
                x + 80 - FoodWidth - intermediateWidth, y,
                80 - FoodWidth - intermediateWidth, 0,
                intermediateWidth, 5,
                80, 5);

        // 疲劳值
        if ( Config.Food_ExhaustionLevel_On ) {
            int ExhaustionLevel = Math.max(1, (int) ((4f - player.getFoodData().getExhaustionLevel()) * 78f) / 4);
            guiGraphics.hLine(x + 79 - ExhaustionLevel, x + 78, y + 4, Config.Color_Food_ExhaustionLevel);
        }

        float InsFood;
        if ( player.getFoodData().getSaturationLevel() > 0 ) {
            InsFood = player.getFoodData().getSaturationLevel();
        } else {
            InsFood = Food;
        }
        // Update intermediate health
        this.intermediateFood += (InsFood - intermediateFood) * partialTick * 0.08;
        if ( Math.abs(InsFood - intermediateFood) <= 0.25 ) {
            this.intermediateFood = InsFood;
        }
    }
}
