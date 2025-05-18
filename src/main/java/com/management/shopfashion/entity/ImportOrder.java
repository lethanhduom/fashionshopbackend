package com.management.shopfashion.entity;

import java.util.List;

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
@Table(name="import_order")
public class ImportOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_import_order")
	private int id_import_order;
	@Column(name = "import_date")
	private String import_date;
	@Column(name = "totalPrice")
	private double totalPrice;
	@ManyToOne
	@JoinColumn(name = "id_supplier")
	private Supplier supplier;
	@OneToMany(mappedBy = "importOrder")
	private List<ImportOrderDetail>importOrderDetails;

}
