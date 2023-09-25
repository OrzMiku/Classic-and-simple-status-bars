package cn.mcxkly.classicandsimplestatusbars.mixin;

import com.alrex.parcool.client.hud.impl.StaminaHUD;
import com.alrex.parcool.client.hud.impl.StaminaHUDController;
import com.alrex.parcool.config.ParCoolConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = StaminaHUDController.class, remap = false)
public class ParCoolMixin {
    StaminaHUD staminaHUD = new StaminaHUD();

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void registerThirstOverlay(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight, CallbackInfo ci) {
        if (ParCoolConfig.Client.Booleans.ParCoolIsActive.get() && !ParCoolConfig.Client.Booleans.UseHungerBarInstead.get()) {
            switch (ParCoolConfig.Client.StaminaHUDType.get()) {
                case Light, Normal:
                    this.staminaHUD.render(gui, guiGraphics, partialTick, screenWidth, screenHeight);
            } // 在不隐藏的情况下，始终以Normal方式渲染HUD。
        }
        ci.cancel();
    }
}
