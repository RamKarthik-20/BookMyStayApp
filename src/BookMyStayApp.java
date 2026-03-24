public class BookMyStayApp {
    class AddOnService {
        private String name;
        private double price;

        public AddOnService(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public void display() {
            System.out.println(name + " (₹" + price + ")");
        }
    }
}
