package com.khesam.papyrus.gateway.exception;

public abstract class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class IOStorageException extends StorageException {

        public IOStorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class EmptyRootLocationStorageException extends StorageException {

        public EmptyRootLocationStorageException(String message) {
            super(message);
        }
    }
}
