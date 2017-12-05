package com.github.gnoely.service

import com.github.gnoely.lex.LexClient
import com.github.gnoely.messageconverter.TwitterMessageConverter
import com.github.gnoely.twitter.TwitterOut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class ConversationService {

    @Autowired private lateinit var twitterOut : TwitterOut
    @Autowired private lateinit var lexClient : LexClient
    @Autowired private lateinit var replyBuilderService: ReplyBuildingService
    @Autowired private lateinit var twitterMessageConverter: TwitterMessageConverter


    private val READY_FOR_FULFILLMENT = "ReadyForFulfillment"
    private val FAILED = "Failed"

    private val PERSONAL_RECOMMMENDATION_INENT = "PersonalRecommendation"
    private val RECIPE_STARTER_INTENT = "RecipeStarter"

    private val sessions : MutableList<Session> = mutableListOf()

    private fun getSession(
        fromScreenName: String,
        fromUserName : String,
        inReplyToStatusId : Long?,
        inReplyToScreenName : String?  ) : Session {



        var session : Session? = null

        if (inReplyToStatusId != null && inReplyToStatusId > 0 && inReplyToScreenName == "BallmerPeakyB") {
            session = sessions.find { it.lastStatusIdFromUs == inReplyToStatusId }
        }

        if (session == null) {
            session = Session(userHandle = fromScreenName,
                userName = fromUserName,
                userId = UUID.randomUUID().toString())
            sessions.add(session)
        }
        println("Using sessionID = ${session.userId}")
        return session
    }

    fun handleMessage(
        fromScreenName: String,
        fromUserName : String,
        inStatusId: Long,
        inReplyToStatusId : Long?,
        inReplyToScreenName : String?,
        replyText: String ) {

        if (fromScreenName == "BallmerPeakyB") {
            return
        }

        val messageText = twitterMessageConverter.convertMessageToQuery(replyText)
        val session = getSession(fromScreenName, fromUserName, inReplyToStatusId, inReplyToScreenName)
        val result = lexClient.sendTextToLex(messageText, session.userId)

        if (READY_FOR_FULFILLMENT.equals(result?.dialogState)) {
            if (PERSONAL_RECOMMMENDATION_INENT.equals(result?.intentName)) {
                val reply = replyBuilderService.buildReply(session.userHandle, emptyList())
                twitterOut.sendReply(inStatusId, fromScreenName, reply.message, reply.imageUrl)
            } else if (RECIPE_STARTER_INTENT.equals(result?.intentName)) {
                val ingredients = mutableListOf<String>()
                result?.slots?.get("mainIngredient")?.let { ingredients.add(it) }
                result?.slots?.get("secondIngredient")?.let { ingredients.add(it) }
                val cuisineString = result?.slots?.get("cuisine")?: "None"
                val reply = replyBuilderService.buildReplyWithCuisine(session.userHandle, ingredients, cuisineString)
                twitterOut.sendReply(inStatusId, fromScreenName, reply.message, reply.imageUrl)
            } else {
                val ingredients = mutableListOf<String>()
                result?.slots?.get("ingredientsOne")?.let { ingredients.add(it) }
                result?.slots?.get("ingredientsTwo")?.let { ingredients.add(it) }
                val reply = replyBuilderService.buildReply(session.userHandle, ingredients)
                twitterOut.sendReply(inStatusId, fromScreenName, reply.message, reply.imageUrl)

                session.lastStatusIdFromUs

            }

        } else if (FAILED.equals(result?.dialogState)){
            println("FAILED: $result $fromScreenName $messageText $session")

        } else {
            val outStatusId = twitterOut.sendReply(inStatusId, fromScreenName, result?.message ?: "", null)
            println("outStatusId $outStatusId")
            session.lastStatusIdFromUs = outStatusId
        }
    }


}

data class Session(var lastStatusIdFromUs: Long? = null,
              var lastDialogState: String? = null,
              val userHandle: String,
              val userName: String,
              val userId: String)
