package com.srishti.sda


import androidx.activity.enableEdgeToEdge

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat






import android.view.GestureDetector
import android.view.MotionEvent

import android.widget.LinearLayout


import androidx.core.view.GestureDetectorCompat

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlin.math.abs


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.max
import kotlin.math.min

class StoryReadingActivity : AppCompatActivity() {





    // UI components
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var storyContainer: ConstraintLayout
    private lateinit var storyScrollView: View
    private lateinit var pullHandle: ImageView
    private lateinit var readingProgress: ProgressBar
    private lateinit var readingTime: TextView
    private lateinit var storyContent: TextView
    private lateinit var chapterTitle: TextView
    private lateinit var nextChapterButton: View
    private lateinit var previousChapterButton: View
    private lateinit var fabSettings: FloatingActionButton
    private lateinit var storyTitle: TextView
    private lateinit var storyImage: ImageView
    private lateinit var likeButton: ImageButton
    private lateinit var shareButton: ImageButton
    private lateinit var menuButton: ImageButton

    // Settings components
    private lateinit var textSizeSeekBar: SeekBar
    private lateinit var textSpacingSeekBar: SeekBar
    private lateinit var brightnessSeekBar: SeekBar
    private lateinit var fontDefault: TextView
    private lateinit var fontSerif: TextView
    private lateinit var fontSansSerif: TextView
    private lateinit var fontMonospace: TextView
    private lateinit var themeLight: View
    private lateinit var themelightimage: View
    private lateinit var themeDark: View
    private lateinit var themeDarkimage: View
    private lateinit var themeSepia: View
    private lateinit var themeSepiaimage: View
    private lateinit var themeCustom: View
    private lateinit var themeCustomImage: View
    private lateinit var saveToFavorites: View
    private lateinit var shareWhatsApp: View
    private lateinit var shareAll: View
    private lateinit var autoScroll: View
    private lateinit var textToSpeech: View
    private lateinit var profileImage: CircleImageView
    private lateinit var profileName: TextView
    private lateinit var readingStats: TextView

    // Story data
    private var currentStory: Story? = null
    private var currentChapterIndex = 0
    private var isLiked = false
    private var isAutoScrollEnabled = false
    private var autoScrollSpeed = 20 // pixels per second
    private var isToolbarVisible = false
    private var initialBrightness: Float = 0.5f

    // Custom themes
    private val THEME_LIGHT = 0
    private val THEME_DARK = 1
    private val THEME_SEPIA = 2
    private val THEME_CUSTOM = 3
    private var currentTheme = THEME_LIGHT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_reading)

        // Initialize UI components
        initializeViews()

        // Set up listeners
        setupClickListeners()
        setupSeekBarListeners()
        setupFontSelectionListeners()
        setupThemeSelectionListeners()

        // Load story data
        loadStoryData()

        // Initialize reading settings
        initializeReadingSettings()

        // Set up the toolbar pull gesture
        setupPullGesture()
    }

    private fun initializeViews() {
        // Main container views
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.settings_navigation_view)
        toolbar = findViewById(R.id.toolbar)
        storyContainer = findViewById(R.id.story_container)
        storyScrollView = findViewById(R.id.story_scrollview)
        pullHandle = findViewById(R.id.pull_handle)
        readingProgress = findViewById(R.id.reading_progress)
        readingTime = findViewById(R.id.reading_time)

        // Story content views
        storyContent = findViewById(R.id.story_content)
        chapterTitle = findViewById(R.id.chapter_title)
        nextChapterButton = findViewById(R.id.next_chapter)
        previousChapterButton = findViewById(R.id.previous_chapter)
        fabSettings = findViewById(R.id.fab_settings)

        // Toolbar views
        storyTitle = findViewById(R.id.story_title)
        storyImage = findViewById(R.id.story_image)
        likeButton = findViewById(R.id.like_button)
        shareButton = findViewById(R.id.share_button)
        menuButton = findViewById(R.id.menu_button)

        // Settings drawer views
        textSizeSeekBar = findViewById(R.id.text_size_seekbar)
        textSpacingSeekBar = findViewById(R.id.text_spacing_seekbar)
        brightnessSeekBar = findViewById(R.id.brightness_seekbar)
        fontDefault = findViewById(R.id.font_default)
        fontSerif = findViewById(R.id.font_serif)
        fontSansSerif = findViewById(R.id.font_sans_serif)
        fontMonospace = findViewById(R.id.font_monospace)
        themeLight = findViewById(R.id.theme_light)
        themelightimage = findViewById(R.id.theme_light_image)
        themeDark = findViewById(R.id.theme_dark)
        themeDarkimage = findViewById(R.id.theme_dark_image)
        themeSepia = findViewById(R.id.theme_sepia)
        themeSepiaimage = findViewById(R.id.theme_sepia_image)
        themeCustom = findViewById(R.id.theme_custom)
        themeCustomImage = findViewById(R.id.theme_custom_image)
        saveToFavorites = findViewById(R.id.save_to_favorites)
        shareWhatsApp = findViewById(R.id.share_whatsapp)
        shareAll = findViewById(R.id.share_all)
        autoScroll = findViewById(R.id.auto_scroll)
        textToSpeech = findViewById(R.id.text_to_speech)
        profileImage = findViewById(R.id.profile_image)
        profileName = findViewById(R.id.profile_name)
        readingStats = findViewById(R.id.reading_stats)

        // Save initial brightness
        initialBrightness = getSystemBrightness()
    }

    private fun setupClickListeners() {
        // Navigation buttons
        nextChapterButton.setOnClickListener {
            navigateToNextChapter()
        }

        previousChapterButton.setOnClickListener {
            navigateToPreviousChapter()
        }

        // Settings FAB
        fabSettings.setOnClickListener {
            openSettingsDrawer()
        }

        // Toolbar buttons
        likeButton.setOnClickListener {
            toggleLike()
        }

        shareButton.setOnClickListener {
            shareStory()
        }

        menuButton.setOnClickListener {
            openSettingsDrawer()
        }

        // Drawer action buttons
        saveToFavorites.setOnClickListener {
            saveStoryToFavorites()
        }

        shareWhatsApp.setOnClickListener {
            shareToWhatsApp()
        }

        shareAll.setOnClickListener {
            shareStory()
        }

        autoScroll.setOnClickListener {
            toggleAutoScroll()
        }

        textToSpeech.setOnClickListener {
            startTextToSpeech()
        }
    }

    private fun setupSeekBarListeners() {
        // Text size seek bar
        textSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateTextSize(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Save settings
                saveReadingSettings()
            }
        })

        // Text spacing seek bar
        textSpacingSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateTextSpacing(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Save settings
                saveReadingSettings()
            }
        })

        // Brightness seek bar
        brightnessSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateBrightness(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Save settings
                saveReadingSettings()
            }
        })
    }

    private fun setupFontSelectionListeners() {
        val fontViews = listOf(fontDefault, fontSerif, fontSansSerif, fontMonospace)

        fontDefault.setOnClickListener {
            selectFont(0, fontViews)
        }

        fontSerif.setOnClickListener {
            selectFont(1, fontViews)
        }

        fontSansSerif.setOnClickListener {
            selectFont(2, fontViews)
        }

        fontMonospace.setOnClickListener {
            selectFont(3, fontViews)
        }
    }

    private fun setupThemeSelectionListeners() {
        themeLight.setOnClickListener {
            applyTheme(THEME_LIGHT)
            themelightimage.setBackgroundResource(R.drawable.ic_check_circle)


            themeDarkimage.setBackgroundResource(R.drawable.ic_circle_outline)
            themeSepiaimage.setBackgroundResource(R.drawable.ic_circle_outline)
            themeCustomImage.setBackgroundResource(R.drawable.ic_circle_outline)

        }

        themeDark.setOnClickListener {
            applyTheme(THEME_DARK)
            themeDarkimage.setBackgroundResource(R.drawable.ic_check_circle_white)



            themelightimage.setBackgroundResource(R.drawable.ic_circle_outline)
            themeSepiaimage.setBackgroundResource(R.drawable.ic_circle_outline)
            themeCustomImage.setBackgroundResource(R.drawable.ic_circle_outline)
        }

        themeSepia.setOnClickListener {
            applyTheme(THEME_SEPIA)

            themeSepiaimage.setBackgroundResource(R.drawable.ic_check_circle)


            themeDarkimage.setBackgroundResource(R.drawable.ic_circle_outline)
            themelightimage.setBackgroundResource(R.drawable.ic_circle_outline)
            themeCustomImage.setBackgroundResource(R.drawable.ic_circle_outline)


        }

        themeCustom.setOnClickListener {
            applyTheme(THEME_CUSTOM)

            themeCustomImage.setBackgroundResource(R.drawable.ic_check_circle)

            themeDarkimage.setBackgroundResource(R.drawable.ic_circle_outline)
            themelightimage.setBackgroundResource(R.drawable.ic_circle_outline)
            themeSepiaimage.setBackgroundResource(R.drawable.ic_circle_outline)

        }
    }

    private fun setupPullGesture() {
        // This would be implemented with a custom touch listener or gesture detector
        // For simplicity, let's just use the pull handle as a button for now
        pullHandle.setOnClickListener {
            toggleToolbarVisibility()
        }
    }

    private fun toggleToolbarVisibility() {
        isToolbarVisible = !isToolbarVisible

        if (isToolbarVisible) {
            toolbar.visibility = View.VISIBLE
            pullHandle.visibility = View.GONE
        } else {
            toolbar.visibility = View.GONE
            pullHandle.visibility = View.VISIBLE
        }
    }

   /* private fun loadStoryData() {
        // In a real app, you would load the story from intent or database
        // For now, let's create a dummy story
        currentStory = Story(
            id = "story_123",
            title = "The Adventure Begins",
            author = "John Writer",
            coverImage = R.drawable.placeholder_image,
            chapters = listOf(
                Chapter(
                    title = "Chapter 2: The Journey",
                    content = "tes"
                ),
                Chapter(
                    title = "Chapter 2: The Journey",
                    content ="."
                )
            ),
            readingTimeMinutes = 5,
            totalUsers = 1200
        )

        // Update UI with story data
        updateStoryUI()
    }*/

    private fun loadStoryData() {
        // Get data from Intent
        val intent = intent
        val title = intent.getStringExtra("title") ?: "Unknown Title"
        val storyContent = intent.getStringExtra("story") ?: "No Content"
        val author = intent.getStringExtra("author") ?: "Unknown Author"
        val category = intent.getStringExtra("category") ?: "Unknown Category"
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""
        val likes = intent.getIntExtra("likes", 0)
        val postedDate = intent.getStringExtra("posted") ?: "Unknown Date"

        // Create a Story object
        currentStory = Story(
            id = "story_${System.currentTimeMillis()}", // Generate unique ID
            title = title,
            author = author,
            coverImage = R.drawable.placeholder_image, // If using a URL, you need to load it with Glide/Picasso
            chapters = listOf(
                Chapter(
                    title = title, // Using title as the chapter title
                    content = storyContent
                )
            ),
            readingTimeMinutes = storyContent.length / 200, // Approximate reading time
            totalUsers = likes // Using likes as an indicator of total users (modify as needed)
        )

        // Update UI with story data
        updateStoryUI()
    }


    private fun updateStoryUI() {
        currentStory?.let { story ->
            // Set story title in toolbar
            storyTitle.text = story.title

            // Set story image
            storyImage.setImageResource(story.coverImage)

            // Set reading time
            readingTime.text = "${story.readingTimeMinutes} min read"

            // Load current chapter
            val currentChapter = story.chapters.getOrNull(currentChapterIndex)
            currentChapter?.let { chapter ->
                chapterTitle.text = chapter.title
                storyContent.text = chapter.content

                // Update chapter navigation buttons
                previousChapterButton.isEnabled = currentChapterIndex > 0
                nextChapterButton.isEnabled = currentChapterIndex < story.chapters.size - 1
            }

            // Update reading progress
            val progress = if (story.chapters.isNotEmpty()) {
                (currentChapterIndex.toFloat() / story.chapters.size.toFloat() * 100).toInt()
            } else {
                0
            }
            readingProgress.progress = progress
        }
    }

    private fun navigateToNextChapter() {
        currentStory?.let { story ->
            if (currentChapterIndex < story.chapters.size - 1) {
                currentChapterIndex++
                updateStoryUI()

                // Scroll to top of the new chapter
                storyScrollView.scrollTo(0, 0)
            }
        }
    }

    private fun navigateToPreviousChapter() {
        if (currentChapterIndex > 0) {
            currentChapterIndex--
            updateStoryUI()

            // Scroll to top of the new chapter
            storyScrollView.scrollTo(0, 0)
        }
    }

    private fun openSettingsDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun toggleLike() {
        isLiked = !isLiked

        // Update like button icon
        val likeIconRes = if (isLiked) {
            R.drawable.icon_new
        } else {
            R.drawable.ic_google
        }

        likeButton.setImageResource(likeIconRes)

        // In a real app, you would update this in a database
    }

    private fun shareStory() {
        currentStory?.let { story ->
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, story.title)
                putExtra(Intent.EXTRA_TEXT, "Check out this amazing story: ${story.title}")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

    private fun shareToWhatsApp() {
        currentStory?.let { story ->
            try {
                val whatsappIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    `package` = "com.whatsapp"
                    putExtra(Intent.EXTRA_TEXT, "Check out this amazing story: ${story.title}")
                }
                startActivity(whatsappIntent)
            } catch (e: Exception) {
                // WhatsApp not installed
                // Show a toast or snackbar message
            }
        }
    }

    private fun saveStoryToFavorites() {
        // In a real app, you would save to database
        // Show a toast or snackbar message
    }

    private fun toggleAutoScroll() {
        isAutoScrollEnabled = !isAutoScrollEnabled

        if (isAutoScrollEnabled) {
            // Start auto-scrolling
            startAutoScroll()
        } else {
            // Stop auto-scrolling
            stopAutoScroll()
        }
    }

    private fun startAutoScroll() {
        // Implementation of auto-scroll feature
        // This would be done with a handler and runnable in a real app
    }

    private fun stopAutoScroll() {
        // Stop the auto-scroll handler
    }

    private fun startTextToSpeech() {
        // Implementation of text-to-speech feature
        // This would use Android's TextToSpeech API in a real app
    }

    private fun initializeReadingSettings() {
        // Load saved preferences
        val sharedPrefs = getSharedPreferences("reading_preferences", Context.MODE_PRIVATE)

        // Text size
        val savedTextSize = sharedPrefs.getInt("text_size", 50)
        textSizeSeekBar.progress = savedTextSize
        updateTextSize(savedTextSize)

        // Text spacing
        val savedTextSpacing = sharedPrefs.getInt("text_spacing", 50)
        textSpacingSeekBar.progress = savedTextSpacing
        updateTextSpacing(savedTextSpacing)

        // Font
        val savedFont = sharedPrefs.getInt("font", 0)
        selectFont(savedFont, listOf(fontDefault, fontSerif, fontSansSerif, fontMonospace))

        // Theme
        val savedTheme = sharedPrefs.getInt("theme", THEME_LIGHT)
        applyTheme(savedTheme)

        // Brightness
        val savedBrightness = sharedPrefs.getInt("brightness", 70)
        brightnessSeekBar.progress = savedBrightness
        updateBrightness(savedBrightness)
    }

    private fun updateTextSize(progress: Int) {
        // Map progress (0-100) to text size (12sp-24sp)
        val minSize = 12
        val maxSize = 24
        val newSize = minSize + (maxSize - minSize) * progress / 100

        storyContent.textSize = newSize.toFloat()
    }

    private fun updateTextSpacing(progress: Int) {
        // Map progress (0-100) to line spacing multiplier (1.0-2.0)
        val minSpacing = 1.0f
        val maxSpacing = 2.0f
        val newSpacing = minSpacing + (maxSpacing - minSpacing) * progress / 100

        storyContent.setLineSpacing(0f, newSpacing)
    }

    private fun updateBrightness(progress: Int) {
        // Update screen brightness
        val brightness = progress / 100f

        val params = window.attributes
        params.screenBrightness = brightness
        window.attributes = params
    }

    private fun selectFont(fontIndex: Int, fontViews: List<TextView>) {
        // Update UI
        for (i in fontViews.indices) {
            if (i == fontIndex) {
                fontViews[i].setBackgroundResource(R.drawable.send)
                fontViews[i].setTextColor(ContextCompat.getColor(this, R.color.white))
            } else {
                fontViews[i].setBackgroundResource(R.drawable.category)
                fontViews[i].setTextColor(ContextCompat.getColor(this, R.color.accent))
            }
        }

        // Apply font to content
        val typeface = when (fontIndex) {
            1 -> android.graphics.Typeface.SERIF
            2 -> android.graphics.Typeface.SANS_SERIF
            3 -> android.graphics.Typeface.MONOSPACE
            else -> android.graphics.Typeface.DEFAULT
        }

        storyContent.typeface = typeface

        // Save preference
        getSharedPreferences("reading_preferences", Context.MODE_PRIVATE).edit()
            .putInt("font", fontIndex)
            .apply()
    }

    private fun applyTheme(themeIndex: Int) {
        currentTheme = themeIndex

        // Update UI selections
        val themeViews = listOf(themeLight, themeDark, themeSepia, themeCustom)

        for (i in themeViews.indices) {
           /* val checkIcon = themeViews[i].findViewById<ImageView>(R.id.theme_light.findViewById<ImageView>(android.R.id.icon).id)

            if (i == themeIndex) {
                checkIcon.setImageResource(R.drawable.ic_google)
            } else {
                checkIcon.setImageResource(R.drawable.ic_google)
            }*/

        }






        // Apply theme colors
        when (themeIndex) {
            THEME_LIGHT -> {
                storyContainer.setBackgroundColor(Color.WHITE)
                storyContent.setTextColor(Color.BLACK)
            }
            THEME_DARK -> {
                storyContainer.setBackgroundColor(Color.parseColor("#121212"))
                storyContent.setTextColor(Color.WHITE)
            }
            THEME_SEPIA -> {
                storyContainer.setBackgroundColor(Color.parseColor("#F8F1E3"))
                storyContent.setTextColor(Color.parseColor("#5B4636"))
            }
            THEME_CUSTOM -> {
                // This would allow user to pick custom colors
                // For now, use a nice blue theme
                storyContainer.setBackgroundColor(Color.parseColor("#E8F0F8"))
                storyContent.setTextColor(Color.parseColor("#2C3E50"))
            }
        }

        // Save preference
        getSharedPreferences("reading_preferences", Context.MODE_PRIVATE).edit()
            .putInt("theme", themeIndex)
            .apply()
    }

    private fun saveReadingSettings() {
        getSharedPreferences("reading_preferences", Context.MODE_PRIVATE).edit().apply {
            putInt("text_size", textSizeSeekBar.progress)
            putInt("text_spacing", textSpacingSeekBar.progress)
            putInt("brightness", brightnessSeekBar.progress)
            apply()
        }
    }

    private fun getSystemBrightness(): Float {
        try {
            val brightness = Settings.System.getInt(
                contentResolver,
                Settings.System.SCREEN_BRIGHTNESS
            )
            return brightness / 255f
        } catch (e: Settings.SettingNotFoundException) {
            return 0.5f
        }
    }

    override fun onPause() {
        super.onPause()
        // Restore system brightness when leaving the activity
        val params = window.attributes
        params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
        window.attributes = params
    }

    // Data classes for story model
    data class Story(
        val id: String,
        val title: String,
        val author: String,
        val coverImage: Int, // Resource ID
        val chapters: List<Chapter>,
        val readingTimeMinutes: Int,
        val totalUsers: Int
    )

    data class Chapter(
        val title: String,
        val content: String
    )
}