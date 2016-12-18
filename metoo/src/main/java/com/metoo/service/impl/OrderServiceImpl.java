package com.metoo.service.impl;

import com.metoo.core.domain.merchant.Merchant;
import com.metoo.core.domain.merchant.MerchantBusinessType;
import com.metoo.core.domain.merchant.MerchantRepository;
import com.metoo.core.domain.order.*;
import com.metoo.core.domain.product.Product;
import com.metoo.core.domain.product.ProductRepository;
import com.metoo.core.domain.user.User;
import com.metoo.core.domain.user.UserRepository;
import com.metoo.dto.order.OrderDTO;
import com.metoo.exception.ErrorMap;
import com.metoo.exception.MetooException;
import com.metoo.service.OrderService;
import com.metoo.utils.JodaUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/11
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository<Order> orderRepository;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private ProductRepository<Product> productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<OrderDTO> loadOrders(OrderStatus status) {
        List<Order> orders;
        if (status == null) {
            orders = orderRepository.findAll();
        } else {
            orders = orderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
                criteriaQuery.where(criteriaBuilder.equal(root.get("status"), status));
                return null;
            });
        }
        return OrderDTO.toDTOs(orders);
    }

    @Override
    public Long saveOrder(OrderDTO orderDTO) {
        Long merchantId = orderDTO.getMerchant().getId();
        Long productId = orderDTO.getProduct().getId();
        if (merchantId == null) {
            throw new MetooException(ErrorMap.INVALID_MERCHANT_ID);
        }
        if (productId == null) {
            throw new MetooException(ErrorMap.INVALID_PRODUCT_ID);
        }
        Integer quantity = orderDTO.getQuantity();
        if (quantity == null) {
            throw new MetooException(ErrorMap.INVALID_QUANTITY);
        }
        Merchant merchant = merchantRepository.findOne(merchantId);
        if (merchant == null) {
            throw new MetooException(ErrorMap.INVALID_MERCHANT_ID);
        }
        Product product = productRepository.findOne(productId);
        if (product == null) {
            throw new MetooException(ErrorMap.INVALID_PRODUCT_ID);
        }
        User user = null;
        Long userId = orderDTO.getUser().getId();
        if (userId != null) {
            user = userRepository.findOne(userId);
        }
        Order order;
        MerchantBusinessType businessType = merchant.getBusinessType();
        Integer days = orderDTO.getDays();
        switch (businessType) {
            case HOTEL:
                if (days == null) {
                    throw new MetooException(ErrorMap.INVALID_DAYS);
                }
                order = new HotelOrder(merchant, user, product);
                break;
            case FOOD:
                order = new FoodOrder(merchant, user, product);
                break;
            case SCENERY:
                order = new SceneryOrder(merchant, user, product);
                break;
            default:
                throw new MetooException(ErrorMap.INVALID_BUSINESS_TYPE);
        }
        String username = orderDTO.getUsername();
        String telephone = orderDTO.getTelephone();
        if (order instanceof FoodOrder) {
            order.update(null, username, telephone);
            ((FoodOrder) order).setQuantity(quantity);
        } else if (order instanceof HotelOrder) {
            ((HotelOrder) order).update(quantity, days);
        } else if (order instanceof SceneryOrder) {
            ((SceneryOrder) order).setQuantity(quantity);
        }
        String bookingDateStr = orderDTO.getBookingDate();
        LocalDate bookingDate = null;
        if (JodaUtils.isValidDate(bookingDateStr)) {
            bookingDate = JodaUtils.parseLocalDate(bookingDateStr);
        }
        order.update(bookingDate, username, telephone);
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public OrderDTO loadOrderById(Long id) {
        Order order = orderRepository.findOne(id);
        return new OrderDTO(order);
    }

    @Override
    public List<OrderDTO> loadOrderByUserId(Long userId) {
        List<Order> orders = orderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.where(criteriaBuilder.equal(root.get("user").get("id"), userId));
            return null;
        });
        return OrderDTO.toDTOs(orders);
    }

    @Override
    public void changeOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findOne(id);
        order.updateStatus(status);
        orderRepository.save(order);
    }
}
