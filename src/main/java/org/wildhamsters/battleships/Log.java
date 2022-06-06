package org.wildhamsters.battleships;

@ExcludeFromJacocoGeneratedReport
record Log(String level, String timeStamp, String microService, String className, String logMsg) {
    enum Level {
        ERROR,
        DEBUG,
        INFO
    }
}
