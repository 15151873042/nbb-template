<template>
  <codemirror
      v-model="code"
      placeholder="Code goes here..."
      :style="{ height: '400px' }"
      :autofocus="true"
      :indent-with-tab="true"
      :tab-size="2"
      :extensions="extensions"
      @ready="handleReady"
      @change="log('change', $event)"
      @focus="log('focus', $event)"
      @blur="log('blur', $event)"
  />
</template>

<script>
import { defineComponent, ref, shallowRef } from 'vue'
import { Codemirror } from 'vue-codemirror'
import { java } from '@codemirror/lang-java'
import { oneDark } from '@codemirror/theme-one-dark'

export default defineComponent({
  components: {
    Codemirror
  },
  setup() {
    const code = ref(`package com.dynamic;

import cn.hutool.core.io.IoUtil;
import cn.hutool.socket.SocketUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DynamicSocketClient {
    // 发送消息到 TCP 服务器并返回响应
    public String sendMessage(String host, int port, String serialAddressCode) throws Exception {
        String sendMessage = serialAddressCode;
        Socket socket = SocketUtil.connect(host, port, 2000);

        OutputStream outputStream = socket.getOutputStream();
        IoUtil.writeUtf8(outputStream, false, sendMessage);

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024 * 1024 * 8];
        int read = inputStream.read(bytes);
        String receiveMessage = new String(bytes, 0, read);
        System.out.println("获取到服务端返回的消息: " + receiveMessage);
        outputStream.close();
        inputStream.close();
        socket.close();
        return receiveMessage;
    }
}
`)
    const extensions = [java(), oneDark]

    // Codemirror EditorView instance ref
    const view = shallowRef()
    const handleReady = (payload) => {
      view.value = payload.view
    }

    // Status is available at all times via Codemirror EditorView
    const getCodemirrorStates = () => {
      const state = view.value.state
      const ranges = state.selection.ranges
      const selected = ranges.reduce((r, range) => r + range.to - range.from, 0)
      const cursor = ranges[0].anchor
      const length = state.doc.length
      const lines = state.doc.lines
      // more state info ...
      // return ...
    }

    return {
      code,
      extensions,
      handleReady,
      log: console.log
    }
  }
})
</script>
