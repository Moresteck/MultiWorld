package pl.moresteck.multiworld;

import java.util.logging.Logger;

public class MLogger {
	private static Logger log;

	public MLogger(String str) {
		log = Logger.getLogger(str);
	}

	public void info(int i) {
		info(false, Integer.toString(i));
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

	public void warning(String str) {
		log.warning(str);
	}
}
