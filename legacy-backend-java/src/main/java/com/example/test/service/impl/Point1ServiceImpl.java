package com.example.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.test.entity.Point1;
import com.example.test.mapper.Point1Mapper;
import com.example.test.service.Point1Service;
import org.springframework.stereotype.Service;

@Service
public class Point1ServiceImpl extends ServiceImpl<Point1Mapper, Point1> implements Point1Service {
}
