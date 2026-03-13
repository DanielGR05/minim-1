import models.Order;
import models.Product;
import models.User;

import java.util.*;

public class ProductManagerImpl implements ProductManager {
    private List<Product> productList;
    private Queue<Order> orderQueue;
    private HashMap<String, User> users;


    public ProductManagerImpl() {
        productList = new ArrayList<>();
        orderQueue = new LinkedList<>();
        users = new HashMap<>();
    }

    @Override
    public void addProduct(String id, String name, double price) {
        productList.add(new Product(id, name, price));
    }

    @Override
    public List<Product> getProductsByPrice() {
        List<Product> copyProductList = new ArrayList<>(productList);
        copyProductList.sort((p1,p2)-> Double.compare(p2.getPrice(), p1.getPrice()));
        return copyProductList;
    }

    public List<Product> getProductsBySales() {
        List<Product> copyProductList = new ArrayList<>(productList);
        copyProductList.sort((p1,p2)-> Double.compare(p2.getPrice(), p1.getPrice()));
        return copyProductList;
    }

    @Override
    public void addOrder(Order order) {
        orderQueue.add(order);
    }

    @Override
    public int numOrders() {
        return orderQueue.size();
    }

    @Override
    public Order deliverOrder() {
        Order order = orderQueue.poll();
        // TO-DO
        return order;
    }

    @Override
    public Product getProduct(String c1) {
        for(Product p: productList){
            if(p.getId().equals(c1)){
                return p;
            }
        }
        return null;
    }

    @Override
    public User getUser(String number) {
        return users.get(number);
    }

    public static void main(String[] args) {
        ProductManager pm = new ProductManagerImpl();
        pm.addProduct("C1", "Coca-cola zero", 2);
        pm.addProduct("C2", "Coca-cola", 2.5);
        pm.addProduct("B1", "Lomo queso", 3);
        pm.addProduct("B2", "bacon queso", 3.5);

        List<Product> products = pm.getProductsByPrice();
        for (Product p : products) {
            System.out.println(p.getId() + " - " + p.getName() + " - " + p.getPrice());
        }
    }
}
