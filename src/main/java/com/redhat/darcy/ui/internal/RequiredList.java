package com.redhat.darcy.ui.internal;

import com.redhat.darcy.ui.DarcyException;
import com.redhat.darcy.util.ReflectionUtil;
import com.redhat.darcy.util.RequiredListBounds;

import java.lang.reflect.Field;
import java.util.List;

public class RequiredList<T> {
    private final List<T> list;
    private final RequiredListBounds bounds;
    private final Class<?> genericType;

    @SuppressWarnings("unchecked")
    public RequiredList(Field field, Object in) {
        this.genericType = ReflectionUtil.getGenericTypeOfCollectionField(field);

        this.bounds = new RequiredListBounds(field);

        try {
            this.list = (List<T>) field.get(in);
        } catch (IllegalAccessException iae) {
            throw new DarcyException("Couldn't access value of object", iae);
        } catch (ClassCastException cce) {
            throw new IllegalArgumentException("Can not cast field of object to List");
        }
    }

    public List<T> list() {
        return this.list;
    }

    public Class<?> genericType() {
        return this.genericType;
    }

    public int atMost() {
        return this.bounds.atMost();
    }

    public int atLeast() {
        return this.bounds.atLeast();
    }
}