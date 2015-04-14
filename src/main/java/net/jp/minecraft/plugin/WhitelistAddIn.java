package net.jp.minecraft.plugin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * よく使うコマンドやアクションをチェスト画面を利用して操作しよう！ってプラグイン
 * AdminInventoryTools
 * @author syokkendesuyo
 */


public class WhitelistAddIn extends JavaPlugin implements Listener {


	/**
	 * プラグインが有効になったときに呼び出されるメソッド
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		String command_help = "help";
		String command_status = "status";
		String command_list = "list";
		String command_on = "on";
		String command_off = "off";
		String command_toggle = "toggle";
		String command_register = "register";
		String command_register_2 = "@a";
		String command_removeall = "remove-all";
		String command_add = "add";
		String command_adds = "adds";
		String command_remove = "remove";
		String command_me = "me";



		if (cmd.getName().equalsIgnoreCase("wla") || cmd.getName().equalsIgnoreCase("whitelist-add-in")){
			if(args.length == 0){
				help(sender , cmd);
			}
			else if(args[0].equalsIgnoreCase(command_help)){
				help(sender , cmd);
			}
			else if(args[0].equalsIgnoreCase("tool")){
				if(!(sender instanceof Player)){
					sender.sendMessage("Please excute this '/Whitelist-Add-In tool' command on a game!");
					sender.sendMessage("'/Whitelist-Add-In tool' コマンドはゲーム内で実行してください。");
				}
				else{
					Player player = (Player) sender;
					if(player.hasPermission("wla.tool")){

						ItemStack item = new ItemStack(Material.STICK);
						ItemMeta itemmeta = item.getItemMeta();
						itemmeta.setDisplayName(ChatColor.GOLD + "Whitelist-Add-In Tool");
						itemmeta.setLore(Arrays.asList(ChatColor.YELLOW + "ホワイトリストの杖:", ChatColor.WHITE + "この杖を空気に向かってクリックすると", ChatColor.WHITE + "ホワイトリストを簡単に制御できるようになります。"));
						itemmeta.addEnchant(Enchantment.SILK_TOUCH , 1, true);
						item.setItemMeta(itemmeta);
						player.getInventory().addItem(item);

						player.sendMessage(ChatColor.AQUA + "□ Whitelist-Add-In Toolを与えました。");
					}
					else{
						player.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
					}
				}
			}
			else if(args[0].equalsIgnoreCase(command_status)){
				status(sender);
			}
			else if(args[0].equalsIgnoreCase(command_list)){
				list(sender);
			}
			else if(args[0].equalsIgnoreCase(command_on)){
				on(sender);
			}
			else if(args[0].equalsIgnoreCase(command_off)){
				off(sender);
			}
			else if(args[0].equalsIgnoreCase(command_toggle)){
				toggle(sender);
			}
			else if(args[0].equalsIgnoreCase(command_register)){
				register(sender);
			}
			else if(args[0].equalsIgnoreCase(command_register_2)){
				register(sender);
			}
			else if(args[0].equalsIgnoreCase(command_removeall)){
				removeall(sender);
			}
			else if(args[0].equalsIgnoreCase(command_add)){
				add(sender , args);
			}
			else if(args[0].equalsIgnoreCase(command_remove)){
				remove(sender , args);
			}
			else if(args[0].equalsIgnoreCase(command_me)){
				if(!(sender instanceof Player)){
					sender.sendMessage("Please excute this '/Whitelist-Add-In me' command on a game!");
					sender.sendMessage("'/Whitelist-Add-In me' コマンドはゲーム内で実行してください。");
				}
				else{
					me(sender);
				}
			}
			else if(args[0].equalsIgnoreCase(command_adds)){
				adds(sender , args);
			}
			return true;
		}
		return false;
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event){
		final Player p = event.getPlayer();
		if(p.hasPermission("wla.open")||p.isOp()){
			if(p.getItemInHand().getType()==Material.AIR){
				//何でもなかった場合無視
			}
			else if(p.getItemInHand().getItemMeta().getDisplayName()==null){
				//通常の棒だった場合無視する
			}
			else if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Whitelist-Add-In Tool")){
				if(event.getAction() == Action.LEFT_CLICK_AIR){
					if(p.getItemInHand().getType() == Material.STICK){
						//ここの内容はインベントリGUIのアイテム設定です

						ItemStack item0 = new ItemStack(Material.APPLE);
						ItemMeta itemmeta0 = item0.getItemMeta();
						itemmeta0.setDisplayName(ChatColor.GOLD  + "ステイタス");
						itemmeta0.setLore(Arrays.asList(ChatColor.YELLOW + "ホワイトリストが有効かどうか確認します。", ChatColor.WHITE + "/wla status と同様です。"));
						item0.setItemMeta(itemmeta0);

						ItemStack item1 = new ItemStack(Material.EYE_OF_ENDER);
						ItemMeta itemmeta1 = item1.getItemMeta();
						itemmeta1.setDisplayName(ChatColor.GOLD  + "リスト");
						itemmeta1.setLore(Arrays.asList(ChatColor.YELLOW + "ホワイトリストの登録者名簿を確認。", ChatColor.WHITE + "/wla list と同様です。"));
						item1.setItemMeta(itemmeta1);

						ItemStack item2 = new ItemStack(Material.WOOL,1 , (byte) 5);
						ItemMeta itemmeta2 = item2.getItemMeta();
						itemmeta2.setDisplayName(ChatColor.GOLD  + "有効化");
						itemmeta2.setLore(Arrays.asList(ChatColor.YELLOW + "ホワイトリストを有効化します。", ChatColor.WHITE + "/wla on と同様です。"));
						item2.setItemMeta(itemmeta2);

						ItemStack item3 = new ItemStack(Material.WOOL,1 , (byte) 14);
						ItemMeta itemmeta3 = item3.getItemMeta();
						itemmeta3.setDisplayName(ChatColor.GOLD  + "無効化");
						itemmeta3.setLore(Arrays.asList(ChatColor.YELLOW + "ホワイトリストを無効化します。", ChatColor.WHITE + "/wla off と同様です。"));
						item3.setItemMeta(itemmeta3);

						ItemStack item4 = new ItemStack(Material.MAGMA_CREAM);
						ItemMeta itemmeta4 = item4.getItemMeta();
						itemmeta4.setDisplayName(ChatColor.GOLD  + "トグル");
						itemmeta4.setLore(Arrays.asList(ChatColor.YELLOW + "ホワイトリストをトグルします。", ChatColor.WHITE + "/wla toggle と同様です。"));
						item4.setItemMeta(itemmeta4);

						ItemStack item5 = new ItemStack(Material.SKULL_ITEM);
						ItemMeta itemmeta5 = item5.getItemMeta();
						itemmeta5.setDisplayName(ChatColor.GOLD  + "一括登録");
						itemmeta5.setLore(Arrays.asList(ChatColor.YELLOW + "オンラインのプレイヤーを全員登録します。", ChatColor.WHITE + "/wla register・@a と同様です。"));
						item5.setItemMeta(itemmeta5);

						ItemStack item6 = new ItemStack(Material.MAP);
						ItemMeta itemmeta6 = item6.getItemMeta();
						itemmeta6.setDisplayName(ChatColor.GOLD  + "一括削除");
						itemmeta6.setLore(Arrays.asList(ChatColor.YELLOW + "登録されているプレイヤーを全て削除します。", ChatColor.WHITE + "/wla remove-all と同様です。"));
						item6.setItemMeta(itemmeta6);

						ItemStack item7 = new ItemStack(Material.ARROW);
						ItemMeta itemmeta7 = item7.getItemMeta();
						itemmeta7.setDisplayName(ChatColor.GOLD  + "自分のステイタス");
						itemmeta7.setLore(Arrays.asList(ChatColor.YELLOW + "自分が登録さてれいるか確認します。", ChatColor.WHITE + "/wla me と同様です。"));
						item7.setItemMeta(itemmeta7);


						ItemStack close = new ItemStack(Material.STICK);
						ItemMeta closemeta = close.getItemMeta();
						closemeta.setDisplayName(ChatColor.GOLD  + "画面を閉じる");
						closemeta.setLore(Arrays.asList(ChatColor.YELLOW + "GUIを閉じます。", ChatColor.WHITE + "作者:syokkendesuyo", ChatColor.WHITE + "ご利用ありがとうございます。"));
						close.setItemMeta(closemeta);

						//インベントリに配置する
						Inventory inv = Bukkit.createInventory(p, 18 ," □ホワイトリスト管理画面□ ");
						inv.setItem(0, item0);
						inv.setItem(1, item1);
						inv.setItem(2, item2);
						inv.setItem(3, item3);
						inv.setItem(4, item4);
						inv.setItem(5, item5);
						inv.setItem(6, item6);
						inv.setItem(7, item7);
						inv.setItem(17, close);

						//インベントリを開ける
						p.openInventory(inv);
					}
				}
			}
		}
		else{
			p.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
		}
	}
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event){
		if (event.getInventory().getName().equalsIgnoreCase(" □ホワイトリスト管理画面□ ")){
			if (event.getRawSlot() < 54 && event.getRawSlot() > -1){
				Player player = (Player) event.getWhoClicked();
				if(event.isRightClick() || event.isLeftClick()|| event.getAction()==InventoryAction.HOTBAR_SWAP || event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || event.getAction() == InventoryAction.DROP_ONE_SLOT ||event.getAction() == InventoryAction.DROP_ALL_SLOT){
					if(event.getRawSlot()==0){
						status(player);
					}

					if(event.getRawSlot()==1){
						list(player);
					}

					if(event.getRawSlot()==2){
						on(player);
					}

					if(event.getRawSlot()==3){
						off(player);
					}

					if(event.getRawSlot()==4){
						toggle(player);
					}

					if(event.getRawSlot()==5){
						register(player);
					}

					if(event.getRawSlot()==6){
						removeall(player);
					}

					if(event.getRawSlot()==7){
						me(player);
					}
					if(event.getRawSlot()==17){
						player.sendMessage(ChatColor.YELLOW + "作者:syokkendesuyo" + ChatColor.WHITE + " ご利用ありがとうございます。");
					}
					player.closeInventory();
					event.setCancelled(true);
				}
			}
		}
	}
	public void status(CommandSender sender){
		if(sender.hasPermission("wla.status")){
			if(Bukkit.hasWhitelist()==true){
				sender.sendMessage(ChatColor.AQUA + "□ ホワイトリストのステイタス："+ ChatColor.GREEN +"有効");
			}
			else{
				sender.sendMessage(ChatColor.AQUA + "□ ホワイトリストのステイタス："+ ChatColor.RED +"無効");
			}
		}
		else{
			sender.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
		}
	}
	public void list(CommandSender sender){
		if(sender.hasPermission("wla.list")){
			if(Bukkit.getServer().getWhitelistedPlayers().size()>0){
				sender.sendMessage(ChatColor.GRAY + "＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
				sender.sendMessage(ChatColor.GREEN + "□ ホワイトリストの登録者名簿：");
				for (OfflinePlayer player : Bukkit.getServer().getWhitelistedPlayers()){
					String name = player.getName();
					sender.sendMessage(ChatColor.AQUA + "- " + name);
				}
				sender.sendMessage(ChatColor.GRAY + "＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
			}
			else{
				sender.sendMessage(ChatColor.RED + "□ ホワイトリストには誰も登録されていません。");
			}
		}
		else{
			sender.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
		}
	}
	public void on(CommandSender sender){
		if(sender.hasPermission("wla.on")){
			if(Bukkit.getServer().hasWhitelist()==true){
				sender.sendMessage(ChatColor.RED + "□ ホワイトリストは既に有効です！");
			}
			else if(Bukkit.getServer().hasWhitelist()==false){
				sender.sendMessage(ChatColor.AQUA + "□ ホワイトリストを"+ ChatColor.GREEN +"有効"+ ChatColor.AQUA +"にしました。");
				Bukkit.getServer().setWhitelist(true);
			}
		}
		else{
			sender.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
		}
	}
	public void off(CommandSender sender){
		if(sender.hasPermission("wla.off")){
			if(Bukkit.getServer().hasWhitelist()==true){
				sender.sendMessage(ChatColor.AQUA + "□ ホワイトリストを"+ ChatColor.RED +"無効"+ ChatColor.AQUA +"にしました。");
				Bukkit.getServer().setWhitelist(false);
			}
			else if(Bukkit.getServer().hasWhitelist()==false){
				sender.sendMessage(ChatColor.RED + "□ ホワイトリストは既に無効です。");
			}
		}
		else{
			sender.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
		}
	}
	public void toggle (CommandSender sender){
		if(sender.hasPermission("wla.toggle")){
			if(Bukkit.getServer().hasWhitelist()==true){
				sender.sendMessage(ChatColor.AQUA + "□ ホワイトリストを"+ ChatColor.RED +"無効"+ ChatColor.AQUA +"にしました。");
				Bukkit.getServer().setWhitelist(false);
			}
			else if(Bukkit.getServer().hasWhitelist()==false){
				sender.sendMessage(ChatColor.AQUA + "□ ホワイトリストを"+ ChatColor.GREEN +"有効"+ ChatColor.AQUA +"にしました。");
				Bukkit.getServer().setWhitelist(true);
			}
		}
		else{
			sender.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
		}
	}
	public void register (CommandSender sender){
		if(sender.hasPermission("wla.register") || sender.hasPermission("wla.@a")){
			sender.sendMessage(ChatColor.GRAY + "＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
			for (Player player : Bukkit.getServer().getOnlinePlayers()){
				String name = player.getName();
				player.setWhitelisted(true);
				sender.sendMessage(ChatColor.AQUA + "- " + name + " をホワイトリストに追加しました。");
			}
			sender.sendMessage(ChatColor.GREEN + "□ ホワイトリストにオンラインの全プレイヤーを追加しました。");
			sender.sendMessage(ChatColor.GRAY + "＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		}
		else{
			sender.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
		}
	}
	public void removeall (CommandSender sender){
		if(sender.hasPermission("wla.remove-all")){
			sender.sendMessage(ChatColor.GRAY + "＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
			for (OfflinePlayer player : Bukkit.getServer().getWhitelistedPlayers()){
				String name = player.getName();
				player.setWhitelisted(false);
				sender.sendMessage(ChatColor.AQUA + "- " + name + " をホワイトリストから削除しました。");
			}
			sender.sendMessage(ChatColor.GREEN + "□ ホワイトリスに登録されている全プレイヤーを削除しました。");
			sender.sendMessage(ChatColor.GRAY + "＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		}
		else{
			sender.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
		}
	}
	public void add (CommandSender sender , String[] args){
		if(sender.hasPermission("wla.add")){
			if(args.length==1){
				sender.sendMessage(ChatColor.AQUA + "□ /wla add <名前> で<名前>をホワイトリストに登録できます");
			}
			else{
				String name = args[1].toString();
				Bukkit.getOfflinePlayer(args[1]).setWhitelisted(true);
				sender.sendMessage(ChatColor.AQUA + "- " + name + " をホワイトリストに追加しました。");
			}
		}
		else{
			sender.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
		}
	}
	public void remove (CommandSender sender , String[] args){
		if(sender.hasPermission("wla.remove")){
			if(args.length==1){
				sender.sendMessage(ChatColor.AQUA + "□ /wla remove <名前> で<名前>をホワイトリストから削除できます");
			}
			else{
				String name = args[1].toString();
				Bukkit.getOfflinePlayer(args[1]).setWhitelisted(false);
				sender.sendMessage(ChatColor.AQUA + "- " + name + " をホワイトリストから削除しました。");
			}
		}
		else{
			sender.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
		}
	}
	public void me (CommandSender sender){
		if(sender.hasPermission("wla.me")){
			String name =sender.getName();
			OfflinePlayer player = Bukkit.getPlayer(name);
			if(player.isWhitelisted()==true){
				sender.sendMessage(ChatColor.GREEN + "□ " + name + " はホワイトリストに登録されています。");
			}
			else if(player.isWhitelisted()==false){
				sender.sendMessage(ChatColor.RED + "□ " + name + " はホワイトリストに登録されていません。");
			}
		}
		else{
			sender.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
		}

	}
	public void adds (CommandSender sender , String[] args){
		if(sender.hasPermission("adds")){
			if(args.length==1){
				sender.sendMessage(ChatColor.AQUA + "□ /wla adds <名前1> <名前2> ... でホワイトリストに複数人登録できます");
			}
			else{
				int cnt = 0;
				for(int n = args.length-1 ; n>cnt ; cnt++){
					String name = args[cnt+1].toString();
					Bukkit.getOfflinePlayer(args[cnt+1]).setWhitelisted(true);
					sender.sendMessage(ChatColor.AQUA + "- " + name + " をホワイトリストに追加しました。");
				}
			}
		}
		else{
			sender.sendMessage(ChatColor.RED + "□ パーミッションがありません。");
		}
	}
	public void help(CommandSender sender , Command cmd){
		sender.sendMessage("");
		sender.sendMessage(ChatColor.YELLOW + "＝＝＝＝＝ Whitelist-All Help ＝＝＝＝＝");
		sender.sendMessage(ChatColor.UNDERLINE + "Auther : syokkendesuyo");
		sender.sendMessage("");
		if(cmd.getName().equalsIgnoreCase("wla")){
			sender.sendMessage(ChatColor.WHITE + "※ wlaコマンドはwhitelist-add-inのエイリアスです。");
		}
		else if(cmd.getName().equalsIgnoreCase("whitelist-all")){
			sender.sendMessage(ChatColor.WHITE + "※ whitelist-allコマンドはエイリアス、wlaが利用可能です。");
		}
		sender.sendMessage("");
		sender.sendMessage(ChatColor.WHITE + "□ /wla help ：当プラグインのヘルプ");
		sender.sendMessage(ChatColor.WHITE + "□ /wla tool ：GUIを表示して簡単マネージメント！");
		sender.sendMessage(ChatColor.WHITE + "□ /wla status ：ホワイトリストを有効化");
		sender.sendMessage(ChatColor.WHITE + "□ /wla on/off ：ホワイトリストが無効化");
		sender.sendMessage(ChatColor.WHITE + "□ /wla toggle ：ホワイトリストをトグル");
		sender.sendMessage(ChatColor.WHITE + "□ /wla register・@a ：現在ログイン中のプレイヤーをホワイトリストに登録");
		sender.sendMessage(ChatColor.WHITE + "□ /wla remove-all ：ホワイトリストに登録されているプレイヤーを全員削除");
		sender.sendMessage(ChatColor.WHITE + "□ /wla add・remove <名前> ：<名前>をホワイトリストへ追加・削除");
		sender.sendMessage(ChatColor.WHITE + "□ /wla adds <名前1> <名前2> ... :ホワイトリストに複数人登録できます");
		sender.sendMessage(ChatColor.WHITE + "□ /wla me ：自分がホワイトリストに所属しているか確認します。");
		sender.sendMessage(ChatColor.YELLOW + "＝＝＝＝＝ ＝＝＝＝＝＝＝＝＝ ＝＝＝＝＝");
		sender.sendMessage("");
	}
}