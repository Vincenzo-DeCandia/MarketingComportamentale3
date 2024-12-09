package com.market.repository.concreteRepository;

import com.market.entity.Order;
import com.market.entity.Product;
import com.market.database.facade.MySqlDatabaseFacade;
import com.market.repository.IOrderRepository;
import com.market.repository.MySqlRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository class for managing Order entities in a MySQL database.
 * Extends the generic MySQL repository and implements methods specific to order management.
 */
public class OrderRepository extends MySqlRepository<Order, Integer> implements IOrderRepository<Order> {

    /**
     * Finds an Order by its unique identifier, along with its associated products.
     *
     * @param id The ID of the Order to find.
     * @return The Order object with its products, or null if no order is found.
     */
    @Override
    public Order findById(Integer id) {
        Order order = null;
        List<Map<String, Object>> rowOrder = mySqlDatabaseFacade.fetchData("SELECT * FROM `order` WHERE order_id = ?", id);
        Map<String, Object> _row = rowOrder.getFirst();
        if (_row != null) {
            List<Product> productList = new ArrayList<>();
            List<Map<String, Object>> rows = mySqlDatabaseFacade.fetchData(
                    "SELECT * FROM product_ordered LEFT JOIN product ON product_ordered.product_id_fk = product_id WHERE order_id_fk = ?",
                    id
            );

            for (Map<String, Object> row : rows) {
                Product product = new Product();
                product.setProductQuantity((Integer) row.get("quantity"));
                product.setProductName((String) row.get("product_name"));
                product.setProductCode((String) row.get("product_code"));
                product.setProductCategory((String) row.get("product_category"));
                product.setProductPrice((Float) row.get("product_price"));
                product.setProductId((Integer) row.get("product_id_fk"));
                product.setProductDescription((String) row.get("product_description"));
                productList.add(product);
            }

            for (Product product : productList) {
                System.out.println("Find by id: " + product.getProductId());
            }
            order = new Order(productList);
            order.setOrderId(id);
            order.setPaymentMethod((String) _row.get("paymentMethod"));
            order.setTotalPrice((Float) _row.get("total"));
            order.setDateOrder((Date) _row.get("order_date"));
            order.setStatus(_row.get("order_state").toString().charAt(0));
        }

        return order;
    }

    /**
     * Saves a new Order entity to the database, including its associated products.
     *
     * @param order The Order entity to save.
     * @return True if the Order and its products were successfully saved, false otherwise.
     */
    @Override
    public boolean save(Order order) {
        Map<String, List<Object>> queries = new HashMap<>();
        String query = "INSERT INTO `order` (order_id, paymentMethod, total, address, order_date, order_state, user_id_fk) VALUES (0, ?, ?, ?, ?, ?, ?)";
        queries.put(query, List.of(order.getPaymentMethod(), order.getTotalPrice(), order.getAddress(), order.getDateOrder(), order.getStatus().toString(), order.getUser().getIdUser()));

        if (!mySqlDatabaseFacade.executeTransaction(queries)) {
            return false;
        }

        List<Map<String, Object>> rows = mySqlDatabaseFacade.fetchData(
                "SELECT MAX(order_id) as id FROM `order` WHERE user_id_fk = ?",
                order.getUser().getIdUser()
        );

        order.setOrderId((Integer) rows.getFirst().get("id"));

        queries = new HashMap<>();
        for (Product product : order.getProducts()) {
            String query2 = "INSERT INTO product_ordered (order_id_fk, product_id_fk, quantity) VALUES (?, ?, ?)";
            queries.put(query2, List.of(order.getOrderId(), product.getProductId(), product.getProductQuantity()));
        }

        return mySqlDatabaseFacade.executeTransaction(queries);
    }

    /**
     * Retrieves all Orders from the database.
     *
     * @return A list of all Orders, or null if no orders are found.
     */
    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        List<Map<String, Object>> rows = mySqlDatabaseFacade.fetchData("SELECT * FROM `order`");
        if (rows != null && !rows.isEmpty()) {
            for (Map<String, Object> row : rows) {
                Integer orderId = (Integer) row.get("order_id");
                Order order = findById(orderId);
                if (order != null) {
                    orders.add(order);
                }
            }
        }
        return orders.isEmpty() ? null : orders;
    }

    /**
     * Finds all Orders associated with a specific user ID.
     *
     * @param userId The ID of the user whose orders are to be retrieved.
     * @return A list of Orders associated with the specified user ID, or null if no orders exist.
     */
    @Override
    public List<Order> findByUserId(int userId) {
        List<Map<String, Object>> rows = mySqlDatabaseFacade.fetchData("SELECT * FROM `order` WHERE user_id_fk = ?", userId);

        List<Order> orders = new ArrayList<>();
        if (rows != null) {
            for (Map<String, Object> row : rows) {
                int orderId = (Integer) row.get("order_id");
                System.out.println(orderId);
                orders.add(findById(orderId));
            }
        }

        for (Order order : orders) {
            System.out.println("Stampa: " + order.getOrderId());
            for (Product product : order.getProducts()) {
                System.out.println("Stampa prodotto: " + product.getProductId());
            }
        }

        System.out.println(orders.isEmpty());

        return orders.isEmpty() ? null : orders;
    }
}

