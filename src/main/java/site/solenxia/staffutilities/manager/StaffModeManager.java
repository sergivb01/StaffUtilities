package site.solenxia.staffutilities.manager;

import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import site.solenxia.staffutilities.StaffUtilities;
import site.solenxia.staffutilities.utils.ItemUtils;

import java.util.*;

public class StaffModeManager extends ItemUtils implements Listener{

	private static List<Player> playerList = new ArrayList<>();
	private static Map<Player, Player> followingPlayers = new HashMap<>();
	private static Map<Player, Location> playersLocations = new HashMap<>();
	private static Map<Player, ItemStack[]> playersInvs = new HashMap<>();
	private static Map<Player, GameMode> playersGms = new HashMap<>();
	private static Map<Player, Player> examineTasks = new HashMap<>();

	public StaffModeManager(StaffUtilities staffUtilities){
		Bukkit.getPluginManager().registerEvents(this, StaffUtilities.getInstance());
	}

	public static boolean toggleStaff(Player player){
		if(!playerList.contains(player)){
			playerList.add(player);
			playersLocations.put(player, player.getLocation());
			playersInvs.put(player, player.getInventory().getContents());
			playersGms.put(player, player.getGameMode());

			player.setGameMode(GameMode.CREATIVE);
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);

			VanishManager.vanish(player);

			setupPlayerItems(player);
			return true;
		}

		if(followingPlayers.containsKey(player)){
			followingPlayers.remove(player);
		}
		playerList.remove(player);
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		if(!player.hasPermission("staffutilities.command.mod") && playersLocations.containsKey(player)){
			player.teleport(playersLocations.get(player));
		}

		if(playersInvs.containsKey(player)){
			player.getInventory().setContents(playersInvs.get(player));
		}

		if(playersGms.containsKey(player)){
			player.setGameMode(playersGms.get(player));
		}

		playersLocations.remove(player);
		playersInvs.remove(player);
		playersGms.remove(player);

		return false;
	}

	private static void setupPlayerItems(Player p){
		Inventory inventory = p.getInventory();
		inventory.setItem(StaffUtilities.getInstance().getConfig().getInt("staffmode.compass.slot"), createItem(Material.COMPASS, 1, ChatColor.translateAlternateColorCodes('&', StaffUtilities.getInstance().getConfig().getString("staffmode.compass.name"))));
		inventory.setItem(StaffUtilities.getInstance().getConfig().getInt("staffmode.book.slot"), createItem(Material.BOOK, 1, ChatColor.translateAlternateColorCodes('&', StaffUtilities.getInstance().getConfig().getString("staffmode.book.name"))));
		inventory.setItem(StaffUtilities.getInstance().getConfig().getInt("staffmode.followplayer.slot"), createItem(Material.LEASH, 1, ChatColor.translateAlternateColorCodes('&', StaffUtilities.getInstance().getConfig().getString("staffmode.followplayer.name"))));
		inventory.setItem(StaffUtilities.getInstance().getConfig().getInt("staffmode.carpet.slot"), createItem(Material.CARPET, ChatColor.translateAlternateColorCodes('&', StaffUtilities.getInstance().getConfig().getString("staffmode.carpet.name"))));
		inventory.setItem(StaffUtilities.getInstance().getConfig().getInt("staffmode.randomteleport.slot"), createItem(Material.RECORD_5, ChatColor.translateAlternateColorCodes('&', StaffUtilities.getInstance().getConfig().getString("staffmode.randomteleport.name"))));
	}

	public static void startExaminationTask(Player p, Player personToBeExamined){
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + "Examining " + ChatColor.BOLD + personToBeExamined.getName());

		for(int i = 0; i < 36; i++){
			ItemStack is = personToBeExamined.getInventory().getItem(i);
			inv.setItem(i, is);
		}

		inv.setItem(36, personToBeExamined.getInventory().getHelmet());
		inv.setItem(37, personToBeExamined.getInventory().getChestplate());
		inv.setItem(38, personToBeExamined.getInventory().getLeggings());
		inv.setItem(39, personToBeExamined.getInventory().getBoots());

		inv.setItem(40, personToBeExamined.getItemInHand());


		for(int i = 0; i < 3; i++){
			inv.setItem(41 + i, new ItemStack(Material.THIN_GLASS, 1));
		}

		inv.setItem(45, createItem(Material.SPECKLED_MELON, (int) personToBeExamined.getHealth(), ChatColor.GREEN + "Health"));

		ItemStack potionEffectItem = createItem(Material.BREWING_STAND, personToBeExamined.getActivePotionEffects().size(), ChatColor.LIGHT_PURPLE + "Active Potion Effects");

		for(PotionEffect pe : personToBeExamined.getActivePotionEffects()){
			addLore(potionEffectItem, ChatColor.GRAY + pe.getType().getName() + " " + (pe.getAmplifier() + 1) + ": " + pe.getDuration() / 20);
		}

		inv.setItem(46, potionEffectItem);

		p.openInventory(inv);

	}

	public static void updateExaminationTask(Player p, Player personToBeExamined){
		if(!p.isOnline()){
			p.closeInventory();
			return;
		}
		Inventory inv = p.getOpenInventory().getTopInventory();

		for(int i = 0; i < 36; i++){
			ItemStack is = personToBeExamined.getInventory().getItem(i);
			inv.setItem(i, is);
		}

		inv.setItem(36, personToBeExamined.getInventory().getHelmet());
		inv.setItem(37, personToBeExamined.getInventory().getChestplate());
		inv.setItem(38, personToBeExamined.getInventory().getLeggings());
		inv.setItem(39, personToBeExamined.getInventory().getBoots());

		inv.setItem(40, personToBeExamined.getItemInHand());

		for(int i = 0; i < 3; i++){
			inv.setItem(41 + i, new ItemStack(Material.THIN_GLASS, 1));
		}

		inv.setItem(44, createItem(Material.FIREBALL, 1, ChatColor.RED + "Ban: " + ChatColor.BOLD + personToBeExamined.getName()));

		inv.setItem(45, createItem(Material.SPECKLED_MELON, (int) personToBeExamined.getHealth(), ChatColor.GREEN + "Health"));

		ItemStack potionEffectItem = createItem(Material.BREWING_STAND, personToBeExamined.getActivePotionEffects().size(), ChatColor.LIGHT_PURPLE + "Active Potion Effects");

		for(PotionEffect pe : personToBeExamined.getActivePotionEffects()){
			addLore(potionEffectItem, ChatColor.GRAY + pe.getType().getName() + " " + (pe.getAmplifier() + 1) + ": " + pe.getDuration() / 20);
		}

		inv.setItem(46, potionEffectItem);
	}

	public static List<Player> getPlayerList(){
		return playerList;
	}

	public static Map<Player, Player> getFollowingPlayers(){
		return followingPlayers;
	}

	public static Map<Player, Location> getPlayersLocations(){
		return playersLocations;
	}

	public static Map<Player, ItemStack[]> getPlayersInvs(){
		return playersInvs;
	}

	public static Map<Player, GameMode> getPlayersGms(){
		return playersGms;
	}

	public static Map<Player, Player> getExamineTasks(){
		return examineTasks;
	}

	public static boolean hasModMode(Player p){
		return playerList.contains(p);
	}

	@EventHandler
	public void handleInteractEvent(PlayerInteractEvent e){
		if(playerList.contains(e.getPlayer())){
			if(e.getAction().name().contains("RIGHT")){
				if(e.getPlayer().getItemInHand().getType() == Material.RECORD_5){
					List<Player> playersOnline = new ArrayList<>();
					for(Player onlinePlayers : Bukkit.getOnlinePlayers()){
						playersOnline.add(onlinePlayers);
					}
					for(Player player : playerList){
						playersOnline.remove(player);
					}
					if(playersOnline.size() <= 0){
						e.getPlayer().sendMessage(ChatColor.RED + "There are no players online.");
						e.setCancelled(true);
						return;
					}
					Random random = new Random();
					int i = random.nextInt(playersOnline.size());
					Player p = (Player) playersOnline.get(i);
					e.getPlayer().teleport(p);

					e.getPlayer().sendMessage(ChatColor.BLUE + "Teleported to " + p.getName());
				}
				if(e.getClickedBlock() != null){
					if(e.getClickedBlock().getType() == Material.CHEST){
						Chest chest = (Chest) e.getClickedBlock().getState();
						Inventory inv = Bukkit.createInventory(null, chest.getInventory().getSize(), "Fake Chest");
						inv.setContents(chest.getInventory().getContents());
						e.getPlayer().openInventory(inv);
						e.setCancelled(true);
						e.getPlayer().sendMessage(ChatColor.BLUE + "Fake chest being opened.");
					}
				}
			}
		}
	}

	@EventHandler
	public void handleDrop(PlayerDropItemEvent e){
		if(playerList.contains(e.getPlayer())){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void handleBreak(BlockBreakEvent e){
		if(playerList.contains(e.getPlayer())){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void handleRespawn(final PlayerRespawnEvent e){
		new BukkitRunnable(){
			public void run(){
				if(!playerList.contains(e.getPlayer())){
					StaffModeManager.toggleStaff(e.getPlayer());
				}
			}
		}.runTaskLater(StaffUtilities.getInstance(), 10L);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void handleInventoryClose(InventoryCloseEvent e){
		if(examineTasks.containsKey(e.getPlayer())){
			examineTasks.remove(e.getPlayer());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void handlePlayerInteract(PlayerInteractEntityEvent e){
		if(playerList.contains(e.getPlayer())){
			Player clicked = (Player) e.getRightClicked();
			if(clicked.getType() == EntityType.PLAYER){
				if(e.getPlayer().getItemInHand().getType() == Material.BOOK){
					startExaminationTask(e.getPlayer(), ((Player) e.getRightClicked()));
					examineTasks.put(e.getPlayer(), ((Player) e.getRightClicked()));
				}
			}
		}
	}

	@EventHandler
	public void handlePickupItem(PlayerPickupItemEvent e){
		if(playerList.contains(e.getPlayer())){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void handleBlockBreak(BlockBreakEvent e){
		if(playerList.contains(e.getPlayer())){
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void handleBlockPlace(BlockPlaceEvent e){
		if(hasModMode(e.getPlayer())){
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onEntityTargetPlayer(EntityTargetEvent e){
		if(!(e.getTarget() instanceof Player)){
			return;
		}
		Player target = (Player) e.getTarget();
		if(playerList.contains(target)){
			e.setCancelled(true);
			if((e.getEntity() instanceof ExperienceOrb)){
				e.setTarget(null);
			}
		}
	}
}
