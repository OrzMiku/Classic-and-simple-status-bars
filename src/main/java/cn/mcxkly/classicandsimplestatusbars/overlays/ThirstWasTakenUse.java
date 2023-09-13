package cn.mcxkly.classicandsimplestatusbars.overlays;

import dev.ghen.thirst.Thirst;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import dev.ghen.thirst.foundation.gui.appleskin.HUDOverlayHandler;
import dev.ghen.thirst.foundation.common.capability.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ThirstWasTakenUse implements IGuiOverlay {
    private static final Minecraft mc = Minecraft.getInstance();
    public static IThirst PLAYER_THIRST = null;
    public static ResourceLocation THIRST_ICONS = new ResourceLocation(Thirst.ID, "textures/gui/thirst_icons.png");
    public static final ResourceLocation MC_ICONS = new ResourceLocation("minecraft", "textures/gui/icons.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        Player player = null;
        int y = 0;
        int x = 0;
        Font font = null;
        if (gui.shouldDrawSurvivalElements()) {
            font = gui.getFont();

            player = (Player) Minecraft.getInstance().cameraEntity;
            if (player == null) return;
            x = width / 2 + 11;
            y = height - 39;
            y += 4;
        }
        renderThirstLevelBar(font, guiGraphics, partialTick, x, y, player);

    }

    private void renderThirstLevelBar(Font font, GuiGraphics guiGraphics, float partialTick, int x, int y, Player player) {
//            guiGraphics.blit(emptyHealthBarLocation,
//                    x, y,
//                    0, 0,
//                    80, 5,
//                    80, 5);
        String text;
        if (PLAYER_THIRST == null || mc.player.tickCount % 40 == 0) {
            assert mc.player != null;
            PLAYER_THIRST = (IThirst) mc.player.getCapability(ModCapabilities.PLAYER_THIRST).orElse(null);
        }
        text = PLAYER_THIRST.getThirst() + " | " + PLAYER_THIRST.getQuenched();
        guiGraphics.drawString(font, text, x, y - 19, 0xF4A460, false);
        guiGraphics.blit(THIRST_ICONS, x, y, 0.0F, 0.0F, 9, 9, 25, 9);

        guiGraphics.blit(THIRST_ICONS, x, y, 8.0F, 0.0F, 9, 9, 25, 9);

    }
}


