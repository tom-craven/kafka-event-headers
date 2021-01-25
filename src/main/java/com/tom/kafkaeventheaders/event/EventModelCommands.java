package com.tom.kafkaeventheaders.event;

public class EventModelCommands {

    public enum ServiceComponent {
        FOO,
        BAR
    }

    public enum EventState {
        TRYING,
        SUCCESS,
        FAIL,
        WARN
    }
}
