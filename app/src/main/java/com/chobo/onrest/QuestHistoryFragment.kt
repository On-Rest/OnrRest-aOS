package com.chobo.onrest

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chobo.onrest.databinding.QuestHistoryBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date

class QuestHistoryFragment : Fragment(), ToggleStateChangeListener {

        private lateinit var binding: QuestHistoryBinding
    lateinit var questHistoryAdapter: QuestHistoryAdapter
    private lateinit var toggleStateChangeListener: ToggleStateChangeListener // 변수 선언
    val datas = mutableListOf<QuestHistoryData>()
    val date = Date() // 현재 날짜와 시간 가져오기
    val year = SimpleDateFormat("yyyy").format(date) // 일만 가져오기
    val month = SimpleDateFormat("MM").format(date) // 일만 가져오기
    val fileLines = mutableListOf<String>()
    val yearMonthList = mutableListOf<String>()
    var index = 0
    val dialogFragment = QuestHistoryPopup()

    override fun onStart(){
        super.onStart()
        readFile(requireContext())
        initRecycler()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toggleStateChangeListener = this // 또는 원하는 대상으로 설정
        // QuestHistoryAdapter 인스턴스를 생성할 때 ToggleStateChangeListener를 전달합니다.
        questHistoryAdapter = QuestHistoryAdapter(requireContext(), toggleStateChangeListener)
        // 리사이클러뷰 설정 등의 작업 수행
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = QuestHistoryBinding.inflate(inflater, container, false)
        initDateFilter()
        dateFilter()

        return binding.root
    }
    override fun onToggleStateChanged(position: Int, isChecked: Boolean) {
        // 상태가 변경된 아이템의 위치(position)와 상태(isChecked)를 여기서 사용할 수 있습니다
        Log.d("ToggleStateChanged", "Position: $position, Checked: $isChecked")
        dialogFragment.show(requireActivity().supportFragmentManager, "QuestHistoryPopup")
    }
    private fun initDateFilter(){
        for (year in 2023..2025) {
            for (month in 1..12) {
                yearMonthList.add("${year}년 ${month}월")
            }
        }
        index = yearMonthList.indexOf("${year}년 ${month}월")
        binding.todayMonthText.text = yearMonthList[index]
    }
    private fun dateFilter(){
        binding.goback.setOnClickListener(){
            index--
            binding.todayMonthText.text = yearMonthList[index]
            initRecycler()
        }
        binding.gonext.setOnClickListener(){
            index++
            binding.todayMonthText.text = yearMonthList[index]
            initRecycler()
        }
    }
    private fun readFile(context: Context){
        val fileName = "${year}"
        // 기존 데이터를 지웁니다.
        fileLines.clear()

        try {
            val fileInputStream = context.openFileInput(fileName)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                fileLines.add(line.orEmpty()) // 각 줄의 데이터를 리스트에 추가합니다.
            }

            fileInputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun initRecycler() {
        binding.questList.adapter = questHistoryAdapter
        datas.clear()


        for(i in 0 until (fileLines.size - 2) step 3) {
            val currentDate = "${fileLines[i].substring(0,4)}년 ${fileLines[i].substring(5,7)}월"
            if (yearMonthList[index] == currentDate){
                Log.d("ad", currentDate)
                val day = fileLines[i].substring(8,10)
                datas.add(QuestHistoryData("${day}일", fileLines[i + 1], fileLines[i + 2].toBoolean()))
            }
        }

        questHistoryAdapter.datas = datas
        questHistoryAdapter.notifyDataSetChanged()
    }
}