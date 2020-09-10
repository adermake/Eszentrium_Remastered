package easyscoreboards;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class AutomaticBoard extends UpdatableBoard implements Runnable
{
    private int taskId, delay;
    ///ssssss
    public AutomaticBoard(int delay)
    {
        this.taskId = -1;
        this.delay = delay;
    }
    //SSSAAS
    public void start(Plugin plugin)
    {
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, this, 0, this.delay).getTaskId();
    }
    //SSS
    public void stop()
    {
        if(this.taskId != -1)
        {
            Bukkit.getScheduler().cancelTask(this.taskId);
            this.taskId = -1;
        }
    }
    
    @Override
    public void run()
    {
        this.update();   
    }
}
