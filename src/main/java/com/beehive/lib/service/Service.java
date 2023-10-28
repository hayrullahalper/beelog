package com.beehive.lib.service;

import com.beehive.annotations.Injectable;
import com.beehive.errors.service.ServiceComplexityError;
import com.beehive.errors.service.ServiceInheritanceError;
import com.beehive.errors.service.ServiceInitializationError;
import com.beehive.errors.service.ServiceInjectableAnnotationNotFoundError;
import com.beehive.lib.injector.Injector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public abstract class Service extends Injector {

  public static void check(Class<?> clazz) {
    if (!clazz.isAnnotationPresent(Injectable.class)) {
      throw new ServiceInjectableAnnotationNotFoundError(clazz.getName());
    }

    if (!Service.class.isAssignableFrom(clazz)) {
      throw new ServiceInheritanceError(clazz.getName());
    }
  }

  public static List<Class<?>> sort(List<Class<?>> services) {
    return services.stream()
      .sorted(Comparator.comparingInt(Service::getWeight))
      .toList();
  }

  public static Service create(Class<?> clazz) {
    try {
      if (!Service.class.isAssignableFrom(clazz)) {
        throw new ServiceInheritanceError(clazz.getName());
      }

      return (Service) clazz.getDeclaredConstructor(new Class<?>[] {}).newInstance();
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new ServiceInitializationError(clazz.getName());
    }
  }

  public static int getWeight(Class<?> clazz) {
    var primes = new int[] {2, 3, 5, 7, 11, 13, 17, 19, 23};

    List<Field> fields = Stream.of(clazz.getDeclaredFields())
      .filter(field -> Service.class.isAssignableFrom(field.getType()))
      .toList();

    if (fields.isEmpty()) {
      return 0;
    }

    if (fields.size() > primes.length) {
      throw new ServiceComplexityError();
    }

    int weight = primes[fields.size() - 1];

    int dependencyWeight = fields.stream().mapToInt(field -> {
      Class<?> fieldType = field.getType();

      if (Service.class.isAssignableFrom(fieldType)) {
        return getWeight(fieldType);
      }

      return 0;
    }).sum();

    return weight + dependencyWeight;
  }
}
