package com.example.test.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class GPointVo {

	private Integer id;

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
