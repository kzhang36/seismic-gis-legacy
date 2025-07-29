package com.example.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.test.entity.FaultLines;
import com.example.test.mapper.FaultLinesMapper;
import com.example.test.service.FaultLinesService;
import org.springframework.stereotype.Service;

@Service
public class FaultLinesServiceImpl extends ServiceImpl<FaultLinesMapper, FaultLines> implements FaultLinesService  {
}
