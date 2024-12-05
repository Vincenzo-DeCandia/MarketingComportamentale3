package com.market.service;

import com.market.entity.Order;
import com.market.exception.ExceptionScene;
import com.market.payment.factorymethod.BancomatCreator;
import com.market.payment.factorymethod.CashCreator;
import com.market.payment.factorymethod.CreditCardCreator;
import com.market.payment.factorymethod.PaymentMethodCreator;
import com.market.repository.IRepository;
import com.market.repository.concreteRepository.OrderRepository;
import com.market.user.concreteuser.User;
import com.market.user.singletonloggeduser.LoggedUserSingleton;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Implements the IPaymentService interface to handle payment processing and order creation.
 * Based on the payment type, it initializes the appropriate payment method and manages successful transactions.
 */
public class PaymentService implements IPaymentService {

    /**
     * Executes a payment for a specified amount using a given payment method type.
     * Processes the payment and handles order creation upon success.
     *
     * @param type The type of payment (e.g., "Contanti", "Carta di credito", "Bancomat").
     * @param amount The amount to be paid.
     * @param paymentData A list containing payment method details (e.g., card number, expiry date, address).
     * @throws ExceptionScene if the payment fails or the payment type is invalid.
     */
    @Override
    public void executePayment(String type, float amount, List<String> paymentData) throws ExceptionScene {
        // Create the appropriate payment method based on the type.
        String address;
        PaymentMethodCreator paymentMethodCreator = switch (type) {
            case "Contanti" -> {
                address = paymentData.get(2);
                yield new CashCreator(paymentData.get(0), paymentData.get(1), paymentData.get(2));
            }
            case "Carta di credito" -> {
                address = paymentData.get(3);
                yield new CreditCardCreator(paymentData.get(0), paymentData.get(1), paymentData.get(2), paymentData.get(3));
            }
            case "Bancomat" -> {
                address = paymentData.get(4);
                yield new BancomatCreator(paymentData.get(0), paymentData.get(1), paymentData.get(2), paymentData.get(3), paymentData.get(4));
            }
            default -> throw new IllegalArgumentException("Invalid payment type");
        };

        // Process the payment and throw an exception if it fails.
        if (!paymentMethodCreator.pay(amount)) {
            throw new ExceptionScene("Pagamento fallito");
        }

        // Handle order creation on successful payment.
        handleOrder(type, amount, address);
    }

    /**
     * Creates and saves an order after a successful payment.
     * Populates the order details from the user's shopping cart and stores it in the repository.
     *
     * @param type The type of payment method used (e.g., "Contanti", "Carta di credito").
     * @param amount The total amount paid for the order.
     * @param address The shipping or billing address associated with the order.
     */
    private void handleOrder(String type, float amount, String address) {
        // Create a new order using the user's shopping cart.
        Order order = new Order(((User) LoggedUserSingleton.getInstance().getLoggedUser())
                .getShoppingCart().getProducts());
        order.setTotalPrice(amount);
        order.setPaymentMethod(type);
        order.setStatus('P'); // 'P' indicates payment completed.
        order.setDateOrder(Date.valueOf(LocalDate.now()));
        order.setAddress(address);
        order.setUser(LoggedUserSingleton.getInstance().getLoggedUser());

        // Save the order to the repository.
        IRepository<Order, Integer> orderRepository = new OrderRepository();
        orderRepository.save(order);
    }
}

