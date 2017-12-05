package com.github.gnoely.service

import com.github.gnoely.model.Order
import com.github.gnoely.model.Product
import com.github.gnoely.repository.OrderRepository
import com.github.gnoely.repository.ProductRepository
import com.github.gnoely.repository.TwitterUserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ProductService(val productRepo: ProductRepository) {

    fun findAllInFoodDepartments(): List<Product> {
        return productRepo.findAllByIsFoodDepartment(true)
    }
}
