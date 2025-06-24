package org.andrexserver.overseer.Commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import org.andrexserver.overseer.Main;


public class ProxyCount implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (!source.hasPermission("oversser.proxycount")) {
            Main.sendMessage(source,"§cSorry, You don't have permission to use this command!");
            return;
        }

        if (args.length != 0) {
            Main.sendMessage(source,"§cUsage /proxycount");
            return;
        }
        Main.sendMessage(source,"§aThere are §6" + Main.instance.proxy.getAllPlayers().size() + "§a Players connected to the §6proxy");
    }
}
