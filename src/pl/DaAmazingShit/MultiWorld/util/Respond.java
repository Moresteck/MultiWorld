package pl.DaAmazingShit.MultiWorld.util;

import pl.DaAmazingShit.MultiWorld.MultiWorldMain;

public enum Respond {
	
	unknownError("Undefined error. See console."),
	notEnoughArguments("Not enough arguments!"),
	notRecognised("World could not be recognised!"),
	createdSuccess("World §e<WORLD>§f created successfully!"),
	clearSuccess("Cleared §b<NUM>§f entities from §e<WORLD>§f."),
	removedSuccess("World §e<WORLD>§f removed successfully!"),
	importedSuccess("World §e<WORLD>§f imported successfully!"),
	errorCreate("World §e<WORLD>§f could not be created! See console."),
	errorWhere("An error occured. Probably the player does not exists?"),
	errorRemove("World §e<WORLD>§f could not be removed! See console."),
	errorImport("World §e<WORLD>§f could not be imported! See console."),
	who("§e<WORLD>§f has §b<NUM>§f player(s): <PLAYERS>"),
	where("§e<PLAYER>§f is in §e<WORLD>§f."),
	info("Worldname: §e<WORLD>§f+Environment: §<CC><WORLD>§f+Entities on the ground: §a<NUM>§f+Players in (§b<NUM>§f): <PLAYERS>"),
	help(" §aMultiWorld help (page 1)+ §e/createworld§f <world> <environment>+ §e/importworld§f <world> <environment>+ §e/whoisin§f [world]+ §e/whereis§f <world>+ §e/clearworld§f [world]+ §e/worldinfo§f [world]+ §e/worldtp§f <world> [player]+ See §e/multiworld 2§f for more help."),
	help2(" §aMultiWorld help (page 2)+ §eEnvironments can be: §anormal §for §cnether§f.+ Plugin created by §bDaAmazingShit§f, §92017§f.+ Version: §6" + MultiWorldMain.mwm.getDescription().getVersion() + "+ §fServer version: §c" + MultiWorldMain.version);
	
	private Respond(String str) {
		
	}
	
	
	// XXX §+{a,b,c,d,e,f,1,2,3,4,5,6,7,8,9,0}
	
	/**
	 *
	public static String notEnough        = "Not enough arguments!";
	public static String notRecognised    = "World could not be recognised!";
	public static String createdSuccess   = "World §e<WORLD>§f created successfully!";
	public static String removedSuccess   = "World §e<WORLD>§f removed successfully!";
	public static String importedSuccess  = "World §e<WORLD>§f imported successfully!";
	public static String who              = "§e<WORLD>§f has §b<NUM>§f player(s): <PLAYERS>";
	public static String infoLine1        = "Worldname: §e<WORLD>";
	public static String infoLine2        = "Environment: §<CC><WORLD>§f";
	public static String infoLine3        = "Entities on the ground: §a<NUM>§f";
	*
	**/
}
