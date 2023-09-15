package cn.mcxkly.classicandsimplestatusbars.overlays;

import dev.ghen.thirst.Thirst;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import dev.ghen.thirst.foundation.common.capability.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ThirstWasTakenUse implements IGuiOverlay {
    private static final Minecraft mc = Minecraft.getInstance();
    public static boolean StopConflictRendering = true;
    public static void StopConflictRenderingIDEA(boolean is){StopConflictRendering = is;};
    public static IThirst PLAYER_THIRST = null;
    public static final ResourceLocation THIRST_ICONS = new ResourceLocation(Thirst.ID, "textures/gui/thirst_icons.png");
    public static final ResourceLocation MC_ICONS = new ResourceLocation("minecraft", "textures/gui/icons.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        if (gui.shouldDrawSurvivalElements() && !StopConflictRendering) {
            Font font = gui.getFont();

            Player player = (Player) Minecraft.getInstance().cameraEntity;
            if (player == null) return;
            int x = width / 2 + 11;
            int y = height - 39;
            y += 5; //4+1

            renderThirstLevelBar(font, guiGraphics, partialTick, x, y, player);
        }
    }
    private void renderThirstLevelBar(Font font, GuiGraphics guiGraphics, float partialTick, int x, int y, Player player) {
//            guiGraphics.blit(emptyHealthBarLocation,
//                    x, y,
//                    0, 0,
//                    80, 5,
//                    80, 5);

        PLAYER_THIRST = player.getCapability(ModCapabilities.PLAYER_THIRST).orElse(null);
        if (PLAYER_THIRST == null) return;
        int Thirst = PLAYER_THIRST.getThirst();
        int Quenched = PLAYER_THIRST.getQuenched();

        guiGraphics.blit(THIRST_ICONS,
                x + 70, y - 10,
                16.0F, 0.0F,
                9, 9,
                25, 9);

        if (Quenched > 0){ // 如果Quenched大于0渲染.
            int x2 = x + 70 - font.width(Quenched + "+") - font.width(String.valueOf(Thirst)); // 计算长度
            // guiGraphics.blit(THIRST_ICONS, x, y, 0.0F, 0.0F, 9, 9, 25, 9);
            guiGraphics.drawString(font, Quenched + "+", x2, y - 9, 0x48D1CC, false);
        }
        font.width("0.3");
        guiGraphics.drawString(font, String.valueOf(Thirst), x + 70 - font.width(String.valueOf(Thirst)), y - 9, 0x4876FF, false);
    }
}


