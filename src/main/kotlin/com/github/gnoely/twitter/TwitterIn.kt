package com.github.gnoely.twitter

import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.Hosts
import com.twitter.hbc.core.HttpHosts
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint
import com.twitter.hbc.core.event.Event
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.auth.Authentication
import com.twitter.hbc.httpclient.auth.OAuth1
import com.twitter.hbc.twitter4j.Twitter4jStatusClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import javax.annotation.PostConstruct


@Component
@ConfigurationProperties("twitter.client.in")
class TwitterIn {

    @Autowired lateinit var twitterConfig : TwitterConfiguration
    @Autowired lateinit var listener : Listener

    var enabled: Boolean = false

    /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
    private val msgQueue : BlockingQueue<String> = LinkedBlockingQueue<String>(100000)
    private val eventQueue : BlockingQueue<Event> = LinkedBlockingQueue<Event>(1000)

    /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
    private val hosebirdHosts : Hosts = HttpHosts(Constants.STREAM_HOST)

    // Optional: set up some followings and track terms


    val numProcessingThreads : Int = 4


    private fun auth() : Authentication {
        return OAuth1(
                twitterConfig.getConsumerKey(),
                twitterConfig.getConsumerSecret(),
                twitterConfig.getAccessToken(),
                twitterConfig.getAccessTokenSecret())
    }



    @PostConstruct
    fun connect()  {
        if (!enabled) {
            println("Twitter Client In IS DISABLED")
            return
        }
        println("Twitter Client In IS ENABLED")

        val hosebirdEndpoint = StatusesFilterEndpoint()

        val terms : List<String> = listOf(twitterConfig.getListenFor())


        hosebirdEndpoint.trackTerms(terms)

        val builder = ClientBuilder()
                .name("BallmerPeakyBlinders")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(auth())
                .endpoint(hosebirdEndpoint)
                .processor(StringDelimitedProcessor(msgQueue))
                .eventMessageQueue(eventQueue)                          // optional: use this if you want to process client events

        val hosebirdClient = builder.build()
        // Attempts to establish a connection.

        val service = Executors.newFixedThreadPool(numProcessingThreads)


        val t4jClient = Twitter4jStatusClient(hosebirdClient, msgQueue, listOf(listener), service)

        // Establish a connection
        t4jClient.connect()

        println("Connected stream listener")

        for (threads in 0 until numProcessingThreads) {
            // This must be called once per processing thread
            t4jClient.process()
        }

    }

}
