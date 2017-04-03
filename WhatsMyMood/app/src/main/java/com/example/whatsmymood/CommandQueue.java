package com.example.whatsmymood;

import java.util.ArrayList;

/**
 * Created by Alex on 3/26/2017.
 *
 * Commands are added to the queue when the app is offline.
 * When the app comes back online, all commands will be excuted.
 * Is a singleton so that multiple activities can access the same queue.
 */

public class CommandQueue {

    private static final CommandQueue ourInstance = new CommandQueue();

    private Command queue;

    /**
     * Returns instance 
     * @return
     */
    public static CommandQueue getInstance() {
        return ourInstance;
    }

    private CommandQueue() {
    }

    public void addQueue(Command newCommand){
        queue = newCommand;
    }

    // Executes the entire queue of Commands
    public void executeAllCommands(){
        queue.executeCommand();
    }
}