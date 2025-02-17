package com.srishti.sda.repository

import android.util.Log
import com.google.firebase.firestore.Source
import com.srishti.sda.model.CategoryWithStories
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
    suspend fun getAllCategoriesWithStories(): List<CategoryWithStories> {
        Log.d("dip1","1")
        val categoryList = mutableListOf<CategoryWithStories>()
        Log.d("dip2","2")
        // Fetch all categories
        val categoryDocs = db.collection("All_Story_Data").get().await()
        Log.d("dip3","3."+categoryDocs.documents)

        for (categoryDoc in categoryDocs.documents) {
            val categoryId = categoryDoc.id  // Category Name

            Log.d("category",categoryId)
            // Fetch stories inside this category
            val storiesSnapshot = categoryDoc.reference.collection("stories").get().await()
            val stories = storiesSnapshot.documents.mapNotNull { doc ->
                doc.toObject(StoryDataModel::class.java) // Convert Firestore document to model
            }

            Log.d("StoryData1",stories.toString())
            if (stories.isNotEmpty()) {
                Log.d("StoryData",stories.toString())
                categoryList.add(CategoryWithStories(categoryId, stories))
            }
        }

        return categoryList
    }
}