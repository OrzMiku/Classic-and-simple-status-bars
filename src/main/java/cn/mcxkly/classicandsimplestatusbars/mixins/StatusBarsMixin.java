package cn.mcxkly.classicandsimplestatusbars.mixins;

import cn.mcxkly.classicandsimplestatusbars.overlays.DehydrationBar;
import cn.mcxkly.classicandsimplestatusbars.overlays.FoodBar;
import cn.mcxkly.classicandsimplestatusbars.overlays.HealthBar;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.fabricmc.loader.api.FabricLoader;

@Mixin(InGameHud.class)
public class StatusBarsMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderStatusBars(Lnet/minecraft/client/gui/DrawContext;)V"), method = "render")
    public void disableStatusBars(InGameHud instance, DrawContext context) {
        // Disable rendering of all survival elements
    }

    // the fish that escaped the net
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderMountHealth(Lnet/minecraft/client/gui/DrawContext;)V"), method = "render")
    private void disableMountBars(InGameHud instance, DrawContext context) {
        // Disable rendering of all survival elements
    }

    // Render Food Bar and More
    private static final DehydrationBar dehydrationBar = new DehydrationBar();
    // Render Food Bar and More
    private static final FoodBar foodBar = new FoodBar();
    // Render Health Bar
    private static final HealthBar healthBar = new HealthBar();

    @Inject(method = "render", at = @At(value = "HEAD"))
    public void renderBars(DrawContext context, float tickDelta, CallbackInfo ci) {
        int ProtocolVersion = SharedConstants.getProtocolVersion(); // 从版本23w31a（1.20.2）开始，icons.png 被移除.
        String Version = String.valueOf(ProtocolVersion);
        if (Version.length() > 3) { // 长度大于3是快照版本
            if (SharedConstants.getProtocolVersion() >= 1073741968) { // 23w31a的协议版本.
                FoodBar.isUseSeparateIconsIDEA(true);
                HealthBar.isUseSeparateIconsIDEA(true);
            }

        } else if (SharedConstants.getProtocolVersion() > 763) { // 763是1.20.1的协议版本.
            FoodBar.isUseSeparateIconsIDEA(true);
            HealthBar.isUseSeparateIconsIDEA(true);
        }
        foodBar.render(context, tickDelta);
        healthBar.render(context, tickDelta);
        // 脱水、口渴 Dehydration Mod
        if (FabricLoader.getInstance().isModLoaded("dehydration")) {
            FoodBar.StopConflictRenderingIDEA(false);
            dehydrationBar.render(context);
        }
        if (FabricLoader.getInstance().isModLoaded("artifacts")) {
        FoodBar.ArtifactsIDEA(true);
        }
    }
//
//    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHealthBar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/player/PlayerEntity;IIIIFIIIZ)V"), method = "renderStatusBars")
//    public void disableVanillaHealthBar(InGameHud instance, DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking) {
//        // do nothing, vanilla health bar is not rendered anymore
//    }

//    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderCrosshair(Lnet/minecraft/client/gui/DrawContext;)V"), method = "render")
//    private void disableVanillaCrosshair(InGameHud instance, DrawContext context) {
//    }
//
//    @Redirect(at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"), method = "renderStatusBars")
//    public int rowHeight(int a, int b) {
//        // The height of a health bar is 10 at Math.max(10 - (q - 2), 3)
//        // at Math.max(j, i) we want the renderer to think there is only one line of hearts, so we return 10
//        return 10;
//    }
//
//    @Redirect(at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"), method = "renderStatusBars")
//    public float fakeHealth(float a, float b) {
//        // The renderer should there is only one health row so the armor is displayed at the right place
//        return 20;
//    }
//
//    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAbsorptionAmount()F"))
//    public float fakeAbsorption(PlayerEntity player) {
//        // The renderer should think there is only one absorption row if the player has absorption
//        // so the armor is displayed at the right place
//        return (player.getAbsorptionAmount() > 0) ? 20 : 0;
//    }
}
