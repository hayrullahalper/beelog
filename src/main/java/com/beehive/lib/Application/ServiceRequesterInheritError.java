package com.beehive.lib.Application;

public class ServiceRequesterInheritError extends Error {
    public ServiceRequesterInheritError() {
        super("ServiceRequester must inherit from Service or Controller");
    }
}
