import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Day15Demo {
    public static void main(String[] args) {
        Product first = new Product("Mouse", 29.99, 100);
        Product second = new Product("Mouse", 29.99, 100);

        Product threeArg = new Product("Keyboard", 79.99, 25);
        Product twoArg = new Product("Monitor", 299.99);
        Product oneArg = new Product("Webcam");
        System.out.println(threeArg);
        System.out.println(twoArg);
        System.out.println(oneArg);


        System.out.println("\n=== Validation via canonical constructor (blank name) ===");
        try {
            new Product("   ", 10.0, 1);
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Caught: " + illegalArgumentException.getMessage());
        }

        System.out.println("\n=== equals / hashCode ===");
        Product laptop = new Product("Laptop", 999.99, 50);
        System.out.println("Same reference: " + laptop.equals(laptop));
        System.out.println("null: " + laptop.equals(null));
        System.out.println("Wrong class: " + laptop.equals("not a product"));

        Product sameIdCopy = Product.withExistingId(laptop.getId(), "Laptop clone", 1.0, 0);
        System.out.println("Same id, different instance: " + laptop.equals(sameIdCopy));

        Product a = new Product("A", 1.0, 1);
        Product b = new Product("B", 2.0, 2);
        Product c = new Product("C", 3.0, 3);

        Set<Product> shelf = new HashSet<>();
        shelf.add(a);
        shelf.add(b);
        shelf.add(c);
        System.out.println("HashSet size (3 distinct ids): " + shelf.size());

        HashMap<Product, String> locations = new HashMap<>();
        locations.put(laptop, "Aisle 3");
        Product lookupKey = Product.withExistingId(laptop.getId(), "Any name", 5.0, 1);
        System.out.println("HashMap lookup with equal id: " + locations.get(lookupKey));

    }
}
