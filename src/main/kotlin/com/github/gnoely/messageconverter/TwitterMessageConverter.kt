package com.github.gnoely.messageconverter

import com.github.gnoely.twitter.TwitterConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class TwitterMessageConverter {

    @Autowired lateinit var twitterConfig : TwitterConfiguration


    fun convertMessageToQuery(message: String) : String {
        return everythingAfterKeyword(twitterConfig.getListenFor(), message)
    }

    private fun everythingAfterKeyword(handle: String, message: String) : String {
        val split = message.split(handle + " ")
        if (split.size < 2) {
            return ""
        }
        return convertSpaces(split[1])
    }

    private fun convertSpaces(query: String) : String {
        return query.replace(" ", "+")
    }

}

