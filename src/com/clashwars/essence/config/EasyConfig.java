package com.clashwars.essence.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Inspired by md_5
 * <p>
 * An awesome super-duper-lazy Config lib!
 * Just extend it, set some (non-static) variables
 *
 * @author codename_B
 * @version 2.1
 */
public abstract class EasyConfig {

    private transient File file = null;
    private transient YamlConfiguration conf = new YamlConfiguration();

    /**
     * Must be called before using config.load() or config.save();
     *
     * @param input
     * @return (Config) instance
     */
    public EasyConfig setFile(Object input) {
        // handle the File
        if (input == null) {
            new InvalidConfigurationException("File cannot be null!").printStackTrace();
        } else if (input instanceof File) {
            // the file, directly
            file = (File) input;
        } else if (input instanceof Plugin) {
            // the config.yml of the plugin
            file = getFile((Plugin) input);
        } else if (input instanceof String) {
            // the literal file from the string
            file = new File((String) input);
        }
        return this;
    }

    /**
     * Lazy load
     */
    public void load() {
        if (file != null) {
            try {
                onLoad(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new InvalidConfigurationException("File cannot be null!").printStackTrace();
        }
    }

    /**
     * Lazy save
     */
    public void save() {
        if (file != null) {
            try {
                onSave(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new InvalidConfigurationException("File cannot be null!").printStackTrace();
        }
    }

    /**
     * Internal method - used by load();
     *
     * @param file
     * @throws Exception
     */
    private void onLoad(File file) throws Exception {
        if (!file.exists()) {
            if (file.getParentFile() != null)
                file.getParentFile().mkdirs();
            file.createNewFile();
        }
        conf.load(file);
        for (Field field : getClass().getDeclaredFields()) {
            String path = field.getName().replaceAll("__", "");
            if (doSkip(field)) {
                // don't touch it
            }
            if (conf.isSet(path)) {
                field.set(this, toBukkit(conf.get(path), field, path));
            } else {
                conf.set(path, toConfig(field.get(this), field, path));
            }
        }
        conf.save(file);
    }

    /**
     * Internal method - used by save();
     *
     * @param file
     * @throws Exception
     */
    private void onSave(File file) throws Exception {
        if (!file.exists()) {
            if (file.getParentFile() != null)
                file.getParentFile().mkdirs();
            file.createNewFile();
        }
        for (Field field : getClass().getDeclaredFields()) {
            String path = field.getName().replaceAll("__", "");
            if (doSkip(field)) {
                // don't touch it
            } else {
                conf.set(path, toConfig(field.get(this), field, path));
            }
        }
        conf.save(file);
    }

    /*
     * Main conversion methods
     */

    private Object toBukkit(Object in, Field field, String path) throws Exception {
        if (isConfigurationSection(in)) {
            return getMap((ConfigurationSection) in, field, path);
        } else {
            return in;
        }
    }

    @SuppressWarnings("rawtypes")
    private Object toConfig(Object out, Field field, String path) throws Exception {
        if (isMap(out)) {
            return getMap((Map) out, field, path);
        } else {
            return out;
        }
    }

    /*
     * Checkers
     */

    private boolean isJSON(Object o) {
        try {
            if (o instanceof String) {
                String s = (String) o;
                if (s.startsWith("{")) {
                    return new JSONParser().parse(s) != null;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings("rawtypes")
    private boolean isMap(Object o) {
        try {
            return (Map) o != null;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isConfigurationSection(Object o) {
        try {
            return (ConfigurationSection) o != null;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * Converters
     */

    @SuppressWarnings({"rawtypes", "unchecked"})
    private ConfigurationSection getMap(Map data, Field field, String path) throws Exception {
        ConfigurationSection cs = conf.createSection(path);
        Set<String> keys = data.keySet();
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                Object out = data.get(key);
                out = toConfig(out, field, path);
                cs.set(key, out);
            }
            return cs;
        }
        return cs;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Map getMap(ConfigurationSection data, Field field, String path) throws Exception {
        Set<String> keys = data.getKeys(false);
        Map map = new HashMap();
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                Object in = data.get(key);
                in = toBukkit(in, field, path);
                map.put(key, in);
            }
            return map;
        }
        return map;
    }

    /*
     * Utility methods
     */

    /**
     * A little internal method to save re-using code
     *
     * @param field
     * @return skip
     */
    private boolean doSkip(Field field) {
        return Modifier.isTransient(field.getModifiers()) || Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers());
    }

    private File getFile(Plugin plugin) {
        return new File(plugin.getDataFolder(), "config.yml");
    }

}