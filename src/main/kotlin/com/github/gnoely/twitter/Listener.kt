package com.github.gnoely.twitter

import com.github.gnoely.model.Reply
import com.github.gnoely.service.ConversationService
import com.github.gnoely.service.ReplyBuildingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import twitter4j.StallWarning
import twitter4j.Status
import twitter4j.StatusDeletionNotice
import twitter4j.StatusListener
import java.time.LocalTime

@Component
class Listener : StatusListener {

    @Autowired lateinit var twitterOut : TwitterOut
    @Autowired lateinit var replyBuildingService : ReplyBuildingService
    @Autowired lateinit var conversationService : ConversationService

    override fun onStatus(status: Status) {
        // Send status to conversation service
         conversationService.handleMessage(status.user.screenName, status.user.name, status.id, status.inReplyToStatusId, status.inReplyToScreenName, status.text)
//        val reply: Reply = replyBuildingService.buildReply(status.text)
//        twitterOut.sendReply(status.id, status.user.screenName, reply.message + " " + LocalTime.now().toString(), reply.imageUrl)
//        println(status)

    }

    override fun onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}

    override fun onTrackLimitationNotice(limit: Int) {}

    override fun onScrubGeo(user: Long, upToStatus: Long) {}

    override fun onStallWarning(warning: StallWarning) {}

    override fun onException(e: Exception) {}
}
