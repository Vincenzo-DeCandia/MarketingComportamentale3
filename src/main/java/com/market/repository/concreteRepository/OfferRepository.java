package com.market.repository.concreteRepository;

import com.market.entity.Offer;
import com.market.repository.IOfferRepository;
import com.market.repository.MySqlRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository class for managing Offer entities in a MySQL database.
 * Extends the generic MySQL repository and implements offer-specific methods.
 */
public class OfferRepository extends MySqlRepository<Offer, Integer> implements IOfferRepository {

    /**
     * Saves a new Offer entity to the database.
     *
     * @param offer The Offer entity to save.
     * @return True if the Offer was successfully saved, false otherwise.
     */
    @Override
    public boolean save(Offer offer) {
        String query = "INSERT INTO offer (offer_name, start_date, end_date, description, discount, product_id_fk, user_id_fk) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Map<String, List<Object>> params = new HashMap<>();
        params.put(query, List.of(offer.getNameOffer(), offer.getStartDate(), offer.getEndDate(), offer.getDescription(), offer.getDiscount(), offer.getIdProduct(), offer.getIdUser()));
        return mySqlDatabaseFacade.executeTransaction(params);
    }

    /**
     * Deletes an Offer entity from the database.
     *
     * @param offer The Offer entity to delete.
     * @return True if the Offer was successfully deleted, false otherwise.
     */
    @Override
    public boolean delete(Offer offer) {
        String query = "DELETE FROM offer WHERE offer_name = ?";
        Map<String, List<Object>> listMap = new HashMap<>();
        listMap.put(query, List.of(offer.getNameOffer()));
        return mySqlDatabaseFacade.executeTransaction(listMap);
    }

    /**
     * Finds all offers associated with a specific user ID.
     *
     * @param userId The ID of the user whose offers are to be retrieved.
     * @return A list of offers associated with the specified user ID.
     */
    @Override
    public List<Offer> findByUserId(int userId) {
        List<Offer> offers = new ArrayList<>();
        String query = "SELECT * FROM offer WHERE user_id_fk = ?";
        List<Map<String, Object>> rows = mySqlDatabaseFacade.fetchData(query, userId);
        for (Map<String, Object> row : rows) {
            offers.add(extractOffer(row));
        }
        return offers.isEmpty() ? null : offers;
    }

    /**
     * Finds an offer associated with a specific product ID.
     *
     * @param productId The ID of the product for which the offer is to be retrieved.
     * @return The offer associated with the specified product ID, or null if no offer is found.
     */
    @Override
    public Offer findByProductId(int productId) {
        String query = "SELECT * FROM offer WHERE product_id_fk = ?";
        List<Map<String, Object>> rows = mySqlDatabaseFacade.fetchData(query, productId);
        for (Map<String, Object> row : rows) {
            return extractOffer(row);
        }
        return null;
    }

    /**
     * Extracts an Offer entity from a result row.
     *
     * @param row The map representing a single row of the result set.
     * @return An Offer entity populated with the data from the row.
     */
    private Offer extractOffer(Map<String, Object> row) {
        Offer offer = new Offer();
        offer.setIdProduct((Integer) row.get("product_id_fk"));
        offer.setIdUser((Integer) row.get("user_id_fk"));
        offer.setNameOffer((String) row.get("offer_name"));
        offer.setStartDate((Date) row.get("start_date"));
        offer.setEndDate((Date) row.get("end_date"));
        offer.setDescription((String) row.get("description"));
        offer.setDiscount((Float) row.get("discount"));
        return offer;
    }
}

