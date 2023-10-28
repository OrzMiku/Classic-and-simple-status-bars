package cn.mcxkly.classicandsimplestatusbars.overlays;

import cn.mcxkly.classicandsimplestatusbars.Config;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import dev.ghen.thirst.foundation.common.capability.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import toughasnails.api.thirst.ThirstHelper;

public class ThirstWasTakenUse implements IGuiOverlay {
    public static boolean StopConflictRendering = true;

    public static void StopConflictRenderingIDEA(boolean is) {
        StopConflictRendering = is;
    }

    public static boolean toughasnailsIS = true; // 支持意志坚定
    public static final ResourceLocation toughasnails_incos = new ResourceLocation("toughasnails:textures/gui/icons.png");

    public static void toughasnailsIDEA(boolean is) {
        toughasnailsIS = is;
    }
    public static final ResourceLocation THIRST_ICONS = new ResourceLocation("thirst:textures/gui/thirst_icons.png");

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
        int Quenched = 0;
        int Thirst = 0;
        if ( !StopConflictRendering ) { // 如果口渴存在，不管意志坚定是否存在.
            IThirst Play_THIRST = player.getCapability(ModCapabilities.PLAYER_THIRST).orElse(null);
            Thirst = Play_THIRST.getThirst();
            Quenched = Play_THIRST.getQuenched();
            guiGraphics.blit(THIRST_ICONS,
                    x + 70, y - 10,
                    16.0F, 0.0F,
                    9, 9,
                    25, 9);
        } else if ( !toughasnailsIS ) { // 否则 如果意志坚定是存在的.
            toughasnails.api.thirst.IThirst thirst = ThirstHelper.getThirst(player);
            Thirst = thirst.getThirst();
            Quenched = (int) thirst.getHydration();
            guiGraphics.blit(toughasnails_incos,
                    x + 70, y - 10,
                    0, 41,
                    9, 9);
        } else {
            return; // 如果两者都不在，跳过渲染.
        }
        if ( Quenched > 0 ) { // 如果Quenched大于0渲染.
            int x2 = x + 70 - font.width(Quenched + Config.Interval_TTT) - font.width(String.valueOf(Thirst)); // 计算长度
            // guiGraphics.blit(THIRST_ICONS, x, y, 0.0F, 0.0F, 9, 9, 25, 9);
            guiGraphics.drawString(font, Quenched + Config.Interval_TTT, x2, y - 9, 0x48D1CC, false);
        }
        font.width("0.3");
        guiGraphics.drawString(font, String.valueOf(Thirst), x + 70 - font.width(String.valueOf(Thirst)), y - 9, 0x4876FF, false);
    }
}


