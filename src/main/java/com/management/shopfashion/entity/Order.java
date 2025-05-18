package com.management.shopfashion.entity;

import java.util.List;

import org.springframework.lang.Nullable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity 
@Table(name = "order_invoice")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_order")
	private int id_order;
	@Column(name = "total_price")
	private double totalPrice;
	@Column(name="status") //0: vừa mới đặt hàng  1:đã thanh toán nếu là thanh toán bằng ck 2:đưa cho nhà vận chuyển 3:đã nhận 4: thành công
	private int status;
	@Column (name="payment_method")
	private String paymentMethod; //COD, BANK_TRANSFER
	@Column (name="recipient_name")
	private String recipientName;
	@Column (name="address")
	private String address;
	@Column (name="phoneNumber")
	private String phoneNumber;
	@Column (name="ship_fee")
	private double shipFee;
	@Column (name="total_price_coupon")
	private double totalPriceCoupon;
	@Column (name="ship_fee_coupon")
	private double shipFeeCoupon;
	@Column (name="product_fee_coupon")
	private double productFeeCoupon;
	@Column (name="total_price_product")
	private double totalPriceProduct;
	@Column (name="date_order")
	private String dateOrder;
	@Column (name="date_final")
	private String dateFinal;
	@Nullable
	@Column (name = "reason")
	private String reason;
	@Nullable
	@Column (name = "description_reason")
	private String descriptionReason;
	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;
	@OneToMany(mappedBy = "order_invoice")
	private List<OrderDetail>orderdetails;
	@ManyToOne
	@JoinColumn(name = "id_coupon_for_product")
	private Coupon couponProduct;
	@ManyToOne 
	@JoinColumn (name="id_coupon_for_shipping")
	private Coupon couponShipping;

}
