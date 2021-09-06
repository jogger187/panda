package com.imakerstudio.pandaui.EditText;

public class Generic<T> {
    private T value;
    public Generic(T key) {
        this.value = key;
    }
    public T getValue(){
        return value;
    }
}
