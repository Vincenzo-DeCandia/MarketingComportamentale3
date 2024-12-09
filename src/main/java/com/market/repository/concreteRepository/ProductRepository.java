package com.market.repository.concreteRepository;

import com.market.entity.Product;
import com.market.database.facade.MySqlDatabaseFacade;
import com.market.repository.IProductRepository;
import com.market.repository.MySqlRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository class for managing Product entities in a MySQL database.
 * Implements methods for CRUD operations on Product entities and additional search functionality.
 */
public class ProductRepository extends MySqlRepository<Product, Integer> implements IProductRepository<Product> {

    /**
     * Finds a Product by its unique identifier.
     *
     * @param id The ID of the Product to find.
     * @return The Product object corresponding to the given ID, or null if no product is found.
     */
    @Override
    public Product findById(Integer id) {
        String query = "SELECT * FROM product WHERE product_id = ?";
        List<Map<String, Object>> mapList = mySqlDatabaseFacade.fetchData(query, id);
        if (mapList.isEmpty()) {
            return null;
        }
        return initProducts(mapList).getFirst();
    }

    /**
     * Retrieves all Product entities.
     *
     * @return A list of all Product entities, or null if no products exist.
     */
    @Override
    public List<Product> findAll() {
        String query = "SELECT * FROM product";
        List<Map<String, Object>> mapList = mySqlDatabaseFacade.fetchData(query);
        if (mapList.isEmpty()) {
            return null;
        }
        return initProducts(mapList);
    }

    /**
     * Saves a new Product entity to the database.
     *
     * @param product The Product entity to save.
     * @return True if the Product was successfully saved, false otherwise.
     */
    @Override
    public boolean save(Product product) {
        String query = "INSERT INTO product (product_id, product_code, product_name, product_description, product_quantity, product_category, product_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Map<String, List<Object>> map = new HashMap<>();
        map.put(query, List.of(
                product.getProductId(),
                product.getProductCode(),
                product.getProductName(),
                product.getProductDescription(),
                product.getProductQuantity(),
                product.getProductCategory(),
                product.getProductPrice()
        ));
        return mySqlDatabaseFacade.executeTransaction(map);
    }

    /**
     * Deletes a Product entity from the database.
     *
     * @param product The Product entity to delete.
     * @return True if the Product was successfully deleted, false otherwise.
     */
    @Override
    public boolean delete(Product product) {
        String query = "DELETE FROM product WHERE product_id = ?";
        Map<String, List<Object>> map = new HashMap<>();
        map.put(query, List.of(product.getProductId()));
        return mySqlDatabaseFacade.executeTransaction(map);
    }

    /**
     * Updates an existing Product entity in the database.
     *
     * @param product The Product entity to update.
     * @return True if the Product was successfully updated, false otherwise.
     */
    @Override
    public boolean update(Product product) {
        String query = "UPDATE product " +
                "SET product_code = ?, " +
                "product_name = ?, " +
                "product_description = ?, " +
                "product_quantity = ?, " +
                "product_category = ?, " +
                "product_price = ? " +
                "WHERE product_id = ?";
        Map<String, List<Object>> map = new HashMap<>();
        map.put(query, List.of(
                product.getProductCode(),
                product.getProductName(),
                product.getProductDescription(),
                product.getProductQuantity(),
                product.getProductCategory(),
                product.getProductPrice(),
                product.getProductId()
        ));
        return mySqlDatabaseFacade.executeTransaction(map);
    }

    /**
     * Searches for Products by their name.
     *
     * @param name The name of the Product to search for.
     * @return A list of Products with the given name, or an empty list if no products match.
     */
    @Override
    public List<Product> searchByName(String name) {
        String query = "SELECT * FROM product WHERE product_name = ?";
        return initProducts(mySqlDatabaseFacade.fetchData(query, name));
    }

    /**
     * Searches for Products by their category.
     *
     * @param category The category of the Product to search for.
     * @return A list of Products with the given category, or an empty list if no products match.
     */
    @Override
    public List<Product> searchByCategory(String category) {
        String query = "SELECT * FROM product WHERE product_category = ?";
        return initProducts(mySqlDatabaseFacade.fetchData(query, category));
    }

    /**
     * Searches for a Product by its code.
     *
     * @param code The code of the Product to search for.
     * @return A Product object with the given code, or null if no product is found.
     */
    @Override
    public Product searchByCode(String code) {
        String query = "SELECT * FROM product WHERE product_code = ?";
        List<Map<String, Object>> mapList = mySqlDatabaseFacade.fetchData(query, code);
        if (mapList.isEmpty()) {
            return null;
        }
        return initProducts(mapList).getFirst();
    }

    /**
     * Initializes a list of Product entities from the database result.
     *
     * @param mapList The list of database rows to convert into Product entities.
     * @return A list of Product entities corresponding to the database rows.
     */
    private List<Product> initProducts(List<Map<String, Object>> mapList) {
        List<Product> products = new ArrayList<>();
        if (mapList == null || mapList.isEmpty()) {
            return products;
        }
        for (Map<String, Object> map : mapList) {
            Product product = new Product();
            product.setProductId((Integer) map.get("product_id"));
            product.setProductCode((String) map.get("product_code"));
            product.setProductName((String) map.get("product_name"));
            product.setProductDescription((String) map.get("product_description"));
            product.setProductQuantity((Integer) map.get("product_quantity"));
            product.setProductCategory((String) map.get("product_category"));
            product.setProductPrice((Float) map.get("product_price"));
            products.add(product);
        }
        return products;
    }
}

