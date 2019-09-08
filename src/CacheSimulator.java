public class CacheSimulator {

    public static void main(String []args) {
        Cache<String> cache = new Cache<String>(5);

        cache.add("Bobby");
        cache.add("Alice");
        cache.add("Michael");
        cache.add("Gabe");
        cache.add("Lilly");

        System.out.println(cache.toString());
        System.out.printf("Cache is full: %b\n", cache.isFull());

        cache.add("Robert");
        System.out.println(cache.toString());

        cache.write("Alice");
        System.out.println(cache.toString());
    }
}