package com.github.gnoely.twitter


import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import twitter4j.Status
import twitter4j.StatusUpdate
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import java.net.URL
import java.nio.file.Files
import javax.annotation.PostConstruct


/**
 * Example application that uses OAuth method to acquire access to your account.<br></br>
 * This application illustrates how to use OAuth method with Twitter4J.<br></br>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */

@Component
@ConfigurationProperties("twitter.client.out")
class TwitterOut {

    @Autowired lateinit var config : TwitterConfiguration
    @Autowired lateinit var limitListener : RateLimitListener

    var enabled: Boolean = false

    val twitter = TwitterFactory().instance!!

    val fakeStatusId = 0

    @PostConstruct
    private fun auth() {
        if (!enabled) {
            println("Twitter Client Out IS DISABLED")
            return
        }
        println("Twitter Client Out IS ENABLED")

        // The factory instance is re-useable and thread safe.
        val accessToken = loadAccessToken()
        twitter.setOAuthConsumer(config.getConsumerKey(), config.getConsumerSecret())
        twitter.oAuthAccessToken = accessToken
        twitter.addRateLimitStatusListener(limitListener)
    }

    fun sendReply(originalStatusId : Long, mentionUser : String , body : String, imageUrl: String?): Long {
        val message = buildMessage(originalStatusId, mentionUser, body, imageUrl)
        if (enabled) {
            val status = twitter.updateStatus(message)
            println("Successfully updated the status to [" + status.text + "].")
            return status.id
        } else {
            println(">>> Outbound tweets disabled -> would have sent message ${message.status}")
            return fakeStatusId + 1L
        }
    }


    private fun buildMessage(inReplyToStatusId: Long, mentionUser: String, body: String, imageUrl: String?): StatusUpdate {
        val statusUpdate = StatusUpdate("@$mentionUser $body")
        if (imageUrl != null) {
            val url = URL(imageUrl)
            val file = Files.createTempFile("temp", ".tmp").toFile()
            FileUtils.copyURLToFile(url, file)
            statusUpdate.media(file)
            file.deleteOnExit()
        }
        statusUpdate.inReplyToStatusId(inReplyToStatusId)
        return statusUpdate
    }

    private fun loadAccessToken() : AccessToken  {
        val token = config.getAccessToken()
        val secret = config.getAccessTokenSecret()

        return AccessToken(token, secret)
    }
}
