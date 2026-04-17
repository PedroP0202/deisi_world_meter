public class scratch {
    public static void main(String[] args) {
        String value = "50000.0";
        try {
            double parsed = Double.parseDouble(value);
            int i = (int) parsed;
            System.out.println("Parsed: " + i);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
