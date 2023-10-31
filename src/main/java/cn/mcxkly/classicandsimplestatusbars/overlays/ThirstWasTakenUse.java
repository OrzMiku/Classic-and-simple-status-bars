package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.Config;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import dev.ghen.thirst.foundation.common.capability.ModCapabilities;
import homeostatic.common.capabilities.CapabilityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import toughasnails.api.thirst.ThirstHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class ThirstWasTakenUse implements IGuiOverlay {
    public static boolean StopConflictRendering = true; // 口渴

    public static void StopConflictRenderingIDEA(boolean is) {
        StopConflictRendering = is;
    }

    public static final ResourceLocation THIRST_ICONS = new ResourceLocation("thirst:textures/gui/thirst_icons.png");

    public static boolean toughasnailsIS = true; // 支持意志坚定

    public static void toughasnailsIDEA(boolean is) {
        toughasnailsIS = is;
    }

    public static final ResourceLocation Toughasnails_Icons = new ResourceLocation("toughasnails:textures/gui/icons.png");


    public static boolean HomeostaticIS = true; // 稳态

    public static void HomeostaticIDEA(boolean is) {
        HomeostaticIS = is;
    }

    public static final ResourceLocation Homeostatic_Icons = new ResourceLocation("homeostatic:textures/gui/icons.png");


    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        Font font = gui.getFont();
        if ( gui.shouldDrawSurvivalElements() ) {
            Player player = (Player) Minecraft.getInstance().cameraEntity;
            if ( player == null ) return;
            int x = width / 2 + 11;
            int y = height - 39;
            y += 5; //4+1
            if ( Config.All_On ) {
                renderThirstLevelBar(font, guiGraphics, partialTick, x, y, player);
            }
        }
    }

    private void renderThirstLevelBar(Font font, GuiGraphics guiGraphics, float partialTick, int x, int y, Player player) {
        AtomicInteger Quenched = new AtomicInteger();
        AtomicInteger Thirst = new AtomicInteger();
        if ( !StopConflictRendering ) { // 如果口渴存在，不管意志坚定是否存在.
            IThirst Play_THIRST = player.getCapability(ModCapabilities.PLAYER_THIRST).orElse(null);
            Thirst.set(Play_THIRST.getThirst());
            Quenched.set(Play_THIRST.getQuenched());
            guiGraphics.blit(THIRST_ICONS,
                    x + 70, y - 10,
                    16.0F, 0.0F,
                    9, 9,
                    25, 9);
        } else if ( !toughasnailsIS ) { // 否则 如果意志坚定是存在的.
            toughasnails.api.thirst.IThirst thirst = ThirstHelper.getThirst(player);
            Thirst.set(thirst.getThirst());
            Quenched.set((int) thirst.getHydration());
            guiGraphics.blit(Toughasnails_Icons,
                    x + 70, y - 10,
                    0, 41,
                    9, 9);
        } else if ( !HomeostaticIS ) { // 优先级最低，迫不得已.
            player.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent((data) -> {
                RenderSystem.enableBlend();
                Thirst.set(data.getWaterLevel());
                Quenched.set((int) data.getWaterSaturationLevel());
                // 底图
                guiGraphics.blit(Homeostatic_Icons,
                        x + 70, y - 10,
                        0, 0,
                        9, 9);
                // 附加1
                guiGraphics.blit(Homeostatic_Icons,
                        x + 70, y - 10,
                        9, 0,
                        9, 9);
                // 附加2
                guiGraphics.blit(Homeostatic_Icons,
                        x + 70, y - 10,
                        0, 9,
                        9, 9);
            });
        } else return; // 如果两者都不在，并且也没有稳态，跳过渲染.
        if ( Quenched.get() > 0 ) { // 如果Quenched大于0渲染.
            int x2 = x + 70 - font.width(Quenched + Config.Interval_TTT) - font.width(String.valueOf(Thirst.get())); // 计算长度
            guiGraphics.drawString(font, Quenched + "", x2, y - 9, Config.Color_Thirst_Quenched, false);
            x2 += font.width(Quenched + "");
            guiGraphics.drawString(font, Config.Interval_TTT, x2, y - 9, Config.Color_Interval_TTT, false);
        }
        font.width("0.3");
        guiGraphics.drawString(font, String.valueOf(Thirst.get()), x + 70 - font.width(String.valueOf(Thirst.get())), y - 9, Config.Color_Thirst, false);
    }
}


