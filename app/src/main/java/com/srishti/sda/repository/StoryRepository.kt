package com.srishti.sda.repository

import com.srishti.sda.model.StoryDataModel
import com.srishti.sda.utils.Constants.Companion.db
import kotlinx.coroutines.tasks.await
import java.util.UUID

class StoryRepository {

    suspend fun addStoryToCategory(categoryId: String, story: StoryDataModel): Boolean {
        return try {
//            db.collection("All_Story_Data")
//                .document(categoryId)
//                .collection("stories")
//                .document(story.id)
//                .set(story)
//                .await()
            val storyId = UUID.randomUUID().toString()  // Generates a unique ID

            db.collection("All_Story_Data")
                .document(categoryId)
                .collection("stories")
                .document(storyId)  // Assign the generated ID
                .set(story)
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }
}