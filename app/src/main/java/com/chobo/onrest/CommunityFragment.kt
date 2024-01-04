package com.chobo.onrest

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import com.chobo.onrest.databinding.CommunityBinding
import com.chobo.onrest.databinding.CommunityPopupViewBinding
import kotlin.math.log

class CommunityFragment : Fragment() {

    private var _binding: CommunityBinding? = null
    private val binding get() = _binding!!
    private lateinit var communityAdapter: CommunityAdapter
    private val datas = mutableListOf<CommunityData>()
    var tagList = mutableListOf<String>()
    var List = mutableListOf<CommunityData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CommunityBinding.inflate(inflater, container, false)
        initRecycler()
        showCustomPopup(binding.filter, requireContext())
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecycler() {
        datas.clear()
        List.clear()
        communityAdapter = CommunityAdapter(requireContext())
        binding.list.adapter = communityAdapter
        List.add(CommunityData("나 오늘 화난썰있어", "태윤이가 일을 너무 잘해서 화났어", "#행복", "#화난", 0, 2))
        List.add(CommunityData("로봇 훈련일지", "가낟쟈ㅕㄹ효가ㅓㅣ더ㅣ렁ㅈ뷰ㅏ로류ㅏㅓㅍ라ㅓㅇ론ㅇ포ㅕㄱ도ㅓㅜㅍㅎㄱ닳궆갖도ㅓㅜ가ㅗ,뒆잧,ㅗㅓㄱ드추조,ㅡ곶,ㅎㄷㄱㄱ,ㅗㅈㄷ훠극슈ㅗㅅㄱ고ㅓㅅㄱㄷㄷ쇽ㄷㄹ효ㅕ", "#짜증", "#신난", 1, 3))
        List.add(CommunityData("ㅜㅜ", "슬픈썰임# 구라임", "#즐거운", "#신난", 0, 0))
        List.add(CommunityData("오늘 bsc갔는데 너무 재밌었다", "해킹은 즐거워 정맗붥", "#평온", "#신난", 0, 1))
        List.add(CommunityData("해해 행복해", "this is 내용", "#행복", "#신난", 0, 1))
        List.add(CommunityData("아페때문에 너무 슬퍼", "아페를꼭 완성시키고싶었는데", "#짜증", "#화난", 0, 3))
        List.add(CommunityData("잠시 쉬어갈래", "this is 내용", "#행복", "#신난", 0, 2))
        List.add(CommunityData("todo", "아페 통신하기", "#슬픈", "#화난", 0, 1))
        List.add(CommunityData("일기", "this is 내용 이다", "#과나", "#화나", 0, 1))
        List.add(CommunityData("누가 디코를 안봐", "this is 내용", "#행복", "#신난", 0, 0))
        List.add(CommunityData("일해라 정성찬", "this is 내용", "#행복", "#신난", 0, 0))
        List.add(CommunityData("너나일해 ", "this is 내용", "#행복", "#신난", 0, 0))

        for (i in List) {
            if (tagList.contains("happy")) {
                if (i.tag1 == "#행복" || i.tag2 == "#행복") {
                    datas.add(i)
                    continue
                }
            }
            if (tagList.contains("excited")) {
                if (i.tag1 == "#신난" || i.tag2 == "#신난") {
                    datas.add(i)
                    continue
                }
            }
            if (tagList.contains("joyful")) {
                if (i.tag1 == "#즐거운" || i.tag2 == "#즐거운") {
                    datas.add(i)
                    continue
                }
            }
            if (tagList.contains("tranquility")) {
                if (i.tag1 == "#평온" || i.tag2 == "#평온") {
                    datas.add(i)
                    continue
                }
            }
            if (tagList.contains("shy")) {
                if (i.tag1 == "#수줍은" || i.tag2 == "#수줍은") {
                    datas.add(i)
                    continue
                }
            }
            if (tagList.contains("anoying")) {
                if (i.tag1 == "#짜증" || i.tag2 == "#짜증") {
                    datas.add(i)
                    continue
                }
            }
            if (tagList.contains("sad")) {
                if (i.tag1 == "#슬픈" || i.tag2 == "#슬픈") {
                    datas.add(i)
                    continue
                }
            }
            if (tagList.contains("helpless")) {
                if (i.tag1 == "#무기력한" || i.tag2 == "#무기력한") {
                    datas.add(i)
                    continue
                }
            }
            if (tagList.contains("angry")) {
                if (i.tag1 == "#화난" || i.tag2 == "#화난") {
                    datas.add(i)
                    continue
                }
            }
        }
        if (tagList.isEmpty()) {
            datas.addAll(List)
        }
        if (datas.isEmpty()) {
            binding.list.setBackgroundColor(Color.parseColor("#00000000"))
        } else {
            binding.list.setBackgroundColor(Color.parseColor("#F8F5F1"))
        }
        communityAdapter.datas = datas
        communityAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchIcon.setOnClickListener {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            binding.search.clearFocus()
        }
        binding.pen.setOnClickListener {
            startActivityWithAnimation(PostWrite::class.java)
        }
    }

    private fun startActivityWithAnimation(clazz: Class<*>) {
        val intent = Intent(requireContext(), clazz)
        startActivity(intent)
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    @SuppressLint("SuspiciousIndentation")
    fun showCustomPopup(anchorView: View, context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupViewBinding = CommunityPopupViewBinding.inflate(inflater)
        val widthInDp = 340 // 원하는 가로 크기(dp)
        val heightInDp = 123 // 원하는 세로 크기(dp)
        val density = resources.displayMetrics.density
        val widthInPx = (widthInDp * density).toInt()
        val heightInPx = (heightInDp * density).toInt()

        val popupWindow = PopupWindow(popupViewBinding.root, widthInPx, heightInPx, true)
        val toggleListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val tag = when (buttonView.id) {
                R.id.sad -> "sad"
                R.id.helpless -> "helpless"
                R.id.shy -> "shy"
                R.id.anoying -> "anoying"
                R.id.angry -> "angry"
                R.id.joyful -> "joyful"
                R.id.tranquility -> "tranquility"
                R.id.excited -> "excited"
                R.id.happy -> "happy"
                else -> ""
            }

            if (isChecked) {
                if (!tagList.contains(tag)) {
                    tagList.add(tag)
                }
            } else {
                if (tagList.contains(tag)) {
                    tagList.remove(tag)
                }
            }
        }

        popupViewBinding.sad.setOnCheckedChangeListener(toggleListener)
        popupViewBinding.helpless.setOnCheckedChangeListener(toggleListener)
        popupViewBinding.shy.setOnCheckedChangeListener(toggleListener)
        popupViewBinding.anoying.setOnCheckedChangeListener(toggleListener)
        popupViewBinding.angry.setOnCheckedChangeListener(toggleListener)
        popupViewBinding.joyful.setOnCheckedChangeListener(toggleListener)
        popupViewBinding.tranquility.setOnCheckedChangeListener(toggleListener)
        popupViewBinding.excited.setOnCheckedChangeListener(toggleListener)
        popupViewBinding.happy.setOnCheckedChangeListener(toggleListener)

        binding.filter.setOnClickListener {
            if (popupWindow.isShowing) {
                popupWindow.dismiss()
                binding.filter.isChecked = false
            } else {
                popupWindow.showAsDropDown(anchorView, 0, 25)
                binding.filter.isChecked = true
            }
        }

        popupWindow.setOnDismissListener {
            binding.filter.isChecked = false
            initRecycler()
        }
        popupWindow.isOutsideTouchable = true
    }
}