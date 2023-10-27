package cn.mcxkly.classicandsimplestatusbars.other;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ApoliMixin1 {
    @OnlyIn(Dist.CLIENT)
    void render(GuiGraphics context, float delta);
}
