package com.example.test.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
@Data
public class FaultLines {

	private String id;

	private String name;

	private String time;

	private Double length;

//	private double

	@TableField(exist = false)
	private String wkt;
}
