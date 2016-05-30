package org.pcsoft.framework.jeb;

import org.pcsoft.framework.jeb.config.JEBConfiguration;
import org.pcsoft.framework.jeb.config.JEBConfigurationFactory;
import org.pcsoft.framework.jeb.utils.bus.EventBusBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Represent the {@link EventBus} Manager to create and destroy event buses
 */
public final class EventBusManager extends JEBManagerBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventBusManager.class);

    /**
     * Create a new manager based on the given configuration
     * @param configuration
     * @return
     */
    public static EventBusManager create(final JEBConfiguration configuration) {
        return new EventBusManager(configuration);
    }

    /**
     * Create a new manager based on the default configuration
     * @return
     */
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

    /**
     * Returns and creates (only first time) a default {@link EventBus}
     * @return
     */
    public EventBus getEventBus() {
        return getEventBus(
                configuration.getBusConfiguration() == null ? DefaultEventBus.class :
                        configuration.getBusConfiguration().getEventBusClass()
        );
    }

    /**
     * Returns and creates (only first time) a custom {@link EventBus}
     * @param eventBusClass
     * @param <T>
     * @return
     */
    public <T extends EventBus> T getEventBus(final Class<T> eventBusClass) {
        if (!eventBusMap.containsKey(eventBusClass)) {
            LOGGER.debug("Create new Event Bus, based on: " + eventBusClass.getName());
            final T eventBus = EventBusBuilder.build(eventBusClass, this);
            eventBusMap.put(eventBusClass, eventBus);
        }

        return (T) eventBusMap.get(eventBusClass);
    }

    /**
     * Destroy a custom event bus
     * @param eventBusClass
     * @param <T>
     */
    public <T extends EventBus> void destroyEventBus(final Class<T> eventBusClass) {
        if (!eventBusMap.containsKey(eventBusClass))
            return;

        LOGGER.debug("Destroy Event Bus, based on: " + eventBusClass.getName());
        eventBusMap.remove(eventBusClass);
    }

    /**
     * Manager for Run-On-Thread system
     * @return
     */
    public RunOnThreadManager getRunOnThreadManager() {
        return runOnThreadManager;
    }

    /**
     * Manager for surround system
     * @return
     */
    public SurroundManager getSurroundManager() {
        return surroundManager;
    }
}
