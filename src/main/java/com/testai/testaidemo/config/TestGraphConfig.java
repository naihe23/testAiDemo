package com.testai.testaidemo.config;

import com.alibaba.cloud.ai.graph.KeyStrategy;
import com.alibaba.cloud.ai.graph.KeyStrategyFactory;
import com.alibaba.cloud.ai.graph.StateGraph;
import com.alibaba.cloud.ai.graph.action.AsyncNodeAction;
import com.alibaba.cloud.ai.graph.exception.GraphStateException;
import com.alibaba.cloud.ai.graph.state.strategy.ReplaceStrategy;
import com.testai.testaidemo.ndoes.GenSentenceNode;
import com.testai.testaidemo.ndoes.TransferEnglishNode;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *@author zhang_jiawang
 *@date 2026/7/2
 *@description
 */
@Configuration
public class TestGraphConfig {

    private final ChatClient chatClient;

    public TestGraphConfig(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Bean
    public void testGraph(){
        KeyStrategyFactory keyStrategyFactory= () -> {
            Map<String, KeyStrategy> map = new HashMap<>();
            map.put("msg", new ReplaceStrategy());
            map.put("sentence", new ReplaceStrategy());
            map.put("english", new ReplaceStrategy());
            return map;
        };
        StateGraph stateGraph = new StateGraph("testGraph", keyStrategyFactory);
        // 添加节点
        try {
            stateGraph.addNode("genSentence", AsyncNodeAction.node_async(new GenSentenceNode(chatClient)));
            stateGraph.addNode("transferEnglish", AsyncNodeAction.node_async(new TransferEnglishNode(chatClient)));
        } catch (GraphStateException e) {
            throw new RuntimeException(e);
        }

        //添加边
        stateGraph.addEdge("genSentence", "transferEnglish");
    }
}
