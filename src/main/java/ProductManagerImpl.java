import models.Order;
import models.Product;
import models.User;

import java.util.*;

import org.apache.log4j.Logger;

public class ProductManagerImpl implements ProductManager {
    private static final Logger logger = Logger.getLogger(ProductManagerImpl.class);
    private static ProductManagerImpl instance;
    private List<Product> productList;
    private Queue<Order> orderQueue;
    private HashMap<String, User> users;

    private ProductManagerImpl() {
        productList = new ArrayList<>();
        orderQueue = new LinkedList<>();
        users = new HashMap<>();
        logger.info("ProductManagerImpl constructor called");
    }

    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManagerImpl();
        }
        return instance;
    }

    @Override
    public void addProduct(String id, String name, double price) {
        logger.info("addProduct start: id=" + id + ", name=" + name + ", price=" + price);
        productList.add(new Product(id, name, price));
        logger.info("addProduct end");
    }

    @Override
    public List<Product> getProductsByPrice() {
        logger.info("getProductsByPrice start");
        List<Product> copyProductList = new ArrayList<>(productList);
        copyProductList.sort((p1,p2)-> Double.compare(p1.getPrice(), p2.getPrice()));
        logger.info("getProductsByPrice end: returned " + copyProductList.size() + " products");
        return copyProductList;
    }

    public List<Product> getProductsBySales() {
        logger.info("getProductsBySales start");
        List<Product> copyProductList = new ArrayList<>(productList);
        copyProductList.sort((p1,p2)-> Integer.compare(p2.sales(), p1.sales()));
        logger.info("getProductsBySales end: returned " + copyProductList.size() + " products");
        return copyProductList;
    }

    @Override
    public void addOrder(Order order) {
        logger.info("addOrder start: order for user " + order.getUser());
        String dni = order.getUser();
        if (!users.containsKey(dni)) {
            users.put(dni, new User(dni));
            logger.info("Created new user: " + dni);
        }
        orderQueue.add(order);
        logger.info("addOrder end");
    }

    @Override
    public int numOrders() {
        logger.info("numOrders start");
        int size = orderQueue.size();
        logger.info("numOrders end: " + size);
        return size;
    }

    @Override
    public Order deliverOrder() {
        logger.info("deliverOrder start");
        Order order = orderQueue.poll();
        if (order != null) {
            String dni = order.getUser();
            User user = users.get(dni);
            if (user != null) {
                user.addOrder(order);
                // Update sales
                Map<String, Integer> productCounts = new HashMap<>();
                for (String productId : order.getComanda()) {
                    productCounts.put(productId, productCounts.getOrDefault(productId, 0) + 1);
                }
                for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
                    Product p = getProduct(entry.getKey());
                    if (p != null) {
                        p.addSales(entry.getValue());
                    } else {
                        logger.error("Product not found: " + entry.getKey());
                    }
                }
                logger.info("deliverOrder end: delivered order for user " + dni);
            } else {
                logger.error("User not found for order: " + dni);
            }
        } else {
            logger.info("deliverOrder end: no orders to deliver");
        }
        return order;
    }

    @Override
    public Product getProduct(String id) {
        logger.info("getProduct start: id=" + id);
        for(Product p: productList){
            if(p.getId().equals(id)){
                logger.info("getProduct end: found product " + p.getName());
                return p;
            }
        }
        logger.info("getProduct end: product not found");
        return null;
    }

    @Override
    public User getUser(String dni) {
        logger.info("getUser start: dni=" + dni);
        User user = users.get(dni);
        logger.info("getUser end: " + (user != null ? "found" : "not found"));
        return user;
    }

    public static void main(String[] args) {
        ProductManager pm = ProductManagerImpl.getInstance();
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
