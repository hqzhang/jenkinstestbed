import org.yaml.snakeyaml.Yaml;
import java.util.Map;

public class YAMLParser {
    public static void main(String[] args) {
        String data = "persons:\n" +
                "  - !person\n" +
                "    name: 'emily'\n" +
                "    age: 19";

        Yaml yaml = new Yaml();
        Map<String, Object> result = yaml.load(data);

        // Dumping the result
        String dumpedResult = yaml.dump(result);
        System.out.println(dumpedResult);
    }
}
