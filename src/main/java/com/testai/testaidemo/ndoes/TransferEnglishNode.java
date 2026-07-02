package com.testai.testaidemo.ndoes;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import org.springframework.ai.chat.client.ChatClient;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.Map;

/**
 *
 *@author zhang_jiawang
 *@date 2026/7/2
 *@description
 */
public class TransferEnglishNode implements NodeAction {

    private final ChatClient chatClient;

    public TransferEnglishNode(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public Map<String, Object> apply(OverAllState state) throws Exception {
        String sentence= state.value("sentence", "");
         Flux<String> content = chatClient.prompt().user(t->t.text("根据输入的句子:{msg}" +
                "翻译成英文").param("sentence", sentence)).stream().content();
         StringBuilder sb = new StringBuilder();
         //阻塞在这里，收集LLm流式输出的内容放到sb中
         content.doOnNext(sb::append).blockFirst();
        return Collections.singletonMap("english", sb.toString());
    }
}
