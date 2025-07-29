package com.example.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.test.entity.GPoint;
import com.example.test.mapper.GPointMapper;
import com.example.test.service.GPointServece;
import org.springframework.stereotype.Service;

@Service
public class GPointServiceImpl extends ServiceImpl<GPointMapper, GPoint> implements GPointServece {
}
