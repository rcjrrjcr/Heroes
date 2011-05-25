package com.herocraftonline.dev.heroes.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet101CloseWindow;
import net.minecraft.server.Packet102WindowClick;
import net.minecraft.server.Packet106Transaction;
import net.minecraft.server.Packet3Chat;
import net.minecraft.server.Packet6SpawnPosition;
import net.minecraft.server.Slot;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.TextWrapper;
import org.bukkit.entity.Player;

public class HNetServerHandler extends NetServerHandler {

    @SuppressWarnings("rawtypes")
    private Map n = new HashMap();
    @SuppressWarnings("unused")
    private int g;
    private int f;

    public HNetServerHandler(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
        super(minecraftserver, networkmanager, entityplayer);
        networkmanager.a(this);
        entityplayer.netServerHandler = this;
    }

    @Override
    public void sendPacket(Packet packet) {
        // CraftBukkit
        if (packet instanceof Packet6SpawnPosition) {
            Packet6SpawnPosition packet6 = (Packet6SpawnPosition) packet;
            this.player.compassTarget = new Location(getPlayer().getWorld(), packet6.x, packet6.y, packet6.z);
        } else if (packet instanceof Packet3Chat) {
            String message = ((Packet3Chat) packet).a;
            for (final String line : TextWrapper.wrapText(message)) {
                this.networkManager.a(new Packet3Chat(line));
            }
            packet = null;
        }
        if (packet != null) {
            this.networkManager.a(packet);
        }
        // CraftBukkit
        this.g = this.f;

        // Heroes Start - This is to cover for plugins which give items to a player directly to the Hotbar.
        //if (packet instanceof Packet103SetSlot) {
        //Bukkit.getServer().getPluginManager().callEvent(new InventoryChangedEvent((Player) this.player.getBukkitEntity()));
        //}
        // Heroes End
    }

    @Override
    public void a(Packet101CloseWindow packet101closewindow) {
        // CraftBukkit start
        if (this.player.dead) {
            return;
        }
        // CraftBukkit end

        this.player.z();
        // Heroes Start
        Bukkit.getServer().getPluginManager().callEvent(new InventoryCloseEvent((Player) this.player.getBukkitEntity()));
        // Heroes End
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void a(Packet102WindowClick packet102windowclick) {
        // CraftBukkit start
        if (this.player.dead) {
            return;
        }
        // CraftBukkit end

        if (this.player.activeContainer.f == packet102windowclick.a && this.player.activeContainer.c(this.player)) {
            ItemStack itemstack = this.player.activeContainer.a(packet102windowclick.b, packet102windowclick.c, packet102windowclick.f, this.player);
            if (ItemStack.equals(packet102windowclick.e, itemstack)) {
                this.player.netServerHandler.sendPacket(new Packet106Transaction(packet102windowclick.a, packet102windowclick.d, true));
                this.player.h = true;
                this.player.activeContainer.a();
                this.player.y();
                this.player.h = false;
            } else {
                this.n.put(Integer.valueOf(this.player.activeContainer.f), Short.valueOf(packet102windowclick.d));
                this.player.netServerHandler.sendPacket(new Packet106Transaction(packet102windowclick.a, packet102windowclick.d, false));
                this.player.activeContainer.a(this.player, false);
                ArrayList arraylist = new ArrayList();
                for (int i = 0; i < this.player.activeContainer.e.size(); ++i) {
                    arraylist.add(((Slot) this.player.activeContainer.e.get(i)).getItem());
                }
                this.player.a(this.player.activeContainer, arraylist);
            }
            // Heroes Start
            InventoryChangedEvent e = new InventoryChangedEvent((Player) this.player.getBukkitEntity());
            Bukkit.getServer().getPluginManager().callEvent(e);
            // Heroes End
        }
    }
}
