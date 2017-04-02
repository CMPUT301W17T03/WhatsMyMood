package com.example.whatsmymood;

import java.util.ArrayList;

/**
 * Created by Alex on 3/26/2017.
 *
 * Commands are added to the queue when the app is offline
 */

public class CommandQueue {

    private static final CommandQueue ourInstance = new CommandQueue();

    private ArrayList<Command> queue = new ArrayList<Command>();

    public static CommandQueue getInstance() {
        return ourInstance;
    }

    private CommandQueue() {
    }

    public void addQueue(Command newCommand){
        queue.add(newCommand);
    }


    // Executes the entire queue of Commands
    public void executeAllCommands(){
        for(int i = 0 ; i < queue.size() ; i++){
            queue.get(i).executeCommand();
        }
        queue.clear();
    }
}