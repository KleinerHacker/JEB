package org.pcsoft.framework.jeb;

import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.config.JEBConfigurationBuilder;
import org.pcsoft.framework.jeb.config.JEBConfigurationFactory;
import org.pcsoft.framework.jeb.utils.bus.EventBusBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pfeifchr on 26.05.2016.
 */
public final class EventBusManager {
    private static final String DEFAULT_NAME = "event_bus.default";

    public static EventBusManager create(final JEBConfigurationBuilder configurationBuilder) {
        return new EventBusManager(configurationBuilder.build());
    }

    public static EventBusManager create() {
        return create(JEBConfigurationFactory.createDefaultBuilder());
    }

    private final JEBConfiguration configuration;
    private final Map<String, EventBus> eventBusMap = new HashMap<>();

    private EventBusManager(JEBConfiguration configuration) {
        this.configuration = configuration;
    }

    public JEBConfiguration getConfiguration() {
        return configuration;
    }

    public EventBus getEventBus() {
        return getEventBus(DEFAULT_NAME);
    }

    public EventBus getEventBus(final String name) {
        return getEventBus(name, DefaultEventBus.class);
    }

    public <T extends EventBus>T getEventBus(final String name, final Class<T> eventBusClass) {
        if (!eventBusMap.containsKey(name)) {
            eventBusMap.put(name, EventBusBuilder.build(eventBusClass, configuration));
        }

        final EventBus eventBus = eventBusMap.get(name);
        if (!eventBusClass.isAssignableFrom(eventBus.getClass()))
            throw new IllegalStateException("Found class of event bus (" + eventBus.getClass().getName() + ") is not the expected parameter class: " + eventBusClass.getName());

        return (T) eventBus;
    }

    public void destroyEventBus(final String name) {
        if (!eventBusMap.containsKey(name))
            return;

        eventBusMap.remove(name);
    }
}
