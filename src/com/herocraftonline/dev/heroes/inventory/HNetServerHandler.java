package com.herocraftonline.dev.heroes.inventory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.ContainerChest;
import net.minecraft.server.ContainerDispenser;
import net.minecraft.server.ContainerFurnace;
import net.minecraft.server.ContainerPlayer;
import net.minecraft.server.ContainerWorkbench;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet101CloseWindow;
import net.minecraft.server.Packet102WindowClick;
import net.minecraft.server.Packet106Transaction;
import net.minecraft.server.Slot;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;

public class HNetServerHandler extends NetServerHandler {

    @SuppressWarnings("rawtypes")
    private Map n = new HashMap();

    public HNetServerHandler(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
        super(minecraftserver, networkmanager, entityplayer);
    }

    @Override
    public void a(Packet101CloseWindow packet101closewindow) {
        this.player.z();
        Bukkit.getServer().getPluginManager().callEvent(new InventoryCloseEvent((Player) this.player.getBukkitEntity()));
    }

    @SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
    @Override
    public void a(Packet102WindowClick packet102windowclick) {
        if (this.player.activeContainer.f == packet102windowclick.a && this.player.activeContainer.c(this.player)) {

            // Heroes Start
            InventorySlotType type = InventorySlotType.CONTAINER;
            int inventorySize = 0;
            boolean player = false;
            if (this.player.activeContainer instanceof ContainerChest) {
                ContainerChest container = (ContainerChest) this.player.activeContainer;
                try {
                    Field chest = ContainerChest.class.getDeclaredField("a");
                    chest.setAccessible(true);
                    IInventory inv = (IInventory) chest.get(container);
                    inventorySize = inv.getSize();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                // System.out.print("Chest " + inventorySize);
            } else if (this.player.activeContainer instanceof ContainerFurnace) {
                // System.out.print("Furnace");
                inventorySize = 3;
                if (packet102windowclick.b == 2) {
                    type = InventorySlotType.RESULT;
                    // System.out.print(" - Result");
                } else if (packet102windowclick.b == 1) {
                    type = InventorySlotType.FUEL;
                    // System.out.print(" - Fuel");
                }
            } else if (this.player.activeContainer instanceof ContainerDispenser) {
                // System.out.print("Dispenser");
                inventorySize = 9;
            } else if (this.player.activeContainer instanceof ContainerWorkbench) {
                // System.out.print("Workbench");
                inventorySize = 10;
                if (packet102windowclick.b == 0) {
                    type = InventorySlotType.RESULT;
                    // System.out.print(" - Result");
                } else if (packet102windowclick.b < inventorySize) {
                    type = InventorySlotType.CRAFTING;
                    // System.out.print(" - Crafting");
                }
            } else if (this.player.activeContainer instanceof ContainerPlayer) {
                // System.out.print("Player - " + packet102windowclick.b);
                inventorySize = 36 + 4;
                if (packet102windowclick.b == 0) {
                    type = InventorySlotType.RESULT;
                    // System.out.print(" - Result");
                } else if (packet102windowclick.b >= 1 && packet102windowclick.b <= 4) {
                    type = InventorySlotType.CRAFTING;
                    // System.out.print(" - Crafting");
                } else if (packet102windowclick.b >= 5 && packet102windowclick.b <= 8) {
                    type = InventorySlotType.ARMOR;
                    // System.out.print(" - Armor");
                }
                player = true;
            }

            // Ok so we know the containers now we need to work out what part they are clicking.
            if (player) {
                if (packet102windowclick.b == -999) {
                    // System.out.print(" - Outside");
                    type = InventorySlotType.OUTSIDE;
                } else if (packet102windowclick.b >= 36 && packet102windowclick.b <= 44) {
                    // System.out.print(" - Quickbar");
                    type = InventorySlotType.QUICKBAR;
                } else if (packet102windowclick.b >= 9 && packet102windowclick.b <= 35) {
                    // System.out.print(" - Pack");
                    type = InventorySlotType.PACK;
                }
            } else {
                if (packet102windowclick.b == -999) {
                    // System.out.print(" - Outside");
                    type = InventorySlotType.OUTSIDE;
                } else if (packet102windowclick.b >= inventorySize + 27) {
                    // System.out.print(" - Quickbar");
                    type = InventorySlotType.QUICKBAR;
                } else if (packet102windowclick.b >= inventorySize) {
                    // System.out.print(" - Pack");
                    type = InventorySlotType.PACK;
                }
            }

            Player p = (Player) this.player.getBukkitEntity();
            ItemStack before = ItemStack.b(packet102windowclick.e);
            CraftItemStack slot = null;
            if (before != null) {
                slot = new CraftItemStack(before);
            }
            CraftItemStack cursor = null;
            if (this.player.inventory.j() != null) {
                cursor = new CraftItemStack(this.player.inventory.j());
            }
            InventoryChangeEvent event = new InventoryChangeEvent(p, type, slot, cursor, packet102windowclick.b);
            Bukkit.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                p.updateInventory();
                return;
            }
            // Heroes End

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
        }
    }
}
