package com.metoo.dto.order;

import com.metoo.core.domain.merchant.Merchant;
import com.metoo.core.domain.order.*;
import com.metoo.core.domain.product.Product;
import com.metoo.core.domain.user.User;
import com.metoo.dto.DTO;
import com.metoo.dto.merchant.MerchantDTO;
import com.metoo.dto.product.ProductDTO;
import com.metoo.dto.user.UserDTO;
import com.metoo.utils.JodaUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/11
 */
public class OrderDTO extends DTO {

    private String type;
    private MerchantDTO merchant = new MerchantDTO();
    private UserDTO user = new UserDTO();
    private ProductDTO product = new ProductDTO();
    private String creationTime;
    private OrderStatus status;

    private String bookingDate;
    private Integer quantity;
    private Integer days; // hotel

    private String username;
    private String telephone;

    public OrderDTO() {
        super();
    }

    public OrderDTO(Order order) {
        super(order);
        this.type = order.getClass().getSimpleName();
        User user = order.getUser();
        Product product = order.getProduct();

        if (user != null) {
            this.user = new UserDTO(user);
        }
        this.product = new ProductDTO(product);
        this.merchant = this.product.getMerchant();
        this.creationTime = JodaUtils.dateTimeToString(order.getCreationTime());

        if (order instanceof FoodOrder) {
            FoodOrder foodOrder = (FoodOrder) order;
            this.quantity = foodOrder.getQuantity();
        } else if (order instanceof SceneryOrder) {
            SceneryOrder sceneryOrder = (SceneryOrder) order;
            this.quantity = sceneryOrder.getQuantity();
        } else if (order instanceof HotelOrder) {
            HotelOrder hotelOrder = (HotelOrder) order;
            this.quantity = hotelOrder.getQuantity();
            this.days = hotelOrder.getDays();
        }
        this.bookingDate = JodaUtils.localDateToString(order.getBookingDate());
        this.username = order.getUsername();
        this.telephone = order.getTelephone();

        this.status = order.getStatus();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MerchantDTO getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantDTO merchant) {
        this.merchant = merchant;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public static List<OrderDTO> toDTOs(List<Order> orders) {
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO(order);
            orderDTOs.add(orderDTO);
        }
        return orderDTOs;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
