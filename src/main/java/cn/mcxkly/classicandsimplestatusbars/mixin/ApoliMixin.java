package cn.mcxkly.classicandsimplestatusbars.mixin;

import cn.mcxkly.classicandsimplestatusbars.other.ApoliMixin1;
import io.github.apace100.apoli.screen.PowerHudRenderer;
import io.github.apace100.apoli.util.ApoliConfigs;
import io.github.apace100.apoli.util.HudRender;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Mixin(value = PowerHudRenderer.class, remap = false)
public abstract class ApoliMixin implements ApoliMixin1 {
    // 好愚蠢的写法......
    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics context, float delta) {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player != null) {
            IPowerContainer.get(player).ifPresent((component) -> {
                int x = client.getWindow().getGuiScaledWidth() / 2 + 20 + (Integer)ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetX.get() + 90; // 大概向右位移一个槽那么宽
                int y = client.getWindow().getGuiScaledHeight() - 47 + (Integer)ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetY.get() + 10; // 向下位移约一个心的高度
                // 啥？我不想骑马.
//                Entity patt1361$temp = player.getVehicle();
//                if (patt1361$temp instanceof LivingEntity vehicle) {
//                    y -= 8 * (int)(vehicle.getMaxHealth() / 20.0F);
//                }
                // 不会在喝水的时候抬高了.
//                if (player.isEyeInFluidType((FluidType) ForgeMod.WATER_TYPE.get()) || player.getAirSupply() < player.getMaxAirSupply()) {
//                    y -= 18;
//                }

                int barWidth = 71;
                int barHeight = 8;
                int iconSize = 8;
                List<? extends ConfiguredPower<?, ?>> configuredPowers = component.getPowers().stream().map(Holder ::value).filter((power) -> {
                    return power.asHudRendered().isPresent();
                }).sorted(Comparator.comparing((power) -> {
                    return ((HudRender)power.getRenderSettings(player).orElse(HudRender.DONT_RENDER)).spriteLocation();
                })).toList();
                Iterator var10 = configuredPowers.iterator();

                while(var10.hasNext()) {
                    ConfiguredPower<?, ?> hudPower = (ConfiguredPower)var10.next();
                    HudRender render = (HudRender)hudPower.getRenderSettings(player).orElse(HudRender.DONT_RENDER);
                    if (render.shouldRender(player) && (Boolean)hudPower.shouldRender(player).orElse(false)) {
                        ResourceLocation currentLocation = render.spriteLocation();
                        context.blit(currentLocation, x, y, 0, 0, barWidth, 5);
                        int v = 8 + render.barIndex() * 10;
                        float fill = (Float)hudPower.getFill(player).orElse(0.0F);
                        if (render.isInverted()) {
                            fill = 1.0F - fill;
                        }

                        int w = (int)(fill * (float)barWidth);
                        context.blit(currentLocation, x, y - 2, 0, v, w, barHeight);
                        context.blit(currentLocation, x - iconSize - 2, y - 2, 73, v, iconSize, iconSize);
                        y -= 8;
                    }
                }

            });
        }
    }


/*
    boolean Bsssa = false;
    @Inject(method = "render", at = @At("HEAD"))
    private void render(GuiGraphics context, float delta, CallbackInfo ci) {
//        我们假设 apoli-client.toml 配置文件没有被修改，那我们就进行修改.
//        [resources_and_cooldowns]
//            hud_offset_x = 0
//            hud_offset_y = 0  // 如果这里没被改就说明可能冲突，我们增加高度.
        //Minecraft client = Minecraft.getInstance();
        if ( !Bsssa ) {
            Bsssa = true; // 确定只改一次
            int y = (Integer) ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetY.get();
            ClassicAndSimpleStatusBars.LOGGER.debug("HUD_H_is-  " + y);
            if ( y == 0 ) {
                ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetY.set(10);
                ClassicAndSimpleStatusBars.LOGGER.debug("HUD_H_is-   " + (Integer) ApoliConfigs.CLIENT.resourcesAndCooldowns.hudOffsetY.get());
            }
        }

        // 极其残忍
        // client.getWindow().setHeight(client.getWindow().getGuiScaledHeight() + 1);
    }*/
}
