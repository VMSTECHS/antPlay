package com.vms.antplay.model;

public class GamePad_RawModal {

    private String Input;
    private String State;
    private String Min;
    private String Max;

    public GamePad_RawModal(String input, String state, String min, String max) {
        Input = input;
        State = state;
        Min = min;
        Max = max;
    }

    public String getInput() {
        return Input;
    }

    public void setInput(String input) {
        Input = input;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getMin() {
        return Min;
    }

    public void setMin(String min) {
        Min = min;
    }

    public String getMax() {
        return Max;
    }

    public void setMax(String max) {
        Max = max;
    }
}
