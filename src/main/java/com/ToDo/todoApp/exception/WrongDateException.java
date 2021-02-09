package com.ToDo.todoApp.exception;

import lombok.Getter;

@Getter
public class WrongDateException extends RuntimeException{
    public WrongDateException (String message){super(message);}
}
