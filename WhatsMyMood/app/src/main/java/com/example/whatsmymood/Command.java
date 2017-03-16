package com.example.whatsmymood;

/**
 * Created by Alex on 3/16/2017.
 */

public abstract class Command {
    public abstract void execute();
    public abstract void unexecute();
    public abstract boolean isReversable();
}
