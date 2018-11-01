package ro.upt.sma.heart.presenters.monitor

interface HeartMonitorPresenter {
    fun bind(view: HeartMonitorView)
    fun unbind()
}
