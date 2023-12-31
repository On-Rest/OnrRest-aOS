import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View.INVISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.chobo.onrest.EmotionChoice1
import com.chobo.onrest.QuestList
import com.chobo.onrest.R
import com.chobo.onrest.databinding.YourEmotionBinding

class YourEmotion : AppCompatActivity() {
    private lateinit var binding: YourEmotionBinding
    private lateinit var aiemotion: String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var emotion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = YourEmotionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        setupListeners()
        handleEmotion()
    }

    private fun initUI() {
        binding.header.setOnClickListener { onBackPressed() }
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        emotion = sharedPreferences.getString("yourEmotion", "defaultValue").toString()
        aiemotion = intent.getStringExtra("emotion1321").toString()
    }

    private fun setupListeners() {
        binding.choice1.setOnClickListener { navigateToQuestList() }
        binding.choice2.setOnClickListener { navigateToEmotionChoice1() }
    }

    private fun handleEmotion() {
        val emotionsMap = mapOf(
            "1" to EmotionData(R.drawable.happy_face, "행복한 햄스터", "happy", "happylist"),
            "2" to EmotionData(R.drawable.angry_face, "화난 햄스터", "sad", "angrylist"),
            "3" to EmotionData(R.drawable.sad_face, "슬픈 햄스터", "angry", "sadlist")
        )

        val emotionData = emotionsMap[aiemotion]
        if (emotionData != null) {
            editor.putString("yourEmotion", emotionData.emotion)
            editor.apply()

            binding.hamsteremotion.text = emotionData.emotionText
            binding.hamsteremotionsrc.setImageResource(emotionData.emotionSrc)
        } else {
            binding.choice1.visibility = INVISIBLE
            binding.hamsteremotion.text = "분석 실패!"
        }
    }

    private fun navigateToQuestList() {
        val intent = Intent(this, QuestList::class.java)
        intent.putExtra("key", aiemotion)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    private fun navigateToEmotionChoice1() {
        startActivity(Intent(this, EmotionChoice1::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    data class EmotionData(val emotionSrc: Int, val emotionText: String, val emotion: String, val aiemotion: String)
}
