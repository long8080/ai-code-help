<script setup lang="ts">
import { nextTick, ref } from 'vue'
import { sendChat } from './api/chat'

interface Message {
  id: number
  role: 'user' | 'assistant'
  content: string
}

const input = ref('')
const messages = ref<Message[]>([])
const loading = ref(false)
const error = ref('')
const listRef = ref<HTMLElement | null>(null)
let idSeq = 0

async function scrollToBottom() {
  await nextTick()
  const el = listRef.value
  if (el) {
    el.scrollTop = el.scrollHeight
  }
}

async function onSubmit() {
  const text = input.value.trim()
  if (!text || loading.value) return

  error.value = ''
  messages.value.push({ id: ++idSeq, role: 'user', content: text })
  input.value = ''
  loading.value = true
  await scrollToBottom()

  try {
    const reply = await sendChat(text)
    messages.value.push({ id: ++idSeq, role: 'assistant', content: reply })
  } catch (e) {
    error.value = e instanceof Error ? e.message : '发送失败'
  } finally {
    loading.value = false
    await scrollToBottom()
  }
}

function onKeydown(e: KeyboardEvent) {
  if (e.key === 'Enter' && !e.shiftKey) {
    e.preventDefault()
    onSubmit()
  }
}
</script>

<template>
  <div class="layout">
    <header class="header">
      <h1>AI Code Help</h1>
      <p>编程助手 · 对接 <code>/api/ai/chat</code></p>
    </header>

    <main ref="listRef" class="messages">
      <div v-if="messages.length === 0" class="empty">
        输入编程问题，按 Enter 发送（Shift+Enter 换行）
      </div>
      <article
        v-for="msg in messages"
        :key="msg.id"
        class="bubble"
        :class="msg.role"
      >
        <span class="role">{{ msg.role === 'user' ? '你' : 'AI' }}</span>
        <pre class="content">{{ msg.content }}</pre>
      </article>
      <div v-if="loading" class="bubble assistant loading">
        <span class="role">AI</span>
        <p class="content">思考中…</p>
      </div>
    </main>

    <p v-if="error" class="error">{{ error }}</p>

    <footer class="composer">
      <textarea
        v-model="input"
        class="input"
        rows="3"
        placeholder="描述你的代码问题…"
        :disabled="loading"
        @keydown="onKeydown"
      />
      <button
        type="button"
        class="send"
        :disabled="loading || !input.trim()"
        @click="onSubmit"
      >
        发送
      </button>
    </footer>
  </div>
</template>

<style scoped>
.layout {
  display: flex;
  flex-direction: column;
  height: 100%;
  max-width: 900px;
  margin: 0 auto;
  padding: 0 1rem 1rem;
}

.header {
  padding: 1.25rem 0 0.75rem;
  border-bottom: 1px solid #2a3544;
}

.header h1 {
  margin: 0;
  font-size: 1.35rem;
  font-weight: 600;
}

.header p {
  margin: 0.35rem 0 0;
  font-size: 0.85rem;
  color: #8b9bb0;
}

.header code {
  font-size: 0.8rem;
  color: #7eb8ff;
}

.messages {
  flex: 1;
  overflow-y: auto;
  padding: 1rem 0;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.empty {
  margin: auto;
  text-align: center;
  color: #6b7d94;
  font-size: 0.95rem;
}

.bubble {
  max-width: 92%;
  padding: 0.65rem 0.85rem;
  border-radius: 10px;
  line-height: 1.5;
}

.bubble.user {
  align-self: flex-end;
  background: #1e4d8c;
}

.bubble.assistant {
  align-self: flex-start;
  background: #1a2330;
  border: 1px solid #2a3544;
}

.bubble.loading {
  opacity: 0.85;
}

.role {
  display: block;
  font-size: 0.72rem;
  font-weight: 600;
  color: #8b9bb0;
  margin-bottom: 0.25rem;
}

.content {
  margin: 0;
  white-space: pre-wrap;
  word-break: break-word;
  font-family: inherit;
  font-size: 0.95rem;
}

.error {
  margin: 0 0 0.5rem;
  padding: 0.5rem 0.75rem;
  background: #3d1f24;
  color: #ffb4b4;
  border-radius: 6px;
  font-size: 0.85rem;
}

.composer {
  display: flex;
  gap: 0.65rem;
  padding-top: 0.5rem;
  border-top: 1px solid #2a3544;
}

.input {
  flex: 1;
  resize: none;
  padding: 0.65rem 0.75rem;
  border: 1px solid #2a3544;
  border-radius: 8px;
  background: #151c26;
  color: #e7ecf3;
  font: inherit;
  line-height: 1.45;
}

.input:focus {
  outline: none;
  border-color: #3d6db5;
}

.input:disabled {
  opacity: 0.6;
}

.send {
  align-self: flex-end;
  padding: 0.55rem 1.1rem;
  border: none;
  border-radius: 8px;
  background: #2d6cdf;
  color: #fff;
  font: inherit;
  font-weight: 500;
  cursor: pointer;
}

.send:hover:not(:disabled) {
  background: #3a7af0;
}

.send:disabled {
  opacity: 0.45;
  cursor: not-allowed;
}
</style>
