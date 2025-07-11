package ipvc.tp.devhive.domain.model

import com.google.firebase.Timestamp

data class GroupMessage(
    val id: String,
    val studyGroupId: String,
    val content: String,
    val senderUid: String,
    val senderName: String,
    val senderImageUrl: String,
    val createdAt: Timestamp,
    val attachments: List<MessageAttachment>,
    val replyToMessageId: String? = null,
    val isEdited: Boolean = false,
    val editedAt: Timestamp? = null
)
