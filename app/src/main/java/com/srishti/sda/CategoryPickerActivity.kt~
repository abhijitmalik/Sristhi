package com.srishti.sda

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.srishti.sda.model.Category

class CategoryPickerActivity : AppCompatActivity() {
    private lateinit var chipGroup: ChipGroup
    private lateinit var continueButton: MaterialButton
    private val selectedCategories = mutableSetOf<String>()
    private val categories = listOf(



        Category("children", "Children", R.drawable.ic_google),
        Category("biographies", "Biographies", R.drawable.ic_google),
        Category("crime", "Crime", R.drawable.ic_google),
        Category("business", "Economy & Business", R.drawable.ic_google),
        Category("erotica", "Erotica", R.drawable.ic_google),
        Category("nonfiction", "Non-Fiction", R.drawable.ic_google),
        Category("fantasy", "Fantasy & SciFi", R.drawable.ic_google),
        Category("history", "History", R.drawable.ic_google),
        Category("classics", "Classics", R.drawable.ic_google),
        Category("poetry", "Lyric Poetry", R.drawable.ic_google),
        Category("shortstories", "Short stories", R.drawable.ic_google),
        Category("development", "Personal development", R.drawable.ic_google),
        Category("fiction", "Fiction", R.drawable.ic_google),
        Category("language", "Language", R.drawable.ic_google),
        Category("thrillers", "Thrillers", R.drawable.ic_google),
        Category("youngadult", "Teens & Young Adult", R.drawable.ic_google),
        Category("romance", "Romance", R.drawable.ic_google),
        Category("religion", "Religion & Spirituality", R.drawable.ic_google)



       /* Category("children", "Children", R.drawable.ic_children),
        Category("biographies", "Biographies", R.drawable.ic_biography),
        Category("crime", "Crime", R.drawable.ic_crime),
        Category("business", "Economy & Business", R.drawable.ic_business),
        Category("erotica", "Erotica", R.drawable.ic_erotica),
        Category("nonfiction", "Non-Fiction", R.drawable.ic_nonfiction),
        Category("fantasy", "Fantasy & SciFi", R.drawable.ic_fantasy),
        Category("history", "History", R.drawable.ic_history),
        Category("classics", "Classics", R.drawable.ic_classics),
        Category("poetry", "Lyric Poetry", R.drawable.ic_poetry),
        Category("shortstories", "Short stories", R.drawable.ic_shortstory),
        Category("development", "Personal development", R.drawable.ic_development),
        Category("fiction", "Fiction", R.drawable.ic_fiction),
        Category("language", "Language", R.drawable.ic_language),
        Category("thrillers", "Thrillers", R.drawable.ic_thriller),
        Category("youngadult", "Teens & Young Adult", R.drawable.ic_youngadult),
        Category("romance", "Romance", R.drawable.ic_romance),
        Category("religion", "Religion & Spirituality", R.drawable.ic_religion)*/
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_picker)

        chipGroup = findViewById(R.id.categoryChipGroup)
        continueButton = findViewById(R.id.continueButton)
        val skipButton = findViewById<TextView>(R.id.skipButton)

        setupCategoryChips()
        setupButtons()
    }

    private fun setupCategoryChips() {
        categories.forEach { category ->
            val chip = layoutInflater.inflate(
                R.layout.item_category_chip,
                chipGroup,
                false
            ) as Chip

            chip.apply {
                id = View.generateViewId()
                text = category.name
                setChipIconResource(category.icon)
                isCheckable = true
                
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedCategories.add(category.id)
                    } else {
                        selectedCategories.remove(category.id)
                    }
                    updateContinueButton()
                }
            }
            chipGroup.addView(chip)
        }
    }

    private fun setupButtons() {
        continueButton.setOnClickListener {
            // TODO: Save selected categories and navigate to main content
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        findViewById<TextView>(R.id.skipButton).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun updateContinueButton() {
        continueButton.isEnabled = selectedCategories.size >= 3
    }
} 