package site.solenxia.staffutilities.listeners.patchlisteners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import site.solenxia.staffutilities.StaffUtilities;

public class ItemPatchListener implements Listener{

	public ItemPatchListener(StaffUtilities plugin){
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if((e.getCurrentItem() != null) && (e.getCurrentItem().containsEnchantment(Enchantment.DAMAGE_ALL)) && (e.getCurrentItem().getEnchantmentLevel(Enchantment.DAMAGE_ALL) > 5)){
			p.sendMessage(ChatColor.RED + "Nope, you are not using this item today.");
			p.getInventory().removeItem(p.getItemInHand());
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			Player p = (Player) e.getDamager();
			if((p.getItemInHand() != null) && (p.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)) && (p.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL) > 5)){
				p.sendMessage(ChatColor.RED + "Nope, you are not using this item today.");
				p.getInventory().removeItem(p.getItemInHand());
				e.setCancelled(true);
			}
		}
	}


}
