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
	
	private Respond(String str) {}
}
