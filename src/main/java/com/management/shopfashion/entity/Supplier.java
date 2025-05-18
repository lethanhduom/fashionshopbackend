package com.management.shopfashion.entity;

import java.util.List;

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
@Table(name = "suppiier")
public class Supplier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_supplier")
	private int id_supplier;
	@Column(name="name_supplier")
	private String nameSupplier;
	@Column(name="email")
	private String email;
	@Column(name="phoneNumber")
	private String phoneNumber;
	@Column(name="adress")
	private String address;
	@OneToMany(mappedBy = "supplier")
	private List<ImportOrder>importOrders;

}
