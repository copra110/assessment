package com.mashreq.assessment.exceptions;

public class NoAvailableRoomException extends RuntimeException {

    public NoAvailableRoomException(String message) {
        super(message);
    }
}
