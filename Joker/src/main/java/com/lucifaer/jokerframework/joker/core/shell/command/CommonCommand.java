package com.lucifaer.jokerframework.joker.core.shell.command;

import com.lucifaer.jokerframework.joker.core.context.JokerContext;
import com.lucifaer.jokerframework.joker.core.shell.ShellHelper;
import com.lucifaer.jokerframework.joker.core.task.CommonTask;
import com.lucifaer.jokerframework.joker.core.task.ExploitTask;
import com.lucifaer.jokerframework.joker.core.task.PluginInitTask;
import com.lucifaer.jokerframework.sdk.context.ShellContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
@ShellComponent
public class CommonCommand {
    private final JokerContext jokerContext;
    private final CommonTask commonTask;
    private final ExploitTask exploitTask;
    private final PluginInitTask pluginInitTask;
    private final ShellHelper shellHelper;

    public CommonCommand(JokerContext jokerContext, CommonTask commonTask, ExploitTask exploitTask, PluginInitTask pluginInitTask, ShellHelper shellHelper) {
        this.jokerContext = jokerContext;
        this.commonTask = commonTask;
        this.exploitTask = exploitTask;
        this.pluginInitTask = pluginInitTask;
        this.shellHelper = shellHelper;
    }

    @ShellMethod(value = "set param", key = "set", group = "Common")
    public String set(String configKey, String configValue) {
        ShellContext shellContext = (ShellContext) jokerContext.getCurrentShell();
        shellContext.getParams().put(configKey, configValue);
        if ("exploit".equals(shellContext.getContextType())) {
            if (configKey.equals("exploitName") && configValue != null) {
                pluginInitTask.createTask(shellContext);
            }
            else if (shellContext.getParams().containsKey("exploitName")) {
                return shellHelper.getSuccessMessage(String.format("set %s with %s", configKey, configValue));
            }
            else {
                return shellHelper.getErrorMessage("must set exploitName to use the exploit plugin");
            }
        }

        return shellHelper.getSuccessMessage(String.format("set %s with %s", configKey, configValue));
    }

    @ShellMethod(value = "list exist mods", key = "list", group = "Common")
    public void list(String type) {
        switch (type) {
            case "session":
                for (String existSession : commonTask.listTask().keySet()) {
                    shellHelper.echoInfo(existSession);
                }
                break;
            case "exploit":
                for (String existExploit : exploitTask.listTask().keySet()) {
                    shellHelper.echoInfo(existExploit);
                }
                break;
            default:
                shellHelper.echoInfo("list task");
        }
    }
}
