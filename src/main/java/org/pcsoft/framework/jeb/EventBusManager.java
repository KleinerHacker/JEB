package org.pcsoft.framework.jeb;

import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.config.JEBConfigurationFactory;
import org.pcsoft.framework.jeb.utils.bus.EventBusBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pfeifchr on 26.05.2016.
 */
public final class EventBusManager extends JEBManagerBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventBusManager.class);

    public static EventBusManager create(final JEBConfiguration configuration) {
        return new EventBusManager(configuration);
    }

    public static EventBusManager create() {
        return create(JEBConfigurationFactory.createDefaultBuilder().build());
    }

    private final Map<Class<? extends EventBus>, EventBus> eventBusMap = new HashMap<>();
    private final RunOnThreadManager runOnThreadManager;
    private final SurroundManager surroundManager;

    private EventBusManager(JEBConfiguration configuration) {
        super(configuration);

        LOGGER.info("Create new instance of event bus manager");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("> Configuration: " + configuration);
        }
        this.runOnThreadManager = new RunOnThreadManager(configuration);
        this.surroundManager = new SurroundManager(configuration);
    }

    public EventBus getEventBus() {
        return getEventBus(
                configuration.getBusConfiguration() == null ? DefaultEventBus.class :
                        configuration.getBusConfiguration().getEventBusClass()
        );
    }

    public <T extends EventBus>T getEventBus(final Class<T> eventBusClass) {
        if (!eventBusMap.containsKey(eventBusClass)) {
            LOGGER.debug("Create new Event Bus, based on: " + eventBusClass.getName());
            eventBusMap.put(eventBusClass, EventBusBuilder.build(eventBusClass, this));
        }

        return (T) eventBusMap.get(eventBusClass);
    }

    public <T extends EventBus>void destroyEventBus(final Class<T> eventBusClass) {
        if (!eventBusMap.containsKey(eventBusClass))
            return;

        LOGGER.debug("Destroy Event Bus, based on: " + eventBusClass.getName());
        eventBusMap.remove(eventBusClass);
    }

    public RunOnThreadManager getRunOnThreadManager() {
        return runOnThreadManager;
    }

    public SurroundManager getSurroundManager() {
        return surroundManager;
    }

    public Map<Class<? extends EventBus>, EventBus> getEventBusMap() {
        return eventBusMap;
    }
}
