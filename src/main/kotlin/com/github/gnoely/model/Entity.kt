package com.github.gnoely.model

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import javax.persistence.*

@Entity
@Table(name="orders")
data class Order(
    @Id
        @Column(name="order_id")
        val id: String = "",
    val userId: String ="",
    val evalSet: String ="",
    val orderNumber: Int = -1,
    val orderDayOfTheWeek: Int = -1,
    val orderHourOfDay: Int = -1,
    val daysSincePriorOrder: Int = -1,
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    @JoinTable(
            name = "orders_products_train",
            joinColumns = arrayOf(JoinColumn(name = "order_id")),
            inverseJoinColumns = arrayOf(JoinColumn(name="product_id"))
    )
    val productsTrain: List<Product> = emptyList(),
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    @JoinTable(
            name = "orders_products_prior",
            joinColumns = arrayOf(JoinColumn(name = "order_id")),
            inverseJoinColumns = arrayOf(JoinColumn(name="product_id"))
    )
    val productsPrior: List<Product> = emptyList()
)

@Entity
data class Product(
        @Id
        @Column(name="product_id")
        val id: String = "",
        val name: String = "<UNKNWON>",
        val aisleId: String = "<UKNOWN>",
        val departmentId: String = "<UKNOWN>",
        val isFoodDepartment: Boolean = false,
        val isExactIgtMatch: Boolean = false,
        val isIgtMatch: Boolean = false,
        val rawMatches: String = ""
)

@Entity
@Table(name="twitter_users")
data class TwitterUser(
    @Id
    val handle: String = "",
    val userId: String = ""
)
