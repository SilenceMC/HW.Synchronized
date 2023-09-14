import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static final int THREADS_AMOUNT = 1000;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        for (int i = 0; i < THREADS_AMOUNT; i++) {
            new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int count = (int) route.chars().filter(ch -> ch == 'R').count();
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(count)) {
                        sizeToFreq.replace(count, sizeToFreq.get(count) + 1);
                    } else {
                        sizeToFreq.put(count, 1);
                    }
                }
            }).start();
        }

        Map.Entry<Integer, Integer> maxValue = sizeToFreq.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow();

        System.out.println("Самое частое количество повторений " + maxValue.getKey() + " (встретилось " + maxValue.getValue() + " раз)");

        System.out.println("Другие размеры:");
        sizeToFreq.remove(maxValue.getKey());
        sizeToFreq.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(x -> System.out.println("- " + x.getKey() + " (" + x.getValue() + " раз)"));
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
