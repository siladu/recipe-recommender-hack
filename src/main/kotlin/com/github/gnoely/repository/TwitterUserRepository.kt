package com.github.gnoely.repository

import com.github.gnoely.model.TwitterUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TwitterUserRepository : CrudRepository<TwitterUser, String>
