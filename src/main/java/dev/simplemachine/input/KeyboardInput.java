package dev.simplemachine.input;

public record KeyboardInput(int glKeyCode, KeyboardInputType inputType, KeyboardInputModifier... modifiers) {
}
