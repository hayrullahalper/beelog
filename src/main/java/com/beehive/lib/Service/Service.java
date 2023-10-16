package com.beehive.lib.Service;

import com.beehive.lib.Injector.Injector;
import com.beehive.annotations.Injectable;
import com.beehive.lib.Repository.Repository;
import com.beehive.lib.Repository.RepositoryNotFoundError;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.lang.reflect.Field;
import java.util.stream.Stream;

public abstract class Service extends Injector {
    private static final int[] weights = new int[] { 0, 2, 3, 5, 7, 11, 13, 17, 19, 23 };

    public static void check(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Injectable.class)) {
            throw new InjectableNotFoundError(clazz.getName());
        }

        if (!Service.class.isAssignableFrom(clazz)) {
            throw new ServiceInheritError(clazz.getName());
        }
    }

    public final boolean isPublic() {
        check(this.getClass());

        Class<?> clazz = this.getClass();

        Injectable annotation = clazz.getAnnotation(Injectable.class);

        return annotation.value() == Injectable.Permission.PUBLIC;
    }

    public static int getWeight(Class<?> clazz) {
        int weight = 0;

        List<Field> fields = Stream.of(clazz.getDeclaredFields())
                .filter(field -> Service.class.isAssignableFrom(field.getType()))
                .toList();

        try {
            weight += weights[fields.size()];

            for (Field field : fields) {
                Class<?> fieldType = field.getType();

                if (Service.class.isAssignableFrom(fieldType)) {
                    weight += getWeight(fieldType);
                }
            }

            return weight;
        } catch (IndexOutOfBoundsException e) {
            throw new MaxServiceDepthError();
        }
    }

    public <T extends Repository<?>> T repository(Class<T> clazz) {
        try {
            return  clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RepositoryNotFoundError(clazz.getName());
        }
    }
}
