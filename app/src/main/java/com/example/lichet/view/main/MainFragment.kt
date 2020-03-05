package com.example.lichet.view.main

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.lichet.R
import com.example.lichet.api.response.HeartBeat
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_heart_beat.*

class MainFragment: Fragment(), MainView.MainActivity.OnBackKeyPressedListener {

    private var listHeartBeat: List<HeartBeat>? = null

    // スタイルとフォントファミリーの設定
    private var mTypeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)

    fun newInstance(listHeartBeat: List<HeartBeat>): MainFragment{
        this.listHeartBeat = listHeartBeat
        return this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_heart_beat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listHeartBeat?: return

        setupLineChart()

        heartBeatChart.data = lineData()
    }

    private fun setupLineChart(){
        heartBeatChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            // 拡大縮小可能
            isScaleXEnabled = true
            setPinchZoom(false)
            setDrawGridBackground(false)

            //データラベルの表示
            legend.apply {
                form = Legend.LegendForm.LINE
                typeface = mTypeface
                textSize = 11f
                textColor = Color.BLACK
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }

            //y軸右側の設定
            axisRight.isEnabled = false

            //X軸表示
            xAxis.apply {
                typeface = mTypeface
                textColor = Color.BLACK
                setDrawLabels(true)
                // 格子線を表示する
                setDrawGridLines(true)
            }

            //y軸左側の表示
            axisLeft.apply {
                typeface = mTypeface
                textColor = Color.BLACK
                // 格子線を表示する
                setDrawGridLines(true)
            }
        }
    }

    private fun lineData(): LineData {
        val values = mutableListOf<Entry>()

        listHeartBeat?.forEach{
            // 値はランダムで表示させる
            val time = it.pnn_time.toFloat()
            val pnn = it.pnn?.toFloat()?: 0f
            values.add(Entry(time, pnn))
        }
        val yValue = LineDataSet(values, getString(R.string.chart_heart_beat)).apply {
            axisDependency =  YAxis.AxisDependency.LEFT
            color = Color.BLACK
            // タップ時のハイライトカラー
            highLightColor = Color.YELLOW
            setDrawCircles(true)
            setDrawCircleHole(true)
            // 点の値非表示
            setDrawValues(false)
            // 線の太さ
            lineWidth = 2f
        }
        return LineData(yValue)
    }

    private fun deleteFragment() {
        fragmentManager?.beginTransaction()?.remove(this)?.commit()
        activity?.findViewById<LinearLayout>(R.id.ll_select)?.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        deleteFragment()
    }

    override fun onBackPressed() {
        deleteFragment()
    }
}