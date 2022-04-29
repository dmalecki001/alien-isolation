package org.example.domain;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public interface EnvironmentConfig {

  static EnvironmentConfig getInstance() {
    return EnvironmentConfigImpl.getInstance();
  }

  String fetchProperty(DomainProp prop);

  String envName();

  class EnvironmentConfigImpl implements EnvironmentConfig {

    private static final EnvironmentVariables VARS = SystemEnvironmentVariables
        .createEnvironmentVariables();
    private static final String ENV_NAME;
    private static final Properties PROPS;

    static {
      ENV_NAME = Optional.ofNullable(System.getProperty("environment"))
          .orElse(
              SystemEnvironmentVariables.createEnvironmentVariables().getProperty("environment"));
      PROPS = extractProps();
    }

    private static Properties extractProps() {
      InputStream is = EnvironmentConfigImpl.class.getClassLoader()
          .getResourceAsStream(
              String.format("environments/%s.properties", Objects.requireNonNull(ENV_NAME)));
      Objects.requireNonNull(is);
      try (Reader envFileReader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
        Properties properties = new Properties();
        properties.load(envFileReader);
        return properties;
      } catch (IOException e) {
        e.printStackTrace();
      }
      throw new IllegalStateException("Loading initial properties has failed.");
    }

    private static EnvironmentConfigImpl getInstance() {
      return EnvironmentConfigImplHelper.INSTANCE;
    }

    @Override
    public String fetchProperty(DomainProp domainProp) {
      return Optional.ofNullable(VARS.getProperty(domainProp.getProp()))
          .or(() -> Optional.ofNullable(PROPS.getProperty(domainProp.getProp())))
          .orElseThrow(() -> new RuntimeException("Property " + domainProp + " not found!"));
    }

    @Override
    public String envName() {
      return ENV_NAME;
    }

    private static final class EnvironmentConfigImplHelper {

      private static final EnvironmentConfigImpl INSTANCE = new EnvironmentConfigImpl();
    }
  }

}
