package ro.upt.sma.heart.presenters.assistant

interface HeartAssistantPresenter {
    fun bind(view: HeartAssistantView)
    fun unbind()
}
