package esze.utils;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Title {


    private String title = "";
    private ChatColor titleColor = ChatColor.WHITE;

    private String subtitle = "";
    private ChatColor subtitleColor = ChatColor.WHITE;

    private int fadeInTime = -1;
    private int stayTime = -1;
    private int fadeOutTime = -1;
    private boolean ticks = false;

    public Title() {

    }

    public Title(String title) {
        this.title = title;

    }

    public Title(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;

    }

    public Title(Title title) {
        // Copy title
        this.title = title.getTitle();
        this.subtitle = title.getSubtitle();
        this.titleColor = title.getTitleColor();
        this.subtitleColor = title.getSubtitleColor();
        this.fadeInTime = title.getFadeInTime();
        this.fadeOutTime = title.getFadeOutTime();
        this.stayTime = title.getStayTime();
        this.ticks = title.isTicks();
        //loadClasses();
    }

    public Title(String title, String subtitle, int fadeInTime, int stayTime,
                 int fadeOutTime) {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeInTime = fadeInTime;
        this.stayTime = stayTime;
        this.fadeOutTime = fadeOutTime;
        //   loadClasses();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setTitleColor(ChatColor color) {
        this.titleColor = color;
    }

    public void setSubtitleColor(ChatColor color) {
        this.subtitleColor = color;
    }

    public Title setFadeInTime(int time) {
        this.fadeInTime = time;
        return this;
    }


    public Title setFadeOutTime(int time) {
        this.fadeOutTime = time;
        return this;
    }

    public Title setStayTime(int time) {
        this.stayTime = time;
        return this;
    }

    public void setTimingsToTicks() {
        ticks = true;
    }

    public void setTimingsToSeconds() {
        ticks = false;
    }

    public void send(Player player) {

        player.sendTitle(getTitle(), getSubtitle(), fadeInTime, stayTime, fadeOutTime);

    }

    public void sendAll() {

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle(getTitle(), getSubtitle(), fadeInTime, stayTime, fadeOutTime);
        }
    }


    public ChatColor getTitleColor() {
        return titleColor;
    }

    public ChatColor getSubtitleColor() {
        return subtitleColor;
    }

    public int getFadeInTime() {
        return fadeInTime;
    }

    public int getFadeOutTime() {
        return fadeOutTime;
    }

    public int getStayTime() {
        return stayTime;
    }

    public boolean isTicks() {
        return ticks;
    }

}
