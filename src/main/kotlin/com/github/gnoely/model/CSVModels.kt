package com.github.gnoely.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Aisle(
        @Id
        @Column(name="aisle_id")
        val id: String = "",
        val aisle: String = ""
)

@Entity
data class Department(
        @Id
        @Column(name="department_id")
        val departmentId: Int = -1,
        val department: String = ""
)
