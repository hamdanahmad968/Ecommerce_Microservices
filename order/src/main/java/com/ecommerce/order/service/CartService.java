package com.ecommerce.order.service;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
//import com.ecommerce.order.model.Product;
//import com.ecommerce.order.model.User;
import com.ecommerce.order.repository.CartItemRepository;
//import com.ecommerce.order.repository.ProductRepository;
//import com.ecommerce.order.repository.UserRepository;
//import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
//import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
//    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
//    private final UserRepository userRepository;

    public void addToCart(Long userId, CartItemRequest request) {


//        Product product = productRepository.findById(request.getProductId())  // fetch product by id or throw exception if product does not exist
//                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
//
//        User user = userRepository.findById(userId) // fetch user by id or throw exception if user does not exist
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(String.valueOf(userId), request.getProductId()); //find existing cart item for the user and product


        int finalQuantity = (existingCartItem != null) // calculate final quantity (existing quantity + new request quantity)
                ? existingCartItem.getQuantity() + request.getQuantity()
                : request.getQuantity();


//        if (finalQuantity > product.getStockQuantity()) { // validate that requested quantity does not exceed available stock
//            throw new IllegalStateException("Product out of stock");
//        }

//        BigDecimal totalPrice =  product.getPrice().multiply(BigDecimal.valueOf(finalQuantity)); //  calculate total price based on final quantity
         BigDecimal totalPrice = BigDecimal.valueOf(10000);

        if (existingCartItem == null) {
            existingCartItem = new CartItem();
            existingCartItem.setUserId(String.valueOf(userId));
            existingCartItem.setProductId(request.getProductId());
        existingCartItem.setQuantity(finalQuantity);
        existingCartItem.setPrice(totalPrice);
        }


        cartItemRepository.save(existingCartItem);
    }

    public boolean removeFromCart(String userId, String productId) {
//        Optional<Product> product = productRepository.findById(productId);
//        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId , productId);

        if (cartItem != null) {
                cartItemRepository.delete(cartItem);
                return true;
            }


        return false;
    }

    public List<CartItem> getCartItems(String userId) {
//       User user =  userRepository.findById(Long.valueOf(userId))
//               .orElseThrow(() -> new EntityNotFoundException("User not found"));
//        return cartItemRepository.findByUser(user);
       return cartItemRepository.findByUserId(userId);



    }

    public void clearCart(String userId) {
//        userRepository.findById(Long.valueOf(userId))
//                        .ifPresent(cartItemRepository::deleteByUser);
        cartItemRepository.deleteByUserId(userId);

    }
}