package com.example.test.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("global_point")
public class GPoint {

	private String id;

	@TableField(exist = false)
	private Double lat;

	@TableField(exist = false)
	private Double lon;

	private String location;

	private String time;

	private Double scale;

	private Double depth;

	private String dId;
}
