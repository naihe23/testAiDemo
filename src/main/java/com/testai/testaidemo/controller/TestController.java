package com.testai.testaidemo.controller;

import com.alibaba.cloud.ai.graph.CompiledGraph;
import com.alibaba.cloud.ai.graph.OverAllState;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 *
 *@author zhang_jiawang
 *@date 2026/7/3
 *@description
 */
@RestController
public class TestController {

    private final CompiledGraph compiledGraph;

    public TestController(CompiledGraph compiledGraph) {
        this.compiledGraph = compiledGraph;
    }

    @GetMapping("/test")
    public Map<String, Object> test(@RequestParam("msg") String msg) {
        OverAllState state =  compiledGraph.invoke(Collections.singletonMap("msg", msg)).orElse(new OverAllState());
        return state.data();
    }
}
