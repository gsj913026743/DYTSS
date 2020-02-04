package com.nhsoft.dormitory.ui;

import priv.lzf.mvvmhabit.base.BaseActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.nhsoft.dormitory.BR;
import com.nhsoft.dormitory.R;
import com.nhsoft.dormitory.databinding.ActivityDormitoryBinding;
import com.nhsoft.dormitory.viewModel.DormitoryViewModel;

import java.util.ArrayList;

public class DormitoryActivity extends BaseActivity<ActivityDormitoryBinding, DormitoryViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_dormitory;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        binding.pcDiscipline.setHoleRadius(0);
        binding.pcDiscipline.setTransparentCircleRadius(1);
        Description description = new Description();
        description.setText("");
        binding.pcDiscipline.setDescription(description);
//        binding.pcDiscipline.setDrawCenterText(true);  //饼状图中间可以添加文字
//        binding.pcDiscipline.setCenterTextOffset(1000,100);
//        binding.pcDiscipline.setCenterTextColor(R.color.color_32A9E7);
//        binding.pcDiscipline.setCenterText("2017年季度收入");  //饼状图中间的文字
//        binding.pcDiscipline.setDrawHoleEnabled(true);
//        binding.pcDiscipline.setRotationAngle(90); // 初始旋转角度
        binding.pcDiscipline.setRotationEnabled(false); // 可以手动旋转
//        binding.pcDiscipline.setUsePercentValues(true);
        // mChart.setUsePercentValues(true);  //显示成百分比
        // 设置可触摸
//        binding.pcDiscipline.setTouchEnabled(true);
        // 设置数据
        binding.pcDiscipline.setData(getPieData());
        binding.pcDiscipline.setEntryLabelColor(R.color.color_32A9E7);
//        binding.pcDiscipline.set(R.color.color_32A9E7);

//        // 取消高亮显示
//        binding.pcDiscipline.highlightValues(null);
//        binding.pcDiscipline.invalidate();

        Legend mLegend = binding.pcDiscipline.getLegend();  //设置比例图
        mLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        mLegend.set
//        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
        mLegend.setForm(Legend.LegendForm.SQUARE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(27f);
        mLegend.setYEntrySpace(18f);
//        mLegend.setTextColor(R.color.color_ffffff);

        //设置动画
        binding.pcDiscipline.animateXY(1000, 1000);

    }

    private PieData getPieData() {
        // xVals用来表示每个饼块上的文字
//        ArrayList<String> xValues = new ArrayList<String>();
//
//        for (int i = 0; i < 4; i++) {
//            xValues.add((i + 1) + "季度");
//        }

        // yVals用来表示封装每个饼块的实际数据
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        // 饼图数据
        float quarterly1 = 456787;
        float quarterly2 = 534283;
        float quarterly3 = 345734;
        float quarterly4 = 658465;

        yValues.add(new PieEntry(quarterly1, "a",0));
        yValues.add(new PieEntry(quarterly2, "b",1));
        yValues.add(new PieEntry(quarterly3, "c",2));
        yValues.add(new PieEntry(quarterly4, "d",3));

        // y轴集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "");
//        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
        pieDataSet.setValueLineVariableLength(false);

        pieDataSet.setYValuePosition( PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueTextColor(R.color.color_ffffff);
//        pieDataSet.setSliceSpace(0f);
//        pieDataSet.setSelectionShift(5f);
        pieDataSet.setValueLinePart1Length(1.2f);
        pieDataSet.setValueLinePart2Length(0.3f);
        pieDataSet.setValueLineColor(R.color.color_ffffff);
        pieDataSet.setValueLineWidth(1f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));

        // 设置饼图颜色
        pieDataSet.setColors(colors);

        // 创建饼图数据
        PieData pieData = new PieData(pieDataSet);

        return pieData;
    }

}
