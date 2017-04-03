package com.example.whatsmymood;

import java.util.ArrayList;

/**
 * Commands are added to the queue when the app is offline.
 * When the app comes back online, all commands will be excuted.
 * Is a singleton so that multiple activities can access the same queue.
 * @author Alex
 * @version 1.0 , 3/26/2017.
 */

public class CommandQueue {

    private static final CommandQueue ourInstance = new CommandQueue();

    private Command queue;

    /**
     * Returns instance of the command queue.
     * @return
     */
    public static CommandQueue getInstance() {
        return ourInstance;
    }


    private CommandQueue() {
    }

    /**
     * Add a new command to the command queue.
     * @param newCommand
     */
    public void addQueue(Command newCommand){
        queue = newCommand;
    }

    /**
     * Executes the entire queue of Commands.
     */
    public void executeAllCommands(){
        queue.executeCommand();
    }
}