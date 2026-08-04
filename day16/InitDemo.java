public class InitDemo {

    private static int counter = 0;

    static {
        System.out.println("static block");
        counter++;
    }

    int id = ++counter;

    {
        System.out.println("instance block, id=" + id);
    }

    InitDemo() {
        System.out.println("constructor, id=" + id);
    }

    static int getCounter() {
        return counter;
    }

    // static void bad() { System.out.println(id); }
    // compile error: id is an instance field, not accessible from static context

    final void describe() {
        System.out.println("InitDemo#" + id);
    }
}
