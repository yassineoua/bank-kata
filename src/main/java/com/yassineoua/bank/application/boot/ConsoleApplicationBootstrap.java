package com.yassineoua.bank.application.boot;

import com.yassineoua.bank.application.context.ApplicationContext;

public class ConsoleApplicationBootstrap implements ApplicationBootstrap {

    private ConsoleManager consoleManager;
    private RequestHandler requestHandler = new RequestHandler();

    @Override
    public void start(ApplicationContext applicationContext) {
        init(applicationContext);
        showBanner();
        requestHandler.handleRequests(applicationContext);
    }

    private void init(ApplicationContext applicationContext) {
        consoleManager = applicationContext.getComponent(ConsoleManager.class);
    }

    private void showBanner() {
        consoleManager.print("  ,--.                                                                            ,--.     \n" +
                " .-,|  |,-.      ,--.   ,--.         ,--.                                        .-,|  |,-.  \n" +
                " _\\ '  ' /_      |  |   |  |  ,---.  |  |  ,---.  ,---.  ,--,--,--.  ,---.       _\\ '  ' /_  \n" +
                "(__      __)     |  |.'.|  | | .-. : |  | | .--' | .-. | |        | | .-. :     (__      __) \n" +
                "  / .  . \\       |   ,'.   | \\   --. |  | \\ `--. ' '-' ' |  |  |  | \\   --.       / .  . \\   \n" +
                " `-'|  |`-'      '--'   '--'  `----' `--'  `---'  `---'  `--`--`--'  `----'      `-'|  |`-'  \n" +
                "    `--'                                                                            `--'     ");
    }

}
