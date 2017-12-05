package com.github.gnoely.twitter

import org.springframework.stereotype.Component
import twitter4j.RateLimitStatusEvent
import twitter4j.RateLimitStatusListener

@Component
class RateLimitListener : RateLimitStatusListener {

    override fun onRateLimitReached(event: RateLimitStatusEvent) {

        println(
            ">>>>>>  RATE LIMIT REACHED: " +
                "is IP Rate Limit Status?  : ${event.isAccountRateLimitStatus}, " +
                "isAccountRateLimitStatus ${event.isAccountRateLimitStatus}, " +
                "Limit: ${event.rateLimitStatus.limit}, " +
                "Requests Remaining: ${event.rateLimitStatus.remaining}, " +
                "Time till reset: ${event.rateLimitStatus.secondsUntilReset}")

    }

    override fun onRateLimitStatus(event: RateLimitStatusEvent) {

        println(
            "Rate limit status" +
                "is IP Rate Limit Status?  : ${event.isAccountRateLimitStatus}, " +
                "isAccountRateLimitStatus ${event.isAccountRateLimitStatus}, " +
                "Limit: ${event.rateLimitStatus.limit}, " +
                "Requests Remaining: ${event.rateLimitStatus.remaining}, " +
                "Time till reset: ${event.rateLimitStatus.secondsUntilReset}")

    }
}
