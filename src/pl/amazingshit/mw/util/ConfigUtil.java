package pl.amazingshit.mw.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.bukkit.util.config.Configuration;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.representer.Representer;
/**
 * ConfigUtil v1.2.1
 */
public class ConfigUtil extends Configuration {

	private String header;
	private File file;
	private Yaml jaml;

	public ConfigUtil(File file) {
		super(file);
		this.file = file;
		DumperOptions options = new DumperOptions();
		options.setIndent(4);
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		jaml = new Yaml(new SafeConstructor(), new Representer(), options);
	}

	public Map<String, Object> getAll() {
		return recursiveBuilder(root);
	}

	@SuppressWarnings("unchecked")
	protected Map<String, Object> recursiveBuilder(Map<String, Object> node) {
		Map<String, Object> map = new TreeMap<String, Object>();
		
		Set<String> keys = node.keySet();
		for( String k : keys ) {
			Object tmp = node.get(k);
			if( tmp instanceof Map<?,?> ) {
				Map<String, Object> rec = recursiveBuilder((Map <String,Object>) tmp);
				
				Set<String> subkeys = rec.keySet();
				for( String sk : subkeys ) {
					map.put(k + "." + sk, rec.get(sk));
				}
			}
			else {
				map.put(k, tmp);
			}
		}
		return map;
	}

	public List<String> getKeys() {
		return new ArrayList<String>(root.keySet());
	}

	public void setHeader(String ... headerLines)  {
		StringBuilder header = new StringBuilder();
		
		for (String line : headerLines) {
			if (header.length() > 0) {
				header.append("\r\n");
			}
			header.append(line);
		}
		setHeader(header.toString());
	}

	public String getHeader() {
        return header;
    }

	/**
	 * Use this to save header.
	 */
	public void save_() {
		FileOutputStream stream = null;
		File parent = file.getParentFile();
		
		if (parent != null) {
			parent.mkdirs();
		}
		try {
			stream = new FileOutputStream(file);
			OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
			if (header != null) {
				writer.append(header);
				writer.append("\r\n");
			}
			jaml.dump(root, writer);
		}
		catch (Exception ex) {}
		finally {
			try {
				if (stream != null) {
					stream.close();
				}
			}
			catch (IOException ex) {}
		}
	}
}