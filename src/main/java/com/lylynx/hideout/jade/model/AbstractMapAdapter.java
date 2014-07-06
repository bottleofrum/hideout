package com.lylynx.hideout.jade.model;

import com.lylynx.hideout.exception.HideoutException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 05.07.14
 * Time: 15:33
 */
public abstract class AbstractMapAdapter implements Map<String, Object> {
    @Override
    public int size() {
        throw new HideoutException("Unimplemented");
    }

    @Override
    public boolean isEmpty() {
        throw new HideoutException("Unimplemented");
    }

    @Override
    public boolean containsKey(final Object key) {
        throw new HideoutException("Unimplemented");
    }

    @Override
    public boolean containsValue(final Object value) {
        throw new HideoutException("Unimplemented");
    }

    @Override
    public Object get(final Object key) {
        throw new HideoutException("Unimplemented");
    }

    @Override
    public Object put(final String key, final Object value) {
        throw new HideoutException("Unimplemented");
    }

    @Override
    public Object remove(final Object key) {
        throw new HideoutException("Unimplemented");
    }

    @Override
    public void putAll(final Map<? extends String, ?> m) {
        throw new HideoutException("Unimplemented");
    }

    @Override
    public void clear() {
        throw new HideoutException("Unimplemented");
    }

    @Override
    public Set<String> keySet() {
        throw new HideoutException("Unimplemented");
    }

    @Override
    public Collection<Object> values() {
        throw new HideoutException("Unimplemented");
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        throw new HideoutException("Unimplemented");
    }
}
