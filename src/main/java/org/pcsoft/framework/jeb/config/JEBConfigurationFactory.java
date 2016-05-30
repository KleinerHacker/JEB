package org.pcsoft.framework.jeb.config;

import org.pcsoft.framework.jeb.config.xml.XJEBConfiguration;
import org.pcsoft.framework.jeb.exception.JEBConfigurationException;

import javax.xml.bind.JAXB;
import javax.xml.transform.Source;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * Represent the configuration factory to create builders, see {@link JEBConfigurationBuilder}
 */
public final class JEBConfigurationFactory {
    private static final String DEFAULT_RESOURCE = "jeb.xml";

    /**
     * Create a default builder
     *
     * @return
     */
    public static JEBConfigurationBuilder createDefaultBuilder() {
        return new JEBDefaultConfigurationBuilder();
    }

    /**
     * Create a builder based on the given XML content
     *
     * @param bytes
     * @return
     */
    public static JEBConfigurationBuilder createXMLBuilder(final byte[] bytes) {
        try {
            final XJEBConfiguration configuration = JAXB.unmarshal(new ByteArrayInputStream(bytes), XJEBConfiguration.class);
            return new JEBXMLConfigurationBuilder(configuration);
        } catch (Exception e) {
            throw new JEBConfigurationException("Unable to load XML configuration!", e);
        }
    }

    /**
     * Create a builder based on the given XML content
     *
     * @param inputStream
     * @return
     */
    public static JEBConfigurationBuilder createXMLBuilder(final InputStream inputStream) {
        try {
            final XJEBConfiguration configuration = JAXB.unmarshal(inputStream, XJEBConfiguration.class);
            return new JEBXMLConfigurationBuilder(configuration);
        } catch (Exception e) {
            throw new JEBConfigurationException("Unable to load XML configuration!", e);
        }
    }

    /**
     * Create a builder based on the given XML content
     *
     * @param reader
     * @return
     */
    public static JEBConfigurationBuilder createXMLBuilder(final Reader reader) {
        try {
            final XJEBConfiguration configuration = JAXB.unmarshal(reader, XJEBConfiguration.class);
            return new JEBXMLConfigurationBuilder(configuration);
        } catch (Exception e) {
            throw new JEBConfigurationException("Unable to load XML configuration!", e);
        }
    }

    /**
     * Create a builder based on the given XML content
     *
     * @param file
     * @return
     */
    public static JEBConfigurationBuilder createXMLBuilder(final File file) {
        try {
            final XJEBConfiguration configuration = JAXB.unmarshal(file, XJEBConfiguration.class);
            return new JEBXMLConfigurationBuilder(configuration);
        } catch (Exception e) {
            throw new JEBConfigurationException("Unable to load XML configuration!", e);
        }
    }

    /**
     * Create a builder based on the given XML content
     *
     * @param xml
     * @return
     */
    public static JEBConfigurationBuilder createXMLBuilder(final String xml) {
        try {
            final XJEBConfiguration configuration = JAXB.unmarshal(xml, XJEBConfiguration.class);
            return new JEBXMLConfigurationBuilder(configuration);
        } catch (Exception e) {
            throw new JEBConfigurationException("Unable to load XML configuration!", e);
        }
    }

    /**
     * Create a builder based on the given XML content
     *
     * @param url
     * @return
     */
    public static JEBConfigurationBuilder createXMLBuilder(final URL url) {
        try {
            final XJEBConfiguration configuration = JAXB.unmarshal(url, XJEBConfiguration.class);
            return new JEBXMLConfigurationBuilder(configuration);
        } catch (Exception e) {
            throw new JEBConfigurationException("Unable to load XML configuration!", e);
        }
    }

    /**
     * Create a builder based on the given XML content
     *
     * @param source
     * @return
     */
    public static JEBConfigurationBuilder createXMLBuilder(final Source source) {
        try {
            final XJEBConfiguration configuration = JAXB.unmarshal(source, XJEBConfiguration.class);
            return new JEBXMLConfigurationBuilder(configuration);
        } catch (Exception e) {
            throw new JEBConfigurationException("Unable to load XML configuration!", e);
        }
    }

    /**
     * Load XML from default resource position: on root in file jeb.xml
     *
     * @return
     */
    public static JEBConfigurationBuilder createXMLBuilder() {
        try {
            final XJEBConfiguration configuration = JAXB.unmarshal(JEBConfigurationFactory.class.getResourceAsStream("/" + DEFAULT_RESOURCE), XJEBConfiguration.class);
            return new JEBXMLConfigurationBuilder(configuration);
        } catch (Exception e) {
            throw new JEBConfigurationException("Unable to load XML configuration!", e);
        }
    }

    private JEBConfigurationFactory() {
    }
}
