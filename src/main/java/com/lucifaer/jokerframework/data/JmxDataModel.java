package com.lucifaer.jokerframework.data;

import com.lucifaer.jokerframework.data.BaseExploitDataModel;

import javax.management.ObjectInstance;
import java.util.Map;

public class JmxDataModel extends BaseExploitDataModel {
    public static String installUrl;
    public static String command;
    public static ObjectInstance mletBean;

    public static void init(Map<String, String> options) {
        for (String optionKey : options.keySet()) {
            switch (optionKey) {
                case "exploitName":
                    exploitName = optionsConfirm(options, optionKey);
                    break;
                case "exploitModeName":
                    exploitModeName = optionsConfirm(options, optionKey);
                    break;
                case "targetUrl":
                    targetUrl = optionsConfirm(options, optionKey);
                    break;
                case "installUrl":
                    installUrl = options.get(optionKey);
                    break;
                case "command":
                    command = options.get(optionKey);
                    break;
                default:
                    installUrl = null;
                    command = null;
            }
        }
    }
}
