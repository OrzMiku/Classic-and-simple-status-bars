package cn.mcxkly.classicandsimplestatusbars.other;

import net.minecraft.resources.ResourceLocation;

public class Icon {
    private final ResourceLocation resourceLocation;
    private final int textureWidth;
    private final int textureHeight;
    private final int u;
    private final int v;
    private final int width;
    private final int height;

    public Icon(ResourceLocation resourceLocation, int textureWidth, int textureHeight, int u, int v, int width, int height) {
        this.resourceLocation = resourceLocation;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public int getTextureHeight() {
        return textureHeight;
    }

    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}