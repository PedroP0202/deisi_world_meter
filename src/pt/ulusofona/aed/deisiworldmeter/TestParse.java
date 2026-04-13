public class TestParse {
    public static void main(String[] args) {
        try {
            int p = Integer.parseInt("20430.0");
            System.out.println("Parsed: " + p);
        } catch (NumberFormatException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
