package de.guntram.mcmod.worldtime;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

public class GuiWorldTime {
    
    public GuiWorldTime() {

    }
    
    public void onRenderGameOverlayPost(float partialticks) {
        Window mainWindow = MinecraftClient.getInstance().window;
        int ypos=5;
        int xpos=mainWindow.getScaledWidth()/2;
        MinecraftClient minecraft = MinecraftClient.getInstance();
        
        if (minecraft == null  || minecraft.player == null || minecraft.player.world == null)
            return;
        
        int hours=(int) (minecraft.player.world.getTimeOfDay()/1000+6)%24;
        int minutes = (int) ((minecraft.player.world.getTimeOfDay()%1000)*60/1000);
        String clock=String.format("%02d:%02d", hours, minutes);
        
        // minecraft.textRenderer.draw(clock, xpos-minecraft.textRenderer.getStringWidth(clock)/2, ypos, 0xffffff);
        minecraft.textRenderer.draw(clock, 0, ypos, 0xffffff);
    }
}
