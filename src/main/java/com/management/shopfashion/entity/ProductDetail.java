package com.management.shopfashion.entity;

import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "product_detail")
public class ProductDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_product_detail")
private int id_product_detail;
	@Column(name = "stock")
private int stock;
	@Column(name = "price")
private double price;
	@Column (name = "is_delete")
private int isDelete;
	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;
    @ManyToOne
    @JoinColumn(name="id_color")
    private Color color;
    @ManyToOne
    @JoinColumn(name = "id_size")
    private Size size;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product_image",referencedColumnName = "id_product_image")
    private productImage productImage;
    @OneToMany(mappedBy = "productDetail")
    private List<CartItem>cartItems;
    @OneToMany(mappedBy = "productDetail")
    private List<OrderDetail>orderDetails;
    @OneToMany(mappedBy = "productDetail")
    private List<OrderReturnDetail>orderReturnDetails;
    @OneToMany(mappedBy = "productDetail")
    private List<Comment>comments;
    @OneToMany(mappedBy = "productDetail")
    private List<ImportOrderDetail>importOrderDetails;
    
    
    
    

}
