package com.beehive.lib.Injector;

import com.beehive.lib.Factory.Factory;
import com.beehive.lib.Service.Service;

import javax.validation.ConstraintViolation;
import java.util.Set;

public abstract class Injector {

    public final <T extends Service> T service(Class<T> service) {
        return Factory.getInstance().getService(this.getClass(), service);
    }

    public <T> Set<ConstraintViolation<T>> validate(T object) {
        return Factory.getInstance().getValidator().validate(object);
    }
}
