package com.srishti.sda.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.srishti.sda.model.Story
import androidx.lifecycle.viewModelScope
import com.srishti.sda.model.StoryDataModel
import com.srishti.sda.repository.StoryRepository
import kotlinx.coroutines.launch
import com.srishti.sda.model.CategoryWithStories

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class StoryViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>()

    // Rest of your code remains the same


    private val db = FirebaseFirestore.getInstance()

    
    private val _featuredStories = MutableLiveData<List<Story>>()
    val featuredStories: LiveData<List<Story>> = _featuredStories

    private val _trendingStories = MutableLiveData<List<Story>>()
    val trendingStories: LiveData<List<Story>> = _trendingStories

    private val _recentStories = MutableLiveData<List<Story>>()
    val recentStories: LiveData<List<Story>> = _recentStories

    private val _editorsChoice = MutableLiveData<List<Story>>()
    val editorsChoice: LiveData<List<Story>> = _editorsChoice

    private val _uploadStatus = MutableLiveData<Result<Boolean>>()
    val uploadStatus: LiveData<Result<Boolean>> get() = _uploadStatus
    private val repository = StoryRepository()

    private val _categoriesWithStories = MutableLiveData<List<CategoryWithStories>>()
    val categoriesWithStories: LiveData<List<CategoryWithStories>> get() = _categoriesWithStories

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun loadFeaturedStories() {
        db.collection("stories")
           // .whereEqualTo("featured", true)
            .limit(10)
            .get()
            .addOnSuccessListener { documents ->
                _featuredStories.value = documents.toObjects(Story::class.java)
            }
    }

    fun loadTrendingStories() {
        db.collection("stories")
            //.orderBy("likes", Query.Direction.DESCENDING)
            .limit(10)
            .get()
            .addOnSuccessListener { documents ->
                _trendingStories.value = documents.toObjects(Story::class.java)
            }
    }

    fun loadRecentPublications() {
        db.collection("stories")
            //.orderBy("publishDate", Query.Direction.DESCENDING)
            .limit(10)
            .get()
            .addOnSuccessListener { documents ->
                _recentStories.value = documents.toObjects(Story::class.java)
            }
    }

    fun loadEditorsChoice() {
        db.collection("stories")
           // .whereEqualTo("editorsChoice", true)
            .limit(10)
            .get()
            .addOnSuccessListener { documents ->
                _editorsChoice.value = documents.toObjects(Story::class.java)
            }
    }

    // Add Story and Observe Status
    fun addStory(categoryId: String, story: StoryDataModel) {
        viewModelScope.launch {
            try {
                val isSuccess = repository.addStoryToCategory(categoryId, story)
                if (isSuccess) {
                    _uploadStatus.value = Result.success(true)
                } else {
                    _uploadStatus.value = Result.failure(Exception("Upload Failed"))
                }
            } catch (e: Exception) {
                _uploadStatus.value = Result.failure(e)
            }
        }
    }

    fun fetchAllCategoriesWithStories() {
        viewModelScope.launch {
            try {
                val data = repository.getAllCategoriesWithStories()
                if (data.isEmpty()) {
                    _errorMessage.postValue("Unable to show details")
                } else {
                    _categoriesWithStories.postValue(data)
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error fetching data: ${e.message}")
            }
        }
    }
}