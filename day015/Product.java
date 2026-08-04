import java.util.Objects;

public class Product {

    private static int totalProducts = 0;
    private static final String PREFIX = "PRD";

    private final String id;
    private String name;
    private double price;
    private int stock;

    static {
        System.out.println("Class loaded");
    }

    {
        System.out.println("Instance block: " + name);
    }

    public Product(String name, double price, int stock) {
        this(null, name, price, stock, false);
    }

    public Product(String name, double price) {
        this(name, price, 0);
    }

    public Product(String name) {
        this(name, 1.0);
    }

    private Product(String existingId, String name, double price, int stock, boolean useExistingId) {
        this.id = useExistingId ? existingId : PREFIX + "-" + (++totalProducts);

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("price must be > 0");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("stock must be >= 0");
        }

        this.name = name;
        this.price = price;
        this.stock = stock;

    }

    static Product withExistingId(String id, String name, double price, int stock) {
        return new Product(id, name, price, stock, true);
    }

    static void resetCounter() {
        totalProducts = 0;
    }

    String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Product{id='" + id + "', name='" + name + "', price=" + price + ", stock=" + stock + "}";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
