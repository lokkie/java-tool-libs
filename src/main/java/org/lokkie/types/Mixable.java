package org.lokkie.types;

import org.lokkie.annotation.SkipMixing;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Allows to mixin data from one object to another instead changing it at all.
 * Supports skipping fields using annotation
 *
 * @author lokkie
 * @version 0.5
 * @see org.lokkie.annotation.SkipMixing
 */
public class Mixable {

    private List<Field> getAllFields(Class<?> type) {
        List<Field> result = new LinkedList<>();
        if (type.getSuperclass() != null) {
            result.addAll(getAllFields(type.getSuperclass()));
        }
        result.addAll(Arrays.asList(type.getDeclaredFields()));
        return result;
    }

    /**
     * Mixes {@code source} properties into this.
     * If {@code source} is not the same class as this, throws {@code RuntimeException}.
     * If property is sub-class of {@code Mixable} - mixes recursively.
     *
     * @param source Source object to collect data from
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws RuntimeException
     */
    public void mixin(Mixable source) throws IntrospectionException, InvocationTargetException, IllegalAccessException, RuntimeException {
        if (source == null) {
            return;
        }
        if (!source.getClass().equals(this.getClass()) && this.getClass().isAssignableFrom(source.getClass())) {
            throw new RuntimeException(source.getClass().getName() + " is not " + this.getClass().getName());
        }
        List<Field> fields = getAllFields(this.getClass());
        BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());
        Arrays.stream(beanInfo.getPropertyDescriptors())
                .filter(propertyDescriptor ->
                        propertyDescriptor.getReadMethod() != null &&
                                !propertyDescriptor.getReadMethod().isAnnotationPresent(SkipMixing.class) &&
                                propertyDescriptor.getWriteMethod() != null &&
                                fields.stream().anyMatch(field ->
                                        field.getName().equals(propertyDescriptor.getName()) && (
                                                !field.isAnnotationPresent(SkipMixing.class)
                                                        || field.getAnnotation(SkipMixing.class).nullOnly()
                                        )
                                )
                ).forEach(propertyDescriptor -> {
                    Method writeMethod = propertyDescriptor.getWriteMethod(),
                            readMethod = propertyDescriptor.getReadMethod();
                    try {
                        Object val = readMethod.invoke(source),
                                hereVal = readMethod.invoke(this);
                        if (val == null) {
                            return;
                        }
                        if (val instanceof Mixable && hereVal != null) {
                            ((Mixable) readMethod.invoke(this)).mixin((Mixable) val);
                        } else if (!(fields.stream().filter(field -> field.getName().equals(propertyDescriptor.getName())).findFirst().orElse(null).isAnnotationPresent(SkipMixing.class)
                                && hereVal != null)) {
                            writeMethod.invoke(this, val);
                        }
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
        );
    }
}