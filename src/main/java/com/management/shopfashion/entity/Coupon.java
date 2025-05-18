package com.management.shopfashion.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "coupon")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_coupon")
	private int id_coupon;
	@Column(name = "code")
	private String code;
	@Column(name = "type_code")
	private String typeCode;//percent:giảm phần trăm, fixed:giảm theo số tiền cụ thể
	@Column(name = "decrease_percent")
	private int decreasePercent;
	@Column(name = "decreaseCrash")
	private long decreaseCrash;
	@Column(name="minOrderValue")
	private double minOrderValue;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private LocalDate startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private LocalDate endDate;
	@Column(name="is_delete")
	private int isDelete;
	@Column(name="category")
	private String category; //shipping: phí ship, product: sản phẩm
	@OneToMany(mappedBy = "couponProduct")
	private List<Order>orders;
	

}
