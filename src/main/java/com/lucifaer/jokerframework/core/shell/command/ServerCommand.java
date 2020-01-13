package com.lucifaer.jokerframework.core.shell.command;

import com.lucifaer.jokerframework.core.factory.JokerServerFactory;
import com.lucifaer.jokerframework.core.shell.config.JokerShellHelper;
import com.lucifaer.jokerframework.data.CommandManagerContext;
import com.lucifaer.jokerframework.data.JokerContext;
import com.lucifaer.jokerframework.data.ShellContext;
import com.lucifaer.jokerframework.plugins.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.HashMap;
import java.util.Map;

@ShellComponent
public class ServerCommand {
    @Autowired
    JokerContext jokerContext;

    @Autowired
    JokerShellHelper jokerShellHelper;

    @Autowired
    JokerServerFactory jokerServerFactory;

    @ShellMethod(value = "use server model", key = "server", group = "Joker")
    public String doServer(String serverName) {
        ShellContext shellContext = shellContext();
        Map<String, String> params = new HashMap<>();
        params.put("serverName", serverName);
        shellContext.commandNode.push("server");
        shellContext.contextName = serverName;
        CommandManagerContext.setIsUsed(true);

        shellContext.setParams(params);
        jokerContext.shellRegister(shellContext);
        return jokerShellHelper.getSuccessMessage(String.format("Setup %s server", serverName));
    }

    @ShellMethod(value = "start server", key = "run", group = "Joker")
    public void doRun() throws Exception {
        Server server = jokerServerFactory.getObject();
        server.createServer();
    }

    @ShellMethod(value = "stop server", key = "stop", group = "Joker")
    public void doStop() throws Exception {
        Server server = jokerServerFactory.getObject();
        server.stopServer();
    }

    @Lookup
    protected ShellContext shellContext() {
        return null;
    }
}