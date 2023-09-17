package cn.mcxkly.classicandsimplestatusbars.overlays;


import net.dehydration.access.ThirstManagerAccess;
import net.dehydration.thirst.ThirstManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import  cn.mcxkly.classicandsimplestatusbars.overlays.FoodBar;

public class DehydrationBar {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    public static final Identifier THIRST_ICON = new Identifier("dehydration:textures/gui/thirst.png");
    public void render(DrawContext context) {

        if (mc.cameraEntity instanceof PlayerEntity playerEntity
                && !mc.options.hudHidden
                && mc.interactionManager != null && mc.interactionManager.hasStatusBars()) {
            int width = mc.getWindow().getScaledWidth();
            int height = mc.getWindow().getScaledHeight();
            float x = (float) width / 2 + 11;
            float y = height - 39;
            y += 5;
            TextRenderer font = mc.textRenderer;
            renderDehydrationValue(font, context, (int) x, (int) y, playerEntity);
        }
    }

    private void renderDehydrationValue(TextRenderer font, DrawContext context, int x, int y, PlayerEntity playerEntity) {
        if (playerEntity == null) return;
        if (playerEntity.isInvulnerable()) return; // why?

        ThirstManager thirstManager = ((ThirstManagerAccess) playerEntity).getThirstManager();
        if (thirstManager.hasThirst()) {
            context.drawTexture(THIRST_ICON,
                    x + 70, y - 10,
                    54, 9,
                    9, 9,
                    256, 256); // 水图标
            int Thirst = thirstManager.getThirstLevel();
            //float Quenched = thirstManager.;
            //if (Quenched > 0) { // 如果Quenched大于0渲染.
                //int x2 = x + 70 - font.getWidth(Quenched + "+") - font.getWidth(String.valueOf(Thirst)); // 计算长度
                // guiGraphics.blit(THIRST_ICONS, x, y, 0.0F, 0.0F, 9, 9, 25, 9);
                //context.drawText(font, Quenched + "+", x2, y - 9, 0x48D1CC, false);
            //}
            font.getWidth("0.3");
            // 0x4876FF 颜色
            context.drawText(font, String.valueOf(Thirst), x + 70 - font.getWidth(String.valueOf(Thirst)), y - 9, 0x48D1CC, false);
        }

    }
}
