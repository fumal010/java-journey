import java.util.ArrayList;
import java.util.List;

public class Day16Demo {

    private static final class AppConfig {
        private static final AppConfig INSTANCE = new AppConfig("MyApp", 8080);

        private final String appName;
        private final int port;

        private AppConfig(String appName, int port) {
            this.appName = appName;
            this.port = port;
        }

        static AppConfig getInstance() {
            return INSTANCE;
        }

        @Override
        public String toString() {
            return "AppConfig{appName='" + appName + "', port=" + port + "}";
        }
    }

    // class Extend extends String {}
    // compile error: String is final — final classes cannot be extended

    // class SubInit extends InitDemo {
    //     @Override
    //     void describe() {}
    // }
    // compile error: describe() is final in InitDemo — final methods cannot be overridden

    public static void main(String[] args) {
        System.out.println("=== Initialization order (2 instances) ===");
        // Next line triggers class load (once): static field init → static block
        InitDemo first = new InitDemo();
        // instance field id = ++counter → instance block → constructor
        InitDemo second = new InitDemo();
        // static block does NOT run again; instance block + constructor run again
        System.out.println("static counter after two instances: " + InitDemo.getCounter());


        System.out.println("\n=== Eager singleton ===");
        AppConfig a = AppConfig.getInstance();
        AppConfig b = AppConfig.getInstance();
        System.out.println(a);
        System.out.println("Same reference: " + (a == b));

        System.out.println("\n=== final keyword ===");
        final int x = 5;
        // x = 6;
        // compile error: cannot assign to final variable x

        final List<String> tags = new ArrayList<>();
        tags.add("java");
        System.out.println("tags: " + tags);
        // tags = new ArrayList<>();
        // compile error: cannot assign to final variable tags — reference is final,
        // but the list object can still be mutated (tags.add works)

        first.describe();

    }
}
