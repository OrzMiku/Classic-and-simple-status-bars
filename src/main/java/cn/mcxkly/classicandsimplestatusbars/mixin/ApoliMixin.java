package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.Config;
import io.github.apace100.apoli.screen.PowerHudRenderer;
import io.github.apace100.apoli.util.ApoliConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PowerHudRenderer.class, remap = false)
public abstract class ApoliMixin {
    boolean Only = false;

    @OnlyIn(Dist.CLIENT)
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render99(GuiGraphics context, float delta, CallbackInfo ci) {
        if ( Config.All_On ) {
//        我们假设 apoli-client.toml 配置文件没有被修改，那我们就进行修改.
//        [resources_and_cooldowns]
//            hud_offset_x = 0
//            hud_offset_y = 0  // 如果这里没被改就说明可能冲突，我们增加高度.
            if ( !Only ) {
                Only = true; // 确定只改一次
                int sy = ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetY.get();
                int sx = ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetX.get();
                if ( sy == 0 || sx == 0 ) {
                    ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetY.set(10);
                    ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetX.set(90);
                }
            }
            // 极其残忍
//          Minecraft client = Minecraft.getInstance();
//          lient.getWindow().setHeight(client.getWindow().getGuiScaledHeight() + 1);
            if ( Config.Origins_On ) {
                ci.cancel(); // 我滴 注入 任务完成啦.
            } else {
                Minecraft client = Minecraft.getInstance();
                LocalPlayer player = client.player;
                // 提前修改，防止动来动去的.
                if ( player != null ) {
                    // 啥？我不想骑马.
                    Entity patt1361$temp = player.getVehicle();
                    if ( patt1361$temp instanceof LivingEntity vehicle ) {
                        ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetY.set(+(8 * (int) (vehicle.getMaxHealth() / 20.0F)));
                    }
                    // 不会在喝水的时候抬高了.
                    if ( player.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) || player.getAirSupply() < player.getMaxAirSupply() ) {
                        ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetY.set(+18);
                    }
                }
            }
        }
    }
}
