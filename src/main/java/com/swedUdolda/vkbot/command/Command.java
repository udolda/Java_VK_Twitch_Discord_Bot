package com.swedUdolda.vkbot.command;

public abstract class Command {
    private final String name;

    public String getName() {
        return name;
    }

    protected Command(String name) { this.name = name; }

    public abstract void exec(int peerId, String message);

    @Override
    public String toString() { return this.name; }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Command){
            if (name.equals(((Command) obj).name)){
                return true;
            }
        }
        return false;
    }
}
