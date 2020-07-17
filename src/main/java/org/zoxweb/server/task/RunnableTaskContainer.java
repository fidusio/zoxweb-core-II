package org.zoxweb.server.task;

class RunnableTaskContainer
        implements TaskExecutor
{
    private final Runnable runnable;
    RunnableTaskContainer(Runnable runnable)
    {
        this.runnable = runnable;
    }

    @Override
    public void executeTask(TaskEvent event) {
        runnable.run();
    }

    @Override
    public void finishTask(TaskEvent event) {

    }

}
