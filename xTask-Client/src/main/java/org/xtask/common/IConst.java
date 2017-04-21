package org.xtask.common;

/**
 * Created by zhxy on 17/4/21.
 */
public interface IConst {
    public static final String RootPath = "/task";
    public static final String AppName_Pattern = "/%s";
    public static final String EnableTaskPath_Pattern = RootPath+AppName_Pattern+"/enable";
    public static final String DisableTaskPath_Pattern = RootPath+AppName_Pattern+"/disable";
    public static final String WaitRunTaskPath_Pattern = RootPath+AppName_Pattern+"/waitRun";
    public static final String RuntimeTaskPath_Pattern = RootPath+AppName_Pattern+"/runtime";
    public static final String HistoryTaskPath_Pattern = RootPath+AppName_Pattern+"/history";

    public static final String ServersTaskPath_Pattern = RootPath+AppName_Pattern+"/servers";

    public static final String DATETIME_YYYYMMDDHHMMSS_FORMAT = "yyyyMMddHHmmss";
}
