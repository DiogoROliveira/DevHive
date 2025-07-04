package ipvc.tp.devhive.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ipvc.tp.devhive.data.local.converter.AttachmentListConverter
import ipvc.tp.devhive.data.model.Attachment
import ipvc.tp.devhive.data.model.Comment
import ipvc.tp.devhive.data.util.SyncStatus
import com.google.firebase.Timestamp

@Entity(tableName = "comments")
@TypeConverters(AttachmentListConverter::class)
data class CommentEntity(
    @PrimaryKey
    val id: String,
    val materialId: String,
    val userId: String,
    val userName: String,
    val userImageUrl: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
    val likes: Int,
    val liked: Boolean,
    val parentCommentId: String?,
    val attachments: List<Attachment>,
    val lastSyncTimestamp: Long,

    // Campos para sincronização
    val syncStatus: String,
    val lastSyncedAt: Long,
    val isLocalOnly: Boolean,
    val pendingOperation: String?
) {
    companion object {
        fun fromComment(comment: Comment, syncStatus: String = SyncStatus.SYNCED): CommentEntity {
            return CommentEntity(
                id = comment.id,
                materialId = comment.materialId,
                userId = comment.userId,
                userName = comment.userName,
                userImageUrl = comment.userImageUrl,
                content = comment.content,
                createdAt = comment.createdAt.seconds * 1000 + comment.createdAt.nanoseconds / 1000000,
                updatedAt = comment.updatedAt.seconds * 1000 + comment.updatedAt.nanoseconds / 1000000,
                likes = comment.likes,
                liked = comment.liked,
                parentCommentId = comment.parentCommentId,
                attachments = comment.attachments,
                lastSyncTimestamp = comment.lastSync.seconds * 1000 + comment.lastSync.nanoseconds / 1000000,
                syncStatus = syncStatus,
                lastSyncedAt = System.currentTimeMillis(),
                isLocalOnly = syncStatus == SyncStatus.PENDING_UPLOAD,
                pendingOperation = if (syncStatus != SyncStatus.SYNCED) "UPDATE" else null
            )
        }

        fun toComment(entity: CommentEntity): Comment {
            return Comment(
                id = entity.id,
                materialId = entity.materialId,
                userId = entity.userId,
                userName = entity.userName,
                userImageUrl = entity.userImageUrl,
                content = entity.content,
                createdAt = Timestamp(entity.createdAt / 1000, ((entity.createdAt % 1000) * 1000000).toInt()),
                updatedAt = Timestamp(entity.updatedAt / 1000, ((entity.updatedAt % 1000) * 1000000).toInt()),
                likes = entity.likes,
                liked = entity.liked,
                parentCommentId = entity.parentCommentId,
                attachments = entity.attachments,
                lastSync = Timestamp(entity.lastSyncTimestamp / 1000, ((entity.lastSyncTimestamp % 1000) * 1000000).toInt())
            )
        }
    }
}
