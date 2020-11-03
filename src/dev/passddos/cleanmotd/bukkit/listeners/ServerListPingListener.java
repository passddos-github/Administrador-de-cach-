package dev._2lstudios.cleanmotd.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import dev._2lstudios.cleanmotd.bukkit.variables.Variables;

public class ServerListPingListener implements Listener {
	private final Variables variables;

	public ServerListPingListener(final Variables variables) {
		this.variables = variables;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onServerListPing(final ServerListPingEvent event) {
		// WARNING!!!
		// There is no way to get player name on Bukkit.
		// There is no way to set player count on Bukkit.
		// Bukkit already has cache for Favicon.

		final int onlinePlayers = event.getNumPlayers();
		int maxPlayers = event.getMaxPlayers();

		if (variables.isMaxPlayersEnabled()) {
			if (variables.isMaxPlayersJustOneMore()) {
				maxPlayers = onlinePlayers + 1;
			} else {
				maxPlayers = variables.getMaxPlayers();
			}

			event.setMaxPlayers(maxPlayers);
		}

		if (variables.isMotdEnabled()) {
			event.setMotd(variables.getMOTD(maxPlayers, onlinePlayers));
		}
	}
}
