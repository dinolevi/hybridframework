package helpers;

import helpers.properteispojo.YamlProperties;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

/**
 * Class will read application properties from yml file
 * @author dino
 *
 */
public class YamlReader {

    public static YamlProperties getProperties() {
        Yaml yaml = new Yaml(new Constructor(YamlProperties.class));
        InputStream inputStream = YamlReader.class.getClassLoader()
                .getResourceAsStream(
                        "properties.yml");
        return yaml.load(inputStream);
    }

}
