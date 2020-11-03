package dev._2lstudios.cleanmotd.bukkit.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigurationUtil {
	final private Plugin plugin;

	public ConfigurationUtil(final Plugin plugin) {
		this.plugin = plugin;
	}

	public YamlConfiguration getConfiguration(String filePath) {
		final File dataFolder = plugin.getDataFolder();
		final File file = new File(filePath.replace("%datafolder%", dataFolder.toPath().toString()));

		if (file.exists())
			return YamlConfiguration.loadConfiguration(file);
		else return new YamlConfiguration();
	}

	public void createConfiguration(String file) {
		try {
			final File dataFolder = plugin.getDataFolder();

			file = file.replace("%datafolder%", dataFolder.toPath().toString());

			final File configFile = new File(file);

			if (!configFile.exists()) {
				final String[] files = file.split("/");
				final InputStream inputStream = plugin.getClass().getClassLoader().getResourceAsStream(files[files.length - 1]);
				final File parentFile = configFile.getParentFile();

				if (parentFile != null) parentFile.mkdirs();

				if (inputStream != null) {
					Files.copy(inputStream, configFile.toPath());
					System.out.print(("[%pluginname%] File " + configFile + " has been created!").replace("%pluginname%", plugin.getDescription().getName()));
				} else configFile.createNewFile();
			}
		} catch (final IOException e) {
			System.out.print(("[%pluginname%] Unable to create configuration file!").replace("%pluginname%", plugin.getDescription().getName()));
		}
	}

	public void saveConfiguration(final YamlConfiguration yamlConfiguration, final String file) {
		plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
			try {
				final File dataFolder = plugin.getDataFolder();

				yamlConfiguration.save(file.replace("%datafolder%", dataFolder.toPath().toString()));
			} catch (final IOException e) {
				System.out.print(("[%pluginname%] Unable to save configuration file!").replace("%pluginname%", plugin.getDescription().getName()));
			}
		});
	}

	public void deleteConfiguration(final String file) {
		plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
			final File file1 = new File(file);

			if (file1.exists()) file1.delete();
		});
	}
}