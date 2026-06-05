export interface ChatRequest {
  message: string
}

export async function sendChat(message: string): Promise<string> {
  const response = await fetch('/api/ai/chat', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ message } satisfies ChatRequest),
  })

  if (!response.ok) {
    const text = await response.text().catch(() => '')
    throw new Error(text || `请求失败 (${response.status})`)
  }

  return response.text()
}
