package pl.moresteck.multiworld;

import java.util.logging.Logger;

public class MLogger {
	static Logger log;

	public MLogger(String str) {
		log = Logger.getLogger(str);
	}

	public void info(String str) {
		info(false, str);
	}

	public void info(boolean debug, String str) {
		if (debug) {
			if (MConfig.debug()) {
				log.info(str);
			}
			return;
		} else {
			log.info(str);
		}
	}
}
