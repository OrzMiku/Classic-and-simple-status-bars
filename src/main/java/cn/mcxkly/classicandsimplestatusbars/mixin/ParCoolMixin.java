package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import com.alrex.parcool.client.hud.impl.HUDType;
import com.alrex.parcool.client.hud.impl.LightStaminaHUD;
import com.alrex.parcool.client.hud.impl.StaminaHUD;
import com.alrex.parcool.client.hud.impl.StaminaHUDController;
import com.alrex.parcool.common.capability.IStamina;
import com.alrex.parcool.common.capability.stamina.HungerStamina;
import com.alrex.parcool.config.ParCoolConfig;
import com.alrex.parcool.extern.epicfight.EpicFightManager;
import com.alrex.parcool.extern.feathers.FeathersManager;
import com.alrex.parcool.extern.paraglider.ParagliderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = StaminaHUDController.class, remap = false)
public class ParCoolMixin {
    @Unique
    StaminaHUD classicandsimplestatusbars$staminaHUD = new StaminaHUD();
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight, CallbackInfo ci) {
        if ( Config.All_On ) {
            AbstractClientPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                if ( ParCoolConfig.Client.Booleans.ParCoolIsActive.get() && !(IStamina.get(player) instanceof HungerStamina) && !EpicFightManager.isUsingEpicFightStamina(player) && !FeathersManager.isUsingFeathers(player) && !ParagliderManager.isUsingParaglider(player)) {
                    if ( Objects.requireNonNull(ParCoolConfig.Client.StaminaHUDType.get()) != HUDType.Normal ) {

                        ci.cancel();
                    }
                }
            }
        }
    }
}