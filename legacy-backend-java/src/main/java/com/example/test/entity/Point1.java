package com.example.test.entity;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Point1 {

	@ExcelProperty("id")
	private String id;

	@TableField(exist = false)
	@ExcelProperty("经度")
	private Double lon;

	@TableField(exist = false)
	@ExcelProperty("维度")
	private Double lat;

	@ExcelProperty("震级_M")
	private Double scale;

	@ExcelProperty("深度_km")
	private Double depth;

	@ExcelProperty("时间")
	private String time;


	@ExcelProperty("发生地")
	private String location;

	@ExcelProperty("所在城市")
	private String city;

	@TableField(exist = false)
	@ExcelProperty("数据类型")
	private String type;

	@ExcelProperty("d_id")
	private String dId;
}
