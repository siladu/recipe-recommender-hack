package com.github.gnoely.repository

import com.github.gnoely.model.Aisle
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AisleRepository : CrudRepository<Aisle, String>