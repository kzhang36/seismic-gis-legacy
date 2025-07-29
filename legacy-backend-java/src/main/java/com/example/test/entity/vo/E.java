package com.example.test.entity.vo;

import com.example.test.entity.Point1;
import lombok.Data;

import java.util.List;

/**
 * excel 下载对象
 */
@Data
public class E {

	private List<Point1> points;

	private String title;
}
