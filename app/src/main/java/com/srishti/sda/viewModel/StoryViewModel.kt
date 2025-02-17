package com.srishti.sda.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.srishti.sda.model.StoryDataModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srishti.sda.model.CategoryWithStories
import com.srishti.sda.repository.StoryRepository
import kotlinx.coroutines.launch

class StoryViewModel:ViewModel(){
    private val _uploadStatus = MutableLiveData<Result<Boolean>>()
    val uploadStatus: LiveData<Result<Boolean>> get() = _uploadStatus
    private val repository = StoryRepository()


    private val _categoriesWithStories = MutableLiveData<List<CategoryWithStories>>()
    val categoriesWithStories: LiveData<List<CategoryWithStories>> get() = _categoriesWithStories

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


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