package site.solenxia.staffutilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import site.solenxia.staffutilities.payloads.Cache;
import site.solenxia.staffutilities.payloads.types.Payload;
import site.solenxia.staffutilities.payloads.types.ReportPayload;
import site.solenxia.staffutilities.utils.StringUtils;

import static org.bukkit.ChatColor.RED;

public class ReportCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(RED + "This command may not be executed by Console.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length < 2) {
            player.sendMessage(RED + "Invalid usage: `/report <playername> <reason...>`");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        String reason = StringUtils.join(args, 1);

        if (target == null) {
            player.sendMessage(RED + "Player `" + args[0] + "` is not online or has never joined before!");
            return true;
        }

        if (player.equals(target)) {
            player.sendMessage(RED + "You may not report yourself!");
            return true;
        }

        if (!Cache.canExecute(player)) {
            player.sendMessage(RED + "You hav already used the report or request command in the past 5 minutes! Please wait and try again.");
            return true;
        }

        Payload payload = new ReportPayload(player.getName(), player.getUniqueId(), target.getName(), target.getUniqueId(), reason);
        System.out.println("Being sent: " + payload.toDocument().toJson());
        payload.send();
        return true;
    }
}
