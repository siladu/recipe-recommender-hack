package com.github.gnoely.twitter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment

@Configuration
@PropertySource("classpath:/secret/twitter.properties")
class TwitterConfiguration {

    @Autowired
    internal var env: Environment? = null


    @Bean
    fun getConsumerKey(): String = (env!!.getProperty("twitter.consumerKey"))

    @Bean
    fun getConsumerSecret(): String = (env!!.getProperty("twitter.consumerSecret"))

    @Bean
    fun getAccessToken(): String = (env!!.getProperty("twitter.accessToken"))

    @Bean
    fun getAccessTokenSecret(): String = (env!!.getProperty("twitter.accessTokenSecret"))

    @Bean
    fun getListenFor(): String = (env!!.getProperty("twitter.listenFor"))
}
