package com.market.repository.concreteRepository;

import com.market.entity.Offer;
import com.market.entity.Product;
import com.market.entity.ShoppingCart;
import com.market.database.facade.MySqlDatabaseFacade;
import com.market.repository.MySqlRepository;
import com.market.user.singletonloggeduser.LoggedUserSingleton;
import javafx.util.Pair;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository class for managing ShoppingCart entities in a MySQL database.
 * Implements methods for CRUD operations on ShoppingCart entities and product management in a user's cart.
 */
public class ShoppingCartRepository extends MySqlRepository<ShoppingCart, Pair<Integer, List<Integer>>> {

    private final MySqlDatabaseFacade mySqlDatabaseFacade = new MySqlDatabaseFacade();

    /**
     * Finds a ShoppingCart for a specific user and a list of product IDs.
     * If a list of product IDs is provided, fetches products from the shopping cart for the user and specific products.
     * Otherwise, fetches all products in the shopping cart for the user.
     *
     * @param idUserAndListIdProducts A Pair containing the user ID and a list of product IDs (can be null).
     * @return The ShoppingCart entity for the given user, or null if no products are found.
     */
    @Override
    public ShoppingCart findById(Pair<Integer, List<Integer>> idUserAndListIdProducts) {
        ShoppingCart shoppingCart = null;
        String query;
        List<Map<String, Object>> mapList;

        // If product list is provided, fetch specific products from the shopping cart
        if (idUserAndListIdProducts.getValue() != null) {
            query = "SELECT * FROM product LEFT JOIN shopping_cart on product.product_id = shopping_cart.product_id_fk WHERE user_id_fk=? AND product_id_fk=?";
            mapList = mySqlDatabaseFacade.fetchData(query, idUserAndListIdProducts.getKey(), idUserAndListIdProducts.getValue().toArray());
        } else {
            // Otherwise, fetch all products from the shopping cart
            query = "SELECT * FROM product LEFT JOIN shopping_cart on product.product_id = shopping_cart.product_id_fk WHERE user_id_fk=?";
            mapList = mySqlDatabaseFacade.fetchData(query, idUserAndListIdProducts.getKey());
            System.out.println("id_user " + idUserAndListIdProducts.getKey());
        }

        if (mapList != null && !mapList.isEmpty()) {
            shoppingCart = new ShoppingCart();
            for (Map<String, Object> map : mapList) {
                Product product = new Product();
                product.setProductId((Integer) map.get("product_id"));
                product.setProductName((String) map.get("product_name"));
                product.setProductPrice((Float) map.get("product_price"));
                product.setProductQuantity((Integer) map.get("quantity"));
                product.setProductDescription((String) map.get("product_description"));
                product.setProductCategory((String) map.get("product_category"));
                product.setProductCode((String) map.get("product_code"));
                shoppingCart.addProduct(product);

                // Fetch associated offers for the product
                String query2 = "SELECT * FROM offer WHERE product_id_fk=? AND user_id_fk=?";
                List<Map<String, Object>> rows = mySqlDatabaseFacade.fetchData(query2, product.getProductId(), idUserAndListIdProducts.getKey());
                if (rows != null && !rows.isEmpty()) {
                    for (Map<String, Object> row : rows) {
                        Offer offer = new Offer();
                        offer.setNameOffer((String) row.get("offer_name"));
                        offer.setIdProduct(product.getProductId());
                        offer.setIdUser(idUserAndListIdProducts.getKey());
                        offer.setDiscount((Float) row.get("discount"));
                        offer.setDescription((String) row.get("offer_description"));
                        offer.setStartDate((Date) row.get("start_date"));
                        offer.setEndDate((Date) row.get("end_date"));
                        shoppingCart.addProductOffer(offer);
                    }
                }
            }
        }
        return shoppingCart;
    }

    /**
     * Saves a ShoppingCart to the database. If a product is already in the cart, updates the quantity.
     * If the product is not in the cart, it inserts a new record.
     *
     * @param shoppingCart The ShoppingCart entity to save.
     * @return True if the operation is successful, false otherwise.
     */
    @Override
    public boolean save(ShoppingCart shoppingCart) {
        if (shoppingCart == null) {
            return false;
        }
        List<Product> products = shoppingCart.getProducts();
        Map<String, List<Object>> listMap = new HashMap<>();

        // Loop through each product and check if it already exists in the cart for the user
        for (Product product : products) {
            System.out.println("Inizio fetch\n");
            List<Map<String, Object>> rows = mySqlDatabaseFacade.fetchData("SELECT * FROM shopping_cart WHERE product_id_fk = ? and user_id_fk = ?",
                    product.getProductId(), LoggedUserSingleton.getInstance().getLoggedUser().getIdUser());
            System.out.println("Fine fetch\n");

            // If the product doesn't exist in the cart, insert a new record
            if (rows == null || rows.isEmpty()) {
                String query = "INSERT INTO shopping_cart (user_id_fk, product_id_fk, quantity) VALUES (?, ?, ?)";
                System.out.println(query);
                listMap.put(query, List.of(LoggedUserSingleton.getInstance().getLoggedUser().getIdUser(), product.getProductId(), product.getProductQuantity()));
            } else {
                // If the product already exists, update the quantity
                String query = "UPDATE shopping_cart SET quantity = ? WHERE product_id_fk = ? and user_id_fk = ?";
                System.out.println(query);
                listMap.put(query, List.of(product.getProductQuantity(), product.getProductId(), LoggedUserSingleton.getInstance().getLoggedUser().getIdUser()));
            }
        }

        System.out.println("Inizio transazione");
        return mySqlDatabaseFacade.executeTransaction(listMap);
    }

    /**
     * Deletes products from the ShoppingCart where the quantity is zero.
     *
     * @param shoppingCart The ShoppingCart entity to delete products from.
     * @return True if the operation is successful, false otherwise.
     */
    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        List<Product> shoppingCartProducts = shoppingCart.getProducts();
        Map<String, List<Object>> listMap = new HashMap<>();

        // Loop through each product and delete it if its quantity is zero
        for (Product product : shoppingCartProducts) {
            if (product.getProductQuantity() == 0) {
                String query = "DELETE FROM shopping_cart WHERE product_id_fk = ? AND user_id_fk = ?";
                listMap.put(query, List.of(product.getProductId(), LoggedUserSingleton.getInstance().getLoggedUser().getIdUser()));
            }
        }
        return mySqlDatabaseFacade.executeTransaction(listMap);
    }
}

