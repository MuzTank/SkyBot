package me.duncte123.skybot.utils;

import net.dv8tion.jda.core.utils.SimpleLog;

public class CloudLogListener implements SimpleLog.LogListener{
    @Override
    public void onLog(SimpleLog log, SimpleLog.Level logLevel, Object message) {
        if(logLevel.getPriority() > 2) {
            AirUtils.log(log.name, CustomLog.Level.valueOf(logLevel.name()), message);
        }
    }

    @Override
    public void onError(SimpleLog log, Throwable err) {
        AirUtils.logger2.log(err);
    }
}
