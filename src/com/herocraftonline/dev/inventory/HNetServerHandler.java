package com.herocraftonline.dev.inventory;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet101CloseWindow;

public class HNetServerHandler extends NetServerHandler {

    public HNetServerHandler(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
        super(minecraftserver, networkmanager, entityplayer);
    }

    @Override
    public void a(Packet101CloseWindow packet101closewindow) {
        this.player.z();
        System.out.print("Inventory Close");
        // Call the Inventory Check here.
    }
}